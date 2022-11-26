package org.python.icu.impl.number;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.FieldPosition;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.python.icu.text.NumberFormat;

public class NumberStringBuilder implements CharSequence {
   private char[] chars;
   private NumberFormat.Field[] fields;
   private int zero;
   private int length;
   private static final Map fieldToDebugChar = new HashMap();

   public NumberStringBuilder() {
      this(40);
   }

   public NumberStringBuilder(int capacity) {
      this.chars = new char[capacity];
      this.fields = new NumberFormat.Field[capacity];
      this.zero = capacity / 2;
      this.length = 0;
   }

   public NumberStringBuilder(NumberStringBuilder source) {
      this(source.chars.length);
      this.zero = source.zero;
      this.length = source.length;
      System.arraycopy(source.chars, this.zero, this.chars, this.zero, this.length);
      System.arraycopy(source.fields, this.zero, this.fields, this.zero, this.length);
   }

   public int length() {
      return this.length;
   }

   public char charAt(int index) {
      if (index >= 0 && index <= this.length) {
         return this.chars[this.zero + index];
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public int appendCodePoint(int codePoint, NumberFormat.Field field) {
      return this.insertCodePoint(this.length, codePoint, field);
   }

   public int insertCodePoint(int index, int codePoint, NumberFormat.Field field) {
      int count = Character.charCount(codePoint);
      int position = this.prepareForInsert(index, count);
      Character.toChars(codePoint, this.chars, position);
      this.fields[position] = field;
      if (count == 2) {
         this.fields[position + 1] = field;
      }

      return count;
   }

   public int append(CharSequence sequence, NumberFormat.Field field) {
      return this.insert(this.length, sequence, field);
   }

   public int insert(int index, CharSequence sequence, NumberFormat.Field field) {
      if (sequence.length() == 0) {
         return 0;
      } else {
         return sequence.length() == 1 ? this.insertCodePoint(index, sequence.charAt(0), field) : this.insert(index, sequence, 0, sequence.length(), field);
      }
   }

   public int insert(int index, CharSequence sequence, int start, int end, NumberFormat.Field field) {
      int count = end - start;
      int position = this.prepareForInsert(index, count);

      for(int i = 0; i < count; ++i) {
         this.chars[position + i] = sequence.charAt(start + i);
         this.fields[position + i] = field;
      }

      return count;
   }

   public int append(char[] chars, NumberFormat.Field[] fields) {
      return this.insert(this.length, chars, fields);
   }

   public int insert(int index, char[] chars, NumberFormat.Field[] fields) {
      assert fields == null || chars.length == fields.length;

      int count = chars.length;
      if (count == 0) {
         return 0;
      } else {
         int position = this.prepareForInsert(index, count);

         for(int i = 0; i < count; ++i) {
            this.chars[position + i] = chars[i];
            this.fields[position + i] = fields == null ? null : fields[i];
         }

         return count;
      }
   }

   public int append(NumberStringBuilder other) {
      return this.insert(this.length, other);
   }

   public int insert(int index, NumberStringBuilder other) {
      if (this == other) {
         throw new IllegalArgumentException("Cannot call insert/append on myself");
      } else {
         int count = other.length;
         if (count == 0) {
            return 0;
         } else {
            int position = this.prepareForInsert(index, count);

            for(int i = 0; i < count; ++i) {
               this.chars[position + i] = other.chars[other.zero + i];
               this.fields[position + i] = other.fields[other.zero + i];
            }

            return count;
         }
      }
   }

   private int prepareForInsert(int index, int count) {
      if (index == 0 && this.zero - count >= 0) {
         this.zero -= count;
         this.length += count;
         return this.zero;
      } else if (index == this.length && this.zero + this.length + count < this.chars.length) {
         this.length += count;
         return this.zero + this.length - count;
      } else {
         return this.prepareForInsertHelper(index, count);
      }
   }

   private int prepareForInsertHelper(int index, int count) {
      if (this.length + count > this.chars.length) {
         char[] newChars = new char[(this.length + count) * 2];
         NumberFormat.Field[] newFields = new NumberFormat.Field[(this.length + count) * 2];
         int newZero = newChars.length / 2 - (this.length + count) / 2;
         System.arraycopy(this.chars, this.zero, newChars, newZero, index);
         System.arraycopy(this.chars, this.zero + index, newChars, newZero + index + count, this.length - index);
         System.arraycopy(this.fields, this.zero, newFields, newZero, index);
         System.arraycopy(this.fields, this.zero + index, newFields, newZero + index + count, this.length - index);
         this.chars = newChars;
         this.fields = newFields;
         this.zero = newZero;
         this.length += count;
      } else {
         int newZero = this.chars.length / 2 - (this.length + count) / 2;
         System.arraycopy(this.chars, this.zero, this.chars, newZero, this.length);
         System.arraycopy(this.chars, newZero + index, this.chars, newZero + index + count, this.length - index);
         System.arraycopy(this.fields, this.zero, this.fields, newZero, this.length);
         System.arraycopy(this.fields, newZero + index, this.fields, newZero + index + count, this.length - index);
         this.zero = newZero;
         this.length += count;
      }

      return this.zero + index;
   }

   public CharSequence subSequence(int start, int end) {
      if (start >= 0 && end <= this.length && end >= start) {
         NumberStringBuilder other = new NumberStringBuilder(this);
         other.zero = this.zero + start;
         other.length = end - start;
         return other;
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public String toString() {
      return new String(this.chars, this.zero, this.length);
   }

   public String toDebugString() {
      StringBuilder sb = new StringBuilder();
      sb.append("<NumberStringBuilder [");
      sb.append(this.toString());
      sb.append("] [");

      for(int i = this.zero; i < this.zero + this.length; ++i) {
         if (this.fields[i] == null) {
            sb.append('n');
         } else {
            sb.append(fieldToDebugChar.get(this.fields[i]));
         }
      }

      sb.append("]>");
      return sb.toString();
   }

   public char[] toCharArray() {
      return Arrays.copyOfRange(this.chars, this.zero, this.zero + this.length);
   }

   public NumberFormat.Field[] toFieldArray() {
      return (NumberFormat.Field[])Arrays.copyOfRange(this.fields, this.zero, this.zero + this.length);
   }

   public boolean contentEquals(char[] chars, NumberFormat.Field[] fields) {
      if (chars.length != this.length) {
         return false;
      } else if (fields.length != this.length) {
         return false;
      } else {
         for(int i = 0; i < this.length; ++i) {
            if (this.chars[this.zero + i] != chars[i]) {
               return false;
            }

            if (this.fields[this.zero + i] != fields[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public boolean contentEquals(NumberStringBuilder other) {
      if (this.length != other.length) {
         return false;
      } else {
         for(int i = 0; i < this.length; ++i) {
            if (this.chars[this.zero + i] != other.chars[other.zero + i]) {
               return false;
            }

            if (this.fields[this.zero + i] != other.fields[other.zero + i]) {
               return false;
            }
         }

         return true;
      }
   }

   public void populateFieldPosition(FieldPosition fp, int offset) {
      java.text.Format.Field rawField = fp.getFieldAttribute();
      if (rawField == null) {
         if (fp.getField() == 0) {
            rawField = NumberFormat.Field.INTEGER;
         } else {
            if (fp.getField() != 1) {
               return;
            }

            rawField = NumberFormat.Field.FRACTION;
         }
      }

      if (!(rawField instanceof NumberFormat.Field)) {
         throw new IllegalArgumentException("You must pass an instance of com.ibm.icu.text.NumberFormat.Field as your FieldPosition attribute.  You passed: " + rawField.getClass().toString());
      } else {
         NumberFormat.Field field = (NumberFormat.Field)rawField;
         boolean seenStart = false;
         int fractionStart = -1;

         for(int i = this.zero; i <= this.zero + this.length; ++i) {
            NumberFormat.Field _field = i < this.zero + this.length ? this.fields[i] : null;
            if (seenStart && field != _field) {
               if (field != NumberFormat.Field.INTEGER || _field != NumberFormat.Field.GROUPING_SEPARATOR) {
                  fp.setEndIndex(i - this.zero + offset);
                  break;
               }
            } else {
               if (!seenStart && field == _field) {
                  fp.setBeginIndex(i - this.zero + offset);
                  seenStart = true;
               }

               if (_field == NumberFormat.Field.INTEGER || _field == NumberFormat.Field.DECIMAL_SEPARATOR) {
                  fractionStart = i - this.zero + 1;
               }
            }
         }

         if (field == NumberFormat.Field.FRACTION && !seenStart) {
            fp.setBeginIndex(fractionStart);
            fp.setEndIndex(fractionStart);
         }

      }
   }

   public AttributedCharacterIterator getIterator() {
      AttributedString as = new AttributedString(this.toString());
      NumberFormat.Field current = null;
      int currentStart = -1;

      for(int i = 0; i < this.length; ++i) {
         NumberFormat.Field field = this.fields[i + this.zero];
         if (current == NumberFormat.Field.INTEGER && field == NumberFormat.Field.GROUPING_SEPARATOR) {
            as.addAttribute(NumberFormat.Field.GROUPING_SEPARATOR, NumberFormat.Field.GROUPING_SEPARATOR, i, i + 1);
         } else if (current != field) {
            if (current != null) {
               as.addAttribute(current, current, currentStart, i);
            }

            current = field;
            currentStart = i;
         }
      }

      if (current != null) {
         as.addAttribute(current, current, currentStart, this.length);
      }

      return as.getIterator();
   }

   public NumberStringBuilder clear() {
      this.zero = this.chars.length / 2;
      this.length = 0;
      return this;
   }

   static {
      fieldToDebugChar.put(NumberFormat.Field.SIGN, '-');
      fieldToDebugChar.put(NumberFormat.Field.INTEGER, 'i');
      fieldToDebugChar.put(NumberFormat.Field.FRACTION, 'f');
      fieldToDebugChar.put(NumberFormat.Field.EXPONENT, 'e');
      fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SIGN, '+');
      fieldToDebugChar.put(NumberFormat.Field.EXPONENT_SYMBOL, 'E');
      fieldToDebugChar.put(NumberFormat.Field.DECIMAL_SEPARATOR, '.');
      fieldToDebugChar.put(NumberFormat.Field.GROUPING_SEPARATOR, ',');
      fieldToDebugChar.put(NumberFormat.Field.PERCENT, '%');
      fieldToDebugChar.put(NumberFormat.Field.PERMILLE, '‰');
      fieldToDebugChar.put(NumberFormat.Field.CURRENCY, '$');
   }
}
