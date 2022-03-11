package com.kurek.antoni.pluton.ui.dtos;

import lombok.Data;

//for some reason java records don't work for thyme forms - it doesn't map values at all
@Data
public class CreatePortfolioFormData {
    private String name;
    private String description;
}
