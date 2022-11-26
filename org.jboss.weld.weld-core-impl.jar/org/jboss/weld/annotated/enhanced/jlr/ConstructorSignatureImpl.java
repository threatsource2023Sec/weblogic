package org.jboss.weld.annotated.enhanced.jlr;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import org.jboss.weld.annotated.enhanced.ConstructorSignature;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedConstructor;
import org.jboss.weld.annotated.enhanced.EnhancedAnnotatedParameter;

public class ConstructorSignatureImpl implements ConstructorSignature {
   public static final ConstructorSignatureImpl NO_ARGS_SIGNATURE = new ConstructorSignatureImpl(new String[0]);
   private static final long serialVersionUID = -9111642596078876778L;
   private final String[] parameterTypes;

   private ConstructorSignatureImpl(String[] parameterTypes) {
      this.parameterTypes = parameterTypes;
   }

   public ConstructorSignatureImpl(EnhancedAnnotatedConstructor method) {
      this.parameterTypes = new String[method.getEnhancedParameters().size()];

      for(int i = 0; i < method.getEnhancedParameters().size(); ++i) {
         this.parameterTypes[i] = ((EnhancedAnnotatedParameter)method.getEnhancedParameters().get(i)).getJavaClass().getName();
      }

   }

   public ConstructorSignatureImpl(Constructor constructor) {
      Class[] parameterTypes = constructor.getParameterTypes();
      this.parameterTypes = new String[parameterTypes.length];

      for(int i = 0; i < parameterTypes.length; ++i) {
         this.parameterTypes[i] = parameterTypes[i].getName();
      }

   }

   public boolean equals(Object obj) {
      if (obj instanceof ConstructorSignature) {
         ConstructorSignature that = (ConstructorSignature)obj;
         return Arrays.equals(this.getParameterTypes(), that.getParameterTypes());
      } else {
         return false;
      }
   }

   public int hashCode() {
      return Arrays.hashCode(this.parameterTypes);
   }

   public String[] getParameterTypes() {
      return (String[])Arrays.copyOf(this.parameterTypes, this.parameterTypes.length);
   }
}
