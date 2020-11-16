package com.mohamed.fileprocessing.entities;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ResponseFile {
    private String name;
    private String url;
    private String type;
    private long size;
}
