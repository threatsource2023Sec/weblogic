package javax.enterprise.deploy.shared;

public class ActionType {
   private int value;
   public static final ActionType EXECUTE = new ActionType(0);
   public static final ActionType CANCEL = new ActionType(1);
   public static final ActionType STOP = new ActionType(2);
   private static final String[] stringTable = new String[]{"execute", "cancel", "stop"};
   private static final ActionType[] enumValueTable;

   protected ActionType(int value) {
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }

   protected String[] getStringTable() {
      return stringTable;
   }

   protected ActionType[] getEnumValueTable() {
      return enumValueTable;
   }

   public static ActionType getActionType(int value) {
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
      enumValueTable = new ActionType[]{EXECUTE, CANCEL, STOP};
   }
}
