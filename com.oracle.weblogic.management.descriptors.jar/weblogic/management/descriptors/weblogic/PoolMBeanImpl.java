package weblogic.management.descriptors.weblogic;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class PoolMBeanImpl extends XMLElementMBeanDelegate implements PoolMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_initialBeansInFreePool = false;
   private int initialBeansInFreePool = 0;
   private boolean isSet_maxBeansInFreePool = false;
   private int maxBeansInFreePool = 1000;

   public int getInitialBeansInFreePool() {
      return this.initialBeansInFreePool;
   }

   public void setInitialBeansInFreePool(int value) {
      int old = this.initialBeansInFreePool;
      this.initialBeansInFreePool = value;
      this.isSet_initialBeansInFreePool = value != -1;
      this.checkChange("initialBeansInFreePool", old, this.initialBeansInFreePool);
   }

   public int getMaxBeansInFreePool() {
      return this.maxBeansInFreePool;
   }

   public void setMaxBeansInFreePool(int value) {
      int old = this.maxBeansInFreePool;
      this.maxBeansInFreePool = value;
      this.isSet_maxBeansInFreePool = value != -1;
      this.checkChange("maxBeansInFreePool", old, this.maxBeansInFreePool);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<pool");
      result.append(">\n");
      if (this.isSet_maxBeansInFreePool || 1000 != this.getMaxBeansInFreePool()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<max-beans-in-free-pool>").append(this.getMaxBeansInFreePool()).append("</max-beans-in-free-pool>\n");
      }

      if (this.isSet_initialBeansInFreePool || 0 != this.getInitialBeansInFreePool()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<initial-beans-in-free-pool>").append(this.getInitialBeansInFreePool()).append("</initial-beans-in-free-pool>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</pool>\n");
      return result.toString();
   }
}
