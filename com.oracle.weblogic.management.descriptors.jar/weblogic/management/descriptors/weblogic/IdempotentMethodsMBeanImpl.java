package weblogic.management.descriptors.weblogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.ejb11.MethodMBean;
import weblogic.management.tools.ToXML;

public class IdempotentMethodsMBeanImpl extends XMLElementMBeanDelegate implements IdempotentMethodsMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_methods = false;
   private List methods;

   public MethodMBean[] getMethods() {
      if (this.methods == null) {
         return new MethodMBean[0];
      } else {
         MethodMBean[] result = new MethodMBean[this.methods.size()];
         result = (MethodMBean[])((MethodMBean[])this.methods.toArray(result));
         return result;
      }
   }

   public void setMethods(MethodMBean[] value) {
      MethodMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getMethods();
      }

      this.isSet_methods = true;
      if (this.methods == null) {
         this.methods = Collections.synchronizedList(new ArrayList());
      } else {
         this.methods.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.methods.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Methods", _oldVal, this.getMethods());
      }

   }

   public void addMethod(MethodMBean value) {
      this.isSet_methods = true;
      if (this.methods == null) {
         this.methods = Collections.synchronizedList(new ArrayList());
      }

      this.methods.add(value);
   }

   public void removeMethod(MethodMBean value) {
      if (this.methods != null) {
         this.methods.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<idempotent-methods");
      result.append(">\n");
      if (null != this.getMethods()) {
         for(int i = 0; i < this.getMethods().length; ++i) {
            result.append(this.getMethods()[i].toXML(indentLevel + 2));
         }
      }

      result.append(ToXML.indent(indentLevel)).append("</idempotent-methods>\n");
      return result.toString();
   }
}
