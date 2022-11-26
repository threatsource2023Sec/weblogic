package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.bridge.MessageUtil;
import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.Shadow;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.WeaverMessages;
import com.bea.core.repackaged.aspectj.weaver.World;
import com.bea.core.repackaged.aspectj.weaver.ast.Test;
import java.io.IOException;
import java.util.Map;

public class PerFromSuper extends PerClause {
   private PerClause.Kind kind;

   public PerFromSuper(PerClause.Kind kind) {
      this.kind = kind;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public int couldMatchKinds() {
      return Shadow.ALL_SHADOW_KINDS_BITS;
   }

   public FuzzyBoolean fastMatch(FastMatchInfo type) {
      throw new RuntimeException("unimplemented");
   }

   protected FuzzyBoolean matchInternal(Shadow shadow) {
      throw new RuntimeException("unimplemented");
   }

   public void resolveBindings(IScope scope, Bindings bindings) {
   }

   protected Test findResidueInternal(Shadow shadow, ExposedState state) {
      throw new RuntimeException("unimplemented");
   }

   public PerClause concretize(ResolvedType inAspect) {
      PerClause p = this.lookupConcretePerClause(inAspect.getSuperclass());
      if (p == null) {
         inAspect.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("missingPerClause", inAspect.getSuperclass()), this.getSourceLocation()));
         return (new PerSingleton()).concretize(inAspect);
      } else {
         if (p.getKind() != this.kind) {
            inAspect.getWorld().getMessageHandler().handleMessage(MessageUtil.error(WeaverMessages.format("wrongPerClause", this.kind, p.getKind()), this.getSourceLocation()));
         }

         return p.concretize(inAspect);
      }
   }

   public Pointcut parameterizeWith(Map typeVariableMap, World w) {
      return this;
   }

   public PerClause lookupConcretePerClause(ResolvedType lookupType) {
      PerClause ret = lookupType.getPerClause();
      if (ret == null) {
         return null;
      } else {
         return ret instanceof PerFromSuper ? this.lookupConcretePerClause(lookupType.getSuperclass()) : ret;
      }
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      FROMSUPER.write(s);
      this.kind.write(s);
      this.writeLocation(s);
   }

   public static PerClause readPerClause(VersionedDataInputStream s, ISourceContext context) throws IOException {
      PerFromSuper ret = new PerFromSuper(PerClause.Kind.read(s));
      ret.readLocation(context, s);
      return ret;
   }

   public String toString() {
      return "perFromSuper(" + this.kind + ", " + this.inAspect + ")";
   }

   public String toDeclarationString() {
      return "";
   }

   public PerClause.Kind getKind() {
      return this.kind;
   }

   public boolean equals(Object other) {
      if (!(other instanceof PerFromSuper)) {
         return false;
      } else {
         boolean var10000;
         label31: {
            PerFromSuper pc = (PerFromSuper)other;
            if (pc.kind.equals(this.kind)) {
               if (pc.inAspect == null) {
                  if (this.inAspect == null) {
                     break label31;
                  }
               } else if (pc.inAspect.equals(this.inAspect)) {
                  break label31;
               }
            }

            var10000 = false;
            return var10000;
         }

         var10000 = true;
         return var10000;
      }
   }

   public int hashCode() {
      int result = 17;
      result = 37 * result + this.kind.hashCode();
      result = 37 * result + (this.inAspect == null ? 0 : this.inAspect.hashCode());
      return result;
   }
}
