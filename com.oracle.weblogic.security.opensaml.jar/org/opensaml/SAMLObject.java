package org.opensaml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import weblogic.utils.encoders.BASE64Encoder;

public abstract class SAMLObject implements Cloneable {
   protected Node root = null;
   private static final DebugLogger LOGGER = SAMLServicesHelper.getDebugLogger();
   protected SAMLConfig config = SAMLConfig.instance();

   protected static final void logDebug(String var0) {
      if (LOGGER != null && LOGGER.isDebugEnabled()) {
         LOGGER.debug(var0);
      }

   }

   protected static final void logDebugException(String var0, Exception var1) {
      if (LOGGER.isDebugEnabled()) {
         LOGGER.debug(var0, var1);
      }

   }

   protected static Element fromStream(InputStream var0) throws SAMLException {
      try {
         Document var1 = XML.parserPool.parse(var0);
         return var1.getDocumentElement();
      } catch (Exception var2) {
         logDebug("SAMLObject.fromStream() caught an exception while parsing a stream:\n" + var2.getMessage());
         throw new MalformedException("SAMLObject.fromStream() caught exception while parsing a stream", var2);
      }
   }

   public void checkValidity() throws SAMLException {
   }

   public void fromDOM(Element var1) throws SAMLException {
      if (var1 == null) {
         throw new MalformedException("SAMLObject.fromDOM() given an empty DOM");
      } else {
         this.root = var1;
      }
   }

   public void toStream(OutputStream var1) throws IOException, SAMLException {
      this.toDOM();
      this.plantRoot();
      XML.outputNode(this.root, var1);
   }

   public byte[] toBase64() throws IOException, SAMLException {
      ByteArrayOutputStream var1 = new ByteArrayOutputStream();
      this.toStream(var1);
      return (new BASE64Encoder()).encodeBuffer(this.stripXMLHeader(var1).getBytes()).getBytes();
   }

   public Node toDOM(Document var1, boolean var2) throws SAMLException {
      this.checkValidity();
      if (this.root != null && this.root.getOwnerDocument() != var1) {
         this.root = var1.adoptNode(this.root);
      }

      return this.root;
   }

   public Node toDOM(boolean var1) throws SAMLException {
      this.checkValidity();
      return this.root != null ? this.root : this.toDOM(XML.parserPool.newDocument(), var1);
   }

   public Node toDOM(Document var1) throws SAMLException {
      return this.toDOM(var1, true);
   }

   public Node toDOM() throws SAMLException {
      return this.toDOM(true);
   }

   protected Node plantRoot() {
      if (this.root != null) {
         Node var1;
         for(var1 = this.root; var1.getParentNode() != null && var1.getParentNode().getNodeType() != 9; var1 = var1.getParentNode()) {
         }

         Element var2 = this.root.getOwnerDocument().getDocumentElement();
         if (var2 != null && var2 != var1) {
            this.root.getOwnerDocument().replaceChild(var1, var2);
         } else if (var2 == null) {
            this.root.getOwnerDocument().appendChild(var1);
         }
      }

      return this.root;
   }

   protected Object clone() throws CloneNotSupportedException {
      SAMLObject var1 = (SAMLObject)super.clone();
      var1.root = null;
      return var1;
   }

   public String toString() {
      try {
         ByteArrayOutputStream var1 = new ByteArrayOutputStream();
         this.toStream(var1);
         return this.stripXMLHeader(var1);
      } catch (IOException var2) {
         logDebug("SAMLObject.toString() caught an I/O exception while serializing XML: " + var2);
         return "";
      } catch (SAMLException var3) {
         logDebug("SAMLObject.toString() caught a SAML exception while serializing XML: " + var3);
         return "";
      }
   }

   private String stripXMLHeader(ByteArrayOutputStream var1) throws IOException {
      String var2 = var1.toString("UTF8");
      if (var2 != null && var2.startsWith("<?")) {
         int var3 = var2.indexOf("?>");
         if (var3 != -1) {
            var2 = var2.substring(var3 + 2);
         }
      }

      return var2;
   }
}
