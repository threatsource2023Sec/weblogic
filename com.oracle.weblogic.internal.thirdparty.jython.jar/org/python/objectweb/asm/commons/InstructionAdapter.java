package org.python.objectweb.asm.commons;

import org.python.objectweb.asm.Handle;
import org.python.objectweb.asm.Label;
import org.python.objectweb.asm.MethodVisitor;
import org.python.objectweb.asm.Type;

public class InstructionAdapter extends MethodVisitor {
   public static final Type OBJECT_TYPE;
   // $FF: synthetic field
   static Class class$org$objectweb$asm$commons$InstructionAdapter;

   public InstructionAdapter(MethodVisitor var1) {
      this(327680, var1);
      if (this.getClass() != class$org$objectweb$asm$commons$InstructionAdapter) {
         throw new IllegalStateException();
      }
   }

   protected InstructionAdapter(int var1, MethodVisitor var2) {
      super(var1, var2);
   }

   public void visitInsn(int var1) {
      switch (var1) {
         case 0:
            this.nop();
            break;
         case 1:
            this.aconst((Object)null);
            break;
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
            this.iconst(var1 - 3);
            break;
         case 9:
         case 10:
            this.lconst((long)(var1 - 9));
            break;
         case 11:
         case 12:
         case 13:
            this.fconst((float)(var1 - 11));
            break;
         case 14:
         case 15:
            this.dconst((double)(var1 - 14));
            break;
         case 16:
         case 17:
         case 18:
         case 19:
         case 20:
         case 21:
         case 22:
         case 23:
         case 24:
         case 25:
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
         case 54:
         case 55:
         case 56:
         case 57:
         case 58:
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
         case 132:
         case 153:
         case 154:
         case 155:
         case 156:
         case 157:
         case 158:
         case 159:
         case 160:
         case 161:
         case 162:
         case 163:
         case 164:
         case 165:
         case 166:
         case 167:
         case 168:
         case 169:
         case 170:
         case 171:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 186:
         case 187:
         case 188:
         case 189:
         case 192:
         case 193:
         default:
            throw new IllegalArgumentException();
         case 46:
            this.aload(Type.INT_TYPE);
            break;
         case 47:
            this.aload(Type.LONG_TYPE);
            break;
         case 48:
            this.aload(Type.FLOAT_TYPE);
            break;
         case 49:
            this.aload(Type.DOUBLE_TYPE);
            break;
         case 50:
            this.aload(OBJECT_TYPE);
            break;
         case 51:
            this.aload(Type.BYTE_TYPE);
            break;
         case 52:
            this.aload(Type.CHAR_TYPE);
            break;
         case 53:
            this.aload(Type.SHORT_TYPE);
            break;
         case 79:
            this.astore(Type.INT_TYPE);
            break;
         case 80:
            this.astore(Type.LONG_TYPE);
            break;
         case 81:
            this.astore(Type.FLOAT_TYPE);
            break;
         case 82:
            this.astore(Type.DOUBLE_TYPE);
            break;
         case 83:
            this.astore(OBJECT_TYPE);
            break;
         case 84:
            this.astore(Type.BYTE_TYPE);
            break;
         case 85:
            this.astore(Type.CHAR_TYPE);
            break;
         case 86:
            this.astore(Type.SHORT_TYPE);
            break;
         case 87:
            this.pop();
            break;
         case 88:
            this.pop2();
            break;
         case 89:
            this.dup();
            break;
         case 90:
            this.dupX1();
            break;
         case 91:
            this.dupX2();
            break;
         case 92:
            this.dup2();
            break;
         case 93:
            this.dup2X1();
            break;
         case 94:
            this.dup2X2();
            break;
         case 95:
            this.swap();
            break;
         case 96:
            this.add(Type.INT_TYPE);
            break;
         case 97:
            this.add(Type.LONG_TYPE);
            break;
         case 98:
            this.add(Type.FLOAT_TYPE);
            break;
         case 99:
            this.add(Type.DOUBLE_TYPE);
            break;
         case 100:
            this.sub(Type.INT_TYPE);
            break;
         case 101:
            this.sub(Type.LONG_TYPE);
            break;
         case 102:
            this.sub(Type.FLOAT_TYPE);
            break;
         case 103:
            this.sub(Type.DOUBLE_TYPE);
            break;
         case 104:
            this.mul(Type.INT_TYPE);
            break;
         case 105:
            this.mul(Type.LONG_TYPE);
            break;
         case 106:
            this.mul(Type.FLOAT_TYPE);
            break;
         case 107:
            this.mul(Type.DOUBLE_TYPE);
            break;
         case 108:
            this.div(Type.INT_TYPE);
            break;
         case 109:
            this.div(Type.LONG_TYPE);
            break;
         case 110:
            this.div(Type.FLOAT_TYPE);
            break;
         case 111:
            this.div(Type.DOUBLE_TYPE);
            break;
         case 112:
            this.rem(Type.INT_TYPE);
            break;
         case 113:
            this.rem(Type.LONG_TYPE);
            break;
         case 114:
            this.rem(Type.FLOAT_TYPE);
            break;
         case 115:
            this.rem(Type.DOUBLE_TYPE);
            break;
         case 116:
            this.neg(Type.INT_TYPE);
            break;
         case 117:
            this.neg(Type.LONG_TYPE);
            break;
         case 118:
            this.neg(Type.FLOAT_TYPE);
            break;
         case 119:
            this.neg(Type.DOUBLE_TYPE);
            break;
         case 120:
            this.shl(Type.INT_TYPE);
            break;
         case 121:
            this.shl(Type.LONG_TYPE);
            break;
         case 122:
            this.shr(Type.INT_TYPE);
            break;
         case 123:
            this.shr(Type.LONG_TYPE);
            break;
         case 124:
            this.ushr(Type.INT_TYPE);
            break;
         case 125:
            this.ushr(Type.LONG_TYPE);
            break;
         case 126:
            this.and(Type.INT_TYPE);
            break;
         case 127:
            this.and(Type.LONG_TYPE);
            break;
         case 128:
            this.or(Type.INT_TYPE);
            break;
         case 129:
            this.or(Type.LONG_TYPE);
            break;
         case 130:
            this.xor(Type.INT_TYPE);
            break;
         case 131:
            this.xor(Type.LONG_TYPE);
            break;
         case 133:
            this.cast(Type.INT_TYPE, Type.LONG_TYPE);
            break;
         case 134:
            this.cast(Type.INT_TYPE, Type.FLOAT_TYPE);
            break;
         case 135:
            this.cast(Type.INT_TYPE, Type.DOUBLE_TYPE);
            break;
         case 136:
            this.cast(Type.LONG_TYPE, Type.INT_TYPE);
            break;
         case 137:
            this.cast(Type.LONG_TYPE, Type.FLOAT_TYPE);
            break;
         case 138:
            this.cast(Type.LONG_TYPE, Type.DOUBLE_TYPE);
            break;
         case 139:
            this.cast(Type.FLOAT_TYPE, Type.INT_TYPE);
            break;
         case 140:
            this.cast(Type.FLOAT_TYPE, Type.LONG_TYPE);
            break;
         case 141:
            this.cast(Type.FLOAT_TYPE, Type.DOUBLE_TYPE);
            break;
         case 142:
            this.cast(Type.DOUBLE_TYPE, Type.INT_TYPE);
            break;
         case 143:
            this.cast(Type.DOUBLE_TYPE, Type.LONG_TYPE);
            break;
         case 144:
            this.cast(Type.DOUBLE_TYPE, Type.FLOAT_TYPE);
            break;
         case 145:
            this.cast(Type.INT_TYPE, Type.BYTE_TYPE);
            break;
         case 146:
            this.cast(Type.INT_TYPE, Type.CHAR_TYPE);
            break;
         case 147:
            this.cast(Type.INT_TYPE, Type.SHORT_TYPE);
            break;
         case 148:
            this.lcmp();
            break;
         case 149:
            this.cmpl(Type.FLOAT_TYPE);
            break;
         case 150:
            this.cmpg(Type.FLOAT_TYPE);
            break;
         case 151:
            this.cmpl(Type.DOUBLE_TYPE);
            break;
         case 152:
            this.cmpg(Type.DOUBLE_TYPE);
            break;
         case 172:
            this.areturn(Type.INT_TYPE);
            break;
         case 173:
            this.areturn(Type.LONG_TYPE);
            break;
         case 174:
            this.areturn(Type.FLOAT_TYPE);
            break;
         case 175:
            this.areturn(Type.DOUBLE_TYPE);
            break;
         case 176:
            this.areturn(OBJECT_TYPE);
            break;
         case 177:
            this.areturn(Type.VOID_TYPE);
            break;
         case 190:
            this.arraylength();
            break;
         case 191:
            this.athrow();
            break;
         case 194:
            this.monitorenter();
            break;
         case 195:
            this.monitorexit();
      }

   }

