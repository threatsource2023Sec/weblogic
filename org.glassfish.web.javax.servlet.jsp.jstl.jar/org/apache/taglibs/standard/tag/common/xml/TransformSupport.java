package org.apache.taglibs.standard.tag.common.xml;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.apache.taglibs.standard.resources.Resources;
import org.apache.taglibs.standard.tag.common.core.ImportSupport;
import org.apache.taglibs.standard.tag.common.core.Util;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

public abstract class TransformSupport extends BodyTagSupport {
   protected Object xml;
   protected String xmlSystemId;
   protected Object xslt;
   protected String xsltSystemId;
   protected Result result;
   private String var;
   private int scope;
   private Transformer t;
   private TransformerFactory tf;
   private DocumentBuilder db;
   private DocumentBuilderFactory dbf;

   public TransformSupport() {
      this.init();
   }

   private void init() {
      this.xml = this.xslt = null;
      this.xmlSystemId = this.xsltSystemId = null;
      this.var = null;
      this.result = null;
      this.tf = null;
      this.scope = 1;
   }

   public int doStartTag() throws JspException {
      try {
         if (this.dbf == null) {
            this.dbf = DocumentBuilderFactory.newInstance();
            this.dbf.setNamespaceAware(true);
            this.dbf.setValidating(false);

            try {
               this.dbf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            } catch (ParserConfigurationException var3) {
               throw new AssertionError("Parser does not support secure processing");
            }
         }

         if (this.db == null) {
            this.db = this.dbf.newDocumentBuilder();
         }

         if (this.tf == null) {
            this.tf = TransformerFactory.newInstance();

            try {
               this.tf.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
            } catch (TransformerConfigurationException var2) {
               throw new AssertionError("TransformerFactory does not support secure processing");
            }
         }

         if (this.xslt != null) {
            if (!(this.xslt instanceof String) && !(this.xslt instanceof Reader) && !(this.xslt instanceof Source)) {
               throw new JspTagException(Resources.getMessage("TRANSFORM_XSLT_UNRECOGNIZED"));
            } else {
               Source s = this.getSource(this.xslt, this.xsltSystemId);
               this.tf.setURIResolver(new JstlUriResolver(this.pageContext));
               this.t = this.tf.newTransformer(s);
               return 2;
            }
         } else {
            throw new JspTagException(Resources.getMessage("TRANSFORM_NO_TRANSFORMER"));
         }
      } catch (SAXException var4) {
         throw new JspException(var4);
      } catch (ParserConfigurationException var5) {
         throw new JspException(var5);
      } catch (IOException var6) {
         throw new JspException(var6);
      } catch (TransformerConfigurationException var7) {
         throw new JspException(var7);
      }
   }

   public int doEndTag() throws JspException {
      try {
         Object xml = this.xml;
         if (xml == null) {
            if (this.bodyContent != null && this.bodyContent.getString() != null) {
               xml = this.bodyContent.getString().trim();
            } else {
               xml = "";
            }
         }

         Source source = this.getSource(xml, this.xmlSystemId);
         if (this.result != null) {
            this.t.transform(source, this.result);
         } else if (this.var != null) {
            Document d = this.db.newDocument();
            Result doc = new DOMResult(d);
            this.t.transform(source, doc);
            this.pageContext.setAttribute(this.var, d, this.scope);
         } else {
            Result page = new StreamResult(new SafeWriter(this.pageContext.getOut()));
            this.t.transform(source, page);
         }

         return 6;
      } catch (SAXException var5) {
         throw new JspException(var5);
      } catch (ParserConfigurationException var6) {
         throw new JspException(var6);
      } catch (IOException var7) {
         throw new JspException(var7);
      } catch (TransformerException var8) {
         throw new JspException(var8);
      }
   }

   public void release() {
      this.init();
   }

   public void addParameter(String name, Object value) {
      this.t.setParameter(name, value);
   }

   private static String wrapSystemId(String systemId) {
      if (systemId == null) {
         return "jstl:";
      } else {
         return ImportSupport.isAbsoluteUrl(systemId) ? systemId : "jstl:" + systemId;
      }
   }

   private Source getSource(Object o, String systemId) throws SAXException, ParserConfigurationException, IOException {
      if (o == null) {
         return null;
      } else if (o instanceof Source) {
         return (Source)o;
      } else if (o instanceof String) {
         return this.getSource(new StringReader((String)o), systemId);
      } else if (o instanceof Reader) {
         XMLReader xr = XMLReaderFactory.createXMLReader();
         xr.setEntityResolver(new ParseSupport.JstlEntityResolver(this.pageContext));
         InputSource s = new InputSource((Reader)o);
         s.setSystemId(wrapSystemId(systemId));
         Source result = new SAXSource(xr, s);
         result.setSystemId(wrapSystemId(systemId));
         return result;
      } else if (o instanceof Node) {
         return new DOMSource((Node)o);
      } else if (o instanceof List) {
         List l = (List)o;
         if (l.size() == 1) {
            return this.getSource(l.get(0), systemId);
         } else {
            throw new IllegalArgumentException(Resources.getMessage("TRANSFORM_SOURCE_INVALID_LIST"));
         }
      } else {
         throw new IllegalArgumentException(Resources.getMessage("TRANSFORM_SOURCE_UNRECOGNIZED") + o.getClass());
      }
   }

   public void setVar(String var) {
      this.var = var;
   }

   public void setScope(String scope) {
      this.scope = Util.getScope(scope);
   }

   private static class JstlUriResolver implements URIResolver {
      private final PageContext ctx;

      public JstlUriResolver(PageContext ctx) {
         this.ctx = ctx;
      }

      public Source resolve(String href, String base) throws TransformerException {
         if (href == null) {
            return null;
         } else {
            int index;
            if (base != null && (index = base.indexOf("jstl:")) != -1) {
               base = base.substring(index + 5);
            }

            if (ImportSupport.isAbsoluteUrl(href) || base != null && ImportSupport.isAbsoluteUrl(base)) {
               return null;
            } else {
               if (base != null && base.lastIndexOf("/") != -1) {
                  base = base.substring(0, base.lastIndexOf("/") + 1);
               } else {
                  base = "";
               }

               String target = base + href;
               InputStream s;
               if (target.startsWith("/")) {
                  s = this.ctx.getServletContext().getResourceAsStream(target);
                  if (s == null) {
                     throw new TransformerException(Resources.getMessage("UNABLE_TO_RESOLVE_ENTITY", (Object)href));
                  }
               } else {
                  String pagePath = ((HttpServletRequest)this.ctx.getRequest()).getServletPath();
                  String basePath = pagePath.substring(0, pagePath.lastIndexOf("/"));
                  s = this.ctx.getServletContext().getResourceAsStream(basePath + "/" + target);
                  if (s == null) {
                     throw new TransformerException(Resources.getMessage("UNABLE_TO_RESOLVE_ENTITY", (Object)href));
                  }
               }

               return new StreamSource(s);
            }
         }
      }
   }

   private static class SafeWriter extends Writer {
      private Writer w;

      public SafeWriter(Writer w) {
         this.w = w;
      }

      public void close() {
      }

      public void flush() {
      }

      public void write(char[] cbuf, int off, int len) throws IOException {
         this.w.write(cbuf, off, len);
      }
   }
}
