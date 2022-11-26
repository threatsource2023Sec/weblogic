package weblogic.j2ee.customizers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.enterprise.deploy.shared.ModuleType;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.DescriptorHelper;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.VariableDefinitionBean;

public class DeploymentPlanBeanCustomizer implements weblogic.j2ee.descriptor.wl.customizers.DeploymentPlanBeanCustomizer {
   private static final boolean debug = false;
   private DescriptorHelper ddhelper = null;
   private DeploymentPlanBean plan;
   private List newBeans = new ArrayList();
   private int varCtr = 0;

   public DeploymentPlanBeanCustomizer(DeploymentPlanBean customized) {
      this.plan = customized;
   }

   private DescriptorHelper getDescriptorHelper() {
      if (this.ddhelper == null) {
         this.ddhelper = DescriptorHelper.getInstance();
      }

      return this.ddhelper;
   }

   public ModuleOverrideBean findModuleOverride(String name) {
      if (name == null) {
         return null;
      } else {
         ModuleOverrideBean[] modules = this.plan.getModuleOverrides();
         if (modules != null) {
            for(int i = 0; i < modules.length; ++i) {
               ModuleOverrideBean module = modules[i];
               if (name.equals(module.getModuleName())) {
                  return module;
               }
            }
         }

         return null;
      }
   }

   public ModuleDescriptorBean findModuleDescriptor(String name, String uri) {
      ModuleOverrideBean module = this.findModuleOverride(name);
      if (module != null) {
         ModuleDescriptorBean[] dds = module.getModuleDescriptors();

         for(int i = 0; i < dds.length; ++i) {
            ModuleDescriptorBean dd = dds[i];
            if (dd.getUri().endsWith(uri)) {
               return dd;
            }
         }
      }

      return null;
   }

   public boolean rootModule(String moduleName) {
      return this.rootModule(this.plan.findModuleOverride(moduleName));
   }

   public boolean hasVariable(ModuleDescriptorBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      return this.findVariable(desc, bean, prop, (Object)null, false) != null;
   }

   public void findAndRemoveAllBeanVariables(ModuleDescriptorBean desc, DescriptorBean bean) throws IllegalArgumentException {
      if (desc == null) {
         throw new IllegalArgumentException("No module descriptor provided");
      } else {
         ArrayList varAssignsToRemove = new ArrayList();
         ArrayList variablesToRemove = new ArrayList();
         String xpathKey = this.getDescriptorHelper().buildKeyXpath(bean);
         VariableDefinitionBean varDef = this.plan.getVariableDefinition();
         VariableAssignmentBean[] assigns = desc.getVariableAssignments();
         VariableBean[] variables = varDef.getVariables();

         for(int i = 0; i < assigns.length; ++i) {
            if (assigns[i].getXpath().startsWith(xpathKey)) {
               varAssignsToRemove.add(assigns[i]);

               for(int j = 0; j < variables.length; ++j) {
                  if (variables[j].getName().equals(assigns[i].getName())) {
                     variablesToRemove.add(variables[j]);
                  }
               }
            }
         }

         Iterator theVarAssignments = varAssignsToRemove.iterator();

         while(theVarAssignments.hasNext()) {
            VariableAssignmentBean tmp = (VariableAssignmentBean)theVarAssignments.next();
            desc.destroyVariableAssignment(tmp);
         }

         Iterator theVariables = variablesToRemove.iterator();

         while(theVariables.hasNext()) {
            VariableBean tmp = (VariableBean)theVariables.next();
            varDef.destroyVariable(tmp);
         }

         desc.setChanged(true);
      }
   }

   public VariableBean findVariable(ModuleDescriptorBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      return this.findVariable(desc, bean, prop, (Object)null, false);
   }

   private VariableBean findVariable(ModuleDescriptorBean desc, DescriptorBean bean, String prop, Object oldKeyValue, boolean oldKeyPresent) throws IllegalArgumentException {
      if (desc == null) {
         throw new IllegalArgumentException("No module descriptor provided");
      } else {
         VariableBean var = null;
         String varName = null;
         String xpath = this.getDescriptorHelper().buildXpath(bean, prop, oldKeyValue, oldKeyPresent);
         VariableAssignmentBean[] assigns = desc.getVariableAssignments();
         if (assigns != null) {
            for(int i = 0; i < assigns.length; ++i) {
               VariableAssignmentBean assign = assigns[i];
               if (xpath.equals(assign.getXpath())) {
                  varName = assign.getName();
                  break;
               }
            }

            if (varName != null) {
               VariableDefinitionBean varDef = this.plan.getVariableDefinition();
               VariableBean[] vars = varDef.getVariables();
               if (vars != null) {
                  for(int i = 0; i < vars.length; ++i) {
                     VariableBean variableBean = vars[i];
                     if (variableBean.getName().equals(varName)) {
                        var = variableBean;
                        break;
                     }
                  }
               }
            }
         }

         return var;
      }
   }

