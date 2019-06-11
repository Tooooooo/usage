package usage.jrwhjd.component.validate;

import com.jrwhjd.infrastructure.validate.UniversalValidator;

/**
 * @author 涂鼎 [eMail: tuding27@gmail.com]
 * @version 0.1 [2019/4/29]
 */
public class UniversalValidatorTest {

  public static void main(String[] args) {
    System.out.println("UniversalValidator.isEmail(\"some#alicom\"): " + UniversalValidator.isEmail("some#alicom"));
    System.out.println("UniversalValidator.isEmail(\"hello@163.com\"): " + UniversalValidator
        .isEmail("hello@163.com"));
    System.out.println("UniversalValidator.isMobile(\"2392338223910\"): " + UniversalValidator
        .isMobile("23938223910"));
    System.out.println("UniversalValidator.isMobile(\"18907182125\"): " + UniversalValidator
        .isMobile("18907182125"));
    System.out.println("UniversalValidator.isMobileLazy(\"65653259\"): " + UniversalValidator
        .isMobileLazy("65653259"));
    System.out.println("UniversalValidator.isMobileLazy(\"13797099649\"): " + UniversalValidator
        .isMobileLazy("13797099649"));
    System.out.println("UniversalValidator.isUrl(\"http:/helloworld\"): " + UniversalValidator
        .isUrl("http:/helloworld"));
    System.out.println("UniversalValidator.isUrl(\"http://helloworld.com\"): " + UniversalValidator
        .isUrl("http://helloworld.com"));
    System.out.println("UniversalValidator.isNumber(\"4d8839.3838\"): " + UniversalValidator
        .isNumber("4d8839.3838"));
    System.out.println(
        "UniversalValidator.isNumber(\"1.2938\"): " + UniversalValidator.isNumber("1.2938"));
    System.out.println(
        "UniversalValidator.isStrNumber(\"3.2928\"): " + UniversalValidator.isStrNumber("3.2928"));
    System.out.println(
        "UniversalValidator.isStrNumber(\"873712\"): " + UniversalValidator.isStrNumber("873712"));
    System.out.println(
        "UniversalValidator.isInteger(\"9932.2\"): " + UniversalValidator.isInteger("9932.2"));
    System.out
        .println("UniversalValidator.isInteger(\"9527\"):" + UniversalValidator.isInteger("9527"));
    System.out.println("UniversalValidator.isIntegerNegative(\"338\"): " + UniversalValidator
        .isIntegerNegative("338"));
    System.out.println("UniversalValidator.isIntegerNegative(\"-123\"): " + UniversalValidator
        .isIntegerNegative("-123"));
    System.out.println("UniversalValidator.isIntegerPositive(\"-848\"): " + UniversalValidator
        .isIntegerPositive("-848"));
    System.out.println("UniversalValidator.isIntegerPositive(\"313\"): " + UniversalValidator
        .isIntegerPositive("313"));
    System.out.println("UniversalValidator.isDateCHNFormat(\"39923-23-82\"): " + UniversalValidator
        .isDateCHNFormat("39923-23-82"));
    System.out.println("UniversalValidator.isDateCHNFormat(\"2019-05-01\"): " + UniversalValidator
        .isDateCHNFormat("2019-05-01"));
    System.out.println("UniversalValidator.isAge(\"121\"): " + UniversalValidator.isAge("121"));
    System.out.println("UniversalValidator.isAge(\"100\"): " + UniversalValidator.isAge("100"));
    System.out.println(
        "UniversalValidator.isLengOut(\"abcde\", 3): " + UniversalValidator.isLengOut("abcde", 3));
    System.out.println(
        "UniversalValidator.isLengOut(\"abcde\", 6): " + UniversalValidator.isLengOut("abcde", 6));
    System.out
        .println("UniversalValidator.isZIP(\"di3320\"): " + UniversalValidator.isZIP("di3320"));
    System.out
        .println("UniversalValidator.isZIP(\"430000\"): " + UniversalValidator.isZIP("430000"));
    System.out.println(
        "UniversalValidator.isLetter(\"9dkk2b\"): " + UniversalValidator.isLetter("9dkk2b"));
    System.out.println(
        "UniversalValidator.isLetter(\"zyxwvu\"): " + UniversalValidator.isLetter("zyxwvu"));
    System.out.println("UniversalValidator.isLetterNumberStr(\"god like\"): " + UniversalValidator
        .isLetterNumberStr("god like"));
    System.out.println("UniversalValidator.isLetterNumberStr(\"51pm\"): " + UniversalValidator
        .isLetterNumberStr("51pm"));
    System.out.println(
        "UniversalValidator.isSafeStr(\"90/45=2\"): " + UniversalValidator.isSafeStr("90/45=2"));
    System.out.println("UniversalValidator.isSafeStr(\"9D94jF9_32\"): " + UniversalValidator
        .isSafeStr("9D94jF9_32"));
    System.out.println(
        "UniversalValidator.isSafeString(12, 15, \"9D94jF9_32\"): " + UniversalValidator
            .isSafeString(12, 15, "9D94jF9_32"));
    System.out.println(
        "UniversalValidator.isSafeString(10, 12, \"9D94jF9_32\"): " + UniversalValidator
            .isSafeString(10, 12, "9D94jF9_32"));
  }

}
