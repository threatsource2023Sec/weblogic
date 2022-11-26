package weblogic.jaxrs.monitoring.impl;

import java.lang.reflect.Method;
import org.glassfish.jersey.server.model.ResourceMethod;
import org.glassfish.jersey.server.monitoring.ResourceMethodStatistics;
import weblogic.management.ManagementException;
import weblogic.management.runtime.JaxRsExecutionStatisticsRuntimeMBean;
import weblogic.management.runtime.JaxRsResourceMethodBaseRuntimeMBean;

class JaxRsResourceMethodBaseMBeanImpl extends JaxRsMonitoringInfoMBeanImpl implements JaxRsResourceMethodBaseRuntimeMBean {
   private final String path;
   private final boolean extended;
   private final String className;
   private final String methodName;
   private final String returnType;
   private final String[] parameterTypes;
   private final JaxRsExecutionStatisticsMBeanImpl requestStats;
   private final JaxRsExecutionStatisticsMBeanImpl methodStats;

   public JaxRsResourceMethodBaseMBeanImpl(String name, JaxRsMonitoringInfoMBeanImpl parent, ResourceMethod method, ResourceMethodStatistics stats, boolean fullPath, boolean extended) throws ManagementException {
      super(name, parent);
      this.path = fullPath ? JaxRsUriMBeanImpl.getFullPath(method) : this.getPath(method);
      this.extended = extended;
      Method javaMethod = method.getInvocable().getHandlingMethod();
      this.className = javaMethod.getDeclaringClass().getName();
      this.methodName = javaMethod.getName();
      this.returnType = javaMethod.getReturnType().getName();
      this.parameterTypes = new String[javaMethod.getParameterTypes().length];
      int i = 0;
      Class[] var9 = javaMethod.getParameterTypes();
      int var10 = var9.length;

      for(int var11 = 0; var11 < var10; ++var11) {
         Class type = var9[var11];
         this.parameterTypes[i++] = type.getName();
      }

      this.requestStats = new JaxRsExecutionStatisticsMBeanImpl(name + "_RequestStatistics", this, stats.getRequestStatistics());
      this.methodStats = new JaxRsExecutionStatisticsMBeanImpl(name + "_MethodStatistics", this, stats.getMethodStatistics());
   }

   private String getPath(ResourceMethod method) {
      return method.getParent().getParent() == null ? null : method.getParent().getPath();
   }

   public void update(ResourceMethodStatistics stats) {
      this.requestStats.update(stats.getRequestStatistics());
      this.methodStats.update(stats.getMethodStatistics());
   }

   public String getPath() {
      return this.path;
   }

   public boolean isExtended() {
      return this.extended;
   }

   public String getClassName() {
      return this.className;
   }

   public String getMethodName() {
      return this.methodName;
   }

   public String[] getParameterTypes() {
      return this.parameterTypes;
   }

   public String getReturnType() {
      return this.returnType;
   }

   public JaxRsExecutionStatisticsRuntimeMBean getMethodStatistics() {
      return this.methodStats;
   }

   public JaxRsExecutionStatisticsRuntimeMBean getRequestStatistics() {
      return this.requestStats;
   }
}
