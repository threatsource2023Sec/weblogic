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
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class PersistenceContextRefBeanImpl extends AbstractDescriptorBean implements PersistenceContextRefBean, Serializable {
   private String[] _Descriptions;
   private String _Id;
   private InjectionTargetBean[] _InjectionTargets;
   private String _MappedName;
   private String _PersistenceContextRefName;
   private String _PersistenceContextType;
   private JavaEEPropertyBean[] _PersistenceProperties;
   private String _PersistenceUnitName;
   private String _SynchronizationType;
   private static SchemaHelper2 _schemaHelper;

   public PersistenceContextRefBeanImpl() {
      this._initializeProperty(-1);
   }

   public PersistenceContextRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public PersistenceContextRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public void setDescriptions(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._Descriptions;
      this._Descriptions = param0;
      this._postSet(0, _oldVal, param0);
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

   public String getPersistenceContextRefName() {
      return this._PersistenceContextRefName;
   }

   public boolean isPersistenceContextRefNameInherited() {
      return false;
   }

   public boolean isPersistenceContextRefNameSet() {
      return this._isSet(1);
   }

   public void setPersistenceContextRefName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistenceContextRefName;
      this._PersistenceContextRefName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getPersistenceUnitName() {
      return this._PersistenceUnitName;
   }

   public boolean isPersistenceUnitNameInherited() {
      return false;
   }

   public boolean isPersistenceUnitNameSet() {
      return this._isSet(2);
   }

   public void setPersistenceUnitName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PersistenceUnitName;
      this._PersistenceUnitName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getPersistenceContextType() {
      return this._PersistenceContextType;
   }

   public boolean isPersistenceContextTypeInherited() {
      return false;
   }

   public boolean isPersistenceContextTypeSet() {
      return this._isSet(3);
   }

   public void setPersistenceContextType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Transaction", "Extended"};
      param0 = LegalChecks.checkInEnum("PersistenceContextType", param0, _set);
      String _oldVal = this._PersistenceContextType;
      this._PersistenceContextType = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getSynchronizationType() {
      return this._SynchronizationType;
   }

   public boolean isSynchronizationTypeInherited() {
      return false;
   }

   public boolean isSynchronizationTypeSet() {
      return this._isSet(4);
   }

   public void setSynchronizationType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Synchronized", "Unsynchronized"};
      param0 = LegalChecks.checkInEnum("SynchronizationType", param0, _set);
      String _oldVal = this._SynchronizationType;
      this._SynchronizationType = param0;
      this._postSet(4, _oldVal, param0);
   }

   public void addPersistenceProperty(JavaEEPropertyBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 5)) {
         JavaEEPropertyBean[] _new;
         if (this._isSet(5)) {
            _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._extendArray(this.getPersistenceProperties(), JavaEEPropertyBean.class, param0));
         } else {
            _new = new JavaEEPropertyBean[]{param0};
         }

         try {
            this.setPersistenceProperties(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JavaEEPropertyBean[] getPersistenceProperties() {
      return this._PersistenceProperties;
   }

   public boolean isPersistencePropertiesInherited() {
      return false;
   }

   public boolean isPersistencePropertiesSet() {
      return this._isSet(5);
   }

   public void removePersistenceProperty(JavaEEPropertyBean param0) {
      this.destroyPersistenceProperty(param0);
   }

   public void setPersistenceProperties(JavaEEPropertyBean[] param0) throws InvalidAttributeValueException {
      JavaEEPropertyBean[] param0 = param0 == null ? new JavaEEPropertyBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 5)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JavaEEPropertyBean[] _oldVal = this._PersistenceProperties;
      this._PersistenceProperties = (JavaEEPropertyBean[])param0;
      this._postSet(5, _oldVal, param0);
   }

   public JavaEEPropertyBean createPersistenceProperty() {
      JavaEEPropertyBeanImpl _val = new JavaEEPropertyBeanImpl(this, -1);

      try {
         this.addPersistenceProperty(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyPersistenceProperty(JavaEEPropertyBean param0) {
      try {
         this._checkIsPotentialChild(param0, 5);
         JavaEEPropertyBean[] _old = this.getPersistenceProperties();
         JavaEEPropertyBean[] _new = (JavaEEPropertyBean[])((JavaEEPropertyBean[])this._getHelper()._removeElement(_old, JavaEEPropertyBean.class, param0));
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
               this.setPersistenceProperties(_new);
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

   public String getMappedName() {
      return this._MappedName;
   }

   public boolean isMappedNameInherited() {
      return false;
   }

   public boolean isMappedNameSet() {
      return this._isSet(6);
   }

   public void setMappedName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappedName;
      this._MappedName = param0;
      this._postSet(6, _oldVal, param0);
   }

   public void addInjectionTarget(InjectionTargetBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 7)) {
         InjectionTargetBean[] _new;
         if (this._isSet(7)) {
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
      return this._isSet(7);
   }

   public void removeInjectionTarget(InjectionTargetBean param0) {
      this.destroyInjectionTarget(param0);
   }

   public void setInjectionTargets(InjectionTargetBean[] param0) throws InvalidAttributeValueException {
      InjectionTargetBean[] param0 = param0 == null ? new InjectionTargetBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 7)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InjectionTargetBean[] _oldVal = this._InjectionTargets;
      this._InjectionTargets = (InjectionTargetBean[])param0;
      this._postSet(7, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 7);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(8);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(8, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getPersistenceContextRefName();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   public boolean _hasKey() {
      return true;
   }

   public boolean _isPropertyAKey(Munger.ReaderEventInfo info) {
      String s = info.getElementName();
      switch (s.length()) {
         case 28:
            if (s.equals("persistence-context-ref-name")) {
               return info.compareXpaths(this._getPropertyXpath("persistence-context-ref-name"));
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
            case 8:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._InjectionTargets = new InjectionTargetBean[0];
               if (initOne) {
                  break;
               }
            case 6:
               this._MappedName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._PersistenceContextRefName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._PersistenceContextType = "Transaction";
               if (initOne) {
                  break;
               }
            case 5:
               this._PersistenceProperties = new JavaEEPropertyBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._PersistenceUnitName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._SynchronizationType = "Synchronized";
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
                  return 8;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("mapped-name")) {
                  return 6;
               }
               break;
            case 16:
               if (s.equals("injection-target")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("persistence-property")) {
                  return 5;
               }

               if (s.equals("synchronization-type")) {
                  return 4;
               }
               break;
            case 21:
               if (s.equals("persistence-unit-name")) {
                  return 2;
               }
               break;
            case 24:
               if (s.equals("persistence-context-type")) {
                  return 3;
               }
               break;
            case 28:
               if (s.equals("persistence-context-ref-name")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 5:
               return new JavaEEPropertyBeanImpl.SchemaHelper2();
            case 7:
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
               return "persistence-context-ref-name";
            case 2:
               return "persistence-unit-name";
            case 3:
               return "persistence-context-type";
            case 4:
               return "synchronization-type";
            case 5:
               return "persistence-property";
            case 6:
               return "mapped-name";
            case 7:
               return "injection-target";
            case 8:
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
            case 7:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 5:
               return true;
            case 7:
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
         indices.add("persistence-context-ref-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private PersistenceContextRefBeanImpl bean;

      protected Helper(PersistenceContextRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "PersistenceContextRefName";
            case 2:
               return "PersistenceUnitName";
            case 3:
               return "PersistenceContextType";
            case 4:
               return "SynchronizationType";
            case 5:
               return "PersistenceProperties";
            case 6:
               return "MappedName";
            case 7:
               return "InjectionTargets";
            case 8:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Descriptions")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 8;
         } else if (propName.equals("InjectionTargets")) {
            return 7;
         } else if (propName.equals("MappedName")) {
            return 6;
         } else if (propName.equals("PersistenceContextRefName")) {
            return 1;
         } else if (propName.equals("PersistenceContextType")) {
            return 3;
         } else if (propName.equals("PersistenceProperties")) {
            return 5;
         } else if (propName.equals("PersistenceUnitName")) {
            return 2;
         } else {
            return propName.equals("SynchronizationType") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getInjectionTargets()));
         iterators.add(new ArrayIterator(this.bean.getPersistenceProperties()));
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

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getInjectionTargets().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInjectionTargets()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMappedNameSet()) {
               buf.append("MappedName");
               buf.append(String.valueOf(this.bean.getMappedName()));
            }

            if (this.bean.isPersistenceContextRefNameSet()) {
               buf.append("PersistenceContextRefName");
               buf.append(String.valueOf(this.bean.getPersistenceContextRefName()));
            }

            if (this.bean.isPersistenceContextTypeSet()) {
               buf.append("PersistenceContextType");
               buf.append(String.valueOf(this.bean.getPersistenceContextType()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getPersistenceProperties().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getPersistenceProperties()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isPersistenceUnitNameSet()) {
               buf.append("PersistenceUnitName");
               buf.append(String.valueOf(this.bean.getPersistenceUnitName()));
            }

            if (this.bean.isSynchronizationTypeSet()) {
               buf.append("SynchronizationType");
               buf.append(String.valueOf(this.bean.getSynchronizationType()));
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
            PersistenceContextRefBeanImpl otherTyped = (PersistenceContextRefBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InjectionTargets", this.bean.getInjectionTargets(), otherTyped.getInjectionTargets(), false);
            this.computeDiff("MappedName", this.bean.getMappedName(), otherTyped.getMappedName(), false);
            this.computeDiff("PersistenceContextRefName", this.bean.getPersistenceContextRefName(), otherTyped.getPersistenceContextRefName(), false);
            this.computeDiff("PersistenceContextType", this.bean.getPersistenceContextType(), otherTyped.getPersistenceContextType(), false);
            this.computeChildDiff("PersistenceProperties", this.bean.getPersistenceProperties(), otherTyped.getPersistenceProperties(), false);
            this.computeDiff("PersistenceUnitName", this.bean.getPersistenceUnitName(), otherTyped.getPersistenceUnitName(), false);
            this.computeDiff("SynchronizationType", this.bean.getSynchronizationType(), otherTyped.getSynchronizationType(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            PersistenceContextRefBeanImpl original = (PersistenceContextRefBeanImpl)event.getSourceBean();
            PersistenceContextRefBeanImpl proposed = (PersistenceContextRefBeanImpl)event.getProposedBean();
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
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
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
                     original._conditionalUnset(update.isUnsetUpdate(), 7);
                  }
               } else if (prop.equals("MappedName")) {
                  original.setMappedName(proposed.getMappedName());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("PersistenceContextRefName")) {
                  original.setPersistenceContextRefName(proposed.getPersistenceContextRefName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("PersistenceContextType")) {
                  original.setPersistenceContextType(proposed.getPersistenceContextType());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("PersistenceProperties")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addPersistenceProperty((JavaEEPropertyBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removePersistenceProperty((JavaEEPropertyBean)update.getRemovedObject());
                  }

                  if (original.getPersistenceProperties() == null || original.getPersistenceProperties().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 5);
                  }
               } else if (prop.equals("PersistenceUnitName")) {
                  original.setPersistenceUnitName(proposed.getPersistenceUnitName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SynchronizationType")) {
                  original.setSynchronizationType(proposed.getSynchronizationType());
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
            PersistenceContextRefBeanImpl copy = (PersistenceContextRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("InjectionTargets")) && this.bean.isInjectionTargetsSet() && !copy._isSet(7)) {
               InjectionTargetBean[] oldInjectionTargets = this.bean.getInjectionTargets();
               InjectionTargetBean[] newInjectionTargets = new InjectionTargetBean[oldInjectionTargets.length];

               for(i = 0; i < newInjectionTargets.length; ++i) {
                  newInjectionTargets[i] = (InjectionTargetBean)((InjectionTargetBean)this.createCopy((AbstractDescriptorBean)oldInjectionTargets[i], includeObsolete));
               }

               copy.setInjectionTargets(newInjectionTargets);
            }

            if ((excludeProps == null || !excludeProps.contains("MappedName")) && this.bean.isMappedNameSet()) {
               copy.setMappedName(this.bean.getMappedName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceContextRefName")) && this.bean.isPersistenceContextRefNameSet()) {
               copy.setPersistenceContextRefName(this.bean.getPersistenceContextRefName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceContextType")) && this.bean.isPersistenceContextTypeSet()) {
               copy.setPersistenceContextType(this.bean.getPersistenceContextType());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceProperties")) && this.bean.isPersistencePropertiesSet() && !copy._isSet(5)) {
               JavaEEPropertyBean[] oldPersistenceProperties = this.bean.getPersistenceProperties();
               JavaEEPropertyBean[] newPersistenceProperties = new JavaEEPropertyBean[oldPersistenceProperties.length];

               for(i = 0; i < newPersistenceProperties.length; ++i) {
                  newPersistenceProperties[i] = (JavaEEPropertyBean)((JavaEEPropertyBean)this.createCopy((AbstractDescriptorBean)oldPersistenceProperties[i], includeObsolete));
               }

               copy.setPersistenceProperties(newPersistenceProperties);
            }

            if ((excludeProps == null || !excludeProps.contains("PersistenceUnitName")) && this.bean.isPersistenceUnitNameSet()) {
               copy.setPersistenceUnitName(this.bean.getPersistenceUnitName());
            }

            if ((excludeProps == null || !excludeProps.contains("SynchronizationType")) && this.bean.isSynchronizationTypeSet()) {
               copy.setSynchronizationType(this.bean.getSynchronizationType());
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
         this.inferSubTree(this.bean.getPersistenceProperties(), clazz, annotation);
      }
   }
}
