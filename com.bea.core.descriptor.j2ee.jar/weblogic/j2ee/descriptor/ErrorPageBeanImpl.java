package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class ErrorPageBeanImpl extends AbstractDescriptorBean implements ErrorPageBean, Serializable {
   private int _ErrorCode;
   private String _ExceptionType;
   private String _Id;
   private String _Location;
   private static SchemaHelper2 _schemaHelper;

   public ErrorPageBeanImpl() {
      this._initializeProperty(-1);
   }

   public ErrorPageBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ErrorPageBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getErrorCode() {
      return this._ErrorCode;
   }

   public boolean isErrorCodeInherited() {
      return false;
   }

   public boolean isErrorCodeSet() {
      return this._isSet(0);
   }

   public void setErrorCode(int param0) {
      int _oldVal = this._ErrorCode;
      this._ErrorCode = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getExceptionType() {
      return this._ExceptionType;
   }

   public boolean isExceptionTypeInherited() {
      return false;
   }

   public boolean isExceptionTypeSet() {
      return this._isSet(1);
   }

   public void setExceptionType(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ExceptionType;
      this._ExceptionType = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getLocation() {
      return this._Location;
   }

   public boolean isLocationInherited() {
      return false;
   }

   public boolean isLocationSet() {
      return this._isSet(2);
   }

   public void setLocation(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Location;
      this._Location = param0;
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

   public Object _getKey() {
      Object keyChoice = null;
      if (keyChoice == null) {
         keyChoice = !this._isSet(0) ? null : new Integer(this.getErrorCode());
      }

      if (keyChoice == null) {
         keyChoice = this.getExceptionType();
      }

      return keyChoice;
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
            if (s.equals("error-code")) {
               return info.compareXpaths(this._getPropertyXpath("error-code"));
            }
            break;
         case 14:
            if (s.equals("exception-type")) {
               return info.compareXpaths(this._getPropertyXpath("exception-type"));
            }
            break;
         default:
            return super._isPropertyAKey(info);
      }

      return super._isPropertyAKey(info);
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
               this._ErrorCode = 0;
               if (initOne) {
                  break;
               }
            case 1:
               this._ExceptionType = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._Location = null;
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
            case 8:
               if (s.equals("location")) {
                  return 2;
               }
               break;
            case 10:
               if (s.equals("error-code")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("exception-type")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "error-code";
            case 1:
               return "exception-type";
            case 2:
               return "location";
            case 3:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isKeyChoice(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isKeyChoice(propIndex);
         }
      }

      public boolean hasKey() {
         return true;
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ErrorPageBeanImpl bean;

      protected Helper(ErrorPageBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ErrorCode";
            case 1:
               return "ExceptionType";
            case 2:
               return "Location";
            case 3:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ErrorCode")) {
            return 0;
         } else if (propName.equals("ExceptionType")) {
            return 1;
         } else if (propName.equals("Id")) {
            return 3;
         } else {
            return propName.equals("Location") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
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
            if (this.bean.isErrorCodeSet()) {
               buf.append("ErrorCode");
               buf.append(String.valueOf(this.bean.getErrorCode()));
            }

            if (this.bean.isExceptionTypeSet()) {
               buf.append("ExceptionType");
               buf.append(String.valueOf(this.bean.getExceptionType()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isLocationSet()) {
               buf.append("Location");
               buf.append(String.valueOf(this.bean.getLocation()));
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
            ErrorPageBeanImpl otherTyped = (ErrorPageBeanImpl)other;
            this.computeDiff("ErrorCode", this.bean.getErrorCode(), otherTyped.getErrorCode(), false);
            this.computeDiff("ExceptionType", this.bean.getExceptionType(), otherTyped.getExceptionType(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("Location", this.bean.getLocation(), otherTyped.getLocation(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ErrorPageBeanImpl original = (ErrorPageBeanImpl)event.getSourceBean();
            ErrorPageBeanImpl proposed = (ErrorPageBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ErrorCode")) {
                  original.setErrorCode(proposed.getErrorCode());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ExceptionType")) {
                  original.setExceptionType(proposed.getExceptionType());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Location")) {
                  original.setLocation(proposed.getLocation());
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
            ErrorPageBeanImpl copy = (ErrorPageBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ErrorCode")) && this.bean.isErrorCodeSet()) {
               copy.setErrorCode(this.bean.getErrorCode());
            }

            if ((excludeProps == null || !excludeProps.contains("ExceptionType")) && this.bean.isExceptionTypeSet()) {
               copy.setExceptionType(this.bean.getExceptionType());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("Location")) && this.bean.isLocationSet()) {
               copy.setLocation(this.bean.getLocation());
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
      }
   }
}
