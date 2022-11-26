package com.bea.core.repackaged.aspectj.weaver;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.patterns.AndPointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.PatternNode;
import com.bea.core.repackaged.aspectj.weaver.patterns.PerClause;
import com.bea.core.repackaged.aspectj.weaver.patterns.Pointcut;
import com.bea.core.repackaged.aspectj.weaver.patterns.TypePattern;
import java.util.Collections;
import java.util.List;

public abstract class Advice extends ShadowMunger {
   protected AjAttribute.AdviceAttribute attribute;
   protected transient AdviceKind kind;
   protected Member signature;
   private boolean isAnnotationStyle;
   protected ResolvedType concreteAspect;
   protected List innerCflowEntries = Collections.emptyList();
   protected int nFreeVars;
   protected TypePattern exceptionType;
   protected UnresolvedType[] bindingParameterTypes;
   protected boolean hasMatchedAtLeastOnce = false;
   protected List suppressedLintKinds = null;
   public ISourceLocation lastReportedMonitorExitJoinpointLocation = null;
   private volatile int hashCode = 0;
   public static final int ExtraArgument = 1;
   public static final int ThisJoinPoint = 2;
   public static final int ThisJoinPointStaticPart = 4;
   public static final int ThisEnclosingJoinPointStaticPart = 8;
   public static final int ParameterMask = 15;
   public static final int ConstantReference = 16;
   public static final int ConstantValue = 32;
   public static final int ThisAspectInstance = 64;

   public static Advice makeCflowEntry(World world, Pointcut entry, boolean isBelow, Member stackField, int nFreeVars, List innerCflowEntries, ResolvedType inAspect) {
      Advice ret = world.createAdviceMunger(isBelow ? AdviceKind.CflowBelowEntry : AdviceKind.CflowEntry, entry, stackField, 0, entry, inAspect);
      ret.innerCflowEntries = innerCflowEntries;
      ret.nFreeVars = nFreeVars;
      ret.setDeclaringType(inAspect);
      return ret;
   }

   public static Advice makePerCflowEntry(World world, Pointcut entry, boolean isBelow, Member stackField, ResolvedType inAspect, List innerCflowEntries) {
      Advice ret = world.createAdviceMunger(isBelow ? AdviceKind.PerCflowBelowEntry : AdviceKind.PerCflowEntry, entry, stackField, 0, entry, inAspect);
      ret.innerCflowEntries = innerCflowEntries;
      ret.concreteAspect = inAspect;
      return ret;
   }

   public static Advice makePerObjectEntry(World world, Pointcut entry, boolean isThis, ResolvedType inAspect) {
      Advice ret = world.createAdviceMunger(isThis ? AdviceKind.PerThisEntry : AdviceKind.PerTargetEntry, entry, (Member)null, 0, entry, inAspect);
      ret.concreteAspect = inAspect;
      return ret;
   }

   public static Advice makePerTypeWithinEntry(World world, Pointcut p, ResolvedType inAspect) {
      Advice ret = world.createAdviceMunger(AdviceKind.PerTypeWithinEntry, p, (Member)null, 0, p, inAspect);
      ret.concreteAspect = inAspect;
      return ret;
   }

   public static Advice makeSoftener(World world, Pointcut entry, TypePattern exceptionType, ResolvedType inAspect, IHasSourceLocation loc) {
      Advice ret = world.createAdviceMunger(AdviceKind.Softener, entry, (Member)null, 0, loc, inAspect);
      ret.exceptionType = exceptionType;
      return ret;
   }

   public Advice(AjAttribute.AdviceAttribute attribute, Pointcut pointcut, Member signature) {
      super(pointcut, attribute.getStart(), attribute.getEnd(), attribute.getSourceContext(), 1);
      this.attribute = attribute;
      this.isAnnotationStyle = signature != null && !signature.getName().startsWith("ajc$");
      this.kind = attribute.getKind();
      this.signature = signature;
      if (signature != null) {
         this.bindingParameterTypes = signature.getParameterTypes();
      } else {
         this.bindingParameterTypes = new UnresolvedType[0];
      }

   }

