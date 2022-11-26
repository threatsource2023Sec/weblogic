package org.python.icu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LocalePriorityList implements Iterable {
   private static final double D0 = 0.0;
   private static final Double D1 = 1.0;
   private static final Pattern languageSplitter = Pattern.compile("\\s*,\\s*");
   private static final Pattern weightSplitter = Pattern.compile("\\s*(\\S*)\\s*;\\s*q\\s*=\\s*(\\S*)");
   private final Map languagesAndWeights;
   private static Comparator myDescendingDouble = new Comparator() {
      public int compare(Double o1, Double o2) {
         int result = o1.compareTo(o2);
         return result > 0 ? -1 : (result < 0 ? 1 : 0);
      }
   };

   public static Builder add(ULocale... languageCode) {
      return (new Builder()).add(languageCode);
   }

   public static Builder add(ULocale languageCode, double weight) {
      return (new Builder()).add(languageCode, weight);
   }

   public static Builder add(LocalePriorityList languagePriorityList) {
      return (new Builder()).add(languagePriorityList);
   }

   public static Builder add(String acceptLanguageString) {
      return (new Builder()).add(acceptLanguageString);
   }

   public Double getWeight(ULocale language) {
      return (Double)this.languagesAndWeights.get(language);
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      Iterator var2 = this.languagesAndWeights.keySet().iterator();

      while(var2.hasNext()) {
         ULocale language = (ULocale)var2.next();
         if (result.length() != 0) {
            result.append(", ");
         }

         result.append(language);
         double weight = (Double)this.languagesAndWeights.get(language);
         if (weight != D1) {
            result.append(";q=").append(weight);
         }
      }

      return result.toString();
   }

   public Iterator iterator() {
      return this.languagesAndWeights.keySet().iterator();
   }

   public boolean equals(Object o) {
      if (o == null) {
         return false;
      } else if (this == o) {
         return true;
      } else {
         try {
            LocalePriorityList that = (LocalePriorityList)o;
            return this.languagesAndWeights.equals(that.languagesAndWeights);
         } catch (RuntimeException var3) {
            return false;
         }
      }
   }

   public int hashCode() {
      return this.languagesAndWeights.hashCode();
   }

   private LocalePriorityList(Map languageToWeight) {
      this.languagesAndWeights = languageToWeight;
   }

   // $FF: synthetic method
   LocalePriorityList(Map x0, Object x1) {
      this(x0);
   }

   public static class Builder {
      private final Map languageToWeight;

      private Builder() {
         this.languageToWeight = new LinkedHashMap();
      }

      public LocalePriorityList build() {
         return this.build(false);
      }

      public LocalePriorityList build(boolean preserveWeights) {
         Map doubleCheck = new TreeMap(LocalePriorityList.myDescendingDouble);

         ULocale lang;
         Object s;
         for(Iterator var3 = this.languageToWeight.keySet().iterator(); var3.hasNext(); ((Set)s).add(lang)) {
            lang = (ULocale)var3.next();
            Double weight = (Double)this.languageToWeight.get(lang);
            s = (Set)doubleCheck.get(weight);
            if (s == null) {
               doubleCheck.put(weight, s = new LinkedHashSet());
            }
         }

         Map temp = new LinkedHashMap();
         Iterator var10 = doubleCheck.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry langEntry = (Map.Entry)var10.next();
            Double weight = (Double)langEntry.getKey();
            Iterator var7 = ((Set)langEntry.getValue()).iterator();

            while(var7.hasNext()) {
               ULocale lang = (ULocale)var7.next();
               temp.put(lang, preserveWeights ? weight : LocalePriorityList.D1);
            }
         }

         return new LocalePriorityList(Collections.unmodifiableMap(temp));
      }

      public Builder add(LocalePriorityList languagePriorityList) {
         Iterator var2 = languagePriorityList.languagesAndWeights.keySet().iterator();

         while(var2.hasNext()) {
            ULocale language = (ULocale)var2.next();
            this.add(language, (Double)languagePriorityList.languagesAndWeights.get(language));
         }

         return this;
      }

      public Builder add(ULocale languageCode) {
         return this.add(languageCode, LocalePriorityList.D1);
      }

      public Builder add(ULocale... languageCodes) {
         ULocale[] var2 = languageCodes;
         int var3 = languageCodes.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            ULocale languageCode = var2[var4];
            this.add(languageCode, LocalePriorityList.D1);
         }

         return this;
      }

      public Builder add(ULocale languageCode, double weight) {
         if (this.languageToWeight.containsKey(languageCode)) {
            this.languageToWeight.remove(languageCode);
         }

         if (weight <= 0.0) {
            return this;
         } else {
            if (weight > LocalePriorityList.D1) {
               weight = LocalePriorityList.D1;
            }

            this.languageToWeight.put(languageCode, weight);
            return this;
         }
      }

      public Builder add(String acceptLanguageList) {
         String[] items = LocalePriorityList.languageSplitter.split(acceptLanguageList.trim());
         Matcher itemMatcher = LocalePriorityList.weightSplitter.matcher("");
         String[] var4 = items;
         int var5 = items.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            String item = var4[var6];
            if (itemMatcher.reset(item).matches()) {
               ULocale language = new ULocale(itemMatcher.group(1));
               double weight = Double.parseDouble(itemMatcher.group(2));
               if (!(weight >= 0.0) || !(weight <= LocalePriorityList.D1)) {
                  throw new IllegalArgumentException("Illegal weight, must be 0..1: " + weight);
               }

               this.add(language, weight);
            } else if (item.length() != 0) {
               this.add(new ULocale(item));
            }
         }

         return this;
      }

      // $FF: synthetic method
      Builder(Object x0) {
         this();
      }
   }
}
