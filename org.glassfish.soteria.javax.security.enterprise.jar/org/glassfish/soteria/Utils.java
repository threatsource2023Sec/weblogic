package org.glassfish.soteria;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;
import javax.el.ELProcessor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.CallerPrincipal;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;

public final class Utils {
   public static final Method validateRequestMethod = getMethod(HttpAuthenticationMechanism.class, "validateRequest", HttpServletRequest.class, HttpServletResponse.class, HttpMessageContext.class);
   public static final Method cleanSubjectMethod = getMethod(HttpAuthenticationMechanism.class, "cleanSubject", HttpServletRequest.class, HttpServletResponse.class, HttpMessageContext.class);
   private static final String ERROR_UNSUPPORTED_ENCODING = "UTF-8 is apparently not supported on this platform.";
   private static final Set FACES_AJAX_HEADERS = unmodifiableSet("partial/ajax", "partial/process");
   private static final String FACES_AJAX_REDIRECT_XML = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>";

   private Utils() {
   }

   public static boolean notNull(Object... objects) {
      Object[] var1 = objects;
      int var2 = objects.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Object object = var1[var3];
         if (object == null) {
            return false;
         }
      }

      return true;
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

   public static Object getParam(InvocationContext invocationContext, int param) {
      return invocationContext.getParameters()[param];
   }

   public static String getBaseURL(HttpServletRequest request) {
      String url = request.getRequestURL().toString();
      return url.substring(0, url.length() - request.getRequestURI().length()) + request.getContextPath();
   }

   public static void redirect(HttpServletResponse response, String location) {
      try {
         response.sendRedirect(location);
      } catch (IOException var3) {
         throw new IllegalStateException(var3);
      }
   }

   public static ELProcessor getELProcessor(String name, Object bean) {
      ELProcessor elProcessor = new ELProcessor();
      elProcessor.defineBean(name, bean);
      return elProcessor;
   }

   public static ELProcessor getELProcessor(String name1, Object bean1, String name2, Object bean2) {
      ELProcessor elProcessor = new ELProcessor();
      elProcessor.defineBean(name1, bean1);
      elProcessor.defineBean(name2, bean2);
      return elProcessor;
   }

   public static ELProcessor getELProcessor(String name1, Object bean1, String name2, Object bean2, String name3, Object bean3) {
      ELProcessor elProcessor = new ELProcessor();
      elProcessor.defineBean(name1, bean1);
      elProcessor.defineBean(name2, bean2);
      elProcessor.defineBean(name3, bean3);
      return elProcessor;
   }

   public static CallerPrincipal toCallerPrincipal(Principal principal) {
      return (CallerPrincipal)(principal instanceof CallerPrincipal ? (CallerPrincipal)principal : new WrappingCallerPrincipal(principal));
   }

   public static void redirect(HttpServletRequest request, HttpServletResponse response, String location) {
      try {
         if (isFacesAjaxRequest(request)) {
            response.setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
            response.setDateHeader("Expires", 0L);
            response.setHeader("Pragma", "no-cache");
            response.setContentType("text/xml");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter().printf("<?xml version=\"1.0\" encoding=\"UTF-8\"?><partial-response><redirect url=\"%s\"></redirect></partial-response>", location);
         } else {
            response.sendRedirect(location);
         }

      } catch (IOException var4) {
         throw new IllegalStateException(var4);
      }
   }

   public static boolean isFacesAjaxRequest(HttpServletRequest request) {
      return FACES_AJAX_HEADERS.contains(request.getHeader("Faces-Request"));
   }

   public static Set unmodifiableSet(Object... values) {
      Set set = new HashSet();
      Object[] var2 = values;
      int var3 = values.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Object value = var2[var4];
         if (value instanceof Object[]) {
            Object[] var10 = (Object[])((Object[])value);
            int var11 = var10.length;

            for(int var8 = 0; var8 < var11; ++var8) {
               Object item = var10[var8];
               set.add(item);
            }
         } else if (value instanceof Collection) {
            Iterator var6 = ((Collection)value).iterator();

            while(var6.hasNext()) {
               Object item = var6.next();
               set.add(item);
            }
         } else {
            set.add(value);
         }
      }

