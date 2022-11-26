package com.bea.core.repackaged.aspectj.apache.bcel.classfile;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ArrayType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConstantPool implements Node {
   private Constant[] pool;
   private int poolSize;
   private Map utf8Cache = new HashMap();
   private Map methodCache = new HashMap();
   private Map fieldCache = new HashMap();

   public int getSize() {
      return this.poolSize;
   }

   public ConstantPool() {
      this.pool = new Constant[10];
      this.poolSize = 0;
   }

   public ConstantPool(Constant[] constants) {
      this.pool = constants;
      this.poolSize = constants == null ? 0 : constants.length;
   }

   ConstantPool(DataInputStream file) throws IOException {
      this.poolSize = file.readUnsignedShort();
      this.pool = new Constant[this.poolSize];

      for(int i = 1; i < this.poolSize; ++i) {
         this.pool[i] = Constant.readConstant(file);
         byte tag = this.pool[i].getTag();
         if (tag == 6 || tag == 5) {
            ++i;
         }
      }

   }

   public Constant getConstant(int index, byte tag) {
      Constant c = this.getConstant(index);
      if (c.tag == tag) {
         return c;
      } else {
         throw new ClassFormatException("Expected class '" + Constants.CONSTANT_NAMES[tag] + "' at index " + index + " and found " + c);
      }
   }

   public Constant getConstant(int index) {
      try {
         return this.pool[index];
      } catch (ArrayIndexOutOfBoundsException var3) {
         throw new ClassFormatException("Index " + index + " into constant pool (size:" + this.poolSize + ") is invalid");
      }
   }

   public ConstantPool copy() {
      Constant[] newConstants = new Constant[this.poolSize];

      for(int i = 1; i < this.poolSize; ++i) {
         if (this.pool[i] != null) {
            newConstants[i] = this.pool[i].copy();
         }
      }

      return new ConstantPool(newConstants);
   }

   public String getConstantString(int index, byte tag) throws ClassFormatException {
      Constant c = this.getConstant(index, tag);
      int i;
      switch (tag) {
         case 7:
            i = ((ConstantClass)c).getNameIndex();
            break;
         case 8:
            i = ((ConstantString)c).getStringIndex();
            break;
         default:
            throw new RuntimeException("getConstantString called with illegal tag " + tag);
      }

      c = this.getConstant(i, (byte)1);
      return ((ConstantUtf8)c).getValue();
   }

   public String constantToString(Constant c) {
      int i;
      String str;
      switch (c.tag) {
         case 1:
         case 3:
         case 4:
         case 5:
         case 6:
            str = ((SimpleConstant)c).getStringValue();
            break;
         case 2:
         default:
            throw new RuntimeException("Unknown constant type " + c.tag);
         case 7:
            i = ((ConstantClass)c).getNameIndex();
            c = this.getConstant(i, (byte)1);
            str = Utility.compactClassName(((ConstantUtf8)c).getValue(), false);
            break;
         case 8:
            i = ((ConstantString)c).getStringIndex();
            c = this.getConstant(i, (byte)1);
            str = "\"" + escape(((ConstantUtf8)c).getValue()) + "\"";
            break;
         case 9:
         case 10:
         case 11:
            str = this.constantToString(((ConstantCP)c).getClassIndex(), (byte)7) + "." + this.constantToString(((ConstantCP)c).getNameAndTypeIndex(), (byte)12);
            break;
         case 12:
            str = this.constantToString(((ConstantNameAndType)c).getNameIndex(), (byte)1) + " " + this.constantToString(((ConstantNameAndType)c).getSignatureIndex(), (byte)1);
      }

      return str;
   }

   private static final String escape(String str) {
      int len = str.length();
      StringBuffer buf = new StringBuffer(len + 5);
      char[] ch = str.toCharArray();

      for(int i = 0; i < len; ++i) {
         switch (ch[i]) {
            case '\b':
               buf.append("\\b");
               break;
            case '\t':
               buf.append("\\t");
               break;
            case '\n':
               buf.append("\\n");
               break;
            case '\r':
               buf.append("\\r");
               break;
            case '"':
               buf.append("\\\"");
               break;
            default:
               buf.append(ch[i]);
         }
      }

      return buf.toString();
   }

   public String constantToString(int index, byte tag) {
      Constant c = this.getConstant(index, tag);
      return this.constantToString(c);
   }

   public String constantToString(int index) {
      return this.constantToString(this.getConstant(index));
   }

   public void accept(ClassVisitor v) {
      v.visitConstantPool(this);
   }

   public Constant[] getConstantPool() {
      return this.pool;
   }

   public void dump(DataOutputStream file) throws IOException {
      file.writeShort(this.poolSize);

      for(int i = 1; i < this.poolSize; ++i) {
         if (this.pool[i] != null) {
            this.pool[i].dump(file);
         }
      }

   }

   public ConstantUtf8 getConstantUtf8(int index) {
      Constant c = this.getConstant(index);

      assert c != null;

      assert c.tag == 1;

      return (ConstantUtf8)c;
   }

   public String getConstantString_CONSTANTClass(int index) {
      ConstantClass c = (ConstantClass)this.getConstant(index, (byte)7);
      index = c.getNameIndex();
      return ((ConstantUtf8)this.getConstant(index, (byte)1)).getValue();
   }

   public int getLength() {
      return this.poolSize;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();

      for(int i = 1; i < this.poolSize; ++i) {
         buf.append(i + ")" + this.pool[i] + "\n");
      }

      return buf.toString();
   }

   public int lookupInteger(int n) {
      for(int i = 1; i < this.poolSize; ++i) {
         if (this.pool[i] instanceof ConstantInteger) {
            ConstantInteger c = (ConstantInteger)this.pool[i];
            if (c.getValue() == n) {
               return i;
            }
         }
      }

      return -1;
   }

   public int lookupUtf8(String string) {
      Integer pos = (Integer)this.utf8Cache.get(string);
      if (pos != null) {
         return pos;
      } else {
         for(int i = 1; i < this.poolSize; ++i) {
            Constant c = this.pool[i];
            if (c != null && c.tag == 1 && ((ConstantUtf8)c).getValue().equals(string)) {
               this.utf8Cache.put(string, i);
               return i;
            }
         }

         return -1;
      }
   }

   public int lookupClass(String classname) {
      for(int i = 1; i < this.poolSize; ++i) {
         Constant c = this.pool[i];
         if (c != null && c.tag == 7) {
            int cIndex = ((ConstantClass)c).getNameIndex();
            String cName = ((ConstantUtf8)this.pool[cIndex]).getValue();
            if (cName.equals(classname)) {
               return i;
            }
         }
      }

      return -1;
   }

   public int addUtf8(String n) {
      int ret = this.lookupUtf8(n);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         ret = this.poolSize;
         this.pool[this.poolSize++] = new ConstantUtf8(n);
         return ret;
      }
   }

   public int addInteger(int n) {
      int ret = this.lookupInteger(n);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         ret = this.poolSize;
         this.pool[this.poolSize++] = new ConstantInteger(n);
         return ret;
      }
   }

   public int addArrayClass(ArrayType type) {
      return this.addClass(type.getSignature());
   }

   public int addClass(ObjectType type) {
      return this.addClass(type.getClassName());
   }

   public int addClass(String classname) {
      String toAdd = classname.replace('.', '/');
      int ret = this.lookupClass(toAdd);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         ConstantClass c = new ConstantClass(this.addUtf8(toAdd));
         ret = this.poolSize;
         this.pool[this.poolSize++] = c;
         return ret;
      }
   }

   private void adjustSize() {
      if (this.poolSize + 3 >= this.pool.length) {
         Constant[] cs = this.pool;
         this.pool = new Constant[cs.length + 8];
         System.arraycopy(cs, 0, this.pool, 0, cs.length);
      }

      if (this.poolSize == 0) {
         this.poolSize = 1;
      }

   }

   public int addFieldref(String class_name, String field_name, String signature) {
      int ret = this.lookupFieldref(class_name, field_name, signature);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         int class_index = this.addClass(class_name);
         int name_and_type_index = this.addNameAndType(field_name, signature);
         ret = this.poolSize;
         this.pool[this.poolSize++] = new ConstantFieldref(class_index, name_and_type_index);
         return ret;
      }
   }

   public int lookupFieldref(String searchClassname, String searchFieldname, String searchSignature) {
      searchClassname = searchClassname.replace('.', '/');
      String k = searchClassname + searchFieldname + searchSignature;
      Integer pos = (Integer)this.fieldCache.get(k);
      if (pos != null) {
         return pos;
      } else {
         for(int i = 1; i < this.poolSize; ++i) {
            Constant c = this.pool[i];
            if (c != null && c.tag == 9) {
               ConstantFieldref cfr = (ConstantFieldref)c;
               ConstantNameAndType cnat = (ConstantNameAndType)this.pool[cfr.getNameAndTypeIndex()];
               int cIndex = cfr.getClassIndex();
               ConstantClass cc = (ConstantClass)this.pool[cIndex];
               String cName = ((ConstantUtf8)this.pool[cc.getNameIndex()]).getValue();
               if (cName.equals(searchClassname)) {
                  String name = ((ConstantUtf8)this.pool[cnat.getNameIndex()]).getValue();
                  if (name.equals(searchFieldname)) {
                     String typeSignature = ((ConstantUtf8)this.pool[cnat.getSignatureIndex()]).getValue();
                     if (typeSignature.equals(searchSignature)) {
                        this.fieldCache.put(k, new Integer(i));
                        return i;
                     }
                  }
               }
            }
         }

         return -1;
      }
   }

   public int addNameAndType(String name, String signature) {
      int ret = this.lookupNameAndType(name, signature);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         int name_index = this.addUtf8(name);
         int signature_index = this.addUtf8(signature);
         ret = this.poolSize;
         this.pool[this.poolSize++] = new ConstantNameAndType(name_index, signature_index);
         return ret;
      }
   }

   public int lookupNameAndType(String searchName, String searchTypeSignature) {
      for(int i = 1; i < this.poolSize; ++i) {
         Constant c = this.pool[i];
         if (c != null && c.tag == 12) {
            ConstantNameAndType cnat = (ConstantNameAndType)c;
            String name = ((ConstantUtf8)this.pool[cnat.getNameIndex()]).getValue();
            if (name.equals(searchName)) {
               String typeSignature = ((ConstantUtf8)this.pool[cnat.getSignatureIndex()]).getValue();
               if (typeSignature.equals(searchTypeSignature)) {
                  return i;
               }
            }
         }
      }

      return -1;
   }

   public int addFloat(float f) {
      int ret = this.lookupFloat(f);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         ret = this.poolSize;
         this.pool[this.poolSize++] = new ConstantFloat(f);
         return ret;
      }
   }

   public int lookupFloat(float f) {
      int bits = Float.floatToIntBits(f);

      for(int i = 1; i < this.poolSize; ++i) {
         Constant c = this.pool[i];
         if (c != null && c.tag == 4) {
            ConstantFloat cf = (ConstantFloat)c;
            if (Float.floatToIntBits(cf.getValue()) == bits) {
               return i;
            }
         }
      }

      return -1;
   }

   public int addDouble(double d) {
      int ret = this.lookupDouble(d);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         ret = this.poolSize;
         this.pool[this.poolSize] = new ConstantDouble(d);
         this.poolSize += 2;
         return ret;
      }
   }

   public int lookupDouble(double d) {
      long bits = Double.doubleToLongBits(d);

      for(int i = 1; i < this.poolSize; ++i) {
         Constant c = this.pool[i];
         if (c != null && c.tag == 6) {
            ConstantDouble cf = (ConstantDouble)c;
            if (Double.doubleToLongBits(cf.getValue()) == bits) {
               return i;
            }
         }
      }

      return -1;
   }

   public int addLong(long l) {
      int ret = this.lookupLong(l);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         ret = this.poolSize;
         this.pool[this.poolSize] = new ConstantLong(l);
         this.poolSize += 2;
         return ret;
      }
   }

   public int lookupString(String s) {
      for(int i = 1; i < this.poolSize; ++i) {
         Constant c = this.pool[i];
         if (c != null && c.tag == 8) {
            ConstantString cs = (ConstantString)c;
            ConstantUtf8 cu8 = (ConstantUtf8)this.pool[cs.getStringIndex()];
            if (cu8.getValue().equals(s)) {
               return i;
            }
         }
      }

      return -1;
   }

   public int addString(String str) {
      int ret = this.lookupString(str);
      if (ret != -1) {
         return ret;
      } else {
         int utf8 = this.addUtf8(str);
         this.adjustSize();
         ConstantString s = new ConstantString(utf8);
         ret = this.poolSize;
         this.pool[this.poolSize++] = s;
         return ret;
      }
   }

   public int lookupLong(long l) {
      for(int i = 1; i < this.poolSize; ++i) {
         Constant c = this.pool[i];
         if (c != null && c.tag == 5) {
            ConstantLong cf = (ConstantLong)c;
            if (cf.getValue() == l) {
               return i;
            }
         }
      }

      return -1;
   }

   public int addConstant(Constant c, ConstantPool cp) {
      Constant[] constants = cp.getConstantPool();
      ConstantUtf8 u8;
      switch (c.getTag()) {
         case 1:
            return this.addUtf8(((ConstantUtf8)c).getValue());
         case 2:
         case 13:
         case 14:
         case 17:
         default:
            throw new RuntimeException("Unknown constant type " + c);
         case 3:
            return this.addInteger(((ConstantInteger)c).getValue());
         case 4:
            return this.addFloat(((ConstantFloat)c).getValue());
         case 5:
            return this.addLong(((ConstantLong)c).getValue());
         case 6:
            return this.addDouble(((ConstantDouble)c).getValue());
         case 7:
            ConstantClass s = (ConstantClass)c;
            u8 = (ConstantUtf8)constants[s.getNameIndex()];
            return this.addClass(u8.getValue());
         case 8:
            ConstantString s = (ConstantString)c;
            u8 = (ConstantUtf8)constants[s.getStringIndex()];
            return this.addString(u8.getValue());
         case 9:
         case 10:
         case 11:
            ConstantCP m = (ConstantCP)c;
            ConstantClass clazz = (ConstantClass)constants[m.getClassIndex()];
            ConstantNameAndType n = (ConstantNameAndType)constants[m.getNameAndTypeIndex()];
            ConstantUtf8 u8 = (ConstantUtf8)constants[clazz.getNameIndex()];
            String class_name = u8.getValue().replace('/', '.');
            u8 = (ConstantUtf8)constants[n.getNameIndex()];
            String name = u8.getValue();
            u8 = (ConstantUtf8)constants[n.getSignatureIndex()];
            String signature = u8.getValue();
            switch (c.getTag()) {
               case 9:
                  return this.addFieldref(class_name, name, signature);
               case 10:
                  return this.addMethodref(class_name, name, signature);
               case 11:
                  return this.addInterfaceMethodref(class_name, name, signature);
               default:
                  throw new RuntimeException("Unknown constant type " + c);
            }
         case 12:
            ConstantNameAndType n = (ConstantNameAndType)c;
            u8 = (ConstantUtf8)constants[n.getNameIndex()];
            ConstantUtf8 u8_2 = (ConstantUtf8)constants[n.getSignatureIndex()];
            return this.addNameAndType(u8.getValue(), u8_2.getValue());
         case 15:
            ConstantMethodHandle cmh = (ConstantMethodHandle)c;
            return this.addMethodHandle(cmh.getReferenceKind(), this.addConstant(constants[cmh.getReferenceIndex()], cp));
         case 16:
            ConstantMethodType cmt = (ConstantMethodType)c;
            return this.addMethodType(this.addConstant(constants[cmt.getDescriptorIndex()], cp));
         case 18:
            ConstantInvokeDynamic cid = (ConstantInvokeDynamic)c;
            int index1 = cid.getBootstrapMethodAttrIndex();
            ConstantNameAndType cnat = (ConstantNameAndType)constants[cid.getNameAndTypeIndex()];
            ConstantUtf8 name = (ConstantUtf8)constants[cnat.getNameIndex()];
            ConstantUtf8 signature = (ConstantUtf8)constants[cnat.getSignatureIndex()];
            int index2 = this.addNameAndType(name.getValue(), signature.getValue());
            return this.addInvokeDynamic(index1, index2);
      }
   }

   public int addMethodHandle(byte referenceKind, int referenceIndex) {
      this.adjustSize();
      int ret = this.poolSize;
      this.pool[this.poolSize++] = new ConstantMethodHandle(referenceKind, referenceIndex);
      return ret;
   }

   public int addMethodType(int descriptorIndex) {
      this.adjustSize();
      int ret = this.poolSize;
      this.pool[this.poolSize++] = new ConstantMethodType(descriptorIndex);
      return ret;
   }

   public int addMethodref(String class_name, String method_name, String signature) {
      int ret;
      if ((ret = this.lookupMethodref(class_name, method_name, signature)) != -1) {
         return ret;
      } else {
         this.adjustSize();
         int name_and_type_index = this.addNameAndType(method_name, signature);
         int class_index = this.addClass(class_name);
         ret = this.poolSize;
         this.pool[this.poolSize++] = new ConstantMethodref(class_index, name_and_type_index);
         return ret;
      }
   }

   public int addInvokeDynamic(int bootstrapMethodIndex, int constantNameAndTypeIndex) {
      this.adjustSize();
      int ret = this.poolSize;
      this.pool[this.poolSize++] = new ConstantInvokeDynamic(bootstrapMethodIndex, constantNameAndTypeIndex);
      return ret;
   }

   public int addInterfaceMethodref(String class_name, String method_name, String signature) {
      int ret = this.lookupInterfaceMethodref(class_name, method_name, signature);
      if (ret != -1) {
         return ret;
      } else {
         this.adjustSize();
         int class_index = this.addClass(class_name);
         int name_and_type_index = this.addNameAndType(method_name, signature);
         ret = this.poolSize;
         this.pool[this.poolSize++] = new ConstantInterfaceMethodref(class_index, name_and_type_index);
         return ret;
      }
   }

   public int lookupInterfaceMethodref(String searchClassname, String searchMethodName, String searchSignature) {
      searchClassname = searchClassname.replace('.', '/');

      for(int i = 1; i < this.poolSize; ++i) {
         Constant c = this.pool[i];
         if (c != null && c.tag == 11) {
            ConstantInterfaceMethodref cfr = (ConstantInterfaceMethodref)c;
            ConstantClass cc = (ConstantClass)this.pool[cfr.getClassIndex()];
            String cName = ((ConstantUtf8)this.pool[cc.getNameIndex()]).getValue();
            if (cName.equals(searchClassname)) {
               ConstantNameAndType cnat = (ConstantNameAndType)this.pool[cfr.getNameAndTypeIndex()];
               String name = ((ConstantUtf8)this.pool[cnat.getNameIndex()]).getValue();
               if (name.equals(searchMethodName)) {
                  String typeSignature = ((ConstantUtf8)this.pool[cnat.getSignatureIndex()]).getValue();
                  if (typeSignature.equals(searchSignature)) {
                     return i;
                  }
               }
            }
         }
      }

      return -1;
   }

   public int lookupMethodref(String searchClassname, String searchMethodName, String searchSignature) {
      String key = searchClassname + searchMethodName + searchSignature;
      Integer cached = (Integer)this.methodCache.get(key);
      if (cached != null) {
         return cached;
      } else {
         searchClassname = searchClassname.replace('.', '/');

         for(int i = 1; i < this.poolSize; ++i) {
            Constant c = this.pool[i];
            if (c != null && c.tag == 10) {
               ConstantMethodref cfr = (ConstantMethodref)c;
               ConstantNameAndType cnat = (ConstantNameAndType)this.pool[cfr.getNameAndTypeIndex()];
               int cIndex = cfr.getClassIndex();
               ConstantClass cc = (ConstantClass)this.pool[cIndex];
               String cName = ((ConstantUtf8)this.pool[cc.getNameIndex()]).getValue();
               if (cName.equals(searchClassname)) {
                  String name = ((ConstantUtf8)this.pool[cnat.getNameIndex()]).getValue();
                  if (name.equals(searchMethodName)) {
                     String typeSignature = ((ConstantUtf8)this.pool[cnat.getSignatureIndex()]).getValue();
                     if (typeSignature.equals(searchSignature)) {
                        this.methodCache.put(key, new Integer(i));
                        return i;
                     }
                  }
               }
            }
         }

         return -1;
      }
   }

   public ConstantPool getFinalConstantPool() {
      Constant[] cs = new Constant[this.poolSize];
      System.arraycopy(this.pool, 0, cs, 0, this.poolSize);
      return new ConstantPool(cs);
   }
}
