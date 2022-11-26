package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.util.ByteSequence;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class InstructionSelect extends InstructionBranch {
   protected int[] match;
   protected int[] indices;
   protected InstructionHandle[] targets;
   protected int fixedLength;
   protected int matchLength;
   protected int padding = 0;
   protected short length;

   InstructionSelect(short opcode, int[] match, InstructionHandle[] targets, InstructionHandle target) {
      super(opcode, target);
      this.targets = targets;

      for(int i = 0; i < targets.length; ++i) {
         notifyTarget((InstructionHandle)null, targets[i], this);
      }

      this.match = match;
      if ((this.matchLength = match.length) != targets.length) {
         throw new ClassGenException("Match and target array have not the same length");
      } else {
         this.indices = new int[this.matchLength];
      }
   }

   protected int getTargetOffset(InstructionHandle target) {
      if (target == null) {
         throw new ClassGenException("Target of " + super.toString(true) + " is invalid null handle");
      } else {
         int t = target.getPosition();
         if (t < 0) {
            throw new ClassGenException("Invalid branch target position offset for " + super.toString(true) + ":" + t + ":" + target);
         } else {
            return t - this.positionOfThisInstruction;
         }
      }
   }

   protected int updatePosition(int offset, int max_offset) {
      this.positionOfThisInstruction += offset;
      short old_length = this.length;
      this.padding = (4 - (this.positionOfThisInstruction + 1) % 4) % 4;
      this.length = (short)(this.fixedLength + this.padding);
      return this.length - old_length;
   }

   public void dump(DataOutputStream out) throws IOException {
      out.writeByte(this.opcode);

      for(int i = 0; i < this.padding; ++i) {
         out.writeByte(0);
      }

      this.targetIndex = this.getTargetOffset();
      out.writeInt(this.targetIndex);
   }

   public InstructionSelect(short opcode, ByteSequence bytes) throws IOException {
      super(opcode);
      this.padding = (4 - bytes.getIndex() % 4) % 4;

      for(int i = 0; i < this.padding; ++i) {
         bytes.readByte();
      }

      this.targetIndex = bytes.readInt();
   }

   public String toString(boolean verbose) {
      StringBuffer buf = new StringBuffer(super.toString(verbose));
      if (verbose) {
         for(int i = 0; i < this.matchLength; ++i) {
            String s = "null";
            if (this.targets[i] != null) {
               s = this.targets[i].getInstruction().toString();
            }

            buf.append("(" + this.match[i] + ", " + s + " = {" + this.indices[i] + "})");
         }
      } else {
         buf.append(" ...");
      }

      return buf.toString();
   }

   public void setTarget(int i, InstructionHandle target) {
      notifyTarget(this.targets[i], target, this);
      this.targets[i] = target;
   }

   public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
      boolean targeted = false;
      if (this.targetInstruction == old_ih) {
         targeted = true;
         this.setTarget(new_ih);
      }

      for(int i = 0; i < this.targets.length; ++i) {
         if (this.targets[i] == old_ih) {
            targeted = true;
            this.setTarget(i, new_ih);
         }
      }

      if (!targeted) {
         throw new ClassGenException("Not targeting " + old_ih);
      }
   }

   public boolean containsTarget(InstructionHandle ih) {
      if (this.targetInstruction == ih) {
         return true;
      } else {
         for(int i = 0; i < this.targets.length; ++i) {
            if (this.targets[i] == ih) {
               return true;
            }
         }

         return false;
      }
   }

   void dispose() {
      super.dispose();

      for(int i = 0; i < this.targets.length; ++i) {
         this.targets[i].removeTargeter(this);
      }

   }

   public int[] getMatchs() {
      return this.match;
   }

   public int[] getIndices() {
      return this.indices;
   }

   public boolean equals(Object other) {
      return this == other;
   }

   public int hashCode() {
      return this.opcode * 37;
   }

   public InstructionHandle[] getTargets() {
      return this.targets;
   }

   public int getLength() {
      return this.length;
   }
}
