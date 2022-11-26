package weblogic.ejb.container.internal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Map;
import javax.ejb.EJBException;
import weblogic.application.AppClassLoaderManager;
import weblogic.application.ClassLoaderNotFoundException;
import weblogic.ejb.container.ejbc.bytecodegen.RemoteBusIntfClassAdapter;
import weblogic.ejb.container.ejbc.codegen.MethodSignature;
import weblogic.ejb.spi.BusinessObject;
import weblogic.kernel.KernelStatus;
import weblogic.rmi.RemoteEJBInvokeException;
import weblogic.rmi.extensions.PortableRemoteObject;
import weblogic.rmi.extensions.RemoteRuntimeException;
import weblogic.server.GlobalServiceLocator;
import weblogic.utils.classloaders.AugmentableClassLoaderManager;
import weblogic.utils.classloaders.GenericClassLoader;
import weblogic.utils.collections.SoftHashMap;

public class RemoteBusinessIntfProxy implements InvocationHandler, Serializable {
   private Object ejbObject;
   private String moduleClassLoaderAnnotationString;
   private String remoteBusinessInterfaceName;
   private String generatedRemoteInterfaceName;
   private Class generatedRemoteInterface = null;
   private boolean newProxy = false;
   private boolean isInterAppCLUsed = false;
   /** @deprecated */
   @Deprecated
   private Map methodsCache = null;
   private static final long serialVersionUID = 4118555029445466816L;
   private static final boolean DEBUG = !KernelStatus.isApplet() && Boolean.getBoolean("weblogic.ejb.enhancement.debug");

