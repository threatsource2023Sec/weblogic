package org.apache.xml.security.encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;

public class TransformSerializer extends AbstractSerializer {
   private TransformerFactory transformerFactory;

   public Node deserialize(byte[] source, Node ctx) throws XMLEncryptionException, IOException {
      byte[] fragment = createContext(source, ctx);
      InputStream is = new ByteArrayInputStream(fragment);
      Throwable var5 = null;

      Node var6;
      try {
         var6 = this.deserialize((Node)ctx, (Source)(new StreamSource(is)));
      } catch (Throwable var15) {
         var5 = var15;
         throw var15;
      } finally {
         if (var5 != null) {
            try {
               is.close();
            } catch (Throwable var14) {
               var5.addSuppressed(var14);
            }
         } else {
            is.close();
         }

      }

      return var6;
   }

   public Node deserialize(String source, Node ctx) throws XMLEncryptionException {
      String fragment = createContext(source, ctx);
      return this.deserialize((Node)ctx, (Source)(new StreamSource(new StringReader(fragment))));
   }

   private Node deserialize(Node ctx, Source source) throws XMLEncryptionException {
      try {
         Document contextDocument = null;
         if (9 == ctx.getNodeType()) {
            contextDocument = (Document)ctx;
         } else {
            contextDocument = ctx.getOwnerDocument();
         }

         if (this.transformerFactory == null) {
            this.transformerFactory = TransformerFactory.newInstance();
            this.transformerFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
         }

         Transformer transformer = this.transformerFactory.newTransformer();
         DOMResult res = new DOMResult();
         Node placeholder = contextDocument.createDocumentFragment();
         res.setNode(placeholder);
         transformer.transform(source, res);
         Node dummyChild = placeholder.getFirstChild();
         Node child = dummyChild.getFirstChild();
         if (child != null && child.getNextSibling() == null) {
            return child;
         } else {
            DocumentFragment docfrag;
            for(docfrag = contextDocument.createDocumentFragment(); child != null; child = dummyChild.getFirstChild()) {
               dummyChild.removeChild(child);
               docfrag.appendChild(child);
            }

            return docfrag;
         }
      } catch (Exception var10) {
         throw new XMLEncryptionException(var10);
      }
   }
}
