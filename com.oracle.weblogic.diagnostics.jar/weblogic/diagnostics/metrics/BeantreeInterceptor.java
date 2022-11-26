package weblogic.diagnostics.metrics;

import com.oracle.weblogic.diagnostics.expressions.Traceable;
import java.beans.BeanInfo;
import java.beans.FeatureDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.WebLogicMBean;
import weblogic.management.mbeanservers.runtime.internal.DiagnosticSupportService;
import weblogic.management.provider.ManagementService;
import weblogic.management.provider.beaninfo.BeanInfoAccess;

class BeantreeInterceptor implements InvocationHandler, Traceable {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDiagnosticsExpressionMetrics");
   private static BeanInfoAccess beanInfoAccess;
   private WebLogicMBean delegate;
   private BeanInfo beanInfo;
   private DiagnosticSupportService dss = DiagnosticSupportService.getDiagnosticSupportService();
   private Class clazz;
   private BeantreeInterceptor parent;

   private BeantreeInterceptor(BeantreeInterceptor parent, WebLogicMBean mbean, Class clazz) {
      this.parent = parent;
      this.delegate = mbean;
      this.clazz = clazz;
   }

   public String getKey() {
      return this.dss.getObjectNameForInstance(this.delegate).getCanonicalName();
   }

   public Traceable getTraceableParent() {
      return this.parent;
   }

   public String getInstanceName() {
      return this.dss.getObjectNameForInstance(this.delegate).getCanonicalName();
   }

   static WebLogicMBean createProxy(BeantreeInterceptor parent, WebLogicMBean mbean, Class clazz) {
      Class var3 = BeantreeInterceptor.class;
      synchronized(BeantreeInterceptor.class) {
         if (beanInfoAccess == null) {
            beanInfoAccess = ManagementService.getBeanInfoAccess();
         }
      }

      WebLogicMBean proxy = (WebLogicMBean)Proxy.newProxyInstance(BeantreeInterceptor.class.getClassLoader(), new Class[]{clazz, Traceable.class}, new BeantreeInterceptor(parent, mbean, clazz));
      return proxy;
   }

   static WebLogicMBean createProxy(WebLogicMBean mbean, Class clazz) {
      return createProxy((BeantreeInterceptor)null, mbean, clazz);
   }

   public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
      if (this.beanInfo == null) {
         this.beanInfo = beanInfoAccess.getBeanInfoForInterface(this.clazz.getName(), true, (String)null);
      }

      Object invokeResult = null;
      if (!m.getDeclaringClass().equals(Traceable.class) && !m.getName().equals("iterator")) {
         if (!this.isAccessAllowed(m)) {
            throw new IllegalAccessError(m.getName());
         }

         invokeResult = m.invoke(this.delegate, args);
      } else {
         invokeResult = m.invoke(this, args);
      }

      Object result = null;
      Class returnType = m.getReturnType();
      Class arrayComponentType = returnType.getComponentType();
      boolean isWLMBeanArrayReturn = arrayComponentType != null && WebLogicMBean.class.isAssignableFrom(arrayComponentType);
      boolean isWLMBeanReturnType = WebLogicMBean.class.isAssignableFrom(returnType);
      if (invokeResult != null && (isWLMBeanReturnType || isWLMBeanArrayReturn)) {
         if (isWLMBeanArrayReturn) {
            WebLogicMBean[] proxyArray = createProxyArray(this, invokeResult, arrayComponentType, Array.getLength(invokeResult));
            result = proxyArray;
         } else {
            result = createProxy(this, (WebLogicMBean)invokeResult, returnType);
         }
      } else {
         result = invokeResult;
      }

      return result;
   }

   static WebLogicMBean[] createProxyArray(WebLogicMBean[] inputArray, Class arrayComponentType) {
      return createProxyArray((BeantreeInterceptor)null, inputArray, arrayComponentType, inputArray.length);
   }

   private static WebLogicMBean[] createProxyArray(BeantreeInterceptor parent, Object inputArray, Class arrayComponentType, int arrayLength) {
      WebLogicMBean[] resultSet = (WebLogicMBean[])((WebLogicMBean[])Array.newInstance(arrayComponentType, arrayLength));

      for(int i = 0; i < Array.getLength(inputArray); ++i) {
         WebLogicMBean arrayChildProxy = createProxy((WebLogicMBean)Array.get(inputArray, i), arrayComponentType);
         Array.set(resultSet, i, arrayChildProxy);
      }

      return resultSet;
   }

   private boolean isOperation(MethodDescriptor md) {
      String roleTag = "role";
      String opTag = "operation";
      Object roleValue = md.getValue("role");
      return roleValue != null && ((String)roleValue).equals("operation");
   }

   private MethodDescriptor findMethodDescriptor(Method m) {
      MethodDescriptor[] methodDescriptors = this.beanInfo.getMethodDescriptors();
      if (methodDescriptors != null) {
         MethodDescriptor[] var3 = methodDescriptors;
         int var4 = methodDescriptors.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            MethodDescriptor descriptor = var3[var5];
            if (m.getName().equals(descriptor.getName())) {
               return descriptor;
            }
         }
      }

      return null;
   }

   private boolean hasTags(Method m, FeatureDescriptor descriptor) {
      String[] basicTags = new String[]{"exclude", "encrypted", "internal"};
      String[] var4 = basicTags;
      int var5 = basicTags.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String tag = var4[var6];
         Object value = descriptor.getValue(tag);
         if (value != null) {
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug(descriptor.getName() + " has tag " + tag);
            }

            return true;
         }
      }

      return false;
   }

   private boolean isAccessAllowed(Method m) {
      String targetName = m.getName();
      Class declaringClass = m.getDeclaringClass();
      if (targetName.startsWith("set")) {
         return false;
      } else if (WebLogicMBean.class.isAssignableFrom(declaringClass)) {
         if (targetName.startsWith("get") || targetName.startsWith("is")) {
            PropertyDescriptor[] var9 = this.beanInfo.getPropertyDescriptors();
            int var5 = var9.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               PropertyDescriptor pd = var9[var6];
               Method writeMethod = pd.getWriteMethod();
               if (writeMethod != null && writeMethod.getName().equals(targetName)) {
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug(targetName + " is property write method");
                  }

                  return false;
               }

               if (pd.getReadMethod().getName().equals(targetName)) {
                  return !this.hasTags(m, pd);
               }
            }
         }

         MethodDescriptor md = this.findMethodDescriptor(m);
         if (md == null) {
            return false;
         } else if (!targetName.startsWith("lookup") && !targetName.startsWith("find")) {
            return !this.isOperation(md);
         } else {
            return !this.hasTags(m, md);
         }
      } else {
         String declaringClassPackageName = declaringClass.getPackage().getName();
         return declaringClassPackageName.startsWith("java.") || declaringClassPackageName.startsWith("javax.");
      }
   }
}
