package org.python.icu.impl.number.formatters;

import org.python.icu.impl.number.Format;
import org.python.icu.impl.number.ModifierHolder;
import org.python.icu.impl.number.NumberStringBuilder;
import org.python.icu.impl.number.Properties;
import org.python.icu.text.NumberFormat;

public class PaddingFormat implements Format.AfterFormat {
   public static final String FALLBACK_PADDING_STRING = " ";
   private final int paddingWidth;
   private final String paddingString;
   private final PadPosition paddingLocation;

   public static boolean usePadding(IProperties properties) {
      return properties.getFormatWidth() != 0;
   }

   public static Format.AfterFormat getInstance(IProperties properties) {
      return new PaddingFormat(properties.getFormatWidth(), properties.getPadString(), properties.getPadPosition());
   }

   private PaddingFormat(int paddingWidth, String paddingString, PadPosition paddingLocation) {
      this.paddingWidth = paddingWidth > 0 ? paddingWidth : 10;
      this.paddingString = paddingString != null ? paddingString : " ";
      this.paddingLocation = paddingLocation != null ? paddingLocation : PaddingFormat.PadPosition.BEFORE_PREFIX;
   }

   public int after(ModifierHolder mods, NumberStringBuilder string, int leftIndex, int rightIndex) {
      int requiredPadding = this.paddingWidth - (rightIndex - leftIndex) - mods.totalLength();
      if (requiredPadding <= 0) {
         return mods.applyAll(string, leftIndex, rightIndex);
      } else {
         int length = 0;
         if (this.paddingLocation == PaddingFormat.PadPosition.AFTER_PREFIX) {
            length += this.addPadding(requiredPadding, string, leftIndex);
         } else if (this.paddingLocation == PaddingFormat.PadPosition.BEFORE_SUFFIX) {
            length += this.addPadding(requiredPadding, string, rightIndex);
         }

         length += mods.applyAll(string, leftIndex, rightIndex + length);
         if (this.paddingLocation == PaddingFormat.PadPosition.BEFORE_PREFIX) {
            length += this.addPadding(requiredPadding, string, leftIndex);
         } else if (this.paddingLocation == PaddingFormat.PadPosition.AFTER_SUFFIX) {
            length += this.addPadding(requiredPadding, string, rightIndex + length);
         }

         return length;
      }
   }

   private int addPadding(int requiredPadding, NumberStringBuilder string, int index) {
      for(int i = 0; i < requiredPadding; ++i) {
         string.insert(index, (CharSequence)this.paddingString, (NumberFormat.Field)null);
      }

      return this.paddingString.length() * requiredPadding;
   }

   public void export(Properties properties) {
      properties.setFormatWidth(this.paddingWidth);
      properties.setPadString(this.paddingString);
      properties.setPadPosition(this.paddingLocation);
   }

   public interface IProperties {
      int DEFAULT_FORMAT_WIDTH = 0;
      String DEFAULT_PAD_STRING = null;
      PadPosition DEFAULT_PAD_POSITION = null;

      int getFormatWidth();

      IProperties setFormatWidth(int var1);

      String getPadString();

      IProperties setPadString(String var1);

      PadPosition getPadPosition();

      IProperties setPadPosition(PadPosition var1);
   }

   public static enum PadPosition {
      BEFORE_PREFIX,
      AFTER_PREFIX,
      BEFORE_SUFFIX,
      AFTER_SUFFIX;

      public static PadPosition fromOld(int old) {
         switch (old) {
            case 0:
               return BEFORE_PREFIX;
            case 1:
               return AFTER_PREFIX;
            case 2:
               return BEFORE_SUFFIX;
            case 3:
               return AFTER_SUFFIX;
            default:
               throw new IllegalArgumentException("Don't know how to map " + old);
         }
      }

      public int toOld() {
         switch (this) {
            case BEFORE_PREFIX:
               return 0;
            case AFTER_PREFIX:
               return 1;
            case BEFORE_SUFFIX:
               return 2;
            case AFTER_SUFFIX:
               return 3;
            default:
               return -1;
         }
      }
   }
}
