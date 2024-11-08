package detailing.reservation.service;

import detailing.reservation.domain.Member;
import detailing.reservation.repository.MemberRepositoryOld;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberRepositoryOld memberRepositoryOld;
    @Autowired
    MemberService memberService;

    @Test
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("test");

        // when
        Long savedId = memberService.join(member);

        // then
        assertEquals(member, memberRepositoryOld.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("test1");

        Member member2 = new Member();
        member2.setName("test1");

        // when
        memberService.join(member1);
        memberService.join(member2);

        // @Test의 expected option으로 대체
//        try{
//            memberService.join(member2);
//        } catch(IllegalStateException e){
//            return;
//        }

        // then
        fail("예외 발생");
    }
}