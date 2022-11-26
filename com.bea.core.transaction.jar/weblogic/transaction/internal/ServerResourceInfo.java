package weblogic.transaction.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import javax.transaction.SystemException;
import javax.transaction.xa.Xid;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

abstract class ServerResourceInfo extends ResourceInfo {
   protected boolean busy;
   protected int busyRetryCount;
   protected ResourceDescriptor rd;
   protected SCInfo scAssignedTo;
   protected XidImpl xid;
   protected boolean enlistedElsewhere;
   protected byte state;
   protected ArrayList extraResourceInfos;
   protected static final byte STATE_NEW = 1;
   protected static final byte STATE_SUSPENDED = 4;
   protected static final byte STATE_COMMITTED = 7;
   protected static final byte STATE_ROLLEDBACK = 8;
   protected static final int clearBusyCount = Integer.getInteger("weblogic.transaction.resource.clearBusyCount", 10);
   protected boolean alias;

   ServerResourceInfo() {
      this.scAssignedTo = null;
      this.xid = null;
      this.enlistedElsewhere = false;
      this.state = 1;
      this.setState((byte)1);
   }

   ServerResourceInfo(String aName) {
      super(aName);
      this.scAssignedTo = null;
      this.xid = null;
      this.enlistedElsewhere = false;
      this.state = 1;
      this.setState((byte)1);
   }

   ServerResourceInfo(String aName, boolean enlistedElsewhere) {
      this(aName);
      this.enlistedElsewhere = enlistedElsewhere;
   }

   ServerResourceInfo(ResourceDescriptor ard) {
      this.scAssignedTo = null;
      this.xid = null;
      this.enlistedElsewhere = false;
      this.state = 1;
      this.setState((byte)1);
      this.rd = ard;
      this.setName(this.rd.getName());
      this.incrementTxRefCount();
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(100);
      sb.append("ServerResourceInfo[").append(this.getName()).append("]=(").append("state=").append(this.getStateAsString()).append(",assigned=").append(this.scAssignedTo == null ? "none" : this.scAssignedTo.getName());
      if (this.extraResourceInfos != null) {
         sb.append(",extraResourceInfos={");

         for(int i = 0; i < this.extraResourceInfos.size(); ++i) {
            sb.append(this.extraResourceInfos.get(i).toString());
            if (i != this.extraResourceInfos.size() - 1) {
               sb.append(",");
            }
         }

         sb.append("}");
      }

      sb.append(")");
      return sb.toString();
   }

   boolean isEmulatedTwoPhaseResource() {
      return false;
   }

   boolean isReadOnly() {
      return false;
   }

   void setEnlistedElsewhere() {
      this.enlistedElsewhere = true;
   }

   void add(ServerResourceInfo aRI) {
      if (this.extraResourceInfos == null) {
         this.extraResourceInfos = new ArrayList(2);
      }

      this.extraResourceInfos.add(aRI);
   }

   boolean enlistIfStatic(ServerTransactionImpl tx, boolean enlistOOAlso) throws AbortRequestedException {
      return true;
   }

   boolean enlistIfStaticAndNoThreadAffinityNeeded(ServerTransactionImpl tx, boolean enlistOOAlso) throws AbortRequestedException {
      return true;
   }

   boolean enlistIfNeedThreadAffinity(ServerTransactionImpl tx, boolean enlistOOAlso) throws AbortRequestedException {
      return true;
   }

   boolean enlist(ServerTransactionImpl tx) throws AbortRequestedException, SystemException {
      return false;
   }

   void delistIfStatic(ServerTransactionImpl tx, int delistFlag, boolean forceDelist) throws AbortRequestedException {
      if (this.rd != null && this.rd.isStatic()) {
         this.delist(tx, delistFlag, forceDelist);
      }

   }

