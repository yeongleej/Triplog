package com.example.travel.route.controller;

import com.example.travel.auth.service.UserService;
import com.example.travel.route.dto.request.ReviewRequestDto;
import com.example.travel.route.dto.response.ReviewResponseDto;
import com.example.travel.route.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/users/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> reviewDetails(@PathVariable Long reviewId) {
        ReviewResponseDto response;

        try {
            response = reviewService.reviewDetails(reviewId);
        } catch (SQLException e) {
            log.info("Review 불러오기 실패: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        log.info(response.getReview_id() + " " + response.getPlace_id());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping("/{routeId}/all")
    public ResponseEntity<?> findReviewsByRoute(@PathVariable Long routeId) {

        try {
            List<ReviewResponseDto> reviewList = reviewService.findReviewsByRoute(routeId);
            if(reviewList != null && !reviewList.isEmpty()) {
                return new ResponseEntity<>(reviewList, HttpStatus.OK);
//				return new ResponseEntity<List<MemberDto>>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (SQLException e) {
            log.info("Review 불러오기 실패: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping
    public ResponseEntity<?> registerReview(@ModelAttribute ReviewRequestDto reviewRequest) {
        ReviewResponseDto review;

        try {
            review = reviewService.registerReview(reviewRequest);
        } catch (SQLException | IOException e) {
            log.info("Review 저장 실패: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<?> updateReviewText(@PathVariable Long reviewId, @RequestBody String reviewText) {
        try {
            reviewService.changeReviewText(reviewId, reviewText);
        } catch (SQLException e) {
            log.info("Failed to update review: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteReview(@PathVariable Long reviewId) {
        try {
            reviewService.deleteReview(reviewId);
        } catch (SQLException e) {
            log.info("Failed to delete review: " + e.getMessage());
        }
    }
}