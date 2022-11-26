package org.hibernate.validator.internal.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TypeVariableBindings {
   private TypeVariableBindings() {
   }

   public static Map getTypeVariableBindings(Class type) {
      Map allBindings = new HashMap();
      Map currentBindings = new HashMap();
      TypeVariable[] subTypeParameters = type.getTypeParameters();
      TypeVariable[] var4 = subTypeParameters;
      int var5 = subTypeParameters.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         TypeVariable typeVariable = var4[var6];
         currentBindings.put(typeVariable, typeVariable);
      }

      allBindings.put(type, currentBindings);
      collectTypeBindings(type, allBindings, currentBindings);
      allBindings.put(Object.class, Collections.emptyMap());
      return CollectionHelper.toImmutableMap(allBindings);
   }

   private static void collectTypeBindings(Class subType, Map allBindings, Map bindings) {
      processGenericSuperType(allBindings, bindings, subType.getGenericSuperclass());
      Type[] var3 = subType.getGenericInterfaces();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Type genericInterface = var3[var5];
         processGenericSuperType(allBindings, bindings, genericInterface);
      }

   }

   private static void processGenericSuperType(Map allBindings, Map bindings, Type genericSuperType) {
      if (genericSuperType != null) {
         if (genericSuperType instanceof ParameterizedType) {
            Map newBindings = new HashMap();
            Type[] typeArguments = ((ParameterizedType)genericSuperType).getActualTypeArguments();
            TypeVariable[] typeParameters = ((Class)((ParameterizedType)genericSuperType).getRawType()).getTypeParameters();

            for(int i = 0; i < typeArguments.length; ++i) {
               Type typeArgument = typeArguments[i];
               TypeVariable typeParameter = typeParameters[i];
               boolean typeParameterFoundInSubType = false;
               Iterator var10 = bindings.entrySet().iterator();

               while(var10.hasNext()) {
                  Map.Entry subTypeParameter = (Map.Entry)var10.next();
                  if (typeArgument.equals(subTypeParameter.getValue())) {
                     newBindings.put(subTypeParameter.getKey(), typeParameter);
                     typeParameterFoundInSubType = true;
                  }
               }

               if (!typeParameterFoundInSubType) {
                  newBindings.put(typeParameter, typeParameter);
               }
            }

            allBindings.put((Class)((ParameterizedType)genericSuperType).getRawType(), newBindings);
            collectTypeBindings((Class)((ParameterizedType)genericSuperType).getRawType(), allBindings, newBindings);
         } else {
            if (!(genericSuperType instanceof Class)) {
               throw new IllegalArgumentException("Unexpected type: " + genericSuperType);
            }

            allBindings.put((Class)genericSuperType, Collections.emptyMap());
            collectTypeBindings((Class)genericSuperType, allBindings, new HashMap());
         }

      }
   }
}
