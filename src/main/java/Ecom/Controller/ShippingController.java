package Ecom.Controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import Ecom.Model.ShippingDetails;
import Ecom.ModelDTO.ShippingDTO;
import Ecom.Service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/ecom/order-shipping")
public class ShippingController {

	@Autowired
    private final ShippingService shippingService;

    // Constructor to inject ShippingService
    
    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping("/{orderId}/{shipperId}")
    public ResponseEntity<ShippingDetails> setShippingDetails(@PathVariable Integer orderId,
                                                              @PathVariable Integer shipperId,
                                                              @Valid @RequestBody ShippingDetails shippingDetails) {
        ShippingDetails savedShippingDetails = shippingService.setShippingDetails(orderId, shipperId, shippingDetails);
        return new ResponseEntity<>(savedShippingDetails, HttpStatus.CREATED);
    }

    @PutMapping("/{shippingId}")
    public ResponseEntity<ShippingDetails> updateShippingAddress(@PathVariable Integer shippingId,
                                                                 @Valid @RequestBody ShippingDTO shippingDTO) {
        ShippingDetails updatedShippingDetails = shippingService.updateShippingAddress(shippingId, shippingDTO);
        return new ResponseEntity<>(updatedShippingDetails, HttpStatus.OK);
    }

    @DeleteMapping("/{shippingId}")
    public ResponseEntity<Void> deleteShippingDetails(@PathVariable Integer shippingId) {
        shippingService.deleteShippingDetails(shippingId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
