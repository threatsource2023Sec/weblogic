package weblogic.deploy.api.shared;

import java.io.File;
import javax.enterprise.deploy.shared.ModuleType;

public class WebLogicModuleType extends ModuleType {
   private int value;
   private static int base = findNextSlot();
   private static int numMods;
   private static final int WLS_OFFSET = 323;
   public static final WebLogicModuleType UNKNOWN;
   public static final WebLogicModuleType JMS;
   public static final WebLogicModuleType JDBC;
   public static final WebLogicModuleType INTERCEPT;
   public static final WebLogicModuleType CONFIG;
   public static final WebLogicModuleType SUBMODULE;
   public static final WebLogicModuleType WLDF;
   public static final WebLogicModuleType WSEE;
   public static final WebLogicModuleType SCA_COMPOSITE;
   public static final WebLogicModuleType SCA_JAVA;
   public static final WebLogicModuleType SCA_SPRING;
   public static final WebLogicModuleType SCA_BPEL;
   public static final WebLogicModuleType SCA_EXTENSION;
   public static final WebLogicModuleType COHERENCE_CLUSTER;
   public static final WebLogicModuleType GAR;
   public static final WebLogicModuleType REST_WEBSERVICE;
   private static final String[] WLStringTable = new String[]{"unknown", "jms", "jdbc", "intercept", "config", "submodule", "wldf", "webservice", "sca composite", "sca java", "sca spring", "sca bpel", "sca extension", "coherence cluster", "gar", "rest webservice"};
   private static volatile String[] stringTable = null;
   private static final WebLogicModuleType[] WLEnumValueTable;
   private static volatile ModuleType[] enumValueTable = null;
   private static final String[] WLModuleExtension = new String[]{"", ".xml", ".xml", ".xml", ".xml", "", ".xml", ".xml", "", "", "", "", "", "", "", ""};
   protected static String[] standardUris;
   protected static String[] wlsUris;
   public static final String MODULETYPE_EAR;
   public static final String MODULETYPE_WAR;
   public static final String MODULETYPE_EJB;
   public static final String MODULETYPE_RAR;
   public static final String MODULETYPE_CAR;
   public static final String MODULETYPE_UNKNOWN;
   public static final String MODULETYPE_JMS;
   public static final String MODULETYPE_JDBC;
   public static final String MODULETYPE_INTERCEPT;
   public static final String MODULETYPE_CONFIG;
   public static final String MODULETYPE_SUBMODULE;
   public static final String MODULETYPE_WLDF;
   public static final String MODULETYPE_WSEE;
   public static final String MODULETYPE_COMPOSITE;
   public static final String MODULETYPE_JAVA;
   public static final String MODULETYPE_SPRING;
   public static final String MODULETYPE_BPEL;
   public static final String MODULETYPE_SCA_EXTENSION;
   public static final String MODULETYPE_COHERENCE_CLUSTER;
   public static final String MODULETYPE_GAR;
   public static final String MODULETYPE_REST_WEBSERVICE;

   public static ModuleType getTypeFromString(String type) {
      if (type == null) {
         return UNKNOWN;
      } else {
         Object moduleType;
         if (type.equalsIgnoreCase(MODULETYPE_EAR)) {
            moduleType = ModuleType.EAR;
         } else if (type.equalsIgnoreCase(MODULETYPE_EJB)) {
            moduleType = ModuleType.EJB;
         } else if (type.equalsIgnoreCase(MODULETYPE_WAR)) {
            moduleType = ModuleType.WAR;
         } else if (type.equalsIgnoreCase(MODULETYPE_RAR)) {
            moduleType = ModuleType.RAR;
         } else if (type.equalsIgnoreCase(MODULETYPE_CAR)) {
            moduleType = ModuleType.CAR;
         } else if (type.equalsIgnoreCase(MODULETYPE_JMS)) {
            moduleType = JMS;
         } else if (type.equalsIgnoreCase(MODULETYPE_JDBC)) {
            moduleType = JDBC;
         } else if (type.equalsIgnoreCase(MODULETYPE_INTERCEPT)) {
            moduleType = INTERCEPT;
         } else if (type.equalsIgnoreCase(MODULETYPE_CONFIG)) {
            moduleType = CONFIG;
         } else if (type.equalsIgnoreCase(MODULETYPE_SUBMODULE)) {
            moduleType = SUBMODULE;
         } else if (type.equalsIgnoreCase(MODULETYPE_WLDF)) {
            moduleType = WLDF;
         } else if (type.equalsIgnoreCase(MODULETYPE_WSEE)) {
            moduleType = WSEE;
         } else if (type.equalsIgnoreCase(MODULETYPE_COMPOSITE)) {
            moduleType = SCA_COMPOSITE;
         } else if (type.equalsIgnoreCase(MODULETYPE_JAVA)) {
            moduleType = SCA_JAVA;
         } else if (type.equalsIgnoreCase(MODULETYPE_SPRING)) {
            moduleType = SCA_SPRING;
         } else if (type.equalsIgnoreCase(MODULETYPE_BPEL)) {
            moduleType = SCA_BPEL;
         } else if (type.equalsIgnoreCase(MODULETYPE_SCA_EXTENSION)) {
            moduleType = SCA_EXTENSION;
         } else if (type.equalsIgnoreCase(MODULETYPE_COHERENCE_CLUSTER)) {
            moduleType = COHERENCE_CLUSTER;
         } else if (type.equalsIgnoreCase(MODULETYPE_GAR)) {
            moduleType = GAR;
         } else if (type.equalsIgnoreCase(MODULETYPE_REST_WEBSERVICE)) {
            moduleType = REST_WEBSERVICE;
         } else {
            moduleType = UNKNOWN;
         }

         return (ModuleType)moduleType;
      }
   }

