package weblogic.management;

import java.util.Enumeration;
import java.util.Hashtable;
import weblogic.utils.NestedException;

public class ApplicationException extends NestedException {
   private static final long serialVersionUID = -2538796139370040809L;
   private Hashtable moduleErrors = new Hashtable();
   private Hashtable targetExs = null;
   private String appMessage = "None";

   public ApplicationException(String msg, Throwable nested) {
      super(msg, nested);
   }

   public ApplicationException(Exception e) {
      super(e);
   }

   public ApplicationException(String error) {
      super(error);
      this.appMessage = error;
   }

   public ApplicationException(Hashtable moduleErrors, String error) {
      super(error);
      this.moduleErrors = moduleErrors;
   }

   public String getApplicationMessage() {
      return this.appMessage;
   }

   public void addError(String failedModule, String moduleErrorMessage) {
      this.moduleErrors.put(failedModule, moduleErrorMessage);
   }

   public Hashtable getModuleErrors() {
      return this.moduleErrors;
   }

   public Exception getTargetException(String moduleName) {
      return this.targetExs == null ? null : (Exception)this.targetExs.get(moduleName);
   }

   public void setTargetException(String moduleName, Exception e) {
      if (this.targetExs == null) {
         this.targetExs = new Hashtable();
      }

      this.targetExs.put(moduleName, e);
   }

   public String toString() {
      StringBuffer me = new StringBuffer("");
      Hashtable ht = this.getModuleErrors();
      if (!ht.isEmpty()) {
         me.append("\n{");
         Enumeration e = ht.keys();

         while(e.hasMoreElements()) {
            Object key = e.nextElement();
            Object val = ht.get(key);
            me.append("\nModule Name: ");
            me.append(key);
            me.append(", Error: ");
            me.append(val);
            Exception ex = this.getTargetException((String)key);
            if (ex != null) {
               me.append("\n TargetException: " + ex);
            }
         }

         me.append("\n}");
      }

      return super.toString() + me;
   }
}
