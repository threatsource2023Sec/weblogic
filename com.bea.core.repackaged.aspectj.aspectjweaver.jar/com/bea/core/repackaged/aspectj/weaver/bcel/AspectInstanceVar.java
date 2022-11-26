package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;

public class AspectInstanceVar extends BcelVar {
   public AspectInstanceVar(ResolvedType type) {
      super(type, -1);
   }

   public Instruction createLoad(InstructionFactory fact) {
      throw new IllegalStateException();
   }

   public Instruction createStore(InstructionFactory fact) {
      throw new IllegalStateException();
   }

   public void appendStore(InstructionList il, InstructionFactory fact) {
      throw new IllegalStateException();
   }

   public void appendLoad(InstructionList il, InstructionFactory fact) {
      throw new IllegalStateException();
   }

   public void appendLoadAndConvert(InstructionList il, InstructionFactory fact, ResolvedType toType) {
      throw new IllegalStateException();
   }

   public void insertLoad(InstructionList il, InstructionFactory fact) {
      InstructionList loadInstructions = new InstructionList();
      loadInstructions.append((Instruction)fact.createInvoke(this.getType().getName(), "aspectOf", "()" + this.getType().getSignature(), (short)184));
      il.insert(loadInstructions);
   }

   public InstructionList createCopyFrom(InstructionFactory fact, int oldSlot) {
      throw new IllegalStateException();
   }

   void appendConvertableArrayLoad(InstructionList il, InstructionFactory fact, int index, ResolvedType convertTo) {
      throw new IllegalStateException();
   }

   void appendConvertableArrayStore(InstructionList il, InstructionFactory fact, int index, BcelVar storee) {
      throw new IllegalStateException();
   }

   InstructionList createConvertableArrayStore(InstructionFactory fact, int index, BcelVar storee) {
      throw new IllegalStateException();
   }

   InstructionList createConvertableArrayLoad(InstructionFactory fact, int index, ResolvedType convertTo) {
      throw new IllegalStateException();
   }

   public int getPositionInAroundState() {
      throw new IllegalStateException();
   }

   public void setPositionInAroundState(int positionInAroundState) {
      throw new IllegalStateException();
   }
}
