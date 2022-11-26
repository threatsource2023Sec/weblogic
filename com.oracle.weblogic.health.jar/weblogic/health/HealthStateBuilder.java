package weblogic.health;

import java.util.LinkedList;
import java.util.List;

public class HealthStateBuilder {
   private int state;
   private List symptoms;
   private String healthMBeanName;
   private String healthMBeanType;
   private String subsystemName;
   private boolean isCritical;
   private String partition;

   public HealthStateBuilder() {
      this(0, new LinkedList(), (String)null, (String)null, (String)null, false);
   }

   public HealthStateBuilder(HealthState healthState) {
      this(healthState.getState(), toList(healthState.getSymptoms()), healthState.getMBeanName(), healthState.getMBeanType(), healthState.getSubsystemName(), healthState.getPartitionName(), healthState.isCritical());
   }

   public HealthStateBuilder(int state, List symptoms, String healthMBeanName, String healthMBeanType, String subsystemName, boolean isCritical) {
      this(state, symptoms, healthMBeanName, healthMBeanType, subsystemName, (String)null, isCritical);
   }

   public HealthStateBuilder(int state, List symptoms, String healthMBeanName, String healthMBeanType, String subsystemName, String partition, boolean isCritical) {
      this.state = state;
      this.symptoms = symptoms;
      this.healthMBeanName = healthMBeanName;
      this.healthMBeanType = healthMBeanType;
      this.subsystemName = subsystemName;
      this.partition = partition;
      this.isCritical = isCritical;
   }

   static List toList(Symptom[] theSymptoms) {
      List symptoms = new LinkedList();
      if (theSymptoms != null) {
         Symptom[] var2 = theSymptoms;
         int var3 = theSymptoms.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            Symptom s = var2[var4];
            symptoms.add(s);
         }
      }

      return symptoms;
   }

   public int append(HealthState healthState) {
      if (healthState.isCritical()) {
         this.isCritical = true;
      }

      return this.append(healthState.getState(), healthState.getPartitionName(), healthState.getSymptoms());
   }

   public int append(int newCode, Symptom... newSymptoms) {
      return this.append(newCode, (String)null, newSymptoms);
   }

   public int append(int newCode, String newPartition, Symptom... newSymptoms) {
      if (newCode != 0 && HealthState.compareSeverities(this.state, newCode) < 0) {
         this.state = newCode;
      }

      if (this.partition == null) {
         this.partition = newPartition;
      } else if (newPartition != null && !this.partition.equals(newPartition)) {
         this.partition = "DOMAIN";
      }

      if (newSymptoms != null && newSymptoms.length > 0) {
         if (this.symptoms == null) {
            this.symptoms = new LinkedList();
         }

         Symptom[] var4 = newSymptoms;
         int var5 = newSymptoms.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            Symptom s = var4[var6];
            this.symptoms.add(s);
         }
      }

      return this.state;
   }

   public HealthState get() {
      HealthState hs = new HealthState(this.state, (Symptom[])this.symptoms.toArray(new Symptom[this.symptoms.size()]));
      hs.setCritical(this.isCritical);
      hs.setMBeanName(this.healthMBeanName);
      hs.setMBeanType(this.healthMBeanType);
      hs.setSubsystemName(this.subsystemName);
      hs.setPartitionName(this.partition);
      return hs;
   }
}
