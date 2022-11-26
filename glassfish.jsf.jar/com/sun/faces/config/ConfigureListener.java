package com.sun.faces.config;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.WebappLifecycleListener;
import com.sun.faces.el.ChainTypeCompositeELResolver;
import com.sun.faces.el.ELContextImpl;
import com.sun.faces.el.ELContextListenerImpl;
import com.sun.faces.el.ELUtils;
import com.sun.faces.el.FacesCompositeELResolver;
import com.sun.faces.mgbean.BeanBuilder;
import com.sun.faces.mgbean.BeanManager;
import com.sun.faces.push.WebsocketEndpoint;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.MojarraThreadFactory;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.Timer;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.ApplicationFactory;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.faces.event.PreDestroyApplicationEvent;
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
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig.Builder;
import javax.xml.parsers.ParserConfigurationException;
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
      Timer timer = Timer.getInstance();
      if (timer != null) {
         timer.startTiming();
      }

      ConfigManager configManager = ConfigManager.getInstance(context);
      if (configManager == null) {
         configManager = ConfigManager.createInstance(context);
      }

      if (!configManager.hasBeenInitialized(context)) {
         InitFacesContext initContext = new InitFacesContext(context);
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, MessageFormat.format("ConfigureListener.contextInitialized({0})", getServletContextIdentifier(context)));
         }

         this.webConfig = WebConfiguration.getInstance(context);
         Object mappingsAdded = context.getAttribute("com.sun.faces.facesInitializerMappingsAdded");
         if (mappingsAdded != null) {
            context.removeAttribute("com.sun.faces.facesInitializerMappingsAdded");
         }

         WebXmlProcessor webXmlProcessor = new WebXmlProcessor(context);
         if (mappingsAdded == null) {
            if (!webXmlProcessor.isFacesServletPresent()) {
               if (!this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.ForceLoadFacesConfigFiles)) {
                  LOGGER.log(Level.FINE, "No FacesServlet found in deployment descriptor - bypassing configuration");
                  WebConfiguration.clear(context);
                  configManager.destroy(context, initContext);
                  ConfigManager.removeInstance(context);
                  InitFacesContext.cleanupInitMaps(context);
                  return;
               }
            } else {
               LOGGER.log(Level.FINE, "FacesServlet found in deployment descriptor - processing configuration.");
            }
         }

         if (webXmlProcessor.isDistributablePresent()) {
            this.webConfig.setOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableDistributable, true);
            context.setAttribute(WebConfiguration.BooleanWebContextInitParameter.EnableDistributable.getQualifiedName(), Boolean.TRUE);
         }

         this.webAppListener = new WebappLifecycleListener(context);
         this.webAppListener.contextInitialized(sce);
         ReflectionUtils.initCache(Thread.currentThread().getContextClassLoader());
         Throwable caughtThrowable = null;

         try {
            if (LOGGER.isLoggable(Level.INFO)) {
               LOGGER.log(Level.INFO, "jsf.config.listener.version", getServletContextIdentifier(context));
            }

            if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.VerifyFacesConfigObjects)) {
               LOGGER.warning("jsf.config.verifyobjects.development_only");
               this.webConfig.overrideContextInitParameter(WebConfiguration.BooleanWebContextInitParameter.EnableLazyBeanValidation, false);
               Verifier.setCurrentInstance(new Verifier());
            }

            configManager.initialize(context, initContext);
            if (this.shouldInitConfigMonitoring()) {
               this.initConfigMonitoring(context);
            }

            Verifier verifier = Verifier.getCurrentInstance();
            if (verifier != null && !verifier.isApplicationValid() && LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.severe("jsf.config.verifyobjects.failures_detected");
               StringBuilder sb = new StringBuilder(128);
               Iterator var11 = verifier.getMessages().iterator();

               while(var11.hasNext()) {
                  String msg = (String)var11.next();
                  sb.append(msg).append('\n');
               }

               LOGGER.severe(sb.toString());
            }

            this.registerELResolverAndListenerWithJsp(context, false);
            ApplicationAssociate associate = ApplicationAssociate.getInstance(context);
            ELContext elContext = new ELContextImpl(initContext.getApplication().getELResolver());
            elContext.putContext(FacesContext.class, initContext);
            ExpressionFactory exFactory = ELUtils.getDefaultExpressionFactory(associate, initContext);
            if (exFactory != null) {
               elContext.putContext(ExpressionFactory.class, exFactory);
            }

            initContext.setELContext(elContext);
            if (associate != null) {
               associate.setContextName(getServletContextIdentifier(context));
               BeanManager manager = associate.getBeanManager();
               List eagerBeans = manager.getEagerBeanNames();
               if (!eagerBeans.isEmpty()) {
                  Iterator var15 = eagerBeans.iterator();

                  while(var15.hasNext()) {
                     String name = (String)var15.next();
                     manager.create(name, initContext);
                  }
               }

               boolean isErrorPagePresent = webXmlProcessor.isErrorPagePresent();
               associate.setErrorPagePresent(isErrorPagePresent);
               context.setAttribute("com.sun.faces.errorPagePresent", isErrorPagePresent);
            }

            if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableWebsocketEndpoint)) {
               ServerContainer serverContainer = (ServerContainer)context.getAttribute(ServerContainer.class.getName());
               if (serverContainer == null) {
                  throw new UnsupportedOperationException("Cannot enable f:websocket. The current websocket container implementation does not support programmatically registering a container-provided endpoint.");
               }

               serverContainer.addEndpoint(Builder.create(WebsocketEndpoint.class, "/javax.faces.push/{channel}").build());
            }

            this.webConfig.doPostBringupActions();
            configManager.publishPostConfigEvent();
         } catch (Throwable var20) {
            LOGGER.log(Level.SEVERE, "Critical error during deployment: ", var20);
            caughtThrowable = var20;
         } finally {
            sce.getServletContext().removeAttribute("com.sun.faces.AnnotatedClasses");
            Verifier.setCurrentInstance((Verifier)null);
            LOGGER.log(Level.FINE, "jsf.config.listener.version.complete");
            if (timer != null) {
               timer.stopTiming();
               timer.logResult("Initialization of context " + getServletContextIdentifier(context));
            }

            if (caughtThrowable != null) {
               throw new RuntimeException(caughtThrowable);
            }

            initContext.releaseCurrentInstance();
         }

      }
   }

   public void contextDestroyed(ServletContextEvent sce) {
      ServletContext context = sce.getServletContext();
      ConfigManager configManager = ConfigManager.getInstance(context);
      if (configManager == null && WebConfiguration.getInstanceWithoutCreating(context) != null && LOGGER.isLoggable(Level.WARNING)) {
         LOGGER.log(Level.WARNING, "Unexpected state during contextDestroyed: no ConfigManager instance in current ServletContext but one is expected to exist.");
      }

      InitFacesContext initContext = null;

      try {
         initContext = this.getInitFacesContext(context);
         if (initContext == null) {
            initContext = new InitFacesContext(context);
         } else {
            InitFacesContext.getThreadInitContextMap().put(Thread.currentThread(), initContext);
         }

         if (this.webAppListener != null) {
            this.webAppListener.contextDestroyed(sce);
            this.webAppListener = null;
         }

         if (this.webResourcePool != null) {
            this.webResourcePool.shutdownNow();
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "ConfigureListener.contextDestroyed({0})", context.getServletContextName());
         }

         if (configManager == null || !configManager.hasBeenInitialized(context)) {
            return;
         }

         ELContext elContext = new ELContextImpl(initContext.getApplication().getELResolver());
         elContext.putContext(FacesContext.class, initContext);
         ExpressionFactory exFactory = ELUtils.getDefaultExpressionFactory(initContext);
         if (null != exFactory) {
            elContext.putContext(ExpressionFactory.class, exFactory);
         }

         initContext.setELContext(elContext);
         Application application = initContext.getApplication();
         application.publishEvent(initContext, PreDestroyApplicationEvent.class, Application.class, application);
      } catch (Exception var11) {
         LOGGER.log(Level.SEVERE, "Unexpected exception when attempting to tear down the Mojarra runtime", var11);
      } finally {
         ApplicationAssociate.clearInstance(context);
         ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
         if (configManager != null) {
            configManager.destroy(context, initContext);
            ConfigManager.removeInstance(context);
         } else if (LOGGER.isLoggable(Level.WARNING)) {
            LOGGER.log(Level.WARNING, "Unexpected state during contextDestroyed: no ConfigManager instance in current ServletContext but one is expected to exist.");
         }

         FactoryFinder.releaseFactories();
         ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
         WebConfiguration.clear(context);
         InitFacesContext.cleanupInitMaps(context);
      }

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

   private boolean shouldInitConfigMonitoring() {
      boolean development = this.isDevModeEnabled();
      boolean threadingOptionSpecified = this.webConfig.isSet(WebConfiguration.BooleanWebContextInitParameter.EnableThreading);
      if (development && !threadingOptionSpecified) {
         return true;
      } else {
         return development && threadingOptionSpecified && this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.EnableThreading);
      }
   }

   private void initConfigMonitoring(ServletContext context) {
      Collection webURIs = (Collection)context.getAttribute("com.sun.faces.webresources");
      if (this.isDevModeEnabled() && webURIs != null && !webURIs.isEmpty()) {
         this.webResourcePool = new ScheduledThreadPoolExecutor(1, new MojarraThreadFactory("WebResourceMonitor"));
         this.webResourcePool.scheduleAtFixedRate(new WebConfigResourceMonitor(context, webURIs), 2000L, 2000L, TimeUnit.MILLISECONDS);
      }

      context.removeAttribute("com.sun.faces.webresources");
   }

   private boolean isDevModeEnabled() {
      return "Development".equals(this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.JavaxFacesProjectStage));
   }

   private void reload(ServletContext servletContext) {
      if (LOGGER.isLoggable(Level.INFO)) {
         LOGGER.log(Level.INFO, "Reloading JSF configuration for context {0}", getServletContextIdentifier(servletContext));
      }

      boolean var20 = false;

      InitFacesContext initContext;
      ConfigManager configManager;
      label344: {
         label345: {
            try {
               var20 = true;
               if (null != this.webAppListener) {
                  List sessions = this.webAppListener.getActiveSessions();
                  HttpSession session;
                  if (sessions != null) {
                     for(Iterator var3 = sessions.iterator(); var3.hasNext(); session.invalidate()) {
                        session = (HttpSession)var3.next();
                        if (LOGGER.isLoggable(Level.INFO)) {
                           LOGGER.log(Level.INFO, "Invalidating Session {0}", session.getId());
                        }
                     }
                  }
               }

               ApplicationAssociate associate = ApplicationAssociate.getInstance(servletContext);
               if (associate != null) {
                  BeanManager manager = associate.getBeanManager();
                  Iterator var29 = manager.getRegisteredBeans().entrySet().iterator();

                  while(var29.hasNext()) {
                     Map.Entry entry = (Map.Entry)var29.next();
                     String name = (String)entry.getKey();
                     BeanBuilder bean = (BeanBuilder)entry.getValue();
                     if (ELUtils.Scope.APPLICATION.toString().equals(bean.getScope())) {
                        if (LOGGER.isLoggable(Level.INFO)) {
                           LOGGER.log(Level.INFO, "Removing application scoped managed bean: {0}", name);
                        }

                        servletContext.removeAttribute(name);
                     }
                  }
               }

               FactoryFinder.releaseFactories();
               var20 = false;
               break label345;
            } catch (Exception var23) {
               var23.printStackTrace();
               var20 = false;
            } finally {
               if (var20) {
                  InitFacesContext initContext = new InitFacesContext(servletContext);
                  ApplicationAssociate.clearInstance(initContext.getExternalContext());
                  ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
                  ConfigManager configManager = ConfigManager.getInstance(servletContext);
                  if (configManager != null) {
                     configManager.destroy(servletContext, initContext);
                     ConfigManager.removeInstance(servletContext);
                  } else if (LOGGER.isLoggable(Level.SEVERE)) {
                     LOGGER.log(Level.SEVERE, "Unexpected state during reload: no ConfigManager instance in current ServletContext but one is expected to exist.");
                  }

                  initContext.release();
                  ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
                  WebConfiguration.clear(servletContext);
               }
            }

            initContext = new InitFacesContext(servletContext);
            ApplicationAssociate.clearInstance(initContext.getExternalContext());
            ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
            configManager = ConfigManager.getInstance(servletContext);
            if (configManager != null) {
               configManager.destroy(servletContext, initContext);
               ConfigManager.removeInstance(servletContext);
            } else if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "Unexpected state during reload: no ConfigManager instance in current ServletContext but one is expected to exist.");
            }

            initContext.release();
            ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
            WebConfiguration.clear(servletContext);
            break label344;
         }

         initContext = new InitFacesContext(servletContext);
         ApplicationAssociate.clearInstance(initContext.getExternalContext());
         ApplicationAssociate.setCurrentInstance((ApplicationAssociate)null);
         configManager = ConfigManager.getInstance(servletContext);
         if (configManager != null) {
            configManager.destroy(servletContext, initContext);
            ConfigManager.removeInstance(servletContext);
         } else if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, "Unexpected state during reload: no ConfigManager instance in current ServletContext but one is expected to exist.");
         }

         initContext.release();
         ReflectionUtils.clearCache(Thread.currentThread().getContextClassLoader());
         WebConfiguration.clear(servletContext);
      }

      this.webAppListener = new WebappLifecycleListener(servletContext);
      initContext = new InitFacesContext(servletContext);
      ReflectionUtils.initCache(Thread.currentThread().getContextClassLoader());

      try {
         configManager = ConfigManager.createInstance(servletContext);
         if (null != configManager) {
            configManager.initialize(servletContext, initContext);
         } else {
            LOGGER.log(Level.SEVERE, "Unexpected state during reload: no ConfigManager instance in current ServletContext but one is expected to exist.");
         }

         this.registerELResolverAndListenerWithJsp(servletContext, true);
         ApplicationAssociate associate = ApplicationAssociate.getInstance(servletContext);
         if (associate != null) {
            Boolean errorPagePresent = (Boolean)servletContext.getAttribute("com.sun.faces.errorPagePresent");
            if (errorPagePresent != null) {
               associate.setErrorPagePresent(errorPagePresent);
               associate.setContextName(getServletContextIdentifier(servletContext));
            }
         }
      } catch (Exception var21) {
         var21.printStackTrace();
      } finally {
         initContext.release();
      }

      if (LOGGER.isLoggable(Level.INFO)) {
         LOGGER.log(Level.INFO, "Reload complete.", getServletContextIdentifier(servletContext));
      }

   }

   private static String getServletContextIdentifier(ServletContext context) {
      if (context.getMajorVersion() == 2 && context.getMinorVersion() < 5) {
         return context.getServletContextName();
      } else {
         try {
            return context.getContextPath();
         } catch (AbstractMethodError var2) {
            return context.getServletContextName();
         }
      }
   }

   private static boolean isJspTwoOne(ServletContext context) {
      try {
         Class.forName("org.apache.jasper.compiler.JspRuntimeContext");
      } catch (ClassNotFoundException var4) {
         if (LOGGER.isLoggable(Level.FINEST)) {
            LOGGER.log(Level.FINEST, "Dected JSP 2.1", var4);
         }
      }

      if (JspFactory.getDefaultFactory() == null) {
         return false;
      } else {
         try {
            JspFactory.class.getMethod("getJspApplicationContext", ServletContext.class);
         } catch (SecurityException | NoSuchMethodException var3) {
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
         } catch (IllegalStateException var9) {
            ApplicationFactory factory = (ApplicationFactory)FactoryFinder.getFactory("javax.faces.application.ApplicationFactory");
            Application app = factory.getApplication();
            if (app.getProjectStage() != ProjectStage.UnitTest && !reloaded) {
               throw var9;
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
         } catch (InstantiationException | IllegalAccessException | ClassNotFoundException var5) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.severe(MessageFormat.format("Unable to instantiate ExpressionFactory ''{0}''", elFactoryType));
            }

            return false;
         }
      }
   }

   private InitFacesContext getInitFacesContext(ServletContext context) {
      Map initContextServletContext = InitFacesContext.getInitContextServletContextMap();
      Set entries = initContextServletContext.entrySet();
      InitFacesContext initContext = null;
      Iterator iterator1 = entries.iterator();

      while(iterator1.hasNext()) {
         Map.Entry entry1 = (Map.Entry)iterator1.next();
         Object initContextKey = entry1.getKey();
         Object value1 = entry1.getValue();
         if (context == value1) {
            initContext = (InitFacesContext)initContextKey;
            break;
         }
      }

      return initContext;
   }

   static {
      LOGGER = FacesLogger.CONFIG.getLogger();
   }

   private class WebConfigResourceMonitor implements Runnable {
      private List monitors;
      private ServletContext sc;

      public WebConfigResourceMonitor(ServletContext sc, Collection uris) {
         assert uris != null;

         this.sc = sc;
         Iterator var4 = uris.iterator();

         while(var4.hasNext()) {
            URI uri = (URI)var4.next();
            if (this.monitors == null) {
               this.monitors = new ArrayList(uris.size());
            }

            try {
               Monitor m = new Monitor(uri);
               this.monitors.add(m);
            } catch (IOException var7) {
               if (ConfigureListener.LOGGER.isLoggable(Level.SEVERE)) {
                  ConfigureListener.LOGGER.severe("Unable to setup resource monitor for " + uri.toString() + ".  Resource will not be monitored for changes.");
               }

               if (ConfigureListener.LOGGER.isLoggable(Level.FINE)) {
                  ConfigureListener.LOGGER.log(Level.FINE, var7.toString(), var7);
               }
            }
         }

      }

      public void run() {
         assert this.monitors != null;

         boolean reloaded = false;
         Iterator i = this.monitors.iterator();

         while(i.hasNext()) {
            Monitor m = (Monitor)i.next();

            try {
               if (m.hasBeenModified() && !reloaded) {
                  reloaded = true;
               }
            } catch (IOException var5) {
               if (ConfigureListener.LOGGER.isLoggable(Level.SEVERE)) {
                  ConfigureListener.LOGGER.severe("Unable to access url " + m.uri.toString() + ".  Monitoring for this resource will no longer occur.");
               }

               if (ConfigureListener.LOGGER.isLoggable(Level.FINE)) {
                  ConfigureListener.LOGGER.log(Level.FINE, var5.toString(), var5);
               }

               i.remove();
            }
         }

         if (reloaded) {
            ConfigureListener.this.reload(this.sc);
         }

      }

      private class Monitor {
         private URI uri;
         private long timestamp = -1L;

         Monitor(URI uri) throws IOException {
            this.uri = uri;
            this.timestamp = this.getLastModified();
            if (ConfigureListener.LOGGER.isLoggable(Level.INFO)) {
               ConfigureListener.LOGGER.log(Level.INFO, "Monitoring {0} for modifications", uri.toURL().toExternalForm());
            }

         }

         boolean hasBeenModified() throws IOException {
            long temp = this.getLastModified();
            if (this.timestamp < temp) {
               this.timestamp = temp;
               if (ConfigureListener.LOGGER.isLoggable(Level.INFO)) {
                  ConfigureListener.LOGGER.log(Level.INFO, "{0} changed!", this.uri.toURL().toExternalForm());
               }

               return true;
            } else {
               return false;
            }
         }

         private long getLastModified() throws IOException {
            InputStream in = null;

            long var3;
            try {
               URLConnection conn = this.uri.toURL().openConnection();
               conn.connect();
               in = conn.getInputStream();
               var3 = conn.getLastModified();
            } finally {
               if (in != null) {
                  try {
                     in.close();
                  } catch (IOException var11) {
                     if (ConfigureListener.LOGGER.isLoggable(Level.FINEST)) {
                        ConfigureListener.LOGGER.log(Level.FINEST, "Exception while closing stream", var11);
                     }
                  }
               }

            }

            return var3;
         }
      }
   }

   private static class WebXmlProcessor {
      private static final String WEB_XML_PATH = "/WEB-INF/web.xml";
      private static final String WEB_FRAGMENT_PATH = "META-INF/web-fragment.xml";
      private boolean facesServletPresent;
      private boolean errorPagePresent;
      private boolean distributablePresent;

      WebXmlProcessor(ServletContext context) {
         if (context != null) {
            this.scanForFacesServlet(context);
         }

      }

      boolean isFacesServletPresent() {
         return this.facesServletPresent;
      }

      boolean isErrorPagePresent() {
         return this.errorPagePresent;
      }

      public boolean isDistributablePresent() {
         return this.distributablePresent;
      }

      private void scanForFacesServlet(ServletContext context) {
         InputStream in = context.getResourceAsStream("/WEB-INF/web.xml");
         if (in == null && context.getMajorVersion() < 3) {
            throw new ConfigurationException("no web.xml present");
         } else {
            SAXParserFactory factory = this.getConfiguredFactory();
            if (in != null) {
               label357: {
                  try {
                     SAXParser parser = factory.newSAXParser();
                     parser.parse(in, new WebXmlHandler());
                     break label357;
                  } catch (SAXException | IOException | ParserConfigurationException var37) {
                     this.warnProcessingError(var37, context);
                     this.facesServletPresent = true;
                  } finally {
                     if (in != null) {
                        try {
                           in.close();
                        } catch (Exception var34) {
                           if (ConfigureListener.LOGGER.isLoggable(Level.FINEST)) {
                              ConfigureListener.LOGGER.log(Level.FINEST, "Closing stream", var34);
                           }
                        }
                     }

                  }

                  return;
               }
            }

            if (!this.facesServletPresent && context.getMajorVersion() >= 3) {
               ClassLoader cl = Util.getCurrentLoader(this);

               Enumeration urls;
               try {
                  urls = cl.getResources("META-INF/web-fragment.xml");
               } catch (IOException var32) {
                  throw new ConfigurationException(var32);
               }

               if (urls != null) {
                  while(urls.hasMoreElements() && !this.facesServletPresent) {
                     InputStream fragmentStream = null;

                     try {
                        URL url = (URL)urls.nextElement();
                        URLConnection conn = url.openConnection();
                        conn.setUseCaches(false);
                        fragmentStream = conn.getInputStream();
                        SAXParser parser = factory.newSAXParser();
                        parser.parse(fragmentStream, new WebXmlHandler());
                        continue;
                     } catch (ParserConfigurationException | SAXException | IOException var35) {
                        this.warnProcessingError(var35, context);
                        this.facesServletPresent = true;
                     } finally {
                        if (fragmentStream != null) {
                           try {
                              fragmentStream.close();
                           } catch (IOException var33) {
                              if (ConfigureListener.LOGGER.isLoggable(Level.WARNING)) {
                                 ConfigureListener.LOGGER.log(Level.WARNING, "Exception whil scanning for FacesServlet", var33);
                              }
                           }
                        }

                     }

                     return;
                  }
               }
            }

         }
      }

      private SAXParserFactory getConfiguredFactory() {
         SAXParserFactory factory = Util.createSAXParserFactory();
         factory.setValidating(false);
         factory.setNamespaceAware(true);
         return factory;
      }

      private void warnProcessingError(Exception e, ServletContext sc) {
         if (ConfigureListener.LOGGER.isLoggable(Level.WARNING)) {
            ConfigureListener.LOGGER.log(Level.WARNING, MessageFormat.format("jsf.configuration.web.xml.parse.failed", ConfigureListener.getServletContextIdentifier(sc)), e);
         }

      }

      private class WebXmlHandler extends DefaultHandler {
         private static final String ERROR_PAGE = "error-page";
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
            if (!WebXmlProcessor.this.errorPagePresent && "error-page".equals(localName)) {
               WebXmlProcessor.this.errorPagePresent = true;
            } else {
               if (!WebXmlProcessor.this.facesServletPresent) {
                  if ("servlet-class".equals(localName)) {
                     this.servletClassFound = true;
                     this.content = new StringBuffer();
                  } else {
                     this.servletClassFound = false;
                  }
               }

               if ("distributable".equals(localName)) {
                  WebXmlProcessor.this.distributablePresent = true;
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
