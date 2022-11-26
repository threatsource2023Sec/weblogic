package weblogic.security.utils;

import java.lang.annotation.Annotation;
import java.util.concurrent.ConcurrentHashMap;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.invocation.ComponentInvocationContext;
import weblogic.invocation.ComponentInvocationContextManager;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.PartitionMBean;
import weblogic.management.provider.RuntimeAccess;
import weblogic.management.security.RealmMBean;
import weblogic.security.shared.LoggerWrapper;
import weblogic.server.GlobalServiceLocator;

public class PartitionUtilsDelegateImpl implements PartitionUtilsDelegate {
   private static final String EMPTY_VALUE = "";
   private static final ConcurrentHashMap partitionsToRealmName = new ConcurrentHashMap();
   private static final ConcurrentHashMap partitionsToIDD = new ConcurrentHashMap();
   private static final LoggerWrapper debugLogger = LoggerWrapper.getInstance("SecurityRealm");
   private volatile RuntimeAccess runtimeAccess;

   public String getPartitionName() {
      ComponentInvocationContext cic = ComponentInvocationContextManager.getInstance().getCurrentComponentInvocationContext();
      if (cic != null && !cic.isGlobalRuntime()) {
         String result = cic.getPartitionName();
         if (debugLogger.isDebugEnabled()) {
            debugLogger.debug("getPartitionName: found " + result);
         }

         return result;
      } else {
         return null;
      }
   }

