package weblogic.management.configuration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class HTTPProxyMBeanImpl extends DeploymentMBeanImpl implements HTTPProxyMBean, Serializable {
   private int _HealthCheckInterval;
   private int _InitialConnections;
   private int _MaxConnections;
   private int _MaxHealthCheckInterval;
   private int _MaxRetries;
   private String _ServerList;
   private static SchemaHelper2 _schemaHelper;

   public HTTPProxyMBeanImpl() {
      this._initializeProperty(-1);
   }

   public HTTPProxyMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public HTTPProxyMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public int getInitialConnections() {
      return this._InitialConnections;
   }

   public boolean isInitialConnectionsInherited() {
      return false;
   }

   public boolean isInitialConnectionsSet() {
      return this._isSet(12);
   }

   public void setInitialConnections(int param0) {
      LegalChecks.checkInRange("InitialConnections", (long)param0, 0L, 65535L);
      int _oldVal = this._InitialConnections;
      this._InitialConnections = param0;
      this._postSet(12, _oldVal, param0);
   }

   public int getMaxConnections() {
      return this._MaxConnections;
   }

   public boolean isMaxConnectionsInherited() {
      return false;
   }

   public boolean isMaxConnectionsSet() {
      return this._isSet(13);
   }

   public void setMaxConnections(int param0) {
      LegalChecks.checkInRange("MaxConnections", (long)param0, 1L, 65535L);
      int _oldVal = this._MaxConnections;
      this._MaxConnections = param0;
      this._postSet(13, _oldVal, param0);
   }

   public String getServerList() {
      return this._ServerList;
   }

   public boolean isServerListInherited() {
      return false;
   }

   public boolean isServerListSet() {
      return this._isSet(14);
   }

   public void setServerList(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ServerList;
      this._ServerList = param0;
      this._postSet(14, _oldVal, param0);
   }

   public int getHealthCheckInterval() {
      return this._HealthCheckInterval;
   }

   public boolean isHealthCheckIntervalInherited() {
      return false;
   }

   public boolean isHealthCheckIntervalSet() {
      return this._isSet(15);
   }

   public void setHealthCheckInterval(int param0) {
      LegalChecks.checkInRange("HealthCheckInterval", (long)param0, 1L, 300L);
      int _oldVal = this._HealthCheckInterval;
      this._HealthCheckInterval = param0;
      this._postSet(15, _oldVal, param0);
   }

   public int getMaxRetries() {
      return this._MaxRetries;
   }

   public boolean isMaxRetriesInherited() {
      return false;
   }

   public boolean isMaxRetriesSet() {
      return this._isSet(16);
   }

   public void setMaxRetries(int param0) {
      LegalChecks.checkMax("MaxRetries", param0, 200);
      int _oldVal = this._MaxRetries;
      this._MaxRetries = param0;
      this._postSet(16, _oldVal, param0);
   }

   public int getMaxHealthCheckInterval() {
      return this._MaxHealthCheckInterval;
   }

   public boolean isMaxHealthCheckIntervalInherited() {
      return false;
   }

   public boolean isMaxHealthCheckIntervalSet() {
      return this._isSet(17);
   }

   public void setMaxHealthCheckInterval(int param0) {
      int _oldVal = this._MaxHealthCheckInterval;
      this._MaxHealthCheckInterval = param0;
      this._postSet(17, _oldVal, param0);
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
         idx = 15;
      }

      try {
         switch (idx) {
            case 15:
               this._HealthCheckInterval = 5;
               if (initOne) {
                  break;
               }
            case 12:
               this._InitialConnections = 0;
               if (initOne) {
                  break;
               }
            case 13:
               this._MaxConnections = 100;
               if (initOne) {
                  break;
               }
            case 17:
               this._MaxHealthCheckInterval = 60;
               if (initOne) {
                  break;
               }
            case 16:
               this._MaxRetries = 3;
               if (initOne) {
                  break;
               }
            case 14:
               this._ServerList = null;
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
      return "HTTPProxy";
   }

   public void putValue(String name, Object v) {
      int oldVal;
      if (name.equals("HealthCheckInterval")) {
         oldVal = this._HealthCheckInterval;
         this._HealthCheckInterval = (Integer)v;
         this._postSet(15, oldVal, this._HealthCheckInterval);
      } else if (name.equals("InitialConnections")) {
         oldVal = this._InitialConnections;
         this._InitialConnections = (Integer)v;
         this._postSet(12, oldVal, this._InitialConnections);
      } else if (name.equals("MaxConnections")) {
         oldVal = this._MaxConnections;
         this._MaxConnections = (Integer)v;
         this._postSet(13, oldVal, this._MaxConnections);
      } else if (name.equals("MaxHealthCheckInterval")) {
         oldVal = this._MaxHealthCheckInterval;
         this._MaxHealthCheckInterval = (Integer)v;
         this._postSet(17, oldVal, this._MaxHealthCheckInterval);
      } else if (name.equals("MaxRetries")) {
         oldVal = this._MaxRetries;
         this._MaxRetries = (Integer)v;
         this._postSet(16, oldVal, this._MaxRetries);
      } else if (name.equals("ServerList")) {
         String oldVal = this._ServerList;
         this._ServerList = (String)v;
         this._postSet(14, oldVal, this._ServerList);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("HealthCheckInterval")) {
         return new Integer(this._HealthCheckInterval);
      } else if (name.equals("InitialConnections")) {
         return new Integer(this._InitialConnections);
      } else if (name.equals("MaxConnections")) {
         return new Integer(this._MaxConnections);
      } else if (name.equals("MaxHealthCheckInterval")) {
         return new Integer(this._MaxHealthCheckInterval);
      } else if (name.equals("MaxRetries")) {
         return new Integer(this._MaxRetries);
      } else {
         return name.equals("ServerList") ? this._ServerList : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 11:
               if (s.equals("max-retries")) {
                  return 16;
               }

               if (s.equals("server-list")) {
                  return 14;
               }
            case 12:
            case 13:
            case 14:
            case 16:
            case 17:
            case 18:
            case 20:
            case 22:
            case 23:
            case 24:
            default:
               break;
            case 15:
               if (s.equals("max-connections")) {
                  return 13;
               }
               break;
            case 19:
               if (s.equals("initial-connections")) {
                  return 12;
               }
               break;
            case 21:
               if (s.equals("health-check-interval")) {
                  return 15;
               }
               break;
            case 25:
               if (s.equals("max-health-check-interval")) {
                  return 17;
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
            case 12:
               return "initial-connections";
            case 13:
               return "max-connections";
            case 14:
               return "server-list";
            case 15:
               return "health-check-interval";
            case 16:
               return "max-retries";
            case 17:
               return "max-health-check-interval";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 10:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
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

   protected static class Helper extends DeploymentMBeanImpl.Helper {
      private HTTPProxyMBeanImpl bean;

      protected Helper(HTTPProxyMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "InitialConnections";
            case 13:
               return "MaxConnections";
            case 14:
               return "ServerList";
            case 15:
               return "HealthCheckInterval";
            case 16:
               return "MaxRetries";
            case 17:
               return "MaxHealthCheckInterval";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("HealthCheckInterval")) {
            return 15;
         } else if (propName.equals("InitialConnections")) {
            return 12;
         } else if (propName.equals("MaxConnections")) {
            return 13;
         } else if (propName.equals("MaxHealthCheckInterval")) {
            return 17;
         } else if (propName.equals("MaxRetries")) {
            return 16;
         } else {
            return propName.equals("ServerList") ? 14 : super.getPropertyIndex(propName);
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
            if (this.bean.isHealthCheckIntervalSet()) {
               buf.append("HealthCheckInterval");
               buf.append(String.valueOf(this.bean.getHealthCheckInterval()));
            }

            if (this.bean.isInitialConnectionsSet()) {
               buf.append("InitialConnections");
               buf.append(String.valueOf(this.bean.getInitialConnections()));
            }

            if (this.bean.isMaxConnectionsSet()) {
               buf.append("MaxConnections");
               buf.append(String.valueOf(this.bean.getMaxConnections()));
            }

            if (this.bean.isMaxHealthCheckIntervalSet()) {
               buf.append("MaxHealthCheckInterval");
               buf.append(String.valueOf(this.bean.getMaxHealthCheckInterval()));
            }

            if (this.bean.isMaxRetriesSet()) {
               buf.append("MaxRetries");
               buf.append(String.valueOf(this.bean.getMaxRetries()));
            }

            if (this.bean.isServerListSet()) {
               buf.append("ServerList");
               buf.append(String.valueOf(this.bean.getServerList()));
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
            HTTPProxyMBeanImpl otherTyped = (HTTPProxyMBeanImpl)other;
            this.computeDiff("HealthCheckInterval", this.bean.getHealthCheckInterval(), otherTyped.getHealthCheckInterval(), true);
            this.computeDiff("InitialConnections", this.bean.getInitialConnections(), otherTyped.getInitialConnections(), false);
            this.computeDiff("MaxConnections", this.bean.getMaxConnections(), otherTyped.getMaxConnections(), false);
            this.computeDiff("MaxHealthCheckInterval", this.bean.getMaxHealthCheckInterval(), otherTyped.getMaxHealthCheckInterval(), false);
            this.computeDiff("MaxRetries", this.bean.getMaxRetries(), otherTyped.getMaxRetries(), true);
            this.computeDiff("ServerList", this.bean.getServerList(), otherTyped.getServerList(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            HTTPProxyMBeanImpl original = (HTTPProxyMBeanImpl)event.getSourceBean();
            HTTPProxyMBeanImpl proposed = (HTTPProxyMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("HealthCheckInterval")) {
                  original.setHealthCheckInterval(proposed.getHealthCheckInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("InitialConnections")) {
                  original.setInitialConnections(proposed.getInitialConnections());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("MaxConnections")) {
                  original.setMaxConnections(proposed.getMaxConnections());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("MaxHealthCheckInterval")) {
                  original.setMaxHealthCheckInterval(proposed.getMaxHealthCheckInterval());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("MaxRetries")) {
                  original.setMaxRetries(proposed.getMaxRetries());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("ServerList")) {
                  original.setServerList(proposed.getServerList());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
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
            HTTPProxyMBeanImpl copy = (HTTPProxyMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("HealthCheckInterval")) && this.bean.isHealthCheckIntervalSet()) {
               copy.setHealthCheckInterval(this.bean.getHealthCheckInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("InitialConnections")) && this.bean.isInitialConnectionsSet()) {
               copy.setInitialConnections(this.bean.getInitialConnections());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxConnections")) && this.bean.isMaxConnectionsSet()) {
               copy.setMaxConnections(this.bean.getMaxConnections());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxHealthCheckInterval")) && this.bean.isMaxHealthCheckIntervalSet()) {
               copy.setMaxHealthCheckInterval(this.bean.getMaxHealthCheckInterval());
            }

            if ((excludeProps == null || !excludeProps.contains("MaxRetries")) && this.bean.isMaxRetriesSet()) {
               copy.setMaxRetries(this.bean.getMaxRetries());
            }

            if ((excludeProps == null || !excludeProps.contains("ServerList")) && this.bean.isServerListSet()) {
               copy.setServerList(this.bean.getServerList());
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
