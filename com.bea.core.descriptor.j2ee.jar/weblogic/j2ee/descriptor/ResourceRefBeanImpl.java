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

public class ResourceRefBeanImpl extends AbstractDescriptorBean implements ResourceRefBean, Serializable {
   private String[] _Descriptions;
   private String _Id;
   private InjectionTargetBean[] _InjectionTargets;
   private String _LookupName;
   private String _MappedName;
   private String _ResAuth;
   private String _ResRefName;
   private String _ResSharingScope;
   private String _ResType;
   private static SchemaHelper2 _schemaHelper;

   public ResourceRefBeanImpl() {
      this._initializeProperty(-1);
   }

   public ResourceRefBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ResourceRefBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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

   public String getResRefName() {
      return this._ResRefName;
   }

   public boolean isResRefNameInherited() {
      return false;
   }

   public boolean isResRefNameSet() {
      return this._isSet(1);
   }

   public void setResRefName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResRefName;
      this._ResRefName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getResType() {
      return this._ResType;
   }

   public boolean isResTypeInherited() {
      return false;
   }

   public boolean isResTypeSet() {
      return this._isSet(2);
   }

   public void setResType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResType;
      this._ResType = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getResAuth() {
      return this._ResAuth;
   }

   public boolean isResAuthInherited() {
      return false;
   }

   public boolean isResAuthSet() {
      return this._isSet(3);
   }

   public void setResAuth(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Application", "Container"};
      param0 = LegalChecks.checkInEnum("ResAuth", param0, _set);
      String _oldVal = this._ResAuth;
      this._ResAuth = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getResSharingScope() {
      return this._ResSharingScope;
   }

   public boolean isResSharingScopeInherited() {
      return false;
   }

   public boolean isResSharingScopeSet() {
      return this._isSet(4);
   }

   public void setResSharingScope(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Shareable", "Unshareable"};
      param0 = LegalChecks.checkInEnum("ResSharingScope", param0, _set);
      String _oldVal = this._ResSharingScope;
      this._ResSharingScope = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getMappedName() {
      return this._MappedName;
   }

   public boolean isMappedNameInherited() {
      return false;
   }

   public boolean isMappedNameSet() {
      return this._isSet(5);
   }

   public void setMappedName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MappedName;
      this._MappedName = param0;
      this._postSet(5, _oldVal, param0);
   }

   public void addInjectionTarget(InjectionTargetBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 6)) {
         InjectionTargetBean[] _new;
         if (this._isSet(6)) {
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
      return this._isSet(6);
   }

   public void removeInjectionTarget(InjectionTargetBean param0) {
      this.destroyInjectionTarget(param0);
   }

   public void setInjectionTargets(InjectionTargetBean[] param0) throws InvalidAttributeValueException {
      InjectionTargetBean[] param0 = param0 == null ? new InjectionTargetBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 6)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InjectionTargetBean[] _oldVal = this._InjectionTargets;
      this._InjectionTargets = (InjectionTargetBean[])param0;
      this._postSet(6, _oldVal, param0);
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
         this._checkIsPotentialChild(param0, 6);
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
      return this._isSet(7);
   }

