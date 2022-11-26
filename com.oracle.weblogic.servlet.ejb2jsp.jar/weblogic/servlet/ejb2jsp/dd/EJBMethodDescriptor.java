package weblogic.servlet.ejb2jsp.dd;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.servlet.ejb2jsp.SourceMethodInfo;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class EJBMethodDescriptor implements ToXML, Serializable {
   private String info;
   private String name;
   private String tagname;
   private String targetType;
   private String returnType;
   private String returnVarName;
   MethodParamDescriptor[] params;
   private boolean enabled;
   private boolean evalOut;

   public EJBMethodDescriptor() {
      this.info = this.name = this.tagname = this.targetType = this.returnType = "";
      this.enabled = true;
      this.params = new MethodParamDescriptor[0];
   }

   public EJBMethodDescriptor(Element e) throws DOMProcessingException {
      String s = null;
      this.info = DOMUtils.getValueByTagName(e, "info");
      this.name = DOMUtils.getValueByTagName(e, "name");
      s = DOMUtils.getValueByTagName(e, "enabled");
      if (!"false".equalsIgnoreCase(s) && !"no".equalsIgnoreCase(s)) {
         this.enabled = true;
      } else {
         this.enabled = false;
      }

      this.tagname = DOMUtils.getValueByTagName(e, "tagname");
      this.targetType = DOMUtils.getValueByTagName(e, "target-type");
      this.returnType = DOMUtils.getValueByTagName(e, "return-type");
      List l = DOMUtils.getOptionalElementsByTagName(e, "parameter");
      Iterator I = l.iterator();
      int cnt = DOMUtils.getElementCount(e, "parameter");
      this.params = new MethodParamDescriptor[cnt];

      for(int i = 0; I.hasNext(); this.params[i++] = new MethodParamDescriptor((Element)I.next())) {
      }

   }

   public String getInfo() {
      return this.info;
   }

   public void setInfo(String s) {
      this.info = s;
   }

   public String getSignature() {
      StringBuffer sb = new StringBuffer();
      String rt = this.getReturnType();
      int ind = rt.lastIndexOf(46);
      if (ind > 0) {
         rt = rt.substring(ind + 1);
      }

      sb.append(rt);
      String ttype = this.getTargetType();
      ind = ttype.lastIndexOf(46);
      if (ind > 0) {
         ttype = ttype.substring(ind + 1);
      }

      sb.append(" " + ttype + ".");
      sb.append(this.getName() + "(");
      MethodParamDescriptor[] args = this.getParams();

      for(int i = 0; args != null && i < args.length; ++i) {
         String type = args[i].getType();
         ind = type.lastIndexOf(46);
         if (ind > 0) {
            type = type.substring(ind + 1);
         }

         sb.append(type + " " + args[i].getName());
         if (i != args.length - 1) {
            sb.append(',');
         }
      }

      return sb.append(")").toString();
   }

   public void setSignature(String s) {
   }

   public String getName() {
      return this.name;
   }

   public void setName(String s) {
      this.name = s;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean b) {
      this.enabled = b;
   }

   public String getTagName() {
      return this.tagname;
   }

   public void setTagName(String s) {
      this.tagname = s;
   }

   public String getTargetType() {
      return this.targetType;
   }

   public void setTargetType(String s) {
      this.targetType = s;
   }

   public String getReturnType() {
      return this.returnType;
   }

   public void setReturnType(String s) {
      this.returnType = s;
   }

   public String getReturnVarName() {
      return this.returnVarName;
   }

   public void setReturnVarName(String s) {
      this.returnVarName = s;
   }

   public MethodParamDescriptor[] getParams() {
      return this.params != null ? this.params : new MethodParamDescriptor[0];
   }

   public void setParams(MethodParamDescriptor[] p) {
      if (p == null) {
         this.params = new MethodParamDescriptor[0];
      } else {
         this.params = (MethodParamDescriptor[])((MethodParamDescriptor[])p.clone());
      }
   }

   public boolean isEvalOut() {
      return this.evalOut;
   }

   public void setEvalOut(boolean b) {
      this.evalOut = b;
   }

   static void p(String s) {
      System.err.println("[EJBMethDesc]: " + s);
   }

   public boolean isResolved() {
      MethodParamDescriptor[] ms = this.getParams();
      if (ms != null && ms.length != 0) {
         for(int i = 0; i < ms.length; ++i) {
            if (("arg" + i).equals(ms[i].getName())) {
               return false;
            }
         }

         return true;
      } else {
         return true;
      }
   }

   public String toString() {
      return this.getSignature();
   }

   public void resolveParamNames(SourceMethodInfo smi) {
      MethodParamDescriptor[] ms = this.getParams();
      String[] p = smi.getParams()[1];

      for(int i = 0; i < ms.length; ++i) {
         ms[i].setName(p[i]);
      }

   }

   public void toXML(XMLWriter x) {
      x.println("<method>");
      x.incrIndent();
      x.println("<info>" + this.info + "</info>");
      x.println("<name>" + this.name + "</name>");
      x.println("<enabled>" + this.isEnabled() + "</enabled>");
      x.println("<tagname>" + this.tagname + "</tagname>");
      x.println("<target-type>" + this.targetType + "</target-type>");
      x.println("<return-type>" + this.returnType + "</return-type>");
      x.println("<return-variable-name>" + this.returnVarName + "</return-variable-name>");
      x.println("<eval-out>" + this.isEvalOut() + "</eval-out>");

      for(int i = 0; this.params != null && i < this.params.length; ++i) {
         this.params[i].toXML(x);
      }

      x.decrIndent();
      x.println("</method>");
   }
}
