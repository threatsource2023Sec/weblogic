package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Utility;

public class InstructionFactory implements InstructionConstants {
   protected ClassGen cg;
   protected ConstantPool cp;
   private static final char[] shortNames = new char[]{'C', 'F', 'D', 'B', 'S', 'I', 'L'};

   public InstructionFactory(ClassGen cg, ConstantPool cp) {
      this.cg = cg;
      this.cp = cp;
   }

   public InstructionFactory(ClassGen cg) {
      this(cg, cg.getConstantPool());
   }

   public InstructionFactory(ConstantPool cp) {
      this((ClassGen)null, cp);
   }

   public InvokeInstruction createInvoke(String class_name, String name, Type ret_type, Type[] arg_types, short kind) {
      String signature = Utility.toMethodSignature(ret_type, arg_types);
      int index;
      if (kind == 185) {
         index = this.cp.addInterfaceMethodref(class_name, name, signature);
      } else {
         if (kind == 186) {
            throw new IllegalStateException("NYI");
         }

         index = this.cp.addMethodref(class_name, name, signature);
      }

      switch (kind) {
         case 182:
            return new InvokeInstruction((short)182, index);
         case 183:
            return new InvokeInstruction((short)183, index);
         case 184:
            return new InvokeInstruction((short)184, index);
         case 185:
            int nargs = 0;

            for(int i = 0; i < arg_types.length; ++i) {
               nargs += arg_types[i].getSize();
            }

            return new INVOKEINTERFACE(index, nargs + 1, 0);
         default:
            throw new RuntimeException("Oops: Unknown invoke kind:" + kind);
      }
   }

   public InvokeInstruction createInvoke(String class_name, String name, String signature, short kind) {
      int index;
      if (kind == 185) {
         index = this.cp.addInterfaceMethodref(class_name, name, signature);
      } else {
         if (kind == 186) {
            throw new IllegalStateException("NYI");
         }

         index = this.cp.addMethodref(class_name, name, signature);
      }

      switch (kind) {
         case 182:
            return new InvokeInstruction((short)182, index);
         case 183:
            return new InvokeInstruction((short)183, index);
         case 184:
            return new InvokeInstruction((short)184, index);
         case 185:
            Type[] argumentTypes = Type.getArgumentTypes(signature);
            int nargs = 0;

            for(int i = 0; i < argumentTypes.length; ++i) {
               nargs += argumentTypes[i].getSize();
            }

            return new INVOKEINTERFACE(index, nargs + 1, 0);
         default:
            throw new RuntimeException("Oops: Unknown invoke kind:" + kind);
      }
   }

   public static Instruction createALOAD(int n) {
      return n < 4 ? new InstructionLV((short)(42 + n)) : new InstructionLV((short)25, n);
   }

   public static Instruction createASTORE(int n) {
      return n < 4 ? new InstructionLV((short)(75 + n)) : new InstructionLV((short)58, n);
   }

   public Instruction createConstant(Object value) {
      Instruction instruction;
      if (value instanceof Number) {
         instruction = PUSH(this.cp, (Number)value);
      } else if (value instanceof String) {
         instruction = PUSH(this.cp, (String)value);
      } else if (value instanceof Boolean) {
         instruction = PUSH(this.cp, (Boolean)value);
      } else if (value instanceof Character) {
         instruction = PUSH(this.cp, (Character)value);
      } else {
         if (!(value instanceof ObjectType)) {
            throw new ClassGenException("Illegal type: " + value.getClass());
         }

         instruction = PUSH(this.cp, (ObjectType)value);
      }

      return instruction;
   }

   public FieldInstruction createFieldAccess(String class_name, String name, Type type, short kind) {
      String signature = type.getSignature();
      int index = this.cp.addFieldref(class_name, name, signature);
      switch (kind) {
         case 178:
            return new FieldInstruction((short)178, index);
         case 179:
            return new FieldInstruction((short)179, index);
         case 180:
            return new FieldInstruction((short)180, index);
         case 181:
            return new FieldInstruction((short)181, index);
         default:
            throw new RuntimeException("Oops: Unknown getfield kind:" + kind);
      }
   }

   public static Instruction createThis() {
      return new InstructionLV((short)25, 0);
   }

