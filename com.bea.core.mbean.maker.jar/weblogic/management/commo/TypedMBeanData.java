package weblogic.management.commo;

import java.io.Serializable;
import java.util.Vector;
import javax.management.ObjectName;

public class TypedMBeanData implements Serializable {
   static final long serialVersionUID = 1L;
   private String typeName = null;
   private String instanceName = null;
   private Commo.Pair[] additionalDescriptorElements = null;
   private Vector attrValues;

   TypedMBeanData(ObjectName type, ObjectName instance, Commo.Pair[] additionalDescriptorElements) {
      this.typeName = type.toString();
      this.instanceName = instance.toString();
      this.additionalDescriptorElements = additionalDescriptorElements;
   }

   Vector getAttrValues() throws Exception {
      if (this.attrValues == null) {
         this.attrValues = new Vector();
      }

      return this.attrValues;
   }

   ObjectName getTypeName() throws Exception {
      return new ObjectName(this.typeName);
   }

   ObjectName getInstanceName() throws Exception {
      return new ObjectName(this.instanceName);
   }

   Commo.Pair[] getAdditionalDescriptorElements() throws Exception {
      return this.additionalDescriptorElements;
   }

   void addAttrValue(String name, Object value) {
      if (this.attrValues == null) {
         this.attrValues = new Vector();
      }

      this.attrValues.addElement(new Commo.Pair(name, value));
   }
}
