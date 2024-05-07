package com.project.vendormanagementsystem.controller;

import com.project.vendormanagementsystem.entity.Vendor;
import com.project.vendormanagementsystem.service.VendorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
public class VendorController {

    @Autowired
    private VendorService vendorService;
    @PostMapping("api/vendors/")
    public ResponseEntity<?> addVendor(@RequestBody Vendor vendor){
        String s = vendorService.createVendor(vendor);
        if(s.equals("success")){
            log.info("Vendor Added Successfully");
            return new ResponseEntity<>("Vendor Added Successfully",HttpStatus.OK);
        }
        else{
            log.error("Got an Error ",s, vendor);
            return new ResponseEntity<>("Got an error",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("api/vendors/")
    public ResponseEntity<?> getAllVendors(){
        List<Vendor> vendorList = vendorService.getAllVendor();
        return new ResponseEntity<>(vendorList, HttpStatus.OK);
    }

    @GetMapping("api/vendors/{vendor_id}")
    public ResponseEntity<?> getVendorById(@PathVariable String vendor_id){
        Optional<Vendor> vendorList = vendorService.getVendor(vendor_id);
        return new ResponseEntity<>(vendorList, HttpStatus.OK);
    }

    @PutMapping("api/vendors/{vendor_id}")
    public ResponseEntity<?> updateVendorById(@PathVariable String vendor_id, @RequestBody Vendor vendor){
        boolean isUpdated = vendorService.updateVendor(vendor_id, vendor);
        if(isUpdated)
            return new ResponseEntity<>("Updated Succesfully", HttpStatus.OK);
        else
            return new ResponseEntity<>("Not Updated", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("api/vendors/{vendor_id}")
    public ResponseEntity<?> deletedVendorById(@PathVariable String vendor_id){
        boolean isDeleted = vendorService.deleteVendor(vendor_id);
        if(isDeleted){
            return new ResponseEntity<>("Vendor Deleted Successfully", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Vendor Not Found", HttpStatus.OK);
        }
    }
}
