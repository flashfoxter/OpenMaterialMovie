/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrailersResponse {
    private int id;
    private TrailersResponseResults[] results;

    @Data
    @AllArgsConstructor
    public class TrailersResponseResults {
        private String site;
        private int size;
        private String iso_3166_1;
        private String name;
        private String id;
        private String type;
        private String iso_639_1;
        private String key;
    }
}
