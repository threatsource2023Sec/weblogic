package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.XMLEventStream;

class OrExp extends BooleanExp {
   private BooleanExp op1;
   private BooleanExp op2;

   public OrExp() {
   }

   public OrExp(BooleanExp op1, BooleanExp op2) {
      this.op1 = op1;
      this.op2 = op2;
   }

   public boolean evaluate(Context context) {
      return this.op1.evaluate(context) || this.op2.evaluate(context);
   }

   public BooleanExp copy() {
      return new OrExp(this.op1.copy(), this.op2.copy());
   }

   public BooleanExp replace(String name, BooleanExp exp) {
      return new OrExp(this.op1.replace(name, exp), this.op2.replace(name, exp));
   }

   public void read(XMLEventStream stream) throws SAXException {
      stream.startElement("operator");
      StartElementEvent event = stream.getSubStream().startElement();
      this.op1 = ExpressionFactory.createExpression(event);
      this.op1.read(stream);
      event = stream.getSubStream().startElement();
      this.op2 = ExpressionFactory.createExpression(event);
      this.op2.read(stream);
   }

   public String toString() {
      return "OR (" + this.op1 + "," + this.op2 + ")";
   }
}