   void delistIfThreadAffinity(ServerTransactionImpl tx, int delistFlag, boolean forceDelist) throws AbortRequestedException {
      if (this.rd != null && this.rd.needThreadAffinity()) {
         this.delist(tx, delistFlag, forceDelist);
      }

   }

   void delist(ServerTransactionImpl tx, int delistFlag, boolean forceDelist) throws AbortRequestedException {
   }

   abstract void rollback(ServerTransactionImpl var1, boolean var2, boolean var3);

   final void assignResourceToSC(SCInfo sci) {
      this.scAssignedTo = sci;
   }

   final boolean isAssignedTo(SCInfo sci) {
      return this.scAssignedTo == sci;
   }

   final boolean isAssigned() {
      return this.scAssignedTo != null;
   }

   SCInfo getSCAssignedTo() {
      return this.scAssignedTo;
   }

   void addSC(SCInfo sci) {
      ArrayList scList = super.getOrCreateSCInfoList();
      if (!scList.contains(sci)) {
         scList.add(sci);
         if (this.rd != null) {
            this.rd.addSC(sci.getCoordinatorDescriptor());
         }

      }
   }

   boolean isAccessibleAt(CoordinatorDescriptor aCoDesc) {
      return this.isAccessibleAt(aCoDesc, false);
   }

   boolean isAccessibleAt(CoordinatorDescriptor aCoDesc, boolean isResourceNotFound) {
      if (this.rd == null) {
         return false;
      } else {
         this.checkForReRegistration(aCoDesc);
         return this.rd.isAccessibleAt(aCoDesc, isResourceNotFound);
      }
   }

   boolean isAccessibleAtAndAssignableTo(ServerSCInfo aSCInfo, boolean retry) {
      return this.isAccessibleAtAndAssignableTo(aSCInfo, retry, false);
   }

   boolean isAccessibleAtAndAssignableTo(ServerSCInfo aSCInfo, boolean retry, boolean isResourceNotFound) {
      boolean accessible = this.isAccessibleAt(aSCInfo.getCoordinatorDescriptor(), isResourceNotFound);
      if (!accessible) {
         return false;
      } else {
         if (this.rd.isAssignableOnlyToEnlistingSCs() && !retry) {
            ArrayList scList = this.getSCInfoList();
            if (scList == null || !scList.contains(aSCInfo)) {
               return false;
            }
         }

         return true;
      }
   }

   boolean isRolledBack() {
      return this.getState() == 8;
   }

   boolean isCommitted() {
      return this.getState() == 7;
   }

   boolean isSuspended() {
      return this.getState() == 4;
   }

   String getStateAsString() {
      return this.getStateAsString(this.getState());
   }

   protected int getState() {
      return this.state;
   }

   protected void setState(byte st) {
      if (TxDebug.JTA2PC.isDebugEnabled() && this.state != st) {
         TxDebug.JTA2PC.debug((String)("Resource[" + this.getName() + "] " + this.getStateAsString(this.state) + "-->" + this.getStateAsString(st)), (Throwable)(TxDebug.JTA2PCStackTrace.isDebugEnabled() ? new Exception("DEBUG") : null));
      }

      this.state = st;
   }

   protected String getStateAsString(int st) {
      switch (st) {
         case 1:
            return "new";
         case 2:
         case 3:
         case 5:
         case 6:
         default:
            return "**** UNKNOWN STATE *** " + st;
         case 4:
            return "suspended";
         case 7:
            return "committed";
         case 8:
            return "rolledback";
      }
   }

   protected synchronized boolean testAndSetBusy() {
      if (this.busy) {
         ++this.busyRetryCount;
         return false;
      } else {
         this.busy = true;
         return true;
      }
   }

   protected boolean isBusy() {
      return this.busy;
   }

   protected synchronized void clearBusy() {
      this.busyRetryCount = 0;
      this.busy = false;
   }

   protected synchronized int getBusyRetryCount() {
      return this.busyRetryCount;
   }

   void setRolledBack() {
      this.setState((byte)8);
   }

