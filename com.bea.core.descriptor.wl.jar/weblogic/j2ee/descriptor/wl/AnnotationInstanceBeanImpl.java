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
import weblogic.j2ee.descriptor.wl.customizers.AnnotationInstanceBeanCustomizer;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class AnnotationInstanceBeanImpl extends AbstractDescriptorBean implements AnnotationInstanceBean, Serializable {
   private String _AnnotationClassName;
   private ArrayMemberBean[] _ArrayMembers;
   private MemberBean[] _Members;
   private NestedAnnotationArrayBean[] _NestedAnnotationArrays;
   private NestedAnnotationBean[] _NestedAnnotations;
   private String _ShortDescription;
   private transient AnnotationInstanceBeanCustomizer _customizer;
   private static SchemaHelper2 _schemaHelper;

   public AnnotationInstanceBeanImpl() {
      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.AnnotationInstanceBeanCustomizerFactory");
         this._customizer = (AnnotationInstanceBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public AnnotationInstanceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.AnnotationInstanceBeanCustomizerFactory");
         this._customizer = (AnnotationInstanceBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public AnnotationInstanceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         CustomizerFactory customizerFactory = CustomizerFactoryBuilder.buildFactory("weblogic.j2ee.customizers.AnnotationInstanceBeanCustomizerFactory");
         this._customizer = (AnnotationInstanceBeanCustomizer)customizerFactory.createCustomizer(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public String getAnnotationClassName() {
      return this._AnnotationClassName;
   }

   public boolean isAnnotationClassNameInherited() {
      return false;
   }

   public boolean isAnnotationClassNameSet() {
      return this._isSet(0);
   }

   public void setAnnotationClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AnnotationClassName;
      this._AnnotationClassName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public void addMember(MemberBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 1)) {
         MemberBean[] _new;
         if (this._isSet(1)) {
            _new = (MemberBean[])((MemberBean[])this._getHelper()._extendArray(this.getMembers(), MemberBean.class, param0));
         } else {
            _new = new MemberBean[]{param0};
         }

         try {
            this.setMembers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public MemberBean[] getMembers() {
      return this._Members;
   }

   public boolean isMembersInherited() {
      return false;
   }

   public boolean isMembersSet() {
      return this._isSet(1);
   }

   public void removeMember(MemberBean param0) {
      MemberBean[] _old = this.getMembers();
      MemberBean[] _new = (MemberBean[])((MemberBean[])this._getHelper()._removeElement(_old, MemberBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setMembers(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setMembers(MemberBean[] param0) throws InvalidAttributeValueException {
      MemberBean[] param0 = param0 == null ? new MemberBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 1)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      MemberBean[] _oldVal = this._Members;
      this._Members = (MemberBean[])param0;
      this._postSet(1, _oldVal, param0);
   }

   public MemberBean createMember() {
      MemberBeanImpl _val = new MemberBeanImpl(this, -1);

      try {
         this.addMember(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addArrayMember(ArrayMemberBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 2)) {
         ArrayMemberBean[] _new;
         if (this._isSet(2)) {
            _new = (ArrayMemberBean[])((ArrayMemberBean[])this._getHelper()._extendArray(this.getArrayMembers(), ArrayMemberBean.class, param0));
         } else {
            _new = new ArrayMemberBean[]{param0};
         }

         try {
            this.setArrayMembers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ArrayMemberBean[] getArrayMembers() {
      return this._ArrayMembers;
   }

   public boolean isArrayMembersInherited() {
      return false;
   }

   public boolean isArrayMembersSet() {
      return this._isSet(2);
   }

   public void removeArrayMember(ArrayMemberBean param0) {
      ArrayMemberBean[] _old = this.getArrayMembers();
      ArrayMemberBean[] _new = (ArrayMemberBean[])((ArrayMemberBean[])this._getHelper()._removeElement(_old, ArrayMemberBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setArrayMembers(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setArrayMembers(ArrayMemberBean[] param0) throws InvalidAttributeValueException {
      ArrayMemberBean[] param0 = param0 == null ? new ArrayMemberBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 2)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ArrayMemberBean[] _oldVal = this._ArrayMembers;
      this._ArrayMembers = (ArrayMemberBean[])param0;
      this._postSet(2, _oldVal, param0);
   }

   public ArrayMemberBean createArrayMember() {
      ArrayMemberBeanImpl _val = new ArrayMemberBeanImpl(this, -1);

      try {
         this.addArrayMember(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addNestedAnnotation(NestedAnnotationBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         NestedAnnotationBean[] _new;
         if (this._isSet(3)) {
            _new = (NestedAnnotationBean[])((NestedAnnotationBean[])this._getHelper()._extendArray(this.getNestedAnnotations(), NestedAnnotationBean.class, param0));
         } else {
            _new = new NestedAnnotationBean[]{param0};
         }

         try {
            this.setNestedAnnotations(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public NestedAnnotationBean[] getNestedAnnotations() {
      return this._NestedAnnotations;
   }

   public boolean isNestedAnnotationsInherited() {
      return false;
   }

   public boolean isNestedAnnotationsSet() {
      return this._isSet(3);
   }

   public void removeNestedAnnotation(NestedAnnotationBean param0) {
      NestedAnnotationBean[] _old = this.getNestedAnnotations();
      NestedAnnotationBean[] _new = (NestedAnnotationBean[])((NestedAnnotationBean[])this._getHelper()._removeElement(_old, NestedAnnotationBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setNestedAnnotations(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setNestedAnnotations(NestedAnnotationBean[] param0) throws InvalidAttributeValueException {
      NestedAnnotationBean[] param0 = param0 == null ? new NestedAnnotationBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      NestedAnnotationBean[] _oldVal = this._NestedAnnotations;
      this._NestedAnnotations = (NestedAnnotationBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public NestedAnnotationBean createNestedAnnotation() {
      NestedAnnotationBeanImpl _val = new NestedAnnotationBeanImpl(this, -1);

      try {
         this.addNestedAnnotation(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void addNestedAnnotationArray(NestedAnnotationArrayBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         NestedAnnotationArrayBean[] _new;
         if (this._isSet(4)) {
            _new = (NestedAnnotationArrayBean[])((NestedAnnotationArrayBean[])this._getHelper()._extendArray(this.getNestedAnnotationArrays(), NestedAnnotationArrayBean.class, param0));
         } else {
            _new = new NestedAnnotationArrayBean[]{param0};
         }

         try {
            this.setNestedAnnotationArrays(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public NestedAnnotationArrayBean[] getNestedAnnotationArrays() {
      return this._NestedAnnotationArrays;
   }

   public boolean isNestedAnnotationArraysInherited() {
      return false;
   }

   public boolean isNestedAnnotationArraysSet() {
      return this._isSet(4);
   }

   public void removeNestedAnnotationArray(NestedAnnotationArrayBean param0) {
      NestedAnnotationArrayBean[] _old = this.getNestedAnnotationArrays();
      NestedAnnotationArrayBean[] _new = (NestedAnnotationArrayBean[])((NestedAnnotationArrayBean[])this._getHelper()._removeElement(_old, NestedAnnotationArrayBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setNestedAnnotationArrays(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setNestedAnnotationArrays(NestedAnnotationArrayBean[] param0) throws InvalidAttributeValueException {
      NestedAnnotationArrayBean[] param0 = param0 == null ? new NestedAnnotationArrayBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      NestedAnnotationArrayBean[] _oldVal = this._NestedAnnotationArrays;
      this._NestedAnnotationArrays = (NestedAnnotationArrayBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public NestedAnnotationArrayBean createNestedAnnotationArray() {
      NestedAnnotationArrayBeanImpl _val = new NestedAnnotationArrayBeanImpl(this, -1);

      try {
         this.addNestedAnnotationArray(_val);
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
      return this.getAnnotationClassName();
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
         case 21:
            if (s.equals("annotation-class-name")) {
               return info.compareXpaths(this._getPropertyXpath("annotation-class-name"));
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
               this._AnnotationClassName = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ArrayMembers = new ArrayMemberBean[0];
               if (initOne) {
                  break;
               }
            case 1:
               this._Members = new MemberBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._NestedAnnotationArrays = new NestedAnnotationArrayBean[0];
               if (initOne) {
                  break;
               }
            case 3:
               this._NestedAnnotations = new NestedAnnotationBean[0];
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
            case 6:
               if (s.equals("member")) {
                  return 1;
               }
               break;
            case 12:
               if (s.equals("array-member")) {
                  return 2;
               }
               break;
            case 17:
               if (s.equals("nested-annotation")) {
                  return 3;
               }

               if (s.equals("short-description")) {
                  return 5;
               }
               break;
            case 21:
               if (s.equals("annotation-class-name")) {
                  return 0;
               }
               break;
            case 23:
               if (s.equals("nested-annotation-array")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new MemberBeanImpl.SchemaHelper2();
            case 2:
               return new ArrayMemberBeanImpl.SchemaHelper2();
            case 3:
               return new NestedAnnotationBeanImpl.SchemaHelper2();
            case 4:
               return new NestedAnnotationArrayBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "annotation-class-name";
            case 1:
               return "member";
            case 2:
               return "array-member";
            case 3:
               return "nested-annotation";
            case 4:
               return "nested-annotation-array";
            case 5:
               return "short-description";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
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
            case 1:
               return true;
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
         indices.add("annotation-class-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private AnnotationInstanceBeanImpl bean;

      protected Helper(AnnotationInstanceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "AnnotationClassName";
            case 1:
               return "Members";
            case 2:
               return "ArrayMembers";
            case 3:
               return "NestedAnnotations";
            case 4:
               return "NestedAnnotationArrays";
            case 5:
               return "ShortDescription";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AnnotationClassName")) {
            return 0;
         } else if (propName.equals("ArrayMembers")) {
            return 2;
         } else if (propName.equals("Members")) {
            return 1;
         } else if (propName.equals("NestedAnnotationArrays")) {
            return 4;
         } else if (propName.equals("NestedAnnotations")) {
            return 3;
         } else {
            return propName.equals("ShortDescription") ? 5 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getArrayMembers()));
         iterators.add(new ArrayIterator(this.bean.getMembers()));
         iterators.add(new ArrayIterator(this.bean.getNestedAnnotationArrays()));
         iterators.add(new ArrayIterator(this.bean.getNestedAnnotations()));
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
            if (this.bean.isAnnotationClassNameSet()) {
               buf.append("AnnotationClassName");
               buf.append(String.valueOf(this.bean.getAnnotationClassName()));
            }

            childValue = 0L;

            int i;
            for(i = 0; i < this.bean.getArrayMembers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getArrayMembers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getMembers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getMembers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getNestedAnnotationArrays().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getNestedAnnotationArrays()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = 0L;

            for(i = 0; i < this.bean.getNestedAnnotations().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getNestedAnnotations()[i]);
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
            AnnotationInstanceBeanImpl otherTyped = (AnnotationInstanceBeanImpl)other;
            this.computeDiff("AnnotationClassName", this.bean.getAnnotationClassName(), otherTyped.getAnnotationClassName(), false);
            this.computeChildDiff("ArrayMembers", this.bean.getArrayMembers(), otherTyped.getArrayMembers(), false);
            this.computeChildDiff("Members", this.bean.getMembers(), otherTyped.getMembers(), false);
            this.computeChildDiff("NestedAnnotationArrays", this.bean.getNestedAnnotationArrays(), otherTyped.getNestedAnnotationArrays(), false);
            this.computeChildDiff("NestedAnnotations", this.bean.getNestedAnnotations(), otherTyped.getNestedAnnotations(), false);
            this.computeDiff("ShortDescription", this.bean.getShortDescription(), otherTyped.getShortDescription(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            AnnotationInstanceBeanImpl original = (AnnotationInstanceBeanImpl)event.getSourceBean();
            AnnotationInstanceBeanImpl proposed = (AnnotationInstanceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AnnotationClassName")) {
                  original.setAnnotationClassName(proposed.getAnnotationClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ArrayMembers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addArrayMember((ArrayMemberBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeArrayMember((ArrayMemberBean)update.getRemovedObject());
                  }

                  if (original.getArrayMembers() == null || original.getArrayMembers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 2);
                  }
               } else if (prop.equals("Members")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addMember((MemberBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMember((MemberBean)update.getRemovedObject());
                  }

                  if (original.getMembers() == null || original.getMembers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
                  }
               } else if (prop.equals("NestedAnnotationArrays")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addNestedAnnotationArray((NestedAnnotationArrayBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeNestedAnnotationArray((NestedAnnotationArrayBean)update.getRemovedObject());
                  }

                  if (original.getNestedAnnotationArrays() == null || original.getNestedAnnotationArrays().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
                  }
               } else if (prop.equals("NestedAnnotations")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addNestedAnnotation((NestedAnnotationBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeNestedAnnotation((NestedAnnotationBean)update.getRemovedObject());
                  }

                  if (original.getNestedAnnotations() == null || original.getNestedAnnotations().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            AnnotationInstanceBeanImpl copy = (AnnotationInstanceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AnnotationClassName")) && this.bean.isAnnotationClassNameSet()) {
               copy.setAnnotationClassName(this.bean.getAnnotationClassName());
            }

            int i;
            if ((excludeProps == null || !excludeProps.contains("ArrayMembers")) && this.bean.isArrayMembersSet() && !copy._isSet(2)) {
               ArrayMemberBean[] oldArrayMembers = this.bean.getArrayMembers();
               ArrayMemberBean[] newArrayMembers = new ArrayMemberBean[oldArrayMembers.length];

               for(i = 0; i < newArrayMembers.length; ++i) {
                  newArrayMembers[i] = (ArrayMemberBean)((ArrayMemberBean)this.createCopy((AbstractDescriptorBean)oldArrayMembers[i], includeObsolete));
               }

               copy.setArrayMembers(newArrayMembers);
            }

            if ((excludeProps == null || !excludeProps.contains("Members")) && this.bean.isMembersSet() && !copy._isSet(1)) {
               MemberBean[] oldMembers = this.bean.getMembers();
               MemberBean[] newMembers = new MemberBean[oldMembers.length];

               for(i = 0; i < newMembers.length; ++i) {
                  newMembers[i] = (MemberBean)((MemberBean)this.createCopy((AbstractDescriptorBean)oldMembers[i], includeObsolete));
               }

               copy.setMembers(newMembers);
            }

            if ((excludeProps == null || !excludeProps.contains("NestedAnnotationArrays")) && this.bean.isNestedAnnotationArraysSet() && !copy._isSet(4)) {
               NestedAnnotationArrayBean[] oldNestedAnnotationArrays = this.bean.getNestedAnnotationArrays();
               NestedAnnotationArrayBean[] newNestedAnnotationArrays = new NestedAnnotationArrayBean[oldNestedAnnotationArrays.length];

               for(i = 0; i < newNestedAnnotationArrays.length; ++i) {
                  newNestedAnnotationArrays[i] = (NestedAnnotationArrayBean)((NestedAnnotationArrayBean)this.createCopy((AbstractDescriptorBean)oldNestedAnnotationArrays[i], includeObsolete));
               }

               copy.setNestedAnnotationArrays(newNestedAnnotationArrays);
            }

            if ((excludeProps == null || !excludeProps.contains("NestedAnnotations")) && this.bean.isNestedAnnotationsSet() && !copy._isSet(3)) {
               NestedAnnotationBean[] oldNestedAnnotations = this.bean.getNestedAnnotations();
               NestedAnnotationBean[] newNestedAnnotations = new NestedAnnotationBean[oldNestedAnnotations.length];

               for(i = 0; i < newNestedAnnotations.length; ++i) {
                  newNestedAnnotations[i] = (NestedAnnotationBean)((NestedAnnotationBean)this.createCopy((AbstractDescriptorBean)oldNestedAnnotations[i], includeObsolete));
               }

               copy.setNestedAnnotations(newNestedAnnotations);
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
         this.inferSubTree(this.bean.getArrayMembers(), clazz, annotation);
         this.inferSubTree(this.bean.getMembers(), clazz, annotation);
         this.inferSubTree(this.bean.getNestedAnnotationArrays(), clazz, annotation);
         this.inferSubTree(this.bean.getNestedAnnotations(), clazz, annotation);
      }
   }
}
