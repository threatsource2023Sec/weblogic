package weblogic.management.patching.commands;

public class CommandException extends Exception {
   public CommandException() {
   }

   public CommandException(String msg) {
      super(msg);
   }

   public CommandException(Throwable cause) {
      super(cause);
   }

   public CommandException(String msg, Throwable cause) {
      super(msg, cause);
   }
}
