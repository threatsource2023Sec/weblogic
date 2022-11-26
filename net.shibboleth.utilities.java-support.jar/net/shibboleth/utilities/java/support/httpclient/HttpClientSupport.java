package net.shibboleth.utilities.java.support.httpclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.DeprecationSupport;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.StrictHostnameVerifier;
import org.apache.http.entity.ContentType;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.CharArrayBuffer;

public final class HttpClientSupport {
   private static final String CONTEXT_KEY_DYNAMIC_CONTEXT_HANDLERS = "java-support.DynamicContextHandlers";

   private HttpClientSupport() {
   }

   @Nonnull
   public static LayeredConnectionSocketFactory buildStrictTLSSocketFactory() {
      return (new TLSSocketFactoryBuilder()).setHostnameVerifier(new StrictHostnameVerifier()).build();
   }

   @Nonnull
   public static LayeredConnectionSocketFactory buildNoTrustTLSSocketFactory() {
      return (new TLSSocketFactoryBuilder()).setTrustManagers(Collections.singletonList(buildNoTrustX509TrustManager())).setHostnameVerifier(new AllowAllHostnameVerifier()).build();
   }

   /** @deprecated */
   @Deprecated
   @Nonnull
   public static SSLConnectionSocketFactory buildStrictSSLConnectionSocketFactory() {
      DeprecationSupport.warnOnce(DeprecationSupport.ObjectType.METHOD, "net.shibboleth.utilities.java.support.httpclient.HttpClientSupport.buildStrictSSLConnectionSocketFactory", (String)null, "buildStrictTLSSocketFactory");
      return new SSLConnectionSocketFactory(SSLContexts.createDefault(), SSLConnectionSocketFactory.STRICT_HOSTNAME_VERIFIER);
   }

   /** @deprecated */
   @Deprecated
   @Nonnull
   public static SSLConnectionSocketFactory buildNoTrustSSLConnectionSocketFactory() {
      DeprecationSupport.warnOnce(DeprecationSupport.ObjectType.METHOD, "net.shibboleth.utilities.java.support.httpclient.HttpClientSupport.buildNoTrustSSLConnectionSocketFactory", (String)null, "buildNoTrustTLSSocketFactory");
      X509TrustManager noTrustManager = buildNoTrustX509TrustManager();

      try {
         SSLContext sslcontext = SSLContext.getInstance("TLS");
         sslcontext.init((KeyManager[])null, new TrustManager[]{noTrustManager}, (SecureRandom)null);
         return new SSLConnectionSocketFactory(sslcontext, SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
      } catch (NoSuchAlgorithmException var2) {
         throw new RuntimeException("TLS SSLContext type is required to be supported by the JVM but is not", var2);
      } catch (KeyManagementException var3) {
         throw new RuntimeException("Somehow the trust everything trust manager didn't trust everything", var3);
      }
   }

   @Nonnull
   public static X509TrustManager buildNoTrustX509TrustManager() {
      return new X509TrustManager() {
         public X509Certificate[] getAcceptedIssuers() {
            return null;
         }

         public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
         }

         public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
         }
      };
   }

   @Nonnull
   public static List getDynamicContextHandlerList(@Nonnull HttpClientContext context) {
      Constraint.isNotNull(context, "HttpClientContext was null");
      List handlers = (List)context.getAttribute("java-support.DynamicContextHandlers", List.class);
      if (handlers == null) {
         handlers = new ArrayList();
         context.setAttribute("java-support.DynamicContextHandlers", handlers);
      }

      return (List)handlers;
   }

   public static void addDynamicContextHandlerFirst(@Nonnull HttpClientContext context, @Nonnull HttpClientContextHandler handler) {
      Constraint.isNotNull(handler, "HttpClientContextHandler was null");
      getDynamicContextHandlerList(context).add(0, handler);
   }

   public static void addDynamicContextHandlerLast(@Nonnull HttpClientContext context, @Nonnull HttpClientContextHandler handler) {
      Constraint.isNotNull(handler, "HttpClientContextHandler was null");
      getDynamicContextHandlerList(context).add(handler);
   }

   @Nullable
   public static String toString(@Nonnull HttpEntity entity, @Nullable Charset defaultCharset, int maxLength) throws IOException, ParseException {
      InputStream instream = entity.getContent();
      Throwable var4 = null;

      try {
         if (instream == null) {
            Object var44 = null;
            return (String)var44;
         } else if (entity.getContentLength() > (long)maxLength || entity.getContentLength() > 2147483647L) {
            throw new IOException("HTTP entity size exceeded limit");
         } else {
            int i = (int)entity.getContentLength();
            if (i < 0) {
               i = 4096;
            }

            Charset charset = null;

            try {
               ContentType contentType = ContentType.get(entity);
               if (contentType != null) {
                  charset = contentType.getCharset();
               }
            } catch (UnsupportedCharsetException var41) {
               throw new UnsupportedEncodingException(var41.getMessage());
            }

            if (charset == null) {
               charset = defaultCharset;
            }

            if (charset == null) {
               charset = HTTP.DEF_CONTENT_CHARSET;
            }

            Reader reader = new InputStreamReader(instream, charset);
            Throwable var8 = null;

            try {
               CharArrayBuffer buffer = new CharArrayBuffer(i);
               char[] tmp = new char[1024];
               int size = 0;

               int l;
               while((l = reader.read(tmp)) != -1) {
                  size += l;
                  if (size > maxLength) {
                     throw new IOException("HTTP entity size exceeded limit");
                  }

                  buffer.append(tmp, 0, l);
               }

               String var13 = buffer.toString();
               return var13;
            } catch (Throwable var39) {
               var8 = var39;
               throw var39;
            } finally {
               if (reader != null) {
                  if (var8 != null) {
                     try {
                        reader.close();
                     } catch (Throwable var38) {
                        var8.addSuppressed(var38);
                     }
                  } else {
                     reader.close();
                  }
               }

            }
         }
      } catch (Throwable var42) {
         var4 = var42;
         throw var42;
      } finally {
         if (instream != null) {
            if (var4 != null) {
               try {
                  instream.close();
               } catch (Throwable var37) {
                  var4.addSuppressed(var37);
               }
            } else {
               instream.close();
            }
         }

      }
   }

   @Nullable
   public static String toString(@Nonnull HttpEntity entity, @Nullable String defaultCharset, int maxLength) throws IOException, ParseException {
      return toString(entity, defaultCharset != null ? Charset.forName(defaultCharset) : null, maxLength);
   }

   @Nullable
   public static String toString(@Nonnull HttpEntity entity, int maxLength) throws IOException, ParseException {
      return toString(entity, (Charset)null, maxLength);
   }
}
