package org.python.icu.text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import org.python.icu.impl.CurrencyData;
import org.python.icu.util.ICUCloneNotSupportedException;
import org.python.icu.util.ULocale;

public class CurrencyPluralInfo implements Cloneable, Serializable {
   private static final long serialVersionUID = 1L;
   private static final char[] tripleCurrencySign = new char[]{'¤', '¤', '¤'};
   private static final String tripleCurrencyStr;
   private static final char[] defaultCurrencyPluralPatternChar;
   private static final String defaultCurrencyPluralPattern;
   private Map pluralCountToCurrencyUnitPattern = null;
   private PluralRules pluralRules = null;
   private ULocale ulocale = null;

   public CurrencyPluralInfo() {
      this.initialize(ULocale.getDefault(ULocale.Category.FORMAT));
   }

   public CurrencyPluralInfo(Locale locale) {
      this.initialize(ULocale.forLocale(locale));
   }

   public CurrencyPluralInfo(ULocale locale) {
      this.initialize(locale);
   }

   public static CurrencyPluralInfo getInstance() {
      return new CurrencyPluralInfo();
   }

   public static CurrencyPluralInfo getInstance(Locale locale) {
      return new CurrencyPluralInfo(locale);
   }

   public static CurrencyPluralInfo getInstance(ULocale locale) {
      return new CurrencyPluralInfo(locale);
   }

   public PluralRules getPluralRules() {
      return this.pluralRules;
   }

   public String getCurrencyPluralPattern(String pluralCount) {
      String currencyPluralPattern = (String)this.pluralCountToCurrencyUnitPattern.get(pluralCount);
      if (currencyPluralPattern == null) {
         if (!pluralCount.equals("other")) {
            currencyPluralPattern = (String)this.pluralCountToCurrencyUnitPattern.get("other");
         }

         if (currencyPluralPattern == null) {
            currencyPluralPattern = defaultCurrencyPluralPattern;
         }
      }

      return currencyPluralPattern;
   }

   public ULocale getLocale() {
      return this.ulocale;
   }

   public void setPluralRules(String ruleDescription) {
      this.pluralRules = PluralRules.createRules(ruleDescription);
   }

   public void setCurrencyPluralPattern(String pluralCount, String pattern) {
      this.pluralCountToCurrencyUnitPattern.put(pluralCount, pattern);
   }

   public void setLocale(ULocale loc) {
      this.ulocale = loc;
      this.initialize(loc);
   }

   public Object clone() {
      try {
         CurrencyPluralInfo other = (CurrencyPluralInfo)super.clone();
         other.ulocale = (ULocale)this.ulocale.clone();
         other.pluralCountToCurrencyUnitPattern = new HashMap();
         Iterator var2 = this.pluralCountToCurrencyUnitPattern.keySet().iterator();

         while(var2.hasNext()) {
            String pluralCount = (String)var2.next();
            String currencyPattern = (String)this.pluralCountToCurrencyUnitPattern.get(pluralCount);
            other.pluralCountToCurrencyUnitPattern.put(pluralCount, currencyPattern);
         }

         return other;
      } catch (CloneNotSupportedException var5) {
         throw new ICUCloneNotSupportedException(var5);
      }
   }

   public boolean equals(Object a) {
      if (!(a instanceof CurrencyPluralInfo)) {
         return false;
      } else {
         CurrencyPluralInfo other = (CurrencyPluralInfo)a;
         return this.pluralRules.equals(other.pluralRules) && this.pluralCountToCurrencyUnitPattern.equals(other.pluralCountToCurrencyUnitPattern);
      }
   }

   /** @deprecated */
   @Deprecated
   public int hashCode() {
      return this.pluralCountToCurrencyUnitPattern.hashCode() ^ this.pluralRules.hashCode() ^ this.ulocale.hashCode();
   }

   /** @deprecated */
   @Deprecated
   String select(double number) {
      return this.pluralRules.select(number);
   }

   /** @deprecated */
   @Deprecated
   String select(PluralRules.FixedDecimal numberInfo) {
      return this.pluralRules.select(numberInfo);
   }

   Iterator pluralPatternIterator() {
      return this.pluralCountToCurrencyUnitPattern.keySet().iterator();
   }

   private void initialize(ULocale uloc) {
      this.ulocale = uloc;
      this.pluralRules = PluralRules.forLocale(uloc);
      this.setupCurrencyPluralPattern(uloc);
   }

   private void setupCurrencyPluralPattern(ULocale uloc) {
      this.pluralCountToCurrencyUnitPattern = new HashMap();
      String numberStylePattern = NumberFormat.getPattern((ULocale)uloc, 0);
      int separatorIndex = numberStylePattern.indexOf(";");
      String negNumberPattern = null;
      if (separatorIndex != -1) {
         negNumberPattern = numberStylePattern.substring(separatorIndex + 1);
         numberStylePattern = numberStylePattern.substring(0, separatorIndex);
      }

      Map map = CurrencyData.provider.getInstance(uloc, true).getUnitPatterns();

      String pluralCount;
      String patternWithCurrencySign;
      for(Iterator var6 = map.entrySet().iterator(); var6.hasNext(); this.pluralCountToCurrencyUnitPattern.put(pluralCount, patternWithCurrencySign)) {
         Map.Entry e = (Map.Entry)var6.next();
         pluralCount = (String)e.getKey();
         String pattern = (String)e.getValue();
         String patternWithNumber = pattern.replace("{0}", numberStylePattern);
         patternWithCurrencySign = patternWithNumber.replace("{1}", tripleCurrencyStr);
         if (separatorIndex != -1) {
            String negWithNumber = pattern.replace("{0}", negNumberPattern);
            String negWithCurrSign = negWithNumber.replace("{1}", tripleCurrencyStr);
            StringBuilder posNegPatterns = new StringBuilder(patternWithCurrencySign);
            posNegPatterns.append(";");
            posNegPatterns.append(negWithCurrSign);
            patternWithCurrencySign = posNegPatterns.toString();
         }
      }

   }

   static {
      tripleCurrencyStr = new String(tripleCurrencySign);
      defaultCurrencyPluralPatternChar = new char[]{'\u0000', '.', '#', '#', ' ', '¤', '¤', '¤'};
      defaultCurrencyPluralPattern = new String(defaultCurrencyPluralPatternChar);
   }
}
