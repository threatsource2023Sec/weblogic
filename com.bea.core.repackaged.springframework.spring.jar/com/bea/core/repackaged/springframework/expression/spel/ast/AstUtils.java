package com.bea.core.repackaged.springframework.expression.spel.ast;

import com.bea.core.repackaged.springframework.expression.PropertyAccessor;
import com.bea.core.repackaged.springframework.lang.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AstUtils {
   public static List getPropertyAccessorsToTry(@Nullable Class targetType, List propertyAccessors) {
      List specificAccessors = new ArrayList();
      List generalAccessors = new ArrayList();
      Iterator var4 = propertyAccessors.iterator();

      while(true) {
         while(var4.hasNext()) {
            PropertyAccessor resolver = (PropertyAccessor)var4.next();
            Class[] targets = resolver.getSpecificTargetClasses();
            if (targets == null) {
               generalAccessors.add(resolver);
            } else if (targetType != null) {
               int pos = 0;
               Class[] var8 = targets;
               int var9 = targets.length;

               for(int var10 = 0; var10 < var9; ++var10) {
                  Class clazz = var8[var10];
                  if (clazz == targetType) {
                     specificAccessors.add(pos++, resolver);
                  } else if (clazz.isAssignableFrom(targetType)) {
                     generalAccessors.add(resolver);
                  }
               }
            }
         }

         List resolvers = new ArrayList(specificAccessors.size() + generalAccessors.size());
         resolvers.addAll(specificAccessors);
         resolvers.addAll(generalAccessors);
         return resolvers;
      }
   }
}
