package kr.ac.kopo.guestbook.controller;


import kr.ac.kopo.guestbook.dto.GuestbookDTO;
import kr.ac.kopo.guestbook.dto.PageRequestDTO;
import kr.ac.kopo.guestbook.service.GuestbookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping({"/guestbook"}) //는 resources > templates > layout > guestbook > list.html
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {
    private final GuestbookService service;
    @GetMapping("/")
    public String index(){
        log.info("index-----------");
        return "redirect:/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model){
        log.info("list----------."+pageRequestDTO);
        model.addAttribute("result",service.getList(pageRequestDTO));
    }
    @GetMapping("/register")
    public void register(){
        log.info("register get....");
    }
    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes){
        log.info("dto. . . . " + dto);
        log.info("gno. . . . " + dto.getGno());
        Long gno = service.register(dto);
        redirectAttributes.addFlashAttribute("msg",gno);
        return "redirect:/guestbook/list";
    }
}
