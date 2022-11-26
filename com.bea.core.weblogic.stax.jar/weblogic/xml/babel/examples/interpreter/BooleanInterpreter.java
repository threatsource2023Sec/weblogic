package weblogic.xml.babel.examples.interpreter;

import java.util.HashMap;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.xml.babel.parsers.BabelXMLEventStream;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.XMLEventStream;

public class BooleanInterpreter {
   private Map expressions = new HashMap();
   private Map contexts = new HashMap();
   private static final String docName = "BooleanInterpreter";
   static final boolean verbose = false;

   private void addExpression(Expression expression) {
      this.expressions.put(expression.getName(), expression);
   }

   public Context lookupContext(String name) {
      return (Context)this.contexts.get(name);
   }

   public Expression lookupExpression(String name) {
      return (Expression)this.expressions.get(name);
   }

   public void addContext(Context context) {
      this.contexts.put(context.getName(), context);
   }

   public static String getArgument(String name, StartElementEvent event) {
      Attributes attributes = event.getAttributes();
      return attributes.getValue(name);
   }

   public static String getFirstArgument(StartElementEvent event) {
      return getArgument(event, 0);
   }

   public static String getSecondArgument(StartElementEvent event) {
      return getArgument(event, 1);
   }

   public static String getArgument(StartElementEvent event, int i) {
      Attributes attributes = event.getAttributes();
      return attributes.getValue(i);
   }

   public void readState(XMLEventStream stream) throws SAXException {
      StartElementEvent startElementEvent;
      do {
         startElementEvent = stream.startElement("expression");
         Expression expression = new Expression();
         expression.setName(getArgument("id", startElementEvent));
         expression.read(stream);
         this.addExpression(expression);
         System.out.println("Read[" + expression.getName() + "]=" + expression);
      } while(stream.nextElementIs("expression"));

      do {
         startElementEvent = stream.getSubStream().startElement();
         Context context = new Context(getArgument("id", startElementEvent));
         context.read(stream);
         this.addContext(context);
         System.out.println("Variables bound in [" + context.getName() + "] " + context);
      } while(stream.nextElementIs("context"));

   }

   public void evaluate(XMLEventStream stream) throws SAXException {
      StartElementEvent startElementEvent = stream.startElement("interpret");
      String contextName = getArgument("context", startElementEvent);
      String expressionName = getArgument("expression", startElementEvent);
      Context context = this.lookupContext(contextName);
      Expression expression = this.lookupExpression(expressionName);
      boolean result = expression.evaluate(context);
      System.out.println("Interpreted expression:" + expressionName + " in context:" + contextName + " to " + result);
   }

   public void replace(XMLEventStream stream) throws SAXException {
      StartElementEvent startElementEvent = stream.startElement("replace");
      String varName = getArgument("var", startElementEvent);
      String newExpressionName = getArgument("with_expression", startElementEvent);
      String oldExpressionName = getArgument("in_expression", startElementEvent);
      System.out.println("Replacing variable [" + varName + "]");
      Expression oldExpression = this.lookupExpression(oldExpressionName);
      System.out.println("\tin old expression:[" + oldExpression + "]");
      Expression newExpression = this.lookupExpression(newExpressionName);
      System.out.println("\twith new:[" + newExpression + "]");
      oldExpression.replace(varName, newExpression.getExpression());
      System.out.println("The new expression is:[" + oldExpression + "]");
   }

   public void readEvaluations(XMLEventStream stream) throws SAXException {
      stream.startElement("evaluate");

      do {
         StartElementEvent event = stream.getSubStream().startElement();
         if (event.getName().equals("replace")) {
            this.replace(stream);
         } else {
            this.evaluate(stream);
         }
      } while(stream.nextElementIs("interpret") || stream.nextElementIs("replace"));

   }

   public void read(XMLEventStream stream) throws SAXException {
      StartElementEvent startElementEvent = stream.startElement();
      if (!startElementEvent.getName().equals("BooleanInterpreter")) {
         throw new SAXException("This intepreter can only understand BooleanInterpreter XML docs");
      } else {
         this.readState(stream.getSubStream());
         this.readEvaluations(stream.getSubStream());
      }
   }

   public static void main(String[] args) throws Exception {
      BooleanInterpreter bi = new BooleanInterpreter();
      BabelXMLEventStream xes = new BabelXMLEventStream();
      xes.startDocument(new InputSource(args[0]));
      bi.read(xes);
   }
}
