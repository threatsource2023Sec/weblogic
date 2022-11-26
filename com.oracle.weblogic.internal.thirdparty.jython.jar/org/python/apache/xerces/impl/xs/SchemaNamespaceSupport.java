package org.python.apache.xerces.impl.xs;

import org.python.apache.xerces.impl.xs.opti.ElementImpl;
import org.python.apache.xerces.util.NamespaceSupport;
import org.python.apache.xerces.util.SymbolTable;
import org.python.apache.xerces.util.XMLSymbols;
import org.python.apache.xerces.xni.NamespaceContext;
import org.python.apache.xerces.xni.QName;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class SchemaNamespaceSupport extends NamespaceSupport {
   private SchemaRootContext fSchemaRootContext = null;

   public SchemaNamespaceSupport(Element var1, SymbolTable var2) {
      if (var1 != null && !(var1 instanceof ElementImpl)) {
         Document var3 = var1.getOwnerDocument();
         if (var3 != null && var1 != var3.getDocumentElement()) {
            this.fSchemaRootContext = new SchemaRootContext(var1, var2);
         }
      }

   }

   public SchemaNamespaceSupport(SchemaNamespaceSupport var1) {
      this.fSchemaRootContext = var1.fSchemaRootContext;
      this.fNamespaceSize = var1.fNamespaceSize;
      if (this.fNamespace.length < this.fNamespaceSize) {
         this.fNamespace = new String[this.fNamespaceSize];
      }

      System.arraycopy(var1.fNamespace, 0, this.fNamespace, 0, this.fNamespaceSize);
      this.fCurrentContext = var1.fCurrentContext;
      if (this.fContext.length <= this.fCurrentContext) {
         this.fContext = new int[this.fCurrentContext + 1];
      }

      System.arraycopy(var1.fContext, 0, this.fContext, 0, this.fCurrentContext + 1);
   }

   public void setEffectiveContext(String[] var1) {
      if (var1 != null && var1.length != 0) {
         this.pushContext();
         int var2 = this.fNamespaceSize + var1.length;
         if (this.fNamespace.length < var2) {
            String[] var3 = new String[var2];
            System.arraycopy(this.fNamespace, 0, var3, 0, this.fNamespace.length);
            this.fNamespace = var3;
         }

         System.arraycopy(var1, 0, this.fNamespace, this.fNamespaceSize, var1.length);
         this.fNamespaceSize = var2;
      }
   }

   public String[] getEffectiveLocalContext() {
      String[] var1 = null;
      if (this.fCurrentContext >= 3) {
         int var2 = this.fContext[3];
         int var3 = this.fNamespaceSize - var2;
         if (var3 > 0) {
            var1 = new String[var3];
            System.arraycopy(this.fNamespace, var2, var1, 0, var3);
         }
      }

      return var1;
   }

   public void makeGlobal() {
      if (this.fCurrentContext >= 3) {
         this.fCurrentContext = 3;
         this.fNamespaceSize = this.fContext[3];
      }

   }

   public String getURI(String var1) {
      String var2 = super.getURI(var1);
      if (var2 == null && this.fSchemaRootContext != null) {
         if (!this.fSchemaRootContext.fDOMContextBuilt) {
            this.fSchemaRootContext.fillNamespaceContext();
            this.fSchemaRootContext.fDOMContextBuilt = true;
         }

         if (this.fSchemaRootContext.fNamespaceSize > 0 && !this.containsPrefix(var1)) {
            var2 = this.fSchemaRootContext.getURI(var1);
         }
      }

      return var2;
   }

   static final class SchemaRootContext {
      String[] fNamespace = new String[32];
      int fNamespaceSize = 0;
      boolean fDOMContextBuilt = false;
      private final Element fSchemaRoot;
      private final SymbolTable fSymbolTable;
      private final QName fAttributeQName = new QName();

      SchemaRootContext(Element var1, SymbolTable var2) {
         this.fSchemaRoot = var1;
         this.fSymbolTable = var2;
      }

      void fillNamespaceContext() {
         if (this.fSchemaRoot != null) {
            for(Node var1 = this.fSchemaRoot.getParentNode(); var1 != null; var1 = var1.getParentNode()) {
               if (1 == var1.getNodeType()) {
                  NamedNodeMap var2 = var1.getAttributes();
                  int var3 = var2.getLength();

                  for(int var4 = 0; var4 < var3; ++var4) {
                     Attr var5 = (Attr)var2.item(var4);
                     String var6 = var5.getValue();
                     if (var6 == null) {
                        var6 = XMLSymbols.EMPTY_STRING;
                     }

                     this.fillQName(this.fAttributeQName, var5);
                     if (this.fAttributeQName.uri == NamespaceContext.XMLNS_URI) {
                        if (this.fAttributeQName.prefix == XMLSymbols.PREFIX_XMLNS) {
                           this.declarePrefix(this.fAttributeQName.localpart, var6.length() != 0 ? this.fSymbolTable.addSymbol(var6) : null);
                        } else {
                           this.declarePrefix(XMLSymbols.EMPTY_STRING, var6.length() != 0 ? this.fSymbolTable.addSymbol(var6) : null);
                        }
                     }
                  }
               }
            }
         }

      }

      String getURI(String var1) {
         for(int var2 = 0; var2 < this.fNamespaceSize; var2 += 2) {
            if (this.fNamespace[var2] == var1) {
               return this.fNamespace[var2 + 1];
            }
         }

         return null;
      }

      private void declarePrefix(String var1, String var2) {
         if (this.fNamespaceSize == this.fNamespace.length) {
            String[] var3 = new String[this.fNamespaceSize * 2];
            System.arraycopy(this.fNamespace, 0, var3, 0, this.fNamespaceSize);
            this.fNamespace = var3;
         }

         this.fNamespace[this.fNamespaceSize++] = var1;
         this.fNamespace[this.fNamespaceSize++] = var2;
      }

      private void fillQName(QName var1, Node var2) {
         String var3 = var2.getPrefix();
         String var4 = var2.getLocalName();
         String var5 = var2.getNodeName();
         String var6 = var2.getNamespaceURI();
         var1.prefix = var3 != null ? this.fSymbolTable.addSymbol(var3) : XMLSymbols.EMPTY_STRING;
         var1.localpart = var4 != null ? this.fSymbolTable.addSymbol(var4) : XMLSymbols.EMPTY_STRING;
         var1.rawname = var5 != null ? this.fSymbolTable.addSymbol(var5) : XMLSymbols.EMPTY_STRING;
         var1.uri = var6 != null && var6.length() > 0 ? this.fSymbolTable.addSymbol(var6) : null;
      }
   }
}
