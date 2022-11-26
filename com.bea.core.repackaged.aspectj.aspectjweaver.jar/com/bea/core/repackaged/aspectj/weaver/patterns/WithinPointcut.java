package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
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

public class WithinPointcut extends Pointcut {
   private TypePattern typePattern;

   public WithinPointcut(TypePattern type) {
      this.typePattern = type;
      this.pointcutKind = 2;
   }

   public TypePattern getTypePattern() {
      return this.typePattern;
   }

   private FuzzyBoolean isWithinType(ResolvedType type) {
      while(type != null) {
         if (this.typePattern.matchesStatically(type)) {
            return FuzzyBoolean.YES;
         }

         type = type.getDeclaringType();
      }

      return FuzzyBoolean.NO;
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      WithinPointcut ret = new WithinPointcut(this.typePattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo info) {
      return this.typePattern.annotationPattern instanceof AnyAnnotationTypePattern ? this.isWithinType(info.getType()) : FuzzyBoolean.MAYBE;
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      ResolvedType enclosingType = shadow.getIWorld().resolve(shadow.getEnclosingType(), true);
      if (enclosingType.isMissing()) {
         shadow.getIWorld().getLint().cantFindType.signal(new String[]{WeaverMessages.format("cantFindTypeWithinpcd", shadow.getEnclosingType().getName())}, shadow.getSourceLocation(), new ISourceLocation[]{this.getSourceLocation()});
      }

      this.typePattern.resolve(shadow.getIWorld());
      return this.isWithinType(enclosingType);
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(2);
      this.typePattern.write(s);
      this.writeLocation(s);
   }

   public static Pointcut read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      TypePattern type = TypePattern.read(s, context);
      WithinPointcut ret = new WithinPointcut(type);
      ret.readLocation(context, s);
      return ret;
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
      this.typePattern = this.typePattern.resolveBindings(scope, bindings, false, false);
      HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor visitor = new HasThisTypePatternTriedToSneakInSomeGenericOrParameterizedTypePatternMatchingStuffAnywhereVisitor();
      this.typePattern.traverse(visitor, (Object)null);
      if (visitor.wellHasItThen()) {
         scope.message(MessageUtil.error(WeaverMessages.format("noParameterizedTypePatternInWithin"), this.getSourceLocation()));
      }

   }

   public void postRead(ResolvedType enclosingType) {
      this.typePattern.postRead(enclosingType);
   }

   public boolean couldEverMatchSameJoinPointsAs(WithinPointcut other) {
      return this.typePattern.couldEverMatchSameTypesAs(other.typePattern);
   }

   public boolean equals(Object other) {
      if (!(other instanceof WithinPointcut)) {
         return false;
      } else {
         WithinPointcut o = (WithinPointcut)other;
         return o.typePattern.equals(this.typePattern);
      }
   }

   public int hashCode() {
      return this.typePattern.hashCode();
   }

   public String toString() {
      return "within(" + this.typePattern + ")";
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      return this.match(shadow).alwaysTrue() ? Literal.TRUE : Literal.FALSE;
   }

   public Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      Pointcut ret = new WithinPointcut(this.typePattern);
      ret.copyLocationFrom(this);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }
}
