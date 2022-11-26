package org.apache.taglibs.standard.tag.common.xml;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathVariableResolver;

public class JSTLXPathVariableResolver implements XPathVariableResolver {
   private PageContext pageContext;
   private static final String PAGE_NS_URL = "http://java.sun.com/jstl/xpath/page";
   private static final String REQUEST_NS_URL = "http://java.sun.com/jstl/xpath/request";
   private static final String SESSION_NS_URL = "http://java.sun.com/jstl/xpath/session";
   private static final String APP_NS_URL = "http://java.sun.com/jstl/xpath/app";
   private static final String PARAM_NS_URL = "http://java.sun.com/jstl/xpath/param";
   private static final String INITPARAM_NS_URL = "http://java.sun.com/jstl/xpath/initParam";
   private static final String COOKIE_NS_URL = "http://java.sun.com/jstl/xpath/cookie";
   private static final String HEADER_NS_URL = "http://java.sun.com/jstl/xpath/header";

   public JSTLXPathVariableResolver(PageContext pc) {
      this.pageContext = pc;
   }

   public Object resolveVariable(QName qname) throws NullPointerException {
      Object varObject = null;
      if (qname == null) {
         throw new NullPointerException("Cannot resolve null variable");
      } else {
         String namespace = qname.getNamespaceURI();
         String prefix = qname.getPrefix();
         String localName = qname.getLocalPart();

         try {
            varObject = this.getVariableValue(namespace, prefix, localName);
         } catch (UnresolvableException var7) {
            System.out.println("JSTLXpathVariableResolver.resolveVariable threw UnresolvableException: " + var7);
         }

         return varObject;
      }
   }

   protected Object getVariableValue(String namespace, String prefix, String localName) throws UnresolvableException {
      if (namespace != null && !namespace.equals("")) {
         if (namespace.equals("http://java.sun.com/jstl/xpath/page")) {
            return this.notNull(this.pageContext.getAttribute(localName, 1), namespace, localName);
         } else if (namespace.equals("http://java.sun.com/jstl/xpath/request")) {
            return this.notNull(this.pageContext.getAttribute(localName, 2), namespace, localName);
         } else if (namespace.equals("http://java.sun.com/jstl/xpath/session")) {
            return this.notNull(this.pageContext.getAttribute(localName, 3), namespace, localName);
         } else if (namespace.equals("http://java.sun.com/jstl/xpath/app")) {
            return this.notNull(this.pageContext.getAttribute(localName, 4), namespace, localName);
         } else if (namespace.equals("http://java.sun.com/jstl/xpath/param")) {
            return this.notNull(this.pageContext.getRequest().getParameter(localName), namespace, localName);
         } else if (namespace.equals("http://java.sun.com/jstl/xpath/initParam")) {
            return this.notNull(this.pageContext.getServletContext().getInitParameter(localName), namespace, localName);
         } else {
            HttpServletRequest hsr;
            if (namespace.equals("http://java.sun.com/jstl/xpath/header")) {
               hsr = (HttpServletRequest)this.pageContext.getRequest();
               return this.notNull(hsr.getHeader(localName), namespace, localName);
            } else if (namespace.equals("http://java.sun.com/jstl/xpath/cookie")) {
               hsr = (HttpServletRequest)this.pageContext.getRequest();
               Cookie[] c = hsr.getCookies();

               for(int i = 0; i < c.length; ++i) {
                  if (c[i].getName().equals(localName)) {
                     return c[i].getValue();
                  }
               }

               throw new UnresolvableException("$" + namespace + ":" + localName);
            } else {
               throw new UnresolvableException("$" + namespace + ":" + localName);
            }
         }
      } else {
         return this.notNull(this.pageContext.findAttribute(localName), namespace, localName);
      }
   }

   private Object notNull(Object o, String namespace, String localName) throws UnresolvableException {
      if (o == null) {
         throw new UnresolvableException("$" + (namespace == null ? "" : namespace + ":") + localName);
      } else {
         return o;
      }
   }

   private static void p(String s) {
      System.out.println("[JSTLXPathVariableResolver] " + s);
   }
}
