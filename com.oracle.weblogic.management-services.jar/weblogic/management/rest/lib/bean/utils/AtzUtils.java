package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import java.lang.reflect.Array;
import javax.management.ObjectName;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.glassfish.admin.rest.debug.DebugLogger;
import weblogic.management.internal.DefaultJMXPolicyManager;
import weblogic.management.internal.JMXContextHandler;
import weblogic.management.internal.SecurityHelper;
import weblogic.management.jmx.PrimitiveMapper;
import weblogic.management.mbeanservers.edit.ActivationTaskMBean;
import weblogic.management.mbeanservers.edit.ConfigurationManagerMBean;
import weblogic.management.rest.lib.utils.SecurityUtils;
import weblogic.security.service.ContextHandler;
import weblogic.security.service.JMXResource;

public class AtzUtils {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(AtzUtils.class);
   private static final String[] APP_SCOPED_TYPES = new String[]{"weblogic.management.runtime.ApplicationRuntimeMBean", "weblogic.management.configuration.JDBCSystemResourceMBean", "weblogic.management.configuration.JMSSystemResourceMBean", "weblogic.management.configuration.WLDFSystemResourceMBean", "weblogic.management.configuration.CustomResourceMBean"};

   public static void checkGetAccess(InvocationContext ic, String restProperty) throws Exception {
      checkGetAccess(ic, BeanType.getBeanType(ic.request(), ic.bean()).getAttributeType(restProperty));
   }

   public static void checkGetAccess(InvocationContext ic, AttributeType attrType) throws Exception {
      checkGetAccess(ic, ic.request(), JMXUtils.getObjectName(ic), getAppName(ic, attrType.getBeanType()), attrType);
   }

   public static void checkGetAccess(HttpServletRequest request, String name, String typeName, String restProperty) throws Exception {
      checkGetAccess(request, name, typeName, typeName, restProperty);
   }

   public static void checkGetAccess(HttpServletRequest request, String name, String typeName, String onameTypeName, String restProperty) throws Exception {
      AttributeType attrType = BeanType.getBeanType(request, typeName).getAttributeType(restProperty);
      String objectNameStr = "com.bea:Name=" + name + ",Type=" + onameTypeName;
      String appName = null;
      checkGetAccess((InvocationContext)null, (HttpServletRequest)request, (ObjectName)(new ObjectName(objectNameStr)), (String)appName, (AttributeType)attrType);
   }

   private static void checkGetAccess(InvocationContext ic, HttpServletRequest request, ObjectName objectName, String appName, AttributeType attrType) throws Exception {
      BeanType type = attrType.getBeanType();
      String typeName = type.getName();
      String target = attrType.getPropertyDescriptor().getName();
      String operation = isEncryptedProperty(attrType) ? "getEncrypted" : "get";
      ContextHandler context = SecurityHelper.getResourceContextHandler(new RestMBeanPartitionFinder(objectName, ic), new JMXContextHandler(objectName), type.getBeanInfo().getBeanDescriptor(), attrType.getPropertyDescriptor(), operation);
      if (!isAccessAllowed(request, appName, typeName, operation, target, context)) {
         throwAccessNotAllowed(getNotAuthorizedMessage(ic, typeName, target, appName));
      }
   }

   public static void checkFindAccess(InvocationContext ic, ContainedBeansType attrType, String identity) throws Exception {
      BeanType type = attrType.getBeanType();
      String appName = getAppName(ic, type);
      String typeName = getTypeName(type);
      MethodDescriptor md = attrType.getFinder(ic.request()).getMethodDescriptor();
      String target = md.getName();
      Object[] javaParams = new Object[]{identity};
      if (!isInvokeAccessAllowed(ic, appName, typeName, "find", target, md, javaParams)) {
         throwAccessNotAllowed(invokeNotAuthorizedMessage(ic, typeName, target, appName));
      }
   }

