package com.alag.agmall.business.core.page;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class ParamBean implements Serializable {
    private String messageId;
    private Integer status;
    private Integer areadlyDead;
    private String consumerQueue;
    private Date createTimeBefore;
    private String listPageSortType;

    private Integer pageNum;
    private Integer pageSize;
}
