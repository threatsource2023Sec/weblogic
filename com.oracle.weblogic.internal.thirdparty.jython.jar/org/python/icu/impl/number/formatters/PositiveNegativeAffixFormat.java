package org.python.icu.impl.number.formatters;

import org.python.icu.impl.number.FormatQuantity;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.PNAffixGenerator;
import org.python.icu.impl.number.modifiers.PositiveNegativeAffixModifier;
import org.python.icu.text.DecimalFormatSymbols;

public class PositiveNegativeAffixFormat {
   public static PositiveNegativeAffixModifier getInstance(DecimalFormatSymbols symbols, IProperties properties) {
      PNAffixGenerator pnag = PNAffixGenerator.getThreadLocalInstance();
      PNAffixGenerator.Result result = pnag.getModifiers(symbols, properties);
      return new PositiveNegativeAffixModifier(result.positive, result.negative);
   }

   public static void apply(FormatQuantity input, ModifierHolder mods, DecimalFormatSymbols symbols, IProperties properties) {
      PNAffixGenerator pnag = PNAffixGenerator.getThreadLocalInstance();
      PNAffixGenerator.Result result = pnag.getModifiers(symbols, properties);
      if (input.isNegative()) {
         mods.add(result.negative);
      } else {
         mods.add(result.positive);
      }

   }

   public interface IProperties {
      String DEFAULT_POSITIVE_PREFIX = null;
      String DEFAULT_POSITIVE_SUFFIX = null;
      String DEFAULT_NEGATIVE_PREFIX = null;
      String DEFAULT_NEGATIVE_SUFFIX = null;
      String DEFAULT_POSITIVE_PREFIX_PATTERN = null;
      String DEFAULT_POSITIVE_SUFFIX_PATTERN = null;
      String DEFAULT_NEGATIVE_PREFIX_PATTERN = null;
      String DEFAULT_NEGATIVE_SUFFIX_PATTERN = null;
      boolean DEFAULT_SIGN_ALWAYS_SHOWN = false;

      String getPositivePrefix();

      IProperties setPositivePrefix(String var1);

      String getPositiveSuffix();

      IProperties setPositiveSuffix(String var1);

      String getNegativePrefix();

      IProperties setNegativePrefix(String var1);

      String getNegativeSuffix();

      IProperties setNegativeSuffix(String var1);

      String getPositivePrefixPattern();

      IProperties setPositivePrefixPattern(String var1);

      String getPositiveSuffixPattern();

      IProperties setPositiveSuffixPattern(String var1);

      String getNegativePrefixPattern();

      IProperties setNegativePrefixPattern(String var1);

      String getNegativeSuffixPattern();

      IProperties setNegativeSuffixPattern(String var1);

      boolean getSignAlwaysShown();

      IProperties setSignAlwaysShown(boolean var1);
   }
}
