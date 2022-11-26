package com.bea.util.jam.internal.elements;

import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JAnnotationValue;
import com.bea.util.jam.JClass;
import com.bea.util.jam.internal.classrefs.JClassRef;
import com.bea.util.jam.internal.classrefs.QualifiedJClassRef;

public class AnnotationValueImpl implements JAnnotationValue {
   private Object mValue = null;
   private JClassRef mType = null;
   private String mName;
   private ElementContext mContext;

   public AnnotationValueImpl(ElementContext ctx, String name, Object value, JClass type) {
      if (ctx == null) {
         throw new IllegalArgumentException("null ctx");
      } else if (name == null) {
         throw new IllegalArgumentException("null name");
      } else if (value == null) {
         throw new IllegalArgumentException("null value");
      } else if (type == null) {
         throw new IllegalArgumentException("null type");
      } else {
         if (value.getClass().isArray()) {
            this.mValue = ensureArrayWrapped(value);
         } else {
            this.mValue = value;
         }

         this.mContext = ctx;
         this.mName = name;
         this.mType = QualifiedJClassRef.create(type);
      }
   }

   public boolean isDefaultValueUsed() {
      throw new IllegalStateException("NYI");
   }

   public String getName() {
      return this.mName;
   }

   public JClass getType() {
      return this.mType.getRefClass();
   }

   public JAnnotation asAnnotation() {
      return this.mValue instanceof JAnnotation ? (JAnnotation)this.mValue : null;
   }

   public JClass asClass() {
      return this.mValue instanceof JClass ? (JClass)this.mValue : null;
   }

   public String asString() {
      return this.mValue == null ? null : this.mValue.toString();
   }

   public int asInt() throws NumberFormatException {
      if (this.mValue == null) {
         return 0;
      } else if (this.mValue instanceof Number) {
         return ((Number)this.mValue).intValue();
      } else {
         try {
            return Integer.parseInt(this.mValue.toString().trim());
         } catch (NumberFormatException var2) {
            return 0;
         }
      }
   }

   public boolean asBoolean() throws IllegalArgumentException {
      return this.mValue == null ? false : Boolean.valueOf(this.mValue.toString().trim());
   }

   public long asLong() throws NumberFormatException {
      if (this.mValue == null) {
         return 0L;
      } else if (this.mValue instanceof Number) {
         return ((Number)this.mValue).longValue();
      } else {
         try {
            return Long.parseLong(this.mValue.toString().trim());
         } catch (NumberFormatException var2) {
            return 0L;
         }
      }
   }

   public short asShort() throws NumberFormatException {
      if (this.mValue == null) {
         return 0;
      } else if (this.mValue instanceof Number) {
         return ((Number)this.mValue).shortValue();
      } else {
         try {
            return Short.parseShort(this.mValue.toString().trim());
         } catch (NumberFormatException var2) {
            return 0;
         }
      }
   }

   public double asDouble() throws NumberFormatException {
      if (this.mValue == null) {
         return 0.0;
      } else if (this.mValue instanceof Number) {
         return ((Number)this.mValue).doubleValue();
      } else {
         try {
            return Double.parseDouble(this.mValue.toString().trim());
         } catch (NumberFormatException var2) {
            return 0.0;
         }
      }
   }

   public float asFloat() throws NumberFormatException {
      if (this.mValue == null) {
         return 0.0F;
      } else if (this.mValue instanceof Number) {
         return ((Number)this.mValue).floatValue();
      } else {
         try {
            return Float.parseFloat(this.mValue.toString().trim());
         } catch (NumberFormatException var2) {
            return 0.0F;
         }
      }
   }

   public byte asByte() throws NumberFormatException {
      if (this.mValue == null) {
         return 0;
      } else if (this.mValue instanceof Number) {
         return ((Number)this.mValue).byteValue();
      } else {
         try {
            return Byte.parseByte(this.mValue.toString().trim());
         } catch (NumberFormatException var2) {
            return 0;
         }
      }
   }

   public char asChar() throws IllegalArgumentException {
      if (this.mValue == null) {
         return '\u0000';
      } else if (this.mValue instanceof Character) {
         return (Character)this.mValue;
      } else {
         this.mValue = this.mValue.toString();
         return ((String)this.mValue).length() == 0 ? '\u0000' : ((String)this.mValue).charAt(0);
      }
   }

   public JClass[] asClassArray() {
      return this.mValue instanceof JClass[] ? (JClass[])((JClass[])this.mValue) : null;
   }

   public JAnnotation[] asAnnotationArray() {
      return this.mValue instanceof JAnnotation[] ? (JAnnotation[])((JAnnotation[])this.mValue) : null;
   }

