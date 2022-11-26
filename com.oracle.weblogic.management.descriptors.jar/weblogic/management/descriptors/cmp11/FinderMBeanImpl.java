package weblogic.management.descriptors.cmp11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class FinderMBeanImpl extends XMLElementMBeanDelegate implements FinderMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_findForUpdate = false;
   private boolean findForUpdate = false;
   private boolean isSet_finderSQL = false;
   private String finderSQL;
   private boolean isSet_finderName = false;
   private String finderName;
   private boolean isSet_finderParams = false;
   private List finderParams;
   private boolean isSet_finderQuery = false;
   private String finderQuery;

   public boolean isFindForUpdate() {
      return this.findForUpdate;
   }

   public void setFindForUpdate(boolean value) {
      boolean old = this.findForUpdate;
      this.findForUpdate = value;
      this.isSet_findForUpdate = true;
      this.checkChange("findForUpdate", old, this.findForUpdate);
   }

   public String getFinderSQL() {
      return this.finderSQL;
   }

   public void setFinderSQL(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.finderSQL;
      this.finderSQL = value;
      this.isSet_finderSQL = value != null;
      this.checkChange("finderSQL", old, this.finderSQL);
   }

   public String getFinderName() {
      return this.finderName;
   }

   public void setFinderName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.finderName;
      this.finderName = value;
      this.isSet_finderName = value != null;
      this.checkChange("finderName", old, this.finderName);
   }

   public String[] getFinderParams() {
      if (this.finderParams == null) {
         return new String[0];
      } else {
         String[] result = new String[this.finderParams.size()];
         result = (String[])((String[])this.finderParams.toArray(result));
         return result;
      }
   }

   public void setFinderParams(String[] value) {
      String[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getFinderParams();
      }

      this.isSet_finderParams = true;
      if (this.finderParams == null) {
         this.finderParams = Collections.synchronizedList(new ArrayList());
      } else {
         this.finderParams.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.finderParams.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("FinderParams", _oldVal, this.getFinderParams());
      }

   }

   public void addFinderParam(String value) {
      this.isSet_finderParams = true;
      if (this.finderParams == null) {
         this.finderParams = Collections.synchronizedList(new ArrayList());
      }

      this.finderParams.add(value);
   }

   public String getFinderQuery() {
      return this.finderQuery;
   }

   public void setFinderQuery(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.finderQuery;
      this.finderQuery = value;
      this.isSet_finderQuery = value != null;
      this.checkChange("finderQuery", old, this.finderQuery);
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<finder");
      result.append(">\n");
      if (null != this.getFinderName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<finder-name>").append(this.getFinderName()).append("</finder-name>\n");
      }

      for(int i = 0; i < this.getFinderParams().length; ++i) {
         result.append(ToXML.indent(indentLevel + 2)).append("<finder-param>").append(this.getFinderParams()[i]).append("</finder-param>\n");
      }

      if (null != this.getFinderQuery()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<finder-query>").append("<![CDATA[" + this.getFinderQuery() + "]]>").append("</finder-query>\n");
      }

      if (null != this.getFinderSQL()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<finder-sql>").append("<![CDATA[" + this.getFinderSQL() + "]]>").append("</finder-sql>\n");
      }

      if (this.isSet_findForUpdate || this.isFindForUpdate()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<find-for-update>").append(ToXML.capitalize(Boolean.valueOf(this.isFindForUpdate()).toString())).append("</find-for-update>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</finder>\n");
      return result.toString();
   }
}
