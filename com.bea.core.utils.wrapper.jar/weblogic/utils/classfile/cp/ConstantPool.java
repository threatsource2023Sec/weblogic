package weblogic.utils.classfile.cp;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import weblogic.utils.classfile.CodeGenHelper;
import weblogic.utils.classfile.DoubleKey;
import weblogic.utils.classfile.MalformedClassException;

public class ConstantPool implements ConstantPoolTags {
   private static final boolean verbose = false;
   private boolean poolDirty = false;
   CPInfo[] pool;
   private int pool_length = 1;
   private Map lookup = new HashMap();
   private List ordered = new ArrayList();

   private synchronized Object find(Object key, Class c) {
      Object o = this.lookup.get(key);
      if (o == null) {
         this.poolDirty = true;
         ++this.pool_length;
         CPInfo cpInfo = null;
         if (c == CPClass.class) {
            cpInfo = new CPClass();
         } else if (c == CPFieldref.class) {
            cpInfo = new CPFieldref();
         } else if (c == CPMethodref.class) {
            cpInfo = new CPMethodref();
         } else if (c == CPInterfaceMethodref.class) {
            cpInfo = new CPInterfaceMethodref();
         } else if (c == CPString.class) {
            cpInfo = new CPString();
         } else if (c == CPInteger.class) {
            cpInfo = new CPInteger();
         } else if (c == CPFloat.class) {
            cpInfo = new CPFloat();
         } else if (c == CPLong.class) {
            cpInfo = new CPLong();
            ++this.pool_length;
         } else if (c == CPDouble.class) {
            cpInfo = new CPDouble();
            ++this.pool_length;
         } else if (c == CPNameAndType.class) {
            cpInfo = new CPNameAndType();
         } else if (c == CPUtf8.class) {
            cpInfo = new CPUtf8();
         }

         ((CPInfo)cpInfo).init(key);
         this.lookup.put(key, cpInfo);
         this.ordered.add(cpInfo);
         return cpInfo;
      } else {
         return o;
      }
   }

   public CPInfo cpInfoAt(int index) {
      return this.pool[index];
   }

   public CPClass classAt(int idx) throws MalformedClassException {
      try {
         return (CPClass)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Class.");
      }
   }

   public CPFieldref fieldrefAt(int idx) throws MalformedClassException {
      try {
         return (CPFieldref)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Fieldref.");
      }
   }

   public CPMethodref methodrefAt(int idx) throws MalformedClassException {
      try {
         return (CPMethodref)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Methodref.");
      }
   }

   public CPInterfaceMethodref interfaceMethodrefAt(int idx) throws MalformedClassException {
      try {
         return (CPInterfaceMethodref)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_InterfaceMethodref.");
      }
   }

   public CPString stringAt(int idx) throws MalformedClassException {
      try {
         return (CPString)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_String.");
      }
   }

   public CPInteger integerAt(int idx) throws MalformedClassException {
      try {
         return (CPInteger)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Integer.");
      }
   }

   public CPFloat floatAt(int idx) throws MalformedClassException {
      try {
         return (CPFloat)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Float.");
      }
   }

   public CPLong longAt(int idx) throws MalformedClassException {
      try {
         return (CPLong)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Long.");
      }
   }

   public CPDouble doubleAt(int idx) throws MalformedClassException {
      try {
         return (CPDouble)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Double.");
      }
   }

   public CPNameAndType nameAndTypeAt(int idx) throws MalformedClassException {
      try {
         return (CPNameAndType)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_NameAndType.");
      }
   }

   public CPUtf8 utf8At(int idx) throws MalformedClassException {
      try {
         return (CPUtf8)this.pool[idx];
      } catch (ClassCastException var3) {
         throw new MalformedClassException("Entry " + idx + " in constant_pool not a CONSTANT_Utf8.");
      }
   }

   public CPInteger getInteger(int value) {
      return (CPInteger)this.find(value, CPInteger.class);
   }

   public CPFloat getFloat(float value) {
      return (CPFloat)this.find(value, CPFloat.class);
   }

   public CPLong getLong(long value) {
      return (CPLong)this.find(value, CPLong.class);
   }

   public CPDouble getDouble(double value) {
      return (CPDouble)this.find(value, CPDouble.class);
   }

   public CPUtf8 getUtf8(String value) {
      return (CPUtf8)this.find(value, CPUtf8.class);
   }

   public void removeUtf8(String value) {
      Object removed = this.lookup.remove(value);
      this.ordered.remove(removed);
      --this.pool_length;
   }

   public void removeUtf8(CPUtf8 utf8) {
      this.removeUtf8(utf8.getValue());
   }

