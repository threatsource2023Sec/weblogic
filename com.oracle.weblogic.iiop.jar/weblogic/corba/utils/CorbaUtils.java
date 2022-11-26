package weblogic.corba.utils;

import java.io.Externalizable;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.rmi.AccessException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import javax.transaction.InvalidTransactionException;
import javax.transaction.TransactionRequiredException;
import javax.transaction.TransactionRolledbackException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.INV_OBJREF;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.Object;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.portable.IDLEntity;
import weblogic.utils.LocatorUtilities;

public class CorbaUtils {
   private static final String WEBLOGIC_OMG_JAVA_PREFIX = "weblogic.corba.idl.";
   private static final String USER_EX = "Ex";
   public static final String USER_EX_SUFFIX = "ception";
   public static final String USER_EXCEPTION = "Exception";
   static final String OMG_JAVA_PREFIX = "org.omg.";
   static final String OMG_IDL_PREFIX = "IDL:omg.org/";
   public static final String IDL_PREFIX = "IDL:";

   public static RemoteException mapSystemException(SystemException ex) {
      RemoteException re = null;
      if (ex instanceof COMM_FAILURE) {
         re = new MarshalException(createDetailMessage(ex), ex);
      } else if (!(ex instanceof INV_OBJREF) && !(ex instanceof OBJECT_NOT_EXIST)) {
         if (ex instanceof NO_PERMISSION) {
            re = new AccessException(createDetailMessage(ex), ex);
         } else if (ex instanceof MARSHAL) {
            if (ex.completed.value() == 1) {
               re = new UnmarshalException(createDetailMessage(ex), ex);
            } else {
               re = new MarshalException(createDetailMessage(ex), ex);
            }
         } else if (ex instanceof TRANSACTION_REQUIRED) {
            re = new TransactionRequiredException(createDetailMessage(ex));
         } else if (ex instanceof TRANSACTION_ROLLEDBACK) {
            re = new TransactionRolledbackException(createDetailMessage(ex));
         } else if (ex instanceof INVALID_TRANSACTION) {
            re = new InvalidTransactionException(createDetailMessage(ex));
         } else if (ex instanceof BAD_PARAM) {
            re = new MarshalException(createDetailMessage(ex), ex);
         } else {
            re = new RemoteException(createDetailMessage(ex), ex);
         }
      } else {
         re = new NoSuchObjectException(createDetailMessage(ex));
      }

      ((RemoteException)re).detail = ex;
      return (RemoteException)re;
   }

   private static String createDetailMessage(SystemException se) {
      StringBuffer msg = new StringBuffer("CORBA ");
      msg.append(se.getClass().getName().substring("org.omg.CORBA.".length()));
      msg.append(" ");
      msg.append(Integer.toHexString(se.minor));
      switch (se.completed.value()) {
         case 0:
            msg.append(" Yes");
            break;
         case 1:
            msg.append(" No");
            break;
         case 2:
            msg.append(" Maybe");
      }

      return msg.toString();
   }

   static String getAnnotation(ClassLoader loadingContext) {
      return CorbaUtils.Singleton.instance.getAnnotation(loadingContext);
   }

   static boolean isIDLInterface(Class c) {
      return Object.class.isAssignableFrom(c) && IDLEntity.class.isAssignableFrom(c);
   }

   public static String createIDFromIDLEntity(Class c) {
      String name = c.getName();
      String typeid;
      if (name.startsWith("org.omg.")) {
         typeid = "IDL:omg.org/" + name.substring("org.omg.".length()).replace('.', '/') + ":1.0";
      } else {
         typeid = "IDL:" + c.getName().replace('.', '/') + ":1.0";
      }

      return typeid;
   }

   public static Class getClassFromID(RepositoryId repid) {
      return getClassFromID(repid, (String)null);
   }

