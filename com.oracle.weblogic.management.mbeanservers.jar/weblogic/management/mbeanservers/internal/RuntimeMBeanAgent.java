package weblogic.management.mbeanservers.internal;

import java.security.AccessController;
import java.security.PrivilegedAction;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.OperationsException;
import weblogic.descriptor.DescriptorBean;
import weblogic.management.jmx.JMXLogger;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;
import weblogic.management.jmx.modelmbean.WLSModelMBeanContext;
import weblogic.management.jmx.modelmbean.WLSModelMBeanFactory;
import weblogic.management.provider.RegistrationHandler;
import weblogic.management.provider.RegistrationManager;
import weblogic.management.provider.Service;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public class RuntimeMBeanAgent {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   final WLSModelMBeanContext context;
   final RegistrationManager access;

   public RuntimeMBeanAgent(WLSModelMBeanContext context, RegistrationManager access) {
      this.context = context;
      this.access = access;
      access.initiateRegistrationHandler(this.createRegistrationHandler());
   }

   private RegistrationHandler createRegistrationHandler() {
      return new RegistrationHandler() {
         public void registeredCustom(ObjectName oname, Object custom) {
            try {
               if (RuntimeMBeanAgent.this.context.getNameManager().isClassMapped(custom.getClass())) {
                  RuntimeMBeanAgent.this.context.getNameManager().registerObject(oname, custom);
                  WLSModelMBeanFactory.registerWLSModelMBean(custom, oname, RuntimeMBeanAgent.this.context);
               } else {
                  WLSMBeanServer wlsMBeanServer = (WLSMBeanServer)RuntimeMBeanAgent.this.context.getMBeanServer();
                  wlsMBeanServer.getMBeanServer().registerMBean(custom, oname);
               }
            } catch (OperationsException var4) {
               JMXLogger.logRegistrationFailed(oname, var4);
            } catch (MBeanRegistrationException var5) {
               JMXLogger.logRegistrationFailed(oname, var5);
            }

         }

         public void unregisteredCustom(final ObjectName oname) {
            SecurityServiceManager.runAs(RuntimeMBeanAgent.KERNEL_ID, RuntimeMBeanAgent.KERNEL_ID, new PrivilegedAction() {
               public Object run() {
                  Object custom = RuntimeMBeanAgent.this.context.getNameManager().lookupObject(oname);
                  if (custom != null) {
                     RuntimeMBeanAgent.this.context.unregister(custom);
                  } else {
                     try {
                        WLSMBeanServer wlsMBeanServer = (WLSMBeanServer)RuntimeMBeanAgent.this.context.getMBeanServer();
                        wlsMBeanServer.getMBeanServer().unregisterMBean(oname);
                     } catch (InstanceNotFoundException var3) {
                        JMXLogger.logUnregisterFailed(oname, var3);
                     } catch (MBeanRegistrationException var4) {
                        JMXLogger.logUnregisterFailed(oname, var4);
                     }
                  }

                  return null;
               }
            });
         }

         public void registered(RuntimeMBean runtime, DescriptorBean config) {
            WLSModelMBeanFactory.registerWLSModelMBean(runtime, RuntimeMBeanAgent.this.context);
         }

         public void unregistered(RuntimeMBean runtime) {
            this.unregisteredInternal(runtime);
         }

         public void registered(Service bean) {
            WLSModelMBeanFactory.registerWLSModelMBean(bean, RuntimeMBeanAgent.this.context);
         }

         public void unregistered(Service bean) {
            this.unregisteredInternal(bean);
         }

         private void unregisteredInternal(Object bean) {
            if (RuntimeMBeanAgent.this.context.getNameManager().isClassMapped(bean.getClass())) {
               ObjectName oname = RuntimeMBeanAgent.this.context.getNameManager().unregisterObjectInstance(bean);
               if (oname == null) {
                  JMXLogger.logBeanUnregisterFailed(bean.toString());
               } else {
                  try {
                     MBeanServer mbeanServer = RuntimeMBeanAgent.this.context.getMBeanServer();
                     if (mbeanServer instanceof WLSMBeanServer) {
                        ((WLSMBeanServer)mbeanServer).internalUnregisterMBean(oname);
                     } else {
                        mbeanServer.unregisterMBean(oname);
                     }
                  } catch (InstanceNotFoundException var4) {
                     JMXLogger.logUnregisterFailed(oname, var4);
                  } catch (MBeanRegistrationException var5) {
                     JMXLogger.logUnregisterFailed(oname, var5);
                  }

               }
            }
         }
      };
   }
}
