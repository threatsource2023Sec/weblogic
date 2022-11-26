package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.Callable;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.debug.DebugLogger;
import org.glassfish.admin.rest.model.RestJsonResponseBody;
import org.glassfish.admin.rest.utils.ExceptionUtil;
import org.glassfish.admin.rest.utils.JsonUtil;

public class InvokeUtils {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(InvokeUtils.class);

   public static Object create(InvocationContext ic, ContainedBeanAttributeType attrType, JSONObject jsonParams) throws Exception {
      boolean ignoreExtraParams = true;
      String type = JsonUtil.getString(jsonParams, "type");
      MethodType mt = attrType.getCreator(ic.request(), type);
      Object[] javaParams = getJavaParams(ic, jsonParams, mt.getMethodDescriptor(), ignoreExtraParams);
      AtzUtils.checkCreateAccess(ic, attrType, type, javaParams);
      Method m = mt.getMethod();

      try {
         Object newBean = invoke(ic, m, javaParams);
         ConfigAuditUtils.auditCreate(ic.clone(newBean), (Exception)null);
         return newBean;
      } catch (Exception var9) {
         ConfigAuditUtils.auditInvoke(ic, mt, javaParams, var9);
         throw var9;
      }
   }

   public static void act(InvocationContext ic, RestJsonResponseBody rb, ActionType type, JSONObject jsonParams) throws Exception {
      boolean ignoreExtraParams = false;
      List mts = type.getMethodTypes();
      int methodIndex = findMatchingMethod(ic, mts, jsonParams, ignoreExtraParams);
      Object[] javaParams = getJavaParams(ic, jsonParams, ((MethodType)mts.get(methodIndex)).getMethodDescriptor(), ignoreExtraParams);
      AtzUtils.checkActionAccess(ic, type, methodIndex, javaParams);
      MethodType mt = (MethodType)mts.get(methodIndex);
      Method m = mt.getMethod();
      Class rt = m.getReturnType();
      Object javaRtn = null;

      try {
         javaRtn = invoke(ic, m, javaParams);
         ConfigAuditUtils.auditInvoke(ic, (MethodType)mt, javaParams, (Exception)null);
      } catch (Exception var14) {
         ConfigAuditUtils.auditInvoke(ic, mt, javaParams, var14);
         throw var14;
      }

      if (!isVoid(rt)) {
         Object jsonRtn = DefaultMarshallers.instance().getMarshaller(ic.request(), rt).marshal(ic, rb, "return", javaRtn);
         JSONObject entity = new JSONObject();
         entity.put("return", jsonRtn);
         rb.setEntity(entity);
      }

   }

   public static Object act(InvocationContext ic, ActionType type, JSONObject jsonParams) throws Exception {
      boolean ignoreExtraParams = false;
      List mts = type.getMethodTypes();
      int methodIndex = findMatchingMethod(ic, mts, jsonParams, ignoreExtraParams);
      Object[] javaParams = getJavaParams(ic, jsonParams, ((MethodType)mts.get(methodIndex)).getMethodDescriptor(), ignoreExtraParams);
      AtzUtils.checkActionAccess(ic, type, methodIndex, javaParams);
      MethodType mt = (MethodType)mts.get(methodIndex);
      Method m = mt.getMethod();

      try {
         Object javaRtn = invoke(ic, false, m, javaParams);
         ConfigAuditUtils.auditInvoke(ic, (MethodType)mt, javaParams, (Exception)null);
         return javaRtn;
      } catch (Exception var10) {
         ConfigAuditUtils.auditInvoke(ic, mt, javaParams, var10);
         throw var10;
      }
   }

   private static int findMatchingMethod(InvocationContext ic, List mts, JSONObject jsonParams, boolean ignoreExtraParams) throws Exception {
      int methodIndex = -1;
      Object[] javaParams = null;

      for(int i = 0; i < mts.size(); ++i) {
         MethodType mt = (MethodType)mts.get(i);
         if (PartitionUtils.isVisible((InvocationContext)ic, mt)) {
            Object[] p = getJavaParams(ic, jsonParams, mt.getMethodDescriptor(), ignoreExtraParams);
            if (p != null) {
               boolean dup = false;
               if (methodIndex != -1) {
                  if (ignoreExtraParams) {
                     MethodType oldMt = (MethodType)mts.get(methodIndex);
                     int subset = isSubset(ic.request(), oldMt, mt);
                     if (subset > 0) {
                        methodIndex = i;
                     } else if (subset >= 0) {
                        dup = true;
                     }
                  } else {
                     dup = true;
                  }
               } else {
                  methodIndex = i;
               }

               if (dup) {
                  throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(MessageUtils.beanFormatter(ic.request()).msgAmbiguousMethod()).build());
               }
            }
         }
      }