   public CPClass getClass(CPUtf8 name) {
      DoubleKey key = new DoubleKey(CPClass.class, name);
      Object info = this.find(key, CPClass.class);
      return (CPClass)info;
   }

   public CPClass getClass(String className) {
      return this.getClass(this.getUtf8(className.replace('.', '/')));
   }

   public CPClass getClass(Class clazz) {
      return this.getClass(this.getUtf8(clazz.getName().replace('.', '/')));
   }

   public void removeClass(String className) {
      CPUtf8 utf8 = this.getUtf8(className);
      DoubleKey key = new DoubleKey(CPClass.class, utf8);
      this.poolDirty = true;
      --this.pool_length;
      Object c = this.lookup.remove(key);
      this.ordered.remove(c);
      if (c != null) {
         this.removeUtf8(utf8);
      }

   }

   public CPFieldref getFieldref(CPClass clazz, CPNameAndType name_and_type) {
      DoubleKey key = new DoubleKey(clazz, name_and_type);
      return (CPFieldref)this.find(key, CPFieldref.class);
   }

   public CPFieldref getFieldref(String className, CPNameAndType name_and_type) {
      return this.getFieldref(this.getClass(className), name_and_type);
   }

   public CPFieldref getFieldref(Class clazz, CPNameAndType name_and_type) {
      return this.getFieldref(this.getClass(clazz), name_and_type);
   }

   public CPFieldref getFieldref(CPClass clazz, String name, String descriptor) {
      return this.getFieldref(clazz, this.getNameAndType(name, descriptor));
   }

   public CPFieldref getFieldref(String className, String name, String descriptor) {
      return this.getFieldref(this.getClass(className), this.getNameAndType(name, descriptor));
   }

   public CPFieldref getFieldref(Class clazz, String name, String descriptor) {
      return this.getFieldref(this.getClass(clazz), this.getNameAndType(name, descriptor));
   }

   public CPMethodref getMethodref(CPClass clazz, CPNameAndType name_and_type) {
      DoubleKey key = new DoubleKey(clazz, name_and_type);
      return (CPMethodref)this.find(key, CPMethodref.class);
   }

   public CPMethodref getMethodref(String className, CPNameAndType name_and_type) {
      return this.getMethodref(this.getClass(className), name_and_type);
   }

   public CPMethodref getMethodref(Class clazz, CPNameAndType name_and_type) {
      return this.getMethodref(this.getClass(clazz), name_and_type);
   }

   public CPMethodref getMethodref(CPClass clazz, String name, String descriptor) {
      return this.getMethodref(clazz, this.getNameAndType(name, descriptor));
   }

   public CPMethodref getMethodref(String className, String name, String descriptor) {
      return this.getMethodref(this.getClass(className), this.getNameAndType(name, descriptor));
   }

   public CPMethodref getMethodref(Class clazz, String name, String descriptor) {
      return this.getMethodref(this.getClass(clazz), this.getNameAndType(name, descriptor));
   }

   public CPMethodref getMethodref(Class c, Method m) {
      return this.getMethodref(c, m.getName(), CodeGenHelper.getMethodDescriptor(m));
   }

   public CPMethodref getMethodref(Method m) {
      return this.getMethodref(m.getDeclaringClass(), m);
   }

   public CPInterfaceMethodref getInterfaceMethodref(CPClass clazz, CPNameAndType name_and_type) {
      DoubleKey key = new DoubleKey(clazz, name_and_type);
      return (CPInterfaceMethodref)this.find(key, CPInterfaceMethodref.class);
   }

   public CPInterfaceMethodref getInterfaceMethodref(String className, CPNameAndType name_and_type) {
      return this.getInterfaceMethodref(this.getClass(className), name_and_type);
   }

   public CPInterfaceMethodref getInterfaceMethodref(Class clazz, CPNameAndType name_and_type) {
      return this.getInterfaceMethodref(this.getClass(clazz), name_and_type);
   }

   public CPInterfaceMethodref getInterfaceMethodref(CPClass clazz, String name, String descriptor) {
      return this.getInterfaceMethodref(clazz, this.getNameAndType(name, descriptor));
   }

   public CPInterfaceMethodref getInterfaceMethodref(String className, String name, String descriptor) {
      return this.getInterfaceMethodref(this.getClass(className), this.getNameAndType(name, descriptor));
   }

   public CPInterfaceMethodref getInterfaceMethodref(Class clazz, String name, String descriptor) {
      return this.getInterfaceMethodref(this.getClass(clazz), this.getNameAndType(name, descriptor));
   }

