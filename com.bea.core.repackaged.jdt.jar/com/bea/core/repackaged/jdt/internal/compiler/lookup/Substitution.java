package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public interface Substitution {
   TypeBinding substitute(TypeVariableBinding var1);

   LookupEnvironment environment();

   boolean isRawSubstitution();

   public static class NullSubstitution implements Substitution {
      LookupEnvironment environment;

      public NullSubstitution(LookupEnvironment environment) {
         this.environment = environment;
      }

      public TypeBinding substitute(TypeVariableBinding typeVariable) {
         return typeVariable;
      }

      public boolean isRawSubstitution() {
         return false;
      }

      public LookupEnvironment environment() {
         return this.environment;
      }
   }
}
