package weblogic.cluster.replication;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OutputStream;
import weblogic.rjvm.LocalRJVM;
import weblogic.utils.io.DataIO;

public final class ROID implements Externalizable {
   private static final long serialVersionUID = -7737873844013713694L;
   private static long numObjects = 0L;
   private static final Object NUM_OBJ_INC_LOCK = new Object();
   private long value;
   private int hashValue;

   static ROID create() {
      int i;
      synchronized(NUM_OBJ_INC_LOCK) {
         i = numObjects++;
      }

      return new ROID(ROID.Initializer.differentiator + i);
   }

   private ROID(long value) {
      this.value = value;
      this.computeHash();
   }

   public ROID() {
   }

   private void computeHash() {
      this.hashValue = (int)(this.value ^ this.value >> 32);
   }

   public int hashCode() {
      return this.hashValue;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj instanceof ROID) {
         ROID other = (ROID)obj;
         return this.value == other.value;
      } else {
         return false;
      }
   }

   public String toString() {
      return "" + this.value;
   }

   public int getValueAsInt() {
      return (int)this.value;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      DataIO.writeLong((OutputStream)out, this.value);
   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.value = DataIO.readLong((InputStream)in);
      this.computeHash();
   }

   private static class Initializer {
      private static final long differentiator = createDifferentiator();

      private static long createDifferentiator() {
         byte[] keyBytes = LocalRJVM.getLocalRJVM().getPublicKey();
         long computeDifferentiator = 0L;
         byte[] var3 = keyBytes;
         int var4 = keyBytes.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            byte keyByte = var3[var5];
            computeDifferentiator = computeDifferentiator << 8 ^ computeDifferentiator ^ (long)keyByte;
         }

         return computeDifferentiator;
      }
   }
}
