package weblogic.jms.saf;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import weblogic.application.ModuleException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.ManagementException;
import weblogic.management.utils.BeanListenerCustomizer;
import weblogic.management.utils.GenericBeanListener;
import weblogic.messaging.saf.utils.Util;
import weblogic.work.WorkManager;

public final class JMSSAFManager {
   static final String JMSSAF_WM_NAME = "weblogic.jms.saf.General";
   public static final JMSSAFManager manager = new JMSSAFManager();
   private Map remoteSAFContexts = Collections.synchronizedMap(new HashMap());
   private Map errorHandlers = Collections.synchronizedMap(new HashMap());
   private WorkManager workManager;

   private void initializeWorkManagers() {
      this.workManager = Util.findOrCreateWorkManager("weblogic.jms.saf.General", -1, 0, -1);
   }

   public void addErrorHandler(String fullyQualifiedName, ErrorHandler eh) {
      this.errorHandlers.put(fullyQualifiedName, eh);
   }

   public ErrorHandler getErrorHandler(String errorHandlingName) {
      return (ErrorHandler)this.errorHandlers.get(errorHandlingName);
   }

   public void removeErrorHandler(String errorHandlingName) {
      this.errorHandlers.remove(errorHandlingName);
   }

   public void addRemoteSAFContext(String remoteContextName, RemoteContext remoteContext) {
      this.remoteSAFContexts.put(remoteContextName, remoteContext);
   }

   public RemoteContext getRemoteSAFContext(String remoteContextName) {
      return (RemoteContext)this.remoteSAFContexts.get(remoteContextName);
   }

   public void removeRemoteSAFContext(String remoteContextName) {
      this.remoteSAFContexts.remove(remoteContextName);
   }

   WorkManager getWorkManager() {
      return this.workManager;
   }

   public static GenericBeanListener initializeGenericBeanListener(DescriptorBean bean, Object listener, BeanListenerCustomizer customizer, HashMap signatures, HashMap addSignatures) throws ModuleException {
      GenericBeanListener beanListener = null;
      if (addSignatures == null) {
         beanListener = new GenericBeanListener(bean, listener, signatures);
      } else {
         beanListener = new GenericBeanListener(bean, listener, signatures, addSignatures);
      }

      if (customizer != null) {
         beanListener.setCustomizer(customizer);
      }

      try {
         beanListener.initialize();
         return beanListener;
      } catch (ManagementException var7) {
         throw new ModuleException(var7.getMessage(), var7);
      }
   }

   static {
      manager.initializeWorkManagers();
   }
}
