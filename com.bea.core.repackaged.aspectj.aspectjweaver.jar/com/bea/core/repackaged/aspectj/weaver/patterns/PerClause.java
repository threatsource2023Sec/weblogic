package com.bea.core.repackaged.aspectj.weaver.patterns;

import com.bea.core.repackaged.aspectj.util.TypeSafeEnum;
import com.bea.core.repackaged.aspectj.weaver.BCException;
import com.bea.core.repackaged.aspectj.weaver.ISourceContext;
import com.bea.core.repackaged.aspectj.weaver.IntMap;
import com.bea.core.repackaged.aspectj.weaver.ResolvedType;
import com.bea.core.repackaged.aspectj.weaver.VersionedDataInputStream;
import java.io.IOException;

public abstract class PerClause extends Pointcut {
   protected ResolvedType inAspect;
   public static final Kind SINGLETON = new Kind("issingleton", 1);
   public static final Kind PERCFLOW = new Kind("percflow", 2);
   public static final Kind PEROBJECT = new Kind("perobject", 3);
   public static final Kind FROMSUPER = new Kind("fromsuper", 4);
   public static final Kind PERTYPEWITHIN = new Kind("pertypewithin", 5);

   public static PerClause readPerClause(VersionedDataInputStream s, ISourceContext context) throws IOException {
      Kind kind = PerClause.Kind.read(s);
      if (kind == SINGLETON) {
         return PerSingleton.readPerClause(s, context);
      } else if (kind == PERCFLOW) {
         return PerCflow.readPerClause(s, context);
      } else if (kind == PEROBJECT) {
         return PerObject.readPerClause(s, context);
      } else if (kind == FROMSUPER) {
         return PerFromSuper.readPerClause(s, context);
      } else if (kind == PERTYPEWITHIN) {
         return PerTypeWithin.readPerClause(s, context);
      } else {
         throw new BCException("unknown kind: " + kind);
      }
   }

   public final Pointcut concretize1(ResolvedType inAspect, ResolvedType declaringType, IntMap bindings) {
      throw new RuntimeException("unimplemented: wrong concretize");
   }

   public abstract PerClause concretize(ResolvedType var1);

   public abstract Kind getKind();

   public abstract String toDeclarationString();

   public static class KindAnnotationPrefix extends TypeSafeEnum {
      public static final KindAnnotationPrefix PERCFLOW = new KindAnnotationPrefix("percflow(", 1);
      public static final KindAnnotationPrefix PERCFLOWBELOW = new KindAnnotationPrefix("percflowbelow(", 2);
      public static final KindAnnotationPrefix PERTHIS = new KindAnnotationPrefix("perthis(", 3);
      public static final KindAnnotationPrefix PERTARGET = new KindAnnotationPrefix("pertarget(", 4);
      public static final KindAnnotationPrefix PERTYPEWITHIN = new KindAnnotationPrefix("pertypewithin(", 5);

      private KindAnnotationPrefix(String name, int key) {
         super(name, key);
      }

      public String extractPointcut(String perClause) {
         int from = this.getName().length();
         int to = perClause.length() - 1;
         if (perClause.startsWith(this.getName()) && perClause.endsWith(")") && from <= perClause.length()) {
            return perClause.substring(from, to);
         } else {
            throw new RuntimeException("cannot read perclause " + perClause);
         }
      }
   }

   public static class Kind extends TypeSafeEnum {
      public Kind(String name, int key) {
         super(name, key);
      }

      public static Kind read(VersionedDataInputStream s) throws IOException {
         int key = s.readByte();
         switch (key) {
            case 1:
               return PerClause.SINGLETON;
            case 2:
               return PerClause.PERCFLOW;
            case 3:
               return PerClause.PEROBJECT;
            case 4:
               return PerClause.FROMSUPER;
            case 5:
               return PerClause.PERTYPEWITHIN;
            default:
               throw new BCException("weird kind " + key);
         }
      }
   }
}
