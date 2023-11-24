package com.electronicstore.Dto;

import lombok.*;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageableResponse <T> {

    public List<T> content;

    public int pagenumber;

    public Integer pagesize;

    public long totalelement;

    public Integer totalpages;

    public boolean lastpage;



}
