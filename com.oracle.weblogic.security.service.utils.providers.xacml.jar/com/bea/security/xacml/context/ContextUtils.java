package com.bea.security.xacml.context;

import com.bea.common.security.xacml.DocumentParseException;
import com.bea.common.security.xacml.URISyntaxException;
import com.bea.common.security.xacml.attr.AttributeRegistry;
import com.bea.common.security.xacml.context.ContextSchemaObject;
import com.bea.common.security.xacml.context.Request;
import com.bea.common.security.xacml.context.Response;
import com.bea.security.xacml.IOException;
import java.io.InputStream;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class ContextUtils {
   private ContextUtils() {
   }

   private static Node getRootNode(InputStream str, DocumentBuilderFactory dbFactory) throws IOException, ParserConfigurationException, SAXException {
      try {
         dbFactory.setIgnoringComments(true);
         dbFactory.setNamespaceAware(true);
         dbFactory.setValidating(false);
         DocumentBuilder db = dbFactory.newDocumentBuilder();
         Document doc = db.parse(str);
         Element root = doc.getDocumentElement();
         return root;
      } catch (java.io.IOException var5) {
         throw new IOException(var5);
      }
   }

   private static Node getRootNode(String str, DocumentBuilderFactory dbFactory) throws IOException, ParserConfigurationException, SAXException {
      try {
         dbFactory.setIgnoringComments(true);
         dbFactory.setNamespaceAware(true);
         dbFactory.setValidating(false);
         DocumentBuilder db = dbFactory.newDocumentBuilder();
         Document doc = db.parse(new InputSource(new StringReader(str)));
         Element root = doc.getDocumentElement();
         return root;
      } catch (java.io.IOException var5) {
         throw new IOException(var5);
      }
   }

   public static ContextSchemaObject read(AttributeRegistry attrReg, InputStream data, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         Node root = getRootNode(data, dbFactory);
         if (root.getNodeName().equals("Request")) {
            return new Request(attrReg, root);
         } else if (root.getNodeName().equals("Response")) {
            return new Response(attrReg, root);
         } else {
            throw new DocumentParseException("Expecting Request or Response, but found: " + root.getNodeName());
         }
      } catch (ParserConfigurationException var4) {
         throw new DocumentParseException(var4);
      } catch (SAXException var5) {
         throw new DocumentParseException(var5);
      }
   }

   public static ContextSchemaObject read(AttributeRegistry attrReg, String data, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         Node root = getRootNode(data, dbFactory);
         if (root.getNodeName().equals("Request")) {
            return new Request(attrReg, root);
         } else if (root.getNodeName().equals("Response")) {
            return new Response(attrReg, root);
         } else {
            throw new DocumentParseException("Expecting Request or Response, but found: " + root.getNodeName());
         }
      } catch (ParserConfigurationException var4) {
         throw new DocumentParseException(var4);
      } catch (SAXException var5) {
         throw new DocumentParseException(var5);
      }
   }

   public static Request readRequest(AttributeRegistry attrReg, InputStream request, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return new Request(attrReg, getRootNode(request, dbFactory));
      } catch (ParserConfigurationException var4) {
         throw new DocumentParseException(var4);
      } catch (SAXException var5) {
         throw new DocumentParseException(var5);
      }
   }

   public static Request readRequest(AttributeRegistry attrReg, String request, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return new Request(attrReg, getRootNode(request, dbFactory));
      } catch (ParserConfigurationException var4) {
         throw new DocumentParseException(var4);
      } catch (SAXException var5) {
         throw new DocumentParseException(var5);
      }
   }

   public static Response readResponse(AttributeRegistry attrReg, InputStream response, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return new Response(attrReg, getRootNode(response, dbFactory));
      } catch (ParserConfigurationException var4) {
         throw new DocumentParseException(var4);
      } catch (SAXException var5) {
         throw new DocumentParseException(var5);
      }
   }

   public static Response readResponse(AttributeRegistry attrReg, String response, DocumentBuilderFactory dbFactory) throws URISyntaxException, DocumentParseException, IOException {
      try {
         return new Response(attrReg, getRootNode(response, dbFactory));
      } catch (ParserConfigurationException var4) {
         throw new DocumentParseException(var4);
      } catch (SAXException var5) {
         throw new DocumentParseException(var5);
      }
   }
}
