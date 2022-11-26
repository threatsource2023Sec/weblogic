package org.apache.regexp;

public final class CharacterArrayCharacterIterator implements CharacterIterator {
   private final char[] src;
   private final int off;
   private final int len;

   public CharacterArrayCharacterIterator(char[] var1, int var2, int var3) {
      this.src = var1;
      this.off = var2;
      this.len = var3;
   }

   public String substring(int var1, int var2) {
      if (var2 > this.len) {
         throw new IndexOutOfBoundsException("endIndex=" + var2 + "; sequence size=" + this.len);
      } else if (var1 >= 0 && var1 <= var2) {
         return new String(this.src, this.off + var1, var2 - var1);
      } else {
         throw new IndexOutOfBoundsException("beginIndex=" + var1 + "; endIndex=" + var2);
      }
   }

   public String substring(int var1) {
      return this.substring(var1, this.len);
   }

   public char charAt(int var1) {
      return this.src[this.off + var1];
   }

   public boolean isEnd(int var1) {
      return var1 >= this.len;
   }
}
