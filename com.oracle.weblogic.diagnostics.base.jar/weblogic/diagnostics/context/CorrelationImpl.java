package weblogic.diagnostics.context;

import java.io.IOException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import oracle.dms.context.internal.wls.WLSContextFamily;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.diagnostics.flightrecorder.JFRDebug;
import weblogic.utils.PropertyHelper;
import weblogic.workarea.NoWorkContextException;
import weblogic.workarea.PrimitiveContextFactory;
import weblogic.workarea.PropertyReadOnlyException;
import weblogic.workarea.SerializableWorkContext;
import weblogic.workarea.WorkContextMap;

public final class CorrelationImpl implements Correlation {
   private static final long serialVersionUID = 1L;
   private static final String LEGACY_WLDF_PAYLOAD_KEY = "__WLDF_LEGACY_PAYLOAD_";
   static final String DYE_VECTOR_KEY = "__WLDF_DYE_VECTOR_";
   private Level logLevel = null;
   private String ecid = null;
   private RID rid = null;
   private Map values = null;
   private long dyeVector = 0L;
   private String legacyDCID = null;
   private Object dmsObject = null;
   private boolean inheritable = true;
   private static DebugLogger debugLog = DebugLogger.getDebugLogger("DebugDiagnosticDataGathering");
   private static final boolean DUMP_DC_CREATEMARSHAL_LOCATIONS = PropertyHelper.getBoolean("weblogic.diagnostics.DumpDCCreateMarshalLocations");
   private static boolean debugContributorInitialized = false;
   private static Method debugContributorGetInstanceMtd = null;
   private static final char[] int2hex = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
   private static final int SEQID_LENGTH = 8;
   private static SecureRandom random = new SecureRandom();
   private static int seqID = 0;
   private static char[] baseID = createBaseID();
   private static final Object syncID = new Object();
   private static final DebugLogger DEBUG_LOGGER = DebugLogger.getDebugLogger("DebugDiagnosticContext");
   private boolean isUnmarshalled;

   public String getECID() {
      return this.ecid;
   }

   public String getRID() {
      return this.rid == null ? null : this.rid.toString();
   }

   public int[] getRIDComponents() {
      return this.rid == null ? null : this.rid.getComponents();
   }

   RID getRIDInternal() {
      return this.rid;
   }

   void setRIDInternal(RID rid) {
      this.rid = rid;
   }

   public int[] newChildRIDComponents() {
      return this.rid == null ? null : this.rid.produceChildComponents();
   }

   public int getRIDChildCount() {
      return this.rid == null ? 0 : this.rid.getChildCount();
   }

   public int incAndGetChildRIDCount() {
      return this.rid == null ? 0 : this.rid.incAndGetChildRIDCount();
   }

   public long getDyeVector() {
      return this.dyeVector;
   }

   public Level getLogLevel() {
      return this.logLevel;
   }

   public String getPayload() {
      return this.values == null ? null : (String)this.values.get("__WLDF_LEGACY_PAYLOAD_");
   }

   public void setPayload(String payload) {
      if (this.values == null) {
         this.values = new HashMap();
      }

      this.values.put("__WLDF_LEGACY_PAYLOAD_", payload);
   }

   public Map values() {
      if (this.values == null) {
         this.values = new HashMap();
      }

      return this.values;
   }

   public void setDyeVector(long dye) {
      this.dyeVector = dye;
   }

   void alignDyeVector() {
      if (this.dyeVector != 0L) {
         if (this.values == null) {
            this.values = new HashMap();
         }

         this.values.put("__WLDF_DYE_VECTOR_", Long.toString(this.dyeVector));
      }
   }

   public final Object setDMSObject(Object p) {
      Object retValue = this.dmsObject;
      this.dmsObject = p;
      return retValue;
   }

   public final Object getDMSObject() {
      return this.dmsObject;
   }

   public boolean getInheritable() {
      return this.inheritable;
   }

   public void setInheritable(boolean inheritable) {
      if (this.inheritable != inheritable) {
         this.inheritable = inheritable;
         CorrelationFactory.updateCorrelation(this);
      }
   }

   public void setLogLevel(Level logLevel) {
      this.logLevel = logLevel;
   }

   static final void blockPropagationOnceBeforeSend(WorkContextMap map) {
      SerializableWorkContext mapEntry = (SerializableWorkContext)map.get("oracle.dms.context.internal.wls.WLSContextFamily");
      if (mapEntry != null) {
         try {
            map.put("oracle.dms.context.internal.wls.WLSContextFamily", mapEntry, CorrelationFactory.getNonInheritablePropagationMode());
         } catch (PropertyReadOnlyException var3) {
         }

      }
   }

