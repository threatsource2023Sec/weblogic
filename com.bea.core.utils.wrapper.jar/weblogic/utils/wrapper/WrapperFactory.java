package weblogic.utils.wrapper;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import weblogic.utils.classfile.BadBytecodesException;

public class WrapperFactory {
   private static final boolean DEBUG = false;
   public static final int NO_HANDLER = 0;
   public static final int PASS_NONE = 1;
   public static final int PASS_NAME = 2;
   public static final int PASS_PARAMS = 4;
   private static WrapperFactory wrapperFactory = new WrapperFactory();
   private final HashMap wrapperClasses = new HashMap();
   private boolean debug = false;

   public Class getCachedWrapperClass(String superClassName, Object vendorObj, ClassLoader classLoader) {
      String wrapperClassName = this.getWrapperClassName(superClassName, vendorObj.getClass().getName());
      ClassLoader cl = null;
      if (classLoader != null) {
         cl = classLoader;
      } else {
         vendorObj.getClass().getClassLoader();
      }

      if (cl == null) {
         cl = this.getClass().getClassLoader();
      }

      synchronized(cl) {
         HashMap map = (HashMap)this.wrapperClasses.get(cl);
         if (map == null) {
            map = new HashMap();
            this.wrapperClasses.put(cl, map);
            return null;
         } else {
            Class ret = (Class)map.get(wrapperClassName);
            return ret;
         }
      }
   }

   public void putCachedWrapperClass(String superClassName, Object vendorObj, Class wrapperClass, ClassLoader classLoader) {
      ClassLoader cl = null;
      if (classLoader != null) {
         cl = classLoader;
      } else {
         cl = vendorObj.getClass().getClassLoader();
      }

      if (cl == null) {
         cl = this.getClass().getClassLoader();
      }

      synchronized(cl) {
         HashMap map = (HashMap)this.wrapperClasses.get(cl);
         if (map == null) {
            map = new HashMap();
            this.wrapperClasses.put(cl, map);
         }

         map.put(wrapperClass.getName(), wrapperClass);
      }
   }

   public int needPreInvocationHandler(Method vendorMethod) {
      return 6;
   }

   public int needPostInvocationHandler(Method vendorMethod) {
      return 6;
   }

   public int needInvocationExceptionHandler(Method vendorMethod) {
      return 0;
   }

   public boolean needFinalize() {
      return false;
   }

   public void customerizeWrapperClass(WrapperClassFile cf, Class superClass, Class vendorClass, boolean remote) {
   }

   public final Object createWrapper(Class wrapperClass, Object vendorObj, boolean remote) {
      try {
         Object wrapper = wrapperClass.newInstance();
         ((Wrapper)wrapper).setVendorObj(vendorObj);
         return wrapper;
      } catch (Exception var5) {
         throw new RuntimeException("Failed to Generate Wrapper Class", var5);
      }
   }

   public final Object createWrapper(String superClassName, Object vendorObj, boolean remote, ClassLoader classLoader) {
      Class wrapperClass = this.getWrapperClass(superClassName, vendorObj, remote, classLoader);
      return this.createWrapper(wrapperClass, vendorObj, remote);
   }

   public final Object createWrapper(String superClassName, Object vendorObj, boolean remote, boolean isApplet, ClassLoader classLoader) {
      Class wrapperClass = this.getWrapperClass(superClassName, vendorObj, remote, isApplet, classLoader);
      return this.createWrapper(wrapperClass, vendorObj, remote);
   }

   public final Class getWrapperClass(String superClassName, Object vendorObj, boolean remote, ClassLoader classLoader) {
      return this.getWrapperClass(superClassName, vendorObj, remote, false, classLoader);
   }

   public final Class getWrapperClass(String superClassName, Object vendorObj, boolean remote, boolean isApplet, ClassLoader classLoader) {
      Class wrapperClass = this.getCachedWrapperClass(superClassName, vendorObj, classLoader);
      if (wrapperClass == null) {
         if (isApplet) {
            try {
               wrapperClass = Class.forName(this.getWrapperClassName(superClassName, vendorObj.getClass().getName()));
            } catch (ClassNotFoundException var12) {
               throw new AssertionError(var12);
            }
         } else {
            Class superClass = null;
            Class vendorClass = vendorObj.getClass();

            try {
               if (classLoader != null) {
                  superClass = Class.forName(superClassName, true, classLoader);
               } else {
                  superClass = Class.forName(superClassName);
               }
            } catch (ClassNotFoundException var11) {
               throw new AssertionError(var11);
            }

            ClassLoader cl = null;
            if (classLoader != null) {
               cl = classLoader;
            } else {
               cl = vendorClass.getClassLoader();
            }

            if (cl == null) {
               cl = this.getClass().getClassLoader();
            }

            try {
               wrapperClass = cl.loadClass(this.getWrapperClassName(superClassName, vendorObj.getClass().getName()));
            } catch (ClassNotFoundException var13) {
               if (!remote) {
                  wrapperClass = this.generateWrapperClass(superClass, vendorClass, cl, vendorObj);
               } else {
                  wrapperClass = this.generateRemoteClass(superClass, vendorClass, cl, vendorObj);
               }
            }
         }

         if (wrapperClass != null) {
            this.putCachedWrapperClass(superClassName, vendorObj, wrapperClass, classLoader);
         }
      }

      return wrapperClass;
   }

