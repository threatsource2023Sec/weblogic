package weblogic.application.library;

import weblogic.management.runtime.RuntimeMBean;

public class LibraryReferencer {
   private RuntimeMBean runtime;
   private final String name;
   private final String error;

   public LibraryReferencer(String name, RuntimeMBean runtime, String error) {
      this.name = name;
      this.runtime = runtime;
      this.error = error;
   }

   public String getReferencerName() {
      return this.name;
   }

   public RuntimeMBean getReferencerRuntime() {
      return this.runtime;
   }

   public void setReferencerRuntime(RuntimeMBean runtime) {
      this.runtime = runtime;
   }

   public String getUnresolvedError() {
      return this.error;
   }
}
