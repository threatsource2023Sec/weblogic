package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;

final class RuntimeStateMessage implements GroupMessage, Externalizable {
   private static final long serialVersionUID = 2610797619397430239L;
   private int srvrRuntimeState;
   private MemberAttributes memberAttributes;
   private long currentSeqNum = -1L;

   RuntimeStateMessage(int state, MemberAttributes attributes, long currentSeqNum) {
      this.srvrRuntimeState = state;
      this.memberAttributes = attributes;
      this.currentSeqNum = currentSeqNum;
   }

   public void execute(HostID memberID) {
      this.processAttributes(this.memberAttributes);
      MemberManager.theOne().updateMemberRuntimeState(this.memberAttributes.identity(), this.srvrRuntimeState, this.currentSeqNum);
   }

   public String toString() {
      return "State:" + this.srvrRuntimeState;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      ((WLObjectOutput)oo).writeInt(this.srvrRuntimeState);
      ((WLObjectOutput)oo).writeObjectWL(this.memberAttributes);
      oo.writeLong(this.currentSeqNum);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.srvrRuntimeState = ((WLObjectInput)oi).readInt();
      this.memberAttributes = (MemberAttributes)((WLObjectInput)oi).readObjectWL();
      if (oi.available() > 0) {
         this.currentSeqNum = oi.readLong();
      }

   }

   public RuntimeStateMessage() {
   }

   private void processAttributes(MemberAttributes attributes) {
      RemoteMemberInfo info = MemberManager.theOne().findOrCreate(attributes.identity());

      try {
         info.processAttributes(attributes);
      } finally {
         MemberManager.theOne().done(info);
      }

   }
}
