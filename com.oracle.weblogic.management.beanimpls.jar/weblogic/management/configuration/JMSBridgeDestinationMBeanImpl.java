package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.ManagementException;
import weblogic.utils.collections.CombinedIterator;

public class JMSBridgeDestinationMBeanImpl extends BridgeDestinationCommonMBeanImpl implements JMSBridgeDestinationMBean, Serializable {
   private String _ConnectionFactoryJNDIName;
   private String _ConnectionURL;
   private String _DestinationJNDIName;
   private String _DestinationType;
   private String _InitialContextFactory;
   private String _Name;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JMSBridgeDestinationMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JMSBridgeDestinationMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JMSBridgeDestinationMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JMSBridgeDestinationMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JMSBridgeDestinationMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      JMSBridgeDestinationMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public JMSBridgeDestinationMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMSBridgeDestinationMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMSBridgeDestinationMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      if (!this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2)) {
         return this._performMacroSubstitution(this._getDelegateBean().getName(), this);
      } else {
         if (!this._isSet(2)) {
            try {
               return ((ConfigurationMBean)this.getParent()).getName();
            } catch (NullPointerException var2) {
            }
         }

         return this._Name;
      }
   }

   public boolean isNameInherited() {
      return !this._isSet(2) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(2);
   }

   public boolean isNameSet() {
      return this._isSet(2);
   }

   public String getConnectionFactoryJNDIName() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionFactoryJNDIName(), this) : this._ConnectionFactoryJNDIName;
   }

   public boolean isConnectionFactoryJNDINameInherited() {
      return !this._isSet(15) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(15);
   }

   public boolean isConnectionFactoryJNDINameSet() {
      return this._isSet(15);
   }

   public void setName(String param0) throws InvalidAttributeValueException, ManagementException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
      }

      LegalChecks.checkNonEmptyString("Name", param0);
      LegalChecks.checkNonNull("Name", param0);
      ConfigurationValidator.validateName(param0);
      boolean wasSet = this._isSet(2);
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(2, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSBridgeDestinationMBeanImpl source = (JMSBridgeDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(2)) {
            source._postSetFirePropertyChange(2, wasSet, _oldVal, param0);
         }
      }

   }

   public void setConnectionFactoryJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateNotNullable(this.getConnectionFactoryJNDIName(), param0);
      boolean wasSet = this._isSet(15);
      String _oldVal = this._ConnectionFactoryJNDIName;
      this._ConnectionFactoryJNDIName = param0;
      this._postSet(15, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSBridgeDestinationMBeanImpl source = (JMSBridgeDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(15)) {
            source._postSetFirePropertyChange(15, wasSet, _oldVal, param0);
         }
      }

   }

   public String getInitialContextFactory() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16) ? this._performMacroSubstitution(this._getDelegateBean().getInitialContextFactory(), this) : this._InitialContextFactory;
   }

   public boolean isInitialContextFactoryInherited() {
      return !this._isSet(16) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(16);
   }

   public boolean isInitialContextFactorySet() {
      return this._isSet(16);
   }

   public void setInitialContextFactory(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(16);
      String _oldVal = this._InitialContextFactory;
      this._InitialContextFactory = param0;
      this._postSet(16, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSBridgeDestinationMBeanImpl source = (JMSBridgeDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(16)) {
            source._postSetFirePropertyChange(16, wasSet, _oldVal, param0);
         }
      }

   }

   public String getConnectionURL() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17) ? this._performMacroSubstitution(this._getDelegateBean().getConnectionURL(), this) : this._ConnectionURL;
   }

   public boolean isConnectionURLInherited() {
      return !this._isSet(17) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(17);
   }

   public boolean isConnectionURLSet() {
      return this._isSet(17);
   }

   public void setConnectionURL(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(17);
      String _oldVal = this._ConnectionURL;
      this._ConnectionURL = param0;
      this._postSet(17, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSBridgeDestinationMBeanImpl source = (JMSBridgeDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(17)) {
            source._postSetFirePropertyChange(17, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDestinationJNDIName() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18) ? this._performMacroSubstitution(this._getDelegateBean().getDestinationJNDIName(), this) : this._DestinationJNDIName;
   }

   public boolean isDestinationJNDINameInherited() {
      return !this._isSet(18) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(18);
   }

   public boolean isDestinationJNDINameSet() {
      return this._isSet(18);
   }

   public void setDestinationJNDIName(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalHelper.validateNotNullable(this.getDestinationJNDIName(), param0);
      boolean wasSet = this._isSet(18);
      String _oldVal = this._DestinationJNDIName;
      this._DestinationJNDIName = param0;
      this._postSet(18, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSBridgeDestinationMBeanImpl source = (JMSBridgeDestinationMBeanImpl)var4.next();
         if (source != null && !source._isSet(18)) {
            source._postSetFirePropertyChange(18, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDestinationType() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19) ? this._performMacroSubstitution(this._getDelegateBean().getDestinationType(), this) : this._DestinationType;
   }

   public boolean isDestinationTypeInherited() {
      return !this._isSet(19) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(19);
   }

   public boolean isDestinationTypeSet() {
      return this._isSet(19);
   }

   public void setDestinationType(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      String[] _set = new String[]{"Queue", "Topic"};
      param0 = LegalChecks.checkInEnum("DestinationType", param0, _set);
      boolean wasSet = this._isSet(19);
      String _oldVal = this._DestinationType;
      this._DestinationType = param0;
      this._postSet(19, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         JMSBridgeDestinationMBeanImpl source = (JMSBridgeDestinationMBeanImpl)var5.next();
         if (source != null && !source._isSet(19)) {
            source._postSetFirePropertyChange(19, wasSet, _oldVal, param0);
         }
      }

   }

   public Object _getKey() {
      return this.getName();
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
         case 4:
            if (s.equals("name")) {
               return info.compareXpaths(this._getPropertyXpath("name"));
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._ConnectionFactoryJNDIName = null;
               if (initOne) {
                  break;
               }
            case 17:
               this._ConnectionURL = null;
               if (initOne) {
                  break;
               }
            case 18:
               this._DestinationJNDIName = null;
               if (initOne) {
                  break;
               }
            case 19:
               this._DestinationType = "Queue";
               if (initOne) {
                  break;
               }
            case 16:
               this._InitialContextFactory = "weblogic.jndi.WLInitialContextFactory";
               if (initOne) {
                  break;
               }
            case 2:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
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
      return "JMSBridgeDestination";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("ConnectionFactoryJNDIName")) {
         oldVal = this._ConnectionFactoryJNDIName;
         this._ConnectionFactoryJNDIName = (String)v;
         this._postSet(15, oldVal, this._ConnectionFactoryJNDIName);
      } else if (name.equals("ConnectionURL")) {
         oldVal = this._ConnectionURL;
         this._ConnectionURL = (String)v;
         this._postSet(17, oldVal, this._ConnectionURL);
      } else if (name.equals("DestinationJNDIName")) {
         oldVal = this._DestinationJNDIName;
         this._DestinationJNDIName = (String)v;
         this._postSet(18, oldVal, this._DestinationJNDIName);
      } else if (name.equals("DestinationType")) {
         oldVal = this._DestinationType;
         this._DestinationType = (String)v;
         this._postSet(19, oldVal, this._DestinationType);
      } else if (name.equals("InitialContextFactory")) {
         oldVal = this._InitialContextFactory;
         this._InitialContextFactory = (String)v;
         this._postSet(16, oldVal, this._InitialContextFactory);
      } else if (name.equals("Name")) {
         oldVal = this._Name;
         this._Name = (String)v;
         this._postSet(2, oldVal, this._Name);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ConnectionFactoryJNDIName")) {
         return this._ConnectionFactoryJNDIName;
      } else if (name.equals("ConnectionURL")) {
         return this._ConnectionURL;
      } else if (name.equals("DestinationJNDIName")) {
         return this._DestinationJNDIName;
      } else if (name.equals("DestinationType")) {
         return this._DestinationType;
      } else if (name.equals("InitialContextFactory")) {
         return this._InitialContextFactory;
      } else {
         return name.equals("Name") ? this._Name : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends BridgeDestinationCommonMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 4:
               if (s.equals("name")) {
                  return 2;
               }
               break;
            case 14:
               if (s.equals("connection-url")) {
                  return 17;
               }
               break;
            case 16:
               if (s.equals("destination-type")) {
                  return 19;
               }
               break;
            case 21:
               if (s.equals("destination-jndi-name")) {
                  return 18;
               }
               break;
            case 23:
               if (s.equals("initial-context-factory")) {
                  return 16;
               }
               break;
            case 28:
               if (s.equals("connection-factory-jndi-name")) {
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
            case 2:
               return "name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            default:
               return super.getElementName(propIndex);
            case 15:
               return "connection-factory-jndi-name";
            case 16:
               return "initial-context-factory";
            case 17:
               return "connection-url";
            case 18:
               return "destination-jndi-name";
            case 19:
               return "destination-type";
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 13:
               return true;
            default:
               return super.isArray(propIndex);
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
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends BridgeDestinationCommonMBeanImpl.Helper {
      private JMSBridgeDestinationMBeanImpl bean;

      protected Helper(JMSBridgeDestinationMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Name";
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            default:
               return super.getPropertyName(propIndex);
            case 15:
               return "ConnectionFactoryJNDIName";
            case 16:
               return "InitialContextFactory";
            case 17:
               return "ConnectionURL";
            case 18:
               return "DestinationJNDIName";
            case 19:
               return "DestinationType";
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionFactoryJNDIName")) {
            return 15;
         } else if (propName.equals("ConnectionURL")) {
            return 17;
         } else if (propName.equals("DestinationJNDIName")) {
            return 18;
         } else if (propName.equals("DestinationType")) {
            return 19;
         } else if (propName.equals("InitialContextFactory")) {
            return 16;
         } else {
            return propName.equals("Name") ? 2 : super.getPropertyIndex(propName);
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
            if (this.bean.isConnectionFactoryJNDINameSet()) {
               buf.append("ConnectionFactoryJNDIName");
               buf.append(String.valueOf(this.bean.getConnectionFactoryJNDIName()));
            }

            if (this.bean.isConnectionURLSet()) {
               buf.append("ConnectionURL");
               buf.append(String.valueOf(this.bean.getConnectionURL()));
            }

            if (this.bean.isDestinationJNDINameSet()) {
               buf.append("DestinationJNDIName");
               buf.append(String.valueOf(this.bean.getDestinationJNDIName()));
            }

            if (this.bean.isDestinationTypeSet()) {
               buf.append("DestinationType");
               buf.append(String.valueOf(this.bean.getDestinationType()));
            }

            if (this.bean.isInitialContextFactorySet()) {
               buf.append("InitialContextFactory");
               buf.append(String.valueOf(this.bean.getInitialContextFactory()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
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
            JMSBridgeDestinationMBeanImpl otherTyped = (JMSBridgeDestinationMBeanImpl)other;
            this.computeDiff("ConnectionFactoryJNDIName", this.bean.getConnectionFactoryJNDIName(), otherTyped.getConnectionFactoryJNDIName(), false);
            this.computeDiff("ConnectionURL", this.bean.getConnectionURL(), otherTyped.getConnectionURL(), false);
            this.computeDiff("DestinationJNDIName", this.bean.getDestinationJNDIName(), otherTyped.getDestinationJNDIName(), false);
            this.computeDiff("DestinationType", this.bean.getDestinationType(), otherTyped.getDestinationType(), false);
            this.computeDiff("InitialContextFactory", this.bean.getInitialContextFactory(), otherTyped.getInitialContextFactory(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSBridgeDestinationMBeanImpl original = (JMSBridgeDestinationMBeanImpl)event.getSourceBean();
            JMSBridgeDestinationMBeanImpl proposed = (JMSBridgeDestinationMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionFactoryJNDIName")) {
                  original.setConnectionFactoryJNDIName(proposed.getConnectionFactoryJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("ConnectionURL")) {
                  original.setConnectionURL(proposed.getConnectionURL());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("DestinationJNDIName")) {
                  original.setDestinationJNDIName(proposed.getDestinationJNDIName());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("DestinationType")) {
                  original.setDestinationType(proposed.getDestinationType());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("InitialContextFactory")) {
                  original.setInitialContextFactory(proposed.getInitialContextFactory());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
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
            JMSBridgeDestinationMBeanImpl copy = (JMSBridgeDestinationMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionFactoryJNDIName")) && this.bean.isConnectionFactoryJNDINameSet()) {
               copy.setConnectionFactoryJNDIName(this.bean.getConnectionFactoryJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionURL")) && this.bean.isConnectionURLSet()) {
               copy.setConnectionURL(this.bean.getConnectionURL());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationJNDIName")) && this.bean.isDestinationJNDINameSet()) {
               copy.setDestinationJNDIName(this.bean.getDestinationJNDIName());
            }

            if ((excludeProps == null || !excludeProps.contains("DestinationType")) && this.bean.isDestinationTypeSet()) {
               copy.setDestinationType(this.bean.getDestinationType());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialContextFactory")) && this.bean.isInitialContextFactorySet()) {
               copy.setInitialContextFactory(this.bean.getInitialContextFactory());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
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
