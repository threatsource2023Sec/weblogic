package weblogic.management.mbeans.custom;

import com.tangosol.run.xml.SimpleParser;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import weblogic.cacheprovider.coherence.CoherenceClusterDescriptorHelper;
import weblogic.coherence.descriptor.wl.CoherenceFederationParamsBean;
import weblogic.coherence.descriptor.wl.CoherencePersistenceParamsBean;
import weblogic.coherence.descriptor.wl.WeblogicCoherenceBean;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorManager;
import weblogic.management.DomainDir;
import weblogic.management.ManagementException;
import weblogic.management.configuration.ClusterMBean;
import weblogic.management.configuration.CoherenceClusterSystemResourceMBean;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.MachineMBean;
import weblogic.management.configuration.NetworkAccessPointMBean;
import weblogic.management.configuration.NodeManagerMBean;
import weblogic.management.configuration.ServerMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.internal.PendingDirectoryManager;
import weblogic.management.provider.custom.ConfigurationMBeanCustomized;
import weblogic.management.provider.internal.DescriptorInfoUtils;
import weblogic.utils.FileUtils;
import weblogic.utils.io.StreamUtils;

public class CoherenceClusterSystemResource extends ConfigurationExtension {
   private String _DescriptorFileName;
   private String _customConfigFile;
   private CoherenceClusterSystemResourceMBean mBean;
   private static final String DEFAULT_APPENDIX = "-coherence.xml";
   private static final String DEFAULT_CUSTOM_CONFIG_FILE_NAME = "tangosol-coherence-override.xml";

   public CoherenceClusterSystemResource(ConfigurationMBeanCustomized customized) {
      super(customized);
      this.mBean = (CoherenceClusterSystemResourceMBean)customized;
   }

   public static String constructDefaultCoherenceSystemFilename(String name) {
      name = name.trim();
      if (name.endsWith("-coherence")) {
         name = name.substring(0, name.length() - 10);
      }

      String fixedName = FileUtils.mapNameToFileName(name);
      return "coherence" + File.separator + fixedName + File.separator + fixedName + "-coherence.xml";
   }

   public String getDescriptorFileName() {
      return this._DescriptorFileName;
   }

   public void setDescriptorFileName(String descriptorFileName) {
      String prefix = "coherence" + File.separator;
      if (descriptorFileName != null && this.isEdit() && !(new File(descriptorFileName)).getPath().startsWith((new File(prefix)).getPath())) {
         this._DescriptorFileName = prefix + descriptorFileName;
      } else {
         this._DescriptorFileName = descriptorFileName;
      }

   }

   public DescriptorBean getResource() {
      return (DescriptorBean)this.getCoherenceClusterResource();
   }

   public WeblogicCoherenceBean getCoherenceClusterResource() {
      WeblogicCoherenceBean bean = (WeblogicCoherenceBean)this.getExtensionRoot(WeblogicCoherenceBean.class, "CoherenceClusterResource", "coherence");
      if (bean != null && this.getMbean() != null && bean.getName() == null) {
         bean.setName(this.getMbean().getName());
      }

      return bean;
   }

   public String getCustomClusterConfigurationFileName() {
      WeblogicCoherenceBean bean = this.getCoherenceClusterResource();
      return bean != null ? bean.getCustomClusterConfigurationFileName() : null;
   }

   public long getCustomConfigFileLastUpdatedTime() {
      WeblogicCoherenceBean bean = this.getCoherenceClusterResource();
      return bean != null ? bean.getCustomClusterConfigurationFileLastUpdatedTimestamp() : 0L;
   }

   public boolean isUsingCustomClusterConfigurationFile() {
      return this.getCustomClusterConfigurationFileName() != null;
   }

