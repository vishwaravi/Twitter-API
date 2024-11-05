package com.vishwa.twitter.utils;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadStatus {
    private String status;
    @Builder.Default
    private List<String> urls = new ArrayList<>();
}