   public void visitIntInsn(int var1, int var2) {
      switch (var1) {
         case 16:
            this.iconst(var2);
            break;
         case 17:
            this.iconst(var2);
            break;
         case 188:
            switch (var2) {
               case 4:
                  this.newarray(Type.BOOLEAN_TYPE);
                  return;
               case 5:
                  this.newarray(Type.CHAR_TYPE);
                  return;
               case 6:
                  this.newarray(Type.FLOAT_TYPE);
                  return;
               case 7:
                  this.newarray(Type.DOUBLE_TYPE);
                  return;
               case 8:
                  this.newarray(Type.BYTE_TYPE);
                  return;
               case 9:
                  this.newarray(Type.SHORT_TYPE);
                  return;
               case 10:
                  this.newarray(Type.INT_TYPE);
                  return;
               case 11:
                  this.newarray(Type.LONG_TYPE);
                  return;
               default:
                  throw new IllegalArgumentException();
            }
         default:
            throw new IllegalArgumentException();
      }

   }

   public void visitVarInsn(int var1, int var2) {
      switch (var1) {
         case 21:
            this.load(var2, Type.INT_TYPE);
            break;
         case 22:
            this.load(var2, Type.LONG_TYPE);
            break;
         case 23:
            this.load(var2, Type.FLOAT_TYPE);
            break;
         case 24:
            this.load(var2, Type.DOUBLE_TYPE);
            break;
         case 25:
            this.load(var2, OBJECT_TYPE);
            break;
         case 54:
            this.store(var2, Type.INT_TYPE);
            break;
         case 55:
            this.store(var2, Type.LONG_TYPE);
            break;
         case 56:
            this.store(var2, Type.FLOAT_TYPE);
            break;
         case 57:
            this.store(var2, Type.DOUBLE_TYPE);
            break;
         case 58:
            this.store(var2, OBJECT_TYPE);
            break;
         case 169:
            this.ret(var2);
            break;
         default:
            throw new IllegalArgumentException();
      }

   }

