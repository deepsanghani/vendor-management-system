package com.project.vendormanagementsystem.controller;

import com.project.vendormanagementsystem.entity.PurchaseOrder;
import com.project.vendormanagementsystem.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Slf4j
public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("api/purchase_orders/")
    public ResponseEntity<?> addPurchaseOrder(@RequestBody PurchaseOrder purchaseOrder){
        purchaseOrderService.addPurchaseOrder(purchaseOrder);
        return new ResponseEntity<>("Purhcase Order Added Successfully", HttpStatus.OK);
    }

    @GetMapping("api/purchase_orders/")
    public ResponseEntity<?> fetchAllPucharseOrder(){
        return new ResponseEntity<>(purchaseOrderService.getAllPurchaseOrder(), HttpStatus.OK);
    }

    @GetMapping("api/purchase_orders/{po_id}")
    public ResponseEntity<?> fetchPucharseOrder(@PathVariable String po_id) {
        Optional<PurchaseOrder> purchaseOrder = purchaseOrderService.getPurchaseOrderById(po_id);
        if (purchaseOrder.isPresent()) {
            return new ResponseEntity<>(purchaseOrder.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found purchase Order", HttpStatus.BAD_REQUEST);
        }
    }
}
