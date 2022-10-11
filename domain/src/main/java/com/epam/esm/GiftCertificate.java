package com.epam.esm;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GiftCertificate {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private int duration;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Tag tag;
}