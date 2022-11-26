package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.core.convert.TypeDescriptor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import com.bea.core.repackaged.springframework.util.ClassUtils;
import java.util.List;

abstract class FormatHelper {
   public static String formatMethodForMessage(String name, List argumentTypes) {
      StringBuilder sb = new StringBuilder(name);
      sb.append("(");

      for(int i = 0; i < argumentTypes.size(); ++i) {
         if (i > 0) {
            sb.append(",");
         }

         TypeDescriptor typeDescriptor = (TypeDescriptor)argumentTypes.get(i);
         if (typeDescriptor != null) {
            sb.append(formatClassNameForMessage(typeDescriptor.getType()));
         } else {
            sb.append(formatClassNameForMessage((Class)null));
         }
      }

      sb.append(")");
      return sb.toString();
   }

   public static String formatClassNameForMessage(@Nullable Class clazz) {
      return clazz != null ? ClassUtils.getQualifiedName(clazz) : "null";
   }
}
