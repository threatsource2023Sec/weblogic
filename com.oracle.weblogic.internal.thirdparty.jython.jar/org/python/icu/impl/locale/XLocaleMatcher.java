package org.python.icu.impl.locale;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.python.icu.util.LocalePriorityList;
import org.python.icu.util.Output;
import org.python.icu.util.ULocale;

public class XLocaleMatcher {
   private static final XLikelySubtags.LSR UND = new XLikelySubtags.LSR("und", "", "");
   private static final ULocale UND_LOCALE = new ULocale("und");
   private final XLocaleDistance localeDistance;
   private final int thresholdDistance;
   private final int demotionPerAdditionalDesiredLocale;
   private final XLocaleDistance.DistanceOption distanceOption;
   private final Map supportedLanguages;
   private final Set exactSupportedLocales;
   private final ULocale defaultLanguage;

   public static Builder builder() {
      return new Builder();
   }

   public XLocaleMatcher(String supportedLocales) {
      this(builder().setSupportedLocales(supportedLocales));
   }

   public XLocaleMatcher(LocalePriorityList supportedLocales) {
      this(builder().setSupportedLocales(supportedLocales));
   }

   public XLocaleMatcher(Set supportedLocales) {
      this(builder().setSupportedLocales(supportedLocales));
   }

   private XLocaleMatcher(Builder builder) {
      this.localeDistance = builder.localeDistance == null ? XLocaleDistance.getDefault() : builder.localeDistance;
      this.thresholdDistance = builder.thresholdDistance < 0 ? this.localeDistance.getDefaultScriptDistance() : builder.thresholdDistance;
      Set paradigms = this.extractLsrSet(this.localeDistance.getParadigms());
      XCldrStub.Multimap temp2 = this.extractLsrMap(builder.supportedLanguagesList, paradigms);
      this.supportedLanguages = temp2.asMap();
      this.exactSupportedLocales = XCldrStub.ImmutableSet.copyOf(temp2.values());
      this.defaultLanguage = builder.defaultLanguage != null ? builder.defaultLanguage : (this.supportedLanguages.isEmpty() ? null : (ULocale)((Set)((Map.Entry)this.supportedLanguages.entrySet().iterator().next()).getValue()).iterator().next());
      this.demotionPerAdditionalDesiredLocale = builder.demotionPerAdditionalDesiredLocale < 0 ? this.localeDistance.getDefaultRegionDistance() + 1 : builder.demotionPerAdditionalDesiredLocale;
      this.distanceOption = builder.distanceOption;
   }

   private Set extractLsrSet(Set languagePriorityList) {
      Set result = new LinkedHashSet();
      Iterator var3 = languagePriorityList.iterator();

      while(var3.hasNext()) {
         ULocale item = (ULocale)var3.next();
         XLikelySubtags.LSR max = item.equals(UND_LOCALE) ? UND : XLikelySubtags.LSR.fromMaximalized(item);
         result.add(max);
      }

      return result;
   }

   private XCldrStub.Multimap extractLsrMap(Set languagePriorityList, Set priorities) {
      XCldrStub.Multimap builder = XCldrStub.LinkedHashMultimap.create();
      Iterator var4 = languagePriorityList.iterator();

      while(var4.hasNext()) {
         ULocale item = (ULocale)var4.next();
         XLikelySubtags.LSR max = item.equals(UND_LOCALE) ? UND : XLikelySubtags.LSR.fromMaximalized(item);
         builder.put(max, item);
      }

      if (builder.size() > 1 && priorities != null) {
         XCldrStub.Multimap builder2 = XCldrStub.LinkedHashMultimap.create();
         boolean first = true;
         Iterator var11 = builder.asMap().entrySet().iterator();

         while(true) {
            Map.Entry entry;
            XLikelySubtags.LSR key;
            do {
               if (!var11.hasNext()) {
                  builder2.putAll(builder);
                  if (!builder2.equals(builder)) {
                     throw new IllegalArgumentException();
                  }

                  builder = builder2;
                  return XCldrStub.ImmutableMultimap.copyOf(builder);
               }

               entry = (Map.Entry)var11.next();
               key = (XLikelySubtags.LSR)entry.getKey();
            } while(!first && !priorities.contains(key));

            builder2.putAll((Object)key, (Collection)((Collection)entry.getValue()));
            first = false;
         }
      } else {
         return XCldrStub.ImmutableMultimap.copyOf(builder);
      }
   }

   public ULocale getBestMatch(ULocale ulocale) {
      return this.getBestMatch((ULocale)ulocale, (Output)null);
   }

   public ULocale getBestMatch(String languageList) {
      return this.getBestMatch((LocalePriorityList)LocalePriorityList.add(languageList).build(), (Output)null);
   }

