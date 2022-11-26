package com.bea.common.security.xacml;

import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.policy.AbstractPolicy;
import com.bea.common.security.xacml.policy.Policy;
import com.bea.common.security.xacml.policy.PolicySet;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import weblogic.utils.XXEUtils;

public class PolicyUtils {
   private static final String DISALLOW_DOCTYPE_DECL = "http://apache.org/xml/features/disallow-doctype-decl";
   private static Validator XACML_SCHEMA_VALIDATOR;

   private PolicyUtils() {
   }

   private static Validator getValidator() throws SAXException {
      if (XACML_SCHEMA_VALIDATOR == null) {
         SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
         Schema schema = sf.newSchema(new StreamSource(PolicyUtils.class.getClassLoader().getResourceAsStream("com/bea/common/security/xacml/access_control-xacml-2.0-policy-schema-os.xsd")));
         XACML_SCHEMA_VALIDATOR = XXEUtils.createValidator(schema);
      }

      return XACML_SCHEMA_VALIDATOR;
   }

   public static void checkXACMLSchema(String policyString) throws DocumentParseException, IOException {
      try {
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         dbFactory.setIgnoringComments(true);
         dbFactory.setNamespaceAware(true);
         dbFactory.setValidating(false);
         dbFactory.setExpandEntityReferences(false);
         dbFactory.setXIncludeAware(false);
         dbFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
         dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
         DocumentBuilder db = dbFactory.newDocumentBuilder();
         Document doc = db.parse(new InputSource(new StringReader(policyString)));
         getValidator().validate(new DOMSource(doc));
      } catch (ParserConfigurationException var4) {
         throw new DocumentParseException(var4);
      } catch (SAXException var5) {
         throw new DocumentParseException(var5);
      } catch (java.io.IOException var6) {
         throw new IOException(var6);
      }
   }

   private static Node getRootNode(InputStream str, DocumentBuilderFactory dbFactory, boolean checkSchema) throws IOException, ParserConfigurationException, SAXException {
      try {
         dbFactory.setIgnoringComments(true);
         dbFactory.setNamespaceAware(true);
         dbFactory.setValidating(false);
         dbFactory.setExpandEntityReferences(false);
         dbFactory.setXIncludeAware(false);
         dbFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
         dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
         DocumentBuilder db = dbFactory.newDocumentBuilder();
         Document doc = db.parse(str);
         if (checkSchema) {
            getValidator().validate(new DOMSource(doc));
         }

         Element root = doc.getDocumentElement();
         return root;
      } catch (java.io.IOException var6) {
         throw new IOException(var6);
      }
   }

   private static Node getRootNode(String str, DocumentBuilderFactory dbFactory, boolean checkSchema) throws IOException, ParserConfigurationException, SAXException {
      try {
         dbFactory.setIgnoringComments(true);
         dbFactory.setNamespaceAware(true);
         dbFactory.setValidating(false);
         dbFactory.setExpandEntityReferences(false);
         dbFactory.setXIncludeAware(false);
         dbFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
         dbFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
         DocumentBuilder db = dbFactory.newDocumentBuilder();
         Document doc = db.parse(new InputSource(new StringReader(str)));
         if (checkSchema) {
            getValidator().validate(new DOMSource(doc));
         }

         Element root = doc.getDocumentElement();
         return root;
      } catch (java.io.IOException var6) {
         throw new IOException(var6);
      }
   }

   public static AbstractPolicy read(AttributeRegistry attrReg, InputStream data, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      return read(attrReg, data, dbFactory, false);
   }

   public static AbstractPolicy read(AttributeRegistry attrReg, InputStream data, DocumentBuilderFactory dbFactory, boolean checkSchema) throws URISyntaxException, DocumentParseException, IOException {
      try {
         Node root = getRootNode(data, dbFactory, checkSchema);
         if (root.getNodeName().equals("Policy")) {
            return new Policy(attrReg, root);
         } else if (root.getNodeName().equals("PolicySet")) {
            return new PolicySet(attrReg, root);
         } else {
            throw new DocumentParseException("Expecting Policy or PolicySet, but found: " + root.getNodeName());
         }
      } catch (ParserConfigurationException var5) {
         throw new DocumentParseException(var5);
      } catch (SAXException var6) {
         throw new DocumentParseException(var6);
      }
   }

