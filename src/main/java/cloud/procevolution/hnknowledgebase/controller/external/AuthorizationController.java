package cloud.procevolution.hnknowledgebase.controller.external;

import cloud.procevolution.hnknowledgebase.service.AuthorizeService;
import cloud.procevolution.hnknowledgebase.service.JwtTokenService;
import cloud.procevolution.hnknowledgebase.view.AuthorizeResponse;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.print.attribute.standard.Media;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class AuthorizationController {

    private final AuthorizeService authorizeService;

    @PostMapping(path = "/public/authorize",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<?> authorize(@RequestParam String email, @RequestParam String password){
        AuthorizeResponse authorizeResponse = null;
//        authorizeResponse = authorizeService.authorize(email, password);
//        return ResponseEntity.accepted().body(authorizeResponse);
        try {
            authorizeResponse = authorizeService.authorize(email, password);
            return ResponseEntity.accepted().body(authorizeResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", e.getMessage()
            ));
        }
    }

}
