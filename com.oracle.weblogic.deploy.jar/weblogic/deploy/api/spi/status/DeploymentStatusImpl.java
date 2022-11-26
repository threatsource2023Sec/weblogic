package weblogic.deploy.api.spi.status;

import java.util.HashSet;
import java.util.Iterator;
import javax.enterprise.deploy.shared.ActionType;
import javax.enterprise.deploy.shared.CommandType;
import javax.enterprise.deploy.shared.StateType;
import weblogic.management.ManagementException;
import weblogic.utils.ErrorCollectionException;

public class DeploymentStatusImpl implements WebLogicDeploymentStatus {
   private StateType state;
   private CommandType command;
   private ActionType action;
   private boolean completed;
   private boolean failed;
   private String message;
   private Throwable error;

   DeploymentStatusImpl() {
      this.state = StateType.RUNNING;
      this.command = null;
      this.action = null;
      this.completed = false;
      this.failed = false;
      this.message = null;
      this.error = null;
   }

   DeploymentStatusImpl(StateType st, CommandType ct, ActionType at, String message) {
      this.state = StateType.RUNNING;
      this.command = null;
      this.action = null;
      this.completed = false;
      this.failed = false;
      this.message = null;
      this.error = null;
      this.setState(st);
      this.setCommand(ct);
      this.setAction(at);
      this.setMessage(message);
   }

   DeploymentStatusImpl(StateType st, CommandType ct, ActionType at, String message, Throwable t) {
      this(st, ct, at, message);
      this.setError(t);
   }

   public StateType getState() {
      return this.state;
   }

   public CommandType getCommand() {
      return this.command;
   }

   public ActionType getAction() {
      return this.action;
   }

   public String getMessage() {
      return this.message;
   }

   public boolean isCompleted() {
      if (!this.completed) {
         this.completed = this.state == StateType.COMPLETED;
      }

      return this.completed;
   }

   public boolean isFailed() {
      if (!this.failed) {
         this.failed = this.state == StateType.FAILED;
      }

      return this.failed;
   }

   public boolean isRunning() {
      return !this.isCompleted() && !this.isFailed() && this.state != StateType.RELEASED;
   }

   public String toString() {
      StringBuffer buff = new StringBuffer(59);
      buff.append("State Type : " + this.state);
      buff.append("; Command Type : " + this.command);
      buff.append("; Action Type  : " + this.action);
      buff.append("; Completed  : " + this.completed);
      buff.append("; Failed : " + this.failed);
      buff.append("; Message : " + this.message);
      buff.append("; Exception : " + this.error);
      return buff.toString();
   }

   public void setState(StateType s) {
      this.state = s;
   }

   public void setCommand(CommandType c) {
      this.command = c;
   }

   public void setAction(ActionType a) {
      this.action = a;
   }

   public void setMessage(String s) {
      this.message = s;
   }

   public void setCompleted(boolean b) {
      this.completed = b;
   }

   public void setFailed(boolean b) {
      this.failed = b;
   }

   public void setError(Throwable t) {
      this.error = t;
   }

   public Iterator getRootException() {
      HashSet errs = new HashSet();
      if (this.error != null) {
         Throwable e = ManagementException.unWrapExceptions(this.error);
         if (e instanceof ErrorCollectionException) {
            return ((ErrorCollectionException)e).getErrors();
         } else {
            errs.add(e);
            return errs.iterator();
         }
      } else {
         return errs.iterator();
      }
   }
}
