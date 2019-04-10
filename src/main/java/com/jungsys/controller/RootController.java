package com.jungsys.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class RootController {
	private final static Logger logger = LoggerFactory.getLogger(RootController.class);
	
    @RequestMapping("/")
    public String root() {
        logger.info("##### Root path requested");

        return new String("You have visited Happynarae Sample.");
    }

    @RequestMapping("/sleep")
    public Map<String, Object> apps(@RequestParam Map<String, String> param) {
        String millis = param.get("millis");
        logger.info("##### sleep entered...millis=" + millis);

        Map<String, Object> myResult = new HashMap<String, Object>();
        myResult.put("svc_name", "SLEEP");
        myResult.put("result", "0000");
        myResult.put("millis", millis);

        try {
            Thread.sleep(Long.parseLong(millis));
        }
        catch(Exception e) {
            logger.error(e.toString());
            myResult.put("result", "-1");
            myResult.put("reason", "Exception");
        }

        logger.info("##### the end..." + myResult.toString());
        return myResult;
    }
}
