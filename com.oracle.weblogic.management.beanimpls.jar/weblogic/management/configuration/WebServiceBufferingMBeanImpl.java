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
import weblogic.j2ee.descriptor.wl.validators.WseeConfigBeanValidator;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WebServiceBufferingMBeanImpl extends ConfigurationMBeanImpl implements WebServiceBufferingMBean, Serializable {
   private int _RetryCount;
   private String _RetryDelay;
   private WebServiceRequestBufferingQueueMBean _WebServiceRequestBufferingQueue;
   private WebServiceResponseBufferingQueueMBean _WebServiceResponseBufferingQueue;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private WebServiceBufferingMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(WebServiceBufferingMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(WebServiceBufferingMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public WebServiceBufferingMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(WebServiceBufferingMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      WebServiceBufferingMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public WebServiceBufferingMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebServiceBufferingMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebServiceBufferingMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public WebServiceRequestBufferingQueueMBean getWebServiceRequestBufferingQueue() {
      return this._WebServiceRequestBufferingQueue;
   }

   public boolean isWebServiceRequestBufferingQueueInherited() {
      return false;
   }

   public boolean isWebServiceRequestBufferingQueueSet() {
      return this._isSet(10) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServiceRequestBufferingQueue());
   }

   public void setWebServiceRequestBufferingQueue(WebServiceRequestBufferingQueueMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 10)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(10);
      WebServiceRequestBufferingQueueMBean _oldVal = this._WebServiceRequestBufferingQueue;
      this._WebServiceRequestBufferingQueue = param0;
      this._postSet(10, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServiceBufferingMBeanImpl source = (WebServiceBufferingMBeanImpl)var5.next();
         if (source != null && !source._isSet(10)) {
            source._postSetFirePropertyChange(10, wasSet, _oldVal, param0);
         }
      }

   }

   public WebServiceResponseBufferingQueueMBean getWebServiceResponseBufferingQueue() {
      return this._WebServiceResponseBufferingQueue;
   }

   public boolean isWebServiceResponseBufferingQueueInherited() {
      return false;
   }

   public boolean isWebServiceResponseBufferingQueueSet() {
      return this._isSet(11) || this._isAnythingSet((AbstractDescriptorBean)this.getWebServiceResponseBufferingQueue());
   }

   public void setWebServiceResponseBufferingQueue(WebServiceResponseBufferingQueueMBean param0) throws InvalidAttributeValueException {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 11)) {
         this._postCreate(_child);
      }

      boolean wasSet = this._isSet(11);
      WebServiceResponseBufferingQueueMBean _oldVal = this._WebServiceResponseBufferingQueue;
      this._WebServiceResponseBufferingQueue = param0;
      this._postSet(11, _oldVal, param0);
      Iterator var5 = this._DelegateSources.iterator();

      while(var5.hasNext()) {
         WebServiceBufferingMBeanImpl source = (WebServiceBufferingMBeanImpl)var5.next();
         if (source != null && !source._isSet(11)) {
            source._postSetFirePropertyChange(11, wasSet, _oldVal, param0);
         }
      }

   }

   public int getRetryCount() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12) ? this._getDelegateBean().getRetryCount() : this._RetryCount;
   }

   public boolean isRetryCountInherited() {
      return !this._isSet(12) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(12);
   }

   public boolean isRetryCountSet() {
      return this._isSet(12);
   }

   public void setRetryCount(int param0) {
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      LegalChecks.checkMin("RetryCount", param0, 0);
      boolean wasSet = this._isSet(12);
      int _oldVal = this._RetryCount;
      this._RetryCount = param0;
      this._postSet(12, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceBufferingMBeanImpl source = (WebServiceBufferingMBeanImpl)var4.next();
         if (source != null && !source._isSet(12)) {
            source._postSetFirePropertyChange(12, wasSet, _oldVal, param0);
         }
      }

   }

   public String getRetryDelay() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13) ? this._performMacroSubstitution(this._getDelegateBean().getRetryDelay(), this) : this._RetryDelay;
   }

   public boolean isRetryDelayInherited() {
      return !this._isSet(13) && this._getDelegateBean() != null && this._getDelegateBean()._isSet(13);
   }

   public boolean isRetryDelaySet() {
      return this._isSet(13);
   }

   public void setRetryDelay(String param0) {
      param0 = param0 == null ? null : param0.trim();
      if (this._isTransient() && this._isSynthetic() && this._getDelegateBean() != null) {
         this._untransient();
      }

      WseeConfigBeanValidator.validateRetryDelay(param0);
      boolean wasSet = this._isSet(13);
      String _oldVal = this._RetryDelay;
      this._RetryDelay = param0;
      this._postSet(13, _oldVal, param0);
      Iterator var4 = this._DelegateSources.iterator();

      while(var4.hasNext()) {
         WebServiceBufferingMBeanImpl source = (WebServiceBufferingMBeanImpl)var4.next();
         if (source != null && !source._isSet(13)) {
            source._postSetFirePropertyChange(13, wasSet, _oldVal, param0);
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
      return super._isAnythingSet() || this.isWebServiceRequestBufferingQueueSet() || this.isWebServiceResponseBufferingQueueSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 12;
      }

      try {
         switch (idx) {
            case 12:
               this._RetryCount = 3;
               if (initOne) {
                  break;
               }
            case 13:
               this._RetryDelay = "P0DT30S";
               if (initOne) {
                  break;
               }
            case 10:
               this._WebServiceRequestBufferingQueue = new WebServiceRequestBufferingQueueMBeanImpl(this, 10);
               this._postCreate((AbstractDescriptorBean)this._WebServiceRequestBufferingQueue);
               if (initOne) {
                  break;
               }
            case 11:
               this._WebServiceResponseBufferingQueue = new WebServiceResponseBufferingQueueMBeanImpl(this, 11);
               this._postCreate((AbstractDescriptorBean)this._WebServiceResponseBufferingQueue);
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
      return "WebServiceBuffering";
   }

   public void putValue(String name, Object v) {
      if (name.equals("RetryCount")) {
         int oldVal = this._RetryCount;
         this._RetryCount = (Integer)v;
         this._postSet(12, oldVal, this._RetryCount);
      } else if (name.equals("RetryDelay")) {
         String oldVal = this._RetryDelay;
         this._RetryDelay = (String)v;
         this._postSet(13, oldVal, this._RetryDelay);
      } else if (name.equals("WebServiceRequestBufferingQueue")) {
         WebServiceRequestBufferingQueueMBean oldVal = this._WebServiceRequestBufferingQueue;
         this._WebServiceRequestBufferingQueue = (WebServiceRequestBufferingQueueMBean)v;
         this._postSet(10, oldVal, this._WebServiceRequestBufferingQueue);
      } else if (name.equals("WebServiceResponseBufferingQueue")) {
         WebServiceResponseBufferingQueueMBean oldVal = this._WebServiceResponseBufferingQueue;
         this._WebServiceResponseBufferingQueue = (WebServiceResponseBufferingQueueMBean)v;
         this._postSet(11, oldVal, this._WebServiceResponseBufferingQueue);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("RetryCount")) {
         return new Integer(this._RetryCount);
      } else if (name.equals("RetryDelay")) {
         return this._RetryDelay;
      } else if (name.equals("WebServiceRequestBufferingQueue")) {
         return this._WebServiceRequestBufferingQueue;
      } else {
         return name.equals("WebServiceResponseBufferingQueue") ? this._WebServiceResponseBufferingQueue : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ConfigurationMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("retry-count")) {
                  return 12;
               }

               if (s.equals("retry-delay")) {
                  return 13;
               }
               break;
            case 35:
               if (s.equals("web-service-request-buffering-queue")) {
                  return 10;
               }
               break;
            case 36:
               if (s.equals("web-service-response-buffering-queue")) {
                  return 11;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 10:
               return new WebServiceRequestBufferingQueueMBeanImpl.SchemaHelper2();
            case 11:
               return new WebServiceResponseBufferingQueueMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "web-service-request-buffering-queue";
            case 11:
               return "web-service-response-buffering-queue";
            case 12:
               return "retry-count";
            case 13:
               return "retry-delay";
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

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 10:
               return true;
            case 11:
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

   protected static class Helper extends ConfigurationMBeanImpl.Helper {
      private WebServiceBufferingMBeanImpl bean;

      protected Helper(WebServiceBufferingMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 10:
               return "WebServiceRequestBufferingQueue";
            case 11:
               return "WebServiceResponseBufferingQueue";
            case 12:
               return "RetryCount";
            case 13:
               return "RetryDelay";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("RetryCount")) {
            return 12;
         } else if (propName.equals("RetryDelay")) {
            return 13;
         } else if (propName.equals("WebServiceRequestBufferingQueue")) {
            return 10;
         } else {
            return propName.equals("WebServiceResponseBufferingQueue") ? 11 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getWebServiceRequestBufferingQueue() != null) {
            iterators.add(new ArrayIterator(new WebServiceRequestBufferingQueueMBean[]{this.bean.getWebServiceRequestBufferingQueue()}));
         }

         if (this.bean.getWebServiceResponseBufferingQueue() != null) {
            iterators.add(new ArrayIterator(new WebServiceResponseBufferingQueueMBean[]{this.bean.getWebServiceResponseBufferingQueue()}));
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
            if (this.bean.isRetryCountSet()) {
               buf.append("RetryCount");
               buf.append(String.valueOf(this.bean.getRetryCount()));
            }

            if (this.bean.isRetryDelaySet()) {
               buf.append("RetryDelay");
               buf.append(String.valueOf(this.bean.getRetryDelay()));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServiceRequestBufferingQueue());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWebServiceResponseBufferingQueue());
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
            WebServiceBufferingMBeanImpl otherTyped = (WebServiceBufferingMBeanImpl)other;
            this.computeDiff("RetryCount", this.bean.getRetryCount(), otherTyped.getRetryCount(), true);
            this.computeDiff("RetryDelay", this.bean.getRetryDelay(), otherTyped.getRetryDelay(), true);
            this.computeSubDiff("WebServiceRequestBufferingQueue", this.bean.getWebServiceRequestBufferingQueue(), otherTyped.getWebServiceRequestBufferingQueue());
            this.computeSubDiff("WebServiceResponseBufferingQueue", this.bean.getWebServiceResponseBufferingQueue(), otherTyped.getWebServiceResponseBufferingQueue());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebServiceBufferingMBeanImpl original = (WebServiceBufferingMBeanImpl)event.getSourceBean();
            WebServiceBufferingMBeanImpl proposed = (WebServiceBufferingMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("RetryCount")) {
                  original.setRetryCount(proposed.getRetryCount());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("RetryDelay")) {
                  original.setRetryDelay(proposed.getRetryDelay());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("WebServiceRequestBufferingQueue")) {
                  if (type == 2) {
                     original.setWebServiceRequestBufferingQueue((WebServiceRequestBufferingQueueMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServiceRequestBufferingQueue()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebServiceRequestBufferingQueue", original.getWebServiceRequestBufferingQueue());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("WebServiceResponseBufferingQueue")) {
                  if (type == 2) {
                     original.setWebServiceResponseBufferingQueue((WebServiceResponseBufferingQueueMBean)this.createCopy((AbstractDescriptorBean)proposed.getWebServiceResponseBufferingQueue()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WebServiceResponseBufferingQueue", original.getWebServiceResponseBufferingQueue());
                  }

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
            WebServiceBufferingMBeanImpl copy = (WebServiceBufferingMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("RetryCount")) && this.bean.isRetryCountSet()) {
               copy.setRetryCount(this.bean.getRetryCount());
            }

            if ((excludeProps == null || !excludeProps.contains("RetryDelay")) && this.bean.isRetryDelaySet()) {
               copy.setRetryDelay(this.bean.getRetryDelay());
            }

            if ((excludeProps == null || !excludeProps.contains("WebServiceRequestBufferingQueue")) && this.bean.isWebServiceRequestBufferingQueueSet() && !copy._isSet(10)) {
               Object o = this.bean.getWebServiceRequestBufferingQueue();
               copy.setWebServiceRequestBufferingQueue((WebServiceRequestBufferingQueueMBean)null);
               copy.setWebServiceRequestBufferingQueue(o == null ? null : (WebServiceRequestBufferingQueueMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WebServiceResponseBufferingQueue")) && this.bean.isWebServiceResponseBufferingQueueSet() && !copy._isSet(11)) {
               Object o = this.bean.getWebServiceResponseBufferingQueue();
               copy.setWebServiceResponseBufferingQueue((WebServiceResponseBufferingQueueMBean)null);
               copy.setWebServiceResponseBufferingQueue(o == null ? null : (WebServiceResponseBufferingQueueMBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getWebServiceRequestBufferingQueue(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServiceResponseBufferingQueue(), clazz, annotation);
      }
   }
}
