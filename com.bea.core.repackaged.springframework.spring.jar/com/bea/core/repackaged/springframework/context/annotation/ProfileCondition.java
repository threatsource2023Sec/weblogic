package com.bea.core.repackaged.springframework.context.annotation;

import com.bea.core.repackaged.springframework.core.env.Profiles;
import com.bea.core.repackaged.springframework.core.type.AnnotatedTypeMetadata;
import com.bea.core.repackaged.springframework.util.MultiValueMap;
import java.util.Iterator;
import java.util.List;

class ProfileCondition implements Condition {
   public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
      MultiValueMap attrs = metadata.getAllAnnotationAttributes(Profile.class.getName());
      if (attrs != null) {
         Iterator var4 = ((List)attrs.get("value")).iterator();

         Object value;
         do {
            if (!var4.hasNext()) {
               return false;
            }

            value = var4.next();
         } while(!context.getEnvironment().acceptsProfiles(Profiles.of((String[])((String[])value))));

         return true;
      } else {
         return true;
      }
   }
}
