/**
 * [禁用]只能输入数字
 */
function IsNum(e) {
    var k = window.event ? e.keyCode : e.which;
    if (((k >= 48) && (k <= 57)) || k == 8 || k == 0) {
    } else {
        if (window.event) {
            window.event.returnValue = false;
        }
        else {
            e.preventDefault(); //for firefox 
        }
    }
}
/**
 * [禁用]只能输入数字 加 小数点
 */
function IsNumAndDot(e) {
	var k = window.event ? e.keyCode : e.which;
	if (((k >= 48) && (k <= 57)) || k == 8 || k == 0 || k == 46) {
	} else {
		if (window.event) {
			window.event.returnValue = false;
		}
		else {
			e.preventDefault(); //for firefox 
		}
	}
}
/**
 * [禁用]只能输入数字 加 横线
 */
function IsNumAndLine(e) {
	var k = window.event ? e.keyCode : e.which;
	if (((k >= 48) && (k <= 57)) || k == 8 || k == 0 || k == 45) {
	} else {
		if (window.event) {
			window.event.returnValue = false;
		}
		else {
			e.preventDefault(); //for firefox 
		}
	}
}

/***************************************/
/***************财年财月  satrt************/
/***************************************/
var fscMonWeekDay;
getfscMonFirstDay = function (day) {
	fscMonWeekDay='';
	day.setDate(day.getDate() - 7);
	if (!isFiscalMonthOfWeek(day)) {
		getfscMonFirstDay(new Date(day));
	} else {
		var spacerDay = 8 - day.getDay();
		fscMonWeekDay = day.setDate(day.getDate() + (spacerDay == 8?1:spacerDay));
	}
	return fscMonWeekDay;
}
getfscMonLastDay = function (day) {
	fscMonWeekDay='';
	if (!isFiscalMonthOfWeek(day)) {
		day.setDate(day.getDate() + 7);
		getfscMonLastDay(new Date(day));
	} else {
		var spacerDay = 7 - day.getDay();
		fscMonWeekDay = day.setDate(day.getDate() + (spacerDay == 7?0:spacerDay));
	}
	return fscMonWeekDay;
}

/**
 * 获取财月
 */
getFiscalMonth = function (day) {
	if (isFiscalMonthOfWeek(day)) {// 取得财月周周一
		var converWeek = setWeekDay(day, 1); // 获取当前周的周一
		var a = (dateDifferent(day, converWeek) + 7) % 7;
		day.setDate(day.getDate() - a);
		return getFiscalMonthByDay(day);
	}
	var date = new Date(day);
	date.setDate(date.getDate() - 7);
	// 判断时间前一周是否是财年结算周
	if (isFiscalMonthOfWeek(date)) {// 取财月周周五
		var converWeek = setWeekDay(day, 5);
		var a = (dateDifferent(day, converWeek)) % 7;
		day.setDate(day.getDate() - a);
		return getFiscalMonthByDay(day);
	}
	return getFiscalMonthByDay(day);
	
}

/**
 * 设置时间星期几
 */
setWeekDay = function (day, weekDay) {
	var date = new Date(day);
	date.setDate(day.getDate() + (weekDay-day.getDay()));
	return date;
}

getFiscalMonthByDay = function (day) {
	var month = day.getMonth();
	return ((month + 2) % 12) + 1;
}

getFiscalYear = function (day) {
	var year = day.getFullYear();
	var month = day.getMonth();
	if (month > 8) {
		var fiscalMonth = getFiscalMonth(day);
		if (fiscalMonth != 12) {
			year++;
		}
	}
	return year;
}


isFiscalMonthOfWeek = function (day) {
	var lastweek = 0;
	var flag = false;
	var qtyDay = dateDifferent(day, new Date(2014, 11 - 1, 3));
	// 多少个星期
	var week = parseInt(qtyDay / 7) + 1;
	// 13周一个循环
	lastweek = week % 13;

	switch (lastweek) {
	case 0:
	case 4:
	case 8:
		flag = true;
		break;
	default:
		flag = false;
		break;
	}
	return flag;
}
function dateDifferent(endDate, startDate){
    var time = endDate.getTime() - startDate.getTime();
    return parseInt(time / (1000 * 60 * 60 * 24));
}

/***************************************/
/***************财年财月  end**************/
/***************************************/