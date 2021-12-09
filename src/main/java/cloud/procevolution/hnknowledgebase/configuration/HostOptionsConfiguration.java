package cloud.procevolution.hnknowledgebase.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@RequiredArgsConstructor
public class HostOptionsConfiguration {

    private final HttpServletRequest httpServletRequest;

//    @Bean
//    @RequestScope
//    public String hostPath() {
//        return httpServletRequest.getRequestURL().toString().replace(httpServletRequest.getRequestURI(), "");
//    }
}
