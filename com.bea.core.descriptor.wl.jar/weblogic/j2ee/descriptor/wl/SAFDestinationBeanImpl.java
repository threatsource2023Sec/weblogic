package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorValidateException;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SAFDestinationBeanImpl extends NamedEntityBeanImpl implements SAFDestinationBean, Serializable {
   private String _LocalJNDIName;
   private MessageLoggingParamsBean _MessageLoggingParams;
   private String _NonPersistentQos;
   private String _PersistentQos;
   private String _RemoteJNDIName;
   private SAFErrorHandlingBean _SAFErrorHandling;
   private long _TimeToLiveDefault;
   private String _UnitOfOrderRouting;
   private boolean _UseSAFTimeToLiveDefault;
   private static SchemaHelper2 _schemaHelper;

   public SAFDestinationBeanImpl() {
      this._initializeProperty(-1);
   }

   public SAFDestinationBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SAFDestinationBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getRemoteJNDIName() {
      return this._RemoteJNDIName;
   }

   public boolean isRemoteJNDINameInherited() {
      return false;
   }

   public boolean isRemoteJNDINameSet() {
      return this._isSet(3);
   }

   public void setRemoteJNDIName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RemoteJNDIName;
      this._RemoteJNDIName = param0;
      this._postSet(3, _oldVal, param0);
   }

   public String getLocalJNDIName() {
      return this._LocalJNDIName;
   }

   public boolean isLocalJNDINameInherited() {
      return false;
   }

   public boolean isLocalJNDINameSet() {
      return this._isSet(4);
   }

   public void setLocalJNDIName(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._LocalJNDIName;
      this._LocalJNDIName = param0;
      this._postSet(4, _oldVal, param0);
   }

   public String getPersistentQos() {
      return this._PersistentQos;
   }

   public boolean isPersistentQosInherited() {
      return false;
   }

   public boolean isPersistentQosSet() {
      return this._isSet(5);
   }

   public void setPersistentQos(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"At-Most-Once", "At-Least-Once", "Exactly-Once"};
      param0 = LegalChecks.checkInEnum("PersistentQos", param0, _set);
      LegalChecks.checkNonNull("PersistentQos", param0);
      String _oldVal = this._PersistentQos;
      this._PersistentQos = param0;
      this._postSet(5, _oldVal, param0);
   }

   public String getNonPersistentQos() {
      return this._NonPersistentQos;
   }

   public boolean isNonPersistentQosInherited() {
      return false;
   }

   public boolean isNonPersistentQosSet() {
      return this._isSet(6);
   }

   public void setNonPersistentQos(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"At-Most-Once", "At-Least-Once", "Exactly-Once"};
      param0 = LegalChecks.checkInEnum("NonPersistentQos", param0, _set);
      LegalChecks.checkNonNull("NonPersistentQos", param0);
      String _oldVal = this._NonPersistentQos;
      this._NonPersistentQos = param0;
      this._postSet(6, _oldVal, param0);
   }

   public SAFErrorHandlingBean getSAFErrorHandling() {
      if (!this._isSet(7)) {
         try {
            return ((SAFImportedDestinationsBean)this.getParentBean()).getSAFErrorHandling();
         } catch (NullPointerException var2) {
         }
      }

      return this._SAFErrorHandling;
   }

   public String getSAFErrorHandlingAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getSAFErrorHandling();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isSAFErrorHandlingInherited() {
      return false;
   }

   public boolean isSAFErrorHandlingSet() {
      return this._isSet(7);
   }

   public void setSAFErrorHandlingAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, SAFErrorHandlingBean.class, new ReferenceManager.Resolver(this, 7) {
            public void resolveReference(Object value) {
               try {
                  SAFDestinationBeanImpl.this.setSAFErrorHandling((SAFErrorHandlingBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         SAFErrorHandlingBean _oldVal = this._SAFErrorHandling;
         this._initializeProperty(7);
         this._postSet(7, _oldVal, this._SAFErrorHandling);
      }

   }

   public void setSAFErrorHandling(SAFErrorHandlingBean param0) throws IllegalArgumentException {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 7, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return SAFDestinationBeanImpl.this.getSAFErrorHandling();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      SAFErrorHandlingBean _oldVal = this._SAFErrorHandling;
      this._SAFErrorHandling = param0;
      this._postSet(7, _oldVal, param0);
   }

   public long getTimeToLiveDefault() {
      if (!this._isSet(8)) {
         try {
            return ((SAFImportedDestinationsBean)this.getParentBean()).getTimeToLiveDefault();
         } catch (NullPointerException var2) {
         }
      }

      return this._TimeToLiveDefault;
   }

   public boolean isTimeToLiveDefaultInherited() {
      return false;
   }

   public boolean isTimeToLiveDefaultSet() {
      return this._isSet(8);
   }

   public void setTimeToLiveDefault(long param0) throws IllegalArgumentException {
      LegalChecks.checkMin("TimeToLiveDefault", param0, -1L);
      long _oldVal = this._TimeToLiveDefault;
      this._TimeToLiveDefault = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isUseSAFTimeToLiveDefault() {
      if (!this._isSet(9)) {
         try {
            return ((SAFImportedDestinationsBean)this.getParentBean()).isUseSAFTimeToLiveDefault();
         } catch (NullPointerException var2) {
         }
      }

      return this._UseSAFTimeToLiveDefault;
   }

   public boolean isUseSAFTimeToLiveDefaultInherited() {
      return false;
   }

   public boolean isUseSAFTimeToLiveDefaultSet() {
      return this._isSet(9);
   }

   public void setUseSAFTimeToLiveDefault(boolean param0) throws IllegalArgumentException {
      boolean _oldVal = this._UseSAFTimeToLiveDefault;
      this._UseSAFTimeToLiveDefault = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getUnitOfOrderRouting() {
      if (!this._isSet(10)) {
         try {
            return ((SAFImportedDestinationsBean)this.getParentBean()).getUnitOfOrderRouting();
         } catch (NullPointerException var2) {
         }
      }

      return this._UnitOfOrderRouting;
   }

   public boolean isUnitOfOrderRoutingInherited() {
      return false;
   }

   public boolean isUnitOfOrderRoutingSet() {
      return this._isSet(10);
   }

   public void setUnitOfOrderRouting(String param0) throws IllegalArgumentException {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"Hash", "PathService"};
      param0 = LegalChecks.checkInEnum("UnitOfOrderRouting", param0, _set);
      LegalChecks.checkNonNull("UnitOfOrderRouting", param0);
      String _oldVal = this._UnitOfOrderRouting;
      this._UnitOfOrderRouting = param0;
      this._postSet(10, _oldVal, param0);
   }

   public MessageLoggingParamsBean getMessageLoggingParams() {
      return this._MessageLoggingParams;
   }

   public boolean isMessageLoggingParamsInherited() {
      return false;
   }

   public boolean isMessageLoggingParamsSet() {
      return this._isSet(11) || this._isAnythingSet((AbstractDescriptorBean)this.getMessageLoggingParams());
   }

   public void setMessageLoggingParams(MessageLoggingParamsBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 11)) {
         this._postCreate(_child);
      }

      MessageLoggingParamsBean _oldVal = this._MessageLoggingParams;
      this._MessageLoggingParams = param0;
      this._postSet(11, _oldVal, param0);
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
      return super._isAnythingSet() || this.isMessageLoggingParamsSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._LocalJNDIName = null;
               if (initOne) {
                  break;
               }
            case 11:
               this._MessageLoggingParams = new MessageLoggingParamsBeanImpl(this, 11);
               this._postCreate((AbstractDescriptorBean)this._MessageLoggingParams);
               if (initOne) {
                  break;
               }
            case 6:
               this._NonPersistentQos = "At-Least-Once";
               if (initOne) {
                  break;
               }
            case 5:
               this._PersistentQos = "Exactly-Once";
               if (initOne) {
                  break;
               }
            case 3:
               this._RemoteJNDIName = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._SAFErrorHandling = null;
               if (initOne) {
                  break;
               }
            case 8:
               this._TimeToLiveDefault = 0L;
               if (initOne) {
                  break;
               }
            case 10:
               this._UnitOfOrderRouting = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._UseSAFTimeToLiveDefault = false;
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

   public static void validateGeneration() {
      try {
         LegalChecks.checkNonNull("NonPersistentQos", "At-Least-Once");
      } catch (IllegalArgumentException var2) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property NonPersistentQos in SAFDestinationBean" + var2.getMessage());
      }

      try {
         LegalChecks.checkNonNull("PersistentQos", "Exactly-Once");
      } catch (IllegalArgumentException var1) {
         throw new DescriptorValidateException("The default value for the property  is null. Properties annotated with false value on @legalZeroLength or @legalNull  should either have @required/@derivedDefault annotations or have a non-null value on @default annotation. Refer annotation legalNull on property PersistentQos in SAFDestinationBean" + var1.getMessage());
      }
   }

   public static class SchemaHelper2 extends NamedEntityBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 14:
               if (s.equals("persistent-qos")) {
                  return 5;
               }
               break;
            case 15:
               if (s.equals("local-jndi-name")) {
                  return 4;
               }
               break;
            case 16:
               if (s.equals("remote-jndi-name")) {
                  return 3;
               }
            case 17:
            case 19:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            default:
               break;
            case 18:
               if (s.equals("non-persistent-qos")) {
                  return 6;
               }

               if (s.equals("saf-error-handling")) {
                  return 7;
               }
               break;
            case 20:
               if (s.equals("time-to-live-default")) {
                  return 8;
               }
               break;
            case 21:
               if (s.equals("unit-of-order-routing")) {
                  return 10;
               }
               break;
            case 22:
               if (s.equals("message-logging-params")) {
                  return 11;
               }
               break;
            case 28:
               if (s.equals("use-saf-time-to-live-default")) {
                  return 9;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 11:
               return new MessageLoggingParamsBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "remote-jndi-name";
            case 4:
               return "local-jndi-name";
            case 5:
               return "persistent-qos";
            case 6:
               return "non-persistent-qos";
            case 7:
               return "saf-error-handling";
            case 8:
               return "time-to-live-default";
            case 9:
               return "use-saf-time-to-live-default";
            case 10:
               return "unit-of-order-routing";
            case 11:
               return "message-logging-params";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isAttribute(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isAttribute(propIndex);
         }
      }

      public String getAttributeName(int propIndex) {
         return this.getElementName(propIndex);
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 11:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 1:
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

   protected static class Helper extends NamedEntityBeanImpl.Helper {
      private SAFDestinationBeanImpl bean;

      protected Helper(SAFDestinationBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 3:
               return "RemoteJNDIName";
            case 4:
               return "LocalJNDIName";
            case 5:
               return "PersistentQos";
            case 6:
               return "NonPersistentQos";
            case 7:
               return "SAFErrorHandling";
            case 8:
               return "TimeToLiveDefault";
            case 9:
               return "UseSAFTimeToLiveDefault";
            case 10:
               return "UnitOfOrderRouting";
            case 11:
               return "MessageLoggingParams";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("LocalJNDIName")) {
            return 4;
         } else if (propName.equals("MessageLoggingParams")) {
            return 11;
         } else if (propName.equals("NonPersistentQos")) {
            return 6;
         } else if (propName.equals("PersistentQos")) {
            return 5;
         } else if (propName.equals("RemoteJNDIName")) {
            return 3;
         } else if (propName.equals("SAFErrorHandling")) {
            return 7;
         } else if (propName.equals("TimeToLiveDefault")) {
            return 8;
         } else if (propName.equals("UnitOfOrderRouting")) {
            return 10;
         } else {
            return propName.equals("UseSAFTimeToLiveDefault") ? 9 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getMessageLoggingParams() != null) {
            iterators.add(new ArrayIterator(new MessageLoggingParamsBean[]{this.bean.getMessageLoggingParams()}));
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
            if (this.bean.isLocalJNDINameSet()) {
               buf.append("LocalJNDIName");
               buf.append(String.valueOf(this.bean.getLocalJNDIName()));
            }

            childValue = this.computeChildHashValue(this.bean.getMessageLoggingParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isNonPersistentQosSet()) {
               buf.append("NonPersistentQos");
               buf.append(String.valueOf(this.bean.getNonPersistentQos()));
            }

            if (this.bean.isPersistentQosSet()) {
               buf.append("PersistentQos");
               buf.append(String.valueOf(this.bean.getPersistentQos()));
            }

            if (this.bean.isRemoteJNDINameSet()) {
               buf.append("RemoteJNDIName");
               buf.append(String.valueOf(this.bean.getRemoteJNDIName()));
            }

            if (this.bean.isSAFErrorHandlingSet()) {
               buf.append("SAFErrorHandling");
               buf.append(String.valueOf(this.bean.getSAFErrorHandling()));
            }

            if (this.bean.isTimeToLiveDefaultSet()) {
               buf.append("TimeToLiveDefault");
               buf.append(String.valueOf(this.bean.getTimeToLiveDefault()));
            }

            if (this.bean.isUnitOfOrderRoutingSet()) {
               buf.append("UnitOfOrderRouting");
               buf.append(String.valueOf(this.bean.getUnitOfOrderRouting()));
            }

            if (this.bean.isUseSAFTimeToLiveDefaultSet()) {
               buf.append("UseSAFTimeToLiveDefault");
               buf.append(String.valueOf(this.bean.isUseSAFTimeToLiveDefault()));
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
            SAFDestinationBeanImpl otherTyped = (SAFDestinationBeanImpl)other;
            this.computeDiff("LocalJNDIName", this.bean.getLocalJNDIName(), otherTyped.getLocalJNDIName(), true);
            this.computeSubDiff("MessageLoggingParams", this.bean.getMessageLoggingParams(), otherTyped.getMessageLoggingParams());
            this.computeDiff("NonPersistentQos", this.bean.getNonPersistentQos(), otherTyped.getNonPersistentQos(), true);
            this.computeDiff("PersistentQos", this.bean.getPersistentQos(), otherTyped.getPersistentQos(), true);
            this.computeDiff("RemoteJNDIName", this.bean.getRemoteJNDIName(), otherTyped.getRemoteJNDIName(), true);
            this.computeDiff("SAFErrorHandling", this.bean.getSAFErrorHandling(), otherTyped.getSAFErrorHandling(), false);
            this.computeDiff("TimeToLiveDefault", this.bean.getTimeToLiveDefault(), otherTyped.getTimeToLiveDefault(), true);
            this.computeDiff("UnitOfOrderRouting", this.bean.getUnitOfOrderRouting(), otherTyped.getUnitOfOrderRouting(), false);
            this.computeDiff("UseSAFTimeToLiveDefault", this.bean.isUseSAFTimeToLiveDefault(), otherTyped.isUseSAFTimeToLiveDefault(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SAFDestinationBeanImpl original = (SAFDestinationBeanImpl)event.getSourceBean();
            SAFDestinationBeanImpl proposed = (SAFDestinationBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LocalJNDIName")) {
                  original.setLocalJNDIName(proposed.getLocalJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("MessageLoggingParams")) {
                  if (type == 2) {
                     original.setMessageLoggingParams((MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getMessageLoggingParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("MessageLoggingParams", (DescriptorBean)original.getMessageLoggingParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("NonPersistentQos")) {
                  original.setNonPersistentQos(proposed.getNonPersistentQos());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("PersistentQos")) {
                  original.setPersistentQos(proposed.getPersistentQos());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("RemoteJNDIName")) {
                  original.setRemoteJNDIName(proposed.getRemoteJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("SAFErrorHandling")) {
                  original.setSAFErrorHandlingAsString(proposed.getSAFErrorHandlingAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("TimeToLiveDefault")) {
                  original.setTimeToLiveDefault(proposed.getTimeToLiveDefault());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("UnitOfOrderRouting")) {
                  original.setUnitOfOrderRouting(proposed.getUnitOfOrderRouting());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("UseSAFTimeToLiveDefault")) {
                  original.setUseSAFTimeToLiveDefault(proposed.isUseSAFTimeToLiveDefault());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
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
            SAFDestinationBeanImpl copy = (SAFDestinationBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LocalJNDIName")) && this.bean.isLocalJNDINameSet()) {
               copy.setLocalJNDIName(this.bean.getLocalJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageLoggingParams")) && this.bean.isMessageLoggingParamsSet() && !copy._isSet(11)) {
               Object o = this.bean.getMessageLoggingParams();
               copy.setMessageLoggingParams((MessageLoggingParamsBean)null);
               copy.setMessageLoggingParams(o == null ? null : (MessageLoggingParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("NonPersistentQos")) && this.bean.isNonPersistentQosSet()) {
               copy.setNonPersistentQos(this.bean.getNonPersistentQos());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentQos")) && this.bean.isPersistentQosSet()) {
               copy.setPersistentQos(this.bean.getPersistentQos());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteJNDIName")) && this.bean.isRemoteJNDINameSet()) {
               copy.setRemoteJNDIName(this.bean.getRemoteJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("SAFErrorHandling")) && this.bean.isSAFErrorHandlingSet()) {
               copy._unSet(copy, 7);
               copy.setSAFErrorHandlingAsString(this.bean.getSAFErrorHandlingAsString());
            }

            if ((excludeProps == null || !excludeProps.contains("TimeToLiveDefault")) && this.bean.isTimeToLiveDefaultSet()) {
               copy.setTimeToLiveDefault(this.bean.getTimeToLiveDefault());
            }

            if ((excludeProps == null || !excludeProps.contains("UnitOfOrderRouting")) && this.bean.isUnitOfOrderRoutingSet()) {
               copy.setUnitOfOrderRouting(this.bean.getUnitOfOrderRouting());
            }

            if ((excludeProps == null || !excludeProps.contains("UseSAFTimeToLiveDefault")) && this.bean.isUseSAFTimeToLiveDefaultSet()) {
               copy.setUseSAFTimeToLiveDefault(this.bean.isUseSAFTimeToLiveDefault());
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
         this.inferSubTree(this.bean.getMessageLoggingParams(), clazz, annotation);
         this.inferSubTree(this.bean.getSAFErrorHandling(), clazz, annotation);
      }
   }
}
