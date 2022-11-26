package org.opensaml.saml.metadata.resolver.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.util.EntityUtils;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.opensaml.security.trust.TrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPMetadataResolver extends AbstractReloadingMetadataResolver {
   private final Logger log;
   private HttpClient httpClient;
   private URI metadataURI;
   private String cachedMetadataETag;
   private String cachedMetadataLastModified;
   /** @deprecated */
   @Nullable
   private BasicCredentialsProvider credentialsProvider;
   /** @deprecated */
   @Nullable
   private TrustEngine tlsTrustEngine;
   @Nullable
   private HttpClientSecurityParameters httpClientSecurityParameters;

   public HTTPMetadataResolver(HttpClient client, String metadataURL) throws ResolverException {
      this((Timer)null, client, metadataURL);
   }

   public HTTPMetadataResolver(Timer backgroundTaskTimer, HttpClient client, String metadataURL) throws ResolverException {
      super(backgroundTaskTimer);
      this.log = LoggerFactory.getLogger(HTTPMetadataResolver.class);
      if (client == null) {
         throw new ResolverException("HTTP client may not be null");
      } else {
         this.httpClient = client;

         try {
            this.metadataURI = new URI(metadataURL);
         } catch (URISyntaxException var5) {
            throw new ResolverException("Illegal URL syntax", var5);
         }
      }
   }

   public String getMetadataURI() {
      return this.metadataURI.toASCIIString();
   }

   /** @deprecated */
   public void setTLSTrustEngine(@Nullable TrustEngine engine) {
      this.tlsTrustEngine = engine;
   }

   /** @deprecated */
   public void setBasicCredentials(@Nullable UsernamePasswordCredentials credentials) {
      this.setBasicCredentialsWithScope(credentials, (AuthScope)null);
   }

   /** @deprecated */
   public void setBasicCredentialsWithScope(@Nullable UsernamePasswordCredentials credentials, @Nullable AuthScope scope) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (credentials != null) {
         AuthScope authScope = scope;
         if (scope == null) {
            authScope = new AuthScope(this.metadataURI.getHost(), this.metadataURI.getPort());
         }

         BasicCredentialsProvider provider = new BasicCredentialsProvider();
         provider.setCredentials(authScope, credentials);
         this.credentialsProvider = provider;
      } else {
         this.log.debug("Either username or password were null, disabling basic auth");
         this.credentialsProvider = null;
      }

   }

   @Nullable
   protected HttpClientSecurityParameters getHttpClientSecurityParameters() {
      return this.httpClientSecurityParameters;
   }

   public void setHttpClientSecurityParameters(@Nullable HttpClientSecurityParameters params) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.httpClientSecurityParameters = params;
   }

   protected void doDestroy() {
      this.httpClient = null;
      this.tlsTrustEngine = null;
      this.credentialsProvider = null;
      this.httpClientSecurityParameters = null;
      this.metadataURI = null;
      this.cachedMetadataETag = null;
      this.cachedMetadataLastModified = null;
      super.doDestroy();
   }

   protected String getMetadataIdentifier() {
      return this.metadataURI.toString();
   }

   protected byte[] fetchMetadata() throws ResolverException {
      HttpGet httpGet = this.buildHttpGet();
      HttpClientContext context = this.buildHttpClientContext(httpGet);
      HttpResponse response = null;

      String errMsg;
      try {
         this.log.debug("{} Attempting to fetch metadata document from '{}'", this.getLogPrefix(), this.metadataURI);
         response = this.httpClient.execute(httpGet, context);
         HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, this.metadataURI.getScheme());
         int httpStatusCode = response.getStatusLine().getStatusCode();
         if (httpStatusCode != 304) {
            if (httpStatusCode != 200) {
               errMsg = "Non-ok status code " + httpStatusCode + " returned from remote metadata source " + this.metadataURI;
               this.log.error("{} " + errMsg, this.getLogPrefix());
               throw new ResolverException(errMsg);
            }

            this.processConditionalRetrievalHeaders(response);
            byte[] rawMetadata = this.getMetadataBytesFromResponse(response);
            this.log.debug("{} Successfully fetched {} bytes of metadata from {}", new Object[]{this.getLogPrefix(), rawMetadata.length, this.getMetadataURI()});
            byte[] var6 = rawMetadata;
            return var6;
         }

         this.log.debug("{} Metadata document from '{}' has not changed since last retrieval", this.getLogPrefix(), this.getMetadataURI());
         errMsg = null;
      } catch (IOException var16) {
         errMsg = "Error retrieving metadata from " + this.metadataURI;
         this.log.error("{} " + errMsg, this.getLogPrefix(), var16);
         throw new ResolverException(errMsg, var16);
      } finally {
         try {
            if (response != null && response instanceof CloseableHttpResponse) {
               ((CloseableHttpResponse)response).close();
            }
         } catch (IOException var15) {
            this.log.error("{} Error closing HTTP response from {}", new Object[]{this.metadataURI, this.getLogPrefix(), var15});
         }

      }

      return (byte[])errMsg;
   }

   /** @deprecated */
   @Deprecated
   protected void checkTLSCredentialTrusted(HttpClientContext context) throws SSLPeerUnverifiedException {
      HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, this.metadataURI.getScheme());
   }

   protected HttpGet buildHttpGet() {
      HttpGet getMethod = new HttpGet(this.getMetadataURI());
      if (this.cachedMetadataETag != null) {
         getMethod.setHeader("If-None-Match", this.cachedMetadataETag);
      }

      if (this.cachedMetadataLastModified != null) {
         getMethod.setHeader("If-Modified-Since", this.cachedMetadataLastModified);
      }

      return getMethod;
   }

   /** @deprecated */
   protected HttpClientContext buildHttpClientContext() {
      return this.buildHttpClientContext((HttpUriRequest)null);
   }

   protected HttpClientContext buildHttpClientContext(@Nullable HttpUriRequest request) {
      HttpClientContext context = HttpClientContext.create();
      HttpClientSecuritySupport.marshalSecurityParameters(context, this.httpClientSecurityParameters, true);
      if (this.credentialsProvider != null) {
         context.setCredentialsProvider(this.credentialsProvider);
      }

      if (this.tlsTrustEngine != null) {
         context.setAttribute("opensaml.TrustEngine", this.tlsTrustEngine);
      }

      if (request != null) {
         HttpClientSecuritySupport.addDefaultTLSTrustEngineCriteria(context, request);
      }

      return context;
   }

   protected void processConditionalRetrievalHeaders(HttpResponse response) {
      Header httpHeader = response.getFirstHeader("ETag");
      if (httpHeader != null) {
         this.cachedMetadataETag = httpHeader.getValue();
      }

      httpHeader = response.getFirstHeader("Last-Modified");
      if (httpHeader != null) {
         this.cachedMetadataLastModified = httpHeader.getValue();
      }

   }

   protected byte[] getMetadataBytesFromResponse(HttpResponse response) throws ResolverException {
      this.log.debug("{} Attempting to extract metadata from response to request for metadata from '{}'", this.getLogPrefix(), this.getMetadataURI());

      byte[] var3;
      try {
         InputStream ins = response.getEntity().getContent();
         var3 = this.inputstreamToByteArray(ins);
      } catch (IOException var7) {
         this.log.error("{} Unable to read response", this.getLogPrefix(), var7);
         throw new ResolverException("Unable to read response", var7);
      } finally {
         EntityUtils.consumeQuietly(response.getEntity());
      }

      return var3;
   }
}
