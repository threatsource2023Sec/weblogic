package com.bea.core.repackaged.springframework.beans;

import com.bea.core.repackaged.springframework.util.ObjectUtils;
import com.bea.core.repackaged.springframework.util.ReflectionUtils;
import com.bea.core.repackaged.springframework.util.StringUtils;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class PropertyMatches {
   public static final int DEFAULT_MAX_DISTANCE = 2;
   private final String propertyName;
   private final String[] possibleMatches;

   public static PropertyMatches forProperty(String propertyName, Class beanClass) {
      return forProperty(propertyName, beanClass, 2);
   }

   public static PropertyMatches forProperty(String propertyName, Class beanClass, int maxDistance) {
      return new BeanPropertyMatches(propertyName, beanClass, maxDistance);
   }

   public static PropertyMatches forField(String propertyName, Class beanClass) {
      return forField(propertyName, beanClass, 2);
   }

   public static PropertyMatches forField(String propertyName, Class beanClass, int maxDistance) {
      return new FieldPropertyMatches(propertyName, beanClass, maxDistance);
   }

   private PropertyMatches(String propertyName, String[] possibleMatches) {
      this.propertyName = propertyName;
      this.possibleMatches = possibleMatches;
   }

   public String getPropertyName() {
      return this.propertyName;
   }

   public String[] getPossibleMatches() {
      return this.possibleMatches;
   }

   public abstract String buildErrorMessage();

   protected void appendHintMessage(StringBuilder msg) {
      msg.append("Did you mean ");

      for(int i = 0; i < this.possibleMatches.length; ++i) {
         msg.append('\'');
         msg.append(this.possibleMatches[i]);
         if (i < this.possibleMatches.length - 2) {
            msg.append("', ");
         } else if (i == this.possibleMatches.length - 2) {
            msg.append("', or ");
         }
      }

      msg.append("'?");
   }

   private static int calculateStringDistance(String s1, String s2) {
      if (s1.isEmpty()) {
         return s2.length();
      } else if (s2.isEmpty()) {
         return s1.length();
      } else {
         int[][] d = new int[s1.length() + 1][s2.length() + 1];

         int i;
         for(i = 0; i <= s1.length(); d[i][0] = i++) {
         }

         for(i = 0; i <= s2.length(); d[0][i] = i++) {
         }

         for(i = 1; i <= s1.length(); ++i) {
            char c1 = s1.charAt(i - 1);

            for(int j = 1; j <= s2.length(); ++j) {
               char c2 = s2.charAt(j - 1);
               byte cost;
               if (c1 == c2) {
                  cost = 0;
               } else {
                  cost = 1;
               }

               d[i][j] = Math.min(Math.min(d[i - 1][j] + 1, d[i][j - 1] + 1), d[i - 1][j - 1] + cost);
            }
         }

         return d[s1.length()][s2.length()];
      }
   }

   // $FF: synthetic method
   PropertyMatches(String x0, String[] x1, Object x2) {
      this(x0, x1);
   }

   private static class FieldPropertyMatches extends PropertyMatches {
      public FieldPropertyMatches(String propertyName, Class beanClass, int maxDistance) {
         super(propertyName, calculateMatches(propertyName, beanClass, maxDistance), null);
      }

      private static String[] calculateMatches(String name, Class clazz, int maxDistance) {
         List candidates = new ArrayList();
         ReflectionUtils.doWithFields(clazz, (field) -> {
            String possibleAlternative = field.getName();
            if (PropertyMatches.calculateStringDistance(name, possibleAlternative) <= maxDistance) {
               candidates.add(possibleAlternative);
            }

         });
         Collections.sort(candidates);
         return StringUtils.toStringArray((Collection)candidates);
      }

      public String buildErrorMessage() {
         StringBuilder msg = new StringBuilder(80);
         msg.append("Bean property '").append(this.getPropertyName()).append("' has no matching field.");
         if (!ObjectUtils.isEmpty((Object[])this.getPossibleMatches())) {
            msg.append(' ');
            this.appendHintMessage(msg);
         }

         return msg.toString();
      }
   }

   private static class BeanPropertyMatches extends PropertyMatches {
      public BeanPropertyMatches(String propertyName, Class beanClass, int maxDistance) {
         super(propertyName, calculateMatches(propertyName, BeanUtils.getPropertyDescriptors(beanClass), maxDistance), null);
      }

      private static String[] calculateMatches(String name, PropertyDescriptor[] descriptors, int maxDistance) {
         List candidates = new ArrayList();
         PropertyDescriptor[] var4 = descriptors;
         int var5 = descriptors.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            PropertyDescriptor pd = var4[var6];
            if (pd.getWriteMethod() != null) {
               String possibleAlternative = pd.getName();
               if (PropertyMatches.calculateStringDistance(name, possibleAlternative) <= maxDistance) {
                  candidates.add(possibleAlternative);
               }
            }
         }

         Collections.sort(candidates);
         return StringUtils.toStringArray((Collection)candidates);
      }

      public String buildErrorMessage() {
         StringBuilder msg = new StringBuilder(160);
         msg.append("Bean property '").append(this.getPropertyName()).append("' is not writable or has an invalid setter method. ");
         if (!ObjectUtils.isEmpty((Object[])this.getPossibleMatches())) {
            this.appendHintMessage(msg);
         } else {
            msg.append("Does the parameter type of the setter match the return type of the getter?");
         }

         return msg.toString();
      }
   }
}
