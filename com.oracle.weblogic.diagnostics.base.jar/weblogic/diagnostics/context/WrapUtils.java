package weblogic.diagnostics.context;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.JFRDebug;

public class WrapUtils {
   private static final char VERSION_ONE_PREFIX = '1';
   private static final char VERSION_TWO_PREFIX = '2';
   private static final char VERSION_SEPARATOR = '.';
   private static final char NULL_VERSION = '\u0000';
   public static final int DEFAULT_MAX_WRAP_SIZE = 16384;
   private static int WRAP_MAX_CHARS = Integer.MAX_VALUE;
   private static final char ContextWrapSepV1 = ';';
   private static final char ContextWrapSepV2 = '.';
   private static final byte RawEncodedSep = 59;
   private static final byte ContextWrapRidSep = -128;
   private static final byte ContextWrapParamLog = 1;
   private static final byte ContextWrapParamLimit = 2;
   private static final byte ContextWrapParamName = 4;
   private static final byte ContextWrapParamValue = 8;
   private static final byte ContextWrapParamBase = -128;
   private static final String ContextStringEncode = "UTF-8";
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
   private static final String LOG_KEY = "ctxLogLevel";
   private static final String SQL_KEY = "ctxUpdateSql";
   private static final String TIME_KEY = "ctxTimeOut";
   private static final String TRUE_VALUE = "true";

   public static Correlation unwrap(String wrapString) {
      WrappedContextComponents components = new WrapStringDecoderImpl(wrapString);
      return components == null ? null : new CorrelationImpl(components);
   }

   public static String wrap(Correlation correlation) {
      String wrapString = "";
      if (correlation == null) {
         return wrapString;
      } else {
         try {
            CorrelationImpl corrImpl = (CorrelationImpl)correlation;
            corrImpl.alignDyeVector();
            wrapString = wrap(correlation.getECID(), correlation.newChildRIDComponents(), correlation.values(), (Collection)null, false, corrImpl.getLogLevel(), WRAP_MAX_CHARS);
         } catch (Throwable var3) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Failed to wrap the Correlation", var3);
               JFRDebug.generateDebugEvent("CorrelationWrap", "wrap failed: " + var3, var3, CorrelationDebugContributor.getInstance(correlation));
            }
         }

