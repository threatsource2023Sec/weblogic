package javax.faces.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public abstract class ExternalContext {
   public static final String BASIC_AUTH = "BASIC";
   public static final String CLIENT_CERT_AUTH = "CLIENT_CERT";
   public static final String DIGEST_AUTH = "DIGEST";
   public static final String FORM_AUTH = "FORM";

   public abstract void dispatch(String var1) throws IOException;

   public abstract String encodeActionURL(String var1);

   public abstract String encodeNamespace(String var1);

   public abstract String encodeResourceURL(String var1);

   public abstract Map getApplicationMap();

   public abstract String getAuthType();

   public abstract Object getContext();

   public abstract String getInitParameter(String var1);

   public abstract Map getInitParameterMap();

   public abstract String getRemoteUser();

   public abstract Object getRequest();

   public void setRequest(Object request) {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         impl.setRequest(request);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setRequestCharacterEncoding(String encoding) throws UnsupportedEncodingException {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         impl.setRequestCharacterEncoding(encoding);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract String getRequestContextPath();

   public abstract Map getRequestCookieMap();

   public abstract Map getRequestHeaderMap();

   public abstract Map getRequestHeaderValuesMap();

   public abstract Locale getRequestLocale();

   public abstract Iterator getRequestLocales();

   public abstract Map getRequestMap();

   public abstract Map getRequestParameterMap();

   public abstract Iterator getRequestParameterNames();

   public abstract Map getRequestParameterValuesMap();

   public abstract String getRequestPathInfo();

   public abstract String getRequestServletPath();

   public String getRequestCharacterEncoding() {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         return impl.getRequestCharacterEncoding();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getRequestContentType() {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         return impl.getRequestContentType();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getResponseCharacterEncoding() {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         return impl.getResponseCharacterEncoding();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getResponseContentType() {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         return impl.getResponseContentType();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract URL getResource(String var1) throws MalformedURLException;

   public abstract InputStream getResourceAsStream(String var1);

   public abstract Set getResourcePaths(String var1);

   public abstract Object getResponse();

   public void setResponse(Object response) {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         impl.setResponse(response);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setResponseCharacterEncoding(String encoding) {
      ExternalContext impl = this.getDefaultExternalContext();
      if (impl != null) {
         impl.setResponseCharacterEncoding(encoding);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract Object getSession(boolean var1);

   public abstract Map getSessionMap();

   public abstract Principal getUserPrincipal();

   public abstract boolean isUserInRole(String var1);

   public abstract void log(String var1);

   public abstract void log(String var1, Throwable var2);

   public abstract void redirect(String var1) throws IOException;

   private ExternalContext getDefaultExternalContext() {
      ExternalContext extCtx = null;
      Map m = (Map)this.getRequestMap().get("com.sun.faces.util.RequestStateManager");
      if (m != null) {
         extCtx = (ExternalContext)m.get("com.sun.faces.ExternalContextImpl");
      }

      return extCtx;
   }
}
