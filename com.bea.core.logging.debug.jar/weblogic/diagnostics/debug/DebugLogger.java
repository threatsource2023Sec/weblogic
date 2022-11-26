package weblogic.diagnostics.debug;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class DebugLogger {
   private static final String EMPTY_STRING = "";
   private static ThreadLocal recursionState = new ThreadLocal() {
      public Object initialValue() {
         return Boolean.FALSE;
      }
   };
   private static final boolean DISABLED_FLAG = isDebugDisabled();
   static final int CTX_MODE_OFF = 0;
   static final int CTX_MODE_AND = 1;
   static final int CTX_MODE_OR = 2;
   private static DebugLoggerRepository defaultRepository = new DebugLoggerRepository();
   private static DebugContext debugContext = null;
   private static int contextMode = 0;
   private static long debugMask = 0L;
   private static PartitionContextProvider partitionContextProvider;
   private static Map partitionDebugConfig = new ConcurrentHashMap();
   private String debugLoggerName = null;
   private String displayName = null;
   private boolean debugEnabled = false;
   private Map debugParams = new ConcurrentHashMap();
   private DebugLoggerRepository debugLoggerRepository;

   public static DebugLoggerRepository getDefaultDebugLoggerRepository() {
      return defaultRepository;
   }

   public static void setPartitionContextProvider(PartitionContextProvider partitionCtxProvider) {
      partitionContextProvider = partitionCtxProvider;
   }

   public static void initializePartitionDebugConfig(String partitionName, Map config) {
      partitionDebugConfig.put(partitionName, config);
   }

   public static void removePartitionDebugConfig(String partitionName) {
      partitionDebugConfig.remove(partitionName);
   }

   private static boolean isDebugDisabled() {
      try {
         return Boolean.getBoolean("weblogic.diagnostics.debug.DebugLogger.DISABLED");
      } catch (Exception var1) {
         return false;
      }
   }

   public static DebugLogger getDebugLogger(String loggerName) {
      return defaultRepository.getDebugLogger(loggerName);
   }

   public static DebugLogger createUnregisteredDebugLogger(String loggerName, boolean initialValue) {
      DebugLogger logger = new DebugLogger(loggerName, getDefaultDebugLoggerRepository());
      logger.setDebugEnabled(initialValue);
      return logger;
   }

   static void setContextMode(int mode) {
      contextMode = mode;
   }

   static void setDebugContext(DebugContext dbgCtx) {
      debugContext = dbgCtx;
   }

   static void setDebugMask(long dbgMask) {
      debugMask = dbgMask;
   }

   DebugLogger(String attributeName, DebugLoggerRepository debugLoggerRepository) {
      this.debugLoggerName = attributeName;
      this.displayName = this.debugLoggerName.replaceFirst("Debug", "");
      this.debugLoggerRepository = debugLoggerRepository;
   }

   public String getDebugLoggerName() {
      return this.debugLoggerName;
   }

   public final boolean isDebugEnabled() {
      return !DISABLED_FLAG ? this.isDebugEnabledInternal() : false;
   }

   private final boolean isDebugEnabledInternal() {
      switch (contextMode) {
         case 1:
            return this.checkDebugEnabledState() && (debugMask & this.getDyeVector()) == debugMask;
         case 2:
            return this.checkDebugEnabledState() && (debugMask & this.getDyeVector()) != 0L;
         default:
            return this.checkDebugEnabledState();
      }
   }

   private boolean checkDebugEnabledState() {
      if (partitionContextProvider != null) {
         String pname = partitionContextProvider.getCurrentPartitionName();
         if (pname != null && !pname.isEmpty()) {
            Map partitionMap = (Map)partitionDebugConfig.get(pname);
            if (partitionMap != null) {
               Boolean enabled = (Boolean)partitionMap.get(this.debugLoggerName);
               if (enabled != null) {
                  return enabled;
               }
            }
         }
      }

      return this.debugEnabled;
   }

   final void setDebugEnabled(boolean value) {
      this.debugEnabled = value;
   }

   public void debug(String msg) {
      this.log(Level.FINE, msg, (Throwable)null);
   }

   public void debug(String msg, Throwable t) {
      this.log(Level.FINE, msg, t);
   }

   public Map getDebugParameters() {
      return this.debugParams;
   }

   private void log(Level level, String msg, Throwable th) {
      if (this.isDebugEnabled()) {
         LogRecord lr = new LogRecord(level, msg);
         lr.setLoggerName(this.displayName);
         lr.setThrown(th);
         lr.setSourceClassName("");
         lr.setSourceMethodName("");
         this.debugLoggerRepository.getLogger().log(lr);
      }

   }

   private long getDyeVector() {
      long value = 0L;
      Boolean state = (Boolean)recursionState.get();
      if (state == Boolean.FALSE) {
         recursionState.set(Boolean.TRUE);
         value = debugContext.getDyeVector();
         recursionState.set(Boolean.FALSE);
      }

      return value;
   }

   public static void println(String str) {
      System.out.println(str);
   }
}