   public void visitTypeInsn(int var1, String var2) {
      Type var3 = Type.getObjectType(var2);
      switch (var1) {
         case 187:
            this.anew(var3);
            break;
         case 188:
         case 190:
         case 191:
         default:
            throw new IllegalArgumentException();
         case 189:
            this.newarray(var3);
            break;
         case 192:
            this.checkcast(var3);
            break;
         case 193:
            this.instanceOf(var3);
      }

   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      switch (var1) {
         case 178:
            this.getstatic(var2, var3, var4);
            break;
         case 179:
            this.putstatic(var2, var3, var4);
            break;
         case 180:
            this.getfield(var2, var3, var4);
            break;
         case 181:
            this.putfield(var2, var3, var4);
            break;
         default:
            throw new IllegalArgumentException();
      }

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
      switch (var1) {
         case 182:
            this.invokevirtual(var2, var3, var4, var5);
            break;
         case 183:
            this.invokespecial(var2, var3, var4, var5);
            break;
         case 184:
            this.invokestatic(var2, var3, var4, var5);
            break;
         case 185:
            this.invokeinterface(var2, var3, var4);
            break;
         default:
            throw new IllegalArgumentException();
      }

   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.invokedynamic(var1, var2, var3, var4);
   }

   public void visitJumpInsn(int var1, Label var2) {
      switch (var1) {
         case 153:
            this.ifeq(var2);
            break;
         case 154:
            this.ifne(var2);
            break;
         case 155:
            this.iflt(var2);
            break;
         case 156:
            this.ifge(var2);
            break;
         case 157:
            this.ifgt(var2);
            break;
         case 158:
            this.ifle(var2);
            break;
         case 159:
            this.ificmpeq(var2);
            break;
         case 160:
            this.ificmpne(var2);
            break;
         case 161:
            this.ificmplt(var2);
            break;
         case 162:
            this.ificmpge(var2);
            break;
         case 163:
            this.ificmpgt(var2);
            break;
         case 164:
            this.ificmple(var2);
            break;
         case 165:
            this.ifacmpeq(var2);
            break;
         case 166:
            this.ifacmpne(var2);
            break;
         case 167:
            this.goTo(var2);
            break;
         case 168:
            this.jsr(var2);
            break;
         case 169:
         case 170:
         case 171:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 186:
         case 187:
         case 188:
         case 189:
         case 190:
         case 191:
         case 192:
         case 193:
         case 194:
         case 195:
         case 196:
         case 197:
         default:
            throw new IllegalArgumentException();
         case 198:
            this.ifnull(var2);
            break;
         case 199:
            this.ifnonnull(var2);
      }

   }

