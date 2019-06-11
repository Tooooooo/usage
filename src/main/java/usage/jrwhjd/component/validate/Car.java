package usage.jrwhjd.component.validate;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.HibernateValidator;

/**
 * @author 涂鼎 [eMail: tuding27@gmail.com]
 * @version 0.1 [2019/4/29]
 */
public class Car {

  // 类成员变量校验
  @NotNull
  @Size(min = 2, max = 14)
  @Getter
  @Setter
  private String manufacturer;

  public Car(String manufacturer) {
    this.manufacturer = manufacturer;
  }


  public static void main(String[] args) {
    ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
        .configure()
        .buildValidatorFactory();
    Validator validator = factory.getValidator();

    Car car = new Car("c");
    Set<ConstraintViolation<Car>> violations = validator.validate(car);
    System.out.println(violations.size()); // size == 1 , 有违例
    System.out.println(violations);  // 可以查看具体检验错误信息
  }
}