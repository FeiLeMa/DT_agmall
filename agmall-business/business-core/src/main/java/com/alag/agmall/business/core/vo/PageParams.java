package com.alag.agmall.business.core.vo;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Getter(AccessLevel.PUBLIC)
@ToString
public class PageParams<T> implements Serializable {
    public T pageParam;
    public Map<String, Object> paramMap = new HashMap<>();
}
