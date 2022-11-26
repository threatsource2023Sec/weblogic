package org.apache.openjpa.slice;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.PluginValue;
import org.apache.openjpa.lib.util.Localizer;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.UserException;

public class ExecutorServiceValue extends PluginValue {
   private static List known = Arrays.asList("cached", "fixed");
   private static Localizer _loc = Localizer.forPackage(ExecutorServiceValue.class);

   public ExecutorServiceValue() {
      super("ThreadingPolicy", true);
      this.setDefault("cached");
   }

   public void setProperties(String props) {
      super.setProperties(props);
   }

   public Object instantiate(Class type, Configuration conf, boolean fatal) {
      Object obj = null;
      int defaultSize = 10;
      String cls = this.getClassName();
      if (!known.contains(cls)) {
         cls = "cached";
      }

      Options opts = Configurations.parseProperties(this.getProperties());
      ThreadFactory factory = null;
      if (opts.containsKey("ThreadFactory")) {
         String fName = opts.getProperty("ThreadFactory");

         try {
            factory = (ThreadFactory)Class.forName(fName).newInstance();
            Configurations.configureInstance(factory, conf, (Properties)opts, this.getProperty());
         } catch (Throwable var14) {
            throw new UserException(_loc.get("bad-thread-factory", (Object)fName), var14);
         } finally {
            opts.removeProperty("ThreadFactory");
         }
      } else {
         factory = Executors.defaultThreadFactory();
      }

      if ("cached".equals(cls)) {
         obj = Executors.newCachedThreadPool(factory);
      } else if ("fixed".equals(cls)) {
         long keepAliveTime = 60L;
         if (opts.containsKey("KeepAliveTime")) {
            keepAliveTime = opts.getLongProperty("KeepAliveTime");
            opts.removeLongProperty("KeepAliveTime");
         }

         obj = new ThreadPoolExecutor(defaultSize, defaultSize, keepAliveTime, TimeUnit.SECONDS, new SynchronousQueue(), factory);
         Configurations.configureInstance(obj, conf, (Properties)opts, this.getProperty());
      }

      this.set(obj, true);
      return obj;
   }
}
