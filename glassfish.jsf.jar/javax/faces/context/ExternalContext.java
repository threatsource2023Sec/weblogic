package javax.faces.context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.faces.lifecycle.ClientWindow;

public abstract class ExternalContext {
   private ExternalContext defaultExternalContext;
   public static final String BASIC_AUTH = "BASIC";
   public static final String CLIENT_CERT_AUTH = "CLIENT_CERT";
   public static final String DIGEST_AUTH = "DIGEST";
   public static final String FORM_AUTH = "FORM";

   public void addResponseCookie(String name, String value, Map properties) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.addResponseCookie(name, value, properties);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract void dispatch(String var1) throws IOException;

   public abstract String encodeActionURL(String var1);

   public abstract String encodeNamespace(String var1);

   public abstract String encodeResourceURL(String var1);

   public abstract String encodeWebsocketURL(String var1);

   public abstract Map getApplicationMap();

   public abstract String getAuthType();

   public Flash getFlash() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getFlash();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getMimeType(String file) {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getMimeType(file);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract Object getContext();

   public String getContextName() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getContextName();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getApplicationContextPath() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getApplicationContextPath();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract String getInitParameter(String var1);

   public abstract Map getInitParameterMap();

   public abstract String getRemoteUser();

   public abstract Object getRequest();

   public void setRequest(Object request) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setRequest(request);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getRequestScheme() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getRequestScheme();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getRequestServerName() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getRequestServerName();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public int getRequestServerPort() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getRequestServerPort();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setRequestCharacterEncoding(String encoding) throws UnsupportedEncodingException {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setRequestCharacterEncoding(encoding);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getRealPath(String path) {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getRealPath(path);
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
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getRequestCharacterEncoding();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getRequestContentType() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getRequestContentType();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public int getRequestContentLength() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getRequestContentLength();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getResponseCharacterEncoding() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getResponseCharacterEncoding();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String getResponseContentType() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getResponseContentType();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract URL getResource(String var1) throws MalformedURLException;

   public abstract InputStream getResourceAsStream(String var1);

   public abstract Set getResourcePaths(String var1);

   public abstract Object getResponse();

   public void setResponse(Object response) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setResponse(response);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public OutputStream getResponseOutputStream() throws IOException {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getResponseOutputStream();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public Writer getResponseOutputWriter() throws IOException {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getResponseOutputWriter();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setResponseCharacterEncoding(String encoding) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setResponseCharacterEncoding(encoding);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setResponseContentType(String contentType) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setResponseContentType(contentType);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract Object getSession(boolean var1);

   public String getSessionId(boolean create) {
      String result = "";
      if (this.defaultExternalContext != null) {
         result = this.defaultExternalContext.getSessionId(create);
         return result;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public int getSessionMaxInactiveInterval() {
      int result = false;
      if (this.defaultExternalContext != null) {
         int result = this.defaultExternalContext.getSessionMaxInactiveInterval();
         return result;
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract Map getSessionMap();

   public abstract Principal getUserPrincipal();

   public ClientWindow getClientWindow() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getClientWindow();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void invalidateSession() {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.invalidateSession();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public abstract boolean isUserInRole(String var1);

   public abstract void log(String var1);

   public abstract void log(String var1, Throwable var2);

   public abstract void redirect(String var1) throws IOException;

   public void setResponseHeader(String name, String value) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setResponseHeader(name, value);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void addResponseHeader(String name, String value) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.addResponseHeader(name, value);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setResponseBufferSize(int size) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setResponseBufferSize(size);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public int getResponseBufferSize() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.getResponseBufferSize();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public boolean isResponseCommitted() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.isResponseCommitted();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void responseReset() {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.responseReset();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void responseSendError(int statusCode, String message) throws IOException {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.responseSendError(statusCode, message);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setResponseStatus(int statusCode) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setResponseStatus(statusCode);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setSessionMaxInactiveInterval(int interval) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setSessionMaxInactiveInterval(interval);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setClientWindow(ClientWindow window) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setClientWindow(window);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void responseFlushBuffer() throws IOException {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.responseFlushBuffer();
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public void setResponseContentLength(int length) {
      if (this.defaultExternalContext != null) {
         this.defaultExternalContext.setResponseContentLength(length);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String encodeBookmarkableURL(String baseUrl, Map parameters) {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.encodeBookmarkableURL(baseUrl, parameters);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String encodeRedirectURL(String baseUrl, Map parameters) {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.encodeRedirectURL(baseUrl, parameters);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public String encodePartialActionURL(String url) {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.encodePartialActionURL(url);
      } else {
         throw new UnsupportedOperationException();
      }
   }

   public boolean isSecure() {
      if (this.defaultExternalContext != null) {
         return this.defaultExternalContext.isSecure();
      } else {
         throw new UnsupportedOperationException();
      }
   }
}
