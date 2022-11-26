package org.opensaml.soap.client.http;

import com.google.common.base.Function;
import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.AbstractInitializableComponent;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.opensaml.messaging.context.InOutOperationContext;
import org.opensaml.messaging.context.httpclient.HttpClientRequestContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.decoder.httpclient.HttpClientResponseMessageDecoder;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.messaging.encoder.httpclient.HttpClientRequestMessageEncoder;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.messaging.pipeline.httpclient.HttpClientMessagePipeline;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.opensaml.security.messaging.HttpClientSecurityContext;
import org.opensaml.soap.client.SOAPClient;
import org.opensaml.soap.client.SOAPFaultException;
import org.opensaml.soap.common.SOAP11FaultDecodingException;
import org.opensaml.soap.common.SOAPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ThreadSafe
public abstract class AbstractPipelineHttpSOAPClient extends AbstractInitializableComponent implements SOAPClient {
   @Nonnull
   private final Logger log = LoggerFactory.getLogger(AbstractPipelineHttpSOAPClient.class);
   @NonnullAfterInit
   private HttpClient httpClient;
   @Nullable
   private HttpClientSecurityParameters httpClientSecurityParameters;
   @Nullable
   private Function tlsCriteriaSetStrategy;

   protected void doInitialize() throws ComponentInitializationException {
      super.doInitialize();
      if (this.httpClient == null) {
         throw new ComponentInitializationException("HttpClient cannot be null");
      }
   }

   protected void doDestroy() {
      this.httpClient = null;
      this.httpClientSecurityParameters = null;
      this.tlsCriteriaSetStrategy = null;
      super.doDestroy();
   }

   @Nonnull
   public HttpClient getHttpClient() {
      return this.httpClient;
   }

