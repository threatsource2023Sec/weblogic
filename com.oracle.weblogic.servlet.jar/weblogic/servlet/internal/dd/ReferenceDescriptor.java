package weblogic.servlet.internal.dd;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.w3c.dom.Element;
import weblogic.management.descriptors.DescriptorValidationException;
import weblogic.management.descriptors.webapp.EjbRefMBean;
import weblogic.management.descriptors.webapp.ResourceRefMBean;
import weblogic.management.descriptors.webappext.EjbReferenceDescriptionMBean;
import weblogic.management.descriptors.webappext.ReferenceDescriptorMBean;
import weblogic.management.descriptors.webappext.ResourceDescriptionMBean;
import weblogic.management.descriptors.weblogic.ResourceEnvDescriptionMBean;
import weblogic.servlet.HTTPLogger;
import weblogic.utils.AssertionError;
import weblogic.xml.dom.DOMProcessingException;
import weblogic.xml.dom.DOMUtils;

public final class ReferenceDescriptor extends BaseServletDescriptor implements ToXML, ReferenceDescriptorMBean {
   private static final long serialVersionUID = 5931465487784198233L;
   private static final String EJB_REF_DESCRIPTION = "ejb-reference-description";
   private static final String EJB_REF_NAME = "ejb-ref-name";
   private static final String JNDI_NAME = "jndi-name";
   private static final String RESOURCE_DESCRIPTION = "resource-description";
   private static final String RESOURCE_ENV_DESCRIPTION = "resource-env-description";
   private static final String RES_ENV_REF_NAME = "res-env-ref-name";
   private static final String RES_REF_NAME = "res-ref-name";
   private ArrayList resRefs = new ArrayList();
   private ArrayList resEnvRefs = new ArrayList();
   private ArrayList ejbRefs = new ArrayList();
   private static String resRefErr = "Can't define resource-definition in weblogic.xml because web.xml has no matching resource-ref";
   private static String EJBRefErr = "Can't define ejb-reference-description in weblogic.xml because web.xml has no matching ejb-ref";

   public ReferenceDescriptor() {
   }

   public ReferenceDescriptor(ReferenceDescriptorMBean mbean) {
      ResourceDescriptionMBean[] rdmba = mbean.getResourceReferences();
      this.resRefs.clear();

      for(int i = 0; i < rdmba.length; ++i) {
         ResourceDescriptionMBean rdmb = rdmba[i];
         ResourceDescription rd = new ResourceDescription();
         rd.setResourceReference(rdmb.getResourceReference());
         rd.setJndiName(rdmb.getJndiName());
         this.resRefs.add(rd);
      }

      ResourceEnvDescriptionMBean[] redmba = mbean.getResourceEnvReferences();
      this.resEnvRefs.clear();

      for(int i = 0; i < redmba.length; ++i) {
         ResourceEnvDescriptionMBean red = redmba[i];
         ResourceEnvDescriptionMBean redi = newResourceEnvDescriptionMBean();
         redi.setResEnvRefName(red.getResEnvRefName());
         redi.setJNDIName(red.getJNDIName());
         this.resEnvRefs.add(redi);
      }

      EjbReferenceDescriptionMBean[] erdmba = mbean.getEjbReferences();
      this.ejbRefs.clear();

      for(int i = 0; i < erdmba.length; ++i) {
         EjbReferenceDescriptionMBean erdmb = erdmba[i];
         EjbReferenceDescription erd = new EjbReferenceDescription();
         erd.setEjbReference(erdmb.getEjbReference());
         erd.setJndiName(erdmb.getJndiName());
         this.ejbRefs.add(erd);
      }

   }

   private static ResourceEnvDescriptionMBean newResourceEnvDescriptionMBean() {
      try {
         return (ResourceEnvDescriptionMBean)Class.forName("weblogic.management.descriptors.weblogic.ResourceEnvDescriptionMBeanImpl").newInstance();
      } catch (ClassNotFoundException var1) {
         throw new AssertionError(var1);
      } catch (IllegalAccessException var2) {
         throw new AssertionError(var2);
      } catch (InstantiationException var3) {
         throw new AssertionError(var3);
      }
   }

