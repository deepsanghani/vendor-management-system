package com.project.vendormanagementsystem.repository;

import com.project.vendormanagementsystem.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, String> {
    @Query(value = "SELECT * FROM purchase_order WHERE vendor = :vendorId", nativeQuery = true)
    List<PurchaseOrder> findByVendorVendor_id(@Param("vendorId") String vendorId);
}
