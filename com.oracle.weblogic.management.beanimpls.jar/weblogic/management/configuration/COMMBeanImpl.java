package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class COMMBeanImpl extends ConfigurationMBeanImpl implements COMMBean, Serializable {
   private boolean _ApartmentThreaded;
   private boolean _MemoryLoggingEnabled;
   private String _NTAuthHost;
   private boolean _NativeModeEnabled;
   private boolean _PrefetchEnums;
   private boolean _VerboseLoggingEnabled;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private COMMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(COMMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(COMMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public COMMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(COMMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      COMMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public COMMBeanImpl() {
      this._initializeProperty(-1);
   }

   public COMMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public COMMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getNTAuthHost() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._performMacroSubstitution(this._getDelegateBean().getNTAuthHost(), this) : this._NTAuthHost;
   }

   public boolean isNTAuthHostInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isNTAuthHostSet() {
      return this._isSet(10);
   }

   public void setNTAuthHost(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(10);
      String _oldVal = this._NTAuthHost;
      this._NTAuthHost = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         COMMBeanImpl source = (COMMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isNativeModeEnabled() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._getDelegateBean().isNativeModeEnabled() : this._NativeModeEnabled;
   }

   public boolean isNativeModeEnabledInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isNativeModeEnabledSet() {
      return this._isSet(11);
   }

   public void setNativeModeEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      boolean _oldVal = this._NativeModeEnabled;
      this._NativeModeEnabled = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         COMMBeanImpl source = (COMMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isVerboseLoggingEnabled() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().isVerboseLoggingEnabled() : this._VerboseLoggingEnabled;
   }

   public boolean isVerboseLoggingEnabledInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isVerboseLoggingEnabledSet() {
      return this._isSet(12);
   }

   public void setVerboseLoggingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      boolean _oldVal = this._VerboseLoggingEnabled;
      this._VerboseLoggingEnabled = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         COMMBeanImpl source = (COMMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isMemoryLoggingEnabled() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._getDelegateBean().isMemoryLoggingEnabled() : this._MemoryLoggingEnabled;
   }

   public boolean isMemoryLoggingEnabledInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isMemoryLoggingEnabledSet() {
      return this._isSet(13);
   }

   public void setMemoryLoggingEnabled(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(13);
      boolean _oldVal = this._MemoryLoggingEnabled;
      this._MemoryLoggingEnabled = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         COMMBeanImpl source = (COMMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isPrefetchEnums() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14) ? this._getDelegateBean().isPrefetchEnums() : this._PrefetchEnums;
   }

   public boolean isPrefetchEnumsInherited() {
      return !this._isSet(14) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(14);
   }

   public boolean isPrefetchEnumsSet() {
      return this._isSet(14);
   }

   public void setPrefetchEnums(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(14);
      boolean _oldVal = this._PrefetchEnums;
      this._PrefetchEnums = param0;
      this._postSet(14, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         COMMBeanImpl source = (COMMBeanImpl)var4.next();
         if (source != null && !source._isSet(14)) {
            source._postSetFirePropertyChange(14, wasSet, _oldVal, param0);
         }
      }

   }

   public boolean isApartmentThreaded() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._getDelegateBean().isApartmentThreaded() : this._ApartmentThreaded;
   }

   public boolean isApartmentThreadedInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isApartmentThreadedSet() {
      return this._isSet(15);
   }

   public void setApartmentThreaded(boolean param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(15);
      boolean _oldVal = this._ApartmentThreaded;
      this._ApartmentThreaded = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         COMMBeanImpl source = (COMMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
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
         idx = 10;
      }

      try {
         switch (idx) {
            case 10:
               this._NTAuthHost = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._ApartmentThreaded = false;
               if (initOne) {
                  break;
               }
            case 13:
               this._MemoryLoggingEnabled = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._NativeModeEnabled = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._PrefetchEnums = false;
               if (initOne) {
                  break;
               }
            case 12:
               this._VerboseLoggingEnabled = false;
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
      return "COM";
   }

   public void putValue(String name, Object v) {
      boolean oldVal;
      if (name.equals("ApartmentThreaded")) {
         oldVal = this._ApartmentThreaded;
         this._ApartmentThreaded = (Boolean)v;
         this._postSet(15, oldVal, this._ApartmentThreaded);
      } else if (name.equals("MemoryLoggingEnabled")) {
         oldVal = this._MemoryLoggingEnabled;
         this._MemoryLoggingEnabled = (Boolean)v;
         this._postSet(13, oldVal, this._MemoryLoggingEnabled);
      } else if (name.equals("NTAuthHost")) {
         String oldVal = this._NTAuthHost;
         this._NTAuthHost = (String)v;
         this._postSet(10, oldVal, this._NTAuthHost);
      } else if (name.equals("NativeModeEnabled")) {
         oldVal = this._NativeModeEnabled;
         this._NativeModeEnabled = (Boolean)v;
         this._postSet(11, oldVal, this._NativeModeEnabled);
      } else if (name.equals("PrefetchEnums")) {
         oldVal = this._PrefetchEnums;
         this._PrefetchEnums = (Boolean)v;
         this._postSet(14, oldVal, this._PrefetchEnums);
      } else if (name.equals("VerboseLoggingEnabled")) {
         oldVal = this._VerboseLoggingEnabled;
         this._VerboseLoggingEnabled = (Boolean)v;
         this._postSet(12, oldVal, this._VerboseLoggingEnabled);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ApartmentThreaded")) {
         return new Boolean(this._ApartmentThreaded);
      } else if (name.equals("MemoryLoggingEnabled")) {
         return new Boolean(this._MemoryLoggingEnabled);
      } else if (name.equals("NTAuthHost")) {
         return this._NTAuthHost;
      } else if (name.equals("NativeModeEnabled")) {
         return new Boolean(this._NativeModeEnabled);
      } else if (name.equals("PrefetchEnums")) {
         return new Boolean(this._PrefetchEnums);
      } else {
         return name.equals("VerboseLoggingEnabled") ? new Boolean(this._VerboseLoggingEnabled) : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 12:
               if (s.equals("nt-auth-host")) {
                  return 10;
               }
            case 13:
            case 15:
            case 16:
            case 17:
            case 20:
            case 21:
            default:
               break;
            case 14:
               if (s.equals("prefetch-enums")) {
                  return 14;
               }
               break;
            case 18:
               if (s.equals("apartment-threaded")) {
                  return 15;
               }
               break;
            case 19:
               if (s.equals("native-mode-enabled")) {
                  return 11;
               }
               break;
            case 22:
               if (s.equals("memory-logging-enabled")) {
                  return 13;
               }
               break;
            case 23:
               if (s.equals("verbose-logging-enabled")) {
                  return 12;
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
               return "nt-auth-host";
            case 11:
               return "native-mode-enabled";
            case 12:
               return "verbose-logging-enabled";
            case 13:
               return "memory-logging-enabled";
            case 14:
               return "prefetch-enums";
            case 15:
               return "apartment-threaded";
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
      private COMMBeanImpl bean;

      protected Helper(COMMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "NTAuthHost";
            case 11:
               return "NativeModeEnabled";
            case 12:
               return "VerboseLoggingEnabled";
            case 13:
               return "MemoryLoggingEnabled";
            case 14:
               return "PrefetchEnums";
            case 15:
               return "ApartmentThreaded";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("NTAuthHost")) {
            return 10;
         } else if (propName.equals("ApartmentThreaded")) {
            return 15;
         } else if (propName.equals("MemoryLoggingEnabled")) {
            return 13;
         } else if (propName.equals("NativeModeEnabled")) {
            return 11;
         } else if (propName.equals("PrefetchEnums")) {
            return 14;
         } else {
            return propName.equals("VerboseLoggingEnabled") ? 12 : super.getPropertyIndex(propName);
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
            if (this.bean.isNTAuthHostSet()) {
               buf.append("NTAuthHost");
               buf.append(String.valueOf(this.bean.getNTAuthHost()));
            }

            if (this.bean.isApartmentThreadedSet()) {
               buf.append("ApartmentThreaded");
               buf.append(String.valueOf(this.bean.isApartmentThreaded()));
            }

            if (this.bean.isMemoryLoggingEnabledSet()) {
               buf.append("MemoryLoggingEnabled");
               buf.append(String.valueOf(this.bean.isMemoryLoggingEnabled()));
            }

            if (this.bean.isNativeModeEnabledSet()) {
               buf.append("NativeModeEnabled");
               buf.append(String.valueOf(this.bean.isNativeModeEnabled()));
            }

            if (this.bean.isPrefetchEnumsSet()) {
               buf.append("PrefetchEnums");
               buf.append(String.valueOf(this.bean.isPrefetchEnums()));
            }

            if (this.bean.isVerboseLoggingEnabledSet()) {
               buf.append("VerboseLoggingEnabled");
               buf.append(String.valueOf(this.bean.isVerboseLoggingEnabled()));
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
            COMMBeanImpl otherTyped = (COMMBeanImpl)other;
            this.computeDiff("NTAuthHost", this.bean.getNTAuthHost(), otherTyped.getNTAuthHost(), false);
            this.computeDiff("ApartmentThreaded", this.bean.isApartmentThreaded(), otherTyped.isApartmentThreaded(), false);
            this.computeDiff("MemoryLoggingEnabled", this.bean.isMemoryLoggingEnabled(), otherTyped.isMemoryLoggingEnabled(), false);
            this.computeDiff("NativeModeEnabled", this.bean.isNativeModeEnabled(), otherTyped.isNativeModeEnabled(), false);
            this.computeDiff("PrefetchEnums", this.bean.isPrefetchEnums(), otherTyped.isPrefetchEnums(), false);
            this.computeDiff("VerboseLoggingEnabled", this.bean.isVerboseLoggingEnabled(), otherTyped.isVerboseLoggingEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            COMMBeanImpl original = (COMMBeanImpl)event.getSourceBean();
            COMMBeanImpl proposed = (COMMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("NTAuthHost")) {
                  original.setNTAuthHost(proposed.getNTAuthHost());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ApartmentThreaded")) {
                  original.setApartmentThreaded(proposed.isApartmentThreaded());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("MemoryLoggingEnabled")) {
                  original.setMemoryLoggingEnabled(proposed.isMemoryLoggingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("NativeModeEnabled")) {
                  original.setNativeModeEnabled(proposed.isNativeModeEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("PrefetchEnums")) {
                  original.setPrefetchEnums(proposed.isPrefetchEnums());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("VerboseLoggingEnabled")) {
                  original.setVerboseLoggingEnabled(proposed.isVerboseLoggingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            COMMBeanImpl copy = (COMMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("NTAuthHost")) && this.bean.isNTAuthHostSet()) {
               copy.setNTAuthHost(this.bean.getNTAuthHost());
            }

            if ((excludeProps == null || !excludeProps.contains("ApartmentThreaded")) && this.bean.isApartmentThreadedSet()) {
               copy.setApartmentThreaded(this.bean.isApartmentThreaded());
            }

            if ((excludeProps == null || !excludeProps.contains("MemoryLoggingEnabled")) && this.bean.isMemoryLoggingEnabledSet()) {
               copy.setMemoryLoggingEnabled(this.bean.isMemoryLoggingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("NativeModeEnabled")) && this.bean.isNativeModeEnabledSet()) {
               copy.setNativeModeEnabled(this.bean.isNativeModeEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("PrefetchEnums")) && this.bean.isPrefetchEnumsSet()) {
               copy.setPrefetchEnums(this.bean.isPrefetchEnums());
            }

            if ((excludeProps == null || !excludeProps.contains("VerboseLoggingEnabled")) && this.bean.isVerboseLoggingEnabledSet()) {
               copy.setVerboseLoggingEnabled(this.bean.isVerboseLoggingEnabled());
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
