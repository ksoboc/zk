package com.zk.atmservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/atms")
public class AtmController {
    @Autowired
    private AtmService atmService;

    @PostMapping("/calculateOrder")
    public List<Response> calculateOrder(@RequestBody final List<Request> requests) {
        return atmService.calculateOrder(requests);
    }
}
