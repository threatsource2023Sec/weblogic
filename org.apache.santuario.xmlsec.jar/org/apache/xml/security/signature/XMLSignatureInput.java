package org.apache.xml.security.signature;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.implementations.Canonicalizer11_OmitComments;
import org.apache.xml.security.c14n.implementations.Canonicalizer20010315OmitComments;
import org.apache.xml.security.c14n.implementations.CanonicalizerBase;
import org.apache.xml.security.exceptions.XMLSecurityRuntimeException;
import org.apache.xml.security.utils.IgnoreAllErrorHandler;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class XMLSignatureInput {
   private InputStream inputOctetStreamProxy;
   private Set inputNodeSet;
   private Node subNode;
   private Node excludeNode;
   private boolean excludeComments = false;
   private boolean isNodeSet = false;
   private byte[] bytes;
   private boolean secureValidation;
   private String mimeType;
   private String sourceURI;
   private List nodeFilters = new ArrayList();
   private boolean needsToBeExpanded = false;
   private OutputStream outputStream;
   private String preCalculatedDigest;

   public XMLSignatureInput(byte[] inputOctets) {
      this.bytes = inputOctets;
   }

   public XMLSignatureInput(InputStream inputOctetStream) {
      this.inputOctetStreamProxy = inputOctetStream;
   }

   public XMLSignatureInput(Node rootNode) {
      this.subNode = rootNode;
   }

   public XMLSignatureInput(Set inputNodeSet) {
      this.inputNodeSet = inputNodeSet;
   }

   public XMLSignatureInput(String preCalculatedDigest) {
      this.preCalculatedDigest = preCalculatedDigest;
   }

   public boolean isNeedsToBeExpanded() {
      return this.needsToBeExpanded;
   }

   public void setNeedsToBeExpanded(boolean needsToBeExpanded) {
      this.needsToBeExpanded = needsToBeExpanded;
   }

   public Set getNodeSet() throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
      return this.getNodeSet(false);
   }

   public Set getInputNodeSet() {
      return this.inputNodeSet;
   }

   public Set getNodeSet(boolean circumvent) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
      if (this.inputNodeSet != null) {
         return this.inputNodeSet;
      } else if (this.inputOctetStreamProxy == null && this.subNode != null) {
         if (circumvent) {
            XMLUtils.circumventBug2650(XMLUtils.getOwnerDocument(this.subNode));
         }

         this.inputNodeSet = new LinkedHashSet();
         XMLUtils.getSet(this.subNode, this.inputNodeSet, this.excludeNode, this.excludeComments);
         return this.inputNodeSet;
      } else if (this.isOctetStream()) {
         this.convertToNodes();
         Set result = new LinkedHashSet();
         XMLUtils.getSet(this.subNode, result, (Node)null, false);
         return result;
      } else {
         throw new RuntimeException("getNodeSet() called but no input data present");
      }
   }

   public InputStream getOctetStream() throws IOException {
      if (this.inputOctetStreamProxy != null) {
         return this.inputOctetStreamProxy;
      } else if (this.bytes != null) {
         this.inputOctetStreamProxy = new ByteArrayInputStream(this.bytes);
         return this.inputOctetStreamProxy;
      } else {
         return null;
      }
   }

   public InputStream getOctetStreamReal() {
      return this.inputOctetStreamProxy;
   }

   public byte[] getBytes() throws IOException, CanonicalizationException {
      byte[] inputBytes = this.getBytesFromInputStream();
      if (inputBytes != null) {
         return inputBytes;
      } else {
         Canonicalizer20010315OmitComments c14nizer = new Canonicalizer20010315OmitComments();
         this.bytes = c14nizer.engineCanonicalize(this);
         return this.bytes;
      }
   }

   public boolean isNodeSet() {
      return this.inputOctetStreamProxy == null && this.inputNodeSet != null || this.isNodeSet;
   }

   public boolean isElement() {
      return this.inputOctetStreamProxy == null && this.subNode != null && this.inputNodeSet == null && !this.isNodeSet;
   }

   public boolean isOctetStream() {
      return (this.inputOctetStreamProxy != null || this.bytes != null) && this.inputNodeSet == null && this.subNode == null;
   }

   public boolean isOutputStreamSet() {
      return this.outputStream != null;
   }

   public boolean isByteArray() {
      return this.bytes != null && this.inputNodeSet == null && this.subNode == null;
   }

   public boolean isPreCalculatedDigest() {
      return this.preCalculatedDigest != null;
   }

   public boolean isInitialized() {
      return this.isOctetStream() || this.isNodeSet();
   }

   public String getMIMEType() {
      return this.mimeType;
   }

   public void setMIMEType(String mimeType) {
      this.mimeType = mimeType;
   }

   public String getSourceURI() {
      return this.sourceURI;
   }

   public void setSourceURI(String sourceURI) {
      this.sourceURI = sourceURI;
   }

   public String toString() {
      if (this.isNodeSet()) {
         return "XMLSignatureInput/NodeSet/" + this.inputNodeSet.size() + " nodes/" + this.getSourceURI();
      } else if (this.isElement()) {
         return "XMLSignatureInput/Element/" + this.subNode + " exclude " + this.excludeNode + " comments:" + this.excludeComments + "/" + this.getSourceURI();
      } else {
         try {
            return "XMLSignatureInput/OctetStream/" + this.getBytes().length + " octets/" + this.getSourceURI();
         } catch (IOException var2) {
            return "XMLSignatureInput/OctetStream//" + this.getSourceURI();
         } catch (CanonicalizationException var3) {
            return "XMLSignatureInput/OctetStream//" + this.getSourceURI();
         }
      }
   }

   public String getHTMLRepresentation() throws XMLSignatureException {
      XMLSignatureInputDebugger db = new XMLSignatureInputDebugger(this);
      return db.getHTMLRepresentation();
   }

   public String getHTMLRepresentation(Set inclusiveNamespaces) throws XMLSignatureException {
      XMLSignatureInputDebugger db = new XMLSignatureInputDebugger(this, inclusiveNamespaces);
      return db.getHTMLRepresentation();
   }

   public Node getExcludeNode() {
      return this.excludeNode;
   }

   public void setExcludeNode(Node excludeNode) {
      this.excludeNode = excludeNode;
   }

   public Node getSubNode() {
      return this.subNode;
   }

   public boolean isExcludeComments() {
      return this.excludeComments;
   }

   public void setExcludeComments(boolean excludeComments) {
      this.excludeComments = excludeComments;
   }

   public void updateOutputStream(OutputStream diOs) throws CanonicalizationException, IOException {
      this.updateOutputStream(diOs, false);
   }

   public void updateOutputStream(OutputStream diOs, boolean c14n11) throws CanonicalizationException, IOException {
      if (diOs != this.outputStream) {
         if (this.bytes != null) {
            diOs.write(this.bytes);
         } else if (this.inputOctetStreamProxy == null) {
            CanonicalizerBase c14nizer = null;
            if (c14n11) {
               c14nizer = new Canonicalizer11_OmitComments();
            } else {
               c14nizer = new Canonicalizer20010315OmitComments();
            }

            ((CanonicalizerBase)c14nizer).setWriter(diOs);
            ((CanonicalizerBase)c14nizer).engineCanonicalize(this);
         } else {
            byte[] buffer = new byte[4096];
            int bytesread = false;

            int bytesread;
            try {
               while((bytesread = this.inputOctetStreamProxy.read(buffer)) != -1) {
                  diOs.write(buffer, 0, bytesread);
               }
            } catch (IOException var6) {
               this.inputOctetStreamProxy.close();
               throw var6;
            }
         }

      }
   }

   public void setOutputStream(OutputStream os) {
      this.outputStream = os;
   }

   private byte[] getBytesFromInputStream() throws IOException {
      if (this.bytes != null) {
         return this.bytes;
      } else if (this.inputOctetStreamProxy == null) {
         return null;
      } else {
         try {
            this.bytes = JavaUtils.getBytesFromStream(this.inputOctetStreamProxy);
         } finally {
            this.inputOctetStreamProxy.close();
         }

         return this.bytes;
      }
   }

   public void addNodeFilter(NodeFilter filter) {
      if (this.isOctetStream()) {
         try {
            this.convertToNodes();
         } catch (Exception var3) {
            throw new XMLSecurityRuntimeException("signature.XMLSignatureInput.nodesetReference", var3);
         }
      }

      this.nodeFilters.add(filter);
   }

   public List getNodeFilters() {
      return this.nodeFilters;
   }

   public void setNodeSet(boolean b) {
      this.isNodeSet = b;
   }

   void convertToNodes() throws CanonicalizationException, ParserConfigurationException, IOException, SAXException {
      DocumentBuilder db = XMLUtils.createDocumentBuilder(false, this.secureValidation);

      try {
         db.setErrorHandler(new IgnoreAllErrorHandler());
         Document doc = db.parse(this.getOctetStream());
         this.subNode = doc;
      } catch (SAXException var32) {
         byte[] result = null;
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         Throwable var5 = null;

         byte[] result;
         try {
            baos.write("<container>".getBytes(StandardCharsets.UTF_8));
            baos.write(this.getBytes());
            baos.write("</container>".getBytes(StandardCharsets.UTF_8));
            result = baos.toByteArray();
         } catch (Throwable var30) {
            var5 = var30;
            throw var30;
         } finally {
            $closeResource(var5, baos);
         }

         ByteArrayInputStream is = new ByteArrayInputStream(result);
         var5 = null;

         try {
            Document document = db.parse(is);
            this.subNode = document.getDocumentElement().getFirstChild().getFirstChild();
         } catch (Throwable var28) {
            var5 = var28;
            throw var28;
         } finally {
            $closeResource(var5, is);
         }
      } finally {
         XMLUtils.repoolDocumentBuilder(db);
         if (this.inputOctetStreamProxy != null) {
            this.inputOctetStreamProxy.close();
         }

         this.inputOctetStreamProxy = null;
         this.bytes = null;
      }

   }

   public boolean isSecureValidation() {
      return this.secureValidation;
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }

   public String getPreCalculatedDigest() {
      return this.preCalculatedDigest;
   }

   // $FF: synthetic method
   private static void $closeResource(Throwable x0, AutoCloseable x1) {
      if (x0 != null) {
         try {
            x1.close();
         } catch (Throwable var3) {
            x0.addSuppressed(var3);
         }
      } else {
         x1.close();
      }

   }
}
