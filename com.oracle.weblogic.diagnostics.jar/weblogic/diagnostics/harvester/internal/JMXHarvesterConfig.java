package weblogic.diagnostics.harvester.internal;

import com.bea.adaptive.mbean.typing.MBeanCategorizer;
import javax.management.MBeanServerConnection;

class JMXHarvesterConfig {
   private String harvesterName;
   private String namespace;
   private MBeanCategorizer categorizer;
   private boolean polling;
   private MBeanServerConnection mbeanServer;

   public JMXHarvesterConfig(String harvesterName, String namespace, MBeanCategorizer categorizer, boolean polling, MBeanServerConnection mbeanServer) {
      this.harvesterName = harvesterName;
      this.namespace = namespace;
      this.categorizer = categorizer;
      this.polling = polling;
      this.mbeanServer = mbeanServer;
   }

   public String getHarvesterName() {
      return this.harvesterName;
   }

   public void setHarvesterName(String harvesterName) {
      this.harvesterName = harvesterName;
   }

   public String getNamespace() {
      return this.namespace;
   }

   public void setNamespace(String harvesterNamespace) {
      this.namespace = harvesterNamespace;
   }

   public MBeanCategorizer getCategorizer() {
      return this.categorizer;
   }

   public void setCategorizer(MBeanCategorizer categorizer) {
      this.categorizer = categorizer;
   }

   public boolean isPolling() {
      return this.polling;
   }

   public void setPolling(boolean polling) {
      this.polling = polling;
   }

   public MBeanServerConnection getMbeanServer() {
      return this.mbeanServer;
   }

   protected void setMbeanServer(MBeanServerConnection mbeanServer) {
      this.mbeanServer = mbeanServer;
   }
}
