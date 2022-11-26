package weblogic.management.descriptors.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class ReferenceDescriptorMBeanImpl extends XMLElementMBeanDelegate implements ReferenceDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_resourceEnvDescriptions = false;
   private List resourceEnvDescriptions;
   private boolean isSet_ejbLocalReferenceDescriptions = false;
   private List ejbLocalReferenceDescriptions;
   private boolean isSet_ejbReferenceDescriptions = false;
   private List ejbReferenceDescriptions;
   private boolean isSet_resourceDescriptions = false;
   private List resourceDescriptions;

   public ResourceEnvDescriptionMBean[] getResourceEnvDescriptions() {
      if (this.resourceEnvDescriptions == null) {
         return new ResourceEnvDescriptionMBean[0];
      } else {
         ResourceEnvDescriptionMBean[] result = new ResourceEnvDescriptionMBean[this.resourceEnvDescriptions.size()];
         result = (ResourceEnvDescriptionMBean[])((ResourceEnvDescriptionMBean[])this.resourceEnvDescriptions.toArray(result));
         return result;
      }
   }

   public void setResourceEnvDescriptions(ResourceEnvDescriptionMBean[] value) {
      ResourceEnvDescriptionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getResourceEnvDescriptions();
      }

      this.isSet_resourceEnvDescriptions = true;
      if (this.resourceEnvDescriptions == null) {
         this.resourceEnvDescriptions = Collections.synchronizedList(new ArrayList());
      } else {
         this.resourceEnvDescriptions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.resourceEnvDescriptions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ResourceEnvDescriptions", _oldVal, this.getResourceEnvDescriptions());
      }

   }

   public void addResourceEnvDescription(ResourceEnvDescriptionMBean value) {
      this.isSet_resourceEnvDescriptions = true;
      if (this.resourceEnvDescriptions == null) {
         this.resourceEnvDescriptions = Collections.synchronizedList(new ArrayList());
      }

      this.resourceEnvDescriptions.add(value);
   }

   public void removeResourceEnvDescription(ResourceEnvDescriptionMBean value) {
      if (this.resourceEnvDescriptions != null) {
         this.resourceEnvDescriptions.remove(value);
      }
   }

   public EJBLocalReferenceDescriptionMBean[] getEJBLocalReferenceDescriptions() {
      if (this.ejbLocalReferenceDescriptions == null) {
         return new EJBLocalReferenceDescriptionMBean[0];
      } else {
         EJBLocalReferenceDescriptionMBean[] result = new EJBLocalReferenceDescriptionMBean[this.ejbLocalReferenceDescriptions.size()];
         result = (EJBLocalReferenceDescriptionMBean[])((EJBLocalReferenceDescriptionMBean[])this.ejbLocalReferenceDescriptions.toArray(result));
         return result;
      }
   }

   public void setEJBLocalReferenceDescriptions(EJBLocalReferenceDescriptionMBean[] value) {
      EJBLocalReferenceDescriptionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEJBLocalReferenceDescriptions();
      }

      this.isSet_ejbLocalReferenceDescriptions = true;
      if (this.ejbLocalReferenceDescriptions == null) {
         this.ejbLocalReferenceDescriptions = Collections.synchronizedList(new ArrayList());
      } else {
         this.ejbLocalReferenceDescriptions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.ejbLocalReferenceDescriptions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EJBLocalReferenceDescriptions", _oldVal, this.getEJBLocalReferenceDescriptions());
      }

   }

   public void addEJBLocalReferenceDescription(EJBLocalReferenceDescriptionMBean value) {
      this.isSet_ejbLocalReferenceDescriptions = true;
      if (this.ejbLocalReferenceDescriptions == null) {
         this.ejbLocalReferenceDescriptions = Collections.synchronizedList(new ArrayList());
      }

      this.ejbLocalReferenceDescriptions.add(value);
   }

   public void removeEJBLocalReferenceDescription(EJBLocalReferenceDescriptionMBean value) {
      if (this.ejbLocalReferenceDescriptions != null) {
         this.ejbLocalReferenceDescriptions.remove(value);
      }
   }

   public EJBReferenceDescriptionMBean[] getEJBReferenceDescriptions() {
      if (this.ejbReferenceDescriptions == null) {
         return new EJBReferenceDescriptionMBean[0];
      } else {
         EJBReferenceDescriptionMBean[] result = new EJBReferenceDescriptionMBean[this.ejbReferenceDescriptions.size()];
         result = (EJBReferenceDescriptionMBean[])((EJBReferenceDescriptionMBean[])this.ejbReferenceDescriptions.toArray(result));
         return result;
      }
   }

   public void setEJBReferenceDescriptions(EJBReferenceDescriptionMBean[] value) {
      EJBReferenceDescriptionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEJBReferenceDescriptions();
      }

      this.isSet_ejbReferenceDescriptions = true;
      if (this.ejbReferenceDescriptions == null) {
         this.ejbReferenceDescriptions = Collections.synchronizedList(new ArrayList());
      } else {
         this.ejbReferenceDescriptions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.ejbReferenceDescriptions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EJBReferenceDescriptions", _oldVal, this.getEJBReferenceDescriptions());
      }

   }

   public void addEJBReferenceDescription(EJBReferenceDescriptionMBean value) {
      this.isSet_ejbReferenceDescriptions = true;
      if (this.ejbReferenceDescriptions == null) {
         this.ejbReferenceDescriptions = Collections.synchronizedList(new ArrayList());
      }

      this.ejbReferenceDescriptions.add(value);
   }

   public void removeEJBReferenceDescription(EJBReferenceDescriptionMBean value) {
      if (this.ejbReferenceDescriptions != null) {
         this.ejbReferenceDescriptions.remove(value);
      }
   }

   public ResourceDescriptionMBean[] getResourceDescriptions() {
      if (this.resourceDescriptions == null) {
         return new ResourceDescriptionMBean[0];
      } else {
         ResourceDescriptionMBean[] result = new ResourceDescriptionMBean[this.resourceDescriptions.size()];
         result = (ResourceDescriptionMBean[])((ResourceDescriptionMBean[])this.resourceDescriptions.toArray(result));
         return result;
      }
   }

   public void setResourceDescriptions(ResourceDescriptionMBean[] value) {
      ResourceDescriptionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getResourceDescriptions();
      }

      this.isSet_resourceDescriptions = true;
      if (this.resourceDescriptions == null) {
         this.resourceDescriptions = Collections.synchronizedList(new ArrayList());
      } else {
         this.resourceDescriptions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.resourceDescriptions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("ResourceDescriptions", _oldVal, this.getResourceDescriptions());
      }

   }

   public void addResourceDescription(ResourceDescriptionMBean value) {
      this.isSet_resourceDescriptions = true;
      if (this.resourceDescriptions == null) {
         this.resourceDescriptions = Collections.synchronizedList(new ArrayList());
      }

      this.resourceDescriptions.add(value);
   }

   public void removeResourceDescription(ResourceDescriptionMBean value) {
      if (this.resourceDescriptions != null) {
         this.resourceDescriptions.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<reference-descriptor");
      result.append(">\n");
      int i;
      if (null != this.getResourceDescriptions()) {
         for(i = 0; i < this.getResourceDescriptions().length; ++i) {
            result.append(this.getResourceDescriptions()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getResourceEnvDescriptions()) {
         for(i = 0; i < this.getResourceEnvDescriptions().length; ++i) {
            result.append(this.getResourceEnvDescriptions()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getEJBReferenceDescriptions()) {
         for(i = 0; i < this.getEJBReferenceDescriptions().length; ++i) {
            result.append(this.getEJBReferenceDescriptions()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getEJBLocalReferenceDescriptions()) {
         for(i = 0; i < this.getEJBLocalReferenceDescriptions().length; ++i) {
            result.append(this.getEJBLocalReferenceDescriptions()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</reference-descriptor>\n");
      return result.toString();
   }
}
