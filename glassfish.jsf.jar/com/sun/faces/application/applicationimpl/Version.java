package com.sun.faces.application.applicationimpl;

import com.sun.faces.cdi.CdiExtension;
import com.sun.faces.cdi.CdiUtils;
import com.sun.faces.util.Util;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import javax.enterprise.inject.spi.BeanManager;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.xml.sax.InputSource;

public class Version {
   private Boolean isJsf23;

   public boolean isJsf23() {
      if (this.isJsf23 == null) {
         FacesContext facesContext = FacesContext.getCurrentInstance();
         BeanManager beanManager = Util.getCdiBeanManager(facesContext);
         if (beanManager == null) {
            if (this.getFacesConfigXmlVersion(facesContext).equals("2.3") || Util.getWebXmlVersion(facesContext).equals("4.0")) {
               throw new FacesException("Unable to find CDI BeanManager");
            }

            this.isJsf23 = false;
         } else {
            this.isJsf23 = ((CdiExtension)CdiUtils.getBeanReference(beanManager, CdiExtension.class)).isAddBeansForJSFImplicitObjects();
         }
      }

      return this.isJsf23;
   }

   private String getFacesConfigXmlVersion(FacesContext facesContext) {
      String result = "";
      InputStream stream = null;

      try {
         URL url = facesContext.getExternalContext().getResource("/WEB-INF/faces-config.xml");
         if (url != null) {
            XPathFactory factory = XPathFactory.newInstance();
            XPath xpath = factory.newXPath();
            xpath.setNamespaceContext(new JavaeeNamespaceContext());
            stream = url.openStream();
            result = xpath.evaluate("string(/javaee:faces-config/@version)", new InputSource(stream));
         }
      } catch (MalformedURLException var17) {
      } catch (IOException | XPathExpressionException var18) {
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (IOException var16) {
            }
         }

      }

      return result;
   }

   public class JavaeeNamespaceContext implements NamespaceContext {
      public String getNamespaceURI(String prefix) {
         return "http://xmlns.jcp.org/xml/ns/javaee";
      }

      public String getPrefix(String namespaceURI) {
         return "javaee";
      }

      public Iterator getPrefixes(String namespaceURI) {
         return null;
      }
   }
}
