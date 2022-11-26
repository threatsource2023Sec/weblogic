package weblogic.jaxrs.server.extension;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.uri.UriComponent;
import org.glassfish.jersey.uri.UriComponent.Type;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.FilterMappingBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.ServletBean;
import weblogic.j2ee.descriptor.ServletMappingBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.wl.RestWebserviceDescriptionBean;
import weblogic.j2ee.descriptor.wl.RestWebservicesBean;
import weblogic.jaxrs.integration.internal.JAXRSIntegrationLogger;
import weblogic.servlet.internal.WebBaseModuleExtensionContext;

public final class JaxRsContainerInitializer {
   private static final DebugLogger LOGGER = DebugLogger.getDebugLogger("DebugRestJersey2Integration");
   private static final String DEFAULT_SERVLET_NAME = "JAX-RS/Jersey#";
   private static final String DEFAULT_SERVLET_MAPPING_URI = "/resources/*";
   private static final char PROVIDER_PACKAGES_DELIMITER = ';';
   private static final Set OLD_JERSEY_SERVLET_CONTAINERS = Sets.newHashSet(new String[]{"com.sun.jersey.spi.container.servlet.ServletContainer", "weblogic.jaxrs.server.portable.servlet.ServletContainer"});

   public static void initialize(WebBaseModuleExtensionContext context, ClassLoader classLoader, WebAppBean webAppBean, RestWebservicesBean restWebservicesBean, boolean isServletVersion2x) throws ClassNotFoundException {
      swapOldServletClasses(webAppBean.getServlets());
      Set applications = context.getWebAppHelper().getHandlesImpls(classLoader, new String[]{Application.class.getName()});
      Map servlets = createServletMap(webAppBean.getServlets());
      Map servletMappings = createServletMappings(webAppBean.getServletMappings());
      Map filterMappings = createFilterMappings(webAppBean.getFilterMappings());
      boolean hasJerseyServlet = false;
      if (!applications.isEmpty()) {
         Map applicationServletMappings = createApplicationToServletMappings(webAppBean.getServlets());
         Map applicationFilterMappings = createApplicationToFilterMappings(webAppBean.getFilters());

         boolean applicationHandled;
         for(Iterator var12 = applications.iterator(); var12.hasNext(); hasJerseyServlet |= applicationHandled) {
            String applicationName = (String)var12.next();
            applicationHandled = false;
            Class applicationClass = classLoader.loadClass(applicationName);
            ServletBean servlet = (ServletBean)servlets.get(applicationName);
            if (servlet != null) {
               applicationHandled |= addServletWithExistingRegistration(webAppBean, restWebservicesBean, servlet, applicationClass, servletMappings);
            }

            if (!applicationServletMappings.isEmpty() || !applicationFilterMappings.isEmpty()) {
               Set allServlets = (Set)applicationServletMappings.get(applicationName);
               if (allServlets != null) {
                  Iterator var18 = allServlets.iterator();

                  while(var18.hasNext()) {
                     ServletBean nextServlet = (ServletBean)var18.next();
                     if (servlet != nextServlet) {
                        applicationHandled |= addServletWithExistingRegistration(webAppBean, restWebservicesBean, nextServlet, applicationClass, servletMappings);
                     }
                  }
               }

               Set allFilters = (Set)applicationFilterMappings.get(applicationName);
               FilterBean nextFilter;
               if (allFilters != null) {
                  for(Iterator var22 = allFilters.iterator(); var22.hasNext(); applicationHandled |= addFilterWithExistingRegistration(restWebservicesBean, nextFilter, applicationClass, filterMappings)) {
                     nextFilter = (FilterBean)var22.next();
                  }
               }
            }

            if (!applicationHandled) {
               applicationHandled |= addServletWithApplication(webAppBean, restWebservicesBean, applicationClass, servletMappings, isServletVersion2x);
            }
         }
      }

      hasJerseyServlet |= addServletWithDefaultConfiguration(webAppBean, restWebservicesBean, servlets, servletMappings);
      hasJerseyServlet |= containsJerseyServletMapping(webAppBean);
      if (!hasJerseyServlet) {
         addDefaultSystemServlet(webAppBean, restWebservicesBean, context.getAnnotatedClasses(false, new Class[]{Path.class, Provider.class}), servlets.keySet(), servletMappings);
      }

   }

