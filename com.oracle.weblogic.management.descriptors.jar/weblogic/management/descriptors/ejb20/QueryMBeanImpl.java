package weblogic.management.descriptors.ejb20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class QueryMBeanImpl extends XMLElementMBeanDelegate implements QueryMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_resultTypeMapping = false;
   private String resultTypeMapping = "Local";
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_ejbQl = false;
   private String ejbQl;
   private boolean isSet_queryMethod = false;
   private QueryMethodMBean queryMethod;

   public String getResultTypeMapping() {
      return this.resultTypeMapping;
   }

   public void setResultTypeMapping(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.resultTypeMapping;
      this.resultTypeMapping = value;
      this.isSet_resultTypeMapping = value != null;
      this.checkChange("resultTypeMapping", old, this.resultTypeMapping);
   }

   public String getDescription() {
      return this.description;
   }

   public void setDescription(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.description;
      this.description = value;
      this.isSet_description = value != null;
      this.checkChange("description", old, this.description);
   }

   public String getEJBQl() {
      return this.ejbQl;
   }

   public void setEJBQl(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbQl;
      this.ejbQl = value;
      this.isSet_ejbQl = value != null;
      this.checkChange("ejbQl", old, this.ejbQl);
   }

   public QueryMethodMBean getQueryMethod() {
      return this.queryMethod;
   }

   public void setQueryMethod(QueryMethodMBean value) {
      QueryMethodMBean old = this.queryMethod;
      this.queryMethod = value;
      this.isSet_queryMethod = value != null;
      this.checkChange("queryMethod", old, this.queryMethod);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<query");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getQueryMethod()) {
         result.append(this.getQueryMethod().toXML(indentLevel + 2)).append("\n");
      }

      if ((this.isSet_resultTypeMapping || !"Local".equals(this.getResultTypeMapping())) && null != this.getResultTypeMapping()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<result-type-mapping>").append(this.getResultTypeMapping()).append("</result-type-mapping>\n");
      }

      if (null != this.getEJBQl()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-ql>").append("<![CDATA[" + this.getEJBQl() + "]]>").append("</ejb-ql>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</query>\n");
      return result.toString();
   }
}
