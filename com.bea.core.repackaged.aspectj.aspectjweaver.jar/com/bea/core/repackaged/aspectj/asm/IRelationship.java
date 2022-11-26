package com.bea.core.repackaged.aspectj.asm;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.List;

public interface IRelationship extends Serializable {
   String getName();

   Kind getKind();

   void addTarget(String var1);

   List getTargets();

   String getSourceHandle();

   boolean hasRuntimeTest();

   boolean isAffects();

   public static class Kind implements Serializable {
      private static final long serialVersionUID = -2691351740214705220L;
      public static final Kind DECLARE_WARNING = new Kind("declare warning");
      public static final Kind DECLARE_ERROR = new Kind("declare error");
      public static final Kind ADVICE_AROUND = new Kind("around advice");
      public static final Kind ADVICE_AFTERRETURNING = new Kind("after returning advice");
      public static final Kind ADVICE_AFTERTHROWING = new Kind("after throwing advice");
      public static final Kind ADVICE_AFTER = new Kind("after advice");
      public static final Kind ADVICE_BEFORE = new Kind("before advice");
      public static final Kind ADVICE = new Kind("advice");
      public static final Kind DECLARE = new Kind("declare");
      public static final Kind DECLARE_INTER_TYPE = new Kind("inter-type declaration");
      public static final Kind USES_POINTCUT = new Kind("uses pointcut");
      public static final Kind DECLARE_SOFT = new Kind("declare soft");
      public static final Kind[] ALL;
      private final String name;
      private static int nextOrdinal;
      private final int ordinal;

      public boolean isDeclareKind() {
         return this == DECLARE_WARNING || this == DECLARE_ERROR || this == DECLARE || this == DECLARE_INTER_TYPE || this == DECLARE_SOFT;
      }

      public String getName() {
         return this.name;
      }

      public static Kind getKindFor(String stringFormOfRelationshipKind) {
         for(int i = 0; i < ALL.length; ++i) {
            if (ALL[i].name.equals(stringFormOfRelationshipKind)) {
               return ALL[i];
            }
         }

         return null;
      }

      private Kind(String name) {
         this.ordinal = nextOrdinal++;
         this.name = name;
      }

      public String toString() {
         return this.name;
      }

      private Object readResolve() throws ObjectStreamException {
         return ALL[this.ordinal];
      }

      static {
         ALL = new Kind[]{DECLARE_WARNING, DECLARE_ERROR, ADVICE_AROUND, ADVICE_AFTERRETURNING, ADVICE_AFTERTHROWING, ADVICE_AFTER, ADVICE_BEFORE, ADVICE, DECLARE, DECLARE_INTER_TYPE, USES_POINTCUT, DECLARE_SOFT};
         nextOrdinal = 0;
      }
   }
}
