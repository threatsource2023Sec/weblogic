package weblogic.xml.util.xed;

import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.XMLInputStream;
import weblogic.xml.stream.XMLStreamException;
import weblogic.xml.xpath.XPathStreamObserver;

public class Controller implements XPathStreamObserver {
   private Context context;
   private Command command;
   private static final boolean debug = false;

   public Controller(Context c, Command co) {
      this.context = c;
      this.command = co;
   }

   public void observe(XMLEvent event) {
      this.context.set(event);
      this.evaluate();
   }

   public void observeAttribute(StartElement event, Attribute attribute) {
      this.context.set(event, attribute);
      this.evaluate();
   }

   public void observeNamespace(StartElement event, Attribute attribute) {
      this.context.set(event, attribute);
      this.evaluate();
   }

   public XMLInputStream getResult() throws XMLStreamException {
      return this.command.getResult();
   }

   private void evaluate() {
      try {
         this.command.evaluate(this.context);
      } catch (StreamEditorException var2) {
         var2.printStackTrace();
         System.out.println("Error: " + var2);
      }

   }
}
