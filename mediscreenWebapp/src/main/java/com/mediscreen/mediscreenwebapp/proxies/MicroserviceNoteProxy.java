package com.mediscreen.mediscreenwebapp.proxies;

        import com.mediscreen.mediscreenwebapp.beans.NoteBean;
        import org.springframework.cloud.openfeign.FeignClient;
        import org.springframework.web.bind.annotation.GetMapping;
        import org.springframework.web.bind.annotation.PostMapping;
        import org.springframework.web.bind.annotation.RequestBody;

        import java.util.List;

@FeignClient(name = "gateway-service", url = "http://gatewayservice:9002")
public interface MicroserviceNoteProxy {

    @GetMapping("api/notes")
    List<NoteBean> getAllNote();

    @PostMapping("api/notes")
    NoteBean createNote(@RequestBody NoteBean noteBean);
}
