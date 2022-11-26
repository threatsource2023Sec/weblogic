package com.bea.security.saml2.binding.impl;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.binding.BindingSender;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseHttpBindingSender implements BindingSender {
   protected LoggerSpi log;
   protected SAML2ConfigSpi config;
   protected HttpServletRequest httpRequest;
   protected HttpServletResponse httpResponse;

   public BaseHttpBindingSender(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      this.config = config;
      this.httpRequest = req;
      this.httpResponse = resp;
      this.log = config.getLogger();
   }
}
