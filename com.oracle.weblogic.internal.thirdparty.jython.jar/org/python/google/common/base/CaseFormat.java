package org.python.google.common.base;

import java.io.Serializable;
import javax.annotation.Nullable;
import org.python.google.common.annotations.GwtCompatible;

@GwtCompatible
public enum CaseFormat {
   LOWER_HYPHEN(CharMatcher.is('-'), "-") {
      String normalizeWord(String word) {
         return Ascii.toLowerCase(word);
      }

      String convert(CaseFormat format, String s) {
         if (format == LOWER_UNDERSCORE) {
            return s.replace('-', '_');
         } else {
            return format == UPPER_UNDERSCORE ? Ascii.toUpperCase(s.replace('-', '_')) : super.convert(format, s);
         }
      }
   },
   LOWER_UNDERSCORE(CharMatcher.is('_'), "_") {
      String normalizeWord(String word) {
         return Ascii.toLowerCase(word);
      }

      String convert(CaseFormat format, String s) {
         if (format == LOWER_HYPHEN) {
            return s.replace('_', '-');
         } else {
            return format == UPPER_UNDERSCORE ? Ascii.toUpperCase(s) : super.convert(format, s);
         }
      }
   },
   LOWER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
      String normalizeWord(String word) {
         return CaseFormat.firstCharOnlyToUpper(word);
      }
   },
   UPPER_CAMEL(CharMatcher.inRange('A', 'Z'), "") {
      String normalizeWord(String word) {
         return CaseFormat.firstCharOnlyToUpper(word);
      }
   },
   UPPER_UNDERSCORE(CharMatcher.is('_'), "_") {
      String normalizeWord(String word) {
         return Ascii.toUpperCase(word);
      }

      String convert(CaseFormat format, String s) {
         if (format == LOWER_HYPHEN) {
            return Ascii.toLowerCase(s.replace('_', '-'));
         } else {
            return format == LOWER_UNDERSCORE ? Ascii.toLowerCase(s) : super.convert(format, s);
         }
      }
   };

   private final CharMatcher wordBoundary;
   private final String wordSeparator;

   private CaseFormat(CharMatcher wordBoundary, String wordSeparator) {
      this.wordBoundary = wordBoundary;
      this.wordSeparator = wordSeparator;
   }

   public final String to(CaseFormat format, String str) {
      Preconditions.checkNotNull(format);
      Preconditions.checkNotNull(str);
      return format == this ? str : this.convert(format, str);
   }

   String convert(CaseFormat format, String s) {
      StringBuilder out = null;
      int i = 0;
      int j = -1;

      while(true) {
         ++j;
         if ((j = this.wordBoundary.indexIn(s, j)) == -1) {
            return i == 0 ? format.normalizeFirstWord(s) : out.append(format.normalizeWord(s.substring(i))).toString();
         }

         if (i == 0) {
            out = new StringBuilder(s.length() + 4 * this.wordSeparator.length());
            out.append(format.normalizeFirstWord(s.substring(i, j)));
         } else {
            out.append(format.normalizeWord(s.substring(i, j)));
         }

         out.append(format.wordSeparator);
         i = j + this.wordSeparator.length();
      }
   }

   public Converter converterTo(CaseFormat targetFormat) {
      return new StringConverter(this, targetFormat);
   }

   abstract String normalizeWord(String var1);

   private String normalizeFirstWord(String word) {
      return this == LOWER_CAMEL ? Ascii.toLowerCase(word) : this.normalizeWord(word);
   }

   private static String firstCharOnlyToUpper(String word) {
      return word.isEmpty() ? word : Ascii.toUpperCase(word.charAt(0)) + Ascii.toLowerCase(word.substring(1));
   }

   // $FF: synthetic method
   CaseFormat(CharMatcher x2, String x3, Object x4) {
      this(x2, x3);
   }

   private static final class StringConverter extends Converter implements Serializable {
      private final CaseFormat sourceFormat;
      private final CaseFormat targetFormat;
      private static final long serialVersionUID = 0L;

      StringConverter(CaseFormat sourceFormat, CaseFormat targetFormat) {
         this.sourceFormat = (CaseFormat)Preconditions.checkNotNull(sourceFormat);
         this.targetFormat = (CaseFormat)Preconditions.checkNotNull(targetFormat);
      }

      protected String doForward(String s) {
         return this.sourceFormat.to(this.targetFormat, s);
      }

      protected String doBackward(String s) {
         return this.targetFormat.to(this.sourceFormat, s);
      }

      public boolean equals(@Nullable Object object) {
         if (!(object instanceof StringConverter)) {
            return false;
         } else {
            StringConverter that = (StringConverter)object;
            return this.sourceFormat.equals(that.sourceFormat) && this.targetFormat.equals(that.targetFormat);
         }
      }

      public int hashCode() {
         return this.sourceFormat.hashCode() ^ this.targetFormat.hashCode();
      }

      public String toString() {
         return this.sourceFormat + ".converterTo(" + this.targetFormat + ")";
      }
   }
}
