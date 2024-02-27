package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

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

    //생성자 주입방법
    //중요! 생성자가 딱 1개만 있으면 생략해도 자동 주입 된다. 물론 스프링 빈에만 해당된다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

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
