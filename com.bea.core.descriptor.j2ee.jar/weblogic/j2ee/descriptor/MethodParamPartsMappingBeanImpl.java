package weblogic.j2ee.descriptor;

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

public class MethodParamPartsMappingBeanImpl extends AbstractDescriptorBean implements MethodParamPartsMappingBean, Serializable {
   private String _Id;
   private int _ParamPosition;
   private String _ParamType;
   private WsdlMessageMappingBean _WsdlMessageMapping;
   private static SchemaHelper2 _schemaHelper;

   public MethodParamPartsMappingBeanImpl() {
      this._initializeProperty(-1);
   }

   public MethodParamPartsMappingBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MethodParamPartsMappingBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getParamPosition() {
      return this._ParamPosition;
   }

   public boolean isParamPositionInherited() {
      return false;
   }

   public boolean isParamPositionSet() {
      return this._isSet(0);
   }

   public void setParamPosition(int param0) {
      int _oldVal = this._ParamPosition;
      this._ParamPosition = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getParamType() {
      return this._ParamType;
   }

   public boolean isParamTypeInherited() {
      return false;
   }

   public boolean isParamTypeSet() {
      return this._isSet(1);
   }

   public void setParamType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ParamType;
      this._ParamType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public WsdlMessageMappingBean getWsdlMessageMapping() {
      return this._WsdlMessageMapping;
   }

   public boolean isWsdlMessageMappingInherited() {
      return false;
   }

   public boolean isWsdlMessageMappingSet() {
      return this._isSet(2);
   }

   public void setWsdlMessageMapping(WsdlMessageMappingBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getWsdlMessageMapping() != null && param0 != this.getWsdlMessageMapping()) {
         throw new BeanAlreadyExistsException(this.getWsdlMessageMapping() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 2)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         WsdlMessageMappingBean _oldVal = this._WsdlMessageMapping;
         this._WsdlMessageMapping = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public WsdlMessageMappingBean createWsdlMessageMapping() {
      WsdlMessageMappingBeanImpl _val = new WsdlMessageMappingBeanImpl(this, -1);

      try {
         this.setWsdlMessageMapping(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyWsdlMessageMapping(WsdlMessageMappingBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._WsdlMessageMapping;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setWsdlMessageMapping((WsdlMessageMappingBean)null);
               this._unSet(2);
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

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(3);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(3, _oldVal, param0);
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
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ParamPosition = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._ParamType = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._WsdlMessageMapping = null;
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
                  return 3;
               }
               break;
            case 10:
               if (s.equals("param-type")) {
                  return 1;
               }
               break;
            case 14:
               if (s.equals("param-position")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("wsdl-message-mapping")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new WsdlMessageMappingBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "param-position";
            case 1:
               return "param-type";
            case 2:
               return "wsdl-message-mapping";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
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
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private MethodParamPartsMappingBeanImpl bean;

      protected Helper(MethodParamPartsMappingBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ParamPosition";
            case 1:
               return "ParamType";
            case 2:
               return "WsdlMessageMapping";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("ParamPosition")) {
            return 0;
         } else if (propName.equals("ParamType")) {
            return 1;
         } else {
            return propName.equals("WsdlMessageMapping") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWsdlMessageMapping() != null) {
            iterators.add(new ArrayIterator(new WsdlMessageMappingBean[]{this.bean.getWsdlMessageMapping()}));
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isParamPositionSet()) {
               buf.append("ParamPosition");
               buf.append(String.valueOf(this.bean.getParamPosition()));
            }

            if (this.bean.isParamTypeSet()) {
               buf.append("ParamType");
               buf.append(String.valueOf(this.bean.getParamType()));
            }

            childValue = this.computeChildHashValue(this.bean.getWsdlMessageMapping());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
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
            MethodParamPartsMappingBeanImpl otherTyped = (MethodParamPartsMappingBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("ParamPosition", this.bean.getParamPosition(), otherTyped.getParamPosition(), false);
            this.computeDiff("ParamType", this.bean.getParamType(), otherTyped.getParamType(), false);
            this.computeChildDiff("WsdlMessageMapping", this.bean.getWsdlMessageMapping(), otherTyped.getWsdlMessageMapping(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MethodParamPartsMappingBeanImpl original = (MethodParamPartsMappingBeanImpl)event.getSourceBean();
            MethodParamPartsMappingBeanImpl proposed = (MethodParamPartsMappingBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ParamPosition")) {
                  original.setParamPosition(proposed.getParamPosition());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ParamType")) {
                  original.setParamType(proposed.getParamType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("WsdlMessageMapping")) {
                  if (type == 2) {
                     original.setWsdlMessageMapping((WsdlMessageMappingBean)this.createCopy((AbstractDescriptorBean)proposed.getWsdlMessageMapping()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WsdlMessageMapping", (DescriptorBean)original.getWsdlMessageMapping());
                  }

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
            MethodParamPartsMappingBeanImpl copy = (MethodParamPartsMappingBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("ParamPosition")) && this.bean.isParamPositionSet()) {
               copy.setParamPosition(this.bean.getParamPosition());
            }

            if ((excludeProps == null || !excludeProps.contains("ParamType")) && this.bean.isParamTypeSet()) {
               copy.setParamType(this.bean.getParamType());
            }

            if ((excludeProps == null || !excludeProps.contains("WsdlMessageMapping")) && this.bean.isWsdlMessageMappingSet() && !copy._isSet(2)) {
               Object o = this.bean.getWsdlMessageMapping();
               copy.setWsdlMessageMapping((WsdlMessageMappingBean)null);
               copy.setWsdlMessageMapping(o == null ? null : (WsdlMessageMappingBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getWsdlMessageMapping(), clazz, annotation);
      }
   }
}
