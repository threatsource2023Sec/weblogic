package org.mozilla.classfile;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import org.mozilla.javascript.WrappedException;

class ConstantPool {
   private static final int ConstantPoolSize = 256;
   private static final byte CONSTANT_Class = 7;
   private static final byte CONSTANT_Fieldref = 9;
   private static final byte CONSTANT_Methodref = 10;
   private static final byte CONSTANT_InterfaceMethodref = 11;
   private static final byte CONSTANT_String = 8;
   private static final byte CONSTANT_Integer = 3;
   private static final byte CONSTANT_Float = 4;
   private static final byte CONSTANT_Long = 5;
   private static final byte CONSTANT_Double = 6;
   private static final byte CONSTANT_NameAndType = 12;
   private static final byte CONSTANT_Utf8 = 1;
   private Hashtable itsUtf8Hash = new Hashtable();
   private Hashtable itsFieldRefHash = new Hashtable();
   private Hashtable itsMethodRefHash = new Hashtable();
   private Hashtable itsClassHash = new Hashtable();
   private int itsTop = 0;
   private int itsTopIndex = 1;
   private byte[] itsPool = new byte[256];

   short addClass(String var1) {
      short var2 = this.addUtf8(ClassFileWriter.fullyQualifiedForm(var1));
      return this.addClass(var2);
   }

   short addClass(short var1) {
      Short var2 = new Short(var1);
      Short var3 = (Short)this.itsClassHash.get(var2);
      if (var3 == null) {
         this.ensure(3);
         this.itsPool[this.itsTop++] = 7;
         this.itsPool[this.itsTop++] = (byte)(var1 >> 8);
         this.itsPool[this.itsTop++] = (byte)var1;
         var3 = new Short((short)(this.itsTopIndex++));
         this.itsClassHash.put(var2, var3);
      }

      return var3;
   }

   short addConstant(double var1) {
      this.ensure(9);
      this.itsPool[this.itsTop++] = 6;
      long var3 = Double.doubleToLongBits(var1);
      this.itsPool[this.itsTop++] = (byte)((int)(var3 >> 56));
      this.itsPool[this.itsTop++] = (byte)((int)(var3 >> 48));
      this.itsPool[this.itsTop++] = (byte)((int)(var3 >> 40));
      this.itsPool[this.itsTop++] = (byte)((int)(var3 >> 32));
      this.itsPool[this.itsTop++] = (byte)((int)(var3 >> 24));
      this.itsPool[this.itsTop++] = (byte)((int)(var3 >> 16));
      this.itsPool[this.itsTop++] = (byte)((int)(var3 >> 8));
      this.itsPool[this.itsTop++] = (byte)((int)var3);
      short var5 = (short)this.itsTopIndex;
      this.itsTopIndex += 2;
      return var5;
   }

   short addConstant(float var1) {
      this.ensure(5);
      this.itsPool[this.itsTop++] = 4;
      int var2 = Float.floatToIntBits(var1);
      this.itsPool[this.itsTop++] = (byte)(var2 >> 24);
      this.itsPool[this.itsTop++] = (byte)(var2 >> 16);
      this.itsPool[this.itsTop++] = (byte)(var2 >> 8);
      this.itsPool[this.itsTop++] = (byte)var2;
      return (short)(this.itsTopIndex++);
   }

   short addConstant(int var1) {
      this.ensure(5);
      this.itsPool[this.itsTop++] = 3;
      this.itsPool[this.itsTop++] = (byte)(var1 >> 24);
      this.itsPool[this.itsTop++] = (byte)(var1 >> 16);
      this.itsPool[this.itsTop++] = (byte)(var1 >> 8);
      this.itsPool[this.itsTop++] = (byte)var1;
      return (short)(this.itsTopIndex++);
   }

   short addConstant(long var1) {
      this.ensure(9);
      this.itsPool[this.itsTop++] = 5;
      this.itsPool[this.itsTop++] = (byte)((int)(var1 >> 56));
      this.itsPool[this.itsTop++] = (byte)((int)(var1 >> 48));
      this.itsPool[this.itsTop++] = (byte)((int)(var1 >> 40));
      this.itsPool[this.itsTop++] = (byte)((int)(var1 >> 32));
      this.itsPool[this.itsTop++] = (byte)((int)(var1 >> 24));
      this.itsPool[this.itsTop++] = (byte)((int)(var1 >> 16));
      this.itsPool[this.itsTop++] = (byte)((int)(var1 >> 8));
      this.itsPool[this.itsTop++] = (byte)((int)var1);
      short var3 = (short)this.itsTopIndex;
      this.itsTopIndex += 2;
      return var3;
   }

