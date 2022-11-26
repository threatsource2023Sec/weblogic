package weblogic.j2ee.descriptor;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class EnvEntryBeanImpl extends AbstractDescriptorBean implements EnvEntryBean, Serializable {
   private String[] _Descriptions;
   private String _EnvEntryName;
   private String _EnvEntryType;
   private String _EnvEntryValue;
   private String _Id;
   private InjectionTargetBean[] _InjectionTargets;
   private String _LookupName;
   private String _MappedName;
   private static SchemaHelper2 _schemaHelper;

   public EnvEntryBeanImpl() {
      this._initializeProperty(-1);
   }

   public EnvEntryBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public EnvEntryBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String[] getDescriptions() {
      return this._Descriptions;
   }

   public boolean isDescriptionsInherited() {
      return false;
   }

   public boolean isDescriptionsSet() {
      return this._isSet(0);
   }

   public void addDescription(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(0)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getDescriptions(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setDescriptions(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeDescription(String param0) {
      String[] _old = this.getDescriptions();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDescriptions(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDescriptions(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getEnvEntryName() {
      return this._EnvEntryName;
   }

   public boolean isEnvEntryNameInherited() {
      return false;
   }

   public boolean isEnvEntryNameSet() {
      return this._isSet(1);
   }

   public void setEnvEntryName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EnvEntryName;
      this._EnvEntryName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getEnvEntryType() {
      return this._EnvEntryType;
   }

   public boolean isEnvEntryTypeInherited() {
      return false;
   }

   public boolean isEnvEntryTypeSet() {
      return this._isSet(2);
   }

   public void setEnvEntryType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EnvEntryType;
      this._EnvEntryType = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getEnvEntryValue() {
      return this._EnvEntryValue;
   }

   public boolean isEnvEntryValueInherited() {
      return false;
   }

   public boolean isEnvEntryValueSet() {
      return this._isSet(3);
   }

   public void setEnvEntryValue(String param0) {
      String _oldVal = this._EnvEntryValue;
      this._EnvEntryValue = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getMappedName() {
      return this._MappedName;
   }

   public boolean isMappedNameInherited() {
      return false;
   }

   public boolean isMappedNameSet() {
      return this._isSet(4);
   }

   public void setMappedName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappedName;
      this._MappedName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void addInjectionTarget(InjectionTargetBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         InjectionTargetBean[] _new;
         if (this._isSet(5)) {
            _new = (InjectionTargetBean[])((InjectionTargetBean[])this._getHelper()._extendArray(this.getInjectionTargets(), InjectionTargetBean.class, param0));
         } else {
            _new = new InjectionTargetBean[]{param0};
         }

         try {
            this.setInjectionTargets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InjectionTargetBean[] getInjectionTargets() {
      return this._InjectionTargets;
   }

   public boolean isInjectionTargetsInherited() {
      return false;
   }

   public boolean isInjectionTargetsSet() {
      return this._isSet(5);
   }

   public void removeInjectionTarget(InjectionTargetBean param0) {
      this.destroyInjectionTarget(param0);
   }

   public void setInjectionTargets(InjectionTargetBean[] param0) throws InvalidAttributeValueException {
      InjectionTargetBean[] param0 = param0 == null ? new InjectionTargetBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InjectionTargetBean[] _oldVal = this._InjectionTargets;
      this._InjectionTargets = (InjectionTargetBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public InjectionTargetBean createInjectionTarget() {
      InjectionTargetBeanImpl _val = new InjectionTargetBeanImpl(this, -1);

      try {
         this.addInjectionTarget(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInjectionTarget(InjectionTargetBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         InjectionTargetBean[] _old = this.getInjectionTargets();
         InjectionTargetBean[] _new = (InjectionTargetBean[])((InjectionTargetBean[])this._getHelper()._removeElement(_old, InjectionTargetBean.class, param0));
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
               this.setInjectionTargets(_new);
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

   public String getLookupName() {
      return this._LookupName;
   }

   public boolean isLookupNameInherited() {
      return false;
   }

   public boolean isLookupNameSet() {
      return this._isSet(6);
   }

   public void setLookupName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LookupName;
      this._LookupName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(7);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(7, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEnvEntryName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
      EnvEntryValidator.validateEnvEntry(this);
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 14:
            if (s.equals("env-entry-name")) {
               return info.compareXpaths(this._getPropertyXpath("env-entry-name"));
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Descriptions = new String[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._EnvEntryName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._EnvEntryType = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._EnvEntryValue = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._InjectionTargets = new InjectionTargetBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._LookupName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._MappedName = null;
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

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 7;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 12:
            case 13:
            default:
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("lookup-name")) {
                  return 6;
               }

               if (s.equals("mapped-name")) {
                  return 4;
               }
               break;
            case 14:
               if (s.equals("env-entry-name")) {
                  return 1;
               }

               if (s.equals("env-entry-type")) {
                  return 2;
               }
               break;
            case 15:
               if (s.equals("env-entry-value")) {
                  return 3;
               }
               break;
            case 16:
               if (s.equals("injection-target")) {
                  return 5;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new InjectionTargetBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "env-entry-name";
            case 2:
               return "env-entry-type";
            case 3:
               return "env-entry-value";
            case 4:
               return "mapped-name";
            case 5:
               return "injection-target";
            case 6:
               return "lookup-name";
            case 7:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
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
         indices.add("env-entry-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private EnvEntryBeanImpl bean;

      protected Helper(EnvEntryBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "EnvEntryName";
            case 2:
               return "EnvEntryType";
            case 3:
               return "EnvEntryValue";
            case 4:
               return "MappedName";
            case 5:
               return "InjectionTargets";
            case 6:
               return "LookupName";
            case 7:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("EnvEntryName")) {
            return 1;
         } else if (propName.equals("EnvEntryType")) {
            return 2;
         } else if (propName.equals("EnvEntryValue")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 7;
         } else if (propName.equals("InjectionTargets")) {
            return 5;
         } else if (propName.equals("LookupName")) {
            return 6;
         } else {
            return propName.equals("MappedName") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getInjectionTargets()));
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
            if (this.bean.isDescriptionsSet()) {
               buf.append("Descriptions");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDescriptions())));
            }

            if (this.bean.isEnvEntryNameSet()) {
               buf.append("EnvEntryName");
               buf.append(String.valueOf(this.bean.getEnvEntryName()));
            }

            if (this.bean.isEnvEntryTypeSet()) {
               buf.append("EnvEntryType");
               buf.append(String.valueOf(this.bean.getEnvEntryType()));
            }

            if (this.bean.isEnvEntryValueSet()) {
               buf.append("EnvEntryValue");
               buf.append(String.valueOf(this.bean.getEnvEntryValue()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getInjectionTargets().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInjectionTargets()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLookupNameSet()) {
               buf.append("LookupName");
               buf.append(String.valueOf(this.bean.getLookupName()));
            }

            if (this.bean.isMappedNameSet()) {
               buf.append("MappedName");
               buf.append(String.valueOf(this.bean.getMappedName()));
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
            EnvEntryBeanImpl otherTyped = (EnvEntryBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("EnvEntryName", this.bean.getEnvEntryName(), otherTyped.getEnvEntryName(), false);
            this.computeDiff("EnvEntryType", this.bean.getEnvEntryType(), otherTyped.getEnvEntryType(), false);
            this.computeDiff("EnvEntryValue", this.bean.getEnvEntryValue(), otherTyped.getEnvEntryValue(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InjectionTargets", this.bean.getInjectionTargets(), otherTyped.getInjectionTargets(), false);
            this.computeDiff("LookupName", this.bean.getLookupName(), otherTyped.getLookupName(), false);
            this.computeDiff("MappedName", this.bean.getMappedName(), otherTyped.getMappedName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            EnvEntryBeanImpl original = (EnvEntryBeanImpl)event.getSourceBean();
            EnvEntryBeanImpl proposed = (EnvEntryBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Descriptions")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addDescription((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeDescription((String)update.getRemovedObject());
                  }

                  if (original.getDescriptions() == null || original.getDescriptions().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
                  }
               } else if (prop.equals("EnvEntryName")) {
                  original.setEnvEntryName(proposed.getEnvEntryName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("EnvEntryType")) {
                  original.setEnvEntryType(proposed.getEnvEntryType());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("EnvEntryValue")) {
                  original.setEnvEntryValue(proposed.getEnvEntryValue());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("InjectionTargets")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInjectionTarget((InjectionTargetBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInjectionTarget((InjectionTargetBean)update.getRemovedObject());
                  }

                  if (original.getInjectionTargets() == null || original.getInjectionTargets().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("LookupName")) {
                  original.setLookupName(proposed.getLookupName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("MappedName")) {
                  original.setMappedName(proposed.getMappedName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            EnvEntryBeanImpl copy = (EnvEntryBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntryName")) && this.bean.isEnvEntryNameSet()) {
               copy.setEnvEntryName(this.bean.getEnvEntryName());
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntryType")) && this.bean.isEnvEntryTypeSet()) {
               copy.setEnvEntryType(this.bean.getEnvEntryType());
            }

            if ((excludeProps == null || !excludeProps.contains("EnvEntryValue")) && this.bean.isEnvEntryValueSet()) {
               copy.setEnvEntryValue(this.bean.getEnvEntryValue());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InjectionTargets")) && this.bean.isInjectionTargetsSet() && !copy._isSet(5)) {
               InjectionTargetBean[] oldInjectionTargets = this.bean.getInjectionTargets();
               InjectionTargetBean[] newInjectionTargets = new InjectionTargetBean[oldInjectionTargets.length];

               for(int i = 0; i < newInjectionTargets.length; ++i) {
                  newInjectionTargets[i] = (InjectionTargetBean)((InjectionTargetBean)this.createCopy((AbstractDescriptorBean)oldInjectionTargets[i], includeObsolete));
               }

               copy.setInjectionTargets(newInjectionTargets);
            }

            if ((excludeProps == null || !excludeProps.contains("LookupName")) && this.bean.isLookupNameSet()) {
               copy.setLookupName(this.bean.getLookupName());
            }

            if ((excludeProps == null || !excludeProps.contains("MappedName")) && this.bean.isMappedNameSet()) {
               copy.setMappedName(this.bean.getMappedName());
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
         this.inferSubTree(this.bean.getInjectionTargets(), clazz, annotation);
      }
   }
}
