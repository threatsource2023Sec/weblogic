package com.oracle.weblogic.diagnostics.expressions;

import com.oracle.weblogic.diagnostics.utils.BeanInfoCopy;
import com.oracle.weblogic.diagnostics.utils.BeanInfoLocalizer;
import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Locale;
import javax.inject.Singleton;
import org.glassfish.hk2.api.PerLookup;
import weblogic.diagnostics.debug.DebugLogger;

public class Utils {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsUtils");

   public static TrackedValue convertPrimitiveToMetricValue(Object result, String attributePath, Traceable parent, String instanceName) {
      TrackedValue valueBean = null;
      if (result instanceof String) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Converting MBean result to StringMetricValue: " + result);
         }

         valueBean = new StringTrackedValue(parent, instanceName, attributePath, (String)result);
      } else if (result instanceof Number) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Converting MBean result to NumericMetricValueBean: " + result);
         }

         valueBean = new NumericTrackedValue(parent, instanceName, attributePath, (Number)result);
      } else if (result != null && (result instanceof Boolean || Boolean.TYPE.isAssignableFrom(result.getClass()))) {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Converting MBean result to BooleanMetricValue: " + result);
         }

         valueBean = new BooleanTrackedValue(parent, instanceName, attributePath, (Boolean)result);
      } else {
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("Did not get expected result type: " + result + "(" + result.getClass().getName() + ")");
         }

         valueBean = new UnknownValueType(parent, instanceName, attributePath, result);
      }

      return (TrackedValue)valueBean;
   }

   static Traceable findParentInChain(Deque termPath) {
      Term lastTerm = null;
      Deque clonePath = new ArrayDeque(termPath);

      do {
         if (clonePath.isEmpty()) {
            return null;
         }

         lastTerm = (Term)clonePath.pop();
      } while(!lastTerm.isTraceable());

      return (Traceable)lastTerm.getValue();
   }

   public static BeanInfo buildLocalizedExpressionBeanInfo(Class implementationClass, Locale l) throws IntrospectionException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Introspecting bean class " + implementationClass.getName());
      }

      BeanInfo beanInfo = getBeanInfoForClass(implementationClass);
      BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
      ExpressionBean beanAnnotation = (ExpressionBean)implementationClass.getAnnotation(ExpressionBean.class);
      if (beanAnnotation != null) {
         beanDescriptor.setName(beanAnnotation.name());
         beanDescriptor.setDisplayName(beanAnnotation.prefix() + "." + beanAnnotation.name());
         beanDescriptor.setValue("namespace", beanAnnotation.prefix());
         beanDescriptor.setValue("prefix", beanAnnotation.prefix());
      }

      Singleton singletonAnnotation = (Singleton)implementationClass.getAnnotation(Singleton.class);
      if (singletonAnnotation != null) {
         beanDescriptor.setValue("scope", "@Singleton");
      } else {
         PerLookup perLookupAnnotation = (PerLookup)implementationClass.getAnnotation(PerLookup.class);
         if (perLookupAnnotation != null) {
            beanDescriptor.setValue("scope", "@PerLookup");
         }
      }

      PropertyDescriptor[] var10 = beanInfo.getPropertyDescriptors();
      int var7 = var10.length;

      int var8;
      for(var8 = 0; var8 < var7; ++var8) {
         PropertyDescriptor pd = var10[var8];
         if (ExpressionExtensionsManager.isExcluded(pd)) {
            pd.setHidden(true);
         } else {
            pd.setHidden(false);
         }
      }

      MethodDescriptor[] var11 = beanInfo.getMethodDescriptors();
      var7 = var11.length;

      for(var8 = 0; var8 < var7; ++var8) {
         MethodDescriptor op = var11[var8];
         if (op.getMethod().getAnnotation(Exclude.class) != null) {
            op.setHidden(true);
         } else {
            op.setHidden(false);
         }
      }

      BeanInfoLocalizer.localize(beanInfo, l);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Generated BeanInfo: " + beanInfo.toString());
      }

      return beanInfo;
   }

   public static BeanInfo getBeanInfoForClass(Class implementationClass) throws IntrospectionException {
      BeanInfo _beanInfo = Introspector.getBeanInfo(implementationClass, Object.class);
      BeanInfo beanInfo = new BeanInfoCopy(_beanInfo);
      return beanInfo;
   }
}
