package com.sun.faces.config;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.WebappLifecycleListener;
import com.sun.faces.el.ChainTypeCompositeELResolver;
import com.sun.faces.el.ELContextListenerImpl;
import com.sun.faces.el.ELUtils;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.mgbean.BeanBuilder;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.scripting.GroovyHelper;
import com.sun.faces.scripting.GroovyHelperFactory;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Timer;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ExpressionFactory;
import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class ConfigureListener implements ServletRequestListener, HttpSessionListener, ServletRequestAttributeListener, HttpSessionAttributeListener, ServletContextAttributeListener, ServletContextListener {
   private static final Logger LOGGER;
   private ScheduledThreadPoolExecutor webResourcePool;
   protected WebappLifecycleListener webAppListener;
   protected WebConfiguration webConfig;

   public void contextInitialized(ServletContextEvent sce) {
      ServletContext context = sce.getServletContext();
      this.webAppListener = new WebappLifecycleListener(context);
      this.webAppListener.contextInitialized(sce);
      Timer timer = Timer.getInstance();
      if (timer != null) {
         timer.startTiming();
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, MessageFormat.format("ConfigureListener.contextInitialized({0})", getServletContextIdentifier(context)));
      }

      this.webConfig = WebConfiguration.getInstance(context);
      ConfigManager configManager = ConfigManager.getInstance();
      if (!configManager.hasBeenInitialized(context)) {
         if (!this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ForceLoadFacesConfigFiles)) {
            WebXmlProcessor processor = new WebXmlProcessor(context);
            if (!processor.isFacesServletPresent()) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.log(Level.FINE, "No FacesServlet found in deployment descriptor - bypassing configuration");
               }

               WebConfiguration.clear(context);
               return;
            }

            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "FacesServlet found in deployment descriptor - processing configuration.");
            }
         }

         FacesContext initContext = new InitFacesContext(context);
         ReflectionUtils.initCache(Thread.currentThread().getContextClassLoader());

         try {
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "jsf.config.listener.version", getServletContextIdentifier(context));
            }

            Util.setHtmlTLVActive(this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableHtmlTagLibraryValidator));
            if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.VerifyFacesConfigObjects)) {
               if (LOGGER.isLoggable(Level.WARNING)) {
                  LOGGER.warning("jsf.config.verifyobjects.development_only");
               }

               this.webConfig.overrideContextInitParameter(WebConfiguration.BooleanWebContextInitParameter.EnableLazyBeanValidation, false);
               Verifier.setCurrentInstance(new Verifier());
            }

            if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableThreading)) {
               this.initScripting();
            }

            configManager.initialize(context);
            this.initConfigMonitoring(context);
            Verifier v = Verifier.getCurrentInstance();
            if (v != null && !v.isApplicationValid() && LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.severe("jsf.config.verifyobjects.failures_detected");
               StringBuilder sb = new StringBuilder(128);
               Iterator i$ = v.getMessages().iterator();

               while(i$.hasNext()) {
                  String m = (String)i$.next();
                  sb.append(m).append('\n');
               }

               LOGGER.severe(sb.toString());
            }

            this.registerELResolverAndListenerWithJsp(context, false);
            ApplicationAssociate associate = ApplicationAssociate.getInstance(context);
            if (associate != null) {
               associate.setContextName(getServletContextIdentifier(context));
            }

            RenderKitUtils.loadSunJsfJs(initContext.getExternalContext());
         } finally {
            Verifier.setCurrentInstance((Verifier)null);
            initContext.release();
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "jsf.config.listener.version.complete");
            }

            if (timer != null) {
               timer.stopTiming();
               timer.logResult("Initialization of context " + getServletContextIdentifier(context));
            }

         }

      }
   }

   public void contextDestroyed(ServletContextEvent sce) {
      this.webAppListener.contextDestroyed(sce);
      this.webAppListener = null;
      ServletContext context = sce.getServletContext();
      GroovyHelper helper = GroovyHelper.getCurrentInstance(context);
      if (helper != null) {
         helper.setClassLoader();
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "ConfigureListener.contextDestroyed({0})", context.getServletContextName());
      }

      boolean var8 = false;

      try {
         var8 = true;
         FactoryFinder.releaseFactories();
         if (this.webResourcePool != null) {
            this.webResourcePool.shutdown();
            var8 = false;
         } else {
            var8 = false;
         }
      } finally {
         if (var8) {
            InitFacesContext initContext = new InitFacesContext(context);
            ApplicationAssociate.clearInstance(initContext.getExternalContext());
            ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
            ConfigManager.getInstance().destory(context);
            initContext.release();
            ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
            WebConfiguration.clear(context);
         }
      }

      FacesContext initContext = new InitFacesContext(context);
      ApplicationAssociate.clearInstance(initContext.getExternalContext());
      ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
      ConfigManager.getInstance().destory(context);
      initContext.release();
      ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
      WebConfiguration.clear(context);
   }

   public void requestDestroyed(ServletRequestEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.requestDestroyed(event);
      }

   }

   public void requestInitialized(ServletRequestEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.requestInitialized(event);
      }

   }

   public void sessionCreated(HttpSessionEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.sessionCreated(event);
      }

   }

   public void sessionDestroyed(HttpSessionEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.sessionDestroyed(event);
      }

   }

   public void attributeAdded(ServletRequestAttributeEvent event) {
   }

   public void attributeRemoved(ServletRequestAttributeEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.attributeRemoved(event);
      }

   }

   public void attributeReplaced(ServletRequestAttributeEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.attributeReplaced(event);
      }

   }

   public void attributeAdded(HttpSessionBindingEvent event) {
   }

   public void attributeRemoved(HttpSessionBindingEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.attributeRemoved(event);
      }

   }

   public void attributeReplaced(HttpSessionBindingEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.attributeReplaced(event);
      }

   }

   public void attributeAdded(ServletContextAttributeEvent event) {
   }

   public void attributeRemoved(ServletContextAttributeEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.attributeRemoved(event);
      }

   }

   public void attributeReplaced(ServletContextAttributeEvent event) {
      if (this.webAppListener != null) {
         this.webAppListener.attributeReplaced(event);
      }

   }

   private void initConfigMonitoring(ServletContext context) {
      Collection webURLs = (Collection)context.getAttribute("com.sun.faces.webresources");
      if (this.isDevModeEnabled() && webURLs != null && !webURLs.isEmpty()) {
         this.webResourcePool = new ScheduledThreadPoolExecutor(1);
         this.webResourcePool.scheduleAtFixedRate(new WebConfigResourceMonitor(context, webURLs), 2000L, 2000L, TimeUnit.MILLISECONDS);
      }

      context.removeAttribute("com.sun.faces.webresources");
   }

   private void initScripting() {
      GroovyHelper helper = GroovyHelperFactory.createHelper();
      if (helper != null) {
         helper.setClassLoader();
      }

   }

   private boolean isDevModeEnabled() {
      return this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DevelopmentMode);
   }

   private void reload(ServletContext sc) {
      if (LOGGER.isLoggable(Level.INFO)) {
         LOGGER.log(Level.INFO, "Reloading JSF configuration for context {0}", getServletContextIdentifier(sc));
      }

      GroovyHelper helper = GroovyHelper.getCurrentInstance();
      if (helper != null) {
         helper.setClassLoader();
      }

      boolean var21 = false;

      InitFacesContext initContext;
      label251: {
         label250: {
            try {
               var21 = true;
               List sessions = this.webAppListener.getActiveSessions();
               HttpSession session;
               if (sessions != null) {
                  for(Iterator i$ = sessions.iterator(); i$.hasNext(); session.invalidate()) {
                     session = (HttpSession)i$.next();
                     if (LOGGER.isLoggable(Level.INFO)) {
                        LOGGER.log(Level.INFO, "Invalidating Session {0}", session.getId());
                     }
                  }
               }

               ApplicationAssociate associate = ApplicationAssociate.getInstance(sc);
               if (associate != null) {
                  BeanManager manager = associate.getBeanManager();
                  Iterator i$ = manager.getRegisteredBeans().entrySet().iterator();

                  while(i$.hasNext()) {
                     Map.Entry entry = (Map.Entry)i$.next();
                     String name = (String)entry.getKey();
                     BeanBuilder bean = (BeanBuilder)entry.getValue();
                     if (bean.getScope() == ELUtils.Scope.APPLICATION) {
                        if (LOGGER.isLoggable(Level.INFO)) {
                           LOGGER.log(Level.INFO, "Removing application scoped managed bean: {0}", name);
                        }

                        sc.removeAttribute(name);
                     }
                  }
               }

               FactoryFinder.releaseFactories();
               var21 = false;
               break label250;
            } catch (Exception var24) {
               var24.printStackTrace();
               var21 = false;
            } finally {
               if (var21) {
                  InitFacesContext initContext = new InitFacesContext(sc);
                  ApplicationAssociate.clearInstance(initContext.getExternalContext());
                  ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
                  ConfigManager.getInstance().destory(sc);
                  initContext.release();
                  ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
                  WebConfiguration.clear(sc);
               }
            }

            initContext = new InitFacesContext(sc);
            ApplicationAssociate.clearInstance(initContext.getExternalContext());
            ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
            ConfigManager.getInstance().destory(sc);
            initContext.release();
            ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
            WebConfiguration.clear(sc);
            break label251;
         }

         initContext = new InitFacesContext(sc);
         ApplicationAssociate.clearInstance(initContext.getExternalContext());
         ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
         ConfigManager.getInstance().destory(sc);
         initContext.release();
         ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
         WebConfiguration.clear(sc);
      }

      this.webAppListener = new WebappLifecycleListener(sc);
      initContext = new InitFacesContext(sc);
      ReflectionUtils.initCache(Thread.currentThread().getContextClassLoader());

      try {
         ConfigManager configManager = ConfigManager.getInstance();
         configManager.initialize(sc);
         this.registerELResolverAndListenerWithJsp(sc, true);
         ApplicationAssociate associate = ApplicationAssociate.getInstance(sc);
         if (associate != null) {
            associate.setContextName(getServletContextIdentifier(sc));
         }

         RenderKitUtils.loadSunJsfJs(initContext.getExternalContext());
      } catch (Exception var22) {
         var22.printStackTrace();
      } finally {
         initContext.release();
      }

      if (LOGGER.isLoggable(Level.INFO)) {
         LOGGER.log(Level.INFO, "Reload complete.", getServletContextIdentifier(sc));
      }

   }

   private static String getServletContextIdentifier(ServletContext context) {
      return context.getMajorVersion() == 2 && context.getMinorVersion() < 5 ? context.getServletContextName() : context.getContextPath();
   }

   private static boolean isJspTwoOne(ServletContext context) {
      try {
         Class.forName("org.apache.jasper.compiler.JspRuntimeContext");
      } catch (ClassNotFoundException var4) {
      }

      if (JspFactory.getDefaultFactory() == null) {
         return false;
      } else {
         try {
            JspFactory.class.getMethod("getJspApplicationContext", ServletContext.class);
         } catch (Exception var3) {
            return false;
         }

         try {
            JspFactory.getDefaultFactory().getJspApplicationContext(context);
            return true;
         } catch (Throwable var2) {
            return false;
         }
      }
   }

   public void registerELResolverAndListenerWithJsp(ServletContext context, boolean reloaded) {
      if (!this.webConfig.isSet(WebConfiguration.WebContextInitParameter.ExpressionFactory) && isJspTwoOne(context)) {
         if (JspFactory.getDefaultFactory().getJspApplicationContext(context) == null) {
            return;
         }

         FacesCompositeELResolver compositeELResolverForJsp = new ChainTypeCompositeELResolver(FacesCompositeELResolver.ELResolverChainType.JSP);
         ApplicationAssociate associate = ApplicationAssociate.getInstance(context);
         if (associate != null) {
            associate.setFacesELResolverForJsp(compositeELResolverForJsp);
         }

         JspApplicationContext jspAppContext = JspFactory.getDefaultFactory().getJspApplicationContext(context);
         if (associate != null) {
            associate.setExpressionFactory(jspAppContext.getExpressionFactory());
         }

         try {
            jspAppContext.addELResolver(compositeELResolverForJsp);
         } catch (IllegalStateException var7) {
            if (!Util.isUnitTestModeEnabled() && !reloaded) {
               throw var7;
            }
         }

         ELContextListenerImpl elContextListener = new ELContextListenerImpl();
         jspAppContext.addELContextListener(elContextListener);
      } else if (!this.installExpressionFactory(context, this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.ExpressionFactory))) {
         throw new ConfigurationException(MessageUtils.getExceptionMessageString("com.sun.faces.INCORRECT_JSP_VERSION", WebConfiguration.WebContextInitParameter.ExpressionFactory.getDefaultValue(), WebConfiguration.WebContextInitParameter.ExpressionFactory.getQualifiedName()));
      }

   }

   private boolean installExpressionFactory(ServletContext sc, String elFactoryType) {
      if (elFactoryType == null) {
         return false;
      } else {
         try {
            ExpressionFactory factory = (ExpressionFactory)Util.loadClass(elFactoryType, this).newInstance();
            ApplicationAssociate associate = ApplicationAssociate.getInstance(sc);
            if (associate != null) {
               associate.setExpressionFactory(factory);
            }

            return true;
         } catch (Exception var5) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.severe(MessageFormat.format("Unable to instantiate ExpressionFactory ''{0}''", elFactoryType));
            }

            return false;
         }
      }
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }

   private class WebConfigResourceMonitor implements Runnable {
      private List monitors;
      private ServletContext sc;

      public WebConfigResourceMonitor(ServletContext sc, Collection urls) {
         assert urls != null;

         this.sc = sc;

         URL url;
         for(Iterator i$ = urls.iterator(); i$.hasNext(); this.monitors.add(new Monitor(url))) {
            url = (URL)i$.next();
            if (this.monitors == null) {
               this.monitors = new ArrayList(urls.size());
            }
         }

      }

      public void run() {
         assert this.monitors != null;

         boolean reloaded = false;
         Iterator i$ = this.monitors.iterator();

         while(i$.hasNext()) {
            Monitor m = (Monitor)i$.next();
            if (m.hasBeenModified() && !reloaded) {
               reloaded = true;
            }
         }

         if (reloaded) {
            ConfigureListener.this.reload(this.sc);
         }

      }

      private class Monitor {
         private URL url;
         private long timestamp = -1L;

         Monitor(URL url) {
            this.url = url;
            this.timestamp = this.getLastModified();
            if (ConfigureListener.LOGGER.isLoggable(Level.INFO)) {
               ConfigureListener.LOGGER.log(Level.INFO, "Monitoring {0} for modifications", url.toExternalForm());
            }

         }

         boolean hasBeenModified() {
            long temp = this.getLastModified();
            if (this.timestamp < temp) {
               this.timestamp = temp;
               if (ConfigureListener.LOGGER.isLoggable(Level.INFO)) {
                  ConfigureListener.LOGGER.log(Level.INFO, "{0} changed!", this.url.toExternalForm());
               }

               return true;
            } else {
               return false;
            }
         }

         private long getLastModified() {
            InputStream in = null;

            long var3;
            try {
               URLConnection conn = this.url.openConnection();
               conn.connect();
               in = conn.getInputStream();
               var3 = conn.getLastModified();
               return var3;
            } catch (IOException var14) {
               if (ConfigureListener.LOGGER.isLoggable(Level.SEVERE)) {
                  ConfigureListener.LOGGER.log(Level.SEVERE, "Unable to check JAR timestamp.", var14);
               }

               var3 = this.timestamp;
            } finally {
               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException var13) {
                  }
               }

            }

            return var3;
         }
      }
   }

   private static class WebXmlProcessor {
      private static final String WEB_XML_PATH = "/WEB-INF/web.xml";
      private boolean facesServletPresent;

      WebXmlProcessor(ServletContext context) {
         if (context != null) {
            this.scanForFacesServlet(context);
         }

      }

      boolean isFacesServletPresent() {
         return this.facesServletPresent;
      }

      private void scanForFacesServlet(ServletContext context) {
         SAXParserFactory factory = this.getConfiguredFactory();

         try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(context.getResourceAsStream("/WEB-INF/web.xml"), new WebXmlHandler());
         } catch (Exception var4) {
            if (ConfigureListener.LOGGER.isLoggable(Level.WARNING)) {
               ConfigureListener.LOGGER.log(Level.WARNING, MessageFormat.format("Unable to process deployment descriptor for context ''{0}''", ConfigureListener.getServletContextIdentifier(context)));
            }

            this.facesServletPresent = true;
         }

      }

      private SAXParserFactory getConfiguredFactory() {
         SAXParserFactory factory = SAXParserFactory.newInstance();
         factory.setValidating(false);
         factory.setNamespaceAware(true);
         return factory;
      }

      private class WebXmlHandler extends DefaultHandler {
         private static final String SERVLET_CLASS = "servlet-class";
         private static final String FACES_SERVLET = "javax.faces.webapp.FacesServlet";
         private boolean servletClassFound;
         private StringBuffer content;

         private WebXmlHandler() {
         }

         public InputSource resolveEntity(String publicId, String systemId) throws SAXException {
            return new InputSource(new StringReader(""));
         }

         public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            if (!WebXmlProcessor.this.facesServletPresent) {
               if ("servlet-class".equals(localName)) {
                  this.servletClassFound = true;
                  this.content = new StringBuffer();
               } else {
                  this.servletClassFound = false;
               }
            }

         }

         public void characters(char[] ch, int start, int length) throws SAXException {
            if (this.servletClassFound && !WebXmlProcessor.this.facesServletPresent) {
               this.content.append(ch, start, length);
            }

         }

         public void endElement(String uri, String localName, String qName) throws SAXException {
            if (this.servletClassFound && !WebXmlProcessor.this.facesServletPresent && "javax.faces.webapp.FacesServlet".equals(this.content.toString().trim())) {
               WebXmlProcessor.this.facesServletPresent = true;
            }

         }

         // $FF: synthetic method
         WebXmlHandler(Object x1) {
            this();
         }
      }
   }
}
