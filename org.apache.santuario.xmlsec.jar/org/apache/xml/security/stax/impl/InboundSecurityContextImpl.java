package org.apache.xml.security.stax.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.ConfigurationProperties;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.securityEvent.AlgorithmSuiteSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEvent;
import org.apache.xml.security.stax.securityEvent.SecurityEventConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;

public class InboundSecurityContextImpl extends AbstractSecurityContextImpl implements InboundSecurityContext {
   private static final Boolean allowMD5Algorithm = Boolean.valueOf(ConfigurationProperties.getProperty("AllowMD5Algorithm"));
   private final Map securityTokenProviders = new HashMap();

   protected void forwardSecurityEvent(SecurityEvent securityEvent) throws XMLSecurityException {
      if (!allowMD5Algorithm && SecurityEventConstants.AlgorithmSuite.equals(securityEvent.getSecurityEventType())) {
         AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = (AlgorithmSuiteSecurityEvent)securityEvent;
         if (algorithmSuiteSecurityEvent.getAlgorithmURI().contains("md5") || algorithmSuiteSecurityEvent.getAlgorithmURI().contains("MD5")) {
            throw new XMLSecurityException("secureProcessing.AllowMD5Algorithm");
         }
      }

      super.forwardSecurityEvent(securityEvent);
   }

   public void registerSecurityTokenProvider(String id, SecurityTokenProvider securityTokenProvider) {
      if (id == null) {
         throw new IllegalArgumentException("Id must not be null");
      } else {
         this.securityTokenProviders.put(id, securityTokenProvider);
      }
   }

   public SecurityTokenProvider getSecurityTokenProvider(String id) {
      return (SecurityTokenProvider)this.securityTokenProviders.get(id);
   }

   public List getRegisteredSecurityTokenProviders() {
      return new ArrayList(this.securityTokenProviders.values());
   }
}
