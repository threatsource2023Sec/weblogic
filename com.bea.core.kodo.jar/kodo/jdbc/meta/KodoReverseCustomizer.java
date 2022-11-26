package kodo.jdbc.meta;

import java.util.Properties;
import java.util.Set;
import org.apache.openjpa.jdbc.meta.ClassMapping;
import org.apache.openjpa.jdbc.meta.FieldMapping;
import org.apache.openjpa.jdbc.meta.ReverseCustomizer;
import org.apache.openjpa.jdbc.meta.ReverseMappingTool;
import org.apache.openjpa.jdbc.meta.ValueMapping;
import org.apache.openjpa.jdbc.meta.strats.HandlerCollectionTableFieldStrategy;
import org.apache.openjpa.jdbc.schema.Column;
import org.apache.openjpa.jdbc.schema.ForeignKey;
import org.apache.openjpa.jdbc.schema.Table;
import org.apache.openjpa.lib.conf.Configurable;
import org.apache.openjpa.lib.conf.Configuration;
import org.apache.openjpa.lib.conf.Configurations;
import org.apache.openjpa.lib.conf.GenericConfigurable;
import org.apache.openjpa.lib.util.Options;
import org.apache.openjpa.util.UserException;

public class KodoReverseCustomizer implements ReverseCustomizer, Configurable, GenericConfigurable {
   private Configuration _conf = null;
   private ReverseCustomizer _delegate = null;
   private ReverseMappingTool _tool = null;

   public ReverseCustomizer getCustomizer() {
      return this._delegate;
   }

   public void setCustomizer(ReverseCustomizer delegate) {
      this._delegate = delegate;
   }

   public void setCustomizer(String typeName) {
      if (typeName == null) {
         this._delegate = null;
      } else {
         try {
            this._delegate = (ReverseCustomizer)Class.forName(typeName, true, ReverseCustomizer.class.getClassLoader()).newInstance();
         } catch (Throwable var3) {
            throw new UserException(var3);
         }
      }
   }

   public void setConfiguration(Properties props) {
      if (this._delegate != null) {
         this._delegate.setConfiguration(props);
      }

   }

   public void setTool(ReverseMappingTool tool) {
      this._tool = tool;
      if (this._delegate != null) {
         this._delegate.setTool(tool);
      }

   }

   public int getTableType(Table table, int defaultType) {
      if (this._delegate != null) {
         int type = this._delegate.getTableType(table, defaultType);
         if (type != defaultType) {
            return type;
         }
      }

      return this.isCollectionTable(table) ? 0 : defaultType;
   }

   private boolean isCollectionTable(Table table) {
      ForeignKey[] fks = table.getForeignKeys();
      return fks.length == 1 && !this._tool.isUnique(fks[0]) && (table.getPrimaryKey() == null || this._tool.getPrimaryKeyOnJoin()) && fks[0].getColumns().length == table.getColumns().length - 1;
   }

   public String getClassName(Table table, String defaultName) {
      return this._delegate == null ? defaultName : this._delegate.getClassName(table, defaultName);
   }

   public void customize(ClassMapping cls) {
      if (this._delegate != null) {
         this._delegate.customize(cls);
      }

   }

   public String getClassCode(ClassMapping mapping) {
      return this._delegate == null ? null : this._delegate.getClassCode(mapping);
   }

   public String getFieldName(ClassMapping dec, Column[] cols, ForeignKey fk, String defaultName) {
      return this._delegate == null ? defaultName : this._delegate.getFieldName(dec, cols, fk, defaultName);
   }

   public void customize(FieldMapping field) {
      if (this._delegate != null) {
         this._delegate.customize(field);
      }

   }

   public String getInitialValue(FieldMapping field) {
      return this._delegate == null ? null : this._delegate.getInitialValue(field);
   }

   public String getDeclaration(FieldMapping field) {
      return this._delegate == null ? null : this._delegate.getDeclaration(field);
   }

   public String getFieldCode(FieldMapping field) {
      return this._delegate == null ? null : this._delegate.getFieldCode(field);
   }

   public boolean unmappedTable(Table table) {
      if (this._delegate != null && this._delegate.unmappedTable(table)) {
         return true;
      } else if (!this.isCollectionTable(table)) {
         return false;
      } else {
         ForeignKey fk = table.getForeignKeys()[0];
         ClassMapping cls = this._tool.getClassMapping(fk.getPrimaryKeyTable());
         if (cls == null) {
            return false;
         } else {
            Column[] cols = table.getColumns();
            Column col = null;

            for(int i = 0; col == null && i < cols.length; ++i) {
               if (!fk.containsColumn(cols[i])) {
                  col = cols[i];
               }
            }

            String name = this._tool.getFieldName(table.getName(), cls);
            Class type = this._tool.getFieldType(col, true);
            FieldMapping field = this._tool.newFieldMapping(name, Set.class, col, (ForeignKey)null, cls);
            if (field == null) {
               return false;
            } else {
               field.setJoinForeignKey(fk);
               this._tool.addJoinConstraints(field);
               ValueMapping vm = field.getElementMapping();
               vm.setDeclaredType(type);
               vm.setSerialized(type == Object.class);
               vm.setColumns(new Column[]{col});
               this._tool.addConstraints(vm);
               field.setStrategy(new HandlerCollectionTableFieldStrategy(), (Boolean)null);
               if (this._delegate != null) {
                  this._delegate.customize(field);
               }

               return true;
            }
         }
      }
   }

   public void close() {
      if (this._delegate != null) {
         this._delegate.close();
      }

   }

   public void setConfiguration(Configuration conf) {
      this._conf = conf;
   }

   public void startConfiguration() {
   }

   public void endConfiguration() {
   }

   public void setInto(Options opts) {
      if (this._delegate != null) {
         Configurations.configureInstance(this._delegate, this._conf, opts);
         opts.clear();
      }

   }
}
