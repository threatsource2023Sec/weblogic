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

public class RelationshipCachingBeanImpl extends AbstractDescriptorBean implements RelationshipCachingBean, Serializable {
   private CachingElementBean[] _CachingElements;
   private String _CachingName;
   private String _Id;
   private static SchemaHelper2 _schemaHelper;

   public RelationshipCachingBeanImpl() {
      this._initializeProperty(-1);
   }

   public RelationshipCachingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public RelationshipCachingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getCachingName() {
      return this._CachingName;
   }

   public boolean isCachingNameInherited() {
      return false;
   }

   public boolean isCachingNameSet() {
      return this._isSet(0);
   }

   public void setCachingName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CachingName;
      this._CachingName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addCachingElement(CachingElementBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         CachingElementBean[] _new;
         if (this._isSet(1)) {
            _new = (CachingElementBean[])((CachingElementBean[])this._getHelper()._extendArray(this.getCachingElements(), CachingElementBean.class, param0));
         } else {
            _new = new CachingElementBean[]{param0};
         }

         try {
            this.setCachingElements(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CachingElementBean[] getCachingElements() {
      return this._CachingElements;
   }

   public boolean isCachingElementsInherited() {
      return false;
   }

   public boolean isCachingElementsSet() {
      return this._isSet(1);
   }

   public void removeCachingElement(CachingElementBean param0) {
      this.destroyCachingElement(param0);
   }

   public void setCachingElements(CachingElementBean[] param0) throws InvalidAttributeValueException {
      CachingElementBean[] param0 = param0 == null ? new CachingElementBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CachingElementBean[] _oldVal = this._CachingElements;
      this._CachingElements = (CachingElementBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public CachingElementBean createCachingElement() {
      CachingElementBeanImpl _val = new CachingElementBeanImpl(this, -1);

      try {
         this.addCachingElement(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCachingElement(CachingElementBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         CachingElementBean[] _old = this.getCachingElements();
         CachingElementBean[] _new = (CachingElementBean[])((CachingElementBean[])this._getHelper()._removeElement(_old, CachingElementBean.class, param0));
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
               this.setCachingElements(_new);
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
      return this.getCachingName();
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
            if (s.equals("caching-name")) {
               return info.compareXpaths(this._getPropertyXpath("caching-name"));
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._CachingElements = new CachingElementBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._CachingName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Id = null;
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
            case 12:
               if (s.equals("caching-name")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("caching-element")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new CachingElementBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "caching-name";
            case 1:
               return "caching-element";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
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
         indices.add("caching-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private RelationshipCachingBeanImpl bean;

      protected Helper(RelationshipCachingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "CachingName";
            case 1:
               return "CachingElements";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CachingElements")) {
            return 1;
         } else if (propName.equals("CachingName")) {
            return 0;
         } else {
            return propName.equals("Id") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCachingElements()));
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

            for(int i = 0; i < this.bean.getCachingElements().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCachingElements()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isCachingNameSet()) {
               buf.append("CachingName");
               buf.append(String.valueOf(this.bean.getCachingName()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
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
            RelationshipCachingBeanImpl otherTyped = (RelationshipCachingBeanImpl)other;
            this.computeChildDiff("CachingElements", this.bean.getCachingElements(), otherTyped.getCachingElements(), false);
            this.computeDiff("CachingName", this.bean.getCachingName(), otherTyped.getCachingName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            RelationshipCachingBeanImpl original = (RelationshipCachingBeanImpl)event.getSourceBean();
            RelationshipCachingBeanImpl proposed = (RelationshipCachingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CachingElements")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCachingElement((CachingElementBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCachingElement((CachingElementBean)update.getRemovedObject());
                  }

                  if (original.getCachingElements() == null || original.getCachingElements().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("CachingName")) {
                  original.setCachingName(proposed.getCachingName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
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
            RelationshipCachingBeanImpl copy = (RelationshipCachingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CachingElements")) && this.bean.isCachingElementsSet() && !copy._isSet(1)) {
               CachingElementBean[] oldCachingElements = this.bean.getCachingElements();
               CachingElementBean[] newCachingElements = new CachingElementBean[oldCachingElements.length];

               for(int i = 0; i < newCachingElements.length; ++i) {
                  newCachingElements[i] = (CachingElementBean)((CachingElementBean)this.createCopy((AbstractDescriptorBean)oldCachingElements[i], includeObsolete));
               }

               copy.setCachingElements(newCachingElements);
            }

            if ((excludeProps == null || !excludeProps.contains("CachingName")) && this.bean.isCachingNameSet()) {
               copy.setCachingName(this.bean.getCachingName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
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
         this.inferSubTree(this.bean.getCachingElements(), clazz, annotation);
      }
   }
}
