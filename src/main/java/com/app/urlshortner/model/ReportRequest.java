package com.app.urlshortner.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ReportRequest")
public class ReportRequest {

    @ApiModelProperty
    @JsonProperty("start")
    @NotNull
    @Min(0)
    private Long start;

    @ApiModelProperty
    @JsonProperty("end")
    @NotNull
    @Min(0)
    private Long end;

    @ApiModelProperty
    @JsonProperty("from")
    @Min(value = 0, message = "From must be greater than or equal to zero")
    private Integer from = 0;

    @ApiModelProperty
    @JsonProperty("size")
    @Min(value = 1, message = "Size must be greater than zero")
    private Integer size = 1000;

    @ApiModelProperty
    @JsonProperty("sort_on")
    private String sortOn = "created_at";

    @ApiModelProperty
    @JsonProperty("sort_order")
    private String sortOrder = "asc";

    @ApiModelProperty
    @Min(value = 0, message = "Size must be greater than or equal zero")
    @JsonProperty("offset")
    private Integer offset;


    public ReportRequest(@NotNull @Min(0) Long start, @NotNull @Min(0) Long end,
                    @Min(value = 0, message = "From must be greater than or equal to zero") Integer from,
                    @Min(value = 1, message = "Size must be greater than zero") Integer size, String sortOn,
                    String sortOrder) {
        super();
        this.start = start;
        this.end = end;
        this.from = from;
        this.size = size;
        this.sortOn = sortOn;
        this.sortOrder = sortOrder;
    }

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSortOn() {
        return sortOn;
    }

    public void setSortOn(String sort) {
        this.sortOn = sort;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }
}
