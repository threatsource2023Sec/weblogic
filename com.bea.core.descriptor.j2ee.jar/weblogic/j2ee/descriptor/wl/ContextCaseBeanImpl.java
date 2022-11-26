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

public class ContextCaseBeanImpl extends AbstractDescriptorBean implements ContextCaseBean, Serializable {
   private FairShareRequestClassBean _FairShareRequestClass;
   private String _GroupName;
   private String _Id;
   private String _RequestClassName;
   private ResponseTimeRequestClassBean _ResponseTimeRequestClass;
   private String _UserName;
   private static SchemaHelper2 _schemaHelper;

   public ContextCaseBeanImpl() {
      this._initializeProperty(-1);
   }

   public ContextCaseBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ContextCaseBeanImpl(DescriptorBean param0, int param1, boolean param2) {
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
      return this._isSet(0);
   }

   public void setUserName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._UserName;
      this._UserName = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getGroupName() {
      return this._GroupName;
   }

   public boolean isGroupNameInherited() {
      return false;
   }

   public boolean isGroupNameSet() {
      return this._isSet(1);
   }

   public void setGroupName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._GroupName;
      this._GroupName = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getRequestClassName() {
      return this._RequestClassName;
   }

   public boolean isRequestClassNameInherited() {
      return false;
   }

   public boolean isRequestClassNameSet() {
      return this._isSet(2);
   }

   public void setRequestClassName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RequestClassName;
      this._RequestClassName = param0;
      this._postSet(2, _oldVal, param0);
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

   public ResponseTimeRequestClassBean getResponseTimeRequestClass() {
      return this._ResponseTimeRequestClass;
   }

   public boolean isResponseTimeRequestClassInherited() {
      return false;
   }

   public boolean isResponseTimeRequestClassSet() {
      return this._isSet(4);
   }

