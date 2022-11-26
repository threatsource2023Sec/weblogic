package weblogic.xml.xmlnode;

import java.io.PrintWriter;
import weblogic.xml.stream.XMLOutputStream;
import weblogic.xml.stream.XMLStreamException;

public class XMLTextNode extends XMLNode {
   private String text;
   private boolean isComment = false;

   public XMLTextNode() {
   }

   public XMLTextNode(String text) {
      this.setText(text);
   }

   public XMLNode setText(String text) {
      this.text = text;
      if (text != null && text.startsWith("<!--") && text.endsWith("-->")) {
         this.isComment = true;
      }

      return this;
   }

   public boolean isComment() {
      return this.isComment;
   }

   public String getText() {
      return this.text;
   }

   public boolean isTextNode() {
      return true;
   }

   public void write(PrintWriter out) {
      if (this.isSpace) {
         out.write("[SPACE[");
      } else {
         out.write("[TEXT[");
      }

      out.write(this.getText());
      if (this.isSpace) {
         out.println("]SPACE]");
      } else {
         out.println("]TEXT]");
      }

   }

   public void write(XMLOutputStream xos) throws XMLStreamException {
      this.writeText(xos);
   }

   protected void writeText(PrintWriter out) {
      if (this.getText() != null) {
         out.write(this.getText());
      }

   }
}
