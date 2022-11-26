package weblogic.management.scripting.jsr88;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;
import javax.enterprise.deploy.model.DDBeanRoot;
import javax.enterprise.deploy.model.DeployableObject;
import javax.enterprise.deploy.model.J2eeApplicationObject;
import javax.enterprise.deploy.shared.ModuleType;
import javax.enterprise.deploy.spi.DConfigBean;
import javax.enterprise.deploy.spi.DConfigBeanRoot;
import javax.enterprise.deploy.spi.exceptions.ConfigurationException;
import weblogic.deploy.api.spi.WebLogicDeploymentConfiguration;
import weblogic.descriptor.DescriptorBean;
import weblogic.j2ee.descriptor.wl.AnnotatedClassBeanDConfig;
import weblogic.j2ee.descriptor.wl.AnnotatedFieldBeanDConfig;
import weblogic.j2ee.descriptor.wl.AnnotationInstanceBeanDConfig;
import weblogic.j2ee.descriptor.wl.AnnotationOverridesBeanDConfig;
import weblogic.j2ee.descriptor.wl.ArrayMemberBeanDConfig;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.MemberBeanDConfig;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.VariableDefinitionBean;
import weblogic.management.scripting.WLScriptContext;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.utils.StringUtils;

public class WLSTPlanImpl implements WLSTPlan {
   WebLogicDeploymentConfiguration wdc = null;
   WLScriptContext ctx = null;
   private WLSTMsgTextFormatter txtFmt;
   String planPath = null;
   public Object cmo = null;
   private boolean beanLevel = true;
   private boolean typeLevel = false;
   private boolean typesLevel = false;

   public WLSTPlanImpl(WebLogicDeploymentConfiguration wdc, WLScriptContext ctx, String planPath) {
      this.wdc = wdc;
      this.ctx = ctx;
      this.txtFmt = ctx.getWLSTMsgFormatter();
      this.planPath = planPath;
      this.cmo = wdc.getPlan();
   }

   public VariableBean[] getVariables() {
      if (this.getVarDef() == null) {
         this.ctx.println(this.txtFmt.getNoPlanVariablesOverwritten());
         return null;
      } else {
         return this.wdc.getPlan().getVariableDefinition().getVariables();
      }
   }

   public VariableBean[] getVars() {
      return this.getVariables();
   }

   public void showVariables() {
      TreeMap attrs = new TreeMap();
      if (this.getVarDef() == null) {
         this.ctx.println(this.txtFmt.getNoPlanVariablesOverwritten());
      } else {
         VariableBean[] vars = this.wdc.getPlan().getVariableDefinition().getVariables();

         for(int i = 0; i < vars.length; ++i) {
            VariableBean bean = vars[i];
            if (bean.getName() != null) {
               attrs.put(bean.getName() + this.ctx.calculateTabSpace(bean.getName(), 35), bean.getValue());
            }
         }

         if (attrs.size() > 0) {
            TreeMap map1 = new TreeMap();
            map1.put(this.txtFmt.getNameHeader() + this.ctx.calculateTabSpace(this.txtFmt.getNameHeader(), 35), this.txtFmt.getValueHeader());
            TreeMap map2 = new TreeMap();
            map2.put("-----" + this.ctx.calculateTabSpace("-----", 35), "-----");
            this.ctx.printNameValuePairs(map1);
            this.ctx.printNameValuePairs(map2);
            this.ctx.printNameValuePairs(attrs);
         } else {
            this.ctx.println(this.txtFmt.getNoPlanVariablesOverwritten());
         }

      }
   }

   public void showVars() {
      this.showVariables();
   }

   public void setVariableValue(String name, String value) {
      if (this.getVarDef() == null) {
         this.ctx.println(this.txtFmt.getNoPlanVariablesOverwritten());
      } else {
         VariableBean[] vars = this.wdc.getPlan().getVariableDefinition().getVariables();
         boolean found = false;

         for(int i = 0; i < vars.length; ++i) {
            if (vars[i].getName().equals(name)) {
               vars[i].setValue(value);
               this.ctx.println(this.txtFmt.getPlanVariableOverwritten(name, value));
               found = true;
            }
         }

         if (!found) {
            this.ctx.println(this.txtFmt.getNoSuchPlanVariable(name));
         }

      }
   }

   public void setVarValue(String name, String value) {
      this.setVariableValue(name, value);
   }

