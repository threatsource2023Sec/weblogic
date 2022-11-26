package weblogic.management.descriptors.cmp11;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import weblogic.management.descriptors.XMLElementMBeanDelegate;
import weblogic.management.tools.ToXML;

public class WeblogicRDBMSBeanMBeanImpl extends XMLElementMBeanDelegate implements WeblogicRDBMSBeanMBean {
   static final long serialVersionUID = 1L;
   private boolean isSet_tableName = false;
   private String tableName;
   private boolean isSet_ejbName = false;
   private String ejbName;
   private boolean isSet_finders = false;
   private List finders;
   private boolean isSet_dataSourceName = false;
   private String dataSourceName;
   private boolean isSet_poolName = false;
   private String poolName;
   private boolean isSet_enableTunedUpdates = false;
   private boolean enableTunedUpdates = true;
   private boolean isSet_fieldMaps = false;
   private List fieldMaps;

   public String getTableName() {
      return this.tableName;
   }

   public void setTableName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.tableName;
      this.tableName = value;
      this.isSet_tableName = value != null;
      this.checkChange("tableName", old, this.tableName);
   }

   public String getEJBName() {
      return this.ejbName;
   }

   public void setEJBName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.ejbName;
      this.ejbName = value;
      this.isSet_ejbName = value != null;
      this.checkChange("ejbName", old, this.ejbName);
   }

   public FinderMBean[] getFinders() {
      if (this.finders == null) {
         return new FinderMBean[0];
      } else {
         FinderMBean[] result = new FinderMBean[this.finders.size()];
         result = (FinderMBean[])((FinderMBean[])this.finders.toArray(result));
         return result;
      }
   }

   public void setFinders(FinderMBean[] value) {
      FinderMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getFinders();
      }

      this.isSet_finders = true;
      if (this.finders == null) {
         this.finders = Collections.synchronizedList(new ArrayList());
      } else {
         this.finders.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.finders.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("Finders", _oldVal, this.getFinders());
      }

   }

   public void addFinder(FinderMBean value) {
      this.isSet_finders = true;
      if (this.finders == null) {
         this.finders = Collections.synchronizedList(new ArrayList());
      }

      this.finders.add(value);
   }

   public void removeFinder(FinderMBean value) {
      if (this.finders != null) {
         this.finders.remove(value);
      }
   }

   public String getDataSourceName() {
      return this.dataSourceName;
   }

   public void setDataSourceName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.dataSourceName;
      this.dataSourceName = value;
      this.isSet_dataSourceName = value != null;
      this.checkChange("dataSourceName", old, this.dataSourceName);
   }

   public String getPoolName() {
      return this.poolName;
   }

   public void setPoolName(String value) {
      if (value != null && value.trim().length() == 0) {
         value = null;
      }

      String old = this.poolName;
      this.poolName = value;
      this.isSet_poolName = value != null;
      this.checkChange("poolName", old, this.poolName);
   }

   public boolean getEnableTunedUpdates() {
      return this.enableTunedUpdates;
   }

   public void setEnableTunedUpdates(boolean value) {
      boolean old = this.enableTunedUpdates;
      this.enableTunedUpdates = value;
      this.isSet_enableTunedUpdates = true;
      this.checkChange("enableTunedUpdates", old, this.enableTunedUpdates);
   }

   public FieldMapMBean[] getFieldMaps() {
      if (this.fieldMaps == null) {
         return new FieldMapMBean[0];
      } else {
         FieldMapMBean[] result = new FieldMapMBean[this.fieldMaps.size()];
         result = (FieldMapMBean[])((FieldMapMBean[])this.fieldMaps.toArray(result));
         return result;
      }
   }

   public void setFieldMaps(FieldMapMBean[] value) {
      FieldMapMBean[] _oldVal = null;
      if (this.changeSupport != null) {
         _oldVal = this.getFieldMaps();
      }

      this.isSet_fieldMaps = true;
      if (this.fieldMaps == null) {
         this.fieldMaps = Collections.synchronizedList(new ArrayList());
      } else {
         this.fieldMaps.clear();
      }

      if (null != value) {
         for(int i = 0; i < value.length; ++i) {
            this.fieldMaps.add(value[i]);
         }
      }

      if (this.changeSupport != null) {
         this.checkChange("FieldMaps", _oldVal, this.getFieldMaps());
      }

   }

   public void addFieldMap(FieldMapMBean value) {
      this.isSet_fieldMaps = true;
      if (this.fieldMaps == null) {
         this.fieldMaps = Collections.synchronizedList(new ArrayList());
      }

      this.fieldMaps.add(value);
   }

   public void removeFieldMap(FieldMapMBean value) {
      if (this.fieldMaps != null) {
         this.fieldMaps.remove(value);
      }
   }

   public String toXML(int indentLevel) {
      StringBuffer result = new StringBuffer();
      result.append(ToXML.indent(indentLevel)).append("<weblogic-rdbms-bean");
      result.append(">\n");
      if (null != this.getEJBName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<ejb-name>").append(this.getEJBName()).append("</ejb-name>\n");
      }

      if (this.isSet_poolName) {
         if (null != this.getPoolName()) {
            result.append(ToXML.indent(indentLevel + 2)).append("<pool-name>").append(this.getPoolName()).append("</pool-name>\n");
         }
      } else if (this.isSet_dataSourceName && null != this.getDataSourceName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<data-source-name>").append(this.getDataSourceName()).append("</data-source-name>\n");
      }

      if (null != this.getTableName()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<table-name>").append(this.getTableName()).append("</table-name>\n");
      }

      int i;
      if (null != this.getFieldMaps()) {
         for(i = 0; i < this.getFieldMaps().length; ++i) {
            result.append(this.getFieldMaps()[i].toXML(indentLevel + 2));
         }
      }

      if (null != this.getFinders()) {
         for(i = 0; i < this.getFinders().length; ++i) {
            result.append(this.getFinders()[i].toXML(indentLevel + 2));
         }
      }

      if (this.isSet_enableTunedUpdates || !this.getEnableTunedUpdates()) {
         result.append(ToXML.indent(indentLevel + 2)).append("<enable-tuned-updates>").append(ToXML.capitalize(Boolean.valueOf(this.getEnableTunedUpdates()).toString())).append("</enable-tuned-updates>\n");
      }

      result.append(ToXML.indent(indentLevel)).append("</weblogic-rdbms-bean>\n");
      return result.toString();
   }
}
