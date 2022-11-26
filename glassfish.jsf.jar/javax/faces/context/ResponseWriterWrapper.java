package javax.faces.context;

import java.io.IOException;
import java.io.Writer;
import javax.faces.FacesWrapper;
import javax.faces.component.UIComponent;

public abstract class ResponseWriterWrapper extends ResponseWriter implements FacesWrapper {
   private ResponseWriter wrapped;

   /** @deprecated */
   @Deprecated
   public ResponseWriterWrapper() {
   }

   public ResponseWriterWrapper(ResponseWriter wrapped) {
      this.wrapped = wrapped;
   }

   public ResponseWriter getWrapped() {
      return this.wrapped;
   }

   public String getContentType() {
      return this.getWrapped().getContentType();
   }

   public String getCharacterEncoding() {
      return this.getWrapped().getCharacterEncoding();
   }

   public void flush() throws IOException {
      this.getWrapped().flush();
   }

   public void startDocument() throws IOException {
      this.getWrapped().startDocument();
   }

   public void endDocument() throws IOException {
      this.getWrapped().endDocument();
   }

   public void startElement(String name, UIComponent component) throws IOException {
      this.getWrapped().startElement(name, component);
   }

   public void startCDATA() throws IOException {
      this.getWrapped().startCDATA();
   }

   public void endCDATA() throws IOException {
      this.getWrapped().endCDATA();
   }

   public void endElement(String name) throws IOException {
      this.getWrapped().endElement(name);
   }

   public void writeAttribute(String name, Object value, String property) throws IOException {
      this.getWrapped().writeAttribute(name, value, property);
   }

   public void writeURIAttribute(String name, Object value, String property) throws IOException {
      this.getWrapped().writeURIAttribute(name, value, property);
   }

   public void writeComment(Object comment) throws IOException {
      this.getWrapped().writeComment(comment);
   }

   public void writeDoctype(String doctype) throws IOException {
      this.getWrapped().writeDoctype(doctype);
   }

   public void writePreamble(String preamble) throws IOException {
      this.getWrapped().writePreamble(preamble);
   }

   public void writeText(Object text, String property) throws IOException {
      this.getWrapped().writeText(text, property);
   }

   public void writeText(Object text, UIComponent component, String property) throws IOException {
      this.getWrapped().writeText(text, component, property);
   }

   public void writeText(char[] text, int off, int len) throws IOException {
      this.getWrapped().writeText(text, off, len);
   }

   public ResponseWriter cloneWithWriter(Writer writer) {
      return this.getWrapped().cloneWithWriter(writer);
   }

   public void close() throws IOException {
      this.getWrapped().close();
   }

   public void write(char[] cbuf, int off, int len) throws IOException {
      this.getWrapped().write(cbuf, off, len);
   }
}
