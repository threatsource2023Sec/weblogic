package weblogic.management.rest.lib.bean.utils;

import java.beans.MethodDescriptor;
import javax.management.Attribute;
import org.glassfish.admin.rest.debug.DebugLogger;
import weblogic.management.internal.ConfigurationAuditor;

public class ConfigAuditUtils {
   private static final DebugLogger DEBUG = DebugLogger.getDebugLogger(ConfigAuditUtils.class);

   public static void auditInvoke(InvocationContext ic, MethodType methodType, Object[] javaParams, Exception ex) throws Exception {
      auditInvoke(ic, methodType.getMethodDescriptor(), javaParams, ex);
   }

   public static void auditInvoke(InvocationContext ic, MethodDescriptor md, Object[] javaParams, Exception ex) throws Exception {
      ConfigurationAuditor.getInstance().invoke(JMXUtils.getObjectName(ic), md, md.getMethod().getName(), JMXUtils.getJMXParams(ic, javaParams), ex);
   }

   public static void auditModify(InvocationContext ic, AttributeType attrType, Object oldJavaValue, Object newJavaValue, Exception ex) throws Exception {
      ConfigurationAuditor.getInstance().modify(JMXUtils.getObjectName(ic), JMXUtils.getJMXValue(ic, oldJavaValue), new Attribute(attrType.getPropertyDescriptor().getName(), JMXUtils.getJMXValue(ic, newJavaValue)), attrType.getPropertyDescriptor(), ex);
   }

   public static void auditCreate(InvocationContext ic, Exception ex) throws Exception {
      ConfigurationAuditor.getInstance().create(JMXUtils.getObjectName(ic), ex);
   }

   public static void auditRemove(InvocationContext ic, Exception ex) throws Exception {
      ConfigurationAuditor.getInstance().remove(JMXUtils.getObjectName(ic), ex);
   }
}
