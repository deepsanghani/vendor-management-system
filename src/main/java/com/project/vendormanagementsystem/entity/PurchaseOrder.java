package com.project.vendormanagementsystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String po_number;
    @ManyToOne
    @JoinColumn(name = "vendor", referencedColumnName = "vendor_id", nullable = false)
    private Vendor vendor;
    @Column(nullable = false)
    private LocalDateTime order_date;
    @Column(nullable = false)
    private LocalDateTime delivery_date;
    private String items;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String status;
    private float quantity_rating;
    @Column(nullable = false)
    private LocalDateTime issue_date;
    private LocalDateTime acknowledegment_time;
}
