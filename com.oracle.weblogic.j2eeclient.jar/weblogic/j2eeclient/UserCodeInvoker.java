package weblogic.j2eeclient;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.naming.NamingException;
import weblogic.application.naming.DataSourceBinder;
import weblogic.common.ResourceException;
import weblogic.j2ee.descriptor.LifecycleCallbackBean;
import weblogic.utils.Debug;
import weblogic.utils.classloaders.GenericClassLoader;

public final class UserCodeInvoker {
   private static final AppClientTextTextFormatter TEXT_FORMATTER = AppClientTextTextFormatter.getInstance();
   private final File clientJar;
   private final String[] userCodeArgs;
   private DataSourceBinder dataSourceBinder;
   private ManagedBeanProcessor mgedBeanProcessor;

   public UserCodeInvoker(File clientJar, String[] argv, DataSourceBinder dataSourceBinder, ManagedBeanProcessor mgedBeanProcessor) throws Exception {
      this.clientJar = clientJar;
      this.userCodeArgs = argv;
      this.dataSourceBinder = dataSourceBinder;
      this.mgedBeanProcessor = mgedBeanProcessor;
   }

   protected void invokeUserCode(GenericClassLoader gcl, String mainClassName, LifecycleCallbackBean[] postConstructCallbackBeans, LifecycleCallbackBean[] preDestroyCallbackBeans) throws InvocationTargetException {
      Class mainClass;
      try {
         mainClass = gcl.loadClass(mainClassName);
      } catch (ClassNotFoundException var24) {
         throw new RuntimeException(TEXT_FORMATTER.mainClassNotFound(mainClassName));
      }

      this.invokePostConstructCallbacks(mainClass, gcl, postConstructCallbackBeans);

      try {
         try {
            mainClass.getMethod("main", String[].class).invoke((Object)null, this.userCodeArgs);
         } catch (IllegalAccessException var22) {
            throw new RuntimeException(TEXT_FORMATTER.methodShouldBePublic(mainClassName, "main"));
         }
      } catch (NoSuchMethodException var23) {
         throw new RuntimeException(TEXT_FORMATTER.methodNotFound(mainClassName, this.clientJar.getPath(), "main", ""));
      } finally {
         if (this.dataSourceBinder != null) {
            try {
               this.dataSourceBinder.unbindDataSources();
            } catch (ResourceException var20) {
               throw new RuntimeException(TEXT_FORMATTER.unableToUnbindDatasources(var20));
            } catch (NamingException var21) {
               throw new RuntimeException(TEXT_FORMATTER.unableToUnbindDatasources(var21));
            }
         }

         this.invokePreDestroyCallbacks(mainClass, gcl, preDestroyCallbackBeans);
         if (this.mgedBeanProcessor != null) {
            try {
               this.mgedBeanProcessor.cleanup();
            } catch (Exception var19) {
               throw new RuntimeException(TEXT_FORMATTER.unableToCleanupManagedBeans(var19));
            }
         }

      }

   }

   private void invokePreDestroyCallbacks(Class mainClass, GenericClassLoader gcl, LifecycleCallbackBean[] preDestroyCallbackBeans) throws InvocationTargetException {
      this.invokeCallbacks(mainClass, gcl, this.sortCallbackClasses(mainClass, gcl, preDestroyCallbackBeans), "PreDestroy");
   }

   private void invokePostConstructCallbacks(Class mainClass, GenericClassLoader gcl, LifecycleCallbackBean[] postConstructCallbackBeans) throws InvocationTargetException {
      this.invokeCallbacks(mainClass, gcl, this.sortCallbackClasses(mainClass, gcl, postConstructCallbackBeans), "PostConstruct");
   }

