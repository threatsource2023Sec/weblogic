package org.opensaml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import weblogic.net.http.HttpURLConnection;
import weblogic.net.http.HttpsURLConnection;

public class SAMLSOAPBinding implements SAMLBinding {
   private static final DebugLogger LOGGER = SAMLServicesHelper.getDebugLogger();
   protected SAMLConfig config = SAMLConfig.instance();

   private static final void logDebug(String var0) {
      if (LOGGER != null && LOGGER.isDebugEnabled()) {
         LOGGER.debug("SAMLSOAPBinding: " + var0);
      }

   }

   public SAMLResponse send(SAMLAuthorityBinding var1, SAMLRequest var2) throws SAMLException {
      return this.send(var2, var1.getLocation(), (PrivateKey)null, (Certificate[])null, (String)null);
   }

   public SAMLResponse send(SAMLRequest var1, String var2, PrivateKey var3, Certificate[] var4, String var5) throws SAMLException {
      try {
         Document var6 = var1.toDOM().getOwnerDocument();
         Element var7 = var6.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "Envelope");
         var7.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns", "http://schemas.xmlsoap.org/soap/envelope/");
         Element var8 = var6.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "Body");
         var7.appendChild(var8);
         var8.appendChild(var1.toDOM());
         if (var6.getDocumentElement() == null) {
            var6.appendChild(var7);
         } else {
            var6.replaceChild(var7, var6.getDocumentElement());
         }

         logDebug("send(): Connecting to SAML authority at " + var2);
         Object var9 = null;
         if (var2.startsWith("https:")) {
            logDebug("send(): Instantiating HTTPS connection");
            HttpsURLConnection var10 = new HttpsURLConnection(new URL(var2));
            this.dumpSSLParams(var3, var4);
            if (var3 != null && var4 != null) {
               logDebug("send(): Have certs and key, loading SSL identity");
               var10.loadLocalIdentity(var4, var3);
            } else {
               logDebug("send(): Missing certs or key, not loading SSL identity");
            }

            var9 = var10;
         } else {
            logDebug("send(): Instantiating HTTP connection");
            var9 = new HttpURLConnection(new URL(var2));
         }

         ((URLConnection)var9).setAllowUserInteraction(false);
         ((URLConnection)var9).setDoOutput(true);
         ((HttpURLConnection)var9).setInstanceFollowRedirects(false);
         ((HttpURLConnection)var9).setRequestMethod("POST");
         ((HttpURLConnection)var9).setRequestProperty("Content-Type", "text/xml; charset=UTF-8");
         ((HttpURLConnection)var9).setRequestProperty("SOAPAction", "http://www.oasis-open.org/committees/security");
         if (var5 != null) {
            ((HttpURLConnection)var9).setRequestProperty("Authorization", "Basic " + var5);
         }

         ((URLConnection)var9).connect();

         try {
            XML.outputNode(var7, ((URLConnection)var9).getOutputStream());
         } catch (IOException var15) {
            logDebug("send(): Exception while sending/receiving: " + var15.toString());
            throw var15;
         }

