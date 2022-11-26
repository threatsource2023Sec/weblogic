package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class GenericJDBCStoreMBeanImpl extends ConfigurationMBeanImpl implements GenericJDBCStoreMBean, Serializable {
   private String _CreateTableDDLFile;
   private String _PrefixName;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private GenericJDBCStoreMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(GenericJDBCStoreMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(GenericJDBCStoreMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public GenericJDBCStoreMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(GenericJDBCStoreMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      GenericJDBCStoreMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public GenericJDBCStoreMBeanImpl() {
      this._initializeProperty(-1);
   }

   public GenericJDBCStoreMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public GenericJDBCStoreMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getPrefixName() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getPrefixName(), this) : this._PrefixName;
   }

   public boolean isPrefixNameInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isPrefixNameSet() {
      return this._isSet(10);
   }

   public void setPrefixName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      JMSLegalHelper.validateJDBCPrefix(param0);
      boolean wasSet = this._isSet(10);
      String _oldVal = this._PrefixName;
      this._PrefixName = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericJDBCStoreMBeanImpl source = (GenericJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getCreateTableDDLFile() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getCreateTableDDLFile(), this) : this._CreateTableDDLFile;
   }

   public boolean isCreateTableDDLFileInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isCreateTableDDLFileSet() {
      return this._isSet(11);
   }

   public void setCreateTableDDLFile(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._CreateTableDDLFile;
      this._CreateTableDDLFile = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         GenericJDBCStoreMBeanImpl source = (GenericJDBCStoreMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 11;
      }

      try {
         switch (idx) {
            case 11:
               this._CreateTableDDLFile = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._PrefixName = null;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "GenericJDBCStore";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("CreateTableDDLFile")) {
         oldVal = this._CreateTableDDLFile;
         this._CreateTableDDLFile = (String)v;
         this._postSet(11, oldVal, this._CreateTableDDLFile);
      } else if (name.equals("PrefixName")) {
         oldVal = this._PrefixName;
         this._PrefixName = (String)v;
         this._postSet(10, oldVal, this._PrefixName);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("CreateTableDDLFile")) {
         return this._CreateTableDDLFile;
      } else {
         return name.equals("PrefixName") ? this._PrefixName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("prefix-name")) {
                  return 10;
               }
               break;
            case 20:
               if (s.equals("create-tableddl-file")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "prefix-name";
            case 11:
               return "create-tableddl-file";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private GenericJDBCStoreMBeanImpl bean;

      protected Helper(GenericJDBCStoreMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "PrefixName";
            case 11:
               return "CreateTableDDLFile";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CreateTableDDLFile")) {
            return 11;
         } else {
            return propName.equals("PrefixName") ? 10 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isCreateTableDDLFileSet()) {
               buf.append("CreateTableDDLFile");
               buf.append(String.valueOf(this.bean.getCreateTableDDLFile()));
            }

            if (this.bean.isPrefixNameSet()) {
               buf.append("PrefixName");
               buf.append(String.valueOf(this.bean.getPrefixName()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            GenericJDBCStoreMBeanImpl otherTyped = (GenericJDBCStoreMBeanImpl)other;
            this.computeDiff("CreateTableDDLFile", this.bean.getCreateTableDDLFile(), otherTyped.getCreateTableDDLFile(), false);
            this.computeDiff("PrefixName", this.bean.getPrefixName(), otherTyped.getPrefixName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            GenericJDBCStoreMBeanImpl original = (GenericJDBCStoreMBeanImpl)event.getSourceBean();
            GenericJDBCStoreMBeanImpl proposed = (GenericJDBCStoreMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CreateTableDDLFile")) {
                  original.setCreateTableDDLFile(proposed.getCreateTableDDLFile());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("PrefixName")) {
                  original.setPrefixName(proposed.getPrefixName());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            GenericJDBCStoreMBeanImpl copy = (GenericJDBCStoreMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CreateTableDDLFile")) && this.bean.isCreateTableDDLFileSet()) {
               copy.setCreateTableDDLFile(this.bean.getCreateTableDDLFile());
            }

            if ((excludeProps == null || !excludeProps.contains("PrefixName")) && this.bean.isPrefixNameSet()) {
               copy.setPrefixName(this.bean.getPrefixName());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
