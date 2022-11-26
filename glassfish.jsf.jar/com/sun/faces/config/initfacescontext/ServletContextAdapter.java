package com.sun.faces.config.initfacescontext;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.context.ApplicationMap;
import com.sun.faces.context.InitParameterMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Principal;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import javax.faces.context.ExternalContext;
import javax.faces.context.Flash;
import javax.servlet.ServletContext;

public class ServletContextAdapter extends ExternalContext {
   private ServletContext servletContext;
   private ApplicationMap applicationMap;
   private InitParameterMap initMap;
   private boolean isEnableTransitionTimeNoOpFlash;

   public ServletContextAdapter(ServletContext servletContext) {
      this.isEnableTransitionTimeNoOpFlash = WebConfiguration.BooleanWebContextInitParameter.EnableTransitionTimeNoOpFlash.getDefaultValue();
      this.servletContext = servletContext;
      String paramValue = servletContext.getInitParameter(WebConfiguration.BooleanWebContextInitParameter.EnableTransitionTimeNoOpFlash.getQualifiedName());
      if (paramValue != null) {
         this.isEnableTransitionTimeNoOpFlash = Boolean.parseBoolean(paramValue);
      }

   }

   public void dispatch(String path) throws IOException {
   }

   public void release() {
      this.servletContext = null;
      this.applicationMap = null;
      this.initMap = null;
   }

   public String encodeActionURL(String url) {
      return null;
   }

   public String encodeNamespace(String name) {
      return null;
   }

   public String encodeResourceURL(String url) {
      return null;
   }

   public String encodeWebsocketURL(String url) {
      return null;
   }

   public Map getApplicationMap() {
      if (this.applicationMap == null) {
         this.applicationMap = new ApplicationMap(this.servletContext);
      }

      return this.applicationMap;
   }

   public Flash getFlash() {
      return (Flash)(this.isEnableTransitionTimeNoOpFlash ? new NoOpFlash() : super.getFlash());
   }

   public String getApplicationContextPath() {
      return this.servletContext.getContextPath();
   }

   public String getAuthType() {
      return null;
   }

   public String getMimeType(String file) {
      return this.servletContext.getMimeType(file);
   }

   public Object getContext() {
      return this.servletContext;
   }

   public String getContextName() {
      return this.servletContext.getServletContextName();
   }

   public String getInitParameter(String name) {
      return this.servletContext.getInitParameter(name);
   }

   public Map getInitParameterMap() {
      if (this.initMap == null) {
         this.initMap = new InitParameterMap(this.servletContext);
      }

      return this.initMap;
   }

   public String getRemoteUser() {
      return null;
   }

   public Object getRequest() {
      return null;
   }

   public void setRequest(Object request) {
   }

   public String getRequestContextPath() {
      return null;
   }

   public Map getRequestCookieMap() {
      return Collections.unmodifiableMap(Collections.emptyMap());
   }

   public Map getRequestHeaderMap() {
      return Collections.unmodifiableMap(Collections.emptyMap());
   }

   public Map getRequestHeaderValuesMap() {
      return Collections.unmodifiableMap(Collections.emptyMap());
   }

   public Locale getRequestLocale() {
      return null;
   }

   public Iterator getRequestLocales() {
      return null;
   }

   public Map getRequestMap() {
      return Collections.emptyMap();
   }

   public Map getRequestParameterMap() {
      return Collections.unmodifiableMap(Collections.emptyMap());
   }

   public Iterator getRequestParameterNames() {
      return Collections.emptyList().iterator();
   }

   public Map getRequestParameterValuesMap() {
      return Collections.unmodifiableMap(Collections.emptyMap());
   }

   public String getRequestPathInfo() {
      return null;
   }

   public String getRequestServletPath() {
      return null;
   }

   public String getRequestContentType() {
      return null;
   }

   public String getResponseContentType() {
      return null;
   }

   public int getRequestContentLength() {
      return -1;
   }

   public URL getResource(String path) throws MalformedURLException {
      return this.servletContext.getResource(path);
   }

   public InputStream getResourceAsStream(String path) {
      return this.servletContext.getResourceAsStream(path);
   }

   public Set getResourcePaths(String path) {
      return this.servletContext.getResourcePaths(path);
   }

   public Object getResponse() {
      return null;
   }

   public void setResponse(Object response) {
   }

   public Object getSession(boolean create) {
      return null;
   }

   public Map getSessionMap() {
      return Collections.emptyMap();
   }

   public Principal getUserPrincipal() {
      return null;
   }

   public boolean isUserInRole(String role) {
      return false;
   }

   public void log(String message) {
      this.servletContext.log(message);
   }

   public void log(String message, Throwable exception) {
      this.servletContext.log(message, exception);
   }

   public void redirect(String url) throws IOException {
   }

   public String getRequestCharacterEncoding() {
      return null;
   }

   public void setRequestCharacterEncoding(String requestCharacterEncoding) throws UnsupportedEncodingException {
   }

   public String getResponseCharacterEncoding() {
      return null;
   }

   public void setResponseCharacterEncoding(String responseCharacterEncoding) {
   }

   public void setResponseHeader(String name, String value) {
   }

   public void addResponseHeader(String name, String value) {
   }

   public String encodePartialActionURL(String url) {
      return null;
   }

   public String getRealPath(String path) {
      return this.servletContext.getRealPath(path);
   }
}
