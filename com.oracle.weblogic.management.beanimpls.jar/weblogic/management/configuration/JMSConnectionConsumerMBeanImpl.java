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
import weblogic.utils.collections.CombinedIterator;

public class JMSConnectionConsumerMBeanImpl extends ConfigurationMBeanImpl implements JMSConnectionConsumerMBean, Serializable {
   private String _Destination;
   private int _MessagesMaximum;
   private String _Selector;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private JMSConnectionConsumerMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(JMSConnectionConsumerMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(JMSConnectionConsumerMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public JMSConnectionConsumerMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(JMSConnectionConsumerMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      JMSConnectionConsumerMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public JMSConnectionConsumerMBeanImpl() {
      this._initializeProperty(-1);
   }

   public JMSConnectionConsumerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JMSConnectionConsumerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getMessagesMaximum() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10) ? this._getDelegateBean().getMessagesMaximum() : this._MessagesMaximum;
   }

   public boolean isMessagesMaximumInherited() {
      return !this._isSet(10) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(10);
   }

   public boolean isMessagesMaximumSet() {
      return this._isSet(10);
   }

   public void setMessagesMaximum(int param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkInRange("MessagesMaximum", (long)param0, 1L, 2147483647L);
      boolean wasSet = this._isSet(10);
      int _oldVal = this._MessagesMaximum;
      this._MessagesMaximum = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSConnectionConsumerMBeanImpl source = (JMSConnectionConsumerMBeanImpl)var4.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public String getSelector() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11) ? this._performMacroSubstitution(this._getDelegateBean().getSelector(), this) : this._Selector;
   }

   public boolean isSelectorInherited() {
      return !this._isSet(11) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(11);
   }

   public boolean isSelectorSet() {
      return this._isSet(11);
   }

   public void setSelector(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(11);
      String _oldVal = this._Selector;
      this._Selector = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSConnectionConsumerMBeanImpl source = (JMSConnectionConsumerMBeanImpl)var4.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public String getDestination() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._performMacroSubstitution(this._getDelegateBean().getDestination(), this) : this._Destination;
   }

   public boolean isDestinationInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isDestinationSet() {
      return this._isSet(12);
   }

   public void setDestination(String param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      boolean wasSet = this._isSet(12);
      String _oldVal = this._Destination;
      this._Destination = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         JMSConnectionConsumerMBeanImpl source = (JMSConnectionConsumerMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
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
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._Destination = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._MessagesMaximum = 10;
               if (initOne) {
                  break;
               }
            case 11:
               this._Selector = null;
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
      return "JMSConnectionConsumer";
   }

   public void putValue(String name, Object v) {
      String oldVal;
      if (name.equals("Destination")) {
         oldVal = this._Destination;
         this._Destination = (String)v;
         this._postSet(12, oldVal, this._Destination);
      } else if (name.equals("MessagesMaximum")) {
         int oldVal = this._MessagesMaximum;
         this._MessagesMaximum = (Integer)v;
         this._postSet(10, oldVal, this._MessagesMaximum);
      } else if (name.equals("Selector")) {
         oldVal = this._Selector;
         this._Selector = (String)v;
         this._postSet(11, oldVal, this._Selector);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("Destination")) {
         return this._Destination;
      } else if (name.equals("MessagesMaximum")) {
         return new Integer(this._MessagesMaximum);
      } else {
         return name.equals("Selector") ? this._Selector : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 8:
               if (s.equals("selector")) {
                  return 11;
               }
               break;
            case 11:
               if (s.equals("destination")) {
                  return 12;
               }
               break;
            case 16:
               if (s.equals("messages-maximum")) {
                  return 10;
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
            case 10:
               return "messages-maximum";
            case 11:
               return "selector";
            case 12:
               return "destination";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
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

      public String[] getKeyElementNames() {
         List indices = new ArrayList();
         indices.add("name");
         return (String[])((String[])indices.toArray(new String[0]));
      }
   }

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private JMSConnectionConsumerMBeanImpl bean;

      protected Helper(JMSConnectionConsumerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "MessagesMaximum";
            case 11:
               return "Selector";
            case 12:
               return "Destination";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Destination")) {
            return 12;
         } else if (propName.equals("MessagesMaximum")) {
            return 10;
         } else {
            return propName.equals("Selector") ? 11 : super.getPropertyIndex(propName);
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
            if (this.bean.isDestinationSet()) {
               buf.append("Destination");
               buf.append(String.valueOf(this.bean.getDestination()));
            }

            if (this.bean.isMessagesMaximumSet()) {
               buf.append("MessagesMaximum");
               buf.append(String.valueOf(this.bean.getMessagesMaximum()));
            }

            if (this.bean.isSelectorSet()) {
               buf.append("Selector");
               buf.append(String.valueOf(this.bean.getSelector()));
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
            JMSConnectionConsumerMBeanImpl otherTyped = (JMSConnectionConsumerMBeanImpl)other;
            this.computeDiff("Destination", this.bean.getDestination(), otherTyped.getDestination(), false);
            this.computeDiff("MessagesMaximum", this.bean.getMessagesMaximum(), otherTyped.getMessagesMaximum(), true);
            this.computeDiff("Selector", this.bean.getSelector(), otherTyped.getSelector(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JMSConnectionConsumerMBeanImpl original = (JMSConnectionConsumerMBeanImpl)event.getSourceBean();
            JMSConnectionConsumerMBeanImpl proposed = (JMSConnectionConsumerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Destination")) {
                  original.setDestination(proposed.getDestination());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MessagesMaximum")) {
                  original.setMessagesMaximum(proposed.getMessagesMaximum());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("Selector")) {
                  original.setSelector(proposed.getSelector());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
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
            JMSConnectionConsumerMBeanImpl copy = (JMSConnectionConsumerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Destination")) && this.bean.isDestinationSet()) {
               copy.setDestination(this.bean.getDestination());
            }

            if ((excludeProps == null || !excludeProps.contains("MessagesMaximum")) && this.bean.isMessagesMaximumSet()) {
               copy.setMessagesMaximum(this.bean.getMessagesMaximum());
            }

            if ((excludeProps == null || !excludeProps.contains("Selector")) && this.bean.isSelectorSet()) {
               copy.setSelector(this.bean.getSelector());
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
