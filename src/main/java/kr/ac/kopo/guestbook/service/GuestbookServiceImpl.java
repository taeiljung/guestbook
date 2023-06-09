package kr.ac.kopo.guestbook.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import kr.ac.kopo.guestbook.dto.GuestbookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.dto.PageResultDTO;
import kr.ac.kopo.guestbook.entity.Guestbook;
import kr.ac.kopo.guestbook.entity.QGuestbook;
import kr.ac.kopo.guestbook.repository.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service // 스프링 빈으로처리되도록 어노테이션 추가
@Log4j2
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {
    private final GuestbookRepository repository;

    @Override
    public GuestbookDTO read(Long gno) {
        Optional<Guestbook> result = repository.findById(gno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    @Override
    public void modify(GuestbookDTO dto) {
        Optional<Guestbook> result = repository.findById(dto.getGno());
        if (result.isPresent()) {
            Guestbook entity = result.get();
            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());
            repository.save(entity);
        }
    }

    @Override
    public Long register(GuestbookDTO dto) {
        log.info("DTO---------------------");
        log.info(dto); // 받아온 dto정보
        Guestbook entity = dtoToEntity(dto);
        log.info(entity); // 생성시 dto에서 entity로 변환된 Guestbook 객체 로그출력.
        repository.save(entity);
        return entity.getGno();
    }

    @Override
    public PageResultDTO<GuestbookDTO, Guestbook> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());
        BooleanBuilder bB = getSearch(requestDTO);
        Page<Guestbook> result = repository.findAll(bB,pageable);
        Function<Guestbook, GuestbookDTO> fn = (entity -> entityToDto(entity));
        return new PageResultDTO<>(result, fn);
    }

    public BooleanBuilder getSearch(PageRequestDTO requestDTO) {
        String type = requestDTO.getType();
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestbook qGuestbook = QGuestbook.guestbook;
        String keyWord = requestDTO.getKeyword();
        BooleanExpression expression = qGuestbook.gno.gt(0L);
        booleanBuilder.and(expression);
        if (type == null || type.trim().length() == 0) {
            return booleanBuilder;
        }
        BooleanBuilder conditionBuilder = new BooleanBuilder();
        if (type.contains("t")) {
            conditionBuilder.or(qGuestbook.title.contains(keyWord));
        }
        if (type.contains("c")) {
            conditionBuilder.or(qGuestbook.content.contains(keyWord));
        }
        if (type.contains("w")) {
            conditionBuilder.or(qGuestbook.writer.contains(keyWord));
        }
        booleanBuilder.and(conditionBuilder);
        return booleanBuilder;
    }
}