package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.XMLEventStream;

class Expression {
   private String name;
   private BooleanExp rootExpression;
   private static final String nodeName = "expression";

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public BooleanExp getExpression() {
      return this.rootExpression;
   }

   public void setExpression(BooleanExp exp) {
      this.rootExpression = exp;
   }

   public void read(XMLEventStream stream) throws SAXException {
      StartElementEvent event = stream.getSubStream().startElement();
      if (event.getName().equals("operator")) {
         this.rootExpression = ExpressionFactory.createExpression(event);
         this.rootExpression.read(stream);
      } else {
         throw new SAXException("Invalid Expression Syntax");
      }
   }

   public boolean evaluate(Context context) {
      return this.rootExpression.evaluate(context);
   }

   public void replace(String name, BooleanExp exp) {
      this.rootExpression = this.rootExpression.replace(name, exp);
   }

   public String toString() {
      return this.rootExpression.toString();
   }
}
