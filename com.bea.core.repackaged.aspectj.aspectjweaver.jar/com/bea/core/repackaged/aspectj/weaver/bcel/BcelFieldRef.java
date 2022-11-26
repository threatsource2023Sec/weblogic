package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class BcelFieldRef extends BcelVar {
   private String className;
   private String fieldName;

   public BcelFieldRef(ResolvedType type, String className, String fieldName) {
      super(type, 0);
      this.className = className;
      this.fieldName = fieldName;
   }

   public String toString() {
      return "BcelFieldRef(" + this.getType() + " " + this.className + "." + this.fieldName + ")";
   }

   public Instruction createLoad(InstructionFactory fact) {
      return fact.createFieldAccess(this.className, this.fieldName, BcelWorld.makeBcelType((UnresolvedType)this.getType()), (short)178);
   }

   public Instruction createStore(InstructionFactory fact) {
      return fact.createFieldAccess(this.className, this.fieldName, BcelWorld.makeBcelType((UnresolvedType)this.getType()), (short)179);
   }

   public InstructionList createCopyFrom(InstructionFactory fact, int oldSlot) {
      throw new RuntimeException("unimplemented");
   }
}
