package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WLDFInstrumentationMonitorBeanImpl extends WLDFBeanImpl implements WLDFInstrumentationMonitorBean, Serializable {
   private String[] _Actions;
   private String _Description;
   private boolean _DyeFilteringEnabled;
   private String _DyeMask;
   private boolean _Enabled;
   private String[] _Excludes;
   private String[] _Includes;
   private String _LocationType;
   private String _Pointcut;
   private String _Properties;
   private static SchemaHelper2 _schemaHelper;

   public WLDFInstrumentationMonitorBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFInstrumentationMonitorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFInstrumentationMonitorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(2);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isEnabled() {
      return this._Enabled;
   }

   public boolean isEnabledInherited() {
      return false;
   }

   public boolean isEnabledSet() {
      return this._isSet(3);
   }

   public void setEnabled(boolean param0) {
      boolean _oldVal = this._Enabled;
      this._Enabled = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getDyeMask() {
      return this._DyeMask;
   }

   public boolean isDyeMaskInherited() {
      return false;
   }

   public boolean isDyeMaskSet() {
      return this._isSet(4);
   }

   public void setDyeMask(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DyeMask;
      this._DyeMask = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isDyeFilteringEnabled() {
      return this._DyeFilteringEnabled;
   }

   public boolean isDyeFilteringEnabledInherited() {
      return false;
   }

   public boolean isDyeFilteringEnabledSet() {
      return this._isSet(5);
   }

   public void setDyeFilteringEnabled(boolean param0) {
      boolean _oldVal = this._DyeFilteringEnabled;
      this._DyeFilteringEnabled = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getProperties() {
      return this._Properties;
   }

   public boolean isPropertiesInherited() {
      return false;
   }

   public boolean isPropertiesSet() {
      return this._isSet(6);
   }

   public void setProperties(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Properties;
      this._Properties = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String[] getActions() {
      return this._Actions;
   }

   public boolean isActionsInherited() {
      return false;
   }

   public boolean isActionsSet() {
      return this._isSet(7);
   }

   public void setActions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Actions;
      this._Actions = param0;
      this._postSet(7, _oldVal, param0);
   }

   public String getLocationType() {
      return this._LocationType;
   }

   public boolean isLocationTypeInherited() {
      return false;
   }

   public boolean isLocationTypeSet() {
      return this._isSet(8);
   }

   public void setLocationType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"before", "after", "around"};
      param0 = LegalChecks.checkInEnum("LocationType", param0, _set);
      String _oldVal = this._LocationType;
      this._LocationType = param0;
      this._postSet(8, _oldVal, param0);
   }

   public String getPointcut() {
      return this._Pointcut;
   }

   public boolean isPointcutInherited() {
      return false;
   }

   public boolean isPointcutSet() {
      return this._isSet(9);
   }

   public void setPointcut(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Pointcut;
      this._Pointcut = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String[] getIncludes() {
      return this._Includes;
   }

   public boolean isIncludesInherited() {
      return false;
   }

   public boolean isIncludesSet() {
      return this._isSet(10);
   }

   public void setIncludes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Includes;
      this._Includes = param0;
      this._postSet(10, _oldVal, param0);
   }

   public String[] getExcludes() {
      return this._Excludes;
   }

   public boolean isExcludesInherited() {
      return false;
   }

   public boolean isExcludesSet() {
      return this._isSet(11);
   }

   public void setExcludes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Excludes;
      this._Excludes = param0;
      this._postSet(11, _oldVal, param0);
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
         idx = 7;
      }

      try {
         switch (idx) {
            case 7:
               this._Actions = new String[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._DyeMask = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._Excludes = new String[0];
               if (initOne) {
                  break;
               }
            case 10:
               this._Includes = new String[0];
               if (initOne) {
                  break;
               }
            case 8:
               this._LocationType = "before";
               if (initOne) {
                  break;
               }
            case 9:
               this._Pointcut = null;
               if (initOne) {
                  break;
               }
            case 6:
               this._Properties = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._DyeFilteringEnabled = false;
               if (initOne) {
                  break;
               }
            case 3:
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/2.0/weblogic-diagnostics.xsd";
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
            case 6:
               if (s.equals("action")) {
                  return 7;
               }
               break;
            case 7:
               if (s.equals("exclude")) {
                  return 11;
               }

               if (s.equals("include")) {
                  return 10;
               }

               if (s.equals("enabled")) {
                  return 3;
               }
               break;
            case 8:
               if (s.equals("dye-mask")) {
                  return 4;
               }

               if (s.equals("pointcut")) {
                  return 9;
               }
            case 9:
            case 12:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            case 19:
            case 20:
            default:
               break;
            case 10:
               if (s.equals("properties")) {
                  return 6;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 2;
               }
               break;
            case 13:
               if (s.equals("location-type")) {
                  return 8;
               }
               break;
            case 21:
               if (s.equals("dye-filtering-enabled")) {
                  return 5;
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
            case 2:
               return "description";
            case 3:
               return "enabled";
            case 4:
               return "dye-mask";
            case 5:
               return "dye-filtering-enabled";
            case 6:
               return "properties";
            case 7:
               return "action";
            case 8:
               return "location-type";
            case 9:
               return "pointcut";
            case 10:
               return "include";
            case 11:
               return "exclude";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 7:
               return true;
            case 8:
            case 9:
            default:
               return super.isArray(propIndex);
            case 10:
               return true;
            case 11:
               return true;
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
            case 5:
               return true;
            case 6:
               return true;
            case 7:
               return true;
            case 8:
               return true;
            case 9:
               return true;
            case 10:
               return true;
            case 11:
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
      private WLDFInstrumentationMonitorBeanImpl bean;

      protected Helper(WLDFInstrumentationMonitorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Description";
            case 3:
               return "Enabled";
            case 4:
               return "DyeMask";
            case 5:
               return "DyeFilteringEnabled";
            case 6:
               return "Properties";
            case 7:
               return "Actions";
            case 8:
               return "LocationType";
            case 9:
               return "Pointcut";
            case 10:
               return "Includes";
            case 11:
               return "Excludes";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Actions")) {
            return 7;
         } else if (propName.equals("Description")) {
            return 2;
         } else if (propName.equals("DyeMask")) {
            return 4;
         } else if (propName.equals("Excludes")) {
            return 11;
         } else if (propName.equals("Includes")) {
            return 10;
         } else if (propName.equals("LocationType")) {
            return 8;
         } else if (propName.equals("Pointcut")) {
            return 9;
         } else if (propName.equals("Properties")) {
            return 6;
         } else if (propName.equals("DyeFilteringEnabled")) {
            return 5;
         } else {
            return propName.equals("Enabled") ? 3 : super.getPropertyIndex(propName);
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
            if (this.bean.isActionsSet()) {
               buf.append("Actions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getActions())));
            }

            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isDyeMaskSet()) {
               buf.append("DyeMask");
               buf.append(String.valueOf(this.bean.getDyeMask()));
            }

            if (this.bean.isExcludesSet()) {
               buf.append("Excludes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getExcludes())));
            }

            if (this.bean.isIncludesSet()) {
               buf.append("Includes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getIncludes())));
            }

            if (this.bean.isLocationTypeSet()) {
               buf.append("LocationType");
               buf.append(String.valueOf(this.bean.getLocationType()));
            }

            if (this.bean.isPointcutSet()) {
               buf.append("Pointcut");
               buf.append(String.valueOf(this.bean.getPointcut()));
            }

            if (this.bean.isPropertiesSet()) {
               buf.append("Properties");
               buf.append(String.valueOf(this.bean.getProperties()));
            }

            if (this.bean.isDyeFilteringEnabledSet()) {
               buf.append("DyeFilteringEnabled");
               buf.append(String.valueOf(this.bean.isDyeFilteringEnabled()));
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
            WLDFInstrumentationMonitorBeanImpl otherTyped = (WLDFInstrumentationMonitorBeanImpl)other;
            this.computeDiff("Actions", this.bean.getActions(), otherTyped.getActions(), true);
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), true);
            this.computeDiff("DyeMask", this.bean.getDyeMask(), otherTyped.getDyeMask(), true);
            this.computeDiff("Excludes", this.bean.getExcludes(), otherTyped.getExcludes(), true);
            this.computeDiff("Includes", this.bean.getIncludes(), otherTyped.getIncludes(), true);
            this.computeDiff("LocationType", this.bean.getLocationType(), otherTyped.getLocationType(), false);
            this.computeDiff("Pointcut", this.bean.getPointcut(), otherTyped.getPointcut(), false);
            this.computeDiff("Properties", this.bean.getProperties(), otherTyped.getProperties(), true);
            this.computeDiff("DyeFilteringEnabled", this.bean.isDyeFilteringEnabled(), otherTyped.isDyeFilteringEnabled(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFInstrumentationMonitorBeanImpl original = (WLDFInstrumentationMonitorBeanImpl)event.getSourceBean();
            WLDFInstrumentationMonitorBeanImpl proposed = (WLDFInstrumentationMonitorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Actions")) {
                  original.setActions(proposed.getActions());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("DyeMask")) {
                  original.setDyeMask(proposed.getDyeMask());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("Excludes")) {
                  original.setExcludes(proposed.getExcludes());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Includes")) {
                  original.setIncludes(proposed.getIncludes());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("LocationType")) {
                  original.setLocationType(proposed.getLocationType());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("Pointcut")) {
                  original.setPointcut(proposed.getPointcut());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("Properties")) {
                  original.setProperties(proposed.getProperties());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("DyeFilteringEnabled")) {
                  original.setDyeFilteringEnabled(proposed.isDyeFilteringEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("Enabled")) {
                  original.setEnabled(proposed.isEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            WLDFInstrumentationMonitorBeanImpl copy = (WLDFInstrumentationMonitorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("Actions")) && this.bean.isActionsSet()) {
               o = this.bean.getActions();
               copy.setActions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("DyeMask")) && this.bean.isDyeMaskSet()) {
               copy.setDyeMask(this.bean.getDyeMask());
            }

            if ((excludeProps == null || !excludeProps.contains("Excludes")) && this.bean.isExcludesSet()) {
               o = this.bean.getExcludes();
               copy.setExcludes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Includes")) && this.bean.isIncludesSet()) {
               o = this.bean.getIncludes();
               copy.setIncludes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("LocationType")) && this.bean.isLocationTypeSet()) {
               copy.setLocationType(this.bean.getLocationType());
            }

            if ((excludeProps == null || !excludeProps.contains("Pointcut")) && this.bean.isPointcutSet()) {
               copy.setPointcut(this.bean.getPointcut());
            }

            if ((excludeProps == null || !excludeProps.contains("Properties")) && this.bean.isPropertiesSet()) {
               copy.setProperties(this.bean.getProperties());
            }

            if ((excludeProps == null || !excludeProps.contains("DyeFilteringEnabled")) && this.bean.isDyeFilteringEnabledSet()) {
               copy.setDyeFilteringEnabled(this.bean.isDyeFilteringEnabled());
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
