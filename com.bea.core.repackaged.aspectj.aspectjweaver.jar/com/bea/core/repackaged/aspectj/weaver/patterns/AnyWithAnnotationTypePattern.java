package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Map;

public class AnyWithAnnotationTypePattern extends TypePattern {
   public AnyWithAnnotationTypePattern(AnnotationTypePattern atp) {
      super(false, false);
      this.annotationPattern = atp;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   protected boolean couldEverMatchSameTypesAs(TypePattern other) {
      return true;
   }

   protected boolean matchesExactly(ResolvedType type) {
      this.annotationPattern.resolve(type.getWorld());
      boolean b = false;
      if (type.temporaryAnnotationTypes != null) {
         b = this.annotationPattern.matches(type, type.temporaryAnnotationTypes).alwaysTrue();
      } else {
         b = this.annotationPattern.matches(type).alwaysTrue();
      }

      return b;
   }

   public TypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding, boolean requireExactType) {
      if (requireExactType) {
         scope.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("wildcardTypePatternNotAllowed"), this.getSourceLocation()));
         return NO;
      } else {
         return super.resolveBindings(scope, bindings, allowBinding, requireExactType);
      }
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      this.annotationPattern.resolve(type.getWorld());
      return this.annotationPattern.matches(annotatedType).alwaysTrue();
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      return Modifier.isFinal(type.getModifiers()) ? FuzzyBoolean.fromBoolean(this.matchesExactly(type)) : FuzzyBoolean.MAYBE;
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      AnyWithAnnotationTypePattern ret = new AnyWithAnnotationTypePattern(this.annotationPattern.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(10);
      this.annotationPattern.write(s);
      this.writeLocation(s);
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext c) throws IOException {
      AnnotationTypePattern annPatt = AnnotationTypePattern.read(s, c);
      AnyWithAnnotationTypePattern ret = new AnyWithAnnotationTypePattern(annPatt);
      ret.readLocation(c, s);
      return ret;
   }

   protected boolean matchesSubtypes(ResolvedType type) {
      return true;
   }

   public boolean isStar() {
      return false;
   }

   public String toString() {
      return "(" + this.annotationPattern + " *)";
   }

   public AnnotationTypePattern getAnnotationTypePattern() {
      return this.annotationPattern;
   }

   public boolean equals(Object obj) {
      if (!(obj instanceof AnyWithAnnotationTypePattern)) {
         return false;
      } else {
         AnyWithAnnotationTypePattern awatp = (AnyWithAnnotationTypePattern)obj;
         return this.annotationPattern.equals(awatp.annotationPattern);
      }
   }

   public int hashCode() {
      return this.annotationPattern.hashCode();
   }
}
