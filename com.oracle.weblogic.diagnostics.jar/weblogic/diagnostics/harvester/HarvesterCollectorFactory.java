package weblogic.diagnostics.harvester;

import java.util.HashMap;
import java.util.Map;
import weblogic.diagnostics.descriptor.WLDFResourceBean;
import weblogic.diagnostics.harvester.internal.HarvesterCollectorFactoryImpl;

public abstract class HarvesterCollectorFactory {
   private static Map hvstCollectorFactories = new HashMap();

   protected HarvesterCollectorFactory() {
   }

   public static HarvesterCollectorFactory getFactoryInstance() {
      return getFactoryInstance("");
   }

   public static synchronized HarvesterCollectorFactory getFactoryInstance(String name) {
      HarvesterCollectorFactory factory = (HarvesterCollectorFactory)hvstCollectorFactories.get(name);
      if (factory == null) {
         try {
            factory = new HarvesterCollectorFactoryImpl(name);
            hvstCollectorFactories.put(name, factory);
         } catch (Exception var3) {
            throw new HarvesterRuntimeException(var3);
         }
      }

      return (HarvesterCollectorFactory)factory;
   }

   public abstract HarvesterCollector findOrCreateHarvesterCollector(WLDFResourceBean var1);

   public abstract void destroyHarvesterCollector(WLDFResourceBean var1);

   public abstract HarvesterCollector lookupHarvesterCollector(WLDFResourceBean var1);

   public abstract HarvesterCollector[] listHarvesterCollectors();

   public abstract int getNumActiveHarvesterModules();
}
