package javax.faces.validator;

class ValueReference {
   private Object base;
   private String property;

   public ValueReference(Object base, String property) {
      this.base = base;
      this.property = property;
   }

   public Class getBaseClass() {
      return this.base.getClass();
   }

   public Object getBase() {
      return this.base;
   }

   public String getProperty() {
      return this.property;
   }
}
