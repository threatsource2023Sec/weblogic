package org.apache.jcp.xml.dsig.internal.dom;

import java.security.AccessController;
import java.security.InvalidParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.ProviderException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class XMLDSigRI extends Provider {
   static final long serialVersionUID = -5049765099299494554L;
   private static final String INFO = "Apache Santuario XMLDSig (DOM XMLSignatureFactory; DOM KeyInfoFactory; C14N 1.0, C14N 1.1, Exclusive C14N, Base64, Enveloped, XPath, XPath2, XSLT TransformServices)";

   public XMLDSigRI() {
      super("ApacheXMLDSig", 2.13, "Apache Santuario XMLDSig (DOM XMLSignatureFactory; DOM KeyInfoFactory; C14N 1.0, C14N 1.1, Exclusive C14N, Base64, Enveloped, XPath, XPath2, XSLT TransformServices)");
      AccessController.doPrivileged(new PrivilegedAction() {
         public Void run() {
            HashMap MECH_TYPE = new HashMap();
            MECH_TYPE.put("MechanismType", "DOM");
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "XMLSignatureFactory", "DOM", "org.apache.jcp.xml.dsig.internal.dom.DOMXMLSignatureFactory"));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "KeyInfoFactory", "DOM", "org.apache.jcp.xml.dsig.internal.dom.DOMKeyInfoFactory"));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315", "org.apache.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14NMethod", new String[]{"INCLUSIVE"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments", "org.apache.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14NMethod", new String[]{"INCLUSIVE_WITH_COMMENTS"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/2006/12/xml-c14n11", "org.apache.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14N11Method", (String[])null, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/2006/12/xml-c14n11#WithComments", "org.apache.jcp.xml.dsig.internal.dom.DOMCanonicalXMLC14N11Method", (String[])null, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/2001/10/xml-exc-c14n#", "org.apache.jcp.xml.dsig.internal.dom.DOMExcC14NMethod", new String[]{"EXCLUSIVE"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/2001/10/xml-exc-c14n#WithComments", "org.apache.jcp.xml.dsig.internal.dom.DOMExcC14NMethod", new String[]{"EXCLUSIVE_WITH_COMMENTS"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/2000/09/xmldsig#base64", "org.apache.jcp.xml.dsig.internal.dom.DOMBase64Transform", new String[]{"BASE64"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/2000/09/xmldsig#enveloped-signature", "org.apache.jcp.xml.dsig.internal.dom.DOMEnvelopedTransform", new String[]{"ENVELOPED"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/2002/06/xmldsig-filter2", "org.apache.jcp.xml.dsig.internal.dom.DOMXPathFilter2Transform", new String[]{"XPATH2"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/TR/1999/REC-xpath-19991116", "org.apache.jcp.xml.dsig.internal.dom.DOMXPathTransform", new String[]{"XPATH"}, MECH_TYPE));
            XMLDSigRI.this.putService(new ProviderService(XMLDSigRI.this, "TransformService", "http://www.w3.org/TR/1999/REC-xslt-19991116", "org.apache.jcp.xml.dsig.internal.dom.DOMXSLTTransform", new String[]{"XSLT"}, MECH_TYPE));
            return null;
         }
      });
   }

   private static final class ProviderService extends Provider.Service {
      ProviderService(Provider p, String type, String algo, String cn) {
         super(p, type, algo, cn, (List)null, (Map)null);
      }

      ProviderService(Provider p, String type, String algo, String cn, String[] aliases) {
         super(p, type, algo, cn, aliases == null ? null : Arrays.asList(aliases), (Map)null);
      }

      ProviderService(Provider p, String type, String algo, String cn, String[] aliases, HashMap attrs) {
         super(p, type, algo, cn, aliases == null ? null : Arrays.asList(aliases), attrs);
      }

      public Object newInstance(Object ctrParamObj) throws NoSuchAlgorithmException {
         String type = this.getType();
         if (ctrParamObj != null) {
            throw new InvalidParameterException("constructorParameter not used with " + type + " engines");
         } else {
            String algo = this.getAlgorithm();

            try {
               if (type.equals("XMLSignatureFactory")) {
                  if (algo.equals("DOM")) {
                     return new DOMXMLSignatureFactory();
                  }
               } else if (type.equals("KeyInfoFactory")) {
                  if (algo.equals("DOM")) {
                     return new DOMKeyInfoFactory();
                  }
               } else if (type.equals("TransformService")) {
                  if (!algo.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315") && !algo.equals("http://www.w3.org/TR/2001/REC-xml-c14n-20010315#WithComments")) {
                     if (!algo.equals("http://www.w3.org/2006/12/xml-c14n11") && !algo.equals("http://www.w3.org/2006/12/xml-c14n11#WithComments")) {
                        if (!algo.equals("http://www.w3.org/2001/10/xml-exc-c14n#") && !algo.equals("http://www.w3.org/2001/10/xml-exc-c14n#WithComments")) {
                           if (algo.equals("http://www.w3.org/2000/09/xmldsig#base64")) {
                              return new DOMBase64Transform();
                           }

                           if (algo.equals("http://www.w3.org/2000/09/xmldsig#enveloped-signature")) {
                              return new DOMEnvelopedTransform();
                           }

                           if (algo.equals("http://www.w3.org/2002/06/xmldsig-filter2")) {
                              return new DOMXPathFilter2Transform();
                           }

                           if (algo.equals("http://www.w3.org/TR/1999/REC-xpath-19991116")) {
                              return new DOMXPathTransform();
                           }

                           if (algo.equals("http://www.w3.org/TR/1999/REC-xslt-19991116")) {
                              return new DOMXSLTTransform();
                           }

                           throw new ProviderException("No impl for " + algo + " " + type);
                        }

                        return new DOMExcC14NMethod();
                     }

                     return new DOMCanonicalXMLC14N11Method();
                  }

                  return new DOMCanonicalXMLC14NMethod();
               }
            } catch (Exception var5) {
               throw new NoSuchAlgorithmException("Error constructing " + type + " for " + algo + " using XMLDSig", var5);
            }

            throw new ProviderException("No impl for " + algo + " " + type);
         }
      }
   }
}
