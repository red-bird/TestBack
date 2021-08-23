package com.redbird.rtback.controller;

import com.redbird.rtback.service.MockupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@Slf4j
@RestController
@RequestMapping("img")
@CrossOrigin(origins = "*")
public class MockupController {

    private final MockupService mockupService;

    public MockupController(MockupService mockupService) throws MalformedURLException {
        this.mockupService = mockupService;
    }

    @PostMapping("laptop")
    public String getMockupLaptop(@RequestBody String img) {
        return mockupService.getLaptopMockup(img);
    }

    @PostMapping("phone")
    public String getMockupPhone(@RequestBody String img) {
        return mockupService.getIphoneMockup(img);
    }

}