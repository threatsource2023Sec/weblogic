package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.IHasPosition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedPointcutDefinition;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;

public class PerThisOrTargetPointcutVisitor extends AbstractPatternNodeVisitor {
   private static final TypePattern MAYBE = new TypePatternMayBe();
   private final boolean m_isTarget;
   private final ResolvedType m_fromAspectType;

   public PerThisOrTargetPointcutVisitor(boolean isTarget, ResolvedType fromAspectType) {
      this.m_isTarget = isTarget;
      this.m_fromAspectType = fromAspectType;
   }

   public TypePattern getPerTypePointcut(Pointcut perClausePointcut) {
      Object o = perClausePointcut.accept(this, perClausePointcut);
      if (o instanceof TypePattern) {
         return (TypePattern)o;
      } else {
         throw new BCException("perClausePointcut visitor did not return a typepattern, it returned " + o + (o == null ? "" : " of type " + o.getClass()));
      }
   }

   public Object visit(WithinPointcut node, Object data) {
      return this.m_isTarget ? MAYBE : node.getTypePattern();
   }

   public Object visit(WithincodePointcut node, Object data) {
      return this.m_isTarget ? MAYBE : node.getSignature().getDeclaringType();
   }

   public Object visit(WithinAnnotationPointcut node, Object data) {
      return this.m_isTarget ? MAYBE : new AnyWithAnnotationTypePattern(node.getAnnotationTypePattern());
   }

   public Object visit(WithinCodeAnnotationPointcut node, Object data) {
      return this.m_isTarget ? MAYBE : MAYBE;
   }

   public Object visit(KindedPointcut node, Object data) {
      if (node.getKind().equals(Shadow.AdviceExecution)) {
         return MAYBE;
      } else if (!node.getKind().equals(Shadow.ConstructorExecution) && !node.getKind().equals(Shadow.Initialization) && !node.getKind().equals(Shadow.MethodExecution) && !node.getKind().equals(Shadow.PreInitialization) && !node.getKind().equals(Shadow.StaticInitialization)) {
         if (!node.getKind().equals(Shadow.ConstructorCall) && !node.getKind().equals(Shadow.FieldGet) && !node.getKind().equals(Shadow.FieldSet) && !node.getKind().equals(Shadow.MethodCall)) {
            if (node.getKind().equals(Shadow.ExceptionHandler)) {
               return MAYBE;
            } else {
               throw new ParserException("Undetermined - should not happen: " + node.getKind().getSimpleName(), (IHasPosition)null);
            }
         } else {
            return this.m_isTarget ? node.getSignature().getDeclaringType() : MAYBE;
         }
      } else {
         SignaturePattern signaturePattern = node.getSignature();
         boolean isStarAnnotation = signaturePattern.isStarAnnotation();
         return !this.m_isTarget && node.getKind().equals(Shadow.MethodExecution) && !isStarAnnotation ? new HasMemberTypePatternForPerThisMatching(signaturePattern) : signaturePattern.getDeclaringType();
      }
   }

   public Object visit(AndPointcut node, Object data) {
      return new AndTypePattern(this.getPerTypePointcut(node.left), this.getPerTypePointcut(node.right));
   }

   public Object visit(OrPointcut node, Object data) {
      return new OrTypePattern(this.getPerTypePointcut(node.left), this.getPerTypePointcut(node.right));
   }

   public Object visit(NotPointcut node, Object data) {
      return MAYBE;
   }

   public Object visit(ThisOrTargetAnnotationPointcut node, Object data) {
      if (this.m_isTarget && !node.isThis()) {
         return new AnyWithAnnotationTypePattern(node.getAnnotationTypePattern());
      } else {
         return !this.m_isTarget && node.isThis() ? new AnyWithAnnotationTypePattern(node.getAnnotationTypePattern()) : MAYBE;
      }
   }

   public Object visit(ThisOrTargetPointcut node, Object data) {
      if (this.m_isTarget && !node.isThis() || !this.m_isTarget && node.isThis()) {
         String pointcutString = node.getType().toString();
         if (pointcutString.equals("<nothing>")) {
            return new NoTypePattern();
         } else {
            TypePattern copy = (new PatternParser(pointcutString.replace('$', '.'))).parseTypePattern();
            copy.includeSubtypes = true;
            return copy;
         }
      } else {
         return MAYBE;
      }
   }

   public Object visit(ReferencePointcut node, Object data) {
      ResolvedType searchStart = this.m_fromAspectType;
      if (node.onType != null) {
         searchStart = node.onType.resolve(this.m_fromAspectType.getWorld());
         if (searchStart.isMissing()) {
            return MAYBE;
         }
      }

      ResolvedPointcutDefinition pointcutDec = searchStart.findPointcut(node.name);
      return this.getPerTypePointcut(pointcutDec.getPointcut());
   }

   public Object visit(IfPointcut node, Object data) {
      return TypePattern.ANY;
   }

   public Object visit(HandlerPointcut node, Object data) {
      return MAYBE;
   }

   public Object visit(CflowPointcut node, Object data) {
      return MAYBE;
   }

   public Object visit(ConcreteCflowPointcut node, Object data) {
      return MAYBE;
   }

   public Object visit(ArgsPointcut node, Object data) {
      return MAYBE;
   }

   public Object visit(ArgsAnnotationPointcut node, Object data) {
      return MAYBE;
   }

   public Object visit(AnnotationPointcut node, Object data) {
      return MAYBE;
   }

   public Object visit(Pointcut.MatchesNothingPointcut node, Object data) {
      return new NoTypePattern() {
         public String toString() {
            return "false";
         }
      };
   }

   private static class TypePatternMayBe extends AnyTypePattern {
      private TypePatternMayBe() {
      }

      // $FF: synthetic method
      TypePatternMayBe(Object x0) {
         this();
      }
   }
}
