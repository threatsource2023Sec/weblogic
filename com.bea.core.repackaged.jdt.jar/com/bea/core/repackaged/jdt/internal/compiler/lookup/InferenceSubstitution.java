package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class InferenceSubstitution extends Scope.Substitutor implements Substitution {
   private LookupEnvironment environment;
   private InferenceVariable[] variables;
   private InvocationSite[] sites;

   public InferenceSubstitution(LookupEnvironment environment, InferenceVariable[] variables, InvocationSite site) {
      this.environment = environment;
      this.variables = variables;
      this.sites = new InvocationSite[]{site};
   }

   public InferenceSubstitution(InferenceContext18 context) {
      this(context.environment, context.inferenceVariables, context.currentInvocation);
   }

   public InferenceSubstitution addContext(InferenceContext18 otherContext) {
      InferenceSubstitution subst = new InferenceSubstitution(this.environment, (InferenceVariable[])null, (InvocationSite)null) {
         protected boolean isSameParameter(TypeBinding p1, TypeBinding originalType) {
            if (TypeBinding.equalsEquals(p1, originalType)) {
               return true;
            } else if (p1 instanceof TypeVariableBinding && originalType instanceof TypeVariableBinding) {
               TypeVariableBinding var1 = (TypeVariableBinding)p1;
               TypeVariableBinding var2 = (TypeVariableBinding)originalType;
               Binding declaring1 = var1.declaringElement;
               Binding declaring2 = var2.declaringElement;
               if (declaring1 instanceof MethodBinding && declaring2 instanceof MethodBinding) {
                  declaring1 = ((MethodBinding)declaring1).original();
                  declaring2 = ((MethodBinding)declaring2).original();
               }

               return declaring1 == declaring2 && var1.rank == var2.rank;
            } else {
               return false;
            }
         }
      };
      int l1 = this.sites.length;
      subst.sites = new InvocationSite[l1 + 1];
      System.arraycopy(this.sites, 0, subst.sites, 0, l1);
      subst.sites[l1] = otherContext.currentInvocation;
      subst.variables = this.variables;
      return subst;
   }

   public TypeBinding substitute(Substitution substitution, TypeBinding originalType) {
      for(int i = 0; i < this.variables.length; ++i) {
         InferenceVariable variable = this.variables[i];
         if (variable.isFromInitialSubstitution && this.isInSites(variable.site) && this.isSameParameter(this.getP(i), originalType)) {
            if (this.environment.globalOptions.isAnnotationBasedNullAnalysisEnabled && originalType.hasNullTypeAnnotations()) {
               return this.environment.createAnnotatedType(variable.withoutToplevelNullAnnotation(), originalType.getTypeAnnotations());
            }

            return variable;
         }
      }

      return super.substitute(substitution, originalType);
   }

   private boolean isInSites(InvocationSite otherSite) {
      for(int i = 0; i < this.sites.length; ++i) {
         if (InferenceContext18.isSameSite(this.sites[i], otherSite)) {
            return true;
         }
      }

      return false;
   }

   protected boolean isSameParameter(TypeBinding p1, TypeBinding originalType) {
      return TypeBinding.equalsEquals(p1, originalType);
   }

   protected TypeBinding getP(int i) {
      return this.variables[i].typeParameter;
   }

   public TypeBinding substitute(TypeVariableBinding typeVariable) {
      ReferenceBinding superclass = typeVariable.superclass;
      ReferenceBinding[] superInterfaces = typeVariable.superInterfaces;
      boolean hasSubstituted = false;

      for(int i = 0; i < this.variables.length; ++i) {
         InferenceVariable variable = this.variables[i];
         TypeBinding pi = this.getP(i);
         if (TypeBinding.equalsEquals(pi, typeVariable)) {
            return variable;
         }

         if (TypeBinding.equalsEquals(pi, (TypeBinding)superclass)) {
            superclass = variable;
            hasSubstituted = true;
         } else if (superInterfaces != null) {
            int ifcLen = superInterfaces.length;

            for(int j = 0; j < ifcLen; ++j) {
               if (TypeBinding.equalsEquals(pi, superInterfaces[j])) {
                  if (superInterfaces == typeVariable.superInterfaces) {
                     System.arraycopy(superInterfaces, 0, superInterfaces = new ReferenceBinding[ifcLen], 0, ifcLen);
                  }

                  superInterfaces[j] = variable;
                  hasSubstituted = true;
                  break;
               }
            }
         }
      }

      if (hasSubstituted) {
         typeVariable = new TypeVariableBinding(typeVariable.sourceName, typeVariable.declaringElement, typeVariable.rank, this.environment);
         typeVariable.superclass = (ReferenceBinding)superclass;
         typeVariable.superInterfaces = superInterfaces;
         typeVariable.firstBound = (TypeBinding)(superclass != null ? superclass : superInterfaces[0]);
         if (typeVariable.firstBound.hasNullTypeAnnotations()) {
            typeVariable.tagBits |= 1048576L;
         }
      }

      return typeVariable;
   }

   public LookupEnvironment environment() {
      return this.environment;
   }

   public boolean isRawSubstitution() {
      return false;
   }
}
