package weblogic.management.workflow.internal;

import java.io.Serializable;
import weblogic.management.workflow.InvalidParameterWorkflowException;
import weblogic.management.workflow.WorkflowException;
import weblogic.management.workflow.command.CommandInterface;

public class BeanCommandProvider implements CommandProvider, Serializable {
   private static final long serialVersionUID = 1L;
   private volatile CommandInterface instance;
   private final Class clazz;

   public BeanCommandProvider(Class clazz) throws InvalidParameterWorkflowException {
      if (clazz == null) {
         throw new InvalidParameterWorkflowException("CommandInterface must be defined");
      } else {
         try {
            clazz.getConstructor();
         } catch (NoSuchMethodException var3) {
            throw new InvalidParameterWorkflowException("CommandInterface class must have public empty constructor");
         }

         this.clazz = clazz;
      }
   }

   public CommandInterface getCommand() {
      CommandInterface result = this.instance;
      if (result == null) {
         synchronized(this) {
            if (this.instance == null) {
               try {
                  this.instance = (CommandInterface)this.clazz.getConstructor().newInstance();
               } catch (Exception var5) {
                  throw new WorkflowException("Can not construct command", var5);
               }
            }

            result = this.instance;
         }
      }

      return result;
   }

   public Class getCommandClass() {
      return this.clazz;
   }

   public String toString() {
      return "BeanCommandProvider(" + (this.clazz == null ? "null" : this.clazz.getName()) + ")";
   }
}
