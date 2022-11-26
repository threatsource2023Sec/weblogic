package org.python.core.stringlib;

import org.python.core.Py;
import org.python.core.PyException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyUnicode;

public class InternalFormat {
   public static Spec fromText(String text) {
      Parser parser = new Parser(text);

      try {
         return parser.parse();
      } catch (IllegalArgumentException var3) {
         throw Py.ValueError(var3.getMessage());
      }
   }

   public static Spec fromText(PyObject text, String method) {
      if (text instanceof PyString) {
         return fromText(((PyString)text).getString());
      } else {
         throw Py.TypeError(method + " requires str or unicode");
      }
   }

   private static class Parser {
      private String spec;
      private int ptr;

      Parser(String spec) {
         this.spec = spec;
         this.ptr = 0;
      }

      Spec parse() {
         char fill = '\uffff';
         char align = '\uffff';
         char sign = '\uffff';
         char type = '\uffff';
         boolean alternate = false;
         boolean grouping = false;
         int width = -1;
         int precision = -1;
         if (this.isAlign()) {
            align = this.spec.charAt(this.ptr++);
         } else {
            ++this.ptr;
            if (this.isAlign()) {
               fill = this.spec.charAt(0);
               align = this.spec.charAt(this.ptr++);
            } else {
               this.ptr = 0;
            }
         }

         if (this.isAt("+- ")) {
            sign = this.spec.charAt(this.ptr++);
         }

         alternate = this.scanPast('#');
         if (this.scanPast('0') && !InternalFormat.Spec.specified(fill)) {
            fill = '0';
            if (!InternalFormat.Spec.specified(align)) {
               align = '=';
            }
         }

         if (this.isDigit()) {
            width = this.scanInteger();
         }

         grouping = this.scanPast(',');
         if (this.scanPast('.')) {
            if (!this.isDigit()) {
               throw new IllegalArgumentException("Format specifier missing precision");
            }

            precision = this.scanInteger();
         }

         if (this.ptr < this.spec.length()) {
            type = this.spec.charAt(this.ptr++);
         }

         if (this.ptr != this.spec.length()) {
            throw new IllegalArgumentException("Invalid conversion specification");
         } else {
            return new Spec(fill, align, sign, alternate, width, grouping, precision, type);
         }
      }

      private boolean scanPast(char c) {
         if (this.ptr < this.spec.length() && this.spec.charAt(this.ptr) == c) {
            ++this.ptr;
            return true;
         } else {
            return false;
         }
      }

      private boolean isAt(String chars) {
         return this.ptr < this.spec.length() && chars.indexOf(this.spec.charAt(this.ptr)) >= 0;
      }

      private boolean isAlign() {
         return this.ptr < this.spec.length() && "<^>=".indexOf(this.spec.charAt(this.ptr)) >= 0;
      }

      private boolean isDigit() {
         return this.ptr < this.spec.length() && Character.isDigit(this.spec.charAt(this.ptr));
      }

      private int scanInteger() {
         int p;
         for(p = this.ptr++; this.isDigit(); ++this.ptr) {
         }

         return Integer.parseInt(this.spec.substring(p, this.ptr));
      }
   }

   public static class Spec {
      public final char fill;
      public final char align;
      public final char sign;
      public final boolean alternate;
      public final int width;
      public final boolean grouping;
      public final int precision;
      public final char type;
      public static final char NONE = '\uffff';
      public static final int UNSPECIFIED = -1;
      public static final Spec NUMERIC = new Spec(' ', '>', '\uffff', false, -1, false, -1, '\uffff');
      public static final Spec STRING = new Spec(' ', '<', '\uffff', false, -1, false, -1, '\uffff');

      public static final boolean specified(char c) {
         return c != '\uffff';
      }

      public static final boolean specified(int value) {
         return value >= 0;
      }

      public Spec(char fill, char align, char sign, boolean alternate, int width, boolean grouping, int precision, char type) {
         this.fill = fill;
         this.align = align;
         this.sign = sign;
         this.alternate = alternate;
         this.width = width;
         this.grouping = grouping;
         this.precision = precision;
         this.type = type;
      }