   public String getRealmName(String partitionName, ConfigurationMBean mbean) {
      if (partitionName == null) {
         return null;
      } else {
         DomainMBean proposedDomain = (DomainMBean)mbean;
         if (proposedDomain != null) {
            if (!(proposedDomain instanceof DomainMBean)) {
               throw new IllegalArgumentException("The getRealmName method requires the use of a domain mbean, not any other type of bean");
            }

            PartitionMBean partition = proposedDomain.lookupPartition(partitionName);
            if (partition == null) {
               return null;
            }

            RealmMBean realm = partition.getRealm();
            if (realm != null) {
               return realm.getName();
            }
         }

         String name = (String)partitionsToRealmName.get(partitionName);
         if (name != null) {
            return name.length() == 0 ? null : name;
         } else {
            PartitionMBean partition = this.getPartition(partitionName);
            if (partition != null) {
               RealmMBean realm = partition.getRealm();
               String result;
               if (realm != null) {
                  result = realm.getName();
               } else {
                  result = null;
               }

               String cachedValue = null == result ? "" : result;
               partitionsToRealmName.put(partitionName, cachedValue);
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("getRealmFromPartition: added mapping for " + partitionName + " to " + result);
               }

               PartitionUtilsDelegateImpl.PartitionMBeanChangeListenerLoader.addListener(partition);
               return null != result && result.length() > 0 ? result : null;
            } else {
               return null;
            }
         }
      }
   }

   public String getPrimaryIdentityDomain(String partitionName) {
      if (partitionName == null) {
         return null;
      } else {
         String name = (String)partitionsToIDD.get(partitionName);
         if (name != null) {
            return "".equals(name) ? null : name;
         } else {
            PartitionMBean partition = this.getPartition(partitionName);
            return partition == null ? null : this.updateIDDCache(partition, partitionName);
         }
      }
   }

   private RuntimeAccess getRuntimeAccess() {
      if (this.runtimeAccess != null) {
         return this.runtimeAccess;
      } else {
         synchronized(this) {
            if (this.runtimeAccess != null) {
               return this.runtimeAccess;
            } else {
               this.runtimeAccess = (RuntimeAccess)GlobalServiceLocator.getServiceLocator().getService(RuntimeAccess.class, new Annotation[0]);
               return this.runtimeAccess;
            }
         }
      }
   }

   public String getAdminIdentityDomain() {
      return this.getRuntimeAccess().getDomain().getSecurityConfiguration().getAdministrativeIdentityDomain();
   }

   public String getCurrentIdentityDomain() {
      String partitionName = this.getPartitionName();
      return partitionName != null ? this.getPrimaryIdentityDomain(partitionName) : this.getAdminIdentityDomain();
   }

   private PartitionMBean getPartition(String partitionName) {
      return this.getRuntimeAccess().getDomain().lookupPartition(partitionName);
   }

   private String updateIDDCache(PartitionMBean partition, String partitionName) {
      String identityDomain = partition.getPrimaryIdentityDomain();
      String cachedValue = null == identityDomain ? "" : identityDomain;
      partitionsToIDD.put(partitionName, cachedValue);
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("getPrimaryIdentityDomain: added mapping for " + partitionName + " to " + identityDomain);
      }

      PartitionUtilsDelegateImpl.PartitionMBeanChangeListenerLoader.addListener(partition);
      return null != identityDomain && identityDomain.length() > 0 ? identityDomain : null;
   }

   private static class PartitionMBeanChangeListenerLoader {
      private static final BeanAttributeChangeListener.AttributeChangeHandler[] partitionMBeanChangeHandlers = new BeanAttributeChangeListener.AttributeChangeHandler[]{new BeanAttributeChangeListener.AttributeChangeHandler("PrimaryIdentityDomain") {
         public void update(BeanUpdateEvent updateEvent, BeanUpdateEvent.PropertyUpdate propertyUpdate) {
            if (propertyUpdate.getUpdateType() == 1) {
               PartitionMBean mBean = (PartitionMBean)updateEvent.getSourceBean();
               String partitionName = mBean.getName();
               PartitionUtilsDelegateImpl.partitionsToIDD.remove(partitionName);
               if (PartitionUtilsDelegateImpl.debugLogger.isDebugEnabled()) {
                  PartitionUtilsDelegateImpl.debugLogger.debug("Invalidated PartitionMBean PrimaryIdentityDomain cache for partition " + partitionName);
               }

            }
         }
      }, new BeanAttributeChangeListener.AttributeChangeHandler("Realm") {
         public void update(BeanUpdateEvent updateEvent, BeanUpdateEvent.PropertyUpdate propertyUpdate) {
            if (propertyUpdate.getUpdateType() == 1) {
               PartitionMBean mBean = (PartitionMBean)updateEvent.getSourceBean();
               String partitionName = mBean.getName();
               PartitionUtilsDelegateImpl.partitionsToRealmName.remove(partitionName);
               if (PartitionUtilsDelegateImpl.debugLogger.isDebugEnabled()) {
                  PartitionUtilsDelegateImpl.debugLogger.debug("Invalidated PartitionMBean Realm cache for partition " + partitionName);
               }

            }
         }
      }};
      private static final BeanAttributeChangeListener.AttributeChangeHandler[] domainMBeanChangeHandlers = new BeanAttributeChangeListener.AttributeChangeHandler[]{new BeanAttributeChangeListener.AttributeChangeHandler("Partitions") {
         public void update(BeanUpdateEvent updateEvent, BeanUpdateEvent.PropertyUpdate propertyUpdate) {
            if (propertyUpdate.getUpdateType() == 3) {
               Object removedObj = propertyUpdate.getRemovedObject();
               if (removedObj instanceof PartitionMBean) {
                  PartitionMBean removedBean = (PartitionMBean)removedObj;
                  String partitionName = removedBean.getName();
                  PartitionUtilsDelegateImpl.partitionsToIDD.remove(partitionName);
                  PartitionUtilsDelegateImpl.partitionsToRealmName.remove(partitionName);
                  PartitionUtilsDelegateImpl.PartitionMBeanChangeListenerLoader.changeListener.removeListenerIfPresent(removedBean);
                  if (PartitionUtilsDelegateImpl.debugLogger.isDebugEnabled()) {
                     PartitionUtilsDelegateImpl.debugLogger.debug("Invalidated PartitionMBean cache for removed partition " + partitionName);
                  }

               }
            }
         }
      }};
      private static final BeanAttributeChangeListener changeListener;
      private static final BeanAttributeChangeListener removeListener;

      static final void addListener(PartitionMBean partitionMBean) {
         changeListener.addListenerIfAbsent(partitionMBean);
         if (partitionMBean.getParentBean() instanceof DomainMBean) {
            removeListener.addListenerIfAbsent((DomainMBean)partitionMBean.getParentBean());
         }

      }

      static {
         changeListener = new BeanAttributeChangeListener(PartitionMBean.class, partitionMBeanChangeHandlers, PartitionUtilsDelegateImpl.debugLogger);
         removeListener = new BeanAttributeChangeListener(DomainMBean.class, domainMBeanChangeHandlers, PartitionUtilsDelegateImpl.debugLogger);
      }
   }
}
