package weblogic.management.configuration;

import java.io.Serializable;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.ReferenceManager;
import weblogic.descriptor.internal.ResolvedReference;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.management.mbeans.custom.Server;
import weblogic.utils.collections.ArrayIterator;
import weblogic.utils.collections.CombinedIterator;

public class ServerMBeanImpl extends ServerTemplateMBeanImpl implements ServerMBean, Serializable {
   private ServerTemplateMBean _ServerTemplate;
   private transient Server _customizer;
   private List _DelegateSources = new CopyOnWriteArrayList();
   private ServerTemplateMBeanImpl _DelegateBean;
   private static SchemaHelper2 _schemaHelper;

   public void _addDelegateSource(ServerTemplateMBeanImpl source) {
      this._DelegateSources.add(source);
   }

   public void _removeDelegateSource(ServerTemplateMBeanImpl source) {
      this._DelegateSources.remove(source);
   }

   public ServerTemplateMBeanImpl _getDelegateBean() {
      return this._DelegateBean;
   }

   public void _setDelegateBean(ServerTemplateMBeanImpl delegate) {
      super._setDelegateBean(delegate);
      ServerTemplateMBeanImpl oldDelegate = this._DelegateBean;
      this._DelegateBean = delegate;
      if (oldDelegate != null) {
         oldDelegate._removeDelegateSource(this);
      }

      if (delegate != null) {
         delegate._addDelegateSource(this);
      }

   }

   public ServerMBeanImpl() {
      try {
         this._customizer = new Server(this);
      } catch (Exception var2) {
         if (var2 instanceof RuntimeException) {
            throw (RuntimeException)var2;
         }

         throw new UndeclaredThrowableException(var2);
      }

      this._initializeProperty(-1);
   }

