package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.XMLEventStream;

class NotExp extends BooleanExp {
   private BooleanExp op;

   public NotExp() {
   }

   public NotExp(BooleanExp op) {
      this.op = op;
   }

   public boolean evaluate(Context context) {
      return !this.op.evaluate(context);
   }

   public BooleanExp copy() {
      return new NotExp(this.op.copy());
   }

   public BooleanExp replace(String name, BooleanExp exp) {
      return new NotExp(this.op.replace(name, exp));
   }

   public void read(XMLEventStream stream) throws SAXException {
      stream.startElement("operator");
      StartElementEvent event = stream.getSubStream().startElement();
      this.op = ExpressionFactory.createExpression(event);
      this.op.read(stream);
   }

   public String toString() {
      return "NOT (" + this.op + ")";
   }
}
