package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.validators.JMSModuleValidator;
import weblogic.utils.collections.CombinedIterator;

public class NamedEntityBeanImpl extends SettableBeanImpl implements NamedEntityBean, Serializable {
   private long _Id;
   private String _Name;
   private String _Notes;
   private static SchemaHelper2 _schemaHelper;

   public NamedEntityBeanImpl() {
      this._initializeProperty(-1);
   }

   public NamedEntityBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public NamedEntityBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getNotes() {
      return this._Notes;
   }

   public boolean isNotesInherited() {
      return false;
   }

   public boolean isNotesSet() {
      return this._isSet(0);
   }

   public void setNotes(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Notes;
      this._Notes = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(1);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      LegalChecks.checkNonNull("Name", param0);
      JMSModuleValidator.validateEntityName(param0);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(1, _oldVal, param0);
   }

   public long getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(2);
   }

   public void setId(long param0) throws InvalidAttributeValueException {
      long _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      LegalChecks.checkIsSet("Name", this.isNameSet());
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
            }

            return super._isPropertyAKey(info);
         default:
            return super._isPropertyAKey(info);
      }
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Id = 0L;
               if (initOne) {
                  break;
               }
            case 1:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._Notes = null;
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

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 2;
               }
            case 3:
            default:
               break;
            case 4:
               if (s.equals("name")) {
                  return 1;
               }
               break;
            case 5:
               if (s.equals("notes")) {
                  return 0;
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
            case 0:
               return "notes";
            case 1:
               return "name";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private NamedEntityBeanImpl bean;

      protected Helper(NamedEntityBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Notes";
            case 1:
               return "Name";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("Name")) {
            return 1;
         } else {
            return propName.equals("Notes") ? 0 : super.getPropertyIndex(propName);
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isNotesSet()) {
               buf.append("Notes");
               buf.append(String.valueOf(this.bean.getNotes()));
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
            NamedEntityBeanImpl otherTyped = (NamedEntityBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("Notes", this.bean.getNotes(), otherTyped.getNotes(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            NamedEntityBeanImpl original = (NamedEntityBeanImpl)event.getSourceBean();
            NamedEntityBeanImpl proposed = (NamedEntityBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Notes")) {
                  original.setNotes(proposed.getNotes());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            NamedEntityBeanImpl copy = (NamedEntityBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("Notes")) && this.bean.isNotesSet()) {
               copy.setNotes(this.bean.getNotes());
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
