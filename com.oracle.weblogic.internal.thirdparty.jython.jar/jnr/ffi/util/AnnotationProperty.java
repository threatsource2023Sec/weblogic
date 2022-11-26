package jnr.ffi.util;

import java.util.Arrays;

final class AnnotationProperty {
   private final String name;
   private final Class type;
   private Object value;

   public AnnotationProperty(String name, Class type) {
      this.name = name;
      this.type = type;
   }

   public String getName() {
      return this.name;
   }

   public Class getType() {
      return this.type;
   }

   public Object getValue() {
      return this.value;
   }

   public void setValue(Object value) {
      if (value == null || this.type.isAssignableFrom(value.getClass()) || this.type == Boolean.TYPE && value.getClass() == Boolean.class || this.type == Byte.TYPE && value.getClass() == Byte.class || this.type == Character.TYPE && value.getClass() == Character.class || this.type == Double.TYPE && value.getClass() == Double.class || this.type == Float.TYPE && value.getClass() == Float.class || this.type == Integer.TYPE && value.getClass() == Integer.class || this.type == Long.TYPE && value.getClass() == Long.class || this.type == Short.TYPE && value.getClass() == Short.class) {
         this.value = value;
      } else {
         throw new IllegalArgumentException("Cannot assign value of type '" + value.getClass().getName() + "' to property '" + this.name + "' of type '" + this.type.getName() + "'");
      }
   }

   public int hashCode() {
      int prime = true;
      int result = 1;
      result = 31 * result + this.name.hashCode();
      result = 31 * result + this.type.hashCode();
      result = 31 * result + this.getValueHashCode();
      return result;
   }

   protected int getValueHashCode() {
      if (this.value == null) {
         return 0;
      } else if (!this.type.isArray()) {
         return this.value.hashCode();
      } else if (this.type == byte[].class) {
         return Arrays.hashCode((byte[])((byte[])this.value));
      } else if (this.type == char[].class) {
         return Arrays.hashCode((char[])((char[])this.value));
      } else if (this.type == double[].class) {
         return Arrays.hashCode((double[])((double[])this.value));
      } else if (this.type == float[].class) {
         return Arrays.hashCode((float[])((float[])this.value));
      } else if (this.type == int[].class) {
         return Arrays.hashCode((int[])((int[])this.value));
      } else if (this.type == long[].class) {
         return Arrays.hashCode((long[])((long[])this.value));
      } else if (this.type == short[].class) {
         return Arrays.hashCode((short[])((short[])this.value));
      } else {
         return this.type == boolean[].class ? Arrays.hashCode((boolean[])((boolean[])this.value)) : Arrays.hashCode((Object[])((Object[])this.value));
      }
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         AnnotationProperty other = (AnnotationProperty)obj;
         if (this.name == null) {
            if (other.getName() != null) {
               return false;
            }
         } else if (!this.name.equals(other.getName())) {
            return false;
         }

         if (this.type == null) {
            if (other.getType() != null) {
               return false;
            }
         } else if (!this.type.equals(other.getType())) {
            return false;
         }

         if (this.value == null) {
            if (other.getValue() != null) {
               return false;
            }
         } else {
            if (!this.type.isArray()) {
               return this.value.equals(other.getValue());
            }

            if (this.value instanceof Object[] && other.getValue() instanceof Object[]) {
               Arrays.equals((Object[])((Object[])this.value), (Object[])((Object[])other.getValue()));
            }

            if (this.type == byte[].class) {
               return Arrays.equals((byte[])((byte[])this.value), (byte[])((byte[])other.getValue()));
            }

            if (this.type == char[].class) {
               return Arrays.equals((char[])((char[])this.value), (char[])((char[])other.getValue()));
            }

            if (this.type == double[].class) {
               return Arrays.equals((double[])((double[])this.value), (double[])((double[])other.getValue()));
            }

            if (this.type == float[].class) {
               return Arrays.equals((float[])((float[])this.value), (float[])((float[])other.getValue()));
            }

            if (this.type == int[].class) {
               return Arrays.equals((int[])((int[])this.value), (int[])((int[])other.getValue()));
            }

            if (this.type == long[].class) {
               return Arrays.equals((long[])((long[])this.value), (long[])((long[])other.getValue()));
            }

            if (this.type == short[].class) {
               return Arrays.equals((short[])((short[])this.value), (short[])((short[])other.getValue()));
            }

            if (this.type == boolean[].class) {
               return Arrays.equals((boolean[])((boolean[])this.value), (boolean[])((boolean[])other.getValue()));
            }
         }

         return false;
      }
   }

   public String toString() {
      return "(name=" + this.name + ", type=" + (this.type.isArray() ? this.type.getComponentType().getName() + "[]" : this.type.getName()) + ", value=" + this.valueToString() + ")";
   }

   protected String valueToString() {
      if (!this.type.isArray()) {
         return String.valueOf(this.value);
      } else {
         Class arrayType = this.type.getComponentType();
         if (arrayType == Boolean.TYPE) {
            return Arrays.toString((boolean[])((boolean[])this.value));
         } else if (arrayType == Byte.TYPE) {
            return Arrays.toString((byte[])((byte[])this.value));
         } else if (arrayType == Character.TYPE) {
            return Arrays.toString((char[])((char[])this.value));
         } else if (arrayType == Double.TYPE) {
            return Arrays.toString((double[])((double[])this.value));
         } else if (arrayType == Float.TYPE) {
            return Arrays.toString((float[])((float[])this.value));
         } else if (arrayType == Integer.TYPE) {
            return Arrays.toString((int[])((int[])this.value));
         } else if (arrayType == Long.TYPE) {
            return Arrays.toString((long[])((long[])this.value));
         } else {
            return arrayType == Short.TYPE ? Arrays.toString((short[])((short[])this.value)) : Arrays.toString((Object[])((Object[])this.value));
         }
      }
   }
}
