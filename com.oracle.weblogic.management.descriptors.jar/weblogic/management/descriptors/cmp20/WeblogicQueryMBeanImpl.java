package weblogic.management.descriptors.cmp20;

import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.descriptors.ejb20.QueryMethodMBean;
import weblogic.management.tools.ToXML;

public class WeblogicQueryMBeanImpl extends XMLElementMBeanDelegate implements WeblogicQueryMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_groupName = false;
   private String groupName;
   private boolean isSet_description = false;
   private String description;
   private boolean isSet_sqlSelectDistinct = false;
   private boolean sqlSelectDistinct = false;
   private boolean isSet_weblogicQl = false;
   private String weblogicQl;
   private boolean isSet_queryMethod = false;
   private QueryMethodMBean queryMethod;
   private boolean isSet_maxElements = false;
   private int maxElements = 0;
   private boolean isSet_cachingName = false;
   private String cachingName;
   private boolean isSet_includeUpdates = false;
   private boolean includeUpdates = true;

   public String getGroupName() {
      return this.groupName;
   }

   public void setGroupName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.groupName;
      this.groupName = value;
      this.isSet_groupName = value != null;
      this.checkChange("groupName", old, this.groupName);
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

   public boolean getSqlSelectDistinct() {
      return this.sqlSelectDistinct;
   }

   public void setSqlSelectDistinct(boolean value) {
      boolean old = this.sqlSelectDistinct;
      this.sqlSelectDistinct = value;
      this.isSet_sqlSelectDistinct = true;
      this.checkChange("sqlSelectDistinct", old, this.sqlSelectDistinct);
   }

   public String getWeblogicQl() {
      return this.weblogicQl;
   }

   public void setWeblogicQl(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.weblogicQl;
      this.weblogicQl = value;
      this.isSet_weblogicQl = value != null;
      this.checkChange("weblogicQl", old, this.weblogicQl);
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

   public int getMaxElements() {
      return this.maxElements;
   }

   public void setMaxElements(int value) {
      int old = this.maxElements;
      this.maxElements = value;
      this.isSet_maxElements = value != -1;
      this.checkChange("maxElements", old, this.maxElements);
   }

   public String getCachingName() {
      return this.cachingName;
   }

   public void setCachingName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.cachingName;
      this.cachingName = value;
      this.isSet_cachingName = value != null;
      this.checkChange("cachingName", old, this.cachingName);
   }

   public boolean isIncludeUpdates() {
      return this.includeUpdates;
   }

   public void setIncludeUpdates(boolean value) {
      boolean old = this.includeUpdates;
      this.includeUpdates = value;
      this.isSet_includeUpdates = true;
      this.checkChange("includeUpdates", old, this.includeUpdates);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-query");
      result.append(">\n");
      if (null != this.getDescription()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<description>").append("<![CDATA[" + this.getDescription() + "]]>").append("</description>\n");
      }

      if (null != this.getQueryMethod()) {
         result.append(this.getQueryMethod().toXML(indentLevel + 2)).append("\n");
      }

      if (null != this.getWeblogicQl()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<weblogic-ql>").append("<![CDATA[" + this.getWeblogicQl() + "]]>").append("</weblogic-ql>\n");
      }

      if (null != this.getGroupName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<group-name>").append(this.getGroupName()).append("</group-name>\n");
      }

      if (null != this.getCachingName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<caching-name>").append(this.getCachingName()).append("</caching-name>\n");
      }

      if (this.isSet_maxElements || 0 != this.getMaxElements()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<max-elements>").append(this.getMaxElements()).append("</max-elements>\n");
      }

      if (this.isSet_includeUpdates || !this.isIncludeUpdates()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<include-updates>").append(ToXML.capitalize(Boolean.valueOf(this.isIncludeUpdates()).toString())).append("</include-updates>\n");
      }

      if (this.isSet_sqlSelectDistinct || this.getSqlSelectDistinct()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<sql-select-distinct>").append(ToXML.capitalize(Boolean.valueOf(this.getSqlSelectDistinct()).toString())).append("</sql-select-distinct>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-query>\n");
      return result.toString();
   }
}
