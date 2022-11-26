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

public class CharsetParamsBeanImpl extends AbstractDescriptorBean implements CharsetParamsBean, Serializable {
   private CharsetMappingBean[] _CharsetMappings;
   private String _Id;
   private InputCharsetBean[] _InputCharsets;
   private static SchemaHelper2 _schemaHelper;

   public CharsetParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public CharsetParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CharsetParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public void addInputCharset(InputCharsetBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         InputCharsetBean[] _new;
         if (this._isSet(0)) {
            _new = (InputCharsetBean[])((InputCharsetBean[])this._getHelper()._extendArray(this.getInputCharsets(), InputCharsetBean.class, param0));
         } else {
            _new = new InputCharsetBean[]{param0};
         }

         try {
            this.setInputCharsets(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InputCharsetBean[] getInputCharsets() {
      return this._InputCharsets;
   }

   public boolean isInputCharsetsInherited() {
      return false;
   }

   public boolean isInputCharsetsSet() {
      return this._isSet(0);
   }

   public void removeInputCharset(InputCharsetBean param0) {
      this.destroyInputCharset(param0);
   }

   public void setInputCharsets(InputCharsetBean[] param0) throws InvalidAttributeValueException {
      InputCharsetBean[] param0 = param0 == null ? new InputCharsetBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InputCharsetBean[] _oldVal = this._InputCharsets;
      this._InputCharsets = (InputCharsetBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public InputCharsetBean createInputCharset() {
      InputCharsetBeanImpl _val = new InputCharsetBeanImpl(this, -1);

      try {
         this.addInputCharset(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyInputCharset(InputCharsetBean param0) {
      try {
         this._checkIsPotentialChild(param0, 0);
         InputCharsetBean[] _old = this.getInputCharsets();
         InputCharsetBean[] _new = (InputCharsetBean[])((InputCharsetBean[])this._getHelper()._removeElement(_old, InputCharsetBean.class, param0));
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
               this.setInputCharsets(_new);
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

   public void addCharsetMapping(CharsetMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         CharsetMappingBean[] _new;
         if (this._isSet(1)) {
            _new = (CharsetMappingBean[])((CharsetMappingBean[])this._getHelper()._extendArray(this.getCharsetMappings(), CharsetMappingBean.class, param0));
         } else {
            _new = new CharsetMappingBean[]{param0};
         }

         try {
            this.setCharsetMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public CharsetMappingBean[] getCharsetMappings() {
      return this._CharsetMappings;
   }

   public boolean isCharsetMappingsInherited() {
      return false;
   }

   public boolean isCharsetMappingsSet() {
      return this._isSet(1);
   }

   public void removeCharsetMapping(CharsetMappingBean param0) {
      this.destroyCharsetMapping(param0);
   }

   public void setCharsetMappings(CharsetMappingBean[] param0) throws InvalidAttributeValueException {
      CharsetMappingBean[] param0 = param0 == null ? new CharsetMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      CharsetMappingBean[] _oldVal = this._CharsetMappings;
      this._CharsetMappings = (CharsetMappingBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public CharsetMappingBean createCharsetMapping() {
      CharsetMappingBeanImpl _val = new CharsetMappingBeanImpl(this, -1);

      try {
         this.addCharsetMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyCharsetMapping(CharsetMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         CharsetMappingBean[] _old = this.getCharsetMappings();
         CharsetMappingBean[] _new = (CharsetMappingBean[])((CharsetMappingBean[])this._getHelper()._removeElement(_old, CharsetMappingBean.class, param0));
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
               this.setCharsetMappings(_new);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._CharsetMappings = new CharsetMappingBean[0];
               if (initOne) {
                  break;
               }
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._InputCharsets = new InputCharsetBean[0];
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
            case 13:
               if (s.equals("input-charset")) {
                  return 0;
               }
               break;
            case 15:
               if (s.equals("charset-mapping")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new InputCharsetBeanImpl.SchemaHelper2();
            case 1:
               return new CharsetMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "input-charset";
            case 1:
               return "charset-mapping";
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
      private CharsetParamsBeanImpl bean;

      protected Helper(CharsetParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InputCharsets";
            case 1:
               return "CharsetMappings";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CharsetMappings")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 2;
         } else {
            return propName.equals("InputCharsets") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getCharsetMappings()));
         iterators.add(new ArrayIterator(this.bean.getInputCharsets()));
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

            int i;
            for(i = 0; i < this.bean.getCharsetMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getCharsetMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getInputCharsets().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInputCharsets()[i]);
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
            CharsetParamsBeanImpl otherTyped = (CharsetParamsBeanImpl)other;
            this.computeChildDiff("CharsetMappings", this.bean.getCharsetMappings(), otherTyped.getCharsetMappings(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeChildDiff("InputCharsets", this.bean.getInputCharsets(), otherTyped.getInputCharsets(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CharsetParamsBeanImpl original = (CharsetParamsBeanImpl)event.getSourceBean();
            CharsetParamsBeanImpl proposed = (CharsetParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CharsetMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addCharsetMapping((CharsetMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeCharsetMapping((CharsetMappingBean)update.getRemovedObject());
                  }

                  if (original.getCharsetMappings() == null || original.getCharsetMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("InputCharsets")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInputCharset((InputCharsetBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInputCharset((InputCharsetBean)update.getRemovedObject());
                  }

                  if (original.getInputCharsets() == null || original.getInputCharsets().length == 0) {
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
            CharsetParamsBeanImpl copy = (CharsetParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            int i;
            if ((excludeProps == null || !excludeProps.contains("CharsetMappings")) && this.bean.isCharsetMappingsSet() && !copy._isSet(1)) {
               CharsetMappingBean[] oldCharsetMappings = this.bean.getCharsetMappings();
               CharsetMappingBean[] newCharsetMappings = new CharsetMappingBean[oldCharsetMappings.length];

               for(i = 0; i < newCharsetMappings.length; ++i) {
                  newCharsetMappings[i] = (CharsetMappingBean)((CharsetMappingBean)this.createCopy((AbstractDescriptorBean)oldCharsetMappings[i], includeObsolete));
               }

               copy.setCharsetMappings(newCharsetMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("InputCharsets")) && this.bean.isInputCharsetsSet() && !copy._isSet(0)) {
               InputCharsetBean[] oldInputCharsets = this.bean.getInputCharsets();
               InputCharsetBean[] newInputCharsets = new InputCharsetBean[oldInputCharsets.length];

               for(i = 0; i < newInputCharsets.length; ++i) {
                  newInputCharsets[i] = (InputCharsetBean)((InputCharsetBean)this.createCopy((AbstractDescriptorBean)oldInputCharsets[i], includeObsolete));
               }

               copy.setInputCharsets(newInputCharsets);
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
         this.inferSubTree(this.bean.getCharsetMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getInputCharsets(), clazz, annotation);
      }
   }
}
