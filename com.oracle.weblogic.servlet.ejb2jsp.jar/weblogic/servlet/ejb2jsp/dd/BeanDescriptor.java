package weblogic.servlet.ejb2jsp.dd;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.servlet.ejb2jsp.SourceMethodInfo;
import weblogic.servlet.internal.dd.ToXML;
import weblogic.utils.io.XMLWriter;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public class BeanDescriptor implements ToXML, Serializable {
   private EJBMethodDescriptor[] ejbMethods;
   private EJBMethodDescriptor[] homeMethods;
   private String ejbName;
   private String remoteType;
   private String homeType;
   private String ejbType;
   private String jndiName;
   private boolean enabled;
   private static final BaseEJB[] baseEJBMethods = new BaseEJB[]{new BaseEJB("javax.ejb.EJBHome", "getEJBHome", (String)null), new BaseEJB("javax.ejb.Handle", "getHandle", (String)null), new BaseEJB("java.lang.Object", "getPrimaryKey", (String)null), new BaseEJB("boolean", "isIdentical", "javax.ejb.EJBObject"), new BaseEJB("void", "remove", (String)null)};
   private static final BaseEJB[] baseHomeMethods = new BaseEJB[]{new BaseEJB("javax.ejb.EJBMetaData", "getEJBMetaData", (String)null), new BaseEJB("javax.ejb.HomeHandle", "getHomeHandle", (String)null), new BaseEJB("void", "remove", "java.lang.Object"), new BaseEJB("void", "remove", "javax.ejb.Handle")};

   static void p(String s) {
      System.err.println("[EJBTagDesc]: " + s);
   }

   public BeanDescriptor() {
      this.ejbMethods = this.homeMethods = new EJBMethodDescriptor[0];
      this.remoteType = this.homeType = this.ejbType = this.ejbName = this.jndiName = "";
      this.enabled = true;
   }

   public BeanDescriptor(Element parent) throws DOMProcessingException {
      this.initFromRoot(parent);
   }

   private void initFromRoot(Element parent) throws DOMProcessingException {
      Element e = null;
      String s = null;
      this.ejbName = DOMUtils.getValueByTagName(parent, "ejb-name");
      this.remoteType = DOMUtils.getValueByTagName(parent, "remote-type");
      this.homeType = DOMUtils.getValueByTagName(parent, "home-type");
      this.jndiName = DOMUtils.getValueByTagName(parent, "jndi-name");
      this.ejbType = DOMUtils.getValueByTagName(parent, "ejb-type");
      s = DOMUtils.getValueByTagName(parent, "enabled");
      if (!"false".equalsIgnoreCase(s) && !"no".equalsIgnoreCase(s)) {
         this.enabled = true;
      } else {
         this.enabled = false;
      }

      e = DOMUtils.getElementByTagName(parent, "ejb-methods");
      List l = DOMUtils.getOptionalElementsByTagName(e, "method");
      List methods = new ArrayList();
      Iterator I = l.iterator();

      while(I.hasNext()) {
         methods.add(new EJBMethodDescriptor((Element)I.next()));
      }

      this.ejbMethods = new EJBMethodDescriptor[methods.size()];
      methods.toArray(this.ejbMethods);
      methods.clear();
      e = DOMUtils.getElementByTagName(parent, "home-methods");
      l = DOMUtils.getOptionalElementsByTagName(e, "method");
      I = l.iterator();

      while(I.hasNext()) {
         methods.add(new EJBMethodDescriptor((Element)I.next()));
      }

      this.homeMethods = new EJBMethodDescriptor[methods.size()];
      methods.toArray(this.homeMethods);
      this.resolveBaseMethods();
   }

   public String toString() {
      return "EJB: " + this.getEJBName();
   }

   public String getRemoteType() {
      return this.remoteType;
   }

   public void setRemoteType(String s) {
      this.remoteType = s;
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public void setEJBName(String s) {
      this.ejbName = s;
   }

   public String getHomeType() {
      return this.homeType;
   }

   public void setHomeType(String s) {
      this.homeType = s;
   }

   public String getEJBType() {
      return this.ejbType;
   }

   public void setEJBType(String s) {
      this.ejbType = s;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public void setEnabled(boolean b) {
      this.enabled = b;
   }

   public String getJNDIName() {
      return this.jndiName;
   }

   public void setJNDIName(String s) {
      this.jndiName = s;
   }

   public EJBMethodDescriptor[] getEJBMethods() {
      return this.ejbMethods != null ? this.ejbMethods : new EJBMethodDescriptor[0];
   }

   public void setEJBMethods(EJBMethodDescriptor[] x) {
      if (x == null) {
         this.ejbMethods = new EJBMethodDescriptor[0];
      } else {
         this.ejbMethods = (EJBMethodDescriptor[])((EJBMethodDescriptor[])x.clone());
      }
   }

   public EJBMethodDescriptor[] getHomeMethods() {
      return this.homeMethods != null ? this.homeMethods : new EJBMethodDescriptor[0];
   }

   public void setHomeMethods(EJBMethodDescriptor[] x) {
      if (x == null) {
         this.homeMethods = new EJBMethodDescriptor[0];
      } else {
         this.homeMethods = (EJBMethodDescriptor[])((EJBMethodDescriptor[])x.clone());
      }
   }

   public EJBMethodDescriptor[] getUnresolvedMethods() {
      List unr = new ArrayList();
      EJBMethodDescriptor[] ms = this.getEJBMethods();

      int i;
      for(i = 0; i < ms.length; ++i) {
         if (ms[i].isEnabled() && !ms[i].isResolved()) {
            unr.add(ms[i]);
         }
      }

      ms = this.getHomeMethods();

      for(i = 0; i < ms.length; ++i) {
         if (ms[i].isEnabled() && !ms[i].isResolved()) {
            unr.add(ms[i]);
         }
      }

      EJBMethodDescriptor[] ret = new EJBMethodDescriptor[unr.size()];
      unr.toArray(ret);
      return ret;
   }

   public void resolveSource(SourceMethodInfo smi) {
      EJBMethodDescriptor[] unr = this.getUnresolvedMethods();

      for(int i = 0; i < unr.length; ++i) {
         if (smi.equalsMethod(unr[i])) {
            unr[i].resolveParamNames(smi);
         }
      }

   }

   public boolean isStatefulBean() {
      return "ENTITY".equalsIgnoreCase(this.getEJBType()) || "STATEFUL".equalsIgnoreCase(this.getEJBType());
   }

   public void setEnableBaseEJB(boolean b) {
      EJBMethodDescriptor[] ms = this.getEJBMethods();

      int i;
      int j;
      for(i = 0; i < ms.length; ++i) {
         for(j = 0; j < baseEJBMethods.length; ++j) {
            if (baseEJBMethods[j].matchesDescriptor(ms[i])) {
               ms[i].setEnabled(b);
            }
         }
      }

      ms = this.getHomeMethods();

      for(i = 0; i < ms.length; ++i) {
         for(j = 0; j < baseHomeMethods.length; ++j) {
            if (baseHomeMethods[j].matchesDescriptor(ms[i])) {
               ms[i].setEnabled(b);
            }
         }
      }

   }

   public void resolveBaseMethods() {
      SourceMethodInfo m = null;
      List argTypes = new ArrayList();
      List argNames = new ArrayList();
      m = new SourceMethodInfo("getEJBHome", "javax.ejb.EJBHome", argTypes, argNames);
      this.resolveSource(m);
      m = new SourceMethodInfo("getHandle", "javax.ejb.Handle", argTypes, argNames);
      this.resolveSource(m);
      m = new SourceMethodInfo("getPrimaryKey", "java.lang.Object", argTypes, argNames);
      this.resolveSource(m);
      m = new SourceMethodInfo("remove", "void", argTypes, argNames);
      this.resolveSource(m);
      argTypes.add("javax.ejb.EJBObject");
      argNames.add("other");
      m = new SourceMethodInfo("isIdentical", "boolean", argTypes, argNames);
      this.resolveSource(m);
      if (this.isStatefulBean()) {
         argTypes.clear();
         argNames.clear();
         m = new SourceMethodInfo("getEJBMetaData", "javax.ejb.EJBMetaData", argTypes, argNames);
         this.resolveSource(m);
         m = new SourceMethodInfo("getHomeHandle", "javax.ejb.HomeHandle", argTypes, argNames);
         this.resolveSource(m);
         argTypes.add("java.lang.Object");
         argNames.add("object");
         m = new SourceMethodInfo("remove", "void", argTypes, argNames);
         this.resolveSource(m);
         argTypes.clear();
         argNames.clear();
         argTypes.add("javax.ejb.Handle");
         argNames.add("handle");
         m = new SourceMethodInfo("remove", "void", argTypes, argNames);
         this.resolveSource(m);
         EJBMethodDescriptor[] meths = this.getHomeMethods();
         boolean didit = false;

         for(int i = 0; i < meths.length; ++i) {
            EJBMethodDescriptor md = meths[i];
            if ("remove".equals(md.getName()) && this.getHomeType().equals(md.getTargetType()) && "home-remove".equals(md.getTagName())) {
               MethodParamDescriptor[] mpds = md.getParams();
               if (mpds.length == 1 && mpds[0].getType().equals("javax.ejb.Handle")) {
                  md.setTagName("home-remove-handle");
                  didit = true;
               }
            }
         }

      }
   }

   public String[] getErrors() {
      if (!this.isEnabled()) {
         return new String[0];
      } else {
         List errors = new ArrayList();
         this.getDuplicateTagNames(errors);
         this.getDuplicateAttributeNames(errors);
         EJBMethodDescriptor[] unr = this.getUnresolvedMethods();

         for(int i = 0; unr != null && i < unr.length; ++i) {
            String s = "tag " + unr[i].getTagName() + " for method " + unr[i].getName() + " on " + unr[i].getTargetType() + " appears to have meaningless parameter names (arg0,arg1,....)";
            errors.add(s);
         }

         String[] ret = new String[errors.size()];
         errors.toArray(ret);
         return ret;
      }
   }

   void getDuplicateAttributeNames(List l) {
      this.getDuplicateAttributeNames(l, this.getEJBMethods());
      this.getDuplicateAttributeNames(l, this.getHomeMethods());
   }

   private void getDuplicateAttributeNames(List l, EJBMethodDescriptor[] mds) {
      for(int i = 0; i < mds.length; ++i) {
         EJBMethodDescriptor md = mds[i];
         if (md.isEnabled()) {
            MethodParamDescriptor[] mpds = md.getParams();
            int lim = mpds.length;
            if (lim >= 2) {
               for(int j = 0; j < lim - 1; ++j) {
                  String aname = mpds[j].getName();

                  for(int k = j + 1; k < lim; ++k) {
                     if (aname.equals(mpds[k].getName())) {
                        String errMsg = "duplicate attribute name \"" + aname + "\" for tag: " + md.getTagName() + " signature: " + md.getSignature();
                        l.add(errMsg);
                     }
                  }
               }
            }
         }
      }

   }

   private void getDuplicateTagNames(List l) {
      this.getDuplicateTagNames(l, this.getEJBMethods());
      this.getDuplicateTagNames(l, this.getHomeMethods());
   }

   private void getDuplicateTagNames(List l, EJBMethodDescriptor[] m) {
      if (m != null) {
         int lim = m.length;

         for(int i = 0; i < lim - 1; ++i) {
            if (m[i].isEnabled()) {
               String tname = m[i].getTagName();

               for(int j = i + 1; j < lim; ++j) {
                  if (m[j].isEnabled() && tname != null && tname.equals(m[j].getTagName())) {
                     String errMsg = "duplicate tag names \"" + tname + "\" refer to methods \"" + m[i].getSignature() + "\" and \"" + m[j].getSignature() + "\"";
                     l.add(errMsg);
                  }
               }
            }
         }

      }
   }

   public void toXML(XMLWriter x) {
      x.println("<ejb>");
      x.incrIndent();
      x.println("<ejb-name>" + this.ejbName + "</ejb-name>");
      x.println("<remote-type>" + this.remoteType + "</remote-type>");
      x.println("<home-type>" + this.homeType + "</home-type>");
      x.println("<jndi-name>" + this.jndiName + "</jndi-name>");
      x.println("<ejb-type>" + this.ejbType + "</ejb-type>");
      x.println("<enabled>" + this.isEnabled() + "</enabled>");
      x.println("<ejb-methods>");
      x.incrIndent();

      int i;
      for(i = 0; this.ejbMethods != null && i < this.ejbMethods.length; ++i) {
         this.ejbMethods[i].toXML(x);
      }

      x.decrIndent();
      x.println("</ejb-methods>");
      x.println("<home-methods>");
      x.incrIndent();

      for(i = 0; this.homeMethods != null && i < this.homeMethods.length; ++i) {
         this.homeMethods[i].toXML(x);
      }

      x.decrIndent();
      x.println("</home-methods>");
      x.decrIndent();
      x.println("</ejb>");
   }
}
