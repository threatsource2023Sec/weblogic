package weblogic.servlet.internal;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.application.ApplicationContextInternal;
import weblogic.application.ModuleContext;
import weblogic.application.naming.BindingsFactory;
import weblogic.application.naming.EnvUtils;
import weblogic.application.naming.Environment;
import weblogic.application.naming.EnvironmentException;
import weblogic.application.naming.MessageDestinationInfoRegistry;
import weblogic.application.naming.MessageDestinationInfoRegistryImpl;
import weblogic.application.naming.ModuleRegistry;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EEUtils;
import weblogic.j2ee.descriptor.EjbLocalRefBean;
import weblogic.j2ee.descriptor.EjbRefBean;
import weblogic.j2ee.descriptor.MessageDestinationBean;
import weblogic.j2ee.descriptor.ResourceEnvRefBean;
import weblogic.j2ee.descriptor.ResourceRefBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.EjbReferenceDescriptionBean;
import weblogic.j2ee.descriptor.wl.MessageDestinationDescriptorBean;
import weblogic.j2ee.descriptor.wl.ResourceDescriptionBean;
import weblogic.j2ee.descriptor.wl.WeblogicWebAppBean;
import weblogic.management.DeploymentException;
import weblogic.protocol.ServerChannel;
import weblogic.protocol.ServerChannelManager;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.servlet.spi.JNDIProvider;
import weblogic.servlet.spi.WebServerRegistry;
import weblogic.utils.classloaders.ClassFinder;
import weblogic.utils.classloaders.Source;

public class CompEnv {
   private static final DebugLogger DEBUG;
   private static final String INJECTION_BINDING = "WebComponentCreator";
   private static final String WEB_APP_LOGGING_PREAMBLE = "WEB-INF/web.xml of ";
   private final Environment eBuilder;
   private final WebAppServletContext servletContext;
   private final WebAppBean waBean;
   private final WeblogicWebAppBean wlBean;
   private final String subcontextName;
   private final Context webctx;
   private final List ejbRefsList = new ArrayList();
   private final List ejbRefsDesList = new ArrayList();
   private final List resRefsList = new ArrayList();
   private final List resRefsDesList = new ArrayList();
   private final String linkRefPrefix;
   private final URL webModuleContextRootURL;

   public CompEnv(WebAppServletContext c) throws DeploymentException {
      if (DEBUG.isDebugEnabled()) {
         this.say("create new comp/env for " + c);
      }

      this.servletContext = c;
      WebAppModule module = this.servletContext.getWebAppModule();
      this.waBean = module != null ? module.getWebAppBean() : null;
      this.wlBean = module != null ? module.getWlWebAppBean() : null;
      this.subcontextName = this.servletContext.getName() + "/" + this.hashCode();
      Context appCtx = this.servletContext.getApplicationContext().getEnvContext();
      this.linkRefPrefix = this.initLinkRefPrefix();
      this.webModuleContextRootURL = this.getWebModuleContextRootURL();

      try {
         JNDIProvider jndiProvider = WebServerRegistry.getInstance().getJNDIProvider();
         if (appCtx == null) {
            appCtx = jndiProvider.createApplicationContext("webapp");
         }
      } catch (Exception var7) {
         throw new DeploymentException("Error initializing JNDI provider", var7);
      }

      try {
         this.webctx = (Context)appCtx.lookup("webapp");
         Context rootCtx = this.webctx.createSubcontext(J2EEUtils.normalizeJarName(this.subcontextName));
         Context compCtx = rootCtx.createSubcontext("comp");
         this.eBuilder = BindingsFactory.getInstance().createWebAppEnvironment(rootCtx, this.servletContext.getApplicationId(), module.getModuleName(), module.getId(), DEBUG, this.servletContext.getApplicationContext().getRootContext(), compCtx);
         this.registerModuleNamingContext(rootCtx);
      } catch (NamingException var6) {
         throw new AssertionError(var6);
      }
   }

