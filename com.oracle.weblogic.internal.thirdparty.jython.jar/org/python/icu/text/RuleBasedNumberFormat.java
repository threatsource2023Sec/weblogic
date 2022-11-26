package org.python.icu.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Set;
import org.python.icu.impl.ICUDebug;
import org.python.icu.impl.ICUResourceBundle;
import org.python.icu.impl.PatternProps;
import org.python.icu.lang.UCharacter;
import org.python.icu.math.BigDecimal;
import org.python.icu.util.ULocale;
import org.python.icu.util.UResourceBundle;
import org.python.icu.util.UResourceBundleIterator;

public class RuleBasedNumberFormat extends NumberFormat {
   static final long serialVersionUID = -7664252765575395068L;
   public static final int SPELLOUT = 1;
   public static final int ORDINAL = 2;
   public static final int DURATION = 3;
   public static final int NUMBERING_SYSTEM = 4;
   private transient NFRuleSet[] ruleSets;
   private transient Map ruleSetsMap;
   private transient NFRuleSet defaultRuleSet;
   private ULocale locale;
   private int roundingMode;
   private transient RbnfLenientScannerProvider scannerProvider;
   private transient boolean lookedForScanner;
   private transient DecimalFormatSymbols decimalFormatSymbols;
   private transient DecimalFormat decimalFormat;
   private transient NFRule defaultInfinityRule;
   private transient NFRule defaultNaNRule;
   private boolean lenientParse;
   private transient String lenientParseRules;
   private transient String postProcessRules;
   private transient RBNFPostProcessor postProcessor;
   private Map ruleSetDisplayNames;
   private String[] publicRuleSetNames;
   private boolean capitalizationInfoIsSet;
   private boolean capitalizationForListOrMenu;
   private boolean capitalizationForStandAlone;
   private transient BreakIterator capitalizationBrkIter;
   private static final boolean DEBUG = ICUDebug.enabled("rbnf");
   private static final String[] rulenames = new String[]{"SpelloutRules", "OrdinalRules", "DurationRules", "NumberingSystemRules"};
   private static final String[] locnames = new String[]{"SpelloutLocalizations", "OrdinalLocalizations", "DurationLocalizations", "NumberingSystemLocalizations"};
   private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(Long.MAX_VALUE);
   private static final BigDecimal MIN_VALUE = BigDecimal.valueOf(Long.MIN_VALUE);

   public RuleBasedNumberFormat(String description) {
      this.ruleSets = null;
      this.ruleSetsMap = null;
      this.defaultRuleSet = null;
      this.locale = null;
      this.roundingMode = 7;
      this.scannerProvider = null;
      this.decimalFormatSymbols = null;
      this.decimalFormat = null;
      this.defaultInfinityRule = null;
      this.defaultNaNRule = null;
      this.lenientParse = false;
      this.capitalizationInfoIsSet = false;
      this.capitalizationForListOrMenu = false;
      this.capitalizationForStandAlone = false;
      this.capitalizationBrkIter = null;
      this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
      this.init(description, (String[][])null);
   }

   public RuleBasedNumberFormat(String description, String[][] localizations) {
      this.ruleSets = null;
      this.ruleSetsMap = null;
      this.defaultRuleSet = null;
      this.locale = null;
      this.roundingMode = 7;
      this.scannerProvider = null;
      this.decimalFormatSymbols = null;
      this.decimalFormat = null;
      this.defaultInfinityRule = null;
      this.defaultNaNRule = null;
      this.lenientParse = false;
      this.capitalizationInfoIsSet = false;
      this.capitalizationForListOrMenu = false;
      this.capitalizationForStandAlone = false;
      this.capitalizationBrkIter = null;
      this.locale = ULocale.getDefault(ULocale.Category.FORMAT);
      this.init(description, localizations);
   }

   public RuleBasedNumberFormat(String description, Locale locale) {
      this(description, ULocale.forLocale(locale));
   }