   public ReferenceDescriptor(WebAppDescriptor wad, Element parentElement) throws DOMProcessingException {
      List elts = DOMUtils.getOptionalElementsByTagName(parentElement, "resource-description");
      this.resRefs.clear();
      Iterator i = elts.iterator();

      Element elt;
      String name;
      String jndiName;
      while(i.hasNext()) {
         elt = (Element)i.next();
         name = DOMUtils.getValueByTagName(elt, "res-ref-name");
         jndiName = DOMUtils.getValueByTagName(elt, "jndi-name");
         ResourceDescription rr = new ResourceDescription();
         ResourceRefMBean mb = this.findResRef(wad, name);
         if (mb != null) {
            rr.setResourceReference(mb);
            rr.setJndiName(jndiName);
            this.resRefs.add(rr);
         }
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "resource-env-description");
      this.resEnvRefs.clear();
      i = elts.iterator();

      while(i.hasNext()) {
         elt = (Element)i.next();
         ResourceEnvDescriptionMBean rer = newResourceEnvDescriptionMBean();
         rer.setResEnvRefName(DOMUtils.getValueByTagName(elt, "res-env-ref-name"));
         rer.setJNDIName(DOMUtils.getValueByTagName(elt, "jndi-name"));
         this.resEnvRefs.add(rer);
      }

      elts = DOMUtils.getOptionalElementsByTagName(parentElement, "ejb-reference-description");
      this.ejbRefs.clear();
      i = elts.iterator();

      while(i.hasNext()) {
         elt = (Element)i.next();
         name = DOMUtils.getValueByTagName(elt, "ejb-ref-name");
         jndiName = DOMUtils.getValueByTagName(elt, "jndi-name");
         EjbReferenceDescription rd = new EjbReferenceDescription();
         EjbRefMBean ref = this.findEjbRef(wad, name);
         if (ref != null) {
            rd.setEjbReference(ref);
            rd.setJndiName(jndiName);
            this.ejbRefs.add(rd);
         }
      }

   }

   private ResourceRefMBean findResRef(WebAppDescriptor wad, String name) {
      ResourceRefMBean[] rr = wad.getResourceReferences();
      if (rr == null) {
         HTTPLogger.logNoResourceRefs();
         return null;
      } else {
         for(int i = 0; i < rr.length; ++i) {
            if (rr[i].getRefName().equals(name)) {
               return rr[i];
            }
         }

         HTTPLogger.logResourceRefNotFound(name);
         return null;
      }
   }

   private EjbRefMBean findEjbRef(WebAppDescriptor wad, String name) {
      EjbRefMBean[] er = wad.getEJBReferences();
      EjbRefMBean[] erLocal = wad.getEJBLocalReferences();
      if (er == null && erLocal == null) {
         HTTPLogger.logNoEjbRefs();
         return null;
      } else {
         int i;
         for(i = 0; er != null && i < er.length; ++i) {
            if (er[i].getEJBRefName().equals(name)) {
               return er[i];
            }
         }

         for(i = 0; erLocal != null && i < erLocal.length; ++i) {
            if (erLocal[i].getEJBRefName().equals(name)) {
               return erLocal[i];
            }
         }

         HTTPLogger.logEjbRefNotFound(name);
         return null;
      }
   }

   public EjbReferenceDescriptionMBean[] getEjbReferences() {
      return (EjbReferenceDescriptionMBean[])((EjbReferenceDescriptionMBean[])this.ejbRefs.toArray(new EjbReferenceDescription[0]));
   }

   public void setEjbReferences(EjbReferenceDescriptionMBean[] erd) {
      if (erd != null && erd.length >= 1) {
         EjbReferenceDescriptionMBean[] old = this.getEjbReferences();
         this.ejbRefs.clear();

         for(int i = 0; i < erd.length; ++i) {
            this.ejbRefs.add(erd[i]);
         }

         if (!comp(old, erd)) {
            this.firePropertyChange("ejbReferences", old, erd);
         }

      }
   }

