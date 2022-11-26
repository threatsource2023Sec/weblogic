package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AnnotatedFieldBeanImpl extends AbstractDescriptorBean implements AnnotatedFieldBean, Serializable {
   private AnnotationInstanceBean[] _Annotations;
   private String _FieldName;
   private String _InstanceType;
   private static SchemaHelper2 _schemaHelper;

   public AnnotatedFieldBeanImpl() {
      this._initializeProperty(-1);
   }

   public AnnotatedFieldBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AnnotatedFieldBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFieldName() {
      return this._FieldName;
   }

   public boolean isFieldNameInherited() {
      return false;
   }

   public boolean isFieldNameSet() {
      return this._isSet(0);
   }

   public void setFieldName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._FieldName;
      this._FieldName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getInstanceType() {
      return this._InstanceType;
   }

   public boolean isInstanceTypeInherited() {
      return false;
   }

   public boolean isInstanceTypeSet() {
      return this._isSet(1);
   }

   public void setInstanceType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._InstanceType;
      this._InstanceType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public void addAnnotation(AnnotationInstanceBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         AnnotationInstanceBean[] _new;
         if (this._isSet(2)) {
            _new = (AnnotationInstanceBean[])((AnnotationInstanceBean[])this._getHelper()._extendArray(this.getAnnotations(), AnnotationInstanceBean.class, param0));
         } else {
            _new = new AnnotationInstanceBean[]{param0};
         }

         try {
            this.setAnnotations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AnnotationInstanceBean[] getAnnotations() {
      return this._Annotations;
   }

   public boolean isAnnotationsInherited() {
      return false;
   }

   public boolean isAnnotationsSet() {
      return this._isSet(2);
   }

   public void removeAnnotation(AnnotationInstanceBean param0) {
      AnnotationInstanceBean[] _old = this.getAnnotations();
      AnnotationInstanceBean[] _new = (AnnotationInstanceBean[])((AnnotationInstanceBean[])this._getHelper()._removeElement(_old, AnnotationInstanceBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setAnnotations(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setAnnotations(AnnotationInstanceBean[] param0) throws InvalidAttributeValueException {
      AnnotationInstanceBean[] param0 = param0 == null ? new AnnotationInstanceBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AnnotationInstanceBean[] _oldVal = this._Annotations;
      this._Annotations = (AnnotationInstanceBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public AnnotationInstanceBean createAnnotation() {
      AnnotationInstanceBeanImpl _val = new AnnotationInstanceBeanImpl(this, -1);

      try {
         this.addAnnotation(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return this.getFieldName();
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
         case 10:
            if (s.equals("field-name")) {
               return info.compareXpaths(this._getPropertyXpath("field-name"));
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
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Annotations = new AnnotationInstanceBean[0];
               if (initOne) {
                  break;
               }
            case 0:
               this._FieldName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._InstanceType = null;
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
            case 10:
               if (s.equals("annotation")) {
                  return 2;
               }

               if (s.equals("field-name")) {
                  return 0;
               }
               break;
            case 13:
               if (s.equals("instance-type")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new AnnotationInstanceBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "field-name";
            case 1:
               return "instance-type";
            case 2:
               return "annotation";
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
         indices.add("field-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AnnotatedFieldBeanImpl bean;

      protected Helper(AnnotatedFieldBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FieldName";
            case 1:
               return "InstanceType";
            case 2:
               return "Annotations";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Annotations")) {
            return 2;
         } else if (propName.equals("FieldName")) {
            return 0;
         } else {
            return propName.equals("InstanceType") ? 1 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAnnotations()));
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

            for(int i = 0; i < this.bean.getAnnotations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAnnotations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isFieldNameSet()) {
               buf.append("FieldName");
               buf.append(String.valueOf(this.bean.getFieldName()));
            }

            if (this.bean.isInstanceTypeSet()) {
               buf.append("InstanceType");
               buf.append(String.valueOf(this.bean.getInstanceType()));
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
            AnnotatedFieldBeanImpl otherTyped = (AnnotatedFieldBeanImpl)other;
            this.computeChildDiff("Annotations", this.bean.getAnnotations(), otherTyped.getAnnotations(), false);
            this.computeDiff("FieldName", this.bean.getFieldName(), otherTyped.getFieldName(), false);
            this.computeDiff("InstanceType", this.bean.getInstanceType(), otherTyped.getInstanceType(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnnotatedFieldBeanImpl original = (AnnotatedFieldBeanImpl)event.getSourceBean();
            AnnotatedFieldBeanImpl proposed = (AnnotatedFieldBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Annotations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addAnnotation((AnnotationInstanceBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeAnnotation((AnnotationInstanceBean)update.getRemovedObject());
                  }

                  if (original.getAnnotations() == null || original.getAnnotations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("FieldName")) {
                  original.setFieldName(proposed.getFieldName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("InstanceType")) {
                  original.setInstanceType(proposed.getInstanceType());
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
            AnnotatedFieldBeanImpl copy = (AnnotatedFieldBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Annotations")) && this.bean.isAnnotationsSet() && !copy._isSet(2)) {
               AnnotationInstanceBean[] oldAnnotations = this.bean.getAnnotations();
               AnnotationInstanceBean[] newAnnotations = new AnnotationInstanceBean[oldAnnotations.length];

               for(int i = 0; i < newAnnotations.length; ++i) {
                  newAnnotations[i] = (AnnotationInstanceBean)((AnnotationInstanceBean)this.createCopy((AbstractDescriptorBean)oldAnnotations[i], includeObsolete));
               }

               copy.setAnnotations(newAnnotations);
            }

            if ((excludeProps == null || !excludeProps.contains("FieldName")) && this.bean.isFieldNameSet()) {
               copy.setFieldName(this.bean.getFieldName());
            }

            if ((excludeProps == null || !excludeProps.contains("InstanceType")) && this.bean.isInstanceTypeSet()) {
               copy.setInstanceType(this.bean.getInstanceType());
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
         this.inferSubTree(this.bean.getAnnotations(), clazz, annotation);
      }
   }
}