   public RemoteBusinessIntfProxy(Object ejbObject, String moduleClassLoaderAnnotationString, String remoteBusinessInterfaceName, String generatedRemoteInterfaceName) {
      this.ejbObject = ejbObject;
      this.moduleClassLoaderAnnotationString = moduleClassLoaderAnnotationString;
      this.remoteBusinessInterfaceName = remoteBusinessInterfaceName;
      this.generatedRemoteInterfaceName = generatedRemoteInterfaceName;
      this.newProxy = true;
   }

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
      Throwable targetException;
      try {
         Object targetObject = this.getTargetObject(method);
         targetException = null;
         Method targetMethod;
         if (this.newProxy) {
            targetMethod = targetObject.getClass().getMethod(method.getName(), method.getParameterTypes());
         } else {
            targetMethod = this.getTargetMethod(method, targetObject);
         }

         if (this.isEqualsMethod(targetMethod)) {
            if (args[0] == null || !Proxy.isProxyClass(args[0].getClass())) {
               return Boolean.FALSE;
            }

            InvocationHandler h = Proxy.getInvocationHandler(args[0]);
            if (!(h instanceof RemoteBusinessIntfProxy)) {
               return Boolean.FALSE;
            }

            args[0] = ((RemoteBusinessIntfProxy)h).ejbObject;
         }

         return targetMethod.invoke(targetObject, args);
      } catch (Throwable var7) {
         if (!(var7 instanceof InvocationTargetException)) {
            throw var7;
         } else {
            targetException = ((InvocationTargetException)var7).getTargetException();
            if (targetException instanceof RemoteRuntimeException) {
               if (targetException.getCause() instanceof RemoteException) {
                  throw this.unwrapRemoteException((RemoteException)targetException.getCause());
               } else {
                  throw targetException;
               }
            } else if (targetException instanceof RemoteException) {
               throw this.unwrapRemoteException((RemoteException)targetException);
            } else {
               throw targetException;
            }
         }
      }
   }

   private boolean isEqualsMethod(Method m) {
      return "equals".equals(m.getName()) && m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == Object.class;
   }

   private Throwable unwrapRemoteException(RemoteException re) {
      if (re instanceof ServerException && re.detail instanceof RemoteException) {
         re = (RemoteException)re.detail;
      }

      if (re instanceof RemoteEJBInvokeException) {
         if (!(re.detail instanceof RemoteException)) {
            return re.detail;
         }

         re = (RemoteException)re.detail;
      }

      if (re.detail != null && re.detail instanceof Exception) {
         return (Throwable)(re.detail instanceof EJBException ? re.detail : new EJBException(re.getMessage(), (Exception)re.detail));
      } else {
         return new EJBException(re.getMessage(), re);
      }
   }

   private Object getTargetObject(Method method) {
      return method.getDeclaringClass() == BusinessObject.class ? PortableRemoteObject.narrow(this.ejbObject, BusinessObject.class) : this.ejbObject;
   }

   /** @deprecated */
   @Deprecated
   private Method getTargetMethod(Method method, Object currentEJBObject) throws ClassNotFoundException, SecurityException, NoSuchMethodException {
      if (this.methodsCache == null) {
         this.methodsCache = new SoftHashMap();
      }

      Method targetMethod = (Method)this.methodsCache.get(method.toGenericString());
      if (targetMethod == null) {
         Class eoClass = currentEJBObject.getClass();
         if (method.toGenericString().equals(method.toString())) {
            targetMethod = eoClass.getMethod(method.getName(), method.getParameterTypes());
         } else {
            if (method.getTypeParameters().length > 0) {
               targetMethod = eoClass.getMethod(method.getName(), method.getParameterTypes());
            } else {
               Class rbi = this.getClassLoader().loadClass(this.remoteBusinessInterfaceName);
               MethodSignature sig = new MethodSignature(method, rbi);
               Method[] var7 = eoClass.getMethods();
               int var8 = var7.length;

               for(int var9 = 0; var9 < var8; ++var9) {
                  Method m = var7[var9];
                  if (MethodSignature.equalsMethodsBySig(sig, new MethodSignature(m, eoClass))) {
                     targetMethod = m;
                     break;
                  }
               }
            }

            if (targetMethod == null) {
               throw new AssertionError("Cannot find method " + method + " on EJB Object");
            }
         }

         this.methodsCache.put(method.toGenericString(), targetMethod);
      }

      if (this.generatedRemoteInterface != null && this.generatedRemoteInterface.isAssignableFrom(method.getDeclaringClass())) {
         return targetMethod;
      } else {
         throw new EJBException(method.getName() + " is not invoked on a valid object.");
      }
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeObject(this.moduleClassLoaderAnnotationString);
      out.writeObject(this.generatedRemoteInterfaceName);
      out.writeObject(this.remoteBusinessInterfaceName);
      out.writeObject(this.ejbObject);
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      this.moduleClassLoaderAnnotationString = (String)in.readObject();
      this.generatedRemoteInterfaceName = (String)in.readObject();
      if (this.generatedRemoteInterfaceName.endsWith("RIntf")) {
         this.newProxy = true;
      }

      this.remoteBusinessInterfaceName = (String)in.readObject();
      ClassLoader cl = this.getClassLoader();
      Thread currentThread = Thread.currentThread();
      ClassLoader save = currentThread.getContextClassLoader();
      if (!KernelStatus.isThinIIOPClient()) {
         GenericClassLoader gcl = AugmentableClassLoaderManager.getAugmentableClassLoader(cl);

         try {
            this.generatedRemoteInterface = gcl.loadClass(this.generatedRemoteInterfaceName);
            if (DEBUG) {
               this.debug("Loaded " + this.generatedRemoteInterfaceName + " using: " + gcl);
            }
         } catch (ClassNotFoundException var31) {
            if (DEBUG) {
               this.debug("Cannot load " + this.generatedRemoteInterfaceName + " using: " + gcl);
            }

            Class busIntf = null;

            try {
               busIntf = gcl.loadClass(this.remoteBusinessInterfaceName);
            } catch (ClassNotFoundException var30) {
               if (DEBUG) {
                  this.debug("Cannot load " + this.remoteBusinessInterfaceName + " using: " + gcl);
               }
            }

            if (busIntf != null) {
               this.generatedRemoteInterface = this.createRemoteInterface(gcl, busIntf);
               if (DEBUG) {
                  this.debug("Generated interface " + this.generatedRemoteInterface);
               }
            }
         }

         currentThread.setContextClassLoader(gcl);

         try {
            Object obj = in.readObject();
            if (this.generatedRemoteInterface == null) {
               this.generatedRemoteInterface = gcl.loadClass(this.generatedRemoteInterfaceName);
               if (DEBUG) {
                  this.debug("Loaded " + this.generatedRemoteInterfaceName + " after reading EJBObject using: " + gcl);
               }
            }

            this.ejbObject = PortableRemoteObject.narrow(obj, this.generatedRemoteInterface);
         } catch (ClassNotFoundException var25) {
            if (DEBUG) {
               this.debug("Cannot read EJBObject. Exception:" + var25 + " ,about: " + this.generatedRemoteInterfaceName + " using: " + gcl + " ,moduleClassLoaderAnnotationString:" + this.moduleClassLoaderAnnotationString + " ,isInterAppCLUsed:" + this.isInterAppCLUsed);
            }

            if (this.moduleClassLoaderAnnotationString != null && !this.isInterAppCLUsed) {
               throw new ClassLoaderNotFoundException(this.generatedRemoteInterfaceName + " is not found due to missing GenericClassLoader.annotation:" + this.moduleClassLoaderAnnotationString, var25);
            }

            throw var25;
         } finally {
            currentThread.setContextClassLoader(save);
         }
      } else {
         try {
            this.generatedRemoteInterface = cl.loadClass(this.generatedRemoteInterfaceName);
            if (DEBUG) {
               this.debug("Loaded " + this.generatedRemoteInterfaceName + " using: " + cl);
            }
         } catch (ClassNotFoundException var29) {
            if (DEBUG) {
               this.debug("Cannot load " + this.generatedRemoteInterfaceName + " using: " + cl);
            }
         }

         currentThread.setContextClassLoader(cl);

         try {
            Object obj = in.readObject();
            if (this.generatedRemoteInterface == null) {
               ClassLoader stubLoader = obj.getClass().getClassLoader();
               if (DEBUG) {
                  this.debug("Attempting to load " + this.generatedRemoteInterfaceName + " using: " + stubLoader);
               }

               this.generatedRemoteInterface = Class.forName(this.generatedRemoteInterfaceName, false, stubLoader);
            }

            this.ejbObject = PortableRemoteObject.narrow(obj, this.generatedRemoteInterface);
         } catch (ClassNotFoundException var27) {
            if (DEBUG) {
               this.debug("Cannot read EJBObject. Exception:" + var27 + " ,about: " + this.generatedRemoteInterfaceName + " using: " + cl + " ,moduleClassLoaderAnnotationString:" + this.moduleClassLoaderAnnotationString + " ,isInterAppCLUsed:" + this.isInterAppCLUsed);
            }

            if (this.moduleClassLoaderAnnotationString != null && !this.isInterAppCLUsed) {
               throw new ClassLoaderNotFoundException(this.generatedRemoteInterfaceName + " is not found due to missing GenericClassLoader.annotation:" + this.moduleClassLoaderAnnotationString, var27);
            }

            throw var27;
         } finally {
            currentThread.setContextClassLoader(save);
         }
      }

   }

   private Class createRemoteInterface(GenericClassLoader gcl, final Class busIntf) throws ClassNotFoundException, IOException {
      if (!this.newProxy) {
         RemoteBusinessIntfGenerator rbiGen = new RemoteBusinessIntfGenerator(this.generatedRemoteInterfaceName, busIntf, gcl);
         return rbiGen.generateRemoteInterface();
      } else {
         final byte[] classBytes = RemoteBusIntfClassAdapter.getRBIBytes(busIntf, this.generatedRemoteInterfaceName.replace('.', '/'));

         try {
            return gcl.defineCodeGenClass(this.generatedRemoteInterfaceName, classBytes, (URL)null);
         } catch (final IllegalAccessError var5) {
            if (DEBUG) {
               this.debug("IllegalAccessError occured, going to define on business interface's ClassLoader");
            }

            return (Class)AccessController.doPrivileged(new PrivilegedAction() {
               public Class run() {
                  try {
                     Method defineMethod = ClassLoader.class.getDeclaredMethod("defineClass", String.class, byte[].class, Integer.TYPE, Integer.TYPE, ProtectionDomain.class);
                     if (!defineMethod.isAccessible()) {
                        defineMethod.setAccessible(true);
                     }

                     return (Class)defineMethod.invoke(busIntf.getClassLoader(), RemoteBusinessIntfProxy.this.generatedRemoteInterfaceName, classBytes, 0, classBytes.length, busIntf.getProtectionDomain());
                  } catch (Exception var2) {
                     throw var5;
                  }
               }
            });
         }
      }
   }

   private ClassLoader getClassLoader() {
      ClassLoader ccl = Thread.currentThread().getContextClassLoader();
      if (KernelStatus.isApplet()) {
         return ccl;
      } else {
         if (this.moduleClassLoaderAnnotationString != null && KernelStatus.isServer()) {
            AppClassLoaderManager mgr = (AppClassLoaderManager)GlobalServiceLocator.getServiceLocator().getService(AppClassLoaderManager.class, new Annotation[0]);
            weblogic.utils.classloaders.Annotation anno = new weblogic.utils.classloaders.Annotation(this.moduleClassLoaderAnnotationString);
            ClassLoader acl = mgr.findLoader(anno);
            if (acl != null) {
               for(ClassLoader c2 = ccl; c2 != null; c2 = c2.getParent()) {
                  if (c2 == acl) {
                     return ccl;
                  }
               }
            }

            acl = mgr.findOrCreateInterAppLoader(anno, ccl);
            if (acl != null) {
               this.isInterAppCLUsed = true;
               return acl;
            }
         }

         this.isInterAppCLUsed = false;
         return ccl;
      }
   }

   private void debug(String msg) {
      System.out.println("[RemoteBusinessIntfProxy] [" + Thread.currentThread() + "] " + msg);
   }
}
