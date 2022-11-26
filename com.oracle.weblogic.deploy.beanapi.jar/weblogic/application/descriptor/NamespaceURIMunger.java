package weblogic.application.descriptor;

import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.util.StreamReaderDelegate;
import weblogic.xml.stax.XMLStreamInputFactory;

public class NamespaceURIMunger extends StreamReaderDelegate {
   protected static final XMLInputFactory xiFactory = new XMLStreamInputFactory();
   private Set oldNamespaceURISet = new HashSet();
   private String newNamespaceURI = null;

   public NamespaceURIMunger(InputStream in, String newNamespaceURI, String[] oldNamespaceURIs) throws XMLStreamException {
      super(xiFactory.createXMLStreamReader(in));
      this.newNamespaceURI = newNamespaceURI;
      String[] var4 = oldNamespaceURIs;
      int var5 = oldNamespaceURIs.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String uri = var4[var6];
         this.oldNamespaceURISet.add(uri);
      }

   }

   public NamespaceContext getNamespaceContext() {
      NamespaceContext context = super.getNamespaceContext();
      return context == null ? null : new NamespaceContextWrapper(context);
   }

   public String getNamespaceURI() {
      return this.mungeNamespaceURI(super.getNamespaceURI());
   }

   public String getNamespaceURI(int index) {
      return this.mungeNamespaceURI(super.getNamespaceURI(index));
   }

   public String getNamespaceURI(String prefix) {
      return this.mungeNamespaceURI(super.getNamespaceURI(prefix));
   }

   private String mungeNamespaceURI(String namespaceURI) {
      return this.oldNamespaceURISet.contains(namespaceURI) ? this.newNamespaceURI : namespaceURI;
   }

   private class NamespaceContextWrapper implements NamespaceContext {
      private NamespaceContext delegate;

      public NamespaceContextWrapper(NamespaceContext delegate) {
         this.delegate = delegate;
      }

      public String getNamespaceURI(String prefix) {
         return NamespaceURIMunger.this.mungeNamespaceURI(this.delegate.getNamespaceURI(prefix));
      }

      public String getPrefix(String namespaceURI) {
         throw new UnsupportedOperationException();
      }

      public Iterator getPrefixes(String namespaceURI) {
         throw new UnsupportedOperationException();
      }
   }
}