   public void setLookupName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LookupName;
      this._LookupName = param0;
      this._postSet(7, _oldVal, param0);
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
      return this.getResRefName();
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
         case 12:
            if (s.equals("res-ref-name")) {
               return info.compareXpaths(this._getPropertyXpath("res-ref-name"));
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
            case 6:
               this._InjectionTargets = new InjectionTargetBean[0];
               if (initOne) {
                  break;
               }
            case 7:
               this._LookupName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._MappedName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ResAuth = "Container";
               if (initOne) {
                  break;
               }
            case 1:
               this._ResRefName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ResSharingScope = "Shareable";
               if (initOne) {
                  break;
               }
            case 2:
               this._ResType = null;
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
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            default:
               break;
            case 8:
               if (s.equals("res-auth")) {
                  return 3;
               }

               if (s.equals("res-type")) {
                  return 2;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("lookup-name")) {
                  return 7;
               }

               if (s.equals("mapped-name")) {
                  return 5;
               }
               break;
            case 12:
               if (s.equals("res-ref-name")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("injection-target")) {
                  return 6;
               }
               break;
            case 17:
               if (s.equals("res-sharing-scope")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 6:
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
               return "res-ref-name";
            case 2:
               return "res-type";
            case 3:
               return "res-auth";
            case 4:
               return "res-sharing-scope";
            case 5:
               return "mapped-name";
            case 6:
               return "injection-target";
            case 7:
               return "lookup-name";
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
            case 6:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 6:
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
         indices.add("res-ref-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ResourceRefBeanImpl bean;

      protected Helper(ResourceRefBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Descriptions";
            case 1:
               return "ResRefName";
            case 2:
               return "ResType";
            case 3:
               return "ResAuth";
            case 4:
               return "ResSharingScope";
            case 5:
               return "MappedName";
            case 6:
               return "InjectionTargets";
            case 7:
               return "LookupName";
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
            return 6;
         } else if (propName.equals("LookupName")) {
            return 7;
         } else if (propName.equals("MappedName")) {
            return 5;
         } else if (propName.equals("ResAuth")) {
            return 3;
         } else if (propName.equals("ResRefName")) {
            return 1;
         } else if (propName.equals("ResSharingScope")) {
            return 4;
         } else {
            return propName.equals("ResType") ? 2 : super.getPropertyIndex(propName);
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

            if (this.bean.isResAuthSet()) {
               buf.append("ResAuth");
               buf.append(String.valueOf(this.bean.getResAuth()));
            }

            if (this.bean.isResRefNameSet()) {
               buf.append("ResRefName");
               buf.append(String.valueOf(this.bean.getResRefName()));
            }

            if (this.bean.isResSharingScopeSet()) {
               buf.append("ResSharingScope");
               buf.append(String.valueOf(this.bean.getResSharingScope()));
            }

            if (this.bean.isResTypeSet()) {
               buf.append("ResType");
               buf.append(String.valueOf(this.bean.getResType()));
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
            ResourceRefBeanImpl otherTyped = (ResourceRefBeanImpl)other;
            this.computeDiff("Descriptions", this.bean.getDescriptions(), otherTyped.getDescriptions(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InjectionTargets", this.bean.getInjectionTargets(), otherTyped.getInjectionTargets(), false);
            this.computeDiff("LookupName", this.bean.getLookupName(), otherTyped.getLookupName(), false);
            this.computeDiff("MappedName", this.bean.getMappedName(), otherTyped.getMappedName(), false);
            this.computeDiff("ResAuth", this.bean.getResAuth(), otherTyped.getResAuth(), false);
            this.computeDiff("ResRefName", this.bean.getResRefName(), otherTyped.getResRefName(), false);
            this.computeDiff("ResSharingScope", this.bean.getResSharingScope(), otherTyped.getResSharingScope(), false);
            this.computeDiff("ResType", this.bean.getResType(), otherTyped.getResType(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ResourceRefBeanImpl original = (ResourceRefBeanImpl)event.getSourceBean();
            ResourceRefBeanImpl proposed = (ResourceRefBeanImpl)event.getProposedBean();
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
                     original._conditionalUnset(update.isUnsetUpdate(), 6);
                  }
               } else if (prop.equals("LookupName")) {
                  original.setLookupName(proposed.getLookupName());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("MappedName")) {
                  original.setMappedName(proposed.getMappedName());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("ResAuth")) {
                  original.setResAuth(proposed.getResAuth());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ResRefName")) {
                  original.setResRefName(proposed.getResRefName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ResSharingScope")) {
                  original.setResSharingScope(proposed.getResSharingScope());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ResType")) {
                  original.setResType(proposed.getResType());
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
            ResourceRefBeanImpl copy = (ResourceRefBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Descriptions")) && this.bean.isDescriptionsSet()) {
               Object o = this.bean.getDescriptions();
               copy.setDescriptions(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InjectionTargets")) && this.bean.isInjectionTargetsSet() && !copy._isSet(6)) {
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

            if ((excludeProps == null || !excludeProps.contains("ResAuth")) && this.bean.isResAuthSet()) {
               copy.setResAuth(this.bean.getResAuth());
            }

            if ((excludeProps == null || !excludeProps.contains("ResRefName")) && this.bean.isResRefNameSet()) {
               copy.setResRefName(this.bean.getResRefName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResSharingScope")) && this.bean.isResSharingScopeSet()) {
               copy.setResSharingScope(this.bean.getResSharingScope());
            }

            if ((excludeProps == null || !excludeProps.contains("ResType")) && this.bean.isResTypeSet()) {
               copy.setResType(this.bean.getResType());
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