   public ServerMBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);

      try {
         this._customizer = new Server(this);
      } catch (Exception var4) {
         if (var4 instanceof RuntimeException) {
            throw (RuntimeException)var4;
         }

         throw new UndeclaredThrowableException(var4);
      }

      this._initializeProperty(-1);
   }

   public ServerMBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);

      try {
         this._customizer = new Server(this);
      } catch (Exception var5) {
         if (var5 instanceof RuntimeException) {
            throw (RuntimeException)var5;
         }

         throw new UndeclaredThrowableException(var5);
      }

      this._initializeProperty(-1);
   }

   public ServerTemplateMBean getServerTemplate() {
      return this._customizer.getServerTemplate();
   }

   public String getServerTemplateAsString() {
      AbstractDescriptorBean bean = (AbstractDescriptorBean)this.getServerTemplate();
      return bean == null ? null : bean._getKey().toString();
   }

   public boolean isServerTemplateInherited() {
      return false;
   }

   public boolean isServerTemplateSet() {
      return this._isSet(241);
   }

   public void setServerTemplateAsString(String param0) {
      if (param0 != null && param0.length() != 0) {
         param0 = param0 == null ? null : param0.trim();
         this._getReferenceManager().registerUnresolvedReference(param0, ServerTemplateMBean.class, new ReferenceManager.Resolver(this, 241) {
            public void resolveReference(Object value) {
               try {
                  ServerMBeanImpl.this.setServerTemplate((ServerTemplateMBean)value);
               } catch (RuntimeException var3) {
                  throw var3;
               } catch (Exception var4) {
                  throw new AssertionError("Impossible exception: " + var4);
               }
            }
         });
      } else {
         ServerTemplateMBean _oldVal = this._ServerTemplate;
         this._initializeProperty(241);
         this._setDelegateBean((ServerTemplateMBeanImpl)null);
         this._postSet(241, _oldVal, this._ServerTemplate);
      }

   }

   public void setServerTemplate(ServerTemplateMBean param0) {
      if (param0 != null) {
         ResolvedReference _ref = new ResolvedReference(this, 241, (AbstractDescriptorBean)param0) {
            protected Object getPropertyValue() {
               return ServerMBeanImpl.this.getServerTemplate();
            }
         };
         this._getReferenceManager().registerResolvedReference((AbstractDescriptorBean)param0, _ref);
      }

      ServerTemplateMBean _oldVal = this.getServerTemplate();
      this._customizer.setServerTemplate(param0);
      if (param0 != _oldVal) {
         int var4;
         int var5;
         if (this._isSet(89)) {
            ConfigurationPropertyMBean[] var3 = this.getConfigurationProperties();
            var4 = var3.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ConfigurationPropertyMBean p = var3[var5];
               if (((ConfigurationPropertyMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyConfigurationProperty(p);
               }
            }
         }

         if (this._isSet(62)) {
            ExecuteQueueMBean[] var8 = this.getExecuteQueues();
            var4 = var8.length;

            for(var5 = 0; var5 < var4; ++var5) {
               ExecuteQueueMBean p = var8[var5];
               if (((ExecuteQueueMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyExecuteQueue(p);
               }
            }
         }

         if (this._isSet(129)) {
            NetworkAccessPointMBean[] var9 = this.getNetworkAccessPoints();
            var4 = var9.length;

            for(var5 = 0; var5 < var4; ++var5) {
               NetworkAccessPointMBean p = var9[var5];
               if (((NetworkAccessPointMBeanImpl)p)._getDelegateBean() != null) {
                  this.destroyNetworkAccessPoint(p);
               }
            }
         }
      }

      this._setDelegateBean((ServerTemplateMBeanImpl)param0);
      this._postSet(241, _oldVal, param0);
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
         idx = 241;
      }

      try {
         switch (idx) {
            case 241:
               this._customizer.setServerTemplate((ServerTemplateMBean)null);
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
      return "Server";
   }

   public void putValue(String name, Object v) {
      if (name.equals("ServerTemplate")) {
         ServerTemplateMBean oldVal = this._ServerTemplate;
         this._ServerTemplate = (ServerTemplateMBean)v;
         this._postSet(241, oldVal, this._ServerTemplate);
      } else if (name.equals("customizer")) {
         Server oldVal = this._customizer;
         this._customizer = (Server)v;
      } else {
         super.putValue(name, v);
      }
   }

   public Object getValue(String name) {
      if (name.equals("ServerTemplate")) {
         return this._ServerTemplate;
      } else {
         return name.equals("customizer") ? this._customizer : super.getValue(name);
      }
   }

   public static class SchemaHelper2 extends ServerTemplateMBeanImpl.SchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 15:
               if (s.equals("server-template")) {
                  return 241;
               }
            default:
               return super.getPropertyIndex(s);
         }
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            case 53:
               return new SSLMBeanImpl.SchemaHelper2();
            case 54:
               return new IIOPMBeanImpl.SchemaHelper2();
            case 55:
               return new LogMBeanImpl.SchemaHelper2();
            case 62:
               return new ExecuteQueueMBeanImpl.SchemaHelper2();
            case 89:
               return new ConfigurationPropertyMBeanImpl.SchemaHelper2();
            case 101:
               return new WebServerMBeanImpl.SchemaHelper2();
            case 117:
               return new COMMBeanImpl.SchemaHelper2();
            case 118:
               return new ServerDebugMBeanImpl.SchemaHelper2();
            case 129:
               return new NetworkAccessPointMBeanImpl.SchemaHelper2();
            case 154:
               return new ServerStartMBeanImpl.SchemaHelper2();
            case 156:
               return new JTAMigratableTargetMBeanImpl.SchemaHelper2();
            case 200:
               return new DefaultFileStoreMBeanImpl.SchemaHelper2();
            case 202:
               return new OverloadProtectionMBeanImpl.SchemaHelper2();
            case 209:
               return new WLDFServerDiagnosticMBeanImpl.SchemaHelper2();
            case 214:
               return new FederationServicesMBeanImpl.SchemaHelper2();
            case 215:
               return new SingleSignOnServicesMBeanImpl.SchemaHelper2();
            case 216:
               return new WebServiceMBeanImpl.SchemaHelper2();
            case 221:
               return new TransactionLogJDBCStoreMBeanImpl.SchemaHelper2();
            case 222:
               return new DataSourceMBeanImpl.SchemaHelper2();
            case 223:
               return new CoherenceMemberConfigMBeanImpl.SchemaHelper2();
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 241:
               return "server-template";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isArray(int propIndex) {
         switch (propIndex) {
            case 9:
               return true;
            case 62:
               return true;
            case 89:
               return true;
            case 110:
               return true;
            case 114:
               return true;
            case 121:
               return true;
            case 129:
               return true;
            case 135:
               return true;
            case 191:
               return true;
            case 195:
               return true;
            case 197:
               return true;
            case 201:
               return true;
            case 211:
               return true;
            default:
               return super.isArray(propIndex);
         }
      }

      public boolean isBean(int propIndex) {
         switch (propIndex) {
            case 53:
               return true;
            case 54:
               return true;
            case 55:
               return true;
            case 62:
               return true;
            case 89:
               return true;
            case 101:
               return true;
            case 117:
               return true;
            case 118:
               return true;
            case 129:
               return true;
            case 154:
               return true;
            case 156:
               return true;
            case 200:
               return true;
            case 202:
               return true;
            case 209:
               return true;
            case 214:
               return true;
            case 215:
               return true;
            case 216:
               return true;
            case 221:
               return true;
            case 222:
               return true;
            case 223:
               return true;
            default:
               return super.isBean(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 31:
               return true;
            case 91:
               return true;
            case 92:
               return true;
            case 95:
               return true;
            case 96:
               return true;
            case 99:
               return true;
            case 102:
               return true;
            case 126:
               return true;
            case 127:
               return true;
            case 128:
               return true;
            case 137:
               return true;
            case 138:
               return true;
            case 147:
               return true;
            case 151:
               return true;
            case 155:
               return true;
            case 157:
               return true;
            case 158:
               return true;
            case 159:
               return true;
            case 160:
               return true;
            case 165:
               return true;
            case 166:
               return true;
            case 167:
               return true;
            case 168:
               return true;
            case 169:
               return true;
            case 170:
               return true;
            case 171:
               return true;
            case 172:
               return true;
            case 176:
               return true;
            case 177:
               return true;
            case 178:
               return true;
            case 179:
               return true;
            case 180:
               return true;
            case 185:
               return true;
            case 198:
               return true;
            case 199:
               return true;
            case 221:
               return true;
            default:
               return super.isConfigurable(propIndex);
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

   protected static class Helper extends ServerTemplateMBeanImpl.Helper {
      private ServerMBeanImpl bean;

      protected Helper(ServerMBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 241:
               return "ServerTemplate";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         return propName.equals("ServerTemplate") ? 241 : super.getPropertyIndex(propName);
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         if (this.bean.getCOM() != null) {
            iterators.add(new ArrayIterator(new COMMBean[]{this.bean.getCOM()}));
         }

         if (this.bean.getCoherenceMemberConfig() != null) {
            iterators.add(new ArrayIterator(new CoherenceMemberConfigMBean[]{this.bean.getCoherenceMemberConfig()}));
         }

         iterators.add(new ArrayIterator(this.bean.getConfigurationProperties()));
         if (this.bean.getDataSource() != null) {
            iterators.add(new ArrayIterator(new DataSourceMBean[]{this.bean.getDataSource()}));
         }

         if (this.bean.getDefaultFileStore() != null) {
            iterators.add(new ArrayIterator(new DefaultFileStoreMBean[]{this.bean.getDefaultFileStore()}));
         }

         iterators.add(new ArrayIterator(this.bean.getExecuteQueues()));
         if (this.bean.getFederationServices() != null) {
            iterators.add(new ArrayIterator(new FederationServicesMBean[]{this.bean.getFederationServices()}));
         }

         if (this.bean.getIIOP() != null) {
            iterators.add(new ArrayIterator(new IIOPMBean[]{this.bean.getIIOP()}));
         }

         if (this.bean.getJTAMigratableTarget() != null) {
            iterators.add(new ArrayIterator(new JTAMigratableTargetMBean[]{this.bean.getJTAMigratableTarget()}));
         }

         if (this.bean.getLog() != null) {
            iterators.add(new ArrayIterator(new LogMBean[]{this.bean.getLog()}));
         }

         iterators.add(new ArrayIterator(this.bean.getNetworkAccessPoints()));
         if (this.bean.getOverloadProtection() != null) {
            iterators.add(new ArrayIterator(new OverloadProtectionMBean[]{this.bean.getOverloadProtection()}));
         }

         if (this.bean.getSSL() != null) {
            iterators.add(new ArrayIterator(new SSLMBean[]{this.bean.getSSL()}));
         }

         if (this.bean.getServerDebug() != null) {
            iterators.add(new ArrayIterator(new ServerDebugMBean[]{this.bean.getServerDebug()}));
         }

         if (this.bean.getServerDiagnosticConfig() != null) {
            iterators.add(new ArrayIterator(new WLDFServerDiagnosticMBean[]{this.bean.getServerDiagnosticConfig()}));
         }

         if (this.bean.getServerStart() != null) {
            iterators.add(new ArrayIterator(new ServerStartMBean[]{this.bean.getServerStart()}));
         }

         if (this.bean.getSingleSignOnServices() != null) {
            iterators.add(new ArrayIterator(new SingleSignOnServicesMBean[]{this.bean.getSingleSignOnServices()}));
         }

         if (this.bean.getTransactionLogJDBCStore() != null) {
            iterators.add(new ArrayIterator(new TransactionLogJDBCStoreMBean[]{this.bean.getTransactionLogJDBCStore()}));
         }

         if (this.bean.getWebServer() != null) {
            iterators.add(new ArrayIterator(new WebServerMBean[]{this.bean.getWebServer()}));
         }

         if (this.bean.getWebService() != null) {
            iterators.add(new ArrayIterator(new WebServiceMBean[]{this.bean.getWebService()}));
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
            if (this.bean.isServerTemplateSet()) {
               buf.append("ServerTemplate");
               buf.append(String.valueOf(this.bean.getServerTemplate()));
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
            ServerMBeanImpl otherTyped = (ServerMBeanImpl)other;
            this.computeDiff("ServerTemplate", this.bean.getServerTemplate(), otherTyped.getServerTemplate(), false);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            ServerMBeanImpl original = (ServerMBeanImpl)event.getSourceBean();
            ServerMBeanImpl proposed = (ServerMBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("ServerTemplate")) {
                  original.setServerTemplateAsString(proposed.getServerTemplateAsString());
                  original._conditionalUnset(update.isUnsetUpdate(), 241);
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
            ServerMBeanImpl copy = (ServerMBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("ServerTemplate")) && this.bean.isServerTemplateSet()) {
               copy._unSet(copy, 241);
               copy.setServerTemplateAsString(this.bean.getServerTemplateAsString());
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
         this.inferSubTree(this.bean.getServerTemplate(), clazz, annotation);
      }
   }
}
