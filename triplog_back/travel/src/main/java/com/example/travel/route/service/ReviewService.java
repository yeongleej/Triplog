package com.example.travel.route.service;

import com.example.travel.route.S3.S3Uploader;
import com.example.travel.route.dto.request.ReviewRequestDto;
import com.example.travel.route.dto.response.ReviewResponseDto;
import com.example.travel.route.entity.Image;
import com.example.travel.route.entity.Review;
import com.example.travel.route.entity.ReviewImage;
import com.example.travel.route.repository.ImageRepository;
import com.example.travel.route.repository.ReviewImageRepository;
import com.example.travel.route.repository.ReviewRepository;
import com.example.travel.route.repository.RoutePlaceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewImageRepository mappingRepository;
    private final RoutePlaceRepository placeRepository;
    private final ImageRepository imgRepository;
    private final S3Uploader uploader;

    public ReviewService(ReviewRepository reviewRepository, ReviewImageRepository mappingRepository,
                         RoutePlaceRepository placeRepository, ImageRepository imgRepository, S3Uploader uploader) {
        this.reviewRepository = reviewRepository;
        this.mappingRepository = mappingRepository;
        this.placeRepository = placeRepository;
        this.imgRepository = imgRepository;
        this.uploader = uploader;
    }

    @Transactional
    public ReviewResponseDto reviewDetails(Long reviewId) throws SQLException {
        Review review = reviewRepository.findById(reviewId);
        List<ReviewImage> reviewImage = mappingRepository.findReviewImages(reviewId);

        ReviewResponseDto response = ReviewResponseDto.builder().review_id(reviewId)
                .place_id(review.getPlace_id()).review_text(review.getReview_text())
                .imageList(reviewImage).build();

        return response;
    }

    @Transactional
    public List<ReviewResponseDto> findReviewsByRoute(Long route_id) throws SQLException {
        List<ReviewResponseDto> reviewList = new ArrayList<>();
        List<Review> reviews = reviewRepository.findByRoute(route_id);
        for(Review review: reviews){
            List<ReviewImage> reviewImages = mappingRepository.findReviewImages(review.getReview_id());
            ReviewResponseDto response = ReviewResponseDto.builder().review_id(review.getReview_id())
                    .place_id(review.getPlace_id()).review_text(review.getReview_text())
                    .imageList(reviewImages).build();
            reviewList.add(response);
        }
        return reviewList;
    }

    @Transactional
    public ReviewResponseDto registerReview(ReviewRequestDto reviewDto) throws IOException, SQLException {

        System.out.println("파일 받았다: " + reviewDto);

        // 리뷰 본문, 사진 데이터가 모두 없는 경우 다른 로직을 수행하지 않고 null 리턴
        if(reviewDto.getReview_text().isEmpty() && reviewDto.getReview_imgs().get(0).isEmpty()) {
            throw new IOException("텍스트 리뷰 혹은 후기 사진 중 하나 이상을 등록해주세요!");
        }

        // 이미지를 제외한 리뷰 먼저 저장
        Review saveReview = Review.builder().place_id(reviewDto.getPlace_id())
                .review_text(reviewDto.getReview_text()).build();
        reviewRepository.saveReview(saveReview);

        Review review = reviewRepository.findByPlaceText(saveReview.getPlace_id(), saveReview.getReview_text());

        // 리뷰 이미지 저장
        for (MultipartFile reviewImg : reviewDto.getReview_imgs()) {
            String imgUrl = uploader.uploadFiles(reviewImg, "reviews");
            String imgName = reviewImg.getOriginalFilename();
            String imgKey = reviewImg.getContentType();

            Image image = Image.builder().img_name(imgName).img_key(imgKey).img_url(imgUrl).build();
            imgRepository.saveImage(image);
            Image img_id = imgRepository.findImageByUrl(imgUrl);

            mappingRepository.saveReviewImage(review.getReview_id(), img_id.getImg_id(), imgUrl);
        }

        List<ReviewImage> reviewImages = mappingRepository.findReviewImages(review.getReview_id());
        return ReviewResponseDto.builder().review_id(review.getReview_id()).place_id(review.getPlace_id())
                .review_text(review.getReview_text()).imageList(reviewImages).build();
    }

    @Transactional
    public int changeReviewText(Long reviewId, String reviewText) throws SQLException {
        return reviewRepository.modifyText(reviewId, reviewText);
    }

    // 이미지 수정 method 필요

    @Transactional
    public void deleteReview(Long reviewId) throws SQLException {
        reviewRepository.deleteReview(reviewId);
    }
}