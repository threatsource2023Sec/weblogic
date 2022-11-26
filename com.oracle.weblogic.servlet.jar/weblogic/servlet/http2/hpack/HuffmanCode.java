package weblogic.servlet.http2.hpack;

public class HuffmanCode {
   int hexCode;
   int lenInBits;

   public HuffmanCode(int hexCode, int lenInBits) {
      this.hexCode = hexCode;
      this.lenInBits = lenInBits;
   }

   public int getHexCode() {
      return this.hexCode;
   }

   public int getLenInBits() {
      return this.lenInBits;
   }

   public boolean equals(Object obj) {
      if (obj != null && this.getClass() == obj.getClass()) {
         HuffmanCode huffmanObj = (HuffmanCode)obj;
         return this.hexCode == huffmanObj.getHexCode() && this.lenInBits == huffmanObj.getLenInBits();
      } else {
         return false;
      }
   }

   public int hashCode() {
      return 31 * this.hexCode + this.lenInBits;
   }

   public String toString() {
      return "HuffmanCode{hexCode=" + this.hexCode + ", [" + this.lenInBits + "]}";
   }
}
