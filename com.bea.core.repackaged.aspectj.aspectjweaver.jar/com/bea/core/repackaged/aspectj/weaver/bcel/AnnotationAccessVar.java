package com.bea.core.repackaged.aspectj.weaver.bcel;

import com.bea.core.repackaged.aspectj.apache.bcel.classfile.Field;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Instruction;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionBranch;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionConstants;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionFactory;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionHandle;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.InstructionList;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.ObjectType;
import com.bea.core.repackaged.aspectj.apache.bcel.generic.Type;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;

public class AnnotationAccessVar extends BcelVar {
   private BcelShadow shadow;
   private Shadow.Kind kind;
   private UnresolvedType containingType;
   private Member member;
   private boolean isWithin;

   public AnnotationAccessVar(BcelShadow shadow, Shadow.Kind kind, ResolvedType annotationType, UnresolvedType theTargetIsStoredHere, Member sig, boolean isWithin) {
      super(annotationType, 0);
      this.shadow = shadow;
      this.kind = kind;
      this.containingType = theTargetIsStoredHere;
      this.member = sig;
      this.isWithin = isWithin;
   }

   public Shadow.Kind getKind() {
      return this.kind;
   }

   public String toString() {
      return "AnnotationAccessVar(" + this.getType() + ")";
   }

   public Instruction createLoad(InstructionFactory fact) {
      throw new IllegalStateException("unimplemented");
   }

   public Instruction createStore(InstructionFactory fact) {
      throw new IllegalStateException("unimplemented");
   }

   public InstructionList createCopyFrom(InstructionFactory fact, int oldSlot) {
      throw new IllegalStateException("unimplemented");
   }

   public void appendLoad(InstructionList il, InstructionFactory fact) {
      il.append(this.createLoadInstructions(this.getType(), fact));
   }

   public void appendLoadAndConvert(InstructionList il, InstructionFactory fact, ResolvedType toType) {
      il.append(this.createLoadInstructions(toType, fact));
   }

   public void insertLoad(InstructionList il, InstructionFactory fact) {
      il.insert(this.createLoadInstructions(this.getType(), fact));
   }

   private InstructionList createLoadInstructions(ResolvedType toType, InstructionFactory fact) {
      InstructionList il = new InstructionList();
      Type jlClass = BcelWorld.makeBcelType(UnresolvedType.JL_CLASS);
      Type jlString = BcelWorld.makeBcelType(UnresolvedType.JL_STRING);
      Type jlClassArray = BcelWorld.makeBcelType(UnresolvedType.JAVA_LANG_CLASS_ARRAY);
      Type jlaAnnotation = BcelWorld.makeBcelType(UnresolvedType.JAVA_LANG_ANNOTATION);
      Instruction pushConstant = fact.createConstant(new ObjectType(toType.getName()));
      if (this.kind == Shadow.MethodCall || this.kind == Shadow.MethodExecution || this.kind == Shadow.PreInitialization || this.kind == Shadow.Initialization || this.kind == Shadow.ConstructorCall || this.kind == Shadow.ConstructorExecution || this.kind == Shadow.AdviceExecution || (this.kind == Shadow.FieldGet || this.kind == Shadow.FieldSet) && this.member.getKind() == Member.METHOD) {
         Type jlrMethod = BcelWorld.makeBcelType(UnresolvedType.forSignature("Ljava/lang/reflect/Method;"));
         Type jlAnnotation = BcelWorld.makeBcelType(UnresolvedType.forSignature("Ljava/lang/annotation/Annotation;"));
         Type[] paramTypes = BcelWorld.makeBcelTypes(this.member.getParameterTypes());
         if (this.kind != Shadow.MethodCall && this.kind != Shadow.MethodExecution && this.kind != Shadow.AdviceExecution && (this.kind != Shadow.FieldGet && this.kind != Shadow.FieldSet || this.member.getKind() != Member.METHOD) && (this.kind != Shadow.ConstructorCall && this.kind != Shadow.ConstructorExecution || this.member.getKind() != Member.METHOD)) {
            il.append(fact.createConstant(BcelWorld.makeBcelType(this.containingType)));
            this.buildArray(il, fact, jlClass, paramTypes, 1);
            Type jlrCtor = BcelWorld.makeBcelType(UnresolvedType.JAVA_LANG_REFLECT_CONSTRUCTOR);
            il.append((Instruction)fact.createInvoke("java/lang/Class", "getDeclaredConstructor", jlrCtor, new Type[]{jlClassArray}, (short)182));
            il.append(pushConstant);
            il.append((Instruction)fact.createInvoke("java/lang/reflect/Constructor", "getAnnotation", jlaAnnotation, new Type[]{jlClass}, (short)182));
         } else {
            Field annotationCachingField = this.shadow.getEnclosingClass().getAnnotationCachingField(this.shadow, toType, this.isWithin);
            il.append((Instruction)fact.createGetStatic(this.shadow.getEnclosingClass().getName(), annotationCachingField.getName(), jlAnnotation));
            il.append(InstructionConstants.DUP);
            InstructionBranch ifNonNull = InstructionFactory.createBranchInstruction((short)199, (InstructionHandle)null);
            il.append(ifNonNull);
            il.append(InstructionConstants.POP);
            il.append(fact.createConstant(BcelWorld.makeBcelType(this.containingType)));
            il.append(fact.createConstant(this.member.getName()));
            this.buildArray(il, fact, jlClass, paramTypes, 1);
            il.append((Instruction)fact.createInvoke("java/lang/Class", "getDeclaredMethod", jlrMethod, new Type[]{jlString, jlClassArray}, (short)182));
            il.append(pushConstant);
            il.append((Instruction)fact.createInvoke("java/lang/reflect/Method", "getAnnotation", jlaAnnotation, new Type[]{jlClass}, (short)182));
            il.append(InstructionConstants.DUP);
            il.append((Instruction)fact.createPutStatic(this.shadow.getEnclosingClass().getName(), annotationCachingField.getName(), jlAnnotation));
            InstructionHandle ifNullElse = il.append(InstructionConstants.NOP);
            ifNonNull.setTarget(ifNullElse);
         }
      } else if (this.kind != Shadow.FieldSet && this.kind != Shadow.FieldGet) {
         if (this.kind != Shadow.StaticInitialization && this.kind != Shadow.ExceptionHandler) {
            throw new RuntimeException("Don't understand this kind " + this.kind);
         }

         il.append(fact.createConstant(BcelWorld.makeBcelType(this.containingType)));
         il.append(pushConstant);
         il.append((Instruction)fact.createInvoke("java/lang/Class", "getAnnotation", jlaAnnotation, new Type[]{jlClass}, (short)182));
      } else {
         this.generateBytecodeToAccessAnnotationAtFieldGetSetShadow(toType, fact, il, pushConstant);
      }

      il.append(Utility.createConversion(fact, jlaAnnotation, BcelWorld.makeBcelType((UnresolvedType)toType)));
      return il;
   }

