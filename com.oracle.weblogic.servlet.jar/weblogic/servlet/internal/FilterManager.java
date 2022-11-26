package weblogic.servlet.internal;

import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import weblogic.i18n.logging.Loggable;
import weblogic.j2ee.descriptor.FilterBean;
import weblogic.j2ee.descriptor.FilterMappingBean;
import weblogic.j2ee.descriptor.ParamValueBean;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.management.DeploymentException;
import weblogic.servlet.FilterUnavailableException;
import weblogic.servlet.HTTPLogger;
import weblogic.servlet.security.internal.WebAppSecurity;
import weblogic.servlet.utils.URLMapping;
import weblogic.servlet.utils.URLMappingFactory;

public final class FilterManager {
   public static final int REQUEST = 0;
   public static final int FORWARD = 1;
   public static final int INCLUDE = 2;
   public static final int ERROR = 3;
   public static final int ASYNC = 4;
   public static final int UNKNOWN = -1;
   private final WebAppServletContext context;
   private Map filters = new HashMap();
   private final List filterPatternList = new ArrayList();
   private int filterPatternListInsertionIndex = 0;
   private int filterServletListInsertionIndex = 0;
   private final List filterServletList = new ArrayList();
   private FilterWrapper reqEventsFilterWrapper;

   FilterManager(WebAppServletContext ctx) {
      this.context = ctx;
   }

   public boolean hasFilters() {
      return !this.filterPatternList.isEmpty() || !this.filterServletList.isEmpty();
   }

   void preloadFilters() throws DeploymentException {
      this.preloadReqEventFilter();
      Iterator it = this.filters.keySet().iterator();

      while(it.hasNext()) {
         FilterWrapper fw = (FilterWrapper)this.filters.get(it.next());
         if (!this.loadFilter(fw)) {
            it.remove();
         }
      }

   }

   boolean loadFilter(FilterWrapper filterWrapper) throws DeploymentException {
      Filter filter = filterWrapper.getFilter();
      if (filter == null) {
         String filterClassName = filterWrapper.getFilterClassName();

         try {
            filter = (Filter)this.context.createInstance(filterClassName);
            filterWrapper.setFilter((String)null, (Class)null, filter, false);
         } catch (Exception var5) {
            HTTPLogger.logCouldNotLoadFilter(this.context.getLogContext() + " " + filterClassName, var5);
            throw new DeploymentException(var5);
         }
      }

      Throwable e = this.initFilter(filterWrapper.getFilterName(), filterWrapper.getFilter(), filterWrapper.getInitParameters());
      return e == null;
   }

   private void preloadReqEventFilter() throws DeploymentException {
      String clsName = RequestEventsFilter.class.getName();
      this.reqEventsFilterWrapper = new FilterWrapper(clsName, clsName, (Map)null, this.context);
      this.loadFilter(this.reqEventsFilterWrapper);
      this.reqEventsFilterWrapper.setHeadFilter(true);
   }

