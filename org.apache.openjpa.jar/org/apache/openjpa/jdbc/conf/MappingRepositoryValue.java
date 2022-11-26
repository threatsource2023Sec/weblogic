package org.apache.openjpa.jdbc.conf;

import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.util.J2DoPrivHelper;
import serp.util.Strings;

public class MappingRepositoryValue extends PluginValue {
   public MappingRepositoryValue(String prop) {
      super(prop, true);
   }

   public Object newInstance(String clsName, Class type, Configuration conf, boolean fatal) {
      try {
         Class cls = Strings.toClass(clsName, (ClassLoader)AccessController.doPrivileged(J2DoPrivHelper.getClassLoaderAction(type)));
         return cls.getConstructor(JDBCConfiguration.class).newInstance(conf);
      } catch (RuntimeException var6) {
         throw var6;
      } catch (InvocationTargetException var7) {
         if (var7.getTargetException() instanceof RuntimeException) {
            throw (RuntimeException)var7.getTargetException();
         } else {
            return super.newInstance(clsName, type, conf, fatal);
         }
      } catch (Exception var8) {
         return super.newInstance(clsName, type, conf, fatal);
      }
   }
}
