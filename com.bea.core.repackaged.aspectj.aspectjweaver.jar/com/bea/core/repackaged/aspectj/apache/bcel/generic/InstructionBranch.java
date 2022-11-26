package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import java.io.DataOutputStream;
import java.io.IOException;

public class InstructionBranch extends Instruction implements InstructionTargeter {
   private static final int UNSET = -1;
   protected int targetIndex = -1;
   protected InstructionHandle targetInstruction;
   protected int positionOfThisInstruction;

   public InstructionBranch(short opcode, InstructionHandle target) {
      super(opcode);
      this.setTarget(target);
   }

   public InstructionBranch(short opcode, int index) {
      super(opcode);
      this.targetIndex = index;
   }

   public InstructionBranch(short opcode) {
      super(opcode);
   }

   public void dump(DataOutputStream out) throws IOException {
      int target = this.getTargetOffset();
      if (Math.abs(target) >= 32767 && this.opcode != 200 && this.opcode != 201) {
         throw new ClassGenException("Branch target offset too large for short.  Instruction: " + this.getName().toUpperCase() + "(" + this.opcode + ")");
      } else {
         out.writeByte(this.opcode);
         switch (this.opcode) {
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
               out.writeShort(target);
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
               throw new IllegalStateException("Don't know how to write out " + this.getName().toUpperCase());
            case 200:
            case 201:
               out.writeInt(target);
         }

      }
   }

   protected int getTargetOffset() {
      if (this.targetInstruction == null && this.targetIndex == -1) {
         throw new ClassGenException("Target of " + super.toString(true) + " is unknown");
      } else {
         return this.targetInstruction == null ? this.targetIndex : this.targetInstruction.getPosition() - this.positionOfThisInstruction;
      }
   }

   protected int updatePosition(int offset, int max_offset) {
      int i = this.getTargetOffset();
      this.positionOfThisInstruction += offset;
      if (Math.abs(i) >= 32767 - max_offset && this.opcode != 201 && this.opcode != 200) {
         if (this.opcode != 168 && this.opcode != 167) {
            throw new IllegalStateException("Unable to pack method, jump (with opcode=" + this.opcode + ") is too far: " + Math.abs(i));
         } else {
            if (this.opcode == 168) {
               this.opcode = 201;
            } else {
               this.opcode = 200;
            }

            return 2;
         }
      } else {
         return 0;
      }
   }

   public String toString(boolean verbose) {
      String s = super.toString(verbose);
      String t = "null";
      if (verbose) {
         if (this.targetInstruction != null) {
            if (this.targetInstruction.getInstruction() == this) {
               t = "<points to itself>";
            } else if (this.targetInstruction.getInstruction() == null) {
               t = "<null destination>";
            } else {
               t = this.targetInstruction.getInstruction().toString(false);
            }
         }
      } else if (this.targetInstruction != null) {
         this.targetIndex = this.getTargetOffset();
         t = "" + (this.targetIndex + this.positionOfThisInstruction);
      }

      return s + " -> " + t;
   }

   public final int getIndex() {
      return this.targetIndex;
   }

   public InstructionHandle getTarget() {
      return this.targetInstruction;
   }

   public void setTarget(InstructionHandle target) {
      notifyTarget(this.targetInstruction, target, this);
      this.targetInstruction = target;
   }

   static final void notifyTarget(InstructionHandle oldHandle, InstructionHandle newHandle, InstructionTargeter t) {
      if (oldHandle != null) {
         oldHandle.removeTargeter(t);
      }

      if (newHandle != null) {
         newHandle.addTargeter(t);
      }

   }

   public void updateTarget(InstructionHandle oldHandle, InstructionHandle newHandle) {
      if (this.targetInstruction == oldHandle) {
         this.setTarget(newHandle);
      } else {
         throw new ClassGenException("Not targeting " + oldHandle + ", but " + this.targetInstruction);
      }
   }

   public boolean containsTarget(InstructionHandle ih) {
      return this.targetInstruction == ih;
   }

   void dispose() {
      this.setTarget((InstructionHandle)null);
      this.targetIndex = -1;
      this.positionOfThisInstruction = -1;
   }

   public Type getType(ConstantPool cp) {
      return (Type)((Constants.instFlags[this.opcode] & 16384L) != 0L ? new ReturnaddressType(this.physicalSuccessor()) : super.getType(cp));
   }

   public InstructionHandle physicalSuccessor() {
      InstructionHandle ih;
      for(ih = this.targetInstruction; ih.getPrev() != null; ih = ih.getPrev()) {
      }

      while(ih.getInstruction() != this) {
         ih = ih.getNext();
      }

      InstructionHandle toThis = ih;

      do {
         if (ih == null) {
            return toThis.getNext();
         }

         ih = ih.getNext();
      } while(ih == null || ih.getInstruction() != this);

      throw new RuntimeException("physicalSuccessor() called on a shared JsrInstruction.");
   }

   public boolean isIfInstruction() {
      return (Constants.instFlags[this.opcode] & 8192L) != 0L;
   }

   public boolean equals(Object other) {
      return this == other;
   }

   public int hashCode() {
      int result = 17;
      result += this.opcode * 37;
      return result;
   }
}
