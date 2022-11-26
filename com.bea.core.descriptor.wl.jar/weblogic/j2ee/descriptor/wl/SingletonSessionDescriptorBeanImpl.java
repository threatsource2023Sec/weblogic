package weblogic.j2ee.descriptor.wl;

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
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class SingletonSessionDescriptorBeanImpl extends AbstractDescriptorBean implements SingletonSessionDescriptorBean, Serializable {
   private String _Id;
   private SingletonClusteringBean _SingletonClustering;
   private TimerDescriptorBean _TimerDescriptor;
   private static SchemaHelper2 _schemaHelper;

   public SingletonSessionDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public SingletonSessionDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public SingletonSessionDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public TimerDescriptorBean getTimerDescriptor() {
      return this._TimerDescriptor;
   }

   public boolean isTimerDescriptorInherited() {
      return false;
   }

   public boolean isTimerDescriptorSet() {
      return this._isSet(0) || this._isAnythingSet((AbstractDescriptorBean)this.getTimerDescriptor());
   }

   public void setTimerDescriptor(TimerDescriptorBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 0)) {
         this._postCreate(_child);
      }

      TimerDescriptorBean _oldVal = this._TimerDescriptor;
      this._TimerDescriptor = param0;
      this._postSet(0, _oldVal, param0);
   }

   public SingletonClusteringBean getSingletonClustering() {
      return this._SingletonClustering;
   }

   public boolean isSingletonClusteringInherited() {
      return false;
   }

   public boolean isSingletonClusteringSet() {
      return this._isSet(1) || this._isAnythingSet((AbstractDescriptorBean)this.getSingletonClustering());
   }

   public void setSingletonClustering(SingletonClusteringBean param0) throws InvalidAttributeValueException {
      AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
      if (this._setParent(_child, this, 1)) {
         this._postCreate(_child);
      }

      SingletonClusteringBean _oldVal = this._SingletonClustering;
      this._SingletonClustering = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(2);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(2, _oldVal, param0);
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
      return super._isAnythingSet() || this.isSingletonClusteringSet() || this.isTimerDescriptorSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 2;
      }

      try {
         switch (idx) {
            case 2:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._SingletonClustering = new SingletonClusteringBeanImpl(this, 1);
               this._postCreate((AbstractDescriptorBean)this._SingletonClustering);
               if (initOne) {
                  break;
               }
            case 0:
               this._TimerDescriptor = new TimerDescriptorBeanImpl(this, 0);
               this._postCreate((AbstractDescriptorBean)this._TimerDescriptor);
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
                  return 2;
               }
               break;
            case 16:
               if (s.equals("timer-descriptor")) {
                  return 0;
               }
               break;
            case 20:
               if (s.equals("singleton-clustering")) {
                  return 1;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new TimerDescriptorBeanImpl.SchemaHelper2();
            case 1:
               return new SingletonClusteringBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "timer-descriptor";
            case 1:
               return "singleton-clustering";
            case 2:
               return "id";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private SingletonSessionDescriptorBeanImpl bean;

      protected Helper(SingletonSessionDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "TimerDescriptor";
            case 1:
               return "SingletonClustering";
            case 2:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Id")) {
            return 2;
         } else if (propName.equals("SingletonClustering")) {
            return 1;
         } else {
            return propName.equals("TimerDescriptor") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getSingletonClustering() != null) {
            iterators.add(new ArrayIterator(new SingletonClusteringBean[]{this.bean.getSingletonClustering()}));
         }

         if (this.bean.getTimerDescriptor() != null) {
            iterators.add(new ArrayIterator(new TimerDescriptorBean[]{this.bean.getTimerDescriptor()}));
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
            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            childValue = this.computeChildHashValue(this.bean.getSingletonClustering());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getTimerDescriptor());
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
            SingletonSessionDescriptorBeanImpl otherTyped = (SingletonSessionDescriptorBeanImpl)other;
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeSubDiff("SingletonClustering", this.bean.getSingletonClustering(), otherTyped.getSingletonClustering());
            this.computeSubDiff("TimerDescriptor", this.bean.getTimerDescriptor(), otherTyped.getTimerDescriptor());
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            SingletonSessionDescriptorBeanImpl original = (SingletonSessionDescriptorBeanImpl)event.getSourceBean();
            SingletonSessionDescriptorBeanImpl proposed = (SingletonSessionDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SingletonClustering")) {
                  if (type == 2) {
                     original.setSingletonClustering((SingletonClusteringBean)this.createCopy((AbstractDescriptorBean)proposed.getSingletonClustering()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SingletonClustering", (DescriptorBean)original.getSingletonClustering());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("TimerDescriptor")) {
                  if (type == 2) {
                     original.setTimerDescriptor((TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)proposed.getTimerDescriptor()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("TimerDescriptor", (DescriptorBean)original.getTimerDescriptor());
                  }

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
            SingletonSessionDescriptorBeanImpl copy = (SingletonSessionDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("SingletonClustering")) && this.bean.isSingletonClusteringSet() && !copy._isSet(1)) {
               Object o = this.bean.getSingletonClustering();
               copy.setSingletonClustering((SingletonClusteringBean)null);
               copy.setSingletonClustering(o == null ? null : (SingletonClusteringBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("TimerDescriptor")) && this.bean.isTimerDescriptorSet() && !copy._isSet(0)) {
               Object o = this.bean.getTimerDescriptor();
               copy.setTimerDescriptor((TimerDescriptorBean)null);
               copy.setTimerDescriptor(o == null ? null : (TimerDescriptorBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
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
         this.inferSubTree(this.bean.getSingletonClustering(), clazz, annotation);
         this.inferSubTree(this.bean.getTimerDescriptor(), clazz, annotation);
      }
   }
}
