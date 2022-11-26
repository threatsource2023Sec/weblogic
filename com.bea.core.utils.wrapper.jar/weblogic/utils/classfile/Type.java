package weblogic.utils.classfile;

import weblogic.utils.AssertionError;
import weblogic.utils.classfile.cp.CPClass;
import weblogic.utils.classfile.cp.CPMethodref;
import weblogic.utils.classfile.cp.ConstantPool;
import weblogic.utils.classfile.expr.CastExpression;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.InvokeExpression;

public class Type {
   private static final int TC_INVALID = 0;
   private static final int TC_BOOLEAN = 1;
   private static final int TC_BYTE = 2;
   private static final int TC_CHARACTER = 3;
   private static final int TC_SHORT = 4;
   private static final int TC_INT = 5;
   private static final int TC_FLOAT = 6;
   private static final int TC_LONG = 7;
   private static final int TC_DOUBLE = 8;
   private static final int TC_OBJECT = 9;
   private static final int TC_VOID = 10;
   private static final int TC_ARRAY = 11;
   public static final Type INVALID = new Type(0);
   public static final Type BOOLEAN = new Type(1);
   public static final Type BYTE = new Type(2);
   public static final Type CHARACTER = new Type(3);
   public static final Type SHORT = new Type(4);
   public static final Type INT = new Type(5);
   public static final Type FLOAT = new Type(6);
   public static final Type LONG = new Type(7);
   public static final Type DOUBLE = new Type(8);
   public static final Type OBJECT = new Type(9);
   public static final Type VOID = new Type(10);
   public static final Type ARRAY = new Type(11);
   private static final String[] CLASS_NAMES = new String[]{"INVALID", "java/lang/Boolean", "java/lang/Byte", "java/lang/Character", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", "java/lang/Object", "java/lang/Void", "java/lang/reflect/Array"};
   private int tc;

   private Type(int tc) {
      this.tc = tc;
   }

   public static Type getType(Class c) {
      if (c == Boolean.TYPE) {
         return BOOLEAN;
      } else if (c == Byte.TYPE) {
         return BYTE;
      } else if (c == Character.TYPE) {
         return CHARACTER;
      } else if (c == Short.TYPE) {
         return SHORT;
      } else if (c == Integer.TYPE) {
         return INT;
      } else if (c == Float.TYPE) {
         return FLOAT;
      } else if (c == Long.TYPE) {
         return LONG;
      } else if (c == Double.TYPE) {
         return DOUBLE;
      } else if (c == Void.TYPE) {
         return VOID;
      } else {
         return c.isArray() ? ARRAY : OBJECT;
      }
   }

   public static Type getType(String descriptor) {
      switch (descriptor.charAt(0)) {
         case 'B':
            return BYTE;
         case 'C':
            return CHARACTER;
         case 'D':
            return DOUBLE;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new AssertionError("Unknown type: " + descriptor);
         case 'F':
            return FLOAT;
         case 'I':
            return INT;
         case 'J':
            return LONG;
         case 'L':
            return OBJECT;
         case 'S':
            return SHORT;
         case 'V':
            return VOID;
         case 'Z':
            return BOOLEAN;
         case '[':
            return ARRAY;
      }
   }

   public boolean isWide() {
      return this.tc == 8 || this.tc == 7;
   }

   public String getClassName() {
      return CLASS_NAMES[this.tc];
   }

   public CPMethodref getConstructor(ConstantPool cp) {
      switch (this.tc) {
         case 1:
            return cp.getMethodref(this.getClassName(), "<init>", "(Z)V");
         case 2:
            return cp.getMethodref(this.getClassName(), "<init>", "(B)V");
         case 3:
            return cp.getMethodref(this.getClassName(), "<init>", "(C)V");
         case 4:
            return cp.getMethodref(this.getClassName(), "<init>", "(S)V");
         case 5:
            return cp.getMethodref(this.getClassName(), "<init>", "(I)V");
         case 6:
            return cp.getMethodref(this.getClassName(), "<init>", "(F)V");
         case 7:
            return cp.getMethodref(this.getClassName(), "<init>", "(J)V");
         case 8:
            return cp.getMethodref(this.getClassName(), "<init>", "(D)V");
         default:
            throw new AssertionError("cannot get constructor for: " + this);
      }
   }

   public Class getJavaClass() {
      try {
         return Class.forName(this.getClassName().replace('/', '.'));
      } catch (ClassNotFoundException var2) {
         throw new AssertionError(var2);
      }
   }

   public static Expression toPrimitive(ConstantPool cp, Type type, Expression o) {
      CPClass clz = cp.getClass(type.getClassName());
      CPMethodref meth;
      if (type == INT) {
         meth = cp.getMethodref(clz, "intValue", "()I");
      } else if (type == BYTE) {
         meth = cp.getMethodref(clz, "byteValue", "()B");
      } else if (type == BOOLEAN) {
         meth = cp.getMethodref(clz, "booleanValue", "()Z");
      } else if (type == SHORT) {
         meth = cp.getMethodref(clz, "shortValue", "()S");
      } else if (type == FLOAT) {
         meth = cp.getMethodref(clz, "floatValue", "()F");
      } else if (type == CHARACTER) {
         meth = cp.getMethodref(clz, "charValue", "()C");
      } else if (type == LONG) {
         meth = cp.getMethodref(clz, "longValue", "()J");
      } else {
         if (type != DOUBLE) {
            throw new AssertionError("cannot get primtive type of : " + type);
         }

         meth = cp.getMethodref(clz, "doubleValue", "()D");
      }

      return new InvokeExpression(meth, new CastExpression(type.getJavaClass(), o), new Expression[0]);
   }

   public String toString() {
      switch (this.tc) {
         case 1:
            return "boolean";
         case 2:
            return "byte";
         case 3:
            return "char";
         case 4:
            return "short";
         case 5:
            return "int";
         case 6:
            return "float";
         case 7:
            return "long";
         case 8:
            return "double";
         case 9:
            return "Object";
         case 10:
            return "VOID";
         case 11:
            return "ARRAY";
         default:
            return "INVALID";
      }
   }
}
