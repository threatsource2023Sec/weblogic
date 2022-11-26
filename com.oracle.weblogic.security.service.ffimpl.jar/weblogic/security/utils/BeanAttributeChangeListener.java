package weblogic.security.utils;

import java.util.concurrent.ConcurrentHashMap;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.management.configuration.ConfigurationMBean;
import weblogic.security.shared.LoggerWrapper;

public final class BeanAttributeChangeListener {
   private final ConcurrentHashMap registered = new ConcurrentHashMap();
   private final AttributeChangeHandler[] handlers;
   private final LoggerWrapper debugLogger;
   private final Class mbeanClass;
   private final BeanUpdateListenerImpl listenerImpl = new BeanUpdateListenerImpl();

   public BeanAttributeChangeListener(Class mbeanClass, AttributeChangeHandler[] handlers, LoggerWrapper debugLogger) {
      if (null == debugLogger) {
         throw new IllegalArgumentException("Null debug logger.");
      } else {
         this.debugLogger = debugLogger;

         try {
            if (null == mbeanClass) {
               throw new IllegalArgumentException("Null mbeanClass.");
            } else if (!ConfigurationMBean.class.isAssignableFrom(mbeanClass)) {
               throw new IllegalArgumentException("mbeanClass must be a ConfigurationMBean or subclass.");
            } else {
               this.mbeanClass = mbeanClass;
               if (null != handlers && 0 != handlers.length) {
                  this.handlers = new AttributeChangeHandler[handlers.length];
                  System.arraycopy(handlers, 0, this.handlers, 0, handlers.length);
                  AttributeChangeHandler[] var4 = handlers;
                  int var5 = handlers.length;

                  for(int var6 = 0; var6 < var5; ++var6) {
                     AttributeChangeHandler h = var4[var6];
                     if (null == h) {
                        throw new IllegalArgumentException("Null AttributeChangeHandler array element.");
                     }
                  }

               } else {
                  throw new IllegalArgumentException("Null or empty AttributeChangeHandler array.");
               }
            }
         } catch (Exception var8) {
            if (this.debugLogger.isDebugEnabled()) {
               debugLogger.debug("Unable to construct BeanAttributeChangeListener: exception=" + var8.getClass().getName() + ", message=" + var8.getMessage(), var8);
            }

            throw var8;
         }
      }
   }

   public void addListenerIfAbsent(ConfigurationMBean configurationMBean) {
      if (!this.mbeanClass.isInstance(configurationMBean)) {
         String msg = "Unable to add listener to " + configurationMBean + " because not instance of " + this.mbeanClass.getName();
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug(msg);
         }

         throw new IllegalArgumentException(msg);
      } else {
         if (null == this.registered.putIfAbsent(configurationMBean, configurationMBean)) {
            configurationMBean.addBeanUpdateListener(this.listenerImpl);
            if (this.debugLogger.isDebugEnabled()) {
               this.debugLogger.debug("Added change listener to " + configurationMBean);
            }
         }

      }
   }

   public void removeListenerIfPresent(ConfigurationMBean configurationMBean) {
      if (null != this.registered.remove(configurationMBean)) {
         configurationMBean.removeBeanUpdateListener(this.listenerImpl);
         if (this.debugLogger.isDebugEnabled()) {
            this.debugLogger.debug("Removed change listener from " + configurationMBean);
         }
      }

   }

   private final class BeanUpdateListenerImpl implements BeanUpdateListener {
      private BeanUpdateListenerImpl() {
      }

      public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      }

      public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
         if (null == event) {
            throw new IllegalArgumentException("Null BeanUpdateEvent.");
         } else {
            BeanUpdateEvent.PropertyUpdate[] propertyUpdates = event.getUpdateList();
            if (null == propertyUpdates) {
               throw new IllegalArgumentException("Null PropertyUpdates.");
            } else {
               for(int i = 0; i < propertyUpdates.length; ++i) {
                  AttributeChangeHandler[] var4 = BeanAttributeChangeListener.this.handlers;
                  int var5 = var4.length;

                  for(int var6 = 0; var6 < var5; ++var6) {
                     AttributeChangeHandler h = var4[var6];
                     if (h.getAttributeName().equals(propertyUpdates[i].getPropertyName())) {
                        try {
                           BeanUpdateEvent.PropertyUpdate propertyUpdate = propertyUpdates[i];
                           if (null == propertyUpdate) {
                              throw new IllegalArgumentException("Null PropertyUpdate " + i);
                           }

                           h.update(event, propertyUpdate);
                           break;
                        } catch (Exception var10) {
                           String msg = "Unable to process BeanUpdateEvent: attribute=" + h.getAttributeName() + ", exception=" + var10.getClass().getName() + ", message=" + var10.getMessage();
                           if (BeanAttributeChangeListener.this.debugLogger.isDebugEnabled()) {
                              BeanAttributeChangeListener.this.debugLogger.debug(msg, var10);
                           }

                           throw new BeanUpdateFailedException(msg, var10);
                        }
                     }
                  }
               }

               if (BeanAttributeChangeListener.this.debugLogger.isDebugEnabled()) {
                  BeanAttributeChangeListener.this.debugLogger.debug("Processed bean update event: " + event);
               }

            }
         }
      }

      public void rollbackUpdate(BeanUpdateEvent event) {
      }

      // $FF: synthetic method
      BeanUpdateListenerImpl(Object x1) {
         this();
      }
   }

   public abstract static class AttributeChangeHandler {
      private final String attributeName;

      public AttributeChangeHandler(String attributeName) {
         if (null != attributeName && attributeName.length() != 0) {
            this.attributeName = attributeName;
         } else {
            throw new IllegalArgumentException("Null or empty attributeName.");
         }
      }

      public final String getAttributeName() {
         return this.attributeName;
      }

      public abstract void update(BeanUpdateEvent var1, BeanUpdateEvent.PropertyUpdate var2) throws Exception;
   }
}
