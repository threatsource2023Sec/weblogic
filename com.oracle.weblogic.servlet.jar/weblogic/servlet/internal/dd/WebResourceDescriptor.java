package weblogic.servlet.internal.dd;

import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.ManagementException;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.WebResourceCollectionMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class WebResourceDescriptor extends BaseServletDescriptor implements ToXML, WebResourceCollectionMBean {
   private static final long serialVersionUID = -7968184076073383050L;
   private static final String WEB_RESOURCE_COLLECTION = "web-resource-collection";
   private static final String WEB_RESOURCE_NAME = "web-resource-name";
   private static final String URL_PATTERN = "url-pattern";
   private static final String HTTP_METHOD = "http-method";
   private String resourceName;
   private String description;
   private String[] urlPatterns;
   private String[] httpMethods;

   public WebResourceDescriptor() {
      this("");
   }

   public WebResourceDescriptor(String rname) {
      this.resourceName = rname;
   }

   public WebResourceDescriptor(WebResourceCollectionMBean mbean) {
      this.setResourceName(mbean.getResourceName());
      this.setDescription(mbean.getDescription());
      this.setUrlPatterns(mbean.getUrlPatterns());
      this.setHttpMethods(mbean.getHttpMethods());
   }

   public WebResourceDescriptor(Element parent) throws DOMProcessingException {
      Element elt = DOMUtils.getElementByTagName(parent, "web-resource-name");
      if (elt == null) {
         throw new DOMProcessingException("You must specify '<web-resource-name>' within web-resource-collection");
      } else {
         this.resourceName = DOMUtils.getValueByTagName(parent, "web-resource-name");
         this.description = DOMUtils.getOptionalValueByTagName(parent, "description");
         List elts = DOMUtils.getOptionalElementsByTagName(parent, "url-pattern");
         if (elts != null && elts.size() > 0) {
            List methods = DOMUtils.getTextDataValues(elts);
            String[] result;
            Iterator i;
            int idx;
            if (methods != null) {
               result = new String[methods.size()];
               i = methods.iterator();

               for(idx = 0; i.hasNext() && idx < result.length; ++idx) {
                  result[idx] = (String)i.next();
                  int len = result[idx].length();
                  if (len >= 2 && result[idx].charAt(len - 1) == '*' && result[idx].charAt(len - 2) != '/') {
                     throw new DOMProcessingException("The <url-pattern> : \"" + result[idx] + "\", specified for the <" + "web-resource-collection" + "> with the <" + "web-resource-name" + "> : \"" + this.resourceName + "\", is an illegal non-exact pattern as per the Servlet specification.");
                  }
               }

               this.urlPatterns = result;
            }

            elts = DOMUtils.getOptionalElementsByTagName(parent, "http-method");
            if (elts != null && elts.size() > 0) {
               methods = DOMUtils.getTextDataValues(elts);
               if (methods != null) {
                  result = new String[methods.size()];
                  i = methods.iterator();

                  for(idx = 0; i.hasNext() && idx < result.length; ++idx) {
                     result[idx] = (String)i.next();
                  }

                  this.httpMethods = result;
               }
            }

         } else {
            HTTPLogger.logUrlPatternMissingFromWebResource(this.resourceName);
            throw new DOMProcessingException("<url-pattern> not specified for <web-resource-name> : " + this.resourceName);
         }
      }
   }

   public String getResourceName() {
      return this.resourceName;
   }

   public void setResourceName(String s) {
      Object o = this.resourceName;
      this.resourceName = s;
      if (!comp(o, s)) {
         this.firePropertyChange("resourceName", o, s);
      }

   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String s) {
      Object o = this.description;
      this.description = s;
      if (!comp(o, this.description)) {
         this.firePropertyChange("description", o, s);
      }

   }

   public String[] getUrlPatterns() {
      return this.urlPatterns;
   }

   public void setUrlPatterns(String[] x) {
      Object o = this.urlPatterns;
      this.urlPatterns = x;
      if (!comp(o, x)) {
         this.firePropertyChange("urlPatterns", o, x);
      }

   }

   public void addUrlPattern(String x) {
      String[] prev = this.getUrlPatterns();
      if (prev == null) {
         prev = new String[]{x};
         this.setUrlPatterns(prev);
      } else {
         String[] curr = new String[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setUrlPatterns(curr);
      }
   }

   public void removeUrlPattern(String x) {
      String[] prev = this.getUrlPatterns();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            String[] curr = new String[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setUrlPatterns(curr);
         }

      }
   }

   public String[] getHttpMethods() {
      return this.httpMethods;
   }

   public void setHttpMethods(String[] x) {
      Object oldval = this.httpMethods;
      this.httpMethods = x;
      if (!comp(oldval, x)) {
         this.firePropertyChange("httpMethods", oldval, x);
      }

   }

   public void addHttpMethod(String x) {
      String[] prev = this.getHttpMethods();
      if (prev == null) {
         prev = new String[]{x};
         this.setHttpMethods(prev);
      } else {
         String[] curr = new String[prev.length + 1];
         System.arraycopy(prev, 0, curr, 0, prev.length);
         curr[prev.length] = x;
         this.setHttpMethods(curr);
      }
   }

   public void removeHttpMethod(String x) {
      String[] prev = this.getHttpMethods();
      if (prev != null) {
         int offset = -1;

         for(int i = 0; i < prev.length; ++i) {
            if (prev[i].equals(x)) {
               offset = i;
               break;
            }
         }

         if (offset >= 0) {
            String[] curr = new String[prev.length - 1];
            System.arraycopy(prev, 0, curr, 0, offset);
            System.arraycopy(prev, offset + 1, curr, offset, prev.length - (offset + 1));
            this.setHttpMethods(curr);
         }

      }
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
      boolean ok = true;
      if (this.resourceName == null || (this.resourceName = this.resourceName.trim()).length() == 0) {
         this.addDescriptorError("NO_WEB_RESOURCE");
         ok = false;
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public void register() throws ManagementException {
      super.register();
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<" + "web-resource-collection" + ">\n";
      indent += 2;
      result = result + this.indentStr(indent) + "<web-resource-name>" + this.resourceName + "</web-resource-name>\n";
      if (this.description != null) {
         result = result + this.indentStr(indent) + "<description>" + this.description + "</description>\n";
      }

      int i;
      if (this.urlPatterns != null) {
         for(i = 0; i < this.urlPatterns.length; ++i) {
            result = result + this.indentStr(indent) + "<url-pattern>" + this.urlPatterns[i] + "</url-pattern>\n";
         }
      }

      if (this.httpMethods != null) {
         for(i = 0; i < this.httpMethods.length; ++i) {
            result = result + this.indentStr(indent) + "<http-method>" + this.httpMethods[i] + "</http-method>\n";
         }
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</web-resource-collection>\n";
      return result;
   }

   public String toString() {
      String result = "WebResourceDescriptor(";
      result = result + "description=" + this.getDescription() + ",";
      result = result + "resource=" + this.getResourceName() + ",";
      String[] methods = this.getHttpMethods();
      if (methods == null) {
         result = result + "methods=null,";
      } else {
         String m = "{";

         for(int i = 0; i < methods.length; ++i) {
            m = m + methods[i];
            if (i == methods.length - 1) {
               m = m + "}";
            } else {
               m = m + ",";
            }
         }

         result = result + "methods=" + m + ",";
      }

      String[] urlPatterns = this.getUrlPatterns();
      if (urlPatterns == null) {
         result = result + "UrlPatterns=null,";
      } else {
         String m = "{";

         for(int i = 0; i < urlPatterns.length; ++i) {
            m = m + urlPatterns[i];
            if (i == urlPatterns.length - 1) {
               m = m + "}";
            } else {
               m = m + ",";
            }
         }

         result = result + "UrlPatterns=" + m + ",";
      }

      result = result + ")";
      return result;
   }
}
