package weblogic.management.patching.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

public class MigrationInfo implements Serializable {
   private static final long serialVersionUID = 1842557886108008394L;
   Map wsmInfo;
   JMSInfo jmsInfo;
   JTAInfo jtaInfo;
   boolean jmsFailbackAfterMigrationToUnpatched = false;
   boolean jtaFailbackAfterMigrationToUnpatched = false;
   boolean wsmFailbackAfterMigrationToUnpatched = false;

   public JMSInfo getJMSInfo() {
      return this.jmsInfo;
   }

   public void setJMSInfo(JMSInfo info) {
      this.jmsInfo = info;
   }

   public JTAInfo getJTAInfo() {
      return this.jtaInfo;
   }

   public void setJTAInfo(JTAInfo info) {
      this.jtaInfo = info;
   }

   public Map getWSMInfo() {
      return this.wsmInfo;
   }

   public void putWSMInfo(String machine, String failback) {
      this.wsmInfo = Collections.singletonMap(machine, failback);
   }

   public String getWSMInfoDestinationMachine() {
      return (String)this.wsmInfo.keySet().iterator().next();
   }

   public boolean isWSMInfoFailback() {
      return ((String)this.wsmInfo.values().iterator().next()).equalsIgnoreCase(Boolean.TRUE.toString());
   }

   public boolean isJmsFailbackAfterMigrationToUnpatched() {
      return this.jmsFailbackAfterMigrationToUnpatched;
   }

   public void setJmsFailbackAfterMigrationToUnpatched(boolean jmsFailbackAfterMigrationToUnpatched) {
      this.jmsFailbackAfterMigrationToUnpatched = jmsFailbackAfterMigrationToUnpatched;
   }

   public boolean isJtaFailbackAfterMigrationToUnpatched() {
      return this.jtaFailbackAfterMigrationToUnpatched;
   }

   public void setJtaFailbackAfterMigrationToUnpatched(boolean jtaFailbackAfterMigrationToUnpatched) {
      this.jtaFailbackAfterMigrationToUnpatched = jtaFailbackAfterMigrationToUnpatched;
   }

   public boolean isWsmFailbackAfterMigrationToUnpatched() {
      return this.wsmFailbackAfterMigrationToUnpatched;
   }

   public void setWsmFailbackAfterMigrationToUnpatched(boolean wsmFailbackAfterMigrationToUnpatched) {
      this.wsmFailbackAfterMigrationToUnpatched = wsmFailbackAfterMigrationToUnpatched;
   }

   public String toString() {
      StringBuffer sb = new StringBuffer();
      if (this.jmsInfo != null) {
         sb.append(" jmsInfo: ");
         sb.append(this.jmsInfo.toString());
      }

      if (this.jtaInfo != null) {
         sb.append(", jtaInfo: ");
         sb.append(this.jtaInfo.toString());
      }

      if (this.wsmInfo != null) {
         sb.append(", wsmInfo: ");
         sb.append(this.wsmInfo.toString());
      }

      return sb.toString();
   }
}