   public String[] asStringArray() {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         String[] out = new String[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element on " + this.getName());
               out[i] = "";
            } else {
               out[i] = ((Object[])((Object[])this.mValue))[i].toString();
            }
         }

         return out;
      }
   }

   public int[] asIntArray() throws NumberFormatException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         int[] out = new int[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = 0;
            } else {
               out[i] = Integer.parseInt(((Object[])((Object[])this.mValue))[i].toString());
            }
         }

         return out;
      }
   }

   public boolean[] asBooleanArray() throws IllegalArgumentException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         boolean[] out = new boolean[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = false;
            } else {
               out[i] = Boolean.valueOf(((Object[])((Object[])this.mValue))[i].toString());
            }
         }

         return out;
      }
   }

   public short[] asShortArray() throws NumberFormatException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         short[] out = new short[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = 0;
            } else {
               out[i] = Short.parseShort(((Object[])((Object[])this.mValue))[i].toString());
            }
         }

         return out;
      }
   }

   public long[] asLongArray() throws NumberFormatException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         long[] out = new long[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = 0L;
            } else {
               out[i] = Long.parseLong(((Object[])((Object[])this.mValue))[i].toString());
            }
         }

         return out;
      }
   }

   public double[] asDoubleArray() throws NumberFormatException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         double[] out = new double[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = 0.0;
            } else {
               out[i] = Double.parseDouble(((Object[])((Object[])this.mValue))[i].toString());
            }
         }

         return out;
      }
   }

   public float[] asFloatArray() throws NumberFormatException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         float[] out = new float[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = 0.0F;
            } else {
               out[i] = Float.parseFloat(((Object[])((Object[])this.mValue))[i].toString());
            }
         }

         return out;
      }
   }

   public byte[] asByteArray() throws NumberFormatException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         byte[] out = new byte[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = 0;
            } else {
               out[i] = Byte.parseByte(((Object[])((Object[])this.mValue))[i].toString());
            }
         }

         return out;
      }
   }

   public char[] asCharArray() throws IllegalArgumentException {
      if (!this.mValue.getClass().isArray()) {
         return null;
      } else {
         char[] out = new char[((Object[])((Object[])this.mValue)).length];

         for(int i = 0; i < out.length; ++i) {
            if (((Object[])((Object[])this.mValue))[i] == null) {
               this.mContext.getLogger().error("Null annotation value array element " + i + " on " + this.getName());
               out[i] = 0;
            } else {
               out[i] = ((Object[])((Object[])this.mValue))[i].toString().charAt(0);
            }
         }

         return out;
      }
   }

   private static final Object[] ensureArrayWrapped(Object o) {
      if (o instanceof Object[]) {
         return (Object[])((Object[])o);
      } else {
         int dims;
         int i;
         if (o instanceof int[]) {
            dims = ((int[])((int[])o)).length;
            Integer[] out = new Integer[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = new Integer(((int[])((int[])o))[i]);
            }

            return out;
         } else if (o instanceof boolean[]) {
            dims = ((boolean[])((boolean[])o)).length;
            Boolean[] out = new Boolean[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = ((boolean[])((boolean[])o))[i];
            }

            return out;
         } else if (o instanceof byte[]) {
            dims = ((byte[])((byte[])o)).length;
            Byte[] out = new Byte[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = new Byte(((byte[])((byte[])o))[i]);
            }

            return out;
         } else if (o instanceof char[]) {
            dims = ((char[])((char[])o)).length;
            Character[] out = new Character[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = new Character(((char[])((char[])o))[i]);
            }

            return out;
         } else if (o instanceof float[]) {
            dims = ((float[])((float[])o)).length;
            Float[] out = new Float[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = new Float(((float[])((float[])o))[i]);
            }

            return out;
         } else if (o instanceof double[]) {
            dims = ((double[])((double[])o)).length;
            Double[] out = new Double[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = new Double(((double[])((double[])o))[i]);
            }

            return out;
         } else if (o instanceof long[]) {
            dims = ((long[])((long[])o)).length;
            Long[] out = new Long[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = new Long(((long[])((long[])o))[i]);
            }

            return out;
         } else if (!(o instanceof short[])) {
            throw new IllegalStateException("Unknown array type " + o.getClass());
         } else {
            dims = ((short[])((short[])o)).length;
            Short[] out = new Short[dims];

            for(i = 0; i < dims; ++i) {
               out[i] = new Short(((short[])((short[])o))[i]);
            }

            return out;
         }
      }
   }

   public Object getValue() {
      return this.mValue;
   }
}
