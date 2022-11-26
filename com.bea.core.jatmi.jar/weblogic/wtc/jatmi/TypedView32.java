package weblogic.wtc.jatmi;

import com.bea.core.jatmi.common.ntrace;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class TypedView32 extends StandardTypes implements TypedBuffer, Cloneable {
   private static final long serialVersionUID = 1478768009528994942L;
   private boolean buildWith64Bit = false;
   private boolean use64BitsLong = false;

   public TypedView32() {
      super("VIEW32", 24);
   }

   public TypedView32(String viewname) {
      super("VIEW32", viewname, 24);
   }

   public TypedView32(String viewname, boolean val) {
      super("VIEW32", viewname, 24);
      this.buildWith64Bit = val;
   }

   public TypedView32 doClone() {
      TypedView32 cpy = null;
      ByteArrayOutputStream bos = new ByteArrayOutputStream();

      try {
         ObjectOutputStream oos = new ObjectOutputStream(bos);
         oos.writeObject(this);
         byte[] ba = bos.toByteArray();
         oos.close();
         bos.close();
         ByteArrayInputStream bis = new ByteArrayInputStream(ba);
         ObjectInputStream ois = new ObjectInputStream(bis);
         cpy = (TypedView32)ois.readObject();
         ois.close();
         bis.close();
         return cpy;
      } catch (Exception var7) {
         ntrace.doTrace("TypedView32:doClone:Exception:1:" + var7);
         return null;
      }
   }

   public abstract void _tmpresend(DataOutputStream var1) throws TPException, IOException;

   public abstract void _tmpostrecv(DataInputStream var1, int var2) throws TPException, IOException;

   public abstract boolean containsOldView();

   public void setUse64BitsLong(boolean val) {
      this.use64BitsLong = val;
   }

   public boolean getUse64BitsLong() {
      return this.use64BitsLong;
   }

   public boolean getBuiltWith64Bit() {
      return this.buildWith64Bit;
   }
}
