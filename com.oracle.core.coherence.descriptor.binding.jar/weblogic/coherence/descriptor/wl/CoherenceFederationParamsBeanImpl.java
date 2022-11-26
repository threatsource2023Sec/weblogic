package weblogic.coherence.descriptor.wl;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.SettableBeanImpl;
import weblogic.descriptor.beangen.LegalChecks;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class CoherenceFederationParamsBeanImpl extends SettableBeanImpl implements CoherenceFederationParamsBean, Serializable {
   private String _FederationTopology;
   private int _RemoteCoherenceClusterListenPort;
   private String _RemoteCoherenceClusterName;
   private String[] _RemoteParticipantHosts;
   private static SchemaHelper2 _schemaHelper;

   public CoherenceFederationParamsBeanImpl() {
      this._initializeProperty(-1);
   }

   public CoherenceFederationParamsBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public CoherenceFederationParamsBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public String getFederationTopology() {
      return this._FederationTopology;
   }

   public boolean isFederationTopologyInherited() {
      return false;
   }

   public boolean isFederationTopologySet() {
      return this._isSet(0);
   }

   public void setFederationTopology(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String[] _set = new String[]{"none", "active-active", "active-passive", "passive-active"};
      param0 = LegalChecks.checkInEnum("FederationTopology", param0, _set);
      String _oldVal = this._FederationTopology;
      this._FederationTopology = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String[] getRemoteParticipantHosts() {
      return this._RemoteParticipantHosts;
   }

   public boolean isRemoteParticipantHostsInherited() {
      return false;
   }

   public boolean isRemoteParticipantHostsSet() {
      return this._isSet(1);
   }

   public void addRemoteParticipantHost(String param0) {
      this._getHelper()._ensureNonNull(param0);
      String[] _new;
      if (this._isSet(1)) {
         _new = (String[])((String[])this._getHelper()._extendArray(this.getRemoteParticipantHosts(), String.class, param0));
      } else {
         _new = new String[]{param0};
      }

      try {
         this.setRemoteParticipantHosts(_new);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         } else {
            throw new UndeclaredThrowableException(var4);
         }
      }
   }

   public void removeRemoteParticipantHost(String param0) {
      String[] _old = this.getRemoteParticipantHosts();
      String[] _new = (String[])((String[])this._getHelper()._removeElement(_old, String.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setRemoteParticipantHosts(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setRemoteParticipantHosts(String[] param0) {
      param0 = param0 == null ? new String[0] : param0;
      param0 = this._getHelper()._trimElements(param0);
      String[] _oldVal = this._RemoteParticipantHosts;
      this._RemoteParticipantHosts = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getRemoteCoherenceClusterName() {
      return this._RemoteCoherenceClusterName;
   }

   public boolean isRemoteCoherenceClusterNameInherited() {
      return false;
   }

   public boolean isRemoteCoherenceClusterNameSet() {
      return this._isSet(2);
   }

   public void setRemoteCoherenceClusterName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._RemoteCoherenceClusterName;
      this._RemoteCoherenceClusterName = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getRemoteCoherenceClusterListenPort() {
      return this._RemoteCoherenceClusterListenPort;
   }

   public boolean isRemoteCoherenceClusterListenPortInherited() {
      return false;
   }

   public boolean isRemoteCoherenceClusterListenPortSet() {
      return this._isSet(3);
   }

   public void setRemoteCoherenceClusterListenPort(int param0) {
      int _oldVal = this._RemoteCoherenceClusterListenPort;
      this._RemoteCoherenceClusterListenPort = param0;
      this._postSet(3, _oldVal, param0);
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
               this._FederationTopology = "none";
               if (initOne) {
                  break;
               }
            case 3:
               this._RemoteCoherenceClusterListenPort = 7574;
               if (initOne) {
                  break;
               }
            case 2:
               this._RemoteCoherenceClusterName = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._RemoteParticipantHosts = new String[0];
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

   public static class SchemaHelper2 extends SettableBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 19:
               if (s.equals("federation-topology")) {
                  return 0;
               }
               break;
            case 23:
               if (s.equals("remote-participant-host")) {
                  return 1;
               }
               break;
            case 29:
               if (s.equals("remote-coherence-cluster-name")) {
                  return 2;
               }
               break;
            case 36:
               if (s.equals("remote-coherence-cluster-listen-port")) {
                  return 3;
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
            case 0:
               return "federation-topology";
            case 1:
               return "remote-participant-host";
            case 2:
               return "remote-coherence-cluster-name";
            case 3:
               return "remote-coherence-cluster-listen-port";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 1:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 1:
               return true;
            case 2:
               return true;
            case 3:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends SettableBeanImpl.Helper {
      private CoherenceFederationParamsBeanImpl bean;

      protected Helper(CoherenceFederationParamsBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "FederationTopology";
            case 1:
               return "RemoteParticipantHosts";
            case 2:
               return "RemoteCoherenceClusterName";
            case 3:
               return "RemoteCoherenceClusterListenPort";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("FederationTopology")) {
            return 0;
         } else if (propName.equals("RemoteCoherenceClusterListenPort")) {
            return 3;
         } else if (propName.equals("RemoteCoherenceClusterName")) {
            return 2;
         } else {
            return propName.equals("RemoteParticipantHosts") ? 1 : super.getPropertyIndex(propName);
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
            if (this.bean.isFederationTopologySet()) {
               buf.append("FederationTopology");
               buf.append(String.valueOf(this.bean.getFederationTopology()));
            }

            if (this.bean.isRemoteCoherenceClusterListenPortSet()) {
               buf.append("RemoteCoherenceClusterListenPort");
               buf.append(String.valueOf(this.bean.getRemoteCoherenceClusterListenPort()));
            }

            if (this.bean.isRemoteCoherenceClusterNameSet()) {
               buf.append("RemoteCoherenceClusterName");
               buf.append(String.valueOf(this.bean.getRemoteCoherenceClusterName()));
            }

            if (this.bean.isRemoteParticipantHostsSet()) {
               buf.append("RemoteParticipantHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getRemoteParticipantHosts())));
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
            CoherenceFederationParamsBeanImpl otherTyped = (CoherenceFederationParamsBeanImpl)other;
            this.computeDiff("FederationTopology", this.bean.getFederationTopology(), otherTyped.getFederationTopology(), false);
            this.computeDiff("RemoteCoherenceClusterListenPort", this.bean.getRemoteCoherenceClusterListenPort(), otherTyped.getRemoteCoherenceClusterListenPort(), false);
            this.computeDiff("RemoteCoherenceClusterName", this.bean.getRemoteCoherenceClusterName(), otherTyped.getRemoteCoherenceClusterName(), false);
            this.computeDiff("RemoteParticipantHosts", this.bean.getRemoteParticipantHosts(), otherTyped.getRemoteParticipantHosts(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            CoherenceFederationParamsBeanImpl original = (CoherenceFederationParamsBeanImpl)event.getSourceBean();
            CoherenceFederationParamsBeanImpl proposed = (CoherenceFederationParamsBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("FederationTopology")) {
                  original.setFederationTopology(proposed.getFederationTopology());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("RemoteCoherenceClusterListenPort")) {
                  original.setRemoteCoherenceClusterListenPort(proposed.getRemoteCoherenceClusterListenPort());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("RemoteCoherenceClusterName")) {
                  original.setRemoteCoherenceClusterName(proposed.getRemoteCoherenceClusterName());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("RemoteParticipantHosts")) {
                  if (type == 2) {
                     update.resetAddedObject(update.getAddedObject());
                     original.addRemoteParticipantHost((String)update.getAddedObject());
                  } else {
                     if (type != 3) {
                        throw new AssertionError("Invalid type: " + type);
                     }

                     original.removeRemoteParticipantHost((String)update.getRemovedObject());
                  }

                  if (original.getRemoteParticipantHosts() == null || original.getRemoteParticipantHosts().length == 0) {
                     original._conditionalUnset(update.isUnsetUpdate(), 1);
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
            CoherenceFederationParamsBeanImpl copy = (CoherenceFederationParamsBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("FederationTopology")) && this.bean.isFederationTopologySet()) {
               copy.setFederationTopology(this.bean.getFederationTopology());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteCoherenceClusterListenPort")) && this.bean.isRemoteCoherenceClusterListenPortSet()) {
               copy.setRemoteCoherenceClusterListenPort(this.bean.getRemoteCoherenceClusterListenPort());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteCoherenceClusterName")) && this.bean.isRemoteCoherenceClusterNameSet()) {
               copy.setRemoteCoherenceClusterName(this.bean.getRemoteCoherenceClusterName());
            }

            if ((excludeProps == null || !excludeProps.contains("RemoteParticipantHosts")) && this.bean.isRemoteParticipantHostsSet()) {
               Object o = this.bean.getRemoteParticipantHosts();
               copy.setRemoteParticipantHosts(o == null ? null : (String[])((String[])((String[])((String[])o)).clone()));
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
