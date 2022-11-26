package org.python.icu.util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.python.icu.impl.Utility;
import org.python.icu.text.BreakIterator;
import org.python.icu.text.Collator;
import org.python.icu.text.DateFormat;
import org.python.icu.text.NumberFormat;
import org.python.icu.text.SimpleDateFormat;

public class GlobalizationPreferences implements Freezable {
   public static final int NF_NUMBER = 0;
   public static final int NF_CURRENCY = 1;
   public static final int NF_PERCENT = 2;
   public static final int NF_SCIENTIFIC = 3;
   public static final int NF_INTEGER = 4;
   private static final int NF_LIMIT = 5;
   public static final int DF_FULL = 0;
   public static final int DF_LONG = 1;
   public static final int DF_MEDIUM = 2;
   public static final int DF_SHORT = 3;
   public static final int DF_NONE = 4;
   private static final int DF_LIMIT = 5;
   public static final int ID_LOCALE = 0;
   public static final int ID_LANGUAGE = 1;
   public static final int ID_SCRIPT = 2;
   public static final int ID_TERRITORY = 3;
   public static final int ID_VARIANT = 4;
   public static final int ID_KEYWORD = 5;
   public static final int ID_KEYWORD_VALUE = 6;
   public static final int ID_CURRENCY = 7;
   public static final int ID_CURRENCY_SYMBOL = 8;
   public static final int ID_TIMEZONE = 9;
   public static final int BI_CHARACTER = 0;
   public static final int BI_WORD = 1;
   public static final int BI_LINE = 2;
   public static final int BI_SENTENCE = 3;
   public static final int BI_TITLE = 4;
   private static final int BI_LIMIT = 5;
   private List locales;
   private String territory;
   private Currency currency;
   private TimeZone timezone;
   private Calendar calendar;
   private Collator collator;
   private BreakIterator[] breakIterators;
   private DateFormat[][] dateFormats;
   private NumberFormat[] numberFormats;
   private List implicitLocales;
   private static final HashMap available_locales = new HashMap();
   private static final int TYPE_GENERIC = 0;
   private static final int TYPE_CALENDAR = 1;
   private static final int TYPE_DATEFORMAT = 2;
   private static final int TYPE_NUMBERFORMAT = 3;
   private static final int TYPE_COLLATOR = 4;
   private static final int TYPE_BREAKITERATOR = 5;
   private static final int TYPE_LIMIT = 6;
   private static final Map language_territory_hack_map;
   private static final String[][] language_territory_hack;
   static final Map territory_tzid_hack_map;
   static final String[][] territory_tzid_hack;
   private volatile boolean frozen;

   public GlobalizationPreferences() {
      this.reset();
   }