   public static AbstractPolicy read(AttributeRegistry attrReg, String data, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      return read(attrReg, data, dbFactory, false);
   }

   public static AbstractPolicy read(AttributeRegistry attrReg, String data, DocumentBuilderFactory dbFactory, boolean checkSchema) throws URISyntaxException, DocumentParseException, IOException {
      try {
         Node root = getRootNode(data, dbFactory, checkSchema);
         if (root.getNodeName().equals("Policy")) {
            return new Policy(attrReg, root);
         } else if (root.getNodeName().equals("PolicySet")) {
            return new PolicySet(attrReg, root);
         } else {
            throw new DocumentParseException("Expecting Policy or PolicySet, but found: " + root.getNodeName());
         }
      } catch (ParserConfigurationException var5) {
         throw new DocumentParseException(var5);
      } catch (SAXException var6) {
         throw new DocumentParseException(var6);
      }
   }

   public static Policy readPolicy(AttributeRegistry attrReg, InputStream policy, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      return readPolicy(attrReg, policy, dbFactory, false);
   }

   public static Policy readPolicy(AttributeRegistry attrReg, InputStream policy, DocumentBuilderFactory dbFactory, boolean checkSchema) throws URISyntaxException, DocumentParseException, IOException {
      try {
         Node root = getRootNode(policy, dbFactory, checkSchema);
         if (!root.getNodeName().equals("Policy")) {
            throw new DocumentParseException("The given policy is not a valid XACML policy.");
         } else {
            return new Policy(attrReg, root);
         }
      } catch (ParserConfigurationException var5) {
         throw new DocumentParseException(var5);
      } catch (SAXException var6) {
         throw new DocumentParseException(var6);
      }
   }

   public static Policy readPolicy(AttributeRegistry attrReg, String policy, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      return readPolicy(attrReg, policy, dbFactory, false);
   }

   public static Policy readPolicy(AttributeRegistry attrReg, String policy, DocumentBuilderFactory dbFactory, boolean checkSchema) throws URISyntaxException, DocumentParseException, IOException {
      try {
         Node root = getRootNode(policy, dbFactory, checkSchema);
         if (!root.getNodeName().equals("Policy")) {
            throw new DocumentParseException("The given policy is not a valid XACML policy.");
         } else {
            return new Policy(attrReg, root);
         }
      } catch (ParserConfigurationException var5) {
         throw new DocumentParseException(var5);
      } catch (SAXException var6) {
         throw new DocumentParseException(var6);
      }
   }

   public static PolicySet readPolicySet(AttributeRegistry attrReg, InputStream policySet, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException {
      return readPolicySet(attrReg, policySet, dbFactory, false);
   }

   public static PolicySet readPolicySet(AttributeRegistry attrReg, InputStream policySet, DocumentBuilderFactory dbFactory, boolean checkSchema) throws URISyntaxException, DocumentParseException {
      try {
         Node root = getRootNode(policySet, dbFactory, checkSchema);
         if (!root.getNodeName().equals("PolicySet")) {
            throw new DocumentParseException("The given policy set is not a valid XACML policy set.");
         } else {
            return new PolicySet(attrReg, root);
         }
      } catch (IOException var5) {
         throw new DocumentParseException(var5);
      } catch (ParserConfigurationException var6) {
         throw new DocumentParseException(var6);
      } catch (SAXException var7) {
         throw new DocumentParseException(var7);
      }
   }

   public static PolicySet readPolicySet(AttributeRegistry attrReg, String policySet, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException {
      return readPolicySet(attrReg, policySet, dbFactory, false);
   }

   public static PolicySet readPolicySet(AttributeRegistry attrReg, String policySet, DocumentBuilderFactory dbFactory, boolean checkSchema) throws URISyntaxException, DocumentParseException {
      try {
         Node root = getRootNode(policySet, dbFactory, checkSchema);
         if (!root.getNodeName().equals("PolicySet")) {
            throw new DocumentParseException("The given policy set is not a valid XACML policy set.");
         } else {
            return new PolicySet(attrReg, root);
         }
      } catch (IOException var5) {
         throw new DocumentParseException(var5);
      } catch (ParserConfigurationException var6) {
         throw new DocumentParseException(var6);
      } catch (SAXException var7) {
         throw new DocumentParseException(var7);
      }
   }
}
