package com.kurek.antoni.pluton.ui.dtos;

public record PortfolioLink(String text, String href) {
    public PortfolioLink(OwnedPortfolioDto dto) {
        this(dto.name(), "/portfolio/" + dto.id().toString());
    }
}
