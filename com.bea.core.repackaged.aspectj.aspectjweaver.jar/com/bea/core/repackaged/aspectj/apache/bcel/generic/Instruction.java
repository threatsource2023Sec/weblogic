package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

public class Instruction implements Cloneable, Serializable, Constants {
   public short opcode = -1;

   public Instruction(short opcode) {
      this.opcode = opcode;
   }

   public void dump(DataOutputStream out) throws IOException {
      out.writeByte(this.opcode);
   }

   public String getName() {
      return Constants.OPCODE_NAMES[this.opcode];
   }

   public final Instruction copy() {
      if (InstructionConstants.INSTRUCTIONS[this.opcode] != null) {
         return this;
      } else {
         Instruction i = null;

         try {
            i = (Instruction)this.clone();
         } catch (CloneNotSupportedException var3) {
            System.err.println(var3);
         }

         return i;
      }
   }

   public static final Instruction readInstruction(ByteSequence bytes) throws IOException {
      boolean wide = false;
      short opcode = (short)bytes.readUnsignedByte();
      if (opcode == 196) {
         wide = true;
         opcode = (short)bytes.readUnsignedByte();
      }

      Instruction constantInstruction = InstructionConstants.INSTRUCTIONS[opcode];
      if (constantInstruction != null) {
         return constantInstruction;
      } else {
         Instruction obj = null;

         try {
            switch (opcode) {
               case 16:
                  obj = new InstructionByte((short)16, bytes.readByte());
                  break;
               case 17:
                  obj = new InstructionShort((short)17, bytes.readShort());
                  break;
               case 18:
                  obj = new InstructionCP((short)18, bytes.readUnsignedByte());
                  break;
               case 19:
               case 20:
                  obj = new InstructionCP(opcode, bytes.readUnsignedShort());
                  break;
               case 21:
               case 22:
               case 23:
               case 24:
               case 25:
               case 54:
               case 55:
               case 56:
               case 57:
               case 58:
                  obj = new InstructionLV(opcode, wide ? bytes.readUnsignedShort() : bytes.readUnsignedByte());
                  break;
               case 132:
                  obj = new IINC(wide ? bytes.readUnsignedShort() : bytes.readUnsignedByte(), wide ? bytes.readShort() : bytes.readByte(), wide);
                  break;
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
               case 198:
               case 199:
                  obj = new InstructionBranch(opcode, bytes.readShort());
                  break;
               case 169:
                  obj = new RET(wide ? bytes.readUnsignedShort() : bytes.readUnsignedByte(), wide);
                  break;
               case 170:
                  obj = new TABLESWITCH(bytes);
                  break;
               case 171:
                  obj = new LOOKUPSWITCH(bytes);
                  break;
               case 178:
               case 179:
               case 180:
               case 181:
                  obj = new FieldInstruction(opcode, bytes.readUnsignedShort());
                  break;
               case 182:
               case 183:
               case 184:
                  obj = new InvokeInstruction(opcode, bytes.readUnsignedShort());
                  break;
               case 185:
                  obj = new INVOKEINTERFACE(bytes.readUnsignedShort(), bytes.readUnsignedByte(), bytes.readByte());
                  break;
               case 186:
                  obj = new InvokeDynamic(bytes.readUnsignedShort(), bytes.readUnsignedShort());
                  break;
               case 187:
                  obj = new InstructionCP((short)187, bytes.readUnsignedShort());
                  break;
               case 188:
                  obj = new InstructionByte((short)188, bytes.readByte());
                  break;
               case 189:
               case 192:
                  obj = new InstructionCP(opcode, bytes.readUnsignedShort());
                  break;
               case 193:
                  obj = new InstructionCP((short)193, bytes.readUnsignedShort());
                  break;
               case 197:
                  obj = new MULTIANEWARRAY(bytes.readUnsignedShort(), bytes.readByte());
                  break;
               case 200:
               case 201:
                  obj = new InstructionBranch(opcode, bytes.readInt());
                  break;
               default:
                  throw new ClassGenException("Illegal opcode detected");
            }

            return (Instruction)obj;
         } catch (ClassGenException var6) {
            throw var6;
         } catch (Exception var7) {
            throw new ClassGenException(var7.toString());
         }
      }
   }

