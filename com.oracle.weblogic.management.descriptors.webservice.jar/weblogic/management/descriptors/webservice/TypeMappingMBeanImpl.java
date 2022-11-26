package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class TypeMappingMBeanImpl extends XMLElementMBeanDelegate implements TypeMappingMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_typeMappingEntries = false;
   private List typeMappingEntries;

   public TypeMappingEntryMBean[] getTypeMappingEntries() {
      if (this.typeMappingEntries == null) {
         return new TypeMappingEntryMBean[0];
      } else {
         TypeMappingEntryMBean[] result = new TypeMappingEntryMBean[this.typeMappingEntries.size()];
         result = (TypeMappingEntryMBean[])((TypeMappingEntryMBean[])this.typeMappingEntries.toArray(result));
         return result;
      }
   }

   public void setTypeMappingEntries(TypeMappingEntryMBean[] value) {
      TypeMappingEntryMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getTypeMappingEntries();
      }

      this.isSet_typeMappingEntries = true;
      if (this.typeMappingEntries == null) {
         this.typeMappingEntries = Collections.synchronizedList(new ArrayList());
      } else {
         this.typeMappingEntries.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.typeMappingEntries.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("TypeMappingEntries", _oldVal, this.getTypeMappingEntries());
      }

   }

   public void addTypeMappingEntry(TypeMappingEntryMBean value) {
      this.isSet_typeMappingEntries = true;
      if (this.typeMappingEntries == null) {
         this.typeMappingEntries = Collections.synchronizedList(new ArrayList());
      }

      this.typeMappingEntries.add(value);
   }

   public void removeTypeMappingEntry(TypeMappingEntryMBean value) {
      if (this.typeMappingEntries != null) {
         this.typeMappingEntries.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<type-mapping");
      result.append(">\n");
      if (null != this.getTypeMappingEntries()) {
         for(int i = 0; i < this.getTypeMappingEntries().length; ++i) {
            result.append(this.getTypeMappingEntries()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</type-mapping>\n");
      return result.toString();
   }
}
