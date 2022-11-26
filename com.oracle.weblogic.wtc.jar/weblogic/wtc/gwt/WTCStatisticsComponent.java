package weblogic.wtc.gwt;

public class WTCStatisticsComponent {
   private String lDomAccessPointId;
   private String rDomAccessPointId;
   private TDMImport imp_svc;
   private TDMExport exp_svc;
   private String svcName;
   private boolean isImport;
   private long inboundMessageTotalCount;
   private long inboundSuccessReqTotalCount;
   private long inboundFailReqTotalCount;
   private long outboundMessageTotalCount;
   private long outboundSuccessReqTotalCount;
   private long outboundFailReqTotalCount;
   private long inboundNWMessageTotalSize;
   private long outboundNWMessageTotalSize;
   private long outstandingNWReqCount;
   private long inTransactionCommittedTotalCount;
   private long inTransactionRolledBackTotalCount;
   private long outTransactionCommittedTotalCount;
   private long outTransactionRolledBackTotalCount;

   public WTCStatisticsComponent(String LDomAccessPointId, String RDomAccessPointId) {
      this.lDomAccessPointId = LDomAccessPointId;
      this.rDomAccessPointId = RDomAccessPointId;
      this.inboundMessageTotalCount = 0L;
      this.inboundSuccessReqTotalCount = 0L;
      this.inboundFailReqTotalCount = 0L;
      this.outboundMessageTotalCount = 0L;
      this.outboundSuccessReqTotalCount = 0L;
      this.outboundFailReqTotalCount = 0L;
      this.inboundNWMessageTotalSize = 0L;
      this.outboundNWMessageTotalSize = 0L;
      this.outstandingNWReqCount = 0L;
      this.inTransactionCommittedTotalCount = 0L;
      this.inTransactionRolledBackTotalCount = 0L;
      this.outTransactionCommittedTotalCount = 0L;
      this.outTransactionRolledBackTotalCount = 0L;
   }

   public WTCStatisticsComponent(TDMImport svc) {
      this.imp_svc = svc;
      this.isImport = true;
      this.svcName = svc.getResourceName();
      this.inboundMessageTotalCount = 0L;
      this.inboundSuccessReqTotalCount = 0L;
      this.inboundFailReqTotalCount = 0L;
      this.outboundMessageTotalCount = 0L;
      this.outboundSuccessReqTotalCount = 0L;
      this.outboundFailReqTotalCount = 0L;
      this.inboundNWMessageTotalSize = 0L;
      this.outboundNWMessageTotalSize = 0L;
      this.outstandingNWReqCount = 0L;
      this.inTransactionCommittedTotalCount = 0L;
      this.inTransactionRolledBackTotalCount = 0L;
      this.outTransactionCommittedTotalCount = 0L;
      this.outTransactionRolledBackTotalCount = 0L;
   }

   public WTCStatisticsComponent(TDMExport svc) {
      this.exp_svc = svc;
      this.isImport = false;
      this.svcName = svc.getResourceName();
      this.inboundMessageTotalCount = 0L;
      this.inboundSuccessReqTotalCount = 0L;
      this.inboundFailReqTotalCount = 0L;
      this.outboundMessageTotalCount = 0L;
      this.outboundSuccessReqTotalCount = 0L;
      this.outboundFailReqTotalCount = 0L;
      this.inboundNWMessageTotalSize = 0L;
      this.outboundNWMessageTotalSize = 0L;
      this.outstandingNWReqCount = 0L;
      this.inTransactionCommittedTotalCount = 0L;
      this.inTransactionRolledBackTotalCount = 0L;
      this.outTransactionCommittedTotalCount = 0L;
      this.outTransactionRolledBackTotalCount = 0L;
   }

   public String getLDomAccessPointId() {
      return this.lDomAccessPointId;
   }

   public String getRDomAccessPointId() {
      return this.rDomAccessPointId;
   }

   public String getServiceName() {
      return this.svcName;
   }

   public void setServiceName(String svc_name) {
      this.svcName = svc_name;
   }

   public boolean getIsImport() {
      return this.isImport;
   }

   public TDMImport getImport() {
      return this.imp_svc;
   }

   public TDMExport getExport() {
      return this.exp_svc;
   }

   public long getInboundMessageCount() {
      return this.inboundMessageTotalCount;
   }

   public synchronized void setInboundMessageCount(long n) {
      this.inboundMessageTotalCount = n;
   }

   public long getInboundSuccessReqCount() {
      return this.inboundSuccessReqTotalCount;
   }

   public synchronized void setInboundSuccessReqCount(long n) {
      this.inboundSuccessReqTotalCount = n;
   }

   public long getInboundFailReqCount() {
      return this.inboundFailReqTotalCount;
   }

   public synchronized void setInboundFailReqCount(long n) {
      this.inboundFailReqTotalCount = n;
   }

   public long getOutboundMessageCount() {
      return this.outboundMessageTotalCount;
   }

   public long getOutboundSuccessReqCount() {
      return this.outboundSuccessReqTotalCount;
   }

   public synchronized void setOutboundSuccessReqCount(long n) {
      this.outboundSuccessReqTotalCount = n;
   }

   public long getOutboundFailReqCount() {
      return this.outboundFailReqTotalCount;
   }

   public synchronized void setOutboundFailReqCount(long n) {
      this.outboundFailReqTotalCount = n;
   }

   public synchronized void setOutboundMessageCount(long n) {
      this.outboundMessageTotalCount = n;
   }

   public long getInboundNWMessageSize() {
      return this.inboundNWMessageTotalSize;
   }

   public synchronized void setInboundNWMessageSize(long n) {
      this.inboundNWMessageTotalSize = n;
   }

   public long getOutboundNWMessageSize() {
      return this.outboundNWMessageTotalSize;
   }

   public synchronized void setOutboundNWMessageSize(long n) {
      this.outboundNWMessageTotalSize = n;
   }

   public long getOutstandingNWReqCount() {
      return this.outstandingNWReqCount;
   }

   public synchronized void setOutstandingNWReqCount(long n) {
      this.outstandingNWReqCount = n;
   }

   public long getInTransactionCommittedTotalCount() {
      return this.inTransactionCommittedTotalCount;
   }

   public synchronized void setInTransactionCommittedTotalCount(long n) {
      this.inTransactionCommittedTotalCount = n;
   }

   public long getInTransactionRolledBackTotalCount() {
      return this.inTransactionRolledBackTotalCount;
   }

   public synchronized void setInTransactionRolledBackTotalCount(long n) {
      this.inTransactionRolledBackTotalCount = n;
   }

   public long getOutTransactionCommittedTotalCount() {
      return this.outTransactionCommittedTotalCount;
   }

   public synchronized void setOutTransactionCommittedTotalCount(long n) {
      this.outTransactionCommittedTotalCount = n;
   }

   public long getOutTransactionRolledBackTotalCount() {
      return this.outTransactionRolledBackTotalCount;
   }

   public synchronized void setOutTransactionRolledBackTotalCount(long n) {
      this.outTransactionRolledBackTotalCount = n;
   }
}
