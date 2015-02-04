package com.deere.common;

/**
 * @ClassName: Constants
 * @Description: Define the global constants
 * @author Gavin Li ligavin@johndeere.com
 * @date Aug 18, 2014 3:31:52 PM
 * 
 */
public interface Const {

	String JSONERRTYPE_TRANFER = "JSON_Transfer";
	String JSONERRTYPE_DUMMY = "JSON_Dummy";

	String TITLE_DEALER_EMP = "1";

	String TITLE_DEALER_PRINCIPLE = "2";

	String ERR_CODE_SOR_01 = "SOR00101";

	String ERR_CODE_SOR_02 = "SOR00102";

	String ERR_CODE_SOR_03 = "SOR00103";

	String ERR_CODE_SOR_04 = "SOR00104";

	// Can't connection email server

	String ERR_CODE_EML_CONN = "EML00201";

	// Can't read email

	String ERR_CODE_EML_RD = "EML00202";

	// Can't send email

	String ERR_CODE_EML_SEND = "EML00203";

	// Other exception of email part

	String ERR_CODE_EML_OTH = "EML00204";

	String ERR_CODE_BIZ_SAVE = "BIZ00301";

	String ERR_CODE_BIZ_DEL = "BIZ00302";

	String ERR_CODE_BIZ_UPDATE = "BIZ00303";

	String ERR_CODE_BIZ_SELECT = "BIZ00304";

	String ERR_CODE_BIZ_CONV = "BIZ00305";

}
