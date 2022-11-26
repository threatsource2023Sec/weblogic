package kodo.manage;

import com.solarmetric.manage.jmx.MBeanProvider;
import javax.management.MBeanServer;
import kodo.profile.KodoProfilingAgent;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.util.Closeable;

public interface ManagementConfiguration extends Configurable, Closeable {
   Management getManagement();

   MBeanServer getMBeanServer();

   void initManagement(MBeanServer var1) throws Exception;

   void closeManagement();

   MBeanProvider[] getMBeanPlugins();

   KodoProfilingAgent getProfilingAgent();

   void initProfiling(KodoProfilingAgent var1);

   void closeProfiling();
}
