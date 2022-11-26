package weblogic.ejb.container.internal;

import java.lang.reflect.Method;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import javax.ejb.EJBException;
import javax.ejb.NoSuchEJBException;
import javax.ejb.NoSuchEntityException;
import javax.ejb.NoSuchObjectLocalException;
import javax.ejb.TransactionRolledbackLocalException;
import javax.jms.JMSException;
import javax.naming.Context;
import javax.persistence.OptimisticLockException;
import javax.transaction.RollbackException;
import javax.transaction.TransactionRolledbackException;
import weblogic.ejb.OptimisticConcurrencyException;
import weblogic.ejb.container.InternalException;
import weblogic.ejb20.persistence.spi.PersistenceRuntimeException;
import weblogic.health.HealthMonitorService;
import weblogic.jndi.factories.java.javaURLContextFactory;
import weblogic.utils.NestedRuntimeException;
import weblogic.utils.StackTraceUtilsClient;

public final class EJBRuntimeUtils {
   static void throwException(Throwable t) throws Exception {
      if (t instanceof Exception) {
         throw (Exception)t;
      } else {
         throw (Error)t;
      }
   }

   static RemoteException asRemoteException(String msg, Throwable th) {
      if (th instanceof OutOfMemoryError) {
         HealthMonitorService.panic(th);
      }

      if (th instanceof InternalException) {
         InternalException ie = (InternalException)th;
         if (ie.detail == null) {
            return new RemoteException(ie.getMessage() + ": " + StackTraceUtilsClient.throwable2StackTrace(ie));
         }

         th = ie.detail;
         msg = ie.getMessage();
      }

      if (th instanceof RollbackException) {
         return asTxRollbackException(msg, th);
      } else if (th instanceof NoSuchEntityException) {
         return new NoSuchObjectException(msg + ": " + StackTraceUtilsClient.throwable2StackTrace(th));
      } else {
         return th instanceof RemoteException ? (RemoteException)th : new RemoteException(msg, th);
      }
   }

   public static EJBException asEJBException(String msg, Throwable th) {
      if (th instanceof InternalException) {
         InternalException ie = (InternalException)th;
         if (ie.detail == null) {
            return new EJBException(ie.getMessage() + ": " + StackTraceUtilsClient.throwable2StackTrace(ie));
         }

         th = ie.detail;
         msg = ie.getMessage();
      }

      if (th instanceof RollbackException) {
         return asTxRollbackLocalException(msg, th);
      } else if (th instanceof NoSuchEntityException) {
         return new NoSuchObjectLocalException(th.getMessage() + ": " + StackTraceUtilsClient.throwable2StackTrace(th), (Exception)th);
      } else if (th instanceof EJBException) {
         return (EJBException)th;
      } else if (th instanceof NoSuchObjectException) {
         return new NoSuchObjectLocalException(th.getMessage() + ": " + StackTraceUtilsClient.throwable2StackTrace(th));
      } else {
         return th instanceof Exception ? new EJBException(msg + ": " + StackTraceUtilsClient.throwable2StackTrace(th), (Exception)th) : new EJBException(msg + ": " + StackTraceUtilsClient.throwable2StackTrace(th));
      }
   }

   public static JMSException asJMSException(String msg, Throwable th) {
      if (th instanceof JMSException) {
         return (JMSException)th;
      } else {
         JMSException jmse = new JMSException(th.toString());
         if (th instanceof Exception) {
            jmse.setLinkedException((Exception)th);
         } else {
            jmse.setLinkedException(new Exception(msg + ": " + StackTraceUtilsClient.throwable2StackTrace(th)));
         }

         return jmse;
      }
   }

   public static void throwInternalException(String msg, Throwable th) throws InternalException {
      if (th instanceof InternalException) {
         throw (InternalException)th;
      } else if (th instanceof PersistenceRuntimeException) {
         PersistenceRuntimeException pre = (PersistenceRuntimeException)th;
         if (pre.getCausedByException() instanceof InternalException) {
            throw (InternalException)pre.getCausedByException();
         } else {
            throw new InternalException(msg, pre.getCausedByException());
         }
      } else {
         throw new InternalException(msg, th);
      }
   }

