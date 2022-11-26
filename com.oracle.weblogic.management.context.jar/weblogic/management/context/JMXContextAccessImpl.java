package weblogic.management.context;

import java.io.IOException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.workarea.NoWorkContextException;
import weblogic.workarea.PrimitiveContextFactory;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.SerializableWorkContext;
import weblogic.workarea.WorkContext;
import weblogic.workarea.WorkContextHelper;
import weblogic.workarea.WorkContextMap;

public class JMXContextAccessImpl implements JMXContextHelper.JMXContextAccess {
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugJMXContext");
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   public JMXContext getJMXContext(boolean create) {
      JMXContext jmxContext = null;

      try {
         jmxContext = (JMXContext)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
               SerializableWorkContext workContext = (SerializableWorkContext)map.get("weblogic.management.JMXContext");
               return workContext != null ? workContext.get() : null;
            }
         });
      } catch (PrivilegedActionException var4) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("JMXContextAccessImpl.getJMXContext(): PrivilegedActionException: " + var4.getStackTrace());
         }
      }

      if (create && jmxContext == null) {
         jmxContext = new JMXContextImpl();
      }

      return (JMXContext)jmxContext;
   }

   public void putJMXContext(final JMXContext jmxContext) {
      try {
         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();

               try {
                  map.put("weblogic.management.JMXContext", PrimitiveContextFactory.create(jmxContext));
               } catch (PropertyReadOnlyException var3) {
                  if (JMXContextAccessImpl.DEBUG_LOGGER.isDebugEnabled()) {
                     JMXContextAccessImpl.DEBUG_LOGGER.debug("JMXContextAccessImpl.putJMXContext(): WorkContext property is read-only: " + var3.getStackTrace());
                  }
               } catch (IOException var4) {
                  if (JMXContextAccessImpl.DEBUG_LOGGER.isDebugEnabled()) {
                     JMXContextAccessImpl.DEBUG_LOGGER.debug("JMXContextAccessImpl.putJMXContext(): IOException: " + var4.getStackTrace());
                  }
               }

               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("JMXContextAccessImpl.putJMXContext(): PrivilegedActionException: " + var3.getStackTrace());
         }
      }

   }

   public void removeJMXContext() {
      try {
         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws Exception {
               try {
                  WorkContextMap map = WorkContextHelper.getWorkContextHelper().getWorkContextMap();
                  WorkContext workContext = map.get("weblogic.management.JMXContext");
                  if (workContext != null) {
                     map.remove("weblogic.management.JMXContext");
                  }
               } catch (NoWorkContextException var3) {
                  if (JMXContextAccessImpl.DEBUG_LOGGER.isDebugEnabled()) {
                     JMXContextAccessImpl.DEBUG_LOGGER.debug("JMXContextAccessImpl.removeJMXContext(): No WorkContext is available: " + var3.getMessage());
                  }
               } catch (PropertyReadOnlyException var4) {
                  if (JMXContextAccessImpl.DEBUG_LOGGER.isDebugEnabled()) {
                     JMXContextAccessImpl.DEBUG_LOGGER.debug("JMXContextAccessImpl.removeJMXContext(): WorkContext property is read-only: " + var4.getMessage());
                  }
               }

               return null;
            }
         });
      } catch (PrivilegedActionException var2) {
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("JMXContextAccessImpl.removeJMXContext(): PrivilegedActionException: " + var2.getStackTrace());
         }
      }

   }
}
