package org.python.icu.impl.number;

import org.python.icu.impl.number.formatters.PositiveNegativeAffixFormat;
import org.python.icu.impl.number.modifiers.ConstantAffixModifier;
import org.python.icu.impl.number.modifiers.ConstantMultiFieldModifier;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.NumberFormat;

public class PNAffixGenerator {
   protected static final ThreadLocal threadLocalInstance = new ThreadLocal() {
      protected PNAffixGenerator initialValue() {
         return new PNAffixGenerator();
      }
   };
   private Result resultInstance = new Result();
   private NumberStringBuilder sb1 = new NumberStringBuilder();
   private NumberStringBuilder sb2 = new NumberStringBuilder();
   private NumberStringBuilder sb3 = new NumberStringBuilder();
   private NumberStringBuilder sb4 = new NumberStringBuilder();
   private NumberStringBuilder sb5 = new NumberStringBuilder();
   private NumberStringBuilder sb6 = new NumberStringBuilder();

   public static PNAffixGenerator getThreadLocalInstance() {
      return (PNAffixGenerator)threadLocalInstance.get();
   }

   public Result getModifiers(DecimalFormatSymbols symbols, PositiveNegativeAffixFormat.IProperties properties) {
      return this.getModifiers(symbols, symbols.getCurrencySymbol(), symbols.getInternationalCurrencySymbol(), symbols.getCurrencySymbol(), properties);
   }

   public Result getModifiers(DecimalFormatSymbols symbols, String currencySymbol, PositiveNegativeAffixFormat.IProperties properties) {
      return this.getModifiers(symbols, currencySymbol, currencySymbol, currencySymbol, properties);
   }

   public Result getModifiers(DecimalFormatSymbols symbols, String curr1, String curr2, String curr3, PositiveNegativeAffixFormat.IProperties properties) {
      if (properties.getSignAlwaysShown()) {
         return this.getModifiersWithPlusSign(symbols, curr1, curr2, curr3, properties);
      } else {
         String ppp = properties.getPositivePrefixPattern();
         String psp = properties.getPositiveSuffixPattern();
         String npp = properties.getNegativePrefixPattern();
         String nsp = properties.getNegativeSuffixPattern();
         this.sb1.clear();
         this.sb2.clear();
         AffixPatternUtils.unescape(ppp, symbols, curr1, curr2, curr3, (String)null, this.sb1);
         AffixPatternUtils.unescape(psp, symbols, curr1, curr2, curr3, (String)null, this.sb2);
         this.setPositiveResult(this.sb1, this.sb2, properties);
         if (npp == null && nsp == null) {
            this.sb1.insert(0, (CharSequence)symbols.getMinusSignString(), (NumberFormat.Field)NumberFormat.Field.SIGN);
         } else {
            this.sb1.clear();
            this.sb2.clear();
            AffixPatternUtils.unescape(npp, symbols, curr1, curr2, curr3, (String)null, this.sb1);
            AffixPatternUtils.unescape(nsp, symbols, curr1, curr2, curr3, (String)null, this.sb2);
         }

         this.setNegativeResult(this.sb1, this.sb2, properties);
         return this.resultInstance;
      }
   }

