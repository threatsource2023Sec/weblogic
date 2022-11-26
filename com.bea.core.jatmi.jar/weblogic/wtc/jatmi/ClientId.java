package weblogic.wtc.jatmi;

import java.io.Serializable;

public final class ClientId implements Serializable {
   private int timestamp = -1;
   private int mchid = -1;
   private int slot = -1;
   private int unused = -1;

   public ClientId() {
   }

   public ClientId(ClientId aCltId) {
      this.timestamp = aCltId.getTimestamp();
      this.mchid = aCltId.getMchid();
      this.slot = aCltId.getSlot();
   }

   public int getTimestamp() {
      return this.timestamp;
   }

   public void setTimestamp(int newTimestamp) {
      this.timestamp = newTimestamp;
   }

   public int getMchid() {
      return this.mchid;
   }

   public void setMchid(int newMchid) {
      this.mchid = newMchid;
   }

   public int getSlot() {
      return this.slot;
   }

   public void setSlot(int newSlot) {
      this.slot = newSlot;
   }
}
