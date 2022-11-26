package com.sun.faces.util;

import com.sun.faces.RIConstants;
import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.config.WebConfiguration;
import com.sun.faces.io.FastStringWriter;
import java.beans.FeatureDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.el.ValueExpression;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.AbortProcessingException;
import javax.faces.webapp.FacesServlet;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.xml.namespace.NamespaceContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;

public class Util {
   private static final Logger LOGGER;
   private static boolean unitTestModeEnabled;
   private static final String PATTERN_CACKE_KEY = "com.sun.faces.patternCache";
   private static final String FACES_SERVLET_CLASS;
   private static final String EXACT_MARKER = "* *";
   private static final String FACES_CONTEXT_ATTRIBUTES_DOCTYPE_KEY;
   private static final String FACES_CONTEXT_ATTRIBUTES_XMLDECL_KEY;

   private Util() {
      throw new IllegalStateException();
   }

   private static Map getPatternCache(Map appMap) {
      Map result = (Map)appMap.get("com.sun.faces.patternCache");
      if (result == null) {
         result = new LRUMap(15);
         appMap.put("com.sun.faces.patternCache", result);
      }

      return (Map)result;
   }

   private static Map getPatternCache(ServletContext sc) {
      Map result = (Map)sc.getAttribute("com.sun.faces.patternCache");
      if (result == null) {
         result = new LRUMap(15);
         sc.setAttribute("com.sun.faces.patternCache", result);
      }

      return (Map)result;
   }

   private static Collection getFacesServletMappings(ServletContext servletContext) {
      ServletRegistration facesRegistration = getExistingFacesServletRegistration(servletContext);
      return (Collection)(facesRegistration != null ? facesRegistration.getMappings() : Collections.emptyList());
   }

   private static ServletRegistration getExistingFacesServletRegistration(ServletContext servletContext) {
      Map existing = servletContext.getServletRegistrations();
      Iterator var2 = existing.values().iterator();

      ServletRegistration registration;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         registration = (ServletRegistration)var2.next();
      } while(!FACES_SERVLET_CLASS.equals(registration.getClassName()));

