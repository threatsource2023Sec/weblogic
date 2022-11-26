package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.harvester.jmx.BaseHarvesterImpl;
import com.bea.adaptive.harvester.jmx.MetricInfoManager;
import java.io.IOException;
import java.lang.reflect.Method;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import weblogic.diagnostics.harvester.HarvesterRuntimeException;
import weblogic.diagnostics.harvester.I18NConstants;
import weblogic.diagnostics.harvester.I18NSupport;
import weblogic.management.mbeanservers.Service;
import weblogic.management.runtime.RuntimeMBean;

public class BeanTreeHarvesterImpl extends BaseHarvesterImpl {
   public static BeanTreeHarvesterImpl getInstance() {
      return BeanTreeHarvesterImpl.BeanTreeHarvesterImplFactory.getInstance();
   }

   private BeanTreeHarvesterImpl(String name) throws Exception {
      this.setUpDebugFlags("DebugBeanTreeHarvester");
      BaseHarvesterImpl.initializeHarvesterInstance(this, name, "ServerRuntime", new BeanTreeRegistrationManager(this), new MetricInfoManager(this));
   }

   public void deallocate(boolean force) {
   }

   public String findTypeName(String instanceName) throws Exception {
      if (instanceName == null) {
         throw new HarvesterRuntimeException(I18NSupport.formatter().getNullParamMessage(I18NConstants.TYPE_I18N));
      } else {
         return TreeBeanHarvestableDataProviderHelper.getTypeNameForInstance(instanceName);
      }
   }

   protected Object getAttribute(Object bean, String attrName) throws Exception {
      String instanceName;
      if (bean instanceof ObjectName) {
         instanceName = ((ObjectName)bean).getCanonicalName();
         bean = TreeBeanHarvestableDataProviderHelper.getInstanceForObjectIdentifier(instanceName);
      } else {
         instanceName = TreeBeanHarvestableDataProviderHelper.getObjectNameForBean(bean);
      }

      String typeName = TreeBeanHarvestableDataProviderHelper.getTypeNameForInstance(instanceName);
      Method m = ((BeanTreeRegistrationManager)this.metricCache).getReadMethod(typeName, attrName);
      if (m == null) {
         return null;
      } else {
         Object result = m.invoke(bean);
         if (result != null) {
            if (!(result instanceof RuntimeMBean) && !(result instanceof Service)) {
               if (result.getClass().isArray()) {
                  Class componentType = result.getClass().getComponentType();
                  if (RuntimeMBean.class.isAssignableFrom(componentType) || Service.class.isAssignableFrom(componentType)) {
                     result = this.normalizeArrayResult((Object[])((Object[])result));
                  }
               }
            } else {
               String name = TreeBeanHarvestableDataProviderHelper.getObjectNameForBean(bean);

               try {
                  result = new ObjectName(name);
               } catch (MalformedObjectNameException var9) {
                  return name;
               }
            }
         }

         return result;
      }
   }

   private Object normalizeArrayResult(Object[] items) {
      ObjectName[] result = new ObjectName[items.length];

      for(int i = 0; i < items.length; ++i) {
         Object item = items[i];
         if (item != null && (item instanceof RuntimeMBean || item instanceof Service)) {
            String name = TreeBeanHarvestableDataProviderHelper.getObjectNameForBean(item);

            try {
               result[i] = new ObjectName(name);
            } catch (MalformedObjectNameException var7) {
               result[i] = null;
            }
         }
      }

      return result;
   }

   protected boolean instanceNameIsValid(String instName) {
      return true;
   }

   protected void validateInstanceName(String instName) throws IllegalArgumentException {
   }

   public int isTypeHandled(String typeName) {
      int vote = -1;
      if (typeName != null) {
         String[][] knownHarvestableTypes;
         try {
            knownHarvestableTypes = this.metricCache.getKnownHarvestableTypes(typeName);
         } catch (IOException var10) {
            throw new RuntimeException(var10);
         }

         if (knownHarvestableTypes != null && knownHarvestableTypes.length > 0) {
            vote = 2;
            String[][] var4 = knownHarvestableTypes;
            int var5 = knownHarvestableTypes.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               String[] typeInfo = var4[var6];
               String className = typeInfo[0];
               if (className != null) {
                  Class typeClass = getTypeClass(className);
                  if (typeClass != null) {
                     break;
                  }

                  if (this.dR()) {
                     this.dbgR("Could not find class for " + className);
                  }
               }
            }
         }
      }

      return vote;
   }

   private static Class getTypeClass(String typeName) {
      Class typeClass;
      try {
         typeClass = Class.forName(typeName);
      } catch (ClassNotFoundException var3) {
         return null;
      }

      return !RuntimeMBean.class.isAssignableFrom(typeClass) && !Service.class.isAssignableFrom(typeClass) ? null : typeClass;
   }

   protected Object getInstance(String instanceName) {
      return TreeBeanHarvestableDataProviderHelper.getInstanceForObjectIdentifier(instanceName);
   }

   // $FF: synthetic method
   BeanTreeHarvesterImpl(String x0, Object x1) throws Exception {
      this(x0);
   }

   private static class BeanTreeHarvesterImplFactory {
      private static BeanTreeHarvesterImpl SINGLETON;

      private static BeanTreeHarvesterImpl createBeanTreeHarvesterImpl() {
         try {
            return new BeanTreeHarvesterImpl("WLSBeanTreeHarvester");
         } catch (Exception var1) {
            throw new RuntimeException(var1);
         }
      }

      static synchronized BeanTreeHarvesterImpl getInstance() {
         if (SINGLETON == null) {
            SINGLETON = createBeanTreeHarvesterImpl();
         }

         return SINGLETON;
      }
   }
}
