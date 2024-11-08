package com.whu.servicedataclient.project.controller;

import com.whu.servicedataclient.project.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin
public class DataController {

    @Autowired
    private DataService dataService;

    @RequestMapping(value = "/test")
    public void Test(@RequestBody Map<String, Object> map) {
        try {
            dataService.sendMsg(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/test1")
    public void test1() {
        try {
            dataService.test1();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
