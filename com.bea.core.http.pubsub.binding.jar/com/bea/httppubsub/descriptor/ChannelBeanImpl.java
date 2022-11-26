package com.bea.httppubsub.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ChannelBeanImpl extends AbstractDescriptorBean implements ChannelBean, Serializable {
   private String _ChannelPattern;
   private ChannelPersistenceBean _ChannelPersistence;
   private String _JmsHandlerName;
   private String[] _MessageFilters;
   private static SchemaHelper2 _schemaHelper;

   public ChannelBeanImpl() {
      this._initializeProperty(-1);
   }

   public ChannelBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ChannelBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getChannelPattern() {
      return this._ChannelPattern;
   }

   public boolean isChannelPatternInherited() {
      return false;
   }

   public boolean isChannelPatternSet() {
      return this._isSet(0);
   }

   public void setChannelPattern(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ChannelPattern;
      this._ChannelPattern = param0;
      this._postSet(0, _oldVal, param0);
   }

   public ChannelPersistenceBean getChannelPersistence() {
      return this._ChannelPersistence;
   }

   public boolean isChannelPersistenceInherited() {
      return false;
   }

   public boolean isChannelPersistenceSet() {
      return this._isSet(1);
   }

   public void setChannelPersistence(ChannelPersistenceBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getChannelPersistence() != null && param0 != this.getChannelPersistence()) {
         throw new BeanAlreadyExistsException(this.getChannelPersistence() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ChannelPersistenceBean _oldVal = this._ChannelPersistence;
         this._ChannelPersistence = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public ChannelPersistenceBean createChannelPersistence() {
      ChannelPersistenceBeanImpl _val = new ChannelPersistenceBeanImpl(this, -1);

      try {
         this.setChannelPersistence(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getJmsHandlerName() {
      return this._JmsHandlerName;
   }

   public boolean isJmsHandlerNameInherited() {
      return false;
   }

   public boolean isJmsHandlerNameSet() {
      return this._isSet(2);
   }

   public void setJmsHandlerName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._JmsHandlerName;
      this._JmsHandlerName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public String[] getMessageFilters() {
      return this._MessageFilters;
   }

   public boolean isMessageFiltersInherited() {
      return false;
   }

   public boolean isMessageFiltersSet() {
      return this._isSet(3);
   }

   public void setMessageFilters(String[] param0) throws InvalidAttributeValueException {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._MessageFilters;
      this._MessageFilters = param0;
      this._postSet(3, _oldVal, param0);
   }

   public void addMessageFilter(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(3)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getMessageFilters(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setMessageFilters(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeMessageFilter(String param0) {
      String[] _old = this.getMessageFilters();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setMessageFilters(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
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
         idx = 0;
      }

      try {
         switch (idx) {
            case 0:
               this._ChannelPattern = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._ChannelPersistence = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._JmsHandlerName = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._MessageFilters = new String[0];
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
            case 14:
               if (s.equals("message-filter")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("channel-pattern")) {
                  return 0;
               }
               break;
            case 16:
               if (s.equals("jms-handler-name")) {
                  return 2;
               }
            case 17:
            case 18:
            default:
               break;
            case 19:
               if (s.equals("channel-persistence")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 1:
               return new ChannelPersistenceBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "channel-pattern";
            case 1:
               return "channel-persistence";
            case 2:
               return "jms-handler-name";
            case 3:
               return "message-filter";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 3:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ChannelBeanImpl bean;

      protected Helper(ChannelBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ChannelPattern";
            case 1:
               return "ChannelPersistence";
            case 2:
               return "JmsHandlerName";
            case 3:
               return "MessageFilters";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ChannelPattern")) {
            return 0;
         } else if (propName.equals("ChannelPersistence")) {
            return 1;
         } else if (propName.equals("JmsHandlerName")) {
            return 2;
         } else {
            return propName.equals("MessageFilters") ? 3 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getChannelPersistence() != null) {
            iterators.add(new ArrayIterator(new ChannelPersistenceBean[]{this.bean.getChannelPersistence()}));
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
            if (this.bean.isChannelPatternSet()) {
               buf.append("ChannelPattern");
               buf.append(String.valueOf(this.bean.getChannelPattern()));
            }

            childValue = this.computeChildHashValue(this.bean.getChannelPersistence());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJmsHandlerNameSet()) {
               buf.append("JmsHandlerName");
               buf.append(String.valueOf(this.bean.getJmsHandlerName()));
            }

            if (this.bean.isMessageFiltersSet()) {
               buf.append("MessageFilters");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getMessageFilters())));
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
            ChannelBeanImpl otherTyped = (ChannelBeanImpl)other;
            this.computeDiff("ChannelPattern", this.bean.getChannelPattern(), otherTyped.getChannelPattern(), false);
            this.computeChildDiff("ChannelPersistence", this.bean.getChannelPersistence(), otherTyped.getChannelPersistence(), false);
            this.computeDiff("JmsHandlerName", this.bean.getJmsHandlerName(), otherTyped.getJmsHandlerName(), false);
            this.computeDiff("MessageFilters", this.bean.getMessageFilters(), otherTyped.getMessageFilters(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ChannelBeanImpl original = (ChannelBeanImpl)event.getSourceBean();
            ChannelBeanImpl proposed = (ChannelBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ChannelPattern")) {
                  original.setChannelPattern(proposed.getChannelPattern());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("ChannelPersistence")) {
                  if (type == 2) {
                     original.setChannelPersistence((ChannelPersistenceBean)this.createCopy((AbstractDescriptorBean)proposed.getChannelPersistence()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ChannelPersistence", (DescriptorBean)original.getChannelPersistence());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("JmsHandlerName")) {
                  original.setJmsHandlerName(proposed.getJmsHandlerName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("MessageFilters")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addMessageFilter((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeMessageFilter((String)update.getRemovedObject());
                  }

                  if (original.getMessageFilters() == null || original.getMessageFilters().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
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
            ChannelBeanImpl copy = (ChannelBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ChannelPattern")) && this.bean.isChannelPatternSet()) {
               copy.setChannelPattern(this.bean.getChannelPattern());
            }

            if ((excludeProps == null || !excludeProps.contains("ChannelPersistence")) && this.bean.isChannelPersistenceSet() && !copy._isSet(1)) {
               Object o = this.bean.getChannelPersistence();
               copy.setChannelPersistence((ChannelPersistenceBean)null);
               copy.setChannelPersistence(o == null ? null : (ChannelPersistenceBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JmsHandlerName")) && this.bean.isJmsHandlerNameSet()) {
               copy.setJmsHandlerName(this.bean.getJmsHandlerName());
            }

            if ((excludeProps == null || !excludeProps.contains("MessageFilters")) && this.bean.isMessageFiltersSet()) {
               Object o = this.bean.getMessageFilters();
               copy.setMessageFilters(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
         this.inferSubTree(this.bean.getChannelPersistence(), clazz, annotation);
      }
   }
}
