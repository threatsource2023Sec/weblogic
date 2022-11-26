package javolution.lang;

public final class CharSet implements Immutable {
   public static final CharSet EMPTY = new CharSet(new long[0]);
   public static final CharSet WHITESPACES = valueOf('\t', '\n', '\u000b', '\f', '\r', '\u001c', '\u001d', '\u001e', '\u001f', ' ', ' ', '\u180e', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\u200b', '\u2028', '\u2029', ' ', '　');
   public static final CharSet SPACES = valueOf(' ', ' ', ' ', '\u180e', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '\u200b', '\u2028', '\u2029', ' ', ' ', '　');
   public static final CharSet ISO_CONTROLS = valueOf('\u0000', '\u0001', '\u0002', '\u0003', '\u0004', '\u0005', '\u0006', '\u0007', '\b', '\t', '\n', '\u000b', '\f', '\r', '\u000e', '\u000f', '\u0010', '\u0011', '\u0012', '\u0013', '\u0014', '\u0015', '\u0016', '\u0017', '\u0018', '\u0019', '\u001a', '\u001b', '\u001c', '\u001d', '\u001e', '\u001f', '\u007f', '\u0080', '\u0081', '\u0082', '\u0083', '\u0084', '\u0085', '\u0086', '\u0087', '\u0088', '\u0089', '\u008a', '\u008b', '\u008c', '\u008d', '\u008e', '\u008f', '\u0090', '\u0091', '\u0092', '\u0093', '\u0094', '\u0095', '\u0096', '\u0097', '\u0098', '\u0099', '\u009a', '\u009b', '\u009c', '\u009d', '\u009e', '\u009f');
   private final long[] _mapping;

   private CharSet(long[] var1) {
      this._mapping = var1;
   }

   public static CharSet valueOf(char... var0) {
      char var1 = 0;
      int var2 = var0.length;

      while(true) {
         --var2;
         if (var2 < 0) {
            CharSet var5 = new CharSet(new long[(var1 >> 6) + 1]);
            int var3 = var0.length;

            while(true) {
               --var3;
               if (var3 < 0) {
                  return var5;
               }

               char var4 = var0[var3];
               long[] var10000 = var5._mapping;
               var10000[var4 >> 6] |= 1L << (var4 & 63);
            }
         }

         if (var0[var2] > var1) {
            var1 = var0[var2];
         }
      }
   }

   public static CharSet rangeOf(char var0, char var1) {
      if (var0 > var1) {
         throw new IllegalArgumentException("first should be less or equal to last");
      } else {
         CharSet var2 = new CharSet(new long[(var1 >> 6) + 1]);

         for(char var3 = var0; var3 <= var1; ++var3) {
            long[] var10000 = var2._mapping;
            var10000[var3 >> 6] |= 1L << (var3 & 63);
         }

         return var2;
      }
   }

   public boolean contains(char var1) {
      int var2 = var1 >> 6;
      return var2 < this._mapping.length ? (this._mapping[var2] & 1L << (var1 & 63)) != 0L : false;
   }

   public CharSet plus(CharSet var1) {
      if (var1._mapping.length > this._mapping.length) {
         return var1.plus(this);
      } else {
         CharSet var2 = this.copy();
         int var3 = var1._mapping.length;

         while(true) {
            --var3;
            if (var3 < 0) {
               return var2;
            }

            long[] var10000 = var2._mapping;
            var10000[var3] |= var1._mapping[var3];
         }
      }
   }

   public CharSet minus(CharSet var1) {
      CharSet var2 = this.copy();
      int var3 = MathLib.min(this._mapping.length, var1._mapping.length);

      while(true) {
         --var3;
         if (var3 < 0) {
            return var2;
         }

         long[] var10000 = var2._mapping;
         var10000[var3] &= ~var1._mapping[var3];
      }
   }

   public String toString() {
      TextBuilder var1 = TextBuilder.newInstance();
      var1.append('{');
      int var2 = this._mapping.length << 6;

      for(int var3 = 0; var3 < var2; ++var3) {
         if (this.contains((char)var3)) {
            if (var1.length() > 1) {
               var1.append(',');
               var1.append(' ');
            }

            var1.append('\'');
            var1.append((char)var3);
            var1.append('\'');
         }
      }

      var1.append('}');
      return var1.toString();
   }

   private CharSet copy() {
      CharSet var1 = new CharSet(new long[this._mapping.length]);
      int var2 = this._mapping.length;

      while(true) {
         --var2;
         if (var2 < 0) {
            return var1;
         }

         var1._mapping[var2] = this._mapping[var2];
      }
   }
}
