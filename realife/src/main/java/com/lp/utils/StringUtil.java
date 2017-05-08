package com.tintinloan.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.tintinloan.bean.background.PlatformUserLogEntry;

/**
 * 字符串工具类
 */
public class StringUtil {
	private static String chars = "0123456789abcdefghijklmnopqrstuvwxyz"; // 用于小写的情况

	/**
	 * 字节数组转换成十六进制字符串 String对象的getBytes()方法按照一定规则生成字节数组 然后使用
	 * new String(byte[] bs)方法复原(使用默认编码)
	 * 
	 * @param bs
	 * @return
	 */
	public static String bytesToHexString(final byte[] bs) {
		final StringBuffer sb = new StringBuffer(bs.length * 2);
		for (int i = 0; i < bs.length; i++) {
			// 保留后8位（防止负数的情况,负数有可能就是负数字节或者中文产生的负数字节）
			final String hex = Integer.toHexString(bs[i] & 0xFF);
			if (hex.length() < 2) {
				// Integer.toHexString(15)结果为F，所以需要加上0组成0F，也就是一个字节（8位）
				sb.append("0" + hex);
			} else {
				sb.append(hex);
			}
		}
		return sb.toString();
	}
	
	/** 
	 * 验证邮箱
	 * @param email
	 * @return
	 */
	public static boolean checkEmail(final String email) {
		final String regexStr = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return StringUtil.checkStringWithRegex(regexStr, email);
	}

	/**
	 * 验证手机号码
	 * @param mobiles
	 * @return
	 */
	public static boolean checkMobileNumber(final String mobileNumber) {
		final String regexStr = "^13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|17[0-9]{9}$|18[0-9]{9}$";
		return StringUtil.checkStringWithRegex(regexStr, mobileNumber);
	}
	/**
	 * 验证身份证号
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkUserCardId(final String cardId) {
		final String regexStr = "(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)";
		return StringUtil.checkStringWithRegex(regexStr, cardId);
	}

	/**
	 * 用户真实姓名校验
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkUserRealName(final String realName) {
		final String regexStr = "^[\u4E00-\u9FA5\uf900-\ufa2d]{2,10}$";
		return StringUtil.checkStringWithRegex(regexStr, realName);
	}
	/**
	 * 邮编校验
	 * 
	 * @param cardId
	 * @return
	 */
	public static boolean checkMailZipCode(final String mailZipCode) {
		final String regexStr = "^[-,+]{0,1}[0-9]{0,}[.]{0,1}[0-9]{0,}$";
		return StringUtil.checkStringWithRegex(regexStr, mailZipCode);
	}
	/**
	 * 指定字符在字符串中存在的数量
	 * 
	 * @param selectStr 查询字符
	 * @param targetStr 目标字符串
	 * @return num
	 */
	public static int findStrIndexOfCount(final String selectStr, final String targetStr) {
		final int selectLength = selectStr.length();
		final int targetLength = targetStr.length();
		if (selectLength > 0 && selectLength < targetLength) {
			return (targetLength - targetStr.replaceAll(selectStr, "").length()) / selectLength;
		}
		return -1;
	}

