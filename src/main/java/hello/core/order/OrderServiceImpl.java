package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
// 롬복이 자바 애노테이션 프로세서라는 기능을 이용해서 컴파일 시점에 생성자 코드를 자동으로 생성해준다.
// 실제 class 파일을 열어보면 생성자 코드가 추가되어있는 것을 확인할 수 있다.
public class OrderServiceImpl implements OrderService {

    //생성자 주입방법
    //중요! 생성자가 딱 1개만 있으면 @Autowired를 생략해도 자동 주입 된다. 물론 스프링 빈에만 해당된다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, @MainDiscountPolicy DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    // 생성자 주입을 사용하면 장점
    // final 키워드를 사용할 수 있다.
    // 불변: 공연을 시작하고 끝날때까지 배역을 바꿀 일은 없다. 수정자 주입을 사용할 일은 없다는 뜻.
    // 예외는 있을 수 있음. 그러나 다른 더 좋은 방법으로 해결하는게 좋다.
    // 누락방지: 오류를 컴파일 시점에 막아준다!
    // 기억하자! 컴파일 오류는 세상에서 가장 빠르고, 좋은 오류다!

    // 참고: 수정자 주입을 포함한 나머지 주입 방식은 모두 생성자 이후에 호출되므로,
    // 필드에 final 키워드를 사용할 수 없다. 오직 생성자 주입 방식만 final 키워드를 사용할 수 있다.

    // 정리:
    // 생성자 주입 방식을 선택하는 이유는 프레임워크에 의존하지 않고, 순수한 자바 언어의 특징을 잘 살리는 법이기도 하다.
    // 기본으로 생성자 주입으로 사용하고, 필수 값이 아닌 경우에는 수정자 주입 방식을 옵션으로 부여하면 된다.
    // 생성자 주입과 수정자 주입을 동시에 사용할 수 있다.
    // 항상 생성자 주입을 선택해라! 그리고 가끔 옵션이 필요하면 수정자 주입을 선택해라.
    // 필드 주입은 사용하지 않는게 좋다.
    // 테스트 같은 곳에서 값을 넣을 수 있는 방법 자체가 없고, 애플리케이션이 굉장히 딱딱해지고, 스프링 컨테이너 없이는 테스트 조차 할 수 없다.
//    @Autowired
//    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }


    //수정자 주입방법
//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        System.out.println("OrderServiceImpl.setMemberRepository");
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        System.out.println("OrderServiceImpl.setDiscountPolicy");
//        this.discountPolicy = discountPolicy;
//    }

    //필드 주입 방법
    //코드는 간결하지만 외부에서 변경이 불가능해서 테스트 하기 힘들다는 치명적인 단점이 있다.
    //DI프레임워크가 없으면 아무것도 할 수 없다.
    //사용하지말자!
    //아래 사용 가능한 목록
    // - 애플리케이션의 실제 코드와 관계 없는 테스트 코드
    // - 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용
//    @Autowired private MemberRepository memberRepository;
//    @Autowired private DiscountPolicy discountPolicy;

    //테스트 코드에서 NPE Exception을 방지하기 위해 setter를 추가해줘야한다.
    //이럴거면 수정자주입 방법을 쓰지 굳이 필드 주입 방법을 쓸 이유가 없다.
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
