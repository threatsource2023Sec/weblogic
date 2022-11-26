package com.bea.security.saml2.binding.impl;

import com.bea.common.security.utils.SAML2ClassLoader;
import com.bea.security.saml2.binding.BindingHandlerException;
import com.bea.security.saml2.config.SAML2ConfigSpi;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import net.shibboleth.utilities.java.support.xml.ParserPool;
import net.shibboleth.utilities.java.support.xml.XMLParserException;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.Marshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.io.Unmarshaller;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.soap.soap11.Body;
import org.opensaml.soap.soap11.Envelope;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import weblogic.security.providers.utils.Utils;

class BindingUtil {
   private static final int DEFAULT_BUFFER_SIZE = 10240;
   public static final String DEFAULT_ENCODING = "UTF-8";

   public static Element marshallXMLObject(XMLObject xmlObject) throws MarshallingException {
      Marshaller marshaller = XMLObjectSupport.getMarshaller(xmlObject);
      return marshaller.marshall(xmlObject);
   }

   public static XMLObject unmarshall(byte[] xml) throws XMLParserException, UnmarshallingException {
      return unmarshall(new InputSource(new ByteArrayInputStream(xml)));
   }

   public static XMLObject unmarshall(InputStream instream) throws XMLParserException, UnmarshallingException {
      return unmarshall(new InputSource(instream));
   }

   public static XMLObject unmarshall(InputSource in) throws XMLParserException, UnmarshallingException {
      ParserPool ppMgr = XMLObjectProviderRegistrySupport.getParserPool();
      Document doc = ppMgr.parse(in.getByteStream());
      Unmarshaller unmarshaller = XMLObjectSupport.getUnmarshaller(doc.getDocumentElement());
      if (unmarshaller == null) {
         throw new UnmarshallingException("Can't get unmarshaller for the document. rootelement.name=" + doc.getDocumentElement().getLocalName());
      } else {
         return unmarshaller.unmarshall(doc.getDocumentElement());
      }
   }

   public static Envelope buildEnvelope(XMLObject object) {
      Envelope envelope = (Envelope)XMLObjectSupport.buildXMLObject(Envelope.DEFAULT_ELEMENT_NAME);
      Body body = (Body)XMLObjectSupport.buildXMLObject(Body.DEFAULT_ELEMENT_NAME);
      envelope.setBody(body);
      body.getUnknownXMLObjects().add(object);
      return envelope;
   }

   public static void setHttpHeaders(HttpServletResponse resp) {
      resp.setHeader("Cache-Control", "no-cache, no-store");
      resp.setHeader("Pragma", "no-cache");
   }

   public static byte[] transformNode(SAML2ConfigSpi config, Node e) throws TransformerException {
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer trans = factory.newTransformer();
      ByteArrayOutputStream out = new ByteArrayOutputStream(10240);
      trans.transform(new DOMSource(e), new StreamResult(out));
      return out.toByteArray();
   }

   public static String urlDecode(String inputURL) throws UnsupportedEncodingException {
      return urlDecode(inputURL, "UTF-8");
   }

   public static String urlDecode(String inputURL, String encoding) throws UnsupportedEncodingException {
      return URLDecoder.decode(inputURL, encoding);
   }

   public static byte[] deflateDecode(byte[] depression) throws DataFormatException {
      Inflater inflater = new Inflater(true);
      inflater.setInput(depression);
      ByteArrayOutputStream bos = new ByteArrayOutputStream(depression.length);
      byte[] buf = new byte[10240];

      while(!inflater.finished() && inflater.getRemaining() > 0) {
         int compressedLength = inflater.inflate(buf);
         bos.write(buf, 0, compressedLength);
      }

      inflater.end();
      return bos.toByteArray();
   }

   public static byte[] deflateEncode(byte[] toBeDeflate) {
      Deflater deflater = new Deflater(-1, true);
      deflater.setInput(toBeDeflate);
      deflater.finish();
      ByteArrayOutputStream bos = new ByteArrayOutputStream(toBeDeflate.length);
      byte[] buf = new byte[10240];

      while(!deflater.finished()) {
         int compressLength = deflater.deflate(buf);
         bos.write(buf, 0, compressLength);
      }

      deflater.end();
      return bos.toByteArray();
   }

