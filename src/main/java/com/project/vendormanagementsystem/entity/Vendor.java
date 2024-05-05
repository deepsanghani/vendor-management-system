package com.project.vendormanagementsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vendor {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String vendor_id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String contact_details;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private float on_time_delivery_rate;
    @Column(nullable = false)
    private float quality_rating_avg;
    @Column(nullable = false)
    private float average_response_time;
    @Column(nullable = false)
    private float fulfillment_rate;
}
