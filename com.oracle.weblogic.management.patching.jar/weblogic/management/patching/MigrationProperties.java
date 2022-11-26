package weblogic.management.patching;

import java.io.Serializable;

public class MigrationProperties implements Serializable {
   private static final long serialVersionUID = 1L;
   public static final String SOURCE_KEY = "source";
   public static final String DESTINATION_KEY = "destination";
   public static final String MIGRATION_TYPE_KEY = "migrationType";
   public static final String FAILBACK_KEY = "failback";
   String source;
   String destination;
   MigrationType migrationType;
   boolean failback;

   public MigrationProperties() {
      this("", "", MigrationProperties.MigrationType.ALL, false);
   }

   public MigrationProperties(String source, String destination, MigrationType migrationType, boolean failback) {
      this.source = source;
      this.destination = destination;
      this.migrationType = migrationType;
      this.failback = failback;
   }

   public String getSource() {
      return this.source;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public String getDestination() {
      return this.destination;
   }

   public void setDestination(String destination) {
      this.destination = destination;
   }

   public boolean getFailback() {
      return this.failback;
   }

   public void setFailback(boolean failback) {
      this.failback = failback;
   }

   public MigrationType getMigrationType() {
      return this.migrationType;
   }

   public void setMigrationType(MigrationType migrationType) {
      this.migrationType = migrationType;
   }

   public void setMigrationType(String migrationType) {
      MigrationType stageMode = MigrationProperties.MigrationType.getMigrationType(migrationType);
      this.setMigrationType(stageMode);
   }

   static enum MigrationType {
      JMS("jms"),
      JTA("jta"),
      SERVER("server"),
      ALL("all"),
      NONE("none");

      String type;

      private MigrationType(String type) {
         this.type = type;
      }

      public String toString() {
         return this.type;
      }

      public static MigrationType getMigrationType(String mType) {
         MigrationType mode = null;
         if (mType != null && !mType.isEmpty()) {
            if (mType.equals(JMS.type)) {
               mode = JMS;
            } else if (mType.equals(JTA.type)) {
               mode = JTA;
            } else if (mType.equals(SERVER.type)) {
               mode = SERVER;
            } else if (mType.equals(ALL.type)) {
               mode = ALL;
            } else if (mType.equals(NONE.type)) {
               mode = NONE;
            }
         }

         return mode;
      }
   }
}