   public void visitLabel(Label var1) {
      this.mark(var1);
   }

   public void visitLdcInsn(Object var1) {
      int var2;
      if (var1 instanceof Integer) {
         var2 = (Integer)var1;
         this.iconst(var2);
      } else if (var1 instanceof Byte) {
         var2 = ((Byte)var1).intValue();
         this.iconst(var2);
      } else if (var1 instanceof Character) {
         char var5 = (Character)var1;
         this.iconst(var5);
      } else if (var1 instanceof Short) {
         var2 = ((Short)var1).intValue();
         this.iconst(var2);
      } else if (var1 instanceof Boolean) {
         var2 = (Boolean)var1 ? 1 : 0;
         this.iconst(var2);
      } else if (var1 instanceof Float) {
         float var7 = (Float)var1;
         this.fconst(var7);
      } else if (var1 instanceof Long) {
         long var3 = (Long)var1;
         this.lconst(var3);
      } else if (var1 instanceof Double) {
         double var6 = (Double)var1;
         this.dconst(var6);
      } else if (var1 instanceof String) {
         this.aconst(var1);
      } else if (var1 instanceof Type) {
         this.tconst((Type)var1);
      } else {
         if (!(var1 instanceof Handle)) {
            throw new IllegalArgumentException();
         }

         this.hconst((Handle)var1);
      }

   }

   public void visitIincInsn(int var1, int var2) {
      this.iinc(var1, var2);
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.tableswitch(var1, var2, var3, var4);
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.lookupswitch(var1, var2, var3);
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.multianewarray(var1, var2);
   }

   public void nop() {
      this.mv.visitInsn(0);
   }

   public void aconst(Object var1) {
      if (var1 == null) {
         this.mv.visitInsn(1);
      } else {
         this.mv.visitLdcInsn(var1);
      }

   }

   public void iconst(int var1) {
      if (var1 >= -1 && var1 <= 5) {
         this.mv.visitInsn(3 + var1);
      } else if (var1 >= -128 && var1 <= 127) {
         this.mv.visitIntInsn(16, var1);
      } else if (var1 >= -32768 && var1 <= 32767) {
         this.mv.visitIntInsn(17, var1);
      } else {
         this.mv.visitLdcInsn(new Integer(var1));
      }

   }

   public void lconst(long var1) {
      if (var1 != 0L && var1 != 1L) {
         this.mv.visitLdcInsn(new Long(var1));
      } else {
         this.mv.visitInsn(9 + (int)var1);
      }

   }

