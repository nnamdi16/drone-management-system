package com.nnamdi.dronemanagementapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;


@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data<T> implements Serializable {

    private List<T> content;
    private boolean last;
    private boolean first;
    private int size;
    private int page;
    private int totalPages;
    private int totalElements;
    private int numberOfElements;

}
