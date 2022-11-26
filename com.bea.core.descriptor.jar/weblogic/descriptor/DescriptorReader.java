package weblogic.descriptor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.util.StreamReaderDelegate;
import weblogic.utils.XXEUtils;

public class DescriptorReader extends StreamReaderDelegate implements XMLStreamReader {
   Map namespaceMap = new HashMap();
   private boolean modified = false;

   public DescriptorReader(InputStream _in) throws XMLStreamException {
      super(XXEUtils.createXMLInputFactoryInstance().createXMLStreamReader(_in));
   }

   public String getNamespaceURI() {
      String uri = super.getNamespaceURI();
      String override = (String)this.namespaceMap.get(uri);
      if (override != null) {
         this.setModified(true);
         uri = override;
      }

      return uri;
   }

   public String getNamespaceURI(int index) {
      String uri = super.getNamespaceURI(index);
      String override = (String)this.namespaceMap.get(uri);
      if (override != null) {
         this.setModified(true);
         uri = override;
      }

      return uri;
   }

   public String getNamespaceURI(String prefix) {
      String result = super.getNamespaceURI(prefix);
      result = this.mapNamespace(result);
      return result;
   }

   public NamespaceContext getNamespaceContext() {
      NamespaceContext result = super.getNamespaceContext();
      return new NamespaceContextWrapper(result);
   }

   public void addNamespaceMapping(String from, String to) {
      this.namespaceMap.put(from, to);
   }

   public boolean isModified() {
      return this.modified;
   }

   protected void setModified(boolean modified) {
      this.modified = modified;
   }

   private String mapNamespace(String result) {
      if (result == null) {
         return null;
      } else {
         Iterator i = this.namespaceMap.keySet().iterator();

         while(i.hasNext()) {
            String next = (String)i.next();
            if (result.equals(next)) {
               result = (String)this.namespaceMap.get(next);
               this.setModified(true);
               break;
            }
         }

         return result;
      }
   }

   private class NamespaceContextWrapper implements NamespaceContext {
      NamespaceContext nc;

      public NamespaceContextWrapper(NamespaceContext _nc) {
         this.nc = _nc;
      }

      public String getNamespaceURI(String key) {
         String result = DescriptorReader.this.mapNamespace(this.nc.getNamespaceURI(key));
         return result;
      }

      public String getPrefix(String key) {
         String result = DescriptorReader.this.mapNamespace(this.nc.getPrefix(key));
         return result;
      }

      public Iterator getPrefixes(String key) {
         return this.nc.getPrefixes(key);
      }
   }
}
