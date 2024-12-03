package com.hms.service;

import com.hms.entity.Review;
import com.hms.repository.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public Review saveReview(Review review) {
        Review save = reviewRepository.save(review);
        return  save;

    }
}
