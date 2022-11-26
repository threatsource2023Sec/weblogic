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
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class MethodBeanImpl extends AbstractDescriptorBean implements MethodBean, Serializable {
   private String _Description;
   private String _EjbName;
   private String _Id;
   private String _MethodIntf;
   private String _MethodName;
   private MethodParamsBean _MethodParams;
   private static SchemaHelper2 _schemaHelper;

   public MethodBeanImpl() {
      this._initializeProperty(-1);
   }

   public MethodBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public MethodBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getDescription() {
      return this._Description;
   }

   public boolean isDescriptionInherited() {
      return false;
   }

   public boolean isDescriptionSet() {
      return this._isSet(0);
   }

   public void setDescription(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Description;
      this._Description = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getEjbName() {
      return this._EjbName;
   }

   public boolean isEjbNameInherited() {
      return false;
   }

   public boolean isEjbNameSet() {
      return this._isSet(1);
   }

   public void setEjbName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._EjbName;
      this._EjbName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getMethodIntf() {
      return !this._isSet(2) ? null : this._MethodIntf;
   }

   public boolean isMethodIntfInherited() {
      return false;
   }

   public boolean isMethodIntfSet() {
      return this._isSet(2);
   }

   public void setMethodIntf(String param0) {
      if (param0 == null) {
         this._unSet(2);
      } else {
         param0 = param0 == null ? null : param0.trim();
         String[] _set = new String[]{"Home", "Remote", "Local", "LocalHome", "ServiceEndpoint"};
         param0 = LegalChecks.checkInEnum("MethodIntf", param0, _set);
         String _oldVal = this._MethodIntf;
         this._MethodIntf = param0;
         this._postSet(2, _oldVal, param0);
      }
   }

   public String getMethodName() {
      return this._MethodName;
   }

   public boolean isMethodNameInherited() {
      return false;
   }

   public boolean isMethodNameSet() {
      return this._isSet(3);
   }

   public void setMethodName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MethodName;
      this._MethodName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public MethodParamsBean getMethodParams() {
      return this._MethodParams;
   }

   public boolean isMethodParamsInherited() {
      return false;
   }

   public boolean isMethodParamsSet() {
      return this._isSet(4);
   }

   public void setMethodParams(MethodParamsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getMethodParams() != null && param0 != this.getMethodParams()) {
         throw new BeanAlreadyExistsException(this.getMethodParams() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         MethodParamsBean _oldVal = this._MethodParams;
         this._MethodParams = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public MethodParamsBean createMethodParams() {
      MethodParamsBeanImpl _val = new MethodParamsBeanImpl(this, -1);

      try {
         this.setMethodParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyMethodParams(MethodParamsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._MethodParams;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setMethodParams((MethodParamsBean)null);
               this._unSet(4);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._Description = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._EjbName = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._MethodIntf = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._MethodName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._MethodParams = null;
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
               break;
            case 8:
               if (s.equals("ejb-name")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("description")) {
                  return 0;
               }

               if (s.equals("method-intf")) {
                  return 2;
               }

               if (s.equals("method-name")) {
                  return 3;
               }
               break;
            case 13:
               if (s.equals("method-params")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new MethodParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "description";
            case 1:
               return "ejb-name";
            case 2:
               return "method-intf";
            case 3:
               return "method-name";
            case 4:
               return "method-params";
            case 5:
               return "id";
            default:
               return super.getElementName(propIndex);
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
      private MethodBeanImpl bean;

      protected Helper(MethodBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Description";
            case 1:
               return "EjbName";
            case 2:
               return "MethodIntf";
            case 3:
               return "MethodName";
            case 4:
               return "MethodParams";
            case 5:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Description")) {
            return 0;
         } else if (propName.equals("EjbName")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 5;
         } else if (propName.equals("MethodIntf")) {
            return 2;
         } else if (propName.equals("MethodName")) {
            return 3;
         } else {
            return propName.equals("MethodParams") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getMethodParams() != null) {
            iterators.add(new ArrayIterator(new MethodParamsBean[]{this.bean.getMethodParams()}));
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
            if (this.bean.isDescriptionSet()) {
               buf.append("Description");
               buf.append(String.valueOf(this.bean.getDescription()));
            }

            if (this.bean.isEjbNameSet()) {
               buf.append("EjbName");
               buf.append(String.valueOf(this.bean.getEjbName()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isMethodIntfSet()) {
               buf.append("MethodIntf");
               buf.append(String.valueOf(this.bean.getMethodIntf()));
            }

            if (this.bean.isMethodNameSet()) {
               buf.append("MethodName");
               buf.append(String.valueOf(this.bean.getMethodName()));
            }

            childValue = this.computeChildHashValue(this.bean.getMethodParams());
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
            MethodBeanImpl otherTyped = (MethodBeanImpl)other;
            this.computeDiff("Description", this.bean.getDescription(), otherTyped.getDescription(), false);
            this.computeDiff("EjbName", this.bean.getEjbName(), otherTyped.getEjbName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("MethodIntf", this.bean.getMethodIntf(), otherTyped.getMethodIntf(), false);
            this.computeDiff("MethodName", this.bean.getMethodName(), otherTyped.getMethodName(), false);
            this.computeChildDiff("MethodParams", this.bean.getMethodParams(), otherTyped.getMethodParams(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            MethodBeanImpl original = (MethodBeanImpl)event.getSourceBean();
            MethodBeanImpl proposed = (MethodBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Description")) {
                  original.setDescription(proposed.getDescription());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("EjbName")) {
                  original.setEjbName(proposed.getEjbName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("MethodIntf")) {
                  original.setMethodIntf(proposed.getMethodIntf());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MethodName")) {
                  original.setMethodName(proposed.getMethodName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("MethodParams")) {
                  if (type == 2) {
                     original.setMethodParams((MethodParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMethodParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MethodParams", (DescriptorBean)original.getMethodParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            MethodBeanImpl copy = (MethodBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Description")) && this.bean.isDescriptionSet()) {
               copy.setDescription(this.bean.getDescription());
            }

            if ((excludeProps == null || !excludeProps.contains("EjbName")) && this.bean.isEjbNameSet()) {
               copy.setEjbName(this.bean.getEjbName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodIntf")) && this.bean.isMethodIntfSet()) {
               copy.setMethodIntf(this.bean.getMethodIntf());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodName")) && this.bean.isMethodNameSet()) {
               copy.setMethodName(this.bean.getMethodName());
            }

            if ((excludeProps == null || !excludeProps.contains("MethodParams")) && this.bean.isMethodParamsSet() && !copy._isSet(4)) {
               Object o = this.bean.getMethodParams();
               copy.setMethodParams((MethodParamsBean)null);
               copy.setMethodParams(o == null ? null : (MethodParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getMethodParams(), clazz, annotation);
      }
   }
}
