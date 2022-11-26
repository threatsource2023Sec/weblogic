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

public class XmlBeanImpl extends AbstractDescriptorBean implements XmlBean, Serializable {
   private EntityMappingBean[] _EntityMappings;
   private ParserFactoryBean _ParserFactory;
   private static SchemaHelper2 _schemaHelper;

   public XmlBeanImpl() {
      this._initializeProperty(-1);
   }

   public XmlBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public XmlBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public ParserFactoryBean getParserFactory() {
      return this._ParserFactory;
   }

   public boolean isParserFactoryInherited() {
      return false;
   }

   public boolean isParserFactorySet() {
      return this._isSet(0);
   }

   public void setParserFactory(ParserFactoryBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getParserFactory() != null && param0 != this.getParserFactory()) {
         throw new BeanAlreadyExistsException(this.getParserFactory() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ParserFactoryBean _oldVal = this._ParserFactory;
         this._ParserFactory = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public ParserFactoryBean createParserFactory() {
      ParserFactoryBeanImpl _val = new ParserFactoryBeanImpl(this, -1);

      try {
         this.setParserFactory(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyParserFactory(ParserFactoryBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ParserFactory;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setParserFactory((ParserFactoryBean)null);
               this._unSet(0);
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

   public void addEntityMapping(EntityMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         EntityMappingBean[] _new;
         if (this._isSet(1)) {
            _new = (EntityMappingBean[])((EntityMappingBean[])this._getHelper()._extendArray(this.getEntityMappings(), EntityMappingBean.class, param0));
         } else {
            _new = new EntityMappingBean[]{param0};
         }

         try {
            this.setEntityMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public EntityMappingBean[] getEntityMappings() {
      return this._EntityMappings;
   }

   public boolean isEntityMappingsInherited() {
      return false;
   }

   public boolean isEntityMappingsSet() {
      return this._isSet(1);
   }

   public void removeEntityMapping(EntityMappingBean param0) {
      this.destroyEntityMapping(param0);
   }

   public void setEntityMappings(EntityMappingBean[] param0) throws InvalidAttributeValueException {
      EntityMappingBean[] param0 = param0 == null ? new EntityMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      EntityMappingBean[] _oldVal = this._EntityMappings;
      this._EntityMappings = (EntityMappingBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public EntityMappingBean createEntityMapping() {
      EntityMappingBeanImpl _val = new EntityMappingBeanImpl(this, -1);

      try {
         this.addEntityMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyEntityMapping(EntityMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 1);
         EntityMappingBean[] _old = this.getEntityMappings();
         EntityMappingBean[] _new = (EntityMappingBean[])((EntityMappingBean[])this._getHelper()._removeElement(_old, EntityMappingBean.class, param0));
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
               this.setEntityMappings(_new);
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
         idx = 1;
      }

      try {
         switch (idx) {
            case 1:
               this._EntityMappings = new EntityMappingBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._ParserFactory = null;
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
            case 14:
               if (s.equals("entity-mapping")) {
                  return 1;
               } else if (s.equals("parser-factory")) {
                  return 0;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new ParserFactoryBeanImpl.SchemaHelper2();
            case 1:
               return new EntityMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "parser-factory";
            case 1:
               return "entity-mapping";
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
      private XmlBeanImpl bean;

      protected Helper(XmlBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ParserFactory";
            case 1:
               return "EntityMappings";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("EntityMappings")) {
            return 1;
         } else {
            return propName.equals("ParserFactory") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getEntityMappings()));
         if (this.bean.getParserFactory() != null) {
            iterators.add(new ArrayIterator(new ParserFactoryBean[]{this.bean.getParserFactory()}));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getEntityMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getEntityMappings()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getParserFactory());
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
            XmlBeanImpl otherTyped = (XmlBeanImpl)other;
            this.computeChildDiff("EntityMappings", this.bean.getEntityMappings(), otherTyped.getEntityMappings(), false);
            this.computeChildDiff("ParserFactory", this.bean.getParserFactory(), otherTyped.getParserFactory(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            XmlBeanImpl original = (XmlBeanImpl)event.getSourceBean();
            XmlBeanImpl proposed = (XmlBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("EntityMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addEntityMapping((EntityMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeEntityMapping((EntityMappingBean)update.getRemovedObject());
                  }

                  if (original.getEntityMappings() == null || original.getEntityMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("ParserFactory")) {
                  if (type == 2) {
                     original.setParserFactory((ParserFactoryBean)this.createCopy((AbstractDescriptorBean)proposed.getParserFactory()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ParserFactory", (DescriptorBean)original.getParserFactory());
                  }

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
            XmlBeanImpl copy = (XmlBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("EntityMappings")) && this.bean.isEntityMappingsSet() && !copy._isSet(1)) {
               EntityMappingBean[] oldEntityMappings = this.bean.getEntityMappings();
               EntityMappingBean[] newEntityMappings = new EntityMappingBean[oldEntityMappings.length];

               for(int i = 0; i < newEntityMappings.length; ++i) {
                  newEntityMappings[i] = (EntityMappingBean)((EntityMappingBean)this.createCopy((AbstractDescriptorBean)oldEntityMappings[i], includeObsolete));
               }

               copy.setEntityMappings(newEntityMappings);
            }

            if ((excludeProps == null || !excludeProps.contains("ParserFactory")) && this.bean.isParserFactorySet() && !copy._isSet(0)) {
               Object o = this.bean.getParserFactory();
               copy.setParserFactory((ParserFactoryBean)null);
               copy.setParserFactory(o == null ? null : (ParserFactoryBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getEntityMappings(), clazz, annotation);
         this.inferSubTree(this.bean.getParserFactory(), clazz, annotation);
      }
   }
}
