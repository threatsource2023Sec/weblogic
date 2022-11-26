package weblogic.j2ee.customizers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.DescriptorHelper;
import weblogic.j2ee.descriptor.wl.ConfigResourceOverrideBean;
import weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBean;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.VariableDefinitionBean;

public class ResDeploymentPlanBeanCustomizer implements weblogic.j2ee.descriptor.wl.customizers.ResDeploymentPlanBeanCustomizer {
   private DescriptorHelper ddhelper = null;
   private ResourceDeploymentPlanBean plan;
   private List newBeans = new ArrayList();
   private int varCtr = 0;

   public ResDeploymentPlanBeanCustomizer(ResourceDeploymentPlanBean customized) {
      this.plan = customized;
   }

   private DescriptorHelper getDescriptorHelper() {
      if (this.ddhelper == null) {
         this.ddhelper = DescriptorHelper.getInstance();
      }

      return this.ddhelper;
   }

   private Object findResourceOverride(String name, Object[] resources) {
      if (resources != null) {
         Object[] var3 = resources;
         int var4 = resources.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Object resource = var3[var5];
            if (resource instanceof ConfigResourceOverrideBean) {
               if (name.equals(((ConfigResourceOverrideBean)resource).getResourceName())) {
                  return resource;
               }
            } else if (name.equals(((ExternalResourceOverrideBean)resource).getResourceName())) {
               return resource;
            }
         }
      }

      return null;
   }

   public ConfigResourceOverrideBean findConfigResourceOverride(String name) {
      return name == null ? null : (ConfigResourceOverrideBean)this.findResourceOverride(name, this.plan.getConfigResourceOverrides());
   }

   public ExternalResourceOverrideBean findExternalResourceOverride(String name) {
      return name == null ? null : (ExternalResourceOverrideBean)this.findResourceOverride(name, this.plan.getExternalResourceOverrides());
   }

   public boolean hasVariable(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      return this.findVariable(desc, bean, prop, (Object)null, false) != null;
   }

   public VariableBean findVariable(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      return this.findVariable(desc, bean, prop, (Object)null, false);
   }

   private VariableBean findVariable(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop, Object oldKeyValue, boolean oldKeyPresent) throws IllegalArgumentException {
      if (desc == null) {
         throw new IllegalArgumentException("No resource provided");
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

   public VariableAssignmentBean[] findVariableAssignments(VariableBean var) {
      List assignList = new ArrayList();
      List aBeans = new ArrayList();
      Object[] beans = this.plan.getConfigResourceOverrides();
      if (beans != null) {
         aBeans.addAll(Arrays.asList(beans));
      }

      Object[] beans = this.plan.getExternalResourceOverrides();
      if (beans != null) {
         aBeans.addAll(Arrays.asList(beans));
      }

      ExternalResourceOverrideBean[] var5 = beans;
      int var6 = beans.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object bean = var5[var7];
         VariableAssignmentBean[] assigns;
         if (bean instanceof ConfigResourceOverrideBean) {
            assigns = ((ExternalResourceOverrideBean)bean).getVariableAssignments();
         } else {
            assigns = ((ExternalResourceOverrideBean)bean).getVariableAssignments();
         }

         if (assigns != null) {
            VariableAssignmentBean[] var10 = assigns;
            int var11 = assigns.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               VariableAssignmentBean assign = var10[var12];
               if (var == this._findVar(assign)) {
                  assignList.add(assign);
               }
            }
         }
      }

      return (VariableAssignmentBean[])((VariableAssignmentBean[])assignList.toArray(new VariableAssignmentBean[0]));
   }

   public VariableAssignmentBean findVariableAssignment(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      if (desc == null) {
         throw new IllegalArgumentException("No resource provided");
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

   public VariableBean findOrCreateVariable(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop) throws IllegalArgumentException {
      return this.findOrCreateVariable(desc, bean, prop, false, (Object)null, false);
   }

   public VariableBean findOrCreateVariable(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop, boolean planBased) throws IllegalArgumentException {
      return this.findOrCreateVariable(desc, bean, prop, planBased, (Object)null, false);
   }

   public VariableBean findOrCreateVariable(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop, boolean planBased, Object oldKeyValue) throws IllegalArgumentException {
      return this.findOrCreateVariable(desc, bean, prop, planBased, oldKeyValue, true);
   }

   private VariableBean findOrCreateVariable(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop, boolean planBased, Object oldKeyValue, boolean oldKeyPresent) throws IllegalArgumentException {
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

   private String _createVarName(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop) {
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

   public Object valueOf(VariableBean var) {
      return var.getValue();
   }

   public VariableAssignmentBean assignVariable(VariableBean var, ConfigResourceOverrideBean desc, DescriptorBean bean, String prop) {
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

   private VariableAssignmentBean _createAssignment(ConfigResourceOverrideBean desc, DescriptorBean bean, String prop) {
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

   public boolean isRemovable(DescriptorBean db) throws IllegalArgumentException {
      if (db == null) {
         throw new IllegalArgumentException("No descriptor bean provided");
      } else {
         String xpathKey = this.getDescriptorHelper().buildKeyXpath(db);
         if (this.newBeans.contains(xpathKey)) {
            return true;
         } else {
            List aBeans = new ArrayList();
            Object[] beans = this.plan.getConfigResourceOverrides();
            if (beans != null) {
               aBeans.addAll(Arrays.asList(beans));
            }

            Object[] beans = this.plan.getExternalResourceOverrides();
            if (beans != null) {
               aBeans.addAll(Arrays.asList(beans));
            }

            ExternalResourceOverrideBean[] var5 = beans;
            int var6 = beans.length;

            for(int var7 = 0; var7 < var6; ++var7) {
               Object bean = var5[var7];
               VariableAssignmentBean[] assigns;
               if (bean instanceof ConfigResourceOverrideBean) {
                  assigns = ((ExternalResourceOverrideBean)bean).getVariableAssignments();
               } else {
                  assigns = ((ExternalResourceOverrideBean)bean).getVariableAssignments();
               }

               if (assigns != null) {
                  VariableAssignmentBean[] var10 = assigns;
                  int var11 = assigns.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     VariableAssignmentBean assign = var10[var12];
                     String assignsXpath = assign.getXpath();
                     if (assignsXpath.startsWith(xpathKey) && assign.getOrigin().equals("planbased") && assignsXpath.lastIndexOf(47) <= xpathKey.length()) {
                        return true;
                     }
                  }
               }
            }

            return false;
         }
      }
   }
}