   public boolean match(Shadow shadow, World world) {
      if (!super.match(shadow, world)) {
         return false;
      } else if (shadow.getKind() != Shadow.ExceptionHandler || !this.kind.isAfter() && this.kind != AdviceKind.Around) {
         if ((shadow.getKind() == Shadow.SynchronizationLock || shadow.getKind() == Shadow.SynchronizationUnlock) && this.kind == AdviceKind.Around) {
            world.showMessage(IMessage.WARNING, WeaverMessages.format("noAroundOnSynchronization"), this.getSourceLocation(), shadow.getSourceLocation());
            return false;
         } else {
            ResolvedType shadowReturnType;
            ResolvedType adviceReturnType;
            boolean matches;
            if (this.hasExtraParameter() && this.kind == AdviceKind.AfterReturning) {
               shadowReturnType = this.getExtraParameterType().resolve(world);
               adviceReturnType = shadow.getReturnType().resolve(world);
               matches = shadowReturnType.isConvertableFrom(adviceReturnType) && shadow.getKind().hasReturnValue();
               if (matches && shadowReturnType.isParameterizedType()) {
                  this.maybeIssueUncheckedMatchWarning(shadowReturnType, adviceReturnType, shadow, world);
               }

               return matches;
            } else if (this.hasExtraParameter() && this.kind == AdviceKind.AfterThrowing) {
               shadowReturnType = this.getExtraParameterType().resolve(world);
               if (shadowReturnType.isCheckedException() && !shadowReturnType.getName().equals("java.lang.Exception")) {
                  UnresolvedType[] shadowThrows = shadow.getSignature().getExceptions(world);
                  matches = false;

                  for(int i = 0; i < shadowThrows.length && !matches; ++i) {
                     ResolvedType type = shadowThrows[i].resolve(world);
                     if (shadowReturnType.isAssignableFrom(type)) {
                        matches = true;
                     }
                  }

                  return matches;
               } else {
                  return true;
               }
            } else if (this.kind == AdviceKind.PerTargetEntry) {
               return shadow.hasTarget();
            } else if (this.kind == AdviceKind.PerThisEntry) {
               return shadow.getEnclosingCodeSignature().getName().equals("<init>") && world.resolve(shadow.getEnclosingType()).isGroovyObject() ? false : shadow.hasThis();
            } else {
               if (this.kind == AdviceKind.Around) {
                  if (shadow.getKind() == Shadow.PreInitialization) {
                     world.showMessage(IMessage.WARNING, WeaverMessages.format("aroundOnPreInit"), this.getSourceLocation(), shadow.getSourceLocation());
                     return false;
                  }

                  if (shadow.getKind() == Shadow.Initialization) {
                     world.showMessage(IMessage.WARNING, WeaverMessages.format("aroundOnInit"), this.getSourceLocation(), shadow.getSourceLocation());
                     return false;
                  }

                  if (shadow.getKind() == Shadow.StaticInitialization && shadow.getEnclosingType().resolve(world).isInterface()) {
                     world.showMessage(IMessage.ERROR, WeaverMessages.format("aroundOnInterfaceStaticInit", shadow.getEnclosingType().getName()), this.getSourceLocation(), shadow.getSourceLocation());
                     return false;
                  }

                  if (this.getSignature().getReturnType().equals(UnresolvedType.VOID)) {
                     if (!shadow.getReturnType().equals(UnresolvedType.VOID)) {
                        String s = shadow.toString();
                        String s2 = WeaverMessages.format("nonVoidReturn", s);
                        world.showMessage(IMessage.ERROR, s2, this.getSourceLocation(), shadow.getSourceLocation());
                        return false;
                     }
                  } else {
                     if (this.getSignature().getReturnType().equals(UnresolvedType.OBJECT)) {
                        return true;
                     }

                     shadowReturnType = shadow.getReturnType().resolve(world);
                     adviceReturnType = this.getSignature().getGenericReturnType().resolve(world);
                     if (shadowReturnType.isParameterizedType() && adviceReturnType.isRawType()) {
                        ResolvedType shadowReturnGenericType = shadowReturnType.getGenericType();
                        ResolvedType adviceReturnGenericType = adviceReturnType.getGenericType();
                        if (shadowReturnGenericType.isAssignableFrom(adviceReturnGenericType) && world.getLint().uncheckedAdviceConversion.isEnabled()) {
                           world.getLint().uncheckedAdviceConversion.signal(new String[]{shadow.toString(), shadowReturnType.getName(), adviceReturnType.getName()}, shadow.getSourceLocation(), new ISourceLocation[]{this.getSourceLocation()});
                        }
                     } else if (!shadowReturnType.isAssignableFrom(adviceReturnType)) {
                        world.showMessage(IMessage.ERROR, WeaverMessages.format("incompatibleReturnType", shadow), this.getSourceLocation(), shadow.getSourceLocation());
                        return false;
                     }
                  }
               }

               return true;
            }
         }
      } else {
         world.showMessage(IMessage.WARNING, WeaverMessages.format("onlyBeforeOnHandler"), this.getSourceLocation(), shadow.getSourceLocation());
         return false;
      }
   }

