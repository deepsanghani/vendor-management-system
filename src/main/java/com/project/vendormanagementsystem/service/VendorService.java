package com.project.vendormanagementsystem.service;

import com.project.vendormanagementsystem.entity.Vendor;
import com.project.vendormanagementsystem.repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendorService {
    @Autowired
    private VendorRepo vendorRepo;

    public String createVendor(Vendor vendor){
        try {
            vendorRepo.save(vendor);
            return "success";
        }
        catch (Exception e){
            return e.toString();
        }
    }

    public List<Vendor> getAllVendor(){
        return vendorRepo.findAll();
    }

    public Optional<Vendor> getVendor(String id){
        return vendorRepo.findById(id);
    }

    public boolean updateVendor(String id, Vendor vendor){
        Optional<Vendor> vendorFound = vendorRepo.findById(id);
        if(vendorFound.isEmpty()){
            return false;
        }
        else{
            Vendor existingVendor = vendorFound.get();
            existingVendor.setName(vendor.getName());
            existingVendor.setContact_details(vendor.getContact_details());
            existingVendor.setAddress(vendor.getAddress());
            existingVendor.setOn_time_delivery_rate(vendor.getOn_time_delivery_rate());
            existingVendor.setQuality_rating_avg(vendor.getQuality_rating_avg());
            existingVendor.setAverage_response_time(vendor.getAverage_response_time());
            existingVendor.setFulfillment_rate(vendor.getFulfillment_rate());
            vendorRepo.save(existingVendor); // Save the updated vendor
            return true;
        }
    }

    public boolean deleteVendor(String id){
        Optional<Vendor> isFound = vendorRepo.findById(id);
        if(isFound.isPresent()){
            vendorRepo.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
