package com.mjc.school.repository.query;

import java.util.List;

public record NewsSearchQueryParams(
    List<String> tagNames,
    List<Integer> tagIds,
    String authorName,
    String title,
    String content
) {
}
