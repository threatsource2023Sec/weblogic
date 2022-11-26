package com.bea.common.security.service;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface SAMLSingleSignOnService {
   void doITSGet(HttpServletRequest var1, HttpServletResponse var2, ServletContext var3) throws ServletException, IOException;

   void doARSPost(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException;

   void doACSGet(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException;

   void doACSPost(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException;

   void doRedirectFilter(ServletRequest var1, ServletResponse var2, FilterChain var3) throws IOException, ServletException;
}
