package org.python.icu.impl.number.formatters;

import org.python.icu.impl.number.Rounder;
import org.python.icu.impl.number.rounders.IncrementRounder;
import org.python.icu.impl.number.rounders.MagnitudeRounder;
import org.python.icu.impl.number.rounders.NoRounder;
import org.python.icu.impl.number.rounders.SignificantDigitsRounder;

public class RoundingFormat {
   public static Rounder getDefaultOrNoRounder(IProperties properties) {
      Rounder candidate = getDefaultOrNull(properties);
      if (candidate == null) {
         candidate = NoRounder.getInstance(properties);
      }

      return (Rounder)candidate;
   }

   public static Rounder getDefaultOrNull(IProperties properties) {
      if (SignificantDigitsRounder.useSignificantDigits(properties)) {
         return SignificantDigitsRounder.getInstance(properties);
      } else if (IncrementRounder.useRoundingIncrement(properties)) {
         return IncrementRounder.getInstance(properties);
      } else {
         return MagnitudeRounder.useFractionFormat(properties) ? MagnitudeRounder.getInstance(properties) : null;
      }
   }

   public interface IProperties extends Rounder.IBasicRoundingProperties, IncrementRounder.IProperties, MagnitudeRounder.IProperties, SignificantDigitsRounder.IProperties {
   }
}
