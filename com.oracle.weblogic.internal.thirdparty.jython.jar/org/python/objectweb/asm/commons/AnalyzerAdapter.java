package org.python.objectweb.asm.commons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.python.objectweb.asm.Handle;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.Opcodes;
import org.python.objectweb.asm.Type;

public class AnalyzerAdapter extends MethodVisitor {
   public List locals;
   public List stack;
   private List labels;
   public Map uninitializedTypes;
   private int maxStack;
   private int maxLocals;
   private String owner;
   // $FF: synthetic field
   static Class class$org$objectweb$asm$commons$AnalyzerAdapter = class$("org.python.objectweb.asm.commons.AnalyzerAdapter");

   public AnalyzerAdapter(String var1, int var2, String var3, String var4, MethodVisitor var5) {
      this(327680, var1, var2, var3, var4, var5);
      if (this.getClass() != class$org$objectweb$asm$commons$AnalyzerAdapter) {
         throw new IllegalStateException();
      }
   }

   protected AnalyzerAdapter(int var1, String var2, int var3, String var4, String var5, MethodVisitor var6) {
      super(var1, var6);
      this.owner = var2;
      this.locals = new ArrayList();
      this.stack = new ArrayList();
      this.uninitializedTypes = new HashMap();
      if ((var3 & 8) == 0) {
         if ("<init>".equals(var4)) {
            this.locals.add(Opcodes.UNINITIALIZED_THIS);
         } else {
            this.locals.add(var2);
         }
      }

      Type[] var7 = Type.getArgumentTypes(var5);

      for(int var8 = 0; var8 < var7.length; ++var8) {
         Type var9 = var7[var8];
         switch (var9.getSort()) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
               this.locals.add(Opcodes.INTEGER);
               break;
            case 6:
               this.locals.add(Opcodes.FLOAT);
               break;
            case 7:
               this.locals.add(Opcodes.LONG);
               this.locals.add(Opcodes.TOP);
               break;
            case 8:
               this.locals.add(Opcodes.DOUBLE);
               this.locals.add(Opcodes.TOP);
               break;
            case 9:
               this.locals.add(var7[var8].getDescriptor());
               break;
            default:
               this.locals.add(var7[var8].getInternalName());
         }
      }

