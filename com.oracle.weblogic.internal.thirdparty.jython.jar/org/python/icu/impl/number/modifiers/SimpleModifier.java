package org.python.icu.impl.number.modifiers;

import org.python.icu.impl.SimpleFormatterImpl;
import org.python.icu.impl.number.Modifier;
import org.python.icu.impl.number.NumberStringBuilder;
import org.python.icu.impl.number.Properties;
import org.python.icu.text.NumberFormat;

public class SimpleModifier extends Modifier.BaseModifier {
   private final String compiledPattern;
   private final NumberFormat.Field field;
   private final boolean strong;

   public SimpleModifier(String compiledPattern, NumberFormat.Field field, boolean strong) {
      this.compiledPattern = compiledPattern == null ? "\u0001\u0000" : compiledPattern;
      this.field = field;
      this.strong = strong;
   }

   public int apply(NumberStringBuilder output, int leftIndex, int rightIndex) {
      return formatAsPrefixSuffix(this.compiledPattern, output, leftIndex, rightIndex, this.field);
   }

   public int length() {
      return formatAsPrefixSuffix(this.compiledPattern, (NumberStringBuilder)null, -1, -1, this.field);
   }

   public boolean isStrong() {
      return this.strong;
   }

   public String getPrefix() {
      throw new UnsupportedOperationException();
   }

   public String getSuffix() {
      throw new UnsupportedOperationException();
   }

   public static int formatAsPrefixSuffix(String compiledPattern, NumberStringBuilder result, int startIndex, int endIndex, NumberFormat.Field field) {
      assert SimpleFormatterImpl.getArgumentLimit(compiledPattern) == 1;

      int ARG_NUM_LIMIT = 256;
      int length = 0;
      int offset = 2;
      int suffixLength;
      if (compiledPattern.charAt(1) != 0) {
         suffixLength = compiledPattern.charAt(1) - ARG_NUM_LIMIT;
         if (result != null) {
            result.insert(startIndex, compiledPattern, 2, 2 + suffixLength, field);
         }

         length += suffixLength;
         offset = 3 + suffixLength;
      }

      if (offset < compiledPattern.length()) {
         suffixLength = compiledPattern.charAt(offset) - ARG_NUM_LIMIT;
         if (result != null) {
            result.insert(endIndex + length, compiledPattern, offset + 1, offset + suffixLength + 1, field);
         }

         length += suffixLength;
      }

      return length;
   }

   public static void testFormatAsPrefixSuffix() {
      String[] patterns = new String[]{"{0}", "X{0}Y", "XX{0}YYY", "{0}YY", "XXXX{0}"};
      Object[][] outputs = new Object[][]{{"", 0, 0}, {"abcde", 0, 0}, {"abcde", 2, 2}, {"abcde", 1, 3}};
      String[][] expecteds = new String[][]{{"", "XY", "XXYYY", "YY", "XXXX"}, {"abcde", "XYabcde", "XXYYYabcde", "YYabcde", "XXXXabcde"}, {"abcde", "abXYcde", "abXXYYYcde", "abYYcde", "abXXXXcde"}, {"abcde", "aXbcYde", "aXXbcYYYde", "abcYYde", "aXXXXbcde"}};

      for(int i = 0; i < patterns.length; ++i) {
         for(int j = 0; j < outputs.length; ++j) {
            String pattern = patterns[i];
            String compiledPattern = SimpleFormatterImpl.compileToStringMinMaxArguments(pattern, new StringBuilder(), 1, 1);
            NumberStringBuilder output = new NumberStringBuilder();
            output.append((CharSequence)((String)outputs[j][0]), (NumberFormat.Field)null);
            formatAsPrefixSuffix(compiledPattern, output, (Integer)outputs[j][1], (Integer)outputs[j][2], (NumberFormat.Field)null);
            String expected = expecteds[j][i];
            String actual = output.toString();

            assert expected.equals(actual);
         }
      }

   }

   public void export(Properties properties) {
      throw new UnsupportedOperationException();
   }
}
