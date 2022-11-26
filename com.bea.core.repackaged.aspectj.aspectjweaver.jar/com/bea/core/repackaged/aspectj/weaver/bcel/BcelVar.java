package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;

public class BcelVar extends Var {
   private int positionInAroundState = -1;
   private int slot;
   public static final BcelVar[] NONE = new BcelVar[0];

   public BcelVar(ResolvedType type, int slot) {
      super(type);
      this.slot = slot;
   }

   public String toString() {
      return "BcelVar(" + this.getType() + " " + this.slot + (this.positionInAroundState != -1 ? " " + this.positionInAroundState : "") + ")";
   }

   public int getSlot() {
      return this.slot;
   }

   public Instruction createLoad(InstructionFactory fact) {
      return InstructionFactory.createLoad(BcelWorld.makeBcelType((UnresolvedType)this.getType()), this.slot);
   }

   public Instruction createStore(InstructionFactory fact) {
      return InstructionFactory.createStore(BcelWorld.makeBcelType((UnresolvedType)this.getType()), this.slot);
   }

   public void appendStore(InstructionList il, InstructionFactory fact) {
      il.append(this.createStore(fact));
   }

   public void appendLoad(InstructionList il, InstructionFactory fact) {
      il.append(this.createLoad(fact));
   }

   public void appendLoadAndConvert(InstructionList il, InstructionFactory fact, ResolvedType toType) {
      il.append(this.createLoad(fact));
      Utility.appendConversion(il, fact, this.getType(), toType);
   }

   public void insertLoad(InstructionList il, InstructionFactory fact) {
      il.insert(this.createLoad(fact));
   }

   public InstructionList createCopyFrom(InstructionFactory fact, int oldSlot) {
      InstructionList il = new InstructionList();
      il.append((Instruction)InstructionFactory.createLoad(BcelWorld.makeBcelType((UnresolvedType)this.getType()), oldSlot));
      il.append(this.createStore(fact));
      return il;
   }

   void appendConvertableArrayLoad(InstructionList il, InstructionFactory fact, int index, ResolvedType convertTo) {
      ResolvedType convertFromType = this.getType().getResolvedComponentType();
      this.appendLoad(il, fact);
      il.append(Utility.createConstant(fact, index));
      il.append(InstructionFactory.createArrayLoad(BcelWorld.makeBcelType((UnresolvedType)convertFromType)));
      Utility.appendConversion(il, fact, convertFromType, convertTo);
   }

   void appendConvertableArrayStore(InstructionList il, InstructionFactory fact, int index, BcelVar storee) {
      ResolvedType convertToType = this.getType().getResolvedComponentType();
      this.appendLoad(il, fact);
      il.append(Utility.createConstant(fact, index));
      storee.appendLoad(il, fact);
      Utility.appendConversion(il, fact, storee.getType(), convertToType);
      il.append(InstructionFactory.createArrayStore(BcelWorld.makeBcelType((UnresolvedType)convertToType)));
   }

   InstructionList createConvertableArrayStore(InstructionFactory fact, int index, BcelVar storee) {
      InstructionList il = new InstructionList();
      this.appendConvertableArrayStore(il, fact, index, storee);
      return il;
   }

   InstructionList createConvertableArrayLoad(InstructionFactory fact, int index, ResolvedType convertTo) {
      InstructionList il = new InstructionList();
      this.appendConvertableArrayLoad(il, fact, index, convertTo);
      return il;
   }

   public int getPositionInAroundState() {
      return this.positionInAroundState;
   }

   public void setPositionInAroundState(int positionInAroundState) {
      this.positionInAroundState = positionInAroundState;
   }
}