   public static Instruction createReturn(Type type) {
      switch (type.getType()) {
         case 4:
         case 5:
         case 8:
         case 9:
         case 10:
            return IRETURN;
         case 6:
            return FRETURN;
         case 7:
            return DRETURN;
         case 11:
            return LRETURN;
         case 12:
            return RETURN;
         case 13:
         case 14:
            return ARETURN;
         default:
            throw new RuntimeException("Invalid type: " + type);
      }
   }

   public static Instruction createPop(int size) {
      return size == 2 ? POP2 : POP;
   }

   public static Instruction createDup(int size) {
      return size == 2 ? DUP2 : DUP;
   }

   public static Instruction createDup_2(int size) {
      return size == 2 ? DUP2_X2 : DUP_X2;
   }

   public static Instruction createDup_1(int size) {
      return size == 2 ? DUP2_X1 : DUP_X1;
   }

   public static InstructionLV createStore(Type type, int index) {
      switch (type.getType()) {
         case 4:
         case 5:
         case 8:
         case 9:
         case 10:
            return new InstructionLV((short)54, index);
         case 6:
            return new InstructionLV((short)56, index);
         case 7:
            return new InstructionLV((short)57, index);
         case 11:
            return new InstructionLV((short)55, index);
         case 12:
         default:
            throw new RuntimeException("Invalid type " + type);
         case 13:
         case 14:
            return new InstructionLV((short)58, index);
      }
   }

   public static InstructionLV createLoad(Type type, int index) {
      switch (type.getType()) {
         case 4:
         case 5:
         case 8:
         case 9:
         case 10:
            return new InstructionLV((short)21, index);
         case 6:
            return new InstructionLV((short)23, index);
         case 7:
            return new InstructionLV((short)24, index);
         case 11:
            return new InstructionLV((short)22, index);
         case 12:
         default:
            throw new RuntimeException("Invalid type " + type);
         case 13:
         case 14:
            return new InstructionLV((short)25, index);
      }
   }

   public static Instruction createArrayLoad(Type type) {
      switch (type.getType()) {
         case 4:
         case 8:
            return BALOAD;
         case 5:
            return CALOAD;
         case 6:
            return FALOAD;
         case 7:
            return DALOAD;
         case 9:
            return SALOAD;
         case 10:
            return IALOAD;
         case 11:
            return LALOAD;
         case 12:
         default:
            throw new RuntimeException("Invalid type " + type);
         case 13:
         case 14:
            return AALOAD;
      }
   }

   public static Instruction createArrayStore(Type type) {
      switch (type.getType()) {
         case 4:
         case 8:
            return BASTORE;
         case 5:
            return CASTORE;
         case 6:
            return FASTORE;
         case 7:
            return DASTORE;
         case 9:
            return SASTORE;
         case 10:
            return IASTORE;
         case 11:
            return LASTORE;
         case 12:
         default:
            throw new RuntimeException("Invalid type " + type);
         case 13:
         case 14:
            return AASTORE;
      }
   }

   public Instruction createCast(Type src_type, Type dest_type) {
      if (src_type instanceof BasicType && dest_type instanceof BasicType) {
         byte dest = dest_type.getType();
         byte src = src_type.getType();
         if (dest == 11 && (src == 5 || src == 8 || src == 9)) {
            src = 10;
         }

         if (src == 7) {
            switch (dest) {
               case 6:
                  return InstructionConstants.D2F;
               case 7:
               case 8:
               case 9:
               default:
                  break;
               case 10:
                  return InstructionConstants.D2I;
               case 11:
                  return InstructionConstants.D2L;
            }
         } else if (src == 6) {
            switch (dest) {
               case 7:
                  return InstructionConstants.F2D;
               case 8:
               case 9:
               default:
                  break;
               case 10:
                  return InstructionConstants.F2I;
               case 11:
                  return InstructionConstants.F2L;
            }
         } else if (src == 10) {
            switch (dest) {
               case 5:
                  return InstructionConstants.I2C;
               case 6:
                  return InstructionConstants.I2F;
               case 7:
                  return InstructionConstants.I2D;
               case 8:
                  return InstructionConstants.I2B;
               case 9:
                  return InstructionConstants.I2S;
               case 10:
               default:
                  break;
               case 11:
                  return InstructionConstants.I2L;
            }
         } else if (src == 11) {
            switch (dest) {
               case 6:
                  return InstructionConstants.L2F;
               case 7:
                  return InstructionConstants.L2D;
               case 8:
               case 9:
               default:
                  break;
               case 10:
                  return InstructionConstants.L2I;
            }
         }

         return null;
      } else if (src_type instanceof ReferenceType && dest_type instanceof ReferenceType) {
         return dest_type instanceof ArrayType ? new InstructionCP((short)192, this.cp.addArrayClass((ArrayType)dest_type)) : new InstructionCP((short)192, this.cp.addClass(((ObjectType)dest_type).getClassName()));
      } else {
         throw new RuntimeException("Can not cast " + src_type + " to " + dest_type);
      }
   }

