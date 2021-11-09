package com.yzh;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Main{
    private static final Logger LOGGER = LogManager.getLogger("Main.class");
    public static void main(String[] args) {

        LOGGER.trace("我是trace信息");

        LOGGER.debug("我是debug信息");

        LOGGER.info("我是info信息");    //info级别的信息

        LOGGER.warn("我是warn信息");

        LOGGER.error("Did it again!");   //error级别的信息，参数就是你输出的信息

        LOGGER.fatal("我是fatal信息");
    }

}