   public RuleBasedNumberFormat(String description, ULocale locale) {
      this.ruleSets = null;
      this.ruleSetsMap = null;
      this.defaultRuleSet = null;
      this.locale = null;
      this.roundingMode = 7;
      this.scannerProvider = null;
      this.decimalFormatSymbols = null;
      this.decimalFormat = null;
      this.defaultInfinityRule = null;
      this.defaultNaNRule = null;
      this.lenientParse = false;
      this.capitalizationInfoIsSet = false;
      this.capitalizationForListOrMenu = false;
      this.capitalizationForStandAlone = false;
      this.capitalizationBrkIter = null;
      this.locale = locale;
      this.init(description, (String[][])null);
   }

   public RuleBasedNumberFormat(String description, String[][] localizations, ULocale locale) {
      this.ruleSets = null;
      this.ruleSetsMap = null;
      this.defaultRuleSet = null;
      this.locale = null;
      this.roundingMode = 7;
      this.scannerProvider = null;
      this.decimalFormatSymbols = null;
      this.decimalFormat = null;
      this.defaultInfinityRule = null;
      this.defaultNaNRule = null;
      this.lenientParse = false;
      this.capitalizationInfoIsSet = false;
      this.capitalizationForListOrMenu = false;
      this.capitalizationForStandAlone = false;
      this.capitalizationBrkIter = null;
      this.locale = locale;
      this.init(description, localizations);
   }

   public RuleBasedNumberFormat(Locale locale, int format) {
      this(ULocale.forLocale(locale), format);
   }

   public RuleBasedNumberFormat(ULocale locale, int format) {
      this.ruleSets = null;
      this.ruleSetsMap = null;
      this.defaultRuleSet = null;
      this.locale = null;
      this.roundingMode = 7;
      this.scannerProvider = null;
      this.decimalFormatSymbols = null;
      this.decimalFormat = null;
      this.defaultInfinityRule = null;
      this.defaultNaNRule = null;
      this.lenientParse = false;
      this.capitalizationInfoIsSet = false;
      this.capitalizationForListOrMenu = false;
      this.capitalizationForStandAlone = false;
      this.capitalizationBrkIter = null;
      this.locale = locale;
      ICUResourceBundle bundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b/rbnf", locale);
      ULocale uloc = bundle.getULocale();
      this.setLocale(uloc, uloc);
      StringBuilder description = new StringBuilder();
      String[][] localizations = (String[][])null;

      ICUResourceBundle locNamesBundle;
      try {
         locNamesBundle = bundle.getWithFallback("RBNFRules/" + rulenames[format - 1]);
         UResourceBundleIterator it = locNamesBundle.getIterator();

         while(it.hasNext()) {
            description.append(it.nextString());
         }
      } catch (MissingResourceException var9) {
      }

      locNamesBundle = bundle.findTopLevel(locnames[format - 1]);
      if (locNamesBundle != null) {
         localizations = new String[locNamesBundle.getSize()][];

         for(int i = 0; i < localizations.length; ++i) {
            localizations[i] = locNamesBundle.get(i).getStringArray();
         }
      }

      this.init(description.toString(), localizations);
   }

   public RuleBasedNumberFormat(int format) {
      this(ULocale.getDefault(ULocale.Category.FORMAT), format);
   }

   public Object clone() {
      return super.clone();
   }

   public boolean equals(Object that) {
      if (!(that instanceof RuleBasedNumberFormat)) {
         return false;
      } else {
         RuleBasedNumberFormat that2 = (RuleBasedNumberFormat)that;
         if (this.locale.equals(that2.locale) && this.lenientParse == that2.lenientParse) {
            if (this.ruleSets.length != that2.ruleSets.length) {
               return false;
            } else {
               for(int i = 0; i < this.ruleSets.length; ++i) {
                  if (!this.ruleSets[i].equals(that2.ruleSets[i])) {
                     return false;
                  }
               }

               return true;
            }
         } else {
            return false;
         }
      }
   }

   /** @deprecated */
   @Deprecated
   public int hashCode() {
      return super.hashCode();
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      NFRuleSet[] var2 = this.ruleSets;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         NFRuleSet ruleSet = var2[var4];
         result.append(ruleSet.toString());
      }

