package weblogic.jms.common;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import weblogic.messaging.dispatcher.DispatcherId;

public final class JMSServerId implements Externalizable, Comparable {
   private static final byte EXTVERSION1 = 1;
   private static final byte EXTVERSION2 = 2;
   static final long serialVersionUID = 7779784416562889644L;
   private JMSID id;
   private DispatcherId dispatcherId = null;

   public JMSServerId(JMSID id, DispatcherId dispatcherId) {
      this.id = id;
      this.dispatcherId = dispatcherId;
   }

   public String toString() {
      return this.id.toString() + ":" + this.dispatcherId.toString();
   }

   public JMSServerId(JMSServerId serverId) {
      this.id = serverId.getId();
      this.dispatcherId = serverId.getDispatcherId();
   }

   public JMSID getId() {
      return this.id;
   }

   public DispatcherId getDispatcherId() {
      return this.dispatcherId;
   }

   public long getTimestamp() {
      return this.id.getTimestamp();
   }

   public int getSeed() {
      return this.id.getSeed();
   }

   public JMSServerId() {
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeByte(2);
      this.id.writeExternal(out);
      this.dispatcherId.writeExternal(out);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      byte vrsn = in.readByte();
      if (vrsn != 1 && vrsn != 2) {
         throw JMSUtilities.versionIOException(vrsn, 1, 2);
      } else {
         this.id = new JMSID();
         this.id.readExternal(in);
         if (vrsn == 1) {
            this.consumeJVMID(in);
         } else {
            this.dispatcherId = new DispatcherId();
            this.dispatcherId.readExternal(in);
         }

      }
   }

   private void consumeJVMID(ObjectInput in) throws ClassNotFoundException, IOException {
      try {
         Class clzJVMID = Class.forName("weblogic.rjvm.JVMID");
         Object jvmid = clzJVMID.newInstance();
         Method mtdReadExternal = clzJVMID.getMethod("readExternal", ObjectInput.class);
         mtdReadExternal.invoke(jvmid, in);
      } catch (InvocationTargetException var5) {
         Throwable target = var5.getTargetException();
         if (target instanceof IOException) {
            throw (IOException)target;
         } else {
            throw new AssertionError(var5);
         }
      } catch (ClassNotFoundException var6) {
         throw var6;
      } catch (Exception var7) {
         throw new AssertionError(var7);
      }
   }

   public boolean equals(Object o) {
      if (!(o instanceof JMSServerId)) {
         return false;
      } else {
         JMSServerId serverId = (JMSServerId)o;
         return !this.id.equals(serverId.getId()) ? false : this.dispatcherId.equals(serverId.getDispatcherId());
      }
   }

   public int compareTo(Object o) {
      JMSServerId sid = (JMSServerId)o;
      int result = this.id.compareTo(sid.id);
      if (result < 0) {
         return -1;
      } else {
         return result > 0 ? 1 : this.dispatcherId.compareTo(sid.dispatcherId);
      }
   }

   public int hashCode() {
      return this.id.hashCode() ^ this.dispatcherId.hashCode();
   }
}
