package com.bea.core.repackaged.jdt.internal.compiler.lookup;

public class SyntheticFactoryMethodBinding extends MethodBinding {
   private MethodBinding staticFactoryFor;
   private LookupEnvironment environment;
   private ReferenceBinding enclosingType;

   public SyntheticFactoryMethodBinding(MethodBinding method, LookupEnvironment environment, ReferenceBinding enclosingType) {
      super(method.modifiers | 8, TypeConstants.SYNTHETIC_STATIC_FACTORY, (TypeBinding)null, (TypeBinding[])null, (ReferenceBinding[])null, method.declaringClass);
      this.environment = environment;
      this.staticFactoryFor = method;
      this.enclosingType = enclosingType;
   }

   public MethodBinding getConstructor() {
      return this.staticFactoryFor;
   }

   public ParameterizedMethodBinding applyTypeArgumentsOnConstructor(TypeBinding[] typeArguments, TypeBinding[] constructorTypeArguments, boolean inferredWithUncheckedConversion, TypeBinding targetType) {
      ReferenceBinding parameterizedType = typeArguments == null ? this.environment.createRawType(this.declaringClass, this.enclosingType) : this.environment.createParameterizedType(this.declaringClass, typeArguments, this.enclosingType);
      MethodBinding[] var9;
      int var8 = (var9 = ((ReferenceBinding)parameterizedType).methods()).length;

      for(int var7 = 0; var7 < var8; ++var7) {
         MethodBinding parameterizedMethod = var9[var7];
         if (parameterizedMethod.original() == this.staticFactoryFor) {
            return (ParameterizedMethodBinding)(constructorTypeArguments.length <= 0 && !inferredWithUncheckedConversion ? (ParameterizedMethodBinding)parameterizedMethod : this.environment.createParameterizedGenericMethod(parameterizedMethod, constructorTypeArguments, inferredWithUncheckedConversion, false, targetType));
         }

         if (parameterizedMethod instanceof ProblemMethodBinding) {
            MethodBinding closestMatch = ((ProblemMethodBinding)parameterizedMethod).closestMatch;
            if (closestMatch instanceof ParameterizedMethodBinding && closestMatch.original() == this.staticFactoryFor) {
               return (ParameterizedMethodBinding)closestMatch;
            }
         }
      }

      throw new IllegalArgumentException("Type doesn't have its own method?");
   }
}