   public FieldInstruction createGetField(String class_name, String name, Type t) {
      return new FieldInstruction((short)180, this.cp.addFieldref(class_name, name, t.getSignature()));
   }

   public FieldInstruction createGetStatic(String class_name, String name, Type t) {
      return new FieldInstruction((short)178, this.cp.addFieldref(class_name, name, t.getSignature()));
   }

   public FieldInstruction createPutField(String class_name, String name, Type t) {
      return new FieldInstruction((short)181, this.cp.addFieldref(class_name, name, t.getSignature()));
   }

   public FieldInstruction createPutStatic(String class_name, String name, Type t) {
      return new FieldInstruction((short)179, this.cp.addFieldref(class_name, name, t.getSignature()));
   }

   public Instruction createCheckCast(ReferenceType t) {
      return t instanceof ArrayType ? new InstructionCP((short)192, this.cp.addArrayClass((ArrayType)t)) : new InstructionCP((short)192, this.cp.addClass((ObjectType)t));
   }

   public Instruction createInstanceOf(ReferenceType t) {
      return t instanceof ArrayType ? new InstructionCP((short)193, this.cp.addArrayClass((ArrayType)t)) : new InstructionCP((short)193, this.cp.addClass((ObjectType)t));
   }

   public Instruction createNew(ObjectType t) {
      return new InstructionCP((short)187, this.cp.addClass(t));
   }

   public Instruction createNew(String s) {
      return this.createNew(new ObjectType(s));
   }

   public Instruction createNewArray(Type t, short dim) {
      if (dim == 1) {
         if (t instanceof ObjectType) {
            return new InstructionCP((short)189, this.cp.addClass((ObjectType)t));
         } else {
            return (Instruction)(t instanceof ArrayType ? new InstructionCP((short)189, this.cp.addArrayClass((ArrayType)t)) : new InstructionByte((short)188, ((BasicType)t).getType()));
         }
      } else {
         ArrayType at;
         if (t instanceof ArrayType) {
            at = (ArrayType)t;
         } else {
            at = new ArrayType(t, dim);
         }

         return new MULTIANEWARRAY(this.cp.addArrayClass(at), dim);
      }
   }

   public static Instruction createNull(Type type) {
      switch (type.getType()) {
         case 4:
         case 5:
         case 8:
         case 9:
         case 10:
            return ICONST_0;
         case 6:
            return FCONST_0;
         case 7:
            return DCONST_0;
         case 11:
            return LCONST_0;
         case 12:
            return NOP;
         case 13:
         case 14:
            return ACONST_NULL;
         default:
            throw new RuntimeException("Invalid type: " + type);
      }
   }

   public static InstructionBranch createBranchInstruction(short opcode, InstructionHandle target) {
      switch (opcode) {
         case 153:
            return new InstructionBranch((short)153, target);
         case 154:
            return new InstructionBranch((short)154, target);
         case 155:
            return new InstructionBranch((short)155, target);
         case 156:
            return new InstructionBranch((short)156, target);
         case 157:
            return new InstructionBranch((short)157, target);
         case 158:
            return new InstructionBranch((short)158, target);
         case 159:
            return new InstructionBranch((short)159, target);
         case 160:
            return new InstructionBranch((short)160, target);
         case 161:
            return new InstructionBranch((short)161, target);
         case 162:
            return new InstructionBranch((short)162, target);
         case 163:
            return new InstructionBranch((short)163, target);
         case 164:
            return new InstructionBranch((short)164, target);
         case 165:
            return new InstructionBranch((short)165, target);
         case 166:
            return new InstructionBranch((short)166, target);
         case 167:
            return new InstructionBranch((short)167, target);
         case 168:
            return new InstructionBranch((short)168, target);
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
            throw new RuntimeException("Invalid opcode: " + opcode);
         case 198:
            return new InstructionBranch((short)198, target);
         case 199:
            return new InstructionBranch((short)199, target);
         case 200:
            return new InstructionBranch((short)200, target);
         case 201:
            return new InstructionBranch((short)201, target);
      }
   }