   public static Class getClassFromID(RepositoryId repid, String codebase) {
      Class ret = (Class)RepositoryId.PRIMITIVE_MAP.get(repid);
      if (ret != null) {
         return ret;
      } else {
         String cname = repid.getClassName();
         int idx = false;

         try {
            ret = loadClass(cname, codebase, (ClassLoader)null);
         } catch (ClassNotFoundException var13) {
            if (cname.endsWith("Ex") && (cname = cname + "ception") != null) {
               try {
                  ret = loadClass(cname, codebase, (ClassLoader)null);
               } catch (ClassNotFoundException var11) {
                  cname = cname.substring(0, cname.length() - "Exception".length());

                  try {
                     ret = loadClass(cname, codebase, (ClassLoader)null);
                  } catch (ClassNotFoundException var10) {
                  }
               }
            } else {
               int idx;
               if ((idx = cname.lastIndexOf(46)) >= 0) {
                  String newcname = cname.substring(0, idx) + "Package" + cname.substring(idx);

                  try {
                     ret = loadClass(newcname, codebase, (ClassLoader)null);
                  } catch (ClassNotFoundException var12) {
                     if (cname.startsWith("org.omg.") && (cname = "weblogic.corba.idl." + cname) != null) {
                        try {
                           ret = loadClass(cname);
                        } catch (ClassNotFoundException var9) {
                        }
                     }
                  }
               }
            }
         }

         return ret;
      }
   }

   public static void verifyClassPermitted(Class clz) {
      if (clz == null) {
         throw new IllegalArgumentException("No class found");
      } else {
         for(Class classToVerify = clz; classToVerify != null; classToVerify = classToVerify.getSuperclass()) {
            CorbaUtils.Singleton.instance.verifyClassPermitted(classToVerify);
         }

      }
   }

   public static void checkLegacyBlacklistIfNeeded(String className) {
      if (className == null) {
         throw new IllegalArgumentException("No class name found");
      } else {
         CorbaUtils.Singleton.instance.checkLegacyBlacklistIfNeeded(className);
      }
   }

   public static Class loadClass(String className) throws ClassNotFoundException {
      return loadClass(className, (String)null, (ClassLoader)null);
   }

   public static Class loadClass(String className, String remoteCodebase, ClassLoader loadingContext) throws ClassNotFoundException {
      Class clz = CorbaUtils.Singleton.instance.loadClass(className, remoteCodebase, loadingContext);
      verifyClassPermitted(clz);
      return clz;
   }

   public static boolean isAbstractInterface(Class c) {
      boolean result = false;
      if (isValid(c) && !isARemote(c) && !isRemote(c) && !Serializable.class.equals(c)) {
         Method[] m = c.getMethods();
         Method[] var3 = m;
         int var4 = m.length;
         int var5 = 0;

         while(true) {
            if (var5 >= var4) {
               result = true;
               break;
            }

            Method aM = var3[var5];
            if (!methodThrowsRemoteException(aM)) {
               break;
            }

            ++var5;
         }
      }

      return result;
   }

   public static boolean isValid(Class c) {
      boolean result = true;
      if (String.class.equals(c) || RemoteException.class.equals(c) || c.isPrimitive() || IDLEntity.class.equals(c) || Void.TYPE.equals(c) || Externalizable.class.equals(c)) {
         result = false;
      }

      return result;
   }

   public static boolean isRemote(Class c) {
      return Remote.class.equals(c);
   }

   public static boolean isARemote(Class c) {
      return !isRemote(c) && Remote.class.isAssignableFrom(c);
   }

   private static boolean methodThrowsRemoteException(Method m) {
      Class[] ex = m.getExceptionTypes();
      Class[] var2 = ex;
      int var3 = ex.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Class anEx = var2[var4];
         if (anEx.isAssignableFrom(RemoteException.class)) {
            return true;
         }
      }

      return false;
   }

   private static class Singleton {
      private static final CorbaUtilsDelegate instance = (CorbaUtilsDelegate)LocatorUtilities.getService(CorbaUtilsDelegate.class);
   }
}
