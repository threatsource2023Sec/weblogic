package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.UnresolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public class ThrowsPattern extends PatternNode {
   private TypePatternList required;
   private TypePatternList forbidden;
   public static final ThrowsPattern ANY;

   public ThrowsPattern(TypePatternList required, TypePatternList forbidden) {
      this.required = required;
      this.forbidden = forbidden;
   }

   public TypePatternList getRequired() {
      return this.required;
   }

   public TypePatternList getForbidden() {
      return this.forbidden;
   }

   public String toString() {
      if (this == ANY) {
         return "";
      } else {
         String ret = "throws " + this.required.toString();
         if (this.forbidden.size() > 0) {
            ret = ret + " !(" + this.forbidden.toString() + ")";
         }

         return ret;
      }
   }

   public boolean equals(Object other) {
      if (!(other instanceof ThrowsPattern)) {
         return false;
      } else {
         ThrowsPattern o = (ThrowsPattern)other;
         boolean ret = o.required.equals(this.required) && o.forbidden.equals(this.forbidden);
         return ret;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.required.hashCode();
      result = 37 * result + this.forbidden.hashCode();
      return result;
   }

   public ThrowsPattern resolveBindings(IScope scope, Bindings bindings) {
      if (this == ANY) {
         return this;
      } else {
         this.required = this.required.resolveBindings(scope, bindings, false, false);
         this.forbidden = this.forbidden.resolveBindings(scope, bindings, false, false);
         return this;
      }
   }

   public ThrowsPattern parameterizeWith(Map typeVariableMap, World w) {
      ThrowsPattern ret = new ThrowsPattern(this.required.parameterizeWith(typeVariableMap, w), this.forbidden.parameterizeWith(typeVariableMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public boolean matches(UnresolvedType[] tys, World world) {
      if (this == ANY) {
         return true;
      } else {
         ResolvedType[] types = world.resolve(tys);
         int j = 0;

         int lenj;
         for(lenj = this.required.size(); j < lenj; ++j) {
            if (!this.matchesAny(this.required.get(j), types)) {
               return false;
            }
         }

         j = 0;

         for(lenj = this.forbidden.size(); j < lenj; ++j) {
            if (this.matchesAny(this.forbidden.get(j), types)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean matchesAny(TypePattern typePattern, ResolvedType[] types) {
      for(int i = types.length - 1; i >= 0; --i) {
         if (typePattern.matchesStatically(types[i])) {
            return true;
         }
      }

      return false;
   }

   public static ThrowsPattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      TypePatternList required = TypePatternList.read(s, context);
      TypePatternList forbidden = TypePatternList.read(s, context);
      if (required.size() == 0 && forbidden.size() == 0) {
         return ANY;
      } else {
         ThrowsPattern ret = new ThrowsPattern(required, forbidden);
         return ret;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      this.required.write(s);
      this.forbidden.write(s);
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      Object ret = this.accept(visitor, data);
      this.forbidden.traverse(visitor, data);
      this.required.traverse(visitor, data);
      return ret;
   }

   static {
      ANY = new ThrowsPattern(TypePatternList.EMPTY, TypePatternList.EMPTY);
   }
}