   public CPInterfaceMethodref getInterfaceMethodref(CPClass cls, Method m) {
      return this.getInterfaceMethodref(cls, m.getName(), CodeGenHelper.getMethodDescriptor(m));
   }

   public CPInterfaceMethodref getInterfaceMethodref(Method m) {
      return this.getInterfaceMethodref(this.getClass(m.getDeclaringClass()), m.getName(), CodeGenHelper.getMethodDescriptor(m));
   }

   public CPNameAndType getNameAndType(CPUtf8 name, CPUtf8 descriptor) {
      DoubleKey key = new DoubleKey(name, descriptor);
      return (CPNameAndType)this.find(key, CPNameAndType.class);
   }

   public CPNameAndType getNameAndType(String name, String descriptor) {
      return this.getNameAndType(this.getUtf8(name), this.getUtf8(descriptor));
   }

   public CPString getString(CPUtf8 string) {
      DoubleKey key = new DoubleKey(CPString.class, string);
      return (CPString)this.find(key, CPString.class);
   }

   public CPString getString(String string) {
      return this.getString(this.getUtf8(string));
   }

   public synchronized void read(DataInput in) throws IOException {
      int count = in.readUnsignedShort();
      this.pool = new CPInfo[count];

      int pass;
      int i;
      for(pass = 1; pass < count; ++pass) {
         i = in.readUnsignedByte();
         int idx1;
         switch (i) {
            case 1:
               this.pool[pass] = this.getUtf8(in.readUTF());
               break;
            case 2:
            default:
               throw new Error("Malformed .class file. Bad type (" + i + ") in constant pool.");
            case 3:
               this.pool[pass] = this.getInteger(in.readInt());
               break;
            case 4:
               this.pool[pass] = this.getFloat(in.readFloat());
               break;
            case 5:
               this.pool[pass] = this.getLong(in.readLong());
               ++pass;
               break;
            case 6:
               this.pool[pass] = this.getDouble(in.readDouble());
               ++pass;
               break;
            case 7:
            case 8:
               idx1 = in.readUnsignedShort();
               this.pool[pass] = new UnresolvedCPInfo(i, idx1);
               break;
            case 9:
            case 10:
            case 11:
            case 12:
               idx1 = in.readUnsignedShort();
               int idx2 = in.readUnsignedShort();
               this.pool[pass] = new UnresolvedCPInfo(i, idx1, idx2);
         }
      }

      for(pass = 0; pass < 2; ++pass) {
         for(i = 1; i < count; ++i) {
            if (this.pool[i] instanceof UnresolvedCPInfo) {
               UnresolvedCPInfo tmp = (UnresolvedCPInfo)this.pool[i];
               if (pass == 0) {
                  switch (tmp.getTag()) {
                     case 7:
                        this.pool[i] = this.getClass((CPUtf8)this.pool[tmp.idx1]);
                        break;
                     case 8:
                        this.pool[i] = this.getString((CPUtf8)this.pool[tmp.idx1]);
                        break;
                     case 12:
                        this.pool[i] = this.getNameAndType((CPUtf8)this.pool[tmp.idx1], (CPUtf8)this.pool[tmp.idx2]);
                  }
               } else {
                  switch (tmp.getTag()) {
                     case 9:
                        this.pool[i] = this.getFieldref((CPClass)this.pool[tmp.idx1], (CPNameAndType)this.pool[tmp.idx2]);
                        break;
                     case 10:
                        this.pool[i] = this.getMethodref((CPClass)this.pool[tmp.idx1], (CPNameAndType)this.pool[tmp.idx2]);
                        break;
                     case 11:
                        this.pool[i] = this.getInterfaceMethodref((CPClass)this.pool[tmp.idx1], (CPNameAndType)this.pool[tmp.idx2]);
                        break;
                     default:
                        throw new Error("Internal Error.");
                  }
               }
            }
         }
      }

      this.poolDirty = false;
      this.resolveIndices();
   }

   public void write(DataOutput out) throws IOException {
      this.resolveIndices();
      this.validate();
      out.writeShort(this.pool.length);

      for(int i = 1; i < this.pool.length; ++i) {
         CPInfo entry = this.pool[i];
         out.writeByte(entry.getTag());
         switch (entry.getTag()) {
            case 1:
               out.writeUTF(((CPUtf8)entry).getValue());
               break;
            case 2:
            default:
               throw new Error("Internal error.");
            case 3:
               out.writeInt(((CPInteger)entry).value);
               break;
            case 4:
               out.writeFloat(((CPFloat)entry).value);
               break;
            case 5:
               out.writeLong(((CPLong)entry).value);
               ++i;
               break;
            case 6:
               out.writeDouble(((CPDouble)entry).value);
               ++i;
               break;
            case 7:
               out.writeShort(((CPClass)entry).name.getIndex());
               break;
            case 8:
               out.writeShort(((CPString)entry).utf8.getIndex());
               break;
            case 9:
            case 10:
            case 11:
               CPMemberType member = (CPMemberType)entry;
               out.writeShort(member.clazz.getIndex());
               out.writeShort(member.name_and_type.getIndex());
               break;
            case 12:
               CPNameAndType name_and_type = (CPNameAndType)entry;
               out.writeShort(name_and_type.name.getIndex());
               out.writeShort(name_and_type.descriptor.getIndex());
         }
      }

   }