   public GlobalizationPreferences setLocales(List inputLocales) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         this.locales = this.processLocales(inputLocales);
         return this;
      }
   }

   public List getLocales() {
      Object result;
      if (this.locales == null) {
         result = this.guessLocales();
      } else {
         result = new ArrayList();
         ((List)result).addAll(this.locales);
      }

      return (List)result;
   }

   public ULocale getLocale(int index) {
      List lcls = this.locales;
      if (lcls == null) {
         lcls = this.guessLocales();
      }

      return index >= 0 && index < lcls.size() ? (ULocale)lcls.get(index) : null;
   }

   public GlobalizationPreferences setLocales(ULocale[] uLocales) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         return this.setLocales(Arrays.asList(uLocales));
      }
   }

   public GlobalizationPreferences setLocale(ULocale uLocale) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         return this.setLocales(new ULocale[]{uLocale});
      }
   }

   public GlobalizationPreferences setLocales(String acceptLanguageString) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         ULocale[] acceptLocales = null;

         try {
            acceptLocales = ULocale.parseAcceptLanguage(acceptLanguageString, true);
         } catch (ParseException var4) {
            throw new IllegalArgumentException("Invalid Accept-Language string");
         }

         return this.setLocales(acceptLocales);
      }
   }

   public ResourceBundle getResourceBundle(String baseName) {
      return this.getResourceBundle(baseName, (ClassLoader)null);
   }

   public ResourceBundle getResourceBundle(String baseName, ClassLoader loader) {
      UResourceBundle urb = null;
      UResourceBundle candidate = null;
      String actualLocaleName = null;
      List fallbacks = this.getLocales();

      for(int i = 0; i < fallbacks.size(); ++i) {
         String localeName = ((ULocale)fallbacks.get(i)).toString();
         if (actualLocaleName != null && localeName.equals(actualLocaleName)) {
            urb = candidate;
            break;
         }

         try {
            if (loader == null) {
               candidate = UResourceBundle.getBundleInstance(baseName, localeName);
            } else {
               candidate = UResourceBundle.getBundleInstance(baseName, localeName, loader);
            }

            if (candidate != null) {
               actualLocaleName = candidate.getULocale().getName();
               if (actualLocaleName.equals(localeName)) {
                  urb = candidate;
                  break;
               }

               if (urb == null) {
                  urb = candidate;
               }
            }
         } catch (MissingResourceException var10) {
            actualLocaleName = null;
         }
      }

      if (urb == null) {
         throw new MissingResourceException("Can't find bundle for base name " + baseName, baseName, "");
      } else {
         return urb;
      }
   }

   public GlobalizationPreferences setTerritory(String territory) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         this.territory = territory;
         return this;
      }
   }

   public String getTerritory() {
      return this.territory == null ? this.guessTerritory() : this.territory;
   }

   public GlobalizationPreferences setCurrency(Currency currency) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         this.currency = currency;
         return this;
      }
   }

   public Currency getCurrency() {
      return this.currency == null ? this.guessCurrency() : this.currency;
   }

   public GlobalizationPreferences setCalendar(Calendar calendar) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         this.calendar = (Calendar)calendar.clone();
         return this;
      }
   }

   public Calendar getCalendar() {
      if (this.calendar == null) {
         return this.guessCalendar();
      } else {
         Calendar temp = (Calendar)this.calendar.clone();
         temp.setTimeZone(this.getTimeZone());
         temp.setTimeInMillis(System.currentTimeMillis());
         return temp;
      }
   }

   public GlobalizationPreferences setTimeZone(TimeZone timezone) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         this.timezone = (TimeZone)timezone.clone();
         return this;
      }
   }

   public TimeZone getTimeZone() {
      return this.timezone == null ? this.guessTimeZone() : this.timezone.cloneAsThawed();
   }

   public Collator getCollator() {
      if (this.collator == null) {
         return this.guessCollator();
      } else {
         try {
            return (Collator)this.collator.clone();
         } catch (CloneNotSupportedException var2) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", var2);
         }
      }
   }

   public GlobalizationPreferences setCollator(Collator collator) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         try {
            this.collator = (Collator)collator.clone();
            return this;
         } catch (CloneNotSupportedException var3) {
            throw new ICUCloneNotSupportedException("Error in cloning collator", var3);
         }
      }
   }

   public BreakIterator getBreakIterator(int type) {
      if (type >= 0 && type < 5) {
         return this.breakIterators != null && this.breakIterators[type] != null ? (BreakIterator)this.breakIterators[type].clone() : this.guessBreakIterator(type);
      } else {
         throw new IllegalArgumentException("Illegal break iterator type");
      }
   }

   public GlobalizationPreferences setBreakIterator(int type, BreakIterator iterator) {
      if (type >= 0 && type < 5) {
         if (this.isFrozen()) {
            throw new UnsupportedOperationException("Attempt to modify immutable object");
         } else {
            if (this.breakIterators == null) {
               this.breakIterators = new BreakIterator[5];
            }

            this.breakIterators[type] = (BreakIterator)iterator.clone();
            return this;
         }
      } else {
         throw new IllegalArgumentException("Illegal break iterator type");
      }
   }

   public String getDisplayName(String id, int type) {
      String result = id;
      Iterator var4 = this.getLocales().iterator();

      while(true) {
         ULocale locale;
         do {
            if (!var4.hasNext()) {
               return result;
            }

            locale = (ULocale)var4.next();
         } while(!this.isAvailableLocale(locale, 0));

         switch (type) {
            case 0:
               result = ULocale.getDisplayName(id, locale);
               break;
            case 1:
               result = ULocale.getDisplayLanguage(id, locale);
               break;
            case 2:
               result = ULocale.getDisplayScript("und-" + id, locale);
               break;
            case 3:
               result = ULocale.getDisplayCountry("und-" + id, locale);
               break;
            case 4:
               result = ULocale.getDisplayVariant("und-QQ-" + id, locale);
               break;
            case 5:
               result = ULocale.getDisplayKeyword(id, locale);
               break;
            case 6:
               String[] parts = new String[2];
               Utility.split(id, '=', parts);
               result = ULocale.getDisplayKeywordValue("und@" + id, parts[0], locale);
               if (result.equals(parts[1])) {
                  continue;
               }
               break;
            case 7:
            case 8:
               Currency temp = new Currency(id);
               result = temp.getName(locale, type == 7 ? 1 : 0, new boolean[1]);
               break;
            case 9:
               SimpleDateFormat dtf = new SimpleDateFormat("vvvv", locale);
               dtf.setTimeZone(TimeZone.getFrozenTimeZone(id));
               result = dtf.format(new Date());
               boolean isBadStr = false;
               String teststr = result;
               int sidx = result.indexOf(40);
               int eidx = result.indexOf(41);
               if (sidx != -1 && eidx != -1 && eidx - sidx == 3) {
                  teststr = result.substring(sidx + 1, eidx);
               }

               if (teststr.length() == 2) {
                  isBadStr = true;

                  for(int i = 0; i < 2; ++i) {
                     char c = teststr.charAt(i);
                     if (c < 'A' || 'Z' < c) {
                        isBadStr = false;
                        break;
                     }
                  }
               }

               if (isBadStr) {
                  continue;
               }
               break;
            default:
               throw new IllegalArgumentException("Unknown type: " + type);
         }

         if (!id.equals(result)) {
            return result;
         }
      }
   }

   public GlobalizationPreferences setDateFormat(int dateStyle, int timeStyle, DateFormat format) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         if (this.dateFormats == null) {
            this.dateFormats = new DateFormat[5][5];
         }

         this.dateFormats[dateStyle][timeStyle] = (DateFormat)format.clone();
         return this;
      }
   }

   public DateFormat getDateFormat(int dateStyle, int timeStyle) {
      if ((dateStyle != 4 || timeStyle != 4) && dateStyle >= 0 && dateStyle < 5 && timeStyle >= 0 && timeStyle < 5) {
         DateFormat result = null;
         if (this.dateFormats != null) {
            result = this.dateFormats[dateStyle][timeStyle];
         }

         if (result != null) {
            result = (DateFormat)result.clone();
            result.setTimeZone(this.getTimeZone());
         } else {
            result = this.guessDateFormat(dateStyle, timeStyle);
         }

         return result;
      } else {
         throw new IllegalArgumentException("Illegal date format style arguments");
      }
   }

   public NumberFormat getNumberFormat(int style) {
      if (style >= 0 && style < 5) {
         NumberFormat result = null;
         if (this.numberFormats != null) {
            result = this.numberFormats[style];
         }

         if (result != null) {
            result = (NumberFormat)result.clone();
         } else {
            result = this.guessNumberFormat(style);
         }

         return result;
      } else {
         throw new IllegalArgumentException("Illegal number format type");
      }
   }

   public GlobalizationPreferences setNumberFormat(int style, NumberFormat format) {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         if (this.numberFormats == null) {
            this.numberFormats = new NumberFormat[5];
         }

         this.numberFormats[style] = (NumberFormat)format.clone();
         return this;
      }
   }

   public GlobalizationPreferences reset() {
      if (this.isFrozen()) {
         throw new UnsupportedOperationException("Attempt to modify immutable object");
      } else {
         this.locales = null;
         this.territory = null;
         this.calendar = null;
         this.collator = null;
         this.breakIterators = null;
         this.timezone = null;
         this.currency = null;
         this.dateFormats = (DateFormat[][])null;
         this.numberFormats = null;
         this.implicitLocales = null;
         return this;
      }
   }

   protected List processLocales(List inputLocales) {
      List result = new ArrayList();

      int index;
      ULocale uloc;
      for(index = 0; index < inputLocales.size(); ++index) {
         uloc = (ULocale)inputLocales.get(index);
         String language = uloc.getLanguage();
         String script = uloc.getScript();
         String country = uloc.getCountry();
         String variant = uloc.getVariant();
         boolean bInserted = false;

         for(int j = 0; j < result.size(); ++j) {
            ULocale u = (ULocale)result.get(j);
            if (u.getLanguage().equals(language)) {
               String s = u.getScript();
               String c = u.getCountry();
               String v = u.getVariant();
               if (!s.equals(script)) {
                  if (s.length() == 0 && c.length() == 0 && v.length() == 0) {
                     result.add(j, uloc);
                     bInserted = true;
                     break;
                  }

                  if (s.length() == 0 && c.equals(country)) {
                     result.add(j, uloc);
                     bInserted = true;
                     break;
                  }

                  if (script.length() == 0 && country.length() > 0 && c.length() == 0) {
                     result.add(j, uloc);
                     bInserted = true;
                     break;
                  }
               } else {
                  if (!c.equals(country) && c.length() == 0 && v.length() == 0) {
                     result.add(j, uloc);
                     bInserted = true;
                     break;
                  }

                  if (!v.equals(variant) && v.length() == 0) {
                     result.add(j, uloc);
                     bInserted = true;
                     break;
                  }
               }
            }
         }

         if (!bInserted) {
            result.add(uloc);
         }
      }

      for(index = 0; index < result.size(); ++index) {
         uloc = (ULocale)result.get(index);

         while((uloc = uloc.getFallback()) != null && uloc.getLanguage().length() != 0) {
            ++index;
            result.add(index, uloc);
         }
      }

      index = 0;

      while(index < result.size() - 1) {
         uloc = (ULocale)result.get(index);
         boolean bRemoved = false;

         for(int i = index + 1; i < result.size(); ++i) {
            if (uloc.equals(result.get(i))) {
               result.remove(index);
               bRemoved = true;
               break;
            }
         }

         if (!bRemoved) {
            ++index;
         }
      }

      return result;
   }

   protected DateFormat guessDateFormat(int dateStyle, int timeStyle) {
      ULocale dfLocale = this.getAvailableLocale(2);
      if (dfLocale == null) {
         dfLocale = ULocale.ROOT;
      }

      DateFormat result;
      if (timeStyle == 4) {
         result = DateFormat.getDateInstance(this.getCalendar(), dateStyle, dfLocale);
      } else if (dateStyle == 4) {
         result = DateFormat.getTimeInstance(this.getCalendar(), timeStyle, dfLocale);
      } else {
         result = DateFormat.getDateTimeInstance(this.getCalendar(), dateStyle, timeStyle, dfLocale);
      }

      return result;
   }

   protected NumberFormat guessNumberFormat(int style) {
      ULocale nfLocale = this.getAvailableLocale(3);
      if (nfLocale == null) {
         nfLocale = ULocale.ROOT;
      }

      NumberFormat result;
      switch (style) {
         case 0:
            result = NumberFormat.getInstance(nfLocale);
            break;
         case 1:
            result = NumberFormat.getCurrencyInstance(nfLocale);
            result.setCurrency(this.getCurrency());
            break;
         case 2:
            result = NumberFormat.getPercentInstance(nfLocale);
            break;
         case 3:
            result = NumberFormat.getScientificInstance(nfLocale);
            break;
         case 4:
            result = NumberFormat.getIntegerInstance(nfLocale);
            break;
         default:
            throw new IllegalArgumentException("Unknown number format style");
      }

      return result;
   }

   protected String guessTerritory() {
      Iterator var1 = this.getLocales().iterator();

      String result;
      do {
         if (!var1.hasNext()) {
            ULocale firstLocale = this.getLocale(0);
            String language = firstLocale.getLanguage();
            String script = firstLocale.getScript();
            result = null;
            if (script.length() != 0) {
               result = (String)language_territory_hack_map.get(language + "_" + script);
            }

            if (result == null) {
               result = (String)language_territory_hack_map.get(language);
            }

            if (result == null) {
               result = "US";
            }

            return result;
         }

         ULocale locale = (ULocale)var1.next();
         result = locale.getCountry();
      } while(result.length() == 0);

      return result;
   }

   protected Currency guessCurrency() {
      return Currency.getInstance(new ULocale("und-" + this.getTerritory()));
   }

   protected List guessLocales() {
      if (this.implicitLocales == null) {
         List result = new ArrayList(1);
         result.add(ULocale.getDefault());
         this.implicitLocales = this.processLocales(result);
      }

      return this.implicitLocales;
   }

   protected Collator guessCollator() {
      ULocale collLocale = this.getAvailableLocale(4);
      if (collLocale == null) {
         collLocale = ULocale.ROOT;
      }

      return Collator.getInstance(collLocale);
   }

   protected BreakIterator guessBreakIterator(int type) {
      BreakIterator bitr = null;
      ULocale brkLocale = this.getAvailableLocale(5);
      if (brkLocale == null) {
         brkLocale = ULocale.ROOT;
      }

      switch (type) {
         case 0:
            bitr = BreakIterator.getCharacterInstance(brkLocale);
            break;
         case 1:
            bitr = BreakIterator.getWordInstance(brkLocale);
            break;
         case 2:
            bitr = BreakIterator.getLineInstance(brkLocale);
            break;
         case 3:
            bitr = BreakIterator.getSentenceInstance(brkLocale);
            break;
         case 4:
            bitr = BreakIterator.getTitleInstance(brkLocale);
            break;
         default:
            throw new IllegalArgumentException("Unknown break iterator type");
      }

      return bitr;
   }

   protected TimeZone guessTimeZone() {
      String timezoneString = (String)territory_tzid_hack_map.get(this.getTerritory());
      if (timezoneString == null) {
         String[] attempt = TimeZone.getAvailableIDs(this.getTerritory());
         if (attempt.length == 0) {
            timezoneString = "Etc/GMT";
         } else {
            int i;
            for(i = 0; i < attempt.length && attempt[i].indexOf("/") < 0; ++i) {
            }

            if (i > attempt.length) {
               i = 0;
            }

            timezoneString = attempt[i];
         }
      }

      return TimeZone.getTimeZone(timezoneString);
   }

   protected Calendar guessCalendar() {
      ULocale calLocale = this.getAvailableLocale(1);
      if (calLocale == null) {
         calLocale = ULocale.US;
      }

      return Calendar.getInstance(this.getTimeZone(), calLocale);
   }

   private ULocale getAvailableLocale(int type) {
      List locs = this.getLocales();
      ULocale result = null;

      for(int i = 0; i < locs.size(); ++i) {
         ULocale l = (ULocale)locs.get(i);
         if (this.isAvailableLocale(l, type)) {
            result = l;
            break;
         }
      }

      return result;
   }

   private boolean isAvailableLocale(ULocale loc, int type) {
      BitSet bits = (BitSet)available_locales.get(loc);
      return bits != null && bits.get(type);
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   public GlobalizationPreferences freeze() {
      this.frozen = true;
      return this;
   }

   public GlobalizationPreferences cloneAsThawed() {
      try {
         GlobalizationPreferences result = (GlobalizationPreferences)this.clone();
         result.frozen = false;
         return result;
      } catch (CloneNotSupportedException var2) {
         return null;
      }
   }

   static {
      ULocale[] allLocales = ULocale.getAvailableLocales();

      BitSet bits;
      for(int i = 0; i < allLocales.length; ++i) {
         bits = new BitSet(6);
         available_locales.put(allLocales[i], bits);
         bits.set(0);
      }

      ULocale[] calLocales = Calendar.getAvailableULocales();

      for(int i = 0; i < calLocales.length; ++i) {
         bits = (BitSet)available_locales.get(calLocales[i]);
         if (bits == null) {
            bits = new BitSet(6);
            available_locales.put(allLocales[i], bits);
         }

         bits.set(1);
      }

      ULocale[] dateLocales = DateFormat.getAvailableULocales();

      for(int i = 0; i < dateLocales.length; ++i) {
         bits = (BitSet)available_locales.get(dateLocales[i]);
         if (bits == null) {
            bits = new BitSet(6);
            available_locales.put(allLocales[i], bits);
         }

         bits.set(2);
      }

      ULocale[] numLocales = NumberFormat.getAvailableULocales();

      for(int i = 0; i < numLocales.length; ++i) {
         bits = (BitSet)available_locales.get(numLocales[i]);
         if (bits == null) {
            bits = new BitSet(6);
            available_locales.put(allLocales[i], bits);
         }

         bits.set(3);
      }

      ULocale[] collLocales = Collator.getAvailableULocales();

      for(int i = 0; i < collLocales.length; ++i) {
         bits = (BitSet)available_locales.get(collLocales[i]);
         if (bits == null) {
            bits = new BitSet(6);
            available_locales.put(allLocales[i], bits);
         }

         bits.set(4);
      }

      ULocale[] brkLocales = BreakIterator.getAvailableULocales();

      for(int i = 0; i < brkLocales.length; ++i) {
         bits = (BitSet)available_locales.get(brkLocales[i]);
         bits.set(5);
      }

      language_territory_hack_map = new HashMap();
      language_territory_hack = new String[][]{{"af", "ZA"}, {"am", "ET"}, {"ar", "SA"}, {"as", "IN"}, {"ay", "PE"}, {"az", "AZ"}, {"bal", "PK"}, {"be", "BY"}, {"bg", "BG"}, {"bn", "IN"}, {"bs", "BA"}, {"ca", "ES"}, {"ch", "MP"}, {"cpe", "SL"}, {"cs", "CZ"}, {"cy", "GB"}, {"da", "DK"}, {"de", "DE"}, {"dv", "MV"}, {"dz", "BT"}, {"el", "GR"}, {"en", "US"}, {"es", "ES"}, {"et", "EE"}, {"eu", "ES"}, {"fa", "IR"}, {"fi", "FI"}, {"fil", "PH"}, {"fj", "FJ"}, {"fo", "FO"}, {"fr", "FR"}, {"ga", "IE"}, {"gd", "GB"}, {"gl", "ES"}, {"gn", "PY"}, {"gu", "IN"}, {"gv", "GB"}, {"ha", "NG"}, {"he", "IL"}, {"hi", "IN"}, {"ho", "PG"}, {"hr", "HR"}, {"ht", "HT"}, {"hu", "HU"}, {"hy", "AM"}, {"id", "ID"}, {"is", "IS"}, {"it", "IT"}, {"ja", "JP"}, {"ka", "GE"}, {"kk", "KZ"}, {"kl", "GL"}, {"km", "KH"}, {"kn", "IN"}, {"ko", "KR"}, {"kok", "IN"}, {"ks", "IN"}, {"ku", "TR"}, {"ky", "KG"}, {"la", "VA"}, {"lb", "LU"}, {"ln", "CG"}, {"lo", "LA"}, {"lt", "LT"}, {"lv", "LV"}, {"mai", "IN"}, {"men", "GN"}, {"mg", "MG"}, {"mh", "MH"}, {"mk", "MK"}, {"ml", "IN"}, {"mn", "MN"}, {"mni", "IN"}, {"mo", "MD"}, {"mr", "IN"}, {"ms", "MY"}, {"mt", "MT"}, {"my", "MM"}, {"na", "NR"}, {"nb", "NO"}, {"nd", "ZA"}, {"ne", "NP"}, {"niu", "NU"}, {"nl", "NL"}, {"nn", "NO"}, {"no", "NO"}, {"nr", "ZA"}, {"nso", "ZA"}, {"ny", "MW"}, {"om", "KE"}, {"or", "IN"}, {"pa", "IN"}, {"pau", "PW"}, {"pl", "PL"}, {"ps", "PK"}, {"pt", "BR"}, {"qu", "PE"}, {"rn", "BI"}, {"ro", "RO"}, {"ru", "RU"}, {"rw", "RW"}, {"sd", "IN"}, {"sg", "CF"}, {"si", "LK"}, {"sk", "SK"}, {"sl", "SI"}, {"sm", "WS"}, {"so", "DJ"}, {"sq", "CS"}, {"sr", "CS"}, {"ss", "ZA"}, {"st", "ZA"}, {"sv", "SE"}, {"sw", "KE"}, {"ta", "IN"}, {"te", "IN"}, {"tem", "SL"}, {"tet", "TL"}, {"th", "TH"}, {"ti", "ET"}, {"tg", "TJ"}, {"tk", "TM"}, {"tkl", "TK"}, {"tvl", "TV"}, {"tl", "PH"}, {"tn", "ZA"}, {"to", "TO"}, {"tpi", "PG"}, {"tr", "TR"}, {"ts", "ZA"}, {"uk", "UA"}, {"ur", "IN"}, {"uz", "UZ"}, {"ve", "ZA"}, {"vi", "VN"}, {"wo", "SN"}, {"xh", "ZA"}, {"zh", "CN"}, {"zh_Hant", "TW"}, {"zu", "ZA"}, {"aa", "ET"}, {"byn", "ER"}, {"eo", "DE"}, {"gez", "ET"}, {"haw", "US"}, {"iu", "CA"}, {"kw", "GB"}, {"sa", "IN"}, {"sh", "HR"}, {"sid", "ET"}, {"syr", "SY"}, {"tig", "ER"}, {"tt", "RU"}, {"wal", "ET"}};

      int i;
      for(i = 0; i < language_territory_hack.length; ++i) {
         language_territory_hack_map.put(language_territory_hack[i][0], language_territory_hack[i][1]);
      }

      territory_tzid_hack_map = new HashMap();
      territory_tzid_hack = new String[][]{{"AQ", "Antarctica/McMurdo"}, {"AR", "America/Buenos_Aires"}, {"AU", "Australia/Sydney"}, {"BR", "America/Sao_Paulo"}, {"CA", "America/Toronto"}, {"CD", "Africa/Kinshasa"}, {"CL", "America/Santiago"}, {"CN", "Asia/Shanghai"}, {"EC", "America/Guayaquil"}, {"ES", "Europe/Madrid"}, {"GB", "Europe/London"}, {"GL", "America/Godthab"}, {"ID", "Asia/Jakarta"}, {"ML", "Africa/Bamako"}, {"MX", "America/Mexico_City"}, {"MY", "Asia/Kuala_Lumpur"}, {"NZ", "Pacific/Auckland"}, {"PT", "Europe/Lisbon"}, {"RU", "Europe/Moscow"}, {"UA", "Europe/Kiev"}, {"US", "America/New_York"}, {"UZ", "Asia/Tashkent"}, {"PF", "Pacific/Tahiti"}, {"FM", "Pacific/Kosrae"}, {"KI", "Pacific/Tarawa"}, {"KZ", "Asia/Almaty"}, {"MH", "Pacific/Majuro"}, {"MN", "Asia/Ulaanbaatar"}, {"SJ", "Arctic/Longyearbyen"}, {"UM", "Pacific/Midway"}};

      for(i = 0; i < territory_tzid_hack.length; ++i) {
         territory_tzid_hack_map.put(territory_tzid_hack[i][0], territory_tzid_hack[i][1]);
      }

   }
}