   private void generateBytecodeToAccessAnnotationAtFieldGetSetShadow(ResolvedType toType, InstructionFactory fact, InstructionList il, Instruction pushConstantAnnotationType) {
      Type jlClass = BcelWorld.makeBcelType(UnresolvedType.JL_CLASS);
      Type jlString = BcelWorld.makeBcelType(UnresolvedType.JL_STRING);
      Type jlaAnnotation = BcelWorld.makeBcelType(UnresolvedType.JAVA_LANG_ANNOTATION);
      Type jlrField = BcelWorld.makeBcelType(UnresolvedType.JAVA_LANG_REFLECT_FIELD);
      LazyClassGen shadowEnclosingClass = this.shadow.getEnclosingClass();
      Field annotationCachingField = shadowEnclosingClass.getAnnotationCachingField(this.shadow, toType, this.isWithin);
      String annotationCachingFieldName = annotationCachingField.getName();
      il.append((Instruction)fact.createGetStatic(shadowEnclosingClass.getName(), annotationCachingFieldName, jlaAnnotation));
      il.appendDUP();
      InstructionBranch ifNonNull = new InstructionBranch((short)199, (InstructionHandle)null);
      il.append(ifNonNull);
      il.appendPOP();
      il.append(fact.createConstant(BcelWorld.makeBcelType(this.containingType)));
      il.append(fact.createConstant(this.member.getName()));
      il.append((Instruction)fact.createInvoke("java/lang/Class", "getDeclaredField", jlrField, new Type[]{jlString}, (short)182));
      il.append(pushConstantAnnotationType);
      il.append((Instruction)fact.createInvoke("java/lang/reflect/Field", "getAnnotation", jlaAnnotation, new Type[]{jlClass}, (short)182));
      il.appendDUP();
      il.append((Instruction)fact.createPutStatic(shadowEnclosingClass.getName(), annotationCachingFieldName, jlaAnnotation));
      InstructionHandle ifNullElse = il.appendNOP();
      ifNonNull.setTarget(ifNullElse);
   }

   private void buildArray(InstructionList il, InstructionFactory fact, Type arrayElementType, Type[] arrayEntries, int dim) {
      il.append(fact.createConstant(arrayEntries == null ? 0 : arrayEntries.length));
      il.append(fact.createNewArray(arrayElementType, (short)dim));
      if (arrayEntries != null) {
         for(int i = 0; i < arrayEntries.length; ++i) {
            il.append(InstructionFactory.createDup(1));
            il.append(fact.createConstant(i));
            switch (arrayEntries[i].getType()) {
               case 4:
                  il.append((Instruction)fact.createGetStatic("java/lang/Boolean", "TYPE", arrayElementType));
                  break;
               case 5:
                  il.append((Instruction)fact.createGetStatic("java/lang/Character", "TYPE", arrayElementType));
                  break;
               case 6:
                  il.append((Instruction)fact.createGetStatic("java/lang/Float", "TYPE", arrayElementType));
                  break;
               case 7:
                  il.append((Instruction)fact.createGetStatic("java/lang/Double", "TYPE", arrayElementType));
                  break;
               case 8:
                  il.append((Instruction)fact.createGetStatic("java/lang/Byte", "TYPE", arrayElementType));
                  break;
               case 9:
                  il.append((Instruction)fact.createGetStatic("java/lang/Short", "TYPE", arrayElementType));
                  break;
               case 10:
                  il.append((Instruction)fact.createGetStatic("java/lang/Integer", "TYPE", arrayElementType));
                  break;
               case 11:
                  il.append((Instruction)fact.createGetStatic("java/lang/Long", "TYPE", arrayElementType));
                  break;
               case 12:
               default:
                  il.append(fact.createConstant(arrayEntries[i]));
                  break;
               case 13:
                  il.append(fact.createConstant(new ObjectType(arrayEntries[i].getSignature())));
            }

            il.append(InstructionConstants.AASTORE);
         }

      }
   }

   public Member getMember() {
      return this.member;
   }

   public Var getAccessorForValue(ResolvedType valueType, String formalName) {
      return new AnnotationAccessFieldVar(this, valueType, formalName);
   }
}
