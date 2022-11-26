package weblogic.deploy.api.shared;

import java.util.HashSet;
import java.util.Set;
import javax.enterprise.deploy.shared.CommandType;

public class WebLogicCommandType extends CommandType {
   private int value;
   private static int base = findNextSlot();
   private static int numCmds;
   private static final int WLS_OFFSET = 323;
   public static final WebLogicCommandType DEPLOY;
   public static final WebLogicCommandType UPDATE;
   /** @deprecated */
   @Deprecated
   public static final WebLogicCommandType DEACTIVATE;
   /** @deprecated */
   @Deprecated
   public static final WebLogicCommandType REMOVE;
   /** @deprecated */
   @Deprecated
   public static final WebLogicCommandType ACTIVATE;
   private static final Set moduleCommandsSet;
   private static final String[] stringTable = new String[]{"deploy", "update", "deactivate", "remove", "activate"};
   private static volatile String[] strTable = null;
   private static final WebLogicCommandType[] enumTable;
   private static volatile CommandType[] enumValueTable = null;

   protected WebLogicCommandType(int i) {
      super(i);
      this.value = i;
   }

   public int getValue() {
      return this.value;
   }

   public String[] getStringTable() {
      if (strTable == null) {
         String[] tmpStrTable = new String[numCmds];
         String[] superStringTable = super.getStringTable();

         int i;
         for(i = 0; i < base; ++i) {
            tmpStrTable[i] = superStringTable[i];
         }

         for(i = 323; i < numCmds; ++i) {
            tmpStrTable[i] = stringTable[i - 323];
         }

         strTable = tmpStrTable;
      }

      return strTable;
   }

   public CommandType[] getEnumValueTable() {
      if (enumValueTable == null) {
         CommandType[] tmpEnumValueTable = new CommandType[numCmds];
         CommandType[] superEnumTable = super.getEnumValueTable();

         int i;
         for(i = 0; i < base; ++i) {
            tmpEnumValueTable[i] = superEnumTable[i];
         }

         for(i = 323; i < numCmds; ++i) {
            tmpEnumValueTable[i] = enumTable[i - 323];
         }

         enumValueTable = tmpEnumValueTable;
      }

      return enumValueTable;
   }

   public static CommandType getCommandType(int i) {
      return (CommandType)(i < base ? CommandType.getCommandType(i) : enumTable[i - 323]);
   }

   public static boolean supportsModuleTargeting(CommandType cmd) {
      return moduleCommandsSet.contains(cmd);
   }

   public String toString() {
      return this.getStringTable()[this.value];
   }

   protected int getOffset() {
      return 323;
   }

   private static int findNextSlot() {
      int i = 0;

      while(true) {
         try {
            CommandType ct = CommandType.getCommandType(i);
         } catch (ArrayIndexOutOfBoundsException var3) {
            return i;
         }

         ++i;
      }
   }

   static {
      int index = 323;
      DEPLOY = new WebLogicCommandType(index++);
      UPDATE = new WebLogicCommandType(index++);
      DEACTIVATE = new WebLogicCommandType(index++);
      REMOVE = new WebLogicCommandType(index++);
      ACTIVATE = new WebLogicCommandType(index++);
      numCmds = index;
      enumTable = new WebLogicCommandType[]{DEPLOY, UPDATE};
      moduleCommandsSet = new HashSet();
      moduleCommandsSet.add(START);
      moduleCommandsSet.add(STOP);
      moduleCommandsSet.add(UNDEPLOY);
      moduleCommandsSet.add(REDEPLOY);
      moduleCommandsSet.add(DEPLOY);
      moduleCommandsSet.add(UPDATE);
      moduleCommandsSet.add(DEACTIVATE);
      moduleCommandsSet.add(REMOVE);
      moduleCommandsSet.add(ACTIVATE);
   }
}
