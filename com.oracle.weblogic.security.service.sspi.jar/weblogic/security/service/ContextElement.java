package weblogic.security.service;

public class ContextElement {
   private String name;
   private Object value = null;

   public ContextElement(String name) {
      this.name = name;
   }

   public ContextElement(String name, Object value) {
      this.name = name;
      this.value = value;
   }

   public String getName() {
      return this.name;
   }

   public Object getValue() {
      return this.resetContextElementMessageStreamValue(this.value);
   }

   public void setValue(Object value) {
      this.value = value;
   }

   private Object resetContextElementMessageStreamValue(Object value) {
      if (value != null && value instanceof ContextElementMessageStream) {
         ContextElementMessageStream stream = (ContextElementMessageStream)value;
         stream.resetToStart();
      }

      return value;
   }
}
