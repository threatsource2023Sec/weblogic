package weblogic.diagnostics.image.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class LoggingImageSourceBeanImpl extends AbstractDescriptorBean implements LoggingImageSourceBean, Serializable {
   private LogEntryBean[] _LogEntries;
   private static SchemaHelper2 _schemaHelper;

   public LoggingImageSourceBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public LoggingImageSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public LoggingImageSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addLogEntry(LogEntryBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         LogEntryBean[] _new;
         if (this._isSet(0)) {
            _new = (LogEntryBean[])((LogEntryBean[])this._getHelper()._extendArray(this.getLogEntries(), LogEntryBean.class, param0));
         } else {
            _new = new LogEntryBean[]{param0};
         }

         try {
            this.setLogEntries(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public LogEntryBean[] getLogEntries() {
      return this._LogEntries;
   }

   public boolean isLogEntriesInherited() {
      return false;
   }

   public boolean isLogEntriesSet() {
      return this._isSet(0);
   }

   public void removeLogEntry(LogEntryBean param0) {
      LogEntryBean[] _old = this.getLogEntries();
      LogEntryBean[] _new = (LogEntryBean[])((LogEntryBean[])this._getHelper()._removeElement(_old, LogEntryBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setLogEntries(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setLogEntries(LogEntryBean[] param0) throws InvalidAttributeValueException {
      LogEntryBean[] param0 = param0 == null ? new LogEntryBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      LogEntryBean[] _oldVal = this._LogEntries;
      this._LogEntries = (LogEntryBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public LogEntryBean createLogEntry() {
      LogEntryBeanImpl _val = new LogEntryBeanImpl(this, -1);

      try {
         this.addLogEntry(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
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
               this._LogEntries = new LogEntryBean[0];
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image/1.0/weblogic-diagnostics-image.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics-image";
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
            case 9:
               if (s.equals("log-entry")) {
                  return 0;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new LogEntryBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "logging-image-source";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "log-entry";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private LoggingImageSourceBeanImpl bean;

      protected Helper(LoggingImageSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "LogEntries";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("LogEntries") ? 0 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getLogEntries()));
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
            childValue = 0L;

            for(int i = 0; i < this.bean.getLogEntries().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getLogEntries()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            LoggingImageSourceBeanImpl otherTyped = (LoggingImageSourceBeanImpl)other;
            this.computeChildDiff("LogEntries", this.bean.getLogEntries(), otherTyped.getLogEntries(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            LoggingImageSourceBeanImpl original = (LoggingImageSourceBeanImpl)event.getSourceBean();
            LoggingImageSourceBeanImpl proposed = (LoggingImageSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("LogEntries")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addLogEntry((LogEntryBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeLogEntry((LogEntryBean)update.getRemovedObject());
                  }

                  if (original.getLogEntries() == null || original.getLogEntries().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 0);
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
            LoggingImageSourceBeanImpl copy = (LoggingImageSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("LogEntries")) && this.bean.isLogEntriesSet() && !copy._isSet(0)) {
               LogEntryBean[] oldLogEntries = this.bean.getLogEntries();
               LogEntryBean[] newLogEntries = new LogEntryBean[oldLogEntries.length];

               for(int i = 0; i < newLogEntries.length; ++i) {
                  newLogEntries[i] = (LogEntryBean)((LogEntryBean)this.createCopy((AbstractDescriptorBean)oldLogEntries[i], includeObsolete));
               }

               copy.setLogEntries(newLogEntries);
            }

            return copy;
         } catch (RuntimeException var9) {
            throw var9;
         } catch (Exception var10) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var10);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
         this.inferSubTree(this.bean.getLogEntries(), clazz, annotation);
      }
   }
}