   private void maybeIssueUncheckedMatchWarning(ResolvedType afterReturningType, ResolvedType shadowReturnType, Shadow shadow, World world) {
      boolean inDoubt = !afterReturningType.isAssignableFrom(shadowReturnType);
      if (inDoubt && world.getLint().uncheckedArgument.isEnabled()) {
         String uncheckedMatchWith = afterReturningType.getSimpleBaseName();
         if (shadowReturnType.isParameterizedType() && shadowReturnType.getRawType() == afterReturningType.getRawType()) {
            uncheckedMatchWith = shadowReturnType.getSimpleName();
         }

         if (!Utils.isSuppressing(this.getSignature().getAnnotations(), "uncheckedArgument")) {
            world.getLint().uncheckedArgument.signal(new String[]{afterReturningType.getSimpleName(), uncheckedMatchWith, afterReturningType.getSimpleBaseName(), shadow.toResolvedString(world)}, this.getSourceLocation(), new ISourceLocation[]{shadow.getSourceLocation()});
         }
      }

   }

   public AdviceKind getKind() {
      return this.kind;
   }

   public Member getSignature() {
      return this.signature;
   }

   public boolean hasExtraParameter() {
      return (this.getExtraParameterFlags() & 1) != 0;
   }

   protected int getExtraParameterFlags() {
      return this.attribute.getExtraParameterFlags();
   }

   protected int getExtraParameterCount() {
      return countOnes(this.getExtraParameterFlags() & 15);
   }

   public UnresolvedType[] getBindingParameterTypes() {
      return this.bindingParameterTypes;
   }

   public void setBindingParameterTypes(UnresolvedType[] types) {
      this.bindingParameterTypes = types;
   }

   public static int countOnes(int bits) {
      int ret;
      for(ret = 0; bits != 0; bits >>= 1) {
         if ((bits & 1) != 0) {
            ++ret;
         }
      }

      return ret;
   }

   public int getBaseParameterCount() {
      return this.getSignature().getParameterTypes().length - this.getExtraParameterCount();
   }

   public String[] getBaseParameterNames(World world) {
      String[] allNames = this.getSignature().getParameterNames(world);
      int extras = this.getExtraParameterCount();
      if (extras == 0) {
         return allNames;
      } else {
         String[] result = new String[this.getBaseParameterCount()];

         for(int i = 0; i < result.length; ++i) {
            result[i] = allNames[i];
         }

         return result;
      }
   }

