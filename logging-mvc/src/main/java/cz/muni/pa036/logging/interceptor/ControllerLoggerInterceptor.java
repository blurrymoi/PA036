package cz.muni.pa036.logging.interceptor;

import cz.muni.pa036.logging.helper.CRUDLogger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kamil Triscik.
 */
public class ControllerLoggerInterceptor extends HandlerInterceptorAdapter {

    private final CRUDLogger CRUD_LOGGER = new CRUDLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler)
            throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CRUD_LOGGER.logURLRedirect(request.getRequestURI(), authentication.getName());
        return true;
    }

}
