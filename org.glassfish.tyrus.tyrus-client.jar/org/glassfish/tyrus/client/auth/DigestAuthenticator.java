package org.glassfish.tyrus.client.auth;

import java.io.IOException;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.glassfish.tyrus.core.l10n.LocalizationMessages;

final class DigestAuthenticator extends Authenticator {
   private static final Logger logger = Logger.getLogger(DigestAuthenticator.class.getName());
   private static final char[] HEX_ARRAY = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final Pattern KEY_VALUE_PAIR_PATTERN = Pattern.compile("(\\w+)\\s*=\\s*(\"([^\"]+)\"|(\\w+))\\s*,?\\s*");
   private static final int CLIENT_NONCE_BYTE_COUNT = 4;
   private SecureRandom randomGenerator;

   DigestAuthenticator() {
      try {
         this.randomGenerator = SecureRandom.getInstance("SHA1PRNG");
      } catch (NoSuchAlgorithmException var2) {
         logger.config(LocalizationMessages.AUTHENTICATION_DIGEST_NO_SUCH_ALG());
      }

   }

   public String generateAuthorizationHeader(URI uri, String wwwAuthenticateHeader, Credentials credentials) throws AuthenticationException {
      if (credentials == null) {
         throw new AuthenticationException(LocalizationMessages.AUTHENTICATION_CREDENTIALS_MISSING());
      } else {
         DigestScheme digestScheme;
         try {
            digestScheme = this.parseAuthHeaders(wwwAuthenticateHeader);
         } catch (IOException var6) {
            throw new AuthenticationException(var6.getMessage());
         }

         if (digestScheme == null) {
            throw new AuthenticationException(LocalizationMessages.AUTHENTICATION_CREATE_AUTH_HEADER_FAILED());
         } else {
            return this.createNextAuthToken(digestScheme, uri.toString(), credentials);
         }
      }
   }

   private DigestScheme parseAuthHeaders(String authHeader) throws IOException {
      if (authHeader == null) {
         return null;
      } else {
         String[] parts = authHeader.trim().split("\\s+", 2);
         if (parts.length != 2) {
            return null;
         } else if (!parts[0].toLowerCase().equals("digest")) {
            return null;
         } else {
            String realm = null;
            String nonce = null;
            String opaque = null;
            QOP qop = DigestAuthenticator.QOP.UNSPECIFIED;
            Algorithm algorithm = DigestAuthenticator.Algorithm.UNSPECIFIED;
            boolean stale = false;
            Matcher match = KEY_VALUE_PAIR_PATTERN.matcher(parts[1]);

            while(match.find()) {
               int nbGroups = match.groupCount();
               if (nbGroups == 4) {
                  String key = match.group(1);
                  String valNoQuotes = match.group(3);
                  String valQuotes = match.group(4);
                  String val = valNoQuotes == null ? valQuotes : valNoQuotes;
                  if (key.equals("qop")) {
                     qop = DigestAuthenticator.QOP.parse(val);
                  } else if (key.equals("realm")) {
                     realm = val;
                  } else if (key.equals("nonce")) {
                     nonce = val;
                  } else if (key.equals("opaque")) {
                     opaque = val;
                  } else if (key.equals("stale")) {
                     stale = Boolean.parseBoolean(val);
                  } else if (key.equals("algorithm")) {
                     algorithm = DigestAuthenticator.Algorithm.parse(val);
                  }
               }
            }

            return new DigestScheme(realm, nonce, opaque, qop, algorithm, stale);
         }
      }
   }

   private String createNextAuthToken(DigestScheme ds, String uri, Credentials credentials) throws AuthenticationException {
      StringBuilder sb = new StringBuilder(100);
      sb.append("Digest ");
      append(sb, "username", credentials.getUsername());
      append(sb, "realm", ds.getRealm());
      append(sb, "nonce", ds.getNonce());
      append(sb, "opaque", ds.getOpaque());
      append(sb, "algorithm", ds.getAlgorithm().toString(), false);
      append(sb, "qop", ds.getQop().toString(), false);
      append(sb, "uri", uri);
      String ha1;
      if (ds.getAlgorithm().equals(DigestAuthenticator.Algorithm.MD5_SESS)) {
         ha1 = md5(md5(credentials.getUsername(), ds.getRealm(), new String(credentials.getPassword(), AuthConfig.CHARACTER_SET)));
      } else {
         ha1 = md5(credentials.getUsername(), ds.getRealm(), new String(credentials.getPassword(), AuthConfig.CHARACTER_SET));
      }

      String ha2 = md5("GET", uri);
      String response;
      if (ds.getQop().equals(DigestAuthenticator.QOP.UNSPECIFIED)) {
         response = md5(ha1, ds.getNonce(), ha2);
      } else {
         String cnonce = this.randomBytes(4);
         append(sb, "cnonce", cnonce);
         String nc = String.format("%08x", ds.incrementCounter());
         append(sb, "nc", nc, false);
         response = md5(ha1, ds.getNonce(), nc, cnonce, ds.getQop().toString(), ha2);
      }

      append(sb, "response", response);
      return sb.toString();
   }

