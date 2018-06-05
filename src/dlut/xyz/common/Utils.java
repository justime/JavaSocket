package dlut.xyz.common;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public  class Utils {
   public static void println(String mess){
	   System.out.println(mess);
   }
   public static void print(String mess){
	   System.out.print(mess);
   }
   /**
	 * 根据文件路径获得文件名称
	 * @param filePath
	 * @return
	 */
   public static String getFileName(String filePath){
		int index=filePath.lastIndexOf(System.getProperty("file.separator"));
		return filePath.substring(index+1);
	}
   public static  String getTimeStmp(){
	   SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmssSSS");
	   return simpleDateFormat.format(new Date());
   }
   public static boolean fileExists(String filePath){
	   File file=new File(filePath);
	   return file.exists();
   }
}
