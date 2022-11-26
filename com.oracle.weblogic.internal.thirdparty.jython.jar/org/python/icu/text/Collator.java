package org.python.icu.text;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;
import org.python.icu.impl.ICUDebug;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.UResource;
import org.python.icu.impl.coll.CollationData;
import org.python.icu.impl.coll.CollationRoot;
import org.python.icu.lang.UCharacter;
import org.python.icu.util.Freezable;
import org.python.icu.util.ICUException;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.VersionInfo;

public abstract class Collator implements Comparator, Freezable, Cloneable {
   public static final int PRIMARY = 0;
   public static final int SECONDARY = 1;
   public static final int TERTIARY = 2;
   public static final int QUATERNARY = 3;
   public static final int IDENTICAL = 15;
   public static final int FULL_DECOMPOSITION = 15;
   public static final int NO_DECOMPOSITION = 16;
   public static final int CANONICAL_DECOMPOSITION = 17;
   private static ServiceShim shim;
   private static final String[] KEYWORDS = new String[]{"collation"};
   private static final String RESOURCE = "collations";
   private static final String BASE = "org/python/icu/impl/data/icudt59b/coll";
   private static final boolean DEBUG = ICUDebug.enabled("collator");

   public boolean equals(Object obj) {
      return this == obj || obj != null && this.getClass() == obj.getClass();
   }

   public int hashCode() {
      return 0;
   }

