package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.Member;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class NotSignaturePattern extends AbstractSignaturePattern {
   private ISignaturePattern negatedSp;

   public NotSignaturePattern(ISignaturePattern negatedSp) {
      this.negatedSp = negatedSp;
   }

   public boolean couldEverMatch(ResolvedType type) {
      if (this.negatedSp.getExactDeclaringTypes().size() == 0) {
         return true;
      } else {
         return !this.negatedSp.couldEverMatch(type);
      }
   }

   public List getExactDeclaringTypes() {
      return this.negatedSp.getExactDeclaringTypes();
   }

   public boolean isMatchOnAnyName() {
      return this.negatedSp.isMatchOnAnyName();
   }

   public boolean isStarAnnotation() {
      return this.negatedSp.isStarAnnotation();
   }

   public boolean matches(Member member, World world, boolean b) {
      return !this.negatedSp.matches(member, world, b);
   }

   public ISignaturePattern parameterizeWith(Map typeVariableBindingMap, World world) {
      return new NotSignaturePattern(this.negatedSp.parameterizeWith(typeVariableBindingMap, world));
   }

   public ISignaturePattern resolveBindings(IScope scope, Bindings bindings) {
      this.negatedSp.resolveBindings(scope, bindings);
      return this;
   }

   public static ISignaturePattern readNotSignaturePattern(VersionedDataInputStream s, ISourceContext context) throws IOException {
      NotSignaturePattern ret = new NotSignaturePattern(readCompoundSignaturePattern(s, context));
      s.readInt();
      s.readInt();
      return ret;
   }

   public ISignaturePattern getNegated() {
      return this.negatedSp;
   }

   public String toString() {
      StringBuilder sb = new StringBuilder();
      sb.append("!").append(this.negatedSp.toString());
      return sb.toString();
   }
}
