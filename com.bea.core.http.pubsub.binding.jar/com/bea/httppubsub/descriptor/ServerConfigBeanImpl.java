package com.bea.httppubsub.descriptor;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
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
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ServerConfigBeanImpl extends AbstractDescriptorBean implements ServerConfigBean, Serializable {
   private int _ClientTimeoutSecs;
   private int _ConnectionIdleTimeoutSecs;
   private String _CookiePath;
   private int _IntervalMillisecs;
   private int _MultiFrameIntervalMillisecs;
   private String _Name;
   private int _PersistentClientTimeoutSecs;
   private boolean _PublishWithoutConnectAllowed;
   private SupportedTransportBean _SupportedTransport;
   private String _WorkManager;
   private static SchemaHelper2 _schemaHelper;

   public ServerConfigBeanImpl() {
      this._initializeProperty(-1);
   }

   public ServerConfigBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public ServerConfigBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getName() {
      return this._Name;
   }

   public boolean isNameInherited() {
      return false;
   }

   public boolean isNameSet() {
      return this._isSet(0);
   }

   public void setName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Name;
      this._Name = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getWorkManager() {
      return this._WorkManager;
   }

   public boolean isWorkManagerInherited() {
      return false;
   }

   public boolean isWorkManagerSet() {
      return this._isSet(1);
   }

   public void setWorkManager(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WorkManager;
      this._WorkManager = param0;
      this._postSet(1, _oldVal, param0);
   }

   public boolean isPublishWithoutConnectAllowed() {
      return this._PublishWithoutConnectAllowed;
   }

   public boolean isPublishWithoutConnectAllowedInherited() {
      return false;
   }

   public boolean isPublishWithoutConnectAllowedSet() {
      return this._isSet(2);
   }

   public void setPublishWithoutConnectAllowed(boolean param0) {
      boolean _oldVal = this._PublishWithoutConnectAllowed;
      this._PublishWithoutConnectAllowed = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getConnectionIdleTimeoutSecs() {
      return this._ConnectionIdleTimeoutSecs;
   }

   public boolean isConnectionIdleTimeoutSecsInherited() {
      return false;
   }

   public boolean isConnectionIdleTimeoutSecsSet() {
      return this._isSet(3);
   }

   public void setConnectionIdleTimeoutSecs(int param0) {
      int _oldVal = this._ConnectionIdleTimeoutSecs;
      this._ConnectionIdleTimeoutSecs = param0;
      this._postSet(3, _oldVal, param0);
   }

   public int getClientTimeoutSecs() {
      return this._ClientTimeoutSecs;
   }

   public boolean isClientTimeoutSecsInherited() {
      return false;
   }

   public boolean isClientTimeoutSecsSet() {
      return this._isSet(4);
   }

   public void setClientTimeoutSecs(int param0) {
      int _oldVal = this._ClientTimeoutSecs;
      this._ClientTimeoutSecs = param0;
      this._postSet(4, _oldVal, param0);
   }

   public int getIntervalMillisecs() {
      return this._IntervalMillisecs;
   }

   public boolean isIntervalMillisecsInherited() {
      return false;
   }

   public boolean isIntervalMillisecsSet() {
      return this._isSet(5);
   }

   public void setIntervalMillisecs(int param0) {
      int _oldVal = this._IntervalMillisecs;
      this._IntervalMillisecs = param0;
      this._postSet(5, _oldVal, param0);
   }

   public int getMultiFrameIntervalMillisecs() {
      return this._MultiFrameIntervalMillisecs;
   }

   public boolean isMultiFrameIntervalMillisecsInherited() {
      return false;
   }

   public boolean isMultiFrameIntervalMillisecsSet() {
      return this._isSet(6);
   }

   public void setMultiFrameIntervalMillisecs(int param0) {
      int _oldVal = this._MultiFrameIntervalMillisecs;
      this._MultiFrameIntervalMillisecs = param0;
      this._postSet(6, _oldVal, param0);
   }

   public int getPersistentClientTimeoutSecs() {
      return this._PersistentClientTimeoutSecs;
   }

   public boolean isPersistentClientTimeoutSecsInherited() {
      return false;
   }

   public boolean isPersistentClientTimeoutSecsSet() {
      return this._isSet(7);
   }

   public void setPersistentClientTimeoutSecs(int param0) {
      int _oldVal = this._PersistentClientTimeoutSecs;
      this._PersistentClientTimeoutSecs = param0;
      this._postSet(7, _oldVal, param0);
   }

   public SupportedTransportBean getSupportedTransport() {
      return this._SupportedTransport;
   }

   public boolean isSupportedTransportInherited() {
      return false;
   }

   public boolean isSupportedTransportSet() {
      return this._isSet(8);
   }

   public void setSupportedTransport(SupportedTransportBean param0) throws InvalidAttributeValueException {
      if (param0 != null && this.getSupportedTransport() != null && param0 != this.getSupportedTransport()) {
         throw new BeanAlreadyExistsException(this.getSupportedTransport() + " has already been created");
      } else {
         if (param0 != null) {
            AbstractDescriptorBean _child = (AbstractDescriptorBean)param0;
            if (this._setParent(_child, this, 8)) {
               this._getReferenceManager().registerBean(_child, false);
               this._postCreate(_child);
            }
         }

         SupportedTransportBean _oldVal = this._SupportedTransport;
         this._SupportedTransport = param0;
         this._postSet(8, _oldVal, param0);
      }
   }

   public SupportedTransportBean createSupportedTransport() {
      SupportedTransportBeanImpl _val = new SupportedTransportBeanImpl(this, -1);

      try {
         this.setSupportedTransport(_val);
         return _val;
      } catch (Exception var3) {
         if (var3 instanceof RuntimeException) {
            throw (RuntimeException)var3;
         } else {
            throw new UndeclaredThrowableException(var3);
         }
      }
   }

   public String getCookiePath() {
      return this._CookiePath;
   }

   public boolean isCookiePathInherited() {
      return false;
   }

   public boolean isCookiePathSet() {
      return this._isSet(9);
   }

   public void setCookiePath(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CookiePath;
      this._CookiePath = param0;
      this._postSet(9, _oldVal, param0);
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
               this._ClientTimeoutSecs = 60;
               if (initOne) {
                  break;
               }
            case 3:
               this._ConnectionIdleTimeoutSecs = 40;
               if (initOne) {
                  break;
               }
            case 9:
               this._CookiePath = "/";
               if (initOne) {
                  break;
               }
            case 5:
               this._IntervalMillisecs = 500;
               if (initOne) {
                  break;
               }
            case 6:
               this._MultiFrameIntervalMillisecs = 3000;
               if (initOne) {
                  break;
               }
            case 0:
               this._Name = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._PersistentClientTimeoutSecs = 600;
               if (initOne) {
                  break;
               }
            case 8:
               this._SupportedTransport = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._WorkManager = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._PublishWithoutConnectAllowed = false;
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
            case 4:
               if (s.equals("name")) {
                  return 0;
               }
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
            case 14:
            case 15:
            case 16:
            case 17:
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 29:
            default:
               break;
            case 11:
               if (s.equals("cookie-path")) {
                  return 9;
               }
               break;
            case 12:
               if (s.equals("work-manager")) {
                  return 1;
               }
               break;
            case 18:
               if (s.equals("interval-millisecs")) {
                  return 5;
               }
               break;
            case 19:
               if (s.equals("client-timeout-secs")) {
                  return 4;
               }

               if (s.equals("supported-transport")) {
                  return 8;
               }
               break;
            case 28:
               if (s.equals("connection-idle-timeout-secs")) {
                  return 3;
               }
               break;
            case 30:
               if (s.equals("multi-frame-interval-millisecs")) {
                  return 6;
               }

               if (s.equals("persistent-client-timeout-secs")) {
                  return 7;
               }
               break;
            case 31:
               if (s.equals("publish-without-connect-allowed")) {
                  return 2;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 8:
               return new SupportedTransportBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "name";
            case 1:
               return "work-manager";
            case 2:
               return "publish-without-connect-allowed";
            case 3:
               return "connection-idle-timeout-secs";
            case 4:
               return "client-timeout-secs";
            case 5:
               return "interval-millisecs";
            case 6:
               return "multi-frame-interval-millisecs";
            case 7:
               return "persistent-client-timeout-secs";
            case 8:
               return "supported-transport";
            case 9:
               return "cookie-path";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 8:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private ServerConfigBeanImpl bean;

      protected Helper(ServerConfigBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Name";
            case 1:
               return "WorkManager";
            case 2:
               return "PublishWithoutConnectAllowed";
            case 3:
               return "ConnectionIdleTimeoutSecs";
            case 4:
               return "ClientTimeoutSecs";
            case 5:
               return "IntervalMillisecs";
            case 6:
               return "MultiFrameIntervalMillisecs";
            case 7:
               return "PersistentClientTimeoutSecs";
            case 8:
               return "SupportedTransport";
            case 9:
               return "CookiePath";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("ClientTimeoutSecs")) {
            return 4;
         } else if (propName.equals("ConnectionIdleTimeoutSecs")) {
            return 3;
         } else if (propName.equals("CookiePath")) {
            return 9;
         } else if (propName.equals("IntervalMillisecs")) {
            return 5;
         } else if (propName.equals("MultiFrameIntervalMillisecs")) {
            return 6;
         } else if (propName.equals("Name")) {
            return 0;
         } else if (propName.equals("PersistentClientTimeoutSecs")) {
            return 7;
         } else if (propName.equals("SupportedTransport")) {
            return 8;
         } else if (propName.equals("WorkManager")) {
            return 1;
         } else {
            return propName.equals("PublishWithoutConnectAllowed") ? 2 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getSupportedTransport() != null) {
            iterators.add(new ArrayIterator(new SupportedTransportBean[]{this.bean.getSupportedTransport()}));
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
            if (this.bean.isClientTimeoutSecsSet()) {
               buf.append("ClientTimeoutSecs");
               buf.append(String.valueOf(this.bean.getClientTimeoutSecs()));
            }

            if (this.bean.isConnectionIdleTimeoutSecsSet()) {
               buf.append("ConnectionIdleTimeoutSecs");
               buf.append(String.valueOf(this.bean.getConnectionIdleTimeoutSecs()));
            }

            if (this.bean.isCookiePathSet()) {
               buf.append("CookiePath");
               buf.append(String.valueOf(this.bean.getCookiePath()));
            }

            if (this.bean.isIntervalMillisecsSet()) {
               buf.append("IntervalMillisecs");
               buf.append(String.valueOf(this.bean.getIntervalMillisecs()));
            }

            if (this.bean.isMultiFrameIntervalMillisecsSet()) {
               buf.append("MultiFrameIntervalMillisecs");
               buf.append(String.valueOf(this.bean.getMultiFrameIntervalMillisecs()));
            }

            if (this.bean.isNameSet()) {
               buf.append("Name");
               buf.append(String.valueOf(this.bean.getName()));
            }

            if (this.bean.isPersistentClientTimeoutSecsSet()) {
               buf.append("PersistentClientTimeoutSecs");
               buf.append(String.valueOf(this.bean.getPersistentClientTimeoutSecs()));
            }

            childValue = this.computeChildHashValue(this.bean.getSupportedTransport());
            if (childValue != 0L) {
               buf.append(String.valueOf(childValue));
            }

            if (this.bean.isWorkManagerSet()) {
               buf.append("WorkManager");
               buf.append(String.valueOf(this.bean.getWorkManager()));
            }

            if (this.bean.isPublishWithoutConnectAllowedSet()) {
               buf.append("PublishWithoutConnectAllowed");
               buf.append(String.valueOf(this.bean.isPublishWithoutConnectAllowed()));
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
            ServerConfigBeanImpl otherTyped = (ServerConfigBeanImpl)other;
            this.computeDiff("ClientTimeoutSecs", this.bean.getClientTimeoutSecs(), otherTyped.getClientTimeoutSecs(), false);
            this.computeDiff("ConnectionIdleTimeoutSecs", this.bean.getConnectionIdleTimeoutSecs(), otherTyped.getConnectionIdleTimeoutSecs(), false);
            this.computeDiff("CookiePath", this.bean.getCookiePath(), otherTyped.getCookiePath(), false);
            this.computeDiff("IntervalMillisecs", this.bean.getIntervalMillisecs(), otherTyped.getIntervalMillisecs(), false);
            this.computeDiff("MultiFrameIntervalMillisecs", this.bean.getMultiFrameIntervalMillisecs(), otherTyped.getMultiFrameIntervalMillisecs(), false);
            this.computeDiff("Name", this.bean.getName(), otherTyped.getName(), false);
            this.computeDiff("PersistentClientTimeoutSecs", this.bean.getPersistentClientTimeoutSecs(), otherTyped.getPersistentClientTimeoutSecs(), false);
            this.computeChildDiff("SupportedTransport", this.bean.getSupportedTransport(), otherTyped.getSupportedTransport(), false);
            this.computeDiff("WorkManager", this.bean.getWorkManager(), otherTyped.getWorkManager(), false);
            this.computeDiff("PublishWithoutConnectAllowed", this.bean.isPublishWithoutConnectAllowed(), otherTyped.isPublishWithoutConnectAllowed(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServerConfigBeanImpl original = (ServerConfigBeanImpl)event.getSourceBean();
            ServerConfigBeanImpl proposed = (ServerConfigBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ClientTimeoutSecs")) {
                  original.setClientTimeoutSecs(proposed.getClientTimeoutSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("ConnectionIdleTimeoutSecs")) {
                  original.setConnectionIdleTimeoutSecs(proposed.getConnectionIdleTimeoutSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("CookiePath")) {
                  original.setCookiePath(proposed.getCookiePath());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("IntervalMillisecs")) {
                  original.setIntervalMillisecs(proposed.getIntervalMillisecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("MultiFrameIntervalMillisecs")) {
                  original.setMultiFrameIntervalMillisecs(proposed.getMultiFrameIntervalMillisecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else if (prop.equals("Name")) {
                  original.setName(proposed.getName());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("PersistentClientTimeoutSecs")) {
                  original.setPersistentClientTimeoutSecs(proposed.getPersistentClientTimeoutSecs());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("SupportedTransport")) {
                  if (type == 2) {
                     original.setSupportedTransport((SupportedTransportBean)this.createCopy((AbstractDescriptorBean)proposed.getSupportedTransport()));
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original._destroySingleton("SupportedTransport", (DescriptorBean)original.getSupportedTransport());
                  }

                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("WorkManager")) {
                  original.setWorkManager(proposed.getWorkManager());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("PublishWithoutConnectAllowed")) {
                  original.setPublishWithoutConnectAllowed(proposed.isPublishWithoutConnectAllowed());
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
            ServerConfigBeanImpl copy = (ServerConfigBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ClientTimeoutSecs")) && this.bean.isClientTimeoutSecsSet()) {
               copy.setClientTimeoutSecs(this.bean.getClientTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("ConnectionIdleTimeoutSecs")) && this.bean.isConnectionIdleTimeoutSecsSet()) {
               copy.setConnectionIdleTimeoutSecs(this.bean.getConnectionIdleTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("CookiePath")) && this.bean.isCookiePathSet()) {
               copy.setCookiePath(this.bean.getCookiePath());
            }

            if ((excludeProps == null || !excludeProps.contains("IntervalMillisecs")) && this.bean.isIntervalMillisecsSet()) {
               copy.setIntervalMillisecs(this.bean.getIntervalMillisecs());
            }

            if ((excludeProps == null || !excludeProps.contains("MultiFrameIntervalMillisecs")) && this.bean.isMultiFrameIntervalMillisecsSet()) {
               copy.setMultiFrameIntervalMillisecs(this.bean.getMultiFrameIntervalMillisecs());
            }

            if ((excludeProps == null || !excludeProps.contains("Name")) && this.bean.isNameSet()) {
               copy.setName(this.bean.getName());
            }

            if ((excludeProps == null || !excludeProps.contains("PersistentClientTimeoutSecs")) && this.bean.isPersistentClientTimeoutSecsSet()) {
               copy.setPersistentClientTimeoutSecs(this.bean.getPersistentClientTimeoutSecs());
            }

            if ((excludeProps == null || !excludeProps.contains("SupportedTransport")) && this.bean.isSupportedTransportSet() && !copy._isSet(8)) {
               Object o = this.bean.getSupportedTransport();
               copy.setSupportedTransport((SupportedTransportBean)null);
               copy.setSupportedTransport(o == null ? null : (SupportedTransportBean)this.createCopy((AbstractDescriptorBean)o, includeObsolete));
            }

            if ((excludeProps == null || !excludeProps.contains("WorkManager")) && this.bean.isWorkManagerSet()) {
               copy.setWorkManager(this.bean.getWorkManager());
            }

            if ((excludeProps == null || !excludeProps.contains("PublishWithoutConnectAllowed")) && this.bean.isPublishWithoutConnectAllowedSet()) {
               copy.setPublishWithoutConnectAllowed(this.bean.isPublishWithoutConnectAllowed());
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
         this.inferSubTree(this.bean.getSupportedTransport(), clazz, annotation);
      }
   }
}
