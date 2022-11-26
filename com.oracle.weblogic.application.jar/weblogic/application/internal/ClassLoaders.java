package weblogic.application.internal;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import weblogic.application.ClassLoadingConfiguration;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.PreferApplicationPackagesBean;
import weblogic.j2ee.descriptor.wl.PreferApplicationResourcesBean;
import weblogic.utils.StringUtils;
import weblogic.utils.classloaders.Annotation;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;

public enum ClassLoaders {
   instance;

   private static final DebugLogger debugger = DebugLogger.getDebugLogger("DebugAppContainer");
   private final Map partitionCLs = new HashMap();
   private final Map sharedAppCLs = new HashMap();
   private final Map sharedAppRefs = new HashMap();

   public synchronized GenericClassLoader getOrCreatePartitionClassLoader(String partitionName) {
      GenericClassLoader cl = (GenericClassLoader)this.partitionCLs.get(partitionName);
      if (cl != null) {
         if (debugger.isDebugEnabled()) {
            debugger.debug("Partition class loader found, no need to create it: " + partitionName);
         }

         return cl;
      } else if (this.partitionCLs.containsKey(partitionName)) {
         throw new AssertionError("Partition Class Loader for " + partitionName + " already exists");
      } else {
         cl = new GenericClassLoader(AugmentableClassLoaderManager.getAugmentableSystemClassLoader());
         cl.setAnnotation(Annotation.createNonAppAnnotation("Partition:" + partitionName));
         this.partitionCLs.put(partitionName, cl);
         if (debugger.isDebugEnabled()) {
            debugger.debug("Created partition class loader for partition " + partitionName);
         }

         return cl;
      }
   }

   public synchronized GenericClassLoader getPartitionClassLoader(String partitionName) {
      return (GenericClassLoader)this.partitionCLs.get(partitionName);
   }

   public synchronized void destroyPartitionClassLoader(String partitionName) {
      if (!this.partitionCLs.containsKey(partitionName)) {
         throw new AssertionError("Partition Class Loader for " + partitionName + " does not exist");
      } else {
         ((GenericClassLoader)this.partitionCLs.remove(partitionName)).close();
         if (debugger.isDebugEnabled()) {
            debugger.debug("Removed partition class loader for partition " + partitionName);
         }

      }
   }

