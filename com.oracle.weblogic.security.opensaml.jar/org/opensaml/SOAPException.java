package org.opensaml;

import java.util.Collection;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public class SOAPException extends BindingException implements Cloneable {
   public static final QName CLIENT = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Client");
   public static final QName SERVER = new QName("http://schemas.xmlsoap.org/soap/envelope/", "Server");
   public static final QName MUSTUNDERSTAND = new QName("http://schemas.xmlsoap.org/soap/envelope/", "MustUnderstand");
   public static final QName VERSION = new QName("http://schemas.xmlsoap.org/soap/envelope/", "VersionMismatch");

   protected SOAPException(Element var1) throws SAMLException {
      super(var1);
   }

   public SOAPException(String var1) {
      super(var1);
   }

   public SOAPException(String var1, Exception var2) {
      super(var1, var2);
   }

   public SOAPException(Collection var1, String var2) {
      super(var1, var2);
   }

   public SOAPException(Collection var1, Exception var2) {
      super(var1, var2);
   }

   public SOAPException(Collection var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public SOAPException(QName var1, String var2) {
      super(var1, var2);
   }

   public SOAPException(QName var1, Exception var2) {
      super(var1, var2);
   }

   public SOAPException(QName var1, String var2, Exception var3) {
      super(var1, var2, var3);
   }

   public void fromDOM(Element var1) throws SAMLException {
      if (var1 == null) {
         throw new MalformedException("SOAPException.fromDOM() given an empty DOM");
      } else {
         this.root = var1;
         if (this.config.getBooleanProperty("org.opensaml.strict-dom-checking") && !XML.isElementNamed(var1, "http://schemas.xmlsoap.org/soap/envelope/", "Fault")) {
            throw new MalformedException(SAMLException.RESPONDER, "SOAPException.fromDOM() requires soap:Fault at root");
         } else {
            Node var2;
            for(var2 = var1.getFirstChild(); var2 != null && var2.getNodeType() != 1; var2 = var2.getNextSibling()) {
            }

            QName var3 = QName.getQNameTextNode((Text)var2.getFirstChild());
            if (var3 == null) {
               throw new MalformedException(SAMLException.RESPONDER, "SAMLException.fromDOM() unable to evaluate faultcode value");
            } else {
               this.codes.add(var3);

               Node var4;
               for(var4 = var2.getNextSibling(); var4 != null && (var4.getNodeType() != 1 || !XML.isElementNamed(var1, (String)null, "faultstring")); var4 = var4.getNextSibling()) {
               }

               if (var4 != null) {
                  this.msg = var4.getFirstChild().getNodeValue();
               }

            }
         }
      }
   }

   public Node toDOM(Document var1) throws DOMException {
      if (this.root != null) {
         if (this.root.getOwnerDocument() != var1) {
            this.root = var1.importNode(this.root, true);
         }
      } else {
         Element var2 = var1.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "Fault");
         var2.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:soap", "http://schemas.xmlsoap.org/soap/envelope/");
         Element var3 = var1.createElementNS((String)null, "faultcode");
         if (this.codes != null && !this.codes.isEmpty()) {
            var3.appendChild(var1.createTextNode("soap:" + ((QName)((QName)this.codes.get(0))).getLocalName()));
         } else {
            var3.appendChild(var1.createTextNode("soap:Server"));
         }

         var2.appendChild(var3);
         if (this.getMessage() != null) {
            Element var4 = var1.createElementNS((String)null, "faultstring");
            var4.appendChild(var1.createTextNode(this.getMessage()));
            var2.appendChild(var4);
         }

         this.root = var2;
      }

      return this.root;
   }
}