   public void importCustomClusterConfigurationFile(String file) throws ManagementException {
      try {
         File fromFile = new File(file);
         if (!fromFile.exists()) {
            throw new ManagementException("File " + fromFile + " does not exist, please check that an accurate, full file path is specified.");
         } else if (fromFile.isFile() && fromFile.canRead()) {
            (new SimpleParser()).parseXml(new FileInputStream(fromFile));
            String customFile = fromFile.getCanonicalPath();
            String relativeName = "coherence" + File.separator + this.getName() + File.separator + fromFile.getName();
            PendingDirectoryManager mgr = PendingDirectoryManager.getInstance();
            FileInputStream in = new FileInputStream(fromFile);
            OutputStream oos = null;

            try {
               in = new FileInputStream(fromFile);
               oos = mgr.getFileOutputStream(relativeName);
               StreamUtils.writeTo(in, oos);
            } finally {
               if (in != null) {
                  in.close();
               }

               if (oos != null) {
                  oos.close();
               }

            }

            this.updateCustomConfigurationFileDetails(relativeName);
            this._customConfigFile = customFile;
            this.mBean.setUsingCustomClusterConfigurationFile(true);
         } else {
            throw new ManagementException("Unable to read from " + fromFile);
         }
      } catch (IOException var12) {
         throw new ManagementException("Failed to import custom cluster configuration file for " + this.getName(), var12);
      }
   }

   public void setUsingCustomClusterConfigurationFile(boolean useCustomFile) throws ManagementException {
      if (!useCustomFile && this._customConfigFile != null) {
         this.updateCustomConfigurationFileDetails((String)null);
         this._customConfigFile = null;
      }

   }

   private void updateCustomConfigurationFileDetails(String srcFile) {
      WeblogicCoherenceBean bean = this.getCoherenceClusterResource();
      if (srcFile != null) {
         bean.setCustomClusterConfigurationFileName(srcFile);
         bean.setCustomClusterConfigurationFileLastUpdatedTimestamp(System.currentTimeMillis());
      } else {
         bean.unSet("CustomClusterConfigurationFileName");
         bean.unSet("CustomClusterConfigurationFileLastUpdatedTimestamp");
      }

   }

   public void _postCreate() {
      this.getCoherenceClusterResource();
      this._customConfigFile = this.getCustomClusterConfigurationFileName();
   }

   public void _preDestroy() {
      DescriptorBean bean = this.getMbean();
      WeblogicCoherenceBean resourceBean = this.getCoherenceClusterResource();
      DescriptorInfoUtils.removeDescriptorInfo((DescriptorBean)resourceBean, bean.getDescriptor());
      File descriptorFile;
      File parent;
      if (this.isUsingCustomClusterConfigurationFile()) {
         descriptorFile = new File(resourceBean.getCustomClusterConfigurationFileName());
         parent = new File(DomainDir.getConfigDir() + File.separator + "coherence" + File.separator + resourceBean.getName() + File.separator + descriptorFile.getName());
         if (parent.exists()) {
            parent.delete();
         }
      }

      descriptorFile = new File(DomainDir.getConfigDir() + File.separator + this.getDescriptorFileName());
      parent = descriptorFile.getParentFile();
      String[] list = parent.list();
      if (list == null || list.length == 0) {
         parent.delete();
      }

   }

   protected Descriptor loadDescriptor(DescriptorManager dm, InputStream in, List holder) throws Exception {
      return ((DescriptorBean)CoherenceClusterDescriptorHelper.createCoherenceDescriptor(in, dm, holder, true)).getDescriptor();
   }

   public TargetMBean[] getTargets() {
      List targets = new ArrayList();
      Object parentMBean = this.getMbean().getParent();
      if (parentMBean != null && parentMBean instanceof DomainMBean) {
         DomainMBean domain = (DomainMBean)parentMBean;
         ServerMBean[] servers = domain.getServers();
         String clusterName = this.getMbean().getName();

         for(int i = 0; i < servers.length; ++i) {
            ServerMBean server = servers[i];
            ClusterMBean cluster = server.getCluster();
            CoherenceClusterSystemResourceMBean cohCluster = cluster != null && cluster.getCoherenceClusterSystemResource() != null ? cluster.getCoherenceClusterSystemResource() : server.getCoherenceClusterSystemResource();
            if (cohCluster != null && cohCluster.getName().equals(this.getMbean().getName())) {
               targets.add(server);
            }
         }

         return (TargetMBean[])targets.toArray(new TargetMBean[0]);
      } else {
         return (TargetMBean[])targets.toArray(new TargetMBean[0]);
      }
   }

