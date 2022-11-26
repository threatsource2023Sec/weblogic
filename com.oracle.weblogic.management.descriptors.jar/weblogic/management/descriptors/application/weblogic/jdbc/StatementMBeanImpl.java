package weblogic.management.descriptors.application.weblogic.jdbc;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class StatementMBeanImpl extends XMLElementMBeanDelegate implements StatementMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_profilingEnabled = false;
   private boolean profilingEnabled;

   public boolean isProfilingEnabled() {
      return this.profilingEnabled;
   }

   public void setProfilingEnabled(boolean value) {
      boolean old = this.profilingEnabled;
      this.profilingEnabled = value;
      this.isSet_profilingEnabled = true;
      this.checkChange("profilingEnabled", old, this.profilingEnabled);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<statement");
      result.append(">\n");
      result.append(ToXML.indent(indentLevel)).append("</statement>\n");
      return result.toString();
   }
}
