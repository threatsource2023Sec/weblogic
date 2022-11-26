package weblogic.cluster;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;

final class AttributesMessage implements GroupMessage, Externalizable {
   private static final long serialVersionUID = 2610797619397430239L;
   MemberAttributes attributes;

   AttributesMessage(MemberAttributes attributes) {
      this.attributes = attributes;
   }

   public void execute(HostID memberID) {
      AttributeManager.theOne().receiveAttributes(memberID, this);
   }

   public String toString() {
      return "Attributes " + this.attributes;
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      ((WLObjectOutput)oo).writeObjectWL(this.attributes);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      this.attributes = (MemberAttributes)((WLObjectInput)oi).readObjectWL();
   }

   public AttributesMessage() {
   }
}
