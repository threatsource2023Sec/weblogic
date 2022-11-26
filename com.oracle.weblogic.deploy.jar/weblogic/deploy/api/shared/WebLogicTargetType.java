package weblogic.deploy.api.shared;

import java.io.Serializable;

public class WebLogicTargetType implements Serializable {
   private static final long serialVersionUID = 1L;
   private int value;
   private static final WebLogicTargetType[] enumValueTable;
   private static String[] stringTable;
   public static final WebLogicTargetType SERVER = new WebLogicTargetType(0);
   public static final WebLogicTargetType CLUSTER = new WebLogicTargetType(1);
   public static final WebLogicTargetType VIRTUALHOST = new WebLogicTargetType(2);
   public static final WebLogicTargetType VIRTUALTARGET = new WebLogicTargetType(5);
   public static final WebLogicTargetType JMSSERVER = new WebLogicTargetType(3);
   public static final WebLogicTargetType SAFAGENT = new WebLogicTargetType(4);

   public WebLogicTargetType() {
   }

   protected WebLogicTargetType(int i) {
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }

   protected String[] getStringTable() {
      return stringTable;
   }

   protected WebLogicTargetType[] getEnumValueTable() {
      return enumValueTable;
   }

   public static WebLogicTargetType getWebLogicTargetType(int i) {
      return enumValueTable[i];
   }

   public String toString() {
      String[] as = this.getStringTable();
      int i = this.value - this.getOffset();
      return as != null && i >= 0 && i < as.length ? as[i] : Integer.toString(this.value);
   }

   protected int getOffset() {
      return 0;
   }

   static {
      enumValueTable = new WebLogicTargetType[]{SERVER, CLUSTER, VIRTUALHOST, JMSSERVER, SAFAGENT, VIRTUALTARGET};
      stringTable = new String[]{"server", "cluster", "virtual host", "JMS server", "SAF agent", "virtual target"};
   }
}