   public void setClassGen(ClassGen c) {
      this.cg = c;
   }

   public ClassGen getClassGen() {
      return this.cg;
   }

   public void setConstantPool(ConstantPool c) {
      this.cp = c;
   }

   public ConstantPool getConstantPool() {
      return this.cp;
   }

   public static Instruction PUSH(ConstantPool cp, int value) {
      Instruction instruction = null;
      if (value >= -1 && value <= 5) {
         return INSTRUCTIONS[3 + value];
      } else {
         if (value >= -128 && value <= 127) {
            instruction = new InstructionByte((short)16, (byte)value);
         } else if (value >= -32768 && value <= 32767) {
            instruction = new InstructionShort((short)17, (short)value);
         } else {
            int pos = cp.addInteger(value);
            if (pos <= 255) {
               instruction = new InstructionCP((short)18, pos);
            } else {
               instruction = new InstructionCP((short)19, pos);
            }
         }

         return (Instruction)instruction;
      }
   }

   public static Instruction PUSH(ConstantPool cp, ObjectType t) {
      return new InstructionCP((short)19, cp.addClass(t));
   }

   public static Instruction PUSH(ConstantPool cp, boolean value) {
      return INSTRUCTIONS[3 + (value ? 1 : 0)];
   }

   public static Instruction PUSH(ConstantPool cp, float value) {
      Instruction instruction = null;
      if ((double)value == 0.0) {
         instruction = FCONST_0;
      } else if ((double)value == 1.0) {
         instruction = FCONST_1;
      } else if ((double)value == 2.0) {
         instruction = FCONST_2;
      } else {
         int i = cp.addFloat(value);
         instruction = new InstructionCP((short)(i <= 255 ? 18 : 19), i);
      }

      return (Instruction)instruction;
   }

   public static Instruction PUSH(ConstantPool cp, long value) {
      Instruction instruction = null;
      if (value == 0L) {
         instruction = LCONST_0;
      } else if (value == 1L) {
         instruction = LCONST_1;
      } else {
         instruction = new InstructionCP((short)20, cp.addLong(value));
      }

      return (Instruction)instruction;
   }

   public static Instruction PUSH(ConstantPool cp, double value) {
      Instruction instruction = null;
      if (value == 0.0) {
         instruction = DCONST_0;
      } else if (value == 1.0) {
         instruction = DCONST_1;
      } else {
         instruction = new InstructionCP((short)20, cp.addDouble(value));
      }

      return (Instruction)instruction;
   }

   public static Instruction PUSH(ConstantPool cp, String value) {
      Instruction instruction = null;
      if (value == null) {
         instruction = ACONST_NULL;
      } else {
         int i = cp.addString(value);
         instruction = new InstructionCP((short)(i <= 255 ? 18 : 19), i);
      }

      return (Instruction)instruction;
   }

   public static Instruction PUSH(ConstantPool cp, Number value) {
      Instruction instruction = null;
      if (!(value instanceof Integer) && !(value instanceof Short) && !(value instanceof Byte)) {
         if (value instanceof Double) {
            instruction = PUSH(cp, value.doubleValue());
         } else if (value instanceof Float) {
            instruction = PUSH(cp, value.floatValue());
         } else {
            if (!(value instanceof Long)) {
               throw new ClassGenException("What's this: " + value);
            }

            instruction = PUSH(cp, value.longValue());
         }
      } else {
         instruction = PUSH(cp, value.intValue());
      }

      return instruction;
   }

   public static Instruction PUSH(ConstantPool cp, Character value) {
      return PUSH(cp, value);
   }

   public static Instruction PUSH(ConstantPool cp, Boolean value) {
      return PUSH(cp, value);
   }

   public InstructionList PUSHCLASS(ConstantPool cp, String className) {
      InstructionList iList = new InstructionList();
      int classIndex = cp.addClass(className);
      if (this.cg != null && this.cg.getMajor() >= 49) {
         if (classIndex <= 255) {
            iList.append((Instruction)(new InstructionCP((short)18, classIndex)));
         } else {
            iList.append((Instruction)(new InstructionCP((short)19, classIndex)));
         }
      } else {
         iList.append(PUSH(cp, className));
         iList.append((Instruction)this.createInvoke("java.lang.Class", "forName", ObjectType.CLASS, Type.STRINGARRAY1, (short)184));
      }

      return iList;
   }
}