   public ULocale getBestMatch(ULocale... locales) {
      return this.getBestMatch((Set)(new LinkedHashSet(Arrays.asList(locales))), (Output)null);
   }

   public ULocale getBestMatch(Set desiredLanguages) {
      return this.getBestMatch((Set)desiredLanguages, (Output)null);
   }

   public ULocale getBestMatch(LocalePriorityList desiredLanguages) {
      return this.getBestMatch((LocalePriorityList)desiredLanguages, (Output)null);
   }

   public ULocale getBestMatch(LocalePriorityList desiredLanguages, Output outputBestDesired) {
      return this.getBestMatch(asSet(desiredLanguages), outputBestDesired);
   }

   private static Set asSet(LocalePriorityList languageList) {
      Set temp = new LinkedHashSet();
      Iterator var2 = languageList.iterator();

      while(var2.hasNext()) {
         ULocale locale = (ULocale)var2.next();
         temp.add(locale);
      }

      return temp;
   }

   public ULocale getBestMatch(Set desiredLanguages, Output outputBestDesired) {
      if (desiredLanguages.size() == 1) {
         return this.getBestMatch((ULocale)desiredLanguages.iterator().next(), outputBestDesired);
      } else {
         XCldrStub.Multimap desiredLSRs = this.extractLsrMap(desiredLanguages, (Set)null);
         int bestDistance = Integer.MAX_VALUE;
         ULocale bestDesiredLocale = null;
         Collection bestSupportedLocales = null;
         int delta = 0;

         label61:
         for(Iterator var8 = desiredLSRs.entries().iterator(); var8.hasNext(); delta += this.demotionPerAdditionalDesiredLocale) {
            Map.Entry desiredLsrAndLocale = (Map.Entry)var8.next();
            ULocale desiredLocale = (ULocale)desiredLsrAndLocale.getValue();
            XLikelySubtags.LSR desiredLSR = (XLikelySubtags.LSR)desiredLsrAndLocale.getKey();
            if (delta < bestDistance) {
               if (this.exactSupportedLocales.contains(desiredLocale)) {
                  if (outputBestDesired != null) {
                     outputBestDesired.value = desiredLocale;
                  }

                  return desiredLocale;
               }

               Collection found = (Collection)this.supportedLanguages.get(desiredLSR);
               if (found != null) {
                  if (outputBestDesired != null) {
                     outputBestDesired.value = desiredLocale;
                  }

                  return (ULocale)found.iterator().next();
               }
            }

            Iterator var15 = this.supportedLanguages.entrySet().iterator();

            while(var15.hasNext()) {
               Map.Entry supportedLsrAndLocale = (Map.Entry)var15.next();
               int distance = delta + this.localeDistance.distanceRaw(desiredLSR, (XLikelySubtags.LSR)supportedLsrAndLocale.getKey(), this.thresholdDistance, this.distanceOption);
               if (distance < bestDistance) {
                  bestDistance = distance;
                  bestDesiredLocale = desiredLocale;
                  bestSupportedLocales = (Collection)supportedLsrAndLocale.getValue();
                  if (distance == 0) {
                     break label61;
                  }
               }
            }
         }

         if (bestDistance >= this.thresholdDistance) {
            if (outputBestDesired != null) {
               outputBestDesired.value = null;
            }

            return this.defaultLanguage;
         } else {
            if (outputBestDesired != null) {
               outputBestDesired.value = bestDesiredLocale;
            }

            return bestSupportedLocales.contains(bestDesiredLocale) ? bestDesiredLocale : (ULocale)bestSupportedLocales.iterator().next();
         }
      }
   }

   public ULocale getBestMatch(ULocale desiredLocale, Output outputBestDesired) {
      int bestDistance = Integer.MAX_VALUE;
      ULocale bestDesiredLocale = null;
      Collection bestSupportedLocales = null;
      XLikelySubtags.LSR desiredLSR = desiredLocale.equals(UND_LOCALE) ? UND : XLikelySubtags.LSR.fromMaximalized(desiredLocale);
      if (this.exactSupportedLocales.contains(desiredLocale)) {
         if (outputBestDesired != null) {
            outputBestDesired.value = desiredLocale;
         }

         return desiredLocale;
      } else {
         if (this.distanceOption == XLocaleDistance.DistanceOption.NORMAL) {
            Collection found = (Collection)this.supportedLanguages.get(desiredLSR);
            if (found != null) {
               if (outputBestDesired != null) {
                  outputBestDesired.value = desiredLocale;
               }

               return (ULocale)found.iterator().next();
            }
         }

         Iterator var10 = this.supportedLanguages.entrySet().iterator();

         while(var10.hasNext()) {
            Map.Entry supportedLsrAndLocale = (Map.Entry)var10.next();
            int distance = this.localeDistance.distanceRaw(desiredLSR, (XLikelySubtags.LSR)supportedLsrAndLocale.getKey(), this.thresholdDistance, this.distanceOption);
            if (distance < bestDistance) {
               bestDistance = distance;
               bestDesiredLocale = desiredLocale;
               bestSupportedLocales = (Collection)supportedLsrAndLocale.getValue();
               if (distance == 0) {
                  break;
               }
            }
         }

         if (bestDistance >= this.thresholdDistance) {
            if (outputBestDesired != null) {
               outputBestDesired.value = null;
            }

            return this.defaultLanguage;
         } else {
            if (outputBestDesired != null) {
               outputBestDesired.value = bestDesiredLocale;
            }

            return bestSupportedLocales.contains(bestDesiredLocale) ? bestDesiredLocale : (ULocale)bestSupportedLocales.iterator().next();
         }
      }
   }

