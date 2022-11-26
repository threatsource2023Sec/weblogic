package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.internal.compiler.lookup.InferenceVariable;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.MethodBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import java.util.Arrays;
import java.util.Comparator;

public class Sorting {
   public static ReferenceBinding[] sortTypes(ReferenceBinding[] types) {
      int len = types.length;
      ReferenceBinding[] unsorted = new ReferenceBinding[len];
      ReferenceBinding[] sorted = new ReferenceBinding[len];
      System.arraycopy(types, 0, unsorted, 0, len);
      int o = 0;

      for(int i = 0; i < len; ++i) {
         o = sort(unsorted, i, sorted, o);
      }

      return sorted;
   }

   private static int sort(ReferenceBinding[] input, int i, ReferenceBinding[] output, int o) {
      if (input[i] == null) {
         return o;
      } else {
         ReferenceBinding superclass = input[i].superclass();
         o = sortSuper(superclass, input, output, o);
         ReferenceBinding[] superInterfaces = input[i].superInterfaces();

         for(int j = 0; j < superInterfaces.length; ++j) {
            o = sortSuper(superInterfaces[j], input, output, o);
         }

         output[o++] = input[i];
         input[i] = null;
         return o;
      }
   }

   private static int sortSuper(ReferenceBinding superclass, ReferenceBinding[] input, ReferenceBinding[] output, int o) {
      if (superclass.id != 1) {
         int j = false;

         int j;
         for(j = 0; j < input.length && !TypeBinding.equalsEquals(input[j], superclass); ++j) {
         }

         if (j < input.length) {
            o = sort(input, j, output, o);
         }
      }

      return o;
   }

   public static MethodBinding[] concreteFirst(MethodBinding[] methods, int length) {
      if (length != 0 && (length <= 0 || methods[0].isAbstract())) {
         MethodBinding[] copy = new MethodBinding[length];
         int idx = 0;

         int i;
         for(i = 0; i < length; ++i) {
            if (!methods[i].isAbstract()) {
               copy[idx++] = methods[i];
            }
         }

         for(i = 0; i < length; ++i) {
            if (methods[i].isAbstract()) {
               copy[idx++] = methods[i];
            }
         }

         return copy;
      } else {
         return methods;
      }
   }

   public static MethodBinding[] abstractFirst(MethodBinding[] methods, int length) {
      if (length != 0 && (length <= 0 || !methods[0].isAbstract())) {
         MethodBinding[] copy = new MethodBinding[length];
         int idx = 0;

         int i;
         for(i = 0; i < length; ++i) {
            if (methods[i].isAbstract()) {
               copy[idx++] = methods[i];
            }
         }

         for(i = 0; i < length; ++i) {
            if (!methods[i].isAbstract()) {
               copy[idx++] = methods[i];
            }
         }

         return copy;
      } else {
         return methods;
      }
   }

   public static void sortInferenceVariables(InferenceVariable[] variables) {
      Arrays.sort(variables, new Comparator() {
         public int compare(InferenceVariable iv1, InferenceVariable iv2) {
            return iv1.rank - iv2.rank;
         }
      });
   }
}