   public static String xmlSigAlgoToSigAlgo(String xmlSigAlgo) {
      if (xmlSigAlgo != null && !xmlSigAlgo.equals("")) {
         if (xmlSigAlgo.trim().equals("http://www.w3.org/2009/xmldsig11#dsa-sha256")) {
            return "SHA256withDSA";
         } else if (xmlSigAlgo.trim().equals("http://www.w3.org/2001/04/xmldsig-more#rsa-sha256")) {
            return "SHA256withRSA";
         } else if (xmlSigAlgo.trim().equals("http://www.w3.org/2000/09/xmldsig#dsa-sha1")) {
            return "SHA1withDSA";
         } else {
            return xmlSigAlgo.trim().equals("http://www.w3.org/2000/09/xmldsig#rsa-sha1") ? "SHA1withRSA" : null;
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   public static String getSamlSignAlgorithm(SignableSAMLObject samlObj) {
      return samlObj.isSigned() ? samlObj.getSignature().getSignatureAlgorithm() : null;
   }

   public static void outputPostForm(HttpServletResponse resp, Hashtable params, String url) throws IOException {
      resp.setContentType("text/html");
      setHttpHeaders(resp);
      PrintWriter out = resp.getWriter();
      out.println("<HTML><HEAD></HEAD>");
      out.println("<BODY onLoad=\"document.forms[0].submit();\">");
      out.println("<noscript><p>Please press the Continue button to proceed.</p></noscript>");
      out.println("<FORM METHOD=\"POST\" ACTION=\"" + url + "\">");
      if (params != null && params.size() > 0) {
         Enumeration keys = params.keys();

         while(keys.hasMoreElements()) {
            String key = (String)keys.nextElement();
            String value = (String)params.get(key);
            out.println("<INPUT TYPE=\"HIDDEN\" NAME=\"" + key + "\" VALUE=\"" + Utils.escapeFormValue(value) + "\" />");
         }
      }

      out.println("<noscript><input type=\"submit\" value=\"Continue\" /></noscript>");
      out.println("</FORM></BODY></HTML>");
   }

   public static void sendPostForm(HttpServletRequest req, HttpServletResponse resp, String postformTemplate, String destinationURL, String relayState, String stringOfSamlObject, String name) throws BindingHandlerException {
      if (postformTemplate != null && postformTemplate.trim().length() != 0) {
         RequestDispatcher templateDispatcher = resolveCustomPostFormDispatcher(req, postformTemplate);
         if (templateDispatcher == null) {
            throw new BindingHandlerException("failed to resolve custom post form: " + postformTemplate, 500);
         }

         req.setAttribute("com.bea.security.saml2.destination", destinationURL);
         req.setAttribute("com.bea.security.saml2.relayState", relayState);
         req.setAttribute("com.bea.security.saml2.samlContent", stringOfSamlObject);

         try {
            templateDispatcher.forward(req, resp);
         } catch (Exception var9) {
            throw new BindingHandlerException(500);
         }
      } else {
         try {
            Hashtable params = new Hashtable();
            if (relayState != null) {
               params.put("RelayState", relayState);
            }

            params.put(name, stringOfSamlObject);
            outputPostForm(resp, params, destinationURL);
         } catch (IOException var10) {
            throw new BindingHandlerException("", var10, 500);
         }
      }

   }

   private static RequestDispatcher resolveCustomPostFormDispatcher(HttpServletRequest req, String postformTemplate) {
      Thread t = Thread.currentThread();
      ClassLoader tcl = t.getContextClassLoader();
      ServletContext reqCtx = null;

      try {
         if (tcl instanceof SAML2ClassLoader) {
            t.setContextClassLoader(((SAML2ClassLoader)tcl).getThreadConextClassLoader());
         }

         HttpSession session = req.getSession();
         reqCtx = session.getServletContext();
      } finally {
         t.setContextClassLoader(tcl);
      }

      ServletContext formCtx = reqCtx.getContext(postformTemplate);
      if (formCtx == null) {
         return null;
      } else {
         String contextPath = null;
         RequestDispatcher templateDispatcher = null;

         try {
            ServletContext.class.getMethod("getContextPath", (Class[])null);
            contextPath = formCtx.getContextPath();
            if (contextPath == null || contextPath.length() == 0) {
               contextPath = "/";
            }

            templateDispatcher = formCtx.getRequestDispatcher(postformTemplate.substring(contextPath.length()));
         } catch (SecurityException var13) {
         } catch (NoSuchMethodException var14) {
         }

         if (templateDispatcher == null) {
            int secondSlashPosition = postformTemplate.indexOf("/", 1);
            if (secondSlashPosition == -1) {
               return null;
            }

            String dispatcherPath = postformTemplate.substring(secondSlashPosition);
            templateDispatcher = formCtx.getRequestDispatcher(dispatcherPath);
         }

         return templateDispatcher;
      }
   }
}