   public static ULocale combine(ULocale bestSupported, ULocale bestDesired) {
      if (!bestSupported.equals(bestDesired) && bestDesired != null) {
         ULocale.Builder b = (new ULocale.Builder()).setLocale(bestSupported);
         String region = bestDesired.getCountry();
         if (!region.isEmpty()) {
            b.setRegion(region);
         }

         String variants = bestDesired.getVariant();
         if (!variants.isEmpty()) {
            b.setVariant(variants);
         }

         Iterator var5 = bestDesired.getExtensionKeys().iterator();

         while(var5.hasNext()) {
            char extensionKey = (Character)var5.next();
            b.setExtension(extensionKey, bestDesired.getExtension(extensionKey));
         }

         bestSupported = b.build();
      }

      return bestSupported;
   }

   public int distance(ULocale desired, ULocale supported) {
      return this.localeDistance.distanceRaw(XLikelySubtags.LSR.fromMaximalized(desired), XLikelySubtags.LSR.fromMaximalized(supported), this.thresholdDistance, this.distanceOption);
   }

   public int distance(String desiredLanguage, String supportedLanguage) {
      return this.localeDistance.distanceRaw(XLikelySubtags.LSR.fromMaximalized(new ULocale(desiredLanguage)), XLikelySubtags.LSR.fromMaximalized(new ULocale(supportedLanguage)), this.thresholdDistance, this.distanceOption);
   }

   public String toString() {
      return this.exactSupportedLocales.toString();
   }

   public double match(ULocale desired, ULocale supported) {
      return (double)(100 - this.distance(desired, supported)) / 100.0;
   }

   /** @deprecated */
   @Deprecated
   public double match(ULocale desired, ULocale desiredMax, ULocale supported, ULocale supportedMax) {
      return this.match(desired, supported);
   }

   public ULocale canonicalize(ULocale ulocale) {
      return null;
   }

   public int getThresholdDistance() {
      return this.thresholdDistance;
   }

   // $FF: synthetic method
   XLocaleMatcher(Builder x0, Object x1) {
      this(x0);
   }

   public static class Builder {
      private Set supportedLanguagesList;
      private int thresholdDistance = -1;
      private int demotionPerAdditionalDesiredLocale = -1;
      private ULocale defaultLanguage;
      private XLocaleDistance localeDistance;
      private XLocaleDistance.DistanceOption distanceOption;

      public Builder setSupportedLocales(String languagePriorityList) {
         this.supportedLanguagesList = XLocaleMatcher.asSet(LocalePriorityList.add(languagePriorityList).build());
         return this;
      }

      public Builder setSupportedLocales(LocalePriorityList languagePriorityList) {
         this.supportedLanguagesList = XLocaleMatcher.asSet(languagePriorityList);
         return this;
      }

      public Builder setSupportedLocales(Set languagePriorityList) {
         this.supportedLanguagesList = languagePriorityList;
         return this;
      }

      public Builder setThresholdDistance(int thresholdDistance) {
         this.thresholdDistance = thresholdDistance;
         return this;
      }

      public Builder setDemotionPerAdditionalDesiredLocale(int demotionPerAdditionalDesiredLocale) {
         this.demotionPerAdditionalDesiredLocale = demotionPerAdditionalDesiredLocale;
         return this;
      }

      public Builder setLocaleDistance(XLocaleDistance localeDistance) {
         this.localeDistance = localeDistance;
         return this;
      }

      public Builder setDefaultLanguage(ULocale defaultLanguage) {
         this.defaultLanguage = defaultLanguage;
         return this;
      }

      public Builder setDistanceOption(XLocaleDistance.DistanceOption distanceOption) {
         this.distanceOption = distanceOption;
         return this;
      }

      public XLocaleMatcher build() {
         return new XLocaleMatcher(this);
      }
   }
}