   private static boolean containsJerseyServletMapping(WebAppBean webAppBean) {
      boolean defaultUrlExisted = false;
      ServletMappingBean[] servletMappings = webAppBean.getServletMappings();
      int var4;
      int var5;
      if (servletMappings != null) {
         ServletMappingBean[] var3 = servletMappings;
         var4 = servletMappings.length;

         for(var5 = 0; var5 < var4; ++var5) {
            ServletMappingBean smb = var3[var5];
            String[] var7 = smb.getUrlPatterns();
            int var8 = var7.length;

            for(int var9 = 0; var9 < var8; ++var9) {
               String url = var7[var9];
               if ("/resources/*".equals(url)) {
                  defaultUrlExisted = true;
                  break;
               }
            }
         }
      }

      if (!defaultUrlExisted) {
         return false;
      } else {
         ServletBean[] var11 = webAppBean.getServlets();
         var4 = var11.length;

         for(var5 = 0; var5 < var4; ++var5) {
            ServletBean servlet = var11[var5];
            if (ServletContainer.class.getName().equals(servlet.getServletClass())) {
               return hasServletMapping(servlet, webAppBean.getServletMappings());
            }
         }

         return false;
      }
   }

   private static boolean hasServletMapping(ServletBean servlet, ServletMappingBean[] mappings) {
      ServletMappingBean[] var2 = mappings;
      int var3 = mappings.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServletMappingBean mapping = var2[var4];
         if (servlet.getServletName() != null && servlet.getServletName().equals(mapping.getServletName())) {
            return true;
         }
      }

