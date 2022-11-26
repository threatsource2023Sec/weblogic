package serp.bytecode.lowlevel;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import serp.bytecode.visitor.VisitAcceptor;

public abstract class Entry implements VisitAcceptor {
   public static final int UTF8 = 1;
   public static final int INT = 3;
   public static final int FLOAT = 4;
   public static final int LONG = 5;
   public static final int DOUBLE = 6;
   public static final int CLASS = 7;
   public static final int STRING = 8;
   public static final int FIELD = 9;
   public static final int METHOD = 10;
   public static final int INTERFACEMETHOD = 11;
   public static final int NAMEANDTYPE = 12;
   public static final int METHODHANDLE = 15;
   public static final int METHODTYPE = 16;
   public static final int INVOKEDYNAMIC = 18;
   private ConstantPool _pool = null;
   private int _index = 0;

   public static Entry read(DataInput in) throws IOException {
      Entry entry = create(in.readUnsignedByte());
      entry.readData(in);
      return entry;
   }

   public static void write(Entry entry, DataOutput out) throws IOException {
      out.writeByte(entry.getType());
      entry.writeData(out);
   }

   public static Entry create(int type) {
      switch (type) {
         case 1:
            return new UTF8Entry();
         case 2:
         case 13:
         case 14:
         case 17:
         default:
            throw new IllegalArgumentException("type = " + type);
         case 3:
            return new IntEntry();
         case 4:
            return new FloatEntry();
         case 5:
            return new LongEntry();
         case 6:
            return new DoubleEntry();
         case 7:
            return new ClassEntry();
         case 8:
            return new StringEntry();
         case 9:
            return new FieldEntry();
         case 10:
            return new MethodEntry();
         case 11:
            return new InterfaceMethodEntry();
         case 12:
            return new NameAndTypeEntry();
         case 15:
            return new MethodHandleEntry();
         case 16:
            return new MethodTypeEntry();
         case 18:
            return new InvokeDynamicEntry();
      }
   }

   public abstract int getType();

   public boolean isWide() {
      return false;
   }

   public ConstantPool getPool() {
      return this._pool;
   }

   public int getIndex() {
      return this._index;
   }

   abstract void readData(DataInput var1) throws IOException;

   abstract void writeData(DataOutput var1) throws IOException;

   Object beforeModify() {
      if (this._pool == null) {
         return null;
      } else {
         ConstantPool var10000 = this._pool;
         return ConstantPool.getKey(this);
      }
   }

   void afterModify(Object key) {
      if (this._pool != null) {
         this._pool.modifyEntry(key, this);
      }

   }

   void setPool(ConstantPool pool) {
      if (this._pool != null && pool != null && this._pool != pool) {
         throw new IllegalStateException("Entry already belongs to a pool");
      } else {
         this._pool = pool;
      }
   }

   void setIndex(int index) {
      this._index = index;
   }
}
