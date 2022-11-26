package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.Checker;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedMember;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class KindedPointcut extends Pointcut {
   Shadow.Kind kind;
   private SignaturePattern signature;
   private int matchKinds;
   private ShadowMunger munger;

   public KindedPointcut(Shadow.Kind kind, SignaturePattern signature) {
      this.munger = null;
      this.kind = kind;
      this.signature = signature;
      this.pointcutKind = 1;
      this.matchKinds = kind.bit;
   }

   public KindedPointcut(Shadow.Kind kind, SignaturePattern signature, ShadowMunger munger) {
      this(kind, signature);
      this.munger = munger;
   }

   public SignaturePattern getSignature() {
      return this.signature;
   }

   public int couldMatchKinds() {
      return this.matchKinds;
   }

   public boolean couldEverMatchSameJoinPointsAs(KindedPointcut other) {
      if (this.kind != other.kind) {
         return false;
      } else {
         String myName = this.signature.getName().maybeGetSimpleName();
         String yourName = other.signature.getName().maybeGetSimpleName();
         if (myName != null && yourName != null && !myName.equals(yourName)) {
            return false;
         } else {
            return this.signature.getParameterTypes().ellipsisCount != 0 || other.signature.getParameterTypes().ellipsisCount != 0 || this.signature.getParameterTypes().getTypePatterns().length == other.signature.getParameterTypes().getTypePatterns().length;
         }
      }
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      if (info.getKind() != null && info.getKind() != this.kind) {
         return FuzzyBoolean.NO;
      } else {
         if (info.world.optimizedMatching && (this.kind == Shadow.MethodExecution || this.kind == Shadow.Initialization) && info.getKind() == null) {
            boolean fastMatchingOnAspect = info.getType().isAspect();
            if (fastMatchingOnAspect) {
               return FuzzyBoolean.MAYBE;
            }

            if (this.getSignature().isExactDeclaringTypePattern()) {
               ExactTypePattern typePattern = (ExactTypePattern)this.getSignature().getDeclaringType();
               ResolvedType patternExactType = typePattern.getResolvedExactType(info.world);
               ResolvedType curr;
               if (patternExactType.isInterface()) {
                  curr = info.getType();
                  Iterator hierarchyWalker = curr.getHierarchy(true, true);
                  boolean found = false;

                  while(hierarchyWalker.hasNext()) {
                     curr = (ResolvedType)hierarchyWalker.next();
                     if (typePattern.matchesStatically(curr)) {
                        found = true;
                        break;
                     }
                  }

                  if (!found) {
                     return FuzzyBoolean.NO;
                  }
               } else if (patternExactType.isClass()) {
                  curr = info.getType();

                  while(!typePattern.matchesStatically(curr)) {
                     curr = curr.getSuperclass();
                     if (curr == null) {
                        break;
                     }
                  }

                  if (curr == null) {
                     return FuzzyBoolean.NO;
                  }
               }
            } else if (this.getSignature().getDeclaringType() instanceof AnyWithAnnotationTypePattern) {
               ResolvedType type = info.getType();
               AnnotationTypePattern annotationTypePattern = ((AnyWithAnnotationTypePattern)this.getSignature().getDeclaringType()).getAnnotationPattern();
               if (annotationTypePattern instanceof ExactAnnotationTypePattern) {
                  ExactAnnotationTypePattern exactAnnotationTypePattern = (ExactAnnotationTypePattern)annotationTypePattern;
                  if (exactAnnotationTypePattern.getAnnotationValues() == null || exactAnnotationTypePattern.getAnnotationValues().size() == 0) {
                     ResolvedType annotationType = exactAnnotationTypePattern.getAnnotationType().resolve(info.world);
                     if (type.hasAnnotation(annotationType)) {
                        return FuzzyBoolean.MAYBE;
                     }

                     if (!annotationType.isInheritedAnnotation()) {
                        return FuzzyBoolean.NO;
                     }

                     ResolvedType toMatchAgainst = type.getSuperclass();

                     boolean found;
                     for(found = false; toMatchAgainst != null; toMatchAgainst = toMatchAgainst.getSuperclass()) {
                        if (toMatchAgainst.hasAnnotation(annotationType)) {
                           found = true;
                           break;
                        }
                     }

                     if (!found) {
                        return FuzzyBoolean.NO;
                     }
                  }
               }
            }
         }

         return FuzzyBoolean.MAYBE;
      }
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      if (shadow.getKind() != this.kind) {
         return FuzzyBoolean.NO;
      } else if (shadow.getKind() == Shadow.SynchronizationLock && this.kind == Shadow.SynchronizationLock) {
         return FuzzyBoolean.YES;
      } else if (shadow.getKind() == Shadow.SynchronizationUnlock && this.kind == Shadow.SynchronizationUnlock) {
         return FuzzyBoolean.YES;
      } else if (!this.signature.matches(shadow.getMatchingSignature(), shadow.getIWorld(), this.kind == Shadow.MethodCall)) {
         if (this.kind == Shadow.MethodCall) {
            this.warnOnConfusingSig(shadow);
         }

         return FuzzyBoolean.NO;
      } else {
         return FuzzyBoolean.YES;
      }
   }

   private void warnOnConfusingSig(Shadow shadow) {
      if (shadow.getIWorld().getLint().unmatchedSuperTypeInCall.isEnabled()) {
         if (!(this.munger instanceof Checker)) {
            World world = shadow.getIWorld();
            UnresolvedType exactDeclaringType = this.signature.getDeclaringType().getExactType();
            ResolvedType shadowDeclaringType = shadow.getSignature().getDeclaringType().resolve(world);
            if (!this.signature.getDeclaringType().isStar() && !ResolvedType.isMissing(exactDeclaringType) && !exactDeclaringType.resolve(world).isMissing()) {
               if (shadowDeclaringType.isAssignableFrom(exactDeclaringType.resolve(world))) {
                  ResolvedMember rm = shadow.getSignature().resolve(world);
                  if (rm != null) {
                     int shadowModifiers = rm.getModifiers();
                     if (ResolvedType.isVisible(shadowModifiers, shadowDeclaringType, exactDeclaringType.resolve(world))) {
                        if (this.signature.getReturnType().matchesStatically(shadow.getSignature().getReturnType().resolve(world))) {
                           if (!exactDeclaringType.resolve(world).isInterface() || !shadowDeclaringType.equals(world.resolve("java.lang.Object"))) {
                              SignaturePattern nonConfusingPattern = new SignaturePattern(this.signature.getKind(), this.signature.getModifiers(), this.signature.getReturnType(), TypePattern.ANY, this.signature.getName(), this.signature.getParameterTypes(), this.signature.getThrowsPattern(), this.signature.getAnnotationPattern());
                              if (nonConfusingPattern.matches(shadow.getSignature(), shadow.getIWorld(), true)) {
                                 shadow.getIWorld().getLint().unmatchedSuperTypeInCall.signal(new String[]{shadow.getSignature().getDeclaringType().toString(), this.signature.getDeclaringType().toString()}, this.getSourceLocation(), new ISourceLocation[]{shadow.getSourceLocation()});
                              }

                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof KindedPointcut)) {
         return false;
      } else {
         KindedPointcut o = (KindedPointcut)other;
         return o.kind == this.kind && o.signature.equals(this.signature);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.kind.hashCode();
      result = 37 * result + this.signature.hashCode();
      return result;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.kind.getSimpleName());
      buf.append("(");
      buf.append(this.signature.toString());
      buf.append(")");
      return buf.toString();
   }

   public void postRead(ResolvedType enclosingType) {
      this.signature.postRead(enclosingType);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(1);
      this.kind.write(s);
      this.signature.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      Shadow.Kind kind = Shadow.Kind.read(s);
      SignaturePattern sig = SignaturePattern.read(s, context);
      KindedPointcut ret = new KindedPointcut(kind, sig);
      ret.readLocation(context, s);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      if (this.kind == Shadow.Initialization) {
      }

      this.signature = this.signature.resolveBindings(scope, bindings);
      UnresolvedType returnType;
      if (this.kind == Shadow.ConstructorExecution && this.signature.getDeclaringType() != null) {
         World world = scope.getWorld();
         returnType = this.signature.getDeclaringType().getExactType();
         if (this.signature.getKind() == Member.CONSTRUCTOR && !ResolvedType.isMissing(returnType) && returnType.resolve(world).isInterface() && !this.signature.getDeclaringType().isIncludeSubtypes()) {
            world.getLint().noInterfaceCtorJoinpoint.signal(returnType.toString(), this.getSourceLocation());
         }
      }

      HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor;
      if (this.kind == Shadow.StaticInitialization) {
         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getDeclaringType().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noStaticInitJPsForParameterizedTypes"), this.getSourceLocation()));
         }
      }

      if (this.kind == Shadow.FieldGet || this.kind == Shadow.FieldSet) {
         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getDeclaringType().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noParameterizedTypesInGetAndSet"), this.getSourceLocation()));
         }

         returnType = this.signature.getReturnType().getExactType();
         if (returnType.equals(UnresolvedType.VOID)) {
            scope.message(MessageUtil.error(WeaverMessages.format("fieldCantBeVoid"), this.getSourceLocation()));
         }
      }

      if (this.kind == Shadow.Initialization || this.kind == Shadow.PreInitialization) {
         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getDeclaringType().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noInitJPsForParameterizedTypes"), this.getSourceLocation()));
         }

         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getThrowsPattern().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noGenericThrowables"), this.getSourceLocation()));
         }
      }

      if (this.kind == Shadow.MethodExecution || this.kind == Shadow.ConstructorExecution) {
         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getDeclaringType().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noParameterizedDeclaringTypesInExecution"), this.getSourceLocation()));
         }

         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getThrowsPattern().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noGenericThrowables"), this.getSourceLocation()));
         }
      }

      if (this.kind == Shadow.MethodCall || this.kind == Shadow.ConstructorCall) {
         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getDeclaringType().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noParameterizedDeclaringTypesInCall"), this.getSourceLocation()));
         }

         visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
         this.signature.getThrowsPattern().traverse(visitor, (Object)null);
         if (visitor.wellHasItThen()) {
            scope.message(MessageUtil.error(WeaverMessages.format("noGenericThrowables"), this.getSourceLocation()));
         }

         if (!scope.getWorld().isJoinpointArrayConstructionEnabled() && this.kind == Shadow.ConstructorCall && this.signature.getDeclaringType().isArray()) {
            scope.message(MessageUtil.warn(WeaverMessages.format("noNewArrayJoinpointsByDefault"), this.getSourceLocation()));
         }
      }

   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      return this.match(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      Pointcut ret = new KindedPointcut(this.kind, this.signature, bindings.getEnclosingAdvice());
      ret.copyLocationFrom(this);
      return ret;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      Pointcut ret = new KindedPointcut(this.kind, this.signature.parameterizeWith(typeVariableMap, w), this.munger);
      ret.copyLocationFrom(this);
      return ret;
   }

   public Shadow.Kind getKind() {
      return this.kind;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