   public String[] getClusterHosts() {
      Object parentMBean = this.getMbean().getParent();
      DomainMBean domain = (DomainMBean)parentMBean;
      ServerMBean[] servers = domain.getServers();
      List listHosts = new ArrayList();
      ServerMBean[] var5 = servers;
      int var6 = servers.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         ServerMBean server = var5[var7];
         String hostName = this.deriveUnicastHostName(server);
         if (hostName != null && !hostName.isEmpty() && !listHosts.contains(hostName)) {
            listHosts.add(hostName);
         }
      }

      return (String[])listHosts.toArray(new String[0]);
   }

   private String deriveUnicastHostName(ServerMBean serverMBean) {
      String hostName = null;
      if (serverMBean != null) {
         hostName = serverMBean.getCoherenceMemberConfig() == null ? null : serverMBean.getCoherenceMemberConfig().getUnicastListenAddress();
         if (hostName == null || hostName.isEmpty()) {
            NetworkAccessPointMBean[] accessPoints = serverMBean.getNetworkAccessPoints();
            if (accessPoints != null) {
               NetworkAccessPointMBean[] var4 = accessPoints;
               int var5 = accessPoints.length;

               for(int var6 = 0; var6 < var5; ++var6) {
                  NetworkAccessPointMBean accessPoint = var4[var6];
                  if (accessPoint.isEnabled() && !accessPoint.isSDPEnabled()) {
                     hostName = accessPoint.getListenAddress();
                     break;
                  }
               }
            }
         }

         hostName = hostName != null && !hostName.isEmpty() ? hostName : serverMBean.getListenAddress();
      }

      if (hostName == null || hostName.isEmpty()) {
         MachineMBean machine = serverMBean == null ? null : serverMBean.getMachine();
         NodeManagerMBean nodeMgr = machine == null ? null : machine.getNodeManager();
         hostName = nodeMgr == null ? null : nodeMgr.getListenAddress();
      }

      return hostName;
   }

   public String getPersistenceDefaultMode() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherencePersistenceParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherencePersistenceParams();
      return bean != null ? bean.getDefaultPersistenceMode() : null;
   }

   public String getPersistenceActiveDirectory() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherencePersistenceParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherencePersistenceParams();
      return bean != null ? bean.getActiveDirectory() : null;
   }

   public String getPersistenceSnapshotDirectory() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherencePersistenceParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherencePersistenceParams();
      return bean != null ? bean.getSnapshotDirectory() : null;
   }

   public String getPersistenceTrashDirectory() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherencePersistenceParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherencePersistenceParams();
      return bean != null ? bean.getTrashDirectory() : null;
   }

   public String getFederationTopology() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherenceFederationParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherenceFederationParams();
      return bean != null ? bean.getFederationTopology() : null;
   }

   public String[] getFederationRemoteParticipantHosts() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherenceFederationParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherenceFederationParams();
      return bean != null ? bean.getRemoteParticipantHosts() : null;
   }

   public String getFederationRemoteClusterName() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherenceFederationParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherenceFederationParams();
      return bean != null ? bean.getRemoteCoherenceClusterName() : null;
   }

   public int getFederationRemoteClusterListenPort() {
      WeblogicCoherenceBean coherenceClusterResource = this.getCoherenceClusterResource();
      CoherenceFederationParamsBean bean = coherenceClusterResource == null ? null : coherenceClusterResource.getCoherenceFederationParams();
      return bean != null ? bean.getRemoteCoherenceClusterListenPort() : 0;
   }
}
