package Ecom.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Ecom.Exception.ProductException;
import Ecom.Exception.ReviewException;
import Ecom.Exception.UserException;
import Ecom.Model.Product;
import Ecom.Model.Review;
import Ecom.Model.User;
import Ecom.Repository.ProductRepository;
import Ecom.Repository.ReviewRepository;
import Ecom.Repository.UserRepository;
import Ecom.Service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

	@Autowired
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;

    // Constructor for dependency injection
    
    public ReviewServiceImpl(ProductRepository productRepository, ReviewRepository reviewRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Review addReviewToProduct(Integer productId, Integer userId, Review review) throws ReviewException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ReviewException("Product Not Found"));

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ReviewException("User Not Found In Database"));

        existingUser.getReviews().add(review);
        review.setUser(existingUser);
        existingProduct.getReviews().add(review);
        review.setProduct(existingProduct);
        userRepository.save(existingUser);
        productRepository.save(existingProduct);

        return reviewRepository.save(review);
    }

    @Override
    public Review updateReviewToProduct(Integer reviewId, Review review) throws ReviewException {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException("Review With Id "+reviewId+" Not Found In DataBase"));

        existingReview.setComment(review.getComment());
        existingReview.setRating(review.getRating());
        return reviewRepository.save(existingReview);  // Don't forget to save the updated review
    }

    @Override
    public void deleteReview(Integer reviewId) throws ReviewException {
        Review existingReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewException("Review With Id "+reviewId+" Not Found In DataBase"));

        reviewRepository.delete(existingReview);
    }

    @Override
    public List<Review> getAllReviewOfProduct(Integer productId) throws ReviewException {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ReviewException("Invalid Product id"));

        List<Review> allReviewsByProductId = reviewRepository.findAllReviewsByProductId(productId);
        if(allReviewsByProductId.isEmpty()) {
            throw new ReviewException("No Reviews Available for the Given Product");
        }
        return allReviewsByProductId;
    }
}