   public UnresolvedType getExtraParameterType() {
      if (!this.hasExtraParameter()) {
         return ResolvedType.MISSING;
      } else if (this.signature instanceof ResolvedMember) {
         ResolvedMember method = (ResolvedMember)this.signature;
         UnresolvedType[] parameterTypes = method.getGenericParameterTypes();
         if (!this.getConcreteAspect().isAnnotationStyleAspect()) {
            return parameterTypes[this.getBaseParameterCount()];
         } else {
            String[] pnames = method.getParameterNames();
            if (pnames != null) {
               AnnotationAJ[] annos = this.getSignature().getAnnotations();
               String parameterToLookup = null;
               int i;
               if (annos != null && (this.getKind() == AdviceKind.AfterThrowing || this.getKind() == AdviceKind.AfterReturning)) {
                  for(i = 0; i < annos.length && parameterToLookup == null; ++i) {
                     AnnotationAJ anno = annos[i];
                     String annosig = anno.getType().getSignature();
                     if (annosig.equals("Lorg/aspectj/lang/annotation/AfterThrowing;")) {
                        parameterToLookup = anno.getStringFormOfValue("throwing");
                     } else if (annosig.equals("Lorg/aspectj/lang/annotation/AfterReturning;")) {
                        parameterToLookup = anno.getStringFormOfValue("returning");
                     }
                  }
               }

               if (parameterToLookup != null) {
                  for(i = 0; i < pnames.length; ++i) {
                     if (pnames[i].equals(parameterToLookup)) {
                        return parameterTypes[i];
                     }
                  }
               }
            }

            int baseParmCnt;
            for(baseParmCnt = this.getBaseParameterCount(); baseParmCnt + 1 < parameterTypes.length && (parameterTypes[baseParmCnt].equals(AjcMemberMaker.TYPEX_JOINPOINT) || parameterTypes[baseParmCnt].equals(AjcMemberMaker.TYPEX_STATICJOINPOINT) || parameterTypes[baseParmCnt].equals(AjcMemberMaker.TYPEX_ENCLOSINGSTATICJOINPOINT)); ++baseParmCnt) {
            }

            return parameterTypes[baseParmCnt];
         }
      } else {
         return this.signature.getParameterTypes()[this.getBaseParameterCount()];
      }
   }

   public UnresolvedType getDeclaringAspect() {
      return this.getOriginalSignature().getDeclaringType();
   }

   protected Member getOriginalSignature() {
      return this.signature;
   }

   protected String extraParametersToString() {
      return this.getExtraParameterFlags() == 0 ? "" : "(extraFlags: " + this.getExtraParameterFlags() + ")";
   }

   public Pointcut getPointcut() {
      return this.pointcut;
   }

   public ShadowMunger concretize(ResolvedType fromType, World world, PerClause clause) {
      Pointcut p = this.pointcut.concretize(fromType, this.getDeclaringType(), this.signature.getArity(), this);
      if (clause != null) {
         Pointcut oldP = p;
         p = new AndPointcut(clause, (Pointcut)p);
         ((Pointcut)p).copyLocationFrom((PatternNode)oldP);
         ((Pointcut)p).state = Pointcut.CONCRETE;
         ((Pointcut)p).m_ignoreUnboundBindingForNames = ((Pointcut)oldP).m_ignoreUnboundBindingForNames;
      }

      Advice munger = world.getWeavingSupport().createAdviceMunger(this.attribute, (Pointcut)p, this.signature, fromType);
      munger.bindingParameterTypes = this.bindingParameterTypes;
      munger.setDeclaringType(this.getDeclaringType());
      return munger;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      sb.append("(").append(this.getKind()).append(this.extraParametersToString());
      sb.append(": ").append(this.pointcut).append("->").append(this.signature).append(")");
      return sb.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof Advice)) {
         return false;
      } else {
         boolean var10000;
         label44: {
            Advice o = (Advice)other;
            if (o.kind.equals(this.kind)) {
               label38: {
                  if (o.pointcut == null) {
                     if (this.pointcut != null) {
                        break label38;
                     }
                  } else if (!o.pointcut.equals(this.pointcut)) {
                     break label38;
                  }

                  if (o.signature == null) {
                     if (this.signature == null) {
                        break label44;
                     }
                  } else if (o.signature.equals(this.signature)) {
                     break label44;
                  }
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      if (this.hashCode == 0) {
         int result = 17;
         result = 37 * result + this.kind.hashCode();
         result = 37 * result + (this.pointcut == null ? 0 : this.pointcut.hashCode());
         result = 37 * result + (this.signature == null ? 0 : this.signature.hashCode());
         this.hashCode = result;
      }

      return this.hashCode;
   }

   public void setLexicalPosition(int lexicalPosition) {
      this.start = lexicalPosition;
   }

   public boolean isAnnotationStyle() {
      return this.isAnnotationStyle;
   }

   public ResolvedType getConcreteAspect() {
      return this.concreteAspect;
   }

   public boolean hasMatchedSomething() {
      return this.hasMatchedAtLeastOnce;
   }

   public void setHasMatchedSomething(boolean hasMatchedSomething) {
      this.hasMatchedAtLeastOnce = hasMatchedSomething;
   }

   public abstract boolean hasDynamicTests();
}
