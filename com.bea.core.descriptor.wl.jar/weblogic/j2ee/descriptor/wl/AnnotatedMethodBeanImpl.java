package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
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
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AnnotatedMethodBeanImpl extends AbstractDescriptorBean implements AnnotatedMethodBean, Serializable {
   private AnnotationInstanceBean[] _Annotations;
   private String _MethodKey;
   private String _MethodName;
   private String[] _ParamterTypes;
   private static SchemaHelper2 _schemaHelper;

   public AnnotatedMethodBeanImpl() {
      this._initializeProperty(-1);
   }

   public AnnotatedMethodBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public AnnotatedMethodBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMethodKey() {
      return this._MethodKey;
   }

   public boolean isMethodKeyInherited() {
      return false;
   }

   public boolean isMethodKeySet() {
      return this._isSet(0);
   }

   public void setMethodKey(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MethodKey;
      this._MethodKey = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getMethodName() {
      return this._MethodName;
   }

   public boolean isMethodNameInherited() {
      return false;
   }

   public boolean isMethodNameSet() {
      return this._isSet(1);
   }

   public void setMethodName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MethodName;
      this._MethodName = param0;
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

   public String[] getParamterTypes() {
      return this._ParamterTypes;
   }

   public boolean isParamterTypesInherited() {
      return false;
   }

   public boolean isParamterTypesSet() {
      return this._isSet(3);
   }

   public void setParamterTypes(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._ParamterTypes;
      this._ParamterTypes = param0;
      this._postSet(3, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getMethodKey();
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
            if (s.equals("method-key")) {
               return info.compareXpaths(this._getPropertyXpath("method-key"));
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
               this._MethodKey = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._MethodName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._ParamterTypes = new String[0];
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

               if (s.equals("method-key")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("method-name")) {
                  return 1;
               }
            case 12:
            default:
               break;
            case 13:
               if (s.equals("paramter-type")) {
                  return 3;
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
               return "method-key";
            case 1:
               return "method-name";
            case 2:
               return "annotation";
            case 3:
               return "paramter-type";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
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
         indices.add("method-key");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AnnotatedMethodBeanImpl bean;

      protected Helper(AnnotatedMethodBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MethodKey";
            case 1:
               return "MethodName";
            case 2:
               return "Annotations";
            case 3:
               return "ParamterTypes";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Annotations")) {
            return 2;
         } else if (propName.equals("MethodKey")) {
            return 0;
         } else if (propName.equals("MethodName")) {
            return 1;
         } else {
            return propName.equals("ParamterTypes") ? 3 : super.getPropertyIndex(propName);
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

            if (this.bean.isMethodKeySet()) {
               buf.append("MethodKey");
               buf.append(String.valueOf(this.bean.getMethodKey()));
            }

            if (this.bean.isMethodNameSet()) {
               buf.append("MethodName");
               buf.append(String.valueOf(this.bean.getMethodName()));
            }

            if (this.bean.isParamterTypesSet()) {
               buf.append("ParamterTypes");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getParamterTypes())));
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
            AnnotatedMethodBeanImpl otherTyped = (AnnotatedMethodBeanImpl)other;
            this.computeChildDiff("Annotations", this.bean.getAnnotations(), otherTyped.getAnnotations(), false);
            this.computeDiff("MethodKey", this.bean.getMethodKey(), otherTyped.getMethodKey(), false);
            this.computeDiff("MethodName", this.bean.getMethodName(), otherTyped.getMethodName(), false);
            this.computeDiff("ParamterTypes", this.bean.getParamterTypes(), otherTyped.getParamterTypes(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnnotatedMethodBeanImpl original = (AnnotatedMethodBeanImpl)event.getSourceBean();
            AnnotatedMethodBeanImpl proposed = (AnnotatedMethodBeanImpl)event.getProposedBean();
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
               } else if (prop.equals("MethodKey")) {
                  original.setMethodKey(proposed.getMethodKey());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("MethodName")) {
                  original.setMethodName(proposed.getMethodName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ParamterTypes")) {
                  original.setParamterTypes(proposed.getParamterTypes());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            AnnotatedMethodBeanImpl copy = (AnnotatedMethodBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Annotations")) && this.bean.isAnnotationsSet() && !copy._isSet(2)) {
               AnnotationInstanceBean[] oldAnnotations = this.bean.getAnnotations();
               AnnotationInstanceBean[] newAnnotations = new AnnotationInstanceBean[oldAnnotations.length];

               for(int i = 0; i < newAnnotations.length; ++i) {
                  newAnnotations[i] = (AnnotationInstanceBean)((AnnotationInstanceBean)this.createCopy((AbstractDescriptorBean)oldAnnotations[i], includeObsolete));
               }

               copy.setAnnotations(newAnnotations);
            }

            if ((excludeProps == null || !excludeProps.contains("MethodKey")) && this.bean.isMethodKeySet()) {
               copy.setMethodKey(this.bean.getMethodKey());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodName")) && this.bean.isMethodNameSet()) {
               copy.setMethodName(this.bean.getMethodName());
            }

            if ((excludeProps == null || !excludeProps.contains("ParamterTypes")) && this.bean.isParamterTypesSet()) {
               Object o = this.bean.getParamterTypes();
               copy.setParamterTypes(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
