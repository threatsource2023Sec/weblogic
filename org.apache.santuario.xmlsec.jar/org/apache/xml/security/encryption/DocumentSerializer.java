package org.apache.xml.security.encryption;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocumentSerializer extends AbstractSerializer {
   public Node deserialize(byte[] source, Node ctx) throws XMLEncryptionException, IOException {
      byte[] fragment = createContext(source, ctx);
      InputStream is = new ByteArrayInputStream(fragment);
      Throwable var5 = null;

      Node var6;
      try {
         var6 = this.deserialize(ctx, new InputSource(is));
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
      return this.deserialize(ctx, new InputSource(new StringReader(fragment)));
   }

   private Node deserialize(Node ctx, InputSource inputSource) throws XMLEncryptionException {
      DocumentBuilder db = null;

      try {
         db = XMLUtils.createDocumentBuilder(false, this.secureValidation);
         Document d = db.parse(inputSource);
         Document contextDocument = null;
         if (9 == ctx.getNodeType()) {
            contextDocument = (Document)ctx;
         } else {
            contextDocument = ctx.getOwnerDocument();
         }

         Element fragElt = (Element)contextDocument.importNode(d.getDocumentElement(), true);
         DocumentFragment result = contextDocument.createDocumentFragment();

         for(Node child = fragElt.getFirstChild(); child != null; child = fragElt.getFirstChild()) {
            fragElt.removeChild(child);
            result.appendChild(child);
         }

         DocumentFragment var9 = result;
         return var9;
      } catch (SAXException var15) {
         throw new XMLEncryptionException(var15);
      } catch (ParserConfigurationException var16) {
         throw new XMLEncryptionException(var16);
      } catch (IOException var17) {
         throw new XMLEncryptionException(var17);
      } finally {
         if (db != null) {
            XMLUtils.repoolDocumentBuilder(db);
         }

      }
   }
}
