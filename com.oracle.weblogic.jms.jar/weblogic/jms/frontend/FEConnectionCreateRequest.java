package weblogic.jms.frontend;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import weblogic.jms.common.JMSUtilities;
import weblogic.jms.dispatcher.DispatcherWrapper;

public final class FEConnectionCreateRequest implements Externalizable {
   static final long serialVersionUID = 8136687183971929785L;
   private static final int EXTVERSION = 1;
   private static final int VERSION_MASK = 255;
   private static final int XA_MASK = 256;
   private static final int HAS_USER_MASK = 512;
   private static final int HAS_PW_MASK = 1024;
   private static final int OBS_MASK = 2048;
   private static final int OBS_SUBJECT_MASK = 4096;
   private DispatcherWrapper wrapper;
   private String userName;
   private String password;
   private boolean createXAConnection;
   private boolean isOBSSupported = true;
   private boolean sendBackSubject = false;

   public FEConnectionCreateRequest() {
   }

   public FEConnectionCreateRequest(DispatcherWrapper wrapper, String userName, String password, boolean createXAConnection) {
      this.wrapper = wrapper;
      this.userName = userName;
      this.password = password;
      this.createXAConnection = createXAConnection;
   }

   public DispatcherWrapper getDispatcherWrapper() {
      return this.wrapper;
   }

   public String getUserName() {
      return this.userName;
   }

   public String getPassword() {
      return this.password;
   }

   public boolean isOBSSupported() {
      return this.isOBSSupported;
   }

   public boolean isSendBackSubject() {
      return this.sendBackSubject;
   }

   public boolean setSendBackSubject(Boolean sendBackSubject) {
      return this.sendBackSubject = sendBackSubject;
   }

   public boolean getCreateXAConnection() {
      return this.createXAConnection;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      int mask = 1;
      if (this.createXAConnection) {
         mask |= 256;
      }

      if (this.userName != null) {
         mask |= 512;
      }

      if (this.password != null) {
         mask |= 1024;
      }

      mask |= 2048;
      if (this.sendBackSubject) {
         mask |= 4096;
      }

      out.writeInt(mask);
      this.wrapper.writeExternal(out);
      if (this.userName != null) {
         out.writeUTF(this.userName);
      }

      if (this.password != null) {
         out.writeUTF(this.password);
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      int mask = in.readInt();
      int version = mask & 255;
      if (version != 1) {
         throw JMSUtilities.versionIOException(version, 1, 1);
      } else {
         this.wrapper = new DispatcherWrapper();
         this.wrapper.readExternal(in);
         this.createXAConnection = (mask & 256) != 0;
         if ((mask & 512) != 0) {
            this.userName = in.readUTF();
         }

         if ((mask & 1024) != 0) {
            this.password = in.readUTF();
         }

         this.isOBSSupported = (mask & 2048) != 0;
         this.sendBackSubject = (mask & 4096) != 0;
      }
   }
}
