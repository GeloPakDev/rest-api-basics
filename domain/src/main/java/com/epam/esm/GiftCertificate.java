package com.epam.esm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
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