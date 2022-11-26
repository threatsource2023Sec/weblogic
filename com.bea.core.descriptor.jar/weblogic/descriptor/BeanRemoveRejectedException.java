package weblogic.descriptor;

import java.util.List;

public class BeanRemoveRejectedException extends IllegalArgumentException {
   private final transient List references;
   private final transient DescriptorBean bean;
   private String message;

   public BeanRemoveRejectedException(DescriptorBean db, List refs) {
      this.bean = db;
      this.references = refs;
      this.message = "Bean " + this.bean + " references " + this.references;
   }

   public DescriptorBean getBean() {
      return this.bean;
   }

   public List getReferences() {
      return this.references;
   }

   public String getMessage() {
      return this.message;
   }
}
