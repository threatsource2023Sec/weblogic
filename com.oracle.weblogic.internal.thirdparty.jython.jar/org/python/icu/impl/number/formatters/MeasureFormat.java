package org.python.icu.impl.number.formatters;

import java.util.Iterator;
import org.python.icu.impl.StandardPlural;
import org.python.icu.impl.number.modifiers.GeneralPluralModifier;
import org.python.icu.impl.number.modifiers.SimpleModifier;
import org.python.icu.text.DecimalFormatSymbols;
import org.python.icu.text.NumberFormat;
import org.python.icu.util.MeasureUnit;
import org.python.icu.util.ULocale;

public class MeasureFormat {
   public static boolean useMeasureFormat(IProperties properties) {
      return properties.getMeasureUnit() != MeasureFormat.IProperties.DEFAULT_MEASURE_UNIT;
   }

   public static GeneralPluralModifier getInstance(DecimalFormatSymbols symbols, IProperties properties) {
      ULocale uloc = symbols.getULocale();
      MeasureUnit unit = properties.getMeasureUnit();
      org.python.icu.text.MeasureFormat.FormatWidth width = properties.getMeasureFormatWidth();
      if (unit == null) {
         throw new IllegalArgumentException("A measure unit is required for MeasureFormat");
      } else {
         if (width == null) {
            width = org.python.icu.text.MeasureFormat.FormatWidth.WIDE;
         }

         org.python.icu.text.MeasureFormat mf = org.python.icu.text.MeasureFormat.getInstance(uloc, width);
         GeneralPluralModifier mod = new GeneralPluralModifier();
         Iterator var7 = StandardPlural.VALUES.iterator();

         while(var7.hasNext()) {
            StandardPlural plural = (StandardPlural)var7.next();
            String formatString = null;
            mf.getPluralFormatter(unit, width, plural.ordinal());
            mod.put(plural, new SimpleModifier((String)formatString, (NumberFormat.Field)null, false));
         }

         return mod;
      }
   }

   public interface IProperties {
      MeasureUnit DEFAULT_MEASURE_UNIT = null;
      org.python.icu.text.MeasureFormat.FormatWidth DEFAULT_MEASURE_FORMAT_WIDTH = null;

      MeasureUnit getMeasureUnit();

      IProperties setMeasureUnit(MeasureUnit var1);

      org.python.icu.text.MeasureFormat.FormatWidth getMeasureFormatWidth();

      IProperties setMeasureFormatWidth(org.python.icu.text.MeasureFormat.FormatWidth var1);
   }
}
