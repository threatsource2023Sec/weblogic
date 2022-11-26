package weblogic.jms.dotnet.proxy.protocol;

import weblogic.jms.dotnet.transport.MarshalReader;
import weblogic.jms.dotnet.transport.MarshalWriter;

public final class MarshalBitMask {
   private static final int VERSION_MASK = 255;
   private static final int HAS_EXTENSION = Integer.MIN_VALUE;
   private int[] masks;
   private int version;
   static final boolean debug = false;

   public MarshalBitMask(int version) {
      this.version = version;
      this.masks = new int[1];
      this.masks[0] = version;
   }

   public final int getVersion() {
      return this.version;
   }

   public final boolean isSet(int bit) {
      int index = this.getIndex(bit);
      int position = this.getPosition(bit, index);
      if (index >= this.masks.length) {
         return false;
      } else {
         int value = this.masks[index];
         return (value & 1 << position) != 0;
      }
   }

   public final void setBit(int bit) {
      int index = this.getIndex(bit);
      if (index >= this.masks.length) {
         this.expand(index);
      }

      int position = this.getPosition(bit, index);
      int[] var10000 = this.masks;
      var10000[index] |= 1 << position;
   }

   private int getIndex(int bit) {
      return (bit + 7) / 31;
   }

   private int getPosition(int bit, int index) {
      return bit + 7 - index * 31;
   }

   private void expand(int index) {
      int[] newMasks = new int[index + 1];

      int i;
      for(i = 0; i < this.masks.length - 1; ++i) {
         newMasks[i] = this.masks[i];
      }

      newMasks[this.masks.length - 1] = this.masks[this.masks.length - 1] | Integer.MIN_VALUE;

      for(i = this.masks.length; i < index - 1; ++i) {
         newMasks[i] = Integer.MIN_VALUE;
      }

      this.masks = newMasks;
   }

   public MarshalBitMask() {
   }

   public void marshal(MarshalWriter mw) {
      int[] var2 = this.masks;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int mask = var2[var4];
         mw.writeInt(mask);
      }

   }

   public void unmarshal(MarshalReader mr) {
      this.masks = new int[1];
      int i = 0;

      while(((this.masks[i++] = mr.readInt()) & Integer.MIN_VALUE) != 0) {
         this.expand(i);
      }

      this.version = this.masks[0] & 255;
   }

   private void debug(String str) {
      System.out.println("[MarshalBitMask]: " + str);
   }
}
