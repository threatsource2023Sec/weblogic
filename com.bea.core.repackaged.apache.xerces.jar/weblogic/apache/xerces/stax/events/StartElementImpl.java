package weblogic.apache.xerces.stax.events;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import weblogic.apache.xerces.stax.DefaultNamespaceContext;

public final class StartElementImpl extends ElementImpl implements StartElement {
   private static final Comparator QNAME_COMPARATOR = new Comparator() {
      public int compare(Object var1, Object var2) {
         if (var1.equals(var2)) {
            return 0;
         } else {
            QName var3 = (QName)var1;
            QName var4 = (QName)var2;
            return var3.toString().compareTo(var4.toString());
         }
      }
   };
   private final Map fAttributes;
   private final NamespaceContext fNamespaceContext;

   public StartElementImpl(QName var1, Iterator var2, Iterator var3, NamespaceContext var4, Location var5) {
      super(var1, true, var3, var5);
      if (var2 != null && var2.hasNext()) {
         this.fAttributes = new TreeMap(QNAME_COMPARATOR);

         do {
            Attribute var6 = (Attribute)var2.next();
            this.fAttributes.put(var6.getName(), var6);
         } while(var2.hasNext());
      } else {
         this.fAttributes = Collections.EMPTY_MAP;
      }

      this.fNamespaceContext = (NamespaceContext)(var4 != null ? var4 : DefaultNamespaceContext.getInstance());
   }

   public Iterator getAttributes() {
      return ElementImpl.createImmutableIterator(this.fAttributes.values().iterator());
   }

   public Attribute getAttributeByName(QName var1) {
      return (Attribute)this.fAttributes.get(var1);
   }

   public NamespaceContext getNamespaceContext() {
      return this.fNamespaceContext;
   }

   public String getNamespaceURI(String var1) {
      return this.fNamespaceContext.getNamespaceURI(var1);
   }

   public void writeAsEncodedUnicode(Writer var1) throws XMLStreamException {
      try {
         var1.write(60);
         QName var2 = this.getName();
         String var3 = var2.getPrefix();
         if (var3 != null && var3.length() > 0) {
            var1.write(var3);
            var1.write(58);
         }

         var1.write(var2.getLocalPart());
         Iterator var4 = this.getNamespaces();

         while(var4.hasNext()) {
            Namespace var5 = (Namespace)var4.next();
            var1.write(32);
            var5.writeAsEncodedUnicode(var1);
         }

         Iterator var8 = this.getAttributes();

         while(var8.hasNext()) {
            Attribute var6 = (Attribute)var8.next();
            var1.write(32);
            var6.writeAsEncodedUnicode(var1);
         }

         var1.write(62);
      } catch (IOException var7) {
         throw new XMLStreamException(var7);
      }
   }
}
