package antlr.ASdebug;

public class TokenOffsetInfo {
   public final int beginOffset;
   public final int length;

   public TokenOffsetInfo(int var1, int var2) {
      this.beginOffset = var1;
      this.length = var2;
   }

   public int getEndOffset() {
      return this.beginOffset + this.length - 1;
   }
}
