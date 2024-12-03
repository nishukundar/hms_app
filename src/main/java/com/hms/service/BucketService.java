package com.hms.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//used to upload the file to aws

@Service
public class BucketService {

//    @Autowired
//    private AmazonS3 amazonS3;


        private AmazonS3 s3client;

        @Value("${amazon.s3.region}")
        private String region;
        @Value("${amazon.s3.bucket}")
        private String bucketName;
        @Value("${amazon.aws.access-key-id}")
        private String accessKey;
        @Value("${amazon.aws.access-key-secret}")
        private String secretKey;


        @PostConstruct
        private void initializeAmazon() {
            AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
            this.s3client = AmazonS3ClientBuilder.standard()
                    .withRegion(Regions.fromName(region))
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();
        }

        public String uploadFile(MultipartFile file, String bucketName) {
            String fileUrl = "";
            try {
                File files = convertMultiPartToFile(file);
                String fileName = generateFileName(file);
                uploadFileTos3bucket(fileName, files);
                files.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return fileUrl;
        }

        private File convertMultiPartToFile(MultipartFile file) throws IOException {
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        }

        private String generateFileName(MultipartFile multiPart) {
            return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
        }

        private void uploadFileTos3bucket(String fileName, File file) {
            s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        }

        public String deleteFileFromS3Bucket(String fileUrl) {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
            return "Successfully deleted";
        }

        public List<String> listFiles() {
            ListObjectsRequest listObjectsRequest =
                    new ListObjectsRequest()
                            .withBucketName(bucketName)
                            .withPrefix("/");

            List<String> keys = new ArrayList<>();
            ObjectListing objects = s3client.listObjects(listObjectsRequest);
            while (true) {
                List<S3ObjectSummary> summaries = objects.getObjectSummaries();
                if (summaries.size() < 1) {
                    break;
                }
                for (S3ObjectSummary item : summaries) {
                    if (!item.getKey().endsWith("/"))
                        keys.add(item.getKey());
                }

                objects = s3client.listNextBatchOfObjects(objects);
            }
            return keys;
        }
    }