   public static void checkSetAccess(InvocationContext ic, AttributeType attrType, Object newJavaValue) throws Exception {
      BeanType type = attrType.getBeanType();
      String appName = getAppName(ic, type);
      String typeName = getTypeName(type);
      String target = attrType.getPropertyDescriptor().getName();
      String operation = "set";
      String auditArgInfo = null;
      if (isEncryptedProperty(attrType)) {
         operation = "setEncrypted";
         auditArgInfo = "1";
      }

      String[] jmxSignature = new String[1];
      Object[] javaParams = new Object[1];
      jmxSignature[0] = attrType.getPropertyDescriptor().getPropertyType().getName();
      javaParams[0] = newJavaValue;
      ObjectName objectName = JMXUtils.getObjectName(ic);
      ContextHandler context = SecurityHelper.getResourceContextHandler(new RestMBeanPartitionFinder(objectName, ic), new JMXContextHandler(objectName, JMXUtils.getJMXParams(ic, javaParams), jmxSignature, auditArgInfo, JMXUtils.getJMXValue(ic, BeanUtils.getBeanProperty(ic, attrType, false))), attrType.getBeanType().getBeanInfo().getBeanDescriptor(), attrType.getPropertyDescriptor(), operation);
      if (!isAccessAllowed(ic, appName, typeName, operation, target, context)) {
         throwAccessNotAllowed(setNotAuthorizedMessage(ic, typeName, target, appName));
      }
   }

   public static void checkCreateAccess(InvocationContext ic, ContainedBeanAttributeType attrType, String type, Object[] javaParams) throws Exception {
      BeanType bt = attrType.getBeanType();
      String appName = getAppName(ic, bt);
      String typeName = getTypeName(bt);
      MethodDescriptor md = attrType.getCreator(ic.request(), type).getMethodDescriptor();
      String target = md.getName();
      if (!isInvokeAccessAllowed(ic, appName, typeName, target, md, javaParams)) {
         throwAccessNotAllowed(invokeNotAuthorizedMessage(ic, typeName, target, appName));
      }
   }

   public static void checkDeleteAccess(InvocationContext ic, ContainedBeanAttributeType attrType, Object bean) throws Exception {
      BeanType type = attrType.getBeanType();
      String appName = getAppName(ic, type);
      String typeName = getTypeName(type);
      MethodDescriptor md = attrType.getDestroyer(ic.request()).getMethodDescriptor();
      String target = md.getName();
      Object[] javaParams = new Object[]{bean};
      if (!isInvokeAccessAllowed(ic, appName, typeName, target, md, javaParams)) {
         throwAccessNotAllowed(invokeNotAuthorizedMessage(ic, typeName, target, appName));
      }
   }

   public static void checkActionAccess(InvocationContext ic, ActionType actionType, int methodIndex, Object[] javaParams) throws Exception {
      BeanType type = actionType.getBeanType();
      String typeName = getTypeName(type);
      String appName = getAppName(ic, type);
      MethodType mt = (MethodType)actionType.getMethodTypes().get(methodIndex);
      MethodDescriptor md = mt.getMethodDescriptor();
      String target = md.getName();
      if (!isInvokeAccessAllowed(ic, appName, typeName, target, md, javaParams)) {
         throwAccessNotAllowed(invokeNotAuthorizedMessage(ic, typeName, target, appName));
      }
   }

   public static void checkIsSetAccess(InvocationContext ic, AttributeType attrType) throws Exception {
      BeanType type = attrType.getBeanType();
      String typeName = getTypeName(type);
      String appName = getAppName(ic, type);
      MethodDescriptor md = findSetMethod(attrType, "isSet");
      String target = md.getName();
      String property = attrType.getPropertyDescriptor().getName();
      Object[] javaParams = new Object[]{property};
      if (!isInvokeAccessAllowed(ic, appName, typeName, target, md, javaParams)) {
         throwAccessNotAllowed(propertyInvokeNotAuthorizedMessage(ic, typeName, target, property, appName));
      }
   }

