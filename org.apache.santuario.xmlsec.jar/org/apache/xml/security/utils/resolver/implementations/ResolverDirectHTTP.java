package org.apache.xml.security.utils.resolver.implementations;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.Proxy.Type;
import java.nio.charset.StandardCharsets;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.apache.xml.security.utils.XMLUtils;
import org.apache.xml.security.utils.resolver.ResourceResolverContext;
import org.apache.xml.security.utils.resolver.ResourceResolverException;
import org.apache.xml.security.utils.resolver.ResourceResolverSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolverDirectHTTP extends ResourceResolverSpi {
   private static final Logger LOG = LoggerFactory.getLogger(ResolverDirectHTTP.class);
   private static final String[] properties = new String[]{"http.proxy.host", "http.proxy.port", "http.proxy.username", "http.proxy.password", "http.basic.username", "http.basic.password"};
   private static final int HttpProxyHost = 0;
   private static final int HttpProxyPort = 1;
   private static final int HttpProxyUser = 2;
   private static final int HttpProxyPass = 3;
   private static final int HttpBasicUser = 4;
   private static final int HttpBasicPass = 5;

   public boolean engineIsThreadSafe() {
      return true;
   }

   public XMLSignatureInput engineResolveURI(ResourceResolverContext context) throws ResourceResolverException {
      try {
         URI uriNew = getNewURI(context.uriToResolve, context.baseUri);
         URL url = uriNew.toURL();
         URLConnection urlConnection = this.openConnection(url);
         String auth = urlConnection.getHeaderField("WWW-Authenticate");
         String mimeType;
         if (auth != null && auth.startsWith("Basic")) {
            mimeType = this.engineGetProperty(properties[4]);
            String pass = this.engineGetProperty(properties[5]);
            if (mimeType != null && pass != null) {
               urlConnection = this.openConnection(url);
               String password = mimeType + ":" + pass;
               String encodedPassword = XMLUtils.encodeToString(password.getBytes(StandardCharsets.ISO_8859_1));
               urlConnection.setRequestProperty("Authorization", "Basic " + encodedPassword);
            }
         }

         mimeType = urlConnection.getHeaderField("Content-Type");
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         Throwable var43 = null;

         try {
            InputStream inputStream = urlConnection.getInputStream();
            Throwable var10 = null;

            try {
               byte[] buf = new byte[4096];
               int read = false;

               int summarized;
               int read;
               for(summarized = 0; (read = inputStream.read(buf)) >= 0; summarized += read) {
                  baos.write(buf, 0, read);
               }

               LOG.debug("Fetched {} bytes from URI {}", summarized, uriNew.toString());
               XMLSignatureInput result = new XMLSignatureInput(baos.toByteArray());
               result.setSecureValidation(context.secureValidation);
               result.setSourceURI(uriNew.toString());
               result.setMIMEType(mimeType);
               XMLSignatureInput var15 = result;
               return var15;
            } catch (Throwable var34) {
               var10 = var34;
               throw var34;
            } finally {
               if (inputStream != null) {
                  $closeResource(var10, inputStream);
               }

            }
         } catch (Throwable var36) {
            var43 = var36;
            throw var36;
         } finally {
            $closeResource(var43, baos);
         }
      } catch (URISyntaxException var38) {
         throw new ResourceResolverException(var38, context.uriToResolve, context.baseUri, "generic.EmptyMessage");
      } catch (MalformedURLException var39) {
         throw new ResourceResolverException(var39, context.uriToResolve, context.baseUri, "generic.EmptyMessage");
      } catch (IOException var40) {
         throw new ResourceResolverException(var40, context.uriToResolve, context.baseUri, "generic.EmptyMessage");
      } catch (IllegalArgumentException var41) {
         throw new ResourceResolverException(var41, context.uriToResolve, context.baseUri, "generic.EmptyMessage");
      }
   }

   private URLConnection openConnection(URL url) throws IOException {
      String proxyHostProp = this.engineGetProperty(properties[0]);
      String proxyPortProp = this.engineGetProperty(properties[1]);
      String proxyUser = this.engineGetProperty(properties[2]);
      String proxyPass = this.engineGetProperty(properties[3]);
      Proxy proxy = null;
      if (proxyHostProp != null && proxyPortProp != null) {
         int port = Integer.parseInt(proxyPortProp);
         proxy = new Proxy(Type.HTTP, new InetSocketAddress(proxyHostProp, port));
      }

      URLConnection urlConnection;
      if (proxy != null) {
         urlConnection = url.openConnection(proxy);
         if (proxyUser != null && proxyPass != null) {
            String password = proxyUser + ":" + proxyPass;
            String authString = "Basic " + XMLUtils.encodeToString(password.getBytes(StandardCharsets.ISO_8859_1));
            urlConnection.setRequestProperty("Proxy-Authorization", authString);
         }
      } else {
         urlConnection = url.openConnection();
      }

      return urlConnection;
   }

   public boolean engineCanResolveURI(ResourceResolverContext context) {
      if (context.uriToResolve == null) {
         LOG.debug("quick fail, uri == null");
         return false;
      } else if (!context.uriToResolve.equals("") && context.uriToResolve.charAt(0) != '#') {
         LOG.debug("I was asked whether I can resolve {}", context.uriToResolve);
         if (!context.uriToResolve.startsWith("http:") && (context.baseUri == null || !context.baseUri.startsWith("http:"))) {
            LOG.debug("I state that I can't resolve {}", context.uriToResolve);
            return false;
         } else {
            LOG.debug("I state that I can resolve {}", context.uriToResolve);
            return true;
         }
      } else {
         LOG.debug("quick fail for empty URIs and local ones");
         return false;
      }
   }

   public String[] engineGetPropertyKeys() {
      return (String[])properties.clone();
   }

   private static URI getNewURI(String uri, String baseURI) throws URISyntaxException {
      URI newUri = null;
      if (baseURI != null && !"".equals(baseURI)) {
         newUri = (new URI(baseURI)).resolve(uri);
      } else {
         newUri = new URI(uri);
      }

      if (newUri.getFragment() != null) {
         URI uriNewNoFrag = new URI(newUri.getScheme(), newUri.getSchemeSpecificPart(), (String)null);
         return uriNewNoFrag;
      } else {
         return newUri;
      }
   }

   // $FF: synthetic method
   private static void $closeResource(Throwable x0, AutoCloseable x1) {
      if (x0 != null) {
         try {
            x1.close();
         } catch (Throwable var3) {
            x0.addSuppressed(var3);
         }
      } else {
         x1.close();
      }

   }
}
