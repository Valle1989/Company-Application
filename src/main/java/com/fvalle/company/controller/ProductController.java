package com.fvalle.company.controller;

import com.fvalle.company.dto.ProductDto;
import com.fvalle.company.dto.ProductUpdateDto;
import com.fvalle.company.entity.Product;
import com.fvalle.company.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Product")
public class ProductController {

    private final IProductService productService;


    @Operation(
            description = "Create a new product",
            summary = "This is a summary for product POST endpoint",
            responses = {
                    @ApiResponse(
                            description = "Created",
                            responseCode = "201"
                    ),
                    @ApiResponse(
                            description = "Unauthorized / Invalid Token",
                            responseCode = "403"
                    )
            }

    )
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('admin:create')")
    public ResponseEntity<ProductDto> addProduct(@Valid @RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.save(productDto), HttpStatus.CREATED);
    }

    /*@PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") int productId,
                                                    @Valid @RequestBody ProductDto productDto){
        return new ResponseEntity<>(productService.update(productId,productDto), HttpStatus.OK);
    }*/

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('admin:update')")
    public ResponseEntity<ProductUpdateDto> updateProduct(@PathVariable("id") int productId,
                                                    @Valid @RequestBody ProductUpdateDto productUpdateDto){
        return new ResponseEntity<>(productService.updateProductWithDto(productId,productUpdateDto), HttpStatus.OK);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<List<ProductDto>> getAll(){
        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/allProducts")
    @PreAuthorize("hasAnyAuthority('user:read', 'admin:read')")
    public ResponseEntity<List<Product>> findAll(){
        return new ResponseEntity<>(productService.getAllProducts(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<ProductDto> getProduct(@PathVariable("id") int productId){
        return productService.getProduct(productId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/category/{id}")
    @PreAuthorize("hasAuthority('user:read')")
    public ResponseEntity<List<ProductDto>> getByCategory(@PathVariable("id") int categoryId){
        return productService.getByCategory(categoryId)
                .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('admin:delete')")
    public ResponseEntity<?> delete(@Valid @PathVariable("id") int productId) {
        if(productService.delete(productId)){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