   CompEnv() {
      this.eBuilder = null;
      this.servletContext = null;
      this.waBean = null;
      this.wlBean = null;
      this.subcontextName = null;
      this.webctx = null;
      this.linkRefPrefix = null;
      this.webModuleContextRootURL = null;
   }

   private void registerModuleNamingContext(Context moduleCtx) {
      ApplicationContextInternal appCtx = this.servletContext.getApplicationContext();
      ModuleContext mc = appCtx.getModuleContext(this.servletContext.getId());
      if (mc != null) {
         ModuleRegistry mr = mc.getRegistry();
         mr.put(Context.class.getName(), moduleCtx);
      }

   }

   private String initLinkRefPrefix() {
      return this.servletContext.getWebAppModule() == null ? null : J2EEUtils.normalizeJarName(this.servletContext.getWebAppModule().getId() + "#");
   }

   void prepare() throws DeploymentException {
      if (this.waBean != null) {
         try {
            this.eBuilder.contributeEnvEntries(this.waBean, this.wlBean, (AuthenticatedSubject)null);
            this.eBuilder.validateEnvEntries(this.servletContext.getServletClassLoader());
            this.registerMessageDestinations();
         } catch (EnvironmentException var2) {
            throw new DeploymentException(var2);
         }
      }
   }

   void activate() throws DeploymentException {
      if (DEBUG.isDebugEnabled()) {
         this.say("activate comp/env for " + this.servletContext);
      }

      Thread thread = Thread.currentThread();
      ClassLoader oldLoader = thread.getContextClassLoader();
      ClassLoader newLoader = this.servletContext.getServletClassLoader();
      thread.setContextClassLoader(newLoader);
      WebAppModule module = this.servletContext.getWebAppModule();
      WebAppBean webBean = module.getWebAppBean();

      try {
         this.addWebComponentCreator();
         this.bindWebModuleContextRootURL();
         JNDIProvider jndiProvider = WebServerRegistry.getInstance().getJNDIProvider();

         try {
            jndiProvider.pushContext(this.eBuilder.getRootContext());
            this.eBuilder.bindValidation(this.getValidationDescriptorURLs());
         } finally {
            jndiProvider.popContext();
         }

         this.eBuilder.bindEnvEntriesFromDDs(newLoader, this.servletContext.getWebAppModule().getPersistenceUnitRegistryProvider(), this.servletContext);
         this.destroyAPIBoundResourceRef();
         this.destroyAPIBoundEJBRef();
      } catch (Exception var16) {
         this.cleanup();
         throw new DeploymentException("Could not setup environment", var16);
      } finally {
         thread.setContextClassLoader(oldLoader);
      }

   }

   private void destroyAPIBoundResourceRef() {
      Iterator var1 = this.resRefsList.iterator();

      while(var1.hasNext()) {
         ResourceRefBean resourceRef = (ResourceRefBean)var1.next();
         this.waBean.destroyResourceRef(resourceRef);
      }

      var1 = this.resRefsDesList.iterator();

      while(var1.hasNext()) {
         ResourceDescriptionBean resourceDescription = (ResourceDescriptionBean)var1.next();
         this.wlBean.destroyResourceDescription(resourceDescription);
      }

   }

   private void destroyAPIBoundEJBRef() {
      Iterator var1 = this.ejbRefsList.iterator();

      while(var1.hasNext()) {
         Object objResourceRef = var1.next();
         if (objResourceRef instanceof EjbRefBean) {
            this.waBean.destroyEjbRef((EjbRefBean)objResourceRef);
         } else {
            this.waBean.destroyEjbLocalRef((EjbLocalRefBean)objResourceRef);
         }
      }

      var1 = this.ejbRefsDesList.iterator();

      while(var1.hasNext()) {
         EjbReferenceDescriptionBean ejbReferenceDescription = (EjbReferenceDescriptionBean)var1.next();
         this.wlBean.destroyEjbReferenceDescription(ejbReferenceDescription);
      }

   }