   private void checkNotFrozen() {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify frozen Collator");
      }
   }

   public void setStrength(int newStrength) {
      this.checkNotFrozen();
   }

   /** @deprecated */
   @Deprecated
   public Collator setStrength2(int newStrength) {
      this.setStrength(newStrength);
      return this;
   }

   public void setDecomposition(int decomposition) {
      this.checkNotFrozen();
   }

   public void setReorderCodes(int... order) {
      throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
   }

   public static final Collator getInstance() {
      return getInstance(ULocale.getDefault());
   }

   public Object clone() throws CloneNotSupportedException {
      return super.clone();
   }

   private static ServiceShim getShim() {
      if (shim == null) {
         try {
            Class cls = Class.forName("org.python.icu.text.CollatorServiceShim");
            shim = (ServiceShim)cls.newInstance();
         } catch (MissingResourceException var1) {
            throw var1;
         } catch (Exception var2) {
            if (DEBUG) {
               var2.printStackTrace();
            }

            throw new ICUException(var2);
         }
      }

      return shim;
   }

   private static final boolean getYesOrNo(String keyword, String s) {
      if (Collator.ASCII.equalIgnoreCase(s, "yes")) {
         return true;
      } else if (Collator.ASCII.equalIgnoreCase(s, "no")) {
         return false;
      } else {
         throw new IllegalArgumentException("illegal locale keyword=value: " + keyword + "=" + s);
      }
   }

   private static final int getIntValue(String keyword, String s, String... values) {
      for(int i = 0; i < values.length; ++i) {
         if (Collator.ASCII.equalIgnoreCase(s, values[i])) {
            return i;
         }
      }

      throw new IllegalArgumentException("illegal locale keyword=value: " + keyword + "=" + s);
   }

   private static final int getReorderCode(String keyword, String s) {
      return 4096 + getIntValue(keyword, s, "space", "punct", "symbol", "currency", "digit");
   }

   private static void setAttributesFromKeywords(ULocale loc, Collator coll, RuleBasedCollator rbc) {
      String value = loc.getKeywordValue("colHiraganaQuaternary");
      if (value != null) {
         throw new UnsupportedOperationException("locale keyword kh/colHiraganaQuaternary");
      } else {
         value = loc.getKeywordValue("variableTop");
         if (value != null) {
            throw new UnsupportedOperationException("locale keyword vt/variableTop");
         } else {
            value = loc.getKeywordValue("colStrength");
            int cf;
            if (value != null) {
               cf = getIntValue("colStrength", value, "primary", "secondary", "tertiary", "quaternary", "identical");
               coll.setStrength(cf <= 3 ? cf : 15);
            }

            value = loc.getKeywordValue("colBackwards");
            if (value != null) {
               if (rbc == null) {
                  throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
               }

               rbc.setFrenchCollation(getYesOrNo("colBackwards", value));
            }

            value = loc.getKeywordValue("colCaseLevel");
            if (value != null) {
               if (rbc == null) {
                  throw new UnsupportedOperationException("locale keyword kb/colBackwards only settable for RuleBasedCollator");
               }

               rbc.setCaseLevel(getYesOrNo("colCaseLevel", value));
            }

            value = loc.getKeywordValue("colCaseFirst");
            if (value != null) {
               if (rbc == null) {
                  throw new UnsupportedOperationException("locale keyword kf/colCaseFirst only settable for RuleBasedCollator");
               }

               cf = getIntValue("colCaseFirst", value, "no", "lower", "upper");
               if (cf == 0) {
                  rbc.setLowerCaseFirst(false);
                  rbc.setUpperCaseFirst(false);
               } else if (cf == 1) {
                  rbc.setLowerCaseFirst(true);
               } else {
                  rbc.setUpperCaseFirst(true);
               }
            }

            value = loc.getKeywordValue("colAlternate");
            if (value != null) {
               if (rbc == null) {
                  throw new UnsupportedOperationException("locale keyword ka/colAlternate only settable for RuleBasedCollator");
               }

               rbc.setAlternateHandlingShifted(getIntValue("colAlternate", value, "non-ignorable", "shifted") != 0);
            }

            value = loc.getKeywordValue("colNormalization");
            if (value != null) {
               coll.setDecomposition(getYesOrNo("colNormalization", value) ? 17 : 16);
            }

            value = loc.getKeywordValue("colNumeric");
            if (value != null) {
               if (rbc == null) {
                  throw new UnsupportedOperationException("locale keyword kn/colNumeric only settable for RuleBasedCollator");
               }

               rbc.setNumericCollation(getYesOrNo("colNumeric", value));
            }

            value = loc.getKeywordValue("colReorder");
            if (value != null) {
               int[] codes = new int[180];
               int codesLength = 0;
               int scriptNameStart = 0;

               while(true) {
                  if (codesLength == codes.length) {
                     throw new IllegalArgumentException("too many script codes for colReorder locale keyword: " + value);
                  }

                  int limit;
                  for(limit = scriptNameStart; limit < value.length() && value.charAt(limit) != '-'; ++limit) {
                  }

                  String scriptName = value.substring(scriptNameStart, limit);
                  int code;
                  if (scriptName.length() == 4) {
                     code = UCharacter.getPropertyValueEnum(4106, scriptName);
                  } else {
                     code = getReorderCode("colReorder", scriptName);
                  }

                  codes[codesLength++] = code;
                  if (limit == value.length()) {
                     if (codesLength == 0) {
                        throw new IllegalArgumentException("no script codes for colReorder locale keyword");
                     }

                     int[] args = new int[codesLength];
                     System.arraycopy(codes, 0, args, 0, codesLength);
                     coll.setReorderCodes(args);
                     break;
                  }

                  scriptNameStart = limit + 1;
               }
            }

            value = loc.getKeywordValue("kv");
            if (value != null) {
               coll.setMaxVariable(getReorderCode("kv", value));
            }

         }
      }
   }

   public static final Collator getInstance(ULocale locale) {
      if (locale == null) {
         locale = ULocale.getDefault();
      }

      Collator coll = getShim().getInstance(locale);
      if (!locale.getName().equals(locale.getBaseName())) {
         setAttributesFromKeywords(locale, coll, coll instanceof RuleBasedCollator ? (RuleBasedCollator)coll : null);
      }

      return coll;
   }

   public static final Collator getInstance(Locale locale) {
      return getInstance(ULocale.forLocale(locale));
   }

   public static final Object registerInstance(Collator collator, ULocale locale) {
      return getShim().registerInstance(collator, locale);
   }

   public static final Object registerFactory(CollatorFactory factory) {
      return getShim().registerFactory(factory);
   }

   public static final boolean unregister(Object registryKey) {
      return shim == null ? false : shim.unregister(registryKey);
   }

   public static Locale[] getAvailableLocales() {
      return shim == null ? ICUResourceBundle.getAvailableLocales("org/python/icu/impl/data/icudt59b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER) : shim.getAvailableLocales();
   }

   public static final ULocale[] getAvailableULocales() {
      return shim == null ? ICUResourceBundle.getAvailableULocales("org/python/icu/impl/data/icudt59b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER) : shim.getAvailableULocales();
   }

   public static final String[] getKeywords() {
      return KEYWORDS;
   }

   public static final String[] getKeywordValues(String keyword) {
      if (!keyword.equals(KEYWORDS[0])) {
         throw new IllegalArgumentException("Invalid keyword: " + keyword);
      } else {
         return ICUResourceBundle.getKeywordValues("org/python/icu/impl/data/icudt59b/coll", "collations");
      }
   }

   public static final String[] getKeywordValuesForLocale(String key, ULocale locale, boolean commonlyUsed) {
      ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/coll", locale);
      KeywordsSink sink = new KeywordsSink();
      bundle.getAllItemsWithFallback("collations", sink);
      return (String[])sink.values.toArray(new String[sink.values.size()]);
   }

   public static final ULocale getFunctionalEquivalent(String keyword, ULocale locID, boolean[] isAvailable) {
      return ICUResourceBundle.getFunctionalEquivalent("org/python/icu/impl/data/icudt59b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER, "collations", keyword, locID, isAvailable, true);
   }

   public static final ULocale getFunctionalEquivalent(String keyword, ULocale locID) {
      return getFunctionalEquivalent(keyword, locID, (boolean[])null);
   }

   public static String getDisplayName(Locale objectLocale, Locale displayLocale) {
      return getShim().getDisplayName(ULocale.forLocale(objectLocale), ULocale.forLocale(displayLocale));
   }

   public static String getDisplayName(ULocale objectLocale, ULocale displayLocale) {
      return getShim().getDisplayName(objectLocale, displayLocale);
   }

   public static String getDisplayName(Locale objectLocale) {
      return getShim().getDisplayName(ULocale.forLocale(objectLocale), ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public static String getDisplayName(ULocale objectLocale) {
      return getShim().getDisplayName(objectLocale, ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public int getStrength() {
      return 2;
   }

   public int getDecomposition() {
      return 16;
   }

   public boolean equals(String source, String target) {
      return this.compare(source, target) == 0;
   }

   public UnicodeSet getTailoredSet() {
      return new UnicodeSet(0, 1114111);
   }

   public abstract int compare(String var1, String var2);

   public int compare(Object source, Object target) {
      return this.doCompare((CharSequence)source, (CharSequence)target);
   }

   /** @deprecated */
   @Deprecated
   protected int doCompare(CharSequence left, CharSequence right) {
      return this.compare(left.toString(), right.toString());
   }

   public abstract CollationKey getCollationKey(String var1);

   public abstract RawCollationKey getRawCollationKey(String var1, RawCollationKey var2);

   public Collator setMaxVariable(int group) {
      throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
   }

   public int getMaxVariable() {
      return 4097;
   }

   /** @deprecated */
   @Deprecated
   public abstract int setVariableTop(String var1);

   public abstract int getVariableTop();

   /** @deprecated */
   @Deprecated
   public abstract void setVariableTop(int var1);

   public abstract VersionInfo getVersion();

   public abstract VersionInfo getUCAVersion();

   public int[] getReorderCodes() {
      throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
   }

   public static int[] getEquivalentReorderCodes(int reorderCode) {
      CollationData baseData = CollationRoot.getData();
      return baseData.getEquivalentScripts(reorderCode);
   }

   public boolean isFrozen() {
      return false;
   }

   public Collator freeze() {
      throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
   }

   public Collator cloneAsThawed() {
      throw new UnsupportedOperationException("Needs to be implemented by the subclass.");
   }

   protected Collator() {
   }

   public ULocale getLocale(ULocale.Type type) {
      return ULocale.ROOT;
   }

   void setLocale(ULocale valid, ULocale actual) {
   }

   private static final class KeywordsSink extends UResource.Sink {
      LinkedList values;
      boolean hasDefault;

      private KeywordsSink() {
         this.values = new LinkedList();
         this.hasDefault = false;
      }

      public void put(UResource.Key key, UResource.Value value, boolean noFallback) {
         UResource.Table collations = value.getTable();

         for(int i = 0; collations.getKeyAndValue(i, key, value); ++i) {
            int type = value.getType();
            String defcoll;
            if (type == 0) {
               if (!this.hasDefault && key.contentEquals("default")) {
                  defcoll = value.getString();
                  if (!defcoll.isEmpty()) {
                     this.values.remove(defcoll);
                     this.values.addFirst(defcoll);
                     this.hasDefault = true;
                  }
               }
            } else if (type == 2 && !key.startsWith("private-")) {
               defcoll = key.toString();
               if (!this.values.contains(defcoll)) {
                  this.values.add(defcoll);
               }
            }
         }

      }

      // $FF: synthetic method
      KeywordsSink(Object x0) {
         this();
      }
   }

   private static final class ASCII {
      static boolean equalIgnoreCase(CharSequence left, CharSequence right) {
         int length = left.length();
         if (length != right.length()) {
            return false;
         } else {
            int i = 0;

            while(true) {
               if (i >= length) {
                  return true;
               }

               char lc = left.charAt(i);
               char rc = right.charAt(i);
               if (lc != rc) {
                  if ('A' <= lc && lc <= 'Z') {
                     if (lc + 32 != rc) {
                        break;
                     }
                  } else if ('A' > rc || rc > 'Z' || rc + 32 != lc) {
                     break;
                  }
               }

               ++i;
            }

            return false;
         }
      }
   }

   abstract static class ServiceShim {
      abstract Collator getInstance(ULocale var1);

      abstract Object registerInstance(Collator var1, ULocale var2);

      abstract Object registerFactory(CollatorFactory var1);

      abstract boolean unregister(Object var1);

      abstract Locale[] getAvailableLocales();

      abstract ULocale[] getAvailableULocales();

      abstract String getDisplayName(ULocale var1, ULocale var2);
   }

   public abstract static class CollatorFactory {
      public boolean visible() {
         return true;
      }

      public Collator createCollator(ULocale loc) {
         return this.createCollator(loc.toLocale());
      }

      public Collator createCollator(Locale loc) {
         return this.createCollator(ULocale.forLocale(loc));
      }

      public String getDisplayName(Locale objectLocale, Locale displayLocale) {
         return this.getDisplayName(ULocale.forLocale(objectLocale), ULocale.forLocale(displayLocale));
      }

      public String getDisplayName(ULocale objectLocale, ULocale displayLocale) {
         if (this.visible()) {
            Set supported = this.getSupportedLocaleIDs();
            String name = objectLocale.getBaseName();
            if (supported.contains(name)) {
               return objectLocale.getDisplayName(displayLocale);
            }
         }

         return null;
      }

      public abstract Set getSupportedLocaleIDs();

      protected CollatorFactory() {
      }
   }

   public interface ReorderCodes {
      int DEFAULT = -1;
      int NONE = 103;
      int OTHERS = 103;
      int SPACE = 4096;
      int FIRST = 4096;
      int PUNCTUATION = 4097;
      int SYMBOL = 4098;
      int CURRENCY = 4099;
      int DIGIT = 4100;
      /** @deprecated */
      @Deprecated
      int LIMIT = 4101;
   }
}
