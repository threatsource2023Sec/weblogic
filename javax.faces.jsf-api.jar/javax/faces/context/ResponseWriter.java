package javax.faces.context;

import java.io.IOException;
import java.io.Writer;
import javax.faces.component.UIComponent;

public abstract class ResponseWriter extends Writer {
   public abstract String getContentType();

   public abstract String getCharacterEncoding();

   public abstract void flush() throws IOException;

   public abstract void startDocument() throws IOException;

   public abstract void endDocument() throws IOException;

   public abstract void startElement(String var1, UIComponent var2) throws IOException;

   public abstract void endElement(String var1) throws IOException;

   public abstract void writeAttribute(String var1, Object var2, String var3) throws IOException;

   public abstract void writeURIAttribute(String var1, Object var2, String var3) throws IOException;

   public abstract void writeComment(Object var1) throws IOException;

   public abstract void writeText(Object var1, String var2) throws IOException;

   public void writeText(Object text, UIComponent component, String property) throws IOException {
      this.writeText(text, property);
   }

   public abstract void writeText(char[] var1, int var2, int var3) throws IOException;

   public abstract ResponseWriter cloneWithWriter(Writer var1);
}
