package weblogic.xml.babel.examples.interpreter;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.xml.sax.SAXException;
import weblogicx.xml.stream.StartElementEvent;
import weblogicx.xml.stream.XMLEventStream;

public class Context {
   private Map values = new HashMap();
   private String name;

   Context(String name) {
      this.name = name;
   }

   public String getName() {
      return this.name;
   }

   public boolean lookup(String name) {
      Boolean b = (Boolean)this.values.get(name);
      return b;
   }

   public void assign(VariableExp variable, boolean value) {
      this.values.put(variable.getName(), new Boolean(value));
   }

   private boolean stringToBool(String type) throws SAXException {
      if (type.equals("false")) {
         return false;
      } else if (type.equals("true")) {
         return true;
      } else {
         throw new SAXException("Unknown boolean type:" + type);
      }
   }

   public void read(XMLEventStream stream) throws SAXException {
      stream.startElement();

      while(stream.nextElementIs("assign")) {
         StartElementEvent event = stream.startElement();
         this.assign(new VariableExp(BooleanInterpreter.getArgument("var", event)), this.stringToBool(BooleanInterpreter.getArgument("value", event)));
      }

   }

   public String toString() {
      Iterator i = this.values.entrySet().iterator();

      String text;
      for(text = ""; i.hasNext(); text = text + i.next().toString() + " ") {
      }

      return text;
   }
}
