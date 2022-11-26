package weblogic.iiop;

import java.io.EOFException;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.AccessException;
import java.rmi.ConnectIOException;
import java.rmi.MarshalException;
import java.rmi.NoSuchObjectException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnmarshalException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.transaction.InvalidTransactionException;
import javax.transaction.TransactionRequiredException;
import javax.transaction.TransactionRolledbackException;
import org.omg.CORBA.BAD_PARAM;
import org.omg.CORBA.COMM_FAILURE;
import org.omg.CORBA.CompletionStatus;
import org.omg.CORBA.INVALID_TRANSACTION;
import org.omg.CORBA.MARSHAL;
import org.omg.CORBA.NO_PERMISSION;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.omg.CORBA.SystemException;
import org.omg.CORBA.TIMEOUT;
import org.omg.CORBA.TRANSACTION_REQUIRED;
import org.omg.CORBA.TRANSACTION_ROLLEDBACK;
import org.omg.CORBA.UNKNOWN;
import org.omg.CORBA.UserException;
import org.omg.CORBA.portable.IDLEntity;
import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.ObjectImpl;
import org.omg.CORBA.portable.OutputStream;
import org.omg.CORBA.portable.ValueBase;
import org.omg.CosNaming.NamingContextHelper;
import weblogic.corba.cos.naming.NamingContextAnyHelper;
import weblogic.corba.utils.CorbaUtils;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.rmi.extensions.RequestTimeoutException;
import weblogic.rmi.extensions.server.ActivatableRemoteReference;
import weblogic.rmi.extensions.server.ActivatableServerReference;
import weblogic.rmi.extensions.server.CollocatedRemoteReference;
import weblogic.rmi.extensions.server.RemoteReference;
import weblogic.rmi.internal.ServerReference;
import weblogic.rmi.internal.StubInfo;
import weblogic.rmi.utils.Utilities;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.ConcurrentWeakHashMap;

public final class Utils {
   private static Map repositoryIdMap = new ConcurrentHashMap();
   private static Map abstractInterfaceMap = new ConcurrentWeakHashMap();
   private static ConcurrentHashMap helperMap = new ConcurrentHashMap();
   private static ConcurrentHashMap declaredMethodMap = new ConcurrentHashMap();
   private static final DebugLogger debugIIOPDetail = DebugLogger.getDebugLogger("DebugIIOPDetail");
   public static final String IDL_ENTITY_HELPER = "Helper";
   static final String IDL_ENTITY_HOLDER = "Holder";
   static final String VALUE_FACTORY_HELPER = "DefaultFactory";
   static final String OMG_STUB_PREFIX = "org.omg.stub.";
   static final String OMG_UNKNOWN_EX = "IDL:omg.org/CORBA/UNKNOWN:1.0";
   static final String STRING_REP = "IDL:omg.org/CORBA/WStringValue:1.0";
   static final String NULL_REPID = "IDL:omg.org/CORBA/AbstractBase:1.0";
   static final String OBJECT_REPID = "IDL:omg.org/CORBA/Object:1.0";
   static final String NAMING_REPID = "IDL:omg.org/CosNaming/NamingContext:1.0";
   static final String EJB_EXCEPTION_REPID = "RMI:javax.ejb.EJBException:0E3E8C42D0E83868:0B0EB2FF36CB22F6";
   static final String OLD_EJB_EXCEPTION_REPID = "RMI:javax.ejb.EJBException:0E3E8C42D0E83868:800C4C7C598DF61F";
   public static final String WRITE_METHOD = "write";
   public static final String NARROW_METHOD = "narrow";
   public static final String READ_METHOD = "read";
   public static final Class READ_METHOD_ARG = InputStream.class;
   static final Class[] NO_ARGS_METHOD = new Class[0];
   static final String CTOR = "<init>";
   public static final String STUB_EXT = "_IIOP_WLStub";

   private static void addToMap(String clz, String id) {
      if (id != null && clz != null) {
         repositoryIdMap.put(clz, id);
      }

   }

