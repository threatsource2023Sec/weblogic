package weblogic.xml.xpath;

import java.io.PrintWriter;
import java.io.StringWriter;
import weblogic.xml.stream.Attribute;
import weblogic.xml.stream.AttributeIterator;
import weblogic.xml.stream.StartElement;
import weblogic.xml.stream.XMLEvent;
import weblogic.xml.stream.events.CharacterDataEvent;
import weblogic.xml.stream.events.ProcessingInstructionEvent;

class MainObserver implements XPathStreamObserver {
   private static final int TRUNCATE = 55;
   private PrintWriter out;
   int matchCount = 0;

   public MainObserver(PrintWriter out) {
      this.out = out;
   }

   public void observe(XMLEvent event) {
      ++this.matchCount;
      switch (event.getType()) {
         case 2:
            this.out.print("[START-ELEMENT]    ");
            StringWriter sw = new StringWriter();
            sw.write("<");
            sw.write(event.getName() + "");
            AttributeIterator atts = ((StartElement)event).getAttributes();

            while(atts.hasNext()) {
               Attribute att = atts.next();
               sw.write(" ");
               sw.write(att.getName() + "");
               sw.write("='");
               sw.write(att.getValue());
               sw.write("'");
            }

            sw.write(">");
            this.out.println(truncate(sw.toString()));
            break;
         case 4:
            this.out.print("[END-ELEMENT]      ");
            this.out.print("<");
            this.out.print(event.getName() + "");
            this.out.println("/>");
            break;
         case 8:
            this.out.print("[PROC-INSTRUCTION] ");
            this.out.println(truncate(((ProcessingInstructionEvent)event).getData()));
            break;
         case 16:
            this.out.print("[CDATA/TEXT]       ");
            this.out.println(truncate(((CharacterDataEvent)event).getContent()));
            break;
         case 32:
            this.out.print("[COMMENT]          ");
            this.out.println(truncate(((CharacterDataEvent)event).getContent()));
            break;
         case 64:
            this.out.println("[SPACE]");
            break;
         case 256:
            this.out.println("[START-DOCUMENT]");
            break;
         case 512:
            this.out.println("[END-DOCUMENT]");
            break;
         default:
            this.out.println("[?OTHER?] " + event.getClass() + " " + event);
      }

   }

   public void observeAttribute(StartElement event, Attribute att) {
      ++this.matchCount;
      this.out.print("[ATTRIBUTE]        ");
      StringWriter sw = new StringWriter();
      sw.write(att.getName() + "");
      sw.write("='");
      String val = att.getValue();
      if (val != null) {
         sw.write(val);
      }

      sw.write("'");
      this.out.println(truncate(sw.toString()));
   }

   public void observeNamespace(StartElement event, Attribute att) {
      ++this.matchCount;
      this.out.print("[NAMESPACE]        ");
      StringWriter sw = new StringWriter();
      sw.write(att.getName() + "");
      sw.write("='");
      String val = att.getValue();
      if (val != null) {
         sw.write(val);
      }

      sw.write("'");
      this.out.println(truncate(sw.toString()));
   }

   private static final String truncate(String s) {
      s = s.replace('\n', ' ');
      if (s.length() > 52) {
         s = s.substring(0, 52) + "...";
      }

      return s;
   }
}
