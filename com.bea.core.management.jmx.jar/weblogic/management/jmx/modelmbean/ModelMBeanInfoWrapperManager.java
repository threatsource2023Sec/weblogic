package weblogic.management.jmx.modelmbean;

import java.beans.BeanInfo;
import java.beans.PropertyDescriptor;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import javax.management.OperationsException;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.CompositeTypeProperties;
import weblogic.management.jmx.CompositeTypeThrowable;
import weblogic.management.jmx.ObjectNameManager;
import weblogic.management.jmx.PrimitiveMapper;
import weblogic.management.provider.BeanInfoKey;
import weblogic.management.provider.beaninfo.BeanInfoAccess;
import weblogic.management.provider.core.ManagementCoreService;
import weblogic.timers.Timer;
import weblogic.timers.TimerListener;
import weblogic.timers.TimerManagerFactory;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public class ModelMBeanInfoWrapperManager {
   static final BeanInfoAccess beanInfoAccess = ManagementCoreService.getBeanInfoAccess();
   private static DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   private static final Map mbeanInfos = new ConcurrentWeakHashMap();
   private static Map openTypes = new ConcurrentWeakHashMap();
   private static long FiveMinutes = 300000L;

   public static ModelMBeanInfoWrapper getModelMBeanInfoForInstance(Object wlsBean, boolean readOnly, String version, ObjectNameManager nameManager) throws OperationsException, MBeanVersionMismatchException {
      if (debug.isDebugEnabled()) {
         debug.debug("getModelMBeanInfo for " + wlsBean.getClass().getName() + " readOnly: " + readOnly + " version: " + version);
      }

      BeanInfoKey beanInfoKey = beanInfoAccess.getBeanInfoKeyForInstance(wlsBean, readOnly, version);
      return getModelMBeanInfoWrapper(beanInfoKey, nameManager);
   }

   public static ModelMBeanInfoWrapper getModelMBeanInfoForInterface(String beanInterfaceName, boolean readOnly, String version, ObjectNameManager nameManager) throws OperationsException, MBeanVersionMismatchException {
      if (debug.isDebugEnabled()) {
         debug.debug("getModelMBeanInfo for " + beanInterfaceName + " readOnly: " + readOnly + " version: " + version);
      }

      BeanInfoKey beanInfoKey = beanInfoAccess.getBeanInfoKeyForInterface(beanInterfaceName, readOnly, version);
      if (beanInfoKey == null) {
         throw new OperationsException("Unable to acquire MBeanInfo for " + beanInterfaceName);
      } else {
         return getModelMBeanInfoWrapper(beanInfoKey, nameManager);
      }
   }

   private static void releaseInactiveMBeanInfos() {
      if (debug.isDebugEnabled()) {
         debug.debug("ModelBeanWrapperManager release inactive MBeanInfo...");
      }

      synchronized(mbeanInfos) {
         long fiveMinAgo = System.currentTimeMillis() - FiveMinutes;
         Iterator var3 = mbeanInfos.values().iterator();

         while(var3.hasNext()) {
            WeakReference wrapperRef = (WeakReference)var3.next();
            ModelMBeanInfoWrapper wrapper = (ModelMBeanInfoWrapper)wrapperRef.get();
            if (wrapper != null) {
               wrapper.releaseInactiveMBeanInfo(fiveMinAgo);
            }
         }

      }
   }

   public static OpenType getOpenTypeForInstance(Object instance, String version, ObjectNameManager nameManager) throws OpenDataException {
      if (debug.isDebugEnabled()) {
         debug.debug("geOpenType for " + instance.getClass().getName() + " version: " + version);
      }

      BeanInfoKey beanInfoKey = beanInfoAccess.getBeanInfoKeyForInstance(instance, true, version);
      return getOpenType(beanInfoKey, version, nameManager);
   }

   public static OpenType getOpenTypeForInterface(String interfaceName, String version, ObjectNameManager nameManager) throws OpenDataException {
      if (debug.isDebugEnabled()) {
         debug.debug("getOpenType for " + interfaceName + " version: " + version);
      }

      BeanInfoKey beanInfoKey = beanInfoAccess.getBeanInfoKeyForInterface(interfaceName, true, version);
      return beanInfoKey == null ? null : getOpenType(beanInfoKey, version, nameManager);
   }

   private static OpenType getOpenType(BeanInfoKey beanInfoKey, String version, ObjectNameManager nameManager) throws OpenDataException {
      ModelMBeanInfoWrapperKey key = new ModelMBeanInfoWrapperKey(beanInfoKey, nameManager);
      OpenType result = (OpenType)openTypes.get(key);
      if (result != null) {
         if (debug.isDebugEnabled()) {
            debug.debug("OpenTypes found in cache " + openTypes.size());
         }

         return result;
      } else {
         BeanInfo beanInfo = beanInfoAccess.getBeanInfo(beanInfoKey);
         result = buildOpenType(beanInfo, version, nameManager);
         openTypes.put(key, result);
         if (debug.isDebugEnabled()) {
            debug.debug("OpenTypes cache count " + openTypes.size());
         }

         return result;
      }
   }

   private static OpenType buildOpenType(BeanInfo beanInfo, String version, ObjectNameManager nameManager) throws OpenDataException {
      String typeName = (String)beanInfo.getBeanDescriptor().getValue("interfaceclassname");
      String description = beanInfo.getBeanDescriptor().getShortDescription();
      String[] itemNames = null;
      String[] itemDescriptions = null;
      OpenType[] itemTypes = null;
      PropertyDescriptor[] properties = beanInfo.getPropertyDescriptors();
      if (properties != null && properties.length != 0) {
         itemNames = new String[properties.length];
         itemDescriptions = new String[properties.length];
         itemTypes = new OpenType[properties.length];

         for(int i = 0; i < properties.length; ++i) {
            PropertyDescriptor property = properties[i];
            itemNames[i] = property.getName();
            itemDescriptions[i] = property.getShortDescription();
            OpenType currentType = convertClassToOpenType(property.getPropertyType(), version, nameManager);
            if (currentType == null) {
               throw new AssertionError("Unable to convert " + property.getName() + " of type " + property.getPropertyType() + " for version " + version);
            }

            itemTypes[i] = currentType;
         }
      }

      CompositeType openType = new CompositeType(typeName, description, itemNames, itemDescriptions, itemTypes);
      return openType;
   }

   private static OpenType convertClassToOpenType(Class propertyType, String version, ObjectNameManager nameManager) throws OpenDataException {
      String propertyClassName = propertyType.getName();
      OpenType returnType = PrimitiveMapper.lookupOpenType(propertyClassName);
      if (returnType != null) {
         return returnType;
      } else if (Throwable.class.isAssignableFrom(propertyType)) {
         return CompositeTypeThrowable.THROWABLE;
      } else if (CompositeTypeProperties.class.isAssignableFrom(propertyType)) {
         return CompositeTypeProperties.PROPERTIES;
      } else {
         return (OpenType)(nameManager.isClassMapped(propertyType) ? SimpleType.OBJECTNAME : getOpenTypeForInterface(propertyClassName, version, nameManager));
      }
   }

   private static ModelMBeanInfoWrapper getModelMBeanInfoWrapper(BeanInfoKey beanInfoKey, ObjectNameManager nameManager) throws OperationsException, MBeanVersionMismatchException {
      ModelMBeanInfoWrapperKey key = new ModelMBeanInfoWrapperKey(beanInfoKey, nameManager);
      ModelMBeanInfoWrapper result = getWrapperFromCache(key);
      if (result != null) {
         return result;
      } else {
         synchronized(mbeanInfos) {
            result = getWrapperFromCache(key);
            if (result != null) {
               return result;
            } else {
               result = new ModelMBeanInfoWrapper(beanInfoKey, key.getNameManager());
               mbeanInfos.put(result, new WeakReference(result));
               if (debug.isDebugEnabled()) {
                  debug.debug("ModelMBeanInfo cache count " + mbeanInfos.size());
               }

               return result;
            }
         }
      }
   }

   private static ModelMBeanInfoWrapper getWrapperFromCache(ModelMBeanInfoWrapperKey key) {
      WeakReference resultRef = (WeakReference)mbeanInfos.get(key);
      if (resultRef != null) {
         ModelMBeanInfoWrapper result = (ModelMBeanInfoWrapper)resultRef.get();
         if (result != null) {
            if (debug.isDebugEnabled()) {
               debug.debug("ModelMBeanInfo found in cache " + mbeanInfos.size());
            }

            return result;
         }
      }

      return null;
   }

   static {
      TimerListener tl = new TimerListener() {
         public void timerExpired(Timer timer) {
            ModelMBeanInfoWrapperManager.releaseInactiveMBeanInfos();
         }
      };

      try {
         TimerManagerFactory.getTimerManagerFactory().getDefaultTimerManager().scheduleAtFixedRate(tl, FiveMinutes, FiveMinutes);
         if (debug.isDebugEnabled()) {
            debug.debug("registered memory listeners for ModelBeanWrapperManager.");
         }

      } catch (RuntimeException var2) {
         throw var2;
      } catch (Exception var3) {
         throw new RuntimeException(var3);
      }
   }
}
