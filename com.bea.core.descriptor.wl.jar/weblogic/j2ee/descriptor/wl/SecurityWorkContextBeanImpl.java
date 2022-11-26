package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SecurityWorkContextBeanImpl extends AbstractDescriptorBean implements SecurityWorkContextBean, Serializable {
   private AnonPrincipalBean _CallerPrincipalDefaultMapped;
   private InboundCallerPrincipalMappingBean[] _CallerPrincipalMappings;
   private String _GroupPrincipalDefaultMapped;
   private InboundGroupPrincipalMappingBean[] _GroupPrincipalMappings;
   private String _Id;
   private boolean _InboundMappingRequired;
   private static SchemaHelper2 _schemaHelper;

   public SecurityWorkContextBeanImpl() {
      this._initializeProperty(-1);
   }

   public SecurityWorkContextBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SecurityWorkContextBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isInboundMappingRequired() {
      return this._InboundMappingRequired;
   }

   public boolean isInboundMappingRequiredInherited() {
      return false;
   }

   public boolean isInboundMappingRequiredSet() {
      return this._isSet(0);
   }

   public void setInboundMappingRequired(boolean param0) {
      boolean _oldVal = this._InboundMappingRequired;
      this._InboundMappingRequired = param0;
      this._postSet(0, _oldVal, param0);
   }

   public AnonPrincipalBean getCallerPrincipalDefaultMapped() {
      return this._CallerPrincipalDefaultMapped;
   }

   public boolean isCallerPrincipalDefaultMappedInherited() {
      return false;
   }

   public boolean isCallerPrincipalDefaultMappedSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getCallerPrincipalDefaultMapped());
   }

   public void setCallerPrincipalDefaultMapped(AnonPrincipalBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      AnonPrincipalBean _oldVal = this._CallerPrincipalDefaultMapped;
      this._CallerPrincipalDefaultMapped = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addCallerPrincipalMapping(InboundCallerPrincipalMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         InboundCallerPrincipalMappingBean[] _new;
         if (this._isSet(2)) {
            _new = (InboundCallerPrincipalMappingBean[])((InboundCallerPrincipalMappingBean[])this._getHelper()._extendArray(this.getCallerPrincipalMappings(), InboundCallerPrincipalMappingBean.class, param0));
         } else {
            _new = new InboundCallerPrincipalMappingBean[]{param0};
         }

         try {
            this.setCallerPrincipalMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InboundCallerPrincipalMappingBean[] getCallerPrincipalMappings() {
      return this._CallerPrincipalMappings;
   }

   public boolean isCallerPrincipalMappingsInherited() {
      return false;
   }

   public boolean isCallerPrincipalMappingsSet() {
      return this._isSet(2);
   }

   public void removeCallerPrincipalMapping(InboundCallerPrincipalMappingBean param0) {
      this.destroyCallerPrincipalMapping(param0);
   }

   public void setCallerPrincipalMappings(InboundCallerPrincipalMappingBean[] param0) throws InvalidAttributeValueException {
      InboundCallerPrincipalMappingBean[] param0 = param0 == null ? new InboundCallerPrincipalMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InboundCallerPrincipalMappingBean[] _oldVal = this._CallerPrincipalMappings;
      this._CallerPrincipalMappings = (InboundCallerPrincipalMappingBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public InboundCallerPrincipalMappingBean createCallerPrincipalMapping(String param0) {
      InboundCallerPrincipalMappingBeanImpl _val = new InboundCallerPrincipalMappingBeanImpl(this, -1);

      try {
         _val.setEisCallerPrincipal(param0);
         this.addCallerPrincipalMapping(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public InboundCallerPrincipalMappingBean lookupCallerPrincipalMapping(String param0) {
      Object[] aary = (Object[])this._CallerPrincipalMappings;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      InboundCallerPrincipalMappingBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (InboundCallerPrincipalMappingBeanImpl)it.previous();
      } while(!bean.getEisCallerPrincipal().equals(param0));

      return bean;
   }

   public void destroyCallerPrincipalMapping(InboundCallerPrincipalMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         InboundCallerPrincipalMappingBean[] _old = this.getCallerPrincipalMappings();
         InboundCallerPrincipalMappingBean[] _new = (InboundCallerPrincipalMappingBean[])((InboundCallerPrincipalMappingBean[])this._getHelper()._removeElement(_old, InboundCallerPrincipalMappingBean.class, param0));
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
               this.setCallerPrincipalMappings(_new);
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

   public String getGroupPrincipalDefaultMapped() {
      return this._GroupPrincipalDefaultMapped;
   }

   public boolean isGroupPrincipalDefaultMappedInherited() {
      return false;
   }

   public boolean isGroupPrincipalDefaultMappedSet() {
      return this._isSet(3);
   }

   public void setGroupPrincipalDefaultMapped(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GroupPrincipalDefaultMapped;
      this._GroupPrincipalDefaultMapped = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addGroupPrincipalMapping(InboundGroupPrincipalMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         InboundGroupPrincipalMappingBean[] _new;
         if (this._isSet(4)) {
            _new = (InboundGroupPrincipalMappingBean[])((InboundGroupPrincipalMappingBean[])this._getHelper()._extendArray(this.getGroupPrincipalMappings(), InboundGroupPrincipalMappingBean.class, param0));
         } else {
            _new = new InboundGroupPrincipalMappingBean[]{param0};
         }

         try {
            this.setGroupPrincipalMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InboundGroupPrincipalMappingBean[] getGroupPrincipalMappings() {
      return this._GroupPrincipalMappings;
   }

   public boolean isGroupPrincipalMappingsInherited() {
      return false;
   }

   public boolean isGroupPrincipalMappingsSet() {
      return this._isSet(4);
   }

   public void removeGroupPrincipalMapping(InboundGroupPrincipalMappingBean param0) {
      this.destroyGroupPrincipalMapping(param0);
   }

   public void setGroupPrincipalMappings(InboundGroupPrincipalMappingBean[] param0) throws InvalidAttributeValueException {
      InboundGroupPrincipalMappingBean[] param0 = param0 == null ? new InboundGroupPrincipalMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InboundGroupPrincipalMappingBean[] _oldVal = this._GroupPrincipalMappings;
      this._GroupPrincipalMappings = (InboundGroupPrincipalMappingBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public InboundGroupPrincipalMappingBean createGroupPrincipalMapping(String param0) {
      InboundGroupPrincipalMappingBeanImpl _val = new InboundGroupPrincipalMappingBeanImpl(this, -1);

      try {
         _val.setEisGroupPrincipal(param0);
         this.addGroupPrincipalMapping(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public InboundGroupPrincipalMappingBean lookupGroupPrincipalMapping(String param0) {
      Object[] aary = (Object[])this._GroupPrincipalMappings;
      int size = aary.length;
      ListIterator it = Arrays.asList(aary).listIterator(size);

      InboundGroupPrincipalMappingBeanImpl bean;
      do {
         if (!it.hasPrevious()) {
            return null;
         }

         bean = (InboundGroupPrincipalMappingBeanImpl)it.previous();
      } while(!bean.getEisGroupPrincipal().equals(param0));

      return bean;
   }

   public void destroyGroupPrincipalMapping(InboundGroupPrincipalMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         InboundGroupPrincipalMappingBean[] _old = this.getGroupPrincipalMappings();
         InboundGroupPrincipalMappingBean[] _new = (InboundGroupPrincipalMappingBean[])((InboundGroupPrincipalMappingBean[])this._getHelper()._removeElement(_old, InboundGroupPrincipalMappingBean.class, param0));
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
               this.setGroupPrincipalMappings(_new);
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
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
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
      return super._isAnythingSet() || this.isCallerPrincipalDefaultMappedSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._CallerPrincipalDefaultMapped = new AnonPrincipalBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._CallerPrincipalDefaultMapped);
               if (initOne) {
                  break;
               }
            case 2:
               this._CallerPrincipalMappings = new InboundCallerPrincipalMappingBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._GroupPrincipalDefaultMapped = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._GroupPrincipalMappings = new InboundGroupPrincipalMappingBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._InboundMappingRequired = false;
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
                  return 5;
               }
               break;
            case 23:
               if (s.equals("group-principal-mapping")) {
                  return 4;
               }
               break;
            case 24:
               if (s.equals("caller-principal-mapping")) {
                  return 2;
               }

               if (s.equals("inbound-mapping-required")) {
                  return 0;
               }
               break;
            case 30:
               if (s.equals("group-principal-default-mapped")) {
                  return 3;
               }
               break;
            case 31:
               if (s.equals("caller-principal-default-mapped")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new AnonPrincipalBeanImpl.SchemaHelper2();
            case 2:
               return new InboundCallerPrincipalMappingBeanImpl.SchemaHelper2();
            case 3:
            default:
               return super.getSchemaHelper(propIndex);
            case 4:
               return new InboundGroupPrincipalMappingBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "inbound-mapping-required";
            case 1:
               return "caller-principal-default-mapped";
            case 2:
               return "caller-principal-mapping";
            case 3:
               return "group-principal-default-mapped";
            case 4:
               return "group-principal-mapping";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            case 2:
               return true;
            case 3:
            default:
               return super.isBean(propIndex);
            case 4:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
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
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SecurityWorkContextBeanImpl bean;

      protected Helper(SecurityWorkContextBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InboundMappingRequired";
            case 1:
               return "CallerPrincipalDefaultMapped";
            case 2:
               return "CallerPrincipalMappings";
            case 3:
               return "GroupPrincipalDefaultMapped";
            case 4:
               return "GroupPrincipalMappings";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CallerPrincipalDefaultMapped")) {
            return 1;
         } else if (propName.equals("CallerPrincipalMappings")) {
            return 2;
         } else if (propName.equals("GroupPrincipalDefaultMapped")) {
            return 3;
         } else if (propName.equals("GroupPrincipalMappings")) {
            return 4;
         } else if (propName.equals("Id")) {
            return 5;
         } else {
            return propName.equals("InboundMappingRequired") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCallerPrincipalDefaultMapped() != null) {
            iterators.add(new ArrayIterator(new AnonPrincipalBean[]{this.bean.getCallerPrincipalDefaultMapped()}));
         }

         iterators.add(new ArrayIterator(this.bean.getCallerPrincipalMappings()));
         iterators.add(new ArrayIterator(this.bean.getGroupPrincipalMappings()));
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
            childValue = this.computeChildHashValue(this.bean.getCallerPrincipalDefaultMapped());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getCallerPrincipalMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCallerPrincipalMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isGroupPrincipalDefaultMappedSet()) {
               buf.append("GroupPrincipalDefaultMapped");
               buf.append(String.valueOf(this.bean.getGroupPrincipalDefaultMapped()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getGroupPrincipalMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getGroupPrincipalMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isInboundMappingRequiredSet()) {
               buf.append("InboundMappingRequired");
               buf.append(String.valueOf(this.bean.isInboundMappingRequired()));
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
            SecurityWorkContextBeanImpl otherTyped = (SecurityWorkContextBeanImpl)other;
            this.computeSubDiff("CallerPrincipalDefaultMapped", this.bean.getCallerPrincipalDefaultMapped(), otherTyped.getCallerPrincipalDefaultMapped());
            this.computeChildDiff("CallerPrincipalMappings", this.bean.getCallerPrincipalMappings(), otherTyped.getCallerPrincipalMappings(), false);
            this.computeDiff("GroupPrincipalDefaultMapped", this.bean.getGroupPrincipalDefaultMapped(), otherTyped.getGroupPrincipalDefaultMapped(), false);
            this.computeChildDiff("GroupPrincipalMappings", this.bean.getGroupPrincipalMappings(), otherTyped.getGroupPrincipalMappings(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("InboundMappingRequired", this.bean.isInboundMappingRequired(), otherTyped.isInboundMappingRequired(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SecurityWorkContextBeanImpl original = (SecurityWorkContextBeanImpl)event.getSourceBean();
            SecurityWorkContextBeanImpl proposed = (SecurityWorkContextBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CallerPrincipalDefaultMapped")) {
                  if (type == 2) {
                     original.setCallerPrincipalDefaultMapped((AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)proposed.getCallerPrincipalDefaultMapped()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("CallerPrincipalDefaultMapped", (DescriptorBean)original.getCallerPrincipalDefaultMapped());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("CallerPrincipalMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCallerPrincipalMapping((InboundCallerPrincipalMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCallerPrincipalMapping((InboundCallerPrincipalMappingBean)update.getRemovedObject());
                  }

                  if (original.getCallerPrincipalMappings() == null || original.getCallerPrincipalMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("GroupPrincipalDefaultMapped")) {
                  original.setGroupPrincipalDefaultMapped(proposed.getGroupPrincipalDefaultMapped());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("GroupPrincipalMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addGroupPrincipalMapping((InboundGroupPrincipalMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeGroupPrincipalMapping((InboundGroupPrincipalMappingBean)update.getRemovedObject());
                  }

                  if (original.getGroupPrincipalMappings() == null || original.getGroupPrincipalMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("InboundMappingRequired")) {
                  original.setInboundMappingRequired(proposed.isInboundMappingRequired());
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
            SecurityWorkContextBeanImpl copy = (SecurityWorkContextBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CallerPrincipalDefaultMapped")) && this.bean.isCallerPrincipalDefaultMappedSet() && !copy._isSet(1)) {
               Object o = this.bean.getCallerPrincipalDefaultMapped();
               copy.setCallerPrincipalDefaultMapped((AnonPrincipalBean)null);
               copy.setCallerPrincipalDefaultMapped(o == null ? null : (AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("CallerPrincipalMappings")) && this.bean.isCallerPrincipalMappingsSet() && !copy._isSet(2)) {
               InboundCallerPrincipalMappingBean[] oldCallerPrincipalMappings = this.bean.getCallerPrincipalMappings();
               InboundCallerPrincipalMappingBean[] newCallerPrincipalMappings = new InboundCallerPrincipalMappingBean[oldCallerPrincipalMappings.length];

               for(i = 0; i < newCallerPrincipalMappings.length; ++i) {
                  newCallerPrincipalMappings[i] = (InboundCallerPrincipalMappingBean)((InboundCallerPrincipalMappingBean)this.createCopy((AbstractDescriptorBean)oldCallerPrincipalMappings[i], includeObsolete));
               }

               copy.setCallerPrincipalMappings(newCallerPrincipalMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("GroupPrincipalDefaultMapped")) && this.bean.isGroupPrincipalDefaultMappedSet()) {
               copy.setGroupPrincipalDefaultMapped(this.bean.getGroupPrincipalDefaultMapped());
            }

            if ((excludeProps == null || !excludeProps.contains("GroupPrincipalMappings")) && this.bean.isGroupPrincipalMappingsSet() && !copy._isSet(4)) {
               InboundGroupPrincipalMappingBean[] oldGroupPrincipalMappings = this.bean.getGroupPrincipalMappings();
               InboundGroupPrincipalMappingBean[] newGroupPrincipalMappings = new InboundGroupPrincipalMappingBean[oldGroupPrincipalMappings.length];

               for(i = 0; i < newGroupPrincipalMappings.length; ++i) {
                  newGroupPrincipalMappings[i] = (InboundGroupPrincipalMappingBean)((InboundGroupPrincipalMappingBean)this.createCopy((AbstractDescriptorBean)oldGroupPrincipalMappings[i], includeObsolete));
               }

               copy.setGroupPrincipalMappings(newGroupPrincipalMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InboundMappingRequired")) && this.bean.isInboundMappingRequiredSet()) {
               copy.setInboundMappingRequired(this.bean.isInboundMappingRequired());
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
         this.inferSubTree(this.bean.getCallerPrincipalDefaultMapped(), clazz, annotation);
         this.inferSubTree(this.bean.getCallerPrincipalMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getGroupPrincipalMappings(), clazz, annotation);
      }
   }
}
