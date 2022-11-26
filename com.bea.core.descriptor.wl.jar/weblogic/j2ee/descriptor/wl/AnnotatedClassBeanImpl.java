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
import weblogic.descriptor.beangen.CustomizerFactory;
import weblogic.descriptor.beangen.CustomizerFactoryBuilder;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.j2ee.descriptor.wl.customizers.AnnotatedClassBeanCustomizer;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AnnotatedClassBeanImpl extends AbstractDescriptorBean implements AnnotatedClassBean, Serializable {
   private String _AnnotatedClassName;
   private AnnotationInstanceBean[] _Annotations;
   private String _ComponentType;
   private AnnotatedFieldBean[] _Fields;
   private AnnotatedMethodBean[] _Methods;
   private String _ShortDescription;
   private transient AnnotatedClassBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public AnnotatedClassBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.AnnotatedClassBeanCustomizerFactory");
         this._customizer = (AnnotatedClassBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public AnnotatedClassBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.AnnotatedClassBeanCustomizerFactory");
         this._customizer = (AnnotatedClassBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public AnnotatedClassBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.AnnotatedClassBeanCustomizerFactory");
         this._customizer = (AnnotatedClassBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getAnnotatedClassName() {
      return this._AnnotatedClassName;
   }

   public boolean isAnnotatedClassNameInherited() {
      return false;
   }

   public boolean isAnnotatedClassNameSet() {
      return this._isSet(0);
   }

   public void setAnnotatedClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AnnotatedClassName;
      this._AnnotatedClassName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getComponentType() {
      return this._ComponentType;
   }

   public boolean isComponentTypeInherited() {
      return false;
   }

   public boolean isComponentTypeSet() {
      return this._isSet(1);
   }

   public void setComponentType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ComponentType;
      this._ComponentType = param0;
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

   public void addField(AnnotatedFieldBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         AnnotatedFieldBean[] _new;
         if (this._isSet(3)) {
            _new = (AnnotatedFieldBean[])((AnnotatedFieldBean[])this._getHelper()._extendArray(this.getFields(), AnnotatedFieldBean.class, param0));
         } else {
            _new = new AnnotatedFieldBean[]{param0};
         }

         try {
            this.setFields(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AnnotatedFieldBean[] getFields() {
      return this._Fields;
   }

   public boolean isFieldsInherited() {
      return false;
   }

   public boolean isFieldsSet() {
      return this._isSet(3);
   }

   public void removeField(AnnotatedFieldBean param0) {
      AnnotatedFieldBean[] _old = this.getFields();
      AnnotatedFieldBean[] _new = (AnnotatedFieldBean[])((AnnotatedFieldBean[])this._getHelper()._removeElement(_old, AnnotatedFieldBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setFields(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setFields(AnnotatedFieldBean[] param0) throws InvalidAttributeValueException {
      AnnotatedFieldBean[] param0 = param0 == null ? new AnnotatedFieldBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AnnotatedFieldBean[] _oldVal = this._Fields;
      this._Fields = (AnnotatedFieldBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public AnnotatedFieldBean createField() {
      AnnotatedFieldBeanImpl _val = new AnnotatedFieldBeanImpl(this, -1);

      try {
         this.addField(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addMethod(AnnotatedMethodBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         AnnotatedMethodBean[] _new;
         if (this._isSet(4)) {
            _new = (AnnotatedMethodBean[])((AnnotatedMethodBean[])this._getHelper()._extendArray(this.getMethods(), AnnotatedMethodBean.class, param0));
         } else {
            _new = new AnnotatedMethodBean[]{param0};
         }

         try {
            this.setMethods(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public AnnotatedMethodBean[] getMethods() {
      return this._Methods;
   }

   public boolean isMethodsInherited() {
      return false;
   }

   public boolean isMethodsSet() {
      return this._isSet(4);
   }

   public void removeMethod(AnnotatedMethodBean param0) {
      AnnotatedMethodBean[] _old = this.getMethods();
      AnnotatedMethodBean[] _new = (AnnotatedMethodBean[])((AnnotatedMethodBean[])this._getHelper()._removeElement(_old, AnnotatedMethodBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMethods(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMethods(AnnotatedMethodBean[] param0) throws InvalidAttributeValueException {
      AnnotatedMethodBean[] param0 = param0 == null ? new AnnotatedMethodBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      AnnotatedMethodBean[] _oldVal = this._Methods;
      this._Methods = (AnnotatedMethodBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public AnnotatedMethodBean createMethod() {
      AnnotatedMethodBeanImpl _val = new AnnotatedMethodBeanImpl(this, -1);

      try {
         this.addMethod(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getShortDescription() {
      return this._customizer.getShortDescription();
   }

   public boolean isShortDescriptionInherited() {
      return false;
   }

   public boolean isShortDescriptionSet() {
      return this._isSet(5);
   }

   public void setShortDescription(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ShortDescription;
      this._ShortDescription = param0;
      this._postSet(5, _oldVal, param0);
   }

   public Object _getKey() {
      return this.getAnnotatedClassName();
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
            if (s.equals("annotated-class-name")) {
               return info.compareXpaths(this._getPropertyXpath("annotated-class-name"));
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
               this._AnnotatedClassName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Annotations = new AnnotationInstanceBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._ComponentType = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Fields = new AnnotatedFieldBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._Methods = new AnnotatedMethodBean[0];
               if (initOne) {
                  break;
               }
            case 5:
               this._ShortDescription = null;
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
            case 5:
               if (s.equals("field")) {
                  return 3;
               }
               break;
            case 6:
               if (s.equals("method")) {
                  return 4;
               }
            case 7:
            case 8:
            case 9:
            case 11:
            case 12:
            case 13:
            case 15:
            case 16:
            case 18:
            case 19:
            default:
               break;
            case 10:
               if (s.equals("annotation")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("component-type")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("short-description")) {
                  return 5;
               }
               break;
            case 20:
               if (s.equals("annotated-class-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new AnnotationInstanceBeanImpl.SchemaHelper2();
            case 3:
               return new AnnotatedFieldBeanImpl.SchemaHelper2();
            case 4:
               return new AnnotatedMethodBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "annotated-class-name";
            case 1:
               return "component-type";
            case 2:
               return "annotation";
            case 3:
               return "field";
            case 4:
               return "method";
            case 5:
               return "short-description";
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
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
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
         indices.add("annotated-class-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AnnotatedClassBeanImpl bean;

      protected Helper(AnnotatedClassBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AnnotatedClassName";
            case 1:
               return "ComponentType";
            case 2:
               return "Annotations";
            case 3:
               return "Fields";
            case 4:
               return "Methods";
            case 5:
               return "ShortDescription";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AnnotatedClassName")) {
            return 0;
         } else if (propName.equals("Annotations")) {
            return 2;
         } else if (propName.equals("ComponentType")) {
            return 1;
         } else if (propName.equals("Fields")) {
            return 3;
         } else if (propName.equals("Methods")) {
            return 4;
         } else {
            return propName.equals("ShortDescription") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getAnnotations()));
         iterators.add(new ArrayIterator(this.bean.getFields()));
         iterators.add(new ArrayIterator(this.bean.getMethods()));
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
            if (this.bean.isAnnotatedClassNameSet()) {
               buf.append("AnnotatedClassName");
               buf.append(String.valueOf(this.bean.getAnnotatedClassName()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getAnnotations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getAnnotations()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isComponentTypeSet()) {
               buf.append("ComponentType");
               buf.append(String.valueOf(this.bean.getComponentType()));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getFields().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getFields()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMethods().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMethods()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isShortDescriptionSet()) {
               buf.append("ShortDescription");
               buf.append(String.valueOf(this.bean.getShortDescription()));
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
            AnnotatedClassBeanImpl otherTyped = (AnnotatedClassBeanImpl)other;
            this.computeDiff("AnnotatedClassName", this.bean.getAnnotatedClassName(), otherTyped.getAnnotatedClassName(), false);
            this.computeChildDiff("Annotations", this.bean.getAnnotations(), otherTyped.getAnnotations(), false);
            this.computeDiff("ComponentType", this.bean.getComponentType(), otherTyped.getComponentType(), false);
            this.computeChildDiff("Fields", this.bean.getFields(), otherTyped.getFields(), false);
            this.computeChildDiff("Methods", this.bean.getMethods(), otherTyped.getMethods(), false);
            this.computeDiff("ShortDescription", this.bean.getShortDescription(), otherTyped.getShortDescription(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnnotatedClassBeanImpl original = (AnnotatedClassBeanImpl)event.getSourceBean();
            AnnotatedClassBeanImpl proposed = (AnnotatedClassBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AnnotatedClassName")) {
                  original.setAnnotatedClassName(proposed.getAnnotatedClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("Annotations")) {
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
               } else if (prop.equals("ComponentType")) {
                  original.setComponentType(proposed.getComponentType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Fields")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addField((AnnotatedFieldBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeField((AnnotatedFieldBean)update.getRemovedObject());
                  }

                  if (original.getFields() == null || original.getFields().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Methods")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMethod((AnnotatedMethodBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMethod((AnnotatedMethodBean)update.getRemovedObject());
                  }

                  if (original.getMethods() == null || original.getMethods().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("ShortDescription")) {
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
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
            AnnotatedClassBeanImpl copy = (AnnotatedClassBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AnnotatedClassName")) && this.bean.isAnnotatedClassNameSet()) {
               copy.setAnnotatedClassName(this.bean.getAnnotatedClassName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("Annotations")) && this.bean.isAnnotationsSet() && !copy._isSet(2)) {
               AnnotationInstanceBean[] oldAnnotations = this.bean.getAnnotations();
               AnnotationInstanceBean[] newAnnotations = new AnnotationInstanceBean[oldAnnotations.length];

               for(i = 0; i < newAnnotations.length; ++i) {
                  newAnnotations[i] = (AnnotationInstanceBean)((AnnotationInstanceBean)this.createCopy((AbstractDescriptorBean)oldAnnotations[i], includeObsolete));
               }

               copy.setAnnotations(newAnnotations);
            }

            if ((excludeProps == null || !excludeProps.contains("ComponentType")) && this.bean.isComponentTypeSet()) {
               copy.setComponentType(this.bean.getComponentType());
            }

            if ((excludeProps == null || !excludeProps.contains("Fields")) && this.bean.isFieldsSet() && !copy._isSet(3)) {
               AnnotatedFieldBean[] oldFields = this.bean.getFields();
               AnnotatedFieldBean[] newFields = new AnnotatedFieldBean[oldFields.length];

               for(i = 0; i < newFields.length; ++i) {
                  newFields[i] = (AnnotatedFieldBean)((AnnotatedFieldBean)this.createCopy((AbstractDescriptorBean)oldFields[i], includeObsolete));
               }

               copy.setFields(newFields);
            }

            if ((excludeProps == null || !excludeProps.contains("Methods")) && this.bean.isMethodsSet() && !copy._isSet(4)) {
               AnnotatedMethodBean[] oldMethods = this.bean.getMethods();
               AnnotatedMethodBean[] newMethods = new AnnotatedMethodBean[oldMethods.length];

               for(i = 0; i < newMethods.length; ++i) {
                  newMethods[i] = (AnnotatedMethodBean)((AnnotatedMethodBean)this.createCopy((AbstractDescriptorBean)oldMethods[i], includeObsolete));
               }

               copy.setMethods(newMethods);
            }

            if ((excludeProps == null || !excludeProps.contains("ShortDescription")) && this.bean.isShortDescriptionSet()) {
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
         this.inferSubTree(this.bean.getFields(), clazz, annotation);
         this.inferSubTree(this.bean.getMethods(), clazz, annotation);
      }
   }
}