   public void setResponseTimeRequestClass(ResponseTimeRequestClassBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getResponseTimeRequestClass() != null && param0 != this.getResponseTimeRequestClass()) {
         throw new BeanAlreadyExistsException(this.getResponseTimeRequestClass() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         ResponseTimeRequestClassBean _oldVal = this._ResponseTimeRequestClass;
         this._ResponseTimeRequestClass = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public ResponseTimeRequestClassBean createResponseTimeRequestClass() {
      ResponseTimeRequestClassBeanImpl _val = new ResponseTimeRequestClassBeanImpl(this, -1);

      try {
         this.setResponseTimeRequestClass(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyResponseTimeRequestClass() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ResponseTimeRequestClass;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setResponseTimeRequestClass((ResponseTimeRequestClassBean)null);
               this._unSet(4);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public FairShareRequestClassBean getFairShareRequestClass() {
      return this._FairShareRequestClass;
   }

   public boolean isFairShareRequestClassInherited() {
      return false;
   }

   public boolean isFairShareRequestClassSet() {
      return this._isSet(5);
   }

   public void setFairShareRequestClass(FairShareRequestClassBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getFairShareRequestClass() != null && param0 != this.getFairShareRequestClass()) {
         throw new BeanAlreadyExistsException(this.getFairShareRequestClass() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 5)) {
               this._getReferenceManager().registerBean(_child, true);
               this._postCreate(_child);
            }
         }

         FairShareRequestClassBean _oldVal = this._FairShareRequestClass;
         this._FairShareRequestClass = param0;
         this._postSet(5, _oldVal, param0);
      }
   }

   public FairShareRequestClassBean createFairShareRequestClass() {
      FairShareRequestClassBeanImpl _val = new FairShareRequestClassBeanImpl(this, -1);

      try {
         this.setFairShareRequestClass(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyFairShareRequestClass() {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._FairShareRequestClass;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setFairShareRequestClass((FairShareRequestClassBean)null);
               this._unSet(5);
            }
         }
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public Object _getKey() {
      return this.getRequestClassName();
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
         case 18:
            if (s.equals("request-class-name")) {
               return info.compareXpaths(this._getPropertyXpath("request-class-name"));
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
         idx = 5;
      }

      try {
         switch (idx) {
            case 5:
               this._FairShareRequestClass = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._GroupName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._RequestClassName = null;
               if (initOne) {
                  break;
               }
            case 4:
               this._ResponseTimeRequestClass = null;
               if (initOne) {
                  break;
               }
            case 0:
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
            case 9:
               if (s.equals("user-name")) {
                  return 0;
               }
               break;
            case 10:
               if (s.equals("group-name")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("request-class-name")) {
                  return 2;
               }
               break;
            case 24:
               if (s.equals("fair-share-request-class")) {
                  return 5;
               }
               break;
            case 27:
               if (s.equals("response-time-request-class")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 4:
               return new ResponseTimeRequestClassBeanImpl.SchemaHelper2();
            case 5:
               return new FairShareRequestClassBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "user-name";
            case 1:
               return "group-name";
            case 2:
               return "request-class-name";
            case 3:
               return "id";
            case 4:
               return "response-time-request-class";
            case 5:
               return "fair-share-request-class";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            case 5:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

      public boolean hasKey() {
         return true;
      }

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("request-class-name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ContextCaseBeanImpl bean;

      protected Helper(ContextCaseBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "UserName";
            case 1:
               return "GroupName";
            case 2:
               return "RequestClassName";
            case 3:
               return "Id";
            case 4:
               return "ResponseTimeRequestClass";
            case 5:
               return "FairShareRequestClass";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FairShareRequestClass")) {
            return 5;
         } else if (propName.equals("GroupName")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 3;
         } else if (propName.equals("RequestClassName")) {
            return 2;
         } else if (propName.equals("ResponseTimeRequestClass")) {
            return 4;
         } else {
            return propName.equals("UserName") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getFairShareRequestClass() != null) {
            iterators.add(new ArrayIterator(new FairShareRequestClassBean[]{this.bean.getFairShareRequestClass()}));
         }

         if (this.bean.getResponseTimeRequestClass() != null) {
            iterators.add(new ArrayIterator(new ResponseTimeRequestClassBean[]{this.bean.getResponseTimeRequestClass()}));
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

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
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
            ContextCaseBeanImpl otherTyped = (ContextCaseBeanImpl)other;
            this.computeChildDiff("FairShareRequestClass", this.bean.getFairShareRequestClass(), otherTyped.getFairShareRequestClass(), false);
            this.computeDiff("GroupName", this.bean.getGroupName(), otherTyped.getGroupName(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("RequestClassName", this.bean.getRequestClassName(), otherTyped.getRequestClassName(), false);
            this.computeChildDiff("ResponseTimeRequestClass", this.bean.getResponseTimeRequestClass(), otherTyped.getResponseTimeRequestClass(), false);
            this.computeDiff("UserName", this.bean.getUserName(), otherTyped.getUserName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ContextCaseBeanImpl original = (ContextCaseBeanImpl)event.getSourceBean();
            ContextCaseBeanImpl proposed = (ContextCaseBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FairShareRequestClass")) {
                  if (type == 2) {
                     original.setFairShareRequestClass((FairShareRequestClassBean)this.createCopy((AbstractDescriptorBean)proposed.getFairShareRequestClass()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("FairShareRequestClass", (DescriptorBean)original.getFairShareRequestClass());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("GroupName")) {
                  original.setGroupName(proposed.getGroupName());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RequestClassName")) {
                  original.setRequestClassName(proposed.getRequestClassName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ResponseTimeRequestClass")) {
                  if (type == 2) {
                     original.setResponseTimeRequestClass((ResponseTimeRequestClassBean)this.createCopy((AbstractDescriptorBean)proposed.getResponseTimeRequestClass()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ResponseTimeRequestClass", (DescriptorBean)original.getResponseTimeRequestClass());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("UserName")) {
                  original.setUserName(proposed.getUserName());
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
            ContextCaseBeanImpl copy = (ContextCaseBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FairShareRequestClass")) && this.bean.isFairShareRequestClassSet() && !copy._isSet(5)) {
               Object o = this.bean.getFairShareRequestClass();
               copy.setFairShareRequestClass((FairShareRequestClassBean)null);
               copy.setFairShareRequestClass(o == null ? null : (FairShareRequestClassBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("GroupName")) && this.bean.isGroupNameSet()) {
               copy.setGroupName(this.bean.getGroupName());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("RequestClassName")) && this.bean.isRequestClassNameSet()) {
               copy.setRequestClassName(this.bean.getRequestClassName());
            }

            if ((excludeProps == null || !excludeProps.contains("ResponseTimeRequestClass")) && this.bean.isResponseTimeRequestClassSet() && !copy._isSet(4)) {
               Object o = this.bean.getResponseTimeRequestClass();
               copy.setResponseTimeRequestClass((ResponseTimeRequestClassBean)null);
               copy.setResponseTimeRequestClass(o == null ? null : (ResponseTimeRequestClassBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
