package org.python.icu.impl.number;

import java.math.BigDecimal;
import java.math.MathContext;
import org.python.icu.impl.StandardPlural;
import org.python.icu.text.PluralRules;

public interface FormatQuantity extends PluralRules.IFixedDecimal {
   void setIntegerFractionLength(int var1, int var2, int var3, int var4);

   void roundToIncrement(BigDecimal var1, MathContext var2);

   void roundToMagnitude(int var1, MathContext var2);

   void roundToInfinity();

   void multiplyBy(BigDecimal var1);

   void adjustMagnitude(int var1);

   int getMagnitude() throws ArithmeticException;

   boolean isZero();

   boolean isNegative();

   boolean isInfinite();

   boolean isNaN();

   double toDouble();

   BigDecimal toBigDecimal();

   int maxRepresentableDigits();

   StandardPlural getStandardPlural(PluralRules var1);

   byte getDigit(int var1);

   int getUpperDisplayMagnitude();

   int getLowerDisplayMagnitude();

   FormatQuantity createCopy();

   void copyFrom(FormatQuantity var1);

   long getPositionFingerprint();
}
