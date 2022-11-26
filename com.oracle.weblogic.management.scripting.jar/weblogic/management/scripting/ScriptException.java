package weblogic.management.scripting;

public class ScriptException extends Exception {
   String message;
   String commandName;

   public ScriptException(String message, String cmdName) {
      super(message);
      this.message = message;
      this.commandName = cmdName;
   }

   public ScriptException(String message, Throwable th, String cmdName) {
      super(message, th);
      this.message = message;
      this.commandName = cmdName;
   }

   public String getMessage() {
      return this.message;
   }

   public String getCommandName() {
      return this.commandName;
   }
}
