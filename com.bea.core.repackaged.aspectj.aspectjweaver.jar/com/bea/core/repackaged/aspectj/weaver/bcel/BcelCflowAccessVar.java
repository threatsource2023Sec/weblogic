package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class BcelCflowAccessVar extends BcelVar {
   private Member stackField;
   private int index;

   public BcelCflowAccessVar(ResolvedType type, Member stackField, int index) {
      super(type, 0);
      this.stackField = stackField;
      this.index = index;
   }

   public String toString() {
      return "BcelCflowAccessVar(" + this.getType() + " " + this.stackField + "." + this.index + ")";
   }

   public Instruction createLoad(InstructionFactory fact) {
      throw new RuntimeException("unimplemented");
   }

   public Instruction createStore(InstructionFactory fact) {
      throw new RuntimeException("unimplemented");
   }

   public InstructionList createCopyFrom(InstructionFactory fact, int oldSlot) {
      throw new RuntimeException("unimplemented");
   }

   public void appendLoad(InstructionList il, InstructionFactory fact) {
      il.append(this.createLoadInstructions(this.getType(), fact));
   }

   public InstructionList createLoadInstructions(ResolvedType toType, InstructionFactory fact) {
      InstructionList il = new InstructionList();
      il.append(Utility.createGet(fact, this.stackField));
      il.append(Utility.createConstant(fact, this.index));
      il.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack", "get", Type.OBJECT, new Type[]{Type.INT}, (short)182));
      il.append(Utility.createConversion(fact, Type.OBJECT, BcelWorld.makeBcelType((UnresolvedType)toType)));
      return il;
   }

   public void appendLoadAndConvert(InstructionList il, InstructionFactory fact, ResolvedType toType) {
      il.append(this.createLoadInstructions(toType, fact));
   }

   public void insertLoad(InstructionList il, InstructionFactory fact) {
      il.insert(this.createLoadInstructions(this.getType(), fact));
   }
}
