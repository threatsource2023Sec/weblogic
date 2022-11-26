package weblogic.xml.saaj.mime4j.field;

public class ContentTransferEncodingField extends Field {
   public static final String ENC_7BIT = "7bit";
   public static final String ENC_8BIT = "8bit";
   public static final String ENC_BINARY = "binary";
   public static final String ENC_QUOTED_PRINTABLE = "quoted-printable";
   public static final String ENC_BASE64 = "base64";
   private String encoding;

   protected ContentTransferEncodingField() {
   }

   protected void parseBody(String body) {
      this.encoding = body.trim().toLowerCase();
   }

   public String getEncoding() {
      return this.encoding;
   }

   public static String getEncoding(ContentTransferEncodingField f) {
      return f != null && f.getEncoding().length() != 0 ? f.getEncoding() : "7bit";
   }
}