   public VariableBean getVariable(String name) {
      if (this.getVarDef() == null) {
         this.ctx.println(this.txtFmt.getNoPlanVariablesOverwritten());
         return null;
      } else {
         VariableBean[] vars = this.wdc.getPlan().getVariableDefinition().getVariables();

         for(int i = 0; i < vars.length; ++i) {
            String v = vars[i].getName();
            if (v != null && v.equals(name)) {
               return vars[i];
            }
         }

         this.ctx.println(this.txtFmt.getNoSuchPlanVariable(name));
         return null;
      }
   }

   public VariableBean getVar(String name) {
      return this.getVariable(name);
   }

   public VariableBean createVariable(String name, String value) {
      if (this.getVarDef() == null) {
         this.ctx.println(this.txtFmt.getNoPlanVariablesOverwritten());
         return null;
      } else {
         VariableBean vb = this.wdc.getPlan().getVariableDefinition().createVariable();
         vb.setName(name);
         vb.setValue(value);
         return vb;
      }
   }

   public VariableBean createVar(String name, String value) {
      return this.createVariable(name, value);
   }

   public void destroyVariable(String name) {
      if (this.getVarDef() == null) {
         this.ctx.println(this.txtFmt.getNoPlanVariablesOverwritten());
      } else {
         VariableBean[] vars = this.wdc.getPlan().getVariableDefinition().getVariables();

         for(int i = 0; i < vars.length; ++i) {
            String v = vars[i].getName();
            if (v != null && v.equals(name)) {
               this.wdc.getPlan().getVariableDefinition().destroyVariable(vars[i]);
               return;
            }
         }

         this.ctx.println(this.txtFmt.getNoSuchPlanVariable(name));
      }
   }

   public void destroyVar(String name) {
      this.destroyVariable(name);
   }

   public DeploymentPlanBean getDeploymentPlan() {
      return this.wdc.getPlan();
   }

   public void save() throws FileNotFoundException, ConfigurationException, IOException {
      FileOutputStream fos = new FileOutputStream(this.planPath);
      this.wdc.save(fos);
      fos.flush();
      fos.close();
   }

   private VariableDefinitionBean getVarDef() {
      return this.wdc.getPlan().getVariableDefinition();
   }

   public String getVariableValue(String name) {
      VariableBean vb = this.getVariable(name);
      return vb != null ? vb.getValue() : null;
   }

   public String getVarValue(String name) {
      return this.getVariableValue(name);
   }

   public ModuleOverrideBean createModuleOverride(String name, String type) {
      return null;
   }

   public ModuleOverrideBean getModuleOverride(String name) {
      ModuleOverrideBean[] mobeans = this.wdc.getPlan().getModuleOverrides();

      for(int i = 0; i < mobeans.length; ++i) {
         String moName = mobeans[i].getModuleName();
         if (moName != null && moName.equals(name)) {
            return mobeans[i];
         }
      }

      this.ctx.println(this.txtFmt.getNoSuchModuleOverride(name));
      return null;
   }

   public ModuleOverrideBean getMO(String name) {
      return this.getModuleOverride(name);
   }

   public ModuleOverrideBean[] getModuleOverrides() {
      return this.wdc.getPlan().getModuleOverrides();
   }

   public ModuleOverrideBean[] getMOs() {
      return this.getModuleOverrides();
   }

   public void showModuleOverrides() {
      TreeMap attrs = new TreeMap();
      ModuleOverrideBean[] mobeans = this.wdc.getPlan().getModuleOverrides();

      for(int i = 0; i < mobeans.length; ++i) {
         String moName = mobeans[i].getModuleName();
         if (moName != null) {
            attrs.put(moName + this.ctx.calculateTabSpace(moName, 35), mobeans[i].getModuleType());
         }
      }

      if (attrs.size() > 0) {
         TreeMap map1 = new TreeMap();
         map1.put("Module Name" + this.ctx.calculateTabSpace("Module Name", 35), "Module Type");
         TreeMap map2 = new TreeMap();
         map2.put("-----------" + this.ctx.calculateTabSpace("-----------", 35), "-----------");
         this.ctx.printNameValuePairs(map1);
         this.ctx.printNameValuePairs(map2);
         this.ctx.printNameValuePairs(attrs);
      } else {
         this.ctx.println(this.txtFmt.getNoModuleOverrides());
      }

   }

   public void showMOs() {
      this.showModuleOverrides();
   }