   private List getValidationDescriptorURLs() {
      List validationDescriptorURLs = new LinkedList();

      try {
         URL url = this.servletContext.getResource("/WEB-INF/validation.xml");
         if (url != null) {
            validationDescriptorURLs.add(url);
         }

         ClassFinder finder = this.servletContext.getWarInstance().getClassFinder();
         Enumeration enu = finder.getSources("META-INF/validation.xml");

         while(enu.hasMoreElements()) {
            Source source = (Source)enu.nextElement();
            if (source != null) {
               validationDescriptorURLs.add(source.getURL());
            }
         }
      } catch (MalformedURLException var6) {
      }

      return validationDescriptorURLs;
   }

   private void registerMessageDestinations() throws EnvironmentException {
      MessageDestinationDescriptorBean[] mdds = this.wlBean == null ? null : this.wlBean.getMessageDestinationDescriptors();
      MessageDestinationBean[] mds = this.waBean == null ? null : this.waBean.getMessageDestinations();
      if (mdds != null && mdds.length != 0 || mds != null && mds.length != 0) {
         ApplicationContextInternal appCtx = this.servletContext.getApplicationContext();
         ModuleRegistry mr = appCtx.getModuleContext(this.servletContext.getId()).getRegistry();
         MessageDestinationInfoRegistry mdir = new MessageDestinationInfoRegistryImpl();
         mr.put(MessageDestinationInfoRegistry.class.getName(), mdir);
         mdir.register(mds, mdds);
      }
   }

   private void unregisterMessageDestinations() {
      MessageDestinationDescriptorBean[] mdds = this.wlBean == null ? null : this.wlBean.getMessageDestinationDescriptors();
      MessageDestinationBean[] mds = this.waBean == null ? null : this.waBean.getMessageDestinations();
      if (mdds != null && mdds.length != 0 || mds != null && mds.length != 0) {
         ApplicationContextInternal appCtx = this.servletContext.getApplicationContext();
         ModuleRegistry mr = appCtx.getModuleContext(this.servletContext.getId()).getRegistry();
         mr.remove(MessageDestinationInfoRegistry.class.getName());
      }
   }

