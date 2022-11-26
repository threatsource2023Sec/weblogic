package weblogic.security.utils;

import com.bea.common.security.SecurityLogger;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import weblogic.management.security.ResourceIdInfo;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.spi.Resource;

public final class ResourceUtils {
   public static String[] listRegisteredResourceTypes() {
      return weblogic.security.providers.utils.ResourceUtils.listRegisteredResourceTypes();
   }

   public static boolean isResourceTypeRegistered(AuthenticatedSubject subject, ResourceIdInfo info) throws IllegalArgumentException {
      if (info == null) {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
      } else {
         Class proxyClass = weblogic.security.providers.utils.ResourceIdInfo.class;
         weblogic.security.providers.utils.ResourceIdInfo providerResourceIdInfo = (weblogic.security.providers.utils.ResourceIdInfo)Proxy.newProxyInstance(proxyClass.getClassLoader(), new Class[]{ResourceIdInfo.class, proxyClass}, new InvocationHandlerImpl(info));
         return weblogic.security.providers.utils.ResourceUtils.isResourceTypeRegistered(subject, providerResourceIdInfo);
      }
   }

   public static void registerResourceType(AuthenticatedSubject subject, ResourceIdInfo info) throws IllegalArgumentException {
      if (info == null) {
         throw new IllegalArgumentException(SecurityLogger.getNoResourceType());
      } else {
         Class proxyClass = weblogic.security.providers.utils.ResourceIdInfo.class;
         weblogic.security.providers.utils.ResourceIdInfo providerResourceIdInfo = (weblogic.security.providers.utils.ResourceIdInfo)Proxy.newProxyInstance(proxyClass.getClassLoader(), new Class[]{ResourceIdInfo.class, proxyClass}, new InvocationHandlerImpl(info));
         weblogic.security.providers.utils.ResourceUtils.registerResourceType(subject, providerResourceIdInfo);
      }
   }

   public static String getResourceIdFromMap(Map resourceData) throws IllegalArgumentException {
      return weblogic.security.providers.utils.ResourceUtils.getResourceIdFromMap(resourceData);
   }

   public static Map getMapFromResourceId(String resourceId) throws IllegalArgumentException {
      return weblogic.security.providers.utils.ResourceUtils.getMapFromResourceId(resourceId);
   }

   public static String[] getResourceKeyNames(String resourceType) throws IllegalArgumentException {
      return weblogic.security.providers.utils.ResourceUtils.getResourceKeyNames(resourceType);
   }

   public static String[] getParentResourceIds(String resourceId) throws IllegalArgumentException {
      return weblogic.security.providers.utils.ResourceUtils.getParentResourceIds(resourceId);
   }

   public static String getResourceTypeNameFilter(String resType) throws IllegalArgumentException {
      return weblogic.security.providers.utils.ResourceUtils.getResourceTypeNameFilter(resType);
   }

   public static SearchHelper getApplicationSearchHelper(String appName) throws IllegalArgumentException {
      return (SearchHelper)Proxy.newProxyInstance(SearchHelper.class.getClassLoader(), new Class[]{weblogic.security.providers.utils.ResourceUtils.SearchHelper.class, SearchHelper.class}, new InvocationHandlerImpl(weblogic.security.providers.utils.ResourceUtils.getApplicationSearchHelper(appName)));
   }

   public static SearchHelper getComponentSearchHelper(String compName, String compType, String appName) throws IllegalArgumentException {
      return (SearchHelper)Proxy.newProxyInstance(SearchHelper.class.getClassLoader(), new Class[]{weblogic.security.providers.utils.ResourceUtils.SearchHelper.class, SearchHelper.class}, new InvocationHandlerImpl(weblogic.security.providers.utils.ResourceUtils.getComponentSearchHelper(compName, compType, appName)));
   }

   public static SearchHelper getChildSearchHelper(String resourceId) throws IllegalArgumentException {
      return (SearchHelper)Proxy.newProxyInstance(SearchHelper.class.getClassLoader(), new Class[]{weblogic.security.providers.utils.ResourceUtils.SearchHelper.class, SearchHelper.class}, new InvocationHandlerImpl(weblogic.security.providers.utils.ResourceUtils.getChildSearchHelper(resourceId)));
   }

   public static ResourceIdInfo getResourceIdInfo(String resType) {
      weblogic.security.providers.utils.ResourceIdInfo idInfo = weblogic.security.providers.utils.ResourceUtils.getResourceIdInfo(resType);
      return idInfo == null ? null : (ResourceIdInfo)Proxy.newProxyInstance(ResourceIdInfo.class.getClassLoader(), new Class[]{weblogic.security.providers.utils.ResourceIdInfo.class, ResourceIdInfo.class}, new InvocationHandlerImpl(idInfo));
   }

   public static SearchHelper getRepeatingActionsSearchHelper(String resourceId) throws IllegalArgumentException {
      weblogic.security.providers.utils.ResourceUtils.SearchHelper helper = weblogic.security.providers.utils.ResourceUtils.getRepeatingActionsSearchHelper(resourceId);
      return helper == null ? null : (SearchHelper)Proxy.newProxyInstance(SearchHelper.class.getClassLoader(), new Class[]{weblogic.security.providers.utils.ResourceUtils.SearchHelper.class, SearchHelper.class}, new InvocationHandlerImpl(helper));
   }

   public static String getResourceIdNameFilter(String resourceId) throws IllegalArgumentException {
      return weblogic.security.providers.utils.ResourceUtils.getResourceIdNameFilter(resourceId);
   }

   public static Resource getScopedResource(String resourceId) throws IllegalArgumentException {
      return weblogic.security.providers.utils.ResourceUtils.getScopedResource(resourceId);
   }

   public static String escapeSearchChars(String searchData) {
      return weblogic.security.providers.utils.ResourceUtils.escapeSearchChars(searchData);
   }

   public static String unescapeChars(String data) {
      return weblogic.security.providers.utils.ResourceUtils.unescapeChars(data);
   }

   public interface SearchHelper {
      String getNameFilter();

      boolean isValid(String var1);
   }

   private static class InvocationHandlerImpl implements InvocationHandler {
      private Object delegate;

      private InvocationHandlerImpl(Object o) {
         this.delegate = o;
      }

      public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
         return method.invoke(this.delegate, args);
      }

      // $FF: synthetic method
      InvocationHandlerImpl(Object x0, Object x1) {
         this(x0);
      }
   }
}
