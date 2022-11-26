package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.FilterMBean;
import weblogic.management.descriptors.webapp.ParameterMBean;
import weblogic.management.descriptors.webapp.UIMBean;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class FilterDescriptor extends BaseServletDescriptor implements ToXML, FilterMBean {
   private static final long serialVersionUID = 1549772288002198411L;
   private static final String FILTER_NAME = "filter-name";
   private static final String FILTER_CLASS = "filter-class";
   private static final String INIT_PARAM = "init-param";
   private String filterName;
   private String filterClass;
   private List initParams;
   private UIMBean uiData;

   public FilterDescriptor() {
      this.uiData = new UIDescriptor();
   }

   public FilterDescriptor(Element parentElement) throws DOMProcessingException {
      this.filterName = DOMUtils.getValueByTagName(parentElement, "filter-name");
      this.filterClass = DOMUtils.getOptionalValueByTagName(parentElement, "filter-class");
      this.uiData = new UIDescriptor(parentElement);
      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "init-param");
      Iterator i = elts.iterator();
      this.initParams = new ArrayList(elts.size());

      while(i.hasNext()) {
         this.initParams.add(new ParameterDescriptor((Element)i.next()));
      }

   }

   public void validate() throws DescriptorValidationException {
      boolean ok = true;
      this.removeDescriptorErrors();
      String s = this.getFilterName();
      if (s != null && (s = s.trim()).length() != 0) {
         this.setFilterName(s);
      } else {
         this.addDescriptorError("NO_FILTER_NAME");
         ok = false;
      }

      s = this.getFilterClass();
      if (s != null) {
         s = s.trim();
         this.setFilterClass(s);
      }

      String clazz = this.getFilterClass();
      if (clazz == null || clazz.length() == 0) {
         this.addDescriptorError("NO_FILTER_CLASS", this.getFilterName());
         ok = false;
      }

      if (!ok) {
         throw new DescriptorValidationException();
      }
   }

   public String toString() {
      return this.getFilterName();
   }

   public String getFilterName() {
      return this.filterName != null ? this.filterName : "";
   }

   public void setFilterName(String name) {
      String old = this.filterName;
      this.filterName = name;
      if (!comp(old, name)) {
         this.firePropertyChange("filterName", old, name);
      }

   }

   public UIMBean getUIData() {
      return this.uiData;
   }

   public void setUIData(UIMBean uid) {
      this.uiData = uid;
   }

   public String getFilterClass() {
      return this.filterClass;
   }

   public void setFilterClass(String s) {
      String old = this.filterClass;
      this.filterClass = s;
      if (!comp(old, s)) {
         this.firePropertyChange("filterClass", old, s);
      }

   }

   public ParameterMBean[] getInitParams() {
      if (this.initParams == null) {
         return new ParameterDescriptor[0];
      } else {
         ParameterDescriptor[] ret = new ParameterDescriptor[this.initParams.size()];
         this.initParams.toArray(ret);
         return (ParameterMBean[])ret;
      }
   }

   public void setInitParams(ParameterMBean[] x) {
      ParameterMBean[] old = this.getInitParams();
      this.initParams = new ArrayList();
      if (x != null) {
         for(int i = 0; i < x.length; ++i) {
            this.initParams.add(x[i]);
         }

         if (!comp(old, x)) {
            this.firePropertyChange("initParams", old, x);
         }

      }
   }

   public void addInitParam(ParameterMBean x) {
      if (this.initParams == null) {
         this.initParams = new ArrayList();
      }

      this.initParams.add(x);
   }

   public void removeInitParam(ParameterMBean x) {
      if (this.initParams != null) {
         this.initParams.remove(x);
      }
   }

   public String toXML(int indent) {
      String result = "";
      result = result + this.indentStr(indent) + "<filter>\n";
      indent += 2;
      if (this.uiData != null) {
         String smallIcon = this.uiData.getSmallIconFileName();
         String largeIcon = this.uiData.getLargeIconFileName();
         if (smallIcon != null || largeIcon != null) {
            result = result + this.indentStr(indent) + "<icon>\n";
            if (smallIcon != null) {
               result = result + this.indentStr(indent + 2) + "<small-icon>" + smallIcon + "</small-icon>\n";
            }

            if (largeIcon != null) {
               result = result + this.indentStr(indent + 2) + "<large-icon>" + largeIcon + "</large-icon>\n";
            }

            result = result + this.indentStr(indent) + "</icon>\n";
         }
      }

      result = result + this.indentStr(indent) + "<filter-name>" + this.filterName + "</filter-name>\n";
      if (this.uiData != null) {
         if (this.uiData.getDisplayName() != null) {
            result = result + this.indentStr(indent) + "<display-name>" + this.uiData.getDisplayName() + "</display-name>\n";
         }

         if (this.uiData.getDescription() != null) {
            result = result + this.indentStr(indent) + "<description>" + this.uiData.getDescription() + "</description>\n";
         }
      }

      result = result + this.indentStr(indent) + "<filter-class>" + this.filterClass + "</filter-class>\n";
      if (this.initParams != null) {
         for(Iterator i = this.initParams.iterator(); i.hasNext(); result = result + this.indentStr(indent) + "</init-param>\n") {
            ParameterDescriptor pd = (ParameterDescriptor)i.next();
            result = result + this.indentStr(indent) + "<init-param>\n";
            indent += 2;
            result = result + this.indentStr(indent) + "<param-name>" + pd.getParamName() + "</param-name>\n";
            result = result + this.indentStr(indent) + "<param-value>" + pd.getParamValue() + "</param-value>\n";
            String d = pd.getDescription();
            if (d != null) {
               result = result + this.indentStr(indent) + "<description>" + d + "</description>\n";
            }

            indent -= 2;
         }
      }

      indent -= 2;
      result = result + this.indentStr(indent) + "</filter>\n";
      return result;
   }
}
