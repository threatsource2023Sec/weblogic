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
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.utils.collections.CombinedIterator;

public class WLDFDataRetirementMBeanImpl extends ConfigurationMBeanImpl implements WLDFDataRetirementMBean, Serializable {
   private String _ArchiveName;
   private boolean _Enabled;
   private int _RetirementPeriod;
   private int _RetirementTime;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WLDFDataRetirementMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WLDFDataRetirementMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WLDFDataRetirementMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WLDFDataRetirementMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WLDFDataRetirementMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WLDFDataRetirementMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public WLDFDataRetirementMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFDataRetirementMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFDataRetirementMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().isEnabled() : this._Enabled;
   }

   public boolean isEnabledInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isEnabledSet() {
      return this._isSet(10);
   }

   public void setEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFDataRetirementMBeanImpl source = (WLDFDataRetirementMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getArchiveName() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getArchiveName(), this) : this._ArchiveName;
   }

   public boolean isArchiveNameInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isArchiveNameSet() {
      return this._isSet(11);
   }

   public void setArchiveName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WLDFValidator.validateDataRetirementArchiveName(this, param0);
      boolean wasSet = this._isSet(11);
      String _oldVal = this._ArchiveName;
      this._ArchiveName = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFDataRetirementMBeanImpl source = (WLDFDataRetirementMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRetirementTime() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getRetirementTime() : this._RetirementTime;
   }

   public boolean isRetirementTimeInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isRetirementTimeSet() {
      return this._isSet(12);
   }

   public void setRetirementTime(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WLDFValidator.validateDataRetirementTime(this, param0);
      boolean wasSet = this._isSet(12);
      int _oldVal = this._RetirementTime;
      this._RetirementTime = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFDataRetirementMBeanImpl source = (WLDFDataRetirementMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRetirementPeriod() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().getRetirementPeriod() : this._RetirementPeriod;
   }

   public boolean isRetirementPeriodInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isRetirementPeriodSet() {
      return this._isSet(13);
   }

   public void setRetirementPeriod(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("RetirementPeriod", param0, 1);
      boolean wasSet = this._isSet(13);
      int _oldVal = this._RetirementPeriod;
      this._RetirementPeriod = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WLDFDataRetirementMBeanImpl source = (WLDFDataRetirementMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
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
               this._ArchiveName = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._RetirementPeriod = 24;
               if (initOne) {
                  break;
               }
            case 12:
               this._RetirementTime = 0;
               if (initOne) {
                  break;
               }
            case 10:
               this._Enabled = true;
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
      return "WLDFDataRetirement";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ArchiveName")) {
         String oldVal = this._ArchiveName;
         this._ArchiveName = (String)v;
         this._postSet(11, oldVal, this._ArchiveName);
      } else if (name.equals("Enabled")) {
         boolean oldVal = this._Enabled;
         this._Enabled = (Boolean)v;
         this._postSet(10, oldVal, this._Enabled);
      } else {
         int oldVal;
         if (name.equals("RetirementPeriod")) {
            oldVal = this._RetirementPeriod;
            this._RetirementPeriod = (Integer)v;
            this._postSet(13, oldVal, this._RetirementPeriod);
         } else if (name.equals("RetirementTime")) {
            oldVal = this._RetirementTime;
            this._RetirementTime = (Integer)v;
            this._postSet(12, oldVal, this._RetirementTime);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("ArchiveName")) {
         return this._ArchiveName;
      } else if (name.equals("Enabled")) {
         return new Boolean(this._Enabled);
      } else if (name.equals("RetirementPeriod")) {
         return new Integer(this._RetirementPeriod);
      } else {
         return name.equals("RetirementTime") ? new Integer(this._RetirementTime) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("enabled")) {
                  return 10;
               }
               break;
            case 12:
               if (s.equals("archive-name")) {
                  return 11;
               }
               break;
            case 15:
               if (s.equals("retirement-time")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("retirement-period")) {
                  return 13;
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
               return "enabled";
            case 11:
               return "archive-name";
            case 12:
               return "retirement-time";
            case 13:
               return "retirement-period";
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
      private WLDFDataRetirementMBeanImpl bean;

      protected Helper(WLDFDataRetirementMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "Enabled";
            case 11:
               return "ArchiveName";
            case 12:
               return "RetirementTime";
            case 13:
               return "RetirementPeriod";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ArchiveName")) {
            return 11;
         } else if (propName.equals("RetirementPeriod")) {
            return 13;
         } else if (propName.equals("RetirementTime")) {
            return 12;
         } else {
            return propName.equals("Enabled") ? 10 : super.getPropertyIndex(propName);
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
            if (this.bean.isArchiveNameSet()) {
               buf.append("ArchiveName");
               buf.append(String.valueOf(this.bean.getArchiveName()));
            }

            if (this.bean.isRetirementPeriodSet()) {
               buf.append("RetirementPeriod");
               buf.append(String.valueOf(this.bean.getRetirementPeriod()));
            }

            if (this.bean.isRetirementTimeSet()) {
               buf.append("RetirementTime");
               buf.append(String.valueOf(this.bean.getRetirementTime()));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
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
            WLDFDataRetirementMBeanImpl otherTyped = (WLDFDataRetirementMBeanImpl)other;
            this.computeDiff("ArchiveName", this.bean.getArchiveName(), otherTyped.getArchiveName(), true);
            this.computeDiff("RetirementPeriod", this.bean.getRetirementPeriod(), otherTyped.getRetirementPeriod(), true);
            this.computeDiff("RetirementTime", this.bean.getRetirementTime(), otherTyped.getRetirementTime(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFDataRetirementMBeanImpl original = (WLDFDataRetirementMBeanImpl)event.getSourceBean();
            WLDFDataRetirementMBeanImpl proposed = (WLDFDataRetirementMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ArchiveName")) {
                  original.setArchiveName(proposed.getArchiveName());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("RetirementPeriod")) {
                  original.setRetirementPeriod(proposed.getRetirementPeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("RetirementTime")) {
                  original.setRetirementTime(proposed.getRetirementTime());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
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
            WLDFDataRetirementMBeanImpl copy = (WLDFDataRetirementMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ArchiveName")) && this.bean.isArchiveNameSet()) {
               copy.setArchiveName(this.bean.getArchiveName());
            }

            if ((excludeProps == null || !excludeProps.contains("RetirementPeriod")) && this.bean.isRetirementPeriodSet()) {
               copy.setRetirementPeriod(this.bean.getRetirementPeriod());
            }

            if ((excludeProps == null || !excludeProps.contains("RetirementTime")) && this.bean.isRetirementTimeSet()) {
               copy.setRetirementTime(this.bean.getRetirementTime());
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
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