   private final String getWrapperClassName(String superClassName, String vendorClassName) {
      return superClassName + "_" + vendorClassName.replace('.', '_');
   }

   private final Method[] getGeneratedMethods(Class superClass, Class vendorClass) {
      Method[] vendorMethods = vendorClass.getMethods();
      ArrayList passthroughMethods = new ArrayList(vendorMethods.length);

      int i;
      for(int i = 0; i < vendorMethods.length; ++i) {
         i = vendorMethods[i].getModifiers();
         if (!Modifier.isAbstract(i) && Modifier.isPublic(i) && !vendorMethods[i].getName().equals("finalize")) {
            try {
               superClass.getMethod(vendorMethods[i].getName(), vendorMethods[i].getParameterTypes());
            } catch (NoSuchMethodException var8) {
               passthroughMethods.add(vendorMethods[i]);
            }
         }
      }

      if (this.debug) {
         System.out.println("The following are generated methods...");
         System.out.println("--------------------------------------");
         Method[] x = (Method[])((Method[])passthroughMethods.toArray(new Method[passthroughMethods.size()]));

         for(i = 0; i < x.length; ++i) {
            System.out.println(x[i]);
         }

         System.out.println("--------------------------------------");
         System.out.println("The above are generated methods...");
      }

      return (Method[])((Method[])passthroughMethods.toArray(new Method[passthroughMethods.size()]));
   }

   public final Class generateWrapperClass(Class superClass, Class vendorClass, ClassLoader classLoader) {
      return this.generateWrapperClass(superClass, vendorClass, classLoader, (Object)null);
   }

   public final Class generateWrapperClass(Class superClass, Class vendorClass, ClassLoader classLoader, Object vendorObj) {
      String vendorClassName = vendorClass.getName();
      String superClassName = superClass.getName();
      String wrapperClassName = this.getWrapperClassName(superClassName, vendorClassName);
      WrapperClassFile cf = new WrapperClassFile(this);
      cf.setClassName(wrapperClassName);
      cf.setSuperClassName(superClassName);
      cf.setup(superClass);
      cf.addAllInterfaces(superClass);
      cf.addAllInterfaces(vendorClass);
      cf.addMethods(this.getGeneratedMethods(superClass, vendorClass), vendorObj);
      this.customerizeWrapperClass(cf, superClass, vendorClass, false);
      if (this.debug) {
         try {
            cf.dump();
         } catch (BadBytecodesException var10) {
         }
      }

      return cf.generateClass(classLoader);
   }

   public final Class generateRemoteClass(Class superClass, Class vendorClass, ClassLoader classLoader) {
      return this.generateRemoteClass(superClass, vendorClass, classLoader, (Object)null);
   }

   public final Class generateRemoteClass(Class superClass, Class vendorClass, ClassLoader classLoader, Object vendorObj) {
      String vendorClassName = vendorClass.getName();
      String superClassName = superClass.getName();
      String wrapperClassName = this.getWrapperClassName(superClassName, vendorClassName);
      WrapperClassFile cf = new WrapperClassFile(this);
      cf.setClassName(wrapperClassName);
      cf.setSuperClassName(superClassName);
      cf.setup(superClass);
      cf.addMethods(this.getGeneratedMethods(superClass, vendorClass), vendorObj);
      Class remoteInterface = this.generateRemoteInterface(superClass, vendorClass, classLoader);
      cf.addInterface(remoteInterface.getName());
      this.customerizeWrapperClass(cf, superClass, vendorClass, true);
      if (this.debug) {
         try {
            cf.dump();
         } catch (BadBytecodesException var11) {
         }
      }

      return cf.generateClass(classLoader);
   }

   private final Class generateRemoteInterface(Class superClass, Class vendorClass, ClassLoader classLoader) {
      String vendorClassName = vendorClass.getName();
      String superClassName = superClass.getName();
      String wrapperClassName = this.getRemoteInterfaceName(superClassName, vendorClassName);
      WrapperClassFile cf = new WrapperClassFile(this);
      cf.setClassName(wrapperClassName);
      cf.setAccessFlags(513);
      cf.setSuperClassName("java.lang.Object");
      cf.addAllInterfaces(vendorClass);
      cf.addInterface("java.rmi.Remote");
      this.customerizeWrapperClass(cf, superClass, vendorClass, false);
      if (this.debug) {
         try {
            cf.dump();
         } catch (BadBytecodesException var9) {
         }
      }

      return cf.generateClass(classLoader);
   }

   private final String getRemoteInterfaceName(String superClassName, String vendorClassName) {
      return superClassName + "_" + vendorClassName.replace('.', '_') + "_RemoteInterface";
   }
}