   protected WebLogicModuleType(int i) {
      super(i);
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }

   public String[] getStringTable() {
      if (stringTable == null) {
         String[] tmpStringTable = new String[numMods];
         String[] superStringTable = super.getStringTable();

         int i;
         for(i = 0; i < base; ++i) {
            tmpStringTable[i] = superStringTable[i];
         }

         for(i = 323; i < numMods; ++i) {
            tmpStringTable[i] = WLStringTable[i - 323];
         }

         stringTable = tmpStringTable;
      }

      return stringTable;
   }

   public ModuleType[] getEnumValueTable() {
      if (enumValueTable == null) {
         ModuleType[] tmpEnumValueTable = new ModuleType[numMods];
         ModuleType[] superEnumTable = super.getEnumValueTable();

         int i;
         for(i = 0; i < base; ++i) {
            tmpEnumValueTable[i] = superEnumTable[i];
         }

         for(i = 323; i < numMods; ++i) {
            tmpEnumValueTable[i] = WLEnumValueTable[i - 323];
         }

         enumValueTable = tmpEnumValueTable;
      }

      return enumValueTable;
   }

   public String getModuleExtension() {
      return WLModuleExtension[this.getValue() - this.getOffset()];
   }

   public static ModuleType getModuleType(int i) {
      if (i < base) {
         return ModuleType.getModuleType(i);
      } else {
         return i >= 323 ? WLEnumValueTable[i - 323] : null;
      }
   }

   protected int getOffset() {
      return 323;
   }

   private static int findNextSlot() {
      int i = 0;

      while(true) {
         try {
            ModuleType mt = ModuleType.getModuleType(i);
         } catch (ArrayIndexOutOfBoundsException var3) {
            return i;
         }

         ++i;
      }
   }

   public boolean equals(ModuleType mod) {
      return this.getValue() == mod.getValue();
   }

   public static int getModuleTypes() {
      return numMods;
   }

   public String toString() {
      return this.getStringTable()[this.getValue()];
   }

   public static ModuleType getFileModuleType(File path) {
      return WebLogicModuleTypeUtil.getFileModuleType(path);
   }

   static {
      int index = 323;
      UNKNOWN = new WebLogicModuleType(index++);
      JMS = new WebLogicModuleType(index++);
      JDBC = new WebLogicModuleType(index++);
      INTERCEPT = new WebLogicModuleType(index++);
      CONFIG = new WebLogicModuleType(index++);
      SUBMODULE = new WebLogicModuleType(index++);
      WLDF = new WebLogicModuleType(index++);
      WSEE = new WebLogicModuleType(index++);
      SCA_COMPOSITE = new WebLogicModuleType(index++);
      SCA_JAVA = new WebLogicModuleType(index++);
      SCA_SPRING = new WebLogicModuleType(index++);
      SCA_BPEL = new WebLogicModuleType(index++);
      SCA_EXTENSION = new WebLogicModuleType(index++);
      COHERENCE_CLUSTER = new WebLogicModuleType(index++);
      GAR = new WebLogicModuleType(index++);
      REST_WEBSERVICE = new WebLogicModuleType(index++);
      WLEnumValueTable = new WebLogicModuleType[]{UNKNOWN, JMS, JDBC, INTERCEPT, CONFIG, SUBMODULE, WLDF, WSEE, SCA_COMPOSITE, SCA_JAVA, SCA_SPRING, SCA_BPEL, SCA_EXTENSION, COHERENCE_CLUSTER, GAR, REST_WEBSERVICE};
      numMods = index;
      MODULETYPE_EAR = ModuleType.EAR.toString();
      MODULETYPE_WAR = ModuleType.WAR.toString();
      MODULETYPE_EJB = ModuleType.EJB.toString();
      MODULETYPE_RAR = ModuleType.RAR.toString();
      MODULETYPE_CAR = ModuleType.CAR.toString();
      MODULETYPE_UNKNOWN = UNKNOWN.toString();
      MODULETYPE_JMS = JMS.toString();
      MODULETYPE_JDBC = JDBC.toString();
      MODULETYPE_INTERCEPT = INTERCEPT.toString();
      MODULETYPE_CONFIG = CONFIG.toString();
      MODULETYPE_SUBMODULE = SUBMODULE.toString();
      MODULETYPE_WLDF = WLDF.toString();
      MODULETYPE_WSEE = WSEE.toString();
      MODULETYPE_COMPOSITE = SCA_COMPOSITE.toString();
      MODULETYPE_JAVA = SCA_JAVA.toString();
      MODULETYPE_SPRING = SCA_SPRING.toString();
      MODULETYPE_BPEL = SCA_BPEL.toString();
      MODULETYPE_SCA_EXTENSION = SCA_EXTENSION.toString();
      MODULETYPE_COHERENCE_CLUSTER = COHERENCE_CLUSTER.toString();
      MODULETYPE_GAR = GAR.toString();
      MODULETYPE_REST_WEBSERVICE = REST_WEBSERVICE.toString();
   }
}
