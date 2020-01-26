package com.skyrity.utils;

import com.skyrity.service.impl.WebServiceImpl;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


public class ComUtil {

	private static Logger logger = Logger.getLogger(ComUtil.class);

	public static String getResultTime(String retStr) {

		return retStr.replace("RESULT_TIME",
				Long.toString(new Date().getTime()));
	}
	public static void printWrite(HttpServletResponse response, String str){
		response.setCharacterEncoding("UTF-8");
		PrintWriter out;
		try {
			out=response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
}
