package com.deere.common;

/**
 * @ClassName: Constants
 * @Description: Define the global constants
 * @author Gavin Li ligavin@johndeere.com
 * @date Aug 18, 2014 3:31:52 PM
 * 
 */
public interface Constants {

	String TITLE_DEALER_EMP = "1";

	String TITLE_DEALER_PRINCIPLE = "2";

	String FILE_PATH = "C:\\REPORT\\SOR";

	String MAIL_SENDER = "ligavin@johndeere.com";

	String MAIL_PASSWORD = "lizhen55";

	String ERR_CODE_SOR_01 = "SOR00101";
	String ERR_CODE_SOR_02 = "SOR00102";
	String ERR_CODE_SOR_03 = "SOR00103";
	String ERR_CODE_SOR_04 = "SOR00104";

	// Can't connection email server
	String ERR_CODE_EML_01 = "EML00201";
	// Can't read email
	String ERR_CODE_EML_02 = "EML00202";
	// Can't send email
	String ERR_CODE_EML_03 = "EML00203";
	// Other exception of email part
	String ERR_CODE_EML_04 = "EML00204";

	String ERR_CODE_BIZ_SAVE = "BIZ00301";
	String ERR_CODE_BIZ_DEL = "BIZ00302";
	String ERR_CODE_BIZ_UPDATE = "BIZ00303";
	String ERR_CODE_BIZ_SELECT = "BIZ00304";

	// start task error
	String ERR_CODE_DMT_01 = "DMT00101";
	// stop task error
	String ERR_CODE_DMT_02 = "DMT00102";
	// create task error
	String ERR_CODE_DMT_03 = "DMT00103";

	// import excel error
	String ERR_CODE_IMS_01 = "IMS00101";
	String ERR_CODE_IMS_02 = "IMS00102";

	// data transter error
	String ERR_CODE_DTF_01 = "DTF00101";

	String dfyyMMdd = "yyyy/MM/dd";
	String[] pattern = new String[]{"yyyy/MM/dd","yyyy-MM","yyyyMM","yyyy/MM"};
//	String pattern[] = new String{"yyyy/MM/dd"};
	
	String dfyyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";
	String dfyyyyMMdd = "yyyy-MM-dd";
	String excelXLS = "xls";
	String excelXLSX = "xlsx";

	String sucess = "SUCCESS";
	String failure = "FAILURE";

	String OPERATIONTYPEADD = "添加";
	String OPERATIONTYPEUPDATE = "变更";
}
