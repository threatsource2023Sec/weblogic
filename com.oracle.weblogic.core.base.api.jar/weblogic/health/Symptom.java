package weblogic.health;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public class Symptom implements Serializable {
   private static final String EMPTY_STRING = "";
   public static final String OPEN_TYPE_NAME = "Symptom";
   public static final String OPEN_DESCRIPTION = "This object represents WLS Health Symptom.";
   public static final String ITEM_TYPE = "Type";
   public static final String ITEM_SEVERITY = "Severity";
   public static final String ITEM_INSTANCEID = "InstanceID";
   public static final String ITEM_INFO = "Info";
   private static String[] itemNames = new String[]{"Type", "Severity", "InstanceID", "Info"};
   private static String[] itemDescriptions = new String[]{"Type of health symptom.", "Symptom severity.", "Identifier of instance for which the symptom is reported.", "Additional information, or reason."};
   private SymptomType type;
   private Severity severity;
   private String instanceId;
   private String info;

   public Symptom(SymptomType type, Severity severity, String instanceId, String info) {
      this.type = type;
      this.severity = severity;
      this.instanceId = instanceId != null ? instanceId : "";
      this.info = info != null ? info : "";
   }

   public SymptomType getType() {
      return this.type;
   }

   public Severity getSeverity() {
      return this.severity;
   }

   public String getInstanceId() {
      return this.instanceId;
   }

   public String getInfo() {
      return this.info;
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(getCompositeType(), this.getCompositeDataMap());
      return cds;
   }

   static CompositeType getCompositeType() throws OpenDataException {
      OpenType[] itemTypes = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, SimpleType.STRING};
      CompositeType ct = new CompositeType("Symptom", "This object represents WLS Health Symptom.", itemNames, itemDescriptions, itemTypes);
      return ct;
   }

   protected Map getCompositeDataMap() {
      Map data = new HashMap();
      data.put("Type", this.type.toString());
      data.put("Severity", this.severity.toString());
      data.put("InstanceID", sanitize(this.instanceId));
      data.put("Info", sanitize(this.info));
      return data;
   }

   private static String sanitize(String s) {
      return s != null ? s : "";
   }

   public boolean equals(Object obj) {
      if (obj instanceof Symptom) {
         Symptom other = (Symptom)obj;
         if (this == other) {
            return true;
         } else {
            return this.type == other.type && this.severity == other.severity && compareStrings(this.instanceId, other.instanceId) && compareStrings(this.info, other.info);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      int hash = this.type.hashCode() + 3 * this.severity.hashCode() + 7 * this.instanceId.hashCode() + 11 * this.info.hashCode();
      return hash;
   }

   private static boolean compareStrings(String s1, String s2) {
      if (s1 == s2) {
         return true;
      } else {
         return s1 != null ? s1.equals(s2) : false;
      }
   }

   public String toString() {
      StringBuilder buf = new StringBuilder();
      buf.append("Symtom{").append(this.type).append(",").append(this.severity).append(",").append(this.instanceId).append(",").append(this.info).append("}");
      return buf.toString();
   }

   public static Severity healthStateSeverity(int state) {
      switch (state) {
         case 1:
            return Symptom.Severity.MEDIUM;
         case 2:
         case 3:
         case 4:
            return Symptom.Severity.HIGH;
         default:
            return Symptom.Severity.LOW;
      }
   }

   public static enum Severity {
      LOW,
      MEDIUM,
      HIGH;
   }

   public static enum SymptomType {
      UNKNOWN,
      LOW_MEMORY,
      HIGH_CPU,
      NETWORK_ERROR,
      CONNECTION_ERROR,
      STUCK_THREADS,
      THREAD_DEADLOCK,
      SOCKET_THRESHOLD_EXCEEDED,
      EXECUTEQUEUE_OVERFLOW,
      CLUSTER_ERROR,
      CONNECTOR_ERROR,
      CONNECTION_POOL_UNHEALTHY,
      CONNECTION_POOL_OVERLOADED,
      WORKMANAGER_SHARED_CAPACITY_EXCEEDED,
      WORKMANAGER_OVERLOADED,
      TRANSACTION,
      JMS_ERROR,
      JMS_MSG_THRESHOLD_TIME_EXCEEDED,
      JMS_MSG_THRESHOLD_RUNNINGTIME_EXCEEDED,
      JMS_BYTES_THRESHOLD_TIME_EXCEEDED,
      JMS_BYTES_THRESHOLD_RUNNINGTIME_EXCEEDED,
      SAF_SEND_ERROR,
      SAF_RECEIVE_ERROR,
      STORE_ERROR,
      MDB;
   }
}
