package org.python.icu.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import org.python.icu.impl.number.AffixPatternUtils;
import org.python.icu.impl.number.Endpoint;
import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.FormatQuantity4;
import org.python.icu.impl.number.Parse;
import org.python.icu.impl.number.PatternString;
import org.python.icu.impl.number.Properties;
import org.python.icu.impl.number.formatters.PaddingFormat;
import org.python.icu.impl.number.formatters.PositiveDecimalFormat;
import org.python.icu.impl.number.formatters.ScientificFormat;
import org.python.icu.impl.number.rounders.SignificantDigitsRounder;
import org.python.icu.math.MathContext;
import org.python.icu.util.Currency;
import org.python.icu.util.CurrencyAmount;
import org.python.icu.util.ULocale;

public class DecimalFormat extends NumberFormat {
   private static final long serialVersionUID = 864413376551465018L;
   private final int serialVersionOnStream;
   transient Properties properties;
   transient volatile DecimalFormatSymbols symbols;
   transient volatile Format.SingularFormat formatter;
   transient volatile Properties exportedProperties;
   private transient int icuMathContextForm;
   private static final ThreadLocal threadLocalProperties = new ThreadLocal() {
      protected Properties initialValue() {
         return new Properties();
      }
   };
   public static final int PAD_BEFORE_PREFIX = 0;
   public static final int PAD_AFTER_PREFIX = 1;
   public static final int PAD_BEFORE_SUFFIX = 2;
   public static final int PAD_AFTER_SUFFIX = 3;

   public DecimalFormat() {
      this.serialVersionOnStream = 5;
      this.icuMathContextForm = 0;
      ULocale def = ULocale.getDefault(ULocale.Category.FORMAT);
      String pattern = getPattern(def, 0);
      this.symbols = getDefaultSymbols();
      this.properties = new Properties();
      this.exportedProperties = new Properties();
      boolean ignorePatternRounding = AffixPatternUtils.hasCurrencySymbols(pattern);
      this.setPropertiesFromPattern(pattern, ignorePatternRounding);
      this.refreshFormatter();
   }

   public DecimalFormat(String pattern) {
      this.serialVersionOnStream = 5;
      this.icuMathContextForm = 0;
      this.symbols = getDefaultSymbols();
      this.properties = new Properties();
      this.exportedProperties = new Properties();
      boolean ignorePatternRounding = AffixPatternUtils.hasCurrencySymbols(pattern);
      this.setPropertiesFromPattern(pattern, ignorePatternRounding);
      this.refreshFormatter();
   }

   public DecimalFormat(String pattern, DecimalFormatSymbols symbols) {
      this.serialVersionOnStream = 5;
      this.icuMathContextForm = 0;
      this.symbols = (DecimalFormatSymbols)symbols.clone();
      this.properties = new Properties();
      this.exportedProperties = new Properties();
      boolean ignorePatternRounding = AffixPatternUtils.hasCurrencySymbols(pattern);
      this.setPropertiesFromPattern(pattern, ignorePatternRounding);
      this.refreshFormatter();
   }

   public DecimalFormat(String pattern, DecimalFormatSymbols symbols, CurrencyPluralInfo infoInput, int style) {
      this(pattern, symbols, style);
      this.properties.setCurrencyPluralInfo(infoInput);
      this.refreshFormatter();
   }

   DecimalFormat(String pattern, DecimalFormatSymbols symbols, int choice) {
      this.serialVersionOnStream = 5;
      this.icuMathContextForm = 0;
      this.symbols = (DecimalFormatSymbols)symbols.clone();
      this.properties = new Properties();
      this.exportedProperties = new Properties();
      if (choice != 1 && choice != 5 && choice != 7 && choice != 8 && choice != 9 && choice != 6 && !AffixPatternUtils.hasCurrencySymbols(pattern)) {
         this.setPropertiesFromPattern(pattern, false);
      } else {
         this.setPropertiesFromPattern(pattern, true);
      }

      this.refreshFormatter();
   }

   private static DecimalFormatSymbols getDefaultSymbols() {
      return DecimalFormatSymbols.getInstance();
   }

