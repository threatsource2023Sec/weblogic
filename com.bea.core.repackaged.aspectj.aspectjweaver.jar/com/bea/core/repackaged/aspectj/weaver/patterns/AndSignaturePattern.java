package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AndSignaturePattern extends AbstractSignaturePattern {
   private ISignaturePattern leftSp;
   private ISignaturePattern rightSp;
   private List exactDeclaringTypes;

   public AndSignaturePattern(ISignaturePattern leftSp, ISignaturePattern rightSp) {
      this.leftSp = leftSp;
      this.rightSp = rightSp;
   }

   public boolean couldEverMatch(ResolvedType type) {
      return this.leftSp.couldEverMatch(type) || this.rightSp.couldEverMatch(type);
   }

   public List getExactDeclaringTypes() {
      if (this.exactDeclaringTypes == null) {
         this.exactDeclaringTypes = new ArrayList();
         this.exactDeclaringTypes.addAll(this.leftSp.getExactDeclaringTypes());
         this.exactDeclaringTypes.addAll(this.rightSp.getExactDeclaringTypes());
      }

      return this.exactDeclaringTypes;
   }

   public boolean isMatchOnAnyName() {
      return this.leftSp.isMatchOnAnyName() || this.rightSp.isMatchOnAnyName();
   }

   public boolean isStarAnnotation() {
      return this.leftSp.isStarAnnotation() || this.rightSp.isStarAnnotation();
   }

   public boolean matches(Member member, World world, boolean b) {
      return this.leftSp.matches(member, world, b) && this.rightSp.matches(member, world, b);
   }

   public ISignaturePattern parameterizeWith(Map typeVariableBindingMap, World world) {
      return new AndSignaturePattern(this.leftSp.parameterizeWith(typeVariableBindingMap, world), this.rightSp.parameterizeWith(typeVariableBindingMap, world));
   }

   public ISignaturePattern resolveBindings(IScope scope, Bindings bindings) {
      this.leftSp.resolveBindings(scope, bindings);
      this.rightSp.resolveBindings(scope, bindings);
      return this;
   }

   public static ISignaturePattern readAndSignaturePattern(VersionedDataInputStream s, ISourceContext context) throws IOException {
      AndSignaturePattern ret = new AndSignaturePattern(readCompoundSignaturePattern(s, context), readCompoundSignaturePattern(s, context));
      s.readInt();
      s.readInt();
      return ret;
   }

   public ISignaturePattern getLeft() {
      return this.leftSp;
   }

   public ISignaturePattern getRight() {
      return this.rightSp;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append(this.leftSp.toString()).append(" && ").append(this.rightSp.toString());
      return sb.toString();
   }
}
