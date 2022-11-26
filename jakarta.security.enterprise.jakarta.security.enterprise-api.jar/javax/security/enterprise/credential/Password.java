package javax.security.enterprise.credential;

import java.util.Arrays;
import java.util.Objects;

public class Password {
   private static final char[] EMPTY_VALUE = new char[0];
   private volatile char[] value;

   public Password(char[] value) {
      Objects.requireNonNull(value, "Password value may not be null");
      this.value = Arrays.copyOf(value, value.length);
   }

   public Password(String value) {
      this(null == value ? null : value.toCharArray());
   }

   public char[] getValue() {
      return this.value;
   }

   public void clear() {
      if (EMPTY_VALUE != this.value) {
         char[] tempValue = this.value;
         this.value = EMPTY_VALUE;

         for(int i = 0; i < tempValue.length; ++i) {
            tempValue[i] = 0;
         }

      }
   }

   public boolean compareTo(String password) {
      return password == null ? false : Arrays.equals(password.toCharArray(), this.value);
   }
}
