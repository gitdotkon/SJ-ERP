package com.deere.base.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringUtilTest {

	@Test
	public void testGetStringSql() {
		System.out.print(StringUtil.getStringSql("aa,bb,cc"));
	}

}
