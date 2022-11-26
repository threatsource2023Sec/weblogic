package weblogic.j2ee.descriptor;

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

public class JspConfigBeanImpl extends AbstractDescriptorBean implements JspConfigBean, Serializable {
   private String _Id;
   private JspPropertyGroupBean[] _JspPropertyGroups;
   private TagLibBean[] _TagLibs;
   private static SchemaHelper2 _schemaHelper;

   public JspConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public JspConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JspConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addTagLib(TagLibBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         TagLibBean[] _new;
         if (this._isSet(0)) {
            _new = (TagLibBean[])((TagLibBean[])this._getHelper()._extendArray(this.getTagLibs(), TagLibBean.class, param0));
         } else {
            _new = new TagLibBean[]{param0};
         }

         try {
            this.setTagLibs(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public TagLibBean[] getTagLibs() {
      return this._TagLibs;
   }

   public boolean isTagLibsInherited() {
      return false;
   }

   public boolean isTagLibsSet() {
      return this._isSet(0);
   }

   public void removeTagLib(TagLibBean param0) {
      this.destroyTagLib(param0);
   }

   public void setTagLibs(TagLibBean[] param0) throws InvalidAttributeValueException {
      TagLibBean[] param0 = param0 == null ? new TagLibBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      TagLibBean[] _oldVal = this._TagLibs;
      this._TagLibs = (TagLibBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public TagLibBean createTagLib() {
      TagLibBeanImpl _val = new TagLibBeanImpl(this, -1);

      try {
         this.addTagLib(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyTagLib(TagLibBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         TagLibBean[] _old = this.getTagLibs();
         TagLibBean[] _new = (TagLibBean[])((TagLibBean[])this._getHelper()._removeElement(_old, TagLibBean.class, param0));
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
               this.setTagLibs(_new);
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

   public void addJspPropertyGroup(JspPropertyGroupBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         JspPropertyGroupBean[] _new;
         if (this._isSet(1)) {
            _new = (JspPropertyGroupBean[])((JspPropertyGroupBean[])this._getHelper()._extendArray(this.getJspPropertyGroups(), JspPropertyGroupBean.class, param0));
         } else {
            _new = new JspPropertyGroupBean[]{param0};
         }

         try {
            this.setJspPropertyGroups(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public JspPropertyGroupBean[] getJspPropertyGroups() {
      return this._JspPropertyGroups;
   }

   public boolean isJspPropertyGroupsInherited() {
      return false;
   }

   public boolean isJspPropertyGroupsSet() {
      return this._isSet(1);
   }

   public void removeJspPropertyGroup(JspPropertyGroupBean param0) {
      this.destroyJspPropertyGroup(param0);
   }

   public void setJspPropertyGroups(JspPropertyGroupBean[] param0) throws InvalidAttributeValueException {
      JspPropertyGroupBean[] param0 = param0 == null ? new JspPropertyGroupBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      JspPropertyGroupBean[] _oldVal = this._JspPropertyGroups;
      this._JspPropertyGroups = (JspPropertyGroupBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public JspPropertyGroupBean createJspPropertyGroup() {
      JspPropertyGroupBeanImpl _val = new JspPropertyGroupBeanImpl(this, -1);

      try {
         this.addJspPropertyGroup(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyJspPropertyGroup(JspPropertyGroupBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         JspPropertyGroupBean[] _old = this.getJspPropertyGroups();
         JspPropertyGroupBean[] _new = (JspPropertyGroupBean[])((JspPropertyGroupBean[])this._getHelper()._removeElement(_old, JspPropertyGroupBean.class, param0));
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
               this.setJspPropertyGroups(_new);
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
      return this._isSet(2);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._JspPropertyGroups = new JspPropertyGroupBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._TagLibs = new TagLibBean[0];
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
            case 6:
               if (s.equals("taglib")) {
                  return 0;
               }
               break;
            case 18:
               if (s.equals("jsp-property-group")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new TagLibBeanImpl.SchemaHelper2();
            case 1:
               return new JspPropertyGroupBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "taglib";
            case 1:
               return "jsp-property-group";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JspConfigBeanImpl bean;

      protected Helper(JspConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TagLibs";
            case 1:
               return "JspPropertyGroups";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("JspPropertyGroups")) {
            return 1;
         } else {
            return propName.equals("TagLibs") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getJspPropertyGroups()));
         iterators.add(new ArrayIterator(this.bean.getTagLibs()));
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getJspPropertyGroups().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getJspPropertyGroups()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getTagLibs().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getTagLibs()[i]);
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
            JspConfigBeanImpl otherTyped = (JspConfigBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("JspPropertyGroups", this.bean.getJspPropertyGroups(), otherTyped.getJspPropertyGroups(), false);
            this.computeChildDiff("TagLibs", this.bean.getTagLibs(), otherTyped.getTagLibs(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JspConfigBeanImpl original = (JspConfigBeanImpl)event.getSourceBean();
            JspConfigBeanImpl proposed = (JspConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("JspPropertyGroups")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addJspPropertyGroup((JspPropertyGroupBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeJspPropertyGroup((JspPropertyGroupBean)update.getRemovedObject());
                  }

                  if (original.getJspPropertyGroups() == null || original.getJspPropertyGroups().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("TagLibs")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addTagLib((TagLibBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeTagLib((TagLibBean)update.getRemovedObject());
                  }

                  if (original.getTagLibs() == null || original.getTagLibs().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            JspConfigBeanImpl copy = (JspConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("JspPropertyGroups")) && this.bean.isJspPropertyGroupsSet() && !copy._isSet(1)) {
               JspPropertyGroupBean[] oldJspPropertyGroups = this.bean.getJspPropertyGroups();
               JspPropertyGroupBean[] newJspPropertyGroups = new JspPropertyGroupBean[oldJspPropertyGroups.length];

               for(i = 0; i < newJspPropertyGroups.length; ++i) {
                  newJspPropertyGroups[i] = (JspPropertyGroupBean)((JspPropertyGroupBean)this.createCopy((AbstractDescriptorBean)oldJspPropertyGroups[i], includeObsolete));
               }

               copy.setJspPropertyGroups(newJspPropertyGroups);
            }

            if ((excludeProps == null || !excludeProps.contains("TagLibs")) && this.bean.isTagLibsSet() && !copy._isSet(0)) {
               TagLibBean[] oldTagLibs = this.bean.getTagLibs();
               TagLibBean[] newTagLibs = new TagLibBean[oldTagLibs.length];

               for(i = 0; i < newTagLibs.length; ++i) {
                  newTagLibs[i] = (TagLibBean)((TagLibBean)this.createCopy((AbstractDescriptorBean)oldTagLibs[i], includeObsolete));
               }

               copy.setTagLibs(newTagLibs);
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
         this.inferSubTree(this.bean.getJspPropertyGroups(), clazz, annotation);
         this.inferSubTree(this.bean.getTagLibs(), clazz, annotation);
      }
   }
}
