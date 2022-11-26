package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.XMLEventStream;

class VariableExp extends BooleanExp {
   private String name;

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public VariableExp() {
   }

   public VariableExp(String name) {
      this.name = name;
   }

   public boolean evaluate(Context context) {
      return context.lookup(this.name);
   }

   public BooleanExp copy() {
      return new VariableExp(this.name);
   }

   public BooleanExp replace(String name, BooleanExp exp) {
      return (BooleanExp)(this.name.equals(name) ? exp.copy() : new VariableExp(this.name));
   }

   public void read(XMLEventStream stream) throws SAXException {
      StartElementEvent event = stream.startElement();
      this.name = BooleanInterpreter.getArgument("name", event);
   }

   public String toString() {
      return this.name;
   }
}
