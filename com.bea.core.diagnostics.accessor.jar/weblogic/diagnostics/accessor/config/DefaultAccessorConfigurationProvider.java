package weblogic.diagnostics.accessor.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import weblogic.diagnostics.accessor.AccessorConfiguration;
import weblogic.diagnostics.accessor.AccessorConfigurationProvider;

public class DefaultAccessorConfigurationProvider implements AccessorConfigurationProvider {
   private boolean dataRetirementEnabled;
   private int preferredStoreSizeLimit;
   private String storeDirectory;
   private int storeSizeCheckPeriod;
   private Map accessorMap = new HashMap();

   public DefaultAccessorConfigurationProvider(String storeDirectory) {
      this.storeDirectory = storeDirectory;
   }

   public boolean isDataRetirementEnabled() {
      return this.dataRetirementEnabled;
   }

   public void setDataRetirementEnabled(boolean enabled) {
      this.dataRetirementEnabled = enabled;
   }

   public boolean isDataRetirementTestModeEnabled() {
      return false;
   }

   public int getPreferredStoreSizeLimit() {
      return this.preferredStoreSizeLimit;
   }

   public void setPreferredStoreSizeLimit(int size) {
      this.preferredStoreSizeLimit = size;
      if (this.preferredStoreSizeLimit < 10) {
         this.preferredStoreSizeLimit = 10;
      }

   }

   public String getStoreDirectory() {
      return this.storeDirectory;
   }

   public int getStoreSizeCheckPeriod() {
      return this.storeSizeCheckPeriod;
   }

   public void setStoreSizeCheckPeriod(int period) {
      this.storeSizeCheckPeriod = period;
   }

   public synchronized AccessorConfiguration getAccessorConfiguration(String accessorName) {
      return (AccessorConfiguration)this.accessorMap.get(accessorName);
   }

   public synchronized String[] getAccessorNames() {
      String[] names = new String[this.accessorMap.size()];
      Set nameSet = this.accessorMap.keySet();
      names = (String[])((String[])nameSet.toArray(names));
      return names;
   }

   public synchronized void addAccessor(AccessorConfiguration accessor) {
      String name = accessor.getName();
      if (this.accessorMap.get(name) == null) {
         this.accessorMap.put(name, accessor);
      }

   }

   public synchronized void removeAccessor(String name) {
      this.accessorMap.remove(name);
   }
}
