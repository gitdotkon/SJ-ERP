package com.deere.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deere.common.HttpContext;
import com.deere.common.Utils;
import com.deere.dao.GenericDao;
import com.deere.exception.GenericException;
import com.deere.model.GenericModel;
import com.deere.model.Log;
import com.deere.model.SystemLog;

@Aspect
@Component
public class DaoLogAspect {
	
//	@Pointcut("execution(* com.mhm.spring.mng.impl..*.*(..))")  
	@Pointcut("execution(* com.deere.dao.GenericDao.*(..))")
	public void anyMethod(){};  

	@Autowired
	private GenericDao<Log> logDao;
	@Autowired
	private GenericDao<SystemLog> systemLogDao;
	
	
	public void afterThrowing(JoinPoint call, GenericException ex) {

		String className = call.getTarget().getClass().getName();
		String methodName = call.getSignature().getName();

		Object args[] = call.getArgs();

		SystemLog logInfo = new SystemLog();
		logInfo.setClassName(className);
		logInfo.setClassMethod(methodName);

		if (null != args) {
			StringBuffer argsStr = new StringBuffer();
			for (Object str : args) {
				argsStr.append(str.toString()).append("\n");
			}
			logInfo.setMethodParameter(StringUtils.substring(argsStr.toString(), 0, 3000));
		}
		if (null != ex.getCause()) {
			String cause = StringUtils.substring(ex.getCause().getMessage(), 0, 3000);
			logInfo.setRootCause(cause);
		}
		if (null != ex.getMessage()) {
			String message = ex.getExceptionCode() + ":" + ex.getMessage();
			logInfo.setInformation(message);
		}
		if (null != ex.getStackTrace()) {
			StringBuffer detail = new StringBuffer();
			for (StackTraceElement element : ex.getStackTrace()) {
				detail.append(element.toString()).append("\n");
			}
			logInfo.setDetail(StringUtils.substring(detail.toString(), 0, 3000));
		}

		logInfo.setType(ex.getExceptionCode());
		logInfo.setOccurTime(new Date());
		systemLogDao.save(logInfo);

		// logger.error(ex);
	}
	
	
	@Around("anyMethod()")  
	public Object recordDaoActionLog(ProceedingJoinPoint jp) throws Throwable {

		// get args of action of dataImport( etc.. )
		// Object ret;
		Object actionDataModel = null;
		String methodName = jp.getSignature().getName();
		if ((methodName == "merge") || (methodName == "save")) {
			Object[] args = jp.getArgs();

			// if arguments'count is 0 then return with do nothing

			// for example : utilService.loadPH( args[0] = phList, args[1] =
			// loginUser)
			actionDataModel = args[0];

			// while actionDataModel is Log itself then return (do nothing)
			/*
			 * if(actionDataModel instanceof BaseLog) { return; }
			 */

			if (actionDataModel instanceof GenericModel) {
				// while action data is type of GenericModel
				GenericModel gm = (GenericModel) actionDataModel;
				gm.setModifiedDate(new Date());

				// if loginUser in arguments[1] then set ModifiedBy field
				HttpSession session = HttpContext.getSession();
				if ((session != null)
						&& ((null != session.getAttribute("user")) || !"".equals(session.getAttribute("user")))) {
					gm.setModifiedBy((String) session.getAttribute("user"));
				} else {
					gm.setModifiedBy("System");
				}
			}

		}

		// System.out.println("before process " + methodName);
		/*
		 * Boolean proceedResult = true; String resultDetail = ""; Method m =
		 * Utils.findGetId(actionDataModel.getClass());
		 */
		try {
			return jp.proceed();
			// return ret;
		} catch (Exception e) {
			String idStr = "";
			if ((methodName == "merge") || (methodName == "save")) {
				if (actionDataModel instanceof GenericModel) {
					Method getId = Utils.findGetId(actionDataModel.getClass());
					idStr = getId.getName() + getId.invoke(actionDataModel) + " : ";
				}

			}
			e.printStackTrace();
			GenericException ex = new GenericException("DAO", idStr + e.getMessage(), e);
			afterThrowing(jp, ex);
			throw ex;
			// TODO: handle exception
		}
		// try {
		// process action of merge/save/update datamodel
		/*
		 * } catch (Exception ex) { proceedResult = false; resultDetail =
		 * ex.getMessage(); throw ex; } finally{
		 * //System.out.println("after process " + methodName); Method m =
		 * Utils.findGetId(actionDataModel.getClass());
		 * //System.out.println(m.getName()+": "+ m.invoke(actionDataModel));
		 * Object id = m.invoke(actionDataModel);
		 * 
		 * String logTitle = "[ " + actionDataModel.getClass().toString() +
		 * " ]"; String logActionTitle = " [ key= " + id.toString() + " ] ";
		 * String logDetail = "[ " + methodName + " record " + additionalInfo
		 * +"] ";
		 * 
		 * StringBuffer buffer = new StringBuffer(logDetail); if (proceedResult
		 * == false) { buffer.append("Failure: " +
		 * StringUtils.substring(resultDetail, 0, 3000)); } else {
		 * buffer.append("Success "); }
		 * 
		 * //save this log data into DB logDao.save(new Log(logTitle,
		 * logActionTitle, buffer.toString()));
		 */

	}

}
