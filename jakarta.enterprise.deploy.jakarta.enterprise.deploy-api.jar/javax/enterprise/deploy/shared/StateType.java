package javax.enterprise.deploy.shared;

public class StateType {
   private int value;
   public static final StateType RUNNING = new StateType(0);
   public static final StateType COMPLETED = new StateType(1);
   public static final StateType FAILED = new StateType(2);
   public static final StateType RELEASED = new StateType(3);
   private static final String[] stringTable = new String[]{"running", "completed", "failed", "released"};
   private static final StateType[] enumValueTable;

   protected StateType(int value) {
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }

   protected String[] getStringTable() {
      return stringTable;
   }

   protected StateType[] getEnumValueTable() {
      return enumValueTable;
   }

   public static StateType getStateType(int value) {
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
      enumValueTable = new StateType[]{RUNNING, COMPLETED, FAILED, RELEASED};
   }
}
