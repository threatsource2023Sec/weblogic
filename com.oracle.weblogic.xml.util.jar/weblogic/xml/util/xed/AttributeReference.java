package weblogic.xml.util.xed;

public class AttributeReference extends Variable {
   public AttributeReference() {
   }

   public AttributeReference(String name) {
      super("@" + name);
   }

   public void setName(String n) {
      super.setName("@" + n);
   }

   public Object evaluate(Context context) throws StreamEditorException {
      return context.getEventType() != 2 ? "" : super.evaluate(context);
   }

   public void assign(Object value, Context context) throws StreamEditorException {
      if (context.getEventType() == 2) {
         super.assign(value, context);
      }
   }

   public String toString() {
      return "$" + this.getName();
   }

   public boolean isAttributeRef() {
      return "@attributes".equals(this.getName());
   }
}