   public static final String getRepositoryID(Class c) {
      String typeid = (String)repositoryIdMap.get(c.getName());
      if (typeid != null) {
         return typeid;
      } else {
         if (Utilities.isARemote(c)) {
            typeid = getIDFromRemote(c);
         } else if (Throwable.class.isAssignableFrom(c)) {
            typeid = getIDFromException(c);
         } else if (IDLEntity.class.isAssignableFrom(c)) {
            typeid = getIDFromIDLEntity(c);
         } else if (Serializable.class.isAssignableFrom(c)) {
            typeid = ValueHandlerImpl.getRepositoryID(c);
         }

         return typeid;
      }
   }

   private static String getIDFromRemote(Class clzz) {
      String typeid = null;

      for(Class c = clzz; Utilities.isARemote(c); c = c.getSuperclass()) {
         Class[] ca = c.getInterfaces();

         for(int i = 0; i < ca.length; ++i) {
            Class ifc = ca[i];
            if (Utilities.isARemote(ifc)) {
               if (IDLUtils.isIDLInterface(ifc)) {
                  typeid = getIDFromIDLEntity(ifc);
               } else {
                  typeid = IDLUtils.getTypeID(ifc);
               }

               if (typeid != null) {
                  repositoryIdMap.put(clzz.getName(), typeid);
               }

               return typeid;
            }
         }
      }

      return null;
   }

   private static String getIDFromIDLEntity(Class c) {
      String typeid = CorbaUtils.createIDFromIDLEntity(c);
      if (typeid != null) {
         repositoryIdMap.put(c.getName(), typeid);
      }

      return typeid;
   }

   public static String getRepositoryID(StubInfo info) {
      String typeid = info.getRepositoryId();
      if (typeid == null) {
         Class[] c = info.getInterfaces();
         Class[] ifs = new Class[c.length];
         int idx = 0;
         int remotes = 0;
         int i = 0;

         while(true) {
            if (i >= c.length) {
               if (remotes > 1) {
                  RemoteReference rr = info.getRemoteRef();
                  if (rr instanceof CollocatedRemoteReference) {
                     ServerReference sr = ((CollocatedRemoteReference)rr).getServerReference();
                     Object impl = sr.getImplementation();
                     if (impl == null) {
                        if (!(sr instanceof ActivatableServerReference) || !(rr instanceof ActivatableRemoteReference)) {
                           debugIIOPDetail.debug("Unexptected error: Unable to get implementation: ServerReference=" + sr + "; RemoteReference=" + rr);
                           throw new RemoteRuntimeException("Unexptected error: unable to get implementation");
                        }

                        try {
                           impl = ((ActivatableServerReference)sr).getImplementation(((ActivatableRemoteReference)rr).getActivationID());
                        } catch (RemoteException var10) {
                           debugIIOPDetail.debug("Unable to get implementation: ServerReference=" + sr + "; RemoteReference=" + rr, var10);
                           throw new RemoteRuntimeException("Unable to get implementation", var10);
                        }
                     }

                     typeid = IDLUtils.getTypeID(impl.getClass());
                  }
               }

               if (typeid == null) {
                  typeid = (String)repositoryIdMap.get(ifs[0].getName());
                  if (typeid == null) {
                     typeid = getIDFromRemote(ifs);
                  }
               }

               info.setRepositoryId(typeid);
               break;
            }

            if (Utilities.isARemote(c[i])) {
               int j = 0;

               while(true) {
                  if (j >= idx) {
                     ifs[idx++] = c[i];
                     if (Remote.class.isAssignableFrom(c[i])) {
                        ++remotes;
                     }
                     break;
                  }

                  if (c[i].isAssignableFrom(ifs[j])) {
                     break;
                  }

                  ++j;
               }
            }

            ++i;
         }
      }

      return typeid;
   }

   private static String getIDFromRemote(Class[] clzz) {
      String typeid = null;
      Class[] ca = clzz;

      for(int i = 0; i < ca.length - 1; ++i) {
         Class ifc = ca[i];
         if (Utilities.isARemote(ifc)) {
            typeid = IDLUtils.getTypeID(ifc);
            if (typeid != null) {
               repositoryIdMap.put(ca[i].getName(), typeid);
            }

            return typeid;
         }
      }

      return null;
   }

