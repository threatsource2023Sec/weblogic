package org.apache.xml.security.stax.securityToken;

import org.apache.xml.security.binding.xmldsig.KeyInfoType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.ConfigurationProperties;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.utils.ClassLoaderUtils;

public abstract class SecurityTokenFactory {
   private static SecurityTokenFactory securityTokenFactory;

   public static synchronized SecurityTokenFactory getInstance() throws XMLSecurityException {
      if (securityTokenFactory == null) {
         String stf = ConfigurationProperties.getProperty("securityTokenFactory");
         if (stf == null) {
            throw new XMLSecurityException("algorithm.ClassDoesNotExist", new Object[]{"null"});
         }

         Class callingClass = ConfigurationProperties.getCallingClass();
         if (callingClass == null) {
            callingClass = SecurityTokenFactory.class;
         }

         try {
            Class securityTokenFactoryClass = ClassLoaderUtils.loadClass(stf, callingClass);
            securityTokenFactory = (SecurityTokenFactory)securityTokenFactoryClass.newInstance();
         } catch (ClassNotFoundException var3) {
            throw new XMLSecurityException(var3, "algorithm.ClassDoesNotExist", new Object[]{stf});
         } catch (InstantiationException var4) {
            throw new XMLSecurityException(var4, "algorithm.ClassDoesNotExist", new Object[]{stf});
         } catch (IllegalAccessException var5) {
            throw new XMLSecurityException(var5, "algorithm.ClassDoesNotExist", new Object[]{stf});
         }
      }

      return securityTokenFactory;
   }

   public abstract InboundSecurityToken getSecurityToken(KeyInfoType var1, SecurityTokenConstants.KeyUsage var2, XMLSecurityProperties var3, InboundSecurityContext var4) throws XMLSecurityException;
}
