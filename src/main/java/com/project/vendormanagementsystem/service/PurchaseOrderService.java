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

    public boolean updatePurchaseOrderById(String po_id, PurchaseOrder purchaseOrder){
        Optional<PurchaseOrder> purchaseOrderFound = purchaseOrderRepo.findById(po_id);
        if(purchaseOrderFound.isPresent()){
            PurchaseOrder poFound = purchaseOrderFound.get();
            poFound.setOrder_date(purchaseOrder.getOrder_date());
            poFound.setDelivery_date(purchaseOrder.getDelivery_date());
            poFound.setItems(purchaseOrder.getItems());
            poFound.setQuantity(purchaseOrder.getQuantity());
            poFound.setStatus(purchaseOrder.getStatus());
            poFound.setDelivery_date(purchaseOrder.getDelivery_date());
            poFound.setIssue_date(purchaseOrder.getIssue_date());
            poFound.setAcknowledegment_time(purchaseOrder.getAcknowledegment_time());
            purchaseOrderRepo.save(poFound);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deletePurchaseOrderById(String po_id){
        if(purchaseOrderRepo.findById(po_id).isPresent()){
            purchaseOrderRepo.deleteById(po_id);
            return true;
        }
        else{
            return false;
        }
    }
}
