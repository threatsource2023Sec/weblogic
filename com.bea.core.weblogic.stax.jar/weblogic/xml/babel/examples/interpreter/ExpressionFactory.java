package weblogic.xml.babel.examples.interpreter;

import org.xml.sax.SAXException;
import weblogicx.xml.stream.StartElementEvent;

class ExpressionFactory {
   public static BooleanExp createExpression(String type, String kind) throws SAXException {
      if (type.equals("operator")) {
         if (kind.equals("and")) {
            return new AndExp();
         } else if (kind.equals("or")) {
            return new OrExp();
         } else if (kind.equals("not")) {
            return new NotExp();
         } else {
            throw new SAXException("Unknown Expression Type:" + type);
         }
      } else if (type.equals("boolean")) {
         return new Constant(kind);
      } else if (type.equals("var")) {
         return new VariableExp(kind);
      } else {
         throw new SAXException("Unknown Expression Type:" + type);
      }
   }

   public static BooleanExp createExpression(StartElementEvent event) throws SAXException {
      return createExpression(event.getName(), BooleanInterpreter.getFirstArgument(event));
   }
}