   public void showModuleDescriptors() {
      ModuleOverrideBean[] mobeans = this.wdc.getPlan().getModuleOverrides();

      for(int i = 0; i < mobeans.length; ++i) {
         String moName = mobeans[i].getModuleName();
         if (moName != null) {
            ModuleDescriptorBean[] mdBeans = mobeans[i].getModuleDescriptors();

            for(int k = 0; k < mdBeans.length; ++k) {
               this.ctx.println(mobeans[i].getModuleName());
               this.ctx.println("   |");
               this.ctx.println("  " + mdBeans[k].getUri());
            }
         }

         this.ctx.println("------------------------------------------------------------");
      }

   }

   public void showMDs() {
      this.showModuleDescriptors();
   }

   public ModuleDescriptorBean createModuleDescriptor(String uri, String moduleOverideName) {
      this.ctx.println(this.txtFmt.getCreatingModuleDescriptor(uri, moduleOverideName));
      ModuleOverrideBean orBean = this.getModuleOverride(moduleOverideName);
      ModuleDescriptorBean mdBean = null;
      if (orBean != null) {
         mdBean = orBean.createModuleDescriptor();
         mdBean.setUri(uri);
         this.ctx.println(this.txtFmt.getCreatedModuleDescriptor(uri, moduleOverideName));
         return mdBean;
      } else {
         return mdBean;
      }
   }

   public ModuleDescriptorBean createMD(String uri, String moduleOverideName) {
      return this.createModuleDescriptor(uri, moduleOverideName);
   }

   public void destroyModuleOverride(String name) {
      ModuleOverrideBean[] moBeans = this.wdc.getPlan().getModuleOverrides();

      for(int i = 0; i < moBeans.length; ++i) {
         if (moBeans[i].getModuleName() != null && moBeans[i].getModuleName().equals(name)) {
            this.wdc.getPlan().destroyModuleOverride(moBeans[i]);
            this.ctx.println(this.txtFmt.getDestroyedModuleOverride(name));
            return;
         }
      }

      this.ctx.println(this.txtFmt.getNoSuchModuleOverride(name));
   }

   public void destroyMO(String name) {
      this.destroyModuleOverride(name);
   }

   public VariableAssignmentBean createVariableAssignment(String name, String moduleOverrideName, String moduleDescriptorUri) {
      VariableAssignmentBean vaBean = null;
      this.ctx.println(this.txtFmt.getCreatingVariableAssignment(moduleOverrideName, moduleDescriptorUri));
      ModuleDescriptorBean mdBean = this.getModuleDescriptor(moduleDescriptorUri, moduleOverrideName);
      if (mdBean != null) {
         vaBean = mdBean.createVariableAssignment();
         vaBean.setName(name);
         this.ctx.println(this.txtFmt.getCreatedVariableAssignment(name));
         return vaBean;
      } else {
         this.ctx.println(this.txtFmt.getErrorCreatingVariableAssignment(name));
         return vaBean;
      }
   }

   public VariableAssignmentBean createVA(String name, String moduleOverrideName, String moduleDescriptorUri) {
      return this.createVariableAssignment(name, moduleOverrideName, moduleDescriptorUri);
   }

   public void showVariableAssignments() {
      ModuleOverrideBean[] moBeans = this.wdc.getPlan().getModuleOverrides();

      for(int i = 0; i < moBeans.length; ++i) {
         ModuleDescriptorBean[] mdBeans = moBeans[i].getModuleDescriptors();

         for(int j = 0; j < mdBeans.length; ++j) {
            VariableAssignmentBean[] vaBeans = mdBeans[j].getVariableAssignments();

            for(int k = 0; k < vaBeans.length; ++k) {
               this.ctx.println(moBeans[i].getModuleName());
               this.ctx.println("   |");
               this.ctx.println("  " + mdBeans[j].getUri());
               this.ctx.println("     |");
               this.ctx.println("    " + vaBeans[k].getName());
            }
         }

         this.ctx.println("------------------------------------------------------------");
      }

   }

   public void showVAs() {
      this.showVariableAssignments();
   }

   public VariableAssignmentBean getVariableAssignment(String name, String moduleOverrideName, String moduleDescriptorUri) {
      ModuleDescriptorBean mdBean = this.getModuleDescriptor(moduleDescriptorUri, moduleOverrideName);
      if (mdBean != null) {
         VariableAssignmentBean[] vaBeans = mdBean.getVariableAssignments();

         for(int i = 0; i < vaBeans.length; ++i) {
            if (vaBeans[i].getName() != null && vaBeans[i].getName().equals(name)) {
               return vaBeans[i];
            }
         }
      }

      this.ctx.println(this.txtFmt.getErrorGetingVariableAssignment(name));
      return null;
   }