   public void addEjbReference(EjbReferenceDescriptionMBean erd) {
      this.ejbRefs.add(erd);
   }

   public void removeEjbReference(EjbReferenceDescriptionMBean erd) {
      if (this.ejbRefs != null) {
         this.ejbRefs.remove(erd);
      }

   }

   public ResourceDescriptionMBean[] getResourceReferences() {
      return (ResourceDescriptionMBean[])((ResourceDescriptionMBean[])this.resRefs.toArray(new ResourceDescription[0]));
   }

   public void setResourceReferences(ResourceDescriptionMBean[] rd) {
      ResourceDescriptionMBean[] old = this.getResourceReferences();
      this.resRefs.clear();
      if (rd != null) {
         for(int i = 0; i < rd.length; ++i) {
            this.resRefs.add(rd[i]);
         }
      }

      if (!comp(old, rd)) {
         this.firePropertyChange("resourceReferences", old, rd);
      }

   }

   public void addResourceReference(ResourceDescriptionMBean rd) {
      this.resRefs.add(rd);
   }

   public void removeResourceReference(ResourceDescriptionMBean rd) {
      if (this.resRefs != null) {
         this.resRefs.remove(rd);
      }

   }

   public void setResourceEnvReferences(ResourceEnvDescriptionMBean[] des) {
      ResourceEnvDescriptionMBean[] old = this.getResourceEnvReferences();
      this.resEnvRefs.clear();
      if (des != null) {
         for(int i = 0; i < des.length; ++i) {
            this.resEnvRefs.add(des[i]);
         }

         if (!comp(old, des)) {
            this.firePropertyChange("resourceEnvReferences", old, des);
         }

      }
   }

   public ResourceEnvDescriptionMBean[] getResourceEnvReferences() {
      return (ResourceEnvDescriptionMBean[])((ResourceEnvDescriptionMBean[])this.resEnvRefs.toArray(new ResourceEnvDescriptionMBean[this.resEnvRefs.size()]));
   }

   public void addResourceEnvReference(ResourceEnvDescriptionMBean des) {
      this.resEnvRefs.add(des);
   }

   public void removeResourceEnvReference(ResourceEnvDescriptionMBean des) {
      this.resEnvRefs.remove(des);
   }

   public void validate() throws DescriptorValidationException {
      this.removeDescriptorErrors();
   }

   public String toXML(int indent) {
      String result = "";
      if (this.resRefs.size() > 0 || this.resEnvRefs.size() > 0 || this.ejbRefs.size() > 0) {
         result = result + this.indentStr(indent) + "<reference-descriptor>\n";
         indent += 2;

         int i;
         for(i = 0; i < this.resRefs.size(); ++i) {
            ResourceDescription rr = (ResourceDescription)this.resRefs.get(i);
            result = result + rr.toXML(indent);
         }

         for(i = 0; i < this.resEnvRefs.size(); ++i) {
            ResourceEnvDescriptionMBean redr = (ResourceEnvDescriptionMBean)this.resEnvRefs.get(i);
            result = result + redr.toXML(indent);
         }

         for(i = 0; i < this.ejbRefs.size(); ++i) {
            EjbReferenceDescription rr = (EjbReferenceDescription)this.ejbRefs.get(i);
            result = result + this.indentStr(indent) + "<ejb-reference-description>\n";
            indent += 2;
            EjbRefMBean ermb = rr.getEjbReference();
            if (ermb != null) {
               result = result + this.indentStr(indent) + "<ejb-ref-name>" + ermb.getEJBRefName() + "</ejb-ref-name>\n";
            }

            result = result + this.indentStr(indent) + "<jndi-name>" + rr.getJndiName() + "</jndi-name>\n";
            indent -= 2;
            result = result + this.indentStr(indent) + "</ejb-reference-description>\n";
         }

         indent -= 2;
         result = result + this.indentStr(indent) + "</reference-descriptor>\n";
      }

      return result;
   }
}
