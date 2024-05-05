package com.project.vendormanagementsystem.repository;

import com.project.vendormanagementsystem.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder, String> {
}
