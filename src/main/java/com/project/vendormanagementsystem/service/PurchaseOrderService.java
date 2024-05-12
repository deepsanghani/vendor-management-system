package com.project.vendormanagementsystem.service;

import com.project.vendormanagementsystem.entity.PurchaseOrder;
import com.project.vendormanagementsystem.entity.Vendor;
import com.project.vendormanagementsystem.repository.PurchaseOrderRepo;
import com.project.vendormanagementsystem.repository.VendorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseOrderService {
    @Autowired
    private PurchaseOrderRepo purchaseOrderRepo;

    @Autowired
    private VendorRepo vendorRepo;

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

    public boolean updateAcknowledgementTime(String po_id, LocalDateTime po){
        PurchaseOrder poById = purchaseOrderRepo.findById(po_id).orElse(null);
        if(poById==null){
            return false;
        }
        System.out.println(po);
        poById.setAcknowledegment_time(po);
        purchaseOrderRepo.save(poById);
        getAverageResponseTimeOfVendor(poById.getVendor().getVendor_id());
        return true;
    }

    @Async
    @Transactional(readOnly = true)
    public void getAverageResponseTimeOfVendor(String vendorId){
        List<PurchaseOrder> purchaseOrdersByVendorId = purchaseOrderRepo.findByVendorVendor_id(vendorId);
        long totalResponseTime = 0, totalQuantityRating = 0, fulfilledOrder = 0, successfulDeliver = 0;
        int countForAcknowledge = 0, count = 0;
        for(int i=0;i<purchaseOrdersByVendorId.size();i++){
            PurchaseOrder currPurchaseOrder = purchaseOrdersByVendorId.get(i);
            if(currPurchaseOrder.getOrder_date()!=null && currPurchaseOrder.getAcknowledegment_time()!=null){
                Duration duration = Duration.between(currPurchaseOrder.getOrder_date(), currPurchaseOrder.getAcknowledegment_time());
                if(duration.isNegative() || duration.isZero()){
                    successfulDeliver += 1;
                }
                totalResponseTime += duration.getSeconds();
                countForAcknowledge++;
            }
            if(currPurchaseOrder.getDelivery_date()!=null && currPurchaseOrder.getAcknowledegment_time()!=null) {
                Duration duration = Duration.between(currPurchaseOrder.getDelivery_date(), currPurchaseOrder.getAcknowledegment_time());
                if (duration.isNegative() || duration.isZero()) {
                    successfulDeliver += 1;
                }
            }
            if(currPurchaseOrder.getAcknowledegment_time()!=null){
                fulfilledOrder += 1;
            }
            totalQuantityRating += (long) currPurchaseOrder.getQuantity_rating();
            count++;
        }
        float averageResponseTimeSeconds = countForAcknowledge > 0 ? (float) totalResponseTime / countForAcknowledge : 0;
        float averageQuantityRating = count > 0 ? (float)  totalQuantityRating / count : 0;
        float onTimeDeliverPercentage = count > 0 ? (float) successfulDeliver / count : 0;
        float fulfilledOrderPercentage = count > 0 ? (float) fulfilledOrder / count : 0;
        Vendor vendor = vendorRepo.findById(vendorId).orElse(null);
        if(vendor!=null) {
            vendor.setAverage_response_time(averageResponseTimeSeconds);
            vendor.setFulfillment_rate(fulfilledOrderPercentage*100);
            vendor.setOn_time_delivery_rate(onTimeDeliverPercentage*100);
            vendor.setQuality_rating_avg(averageQuantityRating);
            vendorRepo.save(vendor);
        }
        System.out.println("Average Response Time for Vendor " + vendorId + ": " + averageResponseTimeSeconds);
        System.out.println("Average Quantity Rating for Vendor " + averageQuantityRating);
        System.out.println("On Time Delivery Percentage " + onTimeDeliverPercentage*100);
        System.out.println("Fulfilled Order Percentage " + fulfilledOrderPercentage*100);
//        long days = (long) (averageResponseTimeSeconds / (3600 * 24));
//        long hours = (long) ((averageResponseTimeSeconds % (3600 * 24)) / 3600);
//        long minutes = (long) ((averageResponseTimeSeconds % 3600) / 60);
//        long seconds = (long) (averageResponseTimeSeconds % 60);
//        System.out.println("Average Response Time for Vendor " + vendorId + ": " + days + " days " + hours + " hours " + minutes + " minutes " + seconds + " seconds");

    }
}
