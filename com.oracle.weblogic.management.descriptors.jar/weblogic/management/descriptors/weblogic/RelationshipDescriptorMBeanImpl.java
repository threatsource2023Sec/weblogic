package weblogic.management.descriptors.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class RelationshipDescriptorMBeanImpl extends XMLElementMBeanDelegate implements RelationshipDescriptorMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_ejbEntityRefDescriptions = false;
   private List ejbEntityRefDescriptions;

   public EJBEntityRefDescriptionMBean[] getEJBEntityRefDescriptions() {
      if (this.ejbEntityRefDescriptions == null) {
         return new EJBEntityRefDescriptionMBean[0];
      } else {
         EJBEntityRefDescriptionMBean[] result = new EJBEntityRefDescriptionMBean[this.ejbEntityRefDescriptions.size()];
         result = (EJBEntityRefDescriptionMBean[])((EJBEntityRefDescriptionMBean[])this.ejbEntityRefDescriptions.toArray(result));
         return result;
      }
   }

   public void setEJBEntityRefDescriptions(EJBEntityRefDescriptionMBean[] value) {
      EJBEntityRefDescriptionMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getEJBEntityRefDescriptions();
      }

      this.isSet_ejbEntityRefDescriptions = true;
      if (this.ejbEntityRefDescriptions == null) {
         this.ejbEntityRefDescriptions = Collections.synchronizedList(new ArrayList());
      } else {
         this.ejbEntityRefDescriptions.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.ejbEntityRefDescriptions.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("EJBEntityRefDescriptions", _oldVal, this.getEJBEntityRefDescriptions());
      }

   }

   public void addEJBEntityRefDescription(EJBEntityRefDescriptionMBean value) {
      this.isSet_ejbEntityRefDescriptions = true;
      if (this.ejbEntityRefDescriptions == null) {
         this.ejbEntityRefDescriptions = Collections.synchronizedList(new ArrayList());
      }

      this.ejbEntityRefDescriptions.add(value);
   }

   public void removeEJBEntityRefDescription(EJBEntityRefDescriptionMBean value) {
      if (this.ejbEntityRefDescriptions != null) {
         this.ejbEntityRefDescriptions.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<relationship-descriptor");
      result.append(">\n");
      if (null != this.getEJBEntityRefDescriptions()) {
         for(int i = 0; i < this.getEJBEntityRefDescriptions().length; ++i) {
            result.append(this.getEJBEntityRefDescriptions()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</relationship-descriptor>\n");
      return result.toString();
   }
}