   protected boolean isEnlistedElsewhere() {
      return this.enlistedElsewhere;
   }

   void setCommitted() {
      this.setState((byte)7);
   }

   protected Xid getXIDwithBranch(XidImpl xidImpl) {
      if (this.xid == null) {
         this.xid = xidImpl.newBranch(this.rd.getBranchQualifier(this.getName()));
      }

      return this.xid;
   }

   protected Xid getXIDwithOldBranch(XidImpl xidImpl) {
      if (this.xid == null) {
         this.xid = xidImpl.newBranch(this.getName());
      }

      return this.xid;
   }

   ResourceDescriptor getResourceDescriptor() {
      return this.rd;
   }

   boolean isObjectOriented() {
      return this.rd.getResourceType() == 3;
   }

   protected void checkForReRegistration(CoordinatorDescriptor aCoDesc) {
      if (this.rd != null && !this.rd.isRegistered() && getTM().isLocalCoordinator(aCoDesc)) {
         ResourceDescriptor tmp = ResourceDescriptor.get(this.rd.getName());
         if (tmp != null && tmp != this.rd && tmp.isRegistered()) {
            this.rd = tmp;
            this.incrementTxRefCount();
         }
      }

   }

   protected static String getTxId(TransactionImpl tx, Xid xid) {
      String id = null;
      if (tx != null) {
         id = tx.getName();
      }

      if (id == null) {
         if (xid instanceof XidImpl) {
            id = xid.toString().toUpperCase(Locale.ENGLISH);
         } else {
            id = XidImpl.create(xid).toString().toUpperCase(Locale.ENGLISH);
         }
      }

      return id;
   }

   protected static ServerTransactionManagerImpl getTM() {
      return (ServerTransactionManagerImpl)TransactionManagerImpl.getTransactionManager();
   }

   void decrementTxRefCount() {
      if (this.rd != null) {
         this.rd.decrementTxRefCount();
      }

   }

   void incrementTxRefCount() {
      if (this.rd != null) {
         this.rd.incrementTxRefCount();
      }

   }

   void checkNewEnlistment() throws SystemException {
      if (this.rd.isUnregistering()) {
         throw new SystemException("Unable to enlist resource '" + this.rd.getName() + "' due to in-progress unregistration");
      }
   }

   public boolean isAlias() {
      return this.alias;
   }

   public String aliasOf() {
      return !this.alias ? null : this.rd.getName();
   }

   public void dump(JTAImageSource imageSource, XMLStreamWriter xsw) throws DiagnosticImageTimeoutException, XMLStreamException {
      imageSource.checkTimeout();
      xsw.writeStartElement("Resource");
      xsw.writeAttribute("name", this.getName());
      xsw.writeAttribute("state", this.getStateAsString());
      if (this.xid != null) {
         xsw.writeAttribute("xid", this.xid.toString());
      }

      if (this.scAssignedTo != null) {
         xsw.writeAttribute("assignedTo", this.scAssignedTo.getScUrl());
      }

      xsw.writeAttribute("busy", String.valueOf(this.busy));
      if (this.extraResourceInfos != null && this.extraResourceInfos.size() > 0) {
         xsw.writeAttribute("extraEnlistmentCount", String.valueOf(this.extraResourceInfos.size()));
      }

      xsw.writeStartElement("Servers");
      ArrayList scList = this.getSCInfoList();
      if (scList == null) {
         xsw.writeAttribute("currentCount", "0");
      } else {
         ArrayList copy = (ArrayList)scList.clone();
         xsw.writeAttribute("currentCount", String.valueOf(copy.size()));
         Iterator it = copy.iterator();

         while(it.hasNext()) {
            ServerSCInfo server = (ServerSCInfo)it.next();
            server.dump(imageSource, xsw);
         }
      }

      xsw.writeEndElement();
      xsw.writeEndElement();
   }
}
