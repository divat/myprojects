package com.codemantra.maestro.config.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import com.codemantra.maestro.service.AuthenticationService;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	AuthenticationService customUserDetailsService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication auth) throws IOException, ServletException {
		HttpSession session = request.getSession();
		SavedRequest savedReq = (SavedRequest) session.getAttribute(WebAttributes.ACCESS_DENIED_403);
	
	
		if (savedReq == null) {
			request.getSession().setAttribute("username", auth.getPrincipal());
			response.sendRedirect(request.getContextPath() + "/home");
		}
		else {
		    response.sendRedirect(request.getContextPath() + "/login?error=no_permit");
		}
	}

}
