package weblogic.messaging.common;

import java.io.StreamCorruptedException;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import javax.naming.Context;
import javax.naming.NamingException;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.jndi.Aggregatable;
import weblogic.jndi.WLContext;
import weblogic.management.ManagementException;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.messaging.MessagingLogger;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.SecurityServiceManager;

public class PrivilegedActionUtilities {
   private static final DebugLogger JNDILogger = DebugLogger.getDebugLogger("DebugJMSJNDI");

   public static void register(RuntimeMBeanDelegate mbean, AuthenticatedSubject kernelId) throws ManagementException {
      final RuntimeMBeanDelegate fmbean = mbean;

      try {
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws ManagementException {
               fmbean.register();
               return null;
            }
         });
      } catch (PrivilegedActionException var4) {
         throw (ManagementException)var4.getException();
      }
   }

   public static void unregister(RuntimeMBeanDelegate mbean, AuthenticatedSubject kernelId) throws ManagementException {
      final RuntimeMBeanDelegate fmbean = mbean;

      try {
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws ManagementException {
               fmbean.unregister();
               return null;
            }
         });
      } catch (PrivilegedActionException var4) {
         throw (ManagementException)var4.getException();
      }
   }

   public static void bindAsSU(Context ctx, String jndiName, Object jndiVal, AuthenticatedSubject kernelId) throws NamingException {
      final Context fctx = ctx;
      final String fjndiName = jndiName;
      final Object fjndiVal = jndiVal;

      try {
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               if (PrivilegedActionUtilities.JNDILogger.isDebugEnabled()) {
                  PrivilegedActionUtilities.JNDILogger.debug("Binding " + fjndiVal.toString() + " to " + fjndiName);
               }

               fctx.bind(fjndiName, fjndiVal);
               return null;
            }
         });
      } catch (PrivilegedActionException var9) {
         Throwable throwMe = var9.getCause();
         if (throwMe instanceof NamingException) {
            throw (NamingException)throwMe;
         } else {
            throw new NamingException(var9.toString());
         }
      }
   }

   public static void unbindAsSU(Context ctx, String jndiName, AuthenticatedSubject kernelId) throws NamingException {
      final Context fctx = ctx;
      final String fjndiName = jndiName;

      try {
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               if (PrivilegedActionUtilities.JNDILogger.isDebugEnabled()) {
                  PrivilegedActionUtilities.JNDILogger.debug("Unbinding " + fjndiName);
               }

               fctx.unbind(fjndiName);
               return null;
            }
         });
      } catch (PrivilegedActionException var7) {
         Throwable throwMe = var7.getCause();
         if (throwMe instanceof NamingException) {
            throw (NamingException)throwMe;
         } else {
            throw new NamingException(var7.toString());
         }
      }
   }

   public static void unbindAsSU(Context ctx, String jndiName, Object jndiVal, AuthenticatedSubject kernelId) throws NamingException {
      final Context fctx = ctx;
      final String fjndiName = jndiName;
      final Object fjndiVal = jndiVal;

      try {
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               if (PrivilegedActionUtilities.JNDILogger.isDebugEnabled()) {
                  PrivilegedActionUtilities.JNDILogger.debug("Unbinding " + fjndiVal.toString() + " from " + fjndiName);
               }

               ((WLContext)fctx).unbind(fjndiName, fjndiVal);
               return null;
            }
         });
      } catch (PrivilegedActionException var8) {
         throw new NamingException(var8.toString());
      }
   }

   public static void rebindAsSU(Context ctx, String jndiName, Object jndiVal, AuthenticatedSubject kernelId) throws NamingException {
      final Context fctx = ctx;
      final String fjndiName = jndiName;
      final Object fjndiVal = jndiVal;

      try {
         SecurityServiceManager.runAs(kernelId, kernelId, new PrivilegedExceptionAction() {
            public Object run() throws NamingException {
               if (fjndiVal instanceof Aggregatable) {
                  if (PrivilegedActionUtilities.JNDILogger.isDebugEnabled()) {
                     PrivilegedActionUtilities.JNDILogger.debug("Rebinding aggregatable " + fjndiVal.toString() + " from " + fjndiName);
                  }

                  ((WLContext)fctx).rebind(fjndiName, fjndiVal, fjndiVal);
               } else {
                  if (PrivilegedActionUtilities.JNDILogger.isDebugEnabled()) {
                     PrivilegedActionUtilities.JNDILogger.debug("Rebinding " + fjndiVal.toString() + " to " + fjndiName);
                  }

                  fctx.rebind(fjndiName, fjndiVal);
               }

               return null;
            }
         });
      } catch (PrivilegedActionException var8) {
         throw new NamingException(var8.toString());
      }
   }

   public static StreamCorruptedException versionIOException(int version, int minExpectedVersion, int maxExpectedVersion) {
      return new StreamCorruptedException(MessagingLogger.logUnsupportedClassVersionLoggable(version, minExpectedVersion, maxExpectedVersion).getMessage());
   }

   public static final int calcObjectSize(Object obj) {
      if (obj == null) {
         return 2;
      } else if (obj instanceof Integer) {
         return 6;
      } else if (obj instanceof String) {
         return 4 + (((String)obj).length() << 2);
      } else if (obj instanceof Long) {
         return 10;
      } else if (obj instanceof Boolean) {
         return 3;
      } else if (obj instanceof Byte) {
         return 3;
      } else if (obj instanceof Short) {
         return 4;
      } else if (obj instanceof Float) {
         return 6;
      } else if (obj instanceof Double) {
         return 10;
      } else {
         return obj instanceof byte[] ? ((byte[])((byte[])obj)).length + 6 : 0;
      }
   }
}
