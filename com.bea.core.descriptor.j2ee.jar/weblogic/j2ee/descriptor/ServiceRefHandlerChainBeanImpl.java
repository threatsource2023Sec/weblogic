package weblogic.j2ee.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanRemoveRejectedException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ServiceRefHandlerChainBeanImpl extends AbstractDescriptorBean implements ServiceRefHandlerChainBean, Serializable {
   private ServiceRefHandlerBean[] _Handlers;
   private String _Id;
   private String _PortNamePattern;
   private String _ProtocolBindings;
   private String _ServiceNamePattern;
   private static SchemaHelper2 _schemaHelper;

   public ServiceRefHandlerChainBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServiceRefHandlerChainBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServiceRefHandlerChainBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getServiceNamePattern() {
      return this._ServiceNamePattern;
   }

   public boolean isServiceNamePatternInherited() {
      return false;
   }

   public boolean isServiceNamePatternSet() {
      return this._isSet(0);
   }

   public void setServiceNamePattern(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServiceNamePattern;
      this._ServiceNamePattern = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPortNamePattern() {
      return this._PortNamePattern;
   }

   public boolean isPortNamePatternInherited() {
      return false;
   }

   public boolean isPortNamePatternSet() {
      return this._isSet(1);
   }

   public void setPortNamePattern(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PortNamePattern;
      this._PortNamePattern = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getProtocolBindings() {
      return this._ProtocolBindings;
   }

   public boolean isProtocolBindingsInherited() {
      return false;
   }

   public boolean isProtocolBindingsSet() {
      return this._isSet(2);
   }

   public void setProtocolBindings(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ProtocolBindings;
      this._ProtocolBindings = param0;
      this._postSet(2, _oldVal, param0);
   }

   public void addHandler(ServiceRefHandlerBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 3)) {
         ServiceRefHandlerBean[] _new;
         if (this._isSet(3)) {
            _new = (ServiceRefHandlerBean[])((ServiceRefHandlerBean[])this._getHelper()._extendArray(this.getHandlers(), ServiceRefHandlerBean.class, param0));
         } else {
            _new = new ServiceRefHandlerBean[]{param0};
         }

         try {
            this.setHandlers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public ServiceRefHandlerBean[] getHandlers() {
      return this._Handlers;
   }

   public boolean isHandlersInherited() {
      return false;
   }

   public boolean isHandlersSet() {
      return this._isSet(3);
   }

   public void removeHandler(ServiceRefHandlerBean param0) {
      this.destroyHandler(param0);
   }

   public void setHandlers(ServiceRefHandlerBean[] param0) throws InvalidAttributeValueException {
      ServiceRefHandlerBean[] param0 = param0 == null ? new ServiceRefHandlerBeanImpl[0] : param0;

      for(int i = 0; i < ((Object[])param0).length; ++i) {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)((Object[])param0)[i];
         if (this._setParent(_child, this, 3)) {
            this._getReferenceManager().registerBean(_child, false);
            this._postCreate(_child);
         }
      }

      ServiceRefHandlerBean[] _oldVal = this._Handlers;
      this._Handlers = (ServiceRefHandlerBean[])param0;
      this._postSet(3, _oldVal, param0);
   }

   public ServiceRefHandlerBean createHandler() {
      ServiceRefHandlerBeanImpl _val = new ServiceRefHandlerBeanImpl(this, -1);

      try {
         this.addHandler(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyHandler(ServiceRefHandlerBean param0) {
      try {
         this._checkIsPotentialChild(param0, 3);
         ServiceRefHandlerBean[] _old = this.getHandlers();
         ServiceRefHandlerBean[] _new = (ServiceRefHandlerBean[])((ServiceRefHandlerBean[])this._getHelper()._removeElement(_old, ServiceRefHandlerBean.class, param0));
         if (_old.length != _new.length) {
            this._preDestroy((AbstractDescriptorBean)param0);

            try {
               AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
               if (_child == null) {
                  return;
               }

               List _refs = this._getReferenceManager().getResolvedReferences(_child);
               if (_refs != null && _refs.size() > 0) {
                  throw new BeanRemoveRejectedException(_child, _refs);
               }

               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setHandlers(_new);
            } catch (Exception var6) {
               if (var6 instanceof RuntimeException) {
                  throw (RuntimeException)var6;
               }

               throw new UndeclaredThrowableException(var6);
            }
         }

      } catch (Exception var7) {
         if (var7 instanceof RuntimeException) {
            throw (RuntimeException)var7;
         } else {
            throw new UndeclaredThrowableException(var7);
         }
      }
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(4);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
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
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 3;
      }

      try {
         switch (idx) {
            case 3:
               this._Handlers = new ServiceRefHandlerBean[0];
               if (initOne) {
                  break;
               }
            case 4:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._PortNamePattern = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._ProtocolBindings = null;
               if (initOne) {
                  break;
               }
            case 0:
               this._ServiceNamePattern = null;
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
                  return 4;
               }
               break;
            case 7:
               if (s.equals("handler")) {
                  return 3;
               }
               break;
            case 17:
               if (s.equals("port-name-pattern")) {
                  return 1;
               }

               if (s.equals("protocol-bindings")) {
                  return 2;
               }
               break;
            case 20:
               if (s.equals("service-name-pattern")) {
                  return 0;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 3:
               return new ServiceRefHandlerBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "service-name-pattern";
            case 1:
               return "port-name-pattern";
            case 2:
               return "protocol-bindings";
            case 3:
               return "handler";
            case 4:
               return "id";
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
            case 3:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServiceRefHandlerChainBeanImpl bean;

      protected Helper(ServiceRefHandlerChainBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "ServiceNamePattern";
            case 1:
               return "PortNamePattern";
            case 2:
               return "ProtocolBindings";
            case 3:
               return "Handlers";
            case 4:
               return "Id";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("Handlers")) {
            return 3;
         } else if (propName.equals("Id")) {
            return 4;
         } else if (propName.equals("PortNamePattern")) {
            return 1;
         } else if (propName.equals("ProtocolBindings")) {
            return 2;
         } else {
            return propName.equals("ServiceNamePattern") ? 0 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         iterators.add(new ArrayIterator(this.bean.getHandlers()));
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

            for(int i = 0; i < this.bean.getHandlers().length; ++i) {
               childValue ^= this.computeChildHashValue(this.bean.getHandlers()[i]);
            }

            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isPortNamePatternSet()) {
               buf.append("PortNamePattern");
               buf.append(String.valueOf(this.bean.getPortNamePattern()));
            }

            if (this.bean.isProtocolBindingsSet()) {
               buf.append("ProtocolBindings");
               buf.append(String.valueOf(this.bean.getProtocolBindings()));
            }

            if (this.bean.isServiceNamePatternSet()) {
               buf.append("ServiceNamePattern");
               buf.append(String.valueOf(this.bean.getServiceNamePattern()));
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
            ServiceRefHandlerChainBeanImpl otherTyped = (ServiceRefHandlerChainBeanImpl)other;
            this.computeChildDiff("Handlers", this.bean.getHandlers(), otherTyped.getHandlers(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("PortNamePattern", this.bean.getPortNamePattern(), otherTyped.getPortNamePattern(), false);
            this.computeDiff("ProtocolBindings", this.bean.getProtocolBindings(), otherTyped.getProtocolBindings(), false);
            this.computeDiff("ServiceNamePattern", this.bean.getServiceNamePattern(), otherTyped.getServiceNamePattern(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServiceRefHandlerChainBeanImpl original = (ServiceRefHandlerChainBeanImpl)event.getSourceBean();
            ServiceRefHandlerChainBeanImpl proposed = (ServiceRefHandlerChainBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("Handlers")) {
                  if (type == 2) {
                     if (!((AbstractDescriptorBean)update.getAddedObject())._isSynthetic()) {
                        update.resetAddedObject(this.createCopy((AbstractDescriptorBean)update.getAddedObject()));
                        original.addHandler((ServiceRefHandlerBean)update.getAddedObject());
                     }
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeHandler((ServiceRefHandlerBean)update.getRemovedObject());
                  }

                  if (original.getHandlers() == null || original.getHandlers().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 3);
                  }
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("PortNamePattern")) {
                  original.setPortNamePattern(proposed.getPortNamePattern());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("ProtocolBindings")) {
                  original.setProtocolBindings(proposed.getProtocolBindings());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("ServiceNamePattern")) {
                  original.setServiceNamePattern(proposed.getServiceNamePattern());
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
            ServiceRefHandlerChainBeanImpl copy = (ServiceRefHandlerChainBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("Handlers")) && this.bean.isHandlersSet() && !copy._isSet(3)) {
               ServiceRefHandlerBean[] oldHandlers = this.bean.getHandlers();
               ServiceRefHandlerBean[] newHandlers = new ServiceRefHandlerBean[oldHandlers.length];

               for(int i = 0; i < newHandlers.length; ++i) {
                  newHandlers[i] = (ServiceRefHandlerBean)((ServiceRefHandlerBean)this.createCopy((AbstractDescriptorBean)oldHandlers[i], includeObsolete));
               }

               copy.setHandlers(newHandlers);
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PortNamePattern")) && this.bean.isPortNamePatternSet()) {
               copy.setPortNamePattern(this.bean.getPortNamePattern());
            }

            if ((excludeProps == null || !excludeProps.contains("ProtocolBindings")) && this.bean.isProtocolBindingsSet()) {
               copy.setProtocolBindings(this.bean.getProtocolBindings());
            }

            if ((excludeProps == null || !excludeProps.contains("ServiceNamePattern")) && this.bean.isServiceNamePatternSet()) {
               copy.setServiceNamePattern(this.bean.getServiceNamePattern());
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
         this.inferSubTree(this.bean.getHandlers(), clazz, annotation);
      }
   }
}
