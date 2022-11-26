package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import javax.xml.namespace.QName;
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

public class JavaXmlTypeMappingBeanImpl extends AbstractDescriptorBean implements JavaXmlTypeMappingBean, Serializable {
   private String _AnonymousTypeQname;
   private String _Id;
   private String _JavaType;
   private String _QnameScope;
   private QName _RootTypeQname;
   private VariableMappingBean[] _VariableMappings;
   private static SchemaHelper2 _schemaHelper;

   public JavaXmlTypeMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public JavaXmlTypeMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JavaXmlTypeMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getJavaType() {
      return this._JavaType;
   }

   public boolean isJavaTypeInherited() {
      return false;
   }

   public boolean isJavaTypeSet() {
      return this._isSet(0);
   }

   public void setJavaType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JavaType;
      this._JavaType = param0;
      this._postSet(0, _oldVal, param0);
   }

   public QName getRootTypeQname() {
      return this._RootTypeQname;
   }

   public boolean isRootTypeQnameInherited() {
      return false;
   }

   public boolean isRootTypeQnameSet() {
      return this._isSet(1);
   }

   public void setRootTypeQname(QName param0) {
      QName _oldVal = this._RootTypeQname;
      this._RootTypeQname = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getAnonymousTypeQname() {
      return this._AnonymousTypeQname;
   }

   public boolean isAnonymousTypeQnameInherited() {
      return false;
   }

   public boolean isAnonymousTypeQnameSet() {
      return this._isSet(2);
   }

   public void setAnonymousTypeQname(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._AnonymousTypeQname;
      this._AnonymousTypeQname = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String getQnameScope() {
      return this._QnameScope;
   }

   public boolean isQnameScopeInherited() {
      return false;
   }

   public boolean isQnameScopeSet() {
      return this._isSet(3);
   }

   public void setQnameScope(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._QnameScope;
      this._QnameScope = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addVariableMapping(VariableMappingBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 4)) {
         VariableMappingBean[] _new;
         if (this._isSet(4)) {
            _new = (VariableMappingBean[])((VariableMappingBean[])this._getHelper()._extendArray(this.getVariableMappings(), VariableMappingBean.class, param0));
         } else {
            _new = new VariableMappingBean[]{param0};
         }

         try {
            this.setVariableMappings(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public VariableMappingBean[] getVariableMappings() {
      return this._VariableMappings;
   }

   public boolean isVariableMappingsInherited() {
      return false;
   }

   public boolean isVariableMappingsSet() {
      return this._isSet(4);
   }

   public void removeVariableMapping(VariableMappingBean param0) {
      this.destroyVariableMapping(param0);
   }

   public void setVariableMappings(VariableMappingBean[] param0) throws InvalidAttributeValueException {
      VariableMappingBean[] param0 = param0 == null ? new VariableMappingBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 4)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      VariableMappingBean[] _oldVal = this._VariableMappings;
      this._VariableMappings = (VariableMappingBean[])param0;
      this._postSet(4, _oldVal, param0);
   }

   public VariableMappingBean createVariableMapping() {
      VariableMappingBeanImpl _val = new VariableMappingBeanImpl(this, -1);

      try {
         this.addVariableMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyVariableMapping(VariableMappingBean param0) {
      try {
         this._checkIsPotentialChild(param0, 4);
         VariableMappingBean[] _old = this.getVariableMappings();
         VariableMappingBean[] _new = (VariableMappingBean[])((VariableMappingBean[])this._getHelper()._removeElement(_old, VariableMappingBean.class, param0));
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
               this.setVariableMappings(_new);
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
      return this._isSet(5);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(5, _oldVal, param0);
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
               this._AnonymousTypeQname = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._JavaType = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._QnameScope = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._RootTypeQname = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._VariableMappings = new VariableMappingBean[0];
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
                  return 5;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 10:
            case 12:
            case 13:
            case 14:
            case 17:
            case 18:
            case 19:
            default:
               break;
            case 9:
               if (s.equals("java-type")) {
                  return 0;
               }
               break;
            case 11:
               if (s.equals("qname-scope")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("root-type-qname")) {
                  return 1;
               }
               break;
            case 16:
               if (s.equals("variable-mapping")) {
                  return 4;
               }
               break;
            case 20:
               if (s.equals("anonymous-type-qname")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new VariableMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "java-type";
            case 1:
               return "root-type-qname";
            case 2:
               return "anonymous-type-qname";
            case 3:
               return "qname-scope";
            case 4:
               return "variable-mapping";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JavaXmlTypeMappingBeanImpl bean;

      protected Helper(JavaXmlTypeMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "JavaType";
            case 1:
               return "RootTypeQname";
            case 2:
               return "AnonymousTypeQname";
            case 3:
               return "QnameScope";
            case 4:
               return "VariableMappings";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("AnonymousTypeQname")) {
            return 2;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("JavaType")) {
            return 0;
         } else if (propName.equals("QnameScope")) {
            return 3;
         } else if (propName.equals("RootTypeQname")) {
            return 1;
         } else {
            return propName.equals("VariableMappings") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getVariableMappings()));
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
            if (this.bean.isAnonymousTypeQnameSet()) {
               buf.append("AnonymousTypeQname");
               buf.append(String.valueOf(this.bean.getAnonymousTypeQname()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isJavaTypeSet()) {
               buf.append("JavaType");
               buf.append(String.valueOf(this.bean.getJavaType()));
            }

            if (this.bean.isQnameScopeSet()) {
               buf.append("QnameScope");
               buf.append(String.valueOf(this.bean.getQnameScope()));
            }

            if (this.bean.isRootTypeQnameSet()) {
               buf.append("RootTypeQname");
               buf.append(String.valueOf(this.bean.getRootTypeQname()));
            }

            childValue = 0L;

            for(int i = 0; i < this.bean.getVariableMappings().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getVariableMappings()[i]);
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
            JavaXmlTypeMappingBeanImpl otherTyped = (JavaXmlTypeMappingBeanImpl)other;
            this.computeDiff("AnonymousTypeQname", this.bean.getAnonymousTypeQname(), otherTyped.getAnonymousTypeQname(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("JavaType", this.bean.getJavaType(), otherTyped.getJavaType(), false);
            this.computeDiff("QnameScope", this.bean.getQnameScope(), otherTyped.getQnameScope(), false);
            this.computeDiff("RootTypeQname", this.bean.getRootTypeQname(), otherTyped.getRootTypeQname(), false);
            this.computeChildDiff("VariableMappings", this.bean.getVariableMappings(), otherTyped.getVariableMappings(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JavaXmlTypeMappingBeanImpl original = (JavaXmlTypeMappingBeanImpl)event.getSourceBean();
            JavaXmlTypeMappingBeanImpl proposed = (JavaXmlTypeMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("AnonymousTypeQname")) {
                  original.setAnonymousTypeQname(proposed.getAnonymousTypeQname());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("JavaType")) {
                  original.setJavaType(proposed.getJavaType());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("QnameScope")) {
                  original.setQnameScope(proposed.getQnameScope());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RootTypeQname")) {
                  original.setRootTypeQname(proposed.getRootTypeQname());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("VariableMappings")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addVariableMapping((VariableMappingBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeVariableMapping((VariableMappingBean)update.getRemovedObject());
                  }

                  if (original.getVariableMappings() == null || original.getVariableMappings().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            JavaXmlTypeMappingBeanImpl copy = (JavaXmlTypeMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("AnonymousTypeQname")) && this.bean.isAnonymousTypeQnameSet()) {
               copy.setAnonymousTypeQname(this.bean.getAnonymousTypeQname());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("JavaType")) && this.bean.isJavaTypeSet()) {
               copy.setJavaType(this.bean.getJavaType());
            }

            if ((excludeProps == null || !excludeProps.contains("QnameScope")) && this.bean.isQnameScopeSet()) {
               copy.setQnameScope(this.bean.getQnameScope());
            }

            if ((excludeProps == null || !excludeProps.contains("RootTypeQname")) && this.bean.isRootTypeQnameSet()) {
               copy.setRootTypeQname(this.bean.getRootTypeQname());
            }

            if ((excludeProps == null || !excludeProps.contains("VariableMappings")) && this.bean.isVariableMappingsSet() && !copy._isSet(4)) {
               VariableMappingBean[] oldVariableMappings = this.bean.getVariableMappings();
               VariableMappingBean[] newVariableMappings = new VariableMappingBean[oldVariableMappings.length];

               for(int i = 0; i < newVariableMappings.length; ++i) {
                  newVariableMappings[i] = (VariableMappingBean)((VariableMappingBean)this.createCopy((AbstractDescriptorBean)oldVariableMappings[i], includeObsolete));
               }

               copy.setVariableMappings(newVariableMappings);
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
         this.inferSubTree(this.bean.getVariableMappings(), clazz, annotation);
      }
   }
}