      this.maxLocals = this.locals.size();
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (var1 != -1) {
         throw new IllegalStateException("ClassReader.accept() should be called with EXPAND_FRAMES flag");
      } else {
         if (this.mv != null) {
            this.mv.visitFrame(var1, var2, var3, var4, var5);
         }

         if (this.locals != null) {
            this.locals.clear();
            this.stack.clear();
         } else {
            this.locals = new ArrayList();
            this.stack = new ArrayList();
         }

         visitFrameTypes(var2, var3, this.locals);
         visitFrameTypes(var4, var5, this.stack);
         this.maxStack = Math.max(this.maxStack, this.stack.size());
      }
   }

   private static void visitFrameTypes(int var0, Object[] var1, List var2) {
      for(int var3 = 0; var3 < var0; ++var3) {
         Object var4 = var1[var3];
         var2.add(var4);
         if (var4 == Opcodes.LONG || var4 == Opcodes.DOUBLE) {
            var2.add(Opcodes.TOP);
         }
      }

   }

   public void visitInsn(int var1) {
      if (this.mv != null) {
         this.mv.visitInsn(var1);
      }

      this.execute(var1, 0, (String)null);
      if (var1 >= 172 && var1 <= 177 || var1 == 191) {
         this.locals = null;
         this.stack = null;
      }

   }

   public void visitIntInsn(int var1, int var2) {
      if (this.mv != null) {
         this.mv.visitIntInsn(var1, var2);
      }

      this.execute(var1, var2, (String)null);
   }

   public void visitVarInsn(int var1, int var2) {
      if (this.mv != null) {
         this.mv.visitVarInsn(var1, var2);
      }

      this.execute(var1, var2, (String)null);
   }

   public void visitTypeInsn(int var1, String var2) {
      if (var1 == 187) {
         if (this.labels == null) {
            Label var3 = new Label();
            this.labels = new ArrayList(3);
            this.labels.add(var3);
            if (this.mv != null) {
               this.mv.visitLabel(var3);
            }
         }

         for(int var4 = 0; var4 < this.labels.size(); ++var4) {
            this.uninitializedTypes.put(this.labels.get(var4), var2);
         }
      }

      if (this.mv != null) {
         this.mv.visitTypeInsn(var1, var2);
      }

      this.execute(var1, 0, var2);
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      if (this.mv != null) {
         this.mv.visitFieldInsn(var1, var2, var3, var4);
      }

      this.execute(var1, 0, var4);
   }

   /** @deprecated */
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         super.visitMethodInsn(var1, var2, var3, var4);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var1 == 185);
      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var5);
      }
   }

   private void doVisitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.mv != null) {
         this.mv.visitMethodInsn(var1, var2, var3, var4, var5);
      }

      if (this.locals == null) {
         this.labels = null;
      } else {
         this.pop(var4);
         if (var1 != 184) {
            Object var6 = this.pop();
            if (var1 == 183 && var3.charAt(0) == '<') {
               Object var7;
               if (var6 == Opcodes.UNINITIALIZED_THIS) {
                  var7 = this.owner;
               } else {
                  var7 = this.uninitializedTypes.get(var6);
               }

               int var8;
               for(var8 = 0; var8 < this.locals.size(); ++var8) {
                  if (this.locals.get(var8) == var6) {
                     this.locals.set(var8, var7);
                  }
               }

               for(var8 = 0; var8 < this.stack.size(); ++var8) {
                  if (this.stack.get(var8) == var6) {
                     this.stack.set(var8, var7);
                  }
               }
            }
         }

         this.pushDesc(var4);
         this.labels = null;
      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      if (this.mv != null) {
         this.mv.visitInvokeDynamicInsn(var1, var2, var3, var4);
      }

      if (this.locals == null) {
         this.labels = null;
      } else {
         this.pop(var2);
         this.pushDesc(var2);
         this.labels = null;
      }
   }

   public void visitJumpInsn(int var1, Label var2) {
      if (this.mv != null) {
         this.mv.visitJumpInsn(var1, var2);
      }

      this.execute(var1, 0, (String)null);
      if (var1 == 167) {
         this.locals = null;
         this.stack = null;
      }

   }

   public void visitLabel(Label var1) {
      if (this.mv != null) {
         this.mv.visitLabel(var1);
      }

      if (this.labels == null) {
         this.labels = new ArrayList(3);
      }

      this.labels.add(var1);
   }

   public void visitLdcInsn(Object var1) {
      if (this.mv != null) {
         this.mv.visitLdcInsn(var1);
      }

      if (this.locals == null) {
         this.labels = null;
      } else {
         if (var1 instanceof Integer) {
            this.push(Opcodes.INTEGER);
         } else if (var1 instanceof Long) {
            this.push(Opcodes.LONG);
            this.push(Opcodes.TOP);
         } else if (var1 instanceof Float) {
            this.push(Opcodes.FLOAT);
         } else if (var1 instanceof Double) {
            this.push(Opcodes.DOUBLE);
            this.push(Opcodes.TOP);
         } else if (var1 instanceof String) {
            this.push("java/lang/String");
         } else if (var1 instanceof Type) {
            int var2 = ((Type)var1).getSort();
            if (var2 != 10 && var2 != 9) {
               if (var2 != 11) {
                  throw new IllegalArgumentException();
               }

               this.push("java/lang/invoke/MethodType");
            } else {
               this.push("java/lang/Class");
            }
         } else {
            if (!(var1 instanceof Handle)) {
               throw new IllegalArgumentException();
            }

            this.push("java/lang/invoke/MethodHandle");
         }

         this.labels = null;
      }
   }

   public void visitIincInsn(int var1, int var2) {
      if (this.mv != null) {
         this.mv.visitIincInsn(var1, var2);
      }

      this.execute(132, var1, (String)null);
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      if (this.mv != null) {
         this.mv.visitTableSwitchInsn(var1, var2, var3, var4);
      }

      this.execute(170, 0, (String)null);
      this.locals = null;
      this.stack = null;
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      if (this.mv != null) {
         this.mv.visitLookupSwitchInsn(var1, var2, var3);
      }

      this.execute(171, 0, (String)null);
      this.locals = null;
      this.stack = null;
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      if (this.mv != null) {
         this.mv.visitMultiANewArrayInsn(var1, var2);
      }

      this.execute(197, var2, var1);
   }

   public void visitMaxs(int var1, int var2) {
      if (this.mv != null) {
         this.maxStack = Math.max(this.maxStack, var1);
         this.maxLocals = Math.max(this.maxLocals, var2);
         this.mv.visitMaxs(this.maxStack, this.maxLocals);
      }

   }

   private Object get(int var1) {
      this.maxLocals = Math.max(this.maxLocals, var1 + 1);
      return var1 < this.locals.size() ? this.locals.get(var1) : Opcodes.TOP;
   }

   private void set(int var1, Object var2) {
      this.maxLocals = Math.max(this.maxLocals, var1 + 1);

      while(var1 >= this.locals.size()) {
         this.locals.add(Opcodes.TOP);
      }

      this.locals.set(var1, var2);
   }

   private void push(Object var1) {
      this.stack.add(var1);
      this.maxStack = Math.max(this.maxStack, this.stack.size());
   }

   private void pushDesc(String var1) {
      int var2 = var1.charAt(0) == '(' ? var1.indexOf(41) + 1 : 0;
      switch (var1.charAt(var2)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            this.push(Opcodes.INTEGER);
            return;
         case 'D':
            this.push(Opcodes.DOUBLE);
            this.push(Opcodes.TOP);
            return;
         case 'E':
         case 'G':
         case 'H':
         case 'K':
         case 'L':
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
            if (var2 == 0) {
               this.push(var1.substring(1, var1.length() - 1));
            } else {
               this.push(var1.substring(var2 + 1, var1.length() - 1));
            }
            break;
         case 'F':
            this.push(Opcodes.FLOAT);
            return;
         case 'J':
            this.push(Opcodes.LONG);
            this.push(Opcodes.TOP);
            return;
         case 'V':
            return;
         case '[':
            if (var2 == 0) {
               this.push(var1);
            } else {
               this.push(var1.substring(var2, var1.length()));
            }
      }

   }

   private Object pop() {
      return this.stack.remove(this.stack.size() - 1);
   }

   private void pop(int var1) {
      int var2 = this.stack.size();
      int var3 = var2 - var1;

      for(int var4 = var2 - 1; var4 >= var3; --var4) {
         this.stack.remove(var4);
      }

   }

   private void pop(String var1) {
      char var2 = var1.charAt(0);
      if (var2 == '(') {
         int var3 = 0;
         Type[] var4 = Type.getArgumentTypes(var1);

         for(int var5 = 0; var5 < var4.length; ++var5) {
            var3 += var4[var5].getSize();
         }

         this.pop(var3);
      } else if (var2 != 'J' && var2 != 'D') {
         this.pop(1);
      } else {
         this.pop(2);
      }

   }

   private void execute(int var1, int var2, String var3) {
      if (this.locals == null) {
         this.labels = null;
      } else {
         Object var4;
         Object var5;
         Object var6;
         label84:
         switch (var1) {
            case 0:
            case 116:
            case 117:
            case 118:
            case 119:
            case 145:
            case 146:
            case 147:
            case 167:
            case 177:
               break;
            case 1:
               this.push(Opcodes.NULL);
               break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 16:
            case 17:
               this.push(Opcodes.INTEGER);
               break;
            case 9:
            case 10:
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 11:
            case 12:
            case 13:
               this.push(Opcodes.FLOAT);
               break;
            case 14:
            case 15:
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 18:
            case 19:
            case 20:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 59:
            case 60:
            case 61:
            case 62:
            case 63:
            case 64:
            case 65:
            case 66:
            case 67:
            case 68:
            case 69:
            case 70:
            case 71:
            case 72:
            case 73:
            case 74:
            case 75:
            case 76:
            case 77:
            case 78:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 196:
            case 197:
            default:
               this.pop(var2);
               this.pushDesc(var3);
               break;
            case 21:
            case 23:
            case 25:
               this.push(this.get(var2));
               break;
            case 22:
            case 24:
               this.push(this.get(var2));
               this.push(Opcodes.TOP);
               break;
            case 46:
            case 51:
            case 52:
            case 53:
               this.pop(2);
               this.push(Opcodes.INTEGER);
               break;
            case 47:
            case 143:
               this.pop(2);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 48:
               this.pop(2);
               this.push(Opcodes.FLOAT);
               break;
            case 49:
            case 138:
               this.pop(2);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 50:
               this.pop(1);
               var4 = this.pop();
               if (var4 instanceof String) {
                  this.pushDesc(((String)var4).substring(1));
               } else {
                  this.push("java/lang/Object");
               }
               break;
            case 54:
            case 56:
            case 58:
               var4 = this.pop();
               this.set(var2, var4);
               if (var2 > 0) {
                  var5 = this.get(var2 - 1);
                  if (var5 == Opcodes.LONG || var5 == Opcodes.DOUBLE) {
                     this.set(var2 - 1, Opcodes.TOP);
                  }
               }
               break;
            case 55:
            case 57:
               this.pop(1);
               var4 = this.pop();
               this.set(var2, var4);
               this.set(var2 + 1, Opcodes.TOP);
               if (var2 > 0) {
                  var5 = this.get(var2 - 1);
                  if (var5 == Opcodes.LONG || var5 == Opcodes.DOUBLE) {
                     this.set(var2 - 1, Opcodes.TOP);
                  }
               }
               break;
            case 79:
            case 81:
            case 83:
            case 84:
            case 85:
            case 86:
               this.pop(3);
               break;
            case 80:
            case 82:
               this.pop(4);
               break;
            case 87:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 170:
            case 171:
            case 172:
            case 174:
            case 176:
            case 191:
            case 194:
            case 195:
            case 198:
            case 199:
               this.pop(1);
               break;
            case 88:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 173:
            case 175:
               this.pop(2);
               break;
            case 89:
               var4 = this.pop();
               this.push(var4);
               this.push(var4);
               break;
            case 90:
               var4 = this.pop();
               var5 = this.pop();
               this.push(var4);
               this.push(var5);
               this.push(var4);
               break;
            case 91:
               var4 = this.pop();
               var5 = this.pop();
               var6 = this.pop();
               this.push(var4);
               this.push(var6);
               this.push(var5);
               this.push(var4);
               break;
            case 92:
               var4 = this.pop();
               var5 = this.pop();
               this.push(var5);
               this.push(var4);
               this.push(var5);
               this.push(var4);
               break;
            case 93:
               var4 = this.pop();
               var5 = this.pop();
               var6 = this.pop();
               this.push(var5);
               this.push(var4);
               this.push(var6);
               this.push(var5);
               this.push(var4);
               break;
            case 94:
               var4 = this.pop();
               var5 = this.pop();
               var6 = this.pop();
               Object var7 = this.pop();
               this.push(var5);
               this.push(var4);
               this.push(var7);
               this.push(var6);
               this.push(var5);
               this.push(var4);
               break;
            case 95:
               var4 = this.pop();
               var5 = this.pop();
               this.push(var4);
               this.push(var5);
               break;
            case 96:
            case 100:
            case 104:
            case 108:
            case 112:
            case 120:
            case 122:
            case 124:
            case 126:
            case 128:
            case 130:
            case 136:
            case 142:
            case 149:
            case 150:
               this.pop(2);
               this.push(Opcodes.INTEGER);
               break;
            case 97:
            case 101:
            case 105:
            case 109:
            case 113:
            case 127:
            case 129:
            case 131:
               this.pop(4);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 98:
            case 102:
            case 106:
            case 110:
            case 114:
            case 137:
            case 144:
               this.pop(2);
               this.push(Opcodes.FLOAT);
               break;
            case 99:
            case 103:
            case 107:
            case 111:
            case 115:
               this.pop(4);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 121:
            case 123:
            case 125:
               this.pop(3);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 132:
               this.set(var2, Opcodes.INTEGER);
               break;
            case 133:
            case 140:
               this.pop(1);
               this.push(Opcodes.LONG);
               this.push(Opcodes.TOP);
               break;
            case 134:
               this.pop(1);
               this.push(Opcodes.FLOAT);
               break;
            case 135:
            case 141:
               this.pop(1);
               this.push(Opcodes.DOUBLE);
               this.push(Opcodes.TOP);
               break;
            case 139:
            case 190:
            case 193:
               this.pop(1);
               this.push(Opcodes.INTEGER);
               break;
            case 148:
            case 151:
            case 152:
               this.pop(4);
               this.push(Opcodes.INTEGER);
               break;
            case 168:
            case 169:
               throw new RuntimeException("JSR/RET are not supported");
            case 178:
               this.pushDesc(var3);
               break;
            case 179:
               this.pop(var3);
               break;
            case 180:
               this.pop(1);
               this.pushDesc(var3);
               break;
            case 181:
               this.pop(var3);
               this.pop();
               break;
            case 187:
               this.push(this.labels.get(0));
               break;
            case 188:
               this.pop();
               switch (var2) {
                  case 4:
                     this.pushDesc("[Z");
                     break label84;
                  case 5:
                     this.pushDesc("[C");
                     break label84;
                  case 6:
                     this.pushDesc("[F");
                     break label84;
                  case 7:
                     this.pushDesc("[D");
                     break label84;
                  case 8:
                     this.pushDesc("[B");
                     break label84;
                  case 9:
                     this.pushDesc("[S");
                     break label84;
                  case 10:
                     this.pushDesc("[I");
                     break label84;
                  default:
                     this.pushDesc("[J");
                     break label84;
               }
            case 189:
               this.pop();
               this.pushDesc("[" + Type.getObjectType(var3));
               break;
            case 192:
               this.pop();
               this.pushDesc(Type.getObjectType(var3).getDescriptor());
         }

         this.labels = null;
      }
   }

   // $FF: synthetic method
   static Class class$(String var0) {
      try {
         return Class.forName(var0);
      } catch (ClassNotFoundException var2) {
         String var1 = var2.getMessage();
         throw new NoClassDefFoundError(var1);
      }
   }
}
