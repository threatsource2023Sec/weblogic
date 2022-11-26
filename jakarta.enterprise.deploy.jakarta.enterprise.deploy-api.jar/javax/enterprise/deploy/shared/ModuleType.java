package javax.enterprise.deploy.shared;

public class ModuleType {
   private int value;
   public static final ModuleType EAR = new ModuleType(0);
   public static final ModuleType EJB = new ModuleType(1);
   public static final ModuleType CAR = new ModuleType(2);
   public static final ModuleType RAR = new ModuleType(3);
   public static final ModuleType WAR = new ModuleType(4);
   private static final String[] stringTable = new String[]{"ear", "ejb", "car", "rar", "war"};
   private static final ModuleType[] enumValueTable;
   private static final String[] moduleExtension;

   protected ModuleType(int value) {
      this.value = value;
   }

   public int getValue() {
      return this.value;
   }

   protected String[] getStringTable() {
      return stringTable;
   }

   protected ModuleType[] getEnumValueTable() {
      return enumValueTable;
   }

   public String getModuleExtension() {
      return moduleExtension[this.getValue()];
   }

   public static ModuleType getModuleType(int value) {
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
      enumValueTable = new ModuleType[]{EAR, EJB, CAR, RAR, WAR};
      moduleExtension = new String[]{".ear", ".jar", ".jar", ".rar", ".war"};
   }
}