   private void destroyContext() {
      try {
         this.webctx.destroySubcontext(this.subcontextName);
      } catch (NamingException var2) {
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("error removing context - app", var2);
         }
      }

   }

   void cleanup() {
      if (DEBUG.isDebugEnabled()) {
         this.say("cleanup comp/env for " + this.servletContext);
      }

      this.unregisterMessageDestinations();
      this.eBuilder.unbindValidation();
      this.unbindWebModuleContextRootURL();
   }

   void destroy() {
      if (DEBUG.isDebugEnabled()) {
         this.say("destroy comp/env for " + this.servletContext);
      }

      this.removeWebComponentCreator();
      this.eBuilder.unbindEnvEntries();
      this.resRefsList.clear();
      this.resRefsDesList.clear();
      this.ejbRefsList.clear();
      this.ejbRefsDesList.clear();
      this.eBuilder.destroy();
      this.destroyContext();
   }

   Context getEnvironmentContext() {
      return this.eBuilder.getRootContext();
   }

   private void say(String msg) {
      DEBUG.debug(this.subcontextName + ": " + msg);
   }

   public void dump() {
      this.say("DUMPING COMP ENV FOR WEBAPP: " + this.subcontextName);
      this.say(EnvUtils.dumpContext(this.eBuilder.getRootContext()));
   }

   void bindResourceRef(String jndiRefName, String resTypeClassName, String resAuth, String jndiName, boolean sharable, String desc) throws DeploymentException {
      ResourceRefBean ref = this.waBean.createResourceRef();
      ref.setResRefName(jndiRefName);
      ref.setResType(resTypeClassName);
      ref.setResAuth(resAuth);
      ref.setResSharingScope(sharable ? "Sharable" : "Unsharable");
      ref.addDescription(desc);
      this.resRefsList.add(ref);
      ResourceDescriptionBean desBean = this.wlBean.createResourceDescription();
      desBean.setResRefName(jndiRefName);
      desBean.setJNDIName(jndiName);
      this.resRefsDesList.add(desBean);
   }

   void bindEjbRef(String name, String ejbRefType, String homeClassName, String remoteClassName, String ejbLink, String jndiName, String desc, boolean local) throws DeploymentException {
      if (ejbRefType.equalsIgnoreCase("Session") && ejbRefType.equalsIgnoreCase("Entity")) {
         if (!local) {
            EjbRefBean ejbRef = this.waBean.createEjbRef();
            ejbRef.setEjbRefName(name);
            ejbRef.setEjbRefType(ejbRefType);
            ejbRef.setHome(homeClassName);
            ejbRef.setRemote(remoteClassName);
            ejbRef.setEjbLink(ejbLink);
            ejbRef.addDescription(desc);
            this.ejbRefsList.add(ejbRef);
         } else {
            EjbLocalRefBean ejblocalRef = this.waBean.createEjbLocalRef();
            ejblocalRef.setEjbRefName(name);
            ejblocalRef.setEjbRefType(ejbRefType);
            ejblocalRef.setEjbLink(ejbLink);
            ejblocalRef.addDescription(desc);
            this.ejbRefsList.add(ejblocalRef);
         }

         EjbReferenceDescriptionBean ejbRefDesBean = this.wlBean.createEjbReferenceDescription();
         ejbRefDesBean.setEjbRefName(name);
         ejbRefDesBean.setJNDIName(jndiName);
         this.ejbRefsDesList.add(ejbRefDesBean);
      } else {
         throw new DeploymentException("invalid value for ejb-ref-type: " + ejbRefType + ", valid values are: \"Entity\" and \"Session\"");
      }
   }

   boolean isResourceBound(String jndiRefName) {
      boolean retry;
      ResourceRefBean[] ejbRefName;
      int var4;
      int var5;
      do {
         try {
            ejbRefName = this.waBean.getResourceRefs();
            var4 = ejbRefName.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ResourceRefBean ref = ejbRefName[var5];
               if (ref.getResRefName().equals(jndiRefName)) {
                  return true;
               }
            }

            Iterator var9 = this.resRefsList.iterator();

            while(var9.hasNext()) {
               ResourceRefBean resourceRef = (ResourceRefBean)var9.next();
               if (resourceRef.getResRefName().equals(jndiRefName)) {
                  return true;
               }
            }

            retry = false;
         } catch (ConcurrentModificationException var8) {
            var8.printStackTrace();
            retry = true;
         }
      } while(retry);

      do {
         try {
            EjbLocalRefBean[] var10 = this.waBean.getEjbLocalRefs();
            var4 = var10.length;

            for(var5 = 0; var5 < var4; ++var5) {
               EjbLocalRefBean bean = var10[var5];
               if (bean.getEjbRefName().equals(jndiRefName)) {
                  return true;
               }
            }

            EjbRefBean[] var11 = this.waBean.getEjbRefs();
            var4 = var11.length;

            for(var5 = 0; var5 < var4; ++var5) {
               EjbRefBean bean = var11[var5];
               if (bean.getEjbRefName().equals(jndiRefName)) {
                  return true;
               }
            }

            ejbRefName = null;
            Iterator var14 = this.ejbRefsList.iterator();

            while(var14.hasNext()) {
               Object objResourceRef = var14.next();
               String ejbRefName;
               if (objResourceRef instanceof EjbRefBean) {
                  ejbRefName = ((EjbRefBean)objResourceRef).getEjbRefName();
               } else {
                  ejbRefName = ((EjbLocalRefBean)objResourceRef).getEjbRefName();
               }

               if (ejbRefName.equals(jndiRefName)) {
                  return true;
               }
            }

            retry = false;
         } catch (ConcurrentModificationException var7) {
            var7.printStackTrace();
            retry = true;
         }
      } while(retry);

      ResourceEnvRefBean[] var15 = this.waBean.getResourceEnvRefs();
      var4 = var15.length;

      for(var5 = 0; var5 < var4; ++var5) {
         ResourceEnvRefBean bean = var15[var5];
         if (bean.getResourceEnvRefName().equals(jndiRefName)) {
            return true;
         }
      }

      return false;
   }

   private void addWebComponentCreator() throws NamingException {
      if (this.servletContext.isJsfApplication()) {
         Context ctx = this.eBuilder.getRootContext();
         Context bea_ctx = (Context)ctx.lookup("bea");
         if (bea_ctx != null) {
            bea_ctx.bind("WebComponentCreator", this.servletContext.getComponentCreator());
         } else {
            throw new NamingException("Can't bind WebComponentCreator because of context bea not found. ");
         }
      }
   }

   private void removeWebComponentCreator() {
      if (this.servletContext.isJsfApplication()) {
         Context ctx = this.eBuilder.getRootContext();

         try {
            Context bea_ctx = (Context)ctx.lookup("bea");
            if (bea_ctx != null) {
               bea_ctx.unbind("WebComponentCreator");
            }
         } catch (NamingException var3) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Error removing context - WebComponentCreator", var3);
            }
         }

      }
   }

   Environment getEnvironmentBuilder() {
      return this.eBuilder;
   }

   private URL getWebModuleContextRootURL() {
      try {
         ServerChannel channel = ServerChannelManager.findLocalServerChannel(ProtocolHandlerHTTP.PROTOCOL_HTTP);
         if (channel != null) {
            return new URL("http", channel.getAddress(), channel.getPort(), this.servletContext.getContextPath());
         }

         channel = ServerChannelManager.findLocalServerChannel(ProtocolHandlerHTTPS.PROTOCOL_HTTPS);
         if (channel != null) {
            return new URL("https", channel.getAddress(), channel.getPort(), this.servletContext.getContextPath());
         }
      } catch (MalformedURLException var2) {
      }

      return null;
   }

   private Context getNonReplicatedJavaGlobalContext() throws NamingException {
      weblogic.jndi.Environment env = new weblogic.jndi.Environment();
      env.setReplicateBindings(false);
      env.setCreateIntermediateContexts(true);
      Context globalNamingRootCtx = env.getInitialContext();
      return (Context)globalNamingRootCtx.lookup("java:global");
   }

   private void bindWebModuleContextRootURL() throws NamingException {
      if (this.webModuleContextRootURL != null) {
         this.eBuilder.getAppNSContext().bind(this.getAppScopeJndiNameForWebModuleContextRootURL(), this.webModuleContextRootURL);
         if (DEBUG.isDebugEnabled()) {
            DEBUG.debug("Web module env [" + this.servletContext.getApplicationId() + ":" + this.servletContext.getModuleName() + "] bound web module context root url with value: " + this.webModuleContextRootURL);
         }

      }
   }

   private void unbindWebModuleContextRootURL() {
      if (this.webModuleContextRootURL != null) {
         try {
            this.eBuilder.getAppNSContext().unbind(this.getAppScopeJndiNameForWebModuleContextRootURL());
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Web module env [" + this.servletContext.getApplicationId() + ":" + this.servletContext.getModuleName() + "] unbound web module context root url");
            }
         } catch (NamingException var2) {
            if (DEBUG.isDebugEnabled()) {
               DEBUG.debug("Error unbinding web module context root url", var2);
            }
         }

      }
   }

   private String getGlobalScopeJndiNameForWebModuleContextRootURL() {
      StringBuilder sb = new StringBuilder();
      if (this.servletContext.getApplicationContext().isEar()) {
         sb.append(this.servletContext.getApplicationName()).append("/");
      }

      sb.append(this.servletContext.getModuleName()).append("!ROOT");
      return sb.toString();
   }

   private String getAppScopeJndiNameForWebModuleContextRootURL() {
      return this.servletContext.getModuleName() + "!ROOT";
   }

   static {
      DEBUG = WebAppModule.DEBUG;
   }
}
