package com.bea.core.repackaged.jdt.internal.compiler.lookup;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract class ConstraintFormula extends ReductionResult {
   static final List EMPTY_VARIABLE_LIST = Collections.emptyList();
   static final ConstraintFormula[] NO_CONSTRAINTS = new ConstraintTypeFormula[0];
   static final char LEFT_ANGLE_BRACKET = '⟨';
   static final char RIGHT_ANGLE_BRACKET = '⟩';

   public abstract Object reduce(InferenceContext18 var1) throws InferenceFailureException;

   Collection inputVariables(InferenceContext18 context) {
      return EMPTY_VARIABLE_LIST;
   }

   Collection outputVariables(InferenceContext18 context) {
      Set variables = new HashSet();
      this.right.collectInferenceVariables(variables);
      if (!variables.isEmpty()) {
         variables.removeAll(this.inputVariables(context));
      }

      return variables;
   }

   public boolean applySubstitution(BoundSet solutionSet, InferenceVariable[] variables) {
      for(int i = 0; i < variables.length; ++i) {
         InferenceVariable variable = variables[i];
         TypeBinding instantiation = solutionSet.getInstantiation(variables[i], (LookupEnvironment)null);
         if (instantiation == null) {
            return false;
         }

         this.right = this.right.substituteInferenceVariable(variable, instantiation);
      }

      return true;
   }

   protected void appendTypeName(StringBuffer buf, TypeBinding type) {
      if (type instanceof CaptureBinding18) {
         buf.append(type.toString());
      } else {
         buf.append(type.readableName());
      }

   }
}
