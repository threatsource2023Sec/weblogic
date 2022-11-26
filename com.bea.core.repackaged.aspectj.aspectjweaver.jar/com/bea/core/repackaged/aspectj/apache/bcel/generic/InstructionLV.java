package com.bea.core.repackaged.aspectj.apache.bcel.generic;

import com.bea.core.repackaged.aspectj.apache.bcel.Constants;
import java.io.DataOutputStream;
import java.io.IOException;

public class InstructionLV extends Instruction {
   protected int lvar = -1;

   public InstructionLV(short opcode, int lvar) {
      super(opcode);
      this.lvar = lvar;
   }

   public InstructionLV(short opcode) {
      super(opcode);
   }

   public void dump(DataOutputStream out) throws IOException {
      if (this.lvar == -1) {
         out.writeByte(this.opcode);
      } else if (this.lvar < 4) {
         if (this.opcode == 25) {
            out.writeByte(42 + this.lvar);
         } else if (this.opcode == 58) {
            out.writeByte(75 + this.lvar);
         } else if (this.opcode == 21) {
            out.writeByte(26 + this.lvar);
         } else if (this.opcode == 54) {
            out.writeByte(59 + this.lvar);
         } else if (this.opcode == 24) {
            out.writeByte(38 + this.lvar);
         } else if (this.opcode == 57) {
            out.writeByte(71 + this.lvar);
         } else if (this.opcode == 23) {
            out.writeByte(34 + this.lvar);
         } else if (this.opcode == 56) {
            out.writeByte(67 + this.lvar);
         } else if (this.opcode == 22) {
            out.writeByte(30 + this.lvar);
         } else if (this.opcode == 55) {
            out.writeByte(63 + this.lvar);
         } else {
            if (this.wide()) {
               out.writeByte(196);
            }

            out.writeByte(this.opcode);
            if (this.wide()) {
               out.writeShort(this.lvar);
            } else {
               out.writeByte(this.lvar);
            }
         }
      } else {
         if (this.wide()) {
            out.writeByte(196);
         }

         out.writeByte(this.opcode);
         if (this.wide()) {
            out.writeShort(this.lvar);
         } else {
            out.writeByte(this.lvar);
         }
      }

   }

   public String toString(boolean verbose) {
      return this.opcode >= 26 && this.opcode <= 45 || this.opcode >= 59 && this.opcode <= 78 ? super.toString(verbose) : super.toString(verbose) + (this.lvar != -1 && this.lvar < 4 ? "_" : " ") + this.lvar;
   }

   public boolean isALOAD() {
      return this.opcode == 25 || this.opcode >= 42 && this.opcode <= 45;
   }

   public boolean isASTORE() {
      return this.opcode == 58 || this.opcode >= 75 && this.opcode <= 78;
   }

   public int getBaseOpcode() {
      if ((this.opcode < 21 || this.opcode > 25) && (this.opcode < 54 || this.opcode > 58)) {
         int ret;
         if (this.opcode >= 26 && this.opcode <= 45) {
            ret = this.opcode - 26;
            ret -= ret % 4;
            ret /= 4;
            return ret + 21;
         } else {
            ret = this.opcode - 59;
            ret -= ret % 4;
            ret /= 4;
            return ret + 54;
         }
      } else {
         return this.opcode;
      }
   }

   public final int getIndex() {
      if (this.lvar != -1) {
         return this.lvar;
      } else if (this.opcode >= 26 && this.opcode <= 45) {
         return (this.opcode - 26) % 4;
      } else {
         return this.opcode >= 59 && this.opcode <= 78 ? (this.opcode - 59) % 4 : -1;
      }
   }

   public void setIndex(int i) {
      if (this.getIndex() != i) {
         if (this.opcode >= 26 && this.opcode <= 45) {
            this.opcode = (short)(21 + (this.opcode - 26) / 4);
         } else if (this.opcode >= 59 && this.opcode <= 78) {
            this.opcode = (short)(54 + (this.opcode - 59) / 4);
         }

         this.lvar = i;
      }

   }

   public boolean canSetIndex() {
      return true;
   }

   public InstructionLV setIndexAndCopyIfNecessary(int newIndex) {
      if (this.canSetIndex()) {
         this.setIndex(newIndex);
         return this;
      } else if (this.getIndex() == newIndex) {
         return this;
      } else {
         InstructionLV newInstruction = null;
         int baseOpCode = this.getBaseOpcode();
         if (newIndex < 4) {
            if (this.isStoreInstruction()) {
               newInstruction = (InstructionLV)InstructionConstants.INSTRUCTIONS[(baseOpCode - 54) * 4 + 59 + newIndex];
            } else {
               newInstruction = (InstructionLV)InstructionConstants.INSTRUCTIONS[(baseOpCode - 21) * 4 + 26 + newIndex];
            }
         } else {
            newInstruction = new InstructionLV((short)baseOpCode, newIndex);
         }

         return newInstruction;
      }
   }

   public int getLength() {
      int size = Constants.iLen[this.opcode];
      if (this.lvar == -1) {
         return size;
      } else if (this.lvar < 4) {
         if (this.opcode != 25 && this.opcode != 58) {
            if (this.opcode != 21 && this.opcode != 54) {
               if (this.opcode != 24 && this.opcode != 57) {
                  if (this.opcode != 23 && this.opcode != 56) {
                     if (this.opcode != 22 && this.opcode != 55) {
                        return this.wide() ? size + 2 : size;
                     } else {
                        return 1;
                     }
                  } else {
                     return 1;
                  }
               } else {
                  return 1;
               }
            } else {
               return 1;
            }
         } else {
            return 1;
         }
      } else {
         return this.wide() ? size + 2 : size;
      }
   }

   private final boolean wide() {
      return this.lvar > 255;
   }

   public boolean equals(Object other) {
      if (!(other instanceof InstructionLV)) {
         return false;
      } else {
         InstructionLV o = (InstructionLV)other;
         return o.opcode == this.opcode && o.lvar == this.lvar;
      }
   }

   public int hashCode() {
      return this.opcode * 37 + this.lvar;
   }
}
