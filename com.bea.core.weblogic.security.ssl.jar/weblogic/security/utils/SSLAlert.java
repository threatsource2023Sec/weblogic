package weblogic.security.utils;

public final class SSLAlert {
   public static final int CLOSE_NOTIFY = 0;
   public static final int UNEXPECTED_MESSAGE = 10;
   public static final int BAD_RECORD_MAC = 20;
   public static final int DECRYPTION_FAILED = 21;
   public static final int RECORD_OVERFLOW = 22;
   public static final int DECOMPRESSION_FAILURE = 30;
   public static final int HANDSHAKE_FAILURE = 40;
   public static final int NO_CERTIFICATE = 41;
   public static final int BAD_CERTIFICATE = 42;
   public static final int UNSUPPORTED_CERTIFICATE = 43;
   public static final int CERTIFICATE_REVOKED = 44;
   public static final int CERTIFICATE_EXPIRED = 45;
   public static final int CERTIFICATE_UNKNOWN = 46;
   public static final int ILLEGAL_PARAMETER = 47;
   public static final int UNKNOWN_CA = 48;
   public static final int ACCESS_DENIED = 49;
   public static final int DECODE_ERROR = 50;
   public static final int DECRYPT_ERROR = 51;
   public static final int EXPORT_RESTRICTION = 60;
   public static final int PROTOCOL_VERSION = 70;
   public static final int INSUFFICIENT_SECURITY = 71;
   public static final int INTERNAL_ERROR = 80;
   public static final int USER_CANCELLED = 90;
   public static final int NO_RENEGOTIATION = 100;
}