   public void fconst(float var1) {
      int var2 = Float.floatToIntBits(var1);
      if ((long)var2 != 0L && var2 != 1065353216 && var2 != 1073741824) {
         this.mv.visitLdcInsn(new Float(var1));
      } else {
         this.mv.visitInsn(11 + (int)var1);
      }

   }

   public void dconst(double var1) {
      long var3 = Double.doubleToLongBits(var1);
      if (var3 != 0L && var3 != 4607182418800017408L) {
         this.mv.visitLdcInsn(new Double(var1));
      } else {
         this.mv.visitInsn(14 + (int)var1);
      }

   }

   public void tconst(Type var1) {
      this.mv.visitLdcInsn(var1);
   }

   public void hconst(Handle var1) {
      this.mv.visitLdcInsn(var1);
   }

   public void load(int var1, Type var2) {
      this.mv.visitVarInsn(var2.getOpcode(21), var1);
   }

   public void aload(Type var1) {
      this.mv.visitInsn(var1.getOpcode(46));
   }

   public void store(int var1, Type var2) {
      this.mv.visitVarInsn(var2.getOpcode(54), var1);
   }

   public void astore(Type var1) {
      this.mv.visitInsn(var1.getOpcode(79));
   }

   public void pop() {
      this.mv.visitInsn(87);
   }

   public void pop2() {
      this.mv.visitInsn(88);
   }

   public void dup() {
      this.mv.visitInsn(89);
   }

   public void dup2() {
      this.mv.visitInsn(92);
   }

   public void dupX1() {
      this.mv.visitInsn(90);
   }

   public void dupX2() {
      this.mv.visitInsn(91);
   }

   public void dup2X1() {
      this.mv.visitInsn(93);
   }

   public void dup2X2() {
      this.mv.visitInsn(94);
   }

   public void swap() {
      this.mv.visitInsn(95);
   }

   public void add(Type var1) {
      this.mv.visitInsn(var1.getOpcode(96));
   }

   public void sub(Type var1) {
      this.mv.visitInsn(var1.getOpcode(100));
   }

   public void mul(Type var1) {
      this.mv.visitInsn(var1.getOpcode(104));
   }

   public void div(Type var1) {
      this.mv.visitInsn(var1.getOpcode(108));
   }

   public void rem(Type var1) {
      this.mv.visitInsn(var1.getOpcode(112));
   }

   public void neg(Type var1) {
      this.mv.visitInsn(var1.getOpcode(116));
   }

   public void shl(Type var1) {
      this.mv.visitInsn(var1.getOpcode(120));
   }

   public void shr(Type var1) {
      this.mv.visitInsn(var1.getOpcode(122));
   }

   public void ushr(Type var1) {
      this.mv.visitInsn(var1.getOpcode(124));
   }

   public void and(Type var1) {
      this.mv.visitInsn(var1.getOpcode(126));
   }

   public void or(Type var1) {
      this.mv.visitInsn(var1.getOpcode(128));
   }

   public void xor(Type var1) {
      this.mv.visitInsn(var1.getOpcode(130));
   }

   public void iinc(int var1, int var2) {
      this.mv.visitIincInsn(var1, var2);
   }

   public void cast(Type var1, Type var2) {
      if (var1 != var2) {
         if (var1 == Type.DOUBLE_TYPE) {
            if (var2 == Type.FLOAT_TYPE) {
               this.mv.visitInsn(144);
            } else if (var2 == Type.LONG_TYPE) {
               this.mv.visitInsn(143);
            } else {
               this.mv.visitInsn(142);
               this.cast(Type.INT_TYPE, var2);
            }
         } else if (var1 == Type.FLOAT_TYPE) {
            if (var2 == Type.DOUBLE_TYPE) {
               this.mv.visitInsn(141);
            } else if (var2 == Type.LONG_TYPE) {
               this.mv.visitInsn(140);
            } else {
               this.mv.visitInsn(139);
               this.cast(Type.INT_TYPE, var2);
            }
         } else if (var1 == Type.LONG_TYPE) {
            if (var2 == Type.DOUBLE_TYPE) {
               this.mv.visitInsn(138);
            } else if (var2 == Type.FLOAT_TYPE) {
               this.mv.visitInsn(137);
            } else {
               this.mv.visitInsn(136);
               this.cast(Type.INT_TYPE, var2);
            }
         } else if (var2 == Type.BYTE_TYPE) {
            this.mv.visitInsn(145);
         } else if (var2 == Type.CHAR_TYPE) {
            this.mv.visitInsn(146);
         } else if (var2 == Type.DOUBLE_TYPE) {
            this.mv.visitInsn(135);
         } else if (var2 == Type.FLOAT_TYPE) {
            this.mv.visitInsn(134);
         } else if (var2 == Type.LONG_TYPE) {
            this.mv.visitInsn(133);
         } else if (var2 == Type.SHORT_TYPE) {
            this.mv.visitInsn(147);
         }
      }

   }