	public static String getBorrowNo(final long userId) {
		final SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date()) + "" + userId;
	}

	/**
	 * 根据字节数组和指定的编码格式生成字符串
	 * 
	 * @param bs
	 * @param charset
	 * @return
	 */
	public static String getString(final byte[] bs, final String charset) {
		String str = null;
		try {
			str = new String(bs, charset);
		} catch (final UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 十六进制字符串转成字节数组 十六进制字符串中用两个字符代表一个字节(8位)
	 * 
	 * @param str
	 * @return
	 */
	public static byte[] hexStringToBytes(final String str) {
		final int length = str.length() / 2;
		final byte[] bs = new byte[length];
		final char[] cs = str.toCharArray();
		for (int i = 0; i < bs.length; i++) {
			final int pos = i * 2;
			bs[i] = (byte) ((charToByte(cs[pos]) << 4) | charToByte(cs[pos + 1]));
		}
		return bs;
	}

	/**
	 * 判断字符串是否为空或者空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNull(final String str) {
		return !isNull(str);
	}

	/**
	 * 判断字符串是否为空或者空字符串
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNull(final String str) {
		return str == null || str.trim().length() == 0 || "".equals(str) || "null".equals(str);
	}

	/**
	 * 三位变两位（直接舍弃不是四舍五入）
	 * 
	 * @param threeBits
	 *            小数点后三位
	 * @return String
	 */
	public static String threeToTwoBits(final Double threeBits) {
		String twoBits = "";
		if (null != threeBits && isNotNull(String.valueOf(threeBits))) {
			twoBits = String.valueOf(Math.floor(Double.valueOf(threeBits) * 100d) / 100);
		}
		return twoBits;
	}

	/**
	 * 将指定字符串中所有包含三位数字的变成两位数字返回
	 * 
	 * @param msg 需要替换的字符串
	 * @return msg 替换完毕
	 */
	public static String threeToTwoBitsAll(String msg) {
		final int count = findStrIndexOfCount("元", msg);
		if (count > 0) {
			int index = msg.indexOf(".");
			int yuan = msg.indexOf("元");
			if (index > 0 && yuan > 0 && (yuan - index == 4)) {
				msg = msg.replace(msg.substring(index, yuan), msg.substring(index, yuan).substring(0, 3));
				for (int i = 0; i < count - 1; i++) {
					index = msg.indexOf(".", index + 1);
					yuan = msg.indexOf("元", yuan + 1);
					if (yuan - index == 4) {
						msg = msg.replace(msg.substring(index, yuan), msg.substring(index, yuan).substring(0, 3));
					}
				}
			}
		}
		return msg;
	}

	/**
	 * char类型转换成byte类型
	 */
	private static byte charToByte(final char c) {
		return (byte) chars.indexOf(c);
	}

	/**
	 * 验证密码格式 6~30位，除<(小于)、>(大于)都可以
	 * 
	 * @param pwd 密码
	 * @return
	 */
	public static boolean passwordFormatCheck(String pwd) {
		// 密码验证规则
		final String regEx = "^[0-9a-zA-Z\\^!@#\\$%^&\\*\\(\\)_\\.\\+\\{\\}:\\|\\[\\]\\?]{6,30}$";
		// 编译正则表达式
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(pwd);
		// 字符串是否与正则表达式相匹配
		return matcher.matches();
	}

	/**
	 * 给字符串增加*星号
	 * 
	 * @param str
	 * @param front 前面保留位数
	 * @param end 后面保留位数
	 * @return
	 */
	public static String secretStar(final String str, int front, int end) {
		if (isNull(str)) {
			return null;
		}
		String product = "";
		if (str.length() <= front) {
			product = str + "******" + str.substring((str.length() - end));
		} else {
			product = str.substring(0, front) + "******" + str.substring((str.length() - end));
		}
		return product;
	}

	public static boolean checkMobilePhone(String phoneNum) {
		Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(phoneNum);
		return m.matches();
	}
	/**
	 * 根据传入的正则表达式校验字符串是否符合正则表达式
	 * 
	 * @param regexStr
	 * @param str
	 * @return
	 */
	public static boolean checkStringWithRegex(final String regexStr,
			final String str) {
		boolean flag = false;
		try {
			final Pattern regex = Pattern.compile(regexStr);
			final Matcher matcher = regex.matcher(str);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	
	public static PlatformUserLogEntry getPlatformUserLogEntry(final HttpServletRequest request, final String operateId,
			final String operateDesc) {
		final PlatformUserLogEntry platformUserLogEntry = new PlatformUserLogEntry();
		String userId = String.valueOf(request.getSession().getAttribute("console_user_id"));
		if (!isNull(userId)) {
			userId = userId.replaceAll("console_", "");
		}
		final String userName = String.valueOf(request.getSession().getAttribute("console_user_name"));
		platformUserLogEntry.setUserId(userId);
		platformUserLogEntry.setUserName(userName);
		final String userIp = CurIpUtil.getRemoteAddress(request);
		platformUserLogEntry.setUserIp(userIp);
		platformUserLogEntry.setOperateId(operateId);
		platformUserLogEntry.setOperateDesc(operateDesc);
		return platformUserLogEntry;
	}
}
