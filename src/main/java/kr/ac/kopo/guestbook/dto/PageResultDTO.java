package kr.ac.kopo.guestbook.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
public class PageResultDTO <DTO,EN>{ // 다양한 곳에서 사용할 수 있도록 제네릭 타입 사용.
    private List<DTO> dtoList; //List 형태로 된 dtoList를 반환하는 곳이다
    private int totalPage;
    /*
    * 현재 페이지 번호                 private int page;
    * 목록 사이즈                     private int size;
    * 시작 페이지 번호, 끝 페이지 번호   private int start, end;
    * 이전, 다음존재 여부              private boolean prev,next;
    * */
    private int page;
    private int size;
    private int start, end;
    private boolean prev,next;
    private List<Integer> pageList;

    public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
        // EN은 엔티티를 의미, EN과 DTO로 구성된 자료타입
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }
    private void makePageList(Pageable pageable){
        this.page = pageable.getPageNumber() +1 ;
        this.size = pageable.getPageSize();

        int tempEnd = (int)Math.ceil(page/10.0) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }
}