   private static Map getParamsMap(ParamValueBean... params) {
      if (params != null && params.length != 0) {
         Map initArgs = new HashMap();
         ParamValueBean[] var2 = params;
         int var3 = params.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ParamValueBean param = var2[var4];
            if (!initArgs.containsKey(param.getParamName())) {
               initArgs.put(param.getParamName(), param.getParamValue());
            }
         }

         return initArgs;
      } else {
         return Collections.emptyMap();
      }
   }

   Throwable initFilter(String filterName, Filter filter, Map params) throws DeploymentException {
      FilterConfig cfg = new FilterConfigImpl(filterName, this.context, params);
      PrivilegedAction action = new FilterInitAction(filter, cfg);
      Throwable e = (Throwable)WebAppSecurity.getProvider().getAnonymousSubject().run((PrivilegedAction)action);
      if (e != null && !(e instanceof FilterUnavailableException)) {
         HTTPLogger.logCouldNotLoadFilter(filter.getClass().getName(), e);
         if (e instanceof ServletException && this.context.getConfigManager().isFailDeployOnFilterInitError()) {
            throw new DeploymentException(e);
         }
      }

      return e;
   }

   void registerFilter(String filterName, String filterClassName, String[] urlPatterns, String[] servletNames, Map initParams, String[] dispatchers) throws DeploymentException {
      FilterWrapper fw = new FilterWrapper(filterName, filterClassName, initParams, this.context);
      if (this.loadFilter(fw)) {
         EnumSet types = FilterManager.FilterInfo.translateDispatcherType(dispatchers, this.context, filterName);
         if (urlPatterns != null) {
            this.addMappingForUrlPatterns(filterName, types, true, urlPatterns);
         }

         if (servletNames != null) {
            this.addMappingForServletNames(filterName, types, true, servletNames);
         }

         this.filters.put(filterName, fw);
      }
   }

   FilterRegistrationImpl addFilter(String filterName, String className, Class filterClass, Filter filter) {
      FilterWrapper fw = (FilterWrapper)this.filters.get(filterName);
      if (fw != null) {
         if (!fw.isConfigurable()) {
            return null;
         }

         fw.setFilter(className, filterClass, filter, filter != null);
      }

      if (fw == null) {
         fw = new FilterWrapper(filterName, className, filterClass, filter, this.context);
         this.filters.put(filterName, fw);
      }

      return new FilterRegistrationImpl(this.context, fw);
   }

   FilterRegistration getFilterRegistration(String filterName) {
      return !this.filters.containsKey(filterName) ? null : new FilterRegistrationImpl(this.context, (FilterWrapper)this.filters.get(filterName));
   }

   public Map getFilterRegistrations() {
      Map registrations = new HashMap();
      Iterator var2 = this.filters.keySet().iterator();

      while(var2.hasNext()) {
         String filterName = (String)var2.next();
         registrations.put(filterName, new FilterRegistrationImpl(this.context, (FilterWrapper)this.filters.get(filterName)));
      }

      return registrations;
   }

   void addMappingForUrlPatterns(String filterName, EnumSet types, boolean isMatchAfter, String... urlPatterns) throws DeploymentException {
      if (urlPatterns != null && urlPatterns.length != 0) {
         URLMapping m = URLMappingFactory.createCompatibleURLMapping(this.context.getUrlMatchMap(), this.context.getServletClassLoader(), WebAppConfigManager.isCaseInsensitive(), WebAppSecurity.getProvider().getEnforceStrictURLPattern());
         String[] var6 = urlPatterns;
         int var7 = urlPatterns.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String pattern = var6[var8];
            if (URLMappingFactory.isInvalidUrlPattern(this.context.getUrlMatchMap(), pattern)) {
               throw new DeploymentException("The url-pattern, '" + pattern + "' is not valid");
            }

            String urlPattern = WebAppSecurity.fixupURLPattern(pattern);
            m.put(urlPattern, filterName);
         }

         if (isMatchAfter) {
            this.filterPatternList.add(new FilterInfo(filterName, m, this.context, types));
         } else {
            this.filterPatternList.add(this.filterPatternListInsertionIndex++, new FilterInfo(filterName, m, this.context, types));
         }

      }
   }

   void addMappingForServletNames(String filterName, EnumSet dispatcherTypes, boolean isMatchAfter, String... servletNames) {
      if (servletNames != null && servletNames.length != 0) {
         Set registered = new HashSet();
         String[] var6 = servletNames;
         int var7 = servletNames.length;

         for(int var8 = 0; var8 < var7; ++var8) {
            String servletName = var6[var8];
            if (servletName != null && !registered.contains(servletName)) {
               if (isMatchAfter) {
                  this.filterServletList.add(new FilterInfo(filterName, servletName, this.context, dispatcherTypes));
               } else {
                  this.filterServletList.add(this.filterServletListInsertionIndex++, new FilterInfo(filterName, servletName, this.context, dispatcherTypes));
               }

               registered.add(servletName);
            }
         }

      }
   }

   private void registerFilterMapping(FilterMappingBean bean) throws DeploymentException {
      String filterName = bean.getFilterName();
      String[] urlPatterns = bean.getUrlPatterns();
      EnumSet dispatcherTypes = FilterManager.FilterInfo.translateDispatcherType(bean.getDispatchers(), this.context, bean.getFilterName());
      this.addMappingForUrlPatterns(filterName, dispatcherTypes, true, urlPatterns);
      String[] servletNames = bean.getServletNames();
      this.addMappingForServletNames(filterName, dispatcherTypes, true, servletNames);
   }

   void registerServletFilters(WebAppBean webAppBean) throws DeploymentException {
      int var4;
      if (webAppBean.getFilters() != null) {
         FilterBean[] var2 = webAppBean.getFilters();
         int var3 = var2.length;

         for(var4 = 0; var4 < var3; ++var4) {
            FilterBean filter = var2[var4];
            String name = filter.getFilterName();
            String filterClass = filter.getFilterClass();
            boolean asyncSupported = filter.isAsyncSupported();
            FilterWrapper fw = new FilterWrapper(name, filterClass, asyncSupported, getParamsMap(filter.getInitParams()), this.context);
            this.filters.put(name, fw);
         }
      }

      FilterMappingBean[] filterMappings = webAppBean.getFilterMappings();
      if (filterMappings != null) {
         FilterMappingBean[] var11 = filterMappings;
         var4 = filterMappings.length;

         for(int var12 = 0; var12 < var4; ++var12) {
            FilterMappingBean filterMapping = var11[var12];
            this.registerFilterMapping(filterMapping);
         }
      }

   }

   public FilterChainImpl getFilterChain(ServletStub stub, ServletRequest rq, ServletResponse rsp, boolean filterReqEvents, int dispatcher) throws ServletException {
      ServletRequestImpl req = ServletRequestImpl.getOriginalRequest(rq);
      FilterChainImpl fci = null;
      if (filterReqEvents) {
         fci = new FilterChainImpl();
         fci.add(this.reqEventsFilterWrapper);
      }

      String includeURI = (String)rq.getAttribute("javax.servlet.include.request_uri");
      String uri;
      if (includeURI != null) {
         uri = includeURI.substring(((String)rq.getAttribute("javax.servlet.include.context_path")).length());
      } else if (dispatcher == 3 && rq.getAttribute("weblogic.servlet.errorPage") != null) {
         uri = (String)rq.getAttribute("weblogic.servlet.errorPage");
      } else {
         uri = req.getRelativeUri();
      }

      if (this.filters.size() > 0) {
         Iterator var10 = this.filterPatternList.iterator();

         String fltrName;
         while(var10.hasNext()) {
            FilterInfo fi = (FilterInfo)var10.next();
            if (fi.isApplicable(dispatcher)) {
               URLMapping m = fi.getMap();
               fltrName = (String)m.get(uri);
               if (fltrName != null) {
                  FilterWrapper wrapper = (FilterWrapper)this.filters.get(fltrName);
                  if (wrapper != null) {
                     if (fci == null) {
                        fci = new FilterChainImpl();
                     }

                     fci.add(wrapper);
                  }
               }
            }
         }

         String stubName = stub.getServletName();
         Iterator var17 = this.filterServletList.iterator();

         label62:
         while(true) {
            String svltName;
            do {
               FilterInfo fi;
               do {
                  if (!var17.hasNext()) {
                     break label62;
                  }

                  fi = (FilterInfo)var17.next();
               } while(!fi.isApplicable(dispatcher));

               fltrName = fi.getFilterName();
               svltName = fi.getServletName();
            } while(!svltName.equals(stubName) && !"*".equals(svltName));

            FilterWrapper wrapper = (FilterWrapper)this.filters.get(fltrName);
            if (wrapper != null) {
               if (fci == null) {
                  fci = new FilterChainImpl();
               }

               fci.add(wrapper);
            }
         }
      }

      if (fci == null) {
         return null;
      } else {
         fci.add(new TailFilter(stub), true);
         fci.setOrigRequest(req);
         fci.setOrigResponse(req.getResponse());
         return fci;
      }
   }

   void destroyFilters() {
      if (!this.filters.isEmpty()) {
         Iterator var1 = this.filters.keySet().iterator();

         while(var1.hasNext()) {
            String filterName = (String)var1.next();
            this.destroyFilter(filterName);
         }

         this.filters.clear();
         this.filterPatternList.clear();
         this.filterPatternListInsertionIndex = 0;
         this.filterServletList.clear();
         this.filterServletListInsertionIndex = 0;
      }

      this.destroyReqEventFilter();
   }

   private void destroyReqEventFilter() {
      if (this.reqEventsFilterWrapper != null) {
         this.destroyFilter(this.reqEventsFilterWrapper.getFilter());
         this.reqEventsFilterWrapper = null;
      }
   }

   void destroyFilter(String filterName) {
      FilterWrapper wrapper = (FilterWrapper)this.filters.get(filterName);
      if (wrapper != null) {
         Filter filter = wrapper.getFilter();
         if (filter != null) {
            Throwable t = this.destroyFilter(filter);
            if (t != null) {
               HTTPLogger.logFailedWhileDestroyingFilter(filterName, t);
            }

         }
      }
   }

   private Throwable destroyFilter(Filter filter) {
      Throwable var3;
      try {
         PrivilegedAction action = new FilterDestroyAction(filter);
         var3 = (Throwable)WebAppSecurity.getProvider().getAnonymousSubject().run((PrivilegedAction)action);
      } finally {
         if (this.context.getComponentCreator() != null) {
            this.context.getComponentCreator().notifyPreDestroy(filter);
         }

      }

      return var3;
   }

   public boolean isFilterRegistered(String filterClassName) {
      return this.filters.containsKey(filterClassName);
   }

   Collection getServletNameMappings(String filterName) {
      if (this.filterServletList.isEmpty()) {
         return Collections.EMPTY_LIST;
      } else {
         ArrayList servletNames = new ArrayList();
         Iterator it = this.filterServletList.iterator();

         while(it.hasNext()) {
            FilterInfo filterInfo = (FilterInfo)it.next();
            if (filterInfo.getFilterName().equals(filterName)) {
               servletNames.add(filterInfo.getServletName());
            }
         }

         return servletNames;
      }
   }

   Collection getUrlPatternMappings(String filterName) {
      if (this.filterPatternList.isEmpty()) {
         return Collections.EMPTY_LIST;
      } else {
         ArrayList urlPatterns = new ArrayList();
         Iterator it = this.filterPatternList.iterator();

         while(it.hasNext()) {
            FilterInfo filterInfo = (FilterInfo)it.next();
            if (filterName.equals(filterInfo.getFilterName())) {
               URLMapping map = filterInfo.getMap();
               urlPatterns.addAll(Arrays.asList(map.keys()));
            }
         }

         return urlPatterns;
      }
   }

   private static class FilterInfo {
      private String filterName;
      private String servletName;
      private URLMapping map;
      EnumSet dispatcherTypes;

      private FilterInfo(String filter, String servlet, WebAppServletContext context, EnumSet dispatcherTypes) {
         this(filter, (String)servlet, (URLMapping)null, dispatcherTypes, (WebAppServletContext)context);
      }

      private FilterInfo(String filter, URLMapping map, WebAppServletContext context, EnumSet dispatcherTypes) {
         this(filter, (String)null, (URLMapping)map, dispatcherTypes, (WebAppServletContext)context);
      }

      private FilterInfo(String filterName, String servletName, URLMapping map, EnumSet dispatcherTypes, WebAppServletContext context) {
         this.filterName = filterName;
         this.servletName = servletName;
         this.map = map;
         this.dispatcherTypes = dispatcherTypes;
         if (dispatcherTypes == null || dispatcherTypes.isEmpty()) {
            this.dispatcherTypes = EnumSet.noneOf(DispatcherType.class);
            this.dispatcherTypes.add(DispatcherType.REQUEST);
            if (context.getConfigManager().isFilterDispatchedRequestsEnabled()) {
               this.dispatcherTypes.add(DispatcherType.FORWARD);
               this.dispatcherTypes.add(DispatcherType.INCLUDE);
            }
         }

      }

      private String getFilterName() {
         return this.filterName;
      }

      private String getServletName() {
         return this.servletName;
      }

      private URLMapping getMap() {
         return this.map;
      }

      private static EnumSet translateDispatcherType(String[] dispatchers, WebAppServletContext context, String filter) throws DeploymentException {
         EnumSet ret = EnumSet.noneOf(DispatcherType.class);
         if (dispatchers == null) {
            ret.add(DispatcherType.REQUEST);
            return ret;
         } else {
            String[] var4 = dispatchers;
            int var5 = dispatchers.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String dispatcher = var4[var6];
               if (dispatcher.equals("REQUEST")) {
                  ret.add(DispatcherType.REQUEST);
               } else if (dispatcher.equals("FORWARD")) {
                  ret.add(DispatcherType.FORWARD);
               } else if (dispatcher.equals("INCLUDE")) {
                  ret.add(DispatcherType.INCLUDE);
               } else if (dispatcher.equals("ERROR")) {
                  ret.add(DispatcherType.ERROR);
               } else {
                  if (!dispatcher.equals("ASYNC")) {
                     Loggable l = HTTPLogger.logInvalidFilterDispatcherLoggable(context.getLogContext(), filter, dispatcher);
                     throw new DeploymentException(l.getMessage());
                  }

                  ret.add(DispatcherType.ASYNC);
               }
            }

            return ret;
         }
      }

      private boolean isApplicable(int dispatcher) {
         if (dispatcher == 0 && this.dispatcherTypes.contains(DispatcherType.REQUEST)) {
            return true;
         } else if (dispatcher == 1 && this.dispatcherTypes.contains(DispatcherType.FORWARD)) {
            return true;
         } else if (dispatcher == 2 && this.dispatcherTypes.contains(DispatcherType.INCLUDE)) {
            return true;
         } else if (dispatcher == 3 && this.dispatcherTypes.contains(DispatcherType.ERROR)) {
            return true;
         } else {
            return dispatcher == 4 && this.dispatcherTypes.contains(DispatcherType.ASYNC);
         }
      }

      // $FF: synthetic method
      FilterInfo(String x0, URLMapping x1, WebAppServletContext x2, EnumSet x3, Object x4) {
         this(x0, x1, x2, x3);
      }

      // $FF: synthetic method
      FilterInfo(String x0, String x1, WebAppServletContext x2, EnumSet x3, Object x4) {
         this(x0, x1, x2, x3);
      }
   }

   private final class FilterDestroyAction implements PrivilegedAction {
      private final Filter filter;

      FilterDestroyAction(Filter f) {
         this.filter = f;
      }

      public Object run() {
         try {
            this.filter.destroy();
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }
   }

   private final class FilterInitAction implements PrivilegedAction {
      private final Filter filter;
      private final FilterConfig cfg;

      FilterInitAction(Filter f, FilterConfig c) {
         this.filter = f;
         this.cfg = c;
      }

      public Object run() {
         try {
            this.filter.init(this.cfg);
            return null;
         } catch (Throwable var2) {
            return var2;
         }
      }
   }
}
