package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.common.JMSMessageId;
import weblogic.jms.dotnet.proxy.internal.SessionProxy;
import weblogic.jms.dotnet.proxy.util.ProxyUtil;
import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;
import weblogic.jms.dotnet.transport.ReceivedTwoWay;
import weblogic.jms.extensions.WLAcknowledgeInfo;
import weblogic.messaging.dispatcher.CompletionListener;

public final class ProxySessionAcknowledgeRequest extends ProxyRequest implements CompletionListener, WLAcknowledgeInfo {
   private static final int EXTVERSION = 1;
   private static final int _DO_COMMIT = 1;
   private static final int _HAS_SEQUENCENUMBER = 2;
   private boolean doCommit;
   private long sequenceNumber;
   private transient SessionProxy sessionProxy;
   private transient ReceivedTwoWay receivedTwoWay;

   public ProxySessionAcknowledgeRequest(long sequenceNumber, boolean doCommit) {
      this.sequenceNumber = sequenceNumber;
      this.doCommit = doCommit;
   }

   public JMSMessageId getMessageId() {
      return null;
   }

   public long getSequenceNumber() {
      return this.sequenceNumber;
   }

   public boolean getClientResponsibleForAcknowledge() {
      return false;
   }

   public boolean isDoCommit() {
      return this.doCommit;
   }

   public void setSessionProxy(SessionProxy sessionProxy) {
      this.sessionProxy = sessionProxy;
   }

   public ReceivedTwoWay getReceivedTwoWay() {
      return this.receivedTwoWay;
   }

   public void setReceivedTwoWay(ReceivedTwoWay receivedTwoWay) {
      this.receivedTwoWay = receivedTwoWay;
   }

   public void onCompletion(Object result) {
      this.sessionProxy.receiveCompletion(this, result, this.receivedTwoWay);
   }

   public void onException(Throwable throwable) {
      this.sessionProxy.receiveException(throwable, this.receivedTwoWay);
   }

   public ProxySessionAcknowledgeRequest() {
   }

   public int getMarshalTypeCode() {
      return 28;
   }

   public void unmarshal(MarshalReader mr) {
      this.versionFlags = new MarshalBitMask();
      this.versionFlags.unmarshal(mr);
      ProxyUtil.checkVersion(this.versionFlags.getVersion(), 1, 1);
      this.doCommit = this.versionFlags.isSet(1);
      if (this.versionFlags.isSet(2)) {
         this.sequenceNumber = mr.readLong();
      }

   }

   public void marshal(MarshalWriter mw) {
      this.versionFlags = new MarshalBitMask(1);
      if (this.doCommit) {
         this.versionFlags.setBit(1);
      }

      if (this.sequenceNumber != 0L) {
         this.versionFlags.setBit(2);
      }

      this.versionFlags.marshal(mw);
      if (this.sequenceNumber != 0L) {
         mw.writeLong(this.sequenceNumber);
      }

   }
}
