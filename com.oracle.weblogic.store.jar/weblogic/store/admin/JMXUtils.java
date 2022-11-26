package weblogic.store.admin;

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.management.ManagementException;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;
import weblogic.store.PersistentStoreConnection;
import weblogic.store.PersistentStoreException;
import weblogic.store.internal.PersistentStoreImpl;

public class JMXUtils {
   private static final AuthenticatedSubject KERNEL_ID = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());

   static PersistentStoreRuntimeMBeanImpl createStoreMBean(final PersistentStoreImpl store) throws PersistentStoreException {
      try {
         return (PersistentStoreRuntimeMBeanImpl)SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws ManagementException {
               return new PersistentStoreRuntimeMBeanImpl(store);
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception exc = var3.getException();
         if (exc instanceof ManagementException) {
            throw new PersistentStoreException(exc);
         } else if (exc instanceof RuntimeException) {
            throw (RuntimeException)exc;
         } else {
            throw new AssertionError(exc);
         }
      }
   }

   static void unregisterStoreMBean(final PersistentStoreRuntimeMBeanImpl mbean) throws PersistentStoreException {
      try {
         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws ManagementException {
               mbean.unregister();
               return null;
            }
         });
      } catch (PrivilegedActionException var3) {
         Exception exc = var3.getException();
         if (exc instanceof ManagementException) {
            throw new PersistentStoreException(exc);
         } else if (exc instanceof RuntimeException) {
            throw (RuntimeException)exc;
         } else {
            throw new AssertionError(exc);
         }
      }
   }

   static void registerConnectionMBean(final PersistentStoreRuntimeMBeanImpl mbean, final PersistentStoreConnection conn) throws PersistentStoreException {
      try {
         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws ManagementException {
               mbean.addConnection(new PersistentStoreConnectionRuntimeMBeanImpl(conn, mbean));
               return null;
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception exc = var4.getException();
         if (exc instanceof ManagementException) {
            throw new PersistentStoreException(exc);
         } else if (exc instanceof RuntimeException) {
            throw (RuntimeException)exc;
         } else {
            throw new AssertionError(exc);
         }
      }
   }

   static void unregisterConnectionMBean(final PersistentStoreRuntimeMBeanImpl mbean, final PersistentStoreConnection conn) throws PersistentStoreException {
      try {
         SecurityServiceManager.runAs(KERNEL_ID, KERNEL_ID, new PrivilegedExceptionAction() {
            public Object run() throws ManagementException {
               mbean.removeConnection(conn);
               return null;
            }
         });
      } catch (PrivilegedActionException var4) {
         Exception exc = var4.getException();
         if (exc instanceof ManagementException) {
            throw new PersistentStoreException(exc);
         } else if (exc instanceof RuntimeException) {
            throw (RuntimeException)exc;
         } else {
            throw new AssertionError(exc);
         }
      }
   }
}
