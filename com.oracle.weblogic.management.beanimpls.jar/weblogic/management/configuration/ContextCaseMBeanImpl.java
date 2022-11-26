package weblogic.management.configuration;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ContextCaseMBeanImpl extends DeploymentMBeanImpl implements ContextCaseMBean, Serializable {
   private FairShareRequestClassMBean _FairShareRequestClass;
   private String _GroupName;
   private String _RequestClassName;
   private ResponseTimeRequestClassMBean _ResponseTimeRequestClass;
   private String _UserName;
   private static SchemaHelper2 _schemaHelper;

   public ContextCaseMBeanImpl() {
      this._initializeProperty(-1);
   }

   public ContextCaseMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ContextCaseMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getUserName() {
      return this._UserName;
   }

   public boolean isUserNameInherited() {
      return false;
   }

   public boolean isUserNameSet() {
      return this._isSet(12);
   }

   public void setUserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserName;
      this._UserName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public String getGroupName() {
      return this._GroupName;
   }

   public boolean isGroupNameInherited() {
      return false;
   }

   public boolean isGroupNameSet() {
      return this._isSet(13);
   }

   public void setGroupName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GroupName;
      this._GroupName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getRequestClassName() {
      return this._RequestClassName;
   }

   public boolean isRequestClassNameInherited() {
      return false;
   }

   public boolean isRequestClassNameSet() {
      return this._isSet(14);
   }

   public void setRequestClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RequestClassName;
      this._RequestClassName = param0;
      this._postSet(14, _oldVal, param0);
   }

   public ResponseTimeRequestClassMBean getResponseTimeRequestClass() {
      return this._ResponseTimeRequestClass;
   }

   public boolean isResponseTimeRequestClassInherited() {
      return false;
   }

   public boolean isResponseTimeRequestClassSet() {
      return this._isSet(15);
   }

   public void setResponseTimeRequestClass(ResponseTimeRequestClassMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getResponseTimeRequestClass() != null && param0 != this.getResponseTimeRequestClass()) {
         throw new BeanAlreadyExistsException(this.getResponseTimeRequestClass() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 15)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         ResponseTimeRequestClassMBean _oldVal = this._ResponseTimeRequestClass;
         this._ResponseTimeRequestClass = param0;
         this._postSet(15, _oldVal, param0);
      }
   }

   public ResponseTimeRequestClassMBean createResponseTimeRequestClass(String param0) {
      ResponseTimeRequestClassMBeanImpl _val = new ResponseTimeRequestClassMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setResponseTimeRequestClass(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyResponseTimeRequestClass(ResponseTimeRequestClassMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ResponseTimeRequestClass;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResponseTimeRequestClass((ResponseTimeRequestClassMBean)null);
               this._unSet(15);
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

   public FairShareRequestClassMBean getFairShareRequestClass() {
      return this._FairShareRequestClass;
   }

   public boolean isFairShareRequestClassInherited() {
      return false;
   }

   public boolean isFairShareRequestClassSet() {
      return this._isSet(16);
   }

   public void setFairShareRequestClass(FairShareRequestClassMBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFairShareRequestClass() != null && param0 != this.getFairShareRequestClass()) {
         throw new BeanAlreadyExistsException(this.getFairShareRequestClass() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 16)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         FairShareRequestClassMBean _oldVal = this._FairShareRequestClass;
         this._FairShareRequestClass = param0;
         this._postSet(16, _oldVal, param0);
      }
   }

   public FairShareRequestClassMBean createFairShareRequestClass(String param0) {
      FairShareRequestClassMBeanImpl _val = new FairShareRequestClassMBeanImpl(this, -1);

      try {
         _val.setName(param0);
         this.setFairShareRequestClass(_val);
         return _val;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void destroyFairShareRequestClass(FairShareRequestClassMBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FairShareRequestClass;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFairShareRequestClass((FairShareRequestClassMBean)null);
               this._unSet(16);
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
         idx = 16;
      }

      try {
         switch (idx) {
            case 16:
               this._FairShareRequestClass = null;
               if (initOne) {
                  break;
               }
            case 13:
               this._GroupName = null;
               if (initOne) {
                  break;
               }
            case 14:
               this._RequestClassName = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._ResponseTimeRequestClass = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._UserName = null;
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

   protected String getSchemaLocation() {
      return "http://xmlns.oracle.com/weblogic/1.0/domain.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/domain";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public String getType() {
      return "ContextCase";
   }

   public void putValue(String name, Object v) {
      if (name.equals("FairShareRequestClass")) {
         FairShareRequestClassMBean oldVal = this._FairShareRequestClass;
         this._FairShareRequestClass = (FairShareRequestClassMBean)v;
         this._postSet(16, oldVal, this._FairShareRequestClass);
      } else {
         String oldVal;
         if (name.equals("GroupName")) {
            oldVal = this._GroupName;
            this._GroupName = (String)v;
            this._postSet(13, oldVal, this._GroupName);
         } else if (name.equals("RequestClassName")) {
            oldVal = this._RequestClassName;
            this._RequestClassName = (String)v;
            this._postSet(14, oldVal, this._RequestClassName);
         } else if (name.equals("ResponseTimeRequestClass")) {
            ResponseTimeRequestClassMBean oldVal = this._ResponseTimeRequestClass;
            this._ResponseTimeRequestClass = (ResponseTimeRequestClassMBean)v;
            this._postSet(15, oldVal, this._ResponseTimeRequestClass);
         } else if (name.equals("UserName")) {
            oldVal = this._UserName;
            this._UserName = (String)v;
            this._postSet(12, oldVal, this._UserName);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("FairShareRequestClass")) {
         return this._FairShareRequestClass;
      } else if (name.equals("GroupName")) {
         return this._GroupName;
      } else if (name.equals("RequestClassName")) {
         return this._RequestClassName;
      } else if (name.equals("ResponseTimeRequestClass")) {
         return this._ResponseTimeRequestClass;
      } else {
         return name.equals("UserName") ? this._UserName : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("user-name")) {
                  return 12;
               }
               break;
            case 10:
               if (s.equals("group-name")) {
                  return 13;
               }
               break;
            case 18:
               if (s.equals("request-class-name")) {
                  return 14;
               }
               break;
            case 24:
               if (s.equals("fair-share-request-class")) {
                  return 16;
               }
               break;
            case 27:
               if (s.equals("response-time-request-class")) {
                  return 15;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 15:
               return new ResponseTimeRequestClassMBeanImpl.SchemaHelper2();
            case 16:
               return new FairShareRequestClassMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "user-name";
            case 13:
               return "group-name";
            case 14:
               return "request-class-name";
            case 15:
               return "response-time-request-class";
            case 16:
               return "fair-share-request-class";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 15:
               return true;
            case 16:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            default:
               return super.isKey(propIndex);
         }
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private ContextCaseMBeanImpl bean;

      protected Helper(ContextCaseMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "UserName";
            case 13:
               return "GroupName";
            case 14:
               return "RequestClassName";
            case 15:
               return "ResponseTimeRequestClass";
            case 16:
               return "FairShareRequestClass";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FairShareRequestClass")) {
            return 16;
         } else if (propName.equals("GroupName")) {
            return 13;
         } else if (propName.equals("RequestClassName")) {
            return 14;
         } else if (propName.equals("ResponseTimeRequestClass")) {
            return 15;
         } else {
            return propName.equals("UserName") ? 12 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getFairShareRequestClass() != null) {
            iterators.add(new ArrayIterator(new FairShareRequestClassMBean[]{this.bean.getFairShareRequestClass()}));
         }

         if (this.bean.getResponseTimeRequestClass() != null) {
            iterators.add(new ArrayIterator(new ResponseTimeRequestClassMBean[]{this.bean.getResponseTimeRequestClass()}));
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
            childValue = this.computeChildHashValue(this.bean.getFairShareRequestClass());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isGroupNameSet()) {
               buf.append("GroupName");
               buf.append(String.valueOf(this.bean.getGroupName()));
            }

            if (this.bean.isRequestClassNameSet()) {
               buf.append("RequestClassName");
               buf.append(String.valueOf(this.bean.getRequestClassName()));
            }

            childValue = this.computeChildHashValue(this.bean.getResponseTimeRequestClass());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isUserNameSet()) {
               buf.append("UserName");
               buf.append(String.valueOf(this.bean.getUserName()));
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
            ContextCaseMBeanImpl otherTyped = (ContextCaseMBeanImpl)other;
            this.computeChildDiff("FairShareRequestClass", this.bean.getFairShareRequestClass(), otherTyped.getFairShareRequestClass(), false);
            this.computeDiff("GroupName", this.bean.getGroupName(), otherTyped.getGroupName(), false);
            this.computeDiff("RequestClassName", this.bean.getRequestClassName(), otherTyped.getRequestClassName(), false);
            this.computeChildDiff("ResponseTimeRequestClass", this.bean.getResponseTimeRequestClass(), otherTyped.getResponseTimeRequestClass(), false);
            this.computeDiff("UserName", this.bean.getUserName(), otherTyped.getUserName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ContextCaseMBeanImpl original = (ContextCaseMBeanImpl)event.getSourceBean();
            ContextCaseMBeanImpl proposed = (ContextCaseMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FairShareRequestClass")) {
                  if (type == 2) {
                     original.setFairShareRequestClass((FairShareRequestClassMBean)this.createCopy((AbstractDescriptorBean)proposed.getFairShareRequestClass()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FairShareRequestClass", original.getFairShareRequestClass());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("GroupName")) {
                  original.setGroupName(proposed.getGroupName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("RequestClassName")) {
                  original.setRequestClassName(proposed.getRequestClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("ResponseTimeRequestClass")) {
                  if (type == 2) {
                     original.setResponseTimeRequestClass((ResponseTimeRequestClassMBean)this.createCopy((AbstractDescriptorBean)proposed.getResponseTimeRequestClass()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ResponseTimeRequestClass", original.getResponseTimeRequestClass());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("UserName")) {
                  original.setUserName(proposed.getUserName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
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
            ContextCaseMBeanImpl copy = (ContextCaseMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FairShareRequestClass")) && this.bean.isFairShareRequestClassSet() && !copy._isSet(16)) {
               Object o = this.bean.getFairShareRequestClass();
               copy.setFairShareRequestClass((FairShareRequestClassMBean)null);
               copy.setFairShareRequestClass(o == null ? null : (FairShareRequestClassMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("GroupName")) && this.bean.isGroupNameSet()) {
               copy.setGroupName(this.bean.getGroupName());
            }

            if ((excludeProps == null || !excludeProps.contains("RequestClassName")) && this.bean.isRequestClassNameSet()) {
               copy.setRequestClassName(this.bean.getRequestClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseTimeRequestClass")) && this.bean.isResponseTimeRequestClassSet() && !copy._isSet(15)) {
               Object o = this.bean.getResponseTimeRequestClass();
               copy.setResponseTimeRequestClass((ResponseTimeRequestClassMBean)null);
               copy.setResponseTimeRequestClass(o == null ? null : (ResponseTimeRequestClassMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("UserName")) && this.bean.isUserNameSet()) {
               copy.setUserName(this.bean.getUserName());
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
         this.inferSubTree(this.bean.getFairShareRequestClass(), clazz, annotation);
         this.inferSubTree(this.bean.getResponseTimeRequestClass(), clazz, annotation);
      }
   }
}