   public GenericClassLoader createSharedAppClassLoader(String appIdSansPartitionName, String moduleId, ClassLoadingConfiguration classLoadingConfiguration, GenericClassLoader parentCL) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      synchronized(this.sharedAppCLs) {
         if (this.sharedAppCLs.containsKey(clName)) {
            throw new AssertionError("Shared App Class Loader for " + clName + " already exists");
         } else {
            GenericClassLoader cl;
            if (parentCL == null) {
               cl = new GenericClassLoader(AugmentableClassLoaderManager.getAugmentableSystemClassLoader(), false);
            } else {
               cl = new GenericClassLoader(parentCL);
            }

            cl.setAnnotation(Annotation.createNonAppAnnotation("Shared:" + clName));
            this.sharedAppCLs.put(clName, new ShareableData(new SharingCriteria(classLoadingConfiguration), cl));
            if (debugger.isDebugEnabled()) {
               debugger.debug("Created shared application class loader for " + clName);
            }

            return cl;
         }
      }
   }

   public GenericClassLoader registerCustomSharedAppClassLoader(String appIdSansPartitionName, String moduleId, ClassLoadingConfiguration classLoadingConfiguration, GenericClassLoader proposedCL) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      synchronized(this.sharedAppCLs) {
         if (this.sharedAppCLs.containsKey(clName)) {
            return ((ShareableData)this.sharedAppCLs.get(clName)).get();
         } else {
            if (StringUtils.isEmptyString(proposedCL.getAnnotation().getDescription())) {
               proposedCL.setAnnotation(Annotation.createNonAppAnnotation("Shared:" + clName));
            }

            this.sharedAppCLs.put(clName, new ShareableData(new SharingCriteria(classLoadingConfiguration), proposedCL));
            if (debugger.isDebugEnabled()) {
               debugger.debug("Registered shared application class loader for " + clName);
            }

            return proposedCL;
         }
      }
   }

   public boolean isShareable(String appIdSansPartitionName, String moduleId, ClassLoadingConfiguration classLoadingConfiguration) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      if (debugger.isDebugEnabled()) {
         debugger.debug("Checking shareability with shared application class loader " + clName);
      }

      synchronized(this.sharedAppCLs) {
         ShareableData data = (ShareableData)this.sharedAppCLs.get(clName);
         return data == null ? false : data.isShareable(classLoadingConfiguration);
      }
   }

   public void addReferenceToSharedAppClassLoader(String appIdSansPartitionName, String moduleId, String appId) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      synchronized(this.sharedAppCLs) {
         Set refs = (Set)this.sharedAppRefs.get(clName);
         if (refs == null) {
            refs = new HashSet();
            this.sharedAppRefs.put(clName, refs);
         }

         ((Set)refs).add(appId);
         if (debugger.isDebugEnabled()) {
            debugger.debug("Added reference " + appId + " to shared application class loader " + clName);
         }

      }
   }

   public GenericClassLoader getSharedAppClassLoader(String appIdSansPartitionName, String moduleId) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      synchronized(this.sharedAppCLs) {
         ShareableData data = (ShareableData)this.sharedAppCLs.get(clName);
         return data != null ? data.get() : null;
      }
   }

   public Set getReferencesForSharedAppClassLoader(String appIdSansPartitionName, String moduleId) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      synchronized(this.sharedAppCLs) {
         return (Set)this.sharedAppRefs.get(clName);
      }
   }

   public void removeReferenceToSharedAppClassLoader(String appIdSansPartitionName, String moduleId, String appId) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      synchronized(this.sharedAppCLs) {
         Set refs = (Set)this.sharedAppRefs.get(clName);
         refs.remove(appId);
         if (refs.size() == 0) {
            this.sharedAppRefs.remove(clName);
         }

         if (debugger.isDebugEnabled()) {
            debugger.debug("Removing reference " + appId + " to shared application class loader " + clName);
         }

      }
   }

   public void removeReferenceOrDestroyOnLastReference(String appIdSansPartitionName, String moduleId, String appId) {
      if (this.getSharedAppClassLoader(appIdSansPartitionName, moduleId) != null) {
         this.removeReferenceToSharedAppClassLoader(appIdSansPartitionName, moduleId, appId);
         if (this.getReferencesForSharedAppClassLoader(appIdSansPartitionName, moduleId) == null) {
            this.destroySharedAppClassLoader(appIdSansPartitionName, moduleId);
         }
      }

   }

   public void destroySharedAppClassLoader(String appIdSansPartitionName, String moduleId) {
      String clName = this.getSharedAppClassLoaderName(appIdSansPartitionName, moduleId);
      synchronized(this.sharedAppCLs) {
         if (!this.sharedAppCLs.containsKey(clName)) {
            throw new AssertionError("Shared App Class Loader for " + clName + " does not exist");
         } else {
            Set refs = (Set)this.sharedAppRefs.get(clName);
            if (refs != null) {
               throw new IllegalStateException("Unable to destroy shared app class loader for " + clName + ". A few references to it still exist: " + refs);
            } else {
               ((ShareableData)this.sharedAppCLs.remove(clName)).get().close();
               if (debugger.isDebugEnabled()) {
                  debugger.debug("Destroyed shared application class loader for " + clName);
               }

            }
         }
      }
   }

   public String getSharedAppClassLoaderName(String appIdSansPartitionName, String moduleId) {
      if (appIdSansPartitionName != null && appIdSansPartitionName.length() != 0) {
         return moduleId != null && moduleId.length() != 0 ? appIdSansPartitionName + '@' + moduleId : appIdSansPartitionName;
      } else {
         throw new IllegalArgumentException("appIdSansPartitionName must neither be null nor empty");
      }
   }

   private class SharingCriteria {
      private final Descriptor dd;
      private final ClassLoadingConfiguration classLoadingConfiguration;

      private SharingCriteria(ClassLoadingConfiguration classLoadingConfiguration) {
         this.classLoadingConfiguration = classLoadingConfiguration;
         this.dd = this.getDescriptor(this.classLoadingConfiguration);
      }

      private boolean match(ClassLoadingConfiguration classLoadingConfiguration) {
         Descriptor dd = this.getDescriptor(classLoadingConfiguration);
         if (dd == null && this.dd == null) {
            if (ClassLoaders.debugger.isDebugEnabled()) {
               ClassLoaders.debugger.debug("Shareability match: Both descriptors are null");
            }

            return true;
         } else if (dd != null && this.dd != null) {
            if (ClassLoaders.debugger.isDebugEnabled()) {
               ClassLoaders.debugger.debug("Shareability check: Both descriptors are non-null, computing diff");
            }

            DescriptorDiff diff = this.dd.computeDiff(dd);
            Iterator var4 = diff.iterator();

            BeanUpdateEvent event;
            do {
               if (!var4.hasNext()) {
                  if (ClassLoaders.debugger.isDebugEnabled()) {
                     ClassLoaders.debugger.debug("Shareability match: No relevant differences found in non-null descriptors");
                  }

                  return true;
               }

               event = (BeanUpdateEvent)var4.next();
            } while(!(event.getProposedBean() instanceof PreferApplicationPackagesBean) && !(event.getProposedBean() instanceof PreferApplicationResourcesBean));

            if (ClassLoaders.debugger.isDebugEnabled()) {
               ClassLoaders.debugger.debug("Shareability match failed: At least one of the beans is different: prefer-application-packages or prefer-application-resources");
            }

            return false;
         } else {
            return dd == null ? this.hasFilteringConfiguration(this.classLoadingConfiguration) : this.hasFilteringConfiguration(classLoadingConfiguration);
         }
      }

      private Descriptor getDescriptor(ClassLoadingConfiguration c) {
         DescriptorBean bean = c == null ? null : this.getAnyNonNullBean(c.getPreferApplicationPackages(), c.getPreferApplicationResources(), c.getClassLoading());
         return bean != null ? bean.getDescriptor() : null;
      }

      private DescriptorBean getAnyNonNullBean(Object... beans) {
         Object[] var2 = beans;
         int var3 = beans.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Object bean = var2[var4];
            if (bean != null) {
               return (DescriptorBean)bean;
            }
         }

         return null;
      }

      private boolean hasFilteringConfiguration(ClassLoadingConfiguration classLoadingConfiguration) {
         boolean found = classLoadingConfiguration != null && (classLoadingConfiguration.getPreferApplicationPackages() != null || classLoadingConfiguration.getPreferApplicationResources() != null);
         if (ClassLoaders.debugger.isDebugEnabled()) {
            if (found) {
               ClassLoaders.debugger.debug("Shareability match failed: One of the two descriptors is non-null and has filtering configuration");
            } else {
               ClassLoaders.debugger.debug("Shareability match: One of the two descriptors is non-null but it has no filtering configuraiton");
            }
         }

         return found;
      }

      // $FF: synthetic method
      SharingCriteria(ClassLoadingConfiguration x1, Object x2) {
         this(x1);
      }
   }

   private class ShareableData {
      private final SharingCriteria criteria;
      private final GenericClassLoader cl;

      private ShareableData(SharingCriteria criteria, GenericClassLoader cl) {
         this.criteria = criteria;
         this.cl = cl;
      }

      private GenericClassLoader get() {
         return this.cl;
      }

      private boolean isShareable(ClassLoadingConfiguration classLoadingConfiguration) {
         return this.criteria.match(classLoadingConfiguration);
      }

      // $FF: synthetic method
      ShareableData(SharingCriteria x1, GenericClassLoader x2, Object x3) {
         this(x1, x2);
      }
   }
}
