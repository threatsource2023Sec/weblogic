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

public class ModuleDescriptorBeanImpl extends AbstractDescriptorBean implements ModuleDescriptorBean, Serializable {
   private boolean _Changed;
   private boolean _External;
   private String _HashCode;
   private String _RootElement;
   private String _Uri;
   private VariableAssignmentBean[] _VariableAssignments;
   private static SchemaHelper2 _schemaHelper;

   public ModuleDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public ModuleDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ModuleDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRootElement() {
      return this._RootElement;
   }

   public boolean isRootElementInherited() {
      return false;
   }

   public boolean isRootElementSet() {
      return this._isSet(0);
   }

   public void setRootElement(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RootElement;
      this._RootElement = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getUri() {
      return this._Uri;
   }

   public boolean isUriInherited() {
      return false;
   }

   public boolean isUriSet() {
      return this._isSet(1);
   }

   public void setUri(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Uri;
      this._Uri = param0;
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

   public String getHashCode() {
      return this._HashCode;
   }

   public boolean isHashCodeInherited() {
      return false;
   }

   public boolean isHashCodeSet() {
      return this._isSet(3);
   }

   public void setHashCode(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._HashCode;
      this._HashCode = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isExternal() {
      return this._External;
   }

   public boolean isExternalInherited() {
      return false;
   }

   public boolean isExternalSet() {
      return this._isSet(4);
   }

   public void setExternal(boolean param0) {
      boolean _oldVal = this._External;
      this._External = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isChanged() {
      return this._Changed;
   }

   public boolean isChangedInherited() {
      return false;
   }

   public boolean isChangedSet() {
      return this._isSet(5);
   }

   public void setChanged(boolean param0) {
      boolean _oldVal = this._Changed;
      this._Changed = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getUri();
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
         case 3:
            if (s.equals("uri")) {
               return info.compareXpaths(this._getPropertyXpath("uri"));
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._HashCode = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._RootElement = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._Uri = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._VariableAssignments = new VariableAssignmentBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._Changed = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._External = false;
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
            case 3:
               if (s.equals("uri")) {
                  return 1;
               }
            case 4:
            case 5:
            case 6:
            case 10:
            case 11:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 18:
            default:
               break;
            case 7:
               if (s.equals("changed")) {
                  return 5;
               }
               break;
            case 8:
               if (s.equals("external")) {
                  return 4;
               }
               break;
            case 9:
               if (s.equals("hash-code")) {
                  return 3;
               }
               break;
            case 12:
               if (s.equals("root-element")) {
                  return 0;
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
               return "root-element";
            case 1:
               return "uri";
            case 2:
               return "variable-assignment";
            case 3:
               return "hash-code";
            case 4:
               return "external";
            case 5:
               return "changed";
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
         indices.add("uri");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ModuleDescriptorBeanImpl bean;

      protected Helper(ModuleDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "RootElement";
            case 1:
               return "Uri";
            case 2:
               return "VariableAssignments";
            case 3:
               return "HashCode";
            case 4:
               return "External";
            case 5:
               return "Changed";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HashCode")) {
            return 3;
         } else if (propName.equals("RootElement")) {
            return 0;
         } else if (propName.equals("Uri")) {
            return 1;
         } else if (propName.equals("VariableAssignments")) {
            return 2;
         } else if (propName.equals("Changed")) {
            return 5;
         } else {
            return propName.equals("External") ? 4 : super.getPropertyIndex(propName);
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
            if (this.bean.isHashCodeSet()) {
               buf.append("HashCode");
               buf.append(String.valueOf(this.bean.getHashCode()));
            }

            if (this.bean.isRootElementSet()) {
               buf.append("RootElement");
               buf.append(String.valueOf(this.bean.getRootElement()));
            }

            if (this.bean.isUriSet()) {
               buf.append("Uri");
               buf.append(String.valueOf(this.bean.getUri()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getVariableAssignments().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getVariableAssignments()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isChangedSet()) {
               buf.append("Changed");
               buf.append(String.valueOf(this.bean.isChanged()));
            }

            if (this.bean.isExternalSet()) {
               buf.append("External");
               buf.append(String.valueOf(this.bean.isExternal()));
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
            ModuleDescriptorBeanImpl otherTyped = (ModuleDescriptorBeanImpl)other;
            this.computeDiff("HashCode", this.bean.getHashCode(), otherTyped.getHashCode(), false);
            this.computeDiff("RootElement", this.bean.getRootElement(), otherTyped.getRootElement(), false);
            this.computeDiff("Uri", this.bean.getUri(), otherTyped.getUri(), false);
            this.computeChildDiff("VariableAssignments", this.bean.getVariableAssignments(), otherTyped.getVariableAssignments(), false);
            this.computeDiff("Changed", this.bean.isChanged(), otherTyped.isChanged(), false);
            this.computeDiff("External", this.bean.isExternal(), otherTyped.isExternal(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ModuleDescriptorBeanImpl original = (ModuleDescriptorBeanImpl)event.getSourceBean();
            ModuleDescriptorBeanImpl proposed = (ModuleDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HashCode")) {
                  original.setHashCode(proposed.getHashCode());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RootElement")) {
                  original.setRootElement(proposed.getRootElement());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Uri")) {
                  original.setUri(proposed.getUri());
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
               } else if (prop.equals("Changed")) {
                  original.setChanged(proposed.isChanged());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("External")) {
                  original.setExternal(proposed.isExternal());
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
            ModuleDescriptorBeanImpl copy = (ModuleDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HashCode")) && this.bean.isHashCodeSet()) {
               copy.setHashCode(this.bean.getHashCode());
            }

            if ((excludeProps == null || !excludeProps.contains("RootElement")) && this.bean.isRootElementSet()) {
               copy.setRootElement(this.bean.getRootElement());
            }

            if ((excludeProps == null || !excludeProps.contains("Uri")) && this.bean.isUriSet()) {
               copy.setUri(this.bean.getUri());
            }

            if ((excludeProps == null || !excludeProps.contains("VariableAssignments")) && this.bean.isVariableAssignmentsSet() && !copy._isSet(2)) {
               VariableAssignmentBean[] oldVariableAssignments = this.bean.getVariableAssignments();
               VariableAssignmentBean[] newVariableAssignments = new VariableAssignmentBean[oldVariableAssignments.length];

               for(int i = 0; i < newVariableAssignments.length; ++i) {
                  newVariableAssignments[i] = (VariableAssignmentBean)((VariableAssignmentBean)this.createCopy((AbstractDescriptorBean)oldVariableAssignments[i], includeObsolete));
               }

               copy.setVariableAssignments(newVariableAssignments);
            }

            if ((excludeProps == null || !excludeProps.contains("Changed")) && this.bean.isChangedSet()) {
               copy.setChanged(this.bean.isChanged());
            }

            if ((excludeProps == null || !excludeProps.contains("External")) && this.bean.isExternalSet()) {
               copy.setExternal(this.bean.isExternal());
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
