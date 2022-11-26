package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.ConstantPool;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.FieldInstruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InvokeInstruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.AjAttribute;
import com.bea.core.repackaged.aspectj.weaver.AjcMemberMaker;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.NameMangler;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.ResolvedTypeMunger;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BcelAccessForInlineMunger extends BcelTypeMunger {
   private Map inlineAccessors;
   private LazyClassGen aspectGen;
   private Set inlineAccessorMethodGens;

   public BcelAccessForInlineMunger(ResolvedType aspectType) {
      super((ResolvedTypeMunger)null, aspectType);
      if (aspectType.getWorld().isXnoInline()) {
         throw new Error("This should not happen");
      }
   }

   public boolean munge(BcelClassWeaver weaver) {
      this.aspectGen = weaver.getLazyClassGen();
      this.inlineAccessors = new HashMap(0);
      this.inlineAccessorMethodGens = new HashSet();
      Iterator i$ = this.aspectGen.getMethodGens().iterator();

      LazyMethodGen lazyMethodGen;
      while(i$.hasNext()) {
         lazyMethodGen = (LazyMethodGen)i$.next();
         if (lazyMethodGen.hasAnnotation(UnresolvedType.forName("com/bea/core/repackaged/aspectj/lang/annotation/Around"))) {
            this.openAroundAdvice(lazyMethodGen);
         }
      }

      i$ = this.inlineAccessorMethodGens.iterator();

      while(i$.hasNext()) {
         lazyMethodGen = (LazyMethodGen)i$.next();
         this.aspectGen.addMethodGen(lazyMethodGen);
      }

      this.inlineAccessorMethodGens = null;
      return true;
   }

   public ResolvedMember getMatchingSyntheticMember(Member member) {
      ResolvedMember rm = (ResolvedMember)this.inlineAccessors.get(member.getName());
      return rm;
   }

   public ResolvedMember getSignature() {
      return null;
   }

   public boolean matches(ResolvedType onType) {
      return this.aspectType.equals(onType);
   }

   private void openAroundAdvice(LazyMethodGen aroundAdvice) {
      InstructionHandle curr = aroundAdvice.getBody().getStart();
      InstructionHandle end = aroundAdvice.getBody().getEnd();
      ConstantPool cpg = aroundAdvice.getEnclosingClass().getConstantPool();
      InstructionFactory factory = aroundAdvice.getEnclosingClass().getFactory();

      boolean realizedCannotInline;
      InstructionHandle next;
      for(realizedCannotInline = false; curr != end && !realizedCannotInline; curr = next) {
         next = curr.getNext();
         Instruction inst = curr.getInstruction();
         ResolvedType callee;
         ResolvedMember accessor;
         if (inst instanceof InvokeInstruction) {
            InvokeInstruction invoke = (InvokeInstruction)inst;
            callee = this.aspectGen.getWorld().resolve(UnresolvedType.forName(invoke.getClassName(cpg)));
            List methods = callee.getMethodsWithoutIterator(false, true, false);
            Iterator i$ = methods.iterator();

            while(i$.hasNext()) {
               accessor = (ResolvedMember)i$.next();
               if (invoke.getName(cpg).equals(accessor.getName()) && invoke.getSignature(cpg).equals(accessor.getSignature()) && !accessor.isPublic()) {
                  if ("<init>".equals(invoke.getName(cpg))) {
                     aroundAdvice.setCanInline(false);
                     realizedCannotInline = true;
                  } else {
                     ResolvedType memberType = this.aspectGen.getWorld().resolve(accessor.getDeclaringType());
                     ResolvedMember accessor;
                     InvokeInstruction newInst;
                     if (!this.aspectType.equals(memberType) && memberType.isAssignableFrom(this.aspectType)) {
                        accessor = this.createOrGetInlineAccessorForSuperDispatch(accessor);
                        newInst = factory.createInvoke(this.aspectType.getName(), accessor.getName(), BcelWorld.makeBcelType(accessor.getReturnType()), BcelWorld.makeBcelTypes(accessor.getParameterTypes()), (short)182);
                        curr.setInstruction(newInst);
                     } else {
                        accessor = this.createOrGetInlineAccessorForMethod(accessor);
                        newInst = factory.createInvoke(this.aspectType.getName(), accessor.getName(), BcelWorld.makeBcelType(accessor.getReturnType()), BcelWorld.makeBcelTypes(accessor.getParameterTypes()), (short)184);
                        curr.setInstruction(newInst);
                     }
                  }
                  break;
               }
            }
         } else if (inst instanceof FieldInstruction) {
            FieldInstruction invoke = (FieldInstruction)inst;
            callee = this.aspectGen.getWorld().resolve(UnresolvedType.forName(invoke.getClassName(cpg)));

            for(int i = 0; i < callee.getDeclaredJavaFields().length; ++i) {
               ResolvedMember resolvedMember = callee.getDeclaredJavaFields()[i];
               if (invoke.getName(cpg).equals(resolvedMember.getName()) && invoke.getSignature(cpg).equals(resolvedMember.getSignature()) && !resolvedMember.isPublic()) {
                  if (inst.opcode != 180 && inst.opcode != 178) {
                     accessor = this.createOrGetInlineAccessorForFieldSet(resolvedMember);
                  } else {
                     accessor = this.createOrGetInlineAccessorForFieldGet(resolvedMember);
                  }

                  InvokeInstruction newInst = factory.createInvoke(this.aspectType.getName(), accessor.getName(), BcelWorld.makeBcelType(accessor.getReturnType()), BcelWorld.makeBcelTypes(accessor.getParameterTypes()), (short)184);
                  curr.setInstruction(newInst);
                  break;
               }
            }
         }
      }

      if (!realizedCannotInline) {
         aroundAdvice.setCanInline(true);
      }

   }

   private ResolvedMember createOrGetInlineAccessorForMethod(ResolvedMember resolvedMember) {
      String accessorName = NameMangler.inlineAccessMethodForMethod(resolvedMember.getName(), resolvedMember.getDeclaringType(), this.aspectType);
      ResolvedMember inlineAccessor = (ResolvedMember)this.inlineAccessors.get(accessorName);
      if (inlineAccessor == null) {
         inlineAccessor = AjcMemberMaker.inlineAccessMethodForMethod(this.aspectType, resolvedMember);
         InstructionFactory factory = this.aspectGen.getFactory();
         LazyMethodGen method = this.makeMethodGen(this.aspectGen, inlineAccessor);
         method.makeSynthetic();
         List methodAttributes = new ArrayList();
         methodAttributes.add(new AjAttribute.AjSynthetic());
         methodAttributes.add(new AjAttribute.EffectiveSignatureAttribute(resolvedMember, Shadow.MethodCall, false));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(0), this.aspectGen.getConstantPool()));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(1), this.aspectGen.getConstantPool()));
         this.inlineAccessorMethodGens.add(method);
         InstructionList il = method.getBody();
         int register = 0;
         int i = 0;

         for(int max = inlineAccessor.getParameterTypes().length; i < max; ++i) {
            UnresolvedType ptype = inlineAccessor.getParameterTypes()[i];
            Type type = BcelWorld.makeBcelType(ptype);
            il.append((Instruction)InstructionFactory.createLoad(type, register));
            register += type.getSize();
         }

         il.append(Utility.createInvoke(factory, (short)(Modifier.isStatic(resolvedMember.getModifiers()) ? 184 : 182), resolvedMember));
         il.append(InstructionFactory.createReturn(BcelWorld.makeBcelType(inlineAccessor.getReturnType())));
         this.inlineAccessors.put(accessorName, new BcelMethod(this.aspectGen.getBcelObjectType(), method.getMethod(), methodAttributes));
      }

      return inlineAccessor;
   }

   private ResolvedMember createOrGetInlineAccessorForSuperDispatch(ResolvedMember resolvedMember) {
      String accessor = NameMangler.superDispatchMethod(this.aspectType, resolvedMember.getName());
      ResolvedMember inlineAccessor = (ResolvedMember)this.inlineAccessors.get(accessor);
      if (inlineAccessor == null) {
         inlineAccessor = AjcMemberMaker.superAccessMethod(this.aspectType, resolvedMember);
         InstructionFactory factory = this.aspectGen.getFactory();
         LazyMethodGen method = this.makeMethodGen(this.aspectGen, inlineAccessor);
         method.makeSynthetic();
         List methodAttributes = new ArrayList();
         methodAttributes.add(new AjAttribute.AjSynthetic());
         methodAttributes.add(new AjAttribute.EffectiveSignatureAttribute(resolvedMember, Shadow.MethodCall, false));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(0), this.aspectGen.getConstantPool()));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(1), this.aspectGen.getConstantPool()));
         this.inlineAccessorMethodGens.add(method);
         InstructionList il = method.getBody();
         il.append((Instruction)InstructionConstants.ALOAD_0);
         int register = 1;

         for(int i = 0; i < inlineAccessor.getParameterTypes().length; ++i) {
            UnresolvedType typeX = inlineAccessor.getParameterTypes()[i];
            Type type = BcelWorld.makeBcelType(typeX);
            il.append((Instruction)InstructionFactory.createLoad(type, register));
            register += type.getSize();
         }

         il.append(Utility.createInvoke(factory, (short)183, resolvedMember));
         il.append(InstructionFactory.createReturn(BcelWorld.makeBcelType(inlineAccessor.getReturnType())));
         this.inlineAccessors.put(accessor, new BcelMethod(this.aspectGen.getBcelObjectType(), method.getMethod(), methodAttributes));
      }

      return inlineAccessor;
   }

   private ResolvedMember createOrGetInlineAccessorForFieldGet(ResolvedMember resolvedMember) {
      String accessor = NameMangler.inlineAccessMethodForFieldGet(resolvedMember.getName(), resolvedMember.getDeclaringType(), this.aspectType);
      ResolvedMember inlineAccessor = (ResolvedMember)this.inlineAccessors.get(accessor);
      if (inlineAccessor == null) {
         inlineAccessor = AjcMemberMaker.inlineAccessMethodForFieldGet(this.aspectType, resolvedMember);
         InstructionFactory factory = this.aspectGen.getFactory();
         LazyMethodGen method = this.makeMethodGen(this.aspectGen, inlineAccessor);
         method.makeSynthetic();
         List methodAttributes = new ArrayList();
         methodAttributes.add(new AjAttribute.AjSynthetic());
         methodAttributes.add(new AjAttribute.EffectiveSignatureAttribute(resolvedMember, Shadow.FieldGet, false));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(0), this.aspectGen.getConstantPool()));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(1), this.aspectGen.getConstantPool()));
         this.inlineAccessorMethodGens.add(method);
         InstructionList il = method.getBody();
         if (!Modifier.isStatic(resolvedMember.getModifiers())) {
            il.append((Instruction)InstructionConstants.ALOAD_0);
         }

         il.append(Utility.createGet(factory, resolvedMember));
         il.append(InstructionFactory.createReturn(BcelWorld.makeBcelType(inlineAccessor.getReturnType())));
         this.inlineAccessors.put(accessor, new BcelMethod(this.aspectGen.getBcelObjectType(), method.getMethod(), methodAttributes));
      }

      return inlineAccessor;
   }

   private ResolvedMember createOrGetInlineAccessorForFieldSet(ResolvedMember resolvedMember) {
      String accessor = NameMangler.inlineAccessMethodForFieldSet(resolvedMember.getName(), resolvedMember.getDeclaringType(), this.aspectType);
      ResolvedMember inlineAccessor = (ResolvedMember)this.inlineAccessors.get(accessor);
      if (inlineAccessor == null) {
         inlineAccessor = AjcMemberMaker.inlineAccessMethodForFieldSet(this.aspectType, resolvedMember);
         InstructionFactory factory = this.aspectGen.getFactory();
         LazyMethodGen method = this.makeMethodGen(this.aspectGen, inlineAccessor);
         method.makeSynthetic();
         List methodAttributes = new ArrayList();
         methodAttributes.add(new AjAttribute.AjSynthetic());
         methodAttributes.add(new AjAttribute.EffectiveSignatureAttribute(resolvedMember, Shadow.FieldSet, false));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(0), this.aspectGen.getConstantPool()));
         method.addAttribute(Utility.bcelAttribute((AjAttribute)methodAttributes.get(1), this.aspectGen.getConstantPool()));
         this.inlineAccessorMethodGens.add(method);
         InstructionList il = method.getBody();
         if (Modifier.isStatic(resolvedMember.getModifiers())) {
            il.append((Instruction)InstructionFactory.createLoad(BcelWorld.makeBcelType(resolvedMember.getReturnType()), 0));
         } else {
            il.append((Instruction)InstructionConstants.ALOAD_0);
            il.append((Instruction)InstructionFactory.createLoad(BcelWorld.makeBcelType(resolvedMember.getReturnType()), 1));
         }

         il.append(Utility.createSet(factory, resolvedMember));
         il.append(InstructionConstants.RETURN);
         this.inlineAccessors.put(accessor, new BcelMethod(this.aspectGen.getBcelObjectType(), method.getMethod(), methodAttributes));
      }

      return inlineAccessor;
   }
}
