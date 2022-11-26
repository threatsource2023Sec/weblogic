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
import javax.faces.FacesWrapper;
import javax.faces.lifecycle.ClientWindow;

public abstract class ExternalContextWrapper extends ExternalContext implements FacesWrapper {
   private ExternalContext wrapped;

   /** @deprecated */
   @Deprecated
   public ExternalContextWrapper() {
   }

   public ExternalContextWrapper(ExternalContext wrapped) {
      this.wrapped = wrapped;
   }

   public ExternalContext getWrapped() {
      return this.wrapped;
   }

   public void dispatch(String path) throws IOException {
      this.getWrapped().dispatch(path);
   }

   public String encodeActionURL(String url) {
      return this.getWrapped().encodeActionURL(url);
   }

   public String encodeNamespace(String name) {
      return this.getWrapped().encodeNamespace(name);
   }

   public String encodePartialActionURL(String url) {
      return this.getWrapped().encodePartialActionURL(url);
   }

   public String encodeResourceURL(String url) {
      return this.getWrapped().encodeResourceURL(url);
   }

   public String encodeWebsocketURL(String url) {
      return this.getWrapped().encodeWebsocketURL(url);
   }

   public Map getApplicationMap() {
      return this.getWrapped().getApplicationMap();
   }

   public String getApplicationContextPath() {
      return this.getWrapped().getApplicationContextPath();
   }

   public String getAuthType() {
      return this.getWrapped().getAuthType();
   }

   public Object getContext() {
      return this.getWrapped().getContext();
   }

   public String getInitParameter(String name) {
      return this.getWrapped().getInitParameter(name);
   }

   public Map getInitParameterMap() {
      return this.getWrapped().getInitParameterMap();
   }

   public String getRemoteUser() {
      return this.getWrapped().getRemoteUser();
   }

   public Object getRequest() {
      return this.getWrapped().getRequest();
   }

   public String getRequestContextPath() {
      return this.getWrapped().getRequestContextPath();
   }

   public Map getRequestCookieMap() {
      return this.getWrapped().getRequestCookieMap();
   }

   public Map getRequestHeaderMap() {
      return this.getWrapped().getRequestHeaderMap();
   }

   public Map getRequestHeaderValuesMap() {
      return this.getWrapped().getRequestHeaderValuesMap();
   }

   public Locale getRequestLocale() {
      return this.getWrapped().getRequestLocale();
   }

   public Iterator getRequestLocales() {
      return this.getWrapped().getRequestLocales();
   }

   public Map getRequestMap() {
      return this.getWrapped().getRequestMap();
   }

   public Map getRequestParameterMap() {
      return this.getWrapped().getRequestParameterMap();
   }

   public Iterator getRequestParameterNames() {
      return this.getWrapped().getRequestParameterNames();
   }

   public Map getRequestParameterValuesMap() {
      return this.getWrapped().getRequestParameterValuesMap();
   }

   public String getRequestPathInfo() {
      return this.getWrapped().getRequestPathInfo();
   }

   public String getRequestServletPath() {
      return this.getWrapped().getRequestServletPath();
   }

   public URL getResource(String path) throws MalformedURLException {
      return this.getWrapped().getResource(path);
   }

   public InputStream getResourceAsStream(String path) {
      return this.getWrapped().getResourceAsStream(path);
   }

   public Set getResourcePaths(String path) {
      return this.getWrapped().getResourcePaths(path);
   }

   public Object getResponse() {
      return this.getWrapped().getResponse();
   }

   public Object getSession(boolean create) {
      return this.getWrapped().getSession(create);
   }

   public String getSessionId(boolean create) {
      return this.getWrapped().getSessionId(create);
   }

   public Map getSessionMap() {
      return this.getWrapped().getSessionMap();
   }

   public int getSessionMaxInactiveInterval() {
      return this.getWrapped().getSessionMaxInactiveInterval();
   }

   public void setSessionMaxInactiveInterval(int interval) {
      this.getWrapped().setSessionMaxInactiveInterval(interval);
   }

   public void setClientWindow(ClientWindow window) {
      this.getWrapped().setClientWindow(window);
   }

