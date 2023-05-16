package kr.ac.kopo.guestbook.service;

import kr.ac.kopo.guestbook.dto.GuestbookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.dto.PageResultDTO;
import kr.ac.kopo.guestbook.entity.Guestbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {
    @Autowired
    private GuestbookService service;

    @Test
    public void testRegister(){
        GuestbookDTO gDTo = GuestbookDTO.builder()
                .title("SAMPLE TITLE...")
                .content("SAMPLE CONTENT..")
                .writer("USER0")
                .build();
        System.out.println("inserted testRegister >> " + service.register(gDTo)); // 실행
        //Impl에 선언한 repository.save로 데이터가 정상적으로 추가됨. 301번행 추가됨
    }
    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(1).size(10).build();
        PageResultDTO<GuestbookDTO, Guestbook> resultDTO = service.getList(pageRequestDTO);
        System.out.println("prev : " + resultDTO.isPrev());
        System.out.println("next : " + resultDTO.isNext());
        System.out.println("total : " + resultDTO.getTotalPage());
        System.out.println("===========================");
        for (GuestbookDTO guestbookDTO : resultDTO.getDtoList()){
            System.out.println(guestbookDTO);
        }
        resultDTO.getPageList().forEach(i->System.out.println(i));
    }
}
