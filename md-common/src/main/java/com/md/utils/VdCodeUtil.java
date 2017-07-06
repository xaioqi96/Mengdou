package com.md.utils;



import java.util.Random;

/**
 * 产生位随机验证码
 * @author cyan
 * */
public class VdCodeUtil {
	
	private static Random rand = new Random();
	
	public static String geneVdCode(){
		String result = String.valueOf(new Double((rand.nextDouble()*1000000)+1).intValue());
		int curLen = result.length();
		for(int i = 0; i < 6-curLen; i++){
			result = result;
		}
		return result;
	}
	
	public static void main(String[] args) {
		System.out.println(VdCodeUtil.geneVdCode());
	}
}
