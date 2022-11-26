package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.jmx.PartitionRuntimeMBeanSetManager;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Singleton;
import org.jvnet.hk2.annotations.Service;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.runtime.AppClientComponentRuntimeMBean;
import weblogic.management.runtime.EJBComponentRuntimeMBean;
import weblogic.management.runtime.InterceptionComponentRuntimeMBean;
import weblogic.management.runtime.JDBCAbstractDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCOracleDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCProxyDataSourceRuntimeMBean;
import weblogic.management.runtime.JDBCUCPDataSourceRuntimeMBean;
import weblogic.management.runtime.JMSComponentRuntimeMBean;
import weblogic.management.runtime.PartitionRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.SCAPojoComponentRuntimeMBean;
import weblogic.management.runtime.SCASpringComponentRuntimeMBean;
import weblogic.management.runtime.WebAppComponentRuntimeMBean;

@Service
@Singleton
public class PartitionRuntimeScopedMBeansSet implements PartitionRuntimeMBeanSetManager {
   private static final DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugPartitionRuntimeMBeanSetBuilder");
   private static Set partitionScopedMBeans;

   public boolean isTypeInSet(String typeName) {
      Class var2 = PartitionRuntimeScopedMBeansSet.class;
      synchronized(PartitionRuntimeScopedMBeansSet.class) {
         if (partitionScopedMBeans == null) {
            buildPartitionedScopedMBeansList();
         }
      }

      return partitionScopedMBeans.contains(typeName);
   }

   private static void buildPartitionedScopedMBeansList() {
      partitionScopedMBeans = new HashSet();
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Building set of known partition-visible WLS RuntimeMBeans parented by PartitionRuntimeMBean");
      }

      introspectInterface(PartitionRuntimeMBean.class);
      introspectInterface(AppClientComponentRuntimeMBean.class);
      introspectInterface(EJBComponentRuntimeMBean.class);
      introspectInterface(WebAppComponentRuntimeMBean.class);
      introspectInterface(InterceptionComponentRuntimeMBean.class);
      introspectInterface(JDBCAbstractDataSourceRuntimeMBean.class);
      introspectInterface(JDBCOracleDataSourceRuntimeMBean.class);
      introspectInterface(JDBCProxyDataSourceRuntimeMBean.class);
      introspectInterface(JDBCUCPDataSourceRuntimeMBean.class);
      introspectInterface(JMSComponentRuntimeMBean.class);
      introspectInterface(SCAPojoComponentRuntimeMBean.class);
      introspectInterface(SCASpringComponentRuntimeMBean.class);
      if (debugLogger.isDebugEnabled()) {
         Object[] array = partitionScopedMBeans.toArray();
         Arrays.sort(array);
         debugLogger.debug("Total set of partition scoped MBeans: " + partitionScopedMBeans.size());
         debugLogger.debug("Partition-visible set: " + Arrays.toString(array));
      }

   }

   private static void introspectInterface(Class clazz) {
      partitionScopedMBeans.add(clazz.getName());
      Method[] var1 = clazz.getMethods();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Method m = var1[var3];
         if (Modifier.isPublic(m.getModifiers())) {
            Class returnType = m.getReturnType();
            if (returnType.isArray()) {
               returnType = returnType.getComponentType();
            }

            if (clazz != returnType && !clazz.isPrimitive() && RuntimeMBean.class.isAssignableFrom(returnType) && !partitionScopedMBeans.contains(returnType.getName())) {
               introspectInterface(returnType);
            }
         }
      }

   }
}
