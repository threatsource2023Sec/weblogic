package weblogic.management.commo;

import java.io.Serializable;
import java.util.Vector;

public class SecurityMBeanData implements Serializable {
   static final long serialVersionUID = 7101036170757294530L;
   private String typeName = null;
   private String instanceName = null;
   private Commo.Pair[] additionalDescriptorElements = null;
   private Vector attrValues;

   public SecurityMBeanData(String type, String instance, Commo.Pair[] additionalDescriptorElements) {
      this.typeName = type.toString();
      this.instanceName = instance.toString();
      this.additionalDescriptorElements = additionalDescriptorElements;
   }

   public Vector getAttrValues() throws Exception {
      if (this.attrValues == null) {
         this.attrValues = new Vector();
      }

      return this.attrValues;
   }

   public String getTypeName() throws Exception {
      return this.typeName;
   }

   public String getInstanceName() throws Exception {
      return this.instanceName;
   }

   public Commo.Pair[] getAdditionalDescriptorElements() throws Exception {
      return this.additionalDescriptorElements;
   }

   public void addAttrValue(String name, Object value) {
      if (this.attrValues == null) {
         this.attrValues = new Vector();
      }

      this.attrValues.addElement(new Commo.Pair(name, value));
   }

   public void setAttrValues(Vector v) throws Exception {
      this.attrValues = v;
   }

   public void setAdditionalDescriptorValues(Commo.Pair[] pairs) throws Exception {
      this.additionalDescriptorElements = pairs;
   }
}
