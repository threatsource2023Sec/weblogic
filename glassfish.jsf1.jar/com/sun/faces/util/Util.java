package com.sun.faces.util;

import com.sun.faces.RIConstants;
import java.beans.FeatureDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.servlet.ServletContext;

public class Util {
   private static final Logger LOGGER;
   public static final String RENDERKIT_LOGGER = ".renderkit";
   public static final String TAGLIB_LOGGER = ".taglib";
   public static final String APPLICATION_LOGGER = ".application";
   public static final String CONTEXT_LOGGER = ".context";
   public static final String CONFIG_LOGGER = ".config";
   public static final String LIFECYCLE_LOGGER = ".lifecycle";
   public static final String TIMING_LOGGER = ".timing";
   private static final String unitTestModeEnabled = "com.sun.faces.unitTestModeEnabled";
   private static final String coreTLVEnabled = "com.sun.faces.coreTLVEnabled";
   private static final String htmlTLVEnabled = "com.sun.faces.htmlTLVEnabled";
   private static final String patternCacheKey = "com.sun.faces.patternCache";
   private static ThreadLocal nonFacesContextApplicationMap;

   private Util() {
      throw new IllegalStateException();
   }

   private static void setNonFacesContextApplicationMap(Map instance) {
      lazilyInitializeNonFacesContextApplicationMap();
      if (null == instance) {
         nonFacesContextApplicationMap.remove();
      } else {
         nonFacesContextApplicationMap.set(instance);
      }

   }

   private static void lazilyInitializeNonFacesContextApplicationMap() {
      if (null == nonFacesContextApplicationMap) {
         nonFacesContextApplicationMap = new ThreadLocal() {
            protected Map initialValue() {
               return null;
            }
         };
      }

   }

   private static Map getNonFacesContextApplicationMap() {
      lazilyInitializeNonFacesContextApplicationMap();
      return (Map)nonFacesContextApplicationMap.get();
   }

   private static Map getApplicationMap() {
      Map result = null;
      FacesContext context = FacesContext.getCurrentInstance();
      if (null != context) {
         ExternalContext externalContext = context.getExternalContext();
         if (null != externalContext) {
            result = externalContext.getApplicationMap();
         }
      }

      if (null == result) {
         result = getNonFacesContextApplicationMap();
         if (null == result) {
            result = new HashMap();
            setNonFacesContextApplicationMap((Map)result);
         }
      }

      return (Map)result;
   }

   private static Map getPatternCache() {
      Map appMap = getApplicationMap();
      Map result = (Map)appMap.get("com.sun.faces.patternCache");
      if (null == result) {
         result = new LRUMap(15);
         appMap.put("com.sun.faces.patternCache", result);
      }

      return (Map)result;
   }

   private static Map getPatternCache(ServletContext sc) {
      Map result = (Map)sc.getAttribute("com.sun.faces.patternCache");
      if (null == result) {
         result = new LRUMap(15);
         sc.setAttribute("com.sun.faces.patternCache", result);
      }

      return (Map)result;
   }

   public static boolean isPortletRequest(FacesContext context) {
      return context.getExternalContext().getRequestMap().get("javax.portlet.faces.phase") != null;
   }

   public static Object getListenerInstance(ValueExpression type, ValueExpression binding) {
      FacesContext faces = FacesContext.getCurrentInstance();
      Object instance = null;
      if (faces == null) {
         return null;
      } else {
         if (binding != null) {
            instance = binding.getValue(faces.getELContext());
         }

         if (instance == null && type != null) {
            try {
               instance = ReflectionUtils.newInstance((String)type.getValue(faces.getELContext()));
            } catch (Exception var5) {
               throw new AbortProcessingException(var5.getMessage(), var5);
            }

            if (binding != null) {
               binding.setValue(faces.getELContext(), instance);
            }
         }

         return instance;
      }
   }

   public static void setUnitTestModeEnabled(boolean enabled) {
      Map appMap = getApplicationMap();
      appMap.put("com.sun.faces.unitTestModeEnabled", enabled);
   }

   public static boolean isUnitTestModeEnabled() {
      Boolean result = false;
      Map appMap = getApplicationMap();
      return null == (result = (Boolean)appMap.get("com.sun.faces.unitTestModeEnabled")) ? false : result;
   }

   public static void setCoreTLVActive(boolean active) {
      Map appMap = getApplicationMap();
      appMap.put("com.sun.faces.coreTLVEnabled", active);
   }

   public static boolean isCoreTLVActive() {
      Boolean result = true;
      Map appMap = getApplicationMap();
      return null == (result = (Boolean)appMap.get("com.sun.faces.coreTLVEnabled")) ? true : result;
   }

