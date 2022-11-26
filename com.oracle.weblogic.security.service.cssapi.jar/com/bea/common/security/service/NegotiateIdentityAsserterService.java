package com.bea.common.security.service;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public interface NegotiateIdentityAsserterService {
   void process(ServletRequest var1, ServletResponse var2, FilterChain var3, NegotiateIdentityAsserterCallback var4) throws IOException, ServletException;

   void setFormBasedAuthEnabled(boolean var1);

   public interface NegotiateIdentityAsserterCallback {
      String getWebAppAuthType(HttpServletRequest var1);

      boolean isAlreadyAuthenticated();

      void userAuthenticated(Identity var1, HttpServletRequest var2);
   }
}