   public void dump(PrintStream out) {
      this.resolveIndices();
      out.println("constant_pool: (" + this.pool.length + " items)");

      for(int i = 1; i < this.pool.length; ++i) {
         CPInfo entry = this.pool[i];
         out.print("pool[" + i + "]: ");
         out.print("<" + entry.getIndex() + "> " + ConstantPoolTags.name[entry.getTag()] + " ");
         switch (entry.getTag()) {
            case 1:
               out.print(((CPUtf8)entry).getValue());
               break;
            case 2:
            default:
               throw new Error("Internal error.");
            case 3:
               out.print(((CPInteger)entry).value);
               break;
            case 4:
               out.print(((CPFloat)entry).value);
               break;
            case 5:
               out.print(((CPLong)entry).value);
               ++i;
               break;
            case 6:
               out.print(((CPDouble)entry).value);
               ++i;
               break;
            case 7:
               CPClass c = (CPClass)entry;
               out.print(c.name.getValue() + "(" + c.name.getIndex() + ")");
               break;
            case 8:
               CPString s = (CPString)entry;
               out.print(s.utf8.getValue() + "(" + s.utf8.getIndex() + ")");
               break;
            case 9:
            case 10:
            case 11:
               CPMemberType member = (CPMemberType)entry;
               out.print("class=" + member.clazz.name.getValue() + "(" + member.clazz.getIndex() + ")");
               out.print("name_and_type=" + member.name_and_type.name.getValue() + "/" + member.name_and_type.descriptor.getValue() + "(" + member.name_and_type.getIndex() + ")");
               break;
            case 12:
               CPNameAndType name_and_type = (CPNameAndType)entry;
               out.print(name_and_type.name.getValue() + "/" + name_and_type.descriptor.getValue() + "(" + name_and_type.getIndex() + ")");
         }

         out.println();
      }

   }

   public Set dumpClassInfo() {
      this.resolveIndices();
      HashSet set = new HashSet();

      for(int i = 1; i < this.pool.length; ++i) {
         CPInfo entry = this.pool[i];
         if (entry != null && entry.getTag() == 7) {
            CPClass c = (CPClass)entry;
            String name = c.name.getValue().replace('[', ' ').trim();
            if (!name.startsWith("java/") && !name.startsWith("javax/") && !name.startsWith("Ljava/") && !name.startsWith("Ljavax/") && !name.startsWith("weblogic/rmi") && !name.startsWith("Lweblogic/rmi") && !name.startsWith("weblogic/ejb20") && !name.startsWith("Lweblogic/ejb20") && !name.startsWith("weblogic/ejb") && !name.startsWith("Lweblogic/ejb") && !name.startsWith("weblogic/utils") && !name.startsWith("weblogic/jndi")) {
               set.add(name);
            }
         }
      }

      return set;
   }

   private synchronized void resolveIndices() {
      if (this.poolDirty) {
         this.pool = new CPInfo[this.pool_length];
         Iterator entries = this.ordered.iterator();
         int i = 1;

         while(true) {
            CPInfo entry;
            do {
               if (!entries.hasNext()) {
                  return;
               }

               entry = (CPInfo)entries.next();
               entry.setIndex(i++);
               this.pool[entry.getIndex()] = entry;
            } while(!(entry instanceof CPDouble) && !(entry instanceof CPLong));

            ++i;
         }
      } else {
         for(int i = 0; i < this.pool.length; ++i) {
            if (this.pool[i] != null) {
               this.pool[i].setIndex(i);
            }
         }

      }
   }

   public void validate() {
      for(int i = 1; i < this.pool.length; ++i) {
         CPInfo entry = this.pool[i];
         if (entry.getIndex() != i) {
            System.out.println("*** entry at " + i + " hash index " + entry.getIndex() + " ***");
         }

         if (entry instanceof CPDouble || entry instanceof CPLong) {
            ++i;
         }
      }

   }

   public static void say(String s) {
      System.out.println(s);
   }
}
