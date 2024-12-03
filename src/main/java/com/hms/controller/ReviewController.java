package com.hms.controller;

import com.hms.entity.AppUser;
import com.hms.entity.Property;
import com.hms.entity.Review;
import com.hms.repository.PropertyRepository;
import com.hms.repository.ReviewRepository;
import com.hms.service.ReviewService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/review")
public class ReviewController {

    private ReviewService reviewService;
    private  ReviewRepository reviewRepository;
    private PropertyRepository propertyRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository, PropertyRepository propertyRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
        this.propertyRepository = propertyRepository;
    }

    @PostMapping("/rating")
    public ResponseEntity<?> writeReview(@RequestBody Review  review, @RequestParam Long propertyId, @AuthenticationPrincipal AppUser user){
        Property property = propertyRepository.findById(propertyId).get();

        if(reviewRepository.existsByAppUserAndProperty(user, property)!=null){
            return  new ResponseEntity<String>("Review already exists",  HttpStatus.ALREADY_REPORTED);
        }
        review.setProperty(property);
        review.setAppUser(user);

        Review review1 = reviewService.saveReview(review);
        return new ResponseEntity<>(review1, HttpStatus.OK);
    }
    @GetMapping("/user/review")
    public ResponseEntity<List<Review>> getUserReview( @AuthenticationPrincipal AppUser user){
        List<Review> byAppUser = reviewRepository.findByAppUser(user);
        return  new ResponseEntity<>(byAppUser, HttpStatus.OK);
    }
}
