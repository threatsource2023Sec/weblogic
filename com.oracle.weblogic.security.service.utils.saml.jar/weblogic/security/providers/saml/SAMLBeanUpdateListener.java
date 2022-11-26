package weblogic.security.providers.saml;

import com.bea.common.logger.spi.LoggableMessageSpi;
import com.bea.common.logger.spi.LoggerSpi;
import com.bea.common.security.ProvidersLogger;
import com.bea.common.security.SecurityLogger;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.BeanUpdateFailedException;
import weblogic.descriptor.BeanUpdateListener;
import weblogic.descriptor.BeanUpdateRejectedException;
import weblogic.descriptor.DescriptorBean;
import weblogic.security.spi.ProviderInitializationException;

public final class SAMLBeanUpdateListener implements BeanUpdateListener {
   UpdateListener listener = null;
   String name = null;
   LoggerSpi log = null;

   private SAMLBeanUpdateListener(UpdateListener listener, String name, LoggerSpi log) {
      this.listener = listener;
      this.name = name;
      this.log = log;
      this.logDebug("constructor", "Listener for '" + this.name + "'");
   }

   public static BeanUpdateListener registerListener(DescriptorBean mbean, UpdateListener listener, String name, LoggerSpi log) throws ProviderInitializationException {
      if (mbean != null && listener != null && name != null) {
         try {
            SAMLBeanUpdateListener l = new SAMLBeanUpdateListener(listener, name, log);
            mbean.addBeanUpdateListener(l);
            l.logDebug("registerListener", "Registered listener for '" + name + "'");
            return l;
         } catch (Exception var6) {
            LoggableMessageSpi spi = SecurityLogger.logSAMLProviderUpdateListenerFailLoggable(name, var6);
            throw new ProviderInitializationException(spi.getFormattedMessageBody());
         }
      } else {
         throw new ProviderInitializationException(SecurityLogger.getNullParameterSupplied("registerListener()"));
      }
   }

   private void logDebug(String method, String msg) {
      if (this.log != null && this.log.isDebugEnabled()) {
         this.log.debug("SAMLBeanUpdateListener[" + this.name + "]." + method + "(): " + msg);
      }

   }

   public void prepareUpdate(BeanUpdateEvent event) throws BeanUpdateRejectedException {
      this.logDebug("prepareUpdate", "Calling prepareBeanUpdate");

      try {
         this.listener.prepareBeanUpdate(event);
      } catch (Exception var4) {
         LoggableMessageSpi spi = ProvidersLogger.logSAMLProviderListenerErrorLoggable(this.name, "prepareUpdate()", var4.getMessage());
         throw new BeanUpdateRejectedException(spi.getFormattedMessageBody());
      }
   }

   public void activateUpdate(BeanUpdateEvent event) throws BeanUpdateFailedException {
      this.logDebug("activateUpdate", "Calling handleBeanUpdate");

      try {
         this.listener.handleBeanUpdate(event);
      } catch (Exception var4) {
         LoggableMessageSpi spi = ProvidersLogger.logSAMLProviderListenerErrorLoggable(this.name, "activateUpdate()", var4.getMessage());
         throw new BeanUpdateFailedException(spi.getFormattedMessageBody());
      }
   }

   public void rollbackUpdate(BeanUpdateEvent event) {
      this.logDebug("rollbackUpdate", "Calling rollbackBeanUpdate");

      try {
         this.listener.rollbackBeanUpdate(event);
      } catch (Exception var3) {
         this.logDebug("rollbackUpdate", "SAMLBeanUpdateListener: " + this.name + ".rollBackUpdate() failed with exception: " + var3.toString());
      }

   }

   public interface UpdateListener {
      void prepareBeanUpdate(BeanUpdateEvent var1) throws BeanUpdateRejectedException;

      void handleBeanUpdate(BeanUpdateEvent var1) throws BeanUpdateFailedException;

      void rollbackBeanUpdate(BeanUpdateEvent var1);
   }
}