   public VariableBean findOrCreateVariable(ModuleDescriptorBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      return this.findOrCreateVariable(desc, bean, prop, false, (Object)null, false);
   }

   public VariableBean findOrCreateVariable(ModuleDescriptorBean desc, DescriptorBean bean, String prop, boolean planBased) throws IllegalArgumentException {
      return this.findOrCreateVariable(desc, bean, prop, planBased, (Object)null, false);
   }

   public VariableBean findOrCreateVariable(ModuleDescriptorBean desc, DescriptorBean bean, String prop, boolean planBased, Object oldKeyValue) throws IllegalArgumentException {
      return this.findOrCreateVariable(desc, bean, prop, planBased, oldKeyValue, true);
   }

   private VariableBean findOrCreateVariable(ModuleDescriptorBean desc, DescriptorBean bean, String prop, boolean planBased, Object oldKeyValue, boolean oldKeyPresent) throws IllegalArgumentException {
      if (this.getDescriptorHelper().isTransient(bean, prop)) {
         throw new IllegalArgumentException("Variables can't be created for transient properties");
      } else {
         VariableBean var = this.findVariable(desc, bean, prop, oldKeyValue, oldKeyPresent);
         if (var != null) {
            return var;
         } else {
            VariableAssignmentBean assign = desc.createVariableAssignment();
            assign.setXpath(this.getDescriptorHelper().buildXpath(bean, prop, oldKeyValue, oldKeyPresent));
            if (oldKeyPresent) {
               assign.setOperation("replace");
            }

            String varName = this._createVarName(desc, bean, prop);
            assign.setName(varName);
            if (planBased) {
               String xpathKey = this.getDescriptorHelper().buildKeyXpath(bean);
               if (!this.newBeans.contains(xpathKey)) {
                  this.newBeans.add(xpathKey);
               }

               assign.setOrigin("planbased");
            }

            try {
               var = this.findVariable(desc, bean, prop, oldKeyValue, oldKeyPresent);
            } catch (Throwable var11) {
               var11.printStackTrace();
            }

            if (var != null) {
               return var;
            } else {
               var = this.plan.getVariableDefinition().createVariable();
               var.setName(varName);
               return var;
            }
         }
      }
   }

   private String _createVarName(ModuleDescriptorBean desc, DescriptorBean bean, String prop) {
      String varName = new String();
      String c = bean.getClass().getName();
      c = c.substring(c.lastIndexOf(".") + 1, c.length() - 8);
      varName = varName + c + "_";
      String key = this.getDescriptorHelper().findKey(bean);
      if (key != null) {
         varName = varName + this.getDescriptorHelper().getKeyValue(bean, key) + "_";
      } else {
         String[] keys = this.getDescriptorHelper().findKeyComponents(bean);
         if (keys != null) {
            for(int i = 0; i < keys.length; ++i) {
               String s = keys[i];
               varName = varName + this.getDescriptorHelper().getKeyValue(bean, s) + "_";
            }
         }
      }

      varName = varName + prop;
      if (!this.plan.isGlobalVariables()) {
         varName = varName + "_" + Long.toString(System.currentTimeMillis()) + Integer.toString(this.varCtr++);
      }

      return varName;
   }

   public VariableAssignmentBean[] findVariableAssignments(VariableBean var) {
      List assignList = new ArrayList();
      ModuleOverrideBean[] mobs = this.plan.getModuleOverrides();
      if (mobs != null) {
         for(int i = 0; i < mobs.length; ++i) {
            ModuleOverrideBean mob = mobs[i];
            ModuleDescriptorBean[] mdbs = mob.getModuleDescriptors();
            if (mdbs != null) {
               for(int j = 0; j < mdbs.length; ++j) {
                  ModuleDescriptorBean mdb = mdbs[j];
                  VariableAssignmentBean[] assigns = mdb.getVariableAssignments();
                  if (assigns != null) {
                     for(int k = 0; k < assigns.length; ++k) {
                        VariableAssignmentBean assign = assigns[k];
                        if (var == this._findVar(assign)) {
                           assignList.add(assign);
                        }
                     }
                  }
               }
            }
         }
      }

      return (VariableAssignmentBean[])((VariableAssignmentBean[])assignList.toArray(new VariableAssignmentBean[0]));
   }

