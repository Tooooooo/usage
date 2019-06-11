package usage.jrwhjd.component;

import com.jrwhjd.infrastructure.util.RandomUtils;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * @author 涂鼎 [eMail: tuding27@gmail.com]
 * @version 0.1 [2019/4/28]
 */
public class RandomDemo {

  private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  public static void main(String[] args) {

    // 随机生成一个 10~100 之间的伪随机数
    System.out.println(RandomUtils.randomInt(10, 100));

    // 以数组的形式产生4个 1~100 的伪随机数
    System.out.println(Arrays.toString(RandomUtils.nonRepeatRandomsInt(1, 100, 4)));

    // 从 data 中选取不重复的3个元素组成新的数组
    String[] dat = new String[]{"foo", "bar", "baz", "qux", "quux", "corge"};
    System.out.println(Arrays.toString(RandomUtils.noneRepeatRandom(dat, 3)));

    // 随机选取 "2010-11-21", "2019-05-01" 之间的一个日期
    System.out.println(df.format(RandomUtils.randomDate("2010-11-21", "2019-05-01")));

    // 随机生成一个6位的密码, 密码由字母和数字以及下划线组成
    System.out.println(RandomUtils.randomPassWord(6));
  }

}
