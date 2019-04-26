/*
 * Copyright (c) 2019. Levashkin Konstantin.
 */

package com.lk.openmaterialmovie.dto;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//@Entity

@Getter
@Setter
//@AllArgsConstructor(onConstructor = @__({@Ignore}))
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //Room annotation after lombok only
public class Trailer {
    // TODO: 2019-04-26 Check working with lombock
    @PrimaryKey
    private String id;
    private String site;
    private int size;
    private String iso_3166_1;
    private String name;
    private String type;
    private String iso_639_1;
    private String key;
}
