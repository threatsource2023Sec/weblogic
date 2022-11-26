package weblogic.jms.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import weblogic.security.subject.AbstractSubject;
import weblogic.security.subject.SubjectManager;

public final class CrossDomainSecurityManager {
   private static final String THICK_SUBJECT_MANAGER = "weblogic.security.service.SubjectManagerImpl";
   private static final String IIOPCLIENT_SUBJECT_MANAGER = "weblogic.corba.client.security.SubjectManagerImpl";
   private static final String T3CLIENT_SUBJECT_MANAGER = "weblogic.security.subject.SubjectManager";
   private static boolean initialized;
   private static final AbstractSubject kernelID = getKernelIdentity();
   public static CrossDomainSecurityUtil util = new ClientCrossDomainSecurityUtil();

   private static final AbstractSubject getKernelIdentity() {
      try {
         ensureSubjectManagerInitialized();
         return (AbstractSubject)AccessController.doPrivileged(SubjectManager.getKernelIdentityAction());
      } catch (AccessControlException var1) {
         return null;
      }
   }

   public static CrossDomainSecurityUtil getCrossDomainSecurityUtil() {
      return util;
   }

   public static void setCrossDomainSecurityUtil(CrossDomainSecurityUtil cdsutil) {
      util = cdsutil;
   }

   public static void ensureSubjectManagerInitialized() {
      if (!initialized) {
         Class subjectManagerImpl;
         try {
            subjectManagerImpl = Class.forName("weblogic.security.service.SubjectManagerImpl");
         } catch (ClassNotFoundException var9) {
            try {
               subjectManagerImpl = Class.forName("weblogic.corba.client.security.SubjectManagerImpl");
            } catch (ClassNotFoundException var8) {
               try {
                  subjectManagerImpl = Class.forName("weblogic.security.subject.SubjectManager");
               } catch (ClassNotFoundException var7) {
                  if (SubjectManager.getSubjectManager() == null) {
                     throw new AssertionError(var7);
                  }

                  subjectManagerImpl = SubjectManager.getSubjectManager().getClass();
               }
            }
         }

         Method ensureInitializedMethod;
         try {
            ensureInitializedMethod = subjectManagerImpl.getMethod("ensureInitialized");
         } catch (NoSuchMethodException var6) {
            throw new AssertionError(var6);
         }

         try {
            ensureInitializedMethod.invoke((Object)null);
         } catch (IllegalAccessException var4) {
            throw new AssertionError(var4);
         } catch (InvocationTargetException var5) {
            throw new AssertionError(var5);
         }

         initialized = true;
      }
   }

   public static final AbstractSubject getCurrentSubject() {
      return SubjectManager.getSubjectManager().getCurrentSubject(kernelID);
   }

   public static final void doAs(AbstractSubject subject, PrivilegedExceptionAction action) throws javax.jms.JMSException {
      try {
         subject.doAs(kernelID, action);
      } catch (PrivilegedActionException var4) {
         Exception e = var4.getException();
         if (e instanceof javax.jms.JMSException) {
            throw (javax.jms.JMSException)e;
         }
      }

   }

   public static final void doAsWithWrappedException(AbstractSubject subject, PrivilegedExceptionAction action) throws PrivilegedActionException {
      subject.doAs(kernelID, action);
   }

   public static final Object runAs(AbstractSubject subject, PrivilegedExceptionAction action) throws PrivilegedActionException {
      return subject.doAs(kernelID, action);
   }

   public static final Object runAs(AbstractSubject subject, PrivilegedAction action) {
      return subject.doAs(kernelID, action);
   }
}
