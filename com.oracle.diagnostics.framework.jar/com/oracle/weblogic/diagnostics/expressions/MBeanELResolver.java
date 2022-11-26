package com.oracle.weblogic.diagnostics.expressions;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import javax.el.ELContext;
import javax.el.ELException;
import javax.el.ELResolver;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.openmbean.CompositeData;
import weblogic.diagnostics.debug.DebugLogger;

public class MBeanELResolver extends ELResolver {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsMBeanELResolver");
   private MBeanServerConnection mbeanServerConnection;

   public MBeanELResolver() {
   }

   public MBeanELResolver(MBeanServerConnection source) {
      this.mbeanServerConnection = source;
   }

   public Object getValue(ELContext context, Object base, Object property) {
      Object result = null;
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getClass().getSimpleName() + " called for property \"" + property + "\" for base " + base);
      }

      try {
         if (base instanceof ObjectName && this.mbeanServerConnection != null) {
            context.setPropertyResolved(true);
            ObjectName instanceName = (ObjectName)base;
            result = this.mbeanServerConnection.getAttribute(instanceName, (String)property);
         } else if (base instanceof ObjectInstance && this.mbeanServerConnection != null) {
            context.setPropertyResolved(true);
            ObjectInstance instance = (ObjectInstance)base;
            result = this.mbeanServerConnection.getAttribute(instance.getObjectName(), (String)property);
         } else if (base instanceof CompositeData) {
            context.setPropertyResolved(true);
            return ((CompositeData)base).get((String)property);
         }

         return result;
      } catch (Exception var6) {
         throw new ELException(var6);
      }
   }

   public Object invoke(ELContext context, Object base, Object method, Class[] paramTypes, Object[] params) {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug(this.getClass().getSimpleName() + " [base: " + base + ", method: " + method + ", params: " + Arrays.toString(params) + ", paramTypes: " + Arrays.toString(paramTypes) + "]");
      }

      if (context == null) {
         throw new NullPointerException();
      } else if (!(method instanceof String)) {
         return null;
      } else {
         String trimmedMethod = method.toString().trim();

         try {
            if (base instanceof ObjectName) {
               context.setPropertyResolved(true);
               String[] mbeanParamTypes = this.convertParamTypesToStringArray(paramTypes);
               return this.mbeanServerConnection.invoke((ObjectName)base, trimmedMethod, params, mbeanParamTypes);
            } else if (base instanceof ObjectInstance) {
               context.setPropertyResolved(true);
               ObjectInstance objectInstance = (ObjectInstance)base;
               String[] mbeanParamTypes = this.convertParamTypesToStringArray(paramTypes);
               return this.mbeanServerConnection.invoke(objectInstance.getObjectName(), trimmedMethod, params, mbeanParamTypes);
            } else {
               return null;
            }
         } catch (MBeanException | ReflectionException | IOException | InstanceNotFoundException var9) {
            throw new ELException(var9);
         }
      }
   }

   public Class getType(ELContext context, Object base, Object property) {
      return null;
   }

   public void setValue(ELContext context, Object base, Object property, Object value) {
      throw new UnsupportedOperationException();
   }

   public boolean isReadOnly(ELContext context, Object base, Object property) {
      return true;
   }

   public Iterator getFeatureDescriptors(ELContext context, Object base) {
      throw new UnsupportedOperationException();
   }

   public Class getCommonPropertyType(ELContext context, Object base) {
      throw new UnsupportedOperationException();
   }

   private String[] convertParamTypesToStringArray(Class[] paramTypes) {
      String[] mbeanParamTypes = null;
      if (paramTypes != null) {
         mbeanParamTypes = new String[paramTypes.length];
         int i = 0;
         Class[] var4 = paramTypes;
         int var5 = paramTypes.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Class paramType = var4[var6];
            mbeanParamTypes[i++] = paramType.getName();
         }
      }

      return mbeanParamTypes;
   }
}
