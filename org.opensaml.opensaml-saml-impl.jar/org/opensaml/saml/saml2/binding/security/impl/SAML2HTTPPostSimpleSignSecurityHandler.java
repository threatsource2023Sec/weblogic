package org.opensaml.saml.saml2.binding.security.impl;

import com.google.common.base.Strings;
import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.Criterion;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.binding.security.impl.BaseSAMLSimpleSignatureSecurityHandler;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCriterion;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

public class SAML2HTTPPostSimpleSignSecurityHandler extends BaseSAMLSimpleSignatureSecurityHandler {
   @Nonnull
   private Logger log = LoggerFactory.getLogger(SAML2HTTPPostSimpleSignSecurityHandler.class);
   @NonnullAfterInit
   private ParserPool parserPool;
   @NonnullAfterInit
   private KeyInfoCredentialResolver keyInfoResolver;

   @NonnullAfterInit
   public ParserPool getParserPool() {
      return this.parserPool;
   }

   public void setParser(@Nonnull ParserPool newParserPool) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.parserPool = (ParserPool)Constraint.isNotNull(newParserPool, "ParserPool cannot be null");
   }

   @NonnullAfterInit
   public KeyInfoCredentialResolver getKeyInfoResolver() {
      return this.keyInfoResolver;
   }

   public void setKeyInfoResolver(@Nonnull KeyInfoCredentialResolver newKeyInfoResolver) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.keyInfoResolver = (KeyInfoCredentialResolver)Constraint.isNotNull(newKeyInfoResolver, "KeyInfoCredentialResolver cannot be null");
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      Constraint.isNotNull(this.parserPool, "ParserPool cannot be null");
      Constraint.isNotNull(this.keyInfoResolver, "KeyInfoCredentialResolver cannot be null");
   }

   protected boolean ruleHandles(@Nonnull MessageContext messageContext) {
      return "POST".equals(this.getHttpServletRequest().getMethod());
   }

   @Nullable
   protected byte[] getSignedContent() throws MessageHandlerException {
      HttpServletRequest request = this.getHttpServletRequest();
      StringBuilder builder = new StringBuilder();

      try {
         String samlMsg;
         if (request.getParameter("SAMLRequest") != null) {
            samlMsg = new String(Base64Support.decode(request.getParameter("SAMLRequest")), "UTF-8");
            builder.append("SAMLRequest=" + samlMsg);
         } else {
            if (request.getParameter("SAMLResponse") == null) {
               this.log.warn("Could not extract either a SAMLRequest or a SAMLResponse from the form control data");
               throw new MessageHandlerException("Extract of SAMLRequest or SAMLResponse from form control data");
            }

            samlMsg = new String(Base64Support.decode(request.getParameter("SAMLResponse")), "UTF-8");
            builder.append("SAMLResponse=" + samlMsg);
         }
      } catch (UnsupportedEncodingException var7) {
      }

      if (request.getParameter("RelayState") != null) {
         builder.append("&RelayState=" + request.getParameter("RelayState"));
      }

      builder.append("&SigAlg=" + request.getParameter("SigAlg"));
      String constructed = builder.toString();
      if (Strings.isNullOrEmpty(constructed)) {
         this.log.warn("Could not construct signed content string from form control data");
         return null;
      } else {
         this.log.debug("Constructed signed content string for HTTP-Post-SimpleSign {}", constructed);

         try {
            return constructed.getBytes("UTF-8");
         } catch (UnsupportedEncodingException var6) {
            return null;
         }
      }
   }

   @Nonnull
   @NonnullElements
   protected List getRequestCredentials(@Nonnull MessageContext samlContext) throws MessageHandlerException {
      String kiBase64 = this.getHttpServletRequest().getParameter("KeyInfo");
      if (Strings.isNullOrEmpty(kiBase64)) {
         this.log.debug("Form control data did not contain a KeyInfo");
         return Collections.emptyList();
      } else {
         this.log.debug("Found a KeyInfo in form control data, extracting validation credentials");
         Unmarshaller unmarshaller = XMLObjectProviderRegistrySupport.getUnmarshallerFactory().getUnmarshaller(KeyInfo.DEFAULT_ELEMENT_NAME);
         if (unmarshaller == null) {
            throw new MessageHandlerException("Could not obtain a KeyInfo unmarshaller");
         } else {
            ByteArrayInputStream is = new ByteArrayInputStream(Base64Support.decode(kiBase64));
            KeyInfo keyInfo = null;

            try {
               Document doc = this.getParserPool().parse(is);
               keyInfo = (KeyInfo)unmarshaller.unmarshall(doc.getDocumentElement());
            } catch (XMLParserException var10) {
               this.log.warn("Error parsing KeyInfo data", var10);
               throw new MessageHandlerException("Error parsing KeyInfo data", var10);
            } catch (UnmarshallingException var11) {
               this.log.warn("Error unmarshalling KeyInfo data", var11);
               throw new MessageHandlerException("Error unmarshalling KeyInfo data", var11);
            }

            if (keyInfo == null) {
               this.log.warn("Could not successfully extract KeyInfo object from the form control data");
               return Collections.emptyList();
            } else {
               List credentials = new ArrayList();
               CriteriaSet criteriaSet = new CriteriaSet(new Criterion[]{new KeyInfoCriterion(keyInfo)});

               try {
                  Iterator var8 = this.keyInfoResolver.resolve(criteriaSet).iterator();

                  while(var8.hasNext()) {
                     Credential cred = (Credential)var8.next();
                     credentials.add(cred);
                  }

                  return credentials;
               } catch (ResolverException var12) {
                  this.log.warn("Error resolving credentials from KeyInfo", var12);
                  throw new MessageHandlerException("Error resolving credentials from KeyInfo", var12);
               }
            }
         }
      }
   }
}
