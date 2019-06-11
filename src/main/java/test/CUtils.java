package test;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.security.Key;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author 涂鼎 [eMail: tuding27@gmail.com]
 * @version 0.1 [2019/6/9]
 */
public class CUtils {
  private static final char[] DIGITS_LOWER = { '0', '1', '2', '3',
      '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
  private static final String md5Secret = "C%A3kh5$78";
  private static final byte[] desClientKey= {(byte)0x36, (byte)0xC6,
      (byte)0x6A, (byte)0xB0,	(byte)0x72, (byte)0xA9, (byte)0xE7, (byte)0xAD};
  private static final byte[] desClientIv = {(byte)0x66, (byte)0xA5,
      (byte)0xE2, (byte)0x02,	(byte)0x10, (byte)0xD7, (byte)0x99, (byte)0x30};
  private static final byte[] desDbDataKey= {(byte)0xBA, (byte)0xF8,
      (byte)0x41, (byte)0x6C,	(byte)0xCF,	(byte)0xE2, (byte)0x1B, (byte)0xDC,
      (byte)0x32, (byte)0x5D, (byte)0xFE, (byte)0x77,	(byte)0x40,	(byte)0xCE,
      (byte)0x3E, (byte)0x2D, (byte)0x7C, (byte)0x5D, (byte)0x93, (byte)0x86,
      (byte)0xAB, (byte)0x8E, (byte)0xB1, (byte)0x2B};
  private static final byte[] desDbDataIv = {(byte)0x49, (byte)0x5F,
      (byte)0x08, (byte)0xBE,	(byte)0x43, (byte)0xD6, (byte)0xE0, (byte)0x06};
  private static final byte[] desTokenKey = {(byte)0x16, (byte)0xDB,
      (byte)0x81, (byte)0x53,	(byte)0xE8, (byte)0xEB, (byte)0x59, (byte)0xB3,
      (byte)0x5D, (byte)0x93, (byte)0x86, (byte)0xAB, (byte)0x8E, (byte)0xB1,
      (byte)0x2B, (byte)0x33, (byte)0xC8, (byte)0x32, (byte)0x5D, (byte)0xFE,
      (byte)0x77, (byte)0x40, (byte)0xCE, (byte)0x3E};
  private static final byte[] desTokenIV  = {(byte)0xAC, (byte)0xBF,
      (byte)0xA9, (byte)0x22, (byte)0xD3, (byte)0x97, (byte)0x3D, (byte)0x80};


  private static final String STR_KEY="fdbc4y6hdhKlf4M3mjgGrMC3PbryXrxw";
  private static final String STR_IV="RfnMfrpec48=";

  private static final Random random = new Random();

  private static Key KeyGenerator(byte[] input) throws Exception {
    if (input.length == 8) {
      DESKeySpec KeySpec = new DESKeySpec(input);
      SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance("DES");
      return KeyFactory.generateSecret(KeySpec);
    } else {
      DESedeKeySpec KeySpec = new DESedeKeySpec(input);
      SecretKeyFactory KeyFactory = SecretKeyFactory.getInstance("DESede");
      return KeyFactory.generateSecret(KeySpec);
    }
  }

  private static IvParameterSpec IVGenerator(byte[] input) throws Exception {
    IvParameterSpec iv = new IvParameterSpec(input);
    return iv;
  }

  private static String encryptString(byte[] keyByte, byte[] ivByte, String strInput) {
    if (strInput.equals("")) return "";
    try
    {
      Key key = KeyGenerator(keyByte);
      IvParameterSpec iv = IVGenerator(ivByte);
      Cipher cipher;
      if (keyByte.length == 8) {
        cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
      }
      else {
        cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      }
      cipher.init(Cipher.ENCRYPT_MODE, key, iv);
      byte[] bInput = strInput.getBytes("UTF-8");
      byte[] output = cipher.doFinal(bInput);
      return Base64.encode(output);
    }
    catch (Exception e)
    {
      //	CLog.logger.error(e, e.fillInStackTrace());
      return "";
    }
  }

  private static String decryptString(byte[] keyByte, byte[] ivByte, String strInput)	{
    if (strInput.equals("")) return "";
    try
    {
      Key key = KeyGenerator(keyByte);
      IvParameterSpec iv = IVGenerator(ivByte);
      Cipher cipher;
      if (keyByte.length == 8) {
        cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
      }
      else {
        cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
      }
      cipher.init(Cipher.DECRYPT_MODE, key, iv);
      byte[] bInput = Base64.decode(strInput);
      byte[] output = cipher.doFinal(bInput);
      return new String(output,"UTF-8");
    }
    catch (Exception e)
    {
      //	CLog.logger.error(e, e.fillInStackTrace());
      e.printStackTrace();
      return "";
    }
  }

  public static String encryptClient(String data) {
    return encryptString(desClientKey, desClientIv, data);
  }

  public static String decryptClient(String data) {
    return decryptString(desClientKey, desClientIv, data);
  }

  public static String encryptDbData(String data) {
    return encryptString(desDbDataKey, desDbDataIv, data);
  }

  public static String decryptDbData(String data) {
    return decryptString(desDbDataKey, desDbDataIv, data);
  }


  public static String encryptToken(String data) {
    return encryptString(desTokenKey, desTokenIV, data);
  }

  public static String decryptToken(String data) {
    return decryptString(desTokenKey, desTokenIV, data);
  }

  public static String getAuthenticator(String data) {
    try
    {
      String tmpData = data + md5Secret;
      byte input[] = tmpData.getBytes("UTF-8");
      byte output[] = (byte[])null;
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(input);
      output = md.digest();
      return Base64.encode(output);
    }
    catch (Exception e)
    {
      //	CLog.logger.error(e, e.fillInStackTrace());
      return "";
    }
  }

  private static char[] encodeHex(byte[] data) {
    int len = data.length;
    char[] out = new char[len <<1];
    for (int i = 0, j = 0; i < len; i++) {
      out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
      out[j++] = DIGITS_LOWER[0x0F & data[i]];
    }
    return out;
  }

  public static String getMD5String(String data) {
    try
    {
      byte input[] = data.getBytes("UTF-8");
      byte output[] = (byte[])null;
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(input);
      output = md.digest();
      return new String(encodeHex(output)).toUpperCase();
    }
    catch (Exception e)
    {
      //	CLog.logger.error(e, e.fillInStackTrace());
      return "";
    }
  }

  public static String encryptPassword(String password){
    byte[] keyByte=Base64.decode(STR_KEY);
    byte[] keyIV=Base64.decode(STR_IV);
    String ret=encryptString(keyByte,keyIV,password);
    if(ret.length()>20){              //98 数据库上密码字段只有20位
      ret=ret.substring(0,20);
    }
    return ret;
  }

  public static Date getDatetime(String str) throws Exception {
    SimpleDateFormat dateFormat;
    switch (str.length()) {
      case 8:
        dateFormat = new SimpleDateFormat("yyyyMMdd");
        break;
      case 10:
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        break;
      case 14:
        dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        break;
      case 19:
        dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        break;
      default:
        throw new Exception("日期时间格式未知");
    }
    return dateFormat.parse(str);
  }

  public static boolean isNumeric(String str) {
    Pattern pattern = Pattern.compile("[0-9]*");
    return pattern.matcher(str).matches();
  }

  public static boolean isMobileNumber(String str) {
    if (str.length() != 11) return false;
    if (!str.startsWith("1")) return false;
    return isNumeric(str);
  }

  public static boolean isIdCard(String idCard) {
    if (idCard.length() != 18) return false;
    if (idCard.charAt(17) == 'X') {
      if (!isNumeric(idCard.substring(0, 17))) return false;
    } else {
      if (!isNumeric(idCard)) return false;
    }
    try {
      Date startDay = getDatetime("1900-01-01");
      Date birthDay = getDatetime(idCard.substring(6, 14));
      if (birthDay.before(startDay) || birthDay.after(new Date())) return false;
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static String Nvl(String inStr, String outStr) {
    if (inStr == null) {	return outStr;	} else return inStr;
  }

  // 返回北京时间的时间戳，Java的系统时间是UTC，北京时间在数值上比UTC大8小时
  public static String getTimeStamp(Date date) {
    return String.valueOf(date.getTime() + 8*3600*1000);
  }

  public static Date getDateTime(String timeStamp) {
    long vTimeStamp = Long.parseLong(timeStamp) - 8*3600*1000;
    return new Date(vTimeStamp);
  }

  public static String getTimeString(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    return dateFormat.format(date);
  }

  public static String getTimeString14(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    return dateFormat.format(date);
  }

  public static String getTimeString19(Date date) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    dateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    return dateFormat.format(date);
  }

  public static int getRandom() {
    int theResult = 0;
    while (theResult <= 0) {
      theResult = random.nextInt();
    }
    return theResult;
  }

  public static String getRandom(int len) {
    String strResult = "";
    while (strResult.length() < len) {
      int intTemp = random.nextInt();
      strResult += intTemp;
    }
    return strResult.substring(strResult.length() - len);
  }

  public static Date DateAddMinute(Date date, int minutes) {
    long milliSeconds = date.getTime();
    milliSeconds += (long)minutes * 60 * 1000;
    return new Date(milliSeconds);
  }

  public static Date DateAddDays(Date date, int days) {
    long milliSeconds = date.getTime();
    milliSeconds += (long)days * 86400 * 1000;
    return new Date(milliSeconds);
  }




  public static String formatVersion(String version) {
    String[] arrVer = version.split("\\.");
    switch (arrVer.length) {
      case 2:
        arrVer[0] = "0" + arrVer[0];
        arrVer[1] = "0" + arrVer[1];
        return arrVer[0].substring(arrVer[0].length() - 2) + "." +
            arrVer[1].substring(arrVer[1].length() - 2) + ".00";
      case 3:
        arrVer[0] = "0" + arrVer[0];
        arrVer[1] = "0" + arrVer[1];
        arrVer[2] = "0" + arrVer[2];
        return arrVer[0].substring(arrVer[0].length() - 2) + "." +
            arrVer[1].substring(arrVer[1].length() - 2) + "." +
            arrVer[2].substring(arrVer[2].length() - 2);
      default:
        return version;
    }
  }

  public static void main(String[] args) {
    System.out.println(encryptPassword("253292"));
  }




}

