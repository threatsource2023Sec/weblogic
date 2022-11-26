package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;

public class TypeAnnotationAccessVar extends BcelVar {
   private BcelVar target;

   public TypeAnnotationAccessVar(ResolvedType type, BcelVar theAnnotatedTargetIsStoredHere) {
      super(type, 0);
      this.target = theAnnotatedTargetIsStoredHere;
   }

   public String toString() {
      return "TypeAnnotationAccessVar(" + this.getType() + ")";
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
      Type jlClass = BcelWorld.makeBcelType(UnresolvedType.JL_CLASS);
      Type jlaAnnotation = BcelWorld.makeBcelType(UnresolvedType.ANNOTATION);
      il.append(this.target.createLoad(fact));
      il.append((Instruction)fact.createInvoke("java/lang/Object", "getClass", jlClass, new Type[0], (short)182));
      il.append(fact.createConstant(new ObjectType(toType.getName())));
      il.append((Instruction)fact.createInvoke("java/lang/Class", "getAnnotation", jlaAnnotation, new Type[]{jlClass}, (short)182));
      il.append(Utility.createConversion(fact, jlaAnnotation, BcelWorld.makeBcelType((UnresolvedType)toType)));
      return il;
   }

   public void appendLoadAndConvert(InstructionList il, InstructionFactory fact, ResolvedType toType) {
      il.append(this.createLoadInstructions(toType, fact));
   }

   public void insertLoad(InstructionList il, InstructionFactory fact) {
      il.insert(this.createLoadInstructions(this.getType(), fact));
   }
}
