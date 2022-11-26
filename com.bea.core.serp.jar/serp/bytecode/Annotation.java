package serp.bytecode;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import serp.bytecode.lowlevel.ConstantEntry;
import serp.bytecode.lowlevel.ConstantPool;
import serp.bytecode.lowlevel.DoubleEntry;
import serp.bytecode.lowlevel.Entry;
import serp.bytecode.lowlevel.FloatEntry;
import serp.bytecode.lowlevel.IntEntry;
import serp.bytecode.lowlevel.LongEntry;
import serp.bytecode.lowlevel.UTF8Entry;
import serp.bytecode.visitor.BCVisitor;
import serp.bytecode.visitor.VisitAcceptor;
import serp.util.Strings;

public class Annotation implements BCEntity, VisitAcceptor {
   private static Method ENUM_VALUEOF = null;
   private static Method ENUM_NAME = null;
   private BCEntity _owner = null;
   private int _typeIndex = 0;
   private List _properties = null;

   Annotation(BCEntity owner) {
      this._owner = owner;
   }

   public BCEntity getOwner() {
      return this._owner;
   }

   void invalidate() {
      this._owner = null;
   }

   public int getTypeIndex() {
      return this._typeIndex;
   }

   public void setTypeIndex(int index) {
      this._typeIndex = index;
   }

   public String getTypeName() {
      String desc = ((UTF8Entry)this.getPool().getEntry(this._typeIndex)).getValue();
      return this.getProject().getNameCache().getExternalForm(desc, false);
   }

   public Class getType() {
      return Strings.toClass(this.getTypeName(), this.getClassLoader());
   }

   public BCClass getTypeBC() {
      return this.getProject().loadClass(this.getTypeName(), this.getClassLoader());
   }

   public void setType(String type) {
      type = this.getProject().getNameCache().getInternalForm(type, true);
      this._typeIndex = this.getPool().findUTF8Entry(type, true);
   }

   public void setType(Class type) {
      this.setType(type.getName());
   }

   public void setType(BCClass type) {
      this.setType(type.getName());
   }

   public Property[] getProperties() {
      return this._properties == null ? new Property[0] : (Property[])((Property[])this._properties.toArray(new Property[this._properties.size()]));
   }

   public void setProperties(Property[] props) {
      this.clearProperties();
      if (props != null) {
         for(int i = 0; i < props.length; ++i) {
            this.addProperty(props[i]);
         }
      }

   }

   public Property getProperty(String name) {
      if (this._properties == null) {
         return null;
      } else {
         for(int i = 0; i < this._properties.size(); ++i) {
            Property prop = (Property)this._properties.get(i);
            if (prop.getName().equals(name)) {
               return prop;
            }
         }

         return null;
      }
   }

   public Property addProperty(Property p) {
      Property prop = this.addProperty(p.getName());
      prop.setValue(p.getValue());
      return prop;
   }

   public Property addProperty(String name) {
      Property prop = new Property(this);
      prop.setName(name);
      if (this._properties == null) {
         this._properties = new ArrayList();
      }

      this._properties.add(prop);
      return prop;
   }

   public void clearProperties() {
      if (this._properties != null) {
         for(int i = 0; i < this._properties.size(); ++i) {
            ((Property)this._properties.get(i)).invalidate();
         }

         this._properties.clear();
      }
   }

   public boolean removeProperty(Property prop) {
      return prop != null && this.removeProperty(prop.getName());
   }

