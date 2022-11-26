package weblogic.management.jmx.modelmbean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.management.AttributeChangeNotification;
import javax.management.Descriptor;
import javax.management.MBeanException;
import javax.management.OperationsException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.openmbean.OpenType;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.management.jmx.JMXLogger;

public class PropertyChangeNotificationTranslator {
   protected static final DebugLogger debug = DebugLogger.getDebugLogger("DebugJMXCore");
   private static final Class[] CHANGELISTENER_PARAMS = new Class[]{PropertyChangeListener.class};
   protected final NotificationGenerator generator;
   protected final WLSModelMBeanContext context;
   protected final ModelMBeanInfoWrapper modelMBeanInfoWrapper;
   private static final String ENCRYPTION_MASK = "**********";

   public PropertyChangeNotificationTranslator(WLSModelMBeanContext context, Object managedResource, NotificationGenerator emitter) {
      this.generator = emitter;
      this.context = context;
      if (emitter instanceof WLSModelMBean) {
         WLSModelMBean modelMBean = (WLSModelMBean)emitter;
         this.modelMBeanInfoWrapper = modelMBean.getModelMBeanInfoWrapper();
      } else {
         try {
            this.modelMBeanInfoWrapper = ModelMBeanInfoWrapperManager.getModelMBeanInfoForInstance(managedResource, context.isReadOnly(), context.getVersion(), context.getNameManager());
         } catch (OperationsException var8) {
            throw new AssertionError(var8);
         }
      }

      try {
         Method addChangeListener = managedResource.getClass().getMethod("addPropertyChangeListener", CHANGELISTENER_PARAMS);
         addChangeListener.invoke(managedResource, this.createPropertyChangeListener());
      } catch (NoSuchMethodException var5) {
      } catch (IllegalAccessException var6) {
         throw new Error(var6);
      } catch (InvocationTargetException var7) {
         throw new Error(var7.getTargetException());
      }

   }

   private PropertyChangeListener createPropertyChangeListener() {
      return new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent evt) {
            if (PropertyChangeNotificationTranslator.this.generator.isSubscribed()) {
               Object oldValue = evt.getOldValue();
               Object newValue = evt.getNewValue();
               String propertyName = evt.getPropertyName();
               OpenType openType = null;
               Class propertyClass = null;
               boolean isEncrypted = false;

               try {
                  ModelMBeanAttributeInfo attrInfo = PropertyChangeNotificationTranslator.this.modelMBeanInfoWrapper.getModelMBeanInfo().getAttribute(propertyName);
                  if (attrInfo == null) {
                     if (PropertyChangeNotificationTranslator.debug.isDebugEnabled()) {
                        PropertyChangeNotificationTranslator.debug.debug("WLSModelMBean: Unable to process property change event for the property: " + propertyName);
                     }

                     return;
                  }

                  Descriptor fieldDescriptor = attrInfo.getDescriptor();
                  if (fieldDescriptor != null) {
                     openType = (OpenType)fieldDescriptor.getFieldValue("openType");
                  }

                  Object encryptedValue = fieldDescriptor.getFieldValue("com.bea.encrypted");
                  if (encryptedValue != null) {
                     isEncrypted = (Boolean)encryptedValue;
                  }

                  propertyClass = Class.forName(attrInfo.getType());
               } catch (MBeanException var11) {
                  JMXLogger.logErrorGeneratingAttributeChangeNotification(PropertyChangeNotificationTranslator.this.generator.getObjectName().toString(), propertyName);
                  return;
               } catch (ClassNotFoundException var12) {
                  JMXLogger.logErrorGeneratingAttributeChangeNotification(PropertyChangeNotificationTranslator.this.generator.getObjectName().toString(), propertyName);
                  return;
               }

               Object jmxOldValue;
               Object jmxNewValue;
               if (isEncrypted) {
                  jmxOldValue = "**********";
                  jmxNewValue = "**********";
               } else {
                  if (PropertyChangeNotificationTranslator.this.context.getSecurityManager() != null && !PropertyChangeNotificationTranslator.this.context.getSecurityManager().isAnonAccessAllowed(PropertyChangeNotificationTranslator.this.generator.getObjectName(), propertyName, "handleNotification")) {
                     return;
                  }

                  jmxOldValue = PropertyChangeNotificationTranslator.this.context.mapToJMX(propertyClass, oldValue, openType);
                  jmxNewValue = PropertyChangeNotificationTranslator.this.context.mapToJMX(propertyClass, newValue, openType);
               }

               PropertyChangeNotificationTranslator.this.generateNotification(propertyName, propertyClass, jmxOldValue, jmxNewValue);
            }
         }
      };
   }

   protected void generateNotification(String propertyName, Class propertyClass, Object jmxOldValue, Object jmxNewValue) {
      AttributeChangeNotification notification = new AttributeChangeNotification(this.generator.getObjectName(), this.generator.incrementSequenceNumber(), System.currentTimeMillis(), "update", propertyName, propertyClass.getName(), jmxOldValue, jmxNewValue);

      try {
         this.generator.sendNotification(notification);
      } catch (MBeanException var7) {
         JMXLogger.logErrorGeneratingAttributeChangeNotification(this.generator.getObjectName().toString(), propertyName);
      }

   }
}
