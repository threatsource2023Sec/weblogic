package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import java.io.IOException;

public class TypeVariablePatternList extends PatternNode {
   public static final TypeVariablePatternList EMPTY = new TypeVariablePatternList(new TypeVariablePattern[0]);
   private TypeVariablePattern[] patterns;

   public TypeVariablePatternList(TypeVariablePattern[] typeVars) {
      this.patterns = typeVars;
   }

   public TypeVariablePattern[] getTypeVariablePatterns() {
      return this.patterns;
   }

   public TypeVariablePattern lookupTypeVariable(String name) {
      for(int i = 0; i < this.patterns.length; ++i) {
         if (this.patterns[i].getName().equals(name)) {
            return this.patterns[i];
         }
      }

      return null;
   }

   public boolean isEmpty() {
      return this.patterns == null || this.patterns.length == 0;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeInt(this.patterns.length);

      for(int i = 0; i < this.patterns.length; ++i) {
         this.patterns[i].write(s);
      }

      this.writeLocation(s);
   }

   public static TypeVariablePatternList read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      TypeVariablePatternList ret = EMPTY;
      int length = s.readInt();
      if (length > 0) {
         TypeVariablePattern[] patterns = new TypeVariablePattern[length];

         for(int i = 0; i < patterns.length; ++i) {
            patterns[i] = TypeVariablePattern.read(s, context);
         }

         ret = new TypeVariablePatternList(patterns);
      }

      ret.readLocation(context, s);
      return ret;
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      Object ret = this.accept(visitor, data);

      for(int i = 0; i < this.patterns.length; ++i) {
         this.patterns[i].traverse(visitor, ret);
      }

      return ret;
   }
}
