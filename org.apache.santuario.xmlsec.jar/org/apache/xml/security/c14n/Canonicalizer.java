package org.apache.xml.security.c14n;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xml.security.c14n.implementations.Canonicalizer11_OmitComments;
import org.apache.xml.security.c14n.implementations.Canonicalizer11_WithComments;
import org.apache.xml.security.c14n.implementations.Canonicalizer20010315ExclOmitComments;
import org.apache.xml.security.c14n.implementations.Canonicalizer20010315ExclWithComments;
import org.apache.xml.security.c14n.implementations.Canonicalizer20010315OmitComments;
import org.apache.xml.security.c14n.implementations.Canonicalizer20010315WithComments;
import org.apache.xml.security.c14n.implementations.CanonicalizerPhysical;
import org.apache.xml.security.exceptions.AlgorithmAlreadyRegisteredException;
import org.apache.xml.security.utils.ClassLoaderUtils;
import org.apache.xml.security.utils.IgnoreAllErrorHandler;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Canonicalizer {
   public static final String ENCODING;
   public static final String XPATH_C14N_WITH_COMMENTS_SINGLE_NODE = "(.//. | .//@* | .//namespace::*)";
   public static final String ALGO_ID_C14N_OMIT_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315";
   public static final String ALGO_ID_C14N_WITH_COMMENTS = "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments";
   public static final String ALGO_ID_C14N_EXCL_OMIT_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#";
   public static final String ALGO_ID_C14N_EXCL_WITH_COMMENTS = "http://www.w3.org/2001/10/xml-exc-c14n#WithComments";
   public static final String ALGO_ID_C14N11_OMIT_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11";
   public static final String ALGO_ID_C14N11_WITH_COMMENTS = "http://www.w3.org/2006/12/xml-c14n11#WithComments";
   public static final String ALGO_ID_C14N_PHYSICAL = "http://santuario.apache.org/c14n/physical";
   private static Map canonicalizerHash;
   private final CanonicalizerSpi canonicalizerSpi;
   private boolean secureValidation;

   private Canonicalizer(String algorithmURI) throws InvalidCanonicalizerException {
      try {
         Class implementingClass = (Class)canonicalizerHash.get(algorithmURI);
         this.canonicalizerSpi = (CanonicalizerSpi)implementingClass.newInstance();
         this.canonicalizerSpi.reset = true;
      } catch (Exception var4) {
         Object[] exArgs = new Object[]{algorithmURI};
         throw new InvalidCanonicalizerException(var4, "signature.Canonicalizer.UnknownCanonicalizer", exArgs);
      }
   }

   public static final Canonicalizer getInstance(String algorithmURI) throws InvalidCanonicalizerException {
      return new Canonicalizer(algorithmURI);
   }

   public static void register(String algorithmURI, String implementingClass) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException {
      JavaUtils.checkRegisterPermission();
      Class registeredClass = (Class)canonicalizerHash.get(algorithmURI);
      if (registeredClass != null) {
         Object[] exArgs = new Object[]{algorithmURI, registeredClass};
         throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
      } else {
         canonicalizerHash.put(algorithmURI, ClassLoaderUtils.loadClass(implementingClass, Canonicalizer.class));
      }
   }

   public static void register(String algorithmURI, Class implementingClass) throws AlgorithmAlreadyRegisteredException, ClassNotFoundException {
      JavaUtils.checkRegisterPermission();
      Class registeredClass = (Class)canonicalizerHash.get(algorithmURI);
      if (registeredClass != null) {
         Object[] exArgs = new Object[]{algorithmURI, registeredClass};
         throw new AlgorithmAlreadyRegisteredException("algorithm.alreadyRegistered", exArgs);
      } else {
         canonicalizerHash.put(algorithmURI, implementingClass);
      }
   }

   public static void registerDefaultAlgorithms() {
      canonicalizerHash.put("http://www.w3.org/TR/2001/REC-xml-c14n-20010315", Canonicalizer20010315OmitComments.class);
      canonicalizerHash.put("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments", Canonicalizer20010315WithComments.class);
      canonicalizerHash.put("http://www.w3.org/2001/10/xml-exc-c14n#", Canonicalizer20010315ExclOmitComments.class);
      canonicalizerHash.put("http://www.w3.org/2001/10/xml-exc-c14n#WithComments", Canonicalizer20010315ExclWithComments.class);
      canonicalizerHash.put("http://www.w3.org/2006/12/xml-c14n11", Canonicalizer11_OmitComments.class);
      canonicalizerHash.put("http://www.w3.org/2006/12/xml-c14n11#WithComments", Canonicalizer11_WithComments.class);
      canonicalizerHash.put("http://santuario.apache.org/c14n/physical", CanonicalizerPhysical.class);
   }

   public final String getURI() {
      return this.canonicalizerSpi.engineGetURI();
   }

   public boolean getIncludeComments() {
      return this.canonicalizerSpi.engineGetIncludeComments();
   }

   public byte[] canonicalize(byte[] inputBytes) throws ParserConfigurationException, IOException, SAXException, CanonicalizationException {
      Document document = null;
      InputStream bais = new ByteArrayInputStream(inputBytes);
      Throwable var4 = null;

      try {
         InputSource in = new InputSource(bais);
         DocumentBuilder db = XMLUtils.createDocumentBuilder(true, this.secureValidation);
         db.setErrorHandler(new IgnoreAllErrorHandler());

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

      return this.canonicalizeSubtree(document);
   }

   public byte[] canonicalizeSubtree(Node node) throws CanonicalizationException {
      this.canonicalizerSpi.secureValidation = this.secureValidation;
      return this.canonicalizerSpi.engineCanonicalizeSubTree(node);
   }

   public byte[] canonicalizeSubtree(Node node, String inclusiveNamespaces) throws CanonicalizationException {
      this.canonicalizerSpi.secureValidation = this.secureValidation;
      return this.canonicalizerSpi.engineCanonicalizeSubTree(node, inclusiveNamespaces);
   }

   public byte[] canonicalizeSubtree(Node node, String inclusiveNamespaces, boolean propagateDefaultNamespace) throws CanonicalizationException {
      this.canonicalizerSpi.secureValidation = this.secureValidation;
      return this.canonicalizerSpi.engineCanonicalizeSubTree(node, inclusiveNamespaces, propagateDefaultNamespace);
   }

   public byte[] canonicalizeXPathNodeSet(NodeList xpathNodeSet) throws CanonicalizationException {
      this.canonicalizerSpi.secureValidation = this.secureValidation;
      return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet);
   }

   public byte[] canonicalizeXPathNodeSet(NodeList xpathNodeSet, String inclusiveNamespaces) throws CanonicalizationException {
      this.canonicalizerSpi.secureValidation = this.secureValidation;
      return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet, inclusiveNamespaces);
   }

   public byte[] canonicalizeXPathNodeSet(Set xpathNodeSet) throws CanonicalizationException {
      this.canonicalizerSpi.secureValidation = this.secureValidation;
      return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet);
   }

   public byte[] canonicalizeXPathNodeSet(Set xpathNodeSet, String inclusiveNamespaces) throws CanonicalizationException {
      this.canonicalizerSpi.secureValidation = this.secureValidation;
      return this.canonicalizerSpi.engineCanonicalizeXPathNodeSet(xpathNodeSet, inclusiveNamespaces);
   }

   public void setWriter(OutputStream os) {
      this.canonicalizerSpi.setWriter(os);
   }

   public String getImplementingCanonicalizerClass() {
      return this.canonicalizerSpi.getClass().getName();
   }

   public void notReset() {
      this.canonicalizerSpi.reset = false;
   }

   public boolean isSecureValidation() {
      return this.secureValidation;
   }

   public void setSecureValidation(boolean secureValidation) {
      this.secureValidation = secureValidation;
   }

   static {
      ENCODING = StandardCharsets.UTF_8.name();
      canonicalizerHash = new ConcurrentHashMap();
   }
}