   public static void setHtmlTLVActive(boolean active) {
      Map appMap = getApplicationMap();
      appMap.put("com.sun.faces.htmlTLVEnabled", active);
   }

   public static boolean isHtmlTLVActive() {
      Boolean result = true;
      Map appMap = getApplicationMap();
      return null == (result = (Boolean)appMap.get("com.sun.faces.htmlTLVEnabled")) ? true : result;
   }

   public static Class loadClass(String name, Object fallbackClass) throws ClassNotFoundException {
      ClassLoader loader = getCurrentLoader(fallbackClass);
      return name.charAt(0) == '[' ? Class.forName(name, true, loader) : loader.loadClass(name);
   }

   public static ClassLoader getCurrentLoader(Object fallbackClass) {
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      if (loader == null) {
         loader = fallbackClass.getClass().getClassLoader();
      }

      return loader;
   }

   public static void notNull(String varname, Object var) {
      if (var == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", varname));
      }
   }

   public static Locale getLocaleFromContextOrSystem(FacesContext context) {
      Locale temp = Locale.getDefault();
      Locale result = temp;
      UIViewRoot root;
      if (null != context && null != (root = context.getViewRoot()) && null == (result = root.getLocale())) {
         result = temp;
      }

      return result;
   }

   public static Converter getConverterForClass(Class converterClass, FacesContext context) {
      if (converterClass == null) {
         return null;
      } else {
         try {
            Application application = context.getApplication();
            return application.createConverter(converterClass);
         } catch (Exception var3) {
            return null;
         }
      }
   }

   public static Converter getConverterForIdentifer(String converterId, FacesContext context) {
      if (converterId == null) {
         return null;
      } else {
         try {
            Application application = context.getApplication();
            return application.createConverter(converterId);
         } catch (Exception var3) {
            return null;
         }
      }
   }

   public static StateManager getStateManager(FacesContext context) throws FacesException {
      return context.getApplication().getStateManager();
   }

   public static ViewHandler getViewHandler(FacesContext context) throws FacesException {
      Application application = context.getApplication();

      assert application != null;

      ViewHandler viewHandler = application.getViewHandler();

      assert viewHandler != null;

      return viewHandler;
   }

   public static boolean componentIsDisabled(UIComponent component) {
      return Boolean.valueOf(String.valueOf(component.getAttributes().get("disabled")));
   }

   public static boolean componentIsDisabledOrReadonly(UIComponent component) {
      return Boolean.valueOf(String.valueOf(component.getAttributes().get("disabled"))) || Boolean.valueOf(String.valueOf(component.getAttributes().get("readonly")));
   }

   public static Locale getLocaleFromString(String localeStr) throws IllegalArgumentException {
      if (null != localeStr && localeStr.length() >= 2) {
         Locale result = null;
         String lang = null;
         String country = null;
         String variant = null;
         char[] seps = new char[]{'-', '_'};
         int i = 0;
         int j = 0;
         int inputLength = localeStr.length();
         if (inputLength >= 2 && (i = indexOfSet(localeStr, seps, 0)) == -1) {
            if (inputLength != 2) {
               throw new IllegalArgumentException("Illegal locale String: " + localeStr);
            }

            lang = localeStr.toLowerCase();
         }

         if (i != -1) {
            lang = localeStr.substring(0, i);
            if (inputLength >= 5 && (j = indexOfSet(localeStr, seps, i + 1)) == -1) {
               if (inputLength != 5) {
                  throw new IllegalArgumentException("Illegal locale String: " + localeStr);
               }

               country = localeStr.substring(i + 1);
            }

            if (j != -1) {
               country = localeStr.substring(i + 1, j);
               if (inputLength < 8) {
                  throw new IllegalArgumentException("Illegal locale String: " + localeStr);
               }

               variant = localeStr.substring(j + 1);
            }
         }

         if (variant != null && country != null && lang != null) {
            result = new Locale(lang, country, variant);
         } else if (lang != null && country != null) {
            result = new Locale(lang, country);
         } else if (lang != null) {
            result = new Locale(lang, "");
         }

         return result;
      } else {
         throw new IllegalArgumentException("Illegal locale String: " + localeStr);
      }
   }

   public static int indexOfSet(String str, char[] set, int fromIndex) {
      int result = -1;
      int i = fromIndex;

      for(int len = str.length(); i < len; ++i) {
         int j = 0;

         for(int innerLen = set.length; j < innerLen; ++j) {
            if (str.charAt(i) == set[j]) {
               result = i;
               break;
            }
         }

         if (result != -1) {
            break;
         }
      }

      return result;
   }

