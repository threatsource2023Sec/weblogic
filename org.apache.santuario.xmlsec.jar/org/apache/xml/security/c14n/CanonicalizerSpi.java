package org.apache.xml.security.c14n;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class CanonicalizerSpi {
   protected boolean reset = false;
   protected boolean secureValidation;

   public byte[] engineCanonicalize(byte[] inputBytes) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
      Document document = null;
      InputStream bais = new ByteArrayInputStream(inputBytes);
      Throwable var4 = null;

      try {
         InputSource in = new InputSource(bais);
         DocumentBuilder db = XMLUtils.createDocumentBuilder(false, this.secureValidation);

         try {
            document = db.parse(in);
         } finally {
            XMLUtils.repoolDocumentBuilder(db);
         }
      } catch (Throwable var21) {
         var4 = var21;
         throw var21;
      } finally {
         if (var4 != null) {
            try {
               bais.close();
            } catch (Throwable var19) {
               var4.addSuppressed(var19);
            }
         } else {
            bais.close();
         }

      }

      return this.engineCanonicalizeSubTree(document);
   }

   public byte[] engineCanonicalizeXPathNodeSet(NodeList xpathNodeSet) throws CanonicalizationException {
      return this.engineCanonicalizeXPathNodeSet(XMLUtils.convertNodelistToSet(xpathNodeSet));
   }

   public byte[] engineCanonicalizeXPathNodeSet(NodeList xpathNodeSet, String inclusiveNamespaces) throws CanonicalizationException {
      return this.engineCanonicalizeXPathNodeSet(XMLUtils.convertNodelistToSet(xpathNodeSet), inclusiveNamespaces);
   }

   public abstract String engineGetURI();

   public abstract boolean engineGetIncludeComments();

   public abstract byte[] engineCanonicalizeXPathNodeSet(Set var1) throws CanonicalizationException;

   public abstract byte[] engineCanonicalizeXPathNodeSet(Set var1, String var2) throws CanonicalizationException;

   public abstract byte[] engineCanonicalizeSubTree(Node var1) throws CanonicalizationException;

   public abstract byte[] engineCanonicalizeSubTree(Node var1, String var2) throws CanonicalizationException;

   public abstract byte[] engineCanonicalizeSubTree(Node var1, String var2, boolean var3) throws CanonicalizationException;

   public abstract void setWriter(OutputStream var1);

   public boolean isSecureValidation() {
      return this.secureValidation;
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }
}
