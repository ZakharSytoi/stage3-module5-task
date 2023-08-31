package com.mjc.school.repository.audit;

import com.mjc.school.repository.model.impl.NewsModel;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<NewsModel> {
    @Override
    public Optional<NewsModel> getCurrentAuditor() {
        return Optional.empty();
    }
}