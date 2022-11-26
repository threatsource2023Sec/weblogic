package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.util.Map;

public class TypeCategoryTypePattern extends TypePattern {
   public static final int CLASS = 1;
   public static final int INTERFACE = 2;
   public static final int ASPECT = 3;
   public static final int INNER = 4;
   public static final int ANONYMOUS = 5;
   public static final int ENUM = 6;
   public static final int ANNOTATION = 7;
   public static final int FINAL = 8;
   private int category;
   private int VERSION = 1;

   public TypeCategoryTypePattern(int category) {
      super(false);
      this.category = category;
   }

   public int getTypeCategory() {
      return this.category;
   }

   protected boolean matchesExactly(ResolvedType type) {
      return this.isRightCategory(type);
   }

   protected boolean matchesExactly(ResolvedType type, ResolvedType annotatedType) {
      return this.isRightCategory(type);
   }

   public FuzzyBoolean matchesInstanceof(ResolvedType type) {
      return FuzzyBoolean.fromBoolean(this.isRightCategory(type));
   }

   public TypePattern parameterizeWith(Map typeVariableMap, World w) {
      return this;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public boolean equals(Object other) {
      if (!(other instanceof TypeCategoryTypePattern)) {
         return false;
      } else {
         TypeCategoryTypePattern o = (TypeCategoryTypePattern)other;
         return o.category == this.category;
      }
   }

   public int hashCode() {
      return this.category * 37;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeByte(12);
      s.writeInt(this.VERSION);
      s.writeInt(this.category);
      this.writeLocation(s);
   }

   public static TypePattern read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      int version = s.readInt();
      int category = s.readInt();
      TypeCategoryTypePattern tp = new TypeCategoryTypePattern(category);
      tp.readLocation(context, s);
      return tp;
   }

   private boolean isRightCategory(ResolvedType type) {
      switch (this.category) {
         case 1:
            return type.isClass();
         case 2:
            return type.isInterface();
         case 3:
            return type.isAspect();
         case 4:
            return type.isNested();
         case 5:
            return type.isAnonymous();
         case 6:
            return type.isEnum();
         case 7:
            return type.isAnnotation();
         case 8:
            return Modifier.isFinal(type.getModifiers());
         default:
            return false;
      }
   }
}