   public void lcmp() {
      this.mv.visitInsn(148);
   }

   public void cmpl(Type var1) {
      this.mv.visitInsn(var1 == Type.FLOAT_TYPE ? 149 : 151);
   }

   public void cmpg(Type var1) {
      this.mv.visitInsn(var1 == Type.FLOAT_TYPE ? 150 : 152);
   }

   public void ifeq(Label var1) {
      this.mv.visitJumpInsn(153, var1);
   }

   public void ifne(Label var1) {
      this.mv.visitJumpInsn(154, var1);
   }

   public void iflt(Label var1) {
      this.mv.visitJumpInsn(155, var1);
   }

   public void ifge(Label var1) {
      this.mv.visitJumpInsn(156, var1);
   }

   public void ifgt(Label var1) {
      this.mv.visitJumpInsn(157, var1);
   }

   public void ifle(Label var1) {
      this.mv.visitJumpInsn(158, var1);
   }

   public void ificmpeq(Label var1) {
      this.mv.visitJumpInsn(159, var1);
   }

   public void ificmpne(Label var1) {
      this.mv.visitJumpInsn(160, var1);
   }

   public void ificmplt(Label var1) {
      this.mv.visitJumpInsn(161, var1);
   }

   public void ificmpge(Label var1) {
      this.mv.visitJumpInsn(162, var1);
   }

   public void ificmpgt(Label var1) {
      this.mv.visitJumpInsn(163, var1);
   }

   public void ificmple(Label var1) {
      this.mv.visitJumpInsn(164, var1);
   }

   public void ifacmpeq(Label var1) {
      this.mv.visitJumpInsn(165, var1);
   }

   public void ifacmpne(Label var1) {
      this.mv.visitJumpInsn(166, var1);
   }

   public void goTo(Label var1) {
      this.mv.visitJumpInsn(167, var1);
   }

   public void jsr(Label var1) {
      this.mv.visitJumpInsn(168, var1);
   }

   public void ret(int var1) {
      this.mv.visitVarInsn(169, var1);
   }

   public void tableswitch(int var1, int var2, Label var3, Label... var4) {
      this.mv.visitTableSwitchInsn(var1, var2, var3, var4);
   }

   public void lookupswitch(Label var1, int[] var2, Label[] var3) {
      this.mv.visitLookupSwitchInsn(var1, var2, var3);
   }

   public void areturn(Type var1) {
      this.mv.visitInsn(var1.getOpcode(172));
   }

   public void getstatic(String var1, String var2, String var3) {
      this.mv.visitFieldInsn(178, var1, var2, var3);
   }

   public void putstatic(String var1, String var2, String var3) {
      this.mv.visitFieldInsn(179, var1, var2, var3);
   }

   public void getfield(String var1, String var2, String var3) {
      this.mv.visitFieldInsn(180, var1, var2, var3);
   }

   public void putfield(String var1, String var2, String var3) {
      this.mv.visitFieldInsn(181, var1, var2, var3);
   }

   /** @deprecated */
   public void invokevirtual(String var1, String var2, String var3) {
      if (this.api >= 327680) {
         this.invokevirtual(var1, var2, var3, false);
      } else {
         this.mv.visitMethodInsn(182, var1, var2, var3);
      }
   }

