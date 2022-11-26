package weblogic.servlet.internal.session;

import com.tangosol.coherence.servlet.AbstractHttpSessionCollection;
import com.tangosol.coherence.servlet.SplitHttpSessionCollection;
import com.tangosol.util.IteratorEnumerator;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.SessionCookieConfig;
import javax.servlet.descriptor.JspConfigDescriptor;
import weblogic.servlet.internal.HttpServer;
import weblogic.servlet.internal.WebAppServletContext;

public class CoherenceWebServletContextWrapper implements ServletContext {
   private WebAppServletContext wlsServletContext;
   private SessionConfigManager wlsSessionConfigManager;
   private HashMap coherenceParams;
   private HashMap localAttributes;

   public CoherenceWebServletContextWrapper(WebAppServletContext sc, SessionConfigManager scm, String webappName) {
      this.wlsServletContext = sc;
      this.wlsSessionConfigManager = scm;
      this.localAttributes = new HashMap();
      HashMap map = new HashMap();
      map.put("coherence-factory-class", WebLogicSPIFactory.class.getName());
      map.put("coherence-sessioncollection-class", SplitHttpSessionCollection.class.getName());
      map.put("coherence-scopecontroller-class", scm.isSessionSharingEnabled() ? AbstractHttpSessionCollection.GlobalScopeController.class.getName() : AbstractHttpSessionCollection.ApplicationScopeController.class.getName());
      map.put("coherence-session-id-length", String.valueOf(scm.getIDLength()));
      map.put("coherence-session-expire-seconds", String.valueOf(scm.getSessionTimeoutSecs()));
      map.put("coherence-reaperdaemon-cycle-seconds", String.valueOf(scm.getInvalidationIntervalSecs()));
      map.put("coherence-application-name", webappName);
      map.put("coherence-reaperdaemon-assume-locality", "false");
      map.put("coherence-session-id-generator-class", WebLogicSPIHttpSessionIdGenerator.class.getName());
      map.put("coherence-enable-attribute-listener-optimization", "true");
      map.put("coherence-reaperdaemon-sweep-modulo", "0");
      this.coherenceParams = map;
   }

   protected void setContextParam(String name, String value) {
      this.coherenceParams.put(name, value);
   }

   public WebAppServletContext getWlsServletContext() {
      return this.wlsServletContext;
   }

   public Enumeration getInitParameterNames() {
      Map map = new HashMap();
      Iterator iter = this.coherenceParams.keySet().iterator();

      Object o;
      while(iter.hasNext()) {
         o = iter.next();
         map.put(o, o);
      }

      Enumeration enmr = this.wlsServletContext.getInitParameterNames();

      while(enmr.hasMoreElements()) {
         o = enmr.nextElement();
         map.put(o, o);
      }

      return new IteratorEnumerator(map.keySet().iterator());
   }

   public boolean setInitParameter(String name, String value) {
      return this.wlsServletContext.setInitParameter(name, value);
   }

   public String getInitParameter(String s) {
      if ("coherence-session-id-length".equals(s)) {
         return String.valueOf(this.wlsSessionConfigManager.getIDLength());
      } else {
         String value = this.wlsServletContext.getInitParameter(s);
         if (value == null) {
            value = (String)this.coherenceParams.get(s);
         }

         return value;
      }
   }

   public Object getAttribute(String s) {
      Object value = this.localAttributes.get(s);
      if (value == null) {
         value = this.wlsServletContext.getAttribute(s);
      }

      return value;
   }

   public Enumeration getAttributeNames() {
      Map map = new HashMap();
      Iterator iter = this.localAttributes.keySet().iterator();

      Object o;
      while(iter.hasNext()) {
         o = iter.next();
         map.put(o, o);
      }

      Enumeration enmr = this.wlsServletContext.getAttributeNames();

      while(enmr.hasMoreElements()) {
         o = enmr.nextElement();
         map.put(o, o);
      }

      return new IteratorEnumerator(map.keySet().iterator());
   }

   public void setAttribute(String s, Object obj) {
      this.localAttributes.put(s, obj);
   }

   public void removeAttribute(String s) {
      this.localAttributes.remove(s);
   }

   public ServletContext getContext(String s) {
      return this.wlsServletContext.getContext(s);
   }

   public String getContextPath() {
      return this.wlsServletContext.getContextPath();
   }

   public int getMajorVersion() {
      return this.wlsServletContext.getMajorVersion();
   }

   public int getMinorVersion() {
      return this.wlsServletContext.getMinorVersion();
   }

   public int getEffectiveMajorVersion() {
      return this.wlsServletContext.getEffectiveMajorVersion();
   }

   public int getEffectiveMinorVersion() {
      return this.wlsServletContext.getEffectiveMinorVersion();
   }

   public String getMimeType(String s) {
      return this.wlsServletContext.getMimeType(s);
   }

   public Set getResourcePaths(String s) {
      return this.wlsServletContext.getResourcePaths(s);
   }

