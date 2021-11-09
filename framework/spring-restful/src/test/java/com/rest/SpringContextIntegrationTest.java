package com.rest;

import com.rest.config.WebConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 * @author simple
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
    classes = WebConfig.class,
    loader = AnnotationConfigContextLoader.class)
class SpringContextIntegrationTest {
    @Test
    public void contextLoads(){

    }
}