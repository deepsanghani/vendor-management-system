package com.project.vendormanagementsystem.repository;

import com.project.vendormanagementsystem.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepo extends JpaRepository<Vendor, String> {
}
