package weblogic.kernel;

import weblogic.management.configuration.IIOPMBean;

final class IIOPMBeanStub extends MBeanStub implements IIOPMBean {
   private int defaultGIOPMinorVersion = 2;
   private String iiopLocationForwardPolicy = "failover";
   private boolean useIIOPLocateRequest = false;
   private boolean useFullRepositoyIdList = false;
   private String iiopTxMechanism = "ots";
   private int idleIIOPConnectionTimeout = 240000;
   private String charCodeset = "US-ASCII";
   private String wcharCodeset = "UCS-2";
   public String systemSecurity = "none";
   private boolean statefulAuthentication = true;
   private boolean useSFV2 = false;
   private boolean enableIORServlet = false;
   private boolean useJavaSerialization = false;

   IIOPMBeanStub() {
      this.initializeFromSystemProperties("weblogic.iiop.");
   }

   public int getMaxMessageSize() {
      return 10000000;
   }

   public void setMaxMessageSize(int maxsize) {
   }

   public int getCompleteMessageTimeout() {
      return 60;
   }

   public void setCompleteMessageTimeout(int seconds) {
   }

   public int getDefaultMinorVersion() {
      return this.defaultGIOPMinorVersion;
   }

   public void setDefaultMinorVersion(int minor) {
      this.defaultGIOPMinorVersion = minor;
   }

   public String getLocationForwardPolicy() {
      return this.iiopLocationForwardPolicy;
   }

   public void setLocationForwardPolicy(String lcpolicy) {
      this.iiopLocationForwardPolicy = lcpolicy;
   }

   public boolean getUseLocateRequest() {
      return this.useIIOPLocateRequest;
   }

   public void setUseLocateRequest(boolean locate) {
      this.useIIOPLocateRequest = locate;
   }

   public boolean getUseFullRepositoryIdList() {
      return this.useFullRepositoyIdList;
   }

   public void setUseFullRepositoryIdList(boolean full) {
      this.useFullRepositoyIdList = full;
   }

   public String getTxMechanism() {
      return this.iiopTxMechanism;
   }

   public void setTxMechanism(String txmech) {
      this.iiopTxMechanism = txmech;
   }

   public int getIdleConnectionTimeout() {
      return this.idleIIOPConnectionTimeout / 1000;
   }

   public void setIdleConnectionTimeout(int seconds) {
      this.idleIIOPConnectionTimeout = seconds * 1000;
   }

   public String getDefaultCharCodeset() {
      return this.charCodeset;
   }

   public void setDefaultCharCodeset(String codeset) {
      this.charCodeset = codeset;
   }

   public String getDefaultWideCharCodeset() {
      return this.wcharCodeset;
   }

   public void setDefaultWideCharCodeset(String codeset) {
      this.wcharCodeset = codeset;
   }

   public String getSystemSecurity() {
      return this.systemSecurity;
   }

   public void setSystemSecurity(String value) {
      this.systemSecurity = value;
   }

   public boolean getUseStatefulAuthentication() {
      return this.statefulAuthentication;
   }

   public void setUseStatefulAuthentication(boolean auth) {
      this.statefulAuthentication = auth;
   }

   public boolean getUseSerialFormatVersion2() {
      return this.useSFV2;
   }

   public void setUseSerialFormatVersion2(boolean sfv) {
      this.useSFV2 = sfv;
   }

   public boolean getEnableIORServlet() {
      return this.enableIORServlet;
   }

   public void setEnableIORServlet(boolean flag) {
      this.enableIORServlet = flag;
   }

   public boolean getUseJavaSerialization() {
      return this.useJavaSerialization;
   }

   public void setUseJavaSerialization(boolean use) {
      this.useJavaSerialization = use;
   }
}
