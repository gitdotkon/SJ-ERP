package com.deere.base.crypto;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashCryptTest {

	@Test
	public void testGetDigestHashString() {
		String password="password";
		String SHA= HashCrypt.getDigestHash(password);
//		System.out.println(SHA);
		assertEquals("5bd5619bb6c63f3f06fd250b6c87331b7e99f0a7", SHA);
//		fail("Not yet implemented");
	}

}
