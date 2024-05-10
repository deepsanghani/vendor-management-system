package com.project.vendormanagementsystem.service;

import com.project.vendormanagementsystem.entity.PurchaseOrder;
import com.project.vendormanagementsystem.repository.PurchaseOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;
    public boolean addPurchaseOrder(PurchaseOrder purchaseOrder){
        purchaseOrderRepo.save(purchaseOrder);
        return true;
    }

    public List<PurchaseOrder> getAllPurchaseOrder(){
        return purchaseOrderRepo.findAll();
    }

    public Optional<PurchaseOrder> getPurchaseOrderById(String id){
        return purchaseOrderRepo.findById(id);
    }
}