   public static String getIDFromException(Class c) {
      if (!SystemException.class.isAssignableFrom(c) && !UserException.class.isAssignableFrom(c)) {
         if (!RuntimeException.class.isAssignableFrom(c) && !Error.class.isAssignableFrom(c)) {
            String name = c.getName().replace('.', '/');
            if (name.endsWith("Exception")) {
               name = name.substring(0, name.length() - "ception".length());
            }

            String typeid = "IDL:" + name + ":1.0";
            repositoryIdMap.put(c.getName(), typeid);
            return typeid;
         } else {
            return "IDL:omg.org/CORBA/UNKNOWN:1.0";
         }
      } else {
         return getIDFromIDLEntity(c);
      }
   }

   public static Class getClassFromStub(Class stubClass) {
      if (stubClass.isInterface() && IDLEntity.class.isAssignableFrom(stubClass) && org.omg.CORBA.Object.class.isAssignableFrom(stubClass) && !IDLEntity.class.equals(stubClass)) {
         return stubClass;
      } else {
         Class[] ifs = stubClass.getInterfaces();

         for(int i = 0; i < ifs.length; ++i) {
            if (IDLEntity.class.isAssignableFrom(ifs[i]) && org.omg.CORBA.Object.class.isAssignableFrom(ifs[i]) && !IDLEntity.class.equals(ifs[i])) {
               return ifs[i];
            }
         }

         return null;
      }
   }

   public static Class getStubFromClass(Class c) {
      return getStubFromClass(c, (String)null);
   }

   public static Class getStubFromClass(Class c, String codebase) {
      String cname = null;
      if (c.getPackage() != null) {
         String pkg = c.getPackage().getName();
         cname = pkg + "._" + c.getName().substring(pkg.length() + 1) + "Stub";
      } else {
         cname = "_" + c.getName() + "Stub";
      }

      Class ret = null;

      try {
         ret = CorbaUtils.loadClass(cname, codebase, c.getClassLoader());
      } catch (ClassNotFoundException var5) {
      }

      return ret;
   }

   public static String getClassNameFromStubName(String name) {
      int iiopstubIndex = name.indexOf("_Stub.class");
      if (iiopstubIndex >= 0) {
         if (name.startsWith("org.omg.stub.")) {
            name = name.substring("org.omg.stub.".length());
            iiopstubIndex -= "org.omg.stub.".length();
         }

         int idx = name.indexOf("._");
         if (idx >= 0) {
            name = name.substring(0, idx) + "." + name.substring(idx + 2, iiopstubIndex);
         } else {
            name = name.substring(1, iiopstubIndex);
         }

         return name;
      } else {
         return null;
      }
   }

   public static boolean isStubName(String name) {
      return name.endsWith("_Stub.class") && (name.indexOf("/_") > 0 || name.startsWith("_"));
   }

   public static boolean isIDLException(Class c) {
      return !IDLEntity.class.equals(c) && IDLEntity.class.isAssignableFrom(c) && !ValueBase.class.isAssignableFrom(c) && Throwable.class.isAssignableFrom(c);
   }

   public static boolean isAbstractInterface(Class c) {
      Boolean is = (Boolean)abstractInterfaceMap.get(c);
      if (is != null) {
         return is;
      } else {
         boolean isa = CorbaUtils.isAbstractInterface(c);
         abstractInterfaceMap.put(c, isa);
         return isa;
      }
   }

   static Class findIDLInterface(Class c) {
      if (c == null) {
         return null;
      } else {
         Class sup = c.getSuperclass();
         if (IDLEntity.class.equals(sup)) {
            return c;
         } else {
            Class[] ifs = c.getInterfaces();

            for(int i = 0; i < ifs.length; ++i) {
               if (IDLEntity.class.equals(ifs[i])) {
                  return c;
               }
            }

            Class ret = null;
            if (sup != null && sup.isInterface() && (ret = findIDLInterface(sup)) != null) {
               return ret;
            } else {
               for(int i = 0; i < ifs.length; ++i) {
                  if ((ret = findIDLInterface(ifs[i])) != null) {
                     return ret;
                  }
               }

               if (sup != null && !sup.isInterface()) {
                  ret = findIDLInterface(sup);
               }

               return ret;
            }
         }
      }
   }