      return registration;
   }

   public static boolean isPortletRequest(FacesContext context) {
      return context.getExternalContext().getRequestMap().get("javax.portlet.faces.phase") != null;
   }

   public static String generateCreatedBy(FacesContext facesContext) {
      String applicationContextPath = "unitTest";

      try {
         applicationContextPath = facesContext.getExternalContext().getApplicationContextPath();
      } catch (Throwable var3) {
      }

      return applicationContextPath + " " + Thread.currentThread().toString() + " " + System.currentTimeMillis();
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
            } catch (IllegalAccessException | InstantiationException var5) {
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
      unitTestModeEnabled = enabled;
   }

   public static boolean isUnitTestModeEnabled() {
      return unitTestModeEnabled;
   }

   public static TransformerFactory createTransformerFactory() {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();

      TransformerFactory factory;
      try {
         Thread.currentThread().setContextClassLoader(Util.class.getClassLoader());
         factory = TransformerFactory.newInstance();
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

      return factory;
   }

   public static SAXParserFactory createSAXParserFactory() {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();

      SAXParserFactory factory;
      try {
         Thread.currentThread().setContextClassLoader(Util.class.getClassLoader());
         factory = SAXParserFactory.newInstance();
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

      return factory;
   }

   public static DocumentBuilderFactory createDocumentBuilderFactory() {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();

      DocumentBuilderFactory factory;
      try {
         Thread.currentThread().setContextClassLoader(Util.class.getClassLoader());
         factory = DocumentBuilderFactory.newInstance();
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

      return factory;
   }

   public static SchemaFactory createSchemaFactory(String uri) {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();

      SchemaFactory factory;
      try {
         Thread.currentThread().setContextClassLoader(Util.class.getClassLoader());
         factory = SchemaFactory.newInstance(uri);
      } finally {
         Thread.currentThread().setContextClassLoader(cl);
      }

      return factory;
   }

   public static Class loadClass(String name, Object fallbackClass) throws ClassNotFoundException {
      ClassLoader loader = getCurrentLoader(fallbackClass);
      String[] primitiveNames = new String[]{"byte", "short", "int", "long", "float", "double", "boolean", "char"};
      Class[] primitiveClasses = new Class[]{Byte.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE, Boolean.TYPE, Character.TYPE};

      for(int i = 0; i < primitiveNames.length; ++i) {
         if (primitiveNames[i].equals(name)) {
            return primitiveClasses[i];
         }
      }

      return Class.forName(name, true, loader);
   }

   public static Class loadClass2(String name, Object fallbackClass) {
      try {
         ClassLoader loader = Thread.currentThread().getContextClassLoader();
         if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
         }

         return Class.forName(name, true, loader);
      } catch (ClassNotFoundException var3) {
         throw new IllegalStateException(var3.getMessage(), var3);
      }
   }

   public static Object newInstance(Class clazz) {
      try {
         return clazz.newInstance();
      } catch (IllegalAccessException | InstantiationException var2) {
         throw new IllegalStateException(var2.getMessage(), var2);
      }
   }

   public static ClassLoader getCurrentLoader(Object fallbackClass) {
      ClassLoader loader = getContextClassLoader();
      if (loader == null) {
         loader = fallbackClass.getClass().getClassLoader();
      }

      return loader;
   }

   private static ClassLoader getContextClassLoader() {
      return System.getSecurityManager() == null ? Thread.currentThread().getContextClassLoader() : (ClassLoader)AccessController.doPrivileged(new PrivilegedAction() {
         public Object run() {
            return Thread.currentThread().getContextClassLoader();
         }
      });
   }

   public static ClassLoader getContextClassLoader2() throws FacesException {
      ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
      if (classLoader == null) {
         throw new FacesException("getContextClassLoader");
      } else {
         return classLoader;
      }
   }

   public static String removeAllButLastSlashPathSegment(String input) {
      if (input.charAt(0) == '/') {
         input = input.substring(1);
      }

      int len = input.length();
      if (input.charAt(len - 1) == '/') {
         input = input.substring(0, len - 1);
      }

      int slash = input.lastIndexOf("/");
      if (-1 != slash) {
         input = input.substring(slash + 1);
      }

      return input;
   }

   public static String removeAllButNextToLastSlashPathSegment(String input) {
      if (input.charAt(0) == '/') {
         input = input.substring(1);
      }

      int len = input.length();
      if (input.charAt(len - 1) == '/') {
         input = input.substring(0, len - 1);
      }

      int lastSlash = input.lastIndexOf("/");
      if (-1 != lastSlash) {
         int startOrPreviousSlash = input.lastIndexOf("/", lastSlash - 1);
         startOrPreviousSlash = -1 == startOrPreviousSlash ? 0 : startOrPreviousSlash;
         input = input.substring(startOrPreviousSlash, lastSlash);
      }

      return input;
   }

   public static String removeLastPathSegment(String input) {
      int slash = input.lastIndexOf("/");
      if (-1 != slash) {
         input = input.substring(0, slash);
      }

      return input;
   }

   public static void notNegative(String varname, long number) {
      if (number < 0L) {
         throw new IllegalArgumentException("\"" + varname + "\" is negative");
      }
   }

   public static void notNull(String varname, Object var) {
      if (var == null) {
         throw new NullPointerException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_PARAMETERS_ERROR", varname));
      }
   }

   public static void notNullViewId(String viewId) {
      if (viewId == null) {
         throw new IllegalArgumentException(MessageUtils.getExceptionMessageString("com.sun.faces.NULL_VIEW_ID"));
      }
   }

   public static void notNullNamedObject(Object object, String objectId, String logMsg) {
      if (object == null) {
         Object[] params = new Object[]{objectId};
         if (LOGGER.isLoggable(Level.SEVERE)) {
            LOGGER.log(Level.SEVERE, logMsg, params);
         }

         throw new FacesException(MessageUtils.getExceptionMessageString("com.sun.faces.NAMED_OBJECT_NOT_FOUND_ERROR", params));
      }
   }

   public static void canSetAppArtifact(ApplicationAssociate applicationAssociate, String artifactName) {
      if (applicationAssociate.hasRequestBeenServiced()) {
         throw new IllegalStateException(MessageUtils.getExceptionMessageString("com.sun.faces.ILLEGAL_ATTEMPT_SETTING_APPLICATION_ARTIFACT", artifactName));
      }
   }

   public static void notNullAttribute(String attributeName, Object attribute) {
      if (attribute == null) {
         throw new FacesException("The \"" + attributeName + "\" attribute is required");
      }
   }

   public static ValueExpression getValueExpressionNullSafe(UIComponent component, String name) {
      ValueExpression valueExpression = component.getValueExpression(name);
      notNullAttribute(name, valueExpression);
      return valueExpression;
   }

   public static boolean isEmpty(String string) {
      return string == null || string.isEmpty();
   }

   public static boolean isEmpty(Object[] array) {
      return array == null || array.length == 0;
   }

   public static boolean isEmpty(Collection collection) {
      return collection == null || collection.isEmpty();
   }

   public static boolean isEmpty(Object value) {
      if (value == null) {
         return true;
      } else if (value instanceof String) {
         return ((String)value).isEmpty();
      } else if (value instanceof Collection) {
         return ((Collection)value).isEmpty();
      } else if (value instanceof Map) {
         return ((Map)value).isEmpty();
      } else if (value instanceof Optional) {
         return !((Optional)value).isPresent();
      } else if (value.getClass().isArray()) {
         return Array.getLength(value) == 0;
      } else {
         return value.toString() == null || value.toString().isEmpty();
      }
   }

   public static boolean isAllEmpty(Object... values) {
      Object[] var1 = values;
      int var2 = values.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object value = var1[var3];
         if (!isEmpty(value)) {
            return false;
         }
      }

      return true;
   }

   public static boolean isAnyEmpty(Object... values) {
      Object[] var1 = values;
      int var2 = values.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object value = var1[var3];
         if (isEmpty(value)) {
            return true;
         }
      }

      return false;
   }

   public static boolean isAllNull(Object... values) {
      Object[] var1 = values;
      int var2 = values.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object value = var1[var3];
         if (value != null) {
            return false;
         }
      }

      return true;
   }

   public static boolean isAnyNull(Object... values) {
      Object[] var1 = values;
      int var2 = values.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object value = var1[var3];
         if (value == null) {
            return true;
         }
      }

      return false;
   }

   @SafeVarargs
   public static boolean isOneOf(Object object, Object... objects) {
      Object[] var2 = objects;
      int var3 = objects.length;
      int var4 = 0;

      while(true) {
         if (var4 >= var3) {
            return false;
         }

         Object other = var2[var4];
         if (object == null) {
            if (other == null) {
               break;
            }
         } else if (object.equals(other)) {
            break;
         }

         ++var4;
      }

      return true;
   }

   @SafeVarargs
   public static Object coalesce(Object... objects) {
      Object[] var1 = objects;
      int var2 = objects.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object object = var1[var3];
         if (object != null) {
            return object;
         }
      }

      return null;
   }

   public static List reverse(List list) {
      int length = list.size();
      List result = new ArrayList(length);

      for(int i = length - 1; i >= 0; --i) {
         result.add(list.get(i));
      }

      return result;
   }

   public static boolean startsWithOneOf(String string, String... prefixes) {
      if (prefixes == null) {
         return false;
      } else {
         String[] var2 = prefixes;
         int var3 = prefixes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            String prefix = var2[var4];
            if (string.startsWith(prefix)) {
               return true;
            }
         }

         return false;
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

   public static Class getTypeFromString(String type) throws ClassNotFoundException {
      Class result;
      switch (type) {
         case "byte":
            result = Byte.TYPE;
            break;
         case "short":
            result = Short.TYPE;
            break;
         case "int":
            result = Integer.TYPE;
            break;
         case "long":
            result = Long.TYPE;
            break;
         case "float":
            result = Float.TYPE;
            break;
         case "double":
            result = Double.TYPE;
            break;
         case "boolean":
            result = Boolean.TYPE;
            break;
         case "char":
            result = Character.TYPE;
            break;
         case "void":
            result = Void.TYPE;
            break;
         default:
            if (type.indexOf(46) == -1) {
               type = "java.lang." + type;
            }

            result = loadClass(type, Void.TYPE);
      }

      return result;
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

         try {
            Method method = Locale.class.getMethod("forLanguageTag", String.class);
            if (method != null) {
               result = (Locale)method.invoke((Object)null, localeStr);
            }
         } catch (SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException var9) {
         }

         if (result == null || result.getLanguage().equals("")) {
            String lang = null;
            String country = null;
            String variant = null;
            char[] seps = new char[]{'-', '_'};
            int inputLength = localeStr.length();
            int i = 0;
            int j = 0;
            if (inputLength >= 2 && (i = indexOfSet(localeStr, seps, 0)) == -1) {
               if (2 != localeStr.length()) {
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

         if (-1 != result) {
            break;
         }
      }

      return result;
   }

   public static String getStackTraceString(Throwable e) {
      if (null == e) {
         return "";
      } else {
         StackTraceElement[] stacks = e.getStackTrace();
         StringBuffer sb = new StringBuffer();
         StackTraceElement[] var3 = stacks;
         int var4 = stacks.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            StackTraceElement stack = var3[var5];
            sb.append(stack.toString()).append('\n');
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
         } catch (IllegalArgumentException | InvocationTargetException | IllegalAccessException var4) {
            throw new FacesException(var4);
         }
      }

      return result;
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

   public static synchronized String[] split(Map appMap, String toSplit, String regex) {
      Map patternCache = getPatternCache(appMap);
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
      notNull("context", context);
      String mapping = (String)RequestStateManager.get(context, "com.sun.faces.INVOCATION_PATH");
      if (mapping == null) {
         ExternalContext externalContext = context.getExternalContext();
         String servletPath = externalContext.getRequestServletPath();
         String pathInfo = externalContext.getRequestPathInfo();
         mapping = getMappingForRequest(externalContext, servletPath, pathInfo);
         if (mapping == null && LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "jsf.faces_servlet_mapping_cannot_be_determined_error", new Object[]{servletPath});
         }

         if (mapping != null) {
            RequestStateManager.set(context, "com.sun.faces.INVOCATION_PATH", mapping);
         }
      }

      if (LOGGER.isLoggable(Level.FINE)) {
         LOGGER.log(Level.FINE, "URL pattern of the FacesServlet executing the current request " + mapping);
      }

      return mapping;
   }

   private static String getMappingForRequest(ExternalContext externalContext, String servletPath, String pathInfo) {
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
         } else if (servletPath.indexOf(46) < 0) {
            Object context = externalContext.getContext();
            if (context instanceof ServletContext) {
               Collection servletMappings = getFacesServletMappings((ServletContext)context);
               if (servletMappings.contains(servletPath)) {
                  return addExactMappedMarker(servletPath);
               }
            }

            return servletPath;
         } else {
            return servletPath.substring(servletPath.lastIndexOf(46));
         }
      }
   }

   public static boolean isViewIdExactMappedToFacesServlet(String viewId) {
      return isResourceExactMappedToFacesServlet(FacesContext.getCurrentInstance().getExternalContext(), viewId);
   }

   public static boolean isResourceExactMappedToFacesServlet(ExternalContext externalContext, String resource) {
      Object context = externalContext.getContext();
      return context instanceof ServletContext ? getFacesServletMappings((ServletContext)context).contains(resource) : false;
   }

   public static String getFirstWildCardMappingToFacesServlet(ExternalContext externalContext) {
      Object context = externalContext.getContext();
      return context instanceof ServletContext ? (String)getFacesServletMappings((ServletContext)context).stream().filter((mapping) -> {
         return mapping.contains("*");
      }).findFirst().orElse((Object)null) : null;
   }

   public static String addExactMappedMarker(String mapping) {
      return "* *" + mapping;
   }

   public static String removeExactMappedMarker(String mapping) {
      return mapping.substring("* *".length());
   }

   public static boolean isExactMapped(String mapping) {
      return mapping.startsWith("* *");
   }

   public static boolean isPrefixMapped(String mapping) {
      return mapping.charAt(0) == '/';
   }

   public static boolean isSpecialAttributeName(String name) {
      boolean isSpecialAttributeName = name.equals("action") || name.equals("actionListener") || name.equals("validator") || name.equals("valueChangeListener");
      return isSpecialAttributeName;
   }

   public static boolean isViewPopulated(FacesContext ctx, UIViewRoot viewToRender) {
      return ctx.getAttributes().containsKey(viewToRender);
   }

   public static void setViewPopulated(FacesContext ctx, UIViewRoot viewToRender) {
      ctx.getAttributes().put(viewToRender, Boolean.TRUE);
   }

   public static void checkIdUniqueness(FacesContext context, UIComponent component, Set componentIds) {
      boolean uniquenessCheckDisabled = false;
      if (context.isProjectStage(ProjectStage.Production)) {
         WebConfiguration config = WebConfiguration.getInstance(context.getExternalContext());
         uniquenessCheckDisabled = config.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DisableIdUniquenessCheck);
      }

      if (!uniquenessCheckDisabled) {
         Iterator kids = component.getFacetsAndChildren();

         while(kids.hasNext()) {
            UIComponent kid = (UIComponent)kids.next();
            String id = kid.getClientId(context);
            if (!componentIds.add(id)) {
               if (LOGGER.isLoggable(Level.SEVERE)) {
                  LOGGER.log(Level.SEVERE, "jsf.duplicate_component_id_error", id);
                  FastStringWriter writer = new FastStringWriter(128);
                  DebugUtil.simplePrintTree(context.getViewRoot(), id, writer);
                  LOGGER.severe(writer.toString());
               }

               String message = MessageUtils.getExceptionMessageString("com.sun.faces.DUPLICATE_COMPONENT_ID_ERROR", id);
               throw new IllegalStateException(message);
            }

            checkIdUniqueness(context, kid, componentIds);
         }
      }

   }

   public static boolean classHasAnnotations(Class clazz) {
      if (clazz != null) {
         for(; clazz != Object.class; clazz = clazz.getSuperclass()) {
            Field[] fields = clazz.getDeclaredFields();
            int var4;
            if (fields != null) {
               Field[] var2 = fields;
               int var3 = fields.length;

               for(var4 = 0; var4 < var3; ++var4) {
                  Field field = var2[var4];
                  if (field.getAnnotations().length > 0) {
                     return true;
                  }
               }
            }

            Method[] methods = clazz.getDeclaredMethods();
            if (methods != null) {
               Method[] var8 = methods;
               var4 = methods.length;

               for(int var9 = 0; var9 < var4; ++var9) {
                  Method method = var8[var9];
                  if (method.getDeclaredAnnotations().length > 0) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public static String getNamingContainerPrefix(FacesContext context) {
      UIViewRoot viewRoot = context.getViewRoot();
      if (viewRoot == null) {
         Application application = context.getApplication();
         viewRoot = (UIViewRoot)application.createComponent("javax.faces.ViewRoot");
      }

      return viewRoot instanceof NamingContainer ? viewRoot.getContainerClientId(context) + UINamingContainer.getSeparatorChar(context) : "";
   }

   public static String getViewStateId(FacesContext context) {
      String result = null;
      String viewStateCounterKey = "com.sun.faces.util.ViewStateCounterKey";
      Map contextAttrs = context.getAttributes();
      Integer counter = (Integer)contextAttrs.get("com.sun.faces.util.ViewStateCounterKey");
      if (null == counter) {
         counter = 0;
      }

      char sep = UINamingContainer.getSeparatorChar(context);
      UIViewRoot root = context.getViewRoot();
      result = root.getContainerClientId(context) + sep + "javax.faces.ViewState" + sep + counter;
      contextAttrs.put("com.sun.faces.util.ViewStateCounterKey", counter + 1);
      return result;
   }

   public static String getClientWindowId(FacesContext context) {
      String result = null;
      String clientWindowIdCounterKey = "com.sun.faces.util.ClientWindowCounterKey";
      Map contextAttrs = context.getAttributes();
      Integer counter = (Integer)contextAttrs.get("com.sun.faces.util.ClientWindowCounterKey");
      if (null == counter) {
         counter = 0;
      }

      char sep = UINamingContainer.getSeparatorChar(context);
      result = context.getViewRoot().getContainerClientId(context) + sep + "javax.faces.ClientWindow" + sep + counter;
      contextAttrs.put("com.sun.faces.util.ClientWindowCounterKey", counter + 1);
      return result;
   }

   public static void saveDOCTYPEToFacesContextAttributes(String DOCTYPE) {
      FacesContext context = FacesContext.getCurrentInstance();
      if (null != context) {
         Map attrs = context.getAttributes();
         attrs.put(FACES_CONTEXT_ATTRIBUTES_DOCTYPE_KEY, DOCTYPE);
      }
   }

   public static String getDOCTYPEFromFacesContextAttributes(FacesContext context) {
      if (null == context) {
         return null;
      } else {
         Map attrs = context.getAttributes();
         return (String)attrs.get(FACES_CONTEXT_ATTRIBUTES_DOCTYPE_KEY);
      }
   }

   public static void saveXMLDECLToFacesContextAttributes(String XMLDECL) {
      FacesContext context = FacesContext.getCurrentInstance();
      if (null != context) {
         Map attrs = context.getAttributes();
         attrs.put(FACES_CONTEXT_ATTRIBUTES_XMLDECL_KEY, XMLDECL);
      }
   }

   public static String getXMLDECLFromFacesContextAttributes(FacesContext context) {
      if (null == context) {
         return null;
      } else {
         Map attrs = context.getAttributes();
         return (String)attrs.get(FACES_CONTEXT_ATTRIBUTES_XMLDECL_KEY);
      }
   }

   public static long getLastModified(URL url) {
      InputStream is = null;

      long lastModified;
      try {
         URLConnection conn = url.openConnection();
         if (conn instanceof JarURLConnection) {
            JarURLConnection jarUrlConnection = (JarURLConnection)conn;
            URL jarFileUrl = jarUrlConnection.getJarFileURL();
            URLConnection jarFileConnection = jarFileUrl.openConnection();
            lastModified = jarFileConnection.getLastModified();
            jarFileConnection.getInputStream().close();
         } else {
            is = conn.getInputStream();
            lastModified = conn.getLastModified();
         }
      } catch (Exception var14) {
         throw new FacesException("Error Checking Last Modified for " + url, var14);
      } finally {
         if (is != null) {
            try {
               is.close();
            } catch (Exception var15) {
               if (LOGGER.isLoggable(Level.FINEST)) {
                  LOGGER.log(Level.FINEST, "Closing stream", var15);
               }
            }
         }

      }

      return lastModified;
   }

   public static String getFacesConfigXmlVersion(FacesContext facesContext) {
      String result = "";
      InputStream stream = null;

      try {
         URL url = facesContext.getExternalContext().getResource("/WEB-INF/faces-config.xml");
         if (url != null) {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            xpath.setNamespaceContext(new JavaeeNamespaceContext());
            stream = url.openStream();
            result = xpath.evaluate("string(/javaee:faces-config/@version)", new InputSource(stream));
         }
      } catch (MalformedURLException var16) {
      } catch (IOException | XPathExpressionException var17) {
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (IOException var15) {
            }
         }

      }

      return result;
   }

   public static String getWebXmlVersion(FacesContext facesContext) {
      String result = "";
      InputStream stream = null;

      try {
         URL url = facesContext.getExternalContext().getResource("/WEB-INF/web.xml");
         if (url != null) {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            xpath.setNamespaceContext(new JavaeeNamespaceContext());
            stream = url.openStream();
            result = xpath.evaluate("string(/javaee:web-app/@version)", new InputSource(stream));
         }
      } catch (MalformedURLException var16) {
      } catch (IOException | XPathExpressionException var17) {
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (IOException var15) {
            }
         }

      }

      return result;
   }

   public static BeanManager getCdiBeanManager(FacesContext facesContext) {
      BeanManager result = null;
      if (facesContext != null && facesContext.getAttributes().containsKey("com.sun.faces.cdi.BeanManager")) {
         result = (BeanManager)facesContext.getAttributes().get("com.sun.faces.cdi.BeanManager");
      } else if (facesContext != null && facesContext.getExternalContext().getApplicationMap().containsKey("com.sun.faces.cdi.BeanManager")) {
         result = (BeanManager)facesContext.getExternalContext().getApplicationMap().get("com.sun.faces.cdi.BeanManager");
      } else {
         try {
            InitialContext initialContext = new InitialContext();
            result = (BeanManager)initialContext.lookup("java:comp/BeanManager");
         } catch (NamingException var5) {
            try {
               InitialContext initialContext = new InitialContext();
               result = (BeanManager)initialContext.lookup("java:comp/env/BeanManager");
            } catch (NamingException var4) {
            }
         }

         if (result == null && facesContext != null) {
            Map applicationMap = facesContext.getExternalContext().getApplicationMap();
            result = (BeanManager)applicationMap.get("org.jboss.weld.environment.servlet.javax.enterprise.inject.spi.BeanManager");
         }

         if (result != null && facesContext != null) {
            facesContext.getAttributes().put("com.sun.faces.cdi.BeanManager", result);
            facesContext.getExternalContext().getApplicationMap().put("com.sun.faces.cdi.BeanManager", result);
         }
      }

      return result;
   }

   public static boolean isCdiAvailable(FacesContext facesContext) {
      boolean result;
      if (facesContext != null && facesContext.getAttributes().containsKey("com.sun.faces.cdi.AvailableFlag")) {
         result = (Boolean)facesContext.getAttributes().get("com.sun.faces.cdi.AvailableFlag");
      } else if (facesContext != null && facesContext.getExternalContext().getApplicationMap().containsKey("com.sun.faces.cdi.AvailableFlag")) {
         result = (Boolean)facesContext.getExternalContext().getApplicationMap().get("com.sun.faces.cdi.AvailableFlag");
      } else {
         result = getCdiBeanManager(facesContext) != null;
         if (result && facesContext != null) {
            facesContext.getAttributes().put("com.sun.faces.cdi.AvailableFlag", result);
            facesContext.getExternalContext().getApplicationMap().put("com.sun.faces.cdi.AvailableFlag", result);
         }
      }

      return result;
   }

   public static boolean isCdiAvailable(ServletContext servletContext) {
      Object value = servletContext.getAttribute("com.sun.faces.cdi.AvailableFlag");
      boolean result;
      if (value != null) {
         result = (Boolean)value;
      } else {
         result = getCdiBeanManager((FacesContext)null) != null;
         if (result) {
            servletContext.setAttribute("com.sun.faces.cdi.AvailableFlag", result);
         }
      }

      return result;
   }

   public static boolean isCdiOneOneOrLater(FacesContext facesContext) {
      boolean result = false;
      if (facesContext != null && facesContext.getAttributes().containsKey("com.sun.faces.cdi.OneOneOrLater")) {
         result = (Boolean)facesContext.getAttributes().get("com.sun.faces.cdi.OneOneOrLater");
      } else if (facesContext != null && facesContext.getExternalContext().getApplicationMap().containsKey("com.sun.faces.cdi.OneOneOrLater")) {
         result = facesContext.getExternalContext().getApplicationMap().containsKey("com.sun.faces.cdi.OneOneOrLater");
      } else {
         try {
            Class.forName("javax.enterprise.context.Initialized");
            result = true;
         } catch (ClassNotFoundException var3) {
            if (LOGGER.isLoggable(Level.FINEST)) {
               LOGGER.log(Level.FINEST, "Detected CDI 1.0", var3);
            }
         }

         if (facesContext != null) {
            facesContext.getAttributes().put("com.sun.faces.cdi.OneOneOrLater", result);
            facesContext.getExternalContext().getApplicationMap().put("com.sun.faces.cdi.OneOneOrLater", result);
         }
      }

      return result;
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      unitTestModeEnabled = false;
      FACES_SERVLET_CLASS = FacesServlet.class.getName();
      FACES_CONTEXT_ATTRIBUTES_DOCTYPE_KEY = Util.class.getName() + "_FACES_CONTEXT_ATTRS_DOCTYPE_KEY";
      FACES_CONTEXT_ATTRIBUTES_XMLDECL_KEY = Util.class.getName() + "_FACES_CONTEXT_ATTRS_XMLDECL_KEY";
   }

   public static class JavaeeNamespaceContext implements NamespaceContext {
      public String getNamespaceURI(String prefix) {
         return "http://xmlns.jcp.org/xml/ns/javaee";
      }

      public String getPrefix(String namespaceURI) {
         return "javaee";
      }

      public Iterator getPrefixes(String namespaceURI) {
         return null;
      }
   }
}