   public static void parameterNonNull(Object param) throws FacesException {
      if (null == param) {
         throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "param"));
      }
   }

   public static void parameterNonEmpty(String param) throws FacesException {
      if (null == param || 0 == param.length()) {
         throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.EMPTY_PARAMETER"));
      }
   }

   public static String getStackTraceString(Throwable e) {
      if (null == e) {
         return "";
      } else {
         StackTraceElement[] stacks = e.getStackTrace();
         StringBuffer sb = new StringBuffer();

         for(int i = 0; i < stacks.length; ++i) {
            sb.append(stacks[i].toString()).append('\n');
         }

         return sb.toString();
      }
   }

   public static String getContentTypeFromResponse(Object response) {
      String result = null;
      if (null != response) {
         try {
            Method method = ReflectionUtils.lookupMethod(response.getClass(), "getContentType", RIConstants.EMPTY_CLASS_ARGS);
            if (null != method) {
               Object obj = method.invoke(response, RIConstants.EMPTY_METH_ARGS);
               if (null != obj) {
                  result = obj.toString();
               }
            }
         } catch (Exception var4) {
            throw new FacesException(var4);
         }
      }

      return result;
   }

   public static boolean prefixViewTraversal(FacesContext context, UIComponent root, TreeTraversalCallback action) throws FacesException {
      boolean keepGoing;
      if (keepGoing = action.takeActionOnNode(context, root)) {
         for(Iterator kids = root.getFacetsAndChildren(); kids.hasNext() && keepGoing; keepGoing = prefixViewTraversal(context, (UIComponent)kids.next(), action)) {
         }
      }

      return keepGoing;
   }

   public static FeatureDescriptor getFeatureDescriptor(String name, String displayName, String desc, boolean expert, boolean hidden, boolean preferred, Object type, Boolean designTime) {
      FeatureDescriptor fd = new FeatureDescriptor();
      fd.setName(name);
      fd.setDisplayName(displayName);
      fd.setShortDescription(desc);
      fd.setExpert(expert);
      fd.setHidden(hidden);
      fd.setPreferred(preferred);
      fd.setValue("type", type);
      fd.setValue("resolvableAtDesignTime", designTime);
      return fd;
   }

   public static synchronized String[] split(String toSplit, String regex) {
      Map patternCache = getPatternCache();
      Pattern pattern = (Pattern)patternCache.get(regex);
      if (pattern == null) {
         pattern = Pattern.compile(regex);
         patternCache.put(regex, pattern);
      }

      return pattern.split(toSplit, 0);
   }

   public static synchronized String[] split(ServletContext sc, String toSplit, String regex) {
      Map patternCache = getPatternCache(sc);
      Pattern pattern = (Pattern)patternCache.get(regex);
      if (pattern == null) {
         pattern = Pattern.compile(regex);
         patternCache.put(regex, pattern);
      }

      return pattern.split(toSplit, 0);
   }

   public static String getFacesMapping(FacesContext context) {
      if (context == null) {
         String message = MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", "context");
         throw new NullPointerException(message);
      } else {
         ExternalContext extContext = context.getExternalContext();
         Map stateMap = RequestStateManager.getStateMap(context);
         String mapping = (String)stateMap.get("com.sun.faces.INVOCATION_PATH");
         if (mapping == null) {
            String servletPath = extContext.getRequestServletPath();
            String pathInfo = extContext.getRequestPathInfo();
            mapping = getMappingForRequest(servletPath, pathInfo);
            if (mapping == null && LOGGER.isLoggable(Level.FINE)) {
               LOGGER.log(Level.FINE, "jsf.faces_servlet_mapping_cannot_be_determined_error", new Object[]{servletPath});
            }
         }

         if (mapping != null) {
            stateMap.put("com.sun.faces.INVOCATION_PATH", mapping);
         }

         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "URL pattern of the FacesServlet executing the current request " + mapping);
         }

         return mapping;
      }
   }

   private static String getMappingForRequest(String servletPath, String pathInfo) {
      if (servletPath == null) {
         return null;
      } else {
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "servletPath " + servletPath);
            LOGGER.log(Level.FINE, "pathInfo " + pathInfo);
         }

         if (servletPath.length() == 0) {
            return "/*";
         } else if (pathInfo != null) {
            return servletPath;
         } else {
            return servletPath.indexOf(46) < 0 ? servletPath : servletPath.substring(servletPath.lastIndexOf(46));
         }
      }
   }

   public static boolean isPrefixMapped(String mapping) {
      return mapping.charAt(0) == '/';
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
   }

   public interface TreeTraversalCallback {
      boolean takeActionOnNode(FacesContext var1, UIComponent var2) throws FacesException;
   }
}