      return result.toString();
   }

   private void writeObject(ObjectOutputStream out) throws IOException {
      out.writeUTF(this.toString());
      out.writeObject(this.locale);
      out.writeInt(this.roundingMode);
   }

   private void readObject(ObjectInputStream in) throws IOException {
      String description = in.readUTF();

      ULocale loc;
      try {
         loc = (ULocale)in.readObject();
      } catch (Exception var6) {
         loc = ULocale.getDefault(ULocale.Category.FORMAT);
      }

      try {
         this.roundingMode = in.readInt();
      } catch (Exception var5) {
      }

      RuleBasedNumberFormat temp = new RuleBasedNumberFormat(description, loc);
      this.ruleSets = temp.ruleSets;
      this.ruleSetsMap = temp.ruleSetsMap;
      this.defaultRuleSet = temp.defaultRuleSet;
      this.publicRuleSetNames = temp.publicRuleSetNames;
      this.decimalFormatSymbols = temp.decimalFormatSymbols;
      this.decimalFormat = temp.decimalFormat;
      this.locale = temp.locale;
      this.defaultInfinityRule = temp.defaultInfinityRule;
      this.defaultNaNRule = temp.defaultNaNRule;
   }

   public String[] getRuleSetNames() {
      return (String[])this.publicRuleSetNames.clone();
   }

   public ULocale[] getRuleSetDisplayNameLocales() {
      if (this.ruleSetDisplayNames == null) {
         return null;
      } else {
         Set s = this.ruleSetDisplayNames.keySet();
         String[] locales = (String[])s.toArray(new String[s.size()]);
         Arrays.sort(locales, String.CASE_INSENSITIVE_ORDER);
         ULocale[] result = new ULocale[locales.length];

         for(int i = 0; i < locales.length; ++i) {
            result[i] = new ULocale(locales[i]);
         }

         return result;
      }
   }

   private String[] getNameListForLocale(ULocale loc) {
      if (loc != null && this.ruleSetDisplayNames != null) {
         String[] localeNames = new String[]{loc.getBaseName(), ULocale.getDefault(ULocale.Category.DISPLAY).getBaseName()};
         String[] var3 = localeNames;
         int var4 = localeNames.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            for(String lname = var3[var5]; lname.length() > 0; lname = ULocale.getFallback(lname)) {
               String[] names = (String[])this.ruleSetDisplayNames.get(lname);
               if (names != null) {
                  return names;
               }
            }
         }
      }

      return null;
   }

   public String[] getRuleSetDisplayNames(ULocale loc) {
      String[] names = this.getNameListForLocale(loc);
      if (names != null) {
         return (String[])names.clone();
      } else {
         names = this.getRuleSetNames();

         for(int i = 0; i < names.length; ++i) {
            names[i] = names[i].substring(1);
         }

         return names;
      }
   }

   public String[] getRuleSetDisplayNames() {
      return this.getRuleSetDisplayNames(ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public String getRuleSetDisplayName(String ruleSetName, ULocale loc) {
      String[] rsnames = this.publicRuleSetNames;

      for(int ix = 0; ix < rsnames.length; ++ix) {
         if (rsnames[ix].equals(ruleSetName)) {
            String[] names = this.getNameListForLocale(loc);
            if (names != null) {
               return names[ix];
            }

            return rsnames[ix].substring(1);
         }
      }

      throw new IllegalArgumentException("unrecognized rule set name: " + ruleSetName);
   }

   public String getRuleSetDisplayName(String ruleSetName) {
      return this.getRuleSetDisplayName(ruleSetName, ULocale.getDefault(ULocale.Category.DISPLAY));
   }

   public String format(double number, String ruleSet) throws IllegalArgumentException {
      if (ruleSet.startsWith("%%")) {
         throw new IllegalArgumentException("Can't use internal rule set");
      } else {
         return this.adjustForContext(this.format(number, this.findRuleSet(ruleSet)));
      }
   }

   public String format(long number, String ruleSet) throws IllegalArgumentException {
      if (ruleSet.startsWith("%%")) {
         throw new IllegalArgumentException("Can't use internal rule set");
      } else {
         return this.adjustForContext(this.format(number, this.findRuleSet(ruleSet)));
      }
   }

   public StringBuffer format(double number, StringBuffer toAppendTo, FieldPosition ignore) {
      if (toAppendTo.length() == 0) {
         toAppendTo.append(this.adjustForContext(this.format(number, this.defaultRuleSet)));
      } else {
         toAppendTo.append(this.format(number, this.defaultRuleSet));
      }

      return toAppendTo;
   }

   public StringBuffer format(long number, StringBuffer toAppendTo, FieldPosition ignore) {
      if (toAppendTo.length() == 0) {
         toAppendTo.append(this.adjustForContext(this.format(number, this.defaultRuleSet)));
      } else {
         toAppendTo.append(this.format(number, this.defaultRuleSet));
      }

      return toAppendTo;
   }

   public StringBuffer format(BigInteger number, StringBuffer toAppendTo, FieldPosition pos) {
      return this.format(new BigDecimal(number), toAppendTo, pos);
   }

   public StringBuffer format(java.math.BigDecimal number, StringBuffer toAppendTo, FieldPosition pos) {
      return this.format(new BigDecimal(number), toAppendTo, pos);
   }

   public StringBuffer format(BigDecimal number, StringBuffer toAppendTo, FieldPosition pos) {
      if (MIN_VALUE.compareTo(number) <= 0 && MAX_VALUE.compareTo(number) >= 0) {
         return number.scale() == 0 ? this.format(number.longValue(), toAppendTo, pos) : this.format(number.doubleValue(), toAppendTo, pos);
      } else {
         return this.getDecimalFormat().format(number, toAppendTo, pos);
      }
   }

   public Number parse(String text, ParsePosition parsePosition) {
      String workingText = text.substring(parsePosition.getIndex());
      ParsePosition workingPos = new ParsePosition(0);
      Number tempResult = null;
      Number result = NFRule.ZERO;
      ParsePosition highWaterMark = new ParsePosition(workingPos.getIndex());

      for(int i = this.ruleSets.length - 1; i >= 0; --i) {
         if (this.ruleSets[i].isPublic() && this.ruleSets[i].isParseable()) {
            tempResult = this.ruleSets[i].parse(workingText, workingPos, Double.MAX_VALUE);
            if (workingPos.getIndex() > highWaterMark.getIndex()) {
               result = tempResult;
               highWaterMark.setIndex(workingPos.getIndex());
            }

            if (highWaterMark.getIndex() == workingText.length()) {
               break;
            }

            workingPos.setIndex(0);
         }
      }

      parsePosition.setIndex(parsePosition.getIndex() + highWaterMark.getIndex());
      return (Number)result;
   }

   public void setLenientParseMode(boolean enabled) {
      this.lenientParse = enabled;
   }

   public boolean lenientParseEnabled() {
      return this.lenientParse;
   }

   public void setLenientScannerProvider(RbnfLenientScannerProvider scannerProvider) {
      this.scannerProvider = scannerProvider;
   }

   public RbnfLenientScannerProvider getLenientScannerProvider() {
      if (this.scannerProvider == null && this.lenientParse && !this.lookedForScanner) {
         try {
            this.lookedForScanner = true;
            Class cls = Class.forName("org.python.icu.impl.text.RbnfScannerProviderImpl");
            RbnfLenientScannerProvider provider = (RbnfLenientScannerProvider)cls.newInstance();
            this.setLenientScannerProvider(provider);
         } catch (Exception var3) {
         }
      }

      return this.scannerProvider;
   }

   public void setDefaultRuleSet(String ruleSetName) {
      if (ruleSetName != null) {
         if (ruleSetName.startsWith("%%")) {
            throw new IllegalArgumentException("cannot use private rule set: " + ruleSetName);
         }

         this.defaultRuleSet = this.findRuleSet(ruleSetName);
      } else if (this.publicRuleSetNames.length > 0) {
         this.defaultRuleSet = this.findRuleSet(this.publicRuleSetNames[0]);
      } else {
         this.defaultRuleSet = null;
         int n = this.ruleSets.length;

         String currentName;
         do {
            --n;
            if (n < 0) {
               n = this.ruleSets.length;

               do {
                  --n;
                  if (n < 0) {
                     return;
                  }
               } while(!this.ruleSets[n].isPublic());

               this.defaultRuleSet = this.ruleSets[n];
               return;
            }

            currentName = this.ruleSets[n].getName();
         } while(!currentName.equals("%spellout-numbering") && !currentName.equals("%digits-ordinal") && !currentName.equals("%duration"));

         this.defaultRuleSet = this.ruleSets[n];
         return;
      }

   }

   public String getDefaultRuleSetName() {
      return this.defaultRuleSet != null && this.defaultRuleSet.isPublic() ? this.defaultRuleSet.getName() : "";
   }

   public void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
      if (newSymbols != null) {
         this.decimalFormatSymbols = (DecimalFormatSymbols)newSymbols.clone();
         if (this.decimalFormat != null) {
            this.decimalFormat.setDecimalFormatSymbols(this.decimalFormatSymbols);
         }

         if (this.defaultInfinityRule != null) {
            this.defaultInfinityRule = null;
            this.getDefaultInfinityRule();
         }

         if (this.defaultNaNRule != null) {
            this.defaultNaNRule = null;
            this.getDefaultNaNRule();
         }

         NFRuleSet[] var2 = this.ruleSets;
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            NFRuleSet ruleSet = var2[var4];
            ruleSet.setDecimalFormatSymbols(this.decimalFormatSymbols);
         }
      }

   }

   public void setContext(DisplayContext context) {
      super.setContext(context);
      if (!this.capitalizationInfoIsSet && (context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE)) {
         this.initCapitalizationContextInfo(this.locale);
         this.capitalizationInfoIsSet = true;
      }

      if (this.capitalizationBrkIter == null && (context == DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE || context == DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU && this.capitalizationForListOrMenu || context == DisplayContext.CAPITALIZATION_FOR_STANDALONE && this.capitalizationForStandAlone)) {
         this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
      }

   }

   public int getRoundingMode() {
      return this.roundingMode;
   }

   public void setRoundingMode(int roundingMode) {
      if (roundingMode >= 0 && roundingMode <= 7) {
         this.roundingMode = roundingMode;
      } else {
         throw new IllegalArgumentException("Invalid rounding mode: " + roundingMode);
      }
   }

   NFRuleSet getDefaultRuleSet() {
      return this.defaultRuleSet;
   }

   RbnfLenientScanner getLenientScanner() {
      if (this.lenientParse) {
         RbnfLenientScannerProvider provider = this.getLenientScannerProvider();
         if (provider != null) {
            return provider.get(this.locale, this.lenientParseRules);
         }
      }

      return null;
   }

   DecimalFormatSymbols getDecimalFormatSymbols() {
      if (this.decimalFormatSymbols == null) {
         this.decimalFormatSymbols = new DecimalFormatSymbols(this.locale);
      }

      return this.decimalFormatSymbols;
   }

   DecimalFormat getDecimalFormat() {
      if (this.decimalFormat == null) {
         String pattern = getPattern(this.locale, 0);
         this.decimalFormat = new DecimalFormat(pattern, this.getDecimalFormatSymbols());
      }

      return this.decimalFormat;
   }

   PluralFormat createPluralFormat(PluralRules.PluralType pluralType, String pattern) {
      return new PluralFormat(this.locale, pluralType, pattern, this.getDecimalFormat());
   }

   NFRule getDefaultInfinityRule() {
      if (this.defaultInfinityRule == null) {
         this.defaultInfinityRule = new NFRule(this, "Inf: " + this.getDecimalFormatSymbols().getInfinity());
      }

      return this.defaultInfinityRule;
   }

   NFRule getDefaultNaNRule() {
      if (this.defaultNaNRule == null) {
         this.defaultNaNRule = new NFRule(this, "NaN: " + this.getDecimalFormatSymbols().getNaN());
      }

      return this.defaultNaNRule;
   }

   private String extractSpecial(StringBuilder description, String specialName) {
      String result = null;
      int lp = description.indexOf(specialName);
      if (lp != -1 && (lp == 0 || description.charAt(lp - 1) == ';')) {
         int lpEnd = description.indexOf(";%", lp);
         if (lpEnd == -1) {
            lpEnd = description.length() - 1;
         }

         int lpStart;
         for(lpStart = lp + specialName.length(); lpStart < lpEnd && PatternProps.isWhiteSpace(description.charAt(lpStart)); ++lpStart) {
         }

         result = description.substring(lpStart, lpEnd);
         description.delete(lp, lpEnd + 1);
      }

      return result;
   }

   private void init(String description, String[][] localizations) {
      this.initLocalizations(localizations);
      StringBuilder descBuf = this.stripWhitespace(description);
      this.lenientParseRules = this.extractSpecial(descBuf, "%%lenient-parse:");
      this.postProcessRules = this.extractSpecial(descBuf, "%%post-process:");
      int numRuleSets = 1;

      int p;
      for(p = 0; (p = descBuf.indexOf(";%", p)) != -1; p += 2) {
         ++numRuleSets;
      }

      this.ruleSets = new NFRuleSet[numRuleSets];
      this.ruleSetsMap = new HashMap(numRuleSets * 2 + 1);
      this.defaultRuleSet = null;
      int publicRuleSetCount = 0;
      String[] ruleSetDescriptions = new String[numRuleSets];
      int curRuleSet = 0;

      for(int start = 0; curRuleSet < this.ruleSets.length; start = p + 1) {
         p = descBuf.indexOf(";%", start);
         if (p < 0) {
            p = descBuf.length() - 1;
         }

         ruleSetDescriptions[curRuleSet] = descBuf.substring(start, p + 1);
         NFRuleSet ruleSet = new NFRuleSet(this, ruleSetDescriptions, curRuleSet);
         this.ruleSets[curRuleSet] = ruleSet;
         String currentName = ruleSet.getName();
         this.ruleSetsMap.put(currentName, ruleSet);
         if (!currentName.startsWith("%%")) {
            ++publicRuleSetCount;
            if (this.defaultRuleSet == null && currentName.equals("%spellout-numbering") || currentName.equals("%digits-ordinal") || currentName.equals("%duration")) {
               this.defaultRuleSet = ruleSet;
            }
         }

         ++curRuleSet;
      }

      int i;
      if (this.defaultRuleSet == null) {
         for(i = this.ruleSets.length - 1; i >= 0; --i) {
            if (!this.ruleSets[i].getName().startsWith("%%")) {
               this.defaultRuleSet = this.ruleSets[i];
               break;
            }
         }
      }

      if (this.defaultRuleSet == null) {
         this.defaultRuleSet = this.ruleSets[this.ruleSets.length - 1];
      }

      for(i = 0; i < this.ruleSets.length; ++i) {
         this.ruleSets[i].parseRules(ruleSetDescriptions[i]);
      }

      String[] publicRuleSetTemp = new String[publicRuleSetCount];
      publicRuleSetCount = 0;

      int i;
      for(i = this.ruleSets.length - 1; i >= 0; --i) {
         if (!this.ruleSets[i].getName().startsWith("%%")) {
            publicRuleSetTemp[publicRuleSetCount++] = this.ruleSets[i].getName();
         }
      }

      if (this.publicRuleSetNames != null) {
         label63:
         for(i = 0; i < this.publicRuleSetNames.length; ++i) {
            String name = this.publicRuleSetNames[i];

            for(int j = 0; j < publicRuleSetTemp.length; ++j) {
               if (name.equals(publicRuleSetTemp[j])) {
                  continue label63;
               }
            }

            throw new IllegalArgumentException("did not find public rule set: " + name);
         }

         this.defaultRuleSet = this.findRuleSet(this.publicRuleSetNames[0]);
      } else {
         this.publicRuleSetNames = publicRuleSetTemp;
      }

   }

   private void initLocalizations(String[][] localizations) {
      if (localizations != null) {
         this.publicRuleSetNames = (String[])localizations[0].clone();
         Map m = new HashMap();

         for(int i = 1; i < localizations.length; ++i) {
            String[] data = localizations[i];
            String loc = data[0];
            String[] names = new String[data.length - 1];
            if (names.length != this.publicRuleSetNames.length) {
               throw new IllegalArgumentException("public name length: " + this.publicRuleSetNames.length + " != localized names[" + i + "] length: " + names.length);
            }

            System.arraycopy(data, 1, names, 0, names.length);
            m.put(loc, names);
         }

         if (!m.isEmpty()) {
            this.ruleSetDisplayNames = m;
         }
      }

   }

   private void initCapitalizationContextInfo(ULocale theLocale) {
      ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("org/python/icu/impl/data/icudt59b", theLocale);

      try {
         ICUResourceBundle rdb = rb.getWithFallback("contextTransforms/number-spellout");
         int[] intVector = rdb.getIntVector();
         if (intVector.length >= 2) {
            this.capitalizationForListOrMenu = intVector[0] != 0;
            this.capitalizationForStandAlone = intVector[1] != 0;
         }
      } catch (MissingResourceException var5) {
      }

   }

   private StringBuilder stripWhitespace(String description) {
      StringBuilder result = new StringBuilder();
      int descriptionLength = description.length();
      int start = 0;

      while(start < descriptionLength) {
         while(start < descriptionLength && PatternProps.isWhiteSpace(description.charAt(start))) {
            ++start;
         }

         if (start < descriptionLength && description.charAt(start) == ';') {
            ++start;
         } else {
            int p = description.indexOf(59, start);
            if (p == -1) {
               result.append(description.substring(start));
               break;
            }

            if (p >= descriptionLength) {
               break;
            }

            result.append(description.substring(start, p + 1));
            start = p + 1;
         }
      }

      return result;
   }

   private String format(double number, NFRuleSet ruleSet) {
      StringBuilder result = new StringBuilder();
      if (this.getRoundingMode() != 7) {
         number = (new BigDecimal(Double.toString(number))).setScale(this.getMaximumFractionDigits(), this.roundingMode).doubleValue();
      }

      ruleSet.format(number, result, 0, 0);
      this.postProcess(result, ruleSet);
      return result.toString();
   }

   private String format(long number, NFRuleSet ruleSet) {
      StringBuilder result = new StringBuilder();
      if (number == Long.MIN_VALUE) {
         result.append(this.getDecimalFormat().format(Long.MIN_VALUE));
      } else {
         ruleSet.format(number, result, 0, 0);
      }

      this.postProcess(result, ruleSet);
      return result.toString();
   }

   private void postProcess(StringBuilder result, NFRuleSet ruleSet) {
      if (this.postProcessRules != null) {
         if (this.postProcessor == null) {
            int ix = this.postProcessRules.indexOf(";");
            if (ix == -1) {
               ix = this.postProcessRules.length();
            }

            String ppClassName = this.postProcessRules.substring(0, ix).trim();

            try {
               Class cls = Class.forName(ppClassName);
               this.postProcessor = (RBNFPostProcessor)cls.newInstance();
               this.postProcessor.init(this, this.postProcessRules);
            } catch (Exception var6) {
               if (DEBUG) {
                  System.out.println("could not locate " + ppClassName + ", error " + var6.getClass().getName() + ", " + var6.getMessage());
               }

               this.postProcessor = null;
               this.postProcessRules = null;
               return;
            }
         }

         this.postProcessor.process(result, ruleSet);
      }

   }

   private String adjustForContext(String result) {
      DisplayContext capitalization = this.getContext(DisplayContext.Type.CAPITALIZATION);
      if (capitalization == DisplayContext.CAPITALIZATION_NONE || result == null || result.length() <= 0 || !UCharacter.isLowerCase(result.codePointAt(0)) || capitalization != DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE && (capitalization != DisplayContext.CAPITALIZATION_FOR_UI_LIST_OR_MENU || !this.capitalizationForListOrMenu) && (capitalization != DisplayContext.CAPITALIZATION_FOR_STANDALONE || !this.capitalizationForStandAlone)) {
         return result;
      } else {
         if (this.capitalizationBrkIter == null) {
            this.capitalizationBrkIter = BreakIterator.getSentenceInstance(this.locale);
         }

         return UCharacter.toTitleCase((ULocale)this.locale, result, this.capitalizationBrkIter, 768);
      }
   }

   NFRuleSet findRuleSet(String name) throws IllegalArgumentException {
      NFRuleSet result = (NFRuleSet)this.ruleSetsMap.get(name);
      if (result == null) {
         throw new IllegalArgumentException("No rule set named " + name);
      } else {
         return result;
      }
   }
}
