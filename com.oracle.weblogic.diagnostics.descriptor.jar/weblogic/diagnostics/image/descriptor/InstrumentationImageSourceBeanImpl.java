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

public class InstrumentationImageSourceBeanImpl extends AbstractDescriptorBean implements InstrumentationImageSourceBean, Serializable {
   private InstrumentationEventBean[] _InstrumentationEvents;
   private static SchemaHelper2 _schemaHelper;

   public InstrumentationImageSourceBeanImpl() {
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public InstrumentationImageSourceBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public InstrumentationImageSourceBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeRootBean(this.getDescriptor());
      this._initializeProperty(-1);
   }

   public void addInstrumentationEvent(InstrumentationEventBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 0)) {
         InstrumentationEventBean[] _new;
         if (this._isSet(0)) {
            _new = (InstrumentationEventBean[])((InstrumentationEventBean[])this._getHelper()._extendArray(this.getInstrumentationEvents(), InstrumentationEventBean.class, param0));
         } else {
            _new = new InstrumentationEventBean[]{param0};
         }

         try {
            this.setInstrumentationEvents(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public InstrumentationEventBean[] getInstrumentationEvents() {
      return this._InstrumentationEvents;
   }

   public boolean isInstrumentationEventsInherited() {
      return false;
   }

   public boolean isInstrumentationEventsSet() {
      return this._isSet(0);
   }

   public void removeInstrumentationEvent(InstrumentationEventBean param0) {
      InstrumentationEventBean[] _old = this.getInstrumentationEvents();
      InstrumentationEventBean[] _new = (InstrumentationEventBean[])((InstrumentationEventBean[])this._getHelper()._removeElement(_old, InstrumentationEventBean.class, param0));
      if (_new.length != _old.length) {
         this._preDestroy((AbstractDescriptorBean)param0);

         try {
            this._getReferenceManager().unregisterBean((AbstractDescriptorBean)param0);
            this.setInstrumentationEvents(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setInstrumentationEvents(InstrumentationEventBean[] param0) throws InvalidAttributeValueException {
      InstrumentationEventBean[] param0 = param0 == null ? new InstrumentationEventBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 0)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      InstrumentationEventBean[] _oldVal = this._InstrumentationEvents;
      this._InstrumentationEvents = (InstrumentationEventBean[])param0;
      this._postSet(0, _oldVal, param0);
   }

   public InstrumentationEventBean createInstrumentationEvent() {
      InstrumentationEventBeanImpl _val = new InstrumentationEventBeanImpl(this, -1);

      try {
         this.addInstrumentationEvent(_val);
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
               this._InstrumentationEvents = new InstrumentationEventBean[0];
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
            case 21:
               if (s.equals("instrumentation-event")) {
                  return 0;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new InstrumentationEventBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getRootElementName() {
         return "instrumentation-image-source";
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "instrumentation-event";
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
      private InstrumentationImageSourceBeanImpl bean;

      protected Helper(InstrumentationImageSourceBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "InstrumentationEvents";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("InstrumentationEvents") ? 0 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getInstrumentationEvents()));
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

            for(int i = 0; i < this.bean.getInstrumentationEvents().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getInstrumentationEvents()[i]);
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
            InstrumentationImageSourceBeanImpl otherTyped = (InstrumentationImageSourceBeanImpl)other;
            this.computeChildDiff("InstrumentationEvents", this.bean.getInstrumentationEvents(), otherTyped.getInstrumentationEvents(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            InstrumentationImageSourceBeanImpl original = (InstrumentationImageSourceBeanImpl)event.getSourceBean();
            InstrumentationImageSourceBeanImpl proposed = (InstrumentationImageSourceBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("InstrumentationEvents")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addInstrumentationEvent((InstrumentationEventBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeInstrumentationEvent((InstrumentationEventBean)update.getRemovedObject());
                  }

                  if (original.getInstrumentationEvents() == null || original.getInstrumentationEvents().length == 0) {
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
            InstrumentationImageSourceBeanImpl copy = (InstrumentationImageSourceBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("InstrumentationEvents")) && this.bean.isInstrumentationEventsSet() && !copy._isSet(0)) {
               InstrumentationEventBean[] oldInstrumentationEvents = this.bean.getInstrumentationEvents();
               InstrumentationEventBean[] newInstrumentationEvents = new InstrumentationEventBean[oldInstrumentationEvents.length];

               for(int i = 0; i < newInstrumentationEvents.length; ++i) {
                  newInstrumentationEvents[i] = (InstrumentationEventBean)((InstrumentationEventBean)this.createCopy((AbstractDescriptorBean)oldInstrumentationEvents[i], includeObsolete));
               }

               copy.setInstrumentationEvents(newInstrumentationEvents);
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
         this.inferSubTree(this.bean.getInstrumentationEvents(), clazz, annotation);
      }
   }
}
