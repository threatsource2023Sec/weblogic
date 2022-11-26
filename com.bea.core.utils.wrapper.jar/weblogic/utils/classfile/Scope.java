package weblogic.utils.classfile;

import java.lang.reflect.Method;
import java.util.LinkedList;
import weblogic.utils.classfile.expr.Expression;
import weblogic.utils.classfile.expr.LocalVariableExpression;
import weblogic.utils.classfile.ops.LocalVarOp;

public class Scope {
   private int maxLocals = 0;
   private LocalVar[] localVars = new LocalVar[2];
   private Expression[] args;

   public Scope(Method method, int modifiers) {
      this.init(modifiers);
      Class[] paramTypes = method.getParameterTypes();
      int length = paramTypes.length;
      this.args = new Expression[length];

      for(int i = 0; i < length; ++i) {
         this.args[i] = new LocalVariableExpression(this, this.allocLocalVar(Type.getType(paramTypes[i])));
      }

   }

   public Scope(String descriptor, int modifiers) {
      this.init(modifiers);
      Type[] paramTypes = this.getParamTypes(descriptor);
      int length = paramTypes.length;
      this.args = new Expression[length];

      for(int i = 0; i < length; ++i) {
         this.args[i] = new LocalVariableExpression(this, this.allocLocalVar(paramTypes[i]));
      }

   }

   private void init(int modifiers) {
      boolean isStatic = (modifiers & 8) == 8;
      if (!isStatic) {
         this.allocLocalVar(Type.OBJECT);
      }

   }

   private void badDescriptor(String descriptor) {
      throw new AssertionError("Invalid descriptor: " + descriptor);
   }

   private Type[] getParamTypes(String descriptor) {
      try {
         if (descriptor.charAt(0) != '(') {
            this.badDescriptor(descriptor);
         }

         int i = descriptor.indexOf(41);
         if (i == -1) {
            this.badDescriptor(descriptor);
         }

         return this.getTypes(descriptor, 1, i);
      } catch (IndexOutOfBoundsException var3) {
         this.badDescriptor(descriptor);
         return null;
      }
   }

   private Type[] getTypes(String descriptor, int startIndex, int endIndex) {
      LinkedList types = new LinkedList();

      for(int i = startIndex; i < endIndex; ++i) {
         switch (descriptor.charAt(i)) {
            case 'B':
               types.add(Type.BYTE);
               break;
            case 'C':
               types.add(Type.CHARACTER);
               break;
            case 'D':
               types.add(Type.DOUBLE);
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
               break;
            case 'F':
               types.add(Type.FLOAT);
               break;
            case 'I':
               types.add(Type.INT);
               break;
            case 'J':
               types.add(Type.LONG);
               break;
            case 'L':
               types.add(Type.OBJECT);

               while(i < endIndex && descriptor.charAt(i) != ';') {
                  ++i;
               }

               if (descriptor.charAt(i) != ';') {
                  this.badDescriptor(descriptor);
               }
               break;
            case 'S':
               types.add(Type.SHORT);
               break;
            case 'V':
               types.add(Type.VOID);
               break;
            case 'Z':
               types.add(Type.BOOLEAN);
               break;
            case '[':
               types.add(Type.ARRAY);

               while(i < endIndex && descriptor.charAt(i) == '[') {
                  ++i;
               }

               if (i == endIndex) {
                  this.badDescriptor(descriptor);
               }

               char c = descriptor.charAt(i);
               if (c == 'V') {
                  this.badDescriptor(descriptor);
               }

               if (c != 'I' && c != 'B' && c != 'Z' && c != 'S' && c != 'F' && c != 'C' && c != 'J' && c != 'D') {
                  if (c != 'L') {
                     this.badDescriptor(descriptor);
                  }

                  while(i < endIndex && descriptor.charAt(i) != ';') {
                     ++i;
                  }

                  if (descriptor.charAt(i) != ';') {
                     this.badDescriptor(descriptor);
                  }
               }
         }
      }

      return (Type[])types.toArray(new Type[types.size()]);
   }

   public Expression getParameter(int i) {
      return this.args[i - 1];
   }

