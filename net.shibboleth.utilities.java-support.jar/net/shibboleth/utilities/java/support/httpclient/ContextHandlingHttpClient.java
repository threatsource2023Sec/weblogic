package net.shibboleth.utilities.java.support.httpclient;

import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpRequestWrapper;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ContextHandlingHttpClient extends CloseableHttpClient {
   private Logger log;
   @Nonnull
   private CloseableHttpClient httpClient;
   @Nonnull
   private List handlers;

   public ContextHandlingHttpClient(@Nonnull CloseableHttpClient client) {
      this(client, (List)null);
   }

   public ContextHandlingHttpClient(@Nonnull CloseableHttpClient client, @Nonnull List staticHandlers) {
      this.log = LoggerFactory.getLogger(ContextHandlingHttpClient.class);
      this.httpClient = (CloseableHttpClient)Constraint.isNotNull(client, "HttpClient was null");
      this.handlers = staticHandlers != null ? staticHandlers : Collections.emptyList();
   }

   public HttpParams getParams() {
      return this.httpClient.getParams();
   }

   public ClientConnectionManager getConnectionManager() {
      return this.httpClient.getConnectionManager();
   }

   public void close() throws IOException {
      this.httpClient.close();
   }

   protected CloseableHttpResponse doExecute(HttpHost target, HttpRequest request, HttpContext context) throws IOException, ClientProtocolException {
      Throwable error = null;
      HttpClientContext clientContext = HttpClientContext.adapt((HttpContext)(context != null ? context : new BasicHttpContext()));
      HttpUriRequest uriRequest = HttpUriRequest.class.isInstance(request) ? (HttpUriRequest)request : HttpRequestWrapper.wrap(request, target);

      CloseableHttpResponse var7;
      try {
         this.invokeBefore((HttpUriRequest)uriRequest, clientContext);
         var7 = this.httpClient.execute(target, request, clientContext);
      } catch (Throwable var11) {
         error = var11;
         throw var11;
      } finally {
         this.invokeAfter((HttpUriRequest)uriRequest, clientContext, error);
      }

      return var7;
   }

   private void invokeBefore(HttpUriRequest request, HttpClientContext context) throws IOException {
      this.log.trace("In invokeBefore");
      List errors = new LazyList();
      Iterator i$ = this.handlers.iterator();

      HttpClientContextHandler handler;
      while(i$.hasNext()) {
         handler = (HttpClientContextHandler)i$.next();

         try {
            if (handler != null) {
               this.log.trace("Invoking static handler invokeBefore: {}", handler.getClass().getName());
               handler.invokeBefore(context, request);
            }
         } catch (Throwable var8) {
            this.log.warn("Static handler invokeBefore threw: {}", handler.getClass().getName(), var8);
            errors.add(var8);
         }
      }

      i$ = HttpClientSupport.getDynamicContextHandlerList(context).iterator();

      while(i$.hasNext()) {
         handler = (HttpClientContextHandler)i$.next();

         try {
            if (handler != null) {
               this.log.trace("Invoking dynamic handler invokeBefore: {}", handler.getClass().getName());
               handler.invokeBefore(context, request);
            }
         } catch (Throwable var7) {
            this.log.warn("Dynamic handler invokeBefore threw: {}", handler.getClass().getName(), var7);
            errors.add(var7);
         }
      }

      IOException exception = this.processHandlerErrors("Invoke Before", errors);
      if (exception != null) {
         throw exception;
      }
   }

   private void invokeAfter(HttpUriRequest request, HttpClientContext context, Throwable priorError) throws IOException {
      this.log.trace("In invokeAfter");
      List errors = new LazyList();
      Iterator i$ = Lists.reverse(HttpClientSupport.getDynamicContextHandlerList(context)).iterator();

      HttpClientContextHandler handler;
      while(i$.hasNext()) {
         handler = (HttpClientContextHandler)i$.next();

         try {
            if (handler != null) {
               this.log.trace("Invoking dynamic handler invokeAfter: {}", handler.getClass().getName());
               handler.invokeAfter(context, request);
            }
         } catch (Throwable var9) {
            this.log.warn("Dynamic handler invokeAfter threw: {}", handler.getClass().getName(), var9);
            errors.add(var9);
         }
      }

      i$ = Lists.reverse(this.handlers).iterator();

      while(i$.hasNext()) {
         handler = (HttpClientContextHandler)i$.next();

         try {
            if (handler != null) {
               this.log.trace("Invoking static handler invokeAfter: {}", handler.getClass().getName());
               handler.invokeAfter(context, request);
            }
         } catch (Throwable var8) {
            this.log.warn("Static handler invokeAfter threw: {}", handler.getClass().getName(), var8);
            errors.add(var8);
         }
      }

      IOException exception = this.processHandlerErrors("Invoke After", errors);
      this.processErrorsForInvokeAfter(exception, priorError);
   }

   private IOException processHandlerErrors(String stage, List errors) {
      if (errors != null && !errors.isEmpty()) {
         if (errors.size() == 1) {
            Throwable t = (Throwable)errors.get(0);
            return IOException.class.isInstance(t) ? (IOException)IOException.class.cast(t) : new IOException(String.format("Context handler threw non-IOException Throwable in stage '%s'", stage), t);
         } else {
            IOException e = new IOException(String.format("Multiple context handlers in stage '%s' reported error, see suppressed list", stage));
            Iterator i$ = errors.iterator();

            while(i$.hasNext()) {
               Throwable t = (Throwable)i$.next();
               e.addSuppressed(t);
            }

            return e;
         }
      } else {
         return null;
      }
   }

   private void processErrorsForInvokeAfter(IOException invokeAfterException, Throwable priorError) throws IOException {
      if (priorError != null) {
         if (invokeAfterException != null) {
            priorError.addSuppressed(invokeAfterException);
         }

         if (IOException.class.isInstance(priorError)) {
            throw (IOException)IOException.class.cast(priorError);
         } else if (RuntimeException.class.isInstance(priorError)) {
            throw (RuntimeException)RuntimeException.class.cast(priorError);
         } else if (Error.class.isInstance(priorError)) {
            throw (Error)Error.class.cast(priorError);
         } else {
            throw new RuntimeException(priorError);
         }
      } else if (invokeAfterException != null) {
         throw invokeAfterException;
      }
   }
}