   public Principal getUserPrincipal() {
      return this.getWrapped().getUserPrincipal();
   }

   public ClientWindow getClientWindow() {
      return this.getWrapped().getClientWindow();
   }

   public boolean isUserInRole(String role) {
      return this.getWrapped().isUserInRole(role);
   }

   public void log(String message) {
      this.getWrapped().log(message);
   }

   public void log(String message, Throwable exception) {
      this.getWrapped().log(message, exception);
   }

   public void redirect(String url) throws IOException {
      this.getWrapped().redirect(url);
   }

   public void addResponseCookie(String name, String value, Map properties) {
      this.getWrapped().addResponseCookie(name, value, properties);
   }

   public String getMimeType(String file) {
      return this.getWrapped().getMimeType(file);
   }

   public String getContextName() {
      return this.getWrapped().getContextName();
   }

   public void setRequest(Object request) {
      this.getWrapped().setRequest(request);
   }

   public String getRequestScheme() {
      return this.getWrapped().getRequestScheme();
   }

   public String getRequestServerName() {
      return this.getWrapped().getRequestServerName();
   }

   public int getRequestServerPort() {
      return this.getWrapped().getRequestServerPort();
   }

   public void setRequestCharacterEncoding(String encoding) throws UnsupportedEncodingException {
      this.getWrapped().setRequestCharacterEncoding(encoding);
   }

   public String getRealPath(String path) {
      return this.getWrapped().getRealPath(path);
   }

   public String getRequestCharacterEncoding() {
      return this.getWrapped().getRequestCharacterEncoding();
   }

   public String getRequestContentType() {
      return this.getWrapped().getRequestContentType();
   }

   public int getRequestContentLength() {
      return this.getWrapped().getRequestContentLength();
   }

   public String getResponseCharacterEncoding() {
      return this.getWrapped().getResponseCharacterEncoding();
   }

   public String getResponseContentType() {
      return this.getWrapped().getResponseContentType();
   }

   public void setResponse(Object response) {
      this.getWrapped().setResponse(response);
   }

   public OutputStream getResponseOutputStream() throws IOException {
      return this.getWrapped().getResponseOutputStream();
   }

   public Writer getResponseOutputWriter() throws IOException {
      return this.getWrapped().getResponseOutputWriter();
   }

   public void setResponseCharacterEncoding(String encoding) {
      this.getWrapped().setResponseCharacterEncoding(encoding);
   }

   public void setResponseContentType(String contentType) {
      this.getWrapped().setResponseContentType(contentType);
   }

   public void invalidateSession() {
      this.getWrapped().invalidateSession();
   }

   public void setResponseHeader(String name, String value) {
      this.getWrapped().setResponseHeader(name, value);
   }

   public void addResponseHeader(String name, String value) {
      this.getWrapped().addResponseHeader(name, value);
   }

   public void setResponseBufferSize(int size) {
      this.getWrapped().setResponseBufferSize(size);
   }

   public int getResponseBufferSize() {
      return this.getWrapped().getResponseBufferSize();
   }

   public boolean isResponseCommitted() {
      return this.getWrapped().isResponseCommitted();
   }

   public boolean isSecure() {
      return this.getWrapped().isSecure();
   }

   public void responseReset() {
      this.getWrapped().responseReset();
   }

   public void responseSendError(int statusCode, String message) throws IOException {
      this.getWrapped().responseSendError(statusCode, message);
   }

   public void setResponseStatus(int statusCode) {
      this.getWrapped().setResponseStatus(statusCode);
   }

   public void responseFlushBuffer() throws IOException {
      this.getWrapped().responseFlushBuffer();
   }

   public void setResponseContentLength(int length) {
      this.getWrapped().setResponseContentLength(length);
   }

   public String encodeBookmarkableURL(String baseUrl, Map parameters) {
      return this.getWrapped().encodeBookmarkableURL(baseUrl, parameters);
   }

   public String encodeRedirectURL(String baseUrl, Map parameters) {
      return this.getWrapped().encodeRedirectURL(baseUrl, parameters);
   }

   public Flash getFlash() {
      return this.getWrapped().getFlash();
   }
}
