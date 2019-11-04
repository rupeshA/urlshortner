package com.app.urlshortner.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ReportRequest")
public class ReportRequest {

    @ApiModelProperty(required = true, value = "the long value of the time stamp from where to start looking for",
                    example = "1572000000000")
    @JsonProperty("start")
    @NotNull
    @Min(0)
    private Long start;

    @ApiModelProperty(required = true, value = "the long value of the time stamp till when to look for",
                    example = "1572873959000")
    @JsonProperty("end")
    @NotNull
    @Min(0)
    private Long end;

    @ApiModelProperty(required = true,
                    value = "Offset value used for getting the result page, for first page the value will be 0",
                    example = "0")
    @JsonProperty("from")
    @Min(value = 0, message = "From must be greater than or equal to zero")
    private Integer from = 0;

    @ApiModelProperty(required = true, value = "Size of the resultset to be fetched min = 1, max=20",
                    example = "10")
    @JsonProperty("size")
    @Min(value = 1, message = "Size must be greater than zero")
    @Max(value = 20, message = "Size must be greater than zero")
    private Integer size;

    @ApiModelProperty(required = true, value = "Column to be used to sort the data", example = "created_at")
    @JsonProperty("sort_on")
    private String sortOn = "created_at";

    @ApiModelProperty(required = true,
                    value = "Sort order to be applied on the column selected to sort on ASC/DESC", example = "ASC")
    @JsonProperty("sort_order")
    private String sortOrder = "asc";


    public ReportRequest(@NotNull @Min(0) Long start, @NotNull @Min(0) Long end,
                    @Min(value = 0, message = "From must be greater than or equal to zero") Integer from,
                    @Min(value = 1, message = "Size must be greater than zero") Integer size, String sortOn,
                    String sortOrder) {
        super();
        this.start = start;
        this.end = end;
        this.from = from;
        this.size = size;
        this.sortOn = sortOn == null ? this.sortOn : sortOn;
        this.sortOrder = sortOrder == null ? this.sortOrder : sortOrder;
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
