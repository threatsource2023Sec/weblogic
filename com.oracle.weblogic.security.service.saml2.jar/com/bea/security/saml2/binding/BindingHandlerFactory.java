package com.bea.security.saml2.binding;

import com.bea.security.saml2.binding.impl.ArtifactBindingReceiver;
import com.bea.security.saml2.binding.impl.ArtifactBindingSender;
import com.bea.security.saml2.binding.impl.HttpPostBindingReceiver;
import com.bea.security.saml2.binding.impl.HttpPostBindingSender;
import com.bea.security.saml2.binding.impl.HttpRedirectBindingReceiver;
import com.bea.security.saml2.binding.impl.HttpRedirectBindingSender;
import com.bea.security.saml2.binding.impl.SoapHttpBindingReceiver;
import com.bea.security.saml2.binding.impl.SoapHttpBindingSender;
import com.bea.security.saml2.binding.impl.SoapSynchronousBindingClient;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import java.net.HttpURLConnection;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BindingHandlerFactory {
   private SAML2ConfigSpi config;

   public BindingHandlerFactory(SAML2ConfigSpi config) {
      this.config = config;
   }

   public BindingSender newBindingSender(String bindingType, HttpServletRequest req, HttpServletResponse resp) throws BindingHandlerException {
      if ("HTTP/Artifact".equals(bindingType)) {
         return new ArtifactBindingSender(this.config, req, resp);
      } else if ("HTTP/POST".equals(bindingType)) {
         return new HttpPostBindingSender(this.config, req, resp);
      } else if ("HTTP/Redirect".equals(bindingType)) {
         return new HttpRedirectBindingSender(this.config, req, resp);
      } else if ("SOAP".equals(bindingType)) {
         return new SoapHttpBindingSender(this.config, req, resp);
      } else {
         throw new BindingHandlerException("Unsupported binding type received: " + bindingType, 400);
      }
   }

   public SynchronousBindingClient newSyncBindingClient(String bindingType, HttpURLConnection conn) throws BindingHandlerException {
      if ("SOAP".equals(bindingType)) {
         return new SoapSynchronousBindingClient(this.config, conn);
      } else {
         throw new BindingHandlerException("Unsupported binding type received: " + bindingType, 400);
      }
   }

   public BindingReceiver newBindingReceiver(String bindingType, HttpServletRequest req, HttpServletResponse resp) throws BindingHandlerException {
      if ("HTTP/Artifact".equals(bindingType)) {
         return new ArtifactBindingReceiver(this.config, req, resp);
      } else if ("HTTP/POST".equals(bindingType)) {
         return new HttpPostBindingReceiver(this.config, req, resp);
      } else if ("HTTP/Redirect".equals(bindingType)) {
         return new HttpRedirectBindingReceiver(this.config, req, resp);
      } else if ("SOAP".equals(bindingType)) {
         return new SoapHttpBindingReceiver(this.config, req, resp);
      } else {
         throw new BindingHandlerException("Unsupported binding type received: " + bindingType, 400);
      }
   }
}
