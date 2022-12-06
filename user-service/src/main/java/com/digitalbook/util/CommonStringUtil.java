package com.digitalbook.util;

import org.springframework.stereotype.Component;

@Component
public class CommonStringUtil {
	
	public String replaceAll(String reg,String rep,String str) {
		return str.replaceAll(reg, rep);
	}

}
