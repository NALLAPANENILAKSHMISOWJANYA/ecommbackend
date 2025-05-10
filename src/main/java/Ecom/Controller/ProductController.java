package Ecom.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Ecom.Model.Product;
import Ecom.ModelDTO.ProductDTO;
import Ecom.Service.ProductService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/ecom/products")
public class ProductController {

    private final ProductService productService;

    // Constructor to inject ProductService manually
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Add a new product
    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@Valid @RequestBody Product product) {
        Product newProduct = productService.addProduct(product);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    // Update an existing product
    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Integer productId, 
            @Valid @RequestBody ProductDTO updatedProduct) {
        Product updatedProductResult = productService.updateProduct(productId, updatedProduct);
        return new ResponseEntity<>(updatedProductResult, HttpStatus.OK);
    }

    // Get products by name
    @GetMapping("/product-By-name/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        List<Product> products = productService.getProductByName(name);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get all products with optional search, sort, and sortBy parameters
    @GetMapping("/all")
    public ResponseEntity<List<Product>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false, defaultValue = "asc") String sort,
            @RequestParam(required = false, defaultValue = "price") String sortBy) {
        List<Product> products = productService.getAllProduct(keyword, sort, sortBy);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(@PathVariable String category) {
        List<Product> products = productService.getProductByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    // Get a single product by its ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getSingleProduct(@PathVariable Integer productId) {
        Product singleProduct = productService.getSingleProduct(productId);
        return new ResponseEntity<>(singleProduct, HttpStatus.OK);
    }

    // Remove a product by its ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> removeProduct(@PathVariable Integer productId) {
        productService.removeProduct(productId);
        return new ResponseEntity<>("Product removed successfully.", HttpStatus.OK);
    }

    // Getter for ProductService (if needed)
    public ProductService getProductService() {
        return productService;
    }
}
