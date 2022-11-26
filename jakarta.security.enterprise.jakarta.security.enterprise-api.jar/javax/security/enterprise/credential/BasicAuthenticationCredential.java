package javax.security.enterprise.credential;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class BasicAuthenticationCredential extends UsernamePasswordCredential {
   public BasicAuthenticationCredential(String authorizationHeader) {
      super(parseUsername(authorizationHeader), parsePassword(authorizationHeader));
   }

   private static String decodeHeader(String authorizationHeader) {
      String BASIC_AUTH_CHARSET = "US-ASCII";
      if (null == authorizationHeader) {
         throw new NullPointerException("authorization header");
      } else if (authorizationHeader.isEmpty()) {
         throw new IllegalArgumentException("authorization header is empty");
      } else {
         Base64.Decoder decoder = Base64.getMimeDecoder();
         byte[] decodedBytes = decoder.decode(authorizationHeader);

         try {
            return new String(decodedBytes, "US-ASCII");
         } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException("Unknown Charset: US-ASCII", var5);
         }
      }
   }

   private static String parseUsername(String authorizationHeader) {
      String decodedAuthorizationHeader = decodeHeader(authorizationHeader);
      int delimiterIndex = decodedAuthorizationHeader.indexOf(58);
      return delimiterIndex > -1 ? decodedAuthorizationHeader.substring(0, delimiterIndex) : decodedAuthorizationHeader;
   }

   private static Password parsePassword(String authorizationHeader) {
      String decodedAuthorizationHeader = decodeHeader(authorizationHeader);
      int delimiterIndex = decodedAuthorizationHeader.indexOf(58);
      return delimiterIndex > -1 ? new Password(decodedAuthorizationHeader.substring(delimiterIndex + 1)) : new Password("");
   }
}
