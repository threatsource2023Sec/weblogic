package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ConfigResourceOverrideBeanImpl extends AbstractDescriptorBean implements ConfigResourceOverrideBean, Serializable {
   private String _ResourceName;
   private String _ResourceType;
   private VariableAssignmentBean[] _VariableAssignments;
   private static SchemaHelper2 _schemaHelper;

   public ConfigResourceOverrideBeanImpl() {
      this._initializeProperty(-1);
   }

   public ConfigResourceOverrideBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ConfigResourceOverrideBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getResourceName() {
      return this._ResourceName;
   }

   public boolean isResourceNameInherited() {
      return false;
   }

   public boolean isResourceNameSet() {
      return this._isSet(0);
   }

   public void setResourceName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceName;
      this._ResourceName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getResourceType() {
      return this._ResourceType;
   }

   public boolean isResourceTypeInherited() {
      return false;
   }

   public boolean isResourceTypeSet() {
      return this._isSet(1);
   }

   public void setResourceType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceType;
      this._ResourceType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addVariableAssignment(VariableAssignmentBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         VariableAssignmentBean[] _new;
         if (this._isSet(2)) {
            _new = (VariableAssignmentBean[])((VariableAssignmentBean[])this._getHelper()._extendArray(this.getVariableAssignments(), VariableAssignmentBean.class, param0));
         } else {
            _new = new VariableAssignmentBean[]{param0};
         }

         try {
            this.setVariableAssignments(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public VariableAssignmentBean[] getVariableAssignments() {
      return this._VariableAssignments;
   }

   public boolean isVariableAssignmentsInherited() {
      return false;
   }

   public boolean isVariableAssignmentsSet() {
      return this._isSet(2);
   }

   public void removeVariableAssignment(VariableAssignmentBean param0) {
      this.destroyVariableAssignment(param0);
   }

   public void setVariableAssignments(VariableAssignmentBean[] param0) throws InvalidAttributeValueException {
      VariableAssignmentBean[] param0 = param0 == null ? new VariableAssignmentBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      VariableAssignmentBean[] _oldVal = this._VariableAssignments;
      this._VariableAssignments = (VariableAssignmentBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public VariableAssignmentBean createVariableAssignment() {
      VariableAssignmentBeanImpl _val = new VariableAssignmentBeanImpl(this, -1);

      try {
         this.addVariableAssignment(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyVariableAssignment(VariableAssignmentBean param0) {
      try {
         this._checkIsPotentialChild(param0, 2);
         VariableAssignmentBean[] _old = this.getVariableAssignments();
         VariableAssignmentBean[] _new = (VariableAssignmentBean[])((VariableAssignmentBean[])this._getHelper()._removeElement(_old, VariableAssignmentBean.class, param0));
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
               this.setVariableAssignments(_new);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ResourceName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ResourceType = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._VariableAssignments = new VariableAssignmentBean[0];
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
            case 13:
               if (s.equals("resource-name")) {
                  return 0;
               }

               if (s.equals("resource-type")) {
                  return 1;
               }
               break;
            case 19:
               if (s.equals("variable-assignment")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new VariableAssignmentBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "resource-name";
            case 1:
               return "resource-type";
            case 2:
               return "variable-assignment";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ConfigResourceOverrideBeanImpl bean;

      protected Helper(ConfigResourceOverrideBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ResourceName";
            case 1:
               return "ResourceType";
            case 2:
               return "VariableAssignments";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ResourceName")) {
            return 0;
         } else if (propName.equals("ResourceType")) {
            return 1;
         } else {
            return propName.equals("VariableAssignments") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getVariableAssignments()));
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
            if (this.bean.isResourceNameSet()) {
               buf.append("ResourceName");
               buf.append(String.valueOf(this.bean.getResourceName()));
            }

            if (this.bean.isResourceTypeSet()) {
               buf.append("ResourceType");
               buf.append(String.valueOf(this.bean.getResourceType()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getVariableAssignments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getVariableAssignments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            ConfigResourceOverrideBeanImpl otherTyped = (ConfigResourceOverrideBeanImpl)other;
            this.computeDiff("ResourceName", this.bean.getResourceName(), otherTyped.getResourceName(), false);
            this.computeDiff("ResourceType", this.bean.getResourceType(), otherTyped.getResourceType(), false);
            this.computeChildDiff("VariableAssignments", this.bean.getVariableAssignments(), otherTyped.getVariableAssignments(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ConfigResourceOverrideBeanImpl original = (ConfigResourceOverrideBeanImpl)event.getSourceBean();
            ConfigResourceOverrideBeanImpl proposed = (ConfigResourceOverrideBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ResourceName")) {
                  original.setResourceName(proposed.getResourceName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ResourceType")) {
                  original.setResourceType(proposed.getResourceType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("VariableAssignments")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addVariableAssignment((VariableAssignmentBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeVariableAssignment((VariableAssignmentBean)update.getRemovedObject());
                  }

                  if (original.getVariableAssignments() == null || original.getVariableAssignments().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
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
            ConfigResourceOverrideBeanImpl copy = (ConfigResourceOverrideBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ResourceName")) && this.bean.isResourceNameSet()) {
               copy.setResourceName(this.bean.getResourceName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceType")) && this.bean.isResourceTypeSet()) {
               copy.setResourceType(this.bean.getResourceType());
            }

            if ((excludeProps == null || !excludeProps.contains("VariableAssignments")) && this.bean.isVariableAssignmentsSet() && !copy._isSet(2)) {
               VariableAssignmentBean[] oldVariableAssignments = this.bean.getVariableAssignments();
               VariableAssignmentBean[] newVariableAssignments = new VariableAssignmentBean[oldVariableAssignments.length];

               for(int i = 0; i < newVariableAssignments.length; ++i) {
                  newVariableAssignments[i] = (VariableAssignmentBean)((VariableAssignmentBean)this.createCopy((AbstractDescriptorBean)oldVariableAssignments[i], includeObsolete));
               }

               copy.setVariableAssignments(newVariableAssignments);
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
         this.inferSubTree(this.bean.getVariableAssignments(), clazz, annotation);
      }
   }
}
