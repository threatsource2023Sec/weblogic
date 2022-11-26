package weblogic.apache.xerces.dom;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import weblogic.apache.xerces.impl.dv.xs.XSSimpleTypeDecl;
import weblogic.apache.xerces.impl.xs.XSComplexTypeDecl;
import weblogic.apache.xerces.xni.NamespaceContext;
import weblogic.apache.xerces.xs.XSTypeDefinition;

public class ElementNSImpl extends ElementImpl {
   static final long serialVersionUID = -9142310625494392642L;
   static final String xmlURI = "http://www.w3.org/XML/1998/namespace";
   protected String namespaceURI;
   protected String localName;
   transient XSTypeDefinition type;

   protected ElementNSImpl() {
   }

   protected ElementNSImpl(CoreDocumentImpl var1, String var2, String var3) throws DOMException {
      super(var1, var3);
      this.setName(var2, var3);
   }

   private void setName(String var1, String var2) {
      this.namespaceURI = var1;
      if (var1 != null) {
         this.namespaceURI = var1.length() == 0 ? null : var1;
      }

      String var6;
      if (var2 == null) {
         var6 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", (Object[])null);
         throw new DOMException((short)14, var6);
      } else {
         int var4 = var2.indexOf(58);
         int var5 = var2.lastIndexOf(58);
         this.ownerDocument.checkNamespaceWF(var2, var4, var5);
         if (var4 < 0) {
            this.localName = var2;
            if (this.ownerDocument.errorChecking) {
               this.ownerDocument.checkQName((String)null, this.localName);
               if (var2.equals("xmlns") && (var1 == null || !var1.equals(NamespaceContext.XMLNS_URI)) || var1 != null && var1.equals(NamespaceContext.XMLNS_URI) && !var2.equals("xmlns")) {
                  var6 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", (Object[])null);
                  throw new DOMException((short)14, var6);
               }
            }
         } else {
            String var3 = var2.substring(0, var4);
            this.localName = var2.substring(var5 + 1);
            if (this.ownerDocument.errorChecking) {
               if (var1 == null || var3.equals("xml") && !var1.equals(NamespaceContext.XML_URI)) {
                  var6 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", (Object[])null);
                  throw new DOMException((short)14, var6);
               }

               this.ownerDocument.checkQName(var3, this.localName);
               this.ownerDocument.checkDOMNSErr(var3, var1);
            }
         }

      }
   }

   protected ElementNSImpl(CoreDocumentImpl var1, String var2, String var3, String var4) throws DOMException {
      super(var1, var3);
      this.localName = var4;
      this.namespaceURI = var2;
   }

   protected ElementNSImpl(CoreDocumentImpl var1, String var2) {
      super(var1, var2);
   }

   void rename(String var1, String var2) {
      if (this.needsSyncData()) {
         this.synchronizeData();
      }

      this.name = var2;
      this.setName(var1, var2);
      this.reconcileDefaultAttributes();
   }

   public String getNamespaceURI() {
      if (this.needsSyncData()) {
         this.synchronizeData();
      }

      return this.namespaceURI;
   }

   public String getPrefix() {
      if (this.needsSyncData()) {
         this.synchronizeData();
      }

      int var1 = this.name.indexOf(58);
      return var1 < 0 ? null : this.name.substring(0, var1);
   }

   public void setPrefix(String var1) throws DOMException {
      if (this.needsSyncData()) {
         this.synchronizeData();
      }

      if (this.ownerDocument.errorChecking) {
         String var2;
         if (this.isReadOnly()) {
            var2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NO_MODIFICATION_ALLOWED_ERR", (Object[])null);
            throw new DOMException((short)7, var2);
         }

         if (var1 != null && var1.length() != 0) {
            if (!CoreDocumentImpl.isXMLName(var1, this.ownerDocument.isXML11Version())) {
               var2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "INVALID_CHARACTER_ERR", (Object[])null);
               throw new DOMException((short)5, var2);
            }

            if (this.namespaceURI == null || var1.indexOf(58) >= 0) {
               var2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", (Object[])null);
               throw new DOMException((short)14, var2);
            }

            if (var1.equals("xml") && !this.namespaceURI.equals("http://www.w3.org/XML/1998/namespace")) {
               var2 = DOMMessageFormatter.formatMessage("http://www.w3.org/dom/DOMTR", "NAMESPACE_ERR", (Object[])null);
               throw new DOMException((short)14, var2);
            }
         }
      }

      if (var1 != null && var1.length() != 0) {
         this.name = var1 + ":" + this.localName;
      } else {
         this.name = this.localName;
      }

   }

   public String getLocalName() {
      if (this.needsSyncData()) {
         this.synchronizeData();
      }

      return this.localName;
   }

   protected Attr getXMLBaseAttribute() {
      return (Attr)this.attributes.getNamedItemNS("http://www.w3.org/XML/1998/namespace", "base");
   }

   public String getTypeName() {
      if (this.type != null) {
         if (this.type instanceof XSSimpleTypeDecl) {
            return ((XSSimpleTypeDecl)this.type).getTypeName();
         }

         if (this.type instanceof XSComplexTypeDecl) {
            return ((XSComplexTypeDecl)this.type).getTypeName();
         }
      }

      return null;
   }

   public String getTypeNamespace() {
      return this.type != null ? this.type.getNamespace() : null;
   }

   public boolean isDerivedFrom(String var1, String var2, int var3) {
      if (this.needsSyncData()) {
         this.synchronizeData();
      }

      if (this.type != null) {
         if (this.type instanceof XSSimpleTypeDecl) {
            return ((XSSimpleTypeDecl)this.type).isDOMDerivedFrom(var1, var2, var3);
         }

         if (this.type instanceof XSComplexTypeDecl) {
            return ((XSComplexTypeDecl)this.type).isDOMDerivedFrom(var1, var2, var3);
         }
      }

      return false;
   }

   public void setType(XSTypeDefinition var1) {
      this.type = var1;
   }
}
