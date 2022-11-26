package weblogic.diagnostics.context;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.UUID;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.workarea.WorkContextInput;
import weblogic.workarea.WorkContextOutput;

public final class DiagnosticContextImpl implements DiagnosticContext {
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static final char[] int2hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final char DUMMY_DCID_PREFIX = 'Z';
   private static final int SEQID_LENGTH = 8;
   private static SecureRandom random = new SecureRandom();
   private static int seqID = 0;
   private static char[] baseID = createBaseID();
   private static char[] dummyBaseID;
   private static final Object syncID;
   private static final DebugLogger DEBUG_LOGGER;
   private String contextId;
   private long dyeVector;
   private String payload;
   private static final String ENCODEDID_DELIMITER = "|";
   private String rid;
   private boolean isUnmarshalled;
   private String legacyDCID;
   private int logLevel = -1;
   private String extraneousInfo;
   static volatile boolean incomingDCImplsNotSeen;

   public static final void unitTestAdvanceSeq() {
      synchronized(syncID) {
         if (seqID < 2147483645) {
            seqID = 2147483645;
         }
      }
   }

   public DiagnosticContextImpl() {
      this.init(true, (String)null);
   }

   DiagnosticContextImpl(boolean generateIdIfNull, String dcid) {
      this.init(generateIdIfNull, dcid);
   }

   void setContextId(String newId) {
      this.contextId = newId;
   }

   public void init(boolean generateIdIfNull, String dcid) {
      if (dcid != null) {
         this.contextId = dcid;
      } else if (generateIdIfNull) {
         this.contextId = this.generateId(false);
      }

      this.dyeVector = 0L;
      this.payload = null;
      this.rid = null;
      this.logLevel = -1;
      this.legacyDCID = null;
      this.extraneousInfo = null;
      this.isUnmarshalled = false;
   }

   private final String generateId(boolean genDummy) {
      int id = false;
      char[] currentBaseID;
      int id;
      synchronized(syncID) {
         if (seqID == Integer.MAX_VALUE) {
            seqID = 0;
            baseID = createBaseID();
            dummyBaseID = createDummyBaseID(baseID);
         }

         id = ++seqID;
         currentBaseID = genDummy ? dummyBaseID : baseID;
      }

      int baseLength = currentBaseID.length;
      int len = baseLength + 8;
      char[] contextIdChars = new char[len];
      System.arraycopy(currentBaseID, 0, contextIdChars, 0, baseLength);

      for(int i = len - 1; i >= baseLength; --i) {
         contextIdChars[i] = int2hex[id & 15];
         id >>= 4;
      }

      return new String(contextIdChars);
   }

   private static char[] createBaseID() {
      byte[] randomBytes = new byte[16];
      random.nextBytes(randomBytes);
      randomBytes[6] = (byte)(randomBytes[6] & 15);
      randomBytes[6] = (byte)(randomBytes[6] | 64);
      randomBytes[8] = (byte)(randomBytes[8] & 63);
      randomBytes[8] = (byte)(randomBytes[8] | 128);
      long mostSignificant = 0L;
      long leastSignificant = 0L;

      int i;
      for(i = 0; i < 8; ++i) {
         mostSignificant = mostSignificant << 8 | (long)(randomBytes[i] & 255);
      }

      for(i = 8; i < 16; ++i) {
         leastSignificant = leastSignificant << 8 | (long)(randomBytes[i] & 255);
      }

      UUID uuId = new UUID(mostSignificant, leastSignificant);
      String str = uuId.toString() + "-";
      int len = str.length();
      char[] id = new char[len];
      str.getChars(0, len, id, 0);
      return id;
   }

   private static char[] createDummyBaseID(char[] baseID) {
      char[] id = new char[baseID.length + 1];
      id[0] = 'Z';
      System.arraycopy(baseID, 0, id, 1, baseID.length);
      return id;
   }

   public String getContextId() {
      return this.contextId;
   }

   public void setDye(byte dye, boolean enable) throws InvalidDyeException {
      if (dye >= 0 && dye <= 63) {
         long val = 1L << dye;
         synchronized(this) {
            if (enable) {
               this.dyeVector |= val;
            } else {
               this.dyeVector &= ~val;
            }

         }
      } else {
         throw new InvalidDyeException("Invalid dye index " + dye);
      }
   }

