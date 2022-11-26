package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public abstract class Declare extends PatternNode {
   public static final byte ERROR_OR_WARNING = 1;
   public static final byte PARENTS = 2;
   public static final byte SOFT = 3;
   public static final byte DOMINATES = 4;
   public static final byte ANNOTATION = 5;
   public static final byte PARENTSMIXIN = 6;
   public static final byte TYPE_ERROR_OR_WARNING = 7;
   private ResolvedType declaringType;

   public static Declare read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      byte kind = s.readByte();
      switch (kind) {
         case 1:
            return DeclareErrorOrWarning.read(s, context);
         case 2:
            return DeclareParents.read(s, context);
         case 3:
            return DeclareSoft.read(s, context);
         case 4:
            return DeclarePrecedence.read(s, context);
         case 5:
            return DeclareAnnotation.read(s, context);
         case 6:
            return DeclareParentsMixin.read(s, context);
         case 7:
            return DeclareTypeErrorOrWarning.read(s, context);
         default:
            throw new RuntimeException("unimplemented");
      }
   }

   public abstract void resolve(IScope var1);

   public abstract Declare parameterizeWith(Map var1, World var2);

   public abstract boolean isAdviceLike();

   public abstract String getNameSuffix();

   public void setDeclaringType(ResolvedType aType) {
      this.declaringType = aType;
   }

   public ResolvedType getDeclaringType() {
      return this.declaringType;
   }
}