      return Collections.unmodifiableSet(set);
   }

   public static String encodeURL(String string) {
      if (string == null) {
         return null;
      } else {
         try {
            return URLEncoder.encode(string, StandardCharsets.UTF_8.name());
         } catch (UnsupportedEncodingException var2) {
            throw new UnsupportedOperationException("UTF-8 is apparently not supported on this platform.", var2);
         }
      }
   }

   public static String decodeURL(String string) {
      if (string == null) {
         return null;
      } else {
         try {
            return URLDecoder.decode(string, StandardCharsets.UTF_8.name());
         } catch (UnsupportedEncodingException var2) {
            throw new UnsupportedOperationException("UTF-8 is apparently not supported on this platform.", var2);
         }
      }
   }

   public static String getSingleParameterFromState(String state, String paramName) {
      Map requestStateParameters = getParameterMapFromState(state);
      List parameterValues = (List)requestStateParameters.get(paramName);
      return !isEmpty((Collection)parameterValues) ? (String)parameterValues.get(0) : null;
   }

   public static Map getParameterMapFromState(String state) {
      return toParameterMap(unserializeURLSafe(state));
   }

   public static Map toParameterMap(String queryString) {
      String[] parameters = queryString.split(Pattern.quote("&"));
      Map parameterMap = new LinkedHashMap(parameters.length);
      String[] var3 = parameters;
      int var4 = parameters.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String parameter = var3[var5];
         if (parameter.contains("=")) {
            String[] pair = parameter.split(Pattern.quote("="));
            String key = decodeURL(pair[0]);
            String value = pair.length > 1 && !isEmpty(pair[1]) ? decodeURL(pair[1]) : "";
            List values = (List)parameterMap.computeIfAbsent(key, (k) -> {
               return new ArrayList(1);
            });
            values.add(value);
         }
      }

      return parameterMap;
   }

   public static String toQueryString(Map parameterMap) {
      StringBuilder queryString = new StringBuilder();
      Iterator var2 = parameterMap.entrySet().iterator();

      while(var2.hasNext()) {
         Map.Entry entry = (Map.Entry)var2.next();
         String name = encodeURL((String)entry.getKey());

         String value;
         for(Iterator var5 = ((List)entry.getValue()).iterator(); var5.hasNext(); queryString.append(name).append("=").append(encodeURL(value))) {
            value = (String)var5.next();
            if (queryString.length() > 0) {
               queryString.append("&");
            }
         }
      }

      return queryString.toString();
   }

   public static String getSingleParameterFromQueryString(String queryString, String paramName) {
      if (!isEmpty(queryString)) {
         Map requestParameters = toParameterMap(queryString);
         if (!isEmpty((Collection)requestParameters.get(paramName))) {
            return (String)((List)requestParameters.get(paramName)).get(0);
         }
      }

      return null;
   }

   public static String serializeURLSafe(String string) {
      if (string == null) {
         return null;
      } else {
         try {
            InputStream raw = new ByteArrayInputStream(string.getBytes(StandardCharsets.UTF_8));
            ByteArrayOutputStream deflated = new ByteArrayOutputStream();
            stream(raw, new DeflaterOutputStream(deflated, new Deflater(9)));
            String base64 = DatatypeConverter.printBase64Binary(deflated.toByteArray());
            return base64.replace('+', '-').replace('/', '_').replace("=", "");
         } catch (IOException var4) {
            throw new RuntimeException(var4);
         }
      }
   }

   public static String unserializeURLSafe(String string) {
      if (string == null) {
         return null;
      } else {
         try {
            String base64 = string.replace('-', '+').replace('_', '/') + "===".substring(0, string.length() % 4);
            InputStream deflated = new ByteArrayInputStream(DatatypeConverter.parseBase64Binary(base64));
            return new String(toByteArray(new InflaterInputStream(deflated)), StandardCharsets.UTF_8);
         } catch (UnsupportedEncodingException var3) {
            throw new RuntimeException(var3);
         } catch (Exception var4) {
            throw new IllegalArgumentException(var4);
         }
      }
   }

   public static long stream(InputStream input, OutputStream output) throws IOException {
      ReadableByteChannel inputChannel = Channels.newChannel(input);
      Throwable var3 = null;

      try {
         WritableByteChannel outputChannel = Channels.newChannel(output);
         Throwable var5 = null;

         try {
            ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
            long size = 0L;

            while(inputChannel.read(buffer) != -1) {
               buffer.flip();
               size += (long)outputChannel.write(buffer);
               buffer.clear();
            }

            long var9 = size;
            return var9;
         } catch (Throwable var33) {
            var5 = var33;
            throw var33;
         } finally {
            if (outputChannel != null) {
               if (var5 != null) {
                  try {
                     outputChannel.close();
                  } catch (Throwable var32) {
                     var5.addSuppressed(var32);
                  }
               } else {
                  outputChannel.close();
               }
            }

         }
      } catch (Throwable var35) {
         var3 = var35;
         throw var35;
      } finally {
         if (inputChannel != null) {
            if (var3 != null) {
               try {
                  inputChannel.close();
               } catch (Throwable var31) {
                  var3.addSuppressed(var31);
               }
            } else {
               inputChannel.close();
            }
         }

      }
   }

   public static byte[] toByteArray(InputStream input) throws IOException {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      stream(input, output);
      return output.toByteArray();
   }

   public static boolean isImplementationOf(Method implementationMethod, Method interfaceMethod) {
      return interfaceMethod.getDeclaringClass().isAssignableFrom(implementationMethod.getDeclaringClass()) && interfaceMethod.getName().equals(implementationMethod.getName()) && Arrays.equals(interfaceMethod.getParameterTypes(), implementationMethod.getParameterTypes());
   }

   public static Method getMethod(Class base, String name, Class... parameterTypes) {
      try {
         return base.getMethod(name, parameterTypes);
      } catch (SecurityException | NoSuchMethodException var4) {
         throw new IllegalStateException(var4);
      }
   }
}
