package weblogic.health;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.management.openmbean.ArrayType;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;
import javax.management.openmbean.CompositeType;
import javax.management.openmbean.OpenDataException;
import javax.management.openmbean.OpenType;
import javax.management.openmbean.SimpleType;

public final class HealthState implements Serializable {
   private static final long serialVersionUID = -8954060526499390055L;
   public static final int HEALTH_OK = 0;
   public static final int HEALTH_WARN = 1;
   public static final int HEALTH_CRITICAL = 2;
   public static final int HEALTH_FAILED = 3;
   public static final int HEALTH_OVERLOADED = 4;
   public static final String LOW_MEMORY_REASON = "server is low on memory";
   private static final String[] NULL_REASONS = new String[0];
   private static final Symptom[] NULL_SYMPTOMS = new Symptom[0];
   private final int state;
   private final String[] reasonCode;
   private final Symptom[] symptoms;
   private String partitionName;
   private String subsystemName;
   public static final String OPEN_TYPE_NAME = "HealthState";
   public static final String OPEN_DESCRIPTION = "This object represents WLS HealthState.";
   public static final String ITEM_COMPONENT = "Component";
   public static final String ITEM_HEALTH_STATE = "HealthState";
   public static final String ITEM_MBEAN = "MBean";
   public static final String ITEM_SYMPTOMS = "Symptoms";
   public static final String ITEM_REASON_CODE = "ReasonCode";
   public static final String ITEM_IS_CRITICAL = "IsCritical";
   public static final String ITEM_PARTITION = "Partition";
   private static String[] itemNames = new String[]{"Component", "HealthState", "MBean", "Symptoms", "ReasonCode", "IsCritical", "Partition"};
   private static String[] itemDescriptions = new String[]{"The name of the component providing health information.", "The state of the service.", "The MBean name.", "Health symptoms", "The service specific diagnostic code.", "If the health state is critical.", "Partition name"};
   private static final int[] logicalSeverityOrder = new int[]{0, 1, 2, 4, 3};
   private boolean isCritical;
   private String mbeanName;
   private String mbeanType;

   public HealthState(int s) {
      this(s, NULL_SYMPTOMS);
   }

   public HealthState(int state, Symptom symptom) {
      this(state, (Symptom)symptom, (String)null);
   }

   public HealthState(int state, Symptom symptom, String partitionName) {
      this(state, symptom != null ? new Symptom[]{symptom} : null, partitionName);
   }

   public HealthState(int state, Symptom[] symptoms) {
      this(state, (Symptom[])symptoms, (String)null);
   }

   public HealthState(int state, Symptom[] symptoms, String partitionName) {
      this.state = state;
      this.reasonCode = this.symptomsToReasoncode(symptoms);
      this.symptoms = symptoms != null ? symptoms : NULL_SYMPTOMS;
      this.partitionName = partitionName;
   }

   private String[] symptomsToReasoncode(Symptom[] symptoms) {
      int length = symptoms != null ? symptoms.length : 0;
      if (length == 0) {
         return NULL_REASONS;
      } else {
         String[] arr = new String[length];

         for(int i = 0; i < length; ++i) {
            arr[i] = symptoms[i].toString();
         }

         return arr;
      }
   }

   public String getSubsystemName() {
      return this.subsystemName;
   }

   public int getState() {
      return this.state;
   }

   public int compareSeverityTo(HealthState other) {
      int otherState = other.getState();
      return this.compareSeverityTo(otherState);
   }

   public int compareSeverityTo(int otherState) {
      return compareSeverities(this.getState(), otherState);
   }

   public static int compareSeverities(int aState, int baseState) {
      if (baseState == aState) {
         return 0;
      } else {
         int otherLogicalSeverity = logicalSeverityOrder[baseState];
         int logicalSeverity = logicalSeverityOrder[aState];
         return logicalSeverity - otherLogicalSeverity;
      }
   }