   public boolean removeProperty(String name) {
      if (name != null && this._properties != null) {
         for(int i = 0; i < this._properties.size(); ++i) {
            Property prop = (Property)this._properties.get(i);
            if (prop.getName().equals(name)) {
               prop.invalidate();
               this._properties.remove(i);
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public Project getProject() {
      return this._owner.getProject();
   }

   public ConstantPool getPool() {
      return this._owner.getPool();
   }

   public ClassLoader getClassLoader() {
      return this._owner.getClassLoader();
   }

   public boolean isValid() {
      return this._owner != null;
   }

   public void acceptVisit(BCVisitor visit) {
      visit.enterAnnotation(this);
      if (this._properties != null) {
         for(int i = 0; i < this._properties.size(); ++i) {
            ((Property)this._properties.get(i)).acceptVisit(visit);
         }
      }

      visit.exitAnnotation(this);
   }

   int getLength() {
      int len = 4;
      if (this._properties != null) {
         for(int i = 0; i < this._properties.size(); ++i) {
            len += ((Property)this._properties.get(i)).getLength();
         }
      }

      return len;
   }

   void read(DataInput in) throws IOException {
      this._typeIndex = in.readUnsignedShort();
      this.clearProperties();
      int props = in.readUnsignedShort();
      if (props > 0) {
         if (this._properties == null) {
            this._properties = new ArrayList(props);
         }

         for(int i = 0; i < props; ++i) {
            Property prop = new Property(this);
            prop.read(in);
            this._properties.add(prop);
         }
      }

   }

   void write(DataOutput out) throws IOException {
      out.writeShort(this._typeIndex);
      out.writeShort(this._properties == null ? 0 : this._properties.size());
      if (this._properties != null) {
         for(int i = 0; i < this._properties.size(); ++i) {
            ((Property)this._properties.get(i)).write(out);
         }
      }

   }

   static {
      try {
         Class c = Class.forName("java.lang.Enum");
         ENUM_VALUEOF = c.getMethod("valueOf", Class.class, String.class);
         ENUM_NAME = c.getMethod("name", (Class[])null);
      } catch (Throwable var1) {
      }

   }

   public static class Property implements BCEntity, VisitAcceptor {
      private Annotation _owner = null;
      private int _nameIndex = 0;
      private final Value _value = new Value();
      private Value[] _values = null;

      Property(Annotation owner) {
         this._owner = owner;
      }

      public Annotation getAnnotation() {
         return this._owner;
      }

      void invalidate() {
         this._owner = null;
      }

      public int getNameIndex() {
         return this._nameIndex;
      }

      public void setNameIndex(int index) {
         this._nameIndex = index;
      }

      public String getName() {
         return ((UTF8Entry)this.getPool().getEntry(this._nameIndex)).getValue();
      }

      public void setName(String name) {
         this._nameIndex = this.getPool().findUTF8Entry(name, true);
      }

      public Object getValue() {
         if (this._values == null) {
            return this.getValue(this._value);
         } else {
            Object[] vals = new Object[this._values.length];

            for(int i = 0; i < vals.length; ++i) {
               vals[i] = this.getValue(this._values[i]);
            }

            return vals;
         }
      }

      private Object getValue(Value val) {
         if (val.index == -1) {
            return val.value;
         } else {
            Object o = ((ConstantEntry)this.getPool().getEntry(val.index)).getConstant();
            if (val.index2 != -1) {
               String e = this.getProject().getNameCache().getExternalForm((String)o, false);
               String name = ((UTF8Entry)this.getPool().getEntry(val.index2)).getValue();

               try {
                  Class cls = Class.forName(e, true, this.getClassLoader());
                  return Annotation.ENUM_VALUEOF.invoke((Object)null, cls, name);
               } catch (Throwable var6) {
                  return e + "." + name;
               }
            } else if (val.type == null) {
               return o;
            } else {
               switch (val.type.getName().charAt(0)) {
                  case 'b':
                     if (val.type == Boolean.TYPE) {
                        return ((Number)o).intValue() != 0 ? Boolean.TRUE : Boolean.FALSE;
                     }

                     return new Byte(((Number)o).byteValue());
                  case 'c':
                     return new Character((char)((Number)o).intValue());
                  case 'j':
                     return this.getProject().getNameCache().getExternalForm((String)o, false);
                  case 's':
                     return new Short(((Number)o).shortValue());
                  default:
                     return o;
               }
            }
         }
      }

      public void setValue(Object value) {
         if (!value.getClass().isArray()) {
            this._values = null;
            this.setValue(this._value, value);
         } else {
            this._value.value = null;
            this._values = new Value[Array.getLength(value)];

            for(int i = 0; i < this._values.length; ++i) {
               this._values[i] = new Value();
               this.setValue(this._values[i], Array.get(value, i));
            }
         }

      }

      private void setValue(Value val, Object o) {
         if (o instanceof String) {
            this.setValue(val, (String)o);
         } else if (o instanceof Boolean) {
            this.setValue(val, (Boolean)o);
         } else if (o instanceof Byte) {
            this.setValue(val, (Byte)o);
         } else if (o instanceof Character) {
            this.setValue(val, (int)(Character)o);
         } else if (o instanceof Double) {
            this.setValue(val, (Double)o);
         } else if (o instanceof Float) {
            this.setValue(val, (Float)o);
         } else if (o instanceof Integer) {
            this.setValue(val, (Integer)o);
         } else if (o instanceof Long) {
            this.setValue(val, (Long)o);
         } else if (o instanceof Short) {
            this.setValue(val, (Short)o);
         } else if (o instanceof Class) {
            this.setClassNameValue(val, ((Class)o).getName());
         } else if (o instanceof BCClass) {
            this.setClassNameValue(val, ((BCClass)o).getName());
         } else if (o instanceof Annotation) {
            this.setValue(val, (Annotation)o);
         } else {
            String name = getEnumName(o);
            if (name != null) {
               String type = this.getProject().getNameCache().getInternalForm(o.getClass().getName(), true);
               val.index = this.getPool().findUTF8Entry(type, true);
               val.index2 = this.getPool().findUTF8Entry(name, true);
               val.value = null;
               val.type = null;
            } else {
               val.index = -1;
               val.index2 = -1;
               val.value = o;
               val.type = o.getClass();
            }
         }

      }

      private static String getEnumName(Object o) {
         for(Class c = o.getClass(); c != Object.class && c != null; c = c.getSuperclass()) {
            if ("java.lang.Enum".equals(c.getName())) {
               try {
                  return (String)Annotation.ENUM_NAME.invoke(o, (Object[])null);
               } catch (Throwable var2) {
                  return o.toString();
               }
            }
         }

         return null;
      }

      public String getStringValue() {
         return (String)this.getValue();
      }

      public boolean getBooleanValue() {
         Object value = this.getValue();
         return value == null ? false : (Boolean)value;
      }

      public byte getByteValue() {
         Object value = this.getValue();
         return value == null ? 0 : ((Number)value).byteValue();
      }

      public int getIntValue() {
         Object value = this.getValue();
         return value == null ? 0 : ((Number)value).intValue();
      }

      public long getLongValue() {
         Object value = this.getValue();
         return value == null ? 0L : ((Number)value).longValue();
      }

      public float getFloatValue() {
         Object value = this.getValue();
         return value == null ? 0.0F : ((Number)value).floatValue();
      }

      public double getDoubleValue() {
         Object value = this.getValue();
         return value == null ? 0.0 : ((Number)value).doubleValue();
      }

      public short getShortValue() {
         Object value = this.getValue();
         return value == null ? 0 : ((Number)value).shortValue();
      }

      public String getClassNameValue() {
         return (String)this.getValue();
      }

      public Annotation getAnnotationValue() {
         return (Annotation)this.getValue();
      }

      public void setValue(String value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, String o) {
         val.index = this.getPool().findUTF8Entry(o, true);
         val.index2 = -1;
         val.value = null;
         val.type = null;
      }

      public void setValue(boolean value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, boolean o) {
         this.setValue(val, o ? 1 : 0);
         val.type = Boolean.TYPE;
      }

      public void setValue(byte value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, byte o) {
         this.setValue(val, (int)o);
         val.type = Byte.TYPE;
      }

      public void setValue(int value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, int o) {
         val.index = this.getPool().findIntEntry(o, true);
         val.index2 = -1;
         val.value = null;
         val.type = null;
      }

      public void setValue(long value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, long o) {
         val.index = this.getPool().findLongEntry(o, true);
         val.index2 = -1;
         val.value = null;
         val.type = null;
      }

      public void setValue(float value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, float o) {
         val.index = this.getPool().findFloatEntry(o, true);
         val.index2 = -1;
         val.value = null;
         val.type = null;
      }

      public void setValue(double value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, double o) {
         val.index = this.getPool().findDoubleEntry(o, true);
         val.index2 = -1;
         val.value = null;
         val.type = null;
      }

      public void setValue(short value) {
         this._values = null;
         this.setValue(this._value, value);
      }

      private void setValue(Value val, short o) {
         this.setValue(val, (int)o);
         val.type = Short.TYPE;
      }

      public void setValue(Class value) {
         this.setClassNameValue(value.getName());
      }

      public void setValue(BCClass value) {
         this.setClassNameValue(value.getName());
      }

      public void setClassNameValue(String value) {
         this._values = null;
         this.setClassNameValue(this._value, value);
      }

      private void setClassNameValue(Value val, String o) {
         o = this.getProject().getNameCache().getInternalForm(o, true);
         val.index = this.getPool().findUTF8Entry(o, true);
         val.index2 = -1;
         val.value = null;
         val.type = Class.class;
      }

      public Annotation setValue(Annotation value) {
         this._values = null;
         return this.setValue(this._value, value);
      }

      private Annotation setValue(Value val, Annotation o) {
         Annotation anno = new Annotation(this);
         anno.setType(o.getTypeName());
         anno.setProperties(o.getProperties());
         val.index = -1;
         val.index2 = -1;
         val.value = anno;
         val.type = null;
         return anno;
      }

      public Annotation[] setValue(Annotation[] value) {
         this._value.value = null;
         this._values = new Value[value.length];
         Annotation[] ret = new Annotation[value.length];

         for(int i = 0; i < this._values.length; ++i) {
            this._values[i] = new Value();
            ret[i] = this.setValue(this._values[i], value[i]);
         }

         return ret;
      }

      public Annotation newAnnotationValue(Class type) {
         return this.newAnnotationValue(type.getName());
      }

      public Annotation newAnnotationValue(BCClass type) {
         return this.newAnnotationValue(type.getName());
      }

      public Annotation newAnnotationValue(String type) {
         Annotation anno = new Annotation(this);
         anno.setType(type);
         this._values = null;
         this._value.index = -1;
         this._value.index2 = -1;
         this._value.value = anno;
         this._value.type = null;
         return anno;
      }

      public Annotation[] newAnnotationArrayValue(Class type, int length) {
         return this.newAnnotationArrayValue(type.getName(), length);
      }

      public Annotation[] newAnnotationArrayValue(BCClass type, int length) {
         return this.newAnnotationArrayValue(type.getName(), length);
      }

      public Annotation[] newAnnotationArrayValue(String type, int length) {
         this._value.value = null;
         this._values = new Value[length];
         Annotation[] ret = new Annotation[length];

         for(int i = 0; i < length; ++i) {
            ret[i] = new Annotation(this);
            ret[i].setType(type);
            this._values[i] = new Value();
            this._values[i].index = -1;
            this._values[i].index2 = -1;
            this._values[i].value = ret[i];
            this._values[i].type = null;
         }

         return ret;
      }

      public Project getProject() {
         return this._owner.getProject();
      }

      public ConstantPool getPool() {
         return this._owner.getPool();
      }

      public ClassLoader getClassLoader() {
         return this._owner.getClassLoader();
      }

      public boolean isValid() {
         return this._owner != null && (this._values != null || this._value.index != -1 || this._value.value != null);
      }

      public void acceptVisit(BCVisitor visit) {
         visit.enterAnnotationProperty(this);
         visit.exitAnnotationProperty(this);
      }

      int getLength() {
         if (!this.isValid()) {
            throw new IllegalStateException();
         } else {
            int len = 2;
            if (this._values == null) {
               len += this.getLength(this._value);
            } else {
               len += 3;

               for(int i = 0; i < this._values.length; ++i) {
                  len += this.getLength(this._values[i]);
               }
            }

            return len;
         }
      }

      private int getLength(Value val) {
         if (val.index2 != -1) {
            return 5;
         } else {
            return val.index != -1 ? 3 : 1 + ((Annotation)val.value).getLength();
         }
      }

      void read(DataInput in) throws IOException {
         this._nameIndex = in.readUnsignedShort();
         int tag = in.readByte();
         if (tag == 91) {
            int len = in.readUnsignedShort();
            this._values = new Value[len];

            for(int i = 0; i < len; ++i) {
               this._values[i] = new Value();
               this.read(this._values[i], in.readByte(), in);
            }
         } else {
            this.read(this._value, tag, in);
         }

      }

      private void read(Value val, int tag, DataInput in) throws IOException {
         switch (tag) {
            case 64:
               Annotation anno = new Annotation(this);
               anno.read(in);
               val.index = -1;
               val.index2 = -1;
               val.value = anno;
               val.type = null;
               break;
            case 66:
               val.index = in.readUnsignedShort();
               val.index2 = -1;
               val.value = null;
               val.type = Byte.TYPE;
               break;
            case 67:
               val.index = in.readUnsignedShort();
               val.index2 = -1;
               val.value = null;
               val.type = Character.TYPE;
               break;
            case 68:
            case 70:
            case 73:
            case 74:
            case 83:
            case 115:
               val.index = in.readUnsignedShort();
               val.index2 = -1;
               val.value = null;
               val.type = null;
               break;
            case 90:
               val.index = in.readUnsignedShort();
               val.index2 = -1;
               val.value = null;
               val.type = Boolean.TYPE;
               break;
            case 99:
               val.index = in.readUnsignedShort();
               val.index2 = -1;
               val.value = null;
               val.type = Class.class;
               break;
            case 101:
               val.index = in.readUnsignedShort();
               val.index2 = in.readUnsignedShort();
               val.value = null;
               val.type = null;
               break;
            default:
               throw new IllegalStateException(String.valueOf(tag));
         }

      }

      void write(DataOutput out) throws IOException {
         if (!this.isValid()) {
            throw new IllegalStateException();
         } else {
            out.writeShort(this._nameIndex);
            if (this._values == null) {
               this.write(this._value, out);
            } else {
               out.writeByte(91);
               out.writeShort(this._values.length);

               for(int i = 0; i < this._values.length; ++i) {
                  this.write(this._values[i], out);
               }
            }

         }
      }

      private void write(Value val, DataOutput out) throws IOException {
         if (val.index2 != -1) {
            out.writeByte(101);
            out.writeShort(val.index);
            out.writeShort(val.index2);
         } else if (val.index != -1) {
            if (val.type != null) {
               switch (val.type.getName().charAt(0)) {
                  case 'b':
                     if (val.type == Byte.TYPE) {
                        out.writeByte(66);
                     } else {
                        out.writeByte(90);
                     }
                     break;
                  case 'c':
                     out.writeByte(67);
                     break;
                  case 'j':
                     out.writeByte(99);
                     break;
                  case 's':
                     out.writeByte(83);
                     break;
                  default:
                     throw new IllegalStateException(val.type.getName());
               }
            } else {
               Entry entry = this.getPool().getEntry(val.index);
               if (entry instanceof DoubleEntry) {
                  out.writeByte(68);
               } else if (entry instanceof FloatEntry) {
                  out.writeByte(70);
               } else if (entry instanceof IntEntry) {
                  out.writeByte(73);
               } else if (entry instanceof LongEntry) {
                  out.writeByte(74);
               } else {
                  if (!(entry instanceof UTF8Entry)) {
                     throw new IllegalStateException(entry.getClass().getName());
                  }

                  out.writeByte(115);
               }
            }

            out.writeShort(val.index);
         } else {
            out.writeByte(64);
            ((Annotation)val.value).write(out);
         }

      }

      private static class Value {
         public int index;
         public int index2;
         public Class type;
         public Object value;

         private Value() {
            this.index = -1;
            this.index2 = -1;
            this.type = null;
            this.value = null;
         }

         // $FF: synthetic method
         Value(Object x0) {
            this();
         }
      }
   }
}
