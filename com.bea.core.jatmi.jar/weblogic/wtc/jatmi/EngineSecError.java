package weblogic.wtc.jatmi;

public final class EngineSecError extends Exception {
   public int errno;
   public int needspace;
   public static final int EE_SEC_FAILURE = -3001;
   public static final int EE_SEC_INVAL = -3002;
   public static final int EE_SEC_SYSTEM = -3003;
   public static final int EE_SEC_STATUS = -3004;
   public static final int EE_SEC_NOSPACE = -3005;
   public static final int EE_SEC_NOT_SUPPORTED = -3006;
   public static final int EE_SEC_UNKNOWN = -3007;
   public static final int EE_SEC_FIXED = -3008;
   public static final int EE_SEC_RANGE = -3009;
   public static final int EE_SEC_NOT_PRIV = -3010;
   public static final int EE_SEC_NOT_FOUND = -3011;
   public static final int EE_SEC_TRUSTED = -3012;
   public static final int EE_SEC_PERM = -3013;
   public static final int EE_SEC_EXPIRED = -3014;
   public static final int EE_SEC_LIMIT = -3015;
   public static final int EE_SEC_NOTFOUND = -3016;
   public static final int EE_SEC_DENIED = -3017;
   public static final int EE_SEC_ABSTAIN = -3018;
   public static final int EE_SEC_COMPLETE = -3019;
   public static final int EE_SEC_CONTINUE = -3020;
   public static final int EE_SEC_PERMIT = 0;

   public EngineSecError() {
      this.errno = 0;
   }

   public EngineSecError(int errno) {
      this.errno = errno;
   }

   public EngineSecError(int errno, String explain) {
      super(explain);
      this.errno = errno;
   }

   public EngineSecError(int errno, int needsize) {
      this.errno = errno;
      this.needspace = needsize;
   }
}