   public static Class getHelper(Class c, String suffix) {
      ClassLoader cl = c.getClassLoader();
      Class helper = (Class)helperMap.get(c);
      if (helper != null) {
         return helper;
      } else {
         String cname = c.getName() + suffix;

         try {
            helper = CorbaUtils.loadClass(cname, (String)null, cl);
         } catch (ClassNotFoundException var8) {
            try {
               helper = CorbaUtils.loadClass(cname);
            } catch (ClassNotFoundException var7) {
            }
         }

         if (helper != null) {
            helperMap.put(c, helper);
         }

         return helper;
      }
   }

   public static Class getIDLHelper(Class c) {
      Class helper = getHelper(c, "Helper");
      if (helper == null) {
         Class idl = findIDLInterface(c);
         if (idl != null) {
            helper = getHelper(idl, "Helper");
         }
      }

      return helper;
   }

   public static Method getIDLWriter(Class c) {
      Method m = null;
      Class idl = c;
      Class helper = getHelper(c, "Helper");
      if (helper == null) {
         idl = findIDLInterface(c);
         helper = getHelper(idl, "Helper");
      }

      return helper == null ? null : getDeclaredMethod(helper, "write", OutputStream.class, idl);
   }

   public static Member getDeclaredMember(Class c, String name, Class[] args) {
      ConcurrentHashMap methodMap = (ConcurrentHashMap)declaredMethodMap.get(c);
      if (methodMap == null) {
         synchronized(declaredMethodMap) {
            methodMap = (ConcurrentHashMap)declaredMethodMap.get(c);
            if (methodMap == null) {
               methodMap = new ConcurrentHashMap();
               declaredMethodMap.put(c, methodMap);
            }
         }
      }

      try {
         AccessibleObject m = (AccessibleObject)methodMap.get(name);
         if (m == null) {
            if ("<init>".equals(name)) {
               m = c.getDeclaredConstructor(args);
            } else {
               m = c.getDeclaredMethod(name, args);
            }

            if (!((AccessibleObject)m).isAccessible()) {
               try {
                  ((AccessibleObject)m).setAccessible(true);
               } catch (SecurityException var6) {
               }
            }

            methodMap.put(name, m);
         }

         return (Member)m;
      } catch (NoSuchMethodException var7) {
         methodMap.put(name, name);
         return null;
      } catch (ClassCastException var8) {
         return null;
      }
   }

   public static Constructor getNoArgConstructor(Class c) {
      return (Constructor)getDeclaredMember(c, "<init>", NO_ARGS_METHOD);
   }

   public static Method getDeclaredMethod(Class c, String name, Class... args) {
      return (Method)getDeclaredMember(c, name, args);
   }

   public static Constructor getDeclaredConstructor(Class c, Class[] args) {
      return (Constructor)getDeclaredMember(c, "<init>", args);
   }

   public static org.omg.CORBA.Object narrow(ObjectImpl o, Class c) throws InvocationTargetException, IllegalAccessException, ClassCastException {
      Class helper = getIDLHelper(c);
      if (helper == null) {
         throw new ClassCastException("Couldn't find helper for: " + c.getName());
      } else {
         Method m = getDeclaredMethod(helper, "narrow", org.omg.CORBA.Object.class);
         if (m == null) {
            throw new ClassCastException("Couldn't find helper for: " + c.getName());
         } else {
            return (org.omg.CORBA.Object)m.invoke((Object)null, o);
         }
      }
   }

   public static IOException mapToRemoteException(IOException e) {
      if (e instanceof UnknownHostException) {
         return new java.rmi.UnknownHostException(e.getMessage(), e);
      } else if (e instanceof ConnectException) {
         return new java.rmi.ConnectException(e.getMessage(), e);
      } else {
         return (IOException)(e instanceof SocketException ? new ConnectIOException(e.getMessage(), e) : e);
      }
   }

   public static String getAnnotation(ClassLoader loadingContext) {
      if (loadingContext == null) {
         loadingContext = Thread.currentThread().getContextClassLoader();
      }

      String annotation = null;
      if (loadingContext instanceof GenericClassLoader) {
         annotation = ((GenericClassLoader)loadingContext).getAnnotation().getAnnotationString();
         if (annotation.length() == 0) {
            annotation = null;
         }
      }

      return annotation;
   }

