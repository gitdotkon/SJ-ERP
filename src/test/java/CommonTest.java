import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.deere.model.GenericPart;

public class CommonTest {

	@Test
	public void testChinese() throws Exception {
//		Integer a =new Integer("");
//		System.out.println(a);
		/*String str = "aaw o是 B是";
		String cStr = "a我a是";
		System.out.println(getCode(str));
		System.out.println(getCode(cStr));*/
		Map<GenericPart,Integer> testmap = new HashMap<GenericPart,Integer>();
		GenericPart part = new GenericPart();
		part.setPartCode("test1");
		testmap.put(part, 10);
		GenericPart part2 = new GenericPart();
		part2.setPartCode("test2");
		testmap.put(part2, 100);
		System.out.println(testmap.size());
		System.out.println(part.hashCode());
		System.out.println(part2.hashCode());
		
	}

	private String getCode(String fileName) {

		int i = 1;
		for (; i < fileName.length(); i++) {
			String leftStr = fileName.substring(0, i);
			if (leftStr.length() < leftStr.getBytes().length)
				break;
		}
		return fileName.substring(0, i - 1);
	}
	
	
	
	

}



