package weblogic.application.utils.internal;

import weblogic.utils.encoders.BASE64Encoder;

public class BASE64URLEncoder extends BASE64Encoder {
   private static final char[] safe_url_pem_array = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '-', '_'};
   private boolean addPadding = true;

   public BASE64URLEncoder() {
   }

   public BASE64URLEncoder(boolean addPadding) {
      this.addPadding = addPadding;
   }

   protected char[] getPemArray() {
      return safe_url_pem_array;
   }

   protected boolean addPadding() {
      return this.addPadding;
   }
}
