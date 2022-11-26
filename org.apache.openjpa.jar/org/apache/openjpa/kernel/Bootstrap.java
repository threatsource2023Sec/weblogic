package org.apache.openjpa.kernel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import org.apache.openjpa.conf.BrokerFactoryValue;
import org.apache.openjpa.lib.conf.ConfigurationProvider;
import org.apache.openjpa.lib.conf.MapConfigurationProvider;
import org.apache.openjpa.lib.conf.ProductDerivations;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.util.InternalException;
import org.apache.openjpa.util.OpenJPAException;
import org.apache.openjpa.util.UserException;

public class Bootstrap {
   private static final Class[] FACTORY_ARGS = new Class[]{ConfigurationProvider.class};
   private static Localizer s_loc = Localizer.forPackage(Bootstrap.class);

   public static BrokerFactory newBrokerFactory() {
      return newBrokerFactory((ConfigurationProvider)null, (ClassLoader)null);
   }

   public static BrokerFactory newBrokerFactory(ConfigurationProvider conf, ClassLoader loader) {
      try {
         return invokeFactory(conf, loader, "newInstance");
      } catch (InvocationTargetException var4) {
         Throwable cause = var4.getTargetException();
         if (cause instanceof OpenJPAException) {
            throw (OpenJPAException)cause;
         } else {
            throw new InternalException(s_loc.get("new-brokerfactory-excep", (Object)getFactoryClassName(conf, loader)), cause);
         }
      } catch (Exception var5) {
         throw (new UserException(s_loc.get("bad-new-brokerfactory", (Object)getFactoryClassName(conf, loader)), var5)).setFatal(true);
      }
   }

   public static BrokerFactory getBrokerFactory() {
      return getBrokerFactory((ConfigurationProvider)null, (ClassLoader)null);
   }

   public static BrokerFactory getBrokerFactory(ConfigurationProvider conf, ClassLoader loader) {
      try {
         return invokeFactory(conf, loader, "getInstance");
      } catch (InvocationTargetException var4) {
         Throwable cause = var4.getTargetException();
         if (cause instanceof OpenJPAException) {
            throw (OpenJPAException)cause;
         } else {
            throw new InternalException(s_loc.get("brokerfactory-excep", (Object)getFactoryClassName(conf, loader)), cause);
         }
      } catch (Exception var5) {
         throw (new UserException(s_loc.get("bad-brokerfactory", (Object)getFactoryClassName(conf, loader)), var5)).setFatal(true);
      }
   }

   private static BrokerFactory invokeFactory(ConfigurationProvider conf, ClassLoader loader, String methodName) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
      if (conf == null) {
         conf = new MapConfigurationProvider();
      }

      ProductDerivations.beforeConfigurationConstruct((ConfigurationProvider)conf);
      Class cls = getFactoryClass((ConfigurationProvider)conf, loader);

      Method meth;
      try {
         meth = cls.getMethod(methodName, FACTORY_ARGS);
      } catch (NoSuchMethodException var6) {
         cls = getFactoryClass((ConfigurationProvider)conf, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(conf.getClass())));
         meth = cls.getMethod(methodName, FACTORY_ARGS);
      }

      return (BrokerFactory)meth.invoke((Object)null, conf);
   }

   private static String getFactoryClassName(ConfigurationProvider conf, ClassLoader loader) {
      try {
         return getFactoryClass(conf, loader).getName();
      } catch (Exception var3) {
         return "<" + var3.toString() + ">";
      }
   }

   private static Class getFactoryClass(ConfigurationProvider conf, ClassLoader loader) {
      if (loader == null) {
         loader = (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getContextClassLoaderAction());
      }

      Object cls = BrokerFactoryValue.get(conf);
      if (cls instanceof Class) {
         return (Class)cls;
      } else {
         BrokerFactoryValue value = new BrokerFactoryValue();
         value.setString((String)cls);
         String clsName = value.getClassName();
         if (clsName == null) {
            throw (new UserException(s_loc.get("no-brokerfactory", (Object)conf.getProperties()))).setFatal(true);
         } else {
            try {
               return Class.forName(clsName, true, loader);
            } catch (Exception var6) {
               throw (new UserException(s_loc.get("bad-brokerfactory-class", (Object)clsName), var6)).setFatal(true);
            }
         }
      }
   }
}