   public synchronized void applyPattern(String pattern) {
      this.setPropertiesFromPattern(pattern, false);
      this.properties.setPositivePrefix((String)null);
      this.properties.setNegativePrefix((String)null);
      this.properties.setPositiveSuffix((String)null);
      this.properties.setNegativeSuffix((String)null);
      this.properties.setCurrencyPluralInfo((CurrencyPluralInfo)null);
      this.refreshFormatter();
   }

   public synchronized void applyLocalizedPattern(String localizedPattern) {
      String pattern = PatternString.convertLocalized(localizedPattern, this.symbols, false);
      this.applyPattern(pattern);
   }

   public Object clone() {
      DecimalFormat other = (DecimalFormat)super.clone();
      other.symbols = (DecimalFormatSymbols)this.symbols.clone();
      other.properties = this.properties.clone();
      other.exportedProperties = new Properties();
      other.refreshFormatter();
      return other;
   }

   private synchronized void writeObject(ObjectOutputStream oos) throws IOException {
      oos.defaultWriteObject();
      oos.writeInt(0);
      oos.writeObject(this.properties);
      oos.writeObject(this.symbols);
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
      ObjectInputStream.GetField fieldGetter = ois.readFields();
      ObjectStreamField[] serializedFields = fieldGetter.getObjectStreamClass().getFields();
      int serialVersion = fieldGetter.get("serialVersionOnStream", -1);
      if (serialVersion > 5) {
         throw new IOException("Cannot deserialize newer com.ibm.icu.text.DecimalFormat (v" + serialVersion + ")");
      } else {
         if (serialVersion == 5) {
            if (serializedFields.length > 1) {
               throw new IOException("Too many fields when reading serial version 5");
            }

            ois.readInt();
            this.properties = (Properties)ois.readObject();
            this.symbols = (DecimalFormatSymbols)ois.readObject();
            this.exportedProperties = new Properties();
            this.refreshFormatter();
         } else {
            this.properties = new Properties();
            String pp = null;
            String ppp = null;
            String ps = null;
            String psp = null;
            String np = null;
            String npp = null;
            String ns = null;
            String nsp = null;
            ObjectStreamField[] var13 = serializedFields;
            int var14 = serializedFields.length;

            for(int var15 = 0; var15 < var14; ++var15) {
               ObjectStreamField field = var13[var15];
               String name = field.getName();
               if (name.equals("decimalSeparatorAlwaysShown")) {
                  this.setDecimalSeparatorAlwaysShown(fieldGetter.get("decimalSeparatorAlwaysShown", false));
               } else if (name.equals("exponentSignAlwaysShown")) {
                  this.setExponentSignAlwaysShown(fieldGetter.get("exponentSignAlwaysShown", false));
               } else if (name.equals("formatWidth")) {
                  this.setFormatWidth(fieldGetter.get("formatWidth", 0));
               } else if (name.equals("groupingSize")) {
                  this.setGroupingSize(fieldGetter.get("groupingSize", (byte)3));
               } else if (name.equals("groupingSize2")) {
                  this.setSecondaryGroupingSize(fieldGetter.get("groupingSize2", (byte)0));
               } else if (name.equals("maxSignificantDigits")) {
                  this.setMaximumSignificantDigits(fieldGetter.get("maxSignificantDigits", 6));
               } else if (name.equals("minExponentDigits")) {
                  this.setMinimumExponentDigits(fieldGetter.get("minExponentDigits", (byte)0));
               } else if (name.equals("minSignificantDigits")) {
                  this.setMinimumSignificantDigits(fieldGetter.get("minSignificantDigits", 1));
               } else if (name.equals("multiplier")) {
                  this.setMultiplier(fieldGetter.get("multiplier", 1));
               } else if (name.equals("pad")) {
                  this.setPadCharacter(fieldGetter.get("pad", ' '));
               } else if (name.equals("padPosition")) {
                  this.setPadPosition(fieldGetter.get("padPosition", 0));
               } else if (name.equals("parseBigDecimal")) {
                  this.setParseBigDecimal(fieldGetter.get("parseBigDecimal", false));
               } else if (name.equals("parseRequireDecimalPoint")) {
                  this.setDecimalPatternMatchRequired(fieldGetter.get("parseRequireDecimalPoint", false));
               } else if (name.equals("roundingMode")) {
                  this.setRoundingMode(fieldGetter.get("roundingMode", 0));
               } else if (name.equals("useExponentialNotation")) {
                  this.setScientificNotation(fieldGetter.get("useExponentialNotation", false));
               } else if (name.equals("useSignificantDigits")) {
                  this.setSignificantDigitsUsed(fieldGetter.get("useSignificantDigits", false));
               } else if (name.equals("currencyPluralInfo")) {
                  this.setCurrencyPluralInfo((CurrencyPluralInfo)fieldGetter.get("currencyPluralInfo", (Object)null));
               } else if (name.equals("mathContext")) {
                  this.setMathContextICU((MathContext)fieldGetter.get("mathContext", (Object)null));
               } else if (name.equals("negPrefixPattern")) {
                  npp = (String)fieldGetter.get("negPrefixPattern", (Object)null);
               } else if (name.equals("negSuffixPattern")) {
                  nsp = (String)fieldGetter.get("negSuffixPattern", (Object)null);
               } else if (name.equals("negativePrefix")) {
                  np = (String)fieldGetter.get("negativePrefix", (Object)null);
               } else if (name.equals("negativeSuffix")) {
                  ns = (String)fieldGetter.get("negativeSuffix", (Object)null);
               } else if (name.equals("posPrefixPattern")) {
                  ppp = (String)fieldGetter.get("posPrefixPattern", (Object)null);
               } else if (name.equals("posSuffixPattern")) {
                  psp = (String)fieldGetter.get("posSuffixPattern", (Object)null);
               } else if (name.equals("positivePrefix")) {
                  pp = (String)fieldGetter.get("positivePrefix", (Object)null);
               } else if (name.equals("positiveSuffix")) {
                  ps = (String)fieldGetter.get("positiveSuffix", (Object)null);
               } else if (name.equals("roundingIncrement")) {
                  this.setRoundingIncrement((BigDecimal)fieldGetter.get("roundingIncrement", (Object)null));
               } else if (name.equals("symbols")) {
                  this.setDecimalFormatSymbols((DecimalFormatSymbols)fieldGetter.get("symbols", (Object)null));
               }
            }

            if (npp == null) {
               this.properties.setNegativePrefix(np);
            } else {
               this.properties.setNegativePrefixPattern(npp);
            }

            if (nsp == null) {
               this.properties.setNegativeSuffix(ns);
            } else {
               this.properties.setNegativeSuffixPattern(nsp);
            }

            if (ppp == null) {
               this.properties.setPositivePrefix(pp);
            } else {
               this.properties.setPositivePrefixPattern(ppp);
            }

            if (psp == null) {
               this.properties.setPositiveSuffix(ps);
            } else {
               this.properties.setPositiveSuffixPattern(psp);
            }

            try {
               java.lang.reflect.Field getter = NumberFormat.class.getDeclaredField("groupingUsed");
               getter.setAccessible(true);
               this.setGroupingUsed((Boolean)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("parseIntegerOnly");
               getter.setAccessible(true);
               this.setParseIntegerOnly((Boolean)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("maximumIntegerDigits");
               getter.setAccessible(true);
               this.setMaximumIntegerDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("minimumIntegerDigits");
               getter.setAccessible(true);
               this.setMinimumIntegerDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("maximumFractionDigits");
               getter.setAccessible(true);
               this.setMaximumFractionDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("minimumFractionDigits");
               getter.setAccessible(true);
               this.setMinimumFractionDigits((Integer)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("currency");
               getter.setAccessible(true);
               this.setCurrency((Currency)getter.get(this));
               getter = NumberFormat.class.getDeclaredField("parseStrict");
               getter.setAccessible(true);
               this.setParseStrict((Boolean)getter.get(this));
            } catch (IllegalArgumentException var18) {
               throw new IOException(var18);
            } catch (IllegalAccessException var19) {
               throw new IOException(var19);
            } catch (NoSuchFieldException var20) {
               throw new IOException(var20);
            } catch (SecurityException var21) {
               throw new IOException(var21);
            }

            if (this.symbols == null) {
               this.symbols = getDefaultSymbols();
            }

            this.exportedProperties = new Properties();
            this.refreshFormatter();
         }

      }
   }

   public StringBuffer format(double number, StringBuffer result, FieldPosition fieldPosition) {
      FormatQuantity4 fq = new FormatQuantity4(number);
      this.formatter.format(fq, result, fieldPosition);
      fq.populateUFieldPosition(fieldPosition);
      return result;
   }

   public StringBuffer format(long number, StringBuffer result, FieldPosition fieldPosition) {
      FormatQuantity4 fq = new FormatQuantity4(number);
      this.formatter.format(fq, result, fieldPosition);
      fq.populateUFieldPosition(fieldPosition);
      return result;
   }

   public StringBuffer format(BigInteger number, StringBuffer result, FieldPosition fieldPosition) {
      FormatQuantity4 fq = new FormatQuantity4(number);
      this.formatter.format(fq, result, fieldPosition);
      fq.populateUFieldPosition(fieldPosition);
      return result;
   }

   public StringBuffer format(BigDecimal number, StringBuffer result, FieldPosition fieldPosition) {
      FormatQuantity4 fq = new FormatQuantity4(number);
      this.formatter.format(fq, result, fieldPosition);
      fq.populateUFieldPosition(fieldPosition);
      return result;
   }

   public StringBuffer format(org.python.icu.math.BigDecimal number, StringBuffer result, FieldPosition fieldPosition) {
      FormatQuantity4 fq = new FormatQuantity4(number.toBigDecimal());
      this.formatter.format(fq, result, fieldPosition);
      fq.populateUFieldPosition(fieldPosition);
      return result;
   }

   public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
      if (!(obj instanceof Number)) {
         throw new IllegalArgumentException();
      } else {
         Number number = (Number)obj;
         FormatQuantity4 fq = new FormatQuantity4(number);
         AttributedCharacterIterator result = this.formatter.formatToCharacterIterator(fq);
         return result;
      }
   }

   public StringBuffer format(CurrencyAmount currAmt, StringBuffer toAppendTo, FieldPosition pos) {
      Properties cprops = (Properties)threadLocalProperties.get();
      Format.SingularFormat fmt = null;
      synchronized(this) {
         if (currAmt.getCurrency().equals(this.properties.getCurrency())) {
            fmt = this.formatter;
         } else {
            cprops.copyFrom(this.properties);
         }
      }

      if (fmt == null) {
         cprops.setCurrency(currAmt.getCurrency());
         fmt = Endpoint.fromBTA(cprops, this.symbols);
      }

      FormatQuantity4 fq = new FormatQuantity4(currAmt.getNumber());
      fmt.format(fq, toAppendTo, pos);
      fq.populateUFieldPosition(pos);
      return toAppendTo;
   }

   public Number parse(String text, ParsePosition parsePosition) {
      Properties pprops = (Properties)threadLocalProperties.get();
      synchronized(this) {
         pprops.copyFrom(this.properties);
      }

      Number result = Parse.parse(text, parsePosition, pprops, this.symbols);
      if (result instanceof BigDecimal) {
         result = new org.python.icu.math.BigDecimal((BigDecimal)result);
      }

      return (Number)result;
   }

   public CurrencyAmount parseCurrency(CharSequence text, ParsePosition parsePosition) {
      try {
         CurrencyAmount result = Parse.parseCurrency(text, parsePosition, this.properties, this.symbols);
         if (result == null) {
            return null;
         } else {
            Number number = result.getNumber();
            if (number instanceof BigDecimal) {
               Number number = new org.python.icu.math.BigDecimal((BigDecimal)number);
               result = new CurrencyAmount(number, result.getCurrency());
            }

            return result;
         }
      } catch (ParseException var5) {
         return null;
      }
   }

   public synchronized DecimalFormatSymbols getDecimalFormatSymbols() {
      return (DecimalFormatSymbols)this.symbols.clone();
   }

   public synchronized void setDecimalFormatSymbols(DecimalFormatSymbols newSymbols) {
      this.symbols = (DecimalFormatSymbols)newSymbols.clone();
      this.refreshFormatter();
   }

   public synchronized String getPositivePrefix() {
      String result = this.exportedProperties.getPositivePrefix();
      return result == null ? "" : result;
   }

   public synchronized void setPositivePrefix(String prefix) {
      this.properties.setPositivePrefix(prefix);
      this.refreshFormatter();
   }

   public synchronized String getNegativePrefix() {
      String result = this.exportedProperties.getNegativePrefix();
      return result == null ? "" : result;
   }

   public synchronized void setNegativePrefix(String suffix) {
      this.properties.setNegativePrefix(suffix);
      this.refreshFormatter();
   }

   public synchronized String getPositiveSuffix() {
      String result = this.exportedProperties.getPositiveSuffix();
      return result == null ? "" : result;
   }

   public synchronized void setPositiveSuffix(String suffix) {
      this.properties.setPositiveSuffix(suffix);
      this.refreshFormatter();
   }

   public synchronized String getNegativeSuffix() {
      String result = this.exportedProperties.getNegativeSuffix();
      return result == null ? "" : result;
   }

   public synchronized void setNegativeSuffix(String suffix) {
      this.properties.setNegativeSuffix(suffix);
      this.refreshFormatter();
   }

   /** @deprecated */
   @Deprecated
   public synchronized boolean getSignAlwaysShown() {
      return this.properties.getSignAlwaysShown();
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setSignAlwaysShown(boolean value) {
      this.properties.setSignAlwaysShown(value);
      this.refreshFormatter();
   }

   public synchronized int getMultiplier() {
      return this.properties.getMultiplier() != null ? this.properties.getMultiplier().intValue() : (int)Math.pow(10.0, (double)this.properties.getMagnitudeMultiplier());
   }

   public synchronized void setMultiplier(int multiplier) {
      if (multiplier == 0) {
         throw new IllegalArgumentException("Multiplier must be nonzero.");
      } else {
         int delta = 0;

         int temp;
         for(int value = multiplier; multiplier != 1; value = temp) {
            ++delta;
            temp = value / 10;
            if (temp * 10 != value) {
               delta = -1;
               break;
            }
         }

         if (delta != -1) {
            this.properties.setMagnitudeMultiplier(delta);
         } else {
            this.properties.setMultiplier(BigDecimal.valueOf((long)multiplier));
         }

         this.refreshFormatter();
      }
   }

   public synchronized BigDecimal getRoundingIncrement() {
      return this.exportedProperties.getRoundingIncrement();
   }

   public synchronized void setRoundingIncrement(BigDecimal increment) {
      if (increment != null && increment.compareTo(BigDecimal.ZERO) == 0) {
         this.properties.setMaximumFractionDigits(Integer.MAX_VALUE);
      } else {
         this.properties.setRoundingIncrement(increment);
         this.refreshFormatter();
      }
   }

   public synchronized void setRoundingIncrement(org.python.icu.math.BigDecimal increment) {
      BigDecimal javaBigDecimal = increment == null ? null : increment.toBigDecimal();
      this.setRoundingIncrement(javaBigDecimal);
   }

   public synchronized void setRoundingIncrement(double increment) {
      if (increment == 0.0) {
         this.setRoundingIncrement((BigDecimal)null);
      } else {
         BigDecimal javaBigDecimal = BigDecimal.valueOf(increment);
         this.setRoundingIncrement(javaBigDecimal);
      }

   }

   public synchronized int getRoundingMode() {
      RoundingMode mode = this.exportedProperties.getRoundingMode();
      return mode == null ? 0 : mode.ordinal();
   }

   public synchronized void setRoundingMode(int roundingMode) {
      this.properties.setRoundingMode(RoundingMode.valueOf(roundingMode));
      this.refreshFormatter();
   }

   public synchronized java.math.MathContext getMathContext() {
      java.math.MathContext mathContext = this.exportedProperties.getMathContext();

      assert mathContext != null;

      return mathContext;
   }

   public synchronized void setMathContext(java.math.MathContext mathContext) {
      this.properties.setMathContext(mathContext);
      this.refreshFormatter();
   }

   public synchronized MathContext getMathContextICU() {
      java.math.MathContext mathContext = this.getMathContext();
      return new MathContext(mathContext.getPrecision(), this.icuMathContextForm, false, mathContext.getRoundingMode().ordinal());
   }

   public synchronized void setMathContextICU(MathContext mathContextICU) {
      this.icuMathContextForm = mathContextICU.getForm();
      java.math.MathContext mathContext;
      if (mathContextICU.getLostDigits()) {
         mathContext = new java.math.MathContext(mathContextICU.getDigits(), RoundingMode.UNNECESSARY);
      } else {
         mathContext = new java.math.MathContext(mathContextICU.getDigits(), RoundingMode.valueOf(mathContextICU.getRoundingMode()));
      }

      this.setMathContext(mathContext);
   }

   public synchronized int getMinimumIntegerDigits() {
      return this.exportedProperties.getMinimumIntegerDigits();
   }

   public synchronized void setMinimumIntegerDigits(int value) {
      this.properties.setMinimumIntegerDigits(value);
      this.refreshFormatter();
   }

   public synchronized int getMaximumIntegerDigits() {
      return this.exportedProperties.getMaximumIntegerDigits();
   }

   public synchronized void setMaximumIntegerDigits(int value) {
      this.properties.setMaximumIntegerDigits(value);
      this.refreshFormatter();
   }

   public synchronized int getMinimumFractionDigits() {
      return this.exportedProperties.getMinimumFractionDigits();
   }

   public synchronized void setMinimumFractionDigits(int value) {
      this.properties.setMinimumFractionDigits(value);
      this.refreshFormatter();
   }

   public synchronized int getMaximumFractionDigits() {
      return this.exportedProperties.getMaximumFractionDigits();
   }

   public synchronized void setMaximumFractionDigits(int value) {
      this.properties.setMaximumFractionDigits(value);
      this.refreshFormatter();
   }

   public synchronized boolean areSignificantDigitsUsed() {
      return SignificantDigitsRounder.useSignificantDigits(this.properties);
   }

   public synchronized void setSignificantDigitsUsed(boolean useSignificantDigits) {
      if (useSignificantDigits) {
         this.properties.setMinimumSignificantDigits(1);
         this.properties.setMaximumSignificantDigits(6);
      } else {
         this.properties.setMinimumSignificantDigits(-1);
         this.properties.setMaximumSignificantDigits(-1);
         this.properties.setSignificantDigitsMode((SignificantDigitsMode)null);
      }

      this.refreshFormatter();
   }

   public synchronized int getMinimumSignificantDigits() {
      return this.exportedProperties.getMinimumSignificantDigits();
   }

   public synchronized void setMinimumSignificantDigits(int value) {
      this.properties.setMinimumSignificantDigits(value);
      this.refreshFormatter();
   }

   public synchronized int getMaximumSignificantDigits() {
      return this.exportedProperties.getMaximumSignificantDigits();
   }

   public synchronized void setMaximumSignificantDigits(int value) {
      this.properties.setMaximumSignificantDigits(value);
      this.refreshFormatter();
   }

   /** @deprecated */
   @Deprecated
   public synchronized SignificantDigitsMode getSignificantDigitsMode() {
      return this.exportedProperties.getSignificantDigitsMode();
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setSignificantDigitsMode(SignificantDigitsMode mode) {
      this.properties.setSignificantDigitsMode(mode);
      this.refreshFormatter();
   }

   public synchronized int getFormatWidth() {
      return this.exportedProperties.getFormatWidth();
   }

   public synchronized void setFormatWidth(int width) {
      this.properties.setFormatWidth(width);
      this.refreshFormatter();
   }

   public synchronized char getPadCharacter() {
      CharSequence paddingString = this.exportedProperties.getPadString();
      return paddingString == null ? '.' : paddingString.charAt(0);
   }

   public synchronized void setPadCharacter(char padChar) {
      this.properties.setPadString(Character.toString(padChar));
      this.refreshFormatter();
   }

   public synchronized int getPadPosition() {
      PaddingFormat.PadPosition loc = this.exportedProperties.getPadPosition();
      return loc == null ? 0 : loc.toOld();
   }

   public synchronized void setPadPosition(int padPos) {
      this.properties.setPadPosition(PaddingFormat.PadPosition.fromOld(padPos));
      this.refreshFormatter();
   }

   public synchronized boolean isScientificNotation() {
      return ScientificFormat.useScientificNotation(this.properties);
   }

   public synchronized void setScientificNotation(boolean useScientific) {
      if (useScientific) {
         this.properties.setMinimumExponentDigits(1);
      } else {
         this.properties.setMinimumExponentDigits(-1);
      }

      this.refreshFormatter();
   }

   public synchronized byte getMinimumExponentDigits() {
      return (byte)this.exportedProperties.getMinimumExponentDigits();
   }

   public synchronized void setMinimumExponentDigits(byte minExpDig) {
      this.properties.setMinimumExponentDigits(minExpDig);
      this.refreshFormatter();
   }

   public synchronized boolean isExponentSignAlwaysShown() {
      return this.exportedProperties.getExponentSignAlwaysShown();
   }

   public synchronized void setExponentSignAlwaysShown(boolean expSignAlways) {
      this.properties.setExponentSignAlwaysShown(expSignAlways);
      this.refreshFormatter();
   }

   public synchronized boolean isGroupingUsed() {
      return PositiveDecimalFormat.useGrouping(this.properties);
   }

   public synchronized void setGroupingUsed(boolean enabled) {
      if (enabled) {
         this.properties.setGroupingSize(3);
      } else {
         this.properties.setGroupingSize(-1);
         this.properties.setSecondaryGroupingSize(-1);
      }

      this.refreshFormatter();
   }

   public synchronized int getGroupingSize() {
      return this.exportedProperties.getGroupingSize();
   }

   public synchronized void setGroupingSize(int width) {
      this.properties.setGroupingSize(width);
      this.refreshFormatter();
   }

   public synchronized int getSecondaryGroupingSize() {
      return this.exportedProperties.getSecondaryGroupingSize();
   }

   public synchronized void setSecondaryGroupingSize(int width) {
      this.properties.setSecondaryGroupingSize(width);
      this.refreshFormatter();
   }

   /** @deprecated */
   @Deprecated
   public synchronized int getMinimumGroupingDigits() {
      return this.properties.getMinimumGroupingDigits();
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setMinimumGroupingDigits(int number) {
      this.properties.setMinimumGroupingDigits(number);
      this.refreshFormatter();
   }

   public synchronized boolean isDecimalSeparatorAlwaysShown() {
      return this.exportedProperties.getDecimalSeparatorAlwaysShown();
   }

   public synchronized void setDecimalSeparatorAlwaysShown(boolean value) {
      this.properties.setDecimalSeparatorAlwaysShown(value);
      this.refreshFormatter();
   }

   public synchronized Currency getCurrency() {
      return this.properties.getCurrency();
   }

   public synchronized void setCurrency(Currency currency) {
      this.properties.setCurrency(currency);
      if (currency != null) {
         this.symbols.setCurrency(currency);
         String symbol = currency.getName((ULocale)this.symbols.getULocale(), 0, (boolean[])null);
         this.symbols.setCurrencySymbol(symbol);
      }

      this.refreshFormatter();
   }

   public synchronized Currency.CurrencyUsage getCurrencyUsage() {
      Currency.CurrencyUsage usage = this.properties.getCurrencyUsage();
      if (usage == null) {
         usage = Currency.CurrencyUsage.STANDARD;
      }

      return usage;
   }

   public synchronized void setCurrencyUsage(Currency.CurrencyUsage usage) {
      this.properties.setCurrencyUsage(usage);
      this.refreshFormatter();
   }

   public synchronized CurrencyPluralInfo getCurrencyPluralInfo() {
      return this.properties.getCurrencyPluralInfo();
   }

   public synchronized void setCurrencyPluralInfo(CurrencyPluralInfo newInfo) {
      this.properties.setCurrencyPluralInfo(newInfo);
      this.refreshFormatter();
   }

   public synchronized boolean isParseBigDecimal() {
      return this.properties.getParseToBigDecimal();
   }

   public synchronized void setParseBigDecimal(boolean value) {
      this.properties.setParseToBigDecimal(value);
   }

   /** @deprecated */
   @Deprecated
   public int getParseMaxDigits() {
      return 1000;
   }

   /** @deprecated */
   @Deprecated
   public void setParseMaxDigits(int maxDigits) {
   }

   public synchronized boolean isParseStrict() {
      return this.properties.getParseMode() == Parse.ParseMode.STRICT;
   }

   public synchronized void setParseStrict(boolean parseStrict) {
      Parse.ParseMode mode = parseStrict ? Parse.ParseMode.STRICT : Parse.ParseMode.LENIENT;
      this.properties.setParseMode(mode);
   }

   public synchronized boolean isParseIntegerOnly() {
      return this.properties.getParseIntegerOnly();
   }

   public synchronized void setParseIntegerOnly(boolean parseIntegerOnly) {
      this.properties.setParseIntegerOnly(parseIntegerOnly);
   }

   public synchronized boolean isDecimalPatternMatchRequired() {
      return this.properties.getDecimalPatternMatchRequired();
   }

   public synchronized void setDecimalPatternMatchRequired(boolean value) {
      this.properties.setDecimalPatternMatchRequired(value);
      this.refreshFormatter();
   }

   /** @deprecated */
   @Deprecated
   public synchronized boolean getParseNoExponent() {
      return this.properties.getParseNoExponent();
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setParseNoExponent(boolean value) {
      this.properties.setParseNoExponent(value);
      this.refreshFormatter();
   }

   /** @deprecated */
   @Deprecated
   public synchronized boolean getParseCaseSensitive() {
      return this.properties.getParseCaseSensitive();
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setParseCaseSensitive(boolean value) {
      this.properties.setParseCaseSensitive(value);
      this.refreshFormatter();
   }

   public synchronized boolean equals(Object obj) {
      if (obj == null) {
         return false;
      } else if (obj == this) {
         return true;
      } else if (!(obj instanceof DecimalFormat)) {
         return false;
      } else {
         DecimalFormat other = (DecimalFormat)obj;
         return this.properties.equals(other.properties) && this.symbols.equals(other.symbols);
      }
   }

   public synchronized int hashCode() {
      return this.properties.hashCode() ^ this.symbols.hashCode();
   }

   public String toString() {
      StringBuilder result = new StringBuilder();
      result.append(this.getClass().getName());
      result.append("@");
      result.append(Integer.toHexString(this.hashCode()));
      result.append(" { symbols@");
      result.append(Integer.toHexString(this.symbols.hashCode()));
      synchronized(this) {
         this.properties.toStringBare(result);
      }

      result.append(" }");
      return result.toString();
   }

   public synchronized String toPattern() {
      Properties tprops = ((Properties)threadLocalProperties.get()).copyFrom(this.properties);
      if (org.python.icu.impl.number.formatters.CurrencyFormat.useCurrency(this.properties)) {
         tprops.setMinimumFractionDigits(this.exportedProperties.getMinimumFractionDigits());
         tprops.setMaximumFractionDigits(this.exportedProperties.getMaximumFractionDigits());
         tprops.setRoundingIncrement(this.exportedProperties.getRoundingIncrement());
      }

      return PatternString.propertiesToString(tprops);
   }

   public synchronized String toLocalizedPattern() {
      String pattern = this.toPattern();
      return PatternString.convertLocalized(pattern, this.symbols, true);
   }

   /** @deprecated */
   @Deprecated
   public PluralRules.IFixedDecimal getFixedDecimal(double number) {
      FormatQuantity4 fq = new FormatQuantity4(number);
      this.formatter.format(fq);
      return fq;
   }

   void refreshFormatter() {
      if (this.exportedProperties != null) {
         this.formatter = Endpoint.fromBTA(this.properties, this.symbols);
         this.exportedProperties.clear();
         this.formatter.export(this.exportedProperties);
      }
   }

   void setPropertiesFromPattern(String pattern, boolean ignoreRounding) {
      PatternString.parseToExistingProperties(pattern, this.properties, ignoreRounding);
   }

   /** @deprecated */
   @Deprecated
   public synchronized void setProperties(PropertySetter func) {
      func.set(this.properties);
      this.refreshFormatter();
   }

   /** @deprecated */
   @Deprecated
   public static enum SignificantDigitsMode {
      /** @deprecated */
      @Deprecated
      OVERRIDE_MAXIMUM_FRACTION,
      /** @deprecated */
      @Deprecated
      RESPECT_MAXIMUM_FRACTION,
      /** @deprecated */
      @Deprecated
      ENSURE_MINIMUM_SIGNIFICANT;
   }

   /** @deprecated */
   @Deprecated
   public interface PropertySetter {
      /** @deprecated */
      @Deprecated
      void set(Properties var1);
   }
}
