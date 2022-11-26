package org.apache.taglibs.standard.tag.common.xml;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.ImportSupport;
import org.apache.taglibs.standard.tag.common.core.Util;
import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public abstract class ParseSupport extends BodyTagSupport {
   protected Object xml;
   protected String systemId;
   protected XMLFilter filter;
   private String var;
   private String varDom;
   private int scope;
   private int scopeDom;
   private DocumentBuilderFactory dbf;
   private DocumentBuilder db;
   private TransformerFactory tf;
   private TransformerHandler th;

   public ParseSupport() {
      this.init();
   }

   private void init() {
      this.var = this.varDom = null;
      this.xml = null;
      this.systemId = null;
      this.filter = null;
      this.dbf = null;
      this.db = null;
      this.tf = null;
      this.th = null;
      this.scope = 1;
      this.scopeDom = 1;
   }

   public int doEndTag() throws JspException {
      try {
         if (this.dbf == null) {
            this.dbf = DocumentBuilderFactory.newInstance();
            this.dbf.setNamespaceAware(true);
            this.dbf.setValidating(false);

            try {
               this.dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            } catch (ParserConfigurationException var4) {
               throw new AssertionError("Parser does not support secure processing");
            }
         }

         this.db = this.dbf.newDocumentBuilder();
         if (this.filter != null) {
            if (this.tf == null) {
               this.tf = TransformerFactory.newInstance();
            }

            if (!this.tf.getFeature("http://javax.xml.transform.sax.SAXTransformerFactory/feature")) {
               throw new JspTagException(Resources.getMessage("PARSE_NO_SAXTRANSFORMER"));
            }

            try {
               this.tf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            } catch (TransformerConfigurationException var3) {
               throw new AssertionError("TransformerFactory does not support secure processing");
            }

            SAXTransformerFactory stf = (SAXTransformerFactory)this.tf;
            this.th = stf.newTransformerHandler();
         }

         Object xmlText = this.xml;
         if (xmlText == null) {
            if (this.bodyContent != null && this.bodyContent.getString() != null) {
               xmlText = this.bodyContent.getString().trim();
            } else {
               xmlText = "";
            }
         }

         Document d;
         if (xmlText instanceof String) {
            d = this.parseStringWithFilter((String)xmlText, this.filter);
         } else {
            if (!(xmlText instanceof Reader)) {
               throw new JspTagException(Resources.getMessage("PARSE_INVALID_SOURCE"));
            }

            d = this.parseReaderWithFilter((Reader)xmlText, this.filter);
         }

         if (this.var != null) {
            this.pageContext.setAttribute(this.var, d, this.scope);
         }

         if (this.varDom != null) {
            this.pageContext.setAttribute(this.varDom, d, this.scopeDom);
         }

         return 6;
      } catch (SAXException var5) {
         throw new JspException(var5);
      } catch (IOException var6) {
         throw new JspException(var6);
      } catch (ParserConfigurationException var7) {
         throw new JspException(var7);
      } catch (TransformerConfigurationException var8) {
         throw new JspException(var8);
      }
   }

   public void release() {
      this.init();
   }

   private Document parseInputSourceWithFilter(InputSource s, XMLFilter f) throws SAXException, IOException {
      if (f != null) {
         Document o = this.db.newDocument();
         this.th.setResult(new DOMResult(o));
         XMLReader xr = XMLReaderFactory.createXMLReader();
         xr.setEntityResolver(new JstlEntityResolver(this.pageContext));
         f.setParent(xr);
         f.setContentHandler(this.th);
         f.parse(s);
         return o;
      } else {
         return this.parseInputSource(s);
      }
   }

   private Document parseReaderWithFilter(Reader r, XMLFilter f) throws SAXException, IOException {
      return this.parseInputSourceWithFilter(new InputSource(r), f);
   }

   private Document parseStringWithFilter(String s, XMLFilter f) throws SAXException, IOException {
      StringReader r = new StringReader(s);
      return this.parseReaderWithFilter(r, f);
   }

   private Document parseURLWithFilter(String url, XMLFilter f) throws SAXException, IOException {
      return this.parseInputSourceWithFilter(new InputSource(url), f);
   }

   private Document parseInputSource(InputSource s) throws SAXException, IOException {
      this.db.setEntityResolver(new JstlEntityResolver(this.pageContext));
      if (this.systemId == null) {
         s.setSystemId("jstl:");
      } else if (ImportSupport.isAbsoluteUrl(this.systemId)) {
         s.setSystemId(this.systemId);
      } else {
         s.setSystemId("jstl:" + this.systemId);
      }

      return this.db.parse(s);
   }

   private Document parseReader(Reader r) throws SAXException, IOException {
      return this.parseInputSource(new InputSource(r));
   }

   private Document parseString(String s) throws SAXException, IOException {
      StringReader r = new StringReader(s);
      return this.parseReader(r);
   }

   private Document parseURL(String url) throws SAXException, IOException {
      return this.parseInputSource(new InputSource(url));
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setVarDom(String varDom) {
      this.varDom = varDom;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   public void setScopeDom(String scopeDom) {
      this.scopeDom = Util.getScope(scopeDom);
   }

   public static class JstlEntityResolver implements EntityResolver {
      private final PageContext ctx;

      public JstlEntityResolver(PageContext ctx) {
         this.ctx = ctx;
      }

      public InputSource resolveEntity(String publicId, String systemId) throws FileNotFoundException {
         if (systemId == null) {
            return null;
         } else {
            if (systemId.startsWith("jstl:")) {
               systemId = systemId.substring(5);
            }

            if (ImportSupport.isAbsoluteUrl(systemId)) {
               return null;
            } else {
               InputStream s;
               if (systemId.startsWith("/")) {
                  s = this.ctx.getServletContext().getResourceAsStream(systemId);
                  if (s == null) {
                     throw new FileNotFoundException(Resources.getMessage("UNABLE_TO_RESOLVE_ENTITY", (Object)systemId));
                  }
               } else {
                  String pagePath = ((HttpServletRequest)this.ctx.getRequest()).getServletPath();
                  String basePath = pagePath.substring(0, pagePath.lastIndexOf("/"));
                  s = this.ctx.getServletContext().getResourceAsStream(basePath + "/" + systemId);
                  if (s == null) {
                     throw new FileNotFoundException(Resources.getMessage("UNABLE_TO_RESOLVE_ENTITY", (Object)systemId));
                  }
               }

               return new InputSource(s);
            }
         }
      }
   }
}
