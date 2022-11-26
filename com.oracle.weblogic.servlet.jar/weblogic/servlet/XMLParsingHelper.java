package weblogic.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.HandlerBase;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import weblogic.diagnostics.instrumentation.DelegatingMonitor;
import weblogic.diagnostics.instrumentation.DiagnosticMonitor;
import weblogic.diagnostics.instrumentation.InstrumentationSupport;
import weblogic.diagnostics.instrumentation.JoinPoint;
import weblogic.diagnostics.instrumentation.LocalHolder;
import weblogic.diagnostics.instrumentation.PointcutHandlingInfo;
import weblogic.diagnostics.instrumentation.ValueHandlingInfo;
import weblogic.utils.XXEUtils;

public class XMLParsingHelper implements Filter {
   static final long serialVersionUID = 4296966455883153104L;
   static final String _WLDF$INST_VERSION = "9.0.0";
   // $FF: synthetic field
   static Class _WLDF$INST_FLD_class = Class.forName("weblogic.servlet.XMLParsingHelper");
   static final DelegatingMonitor _WLDF$INST_FLD_Servlet_Filter_Around_Medium;
   static final JoinPoint _WLDF$INST_JPFLD_0;
   static final DiagnosticMonitor[] _WLDF$INST_JPFLD_JPMONS_0;

   public void init(FilterConfig filterConfig) throws ServletException {
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
      LocalHolder var5;
      if ((var5 = LocalHolder.getInstance(_WLDF$INST_JPFLD_0, _WLDF$INST_JPFLD_JPMONS_0)) != null) {
         if (var5.argsCapture) {
            var5.args = new Object[4];
            Object[] var10000 = var5.args;
            var10000[0] = this;
            var10000[1] = request;
            var10000[2] = response;
            var10000[3] = chain;
         }

         InstrumentationSupport.createDynamicJoinPoint(var5);
         InstrumentationSupport.preProcess(var5);
         var5.resetPostBegin();
      }

      try {
         XMLParsingRequestWrapper wrapper = new XMLParsingRequestWrapper(request);
         chain.doFilter(wrapper, response);
      } catch (Throwable var7) {
         if (var5 != null) {
            var5.th = var7;
            InstrumentationSupport.postProcess(var5);
         }

         throw var7;
      }

      if (var5 != null) {
         InstrumentationSupport.postProcess(var5);
      }

   }

   public void destroy() {
   }

   static {
      _WLDF$INST_FLD_Servlet_Filter_Around_Medium = (DelegatingMonitor)InstrumentationSupport.getMonitor(_WLDF$INST_FLD_class, "Servlet_Filter_Around_Medium");
      _WLDF$INST_JPFLD_0 = InstrumentationSupport.createJoinPoint(_WLDF$INST_FLD_class, "XMLParsingHelper.java", "weblogic.servlet.XMLParsingHelper", "doFilter", "(Ljavax/servlet/ServletRequest;Ljavax/servlet/ServletResponse;Ljavax/servlet/FilterChain;)V", 69, "", "", "", InstrumentationSupport.makeMap(new String[]{"Servlet_Filter_Around_Medium"}, new PointcutHandlingInfo[]{InstrumentationSupport.createPointcutHandlingInfo((ValueHandlingInfo)null, (ValueHandlingInfo)null, new ValueHandlingInfo[]{InstrumentationSupport.createValueHandlingInfo("req", "weblogic.diagnostics.instrumentation.gathering.ServletRequestRenderer", false, true), null, null})}), (boolean)0);
      _WLDF$INST_JPFLD_JPMONS_0 = new DiagnosticMonitor[]{_WLDF$INST_FLD_Servlet_Filter_Around_Medium};
   }

   private class XMLParsingRequestWrapper extends HttpServletRequestWrapper {
      public XMLParsingRequestWrapper(ServletRequest request) {
         super((HttpServletRequest)request);
      }

      public Object getAttribute(String name) {
         Object value = super.getAttribute(name);
         if (value != null) {
            return value;
         } else {
            String ctype = this.getContentType();
            if (name == null || ctype == null || !"POST".equals(this.getMethod()) || !name.equals("org.w3c.dom.Document") || !ctype.startsWith("text/xml") && !ctype.startsWith("application/xml")) {
               return null;
            } else {
               try {
                  DocumentBuilderFactory dbf = XXEUtils.createDocumentBuilderFactoryInstance();
                  dbf.setNamespaceAware(true);
                  DocumentBuilder db = dbf.newDocumentBuilder();
                  EntityResolver er = (EntityResolver)this.getAttribute("org.xml.sax.EntityResolver");
                  if (er != null) {
                     db.setEntityResolver(er);
                  }

                  return db.parse(this.getInputStream());
               } catch (Exception var7) {
                  throw new XMLProcessingException("Could not parse XML into a document", var7);
               }
            }
         }
      }

      public void setAttribute(String name, Object value) {
         if (name != null) {
            String ctype = this.getContentType();
            if (value != null && ctype != null && "POST".equals(this.getMethod()) && (ctype.startsWith("text/xml") || ctype.startsWith("application/xml"))) {
               boolean isHandlerBase = false;
               boolean isDefaultHandler = false;
               if (name.equals("org.xml.sax.HandlerBase") && value instanceof HandlerBase) {
                  isHandlerBase = true;
               } else if (name.equals("org.xml.sax.helpers.DefaultHandler") && value instanceof DefaultHandler) {
                  isDefaultHandler = true;
               }

               if (isHandlerBase || isDefaultHandler) {
                  this.removeAttribute(name);

                  try {
                     SAXParserFactory saxpf = XXEUtils.createSAXParserFactoryInstance();
                     saxpf.setNamespaceAware(true);
                     SAXParser saxParser = saxpf.newSAXParser();
                     if (isHandlerBase) {
                        saxParser.parse(this.getInputStream(), (HandlerBase)value);
                     } else {
                        saxParser.parse(this.getInputStream(), (DefaultHandler)value);
                     }

                     return;
                  } catch (ParserConfigurationException var8) {
                     throw new XMLProcessingException("Could not parse posted XML into SAX events. " + var8.getMessage(), var8);
                  } catch (SAXException var9) {
                     throw new XMLProcessingException("Could not parse posted XML into SAX events." + var9.getMessage(), var9);
                  } catch (IOException var10) {
                     throw new XMLProcessingException("Could not parse posted XML into SAX events." + var10.getMessage(), var10);
                  }
               }
            }

            super.setAttribute(name, value);
         }
      }
   }
}
