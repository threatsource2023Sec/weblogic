package org.python.icu.text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import org.python.icu.impl.CacheBase;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.SoftCache;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.UResourceBundleIterator;

public class NumberingSystem {
   private static final String[] OTHER_NS_KEYWORDS = new String[]{"native", "traditional", "finance"};
   private String desc = "0123456789";
   private int radix = 10;
   private boolean algorithmic = false;
   private String name = "latn";
   private static CacheBase cachedLocaleData = new SoftCache() {
      protected NumberingSystem createInstance(String key, LocaleLookupData localeLookupData) {
         return NumberingSystem.lookupInstanceByLocale(localeLookupData);
      }
   };
   private static CacheBase cachedStringData = new SoftCache() {
      protected NumberingSystem createInstance(String key, Void unused) {
         return NumberingSystem.lookupInstanceByName(key);
      }
   };

   public static NumberingSystem getInstance(int radix_in, boolean isAlgorithmic_in, String desc_in) {
      return getInstance((String)null, radix_in, isAlgorithmic_in, desc_in);
   }

   private static NumberingSystem getInstance(String name_in, int radix_in, boolean isAlgorithmic_in, String desc_in) {
      if (radix_in < 2) {
         throw new IllegalArgumentException("Invalid radix for numbering system");
      } else if (isAlgorithmic_in || desc_in.length() == radix_in && isValidDigitString(desc_in)) {
         NumberingSystem ns = new NumberingSystem();
         ns.radix = radix_in;
         ns.algorithmic = isAlgorithmic_in;
         ns.desc = desc_in;
         ns.name = name_in;
         return ns;
      } else {
         throw new IllegalArgumentException("Invalid digit string for numbering system");
      }
   }

   public static NumberingSystem getInstance(Locale inLocale) {
      return getInstance(ULocale.forLocale(inLocale));
   }

   public static NumberingSystem getInstance(ULocale locale) {
      boolean nsResolved = true;
      String numbersKeyword = locale.getKeywordValue("numbers");
      if (numbersKeyword != null) {
         String[] var3 = OTHER_NS_KEYWORDS;
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            String keyword = var3[var5];
            if (numbersKeyword.equals(keyword)) {
               nsResolved = false;
               break;
            }
         }
      } else {
         numbersKeyword = "default";
         nsResolved = false;
      }

      if (nsResolved) {
         NumberingSystem ns = getInstanceByName(numbersKeyword);
         if (ns != null) {
            return ns;
         }

         numbersKeyword = "default";
      }

      String baseName = locale.getBaseName();
      String key = baseName + "@numbers=" + numbersKeyword;
      LocaleLookupData localeLookupData = new LocaleLookupData(locale, numbersKeyword);
      return (NumberingSystem)cachedLocaleData.getInstance(key, localeLookupData);
   }

   static NumberingSystem lookupInstanceByLocale(LocaleLookupData localeLookupData) {
      ULocale locale = localeLookupData.locale;

      ICUResourceBundle rb;
      try {
         rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", locale);
         rb = rb.getWithFallback("NumberElements");
      } catch (MissingResourceException var6) {
         return new NumberingSystem();
      }

      String numbersKeyword = localeLookupData.numbersKeyword;
      String resolvedNumberingSystem = null;

      while(true) {
         try {
            resolvedNumberingSystem = rb.getStringWithFallback(numbersKeyword);
            break;
         } catch (MissingResourceException var7) {
            if (!numbersKeyword.equals("native") && !numbersKeyword.equals("finance")) {
               if (!numbersKeyword.equals("traditional")) {
                  break;
               }

               numbersKeyword = "native";
            } else {
               numbersKeyword = "default";
            }
         }
      }

      NumberingSystem ns = null;
      if (resolvedNumberingSystem != null) {
         ns = getInstanceByName(resolvedNumberingSystem);
      }

      if (ns == null) {
         ns = new NumberingSystem();
      }

      return ns;
   }

   public static NumberingSystem getInstance() {
      return getInstance(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public static NumberingSystem getInstanceByName(String name) {
      return (NumberingSystem)cachedStringData.getInstance(name, (Object)null);
   }

   private static NumberingSystem lookupInstanceByName(String name) {
      String description;
      int radix;
      boolean isAlgorithmic;
      try {
         UResourceBundle numberingSystemsInfo = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "numberingSystems");
         UResourceBundle nsCurrent = numberingSystemsInfo.get("numberingSystems");
         UResourceBundle nsTop = nsCurrent.get(name);
         description = nsTop.getString("desc");
         UResourceBundle nsRadixBundle = nsTop.get("radix");
         UResourceBundle nsAlgBundle = nsTop.get("algorithmic");
         radix = nsRadixBundle.getInt();
         int algorithmic = nsAlgBundle.getInt();
         isAlgorithmic = algorithmic == 1;
      } catch (MissingResourceException var10) {
         return null;
      }

      return getInstance(name, radix, isAlgorithmic, description);
   }

   public static String[] getAvailableNames() {
      UResourceBundle numberingSystemsInfo = UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", "numberingSystems");
      UResourceBundle nsCurrent = numberingSystemsInfo.get("numberingSystems");
      ArrayList output = new ArrayList();
      UResourceBundleIterator it = nsCurrent.getIterator();

      while(it.hasNext()) {
         UResourceBundle temp = it.next();
         String nsName = temp.getKey();
         output.add(nsName);
      }

      return (String[])output.toArray(new String[output.size()]);
   }

   public static boolean isValidDigitString(String str) {
      int numCodepoints = str.codePointCount(0, str.length());
      return numCodepoints == 10;
   }

   public int getRadix() {
      return this.radix;
   }

   public String getDescription() {
      return this.desc;
   }

   public String getName() {
      return this.name;
   }

   public boolean isAlgorithmic() {
      return this.algorithmic;
   }

   private static class LocaleLookupData {
      public final ULocale locale;
      public final String numbersKeyword;

      LocaleLookupData(ULocale locale, String numbersKeyword) {
         this.locale = locale;
         this.numbersKeyword = numbersKeyword;
      }
   }
}