   short addConstant(String var1) {
      Utf8StringIndexPair var2 = (Utf8StringIndexPair)this.itsUtf8Hash.get(var1);
      if (var2 == null) {
         this.addUtf8(var1);
         var2 = (Utf8StringIndexPair)this.itsUtf8Hash.get(var1);
      }

      if (var2.itsStringIndex == -1) {
         var2.itsStringIndex = (short)(this.itsTopIndex++);
         this.ensure(3);
         this.itsPool[this.itsTop++] = 8;
         this.itsPool[this.itsTop++] = (byte)(var2.itsUtf8Index >> 8);
         this.itsPool[this.itsTop++] = (byte)var2.itsUtf8Index;
      }

      return var2.itsStringIndex;
   }

   short addFieldRef(String var1, String var2, String var3) {
      String var4 = var1 + " " + var2 + " " + var3;
      Short var5 = (Short)this.itsFieldRefHash.get(var4);
      if (var5 == null) {
         short var6 = this.addUtf8(var2);
         short var7 = this.addUtf8(var3);
         short var8 = this.addNameAndType(var6, var7);
         short var9 = this.addClass(var1);
         this.ensure(5);
         this.itsPool[this.itsTop++] = 9;
         this.itsPool[this.itsTop++] = (byte)(var9 >> 8);
         this.itsPool[this.itsTop++] = (byte)var9;
         this.itsPool[this.itsTop++] = (byte)(var8 >> 8);
         this.itsPool[this.itsTop++] = (byte)var8;
         var5 = new Short((short)(this.itsTopIndex++));
         this.itsFieldRefHash.put(var4, var5);
      }

      return var5;
   }

   short addInterfaceMethodRef(String var1, String var2, String var3) {
      short var4 = this.addUtf8(var2);
      short var5 = this.addUtf8(var3);
      short var6 = this.addNameAndType(var4, var5);
      short var7 = this.addClass(var1);
      this.ensure(5);
      this.itsPool[this.itsTop++] = 11;
      this.itsPool[this.itsTop++] = (byte)(var7 >> 8);
      this.itsPool[this.itsTop++] = (byte)var7;
      this.itsPool[this.itsTop++] = (byte)(var6 >> 8);
      this.itsPool[this.itsTop++] = (byte)var6;
      return (short)(this.itsTopIndex++);
   }

   short addMethodRef(String var1, String var2, String var3) {
      String var4 = var1 + " " + var2 + " " + var3;
      Short var5 = (Short)this.itsMethodRefHash.get(var4);
      if (var5 == null) {
         short var6 = this.addUtf8(var2);
         short var7 = this.addUtf8(var3);
         short var8 = this.addNameAndType(var6, var7);
         short var9 = this.addClass(var1);
         this.ensure(5);
         this.itsPool[this.itsTop++] = 10;
         this.itsPool[this.itsTop++] = (byte)(var9 >> 8);
         this.itsPool[this.itsTop++] = (byte)var9;
         this.itsPool[this.itsTop++] = (byte)(var8 >> 8);
         this.itsPool[this.itsTop++] = (byte)var8;
         var5 = new Short((short)(this.itsTopIndex++));
         this.itsMethodRefHash.put(var4, var5);
      }

      return var5;
   }

   short addNameAndType(short var1, short var2) {
      this.ensure(5);
      this.itsPool[this.itsTop++] = 12;
      this.itsPool[this.itsTop++] = (byte)(var1 >> 8);
      this.itsPool[this.itsTop++] = (byte)var1;
      this.itsPool[this.itsTop++] = (byte)(var2 >> 8);
      this.itsPool[this.itsTop++] = (byte)var2;
      return (short)(this.itsTopIndex++);
   }

   short addUtf8(String var1) {
      Utf8StringIndexPair var2 = (Utf8StringIndexPair)this.itsUtf8Hash.get(var1);
      if (var2 == null) {
         var2 = new Utf8StringIndexPair((short)(this.itsTopIndex++), (short)-1);
         this.itsUtf8Hash.put(var1, var2);

         try {
            ByteArrayOutputStream var3 = new ByteArrayOutputStream();
            DataOutputStream var4 = new DataOutputStream(var3);
            var4.writeUTF(var1);
            byte[] var5 = var3.toByteArray();
            this.ensure(1 + var5.length);
            this.itsPool[this.itsTop++] = 1;
            System.arraycopy(var5, 0, this.itsPool, this.itsTop, var5.length);
            this.itsTop += var5.length;
         } catch (IOException var6) {
            throw WrappedException.wrapException(var6);
         }
      }

      return var2.itsUtf8Index;
   }

   void ensure(int var1) {
      while(this.itsTop + var1 >= this.itsPool.length) {
         byte[] var2 = this.itsPool;
         this.itsPool = new byte[this.itsPool.length * 2];
         System.arraycopy(var2, 0, this.itsPool, 0, this.itsTop);
      }

   }

   void write(DataOutputStream var1) throws IOException {
      var1.writeShort((short)this.itsTopIndex);
      var1.write(this.itsPool, 0, this.itsTop);
   }
}
