package hello.core.annotation;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Qualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}

//애노테이션을 만드는 이유는 @Qualifier("mainDiscountPolicy")
//문자를 적으면 컴파일 시 타입 체크가 안된다.
//애노테이션에는 상속이라는 개념이 없다. 스프링이 지원해주는 기능이다.