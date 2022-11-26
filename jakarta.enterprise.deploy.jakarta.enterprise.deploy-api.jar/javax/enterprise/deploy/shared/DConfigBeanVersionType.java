package javax.enterprise.deploy.shared;

public class DConfigBeanVersionType {
   private int value;
   public static final DConfigBeanVersionType V1_3 = new DConfigBeanVersionType(0);
   public static final DConfigBeanVersionType V1_3_1 = new DConfigBeanVersionType(1);
   public static final DConfigBeanVersionType V1_4 = new DConfigBeanVersionType(2);
   public static final DConfigBeanVersionType V5 = new DConfigBeanVersionType(3);
   private static final String[] stringTable = new String[]{"V1_3", "V1_3_1", "V1_4", "V5"};
   private static final DConfigBeanVersionType[] enumValueTable;

   protected DConfigBeanVersionType(int value) {
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }

   protected String[] getStringTable() {
      return stringTable;
   }

   protected DConfigBeanVersionType[] getEnumValueTable() {
      return enumValueTable;
   }

   public static DConfigBeanVersionType getDConfigBeanVersionType(int value) {
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
      enumValueTable = new DConfigBeanVersionType[]{V1_3, V1_3_1, V1_4, V5};
   }
}
