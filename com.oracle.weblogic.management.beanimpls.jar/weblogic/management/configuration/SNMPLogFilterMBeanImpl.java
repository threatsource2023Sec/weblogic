package weblogic.management.configuration;

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
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class SNMPLogFilterMBeanImpl extends SNMPTrapSourceMBeanImpl implements SNMPLogFilterMBean, Serializable {
   private String[] _MessageIds;
   private String _MessageSubstring;
   private String _SeverityLevel;
   private String[] _SubsystemNames;
   private String[] _UserIds;
   private static SchemaHelper2 _schemaHelper;

   public SNMPLogFilterMBeanImpl() {
      this._initializeProperty(-1);
   }

   public SNMPLogFilterMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SNMPLogFilterMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getSeverityLevel() {
      return this._SeverityLevel;
   }

   public boolean isSeverityLevelInherited() {
      return false;
   }

   public boolean isSeverityLevelSet() {
      return this._isSet(11);
   }

   public void setSeverityLevel(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SeverityLevel;
      this._SeverityLevel = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String[] getSubsystemNames() {
      return this._SubsystemNames;
   }

   public boolean isSubsystemNamesInherited() {
      return false;
   }

   public boolean isSubsystemNamesSet() {
      return this._isSet(12);
   }

   public void setSubsystemNames(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._SubsystemNames;
      this._SubsystemNames = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean addSubsystemName(String param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(12)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getSubsystemNames(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setSubsystemNames(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof InvalidAttributeValueException) {
            throw (InvalidAttributeValueException)var4;
         } else if (var4 instanceof ConfigurationException) {
            throw (ConfigurationException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeSubsystemName(String param0) throws InvalidAttributeValueException, ConfigurationException {
      String[] _old = this.getSubsystemNames();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setSubsystemNames(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof ConfigurationException) {
               throw (ConfigurationException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public String[] getUserIds() {
      return this._UserIds;
   }

   public boolean isUserIdsInherited() {
      return false;
   }

   public boolean isUserIdsSet() {
      return this._isSet(13);
   }

   public void setUserIds(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._UserIds;
      this._UserIds = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean addUserId(String param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(13)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getUserIds(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setUserIds(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof InvalidAttributeValueException) {
            throw (InvalidAttributeValueException)var4;
         } else if (var4 instanceof ConfigurationException) {
            throw (ConfigurationException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeUserId(String param0) throws InvalidAttributeValueException, ConfigurationException {
      String[] _old = this.getUserIds();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setUserIds(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof ConfigurationException) {
               throw (ConfigurationException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public String[] getMessageIds() {
      return this._MessageIds;
   }

   public boolean isMessageIdsInherited() {
      return false;
   }

   public boolean isMessageIdsSet() {
      return this._isSet(14);
   }

   public void setMessageIds(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._MessageIds;
      this._MessageIds = param0;
      this._postSet(14, _oldVal, param0);
   }

   public boolean addMessageId(String param0) throws InvalidAttributeValueException, ConfigurationException {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(14)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getMessageIds(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setMessageIds(_new);
         return true;
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else if (var4 instanceof InvalidAttributeValueException) {
            throw (InvalidAttributeValueException)var4;
         } else if (var4 instanceof ConfigurationException) {
            throw (ConfigurationException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public boolean removeMessageId(String param0) throws InvalidAttributeValueException, ConfigurationException {
      String[] _old = this.getMessageIds();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setMessageIds(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var5;
            } else if (var5 instanceof ConfigurationException) {
               throw (ConfigurationException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public String getMessageSubstring() {
      return this._MessageSubstring;
   }

   public boolean isMessageSubstringInherited() {
      return false;
   }

   public boolean isMessageSubstringSet() {
      return this._isSet(15);
   }

   public void setMessageSubstring(String param0) throws InvalidAttributeValueException, ConfigurationException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._MessageSubstring;
      this._MessageSubstring = param0;
      this._postSet(15, _oldVal, param0);
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._MessageIds = new String[0];
               if (initOne) {
                  break;
               }
            case 15:
               this._MessageSubstring = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._SeverityLevel = "Notice";
               if (initOne) {
                  break;
               }
            case 12:
               this._SubsystemNames = new String[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._UserIds = new String[0];
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
      return "SNMPLogFilter";
   }

   public void putValue(String name, Object v) {
      String[] oldVal;
      if (name.equals("MessageIds")) {
         oldVal = this._MessageIds;
         this._MessageIds = (String[])((String[])v);
         this._postSet(14, oldVal, this._MessageIds);
      } else {
         String oldVal;
         if (name.equals("MessageSubstring")) {
            oldVal = this._MessageSubstring;
            this._MessageSubstring = (String)v;
            this._postSet(15, oldVal, this._MessageSubstring);
         } else if (name.equals("SeverityLevel")) {
            oldVal = this._SeverityLevel;
            this._SeverityLevel = (String)v;
            this._postSet(11, oldVal, this._SeverityLevel);
         } else if (name.equals("SubsystemNames")) {
            oldVal = this._SubsystemNames;
            this._SubsystemNames = (String[])((String[])v);
            this._postSet(12, oldVal, this._SubsystemNames);
         } else if (name.equals("UserIds")) {
            oldVal = this._UserIds;
            this._UserIds = (String[])((String[])v);
            this._postSet(13, oldVal, this._UserIds);
         } else {
            super.putValue(name, v);
         }
      }
   }

   public Object getValue(String name) {
      if (name.equals("MessageIds")) {
         return this._MessageIds;
      } else if (name.equals("MessageSubstring")) {
         return this._MessageSubstring;
      } else if (name.equals("SeverityLevel")) {
         return this._SeverityLevel;
      } else if (name.equals("SubsystemNames")) {
         return this._SubsystemNames;
      } else {
         return name.equals("UserIds") ? this._UserIds : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends SNMPTrapSourceMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 7:
               if (s.equals("user-id")) {
                  return 13;
               }
               break;
            case 10:
               if (s.equals("message-id")) {
                  return 14;
               }
               break;
            case 14:
               if (s.equals("severity-level")) {
                  return 11;
               }

               if (s.equals("subsystem-name")) {
                  return 12;
               }
               break;
            case 17:
               if (s.equals("message-substring")) {
                  return 15;
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
            case 11:
               return "severity-level";
            case 12:
               return "subsystem-name";
            case 13:
               return "user-id";
            case 14:
               return "message-id";
            case 15:
               return "message-substring";
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
            case 11:
            default:
               return super.isArray(propIndex);
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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

   protected static class Helper extends SNMPTrapSourceMBeanImpl.Helper {
      private SNMPLogFilterMBeanImpl bean;

      protected Helper(SNMPLogFilterMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 11:
               return "SeverityLevel";
            case 12:
               return "SubsystemNames";
            case 13:
               return "UserIds";
            case 14:
               return "MessageIds";
            case 15:
               return "MessageSubstring";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("MessageIds")) {
            return 14;
         } else if (propName.equals("MessageSubstring")) {
            return 15;
         } else if (propName.equals("SeverityLevel")) {
            return 11;
         } else if (propName.equals("SubsystemNames")) {
            return 12;
         } else {
            return propName.equals("UserIds") ? 13 : super.getPropertyIndex(propName);
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
            if (this.bean.isMessageIdsSet()) {
               buf.append("MessageIds");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getMessageIds())));
            }

            if (this.bean.isMessageSubstringSet()) {
               buf.append("MessageSubstring");
               buf.append(String.valueOf(this.bean.getMessageSubstring()));
            }

            if (this.bean.isSeverityLevelSet()) {
               buf.append("SeverityLevel");
               buf.append(String.valueOf(this.bean.getSeverityLevel()));
            }

            if (this.bean.isSubsystemNamesSet()) {
               buf.append("SubsystemNames");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getSubsystemNames())));
            }

            if (this.bean.isUserIdsSet()) {
               buf.append("UserIds");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getUserIds())));
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
            SNMPLogFilterMBeanImpl otherTyped = (SNMPLogFilterMBeanImpl)other;
            this.computeDiff("MessageIds", this.bean.getMessageIds(), otherTyped.getMessageIds(), true);
            this.computeDiff("MessageSubstring", this.bean.getMessageSubstring(), otherTyped.getMessageSubstring(), true);
            this.computeDiff("SeverityLevel", this.bean.getSeverityLevel(), otherTyped.getSeverityLevel(), true);
            this.computeDiff("SubsystemNames", this.bean.getSubsystemNames(), otherTyped.getSubsystemNames(), true);
            this.computeDiff("UserIds", this.bean.getUserIds(), otherTyped.getUserIds(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SNMPLogFilterMBeanImpl original = (SNMPLogFilterMBeanImpl)event.getSourceBean();
            SNMPLogFilterMBeanImpl proposed = (SNMPLogFilterMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("MessageIds")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addMessageId((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageId((String)update.getRemovedObject());
                  }

                  if (original.getMessageIds() == null || original.getMessageIds().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 14);
                  }
               } else if (prop.equals("MessageSubstring")) {
                  original.setMessageSubstring(proposed.getMessageSubstring());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("SeverityLevel")) {
                  original.setSeverityLevel(proposed.getSeverityLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("SubsystemNames")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addSubsystemName((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeSubsystemName((String)update.getRemovedObject());
                  }

                  if (original.getSubsystemNames() == null || original.getSubsystemNames().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 12);
                  }
               } else if (prop.equals("UserIds")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addUserId((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeUserId((String)update.getRemovedObject());
                  }

                  if (original.getUserIds() == null || original.getUserIds().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
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
            SNMPLogFilterMBeanImpl copy = (SNMPLogFilterMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            String[] o;
            if ((excludeProps == null || !excludeProps.contains("MessageIds")) && this.bean.isMessageIdsSet()) {
               o = this.bean.getMessageIds();
               copy.setMessageIds(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("MessageSubstring")) && this.bean.isMessageSubstringSet()) {
               copy.setMessageSubstring(this.bean.getMessageSubstring());
            }

            if ((excludeProps == null || !excludeProps.contains("SeverityLevel")) && this.bean.isSeverityLevelSet()) {
               copy.setSeverityLevel(this.bean.getSeverityLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("SubsystemNames")) && this.bean.isSubsystemNamesSet()) {
               o = this.bean.getSubsystemNames();
               copy.setSubsystemNames(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
            }

            if ((excludeProps == null || !excludeProps.contains("UserIds")) && this.bean.isUserIdsSet()) {
               o = this.bean.getUserIds();
               copy.setUserIds(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
