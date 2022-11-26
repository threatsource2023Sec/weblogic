package weblogic.kernel;

import java.util.Properties;
import weblogic.management.configuration.DebugScopeMBean;
import weblogic.management.configuration.KernelDebugMBean;

final class KernelDebugMBeanStub extends MBeanStub implements KernelDebugMBean {
   private boolean abbreviation;
   private boolean connection;
   private boolean messaging;
   private boolean routing;
   private boolean localRemoteJVM;
   private boolean loadbalancing;
   private boolean failover;
   private boolean forceGCeachDGC;
   private boolean DGCEnrollment;
   private boolean DGCStatistics;
   private boolean ssl;
   private boolean rc4;
   private boolean rsa;
   private boolean iiop;
   private boolean muxer;
   private boolean muxerDetail;
   private boolean muxerTimeout;
   private boolean muxerConn;
   private boolean muxerException;
   private boolean workContext;
   private Properties debugParameters;
   private boolean iiopTransport;
   private boolean iiopMarshal;
   private boolean iiopSecurity;
   private boolean iiopOTS;
   private boolean iiopReplacer;
   private boolean iiopConnection;
   private boolean iiopStartup;
   private boolean selfTuning;

   KernelDebugMBeanStub() {
      this.initializeFromSystemProperties("weblogic.debug.");
   }

   public boolean getDebugAbbreviation() {
      return this.abbreviation;
   }

   public void setDebugAbbreviation(boolean enable) {
      this.abbreviation = enable;
   }

   public boolean getDebugConnection() {
      return this.connection;
   }

   public void setDebugConnection(boolean enable) {
      this.connection = enable;
   }

   public boolean getDebugMessaging() {
      return this.messaging;
   }

   public void setDebugMessaging(boolean enable) {
      this.messaging = enable;
   }

   public boolean getDebugRouting() {
      return this.routing;
   }

   public void setDebugRouting(boolean enable) {
      this.routing = enable;
   }

   public boolean getDebugLocalRemoteJVM() {
      return this.localRemoteJVM;
   }

   public void setDebugLocalRemoteJVM(boolean enable) {
      this.localRemoteJVM = enable;
   }

   public boolean getDebugWorkContext() {
      return this.workContext;
   }

   public void setDebugWorkContext(boolean enable) {
      this.workContext = enable;
   }

   public boolean getDebugLoadBalancing() {
      return this.loadbalancing;
   }

   public void setDebugLoadBalancing(boolean enable) {
      this.loadbalancing = enable;
   }

   public boolean getDebugFailOver() {
      return this.failover;
   }

   public void setDebugFailOver(boolean enable) {
      this.failover = enable;
   }

   public boolean getForceGCEachDGCPeriod() {
      return this.forceGCeachDGC;
   }

   public void setForceGCEachDGCPeriod() {
   }

   public void setForceGCEachDGCPeriod(boolean enable) {
      this.forceGCeachDGC = enable;
   }

   public boolean getDebugDGCEnrollment() {
      return this.DGCEnrollment;
   }

   public void setDebugDGCEnrollment() {
   }

   public void setDebugDGCEnrollment(boolean enable) {
      this.DGCEnrollment = enable;
   }

   public boolean getLogDGCStatistics() {
      return this.DGCStatistics;
   }

   public void setLogDGCStatistics(boolean enable) {
      this.DGCStatistics = enable;
   }

   public boolean getDebugSSL() {
      return this.ssl;
   }

   public void setDebugSSL(boolean enable) {
      this.ssl = enable;
   }

   public boolean getDebugRC4() {
      return this.rc4;
   }

   public void setDebugRC4(boolean enable) {
      this.rc4 = enable;
   }

   public boolean getDebugRSA() {
      return this.rsa;
   }

   public void setDebugRSA(boolean enable) {
      this.rsa = enable;
   }

   public boolean getDebugIIOP() {
      return this.iiop;
   }

   public void setDebugIIOP(boolean enable) {
      this.iiop = enable;
   }

   public boolean getDebugMuxer() {
      return this.muxer;
   }

   public void setDebugMuxer(boolean enable) {
      this.muxer = enable;
   }

   public boolean getDebugMuxerTimeout() {
      return this.muxerTimeout;
   }

   public void setDebugMuxerTimeout(boolean enable) {
      this.muxerTimeout = enable;
   }

   public boolean getDebugMuxerDetail() {
      return this.muxerDetail;
   }

   public void setDebugMuxerDetail(boolean enable) {
      this.muxerDetail = enable;
   }

   public boolean getDebugMuxerConnection() {
      return this.muxerConn;
   }

   public void setDebugMuxerConnection(boolean enable) {
      this.muxerConn = enable;
   }

   public boolean getDebugMuxerException() {
      return this.muxerException;
   }

   public void setDebugMuxerException(boolean enable) {
      this.muxerException = enable;
   }

   public boolean getDebugIIOPTransport() {
      return this.iiopTransport;
   }

   public void setDebugIIOPTransport(boolean enable) {
      this.iiopTransport = enable;
   }

   public boolean getDebugIIOPMarshal() {
      return this.iiopMarshal;
   }

   public void setDebugIIOPMarshal(boolean enable) {
      this.iiopMarshal = enable;
   }

   public boolean getDebugIIOPSecurity() {
      return this.iiopSecurity;
   }

   public void setDebugIIOPSecurity(boolean enable) {
      this.iiopSecurity = enable;
   }

   public boolean getDebugIIOPOTS() {
      return this.iiopOTS;
   }

   public void setDebugIIOPOTS(boolean enable) {
      this.iiopOTS = enable;
   }

   public boolean getDebugIIOPReplacer() {
      return this.iiopReplacer;
   }

   public void setDebugIIOPReplacer(boolean enable) {
      this.iiopReplacer = enable;
   }

   public boolean getDebugIIOPConnection() {
      return this.iiopConnection;
   }

   public void setDebugIIOPConnection(boolean enable) {
      this.iiopConnection = enable;
   }

   public boolean getDebugIIOPStartup() {
      return this.iiopStartup;
   }

   public void setDebugIIOPStartup(boolean enable) {
      this.iiopStartup = enable;
   }

   public boolean getDebugSelfTuning() {
      return this.selfTuning;
   }

   public void setDebugSelfTuning(boolean enable) {
      this.selfTuning = enable;
   }

   public DebugScopeMBean[] getDebugScopes() {
      return null;
   }

   public DebugScopeMBean createDebugScope(String name) {
      return null;
   }

   public void destroyDebugScope(DebugScopeMBean debugScopeMBean) {
   }

   public DebugScopeMBean lookupDebugScope(String name) {
      return null;
   }

   public Properties getDebugParameters() {
      return this.debugParameters;
   }

   public void setDebugParameters(Properties params) {
      this.debugParameters = params;
   }
}
