package weblogic.xml.util.xed;

public class Variable extends Command {
   private String name;

   public Variable() {
   }

   public Variable(String name) {
      this.name = name;
   }

   public void setName(String n) {
      this.name = n;
   }

   public String getName() {
      return this.name;
   }

   public Object evaluate(Context context) throws StreamEditorException {
      return context.lookup(this);
   }

   public void assign(Object value, Context context) throws StreamEditorException {
      context.assign(this, value);
   }

   public String toString() {
      return "$" + this.getName();
   }

   public boolean isAttributeRef() {
      return false;
   }
}
