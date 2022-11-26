package weblogic.coherence.descriptor;

import java.io.File;
import javax.management.MBeanServer;
import javax.management.remote.JMXServiceURL;

public class CoherenceOverrideBean {
   private boolean localStorageEnabled;
   private String unicastListenAddress;
   private int unicastListenPort;
   private int clusterListenPort;
   private Boolean autoAdjust;
   private Integer autoAdjustAttempts;
   private String siteName;
   private String rackName;
   private String memberName;
   private String machineName;
   private String roleName;
   private String logDestination;
   private File customConfigFile;
   private CoherenceSecurityBean securityBean;
   private boolean managementProxy;
   private boolean managementProxyDefined;
   private MBeanServer mbeanServer;
   private JMXServiceURL jmxServiceUrl;
   private String[] clusterHosts;

   public boolean isLocalStorageEnabled() {
      return this.localStorageEnabled;
   }

   public void setLocalStorageEnabled(boolean localStorageEnabled) {
      this.localStorageEnabled = localStorageEnabled;
   }

   public String getUnicastListenAddress() {
      return this.unicastListenAddress;
   }

   public void setUnicastListenAddress(String unicastListenAddress) {
      this.unicastListenAddress = unicastListenAddress;
   }

   public int getUnicastListenPort() {
      return this.unicastListenPort;
   }

   public void setUnicastListenPort(int unicastListenPort) {
      this.unicastListenPort = unicastListenPort;
   }

   /** @deprecated */
   @Deprecated
   public Boolean isUnicastListenPortAutoAdjust() {
      return this.autoAdjust;
   }

   /** @deprecated */
   @Deprecated
   public void setUnicastListenPortAutoAdjust(boolean autoAdjust) {
      this.autoAdjust = autoAdjust;
   }

   public Integer getUnicastListenPortAutoAdjustAttempts() {
      return this.autoAdjustAttempts;
   }

   public void setUnicastListenPortAutoAdjustAttempts(int autoAdjustAttempts) {
      this.autoAdjustAttempts = autoAdjustAttempts;
   }

   public String getSiteName() {
      return this.siteName;
   }

   public void setSiteName(String siteName) {
      this.siteName = siteName;
   }

   public String getRackName() {
      return this.rackName;
   }

   public void setRackName(String rackName) {
      this.rackName = rackName;
   }

   public String getMemberName() {
      return this.memberName;
   }

   public void setMemberName(String memberName) {
      this.memberName = memberName;
   }

   public String getMachineName() {
      return this.machineName;
   }

   public void setMachineName(String machineName) {
      this.machineName = machineName;
   }

   public String getRoleName() {
      return this.roleName;
   }

   public void setRoleName(String roleName) {
      this.roleName = roleName;
   }

   public String getLogDestination() {
      return this.logDestination;
   }

   public void setLogDestination(String logDestination) {
      this.logDestination = logDestination;
   }

   public File getCustomConfigFile() {
      return this.customConfigFile;
   }

   public void setCustomConfigFile(File customConfigFile) {
      this.customConfigFile = customConfigFile;
   }

   public CoherenceSecurityBean getSecurityBean() {
      return this.securityBean;
   }

   public void setSecurityBean(CoherenceSecurityBean securityBean) {
      this.securityBean = securityBean;
   }

   public boolean isManagementProxy() {
      return this.managementProxy;
   }

   public void setManagementProxy(boolean manage) {
      this.managementProxy = manage;
   }

   public boolean isManagementProxyDefined() {
      return this.managementProxyDefined;
   }

   public void setManagementProxyDefined(boolean manageDefined) {
      this.managementProxyDefined = manageDefined;
   }

   public MBeanServer getRuntimeMBeanServer() {
      return this.mbeanServer;
   }

   public void setRuntimeMBeanServer(MBeanServer mbeanServer) {
      this.mbeanServer = mbeanServer;
   }

   public JMXServiceURL getJmxServiceUrl() {
      return this.jmxServiceUrl;
   }

   public void setJmxServiceUrl(JMXServiceURL url) {
      this.jmxServiceUrl = url;
   }

   public int getClusterListenPort() {
      return this.clusterListenPort;
   }

   public void setClusterListenPort(int clusterListenPort) {
      this.clusterListenPort = clusterListenPort;
   }

   public String[] getClusterHosts() {
      return this.clusterHosts;
   }

   public void setClusterHosts(String[] clusterHosts) {
      this.clusterHosts = clusterHosts;
   }
}
