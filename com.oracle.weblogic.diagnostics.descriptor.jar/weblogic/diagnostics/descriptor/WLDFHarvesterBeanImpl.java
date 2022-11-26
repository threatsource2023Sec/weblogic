package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLDFHarvesterBeanImpl extends WLDFBeanImpl implements WLDFHarvesterBean, Serializable {
   private boolean _Enabled;
   private WLDFHarvestedTypeBean[] _HarvestedTypes;
   private long _SamplePeriod;
   private static SchemaHelper2 _schemaHelper;

   public WLDFHarvesterBeanImpl() {
      this._initializeProperty(-1);
   }

   public WLDFHarvesterBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WLDFHarvesterBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public long getSamplePeriod() {
      return this._SamplePeriod;
   }

   public boolean isSamplePeriodInherited() {
      return false;
   }

   public boolean isSamplePeriodSet() {
      return this._isSet(3);
   }

   public void setSamplePeriod(long param0) throws IllegalArgumentException {
      LegalChecks.checkMin("SamplePeriod", param0, 1000L);
      long _oldVal = this._SamplePeriod;
      this._SamplePeriod = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addHarvestedType(WLDFHarvestedTypeBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         WLDFHarvestedTypeBean[] _new;
         if (this._isSet(4)) {
            _new = (WLDFHarvestedTypeBean[])((WLDFHarvestedTypeBean[])this._getHelper()._extendArray(this.getHarvestedTypes(), WLDFHarvestedTypeBean.class, param0));
         } else {
            _new = new WLDFHarvestedTypeBean[]{param0};
         }

         try {
            this.setHarvestedTypes(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public WLDFHarvestedTypeBean[] getHarvestedTypes() {
      return this._HarvestedTypes;
   }

   public boolean isHarvestedTypesInherited() {
      return false;
   }

   public boolean isHarvestedTypesSet() {
      return this._isSet(4);
   }

   public void removeHarvestedType(WLDFHarvestedTypeBean param0) {
      this.destroyHarvestedType(param0);
   }

   public void setHarvestedTypes(WLDFHarvestedTypeBean[] param0) throws InvalidAttributeValueException {
      WLDFHarvestedTypeBean[] param0 = param0 == null ? new WLDFHarvestedTypeBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      WLDFHarvestedTypeBean[] _oldVal = this._HarvestedTypes;
      this._HarvestedTypes = (WLDFHarvestedTypeBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public WLDFHarvestedTypeBean createHarvestedType(String param0) {
      WLDFHarvestedTypeBeanImpl lookup = (WLDFHarvestedTypeBeanImpl)this.lookupHarvestedType(param0);
      if (lookup != null && lookup._isTransient() && lookup._isSynthetic()) {
         throw new BeanAlreadyExistsException("Bean already exists: " + lookup);
      } else {
         WLDFHarvestedTypeBeanImpl _val = new WLDFHarvestedTypeBeanImpl(this, -1);

         try {
            _val.setName(param0);
            this.addHarvestedType(_val);
            return _val;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      }
   }

   public void destroyHarvestedType(WLDFHarvestedTypeBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         WLDFHarvestedTypeBean[] _old = this.getHarvestedTypes();
         WLDFHarvestedTypeBean[] _new = (WLDFHarvestedTypeBean[])((WLDFHarvestedTypeBean[])this._getHelper()._removeElement(_old, WLDFHarvestedTypeBean.class, param0));
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
               this.setHarvestedTypes(_new);
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

   public WLDFHarvestedTypeBean lookupHarvestedType(String param0) {
      Object[] aary = (Object[])this._HarvestedTypes;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      WLDFHarvestedTypeBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (WLDFHarvestedTypeBeanImpl)it.previous();
      } while(!bean.getName().equals(param0));

      return bean;
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
               this._HarvestedTypes = new WLDFHarvestedTypeBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._SamplePeriod = 300000L;
               if (initOne) {
                  break;
               }
            case 2:
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
            case 7:
               if (s.equals("enabled")) {
                  return 2;
               }
               break;
            case 13:
               if (s.equals("sample-period")) {
                  return 3;
               }
               break;
            case 14:
               if (s.equals("harvested-type")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new WLDFHarvestedTypeBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "enabled";
            case 3:
               return "sample-period";
            case 4:
               return "harvested-type";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
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
      private WLDFHarvesterBeanImpl bean;

      protected Helper(WLDFHarvesterBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Enabled";
            case 3:
               return "SamplePeriod";
            case 4:
               return "HarvestedTypes";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HarvestedTypes")) {
            return 4;
         } else if (propName.equals("SamplePeriod")) {
            return 3;
         } else {
            return propName.equals("Enabled") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getHarvestedTypes()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getHarvestedTypes().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getHarvestedTypes()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isSamplePeriodSet()) {
               buf.append("SamplePeriod");
               buf.append(String.valueOf(this.bean.getSamplePeriod()));
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
            WLDFHarvesterBeanImpl otherTyped = (WLDFHarvesterBeanImpl)other;
            this.computeChildDiff("HarvestedTypes", this.bean.getHarvestedTypes(), otherTyped.getHarvestedTypes(), true);
            this.computeDiff("SamplePeriod", this.bean.getSamplePeriod(), otherTyped.getSamplePeriod(), true);
            this.computeDiff("Enabled", this.bean.isEnabled(), otherTyped.isEnabled(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFHarvesterBeanImpl original = (WLDFHarvesterBeanImpl)event.getSourceBean();
            WLDFHarvesterBeanImpl proposed = (WLDFHarvesterBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HarvestedTypes")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addHarvestedType((WLDFHarvestedTypeBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHarvestedType((WLDFHarvestedTypeBean)update.getRemovedObject());
                  }

                  if (original.getHarvestedTypes() == null || original.getHarvestedTypes().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("SamplePeriod")) {
                  original.setSamplePeriod(proposed.getSamplePeriod());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            WLDFHarvesterBeanImpl copy = (WLDFHarvesterBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HarvestedTypes")) && this.bean.isHarvestedTypesSet() && !copy._isSet(4)) {
               WLDFHarvestedTypeBean[] oldHarvestedTypes = this.bean.getHarvestedTypes();
               WLDFHarvestedTypeBean[] newHarvestedTypes = new WLDFHarvestedTypeBean[oldHarvestedTypes.length];

               for(int i = 0; i < newHarvestedTypes.length; ++i) {
                  newHarvestedTypes[i] = (WLDFHarvestedTypeBean)((WLDFHarvestedTypeBean)this.createCopy((AbstractDescriptorBean)oldHarvestedTypes[i], includeObsolete));
               }

               copy.setHarvestedTypes(newHarvestedTypes);
            }

            if ((excludeProps == null || !excludeProps.contains("SamplePeriod")) && this.bean.isSamplePeriodSet()) {
               copy.setSamplePeriod(this.bean.getSamplePeriod());
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
         this.inferSubTree(this.bean.getHarvestedTypes(), clazz, annotation);
      }
   }
}
