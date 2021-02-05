package com.isee.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private boolean showPrevious;   //是否展示上一页
    private boolean showFirstPage;
    private boolean showNext;   //是否展示下一页
    private boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPages;

    /**
     * 通过分页的参数计算类的其他属性
     * @param totalCount
     * @param page
     * @param size
     */
    public void  setPagination(Integer totalCount, Integer page, Integer size){
        Integer totalPage = 0;
        totalPage=totalCount/size + (totalCount%size==0?0:1);
        this.totalPages = totalPage;
        //防止越界，设置默认值
        if(page<1) page = 1;
        if(page>totalPage) page = totalPage;
        pages.add(page);
        //前面加三个，后面加三个，需要保证有效
        for(int i=1;i<=3;i++){
            if(page-i>0){
                pages.add(0,page-i);
            }
            if(page+i<=totalPage){
                pages.add(page+i);
            }
        }
        showPrevious = (page!=1);
        showNext = (page!=totalPage);
        showFirstPage = !pages.contains(1);
        showEndPage = !pages.contains(totalPage);
    }
}
