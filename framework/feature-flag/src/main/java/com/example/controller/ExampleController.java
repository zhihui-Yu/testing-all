package com.example.controller;

import com.example.Flags;
import com.example.config.FlagConfig;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author simple
 */
@RestController
public class ExampleController {
    @Resource
    Flags flags;

    @GetMapping("/values")
    public Map<Object, Object> values() {
        String titleColor = flags.titleColors.getValue();
        int titleSize = flags.titleSize.getValue();
        double specialNumber = flags.specialNumber.getValue();
        Flags.Color color = flags.titleColorsEnum.getValue();
        boolean tutorialEnabled = flags.enableTutorial.isEnabled();

        Map<Object, Object> map = new HashMap<>(8);
        map.put("titleColor", titleColor);
        map.put("titleSize", titleSize);
        map.put("specialNumber", specialNumber);
        map.put("color", color);
        map.put("tutorialEnabled", tutorialEnabled);
        return map;
    }
}
