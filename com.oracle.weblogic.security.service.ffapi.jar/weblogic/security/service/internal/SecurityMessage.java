package weblogic.security.service.internal;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.security.AccessController;
import weblogic.cluster.GroupMessage;
import weblogic.common.WLObjectInput;
import weblogic.common.WLObjectOutput;
import weblogic.rmi.spi.HostID;
import weblogic.security.acl.internal.AuthenticatedSubject;
import weblogic.security.service.PrivilegedActions;
import weblogic.security.service.SecurityServiceManager;

public final class SecurityMessage implements GroupMessage, Externalizable {
   private static final long serialVersionUID = -1897448590777619482L;
   private static final AuthenticatedSubject kernelId = (AuthenticatedSubject)AccessController.doPrivileged(PrivilegedActions.getKernelIdentityAction());
   private int nextSeqNo;
   private SecurityMulticastRecord record;
   private String realmName;

   public int nextSeqNo() {
      return this.nextSeqNo;
   }

   public SecurityMulticastRecord record() {
      return this.record;
   }

   public SecurityMessage(String realmName, int nextSeqNo, SecurityMulticastRecord record) {
      this.realmName = realmName;
      this.nextSeqNo = nextSeqNo;
      this.record = record;
   }

   public String toString() {
      return "SecurityMessage realm:" + this.realmName + " record:" + this.record + " nextSeqNo:" + this.nextSeqNo;
   }

   public void execute(HostID sender) {
      SecurityServiceManager.getPrincipalAuthenticator(kernelId, this.realmName).receiveSecurityMessageCommon(sender, this);
   }

   public void writeExternal(ObjectOutput oo) throws IOException {
      WLObjectOutput out = (WLObjectOutput)oo;
      out.writeObjectWL(this.realmName);
      oo.writeInt(this.nextSeqNo);
      out.writeObjectWL(this.record);
   }

   public void readExternal(ObjectInput oi) throws IOException, ClassNotFoundException {
      WLObjectInput in = (WLObjectInput)oi;
      this.realmName = (String)in.readObjectWL();
      this.nextSeqNo = oi.readInt();
      this.record = (SecurityMulticastRecord)in.readObjectWL();
   }

   public SecurityMessage() {
   }
}
