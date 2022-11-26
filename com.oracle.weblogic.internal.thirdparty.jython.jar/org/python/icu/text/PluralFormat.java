package org.python.icu.text;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Locale;
import java.util.Map;
import org.python.icu.impl.Utility;
import org.python.icu.util.ULocale;

public class PluralFormat extends UFormat {
   private static final long serialVersionUID = 1L;
   private ULocale ulocale;
   private PluralRules pluralRules;
   private String pattern;
   private transient MessagePattern msgPattern;
   private Map parsedValues;
   private NumberFormat numberFormat;
   private transient double offset;
   private transient PluralSelectorAdapter pluralRulesWrapper;

   public PluralFormat() {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init((PluralRules)null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), (NumberFormat)null);
   }

   public PluralFormat(ULocale ulocale) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init((PluralRules)null, PluralRules.PluralType.CARDINAL, ulocale, (NumberFormat)null);
   }

   public PluralFormat(Locale locale) {
      this(ULocale.forLocale(locale));
   }

   public PluralFormat(PluralRules rules) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), (NumberFormat)null);
   }

   public PluralFormat(ULocale ulocale, PluralRules rules) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init(rules, PluralRules.PluralType.CARDINAL, ulocale, (NumberFormat)null);
   }

   public PluralFormat(Locale locale, PluralRules rules) {
      this(ULocale.forLocale(locale), rules);
   }

   public PluralFormat(ULocale ulocale, PluralRules.PluralType type) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init((PluralRules)null, type, ulocale, (NumberFormat)null);
   }

   public PluralFormat(Locale locale, PluralRules.PluralType type) {
      this(ULocale.forLocale(locale), type);
   }

   public PluralFormat(String pattern) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init((PluralRules)null, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), (NumberFormat)null);
      this.applyPattern(pattern);
   }

   public PluralFormat(ULocale ulocale, String pattern) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init((PluralRules)null, PluralRules.PluralType.CARDINAL, ulocale, (NumberFormat)null);
      this.applyPattern(pattern);
   }

   public PluralFormat(PluralRules rules, String pattern) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init(rules, PluralRules.PluralType.CARDINAL, ULocale.getDefault(ULocale.Category.FORMAT), (NumberFormat)null);
      this.applyPattern(pattern);
   }

   public PluralFormat(ULocale ulocale, PluralRules rules, String pattern) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init(rules, PluralRules.PluralType.CARDINAL, ulocale, (NumberFormat)null);
      this.applyPattern(pattern);
   }

   public PluralFormat(ULocale ulocale, PluralRules.PluralType type, String pattern) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init((PluralRules)null, type, ulocale, (NumberFormat)null);
      this.applyPattern(pattern);
   }

   PluralFormat(ULocale ulocale, PluralRules.PluralType type, String pattern, NumberFormat numberFormat) {
      this.ulocale = null;
      this.pluralRules = null;
      this.pattern = null;
      this.parsedValues = null;
      this.numberFormat = null;
      this.offset = 0.0;
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.init((PluralRules)null, type, ulocale, numberFormat);
      this.applyPattern(pattern);
   }

   private void init(PluralRules rules, PluralRules.PluralType type, ULocale locale, NumberFormat numberFormat) {
      this.ulocale = locale;
      this.pluralRules = rules == null ? PluralRules.forLocale(this.ulocale, type) : rules;
      this.resetPattern();
      this.numberFormat = numberFormat == null ? NumberFormat.getInstance(this.ulocale) : numberFormat;
   }

   private void resetPattern() {
      this.pattern = null;
      if (this.msgPattern != null) {
         this.msgPattern.clear();
      }

      this.offset = 0.0;
   }

   public void applyPattern(String pattern) {
      this.pattern = pattern;
      if (this.msgPattern == null) {
         this.msgPattern = new MessagePattern();
      }

      try {
         this.msgPattern.parsePluralStyle(pattern);
         this.offset = this.msgPattern.getPluralOffset(0);
      } catch (RuntimeException var3) {
         this.resetPattern();
         throw var3;
      }
   }

   public String toPattern() {
      return this.pattern;
   }

   static int findSubMessage(MessagePattern pattern, int partIndex, PluralSelector selector, Object context, double number) {
      int count = pattern.countParts();
      MessagePattern.Part part = pattern.getPart(partIndex);
      double offset;
      if (part.getType().hasNumericValue()) {
         offset = pattern.getNumericValue(part);
         ++partIndex;
      } else {
         offset = 0.0;
      }

      String keyword = null;
      boolean haveKeywordMatch = false;
      int msgStart = 0;

      do {
         part = pattern.getPart(partIndex++);
         MessagePattern.Part.Type type = part.getType();
         if (type == MessagePattern.Part.Type.ARG_LIMIT) {
            break;
         }

         assert type == MessagePattern.Part.Type.ARG_SELECTOR;

         if (pattern.getPartType(partIndex).hasNumericValue()) {
            part = pattern.getPart(partIndex++);
            if (number == pattern.getNumericValue(part)) {
               return partIndex;
            }
         } else if (!haveKeywordMatch) {
            if (pattern.partSubstringMatches(part, "other")) {
               if (msgStart == 0) {
                  msgStart = partIndex;
                  if (keyword != null && keyword.equals("other")) {
                     haveKeywordMatch = true;
                  }
               }
            } else {
               if (keyword == null) {
                  keyword = selector.select(context, number - offset);
                  if (msgStart != 0 && keyword.equals("other")) {
                     haveKeywordMatch = true;
                  }
               }

               if (!haveKeywordMatch && pattern.partSubstringMatches(part, keyword)) {
                  msgStart = partIndex;
                  haveKeywordMatch = true;
               }
            }
         }

         partIndex = pattern.getLimitPartIndex(partIndex);
         ++partIndex;
      } while(partIndex < count);

      return msgStart;
   }

   public final String format(double number) {
      return this.format(number, number);
   }

   public StringBuffer format(Object number, StringBuffer toAppendTo, FieldPosition pos) {
      if (!(number instanceof Number)) {
         throw new IllegalArgumentException("'" + number + "' is not a Number");
      } else {
         Number numberObject = (Number)number;
         toAppendTo.append(this.format(numberObject, numberObject.doubleValue()));
         return toAppendTo;
      }
   }

   private String format(Number numberObject, double number) {
      if (this.msgPattern != null && this.msgPattern.countParts() != 0) {
         double numberMinusOffset = number - this.offset;
         String numberString;
         if (this.offset == 0.0) {
            numberString = this.numberFormat.format((Object)numberObject);
         } else {
            numberString = this.numberFormat.format(numberMinusOffset);
         }

         Object dec;
         if (this.numberFormat instanceof DecimalFormat) {
            dec = ((DecimalFormat)this.numberFormat).getFixedDecimal(numberMinusOffset);
         } else {
            dec = new PluralRules.FixedDecimal(numberMinusOffset);
         }

         int partIndex = findSubMessage(this.msgPattern, 0, this.pluralRulesWrapper, dec, number);
         StringBuilder result = null;
         int prevIndex = this.msgPattern.getPart(partIndex).getLimit();

         while(true) {
            while(true) {
               ++partIndex;
               MessagePattern.Part part = this.msgPattern.getPart(partIndex);
               MessagePattern.Part.Type type = part.getType();
               int index = part.getIndex();
               if (type == MessagePattern.Part.Type.MSG_LIMIT) {
                  if (result == null) {
                     return this.pattern.substring(prevIndex, index);
                  }

                  return result.append(this.pattern, prevIndex, index).toString();
               }

               if (type != MessagePattern.Part.Type.REPLACE_NUMBER && (type != MessagePattern.Part.Type.SKIP_SYNTAX || !this.msgPattern.jdkAposMode())) {
                  if (type == MessagePattern.Part.Type.ARG_START) {
                     if (result == null) {
                        result = new StringBuilder();
                     }

                     result.append(this.pattern, prevIndex, index);
                     prevIndex = index;
                     partIndex = this.msgPattern.getLimitPartIndex(partIndex);
                     index = this.msgPattern.getPart(partIndex).getLimit();
                     MessagePattern.appendReducedApostrophes(this.pattern, prevIndex, index, result);
                     prevIndex = index;
                  }
               } else {
                  if (result == null) {
                     result = new StringBuilder();
                  }

                  result.append(this.pattern, prevIndex, index);
                  if (type == MessagePattern.Part.Type.REPLACE_NUMBER) {
                     result.append(numberString);
                  }

                  prevIndex = part.getLimit();
               }
            }
         }
      } else {
         return this.numberFormat.format((Object)numberObject);
      }
   }

   public Number parse(String text, ParsePosition parsePosition) {
      throw new UnsupportedOperationException();
   }

   public Object parseObject(String source, ParsePosition pos) {
      throw new UnsupportedOperationException();
   }

   String parseType(String source, RbnfLenientScanner scanner, FieldPosition pos) {
      if (this.msgPattern != null && this.msgPattern.countParts() != 0) {
         int partIndex = 0;
         int count = this.msgPattern.countParts();
         int startingAt = pos.getBeginIndex();
         if (startingAt < 0) {
            startingAt = 0;
         }

         String keyword = null;
         String matchedWord = null;
         int matchedIndex = -1;

         while(true) {
            MessagePattern.Part partStart;
            MessagePattern.Part partLimit;
            String currArg;
            int currMatchIndex;
            do {
               do {
                  do {
                     do {
                        do {
                           MessagePattern.Part partSelector;
                           do {
                              if (partIndex >= count) {
                                 if (keyword != null) {
                                    pos.setBeginIndex(matchedIndex);
                                    pos.setEndIndex(matchedIndex + matchedWord.length());
                                    return keyword;
                                 }

                                 pos.setBeginIndex(-1);
                                 pos.setEndIndex(-1);
                                 return null;
                              }

                              partSelector = this.msgPattern.getPart(partIndex++);
                           } while(partSelector.getType() != MessagePattern.Part.Type.ARG_SELECTOR);

                           partStart = this.msgPattern.getPart(partIndex++);
                        } while(partStart.getType() != MessagePattern.Part.Type.MSG_START);

                        partLimit = this.msgPattern.getPart(partIndex++);
                     } while(partLimit.getType() != MessagePattern.Part.Type.MSG_LIMIT);

                     currArg = this.pattern.substring(partStart.getLimit(), partLimit.getIndex());
                     if (scanner != null) {
                        int[] scannerMatchResult = scanner.findText(source, currArg, startingAt);
                        currMatchIndex = scannerMatchResult[0];
                     } else {
                        currMatchIndex = source.indexOf(currArg, startingAt);
                     }
                  } while(currMatchIndex < 0);
               } while(currMatchIndex < matchedIndex);
            } while(matchedWord != null && currArg.length() <= matchedWord.length());

            matchedIndex = currMatchIndex;
            matchedWord = currArg;
            keyword = this.pattern.substring(partStart.getLimit(), partLimit.getIndex());
         }
      } else {
         pos.setBeginIndex(-1);
         pos.setEndIndex(-1);
         return null;
      }
   }

   /** @deprecated */
   @Deprecated
   public void setLocale(ULocale ulocale) {
      if (ulocale == null) {
         ulocale = ULocale.getDefault(ULocale.Category.FORMAT);
      }

      this.init((PluralRules)null, PluralRules.PluralType.CARDINAL, ulocale, (NumberFormat)null);
   }

   public void setNumberFormat(NumberFormat format) {
      this.numberFormat = format;
   }

   public boolean equals(Object rhs) {
      if (this == rhs) {
         return true;
      } else if (rhs != null && this.getClass() == rhs.getClass()) {
         PluralFormat pf = (PluralFormat)rhs;
         return Utility.objectEquals(this.ulocale, pf.ulocale) && Utility.objectEquals(this.pluralRules, pf.pluralRules) && Utility.objectEquals(this.msgPattern, pf.msgPattern) && Utility.objectEquals(this.numberFormat, pf.numberFormat);
      } else {
         return false;
      }
   }

   public boolean equals(PluralFormat rhs) {
      return this.equals((Object)rhs);
   }

   public int hashCode() {
      return this.pluralRules.hashCode() ^ this.parsedValues.hashCode();
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("locale=" + this.ulocale);
      buf.append(", rules='" + this.pluralRules + "'");
      buf.append(", pattern='" + this.pattern + "'");
      buf.append(", format='" + this.numberFormat + "'");
      return buf.toString();
   }

   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
      in.defaultReadObject();
      this.pluralRulesWrapper = new PluralSelectorAdapter();
      this.parsedValues = null;
      if (this.pattern != null) {
         this.applyPattern(this.pattern);
      }

   }

   private final class PluralSelectorAdapter implements PluralSelector {
      private PluralSelectorAdapter() {
      }

      public String select(Object context, double number) {
         PluralRules.IFixedDecimal dec = (PluralRules.IFixedDecimal)context;

         assert dec.getPluralOperand(PluralRules.Operand.n) == Math.abs(number);

         return PluralFormat.this.pluralRules.select(dec);
      }

      // $FF: synthetic method
      PluralSelectorAdapter(Object x1) {
         this();
      }
   }

   interface PluralSelector {
      String select(Object var1, double var2);
   }
}