         return wrapString;
      }
   }

   private static String wrap(String ecid, int[] ridComponents, Map parameterMap, Collection logKeys, Boolean isUpdateSqlText, Level logLevel, int maxChars) {
      int size = false;
      int maxCharsRemaining = maxChars - ecid.length() - 1;
      byte[] ridBytes = null;
      byte[] ridBytes = getWrapRid(ridComponents);
      if (ContextEncode.byteCountToCharCount(1 + ridBytes.length) > maxCharsRemaining) {
         throw new IllegalArgumentException("Can not generate wrap string of max length " + maxChars + " that encodes only the ECID ... and RID ... ");
      } else {
         int size = 1 + ridBytes.length;
         int maxBytesRemaining = ContextEncode.charCountToByteCount(maxCharsRemaining) - size;
         LinkedList parameterList = new LinkedList();
         size += parameterGetList(parameterMap, logKeys, isUpdateSqlText, logLevel, parameterList, maxBytesRemaining);
         byte[] raw = new byte[size];
         int index = copyBytes(raw, 0, ridBytes, 0);
         raw[index] = 59;
         ++index;
         parameterFormatList(parameterList, raw, index);
         String encode = ContextEncode.encodeBytes(raw);
         size = ecid.length() + encode.length() + 1;
         StringBuilder wrap = new StringBuilder(size);
         wrap.append('1').append('.');
         wrap.append(ecid);
         wrap.append(';');
         wrap.append(encode);
         String retVal = wrap.toString();
         if (DEBUG_LOGGER.isDebugEnabled()) {
            DEBUG_LOGGER.debug("Generated wrap string: {" + retVal + "}");
         }

         return retVal;
      }
   }

   private static int parameterGetList(Map parameterMap, Collection logKeys, Boolean isUpdateSqlText, Level logLevel, LinkedList list, int maxSize) {
      int size = 0;
      if (parameterMap != null) {
         Iterator var7 = parameterMap.entrySet().iterator();

         label95:
         while(true) {
            String key;
            byte[] keyRaw;
            byte[] valueRaw;
            while(true) {
               String value;
               do {
                  if (!var7.hasNext()) {
                     break label95;
                  }

                  Map.Entry entry = (Map.Entry)var7.next();
                  key = (String)entry.getKey();
                  value = (String)entry.getValue();
               } while(value == null);

               try {
                  keyRaw = key.getBytes("UTF-8");
                  valueRaw = value.getBytes("UTF-8");
                  if (keyRaw.length <= 4095) {
                     if (valueRaw.length > 4095) {
                        if (DEBUG_LOGGER.isDebugEnabled()) {
                           DEBUG_LOGGER.debug("Truncating value from valueMap because it is too long: value-size(in bytes)={" + valueRaw.length + "}, key={" + key + "}, value={" + value + "}");
                        }

                        valueRaw = truncateString(value, 4095);
                     }
                     break;
                  }

                  if (DEBUG_LOGGER.isDebugEnabled()) {
                     DEBUG_LOGGER.debug("Skipping key+value from valueMap because key contains too many bytes: key-size(in bytes)={" + keyRaw.length + "}, key={" + key + "}");
                  }
               } catch (Exception var18) {
               }
            }

            int pendingSize = size + 3 + keyRaw.length;
            if (keyRaw.length > 63) {
               ++pendingSize;
            }

            pendingSize += valueRaw.length;
            if (valueRaw.length > 63) {
               ++pendingSize;
            }

            if (pendingSize >= maxSize) {
               break;
            }

            boolean toLog;
            if (logKeys != null && logKeys.contains(key)) {
               toLog = true;
            } else {
               toLog = false;
            }

            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("Wrapped key {" + key + "}");
            }

            ParameterStore store = new ParameterStore(keyRaw, valueRaw, toLog, false);
            list.addLast(store);
            size = pendingSize;
         }
      }

      byte[] keyRaw;
      if (isUpdateSqlText != null && isUpdateSqlText) {
         try {
            byte[] keyRaw = "ctxUpdateSql".getBytes("UTF-8");
            keyRaw = "true".getBytes("UTF-8");
            int pendingSize = size + 3 + keyRaw.length + keyRaw.length;
            if (pendingSize < maxSize) {
               size = pendingSize;
               ParameterStore store = new ParameterStore(keyRaw, keyRaw, false, false);
               list.addLast(store);
            }
         } catch (Exception var17) {
         }
      }

      if (logLevel != null) {
         String value = logLevel.toString();

         try {
            keyRaw = "ctxLogLevel".getBytes("UTF-8");
            byte[] valueRaw = value.getBytes("UTF-8");
            int pendingSize = size + 3 + keyRaw.length + valueRaw.length;
            if (valueRaw.length > 63) {
               ++pendingSize;
            }

            if (pendingSize < maxSize) {
               size = pendingSize;
               ParameterStore store = new ParameterStore(keyRaw, valueRaw, false, false);
               list.addLast(store);
            }
         } catch (Exception var16) {
         }
      }

      return size;
   }

   private static void parameterFormatList(LinkedList list, byte[] raw, int index) {
      for(int size = list.size(); size != 0; --size) {
         ParameterStore store = (ParameterStore)list.removeFirst();
         index = parameterFormat(raw, index, store);
      }

   }

   private static int parameterFormat(byte[] raw, int index, ParameterStore store) {
      byte delim = -128;
      if (store.paramLog) {
         delim = (byte)(delim | 1);
      }

      if (store.paramLimit) {
         delim = (byte)(delim | 2);
      }

      int offset = index + 1;
      int length = 1;
      if (store.paramKey.length > 63) {
         delim = (byte)(delim | 4);
         ++length;
      }

      ContextEncode.encodeInt64Byte(store.paramKey.length, raw, offset, length);
      offset += length;
      offset = copyBytes(raw, offset, store.paramKey, 0);
      length = 1;
      if (store.paramValue.length > 63) {
         delim = (byte)(delim | 8);
         ++length;
      }

      ContextEncode.encodeInt64Byte(store.paramValue.length, raw, offset, length);
      offset += length;
      offset = copyBytes(raw, offset, store.paramValue, 0);
      raw[index] = delim;
      return offset;
   }

   private static char getUnwrapVersion(String wrappedString) {
      char unwrapVersion = 0;
      if (wrappedString != null && wrappedString.length() >= 2 && wrappedString.charAt(1) == '.') {
         if (wrappedString.charAt(0) == '1') {
            unwrapVersion = '1';
         } else if (wrappedString.charAt(0) == '2') {
            unwrapVersion = '2';
         }
      }

      return unwrapVersion;
   }

   private static int[] getUnwrapRID(byte[] raw, int end) {
      int[] retVal = null;
      int totalDepth = false;
      int currDepth = 0;
      int index = false;

      int index;
      for(index = 0; index < end; ++index) {
         if ((byte)(raw[index] & -128) == -128) {
            ++currDepth;
         }
      }

      int totalDepth = currDepth;
      if (currDepth > 0) {
         currDepth = 0;
         retVal = new int[totalDepth];
         int start = 0;

         for(index = 0; index < end; ++index) {
            if ((byte)(raw[index] & -128) == -128) {
               raw[index] = (byte)(raw[index] & 127);
               int length = index - start + 1;
               int val = ContextEncode.decodeInt64(raw, start, length);
               retVal[currDepth] = val;
               ++currDepth;
               start = index + 1;
            }
         }
      }

      return retVal;
   }

   private static byte[] getWrapRid(int[] levels) {
      int size = levels.length * 6;
      char[] rid = new char[size];
      int index = 0;

      for(int depth = 0; depth < levels.length; ++depth) {
         int length = ContextEncode.encodeInt64var(levels[depth], rid, index);
         index += length;
         rid[index - 1] |= 'ﾀ';
      }

      size = index;
      byte[] raw = new byte[index];

      for(index = 0; index < size; ++index) {
         raw[index] = (byte)rid[index];
      }

      return raw;
   }

   private static int copyBytes(byte[] dest, int dIndex, byte[] source, int sIndex) {
      while(sIndex < source.length) {
         dest[dIndex] = source[sIndex];
         ++dIndex;
         ++sIndex;
      }

      return dIndex;
   }

   private static byte[] truncateString(String string, int maxBytes) {
      byte[] retVal = null;
      byte[] tmpByteArray = new byte[maxBytes];
      ByteBuffer byteBuffer = ByteBuffer.wrap(tmpByteArray);
      CharBuffer charBuffer = CharBuffer.wrap(string.toCharArray());
      Charset charset = Charset.forName("UTF-8");
      CharsetEncoder encoder = charset.newEncoder();
      encoder.encode(charBuffer, byteBuffer, true);
      byte[] retVal;
      if (byteBuffer.position() == maxBytes) {
         retVal = tmpByteArray;
      } else {
         retVal = new byte[byteBuffer.position()];
         System.arraycopy(tmpByteArray, 0, retVal, 0, retVal.length);
      }

      return retVal;
   }

   public static void main(String[] args) {
      if (args.length <= 1 && (args.length != 1 || args[0] != null)) {
         String outWrap = null;
         CorrelationImpl correlation = null;
         if (args.length == 0) {
            correlation = new CorrelationImpl();
            outWrap = wrap(correlation);
            correlation = (CorrelationImpl)unwrap(outWrap);
         } else {
            correlation = (CorrelationImpl)unwrap(args[0]);
         }

         if (correlation == null) {
            System.out.println("Correlation: null");
         } else {
            System.out.println("Correlation:");
            System.out.println("  ecid:    " + correlation.getECID());
            System.out.println("  rid:     " + correlation.getRID());
            System.out.println("  dye:     " + correlation.getDyeVector());
            System.out.println("  payload: " + correlation.getPayload());
            Map values = correlation.values();
            if (values != null && !values.isEmpty()) {
               Set keys = values.keySet();
               System.out.println("  values:");
               Iterator var5 = keys.iterator();

               while(var5.hasNext()) {
                  String key = (String)var5.next();
                  System.out.println("      Key: " + key + ", value: " + (String)values.get(key));
               }
            } else {
               System.out.println("  values: <empty>");
            }

            if (args.length == 0) {
               System.out.println("Wrap: " + outWrap);
            }

         }
      } else {
         System.out.println("usage: java weblogic.diagnostics.context.WrapUtils");
         System.out.println("usage: java weblogic.diagnostics.context.WrapUtils wrap-string");
      }
   }

   private static class AugmentedKey {
      String mKeyName;
      boolean mIsLoggable;
      boolean mIsLimit;

      AugmentedKey(String keyName, boolean isLoggable, boolean isLimit) {
         this.mKeyName = keyName;
         this.mIsLoggable = isLoggable;
         this.mIsLimit = isLimit;
      }
   }

   private static class WrapStringDecoderImpl implements WrappedContextComponents {
      private String mWrappedStringVn;
      private int mWrappedStringVnPos;
      private char mUnwrapVersion;
      private byte[] mRawBytes;
      private int mRawBytesPos;
      private String mECID;
      private int[] mRIDComponents;
      private Level mLogLevel;
      private Map mValueMap;
      private Boolean mUpdateSqlText;
      private int mState = 0;
      private static int DONE_ECID = 1;
      private static int DONE_RID = 2;
      private static int DONE_PARAMS = 3;

      public WrapStringDecoderImpl(String wrappedString) {
         this.mUnwrapVersion = WrapUtils.getUnwrapVersion(wrappedString);
         if (this.mUnwrapVersion == 0) {
            throw new IllegalArgumentException("No valid version information found in wrap string.");
         } else if ('1' != this.mUnwrapVersion && '2' != this.mUnwrapVersion) {
            throw new IllegalArgumentException("Unsupported version of wrap string.");
         } else {
            this.mWrappedStringVn = wrappedString.substring(2);
            this.mWrappedStringVnPos = 0;
         }
      }

      public String getECID() {
         if (this.mState >= DONE_ECID) {
            return this.mECID;
         } else {
            int index = 0;
            if (this.mUnwrapVersion == '1') {
               index = this.mWrappedStringVn.indexOf(59);
            } else if (this.mUnwrapVersion == '2') {
               index = this.mWrappedStringVn.indexOf(46);
            }

            if (index > 0) {
               this.mECID = this.mWrappedStringVn.substring(0, index);
               this.mWrappedStringVnPos = index + 1;
               this.mState = DONE_ECID;
               return this.mECID;
            } else {
               throw new IllegalArgumentException("Wrap string format invalid - no delineation between ECID and encoded values.");
            }
         }
      }

      public int[] getRIDComponents() {
         if (this.mState >= DONE_RID) {
            return this.mRIDComponents;
         } else {
            this.getECID();
            String base64EncodedData = this.mWrappedStringVn.substring(this.mWrappedStringVnPos, this.mWrappedStringVn.length());
            this.mRawBytes = ContextEncode.decodeBytes(base64EncodedData);

            for(this.mRawBytesPos = 0; this.mRawBytesPos < this.mRawBytes.length && this.mRawBytes[this.mRawBytesPos] != 59; ++this.mRawBytesPos) {
            }

            if (this.mRawBytesPos < this.mRawBytes.length) {
               this.mRIDComponents = WrapUtils.getUnwrapRID(this.mRawBytes, this.mRawBytesPos);
               this.mState = DONE_RID;
               return this.mRIDComponents;
            } else {
               throw new IllegalArgumentException("Wrap string format invalid - no end-of-RID marker found in encoded values.");
            }
         }
      }

      public Map getParameters() {
         this.extractParameters();
         Map retVal = null;
         if (this.mValueMap.size() > 0) {
            retVal = new HashMap();
            Iterator var2 = this.mValueMap.entrySet().iterator();

            while(var2.hasNext()) {
               Map.Entry entry = (Map.Entry)var2.next();
               retVal.put(((AugmentedKey)entry.getKey()).mKeyName, entry.getValue());
            }
         }

         return retVal;
      }

      public Set getLogKeys() {
         this.extractParameters();
         Set retVal = null;
         Iterator var2 = this.mValueMap.entrySet().iterator();

         while(var2.hasNext()) {
            Map.Entry entry = (Map.Entry)var2.next();
            if (((AugmentedKey)entry.getKey()).mIsLoggable) {
               if (retVal == null) {
                  retVal = new HashSet();
               }

               retVal.add(((AugmentedKey)entry.getKey()).mKeyName);
            }
         }

         return retVal;
      }

      public Level getLogLevel() {
         this.extractParameters();
         return this.mLogLevel;
      }

      private void extractParameters() {
         if (this.mState < DONE_PARAMS) {
            this.getRIDComponents();
            this.mValueMap = new HashMap();
            this.mLogLevel = null;
            this.mUpdateSqlText = null;
            ++this.mRawBytesPos;
            if (this.mRawBytesPos < this.mRawBytes.length) {
               int index = this.mRawBytesPos;
               int remain = this.mRawBytes.length - index;

               while(remain >= 5 && (byte)(this.mRawBytes[index] & -128) == -128) {
                  byte delim = this.mRawBytes[index];
                  ++index;
                  boolean log;
                  if ((byte)(delim & 1) == 1) {
                     log = true;
                  } else {
                     log = false;
                  }

                  boolean limit;
                  if ((byte)(delim & 2) == 2) {
                     limit = true;
                  } else {
                     limit = false;
                  }

                  int size = 1;
                  if ((byte)(delim & 4) == 4) {
                     ++size;
                  }

                  int length = ContextEncode.decodeInt64(this.mRawBytes, index, size);
                  remain -= 1 + size + length;
                  if (remain <= 1) {
                     break;
                  }

                  index += size;

                  String key;
                  try {
                     key = new String(this.mRawBytes, index, length, "UTF-8");
                  } catch (Exception var13) {
                     break;
                  }

                  index += length;
                  size = 1;
                  if ((byte)(delim & 8) == 8) {
                     ++size;
                  }

                  length = ContextEncode.decodeInt64(this.mRawBytes, index, size);
                  remain -= size + length;
                  if (remain < 0) {
                     break;
                  }

                  index += size;

                  String value;
                  try {
                     value = new String(this.mRawBytes, index, length, "UTF-8");
                  } catch (Exception var12) {
                     break;
                  }

                  index += length;
                  if (key.equals("ctxLogLevel")) {
                     try {
                        this.mLogLevel = Level.parse(value);
                     } catch (IllegalArgumentException var11) {
                        this.mLogLevel = null;
                     }
                  } else if (key.equals("ctxUpdateSql")) {
                     this.mUpdateSqlText = true;
                  } else {
                     AugmentedKey augmentedKey = new AugmentedKey(key, log, limit);
                     this.mValueMap.put(augmentedKey, value);
                  }
               }

               this.mRawBytesPos = index;
            }

            this.mState = DONE_PARAMS;
         }
      }

      public Boolean getUpdateSqlText() {
         return this.mUpdateSqlText;
      }
   }

   private static class ParameterStore {
      byte[] paramKey;
      byte[] paramValue;
      boolean paramLog;
      boolean paramLimit;

      ParameterStore(byte[] key, byte[] value, boolean log, boolean limit) {
         this.paramKey = key;
         this.paramValue = value;
         this.paramLog = log;
         this.paramLimit = limit;
      }
   }
}
