package hello.core.order;

import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {

    @Test
    void createOrder() {
        //스프링 없이 순수하게 Java코드로 내가 필요한 것들을 조립을 하는 것이다.
        //테스트 코드 상에서 내가 필요한 구현체들을 조합해서 테스트코드를 작성할 수 있다.
        //생성자 주입을 사용하면 컴파일 오류로 바로 확인이 가능하다.
        //수정자 주입을 사용하였을 경우
        // -> 1. OrderServiceImpl orderService = new OrderServiceImpl();
        // -> 2. 테스트 코드 실행
        // -> 3. NPE오류 발생
        MemoryMemberRepository memberRepository = new MemoryMemberRepository();
        memberRepository.save(new Member(1L, "name", Grade.VIP));

        OrderServiceImpl orderService = new OrderServiceImpl(memberRepository, new FixDiscountPolicy());
        Order order = orderService.createOrder(1L, "itemA", 10000);
        Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
    }

}