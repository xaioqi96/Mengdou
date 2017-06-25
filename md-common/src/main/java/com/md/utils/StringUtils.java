/**
 * StringUtils.java <br>
 * com.wuys.utils <br>
 * Copyright (c) 2015.
 */
package com.md.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 字符串处理工具类
 * <p>
 *
 * @version  V1.0.0
 */
public class StringUtils {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };


	public static String encodeByMD5( String originString ) {
		if ( null != originString ) {

			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				byte[] results = md.digest(originString.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString.toUpperCase();
			} catch ( Exception ex ) {
				ex.printStackTrace();
			}
		}
		return null;
	}


	private static String byteArrayToHexString( byte[] b ) {
		StringBuffer resultSB = new StringBuffer();
		for ( int i = 0 ; i < b.length ; i++ ) {
			resultSB.append(byteToHexString(b[i]));
		}
		return resultSB.toString();
	}


	private static String byteToHexString( byte b ) {
		int n = b;
		if ( n < 0 ) n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}


	public static String addLink( String str ) {
		String regex = "((http:|https:)//|www.)[^[A-Za-z0-9\\._\\?%&+\\-=/#]]*";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer result = new StringBuffer();
		while ( matcher.find() ) {
			StringBuffer replace = new StringBuffer();
			if ( matcher.group().contains("http") ) {
				replace.append("<a href=").append(matcher.group());
			} else {
				replace.append("<a href=http://").append(matcher.group());
			}
			replace.append(" target=\"_blank\">" + matcher.group() + "</a>");
			matcher.appendReplacement(result, replace.toString());
		}

		matcher.appendTail(result);
		return result.toString();
	}


	public static String escapeHtml( String html ) {
		if ( !isEmpty(html) ) {
			html = html.replaceAll("\n", "<br>");
			html = html.replaceAll(" ", "&nbsp;");
			return html;
		}
		return html;
	}


	public static String reFormateHtml( String html ) {
		if ( !isEmpty(html) ) {
			html = html.replaceAll("&nbsp;", " ");
			html = html.replaceAll("@lt;", "<");
			html = html.replaceAll("<br>", "\n");
			return html;
		}
		return html;
	}


	/**
	 * 
	 * 判断为空
	 * <p>
	 *
	 * @param str
	 * @return 
	 */
	public static boolean isEmpty( String str ) {
		if ( null == str || "".equals(str) || "null".equals(str) ) {
			return true;
		} else if ( "".equals(str.trim()) ) {
			return true;
		}
		return false;
	}


	public static boolean isNotEmpty( String str ) {
		return !isEmpty(str);
	}


	/**
	 * 判断对象是否是空如果是空返回 0
	 * @param mapParameter name
	 * @return
	 */
	public static String isNotBank( Map mapParameter, String name ) {
		if ( mapParameter.get(name) == null ) {
			return "0";
		}
		return mapParameter.get(name).toString();
	}


	public static boolean isNumber( String str ) {
		String checkPassword = "^[0-9]+$";
		if ( null == str ) {
			return false;
		}
		if ( !str.matches(checkPassword) ) {
			return false;
		}
		return true;
	}


	/**
	 * 判断字符串是数字，可以为正数，可以为负数，不能含有字符
	 * <p>
	 *
	 * @param str
	 * @return 
	 */
	public static boolean isNumeric( String str ) {
		Pattern pattern = Pattern.compile("-?[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if ( !isNum.matches() ) {
			return false;
		}
		return true;
	}


	/**    
	* 将字符数组转为整型数组    
	*     
	* @param c    
	* @return    
	* @throws NumberFormatException    
	*/
	public static int[] converCharToInt( char[] c ) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for ( char temp : c ) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}


	public static boolean checkEmail( String email ) {
		String checkEmail = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
		if ( !isEmpty(email) ) {
			return email.matches(checkEmail);
		} else {
			return false;
		}
	}


	public static boolean checkUserName( String userName ) {
		String checkUserName = "^[\\w\\u4e00-\\u9fa5]+$";
		if ( !isEmpty(userName) ) {
			return userName.matches(checkUserName);
		} else {
			return false;
		}
	}


	/**
	 * 校验手机号码
	 */
	public static boolean checkMobile( String tel ) {
		/**电话号码正则表达式*/
		String telRegex = "^1[0-9]{10}$";
		if ( isNotEmpty(tel) ) {
			return (tel.matches(telRegex));
		} else {
			return false;
		}
	}


	public static boolean checkPassword( String password ) {
		String checkPassword = "^[0-9a-zA-Z]+$";
		if ( !isEmpty(password) ) {
			return password.matches(checkPassword);
		} else {
			return false;
		}
	}


	/**
	 * 校验密码必须为字母加数字组合，不包含空格
	 * <p>
	 *
	 * @param loginPwd
	 * @return 
	 */
	public static boolean checkRegPassWord( String loginPwd ) {
		String reg = "^(?![0-9@#$%^&*()+=|{}':;',\\[\\].<>/?~！!@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$)(?![a-zA-Z@#$%^&*()+=|{}':;',\\[\\].<>/?~！!@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$)[0-9A-Za-z@#$%^&*()+=|{}':;',\\[\\].<>/?~！!@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{6,16}$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(loginPwd);
		boolean b = matcher.matches();
		return b;
	}


	/**
	 * 将原数据前补零，补后的总长度为指定的长度，以字符串的形式返回
	 * <p>
	 *
	 * @param source	源数据
	 * @param formatLength	需要格式化后的数据长度
	 * @return
	 */
	public static String formatWithZero( int source, int formatLength ) {
		String newString = String.format("%0" + formatLength + "d", source);
		return newString;
	}


	/**
	 * utf8转gbk
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String parseToGBK( String str ) throws UnsupportedEncodingException {
		//转换为gbk格式
		byte[] temp = str.getBytes("utf-8");//这里写原编码方式
		byte[] newtemp = new String(temp, "utf-8").getBytes("gbk");//这里写转换后的编码方式
		return new String(newtemp, "gbk");
	}


	/**
	 * base64加密,utf8格式
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeBase64( String str ) throws UnsupportedEncodingException {
		return new String(Base64.encodeBase64(str.getBytes("utf-8")));
	}


	/**
	 * base64加密,固定编码格式
	 * @param str
	 * @param charsetName
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encodeBase64( String str, String charsetName ) throws UnsupportedEncodingException {
		return isEmpty(charsetName) ? encodeBase64(str) : new String(Base64.encodeBase64(str.getBytes(charsetName)));
	}


	/**
	 * 密码加密处理
	 * <p>
	 *
	 * @param username
	 * @param pwd
	 * @return
	 */
	public static String encryptPwd( String username, String pwd ) {
		return DigestUtils.sha256Hex("color" + username + pwd);
	}


	/**
	 * 把字符串的后n位用“*”号代替
	 * @param str  要代替的字符串
	 * @param n   代替的位数
	 * @return
	 */
	public static String userNameReplaceWithStar( String str, int n ) {
		char[] cs = str.toCharArray();
		for ( int i = 0 ; i < n ; i++ ) {

			if ( cs.length > 2 ) {
				cs[cs.length - i - 1] = '*';
			} else { // 处理名字只有两个长度的情况，比如“肖可”只须将最后一个字替换成 * 即可。
				cs[cs.length - 1] = '*';
			}
		}
		return String.valueOf(cs);
	}


	/**
	 * 隐藏手机号码中间四位为星号
	 * <p>
	 *
	 * @param mobile
	 * @return 
	 */
	public static String mobileReplaceWithStar( String mobile ) {
		String regex = "(\\d{3})\\d{4}(\\d{4})";
		String result = null;
		if ( isNotEmpty(mobile) ) {
			result = mobile.replaceAll(regex, "$1****$2");
		}
		return result;
	}


	/**
	 * 隐藏身份证中间10位为星号
	 * <p>
	 *
	 * @param idCard
	 * @return
	 */
	public static String idCardReplaceWithStar( String idCard ) {
		String regex = "(\\d{4})\\d{10}(\\w{4})";
		String result = null;
		if ( isNotEmpty(idCard) ) {
			result = idCard.replaceAll(regex, "$1*****$2");
		}
		return result;
	}


	/**
	 * 获取img标签中的src地址
	 * <p>
	 *
	 * @param htmlStr
	 * @return 
	 */
	public static List<String> getImgStr( String htmlStr ) {
		String img = "";
		Pattern p_image;
		Matcher m_image;
		List<String> pics = new ArrayList<String>();

		String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
		p_image = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		m_image = p_image.matcher(htmlStr);
		while ( m_image.find() ) {
			img = img + "," + m_image.group();

			Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);

			while ( m.find() ) {
				pics.add(m.group(1));
			}
		}
		return pics;
	}


	/**
	 * 生成特定位数的随机数字
	 * @param length
	 * @return
	 */
	public static String getRandomNum( int length ) {
		String val = "";
		Random random = new Random();
		for ( int i = 0 ; i < length ; i++ ) {
			val += String.valueOf(random.nextInt(10));
		}
		return val;
	}


	/**
	 * 去除字符串前后空格
	 * <p>
	 *
	 * @param str
	 * @return 
	 */
	public static String trimBlankSpace( String str ) {
		String result = null;
		if ( isNotEmpty(str) ) {
			result = str.trim();
		}
		return result;
	}


	/**
	 * 获取6位随机码
	 * <p>
	 *
	 * @return
	 */
	public static String getMobileRandomMsgCode() {
		int mobileCode = new Random().nextInt(899999) + 100000;
		return String.valueOf(mobileCode);
	}


	/**
	 *
	 * @param date	出生日期
	 * @return		返回年龄
	 */
	public static int newGetAges( String date ) {
		//创建生日的 Calendar 对象
		if ( date == null ) return 0;
		int age = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = new GregorianCalendar();
		try {
			cal.setTime(sdf.parse(date));

			Calendar birthDate = new GregorianCalendar(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
			Calendar today = Calendar.getInstance();
			age = today.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);

			//如果还没有过生日，则 age - 1
			birthDate.add(Calendar.YEAR, age);
			if ( today.before(birthDate) ) {
				age--;
			}

		} catch ( ParseException e ) {
			//e.printStackTrace();
		}

		return age;
	}


	/**
	 * 格式化数字
	 * @param src
	 * @param forStr
	 * @return 
	 */
	public static String formatNumber( Object src, String forStr ) {
		if ( isEmpty(forStr) ) {
			forStr = "############0.00";
		}
		if ( src instanceof String ) {
			src = Double.parseDouble(src.toString());
		}
		DecimalFormat df = new DecimalFormat(forStr);
		return df.format(src);
	}


	/**
	 * 校验只能输入正整数或两位小数
	 * <p>
	 * 例如：110.11  或 1500
	 *
	 * @param money
	 * @return
	 */
	public static boolean isMoney( String money ) {
		if ( money == null ) {
			return false;
		}
		return Pattern.matches("^+?[1-9][0-9]*[.]?[0-9]?[0-9]?$", money);
	}


	public static boolean checkPassWord( String loginPwd ) {
		String reg = "^(?![0-9@#$%^&*()+=|{}':;',\\[\\].<>/?~！!@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$)(?![a-zA-Z@#$%^&*()+=|{}':;',\\[\\].<>/?~！!@#￥%……&*（）——+|{}【】‘；：”“’。，、？]+$)[0-9A-Za-z@#$%^&*()+=|{}':;',\\[\\].<>/?~！!@#￥%……&*（）——+|{}【】‘；：”“’。，、？]{6,16}$";
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(loginPwd);
		return matcher.matches();
	}


	/**
	 * 将double类型转换为String类型
	 * <p>
	 *
	 * @param data
	 * @return
	 */
	public static String formatDouble2String( double data ) {
		DecimalFormat df = new DecimalFormat("###,##0.00");
		return df.format(data);
	}


	public static String[] shortUrl( String url ) {
		// 可以自定义生成 MD5 加密字符传前的混合 KEY
		String key = "weijinsuo";
		// 要使用生成 URL 的字符
		String[] chars = new String[ ] { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1",
				"2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
				"Z"

		};
		// 对传入网址进行 MD5 加密
		//		String sMD5EncryptResult = (new CMyEncrypt()).getMD5OfStr(key + url);
		String sMD5EncryptResult = getMD5Code(key + url);
		String hex = sMD5EncryptResult;

		String[] resUrl = new String[4];
		for ( int i = 0 ; i < 4 ; i++ ) {

			// 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
			String sTempSubString = hex.substring(i * 8, i * 8 + 8);

			// 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用
			// long ，则会越界
			long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
			String outChars = "";
			for ( int j = 0 ; j < 6 ; j++ ) {
				// 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
				long index = 0x0000003D & lHexLong;
				// 把取得的字符相加
				outChars += chars[(int) index];
				// 每次循环按位右移 5 位
				lHexLong = lHexLong >> 5;
			}
			// 把字符串存入对应索引的输出数组
			resUrl[i] = outChars;
		}
		return resUrl;
	}


	public static String getMD5Code( String strObj ) {
		String resultString = null;
		try {
			resultString = new String(strObj);
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch ( NoSuchAlgorithmException ex ) {
			ex.printStackTrace();
		}
		return resultString;
	}


	// 转换字节数组为16进制字串
	private static String byteToString( byte[] bByte ) {
		StringBuffer sBuffer = new StringBuffer();
		for ( int i = 0 ; i < bByte.length ; i++ ) {
			sBuffer.append(byteToArrayString(bByte[i]));
		}
		return sBuffer.toString();
	}


	// 返回形式为数字跟字符串
	private static String byteToArrayString( byte bByte ) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if ( iRet < 0 ) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return hexDigits[iD1] + hexDigits[iD2];
	}


	/**
	 * 按照指定开始结束替换字符
	 * @param str
	 * @param start
	 * @param end
	 * @return
	 */
	public static String indetifyReplace( String str, int start, int end ) {
		if ( StringUtils.isEmpty(str) ) {
			return null;
		}
		if ( start >= end ) {
			return str;
		}
		String temp = "*";
		for ( int i = 1 ; i < end - start ; i++ ) {
			temp += "*";
		}
		return str.replace(str.substring(start, end), temp);
	}


	/**
	 * 判断字符串是否是手机号码
	 * <p>
	 *
	 * @param mobiles
	 * @return 
	 */
	public static boolean isMobile( String mobiles ) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(mobiles);
		return m.matches();
	}


	/**
	 * 电话号码校验13112341234,010-12456789,01012456789,(010)12456789,00861012456789,+861012456789"; 
	 * @param input
	 * @return
	 */
	public static boolean isPhoneNumber( String input ) {
		Pattern p = Pattern.compile("1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}");
		Matcher m = p.matcher(input);
		return m.matches();
	}


	/**
	 * 只包含数据字母下划线
	 * @param serialNo
	 * @return
	 */
	public static boolean isSerialNo( String serialNo ) {
		Pattern p = Pattern.compile("[A-Za-z0-9_]+");
		Matcher m = p.matcher(serialNo);
		return m.matches();
	}


	public static void main( String[] args ) {
		/*System.out.println(isMoney(""));
		System.out.println(isNumber(""));
		
		System.out.println(isMobile("13800000000"));*/

		System.out.println(isSerialNo("ssss中 "));
	}


}

