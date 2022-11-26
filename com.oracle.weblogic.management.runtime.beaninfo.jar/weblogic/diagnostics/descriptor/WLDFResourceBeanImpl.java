package weblogic.diagnostics.descriptor;

import java.io.Serializable;
import java.util.ArrayList;
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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class WLDFResourceBeanImpl extends WLDFBeanImpl implements WLDFResourceBean, Serializable {
   private WLDFHarvesterBean _Harvester;
   private WLDFInstrumentationBean _Instrumentation;
   private WLDFWatchNotificationBean _WatchNotification;
   private static SchemaHelper2 _schemaHelper;

   public WLDFResourceBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WLDFResourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WLDFResourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public WLDFInstrumentationBean getInstrumentation() {
      return this._Instrumentation;
   }

   public boolean isInstrumentationInherited() {
      return false;
   }

   public boolean isInstrumentationSet() {
      return this._isSet(2) || this._isAnythingSet((AbstractDescriptorBean)this.getInstrumentation());
   }

   public void setInstrumentation(WLDFInstrumentationBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 2)) {
         this._postCreate(_child);
      }

      WLDFInstrumentationBean _oldVal = this._Instrumentation;
      this._Instrumentation = param0;
      this._postSet(2, _oldVal, param0);
   }

   public WLDFHarvesterBean getHarvester() {
      return this._Harvester;
   }

   public boolean isHarvesterInherited() {
      return false;
   }

   public boolean isHarvesterSet() {
      return this._isSet(3) || this._isAnythingSet((AbstractDescriptorBean)this.getHarvester());
   }

   public void setHarvester(WLDFHarvesterBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 3)) {
         this._postCreate(_child);
      }

      WLDFHarvesterBean _oldVal = this._Harvester;
      this._Harvester = param0;
      this._postSet(3, _oldVal, param0);
   }

   public WLDFWatchNotificationBean getWatchNotification() {
      return this._WatchNotification;
   }

   public boolean isWatchNotificationInherited() {
      return false;
   }

   public boolean isWatchNotificationSet() {
      return this._isSet(4) || this._isAnythingSet((AbstractDescriptorBean)this.getWatchNotification());
   }

   public void setWatchNotification(WLDFWatchNotificationBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 4)) {
         this._postCreate(_child);
      }

      WLDFWatchNotificationBean _oldVal = this._WatchNotification;
      this._WatchNotification = param0;
      this._postSet(4, _oldVal, param0);
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
      return super._isAnythingSet() || this.isHarvesterSet() || this.isInstrumentationSet() || this.isWatchNotificationSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Harvester = new WLDFHarvesterBeanImpl(this, 3);
               this._postCreate((AbstractDescriptorBean)this._Harvester);
               if (initOne) {
                  break;
               }
            case 2:
               this._Instrumentation = new WLDFInstrumentationBeanImpl(this, 2);
               this._postCreate((AbstractDescriptorBean)this._Instrumentation);
               if (initOne) {
                  break;
               }
            case 4:
               this._WatchNotification = new WLDFWatchNotificationBeanImpl(this, 4);
               this._postCreate((AbstractDescriptorBean)this._WatchNotification);
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
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics/1.0/weblogic-diagnostics.xsd";
   }

   protected String getTargetNamespace() {
      return "http://xmlns.oracle.com/weblogic/weblogic-diagnostics";
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends WLDFBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 9:
               if (s.equals("harvester")) {
                  return 3;
               }
               break;
            case 15:
               if (s.equals("instrumentation")) {
                  return 2;
               }
               break;
            case 18:
               if (s.equals("watch-notification")) {
                  return 4;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 2:
               return new WLDFInstrumentationBeanImpl.SchemaHelper2();
            case 3:
               return new WLDFHarvesterBeanImpl.SchemaHelper2();
            case 4:
               return new WLDFWatchNotificationBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "wldf-resource";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "instrumentation";
            case 3:
               return "harvester";
            case 4:
               return "watch-notification";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 3:
               return true;
            case 4:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isKey(int propIndex) {
         switch (propIndex) {
            case 0:
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

   protected static class Helper extends WLDFBeanImpl.Helper {
      private WLDFResourceBeanImpl bean;

      protected Helper(WLDFResourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 2:
               return "Instrumentation";
            case 3:
               return "Harvester";
            case 4:
               return "WatchNotification";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Harvester")) {
            return 3;
         } else if (propName.equals("Instrumentation")) {
            return 2;
         } else {
            return propName.equals("WatchNotification") ? 4 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getHarvester() != null) {
            iterators.add(new ArrayIterator(new WLDFHarvesterBean[]{this.bean.getHarvester()}));
         }

         if (this.bean.getInstrumentation() != null) {
            iterators.add(new ArrayIterator(new WLDFInstrumentationBean[]{this.bean.getInstrumentation()}));
         }

         if (this.bean.getWatchNotification() != null) {
            iterators.add(new ArrayIterator(new WLDFWatchNotificationBean[]{this.bean.getWatchNotification()}));
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
            childValue = this.computeChildHashValue(this.bean.getHarvester());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getInstrumentation());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getWatchNotification());
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
            WLDFResourceBeanImpl otherTyped = (WLDFResourceBeanImpl)other;
            this.computeSubDiff("Harvester", this.bean.getHarvester(), otherTyped.getHarvester());
            this.computeSubDiff("Instrumentation", this.bean.getInstrumentation(), otherTyped.getInstrumentation());
            this.computeSubDiff("WatchNotification", this.bean.getWatchNotification(), otherTyped.getWatchNotification());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WLDFResourceBeanImpl original = (WLDFResourceBeanImpl)event.getSourceBean();
            WLDFResourceBeanImpl proposed = (WLDFResourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Harvester")) {
                  if (type == 2) {
                     original.setHarvester((WLDFHarvesterBean)this.createCopy((AbstractDescriptorBean)proposed.getHarvester()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Harvester", (DescriptorBean)original.getHarvester());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("Instrumentation")) {
                  if (type == 2) {
                     original.setInstrumentation((WLDFInstrumentationBean)this.createCopy((AbstractDescriptorBean)proposed.getInstrumentation()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("Instrumentation", (DescriptorBean)original.getInstrumentation());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("WatchNotification")) {
                  if (type == 2) {
                     original.setWatchNotification((WLDFWatchNotificationBean)this.createCopy((AbstractDescriptorBean)proposed.getWatchNotification()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("WatchNotification", (DescriptorBean)original.getWatchNotification());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
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
            WLDFResourceBeanImpl copy = (WLDFResourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Harvester")) && this.bean.isHarvesterSet() && !copy._isSet(3)) {
               Object o = this.bean.getHarvester();
               copy.setHarvester((WLDFHarvesterBean)null);
               copy.setHarvester(o == null ? null : (WLDFHarvesterBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("Instrumentation")) && this.bean.isInstrumentationSet() && !copy._isSet(2)) {
               Object o = this.bean.getInstrumentation();
               copy.setInstrumentation((WLDFInstrumentationBean)null);
               copy.setInstrumentation(o == null ? null : (WLDFInstrumentationBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WatchNotification")) && this.bean.isWatchNotificationSet() && !copy._isSet(4)) {
               Object o = this.bean.getWatchNotification();
               copy.setWatchNotification((WLDFWatchNotificationBean)null);
               copy.setWatchNotification(o == null ? null : (WLDFWatchNotificationBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getHarvester(), clazz, annotation);
         this.inferSubTree(this.bean.getInstrumentation(), clazz, annotation);
         this.inferSubTree(this.bean.getWatchNotification(), clazz, annotation);
      }
   }
}
