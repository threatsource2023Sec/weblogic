package com.bea.security.saml2.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Service {
   boolean process(HttpServletRequest var1, HttpServletResponse var2) throws ServletException, IOException;
}
