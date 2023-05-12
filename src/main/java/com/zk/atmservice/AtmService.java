package com.zk.atmservice;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface AtmService {
    List<Response> calculateOrder(@NotNull List<Request> requests);
}
