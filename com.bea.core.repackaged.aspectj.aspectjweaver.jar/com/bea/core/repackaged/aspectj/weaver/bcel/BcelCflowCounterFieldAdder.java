package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;

public class BcelCflowCounterFieldAdder extends BcelTypeMunger {
   private ResolvedMember cflowCounterField;

   public BcelCflowCounterFieldAdder(ResolvedMember cflowCounterField) {
      super((ResolvedTypeMunger)null, (ResolvedType)cflowCounterField.getDeclaringType());
      this.cflowCounterField = cflowCounterField;
   }

   public boolean munge(BcelClassWeaver weaver) {
      LazyClassGen gen = weaver.getLazyClassGen();
      if (!gen.getType().equals(this.cflowCounterField.getDeclaringType())) {
         return false;
      } else {
         FieldGen f = new FieldGen(this.cflowCounterField.getModifiers(), BcelWorld.makeBcelType(this.cflowCounterField.getReturnType()), this.cflowCounterField.getName(), gen.getConstantPool());
         gen.addField(f, this.getSourceLocation());
         LazyMethodGen clinit = gen.getAjcPreClinit();
         InstructionList setup = new InstructionList();
         InstructionFactory fact = gen.getFactory();
         setup.append(fact.createNew(new ObjectType("com.bea.core.repackaged.aspectj.runtime.internal.CFlowCounter")));
         setup.append(InstructionFactory.createDup(1));
         setup.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.CFlowCounter", "<init>", Type.VOID, new Type[0], (short)183));
         setup.append(Utility.createSet(fact, this.cflowCounterField));
         clinit.getBody().insert(setup);
         return true;
      }
   }

   public ResolvedMember getMatchingSyntheticMember(Member member) {
      return null;
   }

   public ResolvedMember getSignature() {
      return this.cflowCounterField;
   }

   public boolean matches(ResolvedType onType) {
      return onType.equals(this.cflowCounterField.getDeclaringType());
   }

   public boolean existsToSupportShadowMunging() {
      return true;
   }

   public String toString() {
      return "(BcelTypeMunger: CflowField " + this.cflowCounterField.getDeclaringType().getName() + " " + this.cflowCounterField.getName() + ")";
   }
}
