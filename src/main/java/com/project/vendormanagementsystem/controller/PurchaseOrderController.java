package com.project.vendormanagementsystem.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.vendormanagementsystem.entity.PurchaseOrder;
import com.project.vendormanagementsystem.service.PurchaseOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
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

    @PutMapping("api/purchase_orders/{po_id}")
    public ResponseEntity<?> updatePurchaseOrder(@PathVariable String po_id, @RequestBody PurchaseOrder purchaseOrder){
        if(purchaseOrderService.updatePurchaseOrderById(po_id, purchaseOrder)) {
            return new ResponseEntity<>("Updated Data Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Not found purchase Order", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("api/purchase_orders/{po_id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable String po_id){
        if(purchaseOrderService.deletePurchaseOrderById(po_id)){
            return new ResponseEntity<>("Deleted Data Successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Data Not Found", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("api/purchase_orders/{po_id}/acknowledge")
    public ResponseEntity<?> updateAcknowledgement(@PathVariable String po_id, @RequestBody String acknowledgmentTime) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> data = objectMapper.readValue(acknowledgmentTime, new TypeReference<Map<String,Object>>() {});
        LocalDateTime dateTime = LocalDateTime.parse((String)data.get("acknowledgment_time"));
        if(purchaseOrderService.updateAcknowledgementTime(po_id, dateTime)){
            return new ResponseEntity<>("Updated Acknowledge Time Successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Purchase Order Not found", HttpStatus.BAD_REQUEST);
        }
    }
}
