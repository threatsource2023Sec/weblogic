package org.glassfish.grizzly.http.util;

import java.nio.charset.Charset;
import org.glassfish.grizzly.utils.Charsets;

public final class Constants {
   public static final byte CR = 13;
   public static final byte LF = 10;
   public static final byte SP = 32;
   public static final byte HT = 9;
   public static final byte COMMA = 44;
   public static final byte COLON = 58;
   public static final byte SEMI_COLON = 59;
   public static final byte A = 65;
   public static final byte a = 97;
   public static final byte Z = 90;
   public static final byte QUESTION = 63;
   public static final byte LC_OFFSET = -32;
   public static final String DEFAULT_RESPONSE_TYPE = null;
   public static final String CHUNKED_ENCODING = "chunked";
   public static final String FORM_POST_CONTENT_TYPE = "application/x-www-form-urlencoded";
   public static final int KEEP_ALIVE_TIMEOUT_IN_SECONDS = 30;
   public static final int DEFAULT_MAX_KEEP_ALIVE = 256;
   public static final String DEFAULT_HTTP_CHARACTER_ENCODING = System.getProperty(Constants.class.getName() + ".default-character-encoding", "ISO-8859-1");
   public static final Charset DEFAULT_HTTP_CHARSET;
   public static final byte[] IDENTITY;

   static {
      DEFAULT_HTTP_CHARSET = Charsets.lookupCharset(DEFAULT_HTTP_CHARACTER_ENCODING);
      IDENTITY = "identity".getBytes(DEFAULT_HTTP_CHARSET);
   }
}
