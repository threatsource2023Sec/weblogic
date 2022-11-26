package com.bea.core.repackaged.aspectj.lang;

import com.bea.core.repackaged.aspectj.lang.reflect.SourceLocation;

public interface JoinPoint {
   String METHOD_EXECUTION = "method-execution";
   String METHOD_CALL = "method-call";
   String CONSTRUCTOR_EXECUTION = "constructor-execution";
   String CONSTRUCTOR_CALL = "constructor-call";
   String FIELD_GET = "field-get";
   String FIELD_SET = "field-set";
   String STATICINITIALIZATION = "staticinitialization";
   String PREINITIALIZATION = "preinitialization";
   String INITIALIZATION = "initialization";
   String EXCEPTION_HANDLER = "exception-handler";
   String SYNCHRONIZATION_LOCK = "lock";
   String SYNCHRONIZATION_UNLOCK = "unlock";
   String ADVICE_EXECUTION = "adviceexecution";

   String toString();

   String toShortString();

   String toLongString();

   Object getThis();

   Object getTarget();

   Object[] getArgs();

   Signature getSignature();

   SourceLocation getSourceLocation();

   String getKind();

   StaticPart getStaticPart();

   public interface EnclosingStaticPart extends StaticPart {
   }

   public interface StaticPart {
      Signature getSignature();

      SourceLocation getSourceLocation();

      String getKind();

      int getId();

      String toString();

      String toShortString();

      String toLongString();
   }
}
