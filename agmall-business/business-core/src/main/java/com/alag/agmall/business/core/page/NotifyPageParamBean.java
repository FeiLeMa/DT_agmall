package com.alag.agmall.business.core.page;

import lombok.*;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @program: DT_agmall
 * @description:
 * @author: Alag
 * @create: 2019-08-17 17:30
 * @email: alag256@aliyun.com
 **/
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
public class NotifyPageParamBean implements Serializable {
    private String merchantNo;
    private String merchantOrderNo;
    private String listPageSortType;
    private Integer notifyTimes;
    private Integer status;
    private Integer field1;
    private Integer field2;

    private Integer pageNum;
    private Integer pageSize;

    public static NotifyPageParamBean setReturn(Function<NotifyPageParamBean, NotifyPageParamBean> function) {
        return function.apply(new NotifyPageParamBean());
    }
}
