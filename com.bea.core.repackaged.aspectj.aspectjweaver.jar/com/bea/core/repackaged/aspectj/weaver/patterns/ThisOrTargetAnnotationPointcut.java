package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.ShadowMunger;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ThisOrTargetAnnotationPointcut extends NameBindingPointcut {
   private boolean isThis;
   private boolean alreadyWarnedAboutDEoW;
   private ExactAnnotationTypePattern annotationTypePattern;
   private String declarationText;
   private static final int thisKindSet;
   private static final int targetKindSet;

   public ThisOrTargetAnnotationPointcut(boolean isThis, ExactAnnotationTypePattern type) {
      this.alreadyWarnedAboutDEoW = false;
      this.isThis = isThis;
      this.annotationTypePattern = type;
      this.pointcutKind = 19;
      this.buildDeclarationText();
   }

   public ThisOrTargetAnnotationPointcut(boolean isThis, ExactAnnotationTypePattern type, ShadowMunger munger) {
      this(isThis, type);
   }

   public ExactAnnotationTypePattern getAnnotationTypePattern() {
      return this.annotationTypePattern;
   }

   public int couldMatchKinds() {
      return this.isThis ? thisKindSet : targetKindSet;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      ExactAnnotationTypePattern newPattern = (ExactAnnotationTypePattern)this.annotationTypePattern.parameterizeWith(typeVariableMap, w);
      if (newPattern.getAnnotationType() instanceof ResolvedType) {
         this.verifyRuntimeRetention(newPattern.getResolvedAnnotationType());
      }

      ThisOrTargetAnnotationPointcut ret = new ThisOrTargetAnnotationPointcut(this.isThis, (ExactAnnotationTypePattern)this.annotationTypePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      if (!this.couldMatch(shadow)) {
         return FuzzyBoolean.NO;
      } else {
         ResolvedType toMatchAgainst = (this.isThis ? shadow.getThisType() : shadow.getTargetType()).resolve(shadow.getIWorld());
         this.annotationTypePattern.resolve(shadow.getIWorld());
         return this.annotationTypePattern.matchesRuntimeType(toMatchAgainst).alwaysTrue() ? FuzzyBoolean.YES : FuzzyBoolean.MAYBE;
      }
   }

   public boolean isThis() {
      return this.isThis;
   }

   protected void resolveBindings(IScope scope, Bindings bindings) {
      if (!scope.getWorld().isInJava5Mode()) {
         scope.message(MessageUtil.error(WeaverMessages.format(this.isThis ? "atthisNeedsJava5" : "attargetNeedsJava5"), this.getSourceLocation()));
      } else {
         this.annotationTypePattern = (ExactAnnotationTypePattern)this.annotationTypePattern.resolveBindings(scope, bindings, true);
         if (this.annotationTypePattern.annotationType != null) {
            ResolvedType rAnnotationType = (ResolvedType)this.annotationTypePattern.annotationType;
            if (!rAnnotationType.isTypeVariableReference()) {
               this.verifyRuntimeRetention(rAnnotationType);
            }
         }
      }
   }

   private void verifyRuntimeRetention(ResolvedType rAnnotationType) {
      if (!rAnnotationType.isAnnotationWithRuntimeRetention()) {
         IMessage m = MessageUtil.error(WeaverMessages.format("bindingNonRuntimeRetentionAnnotation", rAnnotationType.getName()), this.getSourceLocation());
         rAnnotationType.getWorld().getMessageHandler().handleMessage(m);
      }

   }

   protected Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      if (this.isDeclare(bindings.getEnclosingAdvice())) {
         if (!this.alreadyWarnedAboutDEoW) {
            inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("thisOrTargetInDeclare", this.isThis ? "this" : "target"), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
            this.alreadyWarnedAboutDEoW = true;
         }

         return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
      } else {
         ExactAnnotationTypePattern newType = (ExactAnnotationTypePattern)this.annotationTypePattern.remapAdviceFormals(bindings);
         ThisOrTargetAnnotationPointcut ret = new ThisOrTargetAnnotationPointcut(this.isThis, newType, bindings.getEnclosingAdvice());
         ret.alreadyWarnedAboutDEoW = this.alreadyWarnedAboutDEoW;
         ret.copyLocationFrom(this);
         return ret;
      }
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (!this.couldMatch(shadow)) {
         return Literal.FALSE;
      } else {
         boolean alwaysMatches = this.match(shadow).alwaysTrue();
         Var var = this.isThis ? shadow.getThisVar() : shadow.getTargetVar();
         Var annVar = null;
         UnresolvedType annotationType = this.annotationTypePattern.annotationType;
         if (this.annotationTypePattern instanceof BindingAnnotationTypePattern) {
            BindingAnnotationTypePattern btp = (BindingAnnotationTypePattern)this.annotationTypePattern;
            annotationType = btp.annotationType;
            annVar = this.isThis ? shadow.getThisAnnotationVar(annotationType) : shadow.getTargetAnnotationVar(annotationType);
            if (annVar == null) {
               throw new RuntimeException("Impossible!");
            }

            state.set(btp.getFormalIndex(), annVar);
         }

         if (alwaysMatches && annVar == null) {
            return Literal.TRUE;
         } else {
            ResolvedType rType = annotationType.resolve(shadow.getIWorld());
            return Test.makeHasAnnotation(var, rType);
         }
      }
   }

   private boolean couldMatch(Shadow shadow) {
      return this.isThis ? shadow.hasThis() : shadow.hasTarget();
   }

   public List getBindingAnnotationTypePatterns() {
      if (this.annotationTypePattern instanceof BindingAnnotationTypePattern) {
         List l = new ArrayList();
         l.add((BindingPattern)this.annotationTypePattern);
         return l;
      } else {
         return Collections.emptyList();
      }
   }

   public List getBindingTypePatterns() {
      return Collections.emptyList();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(19);
      s.writeBoolean(this.isThis);
      this.annotationTypePattern.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      boolean isThis = s.readBoolean();
      AnnotationTypePattern type = AnnotationTypePattern.read(s, context);
      ThisOrTargetAnnotationPointcut ret = new ThisOrTargetAnnotationPointcut(isThis, (ExactAnnotationTypePattern)type);
      ret.readLocation(context, s);
      return ret;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof ThisOrTargetAnnotationPointcut)) {
         return false;
      } else {
         ThisOrTargetAnnotationPointcut other = (ThisOrTargetAnnotationPointcut)obj;
         return other.annotationTypePattern.equals(this.annotationTypePattern) && other.isThis == this.isThis;
      }
   }

   public int hashCode() {
      return 17 + 37 * this.annotationTypePattern.hashCode() + (this.isThis ? 49 : 13);
   }

   private void buildDeclarationText() {
      StringBuffer buf = new StringBuffer();
      buf.append(this.isThis ? "@this(" : "@target(");
      String annPatt = this.annotationTypePattern.toString();
      buf.append(annPatt.startsWith("@") ? annPatt.substring(1) : annPatt);
      buf.append(")");
      this.declarationText = buf.toString();
   }

   public String toString() {
      return this.declarationText;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   static {
      int thisFlags = Shadow.ALL_SHADOW_KINDS_BITS;
      int targFlags = Shadow.ALL_SHADOW_KINDS_BITS;

      for(int i = 0; i < Shadow.SHADOW_KINDS.length; ++i) {
         Shadow.Kind kind = Shadow.SHADOW_KINDS[i];
         if (kind.neverHasThis()) {
            thisFlags -= kind.bit;
         }

         if (kind.neverHasTarget()) {
            targFlags -= kind.bit;
         }
      }

      thisKindSet = thisFlags;
      targetKindSet = targFlags;
   }
}
