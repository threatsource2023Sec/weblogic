package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.FuzzyBoolean;
import com.bea.core.repackaged.aspectj.weaver.CompressingDataOutputStream;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import com.bea.core.repackaged.aspectj.weaver.World;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AnnotationPatternList extends PatternNode {
   private AnnotationTypePattern[] typePatterns;
   int ellipsisCount;
   public static final AnnotationPatternList EMPTY = new AnnotationPatternList(new AnnotationTypePattern[0]);
   public static final AnnotationPatternList ANY;

   public AnnotationPatternList() {
      this.ellipsisCount = 0;
      this.typePatterns = new AnnotationTypePattern[0];
      this.ellipsisCount = 0;
   }

   public AnnotationPatternList(AnnotationTypePattern[] arguments) {
      this.ellipsisCount = 0;
      this.typePatterns = arguments;

      for(int i = 0; i < arguments.length; ++i) {
         if (arguments[i] == AnnotationTypePattern.ELLIPSIS) {
            ++this.ellipsisCount;
         }
      }

   }

   public AnnotationPatternList(List l) {
      this((AnnotationTypePattern[])((AnnotationTypePattern[])l.toArray(new AnnotationTypePattern[l.size()])));
   }

   protected AnnotationTypePattern[] getAnnotationPatterns() {
      return this.typePatterns;
   }

   public AnnotationPatternList parameterizeWith(Map typeVariableMap, World w) {
      AnnotationTypePattern[] parameterizedPatterns = new AnnotationTypePattern[this.typePatterns.length];

      for(int i = 0; i < parameterizedPatterns.length; ++i) {
         parameterizedPatterns[i] = this.typePatterns[i].parameterizeWith(typeVariableMap, w);
      }

      AnnotationPatternList ret = new AnnotationPatternList(parameterizedPatterns);
      ret.copyLocationFrom(this);
      return ret;
   }

   public void resolve(World inWorld) {
      for(int i = 0; i < this.typePatterns.length; ++i) {
         this.typePatterns[i].resolve(inWorld);
      }

   }

   public FuzzyBoolean matches(ResolvedType[] someArgs) {
      int numArgsMatchedByEllipsis = someArgs.length + this.ellipsisCount - this.typePatterns.length;
      if (numArgsMatchedByEllipsis < 0) {
         return FuzzyBoolean.NO;
      } else if (numArgsMatchedByEllipsis > 0 && this.ellipsisCount == 0) {
         return FuzzyBoolean.NO;
      } else {
         FuzzyBoolean ret = FuzzyBoolean.YES;
         int argsIndex = 0;

         for(int i = 0; i < this.typePatterns.length; ++i) {
            if (this.typePatterns[i] == AnnotationTypePattern.ELLIPSIS) {
               argsIndex += numArgsMatchedByEllipsis;
            } else if (this.typePatterns[i] == AnnotationTypePattern.ANY) {
               ++argsIndex;
            } else {
               if (someArgs[argsIndex].isPrimitiveType()) {
                  return FuzzyBoolean.NO;
               }

               ExactAnnotationTypePattern ap = (ExactAnnotationTypePattern)this.typePatterns[i];
               FuzzyBoolean matches = ap.matchesRuntimeType(someArgs[argsIndex]);
               if (matches == FuzzyBoolean.NO) {
                  return FuzzyBoolean.MAYBE;
               }

               ++argsIndex;
               ret = ret.and(matches);
            }
         }

         return ret;
      }
   }

   public int size() {
      return this.typePatterns.length;
   }

   public AnnotationTypePattern get(int index) {
      return this.typePatterns[index];
   }

   public AnnotationPatternList resolveBindings(IScope scope, Bindings bindings, boolean allowBinding) {
      for(int i = 0; i < this.typePatterns.length; ++i) {
         AnnotationTypePattern p = this.typePatterns[i];
         if (p != null) {
            this.typePatterns[i] = this.typePatterns[i].resolveBindings(scope, bindings, allowBinding);
         }
      }

      return this;
   }

   public AnnotationPatternList resolveReferences(IntMap bindings) {
      int len = this.typePatterns.length;
      AnnotationTypePattern[] ret = new AnnotationTypePattern[len];

      for(int i = 0; i < len; ++i) {
         ret[i] = this.typePatterns[i].remapAdviceFormals(bindings);
      }

      return new AnnotationPatternList(ret);
   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("(");
      int i = 0;

      for(int len = this.typePatterns.length; i < len; ++i) {
         AnnotationTypePattern type = this.typePatterns[i];
         if (i > 0) {
            buf.append(", ");
         }

         if (type == AnnotationTypePattern.ELLIPSIS) {
            buf.append("..");
         } else {
            String annPatt = type.toString();
            buf.append(annPatt.startsWith("@") ? annPatt.substring(1) : annPatt);
         }
      }

      buf.append(")");
      return buf.toString();
   }

   public boolean equals(Object other) {
      if (!(other instanceof AnnotationPatternList)) {
         return false;
      } else {
         AnnotationPatternList o = (AnnotationPatternList)other;
         int len = o.typePatterns.length;
         if (len != this.typePatterns.length) {
            return false;
         } else {
            for(int i = 0; i < len; ++i) {
               if (!this.typePatterns[i].equals(o.typePatterns[i])) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public int hashCode() {
      int result = 41;
      int i = 0;

      for(int len = this.typePatterns.length; i < len; ++i) {
         result = 37 * result + this.typePatterns[i].hashCode();
      }

      return result;
   }

   public static AnnotationPatternList read(VersionedDataInputStream s, ISourceContext context) throws IOException {
      short len = s.readShort();
      AnnotationTypePattern[] arguments = new AnnotationTypePattern[len];

      for(int i = 0; i < len; ++i) {
         arguments[i] = AnnotationTypePattern.read(s, context);
      }

      AnnotationPatternList ret = new AnnotationPatternList(arguments);
      ret.readLocation(context, s);
      return ret;
   }

   public void write(CompressingDataOutputStream s) throws IOException {
      s.writeShort(this.typePatterns.length);

      for(int i = 0; i < this.typePatterns.length; ++i) {
         this.typePatterns[i].write(s);
      }

      this.writeLocation(s);
   }

   public Object accept(PatternNodeVisitor visitor, Object data) {
      return visitor.visit(this, data);
   }

   public Object traverse(PatternNodeVisitor visitor, Object data) {
      Object ret = this.accept(visitor, data);

      for(int i = 0; i < this.typePatterns.length; ++i) {
         this.typePatterns[i].traverse(visitor, ret);
      }

      return ret;
   }

   static {
      ANY = new AnnotationPatternList(new AnnotationTypePattern[]{AnnotationTypePattern.ELLIPSIS});
   }
}