   static final void restorePropagationAfterSend(WorkContextMap map) {
      SerializableWorkContext mapEntry = (SerializableWorkContext)map.get("oracle.dms.context.internal.wls.WLSContextFamily");
      if (mapEntry != null) {
         try {
            map.put("oracle.dms.context.internal.wls.WLSContextFamily", mapEntry, CorrelationFactory.getPropagationMode());
         } catch (PropertyReadOnlyException var3) {
         }

      }
   }

   static SerializableWorkContext getWorkContext(Correlation ctx) throws IOException {
      return (SerializableWorkContext)PrimitiveContextFactory.createMutable(new WLSContextFamily.SerializableImpl(ctx));
   }

   static CorrelationImpl getCorrelationFromMap(WorkContextMap map) {
      SerializableWorkContext mapEntry = (SerializableWorkContext)map.get("oracle.dms.context.internal.wls.WLSContextFamily");
      if (mapEntry == null) {
         return null;
      } else {
         CorrelationImpl correlation = null;

         try {
            WLSContextFamily.SerializableImpl serImpl = null;
            serImpl = (WLSContextFamily.SerializableImpl)mapEntry.getSerializable();
            correlation = (CorrelationImpl)serImpl.getCorrelation();
         } catch (IOException var4) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("getCorrelationFromMap failed", var4);
            }
         } catch (ClassNotFoundException var5) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("getCorrelationFromMap failed", var5);
            }
         }

         return correlation;
      }
   }

   public void setDye(int dye, boolean enable) throws InvalidDyeException {
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

   public boolean isDyedWith(int dye) throws InvalidDyeException {
      if (dye >= 0 && dye <= 63) {
         return (this.dyeVector & 1L << dye) != 0L;
      } else {
         throw new InvalidDyeException("Invalid dye index " + dye);
      }
   }

   public String getLegacyDCID() {
      return this.legacyDCID;
   }

   void setLegacyDCID(String legacyDCID) {
      this.legacyDCID = legacyDCID;
   }

   public static final void unitTestAdvanceSeq() {
      synchronized(syncID) {
         if (seqID < 2147483645) {
            seqID = 2147483645;
         }
      }
   }

   CorrelationImpl() {
      this.init(true, (String)null);
      if (DEBUG_LOGGER.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("Correlation", "new CorrelationImpl()", (Throwable)null, getDCDebugContributor(this.ecid, this.getRID()));
      }

   }

   CorrelationImpl(DiagnosticContextImpl dcImpl) {
      this.ecid = dcImpl.getContextId();
      this.setDyeVector(dcImpl.getDyeVector());
      this.setPayload(dcImpl.getPayload());
      this.isUnmarshalled = true;
      this.rid = new RID(false);
      this.inheritable = true;
      if (DEBUG_LOGGER.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("Correlation", "new CorrelationImpl(DCImpl)", (Throwable)null, getDCDebugContributor(this.ecid, this.getRID()));
      }

   }

   CorrelationImpl(WrappedContextComponents components) {
      this.ecid = components.getECID();
      int[] ridComponents = components.getRIDComponents();
      if (ridComponents != null) {
         this.rid = new RID(components.getRIDComponents());
      } else {
         this.rid = new RID(false);
      }

      this.values = components.getParameters();
      if (this.values != null) {
         String value = (String)this.values.get("__WLDF_DYE_VECTOR_");
         if (value != null) {
            this.dyeVector = Long.parseLong(value);
         }
      }

      this.isUnmarshalled = true;
      this.inheritable = true;
      this.logLevel = components.getLogLevel();
   }

   CorrelationImpl(String ecid, int[] ridComponents, int kidCount, Map values, long dyeVector, boolean inheritable) {
      this.ecid = ecid;
      this.rid = new RID(ridComponents, kidCount);
      this.dyeVector = dyeVector;
      this.values = values;
      this.inheritable = inheritable;
      this.logLevel = null;
      this.isUnmarshalled = false;
      if (DUMP_DC_CREATEMARSHAL_LOCATIONS || debugLog.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("Correlation", "DMS formed", (Throwable)null, getDCDebugContributor(ecid, this.getRID()));
      }

   }

   CorrelationImpl(String ecid, RID rid, long dyeVector) {
      this.ecid = ecid;
      this.rid = rid;
      this.dyeVector = dyeVector;
      this.logLevel = null;
      this.isUnmarshalled = true;
      this.inheritable = true;
      if (DUMP_DC_CREATEMARSHAL_LOCATIONS || debugLog.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("Correlation", "Unwrapped", (Throwable)null, getDCDebugContributor(ecid, this.getRID()));
      }

   }

   CorrelationImpl(CorrelationImpl original) {
      this.dmsObject = null;
      this.ecid = original.ecid;
      this.rid = original.rid;
      this.dyeVector = original.dyeVector;
      this.isUnmarshalled = original.isUnmarshalled;
      this.legacyDCID = original.legacyDCID;
      this.logLevel = original.logLevel;
      if (original.values != null) {
         this.values = new HashMap();
         this.values.putAll(original.values);
      }

      if (DEBUG_LOGGER.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("Correlation", "new CorrelationImpl(Correlation)", (Throwable)null, getDCDebugContributor(this.ecid, this.getRID()));
      }

   }

   static void produceChildCorrelationIfNeeded(WorkContextMap map) {
      SerializableWorkContext mapEntry = (SerializableWorkContext)map.get("oracle.dms.context.internal.wls.WLSContextFamily");
      if (mapEntry != null) {
         CorrelationImpl parent = null;

         try {
            WLSContextFamily.SerializableImpl serImpl = null;
            serImpl = (WLSContextFamily.SerializableImpl)mapEntry.getSerializable();
            parent = (CorrelationImpl)serImpl.getCorrelation();
            if (parent == null || parent.rid == null) {
               return;
            }

            if (!parent.inheritable) {
               try {
                  map.remove("oracle.dms.context.internal.wls.WLSContextFamily");
               } catch (NoWorkContextException var5) {
               }

               return;
            }

            CorrelationImpl child = new CorrelationImpl(parent);
            child.rid = parent.rid.produceChild();
            map.put("oracle.dms.context.internal.wls.WLSContextFamily", getWorkContext(child), CorrelationFactory.getPropagationMode());
            if (DEBUG_LOGGER.isDebugEnabled()) {
               JFRDebug.generateDebugEvent("Correlation", "produceChildCorrelation", (Throwable)null, getDCDebugContributor(child.ecid, child.getRID()));
            }
         } catch (IOException var6) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("produceChildCorrelationIfNeeded failed", var6);
            }
         } catch (ClassNotFoundException var7) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("produceChildCorrelationIfNeeded failed", var7);
            }
         } catch (PropertyReadOnlyException var8) {
            if (DEBUG_LOGGER.isDebugEnabled()) {
               DEBUG_LOGGER.debug("produceChildCorrelationIfNeeded failed", var8);
            }
         }

      }
   }

   void init(boolean generateIdIfNull, String dcid) {
      if (dcid != null) {
         this.ecid = dcid;
      } else if (generateIdIfNull) {
         this.ecid = this.generateId();
         this.rid = new RID(true);
      }

      this.dyeVector = 0L;
      this.logLevel = null;
      this.isUnmarshalled = false;
      this.inheritable = true;
      if (DUMP_DC_CREATEMARSHAL_LOCATIONS || debugLog.isDebugEnabled()) {
         JFRDebug.generateDebugEvent("Correlation", "Context Init", (Throwable)null, getDCDebugContributor(this.ecid, this.getRID()));
      }

   }

   void treatAsIfUnMarshalled() {
      this.isUnmarshalled = true;
   }

   public boolean isUnmarshalled() {
      return this.isUnmarshalled;
   }

   private final String generateId() {
      int id = false;
      char[] currentBaseID;
      int id;
      synchronized(syncID) {
         if (seqID == Integer.MAX_VALUE) {
            seqID = 0;
            baseID = createBaseID();
         }

         id = ++seqID;
         currentBaseID = baseID;
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

   private static synchronized void ensureDebugContributorInitialized() {
      if (!debugContributorInitialized) {
         try {
            Class managerClazz = Class.forName("weblogic.diagnostics.context.CorrelationDebugContributor");
            debugContributorGetInstanceMtd = managerClazz.getDeclaredMethod("getInstance", String.class, String.class);
         } catch (Throwable var1) {
            debugContributorGetInstanceMtd = null;
         }

         debugContributorInitialized = true;
      }
   }

   static Object getDCDebugContributor(String ECID, String RID) {
      if (!debugContributorInitialized) {
         ensureDebugContributorInitialized();
      }

      if (debugContributorGetInstanceMtd == null) {
         return null;
      } else {
         try {
            return debugContributorGetInstanceMtd.invoke((Object)null, ECID, RID);
         } catch (Exception var3) {
            return null;
         }
      }
   }

   void forTestSetIsUnmarshalled(boolean value) {
      this.isUnmarshalled = value;
   }
}