      return false;
   }

   private static void swapOldServletClasses(ServletBean[] servlets) {
      ServletBean[] var1 = servlets;
      int var2 = servlets.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         ServletBean servletBean = var1[var3];
         if (OLD_JERSEY_SERVLET_CONTAINERS.contains(servletBean.getServletClass())) {
            ParamValueBean pb = servletBean.lookupInitParam("com.sun.jersey.config.property.packages");
            if (null != pb) {
               pb.setParamName("jersey.config.server.provider.packages");
            }

            initServletBean((WebAppBean)null, servletBean, (Map)null, (Collection)null, (String)null);
         }
      }

   }

   private static void swapServletClass(ServletBean servletBean) {
      String servletClass = servletBean.getServletClass();
      if (servletClass != null) {
         if (!ServletContainer.class.getName().equals(servletClass)) {
            JAXRSIntegrationLogger.logChangingServletClass(servletClass, ServletContainer.class.getName());
         }

         servletBean.setServletClass(ServletContainer.class.getName());
      }

   }

   private static boolean addServletWithApplication(WebAppBean webAppBean, RestWebservicesBean restWebservicesBean, Class appClass, Map servletMapping, boolean isServletVersion2x) {
      String appClassName = appClass.getName();
      String mappingPath = null;
      if (servletMapping.get(appClassName) == null) {
         ApplicationPath ap = (ApplicationPath)appClass.getAnnotation(ApplicationPath.class);
         if (ap == null) {
            JAXRSIntegrationLogger.logCanNotAddJerseyServlet(appClassName);
            return false;
         }

         mappingPath = createMappingPath(ap);
      }

      ServletBean servletBean = webAppBean.createServlet(appClassName);
      String servletName = servletBean.getServletName();
      initServletBean(webAppBean, servletBean, servletMapping, mappingPath);
      createRestWebserviceDescriptionBeanForServlet(restWebservicesBean, appClassName, servletName, (Set)servletMapping.get(servletName));
      if (isServletVersion2x) {
         servletBean.setServletClass(ServletContainer.class.getName());
         servletBean.createInitParam("javax.ws.rs.Application").setParamValue(appClassName);
      }

      return true;
   }

   private static boolean addServletWithExistingRegistration(WebAppBean webAppBean, RestWebservicesBean restWebservicesBean, ServletBean servletBean, Class appClass, Map servletMapping) {
      if (servletBean.getServletClass() == null) {
         ApplicationPath ap = (ApplicationPath)appClass.getAnnotation(ApplicationPath.class);
         if (ap != null) {
            initServletBean(webAppBean, servletBean, servletMapping, createMappingPath(ap));
         } else if (servletMapping.containsKey(servletBean.getServletName())) {
            initServletBean(webAppBean, servletBean, servletMapping);
         }
      }

      createRestWebserviceDescriptionBeanForServlet(restWebservicesBean, appClass.getName(), servletBean.getServletName(), (Set)servletMapping.get(servletBean.getServletName()));
      return true;
   }

   private static boolean addFilterWithExistingRegistration(RestWebservicesBean restWebservicesBean, FilterBean filterBean, Class appClass, Map filterToFilterMapping) {
      createRestWebserviceDescriptionBeanForFilter(restWebservicesBean, appClass.getName(), filterBean.getFilterName(), (Set)filterToFilterMapping.get(filterBean.getFilterName()));
      return true;
   }

   private static boolean addServletWithDefaultConfiguration(WebAppBean webAppBean, RestWebservicesBean restWebservicesBean, Map servletsMap, Map servletName2ServletMapping) {
      ServletBean servletBean = (ServletBean)servletsMap.get(Application.class.getName());
      if (servletBean != null) {
         if (servletBean.getServletClass() == null) {
            initServletBean(webAppBean, servletBean, servletName2ServletMapping);
         }

         createRestWebserviceDescriptionBeanForServlet(restWebservicesBean, (String)null, servletBean.getServletName(), (Set)servletName2ServletMapping.get(servletBean.getServletName()));
         return true;
      } else {
         Iterator var5 = servletsMap.values().iterator();

         ServletBean sb;
         do {
            do {
               do {
                  if (!var5.hasNext()) {
                     return false;
                  }

                  sb = (ServletBean)var5.next();
               } while(sb.getServletClass() == null);
            } while(!sb.getServletClass().equals(ServletContainer.class.getName()));
         } while(sb.lookupInitParam("jersey.config.server.provider.packages") == null && sb.lookupInitParam("jersey.config.server.provider.classnames") == null && sb.lookupInitParam("jersey.config.server.provider.classpath") == null);

         initServletBean(webAppBean, sb, servletName2ServletMapping);
         createRestWebserviceDescriptionBeanForServlet(restWebservicesBean, (String)null, sb.getServletName(), (Set)servletName2ServletMapping.get(sb.getServletName()));
         return true;
      }
   }

   private static void addDefaultSystemServlet(WebAppBean webAppBean, RestWebservicesBean restWebservicesBean, Set classes, Set servletNames, Map servletMappings) {
      ServletBean servletBean = webAppBean.createServlet(findSuitableNameForSystemJerseyServlet(servletNames));
      servletBean.setServletClass(ServletContainer.class.getName());
      initServletBean(webAppBean, servletBean, servletMappings, classes, "/resources/*");
      createRestWebserviceDescriptionBeanForServlet(restWebservicesBean, (String)null, servletBean.getServletName(), (Set)servletMappings.get(servletBean.getServletName()));
   }

   private static String findSuitableNameForSystemJerseyServlet(Set servletNames) {
      int index = 0;

      String name;
      do {
         ++index;
         name = "JAX-RS/Jersey#" + index;
      } while(servletNames.contains(name));

      return name;
   }

   private static String createMappingPath(ApplicationPath ap) {
      String path = UriComponent.decode(ap.value(), Type.PATH);
      if (!path.startsWith("/")) {
         path = "/" + path;
      }

      if (!path.endsWith("/*")) {
         if (path.endsWith("/")) {
            path = path + "*";
         } else {
            path = path + "/*";
         }
      }

      return path;
   }

   private static Map createServletMap(ServletBean[] servlets) {
      Map servletMap = new HashMap();
      ServletBean[] var2 = servlets;
      int var3 = servlets.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServletBean servlet = var2[var4];
         servletMap.put(servlet.getServletName(), servlet);
      }

      return servletMap;
   }

   private static Map createApplicationToServletMappings(ServletBean[] allServlets) {
      Map applicationClass2ServletMap = new HashMap();
      ServletBean[] var2 = allServlets;
      int var3 = allServlets.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServletBean servlet = var2[var4];
         ParamValueBean[] var6 = servlet.getInitParams();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ParamValueBean param = var6[var8];
            if ("javax.ws.rs.Application".equals(param.getParamName())) {
               Set servlets = (Set)applicationClass2ServletMap.get(param.getParamValue());
               if (servlets == null) {
                  servlets = new HashSet();
                  applicationClass2ServletMap.put(param.getParamValue(), servlets);
               }

               ((Set)servlets).add(servlet);
            }
         }
      }

      return applicationClass2ServletMap;
   }

   private static Map createApplicationToFilterMappings(FilterBean[] allFilters) {
      Map applicationClass2FilterMap = new HashMap();
      FilterBean[] var2 = allFilters;
      int var3 = allFilters.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         FilterBean filter = var2[var4];
         ParamValueBean[] var6 = filter.getInitParams();
         int var7 = var6.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            ParamValueBean param = var6[var8];
            if ("javax.ws.rs.Application".equals(param.getParamName())) {
               Set filters = (Set)applicationClass2FilterMap.get(param.getParamValue());
               if (filters == null) {
                  filters = new HashSet();
                  applicationClass2FilterMap.put(param.getParamValue(), filters);
               }

               ((Set)filters).add(filter);
            }
         }
      }

      return applicationClass2FilterMap;
   }

   private static Map createServletMappings(ServletMappingBean[] allServletMappings) {
      Map servletName2ServletMappingMap = new HashMap();
      ServletMappingBean[] var2 = allServletMappings;
      int var3 = allServletMappings.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         ServletMappingBean servletMapping = var2[var4];
         Set servletMappings = (Set)servletName2ServletMappingMap.get(servletMapping.getServletName());
         if (servletMappings == null) {
            servletMappings = new HashSet();
            servletName2ServletMappingMap.put(servletMapping.getServletName(), servletMappings);
         }

         ((Set)servletMappings).add(servletMapping);
      }

      return servletName2ServletMappingMap;
   }

   private static Map createFilterMappings(FilterMappingBean[] allFilterMappings) {
      Map filterName2FilterMappingMap = new HashMap();
      FilterMappingBean[] var2 = allFilterMappings;
      int var3 = allFilterMappings.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         FilterMappingBean filterMapping = var2[var4];
         Set filterMappings = (Set)filterName2FilterMappingMap.get(filterMapping.getFilterName());
         if (filterMappings == null) {
            filterMappings = new HashSet();
            filterName2FilterMappingMap.put(filterMapping.getFilterName(), filterMappings);
         }

         ((Set)filterMappings).add(filterMapping);
      }

      return filterName2FilterMappingMap;
   }

   private static void createRestWebserviceDescriptionBeanForServlet(RestWebservicesBean restWebservicesBeanBean, String applicationClassName, String servletName, Set servletMappings) {
      if (restWebservicesBeanBean != null) {
         RestWebserviceDescriptionBean restService = restWebservicesBeanBean.createRestWebserviceDescription();
         restService.setApplicationClassName(applicationClassName);
         restService.setServletName(servletName);
         restService.setApplicationBaseUris(createApplicationBaseUriForServletMappings(servletMappings));
      }

   }

   private static String[] createApplicationBaseUriForServletMappings(Set servletMappings) {
      Set baseUriList = new HashSet(servletMappings.size());
      Iterator var2 = servletMappings.iterator();

      while(var2.hasNext()) {
         ServletMappingBean servletMapping = (ServletMappingBean)var2.next();
         Collections.addAll(baseUriList, servletMapping.getUrlPatterns());
      }

      return (String[])baseUriList.toArray(new String[baseUriList.size()]);
   }

   private static void createRestWebserviceDescriptionBeanForFilter(RestWebservicesBean restWebservicesBeanBean, String applicationClassName, String filterName, Set filterMappings) {
      if (restWebservicesBeanBean != null) {
         RestWebserviceDescriptionBean restService = restWebservicesBeanBean.createRestWebserviceDescription();
         restService.setApplicationClassName(applicationClassName);
         restService.setFilterName(filterName);
         restService.setApplicationBaseUris(createApplicationBaseUriForFilterMappings(filterMappings));
      }

   }

   private static String[] createApplicationBaseUriForFilterMappings(Set filterMappings) {
      Set baseUriList = new HashSet(filterMappings.size());
      Iterator var2 = filterMappings.iterator();

      while(var2.hasNext()) {
         FilterMappingBean filterMapping = (FilterMappingBean)var2.next();
         Collections.addAll(baseUriList, filterMapping.getUrlPatterns());
      }

      return (String[])baseUriList.toArray(new String[baseUriList.size()]);
   }

   private static void initServletBean(WebAppBean webAppBean, ServletBean servletBean, Map servletName2ServletMapping) {
      initServletBean(webAppBean, servletBean, servletName2ServletMapping, (String)null);
   }

   private static void initServletBean(WebAppBean webAppBean, ServletBean servletBean, Map servletName2ServletMapping, String baseUri) {
      initServletBean(webAppBean, servletBean, servletName2ServletMapping, Collections.emptySet(), baseUri);
   }

   private static void initServletBean(WebAppBean webAppBean, ServletBean servletBean, Map servletMappings, Collection classes, String baseUri) {
      swapServletClass(servletBean);
      servletBean.setLoadOnStartup("1");
      servletBean.setAsyncSupported(true);
      if (classes != null && !classes.isEmpty()) {
         if (servletBean.lookupInitParam("jersey.config.server.provider.packages") == null) {
            ParamValueBean packagesProperty = servletBean.createInitParam("jersey.config.server.provider.packages");
            packagesProperty.setParamValue(computeProviderPackages(classes));
         } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Servlet named " + servletBean.getServletName() + " already has set " + "jersey.config.server.provider.packages" + " property, it means it will not be replaced.");
         }
      }

      if (baseUri != null) {
         Set mappings = (Set)servletMappings.get(servletBean.getServletName());
         if (mappings == null) {
            ServletMappingBean servletMappingBean = webAppBean.createServletMapping();
            servletMappingBean.setServletName(servletBean.getServletName());
            servletMappingBean.addUrlPattern(baseUri);
            Set mappings = new HashSet();
            mappings.add(servletMappingBean);
            servletMappings.put(servletBean.getServletName(), mappings);
         } else if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Servlet named " + servletBean.getServletName() + " already has associated mapping, it means it will not be replaced.");
         }
      }

   }

   private static String computeProviderPackages(Collection classes) {
      StringBuilder pkgBuffer = new StringBuilder();
      Set pkgSet = new HashSet(3);
      Iterator var3 = classes.iterator();

      while(var3.hasNext()) {
         Class c = (Class)var3.next();
         if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Examine class: " + c.getName());
         }

         int index = c.getName().lastIndexOf(".");
         if (index > 0) {
            String pkg = c.getName().substring(0, index);
            if (!pkgSet.contains(pkg)) {
               pkgSet.add(pkg);
               if (pkgSet.size() > 1) {
                  pkgBuffer.append(';');
               }

               pkgBuffer.append(pkg);
            }
         }
      }

      JAXRSIntegrationLogger.logListOfResourcePackages(pkgBuffer.toString());
      return pkgBuffer.toString();
   }
}