   public void setHttpClient(@Nonnull HttpClient client) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.httpClient = (HttpClient)Constraint.isNotNull(client, "HttpClient cannot be null");
   }

   @Nullable
   public HttpClientSecurityParameters getHttpClientSecurityParameters() {
      return this.httpClientSecurityParameters;
   }

   public void setHttpClientSecurityParameters(@Nullable HttpClientSecurityParameters params) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.httpClientSecurityParameters = params;
   }

   @Nullable
   public Function getTLSCriteriaSetStrategy() {
      return this.tlsCriteriaSetStrategy;
   }

   public void setTLSCriteriaSetStrategy(@Nullable Function function) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.tlsCriteriaSetStrategy = function;
   }

   public void send(@Nonnull @NotEmpty String endpoint, @Nonnull InOutOperationContext operationContext) throws SOAPException, SecurityException {
      Constraint.isNotNull(endpoint, "Endpoint cannot be null");
      Constraint.isNotNull(operationContext, "Operation context cannot be null");
      HttpClientMessagePipeline pipeline = null;

      try {
         pipeline = this.resolvePipeline(operationContext);
         if (pipeline.getOutboundPayloadMessageHandler() != null) {
            pipeline.getOutboundPayloadMessageHandler().invoke(operationContext.getOutboundMessageContext());
         }

         HttpUriRequest httpRequest = this.buildHttpRequest(endpoint, operationContext);
         HttpClientContext httpContext = this.buildHttpContext(httpRequest, operationContext);
         HttpClientRequestMessageEncoder encoder = pipeline.getEncoder();
         encoder.setHttpRequest(httpRequest);
         encoder.setMessageContext(operationContext.getOutboundMessageContext());
         encoder.initialize();
         encoder.prepareContext();
         if (pipeline.getOutboundTransportMessageHandler() != null) {
            pipeline.getOutboundTransportMessageHandler().invoke(operationContext.getOutboundMessageContext());
         }

         encoder.encode();
         HttpResponse httpResponse = this.getHttpClient().execute(httpRequest, httpContext);
         HttpClientSecuritySupport.checkTLSCredentialEvaluated(httpContext, httpRequest.getURI().getScheme());
         HttpClientResponseMessageDecoder decoder = pipeline.getDecoder();
         decoder.setHttpResponse(httpResponse);
         decoder.initialize();
         decoder.decode();
         operationContext.setInboundMessageContext(decoder.getMessageContext());
         if (pipeline.getInboundMessageHandler() != null) {
            pipeline.getInboundMessageHandler().invoke(operationContext.getInboundMessageContext());
         }
      } catch (SOAP11FaultDecodingException var19) {
         SOAPFaultException faultException = new SOAPFaultException(var19.getMessage(), var19);
         faultException.setFault(var19.getFault());
         throw faultException;
      } catch (SSLException var20) {
         throw new SecurityException("Problem establising TLS connection to: " + endpoint, var20);
      } catch (ComponentInitializationException var21) {
         throw new SOAPException("Problem initializing a SOAP client component", var21);
      } catch (MessageEncodingException var22) {
         throw new SOAPException("Problem encoding SOAP request message to: " + endpoint, var22);
      } catch (MessageDecodingException var23) {
         throw new SOAPException("Problem decoding SOAP response message from: " + endpoint, var23);
      } catch (MessageHandlerException var24) {
         throw new SOAPException("Problem handling SOAP message exchange with: " + endpoint, var24);
      } catch (ClientProtocolException var25) {
         throw new SOAPException("Client protocol problem sending SOAP request message to: " + endpoint, var25);
      } catch (IOException var26) {
         throw new SOAPException("I/O problem with SOAP message exchange with: " + endpoint, var26);
      } finally {
         if (pipeline != null) {
            pipeline.getEncoder().destroy();
            pipeline.getDecoder().destroy();
         }

      }

   }

   @Nonnull
   protected HttpClientMessagePipeline resolvePipeline(@Nonnull InOutOperationContext operationContext) throws SOAPException {
      try {
         return this.newPipeline();
      } catch (SOAPException var3) {
         this.log.warn("Problem resolving pipeline instance", var3);
         throw var3;
      } catch (Exception var4) {
         this.log.warn("Problem resolving pipeline instance", var4);
         throw new SOAPException("Could not resolve pipeline", var4);
      }
   }

   @Nonnull
   protected abstract HttpClientMessagePipeline newPipeline() throws SOAPException;

   /** @deprecated */
   @Deprecated
   protected void checkTLSCredentialTrusted(@Nonnull HttpClientContext context, @Nonnull HttpUriRequest request) throws SSLPeerUnverifiedException {
      HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, request.getURI().getScheme());
   }

   @Nonnull
   protected HttpUriRequest buildHttpRequest(@Nonnull @NotEmpty String endpoint, @Nonnull InOutOperationContext operationContext) {
      return new HttpPost(endpoint);
   }

   @Nonnull
   protected HttpClientContext buildHttpContext(@Nonnull HttpUriRequest request, @Nonnull InOutOperationContext operationContext) {
      HttpClientContext clientContext = this.resolveClientContext(operationContext);
      HttpClientSecurityParameters contextSecurityParameters = this.resolveContextSecurityParameters(operationContext);
      HttpClientSecuritySupport.marshalSecurityParameters(clientContext, contextSecurityParameters, false);
      HttpClientSecuritySupport.marshalSecurityParameters(clientContext, this.getHttpClientSecurityParameters(), false);
      if ("https".equalsIgnoreCase(request.getURI().getScheme()) && clientContext.getAttribute("opensaml.TrustEngine") != null && clientContext.getAttribute("opensaml.CriteriaSet") == null) {
         clientContext.setAttribute("opensaml.CriteriaSet", this.buildTLSCriteriaSet(request, operationContext));
      }

      HttpClientSecuritySupport.addDefaultTLSTrustEngineCriteria(clientContext, request);
      return clientContext;
   }

   protected HttpClientSecurityParameters resolveContextSecurityParameters(@Nonnull InOutOperationContext operationContext) {
      HttpClientSecurityContext securityContext = (HttpClientSecurityContext)operationContext.getOutboundMessageContext().getSubcontext(HttpClientSecurityContext.class);
      return securityContext != null ? securityContext.getSecurityParameters() : null;
   }

   @Nonnull
   protected HttpClientContext resolveClientContext(@Nonnull InOutOperationContext operationContext) {
      HttpClientRequestContext requestContext = (HttpClientRequestContext)operationContext.getOutboundMessageContext().getSubcontext(HttpClientRequestContext.class);
      return requestContext != null && requestContext.getHttpClientContext() != null ? requestContext.getHttpClientContext() : HttpClientContext.create();
   }

   @Nonnull
   protected CriteriaSet buildTLSCriteriaSet(@Nonnull HttpUriRequest request, @Nonnull InOutOperationContext operationContext) {
      CriteriaSet criteriaSet = new CriteriaSet();
      if (this.getTLSCriteriaSetStrategy() != null) {
         CriteriaSet resolved = (CriteriaSet)this.getTLSCriteriaSetStrategy().apply(operationContext);
         if (resolved != null) {
            criteriaSet.addAll(resolved);
         }
      }

      if (!criteriaSet.contains(UsageType.class)) {
         criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
      }

      return criteriaSet;
   }
}
