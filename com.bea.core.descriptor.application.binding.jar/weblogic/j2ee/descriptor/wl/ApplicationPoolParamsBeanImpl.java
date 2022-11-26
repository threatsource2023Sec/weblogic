package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanAlreadyExistsException;
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

public class ApplicationPoolParamsBeanImpl extends AbstractDescriptorBean implements ApplicationPoolParamsBean, Serializable {
   private ConnectionCheckParamsBean _ConnectionCheckParams;
   private int _JDBCXADebugLevel;
   private boolean _LeakProfilingEnabled;
   private int _LoginDelaySeconds;
   private boolean _RemoveInfectedConnectionsEnabled;
   private SizeParamsBean _SizeParams;
   private XAParamsBean _XAParams;
   private static SchemaHelper2 _schemaHelper;

   public ApplicationPoolParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public ApplicationPoolParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ApplicationPoolParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public SizeParamsBean getSizeParams() {
      return this._SizeParams;
   }

   public boolean isSizeParamsInherited() {
      return false;
   }

   public boolean isSizeParamsSet() {
      return this._isSet(0);
   }

   public void setSizeParams(SizeParamsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSizeParams() != null && param0 != this.getSizeParams()) {
         throw new BeanAlreadyExistsException(this.getSizeParams() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 0)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SizeParamsBean _oldVal = this._SizeParams;
         this._SizeParams = param0;
         this._postSet(0, _oldVal, param0);
      }
   }

   public SizeParamsBean createSizeParams() {
      SizeParamsBeanImpl _val = new SizeParamsBeanImpl(this, -1);

      try {
         this.setSizeParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroySizeParams(SizeParamsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._SizeParams;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setSizeParams((SizeParamsBean)null);
               this._unSet(0);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public XAParamsBean getXAParams() {
      return this._XAParams;
   }

   public boolean isXAParamsInherited() {
      return false;
   }

   public boolean isXAParamsSet() {
      return this._isSet(1);
   }

   public void setXAParams(XAParamsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getXAParams() != null && param0 != this.getXAParams()) {
         throw new BeanAlreadyExistsException(this.getXAParams() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 1)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         XAParamsBean _oldVal = this._XAParams;
         this._XAParams = param0;
         this._postSet(1, _oldVal, param0);
      }
   }

   public XAParamsBean createXAParams() {
      XAParamsBeanImpl _val = new XAParamsBeanImpl(this, -1);

      try {
         this.setXAParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyXAParams(XAParamsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._XAParams;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setXAParams((XAParamsBean)null);
               this._unSet(1);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public int getLoginDelaySeconds() {
      return this._LoginDelaySeconds;
   }

   public boolean isLoginDelaySecondsInherited() {
      return false;
   }

   public boolean isLoginDelaySecondsSet() {
      return this._isSet(2);
   }

   public void setLoginDelaySeconds(int param0) {
      int _oldVal = this._LoginDelaySeconds;
      this._LoginDelaySeconds = param0;
      this._postSet(2, _oldVal, param0);
   }

   public boolean isLeakProfilingEnabled() {
      return this._LeakProfilingEnabled;
   }

   public boolean isLeakProfilingEnabledInherited() {
      return false;
   }

   public boolean isLeakProfilingEnabledSet() {
      return this._isSet(3);
   }

   public void setLeakProfilingEnabled(boolean param0) {
      boolean _oldVal = this._LeakProfilingEnabled;
      this._LeakProfilingEnabled = param0;
      this._postSet(3, _oldVal, param0);
   }

   public ConnectionCheckParamsBean getConnectionCheckParams() {
      return this._ConnectionCheckParams;
   }

   public boolean isConnectionCheckParamsInherited() {
      return false;
   }

   public boolean isConnectionCheckParamsSet() {
      return this._isSet(4);
   }

   public void setConnectionCheckParams(ConnectionCheckParamsBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getConnectionCheckParams() != null && param0 != this.getConnectionCheckParams()) {
         throw new BeanAlreadyExistsException(this.getConnectionCheckParams() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 4)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         ConnectionCheckParamsBean _oldVal = this._ConnectionCheckParams;
         this._ConnectionCheckParams = param0;
         this._postSet(4, _oldVal, param0);
      }
   }

   public ConnectionCheckParamsBean createConnectionCheckParams() {
      ConnectionCheckParamsBeanImpl _val = new ConnectionCheckParamsBeanImpl(this, -1);

      try {
         this.setConnectionCheckParams(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public void destroyConnectionCheckParams(ConnectionCheckParamsBean param0) {
      try {
         AbstractDescriptorBean _child = (AbstractDescriptorBean)this._ConnectionCheckParams;
         if (_child != null) {
            List _refs = this._getReferenceManager().getResolvedReferences(_child);
            if (_refs != null && _refs.size() > 0) {
               throw new BeanRemoveRejectedException(_child, _refs);
            } else {
               this._getReferenceManager().unregisterBean(_child);
               this._markDestroyed(_child);
               this.setConnectionCheckParams((ConnectionCheckParamsBean)null);
               this._unSet(4);
            }
         }
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public int getJDBCXADebugLevel() {
      return this._JDBCXADebugLevel;
   }

   public boolean isJDBCXADebugLevelInherited() {
      return false;
   }

   public boolean isJDBCXADebugLevelSet() {
      return this._isSet(5);
   }

   public void setJDBCXADebugLevel(int param0) {
      int _oldVal = this._JDBCXADebugLevel;
      this._JDBCXADebugLevel = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isRemoveInfectedConnectionsEnabled() {
      return this._RemoveInfectedConnectionsEnabled;
   }

   public boolean isRemoveInfectedConnectionsEnabledInherited() {
      return false;
   }

   public boolean isRemoveInfectedConnectionsEnabledSet() {
      return this._isSet(6);
   }

   public void setRemoveInfectedConnectionsEnabled(boolean param0) {
      boolean _oldVal = this._RemoveInfectedConnectionsEnabled;
      this._RemoveInfectedConnectionsEnabled = param0;
      this._postSet(6, _oldVal, param0);
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
         idx = 4;
      }

      try {
         switch (idx) {
            case 4:
               this._ConnectionCheckParams = null;
               if (initOne) {
                  break;
               }
            case 5:
               this._JDBCXADebugLevel = 0;
               if (initOne) {
                  break;
               }
            case 2:
               this._LoginDelaySeconds = 0;
               if (initOne) {
                  break;
               }
            case 0:
               this._SizeParams = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._XAParams = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._LeakProfilingEnabled = false;
               if (initOne) {
                  break;
               }
            case 6:
               this._RemoveInfectedConnectionsEnabled = false;
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
            case 9:
               if (s.equals("xa-params")) {
                  return 1;
               }
               break;
            case 11:
               if (s.equals("size-params")) {
                  return 0;
               }
               break;
            case 18:
               if (s.equals("jdbcxa-debug-level")) {
                  return 5;
               }
               break;
            case 19:
               if (s.equals("login-delay-seconds")) {
                  return 2;
               }
               break;
            case 22:
               if (s.equals("leak-profiling-enabled")) {
                  return 3;
               }
               break;
            case 23:
               if (s.equals("connection-check-params")) {
                  return 4;
               }
               break;
            case 35:
               if (s.equals("remove-infected-connections-enabled")) {
                  return 6;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 0:
               return new SizeParamsBeanImpl.SchemaHelper2();
            case 1:
               return new XAParamsBeanImpl.SchemaHelper2();
            case 2:
            case 3:
            default:
               return super.getSchemaHelper(propIndex);
            case 4:
               return new ConnectionCheckParamsBeanImpl.SchemaHelper2();
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "size-params";
            case 1:
               return "xa-params";
            case 2:
               return "login-delay-seconds";
            case 3:
               return "leak-profiling-enabled";
            case 4:
               return "connection-check-params";
            case 5:
               return "jdbcxa-debug-level";
            case 6:
               return "remove-infected-connections-enabled";
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
            case 2:
            case 3:
            default:
               return super.isBean(propIndex);
            case 4:
               return true;
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 2:
               return true;
            case 5:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ApplicationPoolParamsBeanImpl bean;

      protected Helper(ApplicationPoolParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "SizeParams";
            case 1:
               return "XAParams";
            case 2:
               return "LoginDelaySeconds";
            case 3:
               return "LeakProfilingEnabled";
            case 4:
               return "ConnectionCheckParams";
            case 5:
               return "JDBCXADebugLevel";
            case 6:
               return "RemoveInfectedConnectionsEnabled";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ConnectionCheckParams")) {
            return 4;
         } else if (propName.equals("JDBCXADebugLevel")) {
            return 5;
         } else if (propName.equals("LoginDelaySeconds")) {
            return 2;
         } else if (propName.equals("SizeParams")) {
            return 0;
         } else if (propName.equals("XAParams")) {
            return 1;
         } else if (propName.equals("LeakProfilingEnabled")) {
            return 3;
         } else {
            return propName.equals("RemoveInfectedConnectionsEnabled") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getConnectionCheckParams() != null) {
            iterators.add(new ArrayIterator(new ConnectionCheckParamsBean[]{this.bean.getConnectionCheckParams()}));
         }

         if (this.bean.getSizeParams() != null) {
            iterators.add(new ArrayIterator(new SizeParamsBean[]{this.bean.getSizeParams()}));
         }

         if (this.bean.getXAParams() != null) {
            iterators.add(new ArrayIterator(new XAParamsBean[]{this.bean.getXAParams()}));
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
            childValue = this.computeChildHashValue(this.bean.getConnectionCheckParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isJDBCXADebugLevelSet()) {
               buf.append("JDBCXADebugLevel");
               buf.append(String.valueOf(this.bean.getJDBCXADebugLevel()));
            }

            if (this.bean.isLoginDelaySecondsSet()) {
               buf.append("LoginDelaySeconds");
               buf.append(String.valueOf(this.bean.getLoginDelaySeconds()));
            }

            childValue = this.computeChildHashValue(this.bean.getSizeParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            childValue = this.computeChildHashValue(this.bean.getXAParams());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isLeakProfilingEnabledSet()) {
               buf.append("LeakProfilingEnabled");
               buf.append(String.valueOf(this.bean.isLeakProfilingEnabled()));
            }

            if (this.bean.isRemoveInfectedConnectionsEnabledSet()) {
               buf.append("RemoveInfectedConnectionsEnabled");
               buf.append(String.valueOf(this.bean.isRemoveInfectedConnectionsEnabled()));
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
            ApplicationPoolParamsBeanImpl otherTyped = (ApplicationPoolParamsBeanImpl)other;
            this.computeChildDiff("ConnectionCheckParams", this.bean.getConnectionCheckParams(), otherTyped.getConnectionCheckParams(), false);
            this.computeDiff("JDBCXADebugLevel", this.bean.getJDBCXADebugLevel(), otherTyped.getJDBCXADebugLevel(), true);
            this.computeDiff("LoginDelaySeconds", this.bean.getLoginDelaySeconds(), otherTyped.getLoginDelaySeconds(), true);
            this.computeChildDiff("SizeParams", this.bean.getSizeParams(), otherTyped.getSizeParams(), false);
            this.computeChildDiff("XAParams", this.bean.getXAParams(), otherTyped.getXAParams(), false);
            this.computeDiff("LeakProfilingEnabled", this.bean.isLeakProfilingEnabled(), otherTyped.isLeakProfilingEnabled(), false);
            this.computeDiff("RemoveInfectedConnectionsEnabled", this.bean.isRemoveInfectedConnectionsEnabled(), otherTyped.isRemoveInfectedConnectionsEnabled(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ApplicationPoolParamsBeanImpl original = (ApplicationPoolParamsBeanImpl)event.getSourceBean();
            ApplicationPoolParamsBeanImpl proposed = (ApplicationPoolParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ConnectionCheckParams")) {
                  if (type == 2) {
                     original.setConnectionCheckParams((ConnectionCheckParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getConnectionCheckParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("ConnectionCheckParams", (DescriptorBean)original.getConnectionCheckParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("JDBCXADebugLevel")) {
                  original.setJDBCXADebugLevel(proposed.getJDBCXADebugLevel());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("LoginDelaySeconds")) {
                  original.setLoginDelaySeconds(proposed.getLoginDelaySeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("SizeParams")) {
                  if (type == 2) {
                     original.setSizeParams((SizeParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getSizeParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SizeParams", (DescriptorBean)original.getSizeParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("XAParams")) {
                  if (type == 2) {
                     original.setXAParams((XAParamsBean)this.createCopy((AbstractDescriptorBean)proposed.getXAParams()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("XAParams", (DescriptorBean)original.getXAParams());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("LeakProfilingEnabled")) {
                  original.setLeakProfilingEnabled(proposed.isLeakProfilingEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RemoveInfectedConnectionsEnabled")) {
                  original.setRemoveInfectedConnectionsEnabled(proposed.isRemoveInfectedConnectionsEnabled());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
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
            ApplicationPoolParamsBeanImpl copy = (ApplicationPoolParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ConnectionCheckParams")) && this.bean.isConnectionCheckParamsSet() && !copy._isSet(4)) {
               Object o = this.bean.getConnectionCheckParams();
               copy.setConnectionCheckParams((ConnectionCheckParamsBean)null);
               copy.setConnectionCheckParams(o == null ? null : (ConnectionCheckParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("JDBCXADebugLevel")) && this.bean.isJDBCXADebugLevelSet()) {
               copy.setJDBCXADebugLevel(this.bean.getJDBCXADebugLevel());
            }

            if ((excludeProps == null || !excludeProps.contains("LoginDelaySeconds")) && this.bean.isLoginDelaySecondsSet()) {
               copy.setLoginDelaySeconds(this.bean.getLoginDelaySeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("SizeParams")) && this.bean.isSizeParamsSet() && !copy._isSet(0)) {
               Object o = this.bean.getSizeParams();
               copy.setSizeParams((SizeParamsBean)null);
               copy.setSizeParams(o == null ? null : (SizeParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("XAParams")) && this.bean.isXAParamsSet() && !copy._isSet(1)) {
               Object o = this.bean.getXAParams();
               copy.setXAParams((XAParamsBean)null);
               copy.setXAParams(o == null ? null : (XAParamsBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("LeakProfilingEnabled")) && this.bean.isLeakProfilingEnabledSet()) {
               copy.setLeakProfilingEnabled(this.bean.isLeakProfilingEnabled());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoveInfectedConnectionsEnabled")) && this.bean.isRemoveInfectedConnectionsEnabledSet()) {
               copy.setRemoveInfectedConnectionsEnabled(this.bean.isRemoveInfectedConnectionsEnabled());
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
         this.inferSubTree(this.bean.getConnectionCheckParams(), clazz, annotation);
         this.inferSubTree(this.bean.getSizeParams(), clazz, annotation);
         this.inferSubTree(this.bean.getXAParams(), clazz, annotation);
      }
   }
}
