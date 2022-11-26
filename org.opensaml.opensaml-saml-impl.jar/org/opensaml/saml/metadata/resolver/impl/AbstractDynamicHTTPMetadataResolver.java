package org.opensaml.saml.metadata.resolver.impl;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Collections2;
import com.google.common.io.ByteStreams;
import com.google.common.net.MediaType;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLPeerUnverifiedException;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotLive;
import net.shibboleth.utilities.java.support.annotation.constraint.Unmodifiable;
import net.shibboleth.utilities.java.support.collection.LazySet;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.MediaTypeSupport;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSource;
import org.opensaml.security.httpclient.HttpClientSecurityParameters;
import org.opensaml.security.httpclient.HttpClientSecuritySupport;
import org.opensaml.security.trust.TrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public abstract class AbstractDynamicHTTPMetadataResolver extends AbstractDynamicMetadataResolver {
   public static final String[] DEFAULT_CONTENT_TYPES = new String[]{"application/samlmetadata+xml", "application/xml", "text/xml"};
   public static final String MDC_ATTRIB_CURRENT_REQUEST_URI = AbstractDynamicHTTPMetadataResolver.class.getName() + ".currentRequestURI";
   @Nonnull
   private final Logger log;
   @Nonnull
   private HttpClient httpClient;
   @NonnullAfterInit
   private List supportedContentTypes;
   @NonnullAfterInit
   private String supportedContentTypesValue;
   @NonnullAfterInit
   private Set supportedMediaTypes;
   @Nonnull
   private ResponseHandler responseHandler;
   /** @deprecated */
   @Nullable
   private CredentialsProvider credentialsProvider;
   /** @deprecated */
   @Nullable
   private TrustEngine tlsTrustEngine;
   @Nullable
   private HttpClientSecurityParameters httpClientSecurityParameters;

   public AbstractDynamicHTTPMetadataResolver(@Nonnull HttpClient client) {
      this((Timer)null, client);
   }

   public AbstractDynamicHTTPMetadataResolver(@Nullable Timer backgroundTaskTimer, @Nonnull HttpClient client) {
      super(backgroundTaskTimer);
      this.log = LoggerFactory.getLogger(AbstractDynamicHTTPMetadataResolver.class);
      this.httpClient = (HttpClient)Constraint.isNotNull(client, "HttpClient may not be null");
      this.responseHandler = new BasicMetadataResponseHandler();
   }

   /** @deprecated */
   public void setTLSTrustEngine(@Nullable TrustEngine engine) {
      this.tlsTrustEngine = engine;
   }

   /** @deprecated */
   public void setCredentialsProvider(@Nullable CredentialsProvider provider) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      this.credentialsProvider = provider;
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
            authScope = new AuthScope(AuthScope.ANY_HOST, -1);
         }

         BasicCredentialsProvider provider = new BasicCredentialsProvider();
         provider.setCredentials(authScope, credentials);
         this.credentialsProvider = provider;
      } else {
         this.log.debug("{} Either username or password were null, disabling basic auth", this.getLogPrefix());
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

   @NonnullAfterInit
   @NotLive
   @Unmodifiable
   protected Set getSupportedMediaTypes() {
      return this.supportedMediaTypes;
   }

   @NonnullAfterInit
   @NotLive
   @Unmodifiable
   public List getSupportedContentTypes() {
      return this.supportedContentTypes;
   }

   public void setSupportedContentTypes(@Nullable List types) {
      ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
      ComponentSupport.ifDestroyedThrowDestroyedComponentException(this);
      if (types == null) {
         this.supportedContentTypes = Collections.emptyList();
      } else {
         this.supportedContentTypes = new ArrayList(Collections2.transform(StringSupport.normalizeStringCollection(types), new Function() {
            @Nullable
            public String apply(@Nullable String input) {
               return input == null ? null : input.toLowerCase();
            }
         }));
      }

   }

   protected void initMetadataResolver() throws ComponentInitializationException {
      super.initMetadataResolver();
      if (this.getSupportedContentTypes() == null) {
         this.setSupportedContentTypes(Arrays.asList(DEFAULT_CONTENT_TYPES));
      }

      if (!this.getSupportedContentTypes().isEmpty()) {
         this.supportedContentTypesValue = StringSupport.listToStringValue(this.getSupportedContentTypes(), ", ");
         this.supportedMediaTypes = new LazySet();
         Iterator var1 = this.getSupportedContentTypes().iterator();

         while(var1.hasNext()) {
            String contentType = (String)var1.next();
            this.supportedMediaTypes.add(MediaType.parse(contentType));
         }
      } else {
         this.supportedMediaTypes = Collections.emptySet();
      }

      this.log.debug("{} Supported content types are: {}", this.getLogPrefix(), this.getSupportedContentTypes());
   }

   protected void doDestroy() {
      this.httpClient = null;
      this.credentialsProvider = null;
      this.tlsTrustEngine = null;
      this.httpClientSecurityParameters = null;
      this.supportedContentTypes = null;
      this.supportedContentTypesValue = null;
      this.supportedMediaTypes = null;
      super.doDestroy();
   }

   @Nullable
   protected XMLObject fetchFromOriginSource(@Nonnull CriteriaSet criteria) throws IOException {
      HttpUriRequest request = this.buildHttpRequest(criteria);
      if (request == null) {
         this.log.debug("{} Could not build request based on input criteria, unable to query", this.getLogPrefix());
         return null;
      } else {
         HttpClientContext context = this.buildHttpClientContext(request);

         XMLObject var5;
         try {
            MDC.put(MDC_ATTRIB_CURRENT_REQUEST_URI, request.getURI().toString());
            XMLObject result = (XMLObject)this.httpClient.execute(request, this.responseHandler, context);
            HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, request.getURI().getScheme());
            var5 = result;
         } finally {
            MDC.remove(MDC_ATTRIB_CURRENT_REQUEST_URI);
         }

         return var5;
      }
   }

   /** @deprecated */
   @Deprecated
   protected void checkTLSCredentialTrusted(HttpClientContext context, HttpUriRequest request) throws SSLPeerUnverifiedException {
      HttpClientSecuritySupport.checkTLSCredentialEvaluated(context, request.getURI().getScheme());
   }

   @Nullable
   protected HttpUriRequest buildHttpRequest(@Nonnull CriteriaSet criteria) {
      String url = this.buildRequestURL(criteria);
      this.log.debug("{} Built request URL of: {}", this.getLogPrefix(), url);
      if (url == null) {
         this.log.debug("{} Could not construct request URL from input criteria, unable to query", this.getLogPrefix());
         return null;
      } else {
         HttpGet getMethod = new HttpGet(url);
         if (!Strings.isNullOrEmpty(this.supportedContentTypesValue)) {
            getMethod.addHeader("Accept", this.supportedContentTypesValue);
         }

         return getMethod;
      }
   }

   @Nullable
   protected abstract String buildRequestURL(@Nonnull CriteriaSet var1);

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

   public class BasicMetadataResponseHandler implements ResponseHandler {
      public XMLObject handleResponse(@Nonnull HttpResponse response) throws IOException {
         int httpStatusCode = response.getStatusLine().getStatusCode();
         String currentRequestURI = MDC.get(AbstractDynamicHTTPMetadataResolver.MDC_ATTRIB_CURRENT_REQUEST_URI);
         if (httpStatusCode == 304) {
            AbstractDynamicHTTPMetadataResolver.this.log.debug("{} Metadata document from '{}' has not changed since last retrieval", AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), currentRequestURI);
            return null;
         } else if (httpStatusCode != 200) {
            AbstractDynamicHTTPMetadataResolver.this.log.warn("{} Non-ok status code '{}' returned from remote metadata source: {}", new Object[]{AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), httpStatusCode, currentRequestURI});
            return null;
         } else {
            try {
               this.validateHttpResponse(response);
            } catch (ResolverException var21) {
               AbstractDynamicHTTPMetadataResolver.this.log.error("{} Problem validating dynamic metadata HTTP response", AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), var21);
               return null;
            }

            try {
               InputStream ins = response.getEntity().getContent();
               byte[] source = ByteStreams.toByteArray(ins);
               ByteArrayInputStream bais = new ByteArrayInputStream(source);
               Throwable var7 = null;

               XMLObject var9;
               try {
                  XMLObject xmlObject = AbstractDynamicHTTPMetadataResolver.this.unmarshallMetadata(bais);
                  xmlObject.getObjectMetadata().put(new XMLObjectSource(source));
                  var9 = xmlObject;
               } catch (Throwable var20) {
                  var7 = var20;
                  throw var20;
               } finally {
                  if (bais != null) {
                     if (var7 != null) {
                        try {
                           bais.close();
                        } catch (Throwable var19) {
                           var7.addSuppressed(var19);
                        }
                     } else {
                        bais.close();
                     }
                  }

               }

               return var9;
            } catch (UnmarshallingException | IOException var23) {
               AbstractDynamicHTTPMetadataResolver.this.log.error("{} Error unmarshalling HTTP response stream", AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), var23);
               return null;
            }
         }
      }

      protected void validateHttpResponse(@Nonnull HttpResponse response) throws ResolverException {
         if (!AbstractDynamicHTTPMetadataResolver.this.getSupportedMediaTypes().isEmpty()) {
            String contentTypeValue = null;
            Header contentType = response.getEntity().getContentType();
            if (contentType != null && contentType.getValue() != null) {
               contentTypeValue = StringSupport.trimOrNull(contentType.getValue());
            }

            AbstractDynamicHTTPMetadataResolver.this.log.debug("{} Saw raw Content-Type from response header '{}'", AbstractDynamicHTTPMetadataResolver.this.getLogPrefix(), contentTypeValue);
            if (!MediaTypeSupport.validateContentType(contentTypeValue, AbstractDynamicHTTPMetadataResolver.this.getSupportedMediaTypes(), true, false)) {
               throw new ResolverException("HTTP response specified an unsupported Content-Type MIME type: " + contentTypeValue);
            }
         }

      }
   }
}
