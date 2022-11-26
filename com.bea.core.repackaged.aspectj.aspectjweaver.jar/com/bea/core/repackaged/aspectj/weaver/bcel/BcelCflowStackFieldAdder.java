package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldGen;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;

public class BcelCflowStackFieldAdder extends BcelTypeMunger {
   private ResolvedMember cflowStackField;

   public BcelCflowStackFieldAdder(ResolvedMember cflowStackField) {
      super((ResolvedTypeMunger)null, (ResolvedType)cflowStackField.getDeclaringType());
      this.cflowStackField = cflowStackField;
   }

   public boolean munge(BcelClassWeaver weaver) {
      LazyClassGen gen = weaver.getLazyClassGen();
      if (!gen.getType().equals(this.cflowStackField.getDeclaringType())) {
         return false;
      } else {
         FieldGen f = new FieldGen(this.cflowStackField.getModifiers(), BcelWorld.makeBcelType(this.cflowStackField.getReturnType()), this.cflowStackField.getName(), gen.getConstantPool());
         gen.addField(f, this.getSourceLocation());
         LazyMethodGen clinit = gen.getAjcPreClinit();
         InstructionList setup = new InstructionList();
         InstructionFactory fact = gen.getFactory();
         setup.append(fact.createNew("com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack"));
         setup.append(InstructionFactory.createDup(1));
         setup.append((Instruction)fact.createInvoke("com.bea.core.repackaged.aspectj.runtime.internal.CFlowStack", "<init>", Type.VOID, Type.NO_ARGS, (short)183));
         setup.append(Utility.createSet(fact, this.cflowStackField));
         clinit.getBody().insert(setup);
         return true;
      }
   }

   public ResolvedMember getMatchingSyntheticMember(Member member) {
      return null;
   }

   public ResolvedMember getSignature() {
      return this.cflowStackField;
   }

   public boolean matches(ResolvedType onType) {
      return onType.equals(this.cflowStackField.getDeclaringType());
   }

   public boolean existsToSupportShadowMunging() {
      return true;
   }
}
