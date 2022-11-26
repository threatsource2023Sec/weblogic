package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.AnnotatedElement;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.Map;

public abstract class AnnotationTypePattern extends PatternNode {
   public static final AnnotationTypePattern ANY = new AnyAnnotationTypePattern();
   public static final AnnotationTypePattern ELLIPSIS = new EllipsisAnnotationTypePattern();
   public static final AnnotationTypePattern[] NONE = new AnnotationTypePattern[0];
   private boolean isForParameterAnnotationMatch;
   public static final byte EXACT = 1;
   public static final byte BINDING = 2;
   public static final byte NOT = 3;
   public static final byte OR = 4;
   public static final byte AND = 5;
   public static final byte ELLIPSIS_KEY = 6;
   public static final byte ANY_KEY = 7;
   public static final byte WILD = 8;
   public static final byte EXACTFIELD = 9;
   public static final byte BINDINGFIELD = 10;
   public static final byte BINDINGFIELD2 = 11;

   protected AnnotationTypePattern() {
   }

   public abstract FuzzyBoolean matches(AnnotatedElement var1);

   public abstract FuzzyBoolean matches(AnnotatedElement var1, ResolvedType[] var2);

   public FuzzyBoolean fastMatches(AnnotatedElement annotated) {
      return FuzzyBoolean.MAYBE;
   }

   public AnnotationTypePattern remapAdviceFormals(IntMap bindings) {
      return this;
   }

   public abstract void resolve(World var1);

   public abstract AnnotationTypePattern parameterizeWith(Map var1, World var2);

   public boolean isAny() {
      return false;
   }

   public AnnotationTypePattern resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
      return this;
   }

   public static AnnotationTypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      byte key = s.readByte();
      switch (key) {
         case 1:
            return ExactAnnotationTypePattern.read(s, context);
         case 2:
            return BindingAnnotationTypePattern.read(s, context);
         case 3:
            return NotAnnotationTypePattern.read(s, context);
         case 4:
            return OrAnnotationTypePattern.read(s, context);
         case 5:
            return AndAnnotationTypePattern.read(s, context);
         case 6:
            return ELLIPSIS;
         case 7:
            return ANY;
         case 8:
            return WildAnnotationTypePattern.read(s, context);
         case 9:
            return ExactAnnotationFieldTypePattern.read(s, context);
         case 10:
            return BindingAnnotationFieldTypePattern.read(s, context);
         case 11:
            return BindingAnnotationFieldTypePattern.read2(s, context);
         default:
            throw new BCException("unknown TypePattern kind: " + key);
      }
   }

   public void setForParameterAnnotationMatch() {
      this.isForParameterAnnotationMatch = true;
   }

   public boolean isForParameterAnnotationMatch() {
      return this.isForParameterAnnotationMatch;
   }
}
