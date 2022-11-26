package com.oracle.weblogic.lifecycle.provisioning.core;

import com.google.common.base.CaseFormat;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import weblogic.management.RuntimeDir;

public final class TemporaryPartitionFilesystemAsMap extends JavaBeanMap {
   public TemporaryPartitionFilesystemAsMap(RuntimeDir temporaryPartitionFilesystem) throws IntrospectionException {
      super(temporaryPartitionFilesystem);
      Objects.requireNonNull(temporaryPartitionFilesystem);
   }

   protected void filter(Collection pds) {
      if (pds != null && !pds.isEmpty()) {
         Collection blacklist = Arrays.asList("class", "deleted");
         Iterator iterator = pds.iterator();
         if (iterator != null) {
            while(true) {
               String name;
               do {
                  PropertyDescriptor pd;
                  do {
                     if (!iterator.hasNext()) {
                        return;
                     }

                     pd = (PropertyDescriptor)iterator.next();
                  } while(pd == null);

                  name = pd.getName();
               } while(name != null && !blacklist.contains(name));

               iterator.remove();
            }
         }
      }

   }

   protected String getMapKeyFor(PropertyDescriptor propertyDescriptor) {
      String javaBeanPropertyName;
      if (propertyDescriptor == null) {
         javaBeanPropertyName = null;
      } else {
         javaBeanPropertyName = propertyDescriptor.getName();
      }

      String returnValue;
      if (javaBeanPropertyName != null && !javaBeanPropertyName.isEmpty()) {
         String candidateMapKey = CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, javaBeanPropertyName);

         assert candidateMapKey != null;

         char[] chars = candidateMapKey.toCharArray();

         assert chars != null;

         assert chars.length > 0;

         StringBuilder mapKeyBuilder = new StringBuilder();
         if (!candidateMapKey.startsWith("PARTITION_")) {
            mapKeyBuilder.append("PARTITION_");
         }

         for(int i = 0; i < chars.length; ++i) {
            char c = chars[i];
            mapKeyBuilder.append(c);
            if (i + 3 < chars.length && chars[i + 1] == '_' && chars[i + 3] == '_') {
               ++i;
            }
         }

         returnValue = mapKeyBuilder.toString();
      } else {
         returnValue = javaBeanPropertyName;
      }

      return returnValue;
   }
}
