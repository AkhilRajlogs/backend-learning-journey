package com.security.bank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvestmentDto {

    private String investmentType;

    private double amount;

    private String duration;
}