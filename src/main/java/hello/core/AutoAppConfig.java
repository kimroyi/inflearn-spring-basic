package hello.core;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
//컴포넌트 스캔을 사용하려면 먼저 @ComponentScan을 설정 정보에 붙여주면 된다.
//@Component 애노테이션이 붙은 클래스들을 다 스프링 빈으로 등록해준다.
//기존의 AppConfig와는 다르게 @Bean으로 등록한 클래스가 하나도 없다!
@ComponentScan(
        //탐색할 패키지의 시작 위치를 지정한다. 이 패키지를 포함해서 하위 패키지를 모두 탐색한다.
        //member만 컴포넌트 대상이 된다.
        //basePackages = {"hello.core", "hello.service"} 여러 시작 위치를 지정 가능
        //만약 basePackage를 지정하지 않으면 @ComponentScan이 붙은 설정 정보 클래스의 패키지가 시작 위치가 된다.
        basePackages = "hello.core.member",
        basePackageClasses = AutoAppConfig.class, //지정한 클래스의 패키지를 탐색 시작 위치로 지정한다.
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

}
