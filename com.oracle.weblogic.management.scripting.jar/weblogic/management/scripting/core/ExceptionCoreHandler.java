package weblogic.management.scripting.core;

import java.io.Serializable;
import java.util.Date;
import javax.management.MBeanException;
import weblogic.management.scripting.ScriptException;
import weblogic.management.scripting.WLCoreScriptContext;
import weblogic.management.scripting.utils.ErrorInformation;
import weblogic.management.scripting.utils.WLSTInterpreter;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;

public class ExceptionCoreHandler implements Serializable {
   WLCoreScriptContext ctx = null;
   private transient WLSTMsgTextFormatter txtFmt;

   public ExceptionCoreHandler(WLCoreScriptContext ctx) {
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   public void handleException(ErrorInformation ei) throws ScriptException {
      String msg = ei.getMessage() == null ? "" : ei.getMessage();
      this.ctx.stackTrace = ei.getError();
      WLSTInterpreter interpreter = this.ctx.getWLSTInterpreter();
      Date dte = new Date();
      this.ctx.timeAtError = dte.toString();
      if (ei.getError() != null && ei.getError().getMessage() != null) {
         if (ei.getMessage() != null) {
            if (!ei.getMessage().equals(ei.getError().getMessage())) {
               msg = msg + " : " + ei.getError().getMessage();
            }
         } else {
            msg = ei.getError().getMessage();
         }
      }

      String s = this.getRealMessage(ei.getError());
      if (s != null && !s.equals(ei.getMessage()) && !s.equals(msg)) {
         msg = msg + " " + s;
      }

      if (this.ctx.debug || interpreter != null && interpreter.getScriptMode() && !this.ctx.hideDumpStack) {
         String cmdType = this.ctx.commandType;
         this.ctx.dumpStack(false);
         this.ctx.commandType = cmdType;
      }

      this.ctx.theErrorMessage = msg;
      if (ei.getError() == null) {
         if (this.ctx.redirecting) {
            this.ctx.println(this.txtFmt.getErrorOccurred(this.ctx.commandType, msg + this.ctx.commandType));
         }

         throw new ScriptException(this.txtFmt.getErrorOccurred(this.ctx.commandType, msg), this.ctx.commandType);
      } else {
         if (this.ctx.redirecting) {
            this.ctx.println(this.txtFmt.getErrorOccurredUseDumpStack(this.ctx.commandType, msg));
            this.ctx.println(ei.getError() + this.ctx.commandType);
         }

         throw new ScriptException(this.txtFmt.getErrorOccurredUseDumpStack(this.ctx.commandType, msg), ei.getError(), this.ctx.commandType);
      }
   }

   public void handleException(ErrorInformation ei, String cmdName) throws ScriptException {
      WLSTInterpreter interpreter = this.ctx.getWLSTInterpreter();
      String msg = ei.getMessage();
      if (ei.getError() != null && ei.getError().getMessage() != null && !ei.getMessage().equals(ei.getError().getMessage())) {
         msg = msg + ei.getError().getMessage();
      }

      this.ctx.theErrorMessage = msg;
      if (this.ctx.debug || interpreter != null && interpreter.getScriptMode() && !this.ctx.hideDumpStack) {
         this.ctx.dumpStack(false);
      }

      if (ei.getError() == null) {
         if (this.ctx.redirecting) {
            this.ctx.println(this.txtFmt.getErrorOccurred(cmdName, msg + cmdName));
         }

         throw new ScriptException(this.txtFmt.getErrorOccurred(cmdName, msg), cmdName);
      } else {
         if (this.ctx.redirecting) {
            this.ctx.println(this.txtFmt.getErrorOccurredUseDumpStack(cmdName, msg));
            this.ctx.println(ei.getError() + cmdName);
         }

         throw new ScriptException(this.txtFmt.getErrorOccurredUseDumpStack(cmdName, msg), ei.getError(), cmdName);
      }
   }

   private String getRealMessage(Throwable th) {
      if (th instanceof MBeanException) {
         MBeanException ex = (MBeanException)th;
         if (ex.getCause() != null && ex.getCause().getMessage() != null) {
            return ex.getCause().getMessage();
         }
      }

      return null;
   }
}
