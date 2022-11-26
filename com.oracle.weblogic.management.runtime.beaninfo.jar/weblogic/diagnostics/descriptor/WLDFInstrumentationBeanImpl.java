package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLDFInstrumentationBeanImpl extends WLDFBeanImpl implements WLDFInstrumentationBean, Serializable {
   private boolean _Enabled;
   private String[] _Excludes;
   private String[] _Includes;
   private WLDFInstrumentationMonitorBean[] _WLDFInstrumentationMonitors;
   private static SchemaHelper2 _schemaHelper;

   public WLDFInstrumentationBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFInstrumentationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFInstrumentationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(2);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getIncludes() {
      return this._Includes;
   }

   public boolean isIncludesInherited() {
      return false;
   }

   public boolean isIncludesSet() {
      return this._isSet(3);
   }

   public void setIncludes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Includes;
      this._Includes = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String[] getExcludes() {
      return this._Excludes;
   }

   public boolean isExcludesInherited() {
      return false;
   }

   public boolean isExcludesSet() {
      return this._isSet(4);
   }

   public void setExcludes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Excludes;
      this._Excludes = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void addWLDFInstrumentationMonitor(WLDFInstrumentationMonitorBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         WLDFInstrumentationMonitorBean[] _new;
         if (this._isSet(5)) {
            _new = (WLDFInstrumentationMonitorBean[])((WLDFInstrumentationMonitorBean[])this._getHelper()._extendArray(this.getWLDFInstrumentationMonitors(), WLDFInstrumentationMonitorBean.class, param0));
         } else {
            _new = new WLDFInstrumentationMonitorBean[]{param0};
         }

         try {
            this.setWLDFInstrumentationMonitors(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFInstrumentationMonitorBean[] getWLDFInstrumentationMonitors() {
      return this._WLDFInstrumentationMonitors;
   }

   public boolean isWLDFInstrumentationMonitorsInherited() {
      return false;
   }

   public boolean isWLDFInstrumentationMonitorsSet() {
      return this._isSet(5);
   }

   public void removeWLDFInstrumentationMonitor(WLDFInstrumentationMonitorBean param0) {
      this.destroyWLDFInstrumentationMonitor(param0);
   }

   public void setWLDFInstrumentationMonitors(WLDFInstrumentationMonitorBean[] param0) throws InvalidAttributeValueException {
      WLDFInstrumentationMonitorBean[] param0 = param0 == null ? new WLDFInstrumentationMonitorBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WLDFInstrumentationMonitorBean[] _oldVal = this._WLDFInstrumentationMonitors;
      this._WLDFInstrumentationMonitors = (WLDFInstrumentationMonitorBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public WLDFInstrumentationMonitorBean createWLDFInstrumentationMonitor(String param0) {
      WLDFInstrumentationMonitorBeanImpl _val = new WLDFInstrumentationMonitorBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.addWLDFInstrumentationMonitor(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyWLDFInstrumentationMonitor(WLDFInstrumentationMonitorBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         WLDFInstrumentationMonitorBean[] _old = this.getWLDFInstrumentationMonitors();
         WLDFInstrumentationMonitorBean[] _new = (WLDFInstrumentationMonitorBean[])((WLDFInstrumentationMonitorBean[])this._getHelper()._removeElement(_old, WLDFInstrumentationMonitorBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWLDFInstrumentationMonitors(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._Excludes = new String[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._Includes = new String[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._WLDFInstrumentationMonitors = new WLDFInstrumentationMonitorBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Enabled = false;
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("exclude")) {
                  return 4;
               }

               if (s.equals("include")) {
                  return 3;
               }

               if (s.equals("enabled")) {
                  return 2;
               }
               break;
            case 28:
               if (s.equals("wldf-instrumentation-monitor")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new WLDFInstrumentationMonitorBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "enabled";
            case 3:
               return "include";
            case 4:
               return "exclude";
            case 5:
               return "wldf-instrumentation-monitor";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends WLDFBeanImpl.Helper {
      private WLDFInstrumentationBeanImpl bean;

      protected Helper(WLDFInstrumentationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Enabled";
            case 3:
               return "Includes";
            case 4:
               return "Excludes";
            case 5:
               return "WLDFInstrumentationMonitors";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Excludes")) {
            return 4;
         } else if (propName.equals("Includes")) {
            return 3;
         } else if (propName.equals("WLDFInstrumentationMonitors")) {
            return 5;
         } else {
            return propName.equals("Enabled") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getWLDFInstrumentationMonitors()));
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
            if (this.bean.isExcludesSet()) {
               buf.append("Excludes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getExcludes())));
            }

            if (this.bean.isIncludesSet()) {
               buf.append("Includes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIncludes())));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getWLDFInstrumentationMonitors().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getWLDFInstrumentationMonitors()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isEnabledSet()) {
               buf.append("Enabled");
               buf.append(String.valueOf(this.bean.isEnabled()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            WLDFInstrumentationBeanImpl otherTyped = (WLDFInstrumentationBeanImpl)other;
            this.computeDiff("Excludes", this.bean.getExcludes(), otherTyped.getExcludes(), true);
            this.computeDiff("Includes", this.bean.getIncludes(), otherTyped.getIncludes(), true);
            this.computeChildDiff("WLDFInstrumentationMonitors", this.bean.getWLDFInstrumentationMonitors(), otherTyped.getWLDFInstrumentationMonitors(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFInstrumentationBeanImpl original = (WLDFInstrumentationBeanImpl)event.getSourceBean();
            WLDFInstrumentationBeanImpl proposed = (WLDFInstrumentationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Excludes")) {
                  original.setExcludes(proposed.getExcludes());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Includes")) {
                  original.setIncludes(proposed.getIncludes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("WLDFInstrumentationMonitors")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addWLDFInstrumentationMonitor((WLDFInstrumentationMonitorBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeWLDFInstrumentationMonitor((WLDFInstrumentationMonitorBean)update.getRemovedObject());
                  }

                  if (original.getWLDFInstrumentationMonitors() == null || original.getWLDFInstrumentationMonitors().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
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
            WLDFInstrumentationBeanImpl copy = (WLDFInstrumentationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Excludes")) && this.bean.isExcludesSet()) {
               o = this.bean.getExcludes();
               copy.setExcludes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Includes")) && this.bean.isIncludesSet()) {
               o = this.bean.getIncludes();
               copy.setIncludes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("WLDFInstrumentationMonitors")) && this.bean.isWLDFInstrumentationMonitorsSet() && !copy._isSet(5)) {
               WLDFInstrumentationMonitorBean[] oldWLDFInstrumentationMonitors = this.bean.getWLDFInstrumentationMonitors();
               WLDFInstrumentationMonitorBean[] newWLDFInstrumentationMonitors = new WLDFInstrumentationMonitorBean[oldWLDFInstrumentationMonitors.length];

               for(int i = 0; i < newWLDFInstrumentationMonitors.length; ++i) {
                  newWLDFInstrumentationMonitors[i] = (WLDFInstrumentationMonitorBean)((WLDFInstrumentationMonitorBean)this.createCopy((AbstractDescriptorBean)oldWLDFInstrumentationMonitors[i], includeObsolete));
               }

               copy.setWLDFInstrumentationMonitors(newWLDFInstrumentationMonitors);
            }

            if ((excludeProps == null || !excludeProps.contains("Enabled")) && this.bean.isEnabledSet()) {
               copy.setEnabled(this.bean.isEnabled());
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getWLDFInstrumentationMonitors(), clazz, annotation);
      }
   }
}
