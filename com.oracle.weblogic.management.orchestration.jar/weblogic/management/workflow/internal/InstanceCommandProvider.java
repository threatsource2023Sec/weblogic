package weblogic.management.workflow.internal;

import weblogic.management.workflow.command.CommandInterface;

public class InstanceCommandProvider implements CommandProvider {
   private static final long serialVersionUID = 1L;
   private final CommandInterface command;

   public InstanceCommandProvider(CommandInterface command) {
      this.command = command;
   }

   public CommandInterface getCommand() {
      return this.command;
   }

   public Class getCommandClass() {
      return this.command.getClass();
   }

   public String toString() {
      return "InstanceCommandProvider(" + (this.command == null ? "null" : this.command.getClass().getName()) + ")";
   }
}