   private LifecycleCallbackBean[] sortCallbackClasses(Class mainClass, ClassLoader cl, LifecycleCallbackBean[] beans) {
      if (beans.length <= 1) {
         return beans;
      } else {
         List clzList = this.loadCallbackClasses(mainClass, cl, beans);
         List beansList = new ArrayList(Arrays.asList(beans));

         for(int i = 1; i < clzList.size(); ++i) {
            for(int j = 0; j < i; ++j) {
               Class c1 = (Class)clzList.get(i);
               Class c2 = (Class)clzList.get(j);
               if (c1.equals(Object.class) || c2.equals(Object.class)) {
                  break;
               }

               Class c;
               LifecycleCallbackBean lcb;
               if (c1.isAssignableFrom(c2)) {
                  c = (Class)clzList.remove(i);
                  clzList.add(j, c);
                  lcb = (LifecycleCallbackBean)beansList.remove(i);
                  beansList.add(j, lcb);
                  break;
               }

               if (c2.isAssignableFrom(c1)) {
                  while(j + 1 < i && ((Class)clzList.get(j + 1)).isAssignableFrom(c1)) {
                     ++j;
                  }

                  c = (Class)clzList.remove(i);
                  clzList.add(j + 1, c);
                  lcb = (LifecycleCallbackBean)beansList.remove(i);
                  beansList.add(j + 1, lcb);
                  break;
               }
            }
         }

         return (LifecycleCallbackBean[])beansList.toArray(beans);
      }
   }

   private List loadCallbackClasses(Class mainClass, ClassLoader cl, LifecycleCallbackBean[] beans) {
      List clzList = new ArrayList();

      for(int i = 0; i < beans.length; ++i) {
         String clzName = beans[i].getLifecycleCallbackClass();
         if (clzName != null && clzName.trim().length() != 0) {
            try {
               clzList.add(cl.loadClass(clzName));
            } catch (ClassNotFoundException var8) {
               throw new RuntimeException(TEXT_FORMATTER.callbackClassNotFound(clzName));
            }
         } else {
            clzList.add(mainClass);
         }
      }

      return clzList;
   }

   private void invokeCallbacks(Class mainClass, ClassLoader cl, LifecycleCallbackBean[] callBackBeans, String methodType) throws InvocationTargetException {
      if (callBackBeans.length >= 1) {
         for(int i = 0; i < callBackBeans.length; ++i) {
            String callBackClassName = callBackBeans[i].getLifecycleCallbackClass();
            String callBackMethod = callBackBeans[i].getLifecycleCallbackMethod();
            Class callBackClass = null;
            if (callBackClassName != null && callBackClassName.trim().length() != 0) {
               try {
                  callBackClass = cl.loadClass(callBackClassName);
               } catch (ClassNotFoundException var10) {
                  throw new RuntimeException(TEXT_FORMATTER.callbackClassNotFound(callBackClassName));
               }
            } else {
               callBackClass = mainClass;
            }

            this.callLifeCycleMethod(callBackClass, callBackMethod, methodType);
         }

      }
   }

   private void callLifeCycleMethod(Class callBackClass, String callBackMethod, String methodType) throws InvocationTargetException {
      try {
         Method method = callBackClass.getDeclaredMethod(callBackMethod);
         method.setAccessible(true);
         if (Modifier.isStatic(method.getModifiers())) {
            method.invoke((Object)null);
         } else {
            Debug.say(" the " + methodType + " method " + callBackMethod + " in class" + callBackClass.getName() + " is not static. According to the common annotations specification the j2ee client " + methodType + " method  should be static.");
         }

      } catch (NoSuchMethodException var5) {
         throw new RuntimeException(TEXT_FORMATTER.methodNotFound(callBackClass.getName(), this.clientJar.getPath(), callBackMethod, methodType));
      } catch (IllegalArgumentException var6) {
         throw new AssertionError(var6);
      } catch (IllegalAccessException var7) {
         throw new RuntimeException(TEXT_FORMATTER.methodShouldBePublic(callBackClass.getName(), callBackMethod));
      }
   }
}