         int var19 = ((HttpURLConnection)var9).getResponseCode();
         if (var19 == -1) {
            throw new SAMLException("SAMLSOAPBinding.send(): Invalid response from server");
         } else {
            String var11;
            if (var19 != 200) {
               var11 = ((HttpURLConnection)var9).getResponseMessage();
               throw new SAMLException("SAMLSOAPBinding.send(): Error response from server: '" + var19 + " " + var11 + "'");
            } else {
               var11 = ((HttpURLConnection)var9).getContentType();
               if (var11 != null && var11.startsWith("text/xml")) {
                  var7 = XML.parserPool.parse(((URLConnection)var9).getInputStream()).getDocumentElement();
                  if (!XML.isElementNamed(var7, "http://schemas.xmlsoap.org/soap/envelope/", "Envelope")) {
                     throw new MalformedException(SAMLException.RESPONDER, "SAMLSOAPBinding::send() detected an incompatible or missing SOAP envelope");
                  } else {
                     Element var20 = XML.getFirstChildElement(var7);
                     if (XML.isElementNamed(var20, "http://schemas.xmlsoap.org/soap/envelope/", "Header")) {
                        for(Node var13 = var20.getFirstChild(); var13 != null; var13 = var13.getNextSibling()) {
                           if (var13.getNodeType() == 1 && ((Element)var13).getAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand") != null && ((Element)var13).getAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand").equals("1")) {
                              throw new SOAPException(SAMLException.RESPONDER, "SAMLSOAPBinding::send() detected a mandatory SOAP header");
                           }
                        }

                        var20 = XML.getNextSiblingElement(var20);
                     }

                     if (var20 != null) {
                        var20 = XML.getFirstChildElement(var20);
                        if (var20 != null) {
                           if (var20.getNodeType() == 1 && XML.isElementNamed(var20, "http://schemas.xmlsoap.org/soap/envelope/", "Fault")) {
                              NodeList var22 = var20.getElementsByTagNameNS((String)null, "faultstring");
                              String var14;
                              if (var22 != null && var22.getLength() > 0) {
                                 var14 = var22.item(0).getFirstChild().getNodeValue();
                              } else {
                                 var14 = "SAMLSOAPBinding::send() detected a SOAP fault";
                              }

                              var22 = var20.getElementsByTagNameNS((String)null, "faultstring");
                              if (var22 != null && var22.getLength() > 0) {
                                 throw new SOAPException(QName.getQNameTextNode((Text)var22.item(0).getFirstChild()), var14);
                              }

                              throw new SOAPException(SOAPException.SERVER, var14);
                           }

                           SAMLResponse var21 = new SAMLResponse(var20);
                           if (!var21.getInResponseTo().equals(var1.getId())) {
                              throw new BindingException("SAMLSOAPBinding.send() unable to match SAML InResponseTo value to request");
                           }

                           return var21;
                        }
                     }

                     throw new SOAPException(SOAPException.SERVER, "SAMLSOAPBinding::send() unable to find a SAML response or fault in SOAP body");
                  }
               } else {
                  logDebug("send(): Received an invalid content type in the response (" + var11 + "), with the following content:");
                  BufferedReader var12 = new BufferedReader(new InputStreamReader(((URLConnection)var9).getInputStream()));
                  logDebug(var12.readLine());
                  throw new BindingException(SAMLException.RESPONDER, "send() detected an invalid content type in the response: " + var11);
               }
            }
         }
      } catch (MalformedURLException var16) {
         throw new SAMLException("SAMLSOAPBinding.send() detected a malformed URL in the binding provided", var16);
      } catch (SAXException var17) {
         throw new SAMLException("SAMLSOAPBinding.send() caught an XML exception while parsing the response", var17);
      } catch (IOException var18) {
         throw new SAMLException("SAMLSOAPBinding.send() caught an I/O exception", var18);
      }
   }

   private void dumpSSLParams(PrivateKey var1, Certificate[] var2) {
      if (LOGGER.isDebugEnabled()) {
         logDebug("Private key is " + (var1 == null ? "null" : "not null"));
         if (var2 == null) {
            logDebug("Certificate array is null");
         } else {
            logDebug("There are " + var2.length + " certificates");

            for(int var3 = 0; var3 < var2.length; ++var3) {
               if (var2[var3] != null && var2[var3] instanceof X509Certificate) {
                  String var4 = ((X509Certificate)var2[var3]).getSubjectDN().getName();
                  String var5 = ((X509Certificate)var2[var3]).getIssuerDN().getName();
                  logDebug("Certificate[" + var3 + "]: Subject: " + var4 + ", Issuer: " + var5);
               } else if (var2[var3] != null) {
                  logDebug("Certificate[" + var3 + "]: Not an X509Certificate");
               } else {
                  logDebug("Certificate[" + var3 + "]: (null)");
               }
            }

         }
      }
   }

   /** @deprecated */
   public SAMLRequest receive(Object var1, StringBuffer var2) throws SAMLException {
      var2.setLength(0);
      HttpServletRequest var3 = (HttpServletRequest)var1;
      X509Certificate[] var4 = (X509Certificate[])((X509Certificate[])var3.getAttribute("javax.servlet.request.X509Certificate"));
      if (var4 != null && var4.length > 0) {
         StringTokenizer var5 = new StringTokenizer(var4[0].getSubjectDN().getName(), ", ");

         while(var5.hasMoreTokens()) {
            String var6 = var5.nextToken();
            if (var6.startsWith("CN=")) {
               var2.append(var6.substring(3));
               break;
            }
         }

         logDebug("Requester name: " + var2);
      } else {
         logDebug("No Requester name available.");
      }

      return this.receive(var1);
   }

   public SAMLRequest receive(Object var1) throws SAMLException {
      HttpServletRequest var2 = (HttpServletRequest)var1;
      if (var2.getMethod().equals("POST") && var2.getContentType().startsWith("text/xml")) {
         try {
            Document var3 = XML.parserPool.parse((InputStream)var2.getInputStream());
            Element var4 = var3.getDocumentElement();
            if (!XML.isElementNamed(var4, "http://schemas.xmlsoap.org/soap/envelope/", "Envelope")) {
               throw new SOAPException(SOAPException.VERSION, "SAMLSOAPBinding.receive() detected an incompatible or missing SOAP envelope");
            } else {
               Node var5;
               for(var5 = var4.getFirstChild(); var5 != null && var5.getNodeType() != 1; var5 = var5.getNextSibling()) {
               }

               if (XML.isElementNamed((Element)var5, "http://schemas.xmlsoap.org/soap/envelope/", "Header")) {
                  for(Node var6 = var5.getFirstChild(); var6 != null; var6 = var6.getNextSibling()) {
                     if (var6.getNodeType() == 1 && ((Element)var6).getAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand").equals("1")) {
                        throw new SOAPException(SOAPException.MUSTUNDERSTAND, "SAMLSOAPBinding.receive() detected a mandatory SOAP header");
                     }
                  }

                  for(var5 = var5.getNextSibling(); var5 != null && var5.getNodeType() != 1; var5 = var5.getNextSibling()) {
                  }
               }

               if (var5 != null) {
                  for(var5 = var5.getFirstChild(); var5 != null && var5.getNodeType() != 1; var5 = var5.getNextSibling()) {
                  }
               }

               return new SAMLRequest((Element)var5);
            }
         } catch (SAXException var7) {
            throw new SOAPException(SOAPException.CLIENT, "SAMLSOAPBinding.receive() detected an XML parsing error: " + var7.getMessage());
         } catch (IOException var8) {
            throw new SOAPException(SOAPException.SERVER, "SAMLSOAPBinding.receive() detected an I/O error: " + var8.getMessage());
         }
      } else {
         throw new BindingException(SAMLException.REQUESTER, "SAMLSOAPBinding.receive() found a bad HTTP method or content type");
      }
   }

   public void respond(Object var1, SAMLResponse var2, SAMLException var3) throws IOException {
      HttpServletResponse var4 = (HttpServletResponse)var1;

      try {
         Document var5 = var3 == null ? var2.toDOM().getOwnerDocument() : XML.parserPool.newDocument();
         Element var6 = var5.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soap:Envelope");
         var6.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:soap", "http://schemas.xmlsoap.org/soap/envelope/");
         var6.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsd", "http://www.w3.org/2001/XMLSchema");
         var6.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
         if (var5.getDocumentElement() == null) {
            var5.appendChild(var6);
         } else {
            var5.replaceChild(var6, var5.getDocumentElement());
         }

         Element var7 = var5.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soap:Body");
         var6.appendChild(var7);
         if (var3 != null) {
            Element var8 = var5.createElementNS("http://schemas.xmlsoap.org/soap/envelope/", "soap:Fault");
            var7.appendChild(var8);
            Element var9 = var5.createElementNS((String)null, "faultcode");
            if (var3 instanceof SOAPException) {
               Iterator var10 = var3.getCodes();
               if (var10.hasNext()) {
                  var9.appendChild(var5.createTextNode("soap:" + ((QName)var10.next()).getLocalName()));
               } else {
                  var9.appendChild(var5.createTextNode("soap:" + SOAPException.SERVER.getLocalName()));
               }
            } else {
               var9.appendChild(var5.createTextNode("soap:" + SOAPException.SERVER.getLocalName()));
            }

            var8.appendChild(var9);
            var9 = var5.createElementNS((String)null, "faultstring");
            var8.appendChild(var9).appendChild(var5.createTextNode(var3.getMessage()));
            var4.setStatus(500);
            XML.outputNode(var6, var4.getOutputStream());
            return;
         }

         var7.appendChild(var2.toDOM());
         var4.setContentType("text/xml; charset=UTF-8");
         XML.outputNode(var6, var4.getOutputStream());
      } catch (Exception var11) {
         var11.printStackTrace();
         var4.sendError(500, "SAMLSOAPBinding.respond() caught an unexpected exception: " + var11.getClass().getName() + " " + var11.getMessage());
      }

   }
}
