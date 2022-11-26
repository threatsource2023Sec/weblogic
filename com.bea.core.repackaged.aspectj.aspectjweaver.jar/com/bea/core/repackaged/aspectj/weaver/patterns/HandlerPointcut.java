package com.bea.core.repackaged.aspectj.weaver.patterns;

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
import java.io.IOException;
import java.util.Map;

public class HandlerPointcut extends Pointcut {
   TypePattern exceptionType;
   private static final int MATCH_KINDS;

   public HandlerPointcut(TypePattern exceptionType) {
      this.exceptionType = exceptionType;
      this.pointcutKind = 13;
   }

   public int couldMatchKinds() {
      return MATCH_KINDS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      if (shadow.getKind() != Shadow.ExceptionHandler) {
         return FuzzyBoolean.NO;
      } else {
         this.exceptionType.resolve(shadow.getIWorld());
         return this.exceptionType.matches(shadow.getSignature().getParameterTypes()[0].resolve(shadow.getIWorld()), TypePattern.STATIC);
      }
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      HandlerPointcut ret = new HandlerPointcut(this.exceptionType.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public boolean equals(Object other) {
      if (!(other instanceof HandlerPointcut)) {
         return false;
      } else {
         HandlerPointcut o = (HandlerPointcut)other;
         return o.exceptionType.equals(this.exceptionType);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.exceptionType.hashCode();
      return result;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("handler(");
      buf.append(this.exceptionType.toString());
      buf.append(")");
      return buf.toString();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(13);
      this.exceptionType.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      HandlerPointcut ret = new HandlerPointcut(TypePattern.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.exceptionType = this.exceptionType.resolveBindings(scope, bindings, false, false);
      boolean invalidParameterization = false;
      if (this.exceptionType.getTypeParameters().size() > 0) {
         invalidParameterization = true;
      }

      UnresolvedType exactType = this.exceptionType.getExactType();
      if (exactType != null && exactType.isParameterizedType()) {
         invalidParameterization = true;
      }

      if (invalidParameterization) {
         scope.message(MessageUtil.error(WeaverMessages.format("noParameterizedTypePatternInHandler"), this.getSourceLocation()));
      }

   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      return this.match(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      Pointcut ret = new HandlerPointcut(this.exceptionType);
      ret.copyLocationFrom(this);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   static {
      MATCH_KINDS = Shadow.ExceptionHandler.bit;
   }
}