   public boolean isDyedWith(byte dye) throws InvalidDyeException {
      if (dye >= 0 && dye <= 63) {
         return (this.dyeVector & 1L << dye) != 0L;
      } else {
         throw new InvalidDyeException("Invalid dye index " + dye);
      }
   }

   public void setDyeVector(long vector) {
      this.dyeVector = vector;
   }

   public long getDyeVector() {
      return this.dyeVector;
   }

   public String getPayload() {
      return this.payload;
   }

   public void setPayload(String payload) {
      this.payload = payload;
   }

   public void writeContext(WorkContextOutput out) throws IOException {
      out.writeASCII(this.encodeDCID());
      out.writeLong(this.dyeVector);
      if (this.payload != null) {
         out.writeBoolean(true);
         out.writeASCII(this.payload);
      } else {
         out.writeBoolean(false);
      }

   }

   public void readContext(WorkContextInput in) throws IOException {
      this.decodeDCID(in.readASCII());
      this.dyeVector = in.readLong();
      if (in.readBoolean()) {
         this.payload = in.readASCII();
      } else {
         this.payload = null;
      }

      DiagnosticContextFactory.handleReadContextUpdate(this);
      this.isUnmarshalled = true;
      incomingDCImplsNotSeen = false;
   }

   void setRID(String rid) {
      this.rid = rid;
   }

   public int getLogLevel() {
      return this.logLevel;
   }

   public void setLogLevel(int level) {
      this.logLevel = level;
   }

   public String getRID() {
      return this.rid;
   }

   public boolean isUnmarshalled() {
      return this.isUnmarshalled;
   }

   public String getExtraneousInfo() {
      return this.extraneousInfo;
   }

   private String encodeDCID() {
      if (this.legacyDCID != null) {
         return this.legacyDCID;
      } else {
         StringBuffer sb = new StringBuffer();
         sb.append("|");
         sb.append(this.contextId);
         sb.append("|");
         sb.append("|");
         if (this.extraneousInfo != null) {
            sb.append(this.extraneousInfo);
         }

         return sb.toString();
      }
   }

   private void decodeDCID(String dcid) {
      if (dcid != null && dcid.length() != 0) {
         this.rid = null;
         if (!dcid.startsWith("|")) {
            this.legacyDCID = dcid;
            this.contextId = dcid;
         } else {
            int length = dcid.length();
            if (length == 1) {
               this.contextId = this.generateId(true);
               if (debugLog.isDebugEnabled()) {
                  debugLog.debug("encodedDCID with only a single delimiter was found, generated DCID: " + this.contextId);
               }

            } else {
               int curPos = 1;
               int nextSep = dcid.indexOf(124, curPos);
               if (nextSep == -1) {
                  this.contextId = dcid.substring(curPos, length);
               } else {
                  if (nextSep > curPos) {
                     this.contextId = dcid.substring(curPos, nextSep);
                  } else {
                     this.contextId = this.generateId(true);
                     if (debugLog.isDebugEnabled()) {
                        debugLog.debug("empty DCID found in encodedDCID, generated DCID: " + this.contextId);
                     }
                  }

                  curPos = nextSep + 1;
                  if (curPos < length) {
                     nextSep = dcid.indexOf(124, curPos);
                     if (nextSep != -1) {
                        curPos = nextSep + 1;
                        if (curPos < length) {
                           this.extraneousInfo = dcid.substring(curPos, length);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   DiagnosticContextImpl(Correlation correlation) {
      this.contextId = correlation.getECID();
      this.dyeVector = correlation.getDyeVector();
      this.payload = correlation.getPayload();
      this.legacyDCID = correlation.getLegacyDCID();
      this.extraneousInfo = null;
   }

   String getLegacyDCID() {
      return this.legacyDCID;
   }

   void forTestSetIsUnmarshalled(boolean value) {
      this.isUnmarshalled = value;
   }

   static void forTestSetIncomingDCImplsSeen(boolean value) {
      incomingDCImplsNotSeen = value;
   }

   static {
      dummyBaseID = createDummyBaseID(baseID);
      syncID = new Object();
      DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
      incomingDCImplsNotSeen = true;
   }
}