   public static void checkUnSetAccess(InvocationContext ic, AttributeType attrType) throws Exception {
      BeanType type = attrType.getBeanType();
      String typeName = getTypeName(type);
      String appName = getAppName(ic, type);
      MethodDescriptor md = findSetMethod(attrType, "unSet");
      String target = md.getName();
      String property = attrType.getPropertyDescriptor().getName();
      Object[] javaParams = new Object[]{property};
      if (!isInvokeAccessAllowed(ic, appName, typeName, target, md, javaParams)) {
         throwAccessNotAllowed(propertyInvokeNotAuthorizedMessage(ic, typeName, target, property, appName));
      }
   }

   private static MethodDescriptor findSetMethod(AttributeType attrType, String methodName) throws Exception {
      MethodDescriptor[] mds = attrType.getBeanType().getBeanInfo().getMethodDescriptors();
      if (mds != null) {
         MethodDescriptor[] var3 = mds;
         int var4 = mds.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MethodDescriptor md = var3[var5];
            if (md.getName().equals(methodName)) {
               Class[] pts = md.getMethod().getParameterTypes();
               if (pts != null && pts.length == 1 && String.class.equals(pts[0])) {
                  return md;
               }
            }
         }
      }

      throw new AssertionError("Could not find method descriptor for method " + methodName + " for " + attrType.getBeanType().getName() + " " + attrType.getName());
   }

   public static boolean isInvokeAccessAllowed(InvocationContext ic, String appName, String typeName, String target, MethodDescriptor md, Object[] javaParams) throws Exception {
      return isInvokeAccessAllowed(ic, appName, typeName, "invoke", target, md, javaParams);
   }

   public static boolean isInvokeAccessAllowed(InvocationContext ic, String appName, String typeName, String operation, String target, MethodDescriptor md, Object[] javaParams) throws Exception {
      ObjectName objectName = JMXUtils.getObjectName(ic);
      ContextHandler context = SecurityHelper.getResourceContextHandler(new RestMBeanPartitionFinder(objectName, ic), new JMXContextHandler(objectName, JMXUtils.getJMXParams(ic, javaParams), getSignature(md), getAuditProtectedArgInfo(md), (Object)null), BeanType.getBeanType(ic.request(), typeName).getBeanInfo().getBeanDescriptor(), md, operation);
      return isAccessAllowed(ic, appName, typeName, operation, target, context);
   }

   private static String getAuditProtectedArgInfo(MethodDescriptor md) {
      return (String)md.getValue("wls:auditProtectedArgs");
   }

   private static String[] getSignature(MethodDescriptor md) throws Exception {
      Class[] pts = md.getMethod().getParameterTypes();
      if (pts == null) {
         return null;
      } else {
         String[] typeNames = new String[pts.length];

         for(int i = 0; i < pts.length; ++i) {
            typeNames[i] = getJMXClassName(pts[i]);
         }

         return typeNames;
      }
   }

   private static String getNotAuthorizedMessage(InvocationContext ic, String typeName, String target, String appName) throws Exception {
      return appName != null ? MessageUtils.beanFormatter(ic.request()).msgGetNotAuthorizedForApplication(ic.request().getRemoteUser(), typeName, target, appName) : MessageUtils.beanFormatter(ic.request()).msgGetNotAuthorized(ic.request().getRemoteUser(), typeName, target);
   }

   private static String setNotAuthorizedMessage(InvocationContext ic, String typeName, String target, String appName) throws Exception {
      return appName != null ? MessageUtils.beanFormatter(ic.request()).msgSetNotAuthorizedForApplication(ic.request().getRemoteUser(), typeName, target, appName) : MessageUtils.beanFormatter(ic.request()).msgSetNotAuthorized(ic.request().getRemoteUser(), typeName, target);
   }

   private static String invokeNotAuthorizedMessage(InvocationContext ic, String typeName, String target, String appName) throws Exception {
      return appName != null ? MessageUtils.beanFormatter(ic.request()).msgInvokeNotAuthorizedForApplication(ic.request().getRemoteUser(), typeName, target, appName) : MessageUtils.beanFormatter(ic.request()).msgInvokeNotAuthorized(ic.request().getRemoteUser(), typeName, target);
   }

   private static String propertyInvokeNotAuthorizedMessage(InvocationContext ic, String typeName, String target, String property, String appName) throws Exception {
      return appName != null ? MessageUtils.beanFormatter(ic.request()).msgPropertyInvokeNotAuthorizedForApplication(ic.request().getRemoteUser(), typeName, target, property, appName) : MessageUtils.beanFormatter(ic.request()).msgPropertyInvokeNotAuthorized(ic.request().getRemoteUser(), typeName, target, property);
   }

   private static void throwAccessNotAllowed(String message) throws Exception {
      if (DEBUG.isDebugEnabled()) {
         DEBUG.debug(message);
      }

      throw new WebApplicationException(Response.status(Status.FORBIDDEN).build());
   }

   private static boolean isAccessAllowed(InvocationContext ic, String app, String type, String operation, String target, ContextHandler context) throws Exception {
      return isAccessAllowed(ic.request(), app, type, operation, target, context);
   }

   private static boolean isAccessAllowed(HttpServletRequest request, String app, String type, String operation, String target, ContextHandler context) throws Exception {
      DefaultJMXPolicyManager.init();
      return SecurityUtils.isAccessAllowed(request, new JMXResource(operation, app, type, target), context);
   }

   private static String getTypeName(BeanType type) throws Exception {
      return type.getName();
   }

   private static String getAppName(InvocationContext ic, BeanType type) throws Exception {
      if (ic.bean() instanceof ConfigurationManagerMBean) {
         return null;
      } else if (ic.bean() instanceof ActivationTaskMBean) {
         return "ConfigurationManager";
      } else {
         String typeName = type.getName();
         String[] var3 = APP_SCOPED_TYPES;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String t = var3[var5];
            if (t.equals(typeName)) {
               return getName(ic.bean(), type);
            }
         }

         Object parent = PathUtils.getParent(ic);
         if (parent != null) {
            return getAppName(ic.clone(parent), BeanType.getBeanType(ic.request(), parent));
         } else {
            return null;
         }
      }
   }

   private static String getName(Object bean, BeanType type) throws Exception {
      PropertyType pt = type.getPropertyType("name");
      return pt != null ? (String)((String)pt.getReader().invoke(bean)) : null;
   }

   private static String getJMXClassName(Class classFromSignature) throws Exception {
      return getJMXClass(classFromSignature).getName();
   }

   private static Class getJMXClass(Class classFromSignature) throws Exception {
      if (String.class.equals(classFromSignature)) {
         return classFromSignature;
      } else {
         Class mappedType = PrimitiveMapper.lookupWrapperClass(classFromSignature.getName());
         if (mappedType != null) {
            return mappedType;
         } else if (BeanUtils.isBeanClass(classFromSignature)) {
            return ObjectName.class;
         } else if (classFromSignature.isArray()) {
            Class jmxComponentClass = getJMXClass(classFromSignature.getComponentType());
            return Array.newInstance(jmxComponentClass, 0).getClass();
         } else {
            return classFromSignature;
         }
      }
   }

   private static boolean isEncryptedProperty(AttributeType attrType) throws Exception {
      if (attrType instanceof PropertyType) {
         PropertyType pt = (PropertyType)attrType;
         if (pt.isEncrypted() || pt.isSensitive()) {
            return true;
         }
      }

      return false;
   }

   private static class RestMBeanPartitionFinder implements SecurityHelper.MBeanPartitionFinder {
      private ObjectName oname;
      private InvocationContext ic;

      private RestMBeanPartitionFinder(ObjectName oname, InvocationContext ic) {
         this.oname = oname;
         this.ic = ic;
      }

      public String getPartitionName() throws Exception {
         String partition = null;
         if (this.ic != null) {
            partition = PartitionUtils.getRootPartitionName(this.ic.clone(PartitionUtils.getRootPartitionBean(this.ic)));
         }

         return partition != null ? partition : "DOMAIN";
      }

      public ObjectName getObjectName() {
         return this.oname;
      }

      // $FF: synthetic method
      RestMBeanPartitionFinder(ObjectName x0, InvocationContext x1, Object x2) {
         this(x0, x1);
      }
   }
}
