package com.bea.core.repackaged.aspectj.weaver.reflect;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMemberImpl;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import com.bea.core.repackaged.aspectj.weaver.tools.MatchingContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class StandardShadow extends Shadow {
   private final World world;
   private final ResolvedType enclosingType;
   private final ResolvedMember enclosingMember;
   private final MatchingContext matchContext;
   private Var thisVar = null;
   private Var targetVar = null;
   private Var[] argsVars = null;
   private Var atThisVar = null;
   private Var atTargetVar = null;
   private Map atArgsVars = new HashMap();
   private Map withinAnnotationVar = new HashMap();
   private Map withinCodeAnnotationVar = new HashMap();
   private Map annotationVar = new HashMap();
   private AnnotationFinder annotationFinder;

   public static Shadow makeExecutionShadow(World inWorld, Member forMethod, MatchingContext withContext) {
      Shadow.Kind kind = forMethod instanceof Method ? Shadow.MethodExecution : Shadow.ConstructorExecution;
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(forMethod, inWorld);
      ResolvedType enclosingType = signature.getDeclaringType().resolve(inWorld);
      return new StandardShadow(inWorld, kind, signature, (Shadow)null, enclosingType, (ResolvedMember)null, withContext);
   }

   public static Shadow makeExecutionShadow(World inWorld, ResolvedMember forMethod, MatchingContext withContext) {
      Shadow.Kind kind = forMethod.getName().equals("<init>") ? Shadow.ConstructorExecution : Shadow.MethodExecution;
      return new StandardShadow(inWorld, kind, forMethod, (Shadow)null, (ResolvedType)forMethod.getDeclaringType(), (ResolvedMember)null, withContext);
   }

   public static Shadow makeAdviceExecutionShadow(World inWorld, Method forMethod, MatchingContext withContext) {
      Shadow.Kind kind = Shadow.AdviceExecution;
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedAdviceMember(forMethod, inWorld);
      ResolvedType enclosingType = signature.getDeclaringType().resolve(inWorld);
      return new StandardShadow(inWorld, kind, signature, (Shadow)null, enclosingType, (ResolvedMember)null, withContext);
   }

   public static Shadow makeCallShadow(World inWorld, ResolvedMember aMember, ResolvedMember withinCode, MatchingContext withContext) {
      Shadow enclosingShadow = makeExecutionShadow(inWorld, withinCode, withContext);
      Shadow.Kind kind = !aMember.getName().equals("<init>") ? Shadow.MethodCall : Shadow.ConstructorCall;
      return new StandardShadow(inWorld, kind, aMember, enclosingShadow, (ResolvedType)withinCode.getDeclaringType(), withinCode, withContext);
   }

   public static Shadow makeCallShadow(World inWorld, Member aMember, Class thisClass, MatchingContext withContext) {
      Shadow enclosingShadow = makeStaticInitializationShadow(inWorld, thisClass, withContext);
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(aMember, inWorld);
      ResolvedMember enclosingMember = ReflectionBasedReferenceTypeDelegateFactory.createStaticInitMember(thisClass, inWorld);
      ResolvedType enclosingType = enclosingMember.getDeclaringType().resolve(inWorld);
      Shadow.Kind kind = aMember instanceof Method ? Shadow.MethodCall : Shadow.ConstructorCall;
      return new StandardShadow(inWorld, kind, signature, enclosingShadow, enclosingType, enclosingMember, withContext);
   }

   public static Shadow makeStaticInitializationShadow(World inWorld, Class forType, MatchingContext withContext) {
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createStaticInitMember(forType, inWorld);
      ResolvedType enclosingType = signature.getDeclaringType().resolve(inWorld);
      Shadow.Kind kind = Shadow.StaticInitialization;
      return new StandardShadow(inWorld, kind, signature, (Shadow)null, enclosingType, (ResolvedMember)null, withContext);
   }

   public static Shadow makeStaticInitializationShadow(World inWorld, ResolvedType forType, MatchingContext withContext) {
      ResolvedMember[] members = forType.getDeclaredMethods();
      int clinit = -1;

      for(int i = 0; i < members.length && clinit == -1; ++i) {
         if (members[i].getName().equals("<clinit>")) {
            clinit = i;
         }
      }

      Shadow.Kind kind = Shadow.StaticInitialization;
      if (clinit == -1) {
         com.bea.core.repackaged.aspectj.weaver.Member clinitMember = new ResolvedMemberImpl(com.bea.core.repackaged.aspectj.weaver.Member.STATIC_INITIALIZATION, forType, 8, UnresolvedType.VOID, "<clinit>", new UnresolvedType[0], new UnresolvedType[0]);
         return new StandardShadow(inWorld, kind, clinitMember, (Shadow)null, forType, (ResolvedMember)null, withContext);
      } else {
         return new StandardShadow(inWorld, kind, members[clinit], (Shadow)null, forType, (ResolvedMember)null, withContext);
      }
   }

   public static Shadow makePreInitializationShadow(World inWorld, Constructor forConstructor, MatchingContext withContext) {
      Shadow.Kind kind = Shadow.PreInitialization;
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(forConstructor, inWorld);
      ResolvedType enclosingType = signature.getDeclaringType().resolve(inWorld);
      return new StandardShadow(inWorld, kind, signature, (Shadow)null, enclosingType, (ResolvedMember)null, withContext);
   }

   public static Shadow makeInitializationShadow(World inWorld, Constructor forConstructor, MatchingContext withContext) {
      Shadow.Kind kind = Shadow.Initialization;
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(forConstructor, inWorld);
      ResolvedType enclosingType = signature.getDeclaringType().resolve(inWorld);
      return new StandardShadow(inWorld, kind, signature, (Shadow)null, enclosingType, (ResolvedMember)null, withContext);
   }

   public static Shadow makeHandlerShadow(World inWorld, Class exceptionType, Class withinType, MatchingContext withContext) {
      Shadow.Kind kind = Shadow.ExceptionHandler;
      Shadow enclosingShadow = makeStaticInitializationShadow(inWorld, withinType, withContext);
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createHandlerMember(exceptionType, withinType, inWorld);
      ResolvedMember enclosingMember = ReflectionBasedReferenceTypeDelegateFactory.createStaticInitMember(withinType, inWorld);
      ResolvedType enclosingType = enclosingMember.getDeclaringType().resolve(inWorld);
      return new StandardShadow(inWorld, kind, signature, enclosingShadow, enclosingType, enclosingMember, withContext);
   }

   public static Shadow makeHandlerShadow(World inWorld, Class exceptionType, Member withinCode, MatchingContext withContext) {
      Shadow.Kind kind = Shadow.ExceptionHandler;
      Shadow enclosingShadow = makeExecutionShadow(inWorld, withinCode, withContext);
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createHandlerMember(exceptionType, withinCode.getDeclaringClass(), inWorld);
      ResolvedMember enclosingMember = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(withinCode, inWorld);
      ResolvedType enclosingType = enclosingMember.getDeclaringType().resolve(inWorld);
      return new StandardShadow(inWorld, kind, signature, enclosingShadow, enclosingType, enclosingMember, withContext);
   }

   public static Shadow makeFieldGetShadow(World inWorld, Field forField, Class callerType, MatchingContext withContext) {
      Shadow enclosingShadow = makeStaticInitializationShadow(inWorld, callerType, withContext);
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedField(forField, inWorld);
      ResolvedMember enclosingMember = ReflectionBasedReferenceTypeDelegateFactory.createStaticInitMember(callerType, inWorld);
      ResolvedType enclosingType = enclosingMember.getDeclaringType().resolve(inWorld);
      Shadow.Kind kind = Shadow.FieldGet;
      return new StandardShadow(inWorld, kind, signature, enclosingShadow, enclosingType, enclosingMember, withContext);
   }

   public static Shadow makeFieldGetShadow(World inWorld, Field forField, Member inMember, MatchingContext withContext) {
      Shadow enclosingShadow = makeExecutionShadow(inWorld, inMember, withContext);
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedField(forField, inWorld);
      ResolvedMember enclosingMember = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(inMember, inWorld);
      ResolvedType enclosingType = enclosingMember.getDeclaringType().resolve(inWorld);
      Shadow.Kind kind = Shadow.FieldGet;
      return new StandardShadow(inWorld, kind, signature, enclosingShadow, enclosingType, enclosingMember, withContext);
   }

   public static Shadow makeFieldSetShadow(World inWorld, Field forField, Class callerType, MatchingContext withContext) {
      Shadow enclosingShadow = makeStaticInitializationShadow(inWorld, callerType, withContext);
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedField(forField, inWorld);
      ResolvedMember enclosingMember = ReflectionBasedReferenceTypeDelegateFactory.createStaticInitMember(callerType, inWorld);
      ResolvedType enclosingType = enclosingMember.getDeclaringType().resolve(inWorld);
      Shadow.Kind kind = Shadow.FieldSet;
      return new StandardShadow(inWorld, kind, signature, enclosingShadow, enclosingType, enclosingMember, withContext);
   }

   public static Shadow makeFieldSetShadow(World inWorld, Field forField, Member inMember, MatchingContext withContext) {
      Shadow enclosingShadow = makeExecutionShadow(inWorld, inMember, withContext);
      com.bea.core.repackaged.aspectj.weaver.Member signature = ReflectionBasedReferenceTypeDelegateFactory.createResolvedField(forField, inWorld);
      ResolvedMember enclosingMember = ReflectionBasedReferenceTypeDelegateFactory.createResolvedMember(inMember, inWorld);
      ResolvedType enclosingType = enclosingMember.getDeclaringType().resolve(inWorld);
      Shadow.Kind kind = Shadow.FieldSet;
      return new StandardShadow(inWorld, kind, signature, enclosingShadow, enclosingType, enclosingMember, withContext);
   }

   public StandardShadow(World world, Shadow.Kind kind, com.bea.core.repackaged.aspectj.weaver.Member signature, Shadow enclosingShadow, ResolvedType enclosingType, ResolvedMember enclosingMember, MatchingContext withContext) {
      super(kind, signature, enclosingShadow);
      this.world = world;
      this.enclosingType = enclosingType;
      this.enclosingMember = enclosingMember;
      this.matchContext = withContext;
      if (world instanceof IReflectionWorld) {
         this.annotationFinder = ((IReflectionWorld)world).getAnnotationFinder();
      }

   }

   public World getIWorld() {
      return this.world;
   }

   public Var getThisVar() {
      if (this.thisVar == null && this.hasThis()) {
         this.thisVar = ReflectionVar.createThisVar(this.getThisType().resolve(this.world), this.annotationFinder);
      }

      return this.thisVar;
   }

   public Var getTargetVar() {
      if (this.targetVar == null && this.hasTarget()) {
         this.targetVar = ReflectionVar.createTargetVar(this.getThisType().resolve(this.world), this.annotationFinder);
      }

      return this.targetVar;
   }

   public UnresolvedType getEnclosingType() {
      return this.enclosingType;
   }

   public Var getArgVar(int i) {
      if (this.argsVars == null) {
         this.argsVars = new Var[this.getArgCount()];

         for(int j = 0; j < this.argsVars.length; ++j) {
            this.argsVars[j] = ReflectionVar.createArgsVar(this.getArgType(j).resolve(this.world), j, this.annotationFinder);
         }
      }

      return i < this.argsVars.length ? this.argsVars[i] : null;
   }

   public Var getThisJoinPointVar() {
      return null;
   }

   public Var getThisJoinPointStaticPartVar() {
      return null;
   }

   public Var getThisEnclosingJoinPointStaticPartVar() {
      return null;
   }

   public Var getThisAspectInstanceVar(ResolvedType aspectType) {
      return null;
   }

   public Var getKindedAnnotationVar(UnresolvedType forAnnotationType) {
      ResolvedType annType = forAnnotationType.resolve(this.world);
      if (this.annotationVar.get(annType) == null) {
         Var v = ReflectionVar.createAtAnnotationVar(annType, this.annotationFinder);
         this.annotationVar.put(annType, v);
      }

      return (Var)this.annotationVar.get(annType);
   }

   public Var getWithinAnnotationVar(UnresolvedType forAnnotationType) {
      ResolvedType annType = forAnnotationType.resolve(this.world);
      if (this.withinAnnotationVar.get(annType) == null) {
         Var v = ReflectionVar.createWithinAnnotationVar(annType, this.annotationFinder);
         this.withinAnnotationVar.put(annType, v);
      }

      return (Var)this.withinAnnotationVar.get(annType);
   }

   public Var getWithinCodeAnnotationVar(UnresolvedType forAnnotationType) {
      ResolvedType annType = forAnnotationType.resolve(this.world);
      if (this.withinCodeAnnotationVar.get(annType) == null) {
         Var v = ReflectionVar.createWithinCodeAnnotationVar(annType, this.annotationFinder);
         this.withinCodeAnnotationVar.put(annType, v);
      }

      return (Var)this.withinCodeAnnotationVar.get(annType);
   }

   public Var getThisAnnotationVar(UnresolvedType forAnnotationType) {
      if (this.atThisVar == null) {
         this.atThisVar = ReflectionVar.createThisAnnotationVar(forAnnotationType.resolve(this.world), this.annotationFinder);
      }

      return this.atThisVar;
   }

   public Var getTargetAnnotationVar(UnresolvedType forAnnotationType) {
      if (this.atTargetVar == null) {
         this.atTargetVar = ReflectionVar.createTargetAnnotationVar(forAnnotationType.resolve(this.world), this.annotationFinder);
      }

      return this.atTargetVar;
   }

   public Var getArgAnnotationVar(int i, UnresolvedType forAnnotationType) {
      ResolvedType annType = forAnnotationType.resolve(this.world);
      Var[] vars;
      if (this.atArgsVars.get(annType) == null) {
         vars = new Var[this.getArgCount()];
         this.atArgsVars.put(annType, vars);
      }

      vars = (Var[])((Var[])this.atArgsVars.get(annType));
      if (i > vars.length - 1) {
         return null;
      } else {
         if (vars[i] == null) {
            vars[i] = ReflectionVar.createArgsAnnotationVar(annType, i, this.annotationFinder);
         }

         return vars[i];
      }
   }

   public com.bea.core.repackaged.aspectj.weaver.Member getEnclosingCodeSignature() {
      if (this.getKind().isEnclosingKind()) {
         return this.getSignature();
      } else if (this.getKind() == Shadow.PreInitialization) {
         return this.getSignature();
      } else {
         return (com.bea.core.repackaged.aspectj.weaver.Member)(this.enclosingShadow == null ? this.enclosingMember : this.enclosingShadow.getSignature());
      }
   }

   public ISourceLocation getSourceLocation() {
      return null;
   }

   public MatchingContext getMatchingContext() {
      return this.matchContext;
   }
}