   public static final SystemException mapRemoteToCORBAException(RemoteException e) {
      SystemException se = null;
      if (e instanceof java.rmi.UnknownHostException) {
         se = new BAD_PARAM(e.getMessage(), 1330446344, CompletionStatus.COMPLETED_NO);
      } else if (e instanceof java.rmi.ConnectException) {
         se = new COMM_FAILURE(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
      } else if (e instanceof ConnectIOException) {
         se = new COMM_FAILURE(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
      } else if (e instanceof MarshalException) {
         se = new MARSHAL(e.getMessage(), 0, CompletionStatus.COMPLETED_MAYBE);
      } else if (e instanceof UnmarshalException) {
         se = new MARSHAL(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
      } else if (e instanceof NoSuchObjectException) {
         se = new OBJECT_NOT_EXIST(e.getMessage(), 1330446337, CompletionStatus.COMPLETED_NO);
      } else if (e instanceof AccessException) {
         se = new NO_PERMISSION(e.getMessage(), 0, CompletionStatus.COMPLETED_MAYBE);
      } else if (e instanceof TransactionRequiredException) {
         se = new TRANSACTION_REQUIRED(e.getMessage(), 0, CompletionStatus.COMPLETED_MAYBE);
      } else if (e instanceof TransactionRolledbackException) {
         se = new TRANSACTION_ROLLEDBACK(e.getMessage(), 0, CompletionStatus.COMPLETED_MAYBE);
      } else if (e instanceof InvalidTransactionException) {
         se = new INVALID_TRANSACTION(e.getMessage(), 0, CompletionStatus.COMPLETED_MAYBE);
      } else if (e instanceof RequestTimeoutException) {
         se = new TIMEOUT(e.getMessage(), 0, CompletionStatus.COMPLETED_MAYBE);
      }

      if (se != null) {
         ((SystemException)se).initCause(e);
      }

      return (SystemException)se;
   }

   public static final SystemException mapToCORBAException(IOException e) {
      if (e.getCause() instanceof SystemException) {
         return (SystemException)e.getCause();
      } else if (e.getCause() instanceof IOException) {
         return mapToCORBAException((IOException)e.getCause());
      } else {
         SystemException se = null;
         if (e instanceof RemoteException) {
            se = mapRemoteToCORBAException((RemoteException)e);
            if (se != null) {
               return se;
            }
         }

         Object se;
         if (e instanceof UnknownHostException) {
            se = new BAD_PARAM(e.getMessage(), 1330446344, CompletionStatus.COMPLETED_NO);
         } else if (e instanceof ConnectException) {
            se = new COMM_FAILURE(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
         } else if (e instanceof SocketException) {
            se = new COMM_FAILURE(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
         } else if (e instanceof EOFException) {
            se = new COMM_FAILURE(e.getMessage(), 0, CompletionStatus.COMPLETED_NO);
         } else {
            se = new UNKNOWN(e.getMessage(), 0, CompletionStatus.COMPLETED_MAYBE);
         }

         ((SystemException)se).initCause(e);
         return (SystemException)se;
      }
   }

   public static final MARSHAL wrapMARSHALWithCause(Throwable e) {
      MARSHAL m = new MARSHAL(e.getMessage());
      m.initCause(e);
      return m;
   }

   private static void p(String msg) {
      System.out.println("<Utils>: " + msg);
   }

   static {
      addToMap("org.omg.CosNaming.NamingContext", "IDL:omg.org/CosNaming/NamingContextExt:1.0");
      addToMap("java.lang.String", "IDL:omg.org/CORBA/WStringValue:1.0");
      addToMap("weblogic.cos.naming.NamingContext_IIOP_WLStub", NamingContextAnyHelper.id());
      addToMap("weblogic.cos.naming.NamingContextImpl", NamingContextAnyHelper.id());
      addToMap("org.omg.CosNaming.NamingContext", NamingContextHelper.id());
      addToMap("weblogic.corba.cos.naming.NamingContextAny", NamingContextAnyHelper.id());
   }
}