   private Result getModifiersWithPlusSign(DecimalFormatSymbols symbols, String curr1, String curr2, String curr3, PositiveNegativeAffixFormat.IProperties properties) {
      String ppp = properties.getPositivePrefixPattern();
      String psp = properties.getPositiveSuffixPattern();
      String npp = properties.getNegativePrefixPattern();
      String nsp = properties.getNegativeSuffixPattern();
      if (npp != null || nsp != null) {
         this.sb1.clear();
         this.sb2.clear();
         this.sb3.clear();
         this.sb4.clear();
         AffixPatternUtils.unescape(npp, symbols, curr1, curr2, curr3, (String)null, this.sb1);
         AffixPatternUtils.unescape(nsp, symbols, curr1, curr2, curr3, (String)null, this.sb2);
         AffixPatternUtils.unescape(npp, symbols, curr1, curr2, curr3, symbols.getPlusSignString(), this.sb3);
         AffixPatternUtils.unescape(nsp, symbols, curr1, curr2, curr3, symbols.getPlusSignString(), this.sb4);
         if (!charSequenceEquals(this.sb1, this.sb3) || !charSequenceEquals(this.sb2, this.sb4)) {
            this.setPositiveResult(this.sb3, this.sb4, properties);
            this.setNegativeResult(this.sb1, this.sb2, properties);
            return this.resultInstance;
         }

         this.setNegativeResult(this.sb1, this.sb2, properties);
      }

      this.sb1.clear();
      this.sb2.clear();
      AffixPatternUtils.unescape(ppp, symbols, curr1, curr2, curr3, (String)null, this.sb1);
      AffixPatternUtils.unescape(psp, symbols, curr1, curr2, curr3, (String)null, this.sb2);
      if (npp == null && nsp == null) {
         this.sb3.clear();
         this.sb3.append((CharSequence)symbols.getMinusSignString(), (NumberFormat.Field)NumberFormat.Field.SIGN);
         this.sb3.append(this.sb1);
         this.setNegativeResult(this.sb3, this.sb2, properties);
      }

      this.sb1.insert(0, (CharSequence)symbols.getPlusSignString(), (NumberFormat.Field)NumberFormat.Field.SIGN);
      this.setPositiveResult(this.sb1, this.sb2, properties);
      return this.resultInstance;
   }

   private void setPositiveResult(NumberStringBuilder prefix, NumberStringBuilder suffix, PositiveNegativeAffixFormat.IProperties properties) {
      String _prefix = properties.getPositivePrefix();
      String _suffix = properties.getPositiveSuffix();
      if (_prefix != null) {
         prefix = this.sb5.clear();
         prefix.append((CharSequence)_prefix, (NumberFormat.Field)null);
      }

      if (_suffix != null) {
         suffix = this.sb6.clear();
         suffix.append((CharSequence)_suffix, (NumberFormat.Field)null);
      }

      if (prefix.length() == 0 && suffix.length() == 0) {
         this.resultInstance.positive = ConstantAffixModifier.EMPTY;
      } else if (this.resultInstance.positive == null || !(this.resultInstance.positive instanceof ConstantMultiFieldModifier) || !((ConstantMultiFieldModifier)this.resultInstance.positive).contentEquals(prefix, suffix)) {
         this.resultInstance.positive = new ConstantMultiFieldModifier(prefix, suffix, false);
      }
   }

   private void setNegativeResult(NumberStringBuilder prefix, NumberStringBuilder suffix, PositiveNegativeAffixFormat.IProperties properties) {
      String _prefix = properties.getNegativePrefix();
      String _suffix = properties.getNegativeSuffix();
      if (_prefix != null) {
         prefix = this.sb5.clear();
         prefix.append((CharSequence)_prefix, (NumberFormat.Field)null);
      }

      if (_suffix != null) {
         suffix = this.sb6.clear();
         suffix.append((CharSequence)_suffix, (NumberFormat.Field)null);
      }

      if (prefix.length() == 0 && suffix.length() == 0) {
         this.resultInstance.negative = ConstantAffixModifier.EMPTY;
      } else if (this.resultInstance.negative == null || !(this.resultInstance.negative instanceof ConstantMultiFieldModifier) || !((ConstantMultiFieldModifier)this.resultInstance.negative).contentEquals(prefix, suffix)) {
         this.resultInstance.negative = new ConstantMultiFieldModifier(prefix, suffix, false);
      }
   }

   private static boolean charSequenceEquals(CharSequence a, CharSequence b) {
      if (a == b) {
         return true;
      } else if (a != null && b != null) {
         if (a.length() != b.length()) {
            return false;
         } else {
            for(int i = 0; i < a.length(); ++i) {
               if (a.charAt(i) != b.charAt(i)) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return false;
      }
   }

   public static class Result {
      public Modifier.AffixModifier positive = null;
      public Modifier.AffixModifier negative = null;
   }
}
