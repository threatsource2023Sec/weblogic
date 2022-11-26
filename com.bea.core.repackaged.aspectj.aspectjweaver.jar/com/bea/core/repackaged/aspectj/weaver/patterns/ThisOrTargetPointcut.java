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

public class ThisOrTargetPointcut extends NameBindingPointcut {
   private boolean isThis;
   private TypePattern typePattern;
   private String declarationText;
   private static final int thisKindSet;
   private static final int targetKindSet;

   public boolean isBinding() {
      return this.typePattern instanceof BindingTypePattern;
   }

   public ThisOrTargetPointcut(boolean isThis, TypePattern type) {
      this.isThis = isThis;
      this.typePattern = type;
      this.pointcutKind = 3;
      this.declarationText = (isThis ? "this(" : "target(") + type + ")";
   }

   public TypePattern getType() {
      return this.typePattern;
   }

   public boolean isThis() {
      return this.isThis;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      ThisOrTargetPointcut ret = new ThisOrTargetPointcut(this.isThis, this.typePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public int couldMatchKinds() {
      return this.isThis ? thisKindSet : targetKindSet;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   private boolean couldMatch(Shadow shadow) {
      return this.isThis ? shadow.hasThis() : shadow.hasTarget();
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      if (!this.couldMatch(shadow)) {
         return FuzzyBoolean.NO;
      } else {
         UnresolvedType typeToMatch = this.isThis ? shadow.getThisType() : shadow.getTargetType();
         return this.typePattern.getExactType().equals(ResolvedType.OBJECT) ? FuzzyBoolean.YES : this.typePattern.matches(typeToMatch.resolve(shadow.getIWorld()), TypePattern.DYNAMIC);
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(3);
      s.writeBoolean(this.isThis);
      this.typePattern.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      boolean isThis = s.readBoolean();
      TypePattern type = TypePattern.read(s, context);
      ThisOrTargetPointcut ret = new ThisOrTargetPointcut(isThis, type);
      ret.readLocation(context, s);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.typePattern = this.typePattern.resolveBindings(scope, bindings, true, true);
      HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
      this.typePattern.traverse(visitor, (Object)null);
      if (visitor.wellHasItThen()) {
         scope.message(MessageUtil.error(WeaverMessages.format("noParameterizedTypesInThisAndTarget"), this.getSourceLocation()));
      }

   }

   public void postRead(ResolvedType enclosingType) {
      this.typePattern.postRead(enclosingType);
   }

   public List getBindingAnnotationTypePatterns() {
      return Collections.emptyList();
   }

   public List getBindingTypePatterns() {
      if (this.typePattern instanceof BindingTypePattern) {
         List l = new ArrayList();
         l.add((BindingTypePattern)this.typePattern);
         return l;
      } else {
         return Collections.emptyList();
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof ThisOrTargetPointcut)) {
         return false;
      } else {
         ThisOrTargetPointcut o = (ThisOrTargetPointcut)other;
         return o.isThis == this.isThis && o.typePattern.equals(this.typePattern);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + (this.isThis ? 0 : 1);
      result = 37 * result + this.typePattern.hashCode();
      return result;
   }

   public String toString() {
      return this.declarationText;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (!this.couldMatch(shadow)) {
         return Literal.FALSE;
      } else if (this.typePattern == TypePattern.ANY) {
         return Literal.TRUE;
      } else {
         Var var = this.isThis ? shadow.getThisVar() : shadow.getTargetVar();
         return this.exposeStateForVar(var, this.typePattern, state, shadow.getIWorld());
      }
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      if (this.isDeclare(bindings.getEnclosingAdvice())) {
         inAspect.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("thisOrTargetInDeclare", this.isThis ? "this" : "target"), bindings.getEnclosingAdvice().getSourceLocation(), (ISourceLocation)null);
         return Pointcut.makeMatchesNothing(Pointcut.CONCRETE);
      } else {
         TypePattern newType = this.typePattern.remapAdviceFormals(bindings);
         if (inAspect.crosscuttingMembers != null) {
            inAspect.crosscuttingMembers.exposeType(newType.getExactType());
         }

         Pointcut ret = new ThisOrTargetPointcut(this.isThis, newType);
         ret.copyLocationFrom(this);
         return ret;
      }
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