   public URL getResource(String s) throws MalformedURLException {
      return this.wlsServletContext.getResource(s);
   }

   public InputStream getResourceAsStream(String s) {
      return this.wlsServletContext.getResourceAsStream(s);
   }

   public RequestDispatcher getRequestDispatcher(String s) {
      return this.wlsServletContext.getRequestDispatcher(s);
   }

   public RequestDispatcher getNamedDispatcher(String s) {
      return this.wlsServletContext.getNamedDispatcher(s);
   }

   public Servlet getServlet(String s) throws ServletException {
      return this.wlsServletContext.getServlet(s);
   }

   public Enumeration getServlets() {
      return this.wlsServletContext.getServlets();
   }

   public Enumeration getServletNames() {
      return this.wlsServletContext.getServletNames();
   }

   public void log(String s) {
      this.wlsServletContext.log(s);
   }

   public void log(Exception exception, String s) {
      this.wlsServletContext.log(exception, s);
   }

   public void log(String s, Throwable throwable) {
      this.wlsServletContext.log(s, throwable);
   }

   public String getRealPath(String s) {
      return this.wlsServletContext.getRealPath(s);
   }

   public String getServerInfo() {
      return this.wlsServletContext.getServerInfo();
   }

   public String getServletContextName() {
      return this.wlsServletContext.getServletContextName();
   }

   public ServletRegistration.Dynamic addServlet(String servletName, String className) {
      return this.wlsServletContext.addServlet(servletName, className);
   }

   public ServletRegistration.Dynamic addServlet(String s, Servlet servlet) {
      return this.wlsServletContext.addServlet(s, servlet);
   }

   public ServletRegistration.Dynamic addServlet(String s, Class aClass) {
      return this.wlsServletContext.addServlet(s, aClass);
   }

   public Servlet createServlet(Class tClass) throws ServletException {
      return this.wlsServletContext.createServlet(tClass);
   }

   public ServletRegistration getServletRegistration(String s) {
      return this.wlsServletContext.getServletRegistration(s);
   }

   public Map getServletRegistrations() {
      return this.wlsServletContext.getServletRegistrations();
   }

   public FilterRegistration.Dynamic addFilter(String s, String s2) {
      return this.wlsServletContext.addFilter(s, s2);
   }

   public FilterRegistration.Dynamic addFilter(String s, Filter filter) {
      return this.wlsServletContext.addFilter(s, filter);
   }

   public FilterRegistration.Dynamic addFilter(String s, Class aClass) {
      return this.wlsServletContext.addFilter(s, aClass);
   }

   public Filter createFilter(Class tClass) throws ServletException {
      return this.wlsServletContext.createFilter(tClass);
   }

   public FilterRegistration getFilterRegistration(String s) {
      return this.wlsServletContext.getFilterRegistration(s);
   }

   public Map getFilterRegistrations() {
      return this.wlsServletContext.getFilterRegistrations();
   }

   public SessionCookieConfig getSessionCookieConfig() {
      return this.wlsServletContext.getSessionCookieConfig();
   }

   public void setSessionTrackingModes(Set sessionTrackingModes) {
      this.wlsServletContext.setSessionTrackingModes(sessionTrackingModes);
   }

   public Set getDefaultSessionTrackingModes() {
      return this.wlsServletContext.getDefaultSessionTrackingModes();
   }

   public Set getEffectiveSessionTrackingModes() {
      return this.wlsServletContext.getEffectiveSessionTrackingModes();
   }

   public void addListener(String s) {
      this.wlsServletContext.addListener(s);
   }

   public void addListener(EventListener t) {
      this.wlsServletContext.addListener(t);
   }

   public void addListener(Class aClass) {
      this.wlsServletContext.addListener(aClass);
   }

   public EventListener createListener(Class tClass) throws ServletException {
      return this.wlsServletContext.createListener(tClass);
   }

   public JspConfigDescriptor getJspConfigDescriptor() {
      return this.wlsServletContext.getJspConfigDescriptor();
   }

   public ClassLoader getClassLoader() {
      return this.wlsServletContext.getClassLoader();
   }

   public void declareRoles(String... strings) {
      this.wlsServletContext.declareRoles(strings);
   }

   public String getVirtualServerName() {
      return this.wlsServletContext.getVirtualServerName();
   }

   protected HttpServer.SessionLogin getSessionLogin() {
      return this.wlsServletContext.getServer().getSessionLogin();
   }

   public int getSessionTimeout() {
      return 0;
   }

   public void setSessionTimeout(int sessionTimeout) {
   }

   public String getRequestCharacterEncoding() {
      return null;
   }

   public void setRequestCharacterEncoding(String encoding) {
   }

   public String getResponseCharacterEncoding() {
      return null;
   }

   public void setResponseCharacterEncoding(String encoding) {
   }

   public ServletRegistration.Dynamic addJspFile(String servletName, String jspFile) {
      return null;
   }
}
