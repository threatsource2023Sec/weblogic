package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Literal;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.Map;

public class WithincodePointcut extends Pointcut {
   private SignaturePattern signature;
   private static final int matchedShadowKinds;

   public WithincodePointcut(SignaturePattern signature) {
      this.signature = signature;
      this.pointcutKind = 12;
   }

   public SignaturePattern getSignature() {
      return this.signature;
   }

   public int couldMatchKinds() {
      return matchedShadowKinds;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      WithincodePointcut ret = new WithincodePointcut(this.signature.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      return FuzzyBoolean.fromBoolean(this.signature.matches(shadow.getEnclosingCodeSignature(), shadow.getIWorld(), false));
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(12);
      this.signature.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      WithincodePointcut ret = new WithincodePointcut(SignaturePattern.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.signature = this.signature.resolveBindings(scope, bindings);
      HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
      this.signature.getDeclaringType().traverse(visitor, (Object)null);
      if (visitor.wellHasItThen()) {
         scope.message(MessageUtil.error(WeaverMessages.format("noParameterizedDeclaringTypesWithinCode"), this.getSourceLocation()));
      }

      visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
      this.signature.getThrowsPattern().traverse(visitor, (Object)null);
      if (visitor.wellHasItThen()) {
         scope.message(MessageUtil.error(WeaverMessages.format("noGenericThrowables"), this.getSourceLocation()));
      }

   }

   public void postRead(ResolvedType enclosingType) {
      this.signature.postRead(enclosingType);
   }

   public boolean equals(Object other) {
      if (!(other instanceof WithincodePointcut)) {
         return false;
      } else {
         WithincodePointcut o = (WithincodePointcut)other;
         return o.signature.equals(this.signature);
      }
   }

   public int hashCode() {
      int result = 43;
      result = 37 * result + this.signature.hashCode();
      return result;
   }

   public String toString() {
      return "withincode(" + this.signature + ")";
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      return this.match(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      Pointcut ret = new WithincodePointcut(this.signature);
      ret.copyLocationFrom(this);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   static {
      int flags = Shadow.ALL_SHADOW_KINDS_BITS;

      for(int i = 0; i < Shadow.SHADOW_KINDS.length; ++i) {
         if (Shadow.SHADOW_KINDS[i].isEnclosingKind()) {
            flags -= Shadow.SHADOW_KINDS[i].bit;
         }
      }

      flags |= Shadow.ConstructorExecution.bit;
      flags |= Shadow.Initialization.bit;
      matchedShadowKinds = flags;
   }
}
