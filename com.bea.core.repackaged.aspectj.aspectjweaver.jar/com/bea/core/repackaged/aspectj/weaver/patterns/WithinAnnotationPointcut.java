package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.BCException;
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

public class WithinAnnotationPointcut extends NameBindingPointcut {
   private AnnotationTypePattern annotationTypePattern;
   private String declarationText;

   public WithinAnnotationPointcut(AnnotationTypePattern type) {
      this.annotationTypePattern = type;
      this.pointcutKind = 17;
      this.buildDeclarationText();
   }

   public WithinAnnotationPointcut(AnnotationTypePattern type, ShadowMunger munger) {
      this(type);
      this.pointcutKind = 17;
   }

   public AnnotationTypePattern getAnnotationTypePattern() {
      return this.annotationTypePattern;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      WithinAnnotationPointcut ret = new WithinAnnotationPointcut(this.annotationTypePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      return this.annotationTypePattern.fastMatches(info.getType());
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      ResolvedType enclosingType = shadow.getIWorld().resolve(shadow.getEnclosingType(), true);
      if (enclosingType.isMissing()) {
         shadow.getIWorld().getLint().cantFindType.signal(new String[]{WeaverMessages.format("cantFindTypeWithinpcd", shadow.getEnclosingType().getName())}, shadow.getSourceLocation(), new ISourceLocation[]{this.getSourceLocation()});
      }

      this.annotationTypePattern.resolve(shadow.getIWorld());
      return this.annotationTypePattern.matches(enclosingType);
   }

   protected void resolveBindings(IScope scope, Bindings bindings) {
      if (!scope.getWorld().isInJava5Mode()) {
         scope.message(MessageUtil.error(WeaverMessages.format("atwithinNeedsJava5"), this.getSourceLocation()));
      } else {
         this.annotationTypePattern = this.annotationTypePattern.resolveBindings(scope, bindings, true);
      }
   }

   protected Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      ExactAnnotationTypePattern newType = (ExactAnnotationTypePattern)this.annotationTypePattern.remapAdviceFormals(bindings);
      Pointcut ret = new WithinAnnotationPointcut(newType, bindings.getEnclosingAdvice());
      ret.copyLocationFrom(this);
      return ret;
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      if (this.annotationTypePattern instanceof BindingAnnotationTypePattern) {
         BindingAnnotationTypePattern btp = (BindingAnnotationTypePattern)this.annotationTypePattern;
         UnresolvedType annotationType = btp.annotationType;
         Var var = shadow.getWithinAnnotationVar(annotationType);
         if (var == null) {
            throw new BCException("Impossible! annotation=[" + annotationType + "]  shadow=[" + shadow + " at " + shadow.getSourceLocation() + "]    pointcut is at [" + this.getSourceLocation() + "]");
         }

         state.set(btp.getFormalIndex(), var);
      }

      return this.match(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
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
      s.writeByte(17);
      this.annotationTypePattern.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AnnotationTypePattern type = AnnotationTypePattern.read(s, context);
      WithinAnnotationPointcut ret = new WithinAnnotationPointcut(type);
      ret.readLocation(context, s);
      return ret;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof WithinAnnotationPointcut)) {
         return false;
      } else {
         WithinAnnotationPointcut other = (WithinAnnotationPointcut)obj;
         return other.annotationTypePattern.equals(this.annotationTypePattern);
      }
   }

   public int hashCode() {
      return 17 + 19 * this.annotationTypePattern.hashCode();
   }

   private void buildDeclarationText() {
      StringBuffer buf = new StringBuffer();
      buf.append("@within(");
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
}