   private static void append(StringBuilder sb, String key, String value, boolean useQuote) {
      if (value != null) {
         if (sb.length() > 0 && sb.charAt(sb.length() - 1) != ' ') {
            sb.append(", ");
         }

         sb.append(key);
         sb.append('=');
         if (useQuote) {
            sb.append('"');
         }

         sb.append(value);
         if (useQuote) {
            sb.append('"');
         }

      }
   }

   private static void append(StringBuilder sb, String key, String value) {
      append(sb, key, value, true);
   }

   private static String bytesToHex(byte[] bytes) {
      char[] hexChars = new char[bytes.length * 2];

      for(int j = 0; j < bytes.length; ++j) {
         int v = bytes[j] & 255;
         hexChars[j * 2] = HEX_ARRAY[v >>> 4];
         hexChars[j * 2 + 1] = HEX_ARRAY[v & 15];
      }

      return new String(hexChars);
   }

   private static String md5(String... tokens) throws AuthenticationException {
      StringBuilder sb = new StringBuilder(100);
      String[] var2 = tokens;
      int var3 = tokens.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String token = var2[var4];
         if (sb.length() > 0) {
            sb.append(':');
         }

         sb.append(token);
      }

      MessageDigest md;
      try {
         md = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException var6) {
         throw new AuthenticationException(var6.getMessage());
      }

      md.update(sb.toString().getBytes(AuthConfig.CHARACTER_SET), 0, sb.length());
      byte[] md5hash = md.digest();
      return bytesToHex(md5hash);
   }

   private String randomBytes(int nbBytes) {
      byte[] bytes = new byte[nbBytes];
      this.randomGenerator.nextBytes(bytes);
      return bytesToHex(bytes);
   }

   final class DigestScheme {
      private final String realm;
      private final String nonce;
      private final String opaque;
      private final Algorithm algorithm;
      private final QOP qop;
      private final boolean stale;
      private volatile int nc;

      DigestScheme(String realm, String nonce, String opaque, QOP qop, Algorithm algorithm, boolean stale) {
         this.realm = realm;
         this.nonce = nonce;
         this.opaque = opaque;
         this.qop = qop;
         this.algorithm = algorithm;
         this.stale = stale;
         this.nc = 0;
      }

      public int incrementCounter() {
         return ++this.nc;
      }

      public String getNonce() {
         return this.nonce;
      }

      public String getRealm() {
         return this.realm;
      }

      public String getOpaque() {
         return this.opaque;
      }

      public Algorithm getAlgorithm() {
         return this.algorithm;
      }

      public QOP getQop() {
         return this.qop;
      }

      public boolean isStale() {
         return this.stale;
      }

      public int getNc() {
         return this.nc;
      }
   }

   static enum Algorithm {
      UNSPECIFIED((String)null),
      MD5("MD5"),
      MD5_SESS("MD5-sess");

      private final String md;

      private Algorithm(String md) {
         this.md = md;
      }

      public String toString() {
         return this.md;
      }

      public static Algorithm parse(String val) {
         if (val != null && !val.isEmpty()) {
            val = val.trim();
            return !val.contains(MD5_SESS.md) && !val.contains(MD5_SESS.md.toLowerCase()) ? MD5 : MD5_SESS;
         } else {
            return UNSPECIFIED;
         }
      }
   }

   private static enum QOP {
      UNSPECIFIED((String)null),
      AUTH("auth");

      private final String qop;

      private QOP(String qop) {
         this.qop = qop;
      }

      public String toString() {
         return this.qop;
      }

      public static QOP parse(String val) {
         if (val != null && !val.isEmpty()) {
            if (val.contains("auth")) {
               return AUTH;
            } else {
               throw new UnsupportedOperationException(LocalizationMessages.AUTHENTICATION_DIGEST_QOP_UNSUPPORTED(val));
            }
         } else {
            return UNSPECIFIED;
         }
      }
   }
}
