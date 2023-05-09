package kr.ac.kopo.guestbook.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook.dto.GuestbookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.dto.PageResultDTO;
import kr.ac.kopo.guestbook.entity.Guestbook;
import kr.ac.kopo.guestbook.entity.QGuestbook;
import kr.ac.kopo.guestbook.service.GuestbookServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
public class GuestbookRepositoryTests {
    @Autowired
    private GuestbookRepository guestbookRepository;
    @Autowired
    private GuestbookServiceImpl service;

    @Test
    public void insertDummies(){
        IntStream.rangeClosed(1,300).forEach(i->{
            Guestbook guestbook = Guestbook.builder()
                    .title("Title ... "+i)
                    .content("Content... "+i)
                    .writer("user" + (i % 10))
                    .build();
            System.out.println(guestbookRepository.save(guestbook));
        });
    }
    @Test
    public void updateTest(){ //이 과정까지는 JPA로도 가능하나 , QUERY DSL을 사용하는게 훨씬 효과적이다.
        Optional<Guestbook> result = guestbookRepository.findById(300L);

        if(result.isPresent()){
            Guestbook gb = result.get();
            gb.changeTitle("Change title ....");
            gb.changeContent("Change contents ....");
            guestbookRepository.save(gb);
        }
    }

    @Test
    public void testQuery1(){ //querydsl 의 사용. keyword에 포함된 title을 추출.
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno"));
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "7";
        BooleanBuilder builder = new BooleanBuilder(); // 조건객체 생성.
        BooleanExpression expression = qGuestbook.title.contains(keyword);
        builder.and(expression); // 생성된 조건에 새로운 조건 결합.
        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable); // 결합된 객체와, pageable로 res를 찾아옴.
        result.stream().forEach(guestbook -> System.out.println("guestbook 출력 >> "+guestbook)); // 반복하여 출력.
    }
    @Test
    public void testQuery2(){
        Pageable pageable = PageRequest.of(0,10, Sort.by("gno").ascending());
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyword = "7";
        BooleanBuilder builder = new BooleanBuilder(); // 조건객체 생성.

        BooleanExpression exTitle = qGuestbook.title.contains(keyword);
        BooleanExpression exContent = qGuestbook.title.contains(keyword);
        BooleanExpression exAll = exTitle.or(exContent); // 타이틀과 content를 결합.각각 BooleanExpression
        builder.and(exAll); // builder에 booleanExpression, 즉 조건 결합.
        builder.and(qGuestbook.gno.gt(0L)); //gno가 0보다 큰 조건 결합. 총 2가지 조건이결합된다. gt=Greaterthan?
        Page<Guestbook> result = guestbookRepository.findAll(builder,pageable);
        result.stream().forEach(guestbook -> System.out.println("guestbook>> " + guestbook));
        /*
        where 조건의 일부가 이렇게 삽입된다.
        g1_0.title like ? escape '!'
        or g1_0.title like ? escape '!'
         */
    }
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        for (GuestbookDTO gbDTO : resultDTO.getDtoList()){
            System.out.println(gbDTO);
        }
    }

}
