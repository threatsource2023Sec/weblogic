package weblogic.security.SSL.jsseadapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PEMUtils {
   public static final String PEM_CERT_BEGIN_HEADER = "-----BEGIN CERTIFICATE-----";
   public static final String PEM_CERT_END_HEADER = "-----END CERTIFICATE-----";
   public static final String PEM_ENC_PRIV_KEY_BEGIN_HEADER = "-----BEGIN ENCRYPTED PRIVATE KEY-----";
   public static final String PEM_ENC_PRIV_KEY_END_HEADER = "-----END ENCRYPTED PRIVATE KEY-----";
   public static final String PEM_RSA_PRIV_KEY_BEGIN_HEADER = "-----BEGIN RSA PRIVATE KEY-----";
   public static final String PEM_RSA_PRIV_KEY_END_HEADER = "-----END RSA PRIVATE KEY-----";
   public static final String PEM_PRIV_KEY_BEGIN_HEADER = "-----BEGIN PRIVATE KEY-----";
   public static final String PEM_PRIV_KEY_END_HEADER = "-----END PRIVATE KEY-----";
   public static final String NO_PEM_HEADER = "NO_PEM_HEADER";
   private static final String PEM_PATTERN = "(-----BEGIN [a-zA-Z[ ]]*?[a-zA-Z]-----)(.+?)(-----END [a-zA-Z[ ]]*?[a-zA-Z]-----)";

   public static PEMData parsePEM(InputStream pemIS) throws IOException {
      return pemIS instanceof PEMInputStream ? ((PEMInputStream)pemIS).getPEMData() : parsePEM(JaSSLSupport.readFully(pemIS));
   }

   public static PEMData parsePEM(byte[] pemBytes) throws IOException {
      return parsePEM(new String(pemBytes));
   }

   public static PEMData parsePEM(String pemString) throws IOException {
      Pattern p = Pattern.compile("(-----BEGIN [a-zA-Z[ ]]*?[a-zA-Z]-----)(.+?)(-----END [a-zA-Z[ ]]*?[a-zA-Z]-----)", 32);
      Matcher m = p.matcher(pemString);
      String h = null;
      byte[] d = null;
      byte[] d;
      if (m.find()) {
         h = m.group(1);
         d = JaSSLSupport.decodeData(m.group(2));
      } else {
         h = "NO_PEM_HEADER";
         d = JaSSLSupport.decodeData(pemString);
      }

      return new PEMData(h, d);
   }

   public static class PEMData {
      private String header = null;
      private byte[] data = null;

      public PEMData(String header, byte[] data) {
         this.header = header;
         this.data = data;
      }

      public PEMData(PEMData pd) {
         this.header = pd.header;
         this.data = Arrays.copyOf(pd.data, pd.data.length);
      }

      public String getHeader() {
         return this.header;
      }

      public byte[] getData() {
         return this.data;
      }
   }
}
