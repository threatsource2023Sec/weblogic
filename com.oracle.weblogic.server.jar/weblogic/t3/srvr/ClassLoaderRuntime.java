package weblogic.t3.srvr;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import weblogic.management.ManagementException;
import weblogic.management.runtime.ClassLoaderRuntimeMBean;
import weblogic.management.runtime.RuntimeMBean;
import weblogic.management.runtime.RuntimeMBeanDelegate;
import weblogic.management.runtime.ServerRuntimeMBean;
import weblogic.server.ClassLoaderPerfCounter;

public class ClassLoaderRuntime extends RuntimeMBeanDelegate implements ClassLoaderRuntimeMBean {
   private final ClassLoaderPerfCounter delegate;

   public static void init(ServerRuntimeMBean serverRuntime) throws ManagementException {
      ClassLoaderRuntime clr = new ClassLoaderRuntime(serverRuntime);
      serverRuntime.setClassLoaderRuntime(clr);
   }

   ClassLoaderRuntime(ServerRuntimeMBean serverRuntime) throws ManagementException {
      this("system-class-loader", ClassLoader.getSystemClassLoader(), serverRuntime);
   }

   public ClassLoaderRuntime(String nameArg, ClassLoader loader, RuntimeMBean parentArg) throws ManagementException {
      super(nameArg, parentArg);
      this.delegate = getPerfDelegate(loader);
   }

   private static ClassLoaderPerfCounter getPerfDelegate(final ClassLoader loader) {
      if (loader instanceof ClassLoaderPerfCounter) {
         return (ClassLoaderPerfCounter)loader;
      } else {
         final Class c = loader.getClass();
         InvocationHandler handler = new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               Class[] parameterTypes = null;
               if (args != null) {
                  parameterTypes = new Class[args.length];

                  for(int i = 0; i < args.length; ++i) {
                     parameterTypes[i] = args[i].getClass();
                  }
               }

               Method cm = c.getMethod(method.getName(), parameterTypes);
               return cm.invoke(loader, args);
            }
         };
         Class proxyClass = Proxy.getProxyClass(ClassLoaderPerfCounter.class.getClassLoader(), ClassLoaderPerfCounter.class);

         try {
            return (ClassLoaderPerfCounter)proxyClass.getConstructor(InvocationHandler.class).newInstance(handler);
         } catch (InstantiationException var5) {
            throw new RuntimeException(var5);
         } catch (IllegalAccessException var6) {
            throw new RuntimeException(var6);
         } catch (IllegalArgumentException var7) {
            throw new RuntimeException(var7);
         } catch (InvocationTargetException var8) {
            throw new RuntimeException(var8);
         } catch (NoSuchMethodException var9) {
            throw new RuntimeException(var9);
         } catch (SecurityException var10) {
            throw new RuntimeException(var10);
         }
      }
   }

   public long getLoadClassTime() {
      return convertNanosToMillis(this.delegate.getLoadClassTime());
   }

   public long getLoadClassCount() {
      return this.delegate.getLoadClassCount();
   }

   public long getFindClassTime() {
      return convertNanosToMillis(this.delegate.getFindClassTime());
   }

   public long getFindClassCount() {
      return this.delegate.getFindClassCount();
   }

   public long getDefineClassTime() {
      return convertNanosToMillis(this.delegate.getDefineClassTime());
   }

   public long getDefineClassCount() {
      return this.delegate.getDefineClassCount();
   }

   public long getResourceTime() {
      return convertNanosToMillis(this.delegate.getResourceTime());
   }

   public long getResourceCount() {
      return this.delegate.getResourceCount();
   }

   public long getParentDelegationTime() {
      return convertNanosToMillis(this.delegate.getParentDelegationTime());
   }

   public long getParentDelegationCount() {
      return this.delegate.getParentDelegationCount();
   }

   public long getIndexingTime() {
      return convertNanosToMillis(this.delegate.getIndexingTime());
   }

   public long getBeforeIndexingLoadClassTime() {
      return convertNanosToMillis(this.delegate.getBeforeIndexingLoadClassTime());
   }

   public long getBeforeIndexingLoadClassCount() {
      return this.delegate.getBeforeIndexingLoadClassCount();
   }

   public long getBeforeIndexingFindClassTime() {
      return convertNanosToMillis(this.delegate.getBeforeIndexingFindClassTime());
   }

   public long getBeforeIndexingFindClassCount() {
      return this.delegate.getBeforeIndexingFindClassCount();
   }

   public long getBeforeIndexingResourceTime() {
      return convertNanosToMillis(this.delegate.getBeforeIndexingResourceTime());
   }

   public long getBeforeIndexingResourceCount() {
      return this.delegate.getBeforeIndexingResourceCount();
   }

   public long getDuringIndexingLoadClassTime() {
      return convertNanosToMillis(this.delegate.getDuringIndexingLoadClassTime());
   }

   public long getDuringIndexingLoadClassCount() {
      return this.delegate.getDuringIndexingLoadClassCount();
   }

   public long getDuringIndexingFindClassTime() {
      return convertNanosToMillis(this.delegate.getDuringIndexingFindClassTime());
   }

   public long getDuringIndexingFindClassCount() {
      return this.delegate.getDuringIndexingFindClassCount();
   }

   public long getDuringIndexingResourceTime() {
      return convertNanosToMillis(this.delegate.getDuringIndexingResourceTime());
   }

   public long getDuringIndexingResourceCount() {
      return this.delegate.getDuringIndexingResourceCount();
   }

   public long getAfterIndexingLoadClassTime() {
      return convertNanosToMillis(this.delegate.getAfterIndexingLoadClassTime());
   }

   public long getAfterIndexingLoadClassCount() {
      return this.delegate.getAfterIndexingLoadClassCount();
   }

   public long getAfterIndexingFindClassTime() {
      return convertNanosToMillis(this.delegate.getAfterIndexingFindClassTime());
   }

   public long getAfterIndexingFindClassCount() {
      return this.delegate.getAfterIndexingFindClassCount();
   }

   public long getAfterIndexingResourceTime() {
      return convertNanosToMillis(this.delegate.getAfterIndexingResourceTime());
   }

   public long getAfterIndexingResourceCount() {
      return this.delegate.getAfterIndexingResourceCount();
   }

   private static long convertNanosToMillis(long nanos) {
      return nanos / 1000000L;
   }
}
