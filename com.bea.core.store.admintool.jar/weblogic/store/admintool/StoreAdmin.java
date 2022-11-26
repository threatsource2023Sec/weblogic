package weblogic.store.admintool;

import java.lang.reflect.Method;

public class StoreAdmin implements Runnable {
   private static final String NAME = "storeadmin";
   private static final String CMD_SPLIT_REGEX = "\\s+";
   private static final String INTRO_MESSAGE = "Type \"help\" for available commands";
   private final Interpreter interp;
   private final StoreAdminIFImpl saif;
   private boolean javaMode = true;
   private boolean advancedMode = false;
   private String commandline;
   private boolean successStatus;

   public static void mainInternal(String[] args) {
      StoreAdmin sa = new StoreAdmin(args, true);
      sa.run();
      sa.exit();
   }

   public StoreAdmin(String[] args, boolean javaMode) {
      this.interp = new Interpreter("storeadmin", args);
      this.saif = new StoreAdminIFImpl();
      this.javaMode = javaMode;
      if (javaMode) {
         this.interp.message("Type \"help\" for available commands");
         this.interp.flush();
      }

   }

   public void run() {
      do {
         this.commandline = this.interp.getNextCommand();
         this.successStatus = this.execute(this.commandline);
      } while(!this.interp.checkForQuit());

   }

   public boolean execute(String input) {
      if (input == null) {
         return false;
      } else {
         String[] splitArgs = input.split("\\s+");
         this.interp.clearError();
         CommandDefs.Command command = CommandDefs.getCommand(this, splitArgs);
         if (command == null) {
            this.interp.flush();
            return false;
         } else {
            command.run();
            this.interp.flush();
            return !this.interp.checkForError();
         }
      }
   }

   Interpreter getInterpreter() {
      return this.interp;
   }

   StoreAdminIF getAdminIF() {
      return this.saif;
   }

   boolean getJavaMode() {
      return this.javaMode;
   }

   boolean getAdvancedMode() {
      return this.advancedMode;
   }

   void setAdvancedMode(boolean b) {
      this.advancedMode = b;
   }

   void exit() {
      System.exit(this.successStatus ? 0 : 1);
   }

   static {
      try {
         Class cls = Class.forName("weblogic.kernel.Kernel");
         Method meth = cls.getMethod("ensureInitialized", (Class[])null);
         Object var2 = meth.invoke((Object)null, (Object[])null);
      } catch (ClassNotFoundException var3) {
      } catch (Exception var4) {
      }

   }
}