   public void invokevirtual(String var1, String var2, String var3, boolean var4) {
      if (this.api < 327680) {
         if (var4) {
            throw new IllegalArgumentException("INVOKEVIRTUAL on interfaces require ASM 5");
         } else {
            this.invokevirtual(var1, var2, var3);
         }
      } else {
         this.mv.visitMethodInsn(182, var1, var2, var3, var4);
      }
   }

   /** @deprecated */
   public void invokespecial(String var1, String var2, String var3) {
      if (this.api >= 327680) {
         this.invokespecial(var1, var2, var3, false);
      } else {
         this.mv.visitMethodInsn(183, var1, var2, var3, false);
      }
   }

   public void invokespecial(String var1, String var2, String var3, boolean var4) {
      if (this.api < 327680) {
         if (var4) {
            throw new IllegalArgumentException("INVOKESPECIAL on interfaces require ASM 5");
         } else {
            this.invokespecial(var1, var2, var3);
         }
      } else {
         this.mv.visitMethodInsn(183, var1, var2, var3, var4);
      }
   }

   /** @deprecated */
   public void invokestatic(String var1, String var2, String var3) {
      if (this.api >= 327680) {
         this.invokestatic(var1, var2, var3, false);
      } else {
         this.mv.visitMethodInsn(184, var1, var2, var3, false);
      }
   }

   public void invokestatic(String var1, String var2, String var3, boolean var4) {
      if (this.api < 327680) {
         if (var4) {
            throw new IllegalArgumentException("INVOKESTATIC on interfaces require ASM 5");
         } else {
            this.invokestatic(var1, var2, var3);
         }
      } else {
         this.mv.visitMethodInsn(184, var1, var2, var3, var4);
      }
   }

   public void invokeinterface(String var1, String var2, String var3) {
      this.mv.visitMethodInsn(185, var1, var2, var3, true);
   }

   public void invokedynamic(String var1, String var2, Handle var3, Object[] var4) {
      this.mv.visitInvokeDynamicInsn(var1, var2, var3, var4);
   }

   public void anew(Type var1) {
      this.mv.visitTypeInsn(187, var1.getInternalName());
   }

   public void newarray(Type var1) {
      byte var2;
      switch (var1.getSort()) {
         case 1:
            var2 = 4;
            break;
         case 2:
            var2 = 5;
            break;
         case 3:
            var2 = 8;
            break;
         case 4:
            var2 = 9;
            break;
         case 5:
            var2 = 10;
            break;
         case 6:
            var2 = 6;
            break;
         case 7:
            var2 = 11;
            break;
         case 8:
            var2 = 7;
            break;
         default:
            this.mv.visitTypeInsn(189, var1.getInternalName());
            return;
      }

      this.mv.visitIntInsn(188, var2);
   }

   public void arraylength() {
      this.mv.visitInsn(190);
   }

   public void athrow() {
      this.mv.visitInsn(191);
   }

   public void checkcast(Type var1) {
      this.mv.visitTypeInsn(192, var1.getInternalName());
   }

   public void instanceOf(Type var1) {
      this.mv.visitTypeInsn(193, var1.getInternalName());
   }

   public void monitorenter() {
      this.mv.visitInsn(194);
   }

   public void monitorexit() {
      this.mv.visitInsn(195);
   }

   public void multianewarray(String var1, int var2) {
      this.mv.visitMultiANewArrayInsn(var1, var2);
   }

   public void ifnull(Label var1) {
      this.mv.visitJumpInsn(198, var1);
   }

   public void ifnonnull(Label var1) {
      this.mv.visitJumpInsn(199, var1);
   }

   public void mark(Label var1) {
      this.mv.visitLabel(var1);
   }

   static {
      _clinit_();
      OBJECT_TYPE = Type.getType("Ljava/lang/Object;");
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

   private static void _clinit_() {
      class$org$objectweb$asm$commons$InstructionAdapter = class$("org.python.objectweb.asm.commons.InstructionAdapter");
   }
}