   public VariableAssignmentBean getVA(String name, String moduleOverrideName, String moduleDescriptorUri) {
      return this.getVariableAssignment(name, moduleOverrideName, moduleDescriptorUri);
   }

   public void destroyVariableAssignment(String name, String moduleOverrideName, String moduleDescriptorUri) {
      this.ctx.println(this.txtFmt.getDestroyingVariableAssignment(moduleOverrideName, moduleDescriptorUri));
      ModuleDescriptorBean mdBean = this.getModuleDescriptor(moduleDescriptorUri, moduleOverrideName);
      if (mdBean != null) {
         VariableAssignmentBean[] vaBeans = mdBean.getVariableAssignments();

         for(int i = 0; i < vaBeans.length; ++i) {
            if (vaBeans[i].getName() != null && vaBeans[i].getName().equals(name)) {
               mdBean.destroyVariableAssignment(vaBeans[i]);
               this.ctx.println(this.txtFmt.getDestroyedVariableAssignment(name));
               return;
            }
         }
      }

      this.ctx.println(this.txtFmt.getErrorDestroyingVariableAssignment(name));
   }

   public void destroyVA(String name, String moduleOverrideName, String moduleDescriptorUri) {
      this.destroyVariableAssignment(name, moduleOverrideName, moduleDescriptorUri);
   }

   public ModuleDescriptorBean getModuleDescriptor(String uri, String moduleOverideName) {
      ModuleOverrideBean moBean = this.getModuleOverride(moduleOverideName);
      if (moBean != null) {
         ModuleDescriptorBean[] mdBeans = moBean.getModuleDescriptors();

         for(int i = 0; i < mdBeans.length; ++i) {
            if (mdBeans[i].getUri() != null && mdBeans[i].getUri().equals(uri)) {
               return mdBeans[i];
            }
         }
      }

      this.ctx.println(this.txtFmt.getCannotFindModuleDescriptor());
      return null;
   }

   public ModuleDescriptorBean getMD(String uri, String moduleOverideName) {
      return this.getModuleDescriptor(uri, moduleOverideName);
   }

   public DConfigBean getDConfigBean(ModuleDescriptorBean moduleDescriptor) {
      if (!moduleDescriptor.isExternal()) {
      }

      DescriptorBean db = (DescriptorBean)moduleDescriptor;
      DescriptorBean parent = db.getParentBean();
      if (!(parent instanceof ModuleOverrideBean)) {
         this.ctx.println(this.txtFmt.getParentIsNotModuleOverride());
         return null;
      } else {
         DConfigBean dcb = null;
         ModuleOverrideBean override = (ModuleOverrideBean)parent;
         String moduleType = override.getModuleType();
         String moduleName = override.getModuleName();
         String descriptorUri = moduleDescriptor.getUri();
         DeployableObject deployableObject = null;
         if (moduleType.equals(ModuleType.EAR.toString())) {
            deployableObject = this.wdc.getDeployableObject();
         } else {
            J2eeApplicationObject app = (J2eeApplicationObject)this.wdc.getDeployableObject();
            deployableObject = app.getDeployableObject(moduleName);
         }

         if (deployableObject == null) {
            this.ctx.println(this.txtFmt.getCouldNotGetDeployableObject());
            return null;
         } else {
            InputStream is = deployableObject.getEntry(descriptorUri);
            if (is == null) {
               this.ctx.println(this.txtFmt.getCouldNotOpenDescriptorUri(descriptorUri));
            } else {
               label127: {
                  DDBeanRoot ddRoot;
                  try {
                     DConfigBeanRoot configRoot = this.wdc.getDConfigBeanRoot(deployableObject.getDDBeanRoot());
                     ddRoot = deployableObject.getDDBeanRoot(descriptorUri);
                     dcb = configRoot.getDConfigBean(ddRoot);
                     break label127;
                  } catch (Exception var22) {
                     this.ctx.println(this.txtFmt.getErrorBuildingDConfigBean(var22.getMessage()));
                     ddRoot = null;
                  } finally {
                     if (is != null) {
                        try {
                           is.close();
                        } catch (IOException var21) {
                        }
                     }

                  }

                  return ddRoot;
               }
            }

            if (dcb != null) {
               this.ctx.println("DConfigBean type:" + dcb.getClass().getName());
               this.ctx.println("DConfigBean:" + dcb.toString());
            } else {
               this.ctx.println("DconfigBean is NULL");
            }

            return dcb;
         }
      }
   }

