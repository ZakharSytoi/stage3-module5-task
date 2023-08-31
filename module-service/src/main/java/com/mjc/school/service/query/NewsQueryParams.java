package com.mjc.school.service.query;

import java.util.List;

public record NewsQueryParams(
    List<String> tagNames,
    List<Integer> tagIds,
    String authorName,
    String title,
    String content
) {
}
