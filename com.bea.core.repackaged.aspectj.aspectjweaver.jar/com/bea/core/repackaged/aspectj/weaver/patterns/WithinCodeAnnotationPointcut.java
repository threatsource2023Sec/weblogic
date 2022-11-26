package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.BCException;
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
import com.bea.core.repackaged.aspectj.weaver.ast.Var;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class WithinCodeAnnotationPointcut extends NameBindingPointcut {
   private ExactAnnotationTypePattern annotationTypePattern;
   private String declarationText;
   private static final int matchedShadowKinds;

   public WithinCodeAnnotationPointcut(ExactAnnotationTypePattern type) {
      this.annotationTypePattern = type;
      this.pointcutKind = 18;
      this.buildDeclarationText();
   }

   public WithinCodeAnnotationPointcut(ExactAnnotationTypePattern type, ShadowMunger munger) {
      this(type);
      this.pointcutKind = 18;
   }

   public ExactAnnotationTypePattern getAnnotationTypePattern() {
      return this.annotationTypePattern;
   }

   public int couldMatchKinds() {
      return matchedShadowKinds;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      WithinCodeAnnotationPointcut ret = new WithinCodeAnnotationPointcut((ExactAnnotationTypePattern)this.annotationTypePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      return FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      Member member = shadow.getEnclosingCodeSignature();
      ResolvedMember rMember = member.resolve(shadow.getIWorld());
      if (rMember == null) {
         if (member.getName().startsWith("ajc$")) {
            return FuzzyBoolean.NO;
         } else {
            shadow.getIWorld().getLint().unresolvableMember.signal(member.toString(), this.getSourceLocation());
            return FuzzyBoolean.NO;
         }
      } else {
         this.annotationTypePattern.resolve(shadow.getIWorld());
         return this.annotationTypePattern.matches(rMember);
      }
   }

   protected void resolveBindings(IScope scope, Bindings bindings) {
      if (!scope.getWorld().isInJava5Mode()) {
         scope.message(MessageUtil.error(WeaverMessages.format("atwithincodeNeedsJava5"), this.getSourceLocation()));
      } else {
         this.annotationTypePattern = (ExactAnnotationTypePattern)this.annotationTypePattern.resolveBindings(scope, bindings, true);
      }
   }

   protected Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      ExactAnnotationTypePattern newType = (ExactAnnotationTypePattern)this.annotationTypePattern.remapAdviceFormals(bindings);
      Pointcut ret = new WithinCodeAnnotationPointcut(newType, bindings.getEnclosingAdvice());
      ret.copyLocationFrom(this);
      return ret;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (this.annotationTypePattern instanceof BindingAnnotationTypePattern) {
         BindingAnnotationTypePattern btp = (BindingAnnotationTypePattern)this.annotationTypePattern;
         UnresolvedType annotationType = btp.annotationType;
         Var var = shadow.getWithinCodeAnnotationVar(annotationType);
         if (var == null) {
            throw new BCException("Impossible! annotation=[" + annotationType + "]  shadow=[" + shadow + " at " + shadow.getSourceLocation() + "]    pointcut is at [" + this.getSourceLocation() + "]");
         }

         state.set(btp.getFormalIndex(), var);
      }

      return this.matchInternal(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
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
      s.writeByte(18);
      this.annotationTypePattern.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AnnotationTypePattern type = AnnotationTypePattern.read(s, context);
      WithinCodeAnnotationPointcut ret = new WithinCodeAnnotationPointcut((ExactAnnotationTypePattern)type);
      ret.readLocation(context, s);
      return ret;
   }

   public boolean equals(Object other) {
      if (!(other instanceof WithinCodeAnnotationPointcut)) {
         return false;
      } else {
         WithinCodeAnnotationPointcut o = (WithinCodeAnnotationPointcut)other;
         return o.annotationTypePattern.equals(this.annotationTypePattern);
      }
   }

   public int hashCode() {
      int result = 17;
      result = 23 * result + this.annotationTypePattern.hashCode();
      return result;
   }

   private void buildDeclarationText() {
      StringBuffer buf = new StringBuffer();
      buf.append("@withincode(");
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
      int flags = Shadow.ALL_SHADOW_KINDS_BITS;

      for(int i = 0; i < Shadow.SHADOW_KINDS.length; ++i) {
         if (Shadow.SHADOW_KINDS[i].isEnclosingKind()) {
            flags -= Shadow.SHADOW_KINDS[i].bit;
         }
      }

      matchedShadowKinds = flags;
   }
}
