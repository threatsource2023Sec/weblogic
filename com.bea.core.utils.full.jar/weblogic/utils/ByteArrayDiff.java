package weblogic.utils;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ByteArrayDiff implements Externalizable, PlatformConstants {
   private static final long serialVersionUID = -5581002954736412294L;
   private int length;
   private List diffs = new ArrayList();
   private boolean dumpByteArrays = false;

   public ByteArrayDiff() {
   }

   public ByteArrayDiff(int l) {
      this.setLength(l);
   }

   public void setLength(int l) {
      this.length = l;
   }

   public int getLength() {
      return this.length;
   }

   public boolean isEmpty() {
      return this.diffs.isEmpty();
   }

   public void addDiff(int offset, byte[] change) {
      this.diffs.add(new BeanDiff(offset, change));
   }

   public byte[] applyDiff(byte[] inputArray) {
      byte[] b = null;
      byte[] b;
      if (inputArray != null && inputArray.length == this.getLength()) {
         b = inputArray;
      } else {
         b = new byte[this.getLength()];
         if (inputArray != null) {
            int copyLength = Math.min(this.getLength(), inputArray.length);
            System.arraycopy(inputArray, 0, b, 0, copyLength);
         }
      }

      Iterator it = this.diffs.iterator();

      while(it.hasNext()) {
         BeanDiff bd = (BeanDiff)it.next();
         System.arraycopy(bd.change, 0, b, bd.offset, bd.change.length);
      }

      return b;
   }

   public void writeExternal(ObjectOutput out) throws IOException {
      out.writeInt(this.length);
      int size = this.diffs.size();
      out.writeInt(size);

      for(int i = 0; i < size; ++i) {
         out.writeObject(this.diffs.get(i));
      }

   }

   public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
      this.length = in.readInt();
      int size = in.readInt();
      this.diffs = new ArrayList();

      for(int i = 0; i < size; ++i) {
         this.diffs.add(in.readObject());
      }

   }

   void setDumpByteArrays(boolean b) {
      this.dumpByteArrays = b;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer(200);
      sb.append("ByteArrayDiff dump: " + EOL);
      sb.append("Length is: " + this.getLength() + "." + EOL);
      if (this.diffs.isEmpty()) {
         sb.append("Diffs are empty" + EOL);
         return sb.toString();
      } else {
         int count = 0;
         Iterator it = this.diffs.iterator();

         while(true) {
            BeanDiff bd;
            do {
               if (!it.hasNext()) {
                  return sb.toString();
               }

               bd = (BeanDiff)it.next();
               sb.append("Diff # " + count + " has offset: " + bd.offset + " and length: " + bd.change.length + "." + EOL);
            } while(!this.dumpByteArrays);

            sb.append("Change: ");

            for(int i = 0; i < bd.change.length; ++i) {
               sb.append(" " + bd.change[i]);
            }

            sb.append(EOL);
         }
      }
   }

   public static class BeanDiff implements Externalizable {
      private static final long serialVersionUID = 1800311784822217248L;
      int offset;
      byte[] change;

      BeanDiff(int o, byte[] c) {
         this.offset = o;
         this.change = c;
      }

      public BeanDiff() {
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeInt(this.offset);
         out.writeInt(this.change.length);
         out.write(this.change);
      }

      public void readExternal(ObjectInput in) throws IOException {
         this.offset = in.readInt();
         int size = in.readInt();
         this.change = new byte[size];
         in.readFully(this.change);
      }
   }
}
