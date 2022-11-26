package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.IMessage;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class DeclarePrecedence extends Declare {
   private TypePatternList patterns;
   private IScope scope;

   public DeclarePrecedence(List patterns) {
      this(new TypePatternList(patterns));
   }

   private DeclarePrecedence(TypePatternList patterns) {
      this.scope = null;
      this.patterns = patterns;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Declare parameterizeWith(Map typeVariableBindingMap, World w) {
      DeclarePrecedence ret = new DeclarePrecedence(this.patterns.parameterizeWith(typeVariableBindingMap, w));
      ret.copyLocationFrom(this);
      return ret;
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("declare precedence: ");
      buf.append(this.patterns);
      buf.append(";");
      return buf.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof DeclarePrecedence)) {
         return false;
      } else {
         DeclarePrecedence o = (DeclarePrecedence)other;
         return o.patterns.equals(this.patterns);
      }
   }

   public int hashCode() {
      return this.patterns.hashCode();
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(4);
      this.patterns.write(s);
      this.writeLocation(s);
   }

   public static Declare read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      Declare ret = new DeclarePrecedence(TypePatternList.read(s, context));
      ret.readLocation(context, s);
      return ret;
   }

   public void setScopeForResolution(IScope scope) {
      this.scope = scope;
   }

   public void ensureResolved() {
      if (this.scope != null) {
         try {
            this.resolve(this.scope);
         } finally {
            this.scope = null;
         }
      }

   }

   public void resolve(IScope scope) {
      this.patterns = this.patterns.resolveBindings(scope, Bindings.NONE, false, false);
      boolean seenStar = false;

      for(int i = 0; i < this.patterns.size(); ++i) {
         TypePattern pi = this.patterns.get(i);
         if (pi.isStar()) {
            if (seenStar) {
               scope.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("circularityInPrecedenceStar"), pi.getSourceLocation(), (ISourceLocation)null);
            }

            seenStar = true;
         } else {
            ResolvedType exactType = pi.getExactType().resolve(scope.getWorld());
            if (!exactType.isMissing()) {
               if (!exactType.isAspect() && !exactType.isAnnotationStyleAspect() && !pi.isIncludeSubtypes() && !exactType.isTypeVariableReference()) {
                  scope.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("nonAspectTypesInPrecedence", exactType.getName()), pi.getSourceLocation(), (ISourceLocation)null);
               }

               for(int j = 0; j < this.patterns.size(); ++j) {
                  if (j != i) {
                     TypePattern pj = this.patterns.get(j);
                     if (!pj.isStar() && pj.matchesStatically(exactType)) {
                        scope.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("circularityInPrecedenceTwo", exactType.getName()), pi.getSourceLocation(), pj.getSourceLocation());
                     }
                  }
               }
            }
         }
      }

   }

   public TypePatternList getPatterns() {
      this.ensureResolved();
      return this.patterns;
   }

   private int matchingIndex(ResolvedType a) {
      this.ensureResolved();
      int knownMatch = -1;
      int starMatch = -1;
      int i = 0;

      for(int len = this.patterns.size(); i < len; ++i) {
         TypePattern p = this.patterns.get(i);
         if (p.isStar()) {
            starMatch = i;
         } else if (p.matchesStatically(a)) {
            if (knownMatch != -1) {
               a.getWorld().showMessage(IMessage.ERROR, WeaverMessages.format("multipleMatchesInPrecedence", a, this.patterns.get(knownMatch), p), this.patterns.get(knownMatch).getSourceLocation(), p.getSourceLocation());
               return -1;
            }

            knownMatch = i;
         }
      }

      if (knownMatch == -1) {
         return starMatch;
      } else {
         return knownMatch;
      }
   }

   public int compare(ResolvedType aspect1, ResolvedType aspect2) {
      this.ensureResolved();
      int index1 = this.matchingIndex(aspect1);
      int index2 = this.matchingIndex(aspect2);
      if (index1 != -1 && index2 != -1) {
         if (index1 == index2) {
            return 0;
         } else {
            return index1 > index2 ? -1 : 1;
         }
      } else {
         return 0;
      }
   }

   public boolean isAdviceLike() {
      return false;
   }

   public String getNameSuffix() {
      return "precedence";
   }
}
