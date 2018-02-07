package props;


import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Logger;


/**
 * @author cyan
 * 获取专门为apk工程配置的属性文件中的键值对
 * */
public class ApkProps {

	private static final Logger log = Logger.getLogger(ApkProps.class);
	
	/**属性文件上次更新时间*/
	private static long lastModifiedTime;

	/**属性文件在jvm中映射*/
	public static Properties apkProps;
	
	/**属性文件的路径*/
	private static String filePath;
	
	/**类加载时初始化一次*/
	static{
		try{
			/*获取属性文件路径*/
			URL url = ApkProps.class.getClassLoader().getResource("properties/apk.properties"); 
			filePath = java.net.URLDecoder.decode(url.getPath(),"utf-8");
			Properties tmp = new Properties();
			File propFile = new File(filePath);
			lastModifiedTime = propFile.lastModified();
			tmp.load(new FileInputStream(propFile));
			apkProps = tmp;
			log.info("配置文件解析成功:"+apkProps.toString());
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("配置文件解析失败!");
			
		}
	}
	
	/**
	 * 配置文件重新初始化方法
	 * @throws Exception 
	 * */
	private static boolean init(){
		try{
			/*获取属性文件路径*/
			URL url = ApkProps.class.getClassLoader().getResource("apk.properties"); 
			log.info("重新加载："+url.toString());
			filePath = java.net.URLDecoder.decode(url.getPath(),"utf-8");
			Properties tmp = new Properties();
			File propFile = new File(filePath);
			lastModifiedTime = propFile.lastModified();
			tmp.load(new FileInputStream(propFile));
			apkProps = tmp;
			log.info("配置文件解析成功:"+apkProps.toString());
			
			return true;
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("配置文件解析失败!");
			return false;
			
		}
	}
	
	/**
	 * @param key
	 * @return
	 * 根据属性文件和入参key获取value
	 * */
	public static String getValue(String key){
		
		/*如果属性文件被修改了据当前起码10秒钟 - 重新加载*/
		File proFile = new File(filePath);
		if(lastModifiedTime < proFile.lastModified() 
				&& (proFile.lastModified()+10000) < System.currentTimeMillis()){
			init();
			log.info("配置文件：apk.properties已被修改 - 将重新加载!"+apkProps.toString());
		}
		String result = apkProps.getProperty(key);
		return result;
	}
	

}