      public String toString() {
         StringBuilder buf = new StringBuilder();
         if (specified(this.fill)) {
            buf.append(this.fill);
         }

         if (specified(this.align)) {
            buf.append(this.align);
         }

         if (specified(this.sign)) {
            buf.append(this.sign);
         }

         if (this.alternate) {
            buf.append('#');
         }

         if (specified(this.width)) {
            buf.append(this.width);
         }

         if (this.grouping) {
            buf.append(',');
         }

         if (specified(this.precision)) {
            buf.append('.').append(this.precision);
         }

         if (specified(this.type)) {
            buf.append(this.type);
         }

         return buf.toString();
      }

      public Spec withDefaults(Spec other) {
         return new Spec(specified(this.fill) ? this.fill : other.fill, specified(this.align) ? this.align : other.align, specified(this.sign) ? this.sign : other.sign, this.alternate || other.alternate, specified(this.width) ? this.width : other.width, this.grouping || other.grouping, specified(this.precision) ? this.precision : other.precision, specified(this.type) ? this.type : other.type);
      }

      public Spec(int precision, char type) {
         this(' ', '>', '\uffff', false, -1, false, precision, type);
      }

      public char getFill(char defaultFill) {
         return specified(this.fill) ? this.fill : defaultFill;
      }

      public char getAlign(char defaultAlign) {
         return specified(this.align) ? this.align : defaultAlign;
      }

      public int getPrecision(int defaultPrecision) {
         return specified(this.precision) ? this.precision : defaultPrecision;
      }

      public char getType(char defaultType) {
         return specified(this.type) ? this.type : defaultType;
      }
   }

   public static class Formatter implements Appendable {
      protected final Spec spec;
      protected StringBuilder result;
      protected boolean bytes;
      protected int mark;
      protected int start;
      protected int lenSign;
      protected int lenWhole;

      public Formatter(StringBuilder result, Spec spec) {
         this.spec = spec;
         this.result = result;
         this.start = this.mark = result.length();
      }

      public Formatter(Spec spec, int width) {
         this(new StringBuilder(width), spec);
      }

      public void setBytes(boolean bytes) {
         this.bytes = bytes;
      }

      public boolean isBytes() {
         return this.bytes;
      }

      public String getResult() {
         return this.result.toString();
      }

      public PyString getPyResult() {
         String r = this.getResult();
         return (PyString)(this.bytes ? new PyString(r) : new PyUnicode(r));
      }

      public Formatter append(char c) {
         this.result.append(c);
         return this;
      }

      public Formatter append(CharSequence csq) {
         this.result.append(csq);
         return this;
      }

      public Formatter append(CharSequence csq, int start, int end) throws IndexOutOfBoundsException {
         this.result.append(csq, start, end);
         return this;
      }

      public void setStart() {
         this.start = this.result.length();
         if (this.start > this.mark) {
            this.reset();
         }

      }

      protected void reset() {
         this.lenSign = this.lenWhole = 0;
      }

      protected int[] sectionLengths() {
         return new int[]{this.lenSign, this.lenWhole};
      }

      public String toString() {
         if (this.result == null) {
            return "[]";
         } else {
            StringBuilder buf = new StringBuilder(this.result.length() + 20);
            buf.append(this.result);

            try {
               int p = this.start;
               buf.insert(p++, '[');
               int[] var3 = this.sectionLengths();
               int var4 = var3.length;

               for(int var5 = 0; var5 < var4; ++var5) {
                  int len = var3[var5];
                  p += len;
                  buf.insert(p++, '|');
               }

               buf.setCharAt(p - 1, ']');
            } catch (IndexOutOfBoundsException var7) {
            }

            return buf.toString();
         }
      }

