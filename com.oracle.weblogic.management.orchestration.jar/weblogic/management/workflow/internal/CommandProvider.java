package weblogic.management.workflow.internal;

import java.io.Serializable;
import weblogic.management.workflow.command.CommandInterface;

public interface CommandProvider extends Serializable {
   CommandInterface getCommand();

   Class getCommandClass();
}
