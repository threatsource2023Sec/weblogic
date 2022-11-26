package weblogic.servlet.internal;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.security.DeclareRoles;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.ServletRequestListener;
import javax.servlet.ServletSecurityElement;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionIdListener;
import javax.servlet.http.HttpSessionListener;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.BuilderHelper;
import weblogic.descriptor.DescriptorBean;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.dd.xml.J2eeAnnotationProcessor;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.FilterMappingBean;
import weblogic.j2ee.descriptor.J2eeClientEnvironmentBean;
import weblogic.j2ee.descriptor.ListenerBean;
import weblogic.j2ee.descriptor.MultipartConfigBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.RunAsBean;
import weblogic.j2ee.descriptor.SecurityConstraintBean;
import weblogic.j2ee.descriptor.SecurityRoleBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebResourceCollectionBean;
import weblogic.server.GlobalServiceLocator;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.annotation.WLFilter;
import weblogic.servlet.annotation.WLInitParam;
import weblogic.servlet.annotation.WLServlet;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.websocket.WebSocketListener;
import weblogic.websocket.annotation.WebSocket;

public class WebAnnotationProcessor extends J2eeAnnotationProcessor {
   private static Class webSocketServletClass;

   public void processWLServletAnnotation(Class clz, WebAppBean bean) {
      if (clz.isAnnotationPresent(WebServlet.class)) {
         Loggable logger = HTTPLogger.logIllegalWLServletAnnotationLoggable();
         logger.log();
         this.addProcessingError(logger.getMessage());
      } else {
         WebComponentContributor.dbg("Processing @WLServlet annotation on " + clz);
         HTTPLogger.logWLAnnotationDeprecated("WLServlet", this.getAppNameFromClassLoader(clz), clz.getName());
         WLServlet annotation = (WLServlet)clz.getAnnotation(WLServlet.class);
         WLInitParam[] params = annotation.initParams();
         Map initParam = null;
         if (params != null) {
            initParam = new HashMap(params.length);
            WLInitParam[] var6 = params;
            int var7 = params.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               WLInitParam param = var6[var8];
               initParam.put(param.name(), param.value());
            }
         }

         String[] urlPatterns = annotation.mapping();
         this.processServletAnnotation(bean, clz, this.getName(annotation.name(), clz), initParam, urlPatterns, annotation.loadOnStartup(), annotation.runAs(), false);
      }
   }

   public void processWLFilterAnnotation(Class clz, WebAppBean bean) {
      if (clz.isAnnotationPresent(WebFilter.class)) {
         Loggable logger = HTTPLogger.logIllegalWLFilterAnnotationLoggable();
         logger.log();
         this.addProcessingError(logger.getMessage());
      } else {
         WebComponentContributor.dbg("Processing @WLFilter annotation on " + clz);
         HTTPLogger.logWLAnnotationDeprecated("WLFilter", this.getAppNameFromClassLoader(clz), clz.getName());
         WLFilter annotation = (WLFilter)clz.getAnnotation(WLFilter.class);
         WLInitParam[] params = annotation.initParams();
         Map initParam = null;
         if (params != null) {
            initParam = new HashMap(params.length);
            WLInitParam[] var6 = params;
            int var7 = params.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               WLInitParam param = var6[var8];
               initParam.put(param.name(), param.value());
            }
         }

         String[] urlPattern = annotation.mapping();
         this.processFilterAnnotation(bean, clz, this.getName(annotation.name(), clz), initParam, urlPattern, (String[])null, (String[])null, false);
      }
   }

   public void processWebServletAnnotation(Class clz, WebAppBean bean) {
      WebServlet annotation = (WebServlet)clz.getAnnotation(WebServlet.class);
      WebComponentContributor.dbg("Processing @WebServlet annotation on " + clz);
      if (this.validateValueAndUrlPatterns(clz, annotation)) {
         WebInitParam[] params = annotation.initParams();
         Map initParam = null;
         if (params != null) {
            initParam = new HashMap(params.length);
            WebInitParam[] var6 = params;
            int var7 = params.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               WebInitParam param = var6[var8];
               initParam.put(param.name(), param.value());
            }
         }

         String[] urlPatterns = this.buildAndValidateUrlPatterns(annotation);
         this.processServletAnnotation(bean, clz, this.getSpecCompliantName(annotation.name(), clz), initParam, urlPatterns, annotation.loadOnStartup(), (String)null, annotation.asyncSupported());
      }
   }

   private String[] buildAndValidateUrlPatterns(WebServlet servlet) {
      return servlet.value().length != 0 ? servlet.value() : servlet.urlPatterns();
   }

   private String[] buildAndValidateUrlPatterns(WebFilter filter) {
      return filter.value().length != 0 ? filter.value() : filter.urlPatterns();
   }

   private boolean validateValueAndUrlPatterns(Class clazz, Annotation an) {
      weblogic.logging.Loggable logger;
      if (an instanceof WebServlet) {
         WebServlet servlet = (WebServlet)an;
         if (servlet.value().length != 0 && servlet.urlPatterns().length != 0) {
            logger = HTTPLogger.logExclusiveValueAndUrlPatternsInAnnotationLoggable(clazz.getName());
            this.addProcessingError(logger.getMessage());
            return false;
         }

         if (servlet.value().length == 0 && servlet.urlPatterns().length == 0) {
            logger = HTTPLogger.logMappingAttrsMustBePresentInWebServletAnnotationLoggable(clazz.getName());
            this.addProcessingError(logger.getMessage());
            return false;
         }
      } else if (an instanceof WebFilter) {
         WebFilter filter = (WebFilter)an;
         if (filter.value().length != 0 && filter.urlPatterns().length != 0) {
            logger = HTTPLogger.logExclusiveValueAndUrlPatternsInAnnotationLoggable(clazz.getName());
            this.addProcessingError(logger.getMessage());
            return false;
         }

         if (filter.value().length == 0 && filter.urlPatterns().length == 0 && filter.servletNames().length == 0) {
            logger = HTTPLogger.logMappingAttrsMustBePresentInWebFilterAnnotationLoggable(clazz.getName());
            this.addProcessingError(logger.getMessage());
            return false;
         }
      }

      return true;
   }

   public void processWebFilterAnnotation(Class clz, WebAppBean bean) {
      WebFilter annotation = (WebFilter)clz.getAnnotation(WebFilter.class);
      WebComponentContributor.dbg("Processing @WebFilter annotation on " + clz);
      if (this.validateValueAndUrlPatterns(clz, annotation)) {
         WebInitParam[] params = annotation.initParams();
         Map initParam = null;
         if (params != null) {
            initParam = new HashMap(params.length);
            WebInitParam[] var6 = params;
            int var7 = params.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               WebInitParam param = var6[var8];
               initParam.put(param.name(), param.value());
            }
         }

         String[] urlPatterns = this.buildAndValidateUrlPatterns(annotation);
         String[] dispatchers = null;
         DispatcherType[] dispatcherTypes = annotation.dispatcherTypes();
         if (dispatcherTypes != null && dispatcherTypes.length > 0) {
            dispatchers = new String[dispatcherTypes.length];

            for(int i = 0; i < dispatcherTypes.length; ++i) {
               if (dispatcherTypes[i] == DispatcherType.REQUEST) {
                  dispatchers[i] = "REQUEST";
               } else if (dispatcherTypes[i] == DispatcherType.FORWARD) {
                  dispatchers[i] = "FORWARD";
               } else if (dispatcherTypes[i] == DispatcherType.INCLUDE) {
                  dispatchers[i] = "INCLUDE";
               } else if (dispatcherTypes[i] == DispatcherType.ASYNC) {
                  dispatchers[i] = "ASYNC";
               } else if (dispatcherTypes[i] == DispatcherType.ERROR) {
                  dispatchers[i] = "ERROR";
               }
            }
         }

         this.processFilterAnnotation(bean, clz, this.getSpecCompliantName(annotation.filterName(), clz), initParam, urlPatterns, annotation.servletNames(), dispatchers, annotation.asyncSupported());
      }
   }

   public void processWebListenerAnnotation(Class clz, WebAppBean bean) {
      WebComponentContributor.dbg("Processing @WebListener annotation on " + clz);
      if (!ServletContextListener.class.isAssignableFrom(clz) && !ServletContextAttributeListener.class.isAssignableFrom(clz) && !ServletRequestListener.class.isAssignableFrom(clz) && !ServletRequestAttributeListener.class.isAssignableFrom(clz) && !HttpSessionListener.class.isAssignableFrom(clz) && !HttpSessionIdListener.class.isAssignableFrom(clz) && !HttpSessionAttributeListener.class.isAssignableFrom(clz)) {
         Loggable logger = HTTPLogger.logIllegalWebListenerAnnotationLoggable();
         logger.log();
         this.addProcessingError(logger.getMessage());
      }

      ListenerBean[] var7 = bean.getListeners();
      int var4 = var7.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ListenerBean l = var7[var5];
         if (l.getListenerClass().equals(clz.getName())) {
            return;
         }
      }

      bean.createListener().setListenerClass(clz.getName());
   }

   public void processMultipartConfigAnnotation(Class clz, WebAppBean bean) {
      MultipartConfig annotation = (MultipartConfig)clz.getAnnotation(MultipartConfig.class);
      WebComponentContributor.dbg("Processing @MultipartConfig annotation on " + clz);
      ServletBean[] sbs = bean.getServlets();
      if (sbs != null) {
         ServletBean theBean = this.findServletBeanbyClassName(sbs, clz.getName());
         if (theBean != null) {
            MultipartConfigBean mcbean = theBean.getMultipartConfig();
            if (mcbean == null) {
               mcbean = theBean.createMultipartConfig();
               mcbean.setLocation(annotation.location());
               mcbean.setFileSizeThreshold(annotation.fileSizeThreshold());
               mcbean.setMaxFileSize(annotation.maxFileSize());
               mcbean.setMaxRequestSize(annotation.maxRequestSize());
            } else {
               if (!mcbean.isLocationSet()) {
                  mcbean.setLocation(annotation.location());
               }

               if (!mcbean.isFileSizeThresholdSet()) {
                  mcbean.setFileSizeThreshold(annotation.fileSizeThreshold());
               }

               if (!mcbean.isMaxFileSizeSet()) {
                  mcbean.setMaxFileSize(annotation.maxFileSize());
               }

               if (!mcbean.isMaxRequestSizeSet()) {
                  mcbean.setMaxRequestSize(annotation.maxRequestSize());
               }
            }
         }

      }
   }

   public void processServletSecurityAnnotation(Class clz, WebAppBean bean) {
      WebComponentContributor.dbg("Processing @ServletSecurity annotation on " + clz);
      Set webResourcesToDefine = this.findServletUrlPatterns(clz, bean);
      if (!webResourcesToDefine.isEmpty()) {
         Collection definedWebResources = this.findSecurityConstraintWebResources(bean);
         webResourcesToDefine.removeAll(definedWebResources);
         if (!webResourcesToDefine.isEmpty()) {
            ServletSecurity annotation = (ServletSecurity)clz.getAnnotation(ServletSecurity.class);
            ServletSecurityElement sse = new ServletSecurityElement(annotation);

            try {
               SecurityConstraintHelper.checkServletSecurityElement(sse);
            } catch (IllegalArgumentException var9) {
               Loggable logger = HTTPLogger.logIllegalServletSecurityAnnotationLoggable(var9.getMessage());
               logger.log();
               this.addProcessingError(logger.getMessage());
               return;
            }

            SecurityConstraintHelper.processServletSecurityElement(bean, webResourcesToDefine, sse, true);
         }
      }
   }

   public void processWebSocketAnnotation(Class clz, WebAppBean bean) {
      WebComponentContributor.dbg("Processing @WebSocket annotation on " + clz);
      WebSocket annotation = (WebSocket)clz.getAnnotation(WebSocket.class);
      Map initParam = new HashMap();
      initParam.put(WebSocketListener.class.getName(), clz.getName());
      Class var5 = WebAnnotationProcessor.class;
      synchronized(WebAnnotationProcessor.class) {
         if (webSocketServletClass == null) {
            ServiceLocator serviceLocator = GlobalServiceLocator.getServiceLocator();
            ActiveDescriptor descriptor = serviceLocator.getBestDescriptor(BuilderHelper.createContractFilter(WebSocketServletService.class.getName()));
            serviceLocator.reifyDescriptor(descriptor);
            webSocketServletClass = descriptor.getImplementationClass();
         }
      }

      this.processServletAnnotation(bean, webSocketServletClass, clz.getName(), initParam, annotation.pathPatterns(), -1, "", false);
   }

   private Collection findSecurityConstraintWebResources(WebAppBean webAppBean) {
      ArrayList urlPatternSet = null;
      SecurityConstraintBean[] scbs = webAppBean.getSecurityConstraints();
      if (scbs != null) {
         SecurityConstraintBean[] var4 = scbs;
         int var5 = scbs.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            SecurityConstraintBean scb = var4[var6];
            WebResourceCollectionBean[] wrcbs = scb.getWebResourceCollections();
            if (wrcbs != null) {
               WebResourceCollectionBean[] var9 = wrcbs;
               int var10 = wrcbs.length;

               for(int var11 = 0; var11 < var10; ++var11) {
                  WebResourceCollectionBean wrcb = var9[var11];
                  String[] urlPatterns = wrcb.getUrlPatterns();
                  if (urlPatterns != null && urlPatterns.length > 0) {
                     if (urlPatternSet == null) {
                        urlPatternSet = new ArrayList();
                     }

                     urlPatternSet.addAll(Arrays.asList(urlPatterns));
                  }
               }
            }
         }
      }

      return (Collection)(urlPatternSet == null ? Collections.EMPTY_SET : urlPatternSet);
   }

   private ServletBean findServletBeanbyClassName(ServletBean[] sbs, String className) {
      ServletBean[] var3 = sbs;
      int var4 = sbs.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         ServletBean sb = var3[var5];
         if (className.equals(sb.getServletClass())) {
            return sb;
         }
      }

      return null;
   }

   private Collection findServletBeansbyClassName(ServletBean[] sbs, String className) {
      ArrayList servletBeans = null;
      ServletBean[] var4 = sbs;
      int var5 = sbs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         ServletBean sb = var4[var6];
         if (className.equals(sb.getServletClass())) {
            if (servletBeans == null) {
               servletBeans = new ArrayList();
            }

            servletBeans.add(sb);
         }
      }

      return (Collection)(servletBeans == null ? Collections.EMPTY_LIST : servletBeans);
   }

   private Set findSecurityAnnotatedServlets(Class servletClass, WebAppBean webAppBean) {
      ServletBean[] sbs = webAppBean.getServlets();
      if (sbs == null) {
         return Collections.emptySet();
      } else {
         Collection servletBeans = this.findServletBeansbyClassName(sbs, servletClass.getName());
         HashSet servletNames = new HashSet();
         Iterator var6 = servletBeans.iterator();

         while(var6.hasNext()) {
            ServletBean sb = (ServletBean)var6.next();
            servletNames.add(sb.getServletName());
         }

         ServletBean[] var18 = sbs;
         int var19 = sbs.length;

         for(int var8 = 0; var8 < var19; ++var8) {
            ServletBean sb = var18[var8];
            if (sb.getServletClass() != null && !sb.getServletClass().equals(servletClass.getName())) {
               try {
                  Class otherServletClass = Class.forName(sb.getServletClass(), true, servletClass.getClassLoader());
                  if (servletClass.isAssignableFrom(otherServletClass)) {
                     Annotation[] annotations = otherServletClass.getDeclaredAnnotations();
                     boolean hasServletSecurityAnnotation = false;
                     Annotation[] var13 = annotations;
                     int var14 = annotations.length;

                     for(int var15 = 0; var15 < var14; ++var15) {
                        Annotation annotation = var13[var15];
                        if (ServletSecurity.class.isAssignableFrom(annotation.annotationType())) {
                           hasServletSecurityAnnotation = true;
                           break;
                        }
                     }

                     if (!hasServletSecurityAnnotation) {
                        servletNames.add(sb.getServletName());
                     }
                  }
               } catch (ClassNotFoundException var17) {
               }
            }
         }

         return servletNames;
      }
   }

   private Set findServletUrlPatterns(Class servletClass, WebAppBean webAppBean) {
      HashSet urlPatternSet = null;
      Set servletNames = this.findSecurityAnnotatedServlets(servletClass, webAppBean);
      if (servletNames.isEmpty()) {
         return Collections.emptySet();
      } else {
         ServletMappingBean[] smbs = webAppBean.getServletMappings();
         if (smbs != null) {
            ServletMappingBean[] var6 = smbs;
            int var7 = smbs.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               ServletMappingBean smb = var6[var8];
               if (servletNames.contains(smb.getServletName())) {
                  String[] urlPatterns = smb.getUrlPatterns();
                  if (urlPatterns != null && urlPatterns.length > 0) {
                     if (urlPatternSet == null) {
                        urlPatternSet = new HashSet();
                     }

                     urlPatternSet.addAll(Arrays.asList(urlPatterns));
                  }
               }
            }
         }

         return (Set)(urlPatternSet == null ? Collections.EMPTY_SET : urlPatternSet);
      }
   }

   public void processServlet(WebAppBean bean, ServletBean servlet, Class clz) {
      this.processJ2eeAnnotations(clz, bean);
      this.processRunAs(clz, (DescriptorBean)servlet);
      this.processDeclareRoles(clz, bean);
   }

   public void processDeclareRoles(Class beanClass, WebAppBean webBean) {
      if (beanClass.isAnnotationPresent(DeclareRoles.class)) {
         DeclareRoles dr = (DeclareRoles)beanClass.getAnnotation(DeclareRoles.class);
         this.perhapsDeclareRoles(webBean, dr.value());
      }

   }

   protected void perhapsDeclareRunAs(DescriptorBean identityBean, String roleName) {
      ServletBean servletBean = (ServletBean)identityBean;
      if (servletBean.getRunAs() == null) {
         RunAsBean rab = servletBean.createRunAs();
         rab.setRoleName(roleName);
      }

   }

   private void perhapsDeclareRoles(WebAppBean webBean, String[] roles) {
      Set definedRoles = new HashSet();
      SecurityRoleBean[] var4 = webBean.getSecurityRoles();
      int var5 = var4.length;

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         SecurityRoleBean srb = var4[var6];
         definedRoles.add(srb.getRoleName());
      }

      String[] var9 = roles;
      var5 = roles.length;

      for(var6 = 0; var6 < var5; ++var6) {
         String role = var9[var6];
         if (!definedRoles.contains(role)) {
            SecurityRoleBean srb = webBean.createSecurityRole();
            srb.setRoleName(role);
         }
      }

   }

   private void addUrlPatterns(WebAppBean webBean, String servletName, String[] urlPatterns, boolean alwaysAdd) {
      ServletMappingBean mapping = null;
      ServletMappingBean[] mappings = webBean.getServletMappings();
      if (mappings != null) {
         for(int count = 0; count < mappings.length && mapping == null; ++count) {
            if (mappings[count].getServletName() != null && mappings[count].getServletName().equals(servletName) || mappings[count].getServletName() == null && servletName == null) {
               mapping = mappings[count];
            }
         }
      }

      if (mapping == null) {
         mapping = webBean.createServletMapping();
         mapping.setServletName(servletName);
      }

      if (!notEmpty(mapping.getUrlPatterns())) {
         mapping.setUrlPatterns(urlPatterns);
      } else if (alwaysAdd) {
         List origPatterns = Arrays.asList(mapping.getUrlPatterns());

         for(int count = 0; count < urlPatterns.length; ++count) {
            if (!origPatterns.contains(urlPatterns[count])) {
               mapping.addUrlPattern(urlPatterns[count]);
            }
         }
      }

   }

   private String getName(String s, Class clz) {
      if (s == null || s.length() == 0) {
         s = clz.getSimpleName();
      }

      return s;
   }

   private String getSpecCompliantName(String s, Class clz) {
      if (s == null || s.length() == 0) {
         s = clz.getName();
      }

      return s;
   }

   private void processServletAnnotation(WebAppBean bean, Class clz, String name, Map initParam, String[] urlPatterns, int loadOnStartup, String runAs, boolean asyncSupported) {
      ServletBean servletBean = bean.lookupServlet(name);
      if (servletBean == null) {
         servletBean = bean.createServlet(name);
      }

      if (!servletBean.isServletClassSet()) {
         servletBean.setServletClass(clz.getName());
      }

      if (!servletBean.isLoadOnStartupSet() && loadOnStartup != -1) {
         servletBean.setLoadOnStartup(Integer.toString(loadOnStartup));
      }

      if (!servletBean.isAsyncSupportedSet()) {
         servletBean.setAsyncSupported(asyncSupported);
      }

      if (initParam != null) {
         Iterator keys = initParam.keySet().iterator();

         while(keys.hasNext()) {
            String key = (String)keys.next();
            ParamValueBean paramValueBean = servletBean.lookupInitParam(key);
            if (paramValueBean == null) {
               paramValueBean = servletBean.createInitParam(key);
            }

            if (!paramValueBean.isParamValueSet()) {
               paramValueBean.setParamValue((String)initParam.get(key));
            }
         }
      }

      if (notEmpty(urlPatterns)) {
         this.addUrlPatterns(bean, name, urlPatterns, !clz.isAnnotationPresent(WebServlet.class));
      }

      if (runAs != null && runAs.length() > 0 && !servletBean.isRunAsSet()) {
         RunAsBean runAsBean = servletBean.createRunAs();
         runAsBean.setRoleName(runAs);
      }

   }

   private void processFilterAnnotation(WebAppBean webBean, Class clz, String name, Map initParam, String[] urlPattern, String[] servletNames, String[] dispatchers, boolean asyncSupported) {
      FilterBean filterBean = webBean.lookupFilter(name);
      if (filterBean == null) {
         filterBean = webBean.createFilter(name);
      }

      if (!filterBean.isFilterClassSet()) {
         filterBean.setFilterClass(clz.getName());
      }

      if (!filterBean.isAsyncSupportedSet()) {
         filterBean.setAsyncSupported(asyncSupported);
      }

      if (initParam != null) {
         Iterator keys = initParam.keySet().iterator();

         while(keys.hasNext()) {
            String key = (String)keys.next();
            ParamValueBean paramValueBean = filterBean.lookupInitParam(key);
            if (paramValueBean == null) {
               paramValueBean = filterBean.createInitParam(key);
            }

            if (!paramValueBean.isParamValueSet()) {
               paramValueBean.setParamValue((String)initParam.get(key));
            }
         }
      }

      FilterMappingBean[] mapping = webBean.getFilterMappings();
      List matchedMappings = new LinkedList();
      if (mapping != null) {
         FilterMappingBean[] var19 = mapping;
         int var13 = mapping.length;

         for(int var14 = 0; var14 < var13; ++var14) {
            FilterMappingBean mappingBean = var19[var14];
            if (mappingBean != null && name.equals(mappingBean.getFilterName())) {
               matchedMappings.add(mappingBean);
            }
         }
      }

      if (!matchedMappings.isEmpty() && !clz.isAnnotationPresent(WLFilter.class)) {
         Iterator var22 = matchedMappings.iterator();

         while(var22.hasNext()) {
            FilterMappingBean mappingBean = (FilterMappingBean)var22.next();
            boolean isUrlPatternSet = notEmpty(mappingBean.getUrlPatterns());
            boolean isServletNameSet = notEmpty(mappingBean.getServletNames());
            boolean isDispatcherSet = notEmpty(mappingBean.getDispatchers());
            if (!isUrlPatternSet && notEmpty(urlPattern)) {
               mappingBean.setUrlPatterns(urlPattern);
            }

            if (!isServletNameSet && notEmpty(servletNames)) {
               mappingBean.setServletNames(servletNames);
            }

            if (!isDispatcherSet && notEmpty(dispatchers)) {
               mappingBean.setDispatchers(dispatchers);
            }
         }
      } else {
         FilterMappingBean bean = webBean.createFilterMapping();
         bean.setFilterName(name);
         if (notEmpty(urlPattern)) {
            bean.setUrlPatterns(urlPattern);
         }

         if (notEmpty(servletNames)) {
            bean.setServletNames(servletNames);
         }

         if (notEmpty(dispatchers)) {
            bean.setDispatchers(dispatchers);
         }
      }

   }

   private static boolean notEmpty(Object[] objs) {
      return objs != null && objs.length > 0;
   }

   private String getAppNameFromClassLoader(Class clz) {
      String appName = null;
      if (clz.getClassLoader() instanceof GenericClassLoader) {
         GenericClassLoader classLoader = (GenericClassLoader)clz.getClassLoader();
         if (classLoader.getAnnotation() != null) {
            appName = classLoader.getAnnotation().toString();
         }
      }

      return appName;
   }

   public void processJ2eeAnnotations(Class c, J2eeClientEnvironmentBean eg) {
      this.recordComponentClass(c);
      super.processJ2eeAnnotations(c, eg);
   }
}
