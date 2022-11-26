package weblogic.servlet.jsp.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.webapp.ParameterMBean;
import weblogic.management.descriptors.webapp.ValidatorMBean;
import weblogic.servlet.internal.dd.BaseServletDescriptor;
import weblogic.servlet.internal.dd.ParameterDescriptor;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ValidatorDescriptor extends BaseServletDescriptor implements ValidatorMBean, ToXML {
   private String classname;
   private ParameterMBean[] params;

   public ValidatorDescriptor() {
   }

   public ValidatorDescriptor(Element parent) throws DOMProcessingException {
      Element e = DOMUtils.getElementByTagName(parent, "validator-class");
      this.classname = DOMUtils.getTextData(e);
      List elts = DOMUtils.getOptionalElementsByTagName(parent, "init-param");
      List p = new ArrayList();
      Iterator I = elts.iterator();

      while(I.hasNext()) {
         p.add(new ParameterDescriptor((Element)I.next()));
      }

      this.params = new ParameterMBean[p.size()];
      p.toArray(this.params);
   }

   public String getClassname() {
      return this.classname;
   }

   public void setClassname(String s) {
      String old = this.classname;
      this.classname = s;
      if (!comp(old, s)) {
         this.firePropertyChange("classname", old, s);
      }

   }

   public ParameterMBean[] getParams() {
      if (this.params == null) {
         this.params = new ParameterMBean[0];
      }

      return (ParameterMBean[])((ParameterMBean[])this.params.clone());
   }

   public void setParams(ParameterMBean[] p) {
      ParameterMBean[] old = this.params;
      if (p != null) {
         this.params = (ParameterMBean[])((ParameterMBean[])p.clone());
         if (!comp(old, p)) {
            this.firePropertyChange("params", old, p);
         }

      }
   }

   public void validate() {
      throw new Error("NYI");
   }

   public void toXML(XMLWriter x) {
      if (this.getClassname() != null && this.getClassname().trim().length() != 0) {
         x.println("<validator>");
         x.incrIndent();
         x.println("<validator-class>" + this.getClassname().trim() + "</validator-class>");
         ParameterMBean[] ps = this.getParams();

         for(int i = 0; ps != null && i < ps.length; ++i) {
            ParameterDescriptor pd = (ParameterDescriptor)ps[i];
            x.println("<init-param>");
            x.incrIndent();
            String pname = pd.getParamName();
            String pval = pd.getParamValue();
            String pdesc = pd.getDescription();
            x.println("<param-name>" + pname + "</param-name>");
            x.println("<param-value>" + pval + "</param-value>");
            if (pdesc != null && (pdesc = pdesc.trim()).length() > 0) {
               pdesc = cdata(pdesc);
               x.println("<description>" + pdesc + "</description>");
            }

            x.decrIndent();
            x.println("</init-param>");
         }

         x.decrIndent();
         x.println("</validator>");
      }
   }

   public String toXML(int indent) {
      return TLDDescriptor.toXML(this, indent);
   }
}