   public void applyOverride(String uri, String moduleName, String key, String value) {
      ModuleDescriptorBean mdb = this.getModuleDescriptor(uri, moduleName);
      if (mdb == null) {
         this.ctx.println(this.txtFmt.getModuleDescriptorBeanDoesNotExist(uri, moduleName));
      } else {
         DConfigBean dcb = this.getDConfigBean(mdb);
         if (dcb instanceof AnnotationOverridesBeanDConfig) {
            AnnotationOverridesBeanDConfig aob = (AnnotationOverridesBeanDConfig)dcb;
            AnnotatedClassBeanDConfig[] acBeans = aob.getAnnotatedClasses();

            for(int i = 0; i < acBeans.length; ++i) {
               String className = acBeans[i].getAnnotatedClassName();
               AnnotationInstanceBeanDConfig[] aiBeans = acBeans[i].getAnnotations();

               String annotationClassName;
               int j;
               for(int j = 0; j < aiBeans.length; ++j) {
                  String annotationClassName = aiBeans[j].getAnnotationClassName();
                  MemberBeanDConfig[] mBeans = aiBeans[j].getMembers();

                  for(int k = 0; k < mBeans.length; ++k) {
                     String memberName = mBeans[k].getMemberName();
                     annotationClassName = moduleName + "/" + className + "/" + annotationClassName + "/" + memberName;
                     if (key.equals(annotationClassName)) {
                        this.ctx.printDebug("Overriding the value from " + mBeans[k].getOverrideValue() + " to " + value);
                        mBeans[k].setOverrideValue(value);
                        return;
                     }
                  }

                  ArrayMemberBeanDConfig[] arrayMembers = aiBeans[j].getArrayMembers();

                  for(j = 0; j < arrayMembers.length; ++j) {
                     annotationClassName = arrayMembers[j].getMemberName();
                     String theKey = moduleName + "/" + className + "/" + annotationClassName + "/" + annotationClassName;
                     if (theKey.equals(key)) {
                        String[] overRides = StringUtils.splitCompletely(value, ",");
                        this.ctx.printDebug("Overriding the value from " + StringUtils.join(arrayMembers[j].getOverrideValues(), ",") + " to " + value);
                        arrayMembers[j].setOverrideValues(overRides);
                        return;
                     }
                  }
               }

               AnnotatedFieldBeanDConfig[] afb = acBeans[i].getFields();

               for(int b = 0; b < afb.length; ++b) {
                  String fieldName = afb[b].getFieldName();
                  AnnotationInstanceBeanDConfig[] _aiBeans = afb[b].getAnnotations();

                  for(j = 0; j < _aiBeans.length; ++j) {
                     annotationClassName = _aiBeans[j].getAnnotationClassName();
                     MemberBeanDConfig[] mBeans = _aiBeans[j].getMembers();

                     String memberName;
                     String theKey;
                     for(int k = 0; k < mBeans.length; ++k) {
                        String memberName = mBeans[k].getMemberName();
                        memberName = moduleName + "/" + className + "/" + fieldName + "/" + memberName;
                        if (key.equals(memberName)) {
                           theKey = mBeans[k].getMemberValue();
                           String currentOverrideValue = mBeans[k].getOverrideValue();
                           mBeans[k].setOverrideValue(value);
                           this.ctx.printDebug("Overriding the value from " + mBeans[k].getOverrideValue() + " to " + value);
                           return;
                        }
                     }

                     ArrayMemberBeanDConfig[] arrayMembers = _aiBeans[j].getArrayMembers();

                     for(int a = 0; a < arrayMembers.length; ++a) {
                        memberName = arrayMembers[a].getMemberName();
                        theKey = moduleName + "/" + className + "/" + fieldName + "/" + memberName;
                        if (theKey.equals(key)) {
                           String[] overRides = StringUtils.splitCompletely(value, ",");
                           this.ctx.printDebug("Overriding the value from " + StringUtils.join(arrayMembers[a].getOverrideValues(), ",") + " to " + value);
                           arrayMembers[a].setOverrideValues(overRides);
                           return;
                        }
                     }
                  }
               }
            }
         }

      }
   }

   public void help(String commandName) {
      this.ctx.println("No help for " + commandName);
   }
}
