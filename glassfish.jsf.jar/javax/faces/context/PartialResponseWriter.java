package javax.faces.context;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;

public class PartialResponseWriter extends ResponseWriterWrapper {
   private boolean inChanges = false;
   private boolean inInsertBefore = false;
   private boolean inInsertAfter = false;
   private boolean inUpdate = false;
   public static final String RENDER_ALL_MARKER = "javax.faces.ViewRoot";
   public static final String VIEW_STATE_MARKER = "javax.faces.ViewState";

   public PartialResponseWriter(ResponseWriter writer) {
      super(writer);
   }

   public void startDocument() throws IOException {
      ResponseWriter writer = this.getWrapped();
      String encoding = writer.getCharacterEncoding();
      if (encoding == null) {
         encoding = "utf-8";
      }

      writer.writePreamble("<?xml version='1.0' encoding='" + encoding + "'?>\n");
      writer.startElement("partial-response", (UIComponent)null);
      FacesContext ctx = FacesContext.getCurrentInstance();
      if (null != ctx && ctx.getViewRoot() instanceof NamingContainer) {
         String id = ctx.getViewRoot().getContainerClientId(ctx);
         writer.writeAttribute("id", id, "id");
      }

   }

   public void endDocument() throws IOException {
      this.endChangesIfNecessary();
      ResponseWriter writer = this.getWrapped();
      if (!(writer instanceof PartialResponseWriter)) {
         writer.endElement("partial-response");
      }

      writer.endDocument();
   }

   public void startInsertBefore(String targetId) throws IOException {
      this.startChangesIfNecessary();
      this.inInsertBefore = true;
      ResponseWriter writer = this.getWrapped();
      writer.startElement("insert", (UIComponent)null);
      writer.startElement("before", (UIComponent)null);
      writer.writeAttribute("id", targetId, (String)null);
      writer.startCDATA();
   }

   public void startInsertAfter(String targetId) throws IOException {
      this.startChangesIfNecessary();
      this.inInsertAfter = true;
      ResponseWriter writer = this.getWrapped();
      writer.startElement("insert", (UIComponent)null);
      writer.startElement("after", (UIComponent)null);
      writer.writeAttribute("id", targetId, (String)null);
      writer.startCDATA();
   }

   public void endInsert() throws IOException {
      ResponseWriter writer = this.getWrapped();
      writer.endCDATA();
      if (this.inInsertBefore) {
         writer.endElement("before");
         this.inInsertBefore = false;
      } else if (this.inInsertAfter) {
         writer.endElement("after");
         this.inInsertAfter = false;
      }

      writer.endElement("insert");
   }

   public void startUpdate(String targetId) throws IOException {
      this.startChangesIfNecessary();
      this.inUpdate = true;
      ResponseWriter writer = this.getWrapped();
      writer.startElement("update", (UIComponent)null);
      writer.writeAttribute("id", targetId, (String)null);
      writer.startCDATA();
   }

   public void endUpdate() throws IOException {
      ResponseWriter writer = this.getWrapped();
      writer.endCDATA();
      writer.endElement("update");
      this.inUpdate = false;
   }

   public void updateAttributes(String targetId, Map attributes) throws IOException {
      this.startChangesIfNecessary();
      ResponseWriter writer = this.getWrapped();
      writer.startElement("attributes", (UIComponent)null);
      writer.writeAttribute("id", targetId, (String)null);
      Iterator var4 = attributes.entrySet().iterator();

      while(var4.hasNext()) {
         Map.Entry entry = (Map.Entry)var4.next();
         writer.startElement("attribute", (UIComponent)null);
         writer.writeAttribute("name", entry.getKey(), (String)null);
         writer.writeAttribute("value", entry.getValue(), (String)null);
         writer.endElement("attribute");
      }

      writer.endElement("attributes");
   }

   public void delete(String targetId) throws IOException {
      this.startChangesIfNecessary();
      ResponseWriter writer = this.getWrapped();
      writer.startElement("delete", (UIComponent)null);
      writer.writeAttribute("id", targetId, (String)null);
      writer.endElement("delete");
   }

   public void redirect(String url) throws IOException {
      this.endChangesIfNecessary();
      ResponseWriter writer = this.getWrapped();
      writer.startElement("redirect", (UIComponent)null);
      writer.writeAttribute("url", url, (String)null);
      writer.endElement("redirect");
   }

   public void startEval() throws IOException {
      this.startChangesIfNecessary();
      ResponseWriter writer = this.getWrapped();
      writer.startElement("eval", (UIComponent)null);
      writer.startCDATA();
   }

   public void endEval() throws IOException {
      ResponseWriter writer = this.getWrapped();
      writer.endCDATA();
      writer.endElement("eval");
   }

   public void startExtension(Map attributes) throws IOException {
      this.startChangesIfNecessary();
      ResponseWriter writer = this.getWrapped();
      writer.startElement("extension", (UIComponent)null);
      if (attributes != null && !attributes.isEmpty()) {
         Iterator var3 = attributes.entrySet().iterator();

         while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();
            writer.writeAttribute((String)entry.getKey(), entry.getValue(), (String)null);
         }
      }

   }

   public void endExtension() throws IOException {
      ResponseWriter writer = this.getWrapped();
      writer.endElement("extension");
   }

   public void startError(String errorName) throws IOException {
      this.endUpdateIfNecessary();
      this.endChangesIfNecessary();
      ResponseWriter writer = this.getWrapped();
      writer.startElement("error", (UIComponent)null);
      writer.startElement("error-name", (UIComponent)null);
      writer.write(errorName);
      writer.endElement("error-name");
      writer.startElement("error-message", (UIComponent)null);
      writer.startCDATA();
   }

   public void endError() throws IOException {
      ResponseWriter writer = this.getWrapped();
      writer.endCDATA();
      writer.endElement("error-message");
      writer.endElement("error");
   }

   private void startChangesIfNecessary() throws IOException {
      if (!this.inChanges) {
         ResponseWriter writer = this.getWrapped();
         writer.startElement("changes", (UIComponent)null);
         this.inChanges = true;
      }

   }

   private void endUpdateIfNecessary() throws IOException {
      if (this.inUpdate) {
         this.endUpdate();
      }

   }

   private void endChangesIfNecessary() throws IOException {
      if (this.inChanges) {
         ResponseWriter writer = this.getWrapped();
         writer.endElement("changes");
         this.inChanges = false;
      }

   }
}
