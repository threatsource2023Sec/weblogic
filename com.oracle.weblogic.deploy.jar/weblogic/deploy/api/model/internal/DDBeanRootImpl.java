package weblogic.deploy.api.model.internal;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.enterprise.deploy.model.DDBean;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.exceptions.DDBeanCreateException;
import javax.enterprise.deploy.shared.ModuleType;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import weblogic.deploy.api.internal.SPIDeployerLogger;
import weblogic.deploy.api.internal.utils.ConfigHelper;
import weblogic.deploy.api.internal.utils.Debug;
import weblogic.deploy.api.model.WebLogicDDBeanRoot;
import weblogic.deploy.api.model.WebLogicDeployableObject;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.deploy.api.spi.config.DescriptorParser;
import weblogic.deploy.api.spi.config.DescriptorSupport;
import weblogic.deploy.api.spi.config.DescriptorSupportManager;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.j2ee.descriptor.WebservicesBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.WeblogicExtensionBean;

public class DDBeanRootImpl extends DDBeanImpl implements WebLogicDDBeanRoot, PropertyChangeListener {
   private static final boolean debug = Debug.isDebug("model");
   private static final String FROM_LISTENER = "FROM_LISTENER";
   private Document doc = null;
   private DescriptorParser dom = null;
   private String altdd;
   private ModuleType moduleType;
   private boolean initialized = false;
   private DescriptorBean beanTree = null;
   private boolean schemaBased;
   private boolean planBasedDBean = false;

   public DDBeanRootImpl(String altdd, WebLogicDeployableObject dObject, ModuleType moduleType) {
      super(dObject);
      this.altdd = altdd;
      this.moduleType = moduleType;
      this.setRoot(this);
      this.setDescriptorSupport();
   }

   public DDBeanRootImpl(String altdd, WebLogicDeployableObject dObject, ModuleType moduleType, DescriptorBean db, boolean schemaBased) {
      super(dObject);
      this.altdd = altdd;
      this.moduleType = moduleType;
      this.beanTree = db;
      this.schemaBased = schemaBased;
      this.setRoot(this);
      this.setDescriptorSupport();
      if (debug && db != null) {
         Debug.say("Descriptor bean for " + moduleType + " is not null");
      }

      this.initialize();
      this.initialized = true;
   }

   public synchronized void setupDDBeanRoot(InputStream dd) throws DDBeanCreateException {
      if (!this.initialized) {
         try {
            ModuleType mt = this.getModuleType();
            DescriptorSupport[] dss = DescriptorSupportManager.getForModuleType(mt);
            DescriptorSupport ds = null;
            if (dss.length >= 1) {
               ds = dss[0];
               if (dss.length > 1) {
                  dss = DescriptorSupportManager.getForBaseURI(this.altdd);
                  if (dss.length > 0) {
                     ds = dss[0];
                  }
               }

               if (dd != null) {
                  if (debug) {
                     Debug.say("+++++++++++ PARSING " + mt.toString());
                  }

                  this.dom = DescriptorParser.getDescriptorParser(dd, (DeploymentPlanBean)null, ds);
                  this.doc = this.dom.getDocument();
               }

               Node root = this.doc;
               this.initDD(root, "/", (DDBeanImpl)null);
               this.initialized = true;
            } else {
               throw new DDBeanCreateException(SPIDeployerLogger.unknownDD(this.altdd, this.dObject.getUri()));
            }
         } catch (IOException var6) {
            DDBeanCreateException d = new DDBeanCreateException(var6.getMessage());
            d.initCause(var6);
            throw d;
         }
      }
   }

   public String getFilename() {
      return this.dObject.getFileName(this);
   }

   public String getDDBeanRootVersion() {
      return this.getDocumentVersion();
   }

   public ModuleType getType() {
      return this.moduleType;
   }

   public DeployableObject getDeployableObject() {
      return this.dObject;
   }

   /** @deprecated */
   @Deprecated
   public String getModuleDTDVersion() {
      return this.isSchemaBased() ? null : this.getDDBeanRootVersion();
   }

   public boolean hasDBean() {
      return this.beanTree != null;
   }

   public DescriptorBean getDescriptorBean() {
      return this.beanTree;
   }

   public String getDocumentVersion() {
      return this.dom == null ? null : this.dom.getDocumentVersion();
   }

   public boolean isSchemaBased() {
      return this.schemaBased;
   }

   public Document getDocument() {
      return this.doc;
   }

   public DescriptorParser getDescriptorParser() {
      return this.dom;
   }

   public String getAltdd() {
      return this.altdd;
   }

   public ModuleType getModuleType() {
      return this.moduleType;
   }

   private void dumpNode(Node node) {
      switch (node.getNodeType()) {
         case 1:
            Debug.say("element: " + this.getNodeData(node));
            break;
         case 2:
            Debug.say("attr: " + this.getNodeData(node));
            break;
         case 3:
            Debug.say("text: " + this.getNodeData(node));
            break;
         case 4:
            Debug.say("cdata: " + this.getNodeData(node));
            break;
         case 5:
            Debug.say("entity ref: " + this.getNodeData(node));
            break;
         case 6:
            Debug.say("entity: " + this.getNodeData(node));
            break;
         case 7:
            Debug.say("pi: " + this.getNodeData(node));
            break;
         case 8:
            Debug.say("comment: " + this.getNodeData(node));
            break;
         case 9:
            Debug.say("doc: " + this.getNodeData(node));
            break;
         case 10:
            Debug.say("doctype: " + this.getNodeData(node));
            break;
         case 11:
            Debug.say("docfrag: " + this.getNodeData(node));
            break;
         case 12:
            Debug.say("notation: " + this.getNodeData(node));
      }

   }

