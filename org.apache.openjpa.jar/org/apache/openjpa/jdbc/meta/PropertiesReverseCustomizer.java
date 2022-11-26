package org.apache.openjpa.jdbc.meta;

import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.apache.commons.lang.StringUtils;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.util.Localizer;
import serp.util.Strings;

public class PropertiesReverseCustomizer implements ReverseCustomizer {
   private static final Localizer _loc = Localizer.forPackage(PropertiesReverseCustomizer.class);
   protected ReverseMappingTool tool = null;
   private Properties _props = null;
   private Set _unaccessed = null;

   public void setConfiguration(Properties props) {
      this._props = props;
      this._unaccessed = new TreeSet(props.keySet());
   }

   public void setTool(ReverseMappingTool tool) {
      this.tool = tool;
   }

   public int getTableType(Table table, int defaultType) {
      String type = this.getProperty(table.getName() + ".table-type");
      if (type == null && table.getSchemaName() != null) {
         type = this.getProperty(table.getFullName() + ".table-type");
      }

      if (type == null) {
         return defaultType;
      } else if ("none".equals(type)) {
         return 0;
      } else if ("base".equals(type)) {
         return 1;
      } else if ("secondary".equals(type)) {
         return 2;
      } else if ("secondary-outer".equals(type)) {
         return 3;
      } else if ("association".equals(type)) {
         return 4;
      } else if ("subclass".equals(type)) {
         return 5;
      } else {
         throw new IllegalArgumentException(table.getName() + ".table-type: " + type);
      }
   }

   public String getClassName(Table table, String defaultName) {
      String name = this.getProperty(defaultName + ".rename");
      if (name == null) {
         name = this.getProperty(table.getName() + ".class-name");
         if (name == null && table.getSchemaName() != null) {
            name = this.getProperty(table.getFullName() + ".class-name");
         }
      }

      if (name == null) {
         if (this.tool.getLog().isTraceEnabled()) {
            this.tool.getLog().trace(_loc.get("custom-no-class", defaultName, table));
         }

         return defaultName;
      } else if ("none".equals(name)) {
         if (this.tool.getLog().isInfoEnabled()) {
            this.tool.getLog().info(_loc.get("custom-rm-class", defaultName, table));
         }

         return null;
      } else {
         if (this.tool.getLog().isInfoEnabled()) {
            this.tool.getLog().info(_loc.get("custom-class", defaultName, name));
         }

         return name;
      }
   }

   public void customize(ClassMapping cls) {
      String id = this.getProperty(cls.getDescribedType().getName() + ".identity");
      if ("datastore".equals(id)) {
         cls.setObjectIdType((Class)null, false);
         cls.setIdentityType(1);
      } else if ("builtin".equals(id)) {
         cls.setObjectIdType((Class)null, false);
         cls.setIdentityType(2);
      } else if (id != null) {
         cls.setObjectIdType(this.tool.generateClass(id, (Class)null), false);
      }

   }

   public String getClassCode(ClassMapping mapping) {
      return null;
   }

   public void customize(FieldMapping field) {
      String type = this.getProperty(field.getFullName(false) + ".type");
      if (type != null) {
         field.setDeclaredType(Strings.toClass(type, (ClassLoader)null));
      }

   }

   public String getFieldName(ClassMapping dec, Column[] cols, ForeignKey fk, String defaultName) {
      String name = this.getProperty(dec.getDescribedType().getName() + "." + defaultName + ".rename");

      for(int i = 0; name == null && i < cols.length; ++i) {
         name = this.getProperty(cols[i].getTableName() + "." + cols[i].getName() + "." + "field-name");
         if (name == null && cols[i].getTable().getSchemaName() != null) {
            name = this.getProperty(cols[i].getTable().getFullName() + "." + cols[i].getName() + "." + "field-name");
         }
      }

      if (name == null) {
         if (this.tool.getLog().isTraceEnabled()) {
            this.tool.getLog().trace(_loc.get("custom-no-field", defaultName, dec));
         }

         return defaultName;
      } else if ("none".equals(name)) {
         if (this.tool.getLog().isInfoEnabled()) {
            this.tool.getLog().info(_loc.get("custom-rm-field", defaultName, dec));
         }

         return null;
      } else {
         if (this.tool.getLog().isInfoEnabled()) {
            this.tool.getLog().info(_loc.get("custom-field", defaultName, dec, name));
         }

         return name;
      }
   }

   public String getInitialValue(FieldMapping field) {
      return this.getProperty(field.getFullName(false) + ".value");
   }

   public String getDeclaration(FieldMapping field) {
      return null;
   }

   public String getFieldCode(FieldMapping field) {
      return null;
   }

   public boolean unmappedTable(Table table) {
      return false;
   }

   public void close() {
      if (!this._unaccessed.isEmpty() && this.tool.getLog().isTraceEnabled()) {
         this.tool.getLog().trace(_loc.get("custom-unused-props", (Object)this._unaccessed));
      }

   }

   protected String getProperty(String key) {
      String val = StringUtils.trimToNull(this._props.getProperty(key));
      this._unaccessed.remove(key);
      return val;
   }
}
