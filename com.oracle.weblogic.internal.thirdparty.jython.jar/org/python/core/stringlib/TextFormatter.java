package org.python.core.stringlib;

public class TextFormatter extends InternalFormat.Formatter {
   public TextFormatter(StringBuilder result, InternalFormat.Spec spec) {
      super(result, spec);
   }

   public TextFormatter(InternalFormat.Spec spec) {
      this(new StringBuilder(), spec);
   }

   public TextFormatter append(char c) {
      super.append(c);
      return this;
   }

   public TextFormatter append(CharSequence csq) {
      super.append(csq);
      return this;
   }

   public TextFormatter append(CharSequence csq, int start, int end) throws IndexOutOfBoundsException {
      super.append(csq, start, end);
      return this;
   }

   public TextFormatter format(String value) {
      this.setStart();
      int p = this.spec.precision;
      int n = value.length();
      if (InternalFormat.Spec.specified(p) && p < n) {
         int space = Math.max(this.spec.width, p);
         this.result.ensureCapacity(this.result.length() + space + (this.bytes ? 0 : space / 4));
         int count = 0;

         while(count < p) {
            char c = value.charAt(count++);
            this.result.append(c);
            if (Character.isHighSurrogate(c) && p < n) {
               ++p;
            }
         }

         this.lenWhole = count;
      } else {
         this.lenWhole = n;
         this.result.append(value);
      }

      return this;
   }

   public TextFormatter pad() {
      int n = this.spec.width - this.result.codePointCount(this.mark, this.result.length());
      if (n > 0) {
         this.pad(this.mark, n);
      }

      return this;
   }
}
