package com.deere.model.dto;

import static org.junit.Assert.*;

import org.junit.Test;

import com.deere.model.json.JsonCell;

public class JsonCellTest {

	@Test
	public void test() {
//		fail("Not yet implemented");
		JsonCell jc = new JsonCell();
		jc.setName("parent");
		jc.setLevel(1);
		jc.setParentId(10);
		jc.setIsLeaf(true);
		jc.setIsExpended(false);
		Object[] r = jc.toList();
		for (Object object : r) {
			System.out.println(object);
		}
	}

}
