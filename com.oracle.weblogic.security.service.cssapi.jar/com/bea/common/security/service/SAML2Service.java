package com.bea.common.security.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SAML2Service {
   boolean process(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException;

   void publish(String var1) throws SAML2PublishException;

   void publish(String var1, boolean var2) throws SAML2PublishException;

   String exportMetadata() throws SAML2PublishException;
}