   public VariableAssignmentBean findVariableAssignment(ModuleDescriptorBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      if (desc == null) {
         throw new IllegalArgumentException("No module descriptor provided");
      } else {
         String xpath = this.getDescriptorHelper().buildXpath(bean, prop, (Object)null, false);
         VariableAssignmentBean[] assigns = desc.getVariableAssignments();
         if (assigns != null) {
            VariableAssignmentBean[] var6 = assigns;
            int var7 = assigns.length;

            for(int var8 = 0; var8 < var7; ++var8) {
               VariableAssignmentBean assign = var6[var8];
               if (xpath.equals(assign.getXpath())) {
                  return assign;
               }
            }
         }

         return null;
      }
   }

   public Object valueOf(VariableBean var) {
      return var.getValue();
   }

   public VariableAssignmentBean assignVariable(VariableBean var, ModuleDescriptorBean desc, DescriptorBean bean, String prop) {
      VariableAssignmentBean assign = this._createAssignment(desc, bean, prop);
      assign.setName(var.getName());
      return assign;
   }

   private VariableBean _findVar(VariableAssignmentBean assign) {
      VariableBean[] vars = this.plan.getVariableDefinition().getVariables();
      if (vars != null) {
         for(int i = 0; i < vars.length; ++i) {
            VariableBean var = vars[i];
            if (var.getName().equals(assign.getName())) {
               return var;
            }
         }
      }

      return null;
   }

   private VariableAssignmentBean _createAssignment(ModuleDescriptorBean desc, DescriptorBean bean, String prop) {
      String xpath = this.getDescriptorHelper().buildXpath(bean, prop);
      VariableAssignmentBean[] assigns = desc.getVariableAssignments();
      VariableAssignmentBean assign = null;
      if (assigns != null) {
         for(int i = 0; i < assigns.length; ++i) {
            if (xpath.equals(assigns[i].getXpath())) {
               assign = assigns[i];
               break;
            }
         }
      }

      if (assign == null) {
         assign = desc.createVariableAssignment();
         assign.setXpath(xpath);
      }

      return assign;
   }

   public ModuleOverrideBean findRootModule() {
      ModuleOverrideBean[] mobs = this.plan.getModuleOverrides();
      if (mobs != null) {
         for(int i = 0; i < mobs.length; ++i) {
            if (this.rootModule(mobs[i])) {
               return mobs[i];
            }
         }
      }

      return null;
   }

   private boolean rootModule(ModuleOverrideBean module) {
      if (module == null) {
         return false;
      } else if (module.getModuleType().equals(ModuleType.EAR.toString())) {
         return true;
      } else {
         return this.plan.getModuleOverrides().length == 1;
      }
   }

   public boolean isRemovable(DescriptorBean db) throws IllegalArgumentException {
      if (db == null) {
         throw new IllegalArgumentException("No descriptor bean provided");
      } else {
         String xpathKey = this.getDescriptorHelper().buildKeyXpath(db);
         if (this.newBeans.contains(xpathKey)) {
            return true;
         } else {
            ModuleOverrideBean[] mobs = this.plan.getModuleOverrides();
            if (mobs != null) {
               for(int i = 0; i < mobs.length; ++i) {
                  ModuleOverrideBean mob = mobs[i];
                  ModuleDescriptorBean[] mdbs = mob.getModuleDescriptors();
                  if (mdbs != null) {
                     for(int j = 0; j < mdbs.length; ++j) {
                        ModuleDescriptorBean mdb = mdbs[j];
                        VariableAssignmentBean[] assigns = mdb.getVariableAssignments();
                        if (assigns != null) {
                           for(int k = 0; k < assigns.length; ++k) {
                              String assignsXpath = assigns[k].getXpath();
                              if (assignsXpath.startsWith(xpathKey) && assigns[k].getOrigin().equals("planbased") && assignsXpath.lastIndexOf(47) <= xpathKey.length()) {
                                 return true;
                              }
                           }
                        }
                     }
                  }
               }
            }

            return false;
         }
      }
   }
}
