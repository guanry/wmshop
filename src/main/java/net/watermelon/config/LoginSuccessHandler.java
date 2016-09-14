package net.watermelon.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.watermelon.admin.menu.manager.MenuRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;

public class LoginSuccessHandler implements AuthenticationSuccessHandler {
	 protected Log logger = LogFactory.getLog(getClass());  

	 public void onAuthenticationSuccess(HttpServletRequest request,
			HttpServletResponse response, Authentication arg2) throws IOException,
			ServletException {
		logger.info("Success hanlder");
		initMenu(request);
		String  redirectUrl = "index"; //缺省的登陆成功页面
		SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");  
        if(savedRequest != null) {  
            redirectUrl =   savedRequest.getRedirectUrl();  
            request.getSession().removeAttribute("SPRING_SECURITY_SAVED_REQUEST");  
        }  
        
        response.sendRedirect(redirectUrl);
	}
	
	 @Autowired
	 MenuRepository menuRepository;
	 
	 private void initMenu(HttpServletRequest request ){
		  request.getSession().setAttribute("SESSON_MENU", menuRepository.getMenuList());
		 }

}
