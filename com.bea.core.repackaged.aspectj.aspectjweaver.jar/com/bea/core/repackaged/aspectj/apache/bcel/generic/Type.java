package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.ConstantsInitializer;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ClassFormatException;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Type {
   protected byte type;
   protected String signature;
   public static final BasicType VOID = new BasicType((byte)12);
   public static final BasicType BOOLEAN = new BasicType((byte)4);
   public static final BasicType INT = new BasicType((byte)10);
   public static final BasicType SHORT = new BasicType((byte)9);
   public static final BasicType BYTE = new BasicType((byte)8);
   public static final BasicType LONG = new BasicType((byte)11);
   public static final BasicType DOUBLE = new BasicType((byte)7);
   public static final BasicType FLOAT = new BasicType((byte)6);
   public static final BasicType CHAR = new BasicType((byte)5);
   public static final ObjectType OBJECT = new ObjectType("java.lang.Object");
   public static final ObjectType STRING = new ObjectType("java.lang.String");
   public static final ObjectType OBJECT_ARRAY = new ObjectType("java.lang.Object[]");
   public static final ObjectType STRING_ARRAY = new ObjectType("java.lang.String[]");
   public static final ObjectType STRINGBUFFER = new ObjectType("java.lang.StringBuffer");
   public static final ObjectType STRINGBUILDER = new ObjectType("java.lang.StringBuilder");
   public static final ObjectType THROWABLE = new ObjectType("java.lang.Throwable");
   public static final ObjectType CLASS = new ObjectType("java.lang.Class");
   public static final ObjectType INTEGER = new ObjectType("java.lang.Integer");
   public static final ObjectType EXCEPTION = new ObjectType("java.lang.Exception");
   public static final ObjectType LIST = new ObjectType("java.util.List");
   public static final ObjectType ITERATOR = new ObjectType("java.util.Iterator");
   public static final Type[] NO_ARGS = new Type[0];
   public static final ReferenceType NULL = new ReferenceType() {
   };
   public static final Type UNKNOWN = new Type(15, "<unknown object>") {
   };
   public static final Type[] STRINGARRAY1;
   public static final Type[] STRINGARRAY2;
   public static final Type[] STRINGARRAY3;
   public static final Type[] STRINGARRAY4;
   public static final Type[] STRINGARRAY5;
   public static final Type[] STRINGARRAY6;
   public static final Type[] STRINGARRAY7;
   private static Map commonTypes;

   static {
      STRINGARRAY1 = new Type[]{STRING};
      STRINGARRAY2 = new Type[]{STRING, STRING};
      STRINGARRAY3 = new Type[]{STRING, STRING, STRING};
      STRINGARRAY4 = new Type[]{STRING, STRING, STRING, STRING};
      STRINGARRAY5 = new Type[]{STRING, STRING, STRING, STRING, STRING};
      STRINGARRAY6 = new Type[]{STRING, STRING, STRING, STRING, STRING, STRING};
      STRINGARRAY7 = new Type[]{STRING, STRING, STRING, STRING, STRING, STRING, STRING};
      commonTypes = new HashMap();
      commonTypes.put(STRING.getSignature(), STRING);
      commonTypes.put(THROWABLE.getSignature(), THROWABLE);
      commonTypes.put(VOID.getSignature(), VOID);
      commonTypes.put(BOOLEAN.getSignature(), BOOLEAN);
      commonTypes.put(BYTE.getSignature(), BYTE);
      commonTypes.put(SHORT.getSignature(), SHORT);
      commonTypes.put(CHAR.getSignature(), CHAR);
      commonTypes.put(INT.getSignature(), INT);
      commonTypes.put(LONG.getSignature(), LONG);
      commonTypes.put(DOUBLE.getSignature(), DOUBLE);
      commonTypes.put(FLOAT.getSignature(), FLOAT);
      commonTypes.put(CLASS.getSignature(), CLASS);
      commonTypes.put(OBJECT.getSignature(), OBJECT);
      commonTypes.put(STRING_ARRAY.getSignature(), STRING_ARRAY);
      commonTypes.put(OBJECT_ARRAY.getSignature(), OBJECT_ARRAY);
      commonTypes.put(INTEGER.getSignature(), INTEGER);
      commonTypes.put(EXCEPTION.getSignature(), EXCEPTION);
      commonTypes.put(STRINGBUFFER.getSignature(), STRINGBUFFER);
      commonTypes.put(STRINGBUILDER.getSignature(), STRINGBUILDER);
      commonTypes.put(LIST.getSignature(), LIST);
      commonTypes.put(ITERATOR.getSignature(), ITERATOR);
      ConstantsInitializer.initialize();
   }

   protected Type(byte t, String s) {
      this.type = t;
      this.signature = s;
   }

   public String getSignature() {
      return this.signature;
   }

   public byte getType() {
      return this.type;
   }

   public int getSize() {
      switch (this.type) {
         case 7:
         case 11:
            return 2;
         case 8:
         case 9:
         case 10:
         default:
            return 1;
         case 12:
            return 0;
      }
   }

   public String toString() {
      return !this.equals(NULL) && this.type < 15 ? Utility.signatureToString(this.signature, false) : this.signature;
   }

   public static final Type getType(String signature) {
      Type t = (Type)commonTypes.get(signature);
      if (t != null) {
         return t;
      } else {
         byte type = Utility.typeOfSignature(signature);
         if (type <= 12) {
            return BasicType.getType(type);
         } else {
            int nextAngly;
            if (type == 13) {
               nextAngly = 0;

               do {
                  ++nextAngly;
               } while(signature.charAt(nextAngly) == '[');

               Type componentType = getType(signature.substring(nextAngly));
               return new ArrayType(componentType, nextAngly);
            } else {
               nextAngly = signature.indexOf(60);
               int index = signature.indexOf(59);
               String typeString = null;
               if (nextAngly != -1 && nextAngly <= index) {
                  boolean endOfSigReached = false;
                  int posn = nextAngly;
                  int genericDepth = 0;

                  while(!endOfSigReached) {
                     switch (signature.charAt(posn++)) {
                        case ';':
                           if (genericDepth == 0) {
                              endOfSigReached = true;
                           }
                           break;
                        case '<':
                           ++genericDepth;
                        case '=':
                        default:
                           break;
                        case '>':
                           --genericDepth;
                     }
                  }

                  index = posn - 1;
                  typeString = signature.substring(1, nextAngly).replace('/', '.');
               } else {
                  typeString = signature.substring(1, index).replace('/', '.');
               }

               return new ObjectType(typeString);
            }
         }
      }
   }

   public static final TypeHolder getTypeInternal(String signature) throws StringIndexOutOfBoundsException {
      byte type = Utility.typeOfSignature(signature);
      if (type <= 12) {
         return new TypeHolder(BasicType.getType(type), 1);
      } else {
         int index;
         if (type == 13) {
            index = 0;

            do {
               ++index;
            } while(signature.charAt(index) == '[');

            TypeHolder th = getTypeInternal(signature.substring(index));
            return new TypeHolder(new ArrayType(th.getType(), index), index + th.getConsumed());
         } else {
            index = signature.indexOf(59);
            if (index < 0) {
               throw new ClassFormatException("Invalid signature: " + signature);
            } else {
               int nextAngly = signature.indexOf(60);
               String typeString = null;
               if (nextAngly != -1 && nextAngly <= index) {
                  boolean endOfSigReached = false;
                  int posn = nextAngly;
                  int genericDepth = 0;

                  while(!endOfSigReached) {
                     switch (signature.charAt(posn++)) {
                        case ';':
                           if (genericDepth == 0) {
                              endOfSigReached = true;
                           }
                           break;
                        case '<':
                           ++genericDepth;
                        case '=':
                        default:
                           break;
                        case '>':
                           --genericDepth;
                     }
                  }

                  index = posn - 1;
                  typeString = signature.substring(1, nextAngly).replace('/', '.');
               } else {
                  typeString = signature.substring(1, index).replace('/', '.');
               }

               return new TypeHolder(new ObjectType(typeString), index + 1);
            }
         }
      }
   }

   public static Type getReturnType(String signature) {
      try {
         int index = signature.lastIndexOf(41) + 1;
         return getType(signature.substring(index));
      } catch (StringIndexOutOfBoundsException var2) {
         throw new ClassFormatException("Invalid method signature: " + signature);
      }
   }

   public static Type[] getArgumentTypes(String signature) {
      List argumentTypes = new ArrayList();

      try {
         if (signature.charAt(0) != '(') {
            throw new ClassFormatException("Invalid method signature: " + signature);
         }

         TypeHolder th;
         for(int index = 1; signature.charAt(index) != ')'; index += th.getConsumed()) {
            th = getTypeInternal(signature.substring(index));
            argumentTypes.add(th.getType());
         }
      } catch (StringIndexOutOfBoundsException var5) {
         throw new ClassFormatException("Invalid method signature: " + signature);
      }

      Type[] types = new Type[argumentTypes.size()];
      argumentTypes.toArray(types);
      return types;
   }

   public static int getArgumentSizes(String signature) {
      int size = 0;
      if (signature.charAt(0) != '(') {
         throw new ClassFormatException("Invalid method signature: " + signature);
      } else {
         int index = 1;

         try {
            while(true) {
               while(signature.charAt(index) != ')') {
                  byte type = Utility.typeOfSignature(signature.charAt(index));
                  if (type <= 12) {
                     size += BasicType.getType(type).getSize();
                     ++index;
                  } else {
                     int index2;
                     if (type == 13) {
                        index2 = 0;

                        do {
                           ++index2;
                        } while(signature.charAt(index2 + index) == '[');

                        TypeHolder th = getTypeInternal(signature.substring(index2 + index));
                        ++size;
                        index += index2 + th.getConsumed();
                     } else {
                        index2 = signature.indexOf(59, index);
                        int nextAngly = signature.indexOf(60, index);
                        if (nextAngly != -1 && nextAngly <= index2) {
                           boolean endOfSigReached = false;
                           int posn = nextAngly;
                           int genericDepth = 0;

                           while(!endOfSigReached) {
                              switch (signature.charAt(posn++)) {
                                 case ';':
                                    if (genericDepth == 0) {
                                       endOfSigReached = true;
                                    }
                                    break;
                                 case '<':
                                    ++genericDepth;
                                 case '=':
                                 default:
                                    break;
                                 case '>':
                                    --genericDepth;
                              }
                           }

                           index2 = posn - 1;
                        }

                        ++size;
                        index = index2 + 1;
                     }
                  }
               }

               return size;
            }
         } catch (StringIndexOutOfBoundsException var9) {
            throw new ClassFormatException("Invalid method signature: " + signature);
         }
      }
   }

   public static int getTypeSize(String signature) {
      byte type = Utility.typeOfSignature(signature.charAt(0));
      if (type <= 12) {
         return BasicType.getType(type).getSize();
      } else {
         return type == 13 ? 1 : 1;
      }
   }

   public static Type getType(Class cl) {
      if (cl == null) {
         throw new IllegalArgumentException("Class must not be null");
      } else if (cl.isArray()) {
         return getType(cl.getName());
      } else if (cl.isPrimitive()) {
         if (cl == Integer.TYPE) {
            return INT;
         } else if (cl == Void.TYPE) {
            return VOID;
         } else if (cl == Double.TYPE) {
            return DOUBLE;
         } else if (cl == Float.TYPE) {
            return FLOAT;
         } else if (cl == Boolean.TYPE) {
            return BOOLEAN;
         } else if (cl == Byte.TYPE) {
            return BYTE;
         } else if (cl == Short.TYPE) {
            return SHORT;
         } else if (cl == Byte.TYPE) {
            return BYTE;
         } else if (cl == Long.TYPE) {
            return LONG;
         } else if (cl == Character.TYPE) {
            return CHAR;
         } else {
            throw new IllegalStateException("Ooops, what primitive type is " + cl);
         }
      } else {
         return new ObjectType(cl.getName());
      }
   }

   public static String getSignature(Method meth) {
      StringBuffer sb = new StringBuffer("(");
      Class[] params = meth.getParameterTypes();

      for(int j = 0; j < params.length; ++j) {
         sb.append(getType(params[j]).getSignature());
      }

      sb.append(")");
      sb.append(getType(meth.getReturnType()).getSignature());
      return sb.toString();
   }

   public static String getSignature(Constructor cons) {
      StringBuffer sb = new StringBuffer("(");
      Class[] params = cons.getParameterTypes();

      for(int j = 0; j < params.length; ++j) {
         sb.append(getType(params[j]).getSignature());
      }

      sb.append(")V");
      return sb.toString();
   }

   public static class TypeHolder {
      private Type t;
      private int consumed;

      public Type getType() {
         return this.t;
      }

      public int getConsumed() {
         return this.consumed;
      }

      public TypeHolder(Type t, int i) {
         this.t = t;
         this.consumed = i;
      }
   }
}
