package weblogic.management.rest.lib.utils;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.admin.rest.model.ResponseBody;
import org.glassfish.admin.rest.utils.PropertyException;
import org.glassfish.admin.rest.utils.PropertyExceptions;

public class ConfigurationTransaction {
   public static Result doTransaction(TransactionHelper helper, JSONObject model, TransactionContents contents, boolean transactional, boolean saveChanges) throws Exception {
      return doTransaction(helper, model, contents, transactional, saveChanges, false);
   }

   public static Result doTransaction(TransactionHelper helper, JSONObject model, TransactionContents contents, boolean transactional, boolean saveChanges, boolean warnOnPropertyExceptions) throws Exception {
      if (!transactional) {
         return doWork(helper.getRequest(), model, contents, warnOnPropertyExceptions);
      } else {
         Result result;
         if (helper.isConfigLocked() && helper.getConfigurationManager().isEditor() && !helper.getConfigurationManager().isCurrentEditorExclusive()) {
            result = doWork(helper.getRequest(), model, contents, warnOnPropertyExceptions);
            if (saveChanges && result.succeeded()) {
               try {
                  helper.getConfigurationManager().save();
               } catch (Throwable var14) {
                  return new Result(ConfigurationTransaction.Result.Status.TransactionContentsException, var14, warnOnPropertyExceptions);
               }
            }

            return result;
         } else {
            try {
               helper.beginConfigurationTransaction();
            } catch (Throwable var15) {
               return new Result(ConfigurationTransaction.Result.Status.BeginTransactionException, var15, warnOnPropertyExceptions);
            }

            Result var8;
            try {
               result = doWork(helper.getRequest(), model, contents, warnOnPropertyExceptions);
               Result var7;
               if (!result.succeeded()) {
                  var7 = result;
                  return var7;
               }

               try {
                  helper.commitConfigurationTransaction();
                  var7 = new Result(ConfigurationTransaction.Result.Status.Succeeded, result.getException(), warnOnPropertyExceptions);
                  return var7;
               } catch (Throwable var16) {
                  var8 = new Result(ConfigurationTransaction.Result.Status.CommitTransactionException, var16, warnOnPropertyExceptions);
               }
            } finally {
               helper.stopConfigurationTransaction();
            }

            return var8;
         }
      }
   }

   public static Result doWork(HttpServletRequest request, JSONObject model, TransactionContents contents, boolean warnOnPropertyExceptions) throws Exception {
      Result.Status status;
      try {
         if (!contents.doWork(request, model)) {
            return new Result(ConfigurationTransaction.Result.Status.Failed, (Throwable)null, warnOnPropertyExceptions);
         }
      } catch (PropertyExceptions var6) {
         status = warnOnPropertyExceptions ? ConfigurationTransaction.Result.Status.Succeeded : ConfigurationTransaction.Result.Status.TransactionContentsException;
         return new Result(status, var6, warnOnPropertyExceptions);
      } catch (PropertyException var7) {
         status = warnOnPropertyExceptions ? ConfigurationTransaction.Result.Status.Succeeded : ConfigurationTransaction.Result.Status.TransactionContentsException;
         return new Result(status, var7, warnOnPropertyExceptions);
      } catch (Throwable var8) {
         return new Result(ConfigurationTransaction.Result.Status.Failed, var8, warnOnPropertyExceptions);
      }

      return new Result(ConfigurationTransaction.Result.Status.Succeeded, (Throwable)null, warnOnPropertyExceptions);
   }

   public interface TransactionContents {
      boolean doWork(HttpServletRequest var1, JSONObject var2) throws Exception;
   }

   public static class Result {
      private Status status;
      private Throwable exception;
      private boolean warnOnPropertyExceptions;

      private Result(Status status, Throwable exception, boolean warnOnPropertyExceptions) {
         this.status = status;
         this.exception = exception;
         this.warnOnPropertyExceptions = warnOnPropertyExceptions;
      }

      public Status getStatus() {
         return this.status;
      }

      public Throwable getException() {
         return this.exception;
      }

      public boolean succeeded() {
         return this.getStatus() == ConfigurationTransaction.Result.Status.Succeeded;
      }

      public ResponseBody report(ResponseBody rb) {
         Throwable t = this.getException();
         if (t instanceof WebApplicationException) {
            throw (WebApplicationException)t;
         } else {
            if (t instanceof PropertyExceptions) {
               Iterator var3 = ((PropertyExceptions)t).getPropertyExceptions().iterator();

               while(var3.hasNext()) {
                  PropertyException pe = (PropertyException)var3.next();
                  this.reportPropertyException(rb, pe);
               }
            } else if (t instanceof PropertyException) {
               this.reportPropertyException(rb, (PropertyException)t);
            } else if (t != null) {
               rb.addFailure(t);
            }

            return rb;
         }
      }

      private void reportPropertyException(ResponseBody rb, PropertyException pe) {
         String field = pe.getField();
         String message = pe.getLocalizedMessage();
         if (this.warnOnPropertyExceptions) {
            rb.addWarning(field, message);
         } else {
            rb.addFailure(field, message);
         }

      }

      // $FF: synthetic method
      Result(Status x0, Throwable x1, boolean x2, Object x3) {
         this(x0, x1, x2);
      }

      private static enum Status {
         Succeeded,
         Failed,
         BeginTransactionException,
         TransactionContentsException,
         CommitTransactionException;
      }
   }
}
