package org.apache.xml.security.stax.impl.resourceResolvers;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.regex.Pattern;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.ResourceResolver;
import org.apache.xml.security.stax.ext.ResourceResolverLookup;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;

public class ResolverHttp implements ResourceResolver, ResourceResolverLookup {
   private static Proxy proxy;
   private String uri;
   private String baseURI;
   private Pattern pattern = Pattern.compile("^http[s]?://.*");

   public ResolverHttp() {
   }

   public ResolverHttp(String uri, String baseURI) {
      this.uri = uri;
      this.baseURI = baseURI;
   }

   public static void setProxy(Proxy proxy) {
      ResolverHttp.proxy = proxy;
   }

   public ResourceResolverLookup canResolve(String uri, String baseURI) {
      if (uri == null) {
         return null;
      } else {
         return !this.pattern.matcher(uri).matches() && (baseURI == null || !this.pattern.matcher(baseURI).matches()) ? null : this;
      }
   }

   public ResourceResolver newInstance(String uri, String baseURI) {
      return new ResolverHttp(uri, baseURI);
   }

   public boolean isSameDocumentReference() {
      return false;
   }

   public boolean matches(XMLSecStartElement xmlSecStartElement) {
      return false;
   }

   public InputStream getInputStreamFromExternalReference() throws XMLSecurityException {
      try {
         URI tmp;
         if (this.baseURI != null && !"".equals(this.baseURI)) {
            tmp = (new URI(this.baseURI)).resolve(this.uri);
         } else {
            tmp = new URI(this.uri);
         }

         if (tmp.getFragment() != null) {
            tmp = new URI(tmp.getScheme(), tmp.getSchemeSpecificPart(), (String)null);
         }

         URL url = tmp.toURL();
         HttpURLConnection urlConnection;
         if (proxy != null) {
            urlConnection = (HttpURLConnection)url.openConnection(proxy);
         } else {
            urlConnection = (HttpURLConnection)url.openConnection();
         }

         return urlConnection.getInputStream();
      } catch (MalformedURLException var4) {
         throw new XMLSecurityException(var4);
      } catch (IOException var5) {
         throw new XMLSecurityException(var5);
      } catch (URISyntaxException var6) {
         throw new XMLSecurityException(var6);
      }
   }
}
