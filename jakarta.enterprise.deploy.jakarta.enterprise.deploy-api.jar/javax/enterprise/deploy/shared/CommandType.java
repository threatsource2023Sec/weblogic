package javax.enterprise.deploy.shared;

public class CommandType {
   private int value;
   public static final CommandType DISTRIBUTE = new CommandType(0);
   public static final CommandType START = new CommandType(1);
   public static final CommandType STOP = new CommandType(2);
   public static final CommandType UNDEPLOY = new CommandType(3);
   public static final CommandType REDEPLOY = new CommandType(4);
   private static final String[] stringTable = new String[]{"distribute", "start", "stop", "undeploy", "redeploy"};
   private static final CommandType[] enumValueTable;

   protected CommandType(int value) {
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }

   protected String[] getStringTable() {
      return stringTable;
   }

   protected CommandType[] getEnumValueTable() {
      return enumValueTable;
   }

   public static CommandType getCommandType(int value) {
      return enumValueTable[value];
   }

   public String toString() {
      String[] strTable = this.getStringTable();
      int index = this.value - this.getOffset();
      return strTable != null && index >= 0 && index < strTable.length ? strTable[index] : Integer.toString(this.value);
   }

   protected int getOffset() {
      return 0;
   }

   static {
      enumValueTable = new CommandType[]{DISTRIBUTE, START, STOP, UNDEPLOY, REDEPLOY};
   }
}