   public Expression[] getArgs() {
      return this.args;
   }

   LocalVar allocLocalVar(Type type) {
      LocalVar var = new LocalVar(type);
      int endIndex = this.localVars.length - (var.isWide() ? 1 : 0);

      int i;
      for(i = 0; i < endIndex; ++i) {
         if (this.localVars[i] == null) {
            if (!var.isWide()) {
               this.localVars[i] = var;
               var.setIndex(i);
               if (i + 1 > this.maxLocals) {
                  this.maxLocals = i + 1;
               }

               return var;
            }

            if (this.localVars[i + 1] == null) {
               this.localVars[i] = this.localVars[i + 1] = var;
               var.setIndex(i);
               if (i + 2 > this.maxLocals) {
                  this.maxLocals = i + 2;
               }

               return var;
            }
         }
      }

      i = this.localVars.length;
      LocalVar[] tmp = new LocalVar[i * 2];
      System.arraycopy(this.localVars, 0, tmp, 0, i);
      this.localVars = tmp;
      if (var.isWide()) {
         if (this.localVars[i - 1] == null) {
            --i;
         }

         this.localVars[i] = this.localVars[i + 1] = var;
         var.setIndex(i);
         if (i + 2 > this.maxLocals) {
            this.maxLocals = i + 2;
         }

         return var;
      } else {
         this.localVars[i] = var;
         var.setIndex(i);
         if (i + 1 > this.maxLocals) {
            this.maxLocals = i + 1;
         }

         return var;
      }
   }

   public LocalVariableExpression createLocalVar(Type t) {
      return new LocalVariableExpression(this, this.allocLocalVar(t));
   }

   public void freeLocalVar(LocalVariableExpression lve) {
      if (lve.var != null) {
         this.localVars[lve.var.index] = null;
         if (lve.var.isWide()) {
            this.localVars[lve.var.index + 1] = null;
         }
      }

   }

   int getMaxLocals() {
      return this.maxLocals;
   }

   public static class LocalVar {
      Type type;
      int index;
      int loadOpCode;
      int loadOpCode0;
      int storeOpCode;
      int storeOpCode0;

      public LocalVar(Type type) {
         this.type = type;
         if (type != Type.OBJECT && type != Type.ARRAY) {
            if (type != Type.INT && type != Type.BYTE && type != Type.BOOLEAN && type != Type.SHORT && type != Type.CHARACTER) {
               if (type == Type.LONG) {
                  this.loadOpCode = 22;
                  this.loadOpCode0 = 30;
                  this.storeOpCode = 55;
                  this.storeOpCode0 = 63;
               } else if (type == Type.FLOAT) {
                  this.loadOpCode = 23;
                  this.loadOpCode0 = 34;
                  this.storeOpCode = 56;
                  this.storeOpCode0 = 67;
               } else {
                  if (type != Type.DOUBLE) {
                     throw new AssertionError("Unknown type: " + type);
                  }

                  this.loadOpCode = 24;
                  this.loadOpCode0 = 38;
                  this.storeOpCode = 57;
                  this.storeOpCode0 = 71;
               }
            } else {
               this.loadOpCode = 21;
               this.loadOpCode0 = 26;
               this.storeOpCode = 54;
               this.storeOpCode0 = 59;
            }
         } else {
            this.loadOpCode = 25;
            this.loadOpCode0 = 42;
            this.storeOpCode = 58;
            this.storeOpCode0 = 75;
         }

      }

      public int getIndex() {
         return this.index;
      }

      void setIndex(int index) {
         this.index = index;
      }

      boolean isWide() {
         return this.type == Type.LONG || this.type == Type.DOUBLE;
      }

      public Op getStoreOp() {
         return (Op)(this.index > 3 ? new LocalVarOp(this.storeOpCode, this.index) : new Op(this.storeOpCode0 + this.index));
      }

      public Op getLoadOp() {
         return (Op)(this.index > 3 ? new LocalVarOp(this.loadOpCode, this.index) : new Op(this.loadOpCode0 + this.index));
      }

      public Op getReturnOp() {
         return new LocalVarOp(169, this.index);
      }

      public Type getType() {
         return this.type;
      }
   }
}