   /** @deprecated */
   @Deprecated
   public String[] getReasonCode() {
      return this.reasonCode;
   }

   public String getReasonCodeSummary() {
      StringBuffer sb = new StringBuffer();
      String[] var2 = this.reasonCode;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String reason = var2[var4];
         sb.append(reason);
         sb.append(13);
      }

      return sb.toString();
   }

   public String toString() {
      StringBuffer buf = new StringBuffer("Component:" + this.subsystemName + ",");
      buf.append("Partition: " + this.partitionName + ",");
      buf.append("State:" + mapToString(this.state) + ",");
      buf.append("MBean:" + this.mbeanName + ",");
      buf.append("Symptoms:[");
      int lcv;
      if (this.symptoms != null && this.symptoms.length > 0) {
         for(lcv = 0; lcv < this.symptoms.length - 1; ++lcv) {
            buf.append(this.symptoms[lcv]).append(",");
         }

         buf.append(this.symptoms[this.symptoms.length - 1]);
      }

      buf.append("], ");
      buf.append("ReasonCode:[");
      if (this.reasonCode != null && this.reasonCode.length > 0) {
         for(lcv = 0; lcv < this.reasonCode.length - 1; ++lcv) {
            buf.append(this.reasonCode[lcv] + ",");
         }

         buf.append(this.reasonCode[this.reasonCode.length - 1]);
      }

      buf.append("]");
      return buf.toString();
   }

   public static String mapToString(int intValue) {
      switch (intValue) {
         case 0:
            return "HEALTH_OK";
         case 1:
            return "HEALTH_WARN";
         case 2:
            return "HEALTH_CRITICAL";
         case 3:
            return "HEALTH_FAILED";
         case 4:
            return "HEALTH_OVERLOADED";
         default:
            return "UNKNOWN";
      }
   }

   public void setSubsystemName(String key) {
      this.subsystemName = key;
   }

   public CompositeData toCompositeData() throws OpenDataException {
      CompositeDataSupport cds = new CompositeDataSupport(this.getCompositeType(), this.getCompositeDataMap());
      return cds;
   }

   protected CompositeType getCompositeType() throws OpenDataException {
      OpenType[] itemTypes = new OpenType[]{SimpleType.STRING, SimpleType.STRING, SimpleType.STRING, ArrayType.getArrayType(Symptom.getCompositeType()), ArrayType.getArrayType(SimpleType.STRING), SimpleType.BOOLEAN, SimpleType.STRING};
      CompositeType ct = new CompositeType("HealthState", "This object represents WLS HealthState.", itemNames, itemDescriptions, itemTypes);
      return ct;
   }

   protected Map getCompositeDataMap() throws OpenDataException {
      Map data = new HashMap();
      CompositeData[] symptomsData = new CompositeData[this.symptoms.length];

      for(int i = 0; i < this.symptoms.length; ++i) {
         symptomsData[i] = this.symptoms[i].toCompositeData();
      }

      data.put("Component", this.getSubsystemName());
      data.put("HealthState", mapToString(this.state));
      data.put("MBean", this.getMBeanName());
      data.put("Symptoms", symptomsData);
      data.put("ReasonCode", this.getReasonCode());
      data.put("IsCritical", new Boolean(this.isCritical()));
      data.put("Partition", this.getPartitionName());
      return data;
   }

   public void setCritical(boolean isCritical) {
      this.isCritical = isCritical;
   }

   public boolean isCritical() {
      return this.isCritical;
   }

   public void setMBeanName(String name) {
      this.mbeanName = name;
   }

   public String getMBeanName() {
      return this.mbeanName;
   }

   public void setMBeanType(String type) {
      this.mbeanType = type;
   }

   public String getMBeanType() {
      return this.mbeanType;
   }

   public Symptom[] getSymptoms() {
      return this.symptoms;
   }

   public String getPartitionName() {
      return this.partitionName;
   }

   public void setPartitionName(String partitionName) {
      this.partitionName = partitionName;
   }
}
