package com.bea.core.repackaged.jdt.internal.compiler.classfmt;

import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryElementValuePair;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryMethod;
import com.bea.core.repackaged.jdt.internal.compiler.env.IBinaryTypeAnnotation;
import com.bea.core.repackaged.jdt.internal.compiler.util.Util;

public class BinaryTypeFormatter {
   public static String annotationToString(IBinaryAnnotation annotation) {
      StringBuffer buffer = new StringBuffer();
      buffer.append('@');
      buffer.append(annotation.getTypeName());
      IBinaryElementValuePair[] valuePairs = annotation.getElementValuePairs();
      if (valuePairs != null) {
         buffer.append('(');
         buffer.append("\n\t");
         int i = 0;

         for(int len = valuePairs.length; i < len; ++i) {
            if (i > 0) {
               buffer.append(",\n\t");
            }

            buffer.append(valuePairs[i]);
         }

         buffer.append(')');
      }

      return buffer.toString();
   }

   public static String annotationToString(IBinaryTypeAnnotation typeAnnotation) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(typeAnnotation.getAnnotation());
      buffer.append(' ');
      buffer.append("target_type=").append(typeAnnotation.getTargetType());
      buffer.append(", info=").append(typeAnnotation.getSupertypeIndex());
      buffer.append(", info2=").append(typeAnnotation.getBoundIndex());
      int[] theTypePath = typeAnnotation.getTypePath();
      if (theTypePath != null && theTypePath.length != 0) {
         buffer.append(", location=[");
         int i = 0;

         for(int max = theTypePath.length; i < max; i += 2) {
            if (i > 0) {
               buffer.append(", ");
            }

            switch (theTypePath[i]) {
               case 0:
                  buffer.append("ARRAY");
                  break;
               case 1:
                  buffer.append("INNER_TYPE");
                  break;
               case 2:
                  buffer.append("WILDCARD");
                  break;
               case 3:
                  buffer.append("TYPE_ARGUMENT(").append(theTypePath[i + 1]).append(')');
            }
         }

         buffer.append(']');
      }

      return buffer.toString();
   }

   public static String methodToString(IBinaryMethod method) {
      StringBuffer result = new StringBuffer();
      methodToStringContent(result, method);
      return result.toString();
   }

   public static void methodToStringContent(StringBuffer buffer, IBinaryMethod method) {
      int modifiers = method.getModifiers();
      char[] desc = method.getGenericSignature();
      if (desc == null) {
         desc = method.getMethodDescriptor();
      }

      buffer.append('{').append(((modifiers & 1048576) != 0 ? "deprecated " : Util.EMPTY_STRING) + ((modifiers & 1) == 1 ? "public " : Util.EMPTY_STRING) + ((modifiers & 2) == 2 ? "private " : Util.EMPTY_STRING) + ((modifiers & 4) == 4 ? "protected " : Util.EMPTY_STRING) + ((modifiers & 8) == 8 ? "static " : Util.EMPTY_STRING) + ((modifiers & 16) == 16 ? "final " : Util.EMPTY_STRING) + ((modifiers & 64) == 64 ? "bridge " : Util.EMPTY_STRING) + ((modifiers & 128) == 128 ? "varargs " : Util.EMPTY_STRING)).append(method.getSelector()).append(desc).append('}');
      Object defaultValue = method.getDefaultValue();
      int i;
      int i;
      if (defaultValue != null) {
         buffer.append(" default ");
         if (!(defaultValue instanceof Object[])) {
            buffer.append(defaultValue);
         } else {
            buffer.append('{');
            Object[] elements = (Object[])defaultValue;
            i = 0;

            for(i = elements.length; i < i; ++i) {
               if (i > 0) {
                  buffer.append(", ");
               }

               buffer.append(elements[i]);
            }

            buffer.append('}');
         }

         buffer.append('\n');
      }

      IBinaryAnnotation[] annotations = method.getAnnotations();
      i = 0;

      for(i = annotations == null ? 0 : annotations.length; i < i; ++i) {
         buffer.append(annotations[i]);
         buffer.append('\n');
      }

      i = method.getAnnotatedParametersCount();

      for(i = 0; i < i; ++i) {
         buffer.append("param" + (i - 1));
         buffer.append('\n');
         IBinaryAnnotation[] infos = method.getParameterAnnotations(i, new char[0]);
         int j = 0;

         for(int k = infos == null ? 0 : infos.length; j < k; ++j) {
            buffer.append(infos[j]);
            buffer.append('\n');
         }
      }

   }
}
