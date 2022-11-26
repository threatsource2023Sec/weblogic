package kodo.manage;

import com.solarmetric.manage.jmx.MBeanProvider;
import java.util.Collection;
import java.util.Collections;
import javax.management.MBeanServer;
import kodo.profile.KodoProfilingAgent;
import kodo.profile.Profiling;
import kodo.profile.ProfilingValue;
import org.apache.openjpa.conf.OpenJPAConfiguration;
import org.apache.openjpa.lib.conf.Configuration;

public abstract class AbstractManagementConfiguration implements ManagementConfiguration {
   protected OpenJPAConfiguration conf;
   protected Management mgmnt;

   public void setConfiguration(Configuration conf) {
      this.conf = (OpenJPAConfiguration)conf;
      this.mgmnt = new Management(this.conf, this);
   }

   protected final Profiling getProfiling() {
      return ProfilingValue.getProfiling(this.conf);
   }

   public KodoProfilingAgent getProfilingAgent() {
      return this.getProfiling() != null ? this.getProfiling().getProfilingAgent() : null;
   }

   public Management getManagement() {
      return this.mgmnt;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public Collection getPlugins() {
      return (Collection)(this.getProfiling() != null ? this.getProfiling().getPlugins() : Collections.EMPTY_SET);
   }

   public MBeanServer getMBeanServer() {
      return null;
   }

   public void initManagement(MBeanServer mbeanServer) throws Exception {
   }

   public void closeManagement() {
   }

   public MBeanProvider[] getMBeanPlugins() {
      return null;
   }

   public void initProfiling(KodoProfilingAgent agent) {
      if (this.getProfiling() != null) {
         this.getProfiling().initProfiling(agent);
      }

   }

   public void closeProfiling() {
   }

   public void close() {
      this.closeManagement();
      this.closeProfiling();
   }
}
