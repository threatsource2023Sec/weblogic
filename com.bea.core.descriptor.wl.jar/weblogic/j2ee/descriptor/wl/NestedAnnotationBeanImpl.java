package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class NestedAnnotationBeanImpl extends AbstractDescriptorBean implements NestedAnnotationBean, Serializable {
   private AnnotationInstanceBean _Annotation;
   private String _MemberName;
   private static SchemaHelper2 _schemaHelper;

   public NestedAnnotationBeanImpl() {
      this._initializeProperty(-1);
   }

   public NestedAnnotationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public NestedAnnotationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getMemberName() {
      return this._MemberName;
   }

   public boolean isMemberNameInherited() {
      return false;
   }

   public boolean isMemberNameSet() {
      return this._isSet(0);
   }

   public void setMemberName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MemberName;
      this._MemberName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public AnnotationInstanceBean getAnnotation() {
      return this._Annotation;
   }

   public boolean isAnnotationInherited() {
      return false;
   }

   public boolean isAnnotationSet() {
      return this._isSet(1);
   }

   public void setAnnotation(AnnotationInstanceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getAnnotation() != null && param0 != this.getAnnotation()) {
         throw new BeanAlreadyExistsException(this.getAnnotation() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         AnnotationInstanceBean _oldVal = this._Annotation;
         this._Annotation = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public AnnotationInstanceBean createAnnotation() {
      AnnotationInstanceBeanImpl _val = new AnnotationInstanceBeanImpl(this, -1);

      try {
         this.setAnnotation(_val);
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
      return this.getMemberName();
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
         case 11:
            if (s.equals("member-name")) {
               return info.compareXpaths(this._getPropertyXpath("member-name"));
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
               this._Annotation = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._MemberName = null;
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
                  return 1;
               }
               break;
            case 11:
               if (s.equals("member-name")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new AnnotationInstanceBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "member-name";
            case 1:
               return "annotation";
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
         indices.add("member-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private NestedAnnotationBeanImpl bean;

      protected Helper(NestedAnnotationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "MemberName";
            case 1:
               return "Annotation";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Annotation")) {
            return 1;
         } else {
            return propName.equals("MemberName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getAnnotation() != null) {
            iterators.add(new ArrayIterator(new AnnotationInstanceBean[]{this.bean.getAnnotation()}));
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
            childValue = this.computeChildHashValue(this.bean.getAnnotation());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isMemberNameSet()) {
               buf.append("MemberName");
               buf.append(String.valueOf(this.bean.getMemberName()));
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
            NestedAnnotationBeanImpl otherTyped = (NestedAnnotationBeanImpl)other;
            this.computeChildDiff("Annotation", this.bean.getAnnotation(), otherTyped.getAnnotation(), false);
            this.computeDiff("MemberName", this.bean.getMemberName(), otherTyped.getMemberName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            NestedAnnotationBeanImpl original = (NestedAnnotationBeanImpl)event.getSourceBean();
            NestedAnnotationBeanImpl proposed = (NestedAnnotationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Annotation")) {
                  if (type == 2) {
                     original.setAnnotation((AnnotationInstanceBean)this.createCopy((AbstractDescriptorBean)proposed.getAnnotation()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Annotation", (DescriptorBean)original.getAnnotation());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("MemberName")) {
                  original.setMemberName(proposed.getMemberName());
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
            NestedAnnotationBeanImpl copy = (NestedAnnotationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Annotation")) && this.bean.isAnnotationSet() && !copy._isSet(1)) {
               Object o = this.bean.getAnnotation();
               copy.setAnnotation((AnnotationInstanceBean)null);
               copy.setAnnotation(o == null ? null : (AnnotationInstanceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("MemberName")) && this.bean.isMemberNameSet()) {
               copy.setMemberName(this.bean.getMemberName());
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
         this.inferSubTree(this.bean.getAnnotation(), clazz, annotation);
      }
   }
}
