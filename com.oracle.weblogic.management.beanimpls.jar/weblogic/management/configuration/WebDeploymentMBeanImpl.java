package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import javax.management.InvalidAttributeValueException;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.DistributedManagementException;
import weblogic.utils.ArrayUtils;
import weblogic.utils.collections.CombinedIterator;

public class WebDeploymentMBeanImpl extends DeploymentMBeanImpl implements WebDeploymentMBean, Serializable {
   private VirtualHostMBean[] _DeployedVirtualHosts;
   private VirtualHostMBean[] _VirtualHosts;
   private WebServerMBean[] _WebServers;
   private static SchemaHelper2 _schemaHelper;

   public WebDeploymentMBeanImpl() {
      this._initializeProperty(-1);
   }

   public WebDeploymentMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public WebDeploymentMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public WebServerMBean[] getWebServers() {
      return this._WebServers;
   }

   public boolean isWebServersInherited() {
      return false;
   }

   public boolean isWebServersSet() {
      return this._isSet(12);
   }

   public void setWebServers(WebServerMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      WebServerMBean[] param0 = param0 == null ? new WebServerMBeanImpl[0] : param0;
      this._WebServers = (WebServerMBean[])param0;
   }

   public boolean addWebServer(WebServerMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 12)) {
         WebServerMBean[] _new = (WebServerMBean[])((WebServerMBean[])this._getHelper()._extendArray(this.getWebServers(), WebServerMBean.class, param0));

         try {
            this.setWebServers(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeWebServer(WebServerMBean param0) throws DistributedManagementException {
      WebServerMBean[] _old = this.getWebServers();
      WebServerMBean[] _new = (WebServerMBean[])((WebServerMBean[])this._getHelper()._removeElement(_old, WebServerMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setWebServers(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public VirtualHostMBean[] getVirtualHosts() {
      return this._VirtualHosts;
   }

   public String getVirtualHostsAsString() {
      return this._getHelper()._serializeKeyList(this.getVirtualHosts());
   }

   public boolean isVirtualHostsInherited() {
      return false;
   }

   public boolean isVirtualHostsSet() {
      return this._isSet(13);
   }

   public void setVirtualHostsAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         String[] refs = this._getHelper()._splitKeyList(param0);
         List oldRefs = this._getHelper()._getKeyList(this._VirtualHosts);

         String ref;
         for(int i = 0; i < refs.length; ++i) {
            ref = refs[i];
            ref = ref == null ? null : ref.trim();
            if (oldRefs.contains(ref)) {
               oldRefs.remove(ref);
            } else {
               this._getReferenceManager().registerUnresolvedReference(ref, VirtualHostMBean.class, new ReferenceManager.Resolver(this, 13, param0) {
                  public void resolveReference(Object value) {
                     try {
                        WebDeploymentMBeanImpl.this.addVirtualHost((VirtualHostMBean)value);
                        WebDeploymentMBeanImpl.this._getHelper().reorderArrayObjects((Object[])WebDeploymentMBeanImpl.this._VirtualHosts, this.getHandbackObject());
                     } catch (RuntimeException var3) {
                        throw var3;
                     } catch (Exception var4) {
                        throw new AssertionError("Impossible exception: " + var4);
                     }
                  }
               });
            }
         }

         Iterator var14 = oldRefs.iterator();

         while(true) {
            while(var14.hasNext()) {
               ref = (String)var14.next();
               VirtualHostMBean[] var6 = this._VirtualHosts;
               int var7 = var6.length;

               for(int var8 = 0; var8 < var7; ++var8) {
                  VirtualHostMBean member = var6[var8];
                  if (ref.equals(member.getName())) {
                     try {
                        this.removeVirtualHost(member);
                        break;
                     } catch (RuntimeException var11) {
                        throw var11;
                     } catch (Exception var12) {
                        throw new AssertionError("Impossible exception: " + var12);
                     }
                  }
               }
            }

            return;
         }
      } else {
         VirtualHostMBean[] _oldVal = this._VirtualHosts;
         this._initializeProperty(13);
         this._postSet(13, _oldVal, this._VirtualHosts);
      }
   }

   public void setVirtualHosts(VirtualHostMBean[] param0) throws InvalidAttributeValueException, DistributedManagementException {
      VirtualHostMBean[] param0 = param0 == null ? new VirtualHostMBeanImpl[0] : param0;
      param0 = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._cleanAndValidateArray(param0, VirtualHostMBean.class));

      for(int i = 0; i < param0.length; ++i) {
         if (param0[i] != null) {
            ResolvedReference _ref = new ResolvedReference(this, 13, (AbstractDescriptorBean)param0[i]) {
               protected Object getPropertyValue() {
                  return WebDeploymentMBeanImpl.this.getVirtualHosts();
               }
            };
            this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0[i], _ref);
         }
      }

      VirtualHostMBean[] _oldVal = this._VirtualHosts;
      this._VirtualHosts = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean addVirtualHost(VirtualHostMBean param0) throws InvalidAttributeValueException, DistributedManagementException {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 13)) {
         VirtualHostMBean[] _new;
         if (this._isSet(13)) {
            _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._extendArray(this.getVirtualHosts(), VirtualHostMBean.class, param0));
         } else {
            _new = new VirtualHostMBean[]{param0};
         }

         try {
            this.setVirtualHosts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            if (var4 instanceof InvalidAttributeValueException) {
               throw (InvalidAttributeValueException)var4;
            }

            if (var4 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

      return true;
   }

   public boolean removeVirtualHost(VirtualHostMBean param0) throws DistributedManagementException {
      VirtualHostMBean[] _old = this.getVirtualHosts();
      VirtualHostMBean[] _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._removeElement(_old, VirtualHostMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setVirtualHosts(_new);
            return true;
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            } else if (var5 instanceof DistributedManagementException) {
               throw (DistributedManagementException)var5;
            } else {
               throw new UndeclaredThrowableException(var5);
            }
         }
      } else {
         return false;
      }
   }

   public void addDeployedVirtualHost(VirtualHostMBean param0) {
      this._getHelper()._ensureNonNull(param0);
      if (!((AbstractDescriptorBean)param0).isChildProperty(this, 14)) {
         VirtualHostMBean[] _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._extendArray(this.getDeployedVirtualHosts(), VirtualHostMBean.class, param0));

         try {
            this.setDeployedVirtualHosts(_new);
         } catch (Exception var4) {
            if (var4 instanceof RuntimeException) {
               throw (RuntimeException)var4;
            }

            throw new UndeclaredThrowableException(var4);
         }
      }

   }

   public VirtualHostMBean[] getDeployedVirtualHosts() {
      return this._DeployedVirtualHosts;
   }

   public boolean isDeployedVirtualHostsInherited() {
      return false;
   }

   public boolean isDeployedVirtualHostsSet() {
      return this._isSet(14);
   }

   public void removeDeployedVirtualHost(VirtualHostMBean param0) {
      VirtualHostMBean[] _old = this.getDeployedVirtualHosts();
      VirtualHostMBean[] _new = (VirtualHostMBean[])((VirtualHostMBean[])this._getHelper()._removeElement(_old, VirtualHostMBean.class, param0));
      if (_new.length != _old.length) {
         try {
            this.setDeployedVirtualHosts(_new);
         } catch (Exception var5) {
            if (var5 instanceof RuntimeException) {
               throw (RuntimeException)var5;
            }

            throw new UndeclaredThrowableException(var5);
         }
      }

   }

   public void setDeployedVirtualHosts(VirtualHostMBean[] param0) {
      VirtualHostMBean[] param0 = param0 == null ? new VirtualHostMBeanImpl[0] : param0;
      this._DeployedVirtualHosts = (VirtualHostMBean[])param0;
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
         idx = 14;
      }

      try {
         switch (idx) {
            case 14:
               this._DeployedVirtualHosts = new VirtualHostMBean[0];
               if (initOne) {
                  break;
               }
            case 13:
               this._VirtualHosts = new VirtualHostMBean[0];
               if (initOne) {
                  break;
               }
            case 12:
               this._WebServers = new WebServerMBean[0];
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
      return "WebDeployment";
   }

   public void putValue(String name, Object v) {
      VirtualHostMBean[] oldVal;
      if (name.equals("DeployedVirtualHosts")) {
         oldVal = this._DeployedVirtualHosts;
         this._DeployedVirtualHosts = (VirtualHostMBean[])((VirtualHostMBean[])v);
         this._postSet(14, oldVal, this._DeployedVirtualHosts);
      } else if (name.equals("VirtualHosts")) {
         oldVal = this._VirtualHosts;
         this._VirtualHosts = (VirtualHostMBean[])((VirtualHostMBean[])v);
         this._postSet(13, oldVal, this._VirtualHosts);
      } else if (name.equals("WebServers")) {
         WebServerMBean[] oldVal = this._WebServers;
         this._WebServers = (WebServerMBean[])((WebServerMBean[])v);
         this._postSet(12, oldVal, this._WebServers);
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("DeployedVirtualHosts")) {
         return this._DeployedVirtualHosts;
      } else if (name.equals("VirtualHosts")) {
         return this._VirtualHosts;
      } else {
         return name.equals("WebServers") ? this._WebServers : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends DeploymentMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 10:
               if (s.equals("web-server")) {
                  return 12;
               }
               break;
            case 12:
               if (s.equals("virtual-host")) {
                  return 13;
               }
               break;
            case 21:
               if (s.equals("deployed-virtual-host")) {
                  return 14;
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
               return "web-server";
            case 13:
               return "virtual-host";
            case 14:
               return "deployed-virtual-host";
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
            case 11:
            default:
               return super.isArray(propIndex);
            case 12:
               return true;
            case 13:
               return true;
            case 14:
               return true;
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
      private WebDeploymentMBeanImpl bean;

      protected Helper(WebDeploymentMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 12:
               return "WebServers";
            case 13:
               return "VirtualHosts";
            case 14:
               return "DeployedVirtualHosts";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("DeployedVirtualHosts")) {
            return 14;
         } else if (propName.equals("VirtualHosts")) {
            return 13;
         } else {
            return propName.equals("WebServers") ? 12 : super.getPropertyIndex(propName);
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
            if (this.bean.isDeployedVirtualHostsSet()) {
               buf.append("DeployedVirtualHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getDeployedVirtualHosts())));
            }

            if (this.bean.isVirtualHostsSet()) {
               buf.append("VirtualHosts");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getVirtualHosts())));
            }

            if (this.bean.isWebServersSet()) {
               buf.append("WebServers");
               buf.append(Arrays.toString(ArrayUtils.copyAndSort(this.bean.getWebServers())));
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
            WebDeploymentMBeanImpl otherTyped = (WebDeploymentMBeanImpl)other;
            this.computeDiff("VirtualHosts", this.bean.getVirtualHosts(), otherTyped.getVirtualHosts(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            WebDeploymentMBeanImpl original = (WebDeploymentMBeanImpl)event.getSourceBean();
            WebDeploymentMBeanImpl proposed = (WebDeploymentMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (!prop.equals("DeployedVirtualHosts")) {
                  if (prop.equals("VirtualHosts")) {
                     original.setVirtualHostsAsString(proposed.getVirtualHostsAsString());
                     original._conditionalUnset(update.isUnsetUpdate(), 13);
                  } else if (!prop.equals("WebServers")) {
                     super.applyPropertyUpdate(event, update);
                  }
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
            WebDeploymentMBeanImpl copy = (WebDeploymentMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("VirtualHosts")) && this.bean.isVirtualHostsSet()) {
               copy._unSet(copy, 13);
               copy.setVirtualHostsAsString(this.bean.getVirtualHostsAsString());
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
         this.inferSubTree(this.bean.getDeployedVirtualHosts(), clazz, annotation);
         this.inferSubTree(this.bean.getVirtualHosts(), clazz, annotation);
         this.inferSubTree(this.bean.getWebServers(), clazz, annotation);
      }
   }
}
