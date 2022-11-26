package com.bea.security.saml2.binding.impl;

import com.bea.common.logger.spi.LoggerSpi;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.binding.BindingReceiver;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import java.security.PublicKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class BaseHttpBindingReceiver implements BindingReceiver {
   protected SAML2ConfigSpi config;
   protected LoggerSpi log;
   protected HttpServletRequest httpRequest;
   protected HttpServletResponse httpResponse;
   protected String relayState;

   public BaseHttpBindingReceiver(SAML2ConfigSpi config, HttpServletRequest req, HttpServletResponse resp) {
      this.config = config;
      this.httpRequest = req;
      this.httpResponse = resp;
      this.relayState = req.getParameter("RelayState");
      this.log = config.getLogger();
   }

   public String getRelayState() {
      return this.relayState;
   }

   public boolean verifySignature(PublicKey key) throws BindingHandlerException {
      return false;
   }
}
