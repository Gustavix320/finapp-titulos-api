package com.gustavo.finapp.finapp_titulos_api.dto;

import java.util.List;

public class TituloPageResponse {

    private List<TituloDto> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private String sortBy;
    private String direction;

    public TituloPageResponse() {
    }

    public TituloPageResponse(List<TituloDto> content,
                              int page,
                              int size,
                              long totalElements,
                              String sortBy,
                              String direction) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.sortBy = sortBy;
        this.direction = direction;

        if (size > 0) {
            this.totalPages = (int) Math.ceil((double) totalElements / (double) size);
        } else {
            this.totalPages = 1;
        }
    }

    public List<TituloDto> getContent() {
        return content;
    }

    public void setContent(List<TituloDto> content) {
        this.content = content;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