   private String getNodeData(Node node) {
      return node.getNodeName() + " value: " + node.getNodeValue();
   }

   public boolean isInitialized() {
      return this.initialized;
   }

   private void initialize() {
      ByteArrayOutputStream out = null;
      ByteArrayInputStream in = null;
      this.xpath = "/";
      if (this.beanTree != null) {
         try {
            if (this.beanTree instanceof WeblogicExtensionBean) {
               DescriptorSupportManager.registerWebLogicExtensions((WeblogicExtensionBean)this.beanTree, this.altdd);
            }

            if (this.moduleType != null) {
               out = new ByteArrayOutputStream();
               (new EditableDescriptorManager()).writeDescriptorAsXML(this.beanTree.getDescriptor(), out);
               in = new ByteArrayInputStream(out.toByteArray());
               this.setupDDBeanRoot(in);
            }
         } catch (IOException var14) {
            if (debug) {
               Debug.say("Marshalling of Descriptor Bean failed. XML view will not be available for this bean  " + var14);
            }
         } catch (DDBeanCreateException var15) {
            if (debug) {
               Debug.say("Creation of child DD Beans failed " + var15);
            }
         } finally {
            try {
               if (out != null) {
                  out.close();
               }

               if (in != null) {
                  in.close();
               }
            } catch (IOException var13) {
            }

         }
      }

   }

   public void setDC(WebLogicDeploymentConfiguration dc) {
      this.dc = dc;
   }

   private void setDescriptorSupport() {
      DescriptorSupport[] ds = null;
      if (this.beanTree instanceof WebservicesBean) {
         ds = DescriptorSupportManager.getForBaseURI("WEB-INF/webservices.xml");
         if (ds != null && ds.length == 1) {
            this.descriptorSupport = ds[0];
            return;
         }
      } else {
         ds = DescriptorSupportManager.getForModuleType(this.getType());
      }

      if (ds != null && ds.length != 0) {
         String tag = null;
         if (ds.length == 1) {
            tag = ds[0].getBaseTag();
         } else {
            tag = ds[0].getBaseTag();

            for(int n = 0; n < ds.length; ++n) {
               String ns = ConfigHelper.getNSPrefix(this, ds[n].getBaseNameSpace());
               DDBean[] dd = this.getChildBean(ConfigHelper.applyNamespace(ns, ds[n].getBaseTag()));
               if (dd != null) {
                  tag = ds[n].getBaseTag();
                  break;
               }
            }

            if (this.getType() == ModuleType.EJB) {
               tag = DescriptorSupportManager.EJB_DESC_SUPPORT.getBaseTag();
            }
         }

         if (tag == null) {
            tag = ds[0].getBaseTag();
         }

         this.descriptorSupport = DescriptorSupportManager.getForTag(tag);
      }
   }

   public void registerAsListener(WebLogicDeploymentConfiguration dc, DescriptorBean bean) {
      this.dc = dc;
      this.registerAsListener(bean);
   }

   protected void registerWebservices(String uri) {
      WebLogicDDBeanRoot root = null;

      try {
         if (!this.dObject.hasDDBean(uri)) {
            return;
         }

         root = (WebLogicDDBeanRoot)this.dObject.getDDBeanRoot(uri);
      } catch (FileNotFoundException var6) {
         return;
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }

      DescriptorBean bean = null;

      try {
         bean = root.getDescriptorBean();
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      DDBeanRootImpl rootImpl = (DDBeanRootImpl)root;
      rootImpl.setDC(this.dc);
      rootImpl.setAppName(this.getAppName());
      this.getDescriptorHelper().addPropertyChangeListener(bean, rootImpl);
   }

   protected void deregisterWebservices(String uri) {
      WebLogicDDBeanRoot root = null;

      try {
         if (!this.dObject.hasDDBean(uri)) {
            return;
         }

         root = (WebLogicDDBeanRoot)this.dObject.getDDBeanRoot(uri);
      } catch (FileNotFoundException var6) {
         return;
      } catch (Exception var7) {
         throw new RuntimeException(var7);
      }

      DescriptorBean bean = null;

      try {
         bean = root.getDescriptorBean();
      } catch (IOException var5) {
         throw new RuntimeException(var5);
      }

      DDBeanRootImpl rootImpl = (DDBeanRootImpl)root;
      this.getDescriptorHelper().removePropertyChangeListener(bean, rootImpl);
   }

   public void propertyChange(PropertyChangeEvent pce) {
      this.setModified(true);
      this.setPlanBasedDBean(false);
      DescriptorBean bean = (DescriptorBean)pce.getSource();
      if (debug) {
         Debug.say("PropertyChangeEvent : " + pce);
         Debug.say("PropertyChangeEvent.source : " + bean);
         Debug.say("PropertyChangeEvent.prop : " + pce.getPropertyName());
         Debug.say("PropertyChangeEvent.old : " + pce.getOldValue());
         Debug.say("PropertyChangeEvent.new : " + pce.getNewValue());
      }

      this.handleChange(bean, pce.getPropertyName(), pce.getOldValue(), pce.getNewValue());
      this.reregister(bean);
   }

   private void addEmptyBeanKey(DescriptorBean ddbean, String prop, Object newValue) {
      ModuleDescriptorBean md = this.dc.getPlan().findModuleDescriptor(this.getAppName(), this.getDescriptorSupport().getConfigURI());
      VariableBean var = this.dc.getPlan().findOrCreateVariable(md, ddbean, prop, this.getPlanBasedDBean());
      var.setValue(newValue.toString());
   }
}
