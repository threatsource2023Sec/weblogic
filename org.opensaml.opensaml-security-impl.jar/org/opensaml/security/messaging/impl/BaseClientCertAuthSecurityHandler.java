package org.opensaml.security.messaging.impl;

import com.google.common.base.Strings;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
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
import org.opensaml.core.criterion.EntityIdCriterion;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.messaging.ClientTLSSecurityParametersContext;
import org.opensaml.security.messaging.ServletRequestX509CredentialAdapter;
import org.opensaml.security.trust.TrustEngine;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.X509Support;
import org.opensaml.security.x509.tls.CertificateNameOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseClientCertAuthSecurityHandler extends BaseTrustEngineSecurityHandler {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(BaseClientCertAuthSecurityHandler.class);
   @Nullable
   private CertificateNameOptions certNameOptions;
   @NonnullAfterInit
   private HttpServletRequest httpServletRequest;

   @NonnullAfterInit
   public HttpServletRequest getHttpServletRequest() {
      return this.httpServletRequest;
   }

   public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      this.httpServletRequest = (HttpServletRequest)Constraint.isNotNull(request, "HttpServletRequest cannot be null");
   }

   @Nullable
   protected CertificateNameOptions getCertificateNameOptions() {
      return this.certNameOptions;
   }

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.httpServletRequest == null) {
         throw new ComponentInitializationException("HttpServletRequest cannot be null");
      }
   }

   @Nullable
   protected TrustEngine resolveTrustEngine(@Nonnull MessageContext messageContext) {
      ClientTLSSecurityParametersContext secContext = (ClientTLSSecurityParametersContext)messageContext.getSubcontext(ClientTLSSecurityParametersContext.class);
      return secContext != null && secContext.getValidationParameters() != null ? secContext.getValidationParameters().getX509TrustEngine() : null;
   }

   protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      if (!super.doPreInvoke(messageContext)) {
         return false;
      } else {
         ClientTLSSecurityParametersContext secContext = (ClientTLSSecurityParametersContext)messageContext.getSubcontext(ClientTLSSecurityParametersContext.class);
         if (secContext != null && !secContext.isEvaluateClientCertificate()) {
            this.log.debug("{} ClientTLSSecurityParametersContext signals to not perform client TLS cert evaluation", this.getLogPrefix());
            return false;
         } else if (secContext != null && secContext.getValidationParameters() != null && secContext.getValidationParameters().getCertificateNameOptions() != null) {
            this.certNameOptions = secContext.getValidationParameters().getCertificateNameOptions();
            return true;
         } else {
            throw new MessageHandlerException("CertificateNameOptions was not available from the MessageContext");
         }
      }
   }

   protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
      ServletRequestX509CredentialAdapter requestCredential;
      try {
         requestCredential = new ServletRequestX509CredentialAdapter(this.getHttpServletRequest());
      } catch (SecurityException var5) {
         this.log.debug("{} HttpServletRequest did not contain a peer credential, skipping client certificate authentication", this.getLogPrefix());
         return;
      }

      if (this.log.isDebugEnabled()) {
         try {
            this.log.debug("{} Attempting to authenticate inbound connection that presented the certificate:", this.getLogPrefix());
            this.log.debug(Base64Support.encode(requestCredential.getEntityCertificate().getEncoded(), false));
         } catch (CertificateEncodingException var4) {
         }
      }

      this.doEvaluate(requestCredential, messageContext);
   }

   protected void doEvaluate(@Nonnull X509Credential requestCredential, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      String presenterEntityID = this.getCertificatePresenterEntityID(messageContext);
      if (presenterEntityID != null) {
         this.log.debug("{} Attempting client certificate authentication using context presenter entity ID: {}", this.getLogPrefix(), presenterEntityID);
         if (this.evaluate(requestCredential, presenterEntityID, messageContext)) {
            this.log.debug("{} Authentication via client certificate succeeded for context presenter entity ID: {}", this.getLogPrefix(), presenterEntityID);
            this.setAuthenticatedState(messageContext, true);
         } else {
            this.log.error("{} Authentication via client certificate failed for context presenter entity ID: {}", this.getLogPrefix(), presenterEntityID);
            throw new MessageHandlerException("Client certificate authentication failed for context presenter entity ID");
         }
      } else {
         String derivedPresenter = this.evaluateCertificateNameDerivedPresenters(requestCredential, messageContext);
         if (derivedPresenter != null) {
            this.log.debug("{} Authentication via client certificate succeeded for certificate-derived presenter entity ID: {}", this.getLogPrefix(), derivedPresenter);
            this.setAuthenticatedCertificatePresenterEntityID(messageContext, derivedPresenter);
            this.setAuthenticatedState(messageContext, true);
         } else {
            derivedPresenter = this.evaluateDerivedPresenters(requestCredential, messageContext);
            if (derivedPresenter != null) {
               this.log.debug("{} Authentication via client certificate succeeded for derived presenter entity ID: {}", this.getLogPrefix(), derivedPresenter);
               this.setAuthenticatedCertificatePresenterEntityID(messageContext, derivedPresenter);
               this.setAuthenticatedState(messageContext, true);
            }
         }
      }
   }

   @Nullable
   protected abstract String getCertificatePresenterEntityID(@Nonnull MessageContext var1);

   protected abstract void setAuthenticatedCertificatePresenterEntityID(@Nonnull MessageContext var1, @Nullable String var2);

   protected abstract void setAuthenticatedState(@Nonnull MessageContext var1, boolean var2);

   @Nullable
   protected CriteriaSet buildCriteriaSet(@Nullable String entityID, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      CriteriaSet criteriaSet = new CriteriaSet();
      if (!Strings.isNullOrEmpty(entityID)) {
         criteriaSet.add(new EntityIdCriterion(entityID));
      }

      criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
      return criteriaSet;
   }

   @Nullable
   protected String evaluateDerivedPresenters(@Nonnull X509Credential requestCredential, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      return null;
   }

   @Nullable
   protected String evaluateCertificateNameDerivedPresenters(@Nullable X509Credential requestCredential, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      String candidatePresenter = null;
      if (this.getCertificateNameOptions().evaluateSubjectDN()) {
         candidatePresenter = this.evaluateSubjectDN(requestCredential, messageContext);
         if (candidatePresenter != null) {
            return candidatePresenter;
         }
      }

      if (!this.getCertificateNameOptions().getSubjectAltNames().isEmpty()) {
         candidatePresenter = this.evaluateSubjectAltNames(requestCredential, messageContext);
         if (candidatePresenter != null) {
            return candidatePresenter;
         }
      }

      if (this.getCertificateNameOptions().evaluateSubjectCommonName()) {
         candidatePresenter = this.evaluateSubjectCommonName(requestCredential, messageContext);
         if (candidatePresenter != null) {
            return candidatePresenter;
         }
      }

      return null;
   }

   @Nullable
   protected String evaluateSubjectCommonName(@Nonnull X509Credential requestCredential, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("{} Evaluating client cert by deriving presenter as cert CN", this.getLogPrefix());
      X509Certificate certificate = requestCredential.getEntityCertificate();
      String candidatePresenter = this.getCommonName(certificate);
      if (candidatePresenter != null) {
         if (this.evaluate(requestCredential, candidatePresenter, messageContext)) {
            this.log.debug("{} Authentication succeeded for presenter entity ID derived from CN: {}", this.getLogPrefix(), candidatePresenter);
            return candidatePresenter;
         }

         this.log.debug("{} Authentication failed for presenter entity ID derived from CN: {}", this.getLogPrefix(), candidatePresenter);
      }

      return null;
   }

   @Nullable
   protected String evaluateSubjectDN(@Nonnull X509Credential requestCredential, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("{} Evaluating client cert by deriving presenter as cert subject DN", this.getLogPrefix());
      X509Certificate certificate = requestCredential.getEntityCertificate();
      String candidatePresenter = this.getSubjectName(certificate);
      if (candidatePresenter != null) {
         if (this.evaluate(requestCredential, candidatePresenter, messageContext)) {
            this.log.debug("{} Authentication succeeded for presenter entity ID derived from subject DN: {}", this.getLogPrefix(), candidatePresenter);
            return candidatePresenter;
         }

         this.log.debug("{} Authentication failed for presenter entity ID derived from subject DN: {}", this.getLogPrefix(), candidatePresenter);
      }

      return null;
   }

   @Nullable
   protected String evaluateSubjectAltNames(@Nonnull X509Credential requestCredential, @Nonnull MessageContext messageContext) throws MessageHandlerException {
      this.log.debug("{} Evaluating client cert by deriving presenter from subject alt names", this.getLogPrefix());
      X509Certificate certificate = requestCredential.getEntityCertificate();
      Iterator var4 = this.getCertificateNameOptions().getSubjectAltNames().iterator();

      while(var4.hasNext()) {
         Integer altNameType = (Integer)var4.next();
         this.log.debug("{} Evaluating alt names of type: {}", this.getLogPrefix(), altNameType.toString());
         List altNames = this.getAltNames(certificate, altNameType);
         Iterator var7 = altNames.iterator();

         while(var7.hasNext()) {
            String altName = (String)var7.next();
            if (this.evaluate(requestCredential, altName, messageContext)) {
               this.log.debug("{} Authentication succeeded for presenter entity ID derived from subject alt name: {}", this.getLogPrefix(), altName);
               return altName;
            }

            this.log.debug("{} Authentication failed for presenter entity ID derived from subject alt name: {}", this.getLogPrefix(), altName);
         }
      }

      return null;
   }

   @Nullable
   protected String getCommonName(@Nonnull X509Certificate cert) {
      List names = X509Support.getCommonNames(cert.getSubjectX500Principal());
      if (names != null && !names.isEmpty()) {
         String name = (String)names.get(0);
         this.log.debug("{} Extracted common name from certificate: {}", this.getLogPrefix(), name);
         return name;
      } else {
         return null;
      }
   }

   @Nullable
   protected String getSubjectName(@Nonnull X509Certificate cert) {
      if (cert == null) {
         return null;
      } else {
         String name = null;
         if (!Strings.isNullOrEmpty(this.getCertificateNameOptions().getX500SubjectDNFormat())) {
            name = this.getCertificateNameOptions().getX500DNHandler().getName(cert.getSubjectX500Principal(), this.getCertificateNameOptions().getX500SubjectDNFormat());
         } else {
            name = this.getCertificateNameOptions().getX500DNHandler().getName(cert.getSubjectX500Principal());
         }

         this.log.debug("{} Extracted subject name from certificate: {}", this.getLogPrefix(), name);
         return name;
      }
   }

   @Nonnull
   @NonnullElements
   protected List getAltNames(@Nonnull X509Certificate cert, @Nonnull Integer altNameType) {
      this.log.debug("{} Extracting alt names from certificate of type: {}", this.getLogPrefix(), altNameType.toString());
      Integer[] nameTypes = new Integer[]{altNameType};
      List altNames = X509Support.getAltNames(cert, nameTypes);
      List names = new ArrayList();
      Iterator var6 = altNames.iterator();

      while(var6.hasNext()) {
         Object altNameValue = var6.next();
         if (!(altNameValue instanceof String)) {
            this.log.debug("{} Skipping non-String certificate alt name value", this.getLogPrefix());
         } else {
            names.add((String)altNameValue);
         }
      }

      this.log.debug("{} Extracted alt names from certificate: {}", this.getLogPrefix(), names.toString());
      return names;
   }
}
