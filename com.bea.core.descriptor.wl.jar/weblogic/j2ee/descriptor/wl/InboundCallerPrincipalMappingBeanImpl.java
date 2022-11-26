package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
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

public class InboundCallerPrincipalMappingBeanImpl extends AbstractDescriptorBean implements InboundCallerPrincipalMappingBean, Serializable {
   private String _EisCallerPrincipal;
   private String _Id;
   private AnonPrincipalBean _MappedCallerPrincipal;
   private static SchemaHelper2 _schemaHelper;

   public InboundCallerPrincipalMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public InboundCallerPrincipalMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public InboundCallerPrincipalMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getEisCallerPrincipal() {
      return this._EisCallerPrincipal;
   }

   public boolean isEisCallerPrincipalInherited() {
      return false;
   }

   public boolean isEisCallerPrincipalSet() {
      return this._isSet(0);
   }

   public void setEisCallerPrincipal(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EisCallerPrincipal;
      this._EisCallerPrincipal = param0;
      this._postSet(0, _oldVal, param0);
   }

   public AnonPrincipalBean getMappedCallerPrincipal() {
      return this._MappedCallerPrincipal;
   }

   public boolean isMappedCallerPrincipalInherited() {
      return false;
   }

   public boolean isMappedCallerPrincipalSet() {
      return this._isSet(1);
   }

   public void setMappedCallerPrincipal(AnonPrincipalBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMappedCallerPrincipal() != null && param0 != this.getMappedCallerPrincipal()) {
         throw new BeanAlreadyExistsException(this.getMappedCallerPrincipal() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AnonPrincipalBean _oldVal = this._MappedCallerPrincipal;
         this._MappedCallerPrincipal = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public AnonPrincipalBean createMappedCallerPrincipal() {
      AnonPrincipalBeanImpl _val = new AnonPrincipalBeanImpl(this, -1);

      try {
         this.setMappedCallerPrincipal(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMappedCallerPrincipal(AnonPrincipalBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MappedCallerPrincipal;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMappedCallerPrincipal((AnonPrincipalBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
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
      return this._isSet(2);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getEisCallerPrincipal();
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
         case 20:
            if (s.equals("eis-caller-principal")) {
               return info.compareXpaths(this._getPropertyXpath("eis-caller-principal"));
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
               this._EisCallerPrincipal = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MappedCallerPrincipal = null;
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
                  return 2;
               }
               break;
            case 20:
               if (s.equals("eis-caller-principal")) {
                  return 0;
               }
               break;
            case 23:
               if (s.equals("mapped-caller-principal")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new AnonPrincipalBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "eis-caller-principal";
            case 1:
               return "mapped-caller-principal";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("eis-caller-principal");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private InboundCallerPrincipalMappingBeanImpl bean;

      protected Helper(InboundCallerPrincipalMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "EisCallerPrincipal";
            case 1:
               return "MappedCallerPrincipal";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EisCallerPrincipal")) {
            return 0;
         } else if (propName.equals("Id")) {
            return 2;
         } else {
            return propName.equals("MappedCallerPrincipal") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getMappedCallerPrincipal() != null) {
            iterators.add(new ArrayIterator(new AnonPrincipalBean[]{this.bean.getMappedCallerPrincipal()}));
         }

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
            if (this.bean.isEisCallerPrincipalSet()) {
               buf.append("EisCallerPrincipal");
               buf.append(String.valueOf(this.bean.getEisCallerPrincipal()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getMappedCallerPrincipal());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            InboundCallerPrincipalMappingBeanImpl otherTyped = (InboundCallerPrincipalMappingBeanImpl)other;
            this.computeDiff("EisCallerPrincipal", this.bean.getEisCallerPrincipal(), otherTyped.getEisCallerPrincipal(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("MappedCallerPrincipal", this.bean.getMappedCallerPrincipal(), otherTyped.getMappedCallerPrincipal(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InboundCallerPrincipalMappingBeanImpl original = (InboundCallerPrincipalMappingBeanImpl)event.getSourceBean();
            InboundCallerPrincipalMappingBeanImpl proposed = (InboundCallerPrincipalMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EisCallerPrincipal")) {
                  original.setEisCallerPrincipal(proposed.getEisCallerPrincipal());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MappedCallerPrincipal")) {
                  if (type == 2) {
                     original.setMappedCallerPrincipal((AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)proposed.getMappedCallerPrincipal()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MappedCallerPrincipal", (DescriptorBean)original.getMappedCallerPrincipal());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            InboundCallerPrincipalMappingBeanImpl copy = (InboundCallerPrincipalMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EisCallerPrincipal")) && this.bean.isEisCallerPrincipalSet()) {
               copy.setEisCallerPrincipal(this.bean.getEisCallerPrincipal());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MappedCallerPrincipal")) && this.bean.isMappedCallerPrincipalSet() && !copy._isSet(1)) {
               Object o = this.bean.getMappedCallerPrincipal();
               copy.setMappedCallerPrincipal((AnonPrincipalBean)null);
               copy.setMappedCallerPrincipal(o == null ? null : (AnonPrincipalBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getMappedCallerPrincipal(), clazz, annotation);
      }
   }
}
