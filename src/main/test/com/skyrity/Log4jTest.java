package com.skyrity;

import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * @author ： VULCAN
 * @date ：2020/01/10 20:16
 * @description : ${description}
 * @path : com.skyrity.Log4jTest
 * @modifiedBy ：
 */
public class Log4jTest {
    private static Logger logger = Logger.getLogger(Test.class);

    @Test
    public void run(){
        logger.trace("trace");
        logger.debug("debug");
        logger.info("info");
        logger.warn("warn");
        logger.error("error");
    }
}
