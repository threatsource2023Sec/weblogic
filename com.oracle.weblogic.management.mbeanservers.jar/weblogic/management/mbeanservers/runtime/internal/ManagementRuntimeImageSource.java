package weblogic.management.mbeanservers.runtime.internal;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Set;
import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanAttributeInfo;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.QueryExp;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorManager;
import weblogic.diagnostics.image.ImageSource;
import weblogic.diagnostics.image.ImageSourceCreationException;
import weblogic.diagnostics.image.descriptor.InstanceMetricBean;
import weblogic.diagnostics.image.descriptor.JMXDomainStatisticsBean;
import weblogic.diagnostics.image.descriptor.ManagementRuntimeImageBean;
import weblogic.diagnostics.image.descriptor.MetricBean;
import weblogic.diagnostics.image.descriptor.ServerRuntimeStateBean;
import weblogic.diagnostics.image.descriptor.ServerRuntimeStatisticsBean;
import weblogic.management.jmx.mbeanserver.WLSMBeanServer;

public class ManagementRuntimeImageSource implements ImageSource {
   private static final String[][] METRICS_TABLE = new String[][]{{"com.bea:Type=WorkManagerRuntime,*", "StuckThreadCount"}, {"com.bea:Type=JRockitRuntime,*", "HeapFreePercent", "JvmProcessorLoad"}, {"com.bea:Type=JVMRuntime,*", "HeapFreePercent"}, {"com.bea:Type=ThreadPoolRuntime,*", "HoggingThreadCount", "PendingUserRequestCount", "Throughput"}, {"com.bea:Type=JDBCDataSourceRuntime,*", "LeakedConnectionCount", "ActiveConnectionsCurrentCount", "ConnectionDelayTime", "LeakedConnectionCount", "NumAvailable", "ReserveRequestCount"}, {"com.bea:Type=JTARuntime,*", "ActiveTransactionsTotalCount", "SecondsActiveTotalCount"}, {"com.bea:Type=LogBroadcasterRuntime,*", "MessagesLogged"}, {"com.bea:Type=ServerRuntime,*", "OpenSocketsCurrentCount", "State"}, {"com.bea:Type=WebAppComponentRuntime,*", "OpenSessionsCurrentCount"}};
   private WLSMBeanServer mbs;
   private boolean timedOut;

   public ManagementRuntimeImageSource(WLSMBeanServer mbeanServer) {
      this.mbs = mbeanServer;
   }

   public void createDiagnosticImage(OutputStream out) throws ImageSourceCreationException {
      try {
         DescriptorManager dm = new DescriptorManager();
         Descriptor desc = dm.createDescriptorRoot(ManagementRuntimeImageBean.class);
         ManagementRuntimeImageBean root = (ManagementRuntimeImageBean)desc.getRootBean();
         ServerRuntimeStatisticsBean mBeanServerStatistics = root.getServerRuntimeStatistics();
         mBeanServerStatistics.setTotalRegisteredMBeansCount(this.mbs.getMBeanCount());
         String[] domains = this.mbs.getDomains();
         String[] var7 = domains;
         int var8 = domains.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            String domain = var7[var9];
            if (this.timedOut) {
               break;
            }

            Set domainSet = this.mbs.queryMBeans(new ObjectName(domain + ":*"), (QueryExp)null);
            JMXDomainStatisticsBean domainStatistics = mBeanServerStatistics.createJMXDomainStatistic();
            domainStatistics.setDomainName(domain);
            domainStatistics.setCount(domainSet.size());
         }

         this.populateMBeanStats(root);
         this.writeImageSourceBean(out, dm, desc);
      } catch (Exception var13) {
         throw new ImageSourceCreationException(var13);
      }
   }

   private void writeImageSourceBean(OutputStream out, DescriptorManager dm, Descriptor desc) throws ImageSourceCreationException {
      if (!this.timedOut) {
         try {
            dm.writeDescriptorAsXML(desc, out);
         } catch (IOException var5) {
            throw new ImageSourceCreationException(var5);
         }
      }
   }

   private void populateMBeanStats(ManagementRuntimeImageBean root) throws Exception {
      if (!this.timedOut) {
         ServerRuntimeStateBean state = root.getServerRuntimeState();
         String[][] var3 = METRICS_TABLE;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String[] metricSet = var3[var5];
            if (this.timedOut) {
               break;
            }

            this.queryValues(state, metricSet);
         }

      }
   }

   public void timeoutImageCreation() {
      this.timedOut = true;
   }

   private void queryValues(ServerRuntimeStateBean state, String[] metricSet) throws Exception {
      ObjectName pattern = new ObjectName(metricSet[0]);
      Set mBeans = this.mbs.queryMBeans(pattern, (QueryExp)null);
      Iterator var5 = mBeans.iterator();

      while(var5.hasNext()) {
         Object instance = var5.next();
         if (this.timedOut) {
            break;
         }

         ObjectInstance oi = (ObjectInstance)instance;
         InstanceMetricBean instanceMetric = state.createInstanceMetric();
         instanceMetric.setInstanceName(oi.getObjectName().getCanonicalName());
         String[] attrList = this.copyAttributes(metricSet);
         AttributeList attributeList = this.mbs.getAttributes(oi.getObjectName(), attrList);
         Iterator var11 = attributeList.iterator();

         while(var11.hasNext()) {
            Object obj = var11.next();
            Attribute attr = (Attribute)obj;
            String attrType = this.getAttributeType(oi.getObjectName(), attr.getName());
            MetricBean metric = instanceMetric.createMBeanMetric();
            metric.setAttributeName(attr.getName());
            metric.setAttributeType(attrType);
            metric.setAttributeValue(attr.getValue().toString());
         }
      }

   }

   private String[] copyAttributes(String[] metricSet) {
      String[] attrsArray = new String[metricSet.length - 1];
      System.arraycopy(metricSet, 1, attrsArray, 0, Math.min(metricSet.length - 1, attrsArray.length));
      return attrsArray;
   }

   private String getAttributeType(ObjectName objectName, String name) throws Exception {
      MBeanAttributeInfo[] attributes = this.mbs.getMBeanInfo(objectName).getAttributes();
      MBeanAttributeInfo[] var4 = attributes;
      int var5 = attributes.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         MBeanAttributeInfo info = var4[var6];
         if (info.getName().equals(name)) {
            return info.getType();
         }
      }

      return String.class.getName();
   }
}
