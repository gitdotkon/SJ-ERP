package com.deere.manufacture.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deere.dao.GenericDao;
import com.deere.model.BOMTree;
import com.deere.model.GenericPart;

@Service
public class MPRService {
	
	@Autowired
	private GenericDao<BOMTree> bomTreeDao;
	
	public  Map<String,Integer> getMPR(GenericPart parent,Integer dosage){
		Map<String,Integer> mprList = new HashMap<String, Integer>();
		mprList.put(parent.getPartCode(), dosage);
//		Map<GenericPart,Integer> bomChild= getBomChild(parent);
//		Iterator<GenericPart> it =  bomChild.keySet().iterator();
//		bomChild.entrySet()
		
		String query= "from BOMTree where parent='"+parent.getPartCode()+"'";
		List<BOMTree> machineList = bomTreeDao.query(query);
		for (BOMTree bomTree : machineList) {
			GenericPart child = bomTree.getChild();
			Map<String,Integer> childMap=getMPR(child,bomTree.getDosage());
			Iterator<String> it =  childMap.keySet().iterator();
			while(it.hasNext()){
				String partCode=it.next();
				Integer quantity = dosage*childMap.get(partCode);
				if(mprList.containsKey(partCode)){
					quantity+=mprList.get(partCode);
				}
				mprList.put(partCode, quantity);
			}
		}
		return mprList;
		
	}
	
}
