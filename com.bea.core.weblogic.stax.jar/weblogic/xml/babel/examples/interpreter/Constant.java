package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.XMLEventStream;

class Constant extends BooleanExp {
   private boolean value;

   public Constant() {
   }

   public Constant(String type) throws SAXException {
      this.value = this.stringToBool(type);
   }

   public Constant(boolean value) {
      this.value = value;
   }

   public boolean evaluate(Context context) {
      return this.value;
   }

   public BooleanExp copy() {
      return new Constant(true);
   }

   public BooleanExp replace(String name, BooleanExp exp) {
      return this;
   }

   private boolean stringToBool(String type) throws SAXException {
      if (type.equals("false")) {
         return false;
      } else if (type.equals("true")) {
         return true;
      } else {
         throw new SAXException("Constants must be true or false");
      }
   }

   public void read(XMLEventStream stream) throws SAXException {
      StartElementEvent event = stream.startElement();
      String type = BooleanInterpreter.getArgument("value", event);
      this.value = this.stringToBool(type);
   }

   public String toString() {
      return (new Boolean(this.value)).toString();
   }
}
