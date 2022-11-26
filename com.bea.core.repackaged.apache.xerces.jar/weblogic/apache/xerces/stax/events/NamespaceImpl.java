package weblogic.apache.xerces.stax.events;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.events.Namespace;

public final class NamespaceImpl extends AttributeImpl implements Namespace {
   private final String fPrefix;
   private final String fNamespaceURI;

   public NamespaceImpl(String var1, String var2, Location var3) {
      super(13, makeAttributeQName(var1), var2, (String)null, true, var3);
      this.fPrefix = var1 == null ? "" : var1;
      this.fNamespaceURI = var2;
   }

   private static QName makeAttributeQName(String var0) {
      return var0 != null && !var0.equals("") ? new QName("http://www.w3.org/2000/xmlns/", var0, "xmlns") : new QName("http://www.w3.org/2000/xmlns/", "xmlns", "");
   }

   public String getPrefix() {
      return this.fPrefix;
   }

   public String getNamespaceURI() {
      return this.fNamespaceURI;
   }

   public boolean isDefaultNamespaceDeclaration() {
      return this.fPrefix.length() == 0;
   }
}