      if (methodIndex == -1) {
         StringBuilder sb = new StringBuilder();

         for(int i = 0; i < mts.size(); ++i) {
            MethodType mt = (MethodType)mts.get(i);
            if (PartitionUtils.isVisible((InvocationContext)ic, mt)) {
               if (i > 0) {
                  sb.append(", ");
               }

               sb.append(mt.getName());
               sb.append("(");
               sb.append(formatParams(ic.request(), mt.getMethodDescriptor()));
               sb.append(")");
            }
         }

         throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(MessageUtils.beanFormatter(ic.request()).msgInvalidParameters(sb.toString())).build());
      } else {
         return methodIndex;
      }
   }

   public static Object invoke(InvocationContext ic, Method m, Object... params) throws Exception {
      return invoke(ic, true, m, params);
   }

   public static Object invoke(final InvocationContext ic, final boolean wrapExceptions, final Method m, final Object... params) throws Exception {
      return !BeanUtils.isBeanClass(ic.bean().getClass()) ? _invoke(ic, wrapExceptions, m, params) : PartitionUtils.runAs(ic, new Callable() {
         public Object call() throws Exception {
            return InvokeUtils._invoke(ic, wrapExceptions, m, params);
         }
      });
   }

   private static Object _invoke(InvocationContext ic, boolean wrapExceptions, Method m, Object... params) throws Exception {
      try {
         return m.invoke(ic.bean(), params);
      } catch (InvocationTargetException var6) {
         Throwable cause = var6.getCause();
         if (cause == null) {
            cause = var6;
         }

         if (wrapExceptions) {
            throw ExceptionUtil.badRequest((Throwable)cause, false);
         } else {
            ExceptionUtil.rethrow((Throwable)cause);
            return null;
         }
      } catch (IllegalArgumentException var7) {
         throw ExceptionUtil.badRequest(var7, true);
      }
   }

   private static Object[] getJavaParams(InvocationContext ic, JSONObject params, MethodDescriptor md, boolean ignoreExtraParams) throws Exception {
      Method m = md.getMethod();
      Class[] pts = m.getParameterTypes();
      ParameterDescriptor[] pds = md.getParameterDescriptors();
      int lengthWant = pds == null ? 0 : pds.length;
      int lengthHave = params == null ? 0 : params.length();
      if (!ignoreExtraParams && lengthWant != lengthHave) {
         return null;
      } else {
         Object[] javaParams = new Object[lengthWant];

         for(int i = 0; i < lengthWant; ++i) {
            String pn = pds[i].getName();
            if (!params.has(pn)) {
               return null;
            }

            Object jsonValue = params.get(pn);
            Class javaType = pts[i];
            Unmarshaller unmarshaller = DefaultMarshallers.instance().getUnmarshaller(ic.request(), javaType);
            boolean paramMatches = unmarshaller.matches(ic, jsonValue);
            if (!paramMatches) {
               return null;
            }

            javaParams[i] = unmarshaller.unmarshal(ic, jsonValue);
         }

         return javaParams;
      }
   }

   private static int isSubset(HttpServletRequest request, MethodType mt1, MethodType mt2) throws Exception {
      String sig1 = formatParams(request, mt1.getMethodDescriptor());
      String sig2 = formatParams(request, mt2.getMethodDescriptor());
      if (sig2.startsWith(sig1)) {
         return 1;
      } else {
         return sig1.startsWith(sig2) ? -1 : 0;
      }
   }

   public static boolean invokable(HttpServletRequest request, MethodDescriptor md) throws Exception {
      return getNotInvokableReason(request, md) == null;
   }

   public static String getNotInvokableReason(HttpServletRequest request, MethodDescriptor md) throws Exception {
      Method m = md.getMethod();
      Class rt = m.getReturnType();
      if (!isVoid(rt) && findMarshaller(request, rt) == null) {
         return "cannot map return type: " + rt.getName();
      } else {
         Class[] pts = m.getParameterTypes();

         for(int i = 0; pts != null && i < pts.length; ++i) {
            if (findUnmarshaller(request, pts[i]) == null) {
               return "cannot map argument type: " + pts[i].getName();
            }
         }

         if (pts != null && pts.length > 0) {
            ParameterDescriptor[] pds = md.getParameterDescriptors();
            if (pds == null || pds.length != pts.length) {
               return "parameter names unavailable, ensure that the method has an @param javadoc tag for each parameter";
            }
         }

         return null;
      }
   }

   public static String formatParams(HttpServletRequest request, MethodDescriptor md) throws Exception {
      StringBuilder sb = new StringBuilder();
      Method m = md.getMethod();
      Class[] pts = m.getParameterTypes();
      ParameterDescriptor[] pds = md.getParameterDescriptors();

      for(int i = 0; pts != null && i < pts.length; ++i) {
         if (i != 0) {
            sb.append(", ");
         }

         if (pds != null && i < pds.length) {
            sb.append(pds[i].getName());
            sb.append(": ");
         }

         sb.append(formatClass(request, pts[i]));
      }

      return sb.toString();
   }

   private static String formatClass(HttpServletRequest request, Class clazz) throws Exception {
      Marshaller m = findMarshaller(request, clazz);
      return m != null ? m.describeJsonType() : "java(" + clazz.getName() + ")";
   }

   private static Marshaller findMarshaller(HttpServletRequest request, Class clazz) throws Exception {
      return DefaultMarshallers.instance().findMarshaller(request, clazz);
   }

   private static Unmarshaller findUnmarshaller(HttpServletRequest request, Class clazz) throws Exception {
      return DefaultMarshallers.instance().findUnmarshaller(request, clazz);
   }

   public static boolean isVoid(Class clazz) {
      return Void.class == clazz || Void.TYPE == clazz;
   }
}