   public int consumeStack(ConstantPool cpg) {
      return Constants.CONSUME_STACK[this.opcode];
   }

   public int produceStack(ConstantPool cpg) {
      return Constants.stackEntriesProduced[this.opcode];
   }

   public short getOpcode() {
      return this.opcode;
   }

   public int getLength() {
      int len = Constants.iLen[this.opcode];

      assert len != 0;

      return len;
   }

   void dispose() {
   }

   public boolean equals(Object other) {
      if (this.getClass() != Instruction.class) {
         throw new RuntimeException("NO WAY " + this.getClass());
      } else if (!(other instanceof Instruction)) {
         return false;
      } else {
         return ((Instruction)other).opcode == this.opcode;
      }
   }

   public int hashCode() {
      if (this.getClass() != Instruction.class) {
         throw new RuntimeException("NO WAY " + this.getClass());
      } else {
         return this.opcode * 37;
      }
   }

   public Type getType() {
      return this.getType((ConstantPool)null);
   }

   public Type getType(ConstantPool cp) {
      Type t = Constants.types[this.opcode];
      if (t != null) {
         return t;
      } else {
         throw new RuntimeException("Do not know type for instruction " + this.getName() + "(" + this.opcode + ")");
      }
   }

   public Number getValue() {
      assert (instFlags[this.opcode] & 2L) == 0L;

      switch (this.opcode) {
         case 2:
         case 3:
         case 4:
         case 5:
         case 6:
         case 7:
         case 8:
            return new Integer(this.opcode - 3);
         default:
            throw new IllegalStateException("Not implemented yet for " + this.getName());
      }
   }

   public int getIndex() {
      return -1;
   }

   public void setIndex(int i) {
      throw new IllegalStateException("Shouldnt be asking " + this.getName().toUpperCase());
   }

   public Object getValue(ConstantPool cpg) {
      throw new IllegalStateException("Shouldnt be asking " + this.getName().toUpperCase());
   }

   public boolean isLoadInstruction() {
      return (Constants.instFlags[this.opcode] & 32L) != 0L;
   }

   public boolean isASTORE() {
      return false;
   }

   public boolean isALOAD() {
      return false;
   }

   public boolean isStoreInstruction() {
      return (Constants.instFlags[this.opcode] & 256L) != 0L;
   }

   public boolean isJsrInstruction() {
      return (Constants.instFlags[this.opcode] & 16384L) != 0L;
   }

   public boolean isConstantInstruction() {
      return (Constants.instFlags[this.opcode] & 2L) != 0L;
   }

   public boolean isConstantPoolInstruction() {
      return (Constants.instFlags[this.opcode] & 8L) != 0L;
   }

   public boolean isStackProducer() {
      return Constants.stackEntriesProduced[this.opcode] != 0;
   }

   public boolean isStackConsumer() {
      return Constants.CONSUME_STACK[this.opcode] != 0;
   }

   public boolean isIndexedInstruction() {
      return (Constants.instFlags[this.opcode] & 16L) != 0L;
   }

   public boolean isArrayCreationInstruction() {
      return this.opcode == 188 || this.opcode == 189 || this.opcode == 197;
   }

   public ObjectType getLoadClassType(ConstantPool cpg) {
      assert (Constants.instFlags[this.opcode] & 4L) == 0L;

      Type t = this.getType(cpg);
      if (t instanceof ArrayType) {
         t = ((ArrayType)t).getBasicType();
      }

      return t instanceof ObjectType ? (ObjectType)t : null;
   }

   public boolean isReturnInstruction() {
      return (Constants.instFlags[this.opcode] & 32768L) != 0L;
   }

   public boolean isLocalVariableInstruction() {
      return (Constants.instFlags[this.opcode] & 64L) != 0L;
   }

   public String toString(boolean verbose) {
      if (verbose) {
         StringBuffer sb = new StringBuffer();
         sb.append(this.getName()).append("[").append(this.opcode).append("](size").append(Constants.iLen[this.opcode]).append(")");
         return sb.toString();
      } else {
         return this.getName();
      }
   }

   public String toString() {
      return this.toString(true);
   }
}
