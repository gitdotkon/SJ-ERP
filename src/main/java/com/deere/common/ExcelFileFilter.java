package com.deere.common;

import java.io.File;
import java.io.FileFilter;

public class ExcelFileFilter  implements FileFilter{  
	 
    @Override  
    public boolean accept(File pathname) {  
        String filename = pathname.getName().toLowerCase();  
        if(filename.contains(".txt")){  
            return false;  
        }else{  
            return true;  
            }  
    }  
}  