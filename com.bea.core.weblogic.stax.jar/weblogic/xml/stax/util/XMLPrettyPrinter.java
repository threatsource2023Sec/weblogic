package weblogic.xml.stax.util;

import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Arrays;
import javax.xml.stream.XMLStreamException;
import weblogic.xml.stax.XMLStreamReaderBase;
import weblogic.xml.stax.XMLWriterBase;

public class XMLPrettyPrinter extends XMLWriterBase {
   private static final String NEWLINE = System.getProperty("line.separator");
   private static final int DEFAULT_INDENT_SIZE = 4;
   private int indentLevel;
   private boolean lastWroteEndTag = false;
   private char[] indentText;

   public XMLPrettyPrinter(Writer writer, int indentSize) {
      super(writer);
      this.setIndentSize(indentSize);
   }

   public XMLPrettyPrinter(Writer writer) {
      super(writer);
      this.setIndentSize(4);
   }

   public void writeCharacters(String text) throws XMLStreamException {
      this.lastWroteEndTag = false;
      this.writeCharacters(text.toCharArray(), 0, text.length());
   }

   public void writeCharacters(char[] text, int start, int len) throws XMLStreamException {
      this.lastWroteEndTag = false;

      for(int i = 0; i < len; ++i) {
         char c = text[i + start];
         if (c != '\n' && c != '\r' && c != '\t') {
            super.writeCharacters(text, start, len);
            break;
         }
      }

   }

   protected void closeStartTag() throws XMLStreamException {
      this.flushNamespace();
      if (this.isEmptyElement()) {
         this.write("/>");
         this.lastWroteEndTag = true;
         --this.indentLevel;
      } else {
         this.write(">");
      }

   }

   protected void openStartTag() throws XMLStreamException {
      this.write(NEWLINE);
      this.writeIndent();
      this.write("<");
      ++this.indentLevel;
      this.lastWroteEndTag = false;
   }

   protected void openEndTag() throws XMLStreamException {
      --this.indentLevel;
      this.indentIfNeeded();
      this.write("</");
      this.lastWroteEndTag = true;
   }

   protected void indentIfNeeded() throws XMLStreamException {
      if (this.lastWroteEndTag) {
         this.write(NEWLINE);
         this.writeIndent();
      }

   }

   protected void writeIndent() throws XMLStreamException {
      for(int i = 0; i < this.indentLevel; ++i) {
         this.write(this.indentText);
      }

   }

   public void writeComment(String data) throws XMLStreamException {
      this.closeStartElement();
      this.indentIfNeeded();
      this.write("<!--");
      if (data != null) {
         this.write(data);
      }

      this.write("-->");
   }

   public void writeProcessingInstruction(String target, String text) throws XMLStreamException {
      this.closeStartElement();
      this.indentIfNeeded();
      this.write("<?");
      if (target != null) {
         this.write(target);
      }

      if (text != null) {
         this.write(text);
      }

      this.write("?>");
   }

   public void writeCData(String data) throws XMLStreamException {
      this.closeStartElement();
      this.indentIfNeeded();
      this.write("<![CDATA[");
      if (data != null) {
         this.write(data);
      }

      this.write("]]>");
   }

   public void setIndentSize(int indentSize) {
      this.indentText = new char[indentSize];
      Arrays.fill(this.indentText, ' ');
   }

   public int getIndentSize() {
      return this.indentText.length;
   }

   public static void main(String[] args) throws Exception {
      Writer w = new OutputStreamWriter(System.out);
      XMLPrettyPrinter b = new XMLPrettyPrinter(w);
      XMLStreamReaderBase i = new XMLStreamReaderBase(new FileReader(args[0]));

      while(i.hasNext()) {
         b.write(i);
         i.next();
      }

      b.flush();
   }
}
