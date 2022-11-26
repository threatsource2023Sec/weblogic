package weblogic.management.descriptors.webservice;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class OperationsMBeanImpl extends XMLElementMBeanDelegate implements OperationsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_operations = false;
   private List operations;

   public OperationMBean[] getOperations() {
      if (this.operations == null) {
         return new OperationMBean[0];
      } else {
         OperationMBean[] result = new OperationMBean[this.operations.size()];
         result = (OperationMBean[])((OperationMBean[])this.operations.toArray(result));
         return result;
      }
   }

   public void setOperations(OperationMBean[] value) {
      OperationMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getOperations();
      }

      this.isSet_operations = true;
      if (this.operations == null) {
         this.operations = Collections.synchronizedList(new ArrayList());
      } else {
         this.operations.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.operations.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Operations", _oldVal, this.getOperations());
      }

   }

   public void addOperation(OperationMBean value) {
      this.isSet_operations = true;
      if (this.operations == null) {
         this.operations = Collections.synchronizedList(new ArrayList());
      }

      this.operations.add(value);
   }

   public void removeOperation(OperationMBean value) {
      if (this.operations != null) {
         this.operations.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<operations");
      result.append(">\n");
      if (null != this.getOperations()) {
         for(int i = 0; i < this.getOperations().length; ++i) {
            result.append(this.getOperations()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</operations>\n");
      return result.toString();
   }
}