      protected void groupDigits(int groupSize, char comma) {
         int commasNeeded = (this.lenWhole - 1) / groupSize;
         if (commasNeeded > 0) {
            int from = this.start + this.lenSign + this.lenWhole;
            this.makeSpaceAt(from, commasNeeded);
            int to = from + commasNeeded;
            this.lenWhole += commasNeeded;

            while(to > from) {
               for(int i = 0; i < groupSize; ++i) {
                  --to;
                  --from;
                  this.result.setCharAt(to, this.result.charAt(from));
               }

               --to;
               this.result.setCharAt(to, comma);
            }
         }

      }

      protected void makeSpaceAt(int pos, int size) {
         int n = this.result.length();
         if (pos < n) {
            String tail = this.result.substring(pos);
            this.result.setLength(n + size);
            this.result.replace(pos + size, n + size, tail);
         } else {
            this.result.setLength(n + size);
         }

      }

      protected void uppercase() {
         int end = this.result.length();

         for(int i = this.start; i < end; ++i) {
            char c = this.result.charAt(i);
            this.result.setCharAt(i, Character.toUpperCase(c));
         }

      }

      public Formatter pad() {
         int n = this.spec.width - (this.result.length() - this.mark);
         if (n > 0) {
            this.pad(this.mark, n);
         }

         return this;
      }

      protected void pad(int leftIndex, int n) {
         char align = this.spec.getAlign('>');
         char fill = this.spec.getFill(' ');
         int leading = n;
         if (align == '^') {
            leading = n / 2;
         } else if (align == '<') {
            leading = 0;
         }

         int trailing = n - leading;
         int i;
         if (leading > 0) {
            if (align == '=') {
               leftIndex = this.start + this.lenSign;
               this.lenWhole += leading;
            } else {
               this.start += leading;
            }

            this.makeSpaceAt(leftIndex, leading);

            for(i = 0; i < leading; ++i) {
               this.result.setCharAt(leftIndex + i, fill);
            }
         }

         for(i = 0; i < trailing; ++i) {
            this.result.append(fill);
         }

         if (align == '=' && fill == '0' && this.spec.grouping) {
            this.zeroPadAfterSignWithGroupingFixup(3, ',');
         }

      }

      protected void zeroPadAfterSignWithGroupingFixup(int groupSize, char comma) {
         int firstZero = this.start + this.lenSign;
         int p = firstZero + this.lenWhole;
         int step = groupSize + 1;

         for(p -= step; p >= firstZero; p -= step) {
            this.result.setCharAt(p, comma);
         }

         if (p + step == firstZero) {
            this.result.insert(firstZero, '0');
            ++this.lenWhole;
         }

      }

      public static PyException unknownFormat(char code, String forType) {
         String msg = "Unknown format code '" + code + "' for object of type '" + forType + "'";
         return Py.ValueError(msg);
      }

      public static PyException alternateFormNotAllowed(String forType) {
         return alternateFormNotAllowed(forType, '\u0000');
      }

      public static PyException alternateFormNotAllowed(String forType, char code) {
         return notAllowed("Alternate form (#)", forType, code);
      }

      public static PyException alignmentNotAllowed(char align, String forType) {
         return notAllowed("'" + align + "' alignment flag", forType, '\u0000');
      }

      public static PyException signNotAllowed(String forType, char code) {
         return notAllowed("Sign", forType, code);
      }

      public static PyException precisionNotAllowed(String forType) {
         return notAllowed("Precision", forType, '\u0000');
      }

      public static PyException zeroPaddingNotAllowed(String forType) {
         return notAllowed("Zero padding", forType, '\u0000');
      }

      public static PyException notAllowed(String outrage, String forType) {
         return notAllowed(outrage, forType, '\u0000');
      }

      public static PyException notAllowed(String outrage, String forType, char code) {
         String withOrIn;
         String codeAsString;
         if (code == 0) {
            withOrIn = "in ";
            codeAsString = "";
         } else {
            withOrIn = "with ";
            codeAsString = " '" + code + "'";
         }

         String msg = outrage + " not allowed " + withOrIn + forType + " format specifier" + codeAsString;
         return Py.ValueError(msg);
      }

      public static PyException precisionTooLarge(String type) {
         String msg = "formatted " + type + " is too long (precision too large?)";
         return Py.OverflowError(msg);
      }
   }
}