   public static TransactionRolledbackException asTxRollbackException(String msg, Throwable th) {
      if (th instanceof InternalException) {
         InternalException ie = (InternalException)th;
         if (ie.detail == null) {
            return new TransactionRolledbackException(ie.getMessage() + ": " + StackTraceUtilsClient.throwable2StackTrace(ie));
         }

         th = ie.detail;
         msg = ie.getMessage();
      }

      TransactionRolledbackException tre = new TransactionRolledbackException(msg);
      tre.detail = unwrapRollbackException(th);
      return tre;
   }

   public static TransactionRolledbackLocalException asTxRollbackLocalException(String msg, Throwable th) {
      if (th instanceof InternalException) {
         InternalException ie = (InternalException)th;
         if (ie.detail == null) {
            return new TransactionRolledbackLocalException(ie.getMessage() + ": " + StackTraceUtilsClient.throwable2StackTrace(th));
         }

         th = ie.detail;
         msg = ie.getMessage();
      }

      if (th instanceof Exception) {
         return new TransactionRolledbackLocalException(msg, (Exception)unwrapRollbackException(th));
      } else {
         TransactionRolledbackLocalException tre = new TransactionRolledbackLocalException(msg);
         tre.initCause(unwrapRollbackException(th));
         return tre;
      }
   }

   private static Throwable unwrapRollbackException(Throwable t) {
      if (t instanceof weblogic.transaction.RollbackException) {
         weblogic.transaction.RollbackException rbe = (weblogic.transaction.RollbackException)t;
         if (rbe.getNested() != null) {
            for(t = rbe.getNested(); t instanceof NestedRuntimeException && t.getCause() != null; t = ((NestedRuntimeException)t).getNestedException()) {
            }
         }
      }

      return t;
   }

   static boolean isSpecialSystemException(Throwable th) {
      if (th instanceof InternalException) {
         InternalException ie = (InternalException)th;
         if (ie.detail != null) {
            th = ie.detail;
         }
      }

      return th instanceof NoSuchObjectException || th instanceof NoSuchEntityException || th instanceof NoSuchObjectLocalException || th instanceof NoSuchEJBException;
   }

   static EJBException wrapCauseInEJBException(RemoteException re) {
      Throwable cause = re.getCause();
      if (cause == null) {
         return new EJBException(re.getMessage(), re);
      } else if (cause instanceof EJBException) {
         return (EJBException)cause;
      } else {
         EJBException ejbEx = new EJBException();
         ejbEx.initCause(cause);
         return ejbEx;
      }
   }

   public static boolean isAppException(Method m, Throwable th) {
      return isAppException(m, (Class[])null, th);
   }

   public static boolean isAppException(Method m, Class[] extraExceptions, Throwable th) {
      if (th instanceof InternalException) {
         InternalException ie = (InternalException)th;
         if (ie.detail == null) {
            return false;
         }

         th = ie.detail;
      } else if (th instanceof PersistenceRuntimeException) {
         th = ((PersistenceRuntimeException)th).getCausedByException();
      }

      if (!(th instanceof RemoteException) && !(th instanceof RuntimeException)) {
         Class[] var7 = m.getExceptionTypes();
         int var4 = var7.length;

         int var5;
         Class c;
         for(var5 = 0; var5 < var4; ++var5) {
            c = var7[var5];
            if (c.isAssignableFrom(th.getClass())) {
               return true;
            }
         }

         if (extraExceptions != null) {
            var7 = extraExceptions;
            var4 = extraExceptions.length;

            for(var5 = 0; var5 < var4; ++var5) {
               c = var7[var5];
               if (c.isAssignableFrom(th.getClass())) {
                  return true;
               }
            }
         }

         return false;
      } else {
         return false;
      }
   }

   static boolean isOptimisticLockException(Throwable th) {
      return th instanceof OptimisticLockException || th instanceof OptimisticConcurrencyException;
   }

   public static boolean isCausedBy(Throwable th, Class causedByCls) {
      while(th != null) {
         if (causedByCls.isInstance(th)) {
            return true;
         }

         th = th.getCause();
      }

      return false;
   }

   public static void pushEnvironment(Context envContext) {
      javaURLContextFactory.pushContext(envContext);
   }

   public static void popEnvironment() {
      javaURLContextFactory.popContext();
   }
}
