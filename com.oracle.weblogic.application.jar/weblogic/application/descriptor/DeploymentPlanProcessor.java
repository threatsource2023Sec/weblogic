package weblogic.application.descriptor;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import weblogic.application.descriptor.parser.PredicateExpression;
import weblogic.application.descriptor.parser.PredicateInfo;
import weblogic.application.descriptor.parser.StepExpression;
import weblogic.application.descriptor.parser.XPathLexer;
import weblogic.application.descriptor.parser.XPathParseResults;
import weblogic.application.descriptor.parser.XPathParser;
import weblogic.application.utils.ApplicationVersionUtils;
import weblogic.application.utils.ManagementUtils;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.EditableDescriptorManager;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.descriptor.wl.ConfigResourceOverrideBean;
import weblogic.j2ee.descriptor.wl.DeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.ExternalResourceOverrideBean;
import weblogic.j2ee.descriptor.wl.ModuleDescriptorBean;
import weblogic.j2ee.descriptor.wl.ModuleOverrideBean;
import weblogic.j2ee.descriptor.wl.ResourceDeploymentPlanBean;
import weblogic.j2ee.descriptor.wl.VariableAssignmentBean;
import weblogic.j2ee.descriptor.wl.VariableBean;
import weblogic.j2ee.descriptor.wl.VariableDefinitionBean;
import weblogic.logging.Loggable;
import weblogic.management.configuration.DomainMBean;
import weblogic.management.configuration.JMSServerMBean;
import weblogic.management.configuration.SubDeploymentMBean;
import weblogic.management.configuration.TargetMBean;
import weblogic.management.provider.internal.DescriptorManagerHelper;

public class DeploymentPlanProcessor {
   private static DebugLogger debugLogger = DebugLogger.getDebugLogger("DebugDeploymentPlan");
   private HashMap valueTable;
   private HashMap symbolTable;
   private boolean verbose;
   private boolean ignoreErrors;
   private DescriptorBean rootBean;
   private String moduleName;
   private String documentURI;
   private String resourceName;
   private HashMap methodCache;
   private PredicateMatcher predicateMatcher;

   public DeploymentPlanProcessor(DeploymentPlanBean plan, String moduleName, String documentURI, DescriptorBean rootBean, boolean enableVerbose) {
      this(plan, moduleName, documentURI, rootBean, enableVerbose, (PredicateMatcher)null);
   }

   public DeploymentPlanProcessor(DeploymentPlanBean plan, String moduleName, String documentURI, DescriptorBean rootBean, boolean enableVerbose, PredicateMatcher predicateMatcher) {
      this.valueTable = new LinkedHashMap();
      this.ignoreErrors = Boolean.getBoolean("weblogic.deploy.ignorePlanErrors");
      this.methodCache = new HashMap();
      this.rootBean = rootBean;
      this.moduleName = moduleName;
      this.documentURI = documentURI;
      if (predicateMatcher == null) {
         this.predicateMatcher = new PredicateMatcher() {
            public boolean match(DescriptorBean currBean, Object getValue, Object literalValue) {
               boolean match = literalValue.getClass().isArray() ? Arrays.equals((Object[])((Object[])getValue), (Object[])((Object[])literalValue)) : literalValue.equals(getValue);
               return match;
            }
         };
      } else {
         this.predicateMatcher = predicateMatcher;
      }

      this.verbose = enableVerbose;
      if (debugLogger.isDebugEnabled()) {
         this.verbose = true;
      }

      if (this.verbose) {
         MungerLogger.logPlanUpdateStarting(moduleName, documentURI);
      }

      if (debugLogger.isDebugEnabled()) {
         ByteArrayOutputStream buf = new ByteArrayOutputStream();

         try {
            ((DescriptorBean)plan).getDescriptor().toXML(new PrintStream(buf));
         } catch (Exception var21) {
         }

         debugLogger.debug("Plan is:\n" + buf.toString());
      }

      if (plan != null && moduleName != null && documentURI != null) {
         boolean matched = false;
         ModuleOverrideBean[] var8 = plan.getModuleOverrides();
         int var9 = var8.length;

         for(int var10 = 0; var10 < var9; ++var10) {
            ModuleOverrideBean mob = var8[var10];
            if (!mob.getModuleName().equals(moduleName)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Plan: type: = " + mob.getModuleType() + " name: " + mob.getModuleName() + " did not match " + moduleName);
               }
            } else {
               ModuleDescriptorBean[] var12 = mob.getModuleDescriptors();
               int var13 = var12.length;

               for(int var14 = 0; var14 < var13; ++var14) {
                  ModuleDescriptorBean mdb = var12[var14];
                  if (!mdb.getUri().equals(documentURI)) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Plan: document uri " + documentURI + " did not match: " + mdb.getUri());
                     }
                  } else {
                     this.initializeSymbolTable(plan);
                     matched = true;
                     VariableAssignmentBean[] var16 = mdb.getVariableAssignments();
                     int var17 = var16.length;

                     for(int var18 = 0; var18 < var17; ++var18) {
                        VariableAssignmentBean vab = var16[var18];
                        if (this.verbose) {
                           MungerLogger.logMatchingVariableAssignment(vab.getName(), vab.getXpath());
                        }

                        VariableAssignment tableEntry = new VariableAssignment(vab.getName(), (String)this.symbolTable.get(vab.getName()), vab.getOperation());
                        this.valueTable.put(vab.getXpath(), tableEntry);
                     }
                  }
               }
            }
         }

         if (matched) {
            MungerLogger.logValidPlanMerged(moduleName, documentURI);
         } else if (this.verbose) {
            MungerLogger.logNoMatchingPlanUpdates(moduleName, documentURI);
         }

      }
   }

   public DeploymentPlanProcessor(ResourceDeploymentPlanBean resourcePlan, String resourceName, DescriptorBean rootBean, boolean enableVerbose) {
      this.valueTable = new LinkedHashMap();
      this.ignoreErrors = Boolean.getBoolean("weblogic.deploy.ignorePlanErrors");
      this.methodCache = new HashMap();
      this.rootBean = rootBean;
      this.resourceName = resourceName;
      this.predicateMatcher = new PredicateMatcher() {
         public boolean match(DescriptorBean currBean, Object getValue, Object literalValue) {
            boolean match = literalValue.equals(getValue);
            return match;
         }
      };
      this.verbose = enableVerbose;
      if (debugLogger.isDebugEnabled()) {
         this.verbose = true;
      }

      if (this.verbose) {
         MungerLogger.logResourcePlanUpdateStarting(resourceName);
      }

      if (debugLogger.isDebugEnabled()) {
         ByteArrayOutputStream buf = new ByteArrayOutputStream();

         try {
            ((DescriptorBean)resourcePlan).getDescriptor().toXML(new PrintStream(buf));
         } catch (Exception var15) {
         }

         debugLogger.debug("Plan is:\n" + buf.toString());
      }

      if (resourcePlan != null && resourceName != null) {
         boolean matched = false;
         ExternalResourceOverrideBean[] var6 = resourcePlan.getExternalResourceOverrides();
         int var7 = var6.length;

         int var8;
         VariableAssignmentBean[] var10;
         int var11;
         int var12;
         VariableAssignmentBean vab;
         VariableAssignment tableEntry;
         for(var8 = 0; var8 < var7; ++var8) {
            ExternalResourceOverrideBean erob = var6[var8];
            if (!erob.getResourceName().equals(resourceName)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Resource plan: type: = " + erob.getResourceType() + " name: " + erob.getResourceName() + " did not match " + resourceName);
               }
            } else {
               this.initializeSymbolTable(resourcePlan);
               matched = true;
               var10 = erob.getVariableAssignments();
               var11 = var10.length;

               for(var12 = 0; var12 < var11; ++var12) {
                  vab = var10[var12];
                  if (this.verbose) {
                     MungerLogger.logMatchingVariableAssignment(vab.getName(), vab.getXpath());
                  }

                  tableEntry = new VariableAssignment(vab.getName(), (String)this.symbolTable.get(vab.getName()), vab.getOperation());
                  this.valueTable.put(vab.getXpath(), tableEntry);
               }
            }
         }

         ConfigResourceOverrideBean[] var17 = resourcePlan.getConfigResourceOverrides();
         var7 = var17.length;

         for(var8 = 0; var8 < var7; ++var8) {
            ConfigResourceOverrideBean crob = var17[var8];
            if (!crob.getResourceName().equals(resourceName)) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Resource plan: type: = " + crob.getResourceType() + " name: " + crob.getResourceName() + " did not match " + resourceName);
               }
            } else {
               this.initializeSymbolTable(resourcePlan);
               matched = true;
               var10 = crob.getVariableAssignments();
               var11 = var10.length;

               for(var12 = 0; var12 < var11; ++var12) {
                  vab = var10[var12];
                  if (this.verbose) {
                     MungerLogger.logMatchingVariableAssignment(vab.getName(), vab.getXpath());
                  }

                  tableEntry = new VariableAssignment(vab.getName(), (String)this.symbolTable.get(vab.getName()), vab.getOperation());
                  this.valueTable.put(vab.getXpath(), tableEntry);
               }
            }
         }

         if (matched) {
            MungerLogger.logValidResourcePlanMerged(resourceName);
         } else if (this.verbose) {
            MungerLogger.logNoMatchingResourcePlanUpdates(resourceName);
         }

      }
   }

   public DescriptorBean applyPlanOverrides() throws DeploymentPlanException {
      if (this.valueTable.isEmpty()) {
         return this.rootBean;
      } else {
         if (this.verbose) {
            if (this.resourceName != null) {
               MungerLogger.logApplyResourceOverridesStarting(this.resourceName);
            } else {
               MungerLogger.logApplyOverridesStarting(this.moduleName, this.documentURI);
            }
         }

         Iterator var1 = this.valueTable.entrySet().iterator();

         while(true) {
            VariableAssignment varAssignment;
            int operation;
            XPathParseResults results;
            while(true) {
               if (!var1.hasNext()) {
                  if (this.verbose) {
                     try {
                        ByteArrayOutputStream buf = new ByteArrayOutputStream();
                        ((EditableDescriptorManager)DescriptorManagerHelper.getDescriptorManager(true)).writeDescriptorBeanAsXML(this.rootBean, buf, "UTF-8", false);
                        MungerLogger.logDescriptorUpdatedByPlan(this.documentURI, buf.toString());
                     } catch (Throwable var13) {
                     }
                  }

                  return this.rootBean;
               }

               Map.Entry entry = (Map.Entry)var1.next();
               String xpath = (String)entry.getKey();
               varAssignment = (VariableAssignment)entry.getValue();
               operation = varAssignment.getOperation();
               if (this.verbose) {
                  MungerLogger.logApplyOverride(varAssignment.getName(), xpath, this.getOperationName(operation));
               }

               xpath = this.fixUpXPath(xpath);
               XPathLexer lexer = new XPathLexer(new StringReader(xpath));
               XPathParser parser = new XPathParser(lexer);
               results = null;

               try {
                  results = parser.start();
                  results.setXPath(xpath);
                  Collection errors = parser.getErrors();
                  if (errors != null && errors.size() > 0) {
                     String errorTxt = "";

                     Object err;
                     for(Iterator var11 = errors.iterator(); var11.hasNext(); errorTxt = errorTxt + err + "\n") {
                        err = var11.next();
                     }

                     Loggable l = MungerLogger.logXPathParseErrorLoggable(xpath, errorTxt);
                     l.log();
                     throw new DeploymentPlanException(l.getMessage());
                  }
                  break;
               } catch (Exception var14) {
                  Loggable l = MungerLogger.logXPathParseExceptionLoggable(xpath, var14);
                  l.log();
                  if (!this.ignoreErrors) {
                     throw new DeploymentPlanException(l.getMessage(), var14);
                  }
               }
            }

            try {
               switch (operation) {
                  case -1:
                  case 1:
                     this.applyAddOverride(varAssignment, results);
                     break;
                  case 0:
                  default:
                     throw new AssertionError("Invalid operation:" + operation);
                  case 2:
                     this.applyRemoveOverride(varAssignment, results);
                     break;
                  case 3:
                     this.applyReplaceOverride(varAssignment, results);
               }
            } catch (DeploymentPlanException var15) {
               if (!this.ignoreErrors) {
                  throw var15;
               }
            }
         }
      }
   }

   private String fixUpXPath(String xpath) {
      String newXPath = xpath;
      if (xpath == null) {
         return xpath;
      } else {
         if (xpath.charAt(xpath.length() - 1) == '/') {
            MungerLogger.logXPathInvalidTrailingSlash(xpath);
            newXPath = xpath.substring(0, xpath.length() - 1);
         }

         return newXPath;
      }
   }

   private String getPropertyName(DescriptorBean bean, String elementName) throws DeploymentPlanException {
      int idx = this.getPropertyIndex(bean, elementName);
      return ((AbstractDescriptorBean)bean)._getPropertyName(idx);
   }

   private int getPropertyIndex(DescriptorBean bean, String elementName) throws DeploymentPlanException {
      SchemaHelper sh = ((AbstractDescriptorBean)bean)._getSchemaHelper2();
      int idx = sh.getPropertyIndex(elementName);
      if (idx == -1) {
         throw new DeploymentPlanException("Element " + elementName + " does not exist at this level in the schema.");
      } else {
         return idx;
      }
   }

   private int getPropertyIndex(SchemaHelper sh, String elementName) throws DeploymentPlanException {
      int idx = sh.getPropertyIndex(elementName);
      if (idx == -1) {
         throw new DeploymentPlanException("Element " + elementName + " does not exist at this level in the schema.");
      } else {
         return idx;
      }
   }

   private String getOperationName(int operation) {
      switch (operation) {
         case -1:
            return "add";
         case 0:
         default:
            return "" + operation;
         case 1:
            return "add";
         case 2:
            return "remove";
         case 3:
            return "replace";
      }
   }

   private void applyAddOverride(VariableAssignment varAssignment, XPathParseResults parsedXPath) throws DeploymentPlanException {
      if (varAssignment.getValue() == null) {
         if (this.verbose) {
            MungerLogger.logSkippingOverride(varAssignment.getName(), parsedXPath.getXPath());
         }

      } else {
         Collection beans = this.findMatchingBeans(this.rootBean, parsedXPath, true, varAssignment);
         Iterator var4 = beans.iterator();

         while(var4.hasNext()) {
            DescriptorBean bean = (DescriptorBean)var4.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Plan add: processing bean: " + bean);
            }

            StepExpression step = parsedXPath.getLeafStepExpression();
            String elementName = step.getPathName();
            if (elementName != null) {
               SchemaHelper sh = ((AbstractDescriptorBean)bean)._getSchemaHelper2();
               int idx = this.getPropertyIndex(bean, elementName);
               if (sh.isArray(idx)) {
                  this.addProperty(elementName, bean, varAssignment);
               } else {
                  this.updateProperty(elementName, bean, varAssignment, varAssignment.getValue());
               }
            } else {
               MungerLogger.logXPathInvalidAddNoProperty(varAssignment.getName(), parsedXPath.getXPath());
            }
         }

      }
   }

   private void applyReplaceOverride(VariableAssignment varAssignment, XPathParseResults parsedXPath) throws DeploymentPlanException {
      if (varAssignment.getValue() == null) {
         if (this.verbose) {
            MungerLogger.logSkippingOverride(varAssignment.getName(), parsedXPath.getXPath());
         }

      } else {
         Collection beans = this.findMatchingBeans(this.rootBean, parsedXPath, false, varAssignment);
         Iterator var4 = beans.iterator();

         while(var4.hasNext()) {
            DescriptorBean bean = (DescriptorBean)var4.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Plan replace: processing bean: " + bean);
            }

            StepExpression step = parsedXPath.getLeafStepExpression();
            this.updateBeanWithProperty(bean, step, varAssignment);
         }

      }
   }

   private void applyRemoveOverride(VariableAssignment varAssignment, XPathParseResults parsedXPath) throws DeploymentPlanException {
      Collection beans = this.findMatchingBeans(this.rootBean, parsedXPath, false, varAssignment);
      Iterator var4 = beans.iterator();

      while(true) {
         while(var4.hasNext()) {
            DescriptorBean bean = (DescriptorBean)var4.next();
            StepExpression step = parsedXPath.getLeafStepExpression();
            SchemaHelper sh = step.getSchemaHelper();
            String elementName = step.getPathName();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Plan remove: processing bean: " + bean + " step: " + step + " elementName: " + elementName + " bean: " + bean);
            }

            int idx;
            DescriptorBean parentBean;
            String previousElementName;
            if (elementName != null && step.getPredicate() == null) {
               idx = this.getPropertyIndex(sh, elementName);
               if (sh.isBean(idx)) {
                  parentBean = bean.getParentBean();
                  previousElementName = this.getPropertyName(parentBean, elementName);
                  ((AbstractDescriptorBean)parentBean).unSet(previousElementName);
               } else {
                  String propertyName = this.getPropertyName(bean, elementName);
                  ((AbstractDescriptorBean)bean).unSet(propertyName);
               }

               if (this.verbose) {
                  MungerLogger.logAppliedRemoveOverride(varAssignment.getName(), elementName);
               }
            } else if (elementName != null) {
               idx = this.getPropertyIndex(sh, elementName);
               if (sh.isBean(idx)) {
                  parentBean = bean.getParentBean();
                  this.destroyProperty(parentBean, elementName, bean, varAssignment);
               } else {
                  PredicateInfo predicateInfo = step.getPredicate();
                  int index = this.getIndexFromPredicate(predicateInfo, elementName, varAssignment);
                  this.removeProperty(bean, elementName, varAssignment, index);
               }
            } else if (step.getPredicate() != null) {
               StepExpression[] stepExprs = (StepExpression[])parsedXPath.getSteps().toArray(new StepExpression[0]);
               StepExpression previousStep = stepExprs[stepExprs.length - 2];
               previousElementName = previousStep.getPathName();
               DescriptorBean parentBean = bean.getParentBean();
               this.destroyProperty(parentBean, previousElementName, bean, varAssignment);
            }
         }

         return;
      }
   }

   private Collection findMatchingBeans(DescriptorBean rootBean, XPathParseResults parsedXPath, boolean createMissingNodes, VariableAssignment varAssignment) throws DeploymentPlanException {
      if (rootBean != null && rootBean instanceof AbstractDescriptorBean) {
         if (parsedXPath != null && parsedXPath.getSteps().size() != 0) {
            ArrayList currentBeans = new ArrayList();
            currentBeans.add(rootBean);
            StepExpression[] stepExprs = (StepExpression[])parsedXPath.getSteps().toArray(new StepExpression[0]);
            StepExpression step = stepExprs[0];
            SchemaHelper rootHelper = ((AbstractDescriptorBean)rootBean)._getSchemaHelper2();
            String rootElementName = rootHelper.getRootElementName();
            if (rootElementName != null && !rootElementName.equals(step.getPathName())) {
               throw new DeploymentPlanException("XPath location " + step.getPathName() + " does not match bean " + rootElementName);
            } else {
               for(int stepIdx = 1; stepIdx < stepExprs.length; ++stepIdx) {
                  step = stepExprs[stepIdx];
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("Find beans: next step, pathName: " + step.getPathName() + " predicate: " + (step.getPredicate() == null ? "null" : "" + step.getPredicate().getExpressions()));
                  }

                  ArrayList nextBeans = new ArrayList();

                  for(int i = 0; i < currentBeans.size(); ++i) {
                     DescriptorBean currBean = (DescriptorBean)currentBeans.get(i);
                     SchemaHelper currBeanHelper = ((AbstractDescriptorBean)currBean)._getSchemaHelper2();
                     step.setSchemaHelper(currBeanHelper);
                     String pathName = step.getPathName();
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Find beans: processing : " + currBean + " for level " + pathName + " predicate: " + step.getPredicate());
                     }

                     String previousElementName;
                     if (pathName != null) {
                        int idx = this.getPropertyIndex(currBean, pathName);
                        if (!currBeanHelper.isBean(idx)) {
                           if (debugLogger.isDebugEnabled()) {
                              debugLogger.debug("Find beans: exiting " + pathName + " is not a bean");
                           }

                           return currentBeans;
                        }

                        SchemaHelper nextHelper = currBeanHelper.getSchemaHelper(idx);
                        if (nextHelper == null) {
                           throw new DeploymentPlanException("XPath location " + pathName + " does not exist in bean ");
                        }

                        previousElementName = ((AbstractDescriptorBean)currBean)._getPropertyName(idx);
                        nextBeans.addAll(this.getBeansFromParent(currBean, previousElementName, pathName, varAssignment));
                     }

                     if (step.getPredicate() != null) {
                        nextBeans = this.findMatchingBeansBasedOnPredicates(varAssignment, pathName == null ? currentBeans : nextBeans, step.getPredicate());
                     } else if (createMissingNodes && stepIdx == stepExprs.length - 2) {
                        nextBeans = this.findMatchingBeansBasedOnAssignment(varAssignment, pathName == null ? currentBeans : nextBeans, parsedXPath);
                     }

                     if (createMissingNodes && nextBeans.size() == 0 && i == currentBeans.size() - 1) {
                        if (debugLogger.isDebugEnabled()) {
                           debugLogger.debug("Create new beans: stepIdx: " + stepIdx);
                        }

                        DescriptorBean newBean;
                        if (pathName == null) {
                           DescriptorBean parent = currBean.getParentBean();
                           previousElementName = stepExprs[stepIdx - 1].getPathName();
                           int idx = this.getPropertyIndex(parent, previousElementName);
                           String propertyName = ((AbstractDescriptorBean)parent)._getPropertyName(idx);
                           newBean = this.createProperty(parent, stepExprs, stepIdx - 1, propertyName, previousElementName, varAssignment);
                        } else {
                           int idx = this.getPropertyIndex(currBean, pathName);
                           previousElementName = ((AbstractDescriptorBean)currBean)._getPropertyName(idx);
                           newBean = this.createProperty(currBean, stepExprs, stepIdx, previousElementName, pathName, varAssignment);
                        }

                        nextBeans.add(newBean);
                        break;
                     }
                  }

                  currentBeans = nextBeans;
               }

               return currentBeans;
            }
         } else {
            throw new IllegalArgumentException("Parsed XPath is null or no levels are present");
         }
      } else {
         throw new IllegalArgumentException("Deployment plan bean is null or not a descriptor bean");
      }
   }

   private ArrayList getBeansFromParent(DescriptorBean parentBean, String propertyName, String elementName, VariableAssignment varAssignment) throws DeploymentPlanException {
      String methodName = "get" + propertyName;
      Method getMethod = this.lookupMethodVariants(methodName, parentBean, propertyName);

      try {
         Object getResult = getMethod.invoke(parentBean);
         Object[] beans = null;
         if (getResult instanceof Object[]) {
            beans = (Object[])((Object[])getResult);
         } else if (getResult instanceof DescriptorBean) {
            beans = new Object[]{getResult};
         } else if (getResult != null) {
            throw new DeploymentPlanException("Unsupported bean type: " + getResult);
         }

         ArrayList returnBeans = new ArrayList();
         if (beans == null) {
            return returnBeans;
         } else {
            Object[] var10 = beans;
            int var11 = beans.length;

            for(int var12 = 0; var12 < var11; ++var12) {
               Object bean = var10[var12];
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Got bean from parent: " + bean);
               }

               returnBeans.add((DescriptorBean)bean);
            }

            return returnBeans;
         }
      } catch (Exception var14) {
         this.throwPlanOperationFailed(varAssignment, elementName, var14);
         return null;
      }
   }

   private ArrayList findMatchingBeansBasedOnPredicates(VariableAssignment varAssignment, ArrayList currentBeans, PredicateInfo predicateInfo) throws DeploymentPlanException {
      if (predicateInfo == null) {
         return currentBeans;
      } else {
         ArrayList subsetBeans = new ArrayList();
         subsetBeans.addAll(currentBeans);
         Iterator var5 = predicateInfo.getExpressions().iterator();

         while(var5.hasNext()) {
            PredicateExpression predExpr = (PredicateExpression)var5.next();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Find predicate: indexValue: " + predExpr.getIndexValue() + " key: " + predExpr.getKeyName() + " literal: " + predExpr.getLiteralValue());
            }

            Iterator var7 = currentBeans.iterator();

            while(var7.hasNext()) {
               DescriptorBean currBean = (DescriptorBean)var7.next();
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Find predicate: processing bean: " + currBean);
               }

               if (predExpr.getIndexValue() != null) {
                  int idx = this.convertPredicateIndextoInt(predExpr);
                  ArrayList matchingBeans = new ArrayList();
                  DescriptorBean idxBean = (DescriptorBean)currentBeans.get(idx - 1);
                  if (idxBean != null) {
                     matchingBeans.add(idxBean);
                  }

                  return matchingBeans;
               }

               String keyName = predExpr.getKeyName();
               String keyMatch = predExpr.getLiteralValue();
               String propertyName = this.getPropertyName(currBean, keyName);
               String methodName = "get" + propertyName;
               Method getMethod = this.lookupMethodVariants(methodName, currBean, keyName);

               try {
                  Object getValue = getMethod.invoke(currBean);
                  Object literalValue = this.convertValue(keyMatch, getMethod.getReturnType(), currBean);
                  if (!this.predicateMatcher.match(currBean, getValue, literalValue)) {
                     if (debugLogger.isDebugEnabled()) {
                        debugLogger.debug("Find predicate: literal " + literalValue + " did not match " + getValue + " removing bean: " + currBean);
                     }

                     subsetBeans.remove(currBean);
                  }
               } catch (Exception var16) {
                  this.throwPlanOperationFailed(varAssignment, keyName, var16);
               }
            }
         }

         return subsetBeans;
      }
   }

   private ArrayList findMatchingBeansBasedOnAssignment(VariableAssignment varAssignment, ArrayList currentBeans, XPathParseResults parsedXPath) throws DeploymentPlanException {
      String keyName = parsedXPath.getLeafStepExpression().getPathName();
      String value = varAssignment.getValue();
      if (keyName != null && value != null && currentBeans.size() > 0) {
         DescriptorBean currBean = (DescriptorBean)currentBeans.get(0);
         SchemaHelper sh = ((AbstractDescriptorBean)currBean)._getSchemaHelper2();
         int idx = this.getPropertyIndex(currBean, keyName);
         if (!sh.isArray(idx) && (sh.isKey(idx) || sh.isKeyComponent(idx))) {
            PredicateExpression expr = new PredicateExpression();
            expr.setKeyName(keyName);
            expr.setLiteralValue(value);
            PredicateInfo predicateInfo = new PredicateInfo();
            predicateInfo.addExpression(expr);
            return this.findMatchingBeansBasedOnPredicates(varAssignment, currentBeans, predicateInfo);
         }
      }

      return currentBeans;
   }

   private void updateBeanWithProperty(DescriptorBean bean, StepExpression step, VariableAssignment varAssignment) throws DeploymentPlanException {
      SchemaHelper sh = ((AbstractDescriptorBean)bean)._getSchemaHelper2();
      String elementName = step.getPathName();
      if (elementName != null) {
         PredicateInfo predicateInfo = step.getPredicate();
         if (predicateInfo != null && sh.isArray(this.getPropertyIndex(bean, elementName))) {
            this.updatePropertyArrayElement(elementName, bean, predicateInfo, varAssignment);
         } else {
            this.updateProperty(elementName, bean, varAssignment, varAssignment.getValue());
         }
      } else {
         if (step.getPredicate() == null) {
            throw new DeploymentPlanException("Element name or predicate not specified " + step);
         }

         String[] keyNames = sh.getKeyElementNames();
         if (keyNames == null || keyNames.length == 0) {
            Loggable l = MungerLogger.logXPathInvalidNoKeyNamesLoggable(varAssignment.getName(), bean.getClass().getName());
            l.log();
            throw new DeploymentPlanException(l.getMessage());
         }

         String keyName = keyNames[0];
         this.updateProperty(keyName, bean, varAssignment, varAssignment.getValue());
      }

   }

   private void addProperty(String elementName, DescriptorBean bean, VariableAssignment varAssignment) throws DeploymentPlanException {
      String propertyName = this.getPropertyName(bean, elementName);
      String value = varAssignment.getValue();
      String methodName = "set" + propertyName;
      Method setMethod = this.lookupMethodVariants(methodName, bean, elementName);
      methodName = "get" + propertyName;
      Method getMethod = this.lookupMethodVariants(methodName, bean, elementName);
      Class paramType = getMethod.getReturnType();
      Class compType = paramType.getComponentType();

      try {
         Object[] valueArray = (Object[])((Object[])getMethod.invoke(bean));
         int sourceLength = valueArray == null ? 0 : Array.getLength(valueArray);
         Object newArray;
         Object addValue;
         if (this.isArrayValue(value, compType)) {
            addValue = this.convertValue(value, Array.newInstance(compType, 0).getClass(), bean);
            int len = ((Object[])((Object[])addValue)).length;
            newArray = Array.newInstance(compType, sourceLength + len);
            System.arraycopy(valueArray, 0, newArray, 0, sourceLength);
            System.arraycopy(addValue, 0, newArray, sourceLength, len);
         } else {
            addValue = this.convertValue(value, compType, bean);
            newArray = Array.newInstance(compType, sourceLength + 1);
            System.arraycopy(valueArray, 0, newArray, 0, sourceLength);
            Array.set(newArray, Array.getLength(valueArray), addValue);
         }

         setMethod.invoke(bean, newArray);
      } catch (Exception var16) {
         this.throwPlanOperationFailed(varAssignment, elementName, var16);
      }

      if (this.verbose) {
         MungerLogger.logAppliedAddOverride(varAssignment.getName(), elementName, value);
      }

   }

   private void updateProperty(String elementName, DescriptorBean bean, VariableAssignment varAssignment, String value) throws DeploymentPlanException {
      String propertyName = this.getPropertyName(bean, elementName);
      String methodName = "set" + propertyName;
      Method setMethod = this.lookupMethodVariants(methodName, bean, elementName);
      if (setMethod.getParameterTypes().length != 1) {
         throw new DeploymentPlanException("Update failed due to mismatch in expected parameters");
      } else {
         try {
            Object setValues = this.convertValue(value, setMethod.getParameterTypes()[0], bean);
            setMethod.invoke(bean, setValues);
         } catch (Exception var9) {
            this.throwPlanOperationFailed(varAssignment, elementName, var9);
         }

         if (this.verbose) {
            MungerLogger.logAppliedUpdateOverride(varAssignment.getName(), elementName, value);
         }

      }
   }

   private void updatePropertyArrayElement(String elementName, DescriptorBean bean, PredicateInfo predicateInfo, VariableAssignment varAssignment) throws DeploymentPlanException {
      String propertyName = this.getPropertyName(bean, elementName);
      String value = varAssignment.getValue();
      String methodName = "set" + propertyName;
      Method setMethod = this.lookupMethodVariants(methodName, bean, elementName);
      methodName = "get" + propertyName;
      Method getMethod = this.lookupMethodVariants(methodName, bean, elementName);
      int index = this.getIndexFromPredicate(predicateInfo, elementName, varAssignment);

      try {
         Object[] valueArray = (Object[])((Object[])getMethod.invoke(bean));
         if (valueArray == null || valueArray.length <= index) {
            if (this.verbose) {
               MungerLogger.logNoMatchingArrayIdx(varAssignment.getName(), elementName, index);
            }

            return;
         }

         valueArray[index - 1] = this.convertValue(value, setMethod.getParameterTypes()[0].getComponentType(), bean);
         setMethod.invoke(bean, (Object)valueArray);
      } catch (Exception var12) {
         this.throwPlanOperationFailed(varAssignment, elementName, var12);
      }

      if (this.verbose) {
         MungerLogger.logAppliedUpdateOverride(varAssignment.getName(), elementName, value);
      }

   }

   private void removeProperty(DescriptorBean bean, String elementName, VariableAssignment varAssignment, int index) throws DeploymentPlanException {
      String propertyName = this.getPropertyName(bean, elementName);
      String methodName = "remove" + propertyName;
      Method removeMethod = this.lookupMethodVariants(methodName, bean, elementName);

      try {
         Object[] params;
         if (removeMethod.getParameterTypes().length == 1) {
            methodName = "get" + propertyName;
            Method getMethod = this.lookupMethodVariants(methodName, bean, elementName);
            Object[] valueArray = (Object[])((Object[])getMethod.invoke(bean));
            if (valueArray == null || valueArray.length <= index) {
               if (this.verbose) {
                  MungerLogger.logNoMatchingArrayIdx(varAssignment.getName(), elementName, index);
               }

               return;
            }

            params = new Object[]{valueArray[index - 1]};
         } else {
            params = new Object[0];
         }

         removeMethod.invoke(bean, params);
      } catch (Exception var11) {
         this.throwPlanOperationFailed(varAssignment, elementName, var11);
      }

      if (this.verbose) {
         MungerLogger.logAppliedRemoveOverride(varAssignment.getName(), elementName);
      }

   }

   private void destroyProperty(DescriptorBean parentBean, String elementName, DescriptorBean childBean, VariableAssignment varAssignment) throws DeploymentPlanException {
      String propertyName = this.getPropertyName(parentBean, elementName);
      String methodName = "destroy" + propertyName;
      Method destroyMethod = this.lookupMethodVariants(methodName, parentBean, elementName);
      Object[] params;
      if (destroyMethod.getParameterTypes().length == 0) {
         params = new Object[0];
      } else {
         if (destroyMethod.getParameterTypes().length != 1) {
            throw new DeploymentPlanException("Destroy element failed due to mismatch in expected parameters");
         }

         params = new Object[]{childBean};
      }

      try {
         destroyMethod.invoke(parentBean, params);
      } catch (Exception var10) {
         this.throwPlanOperationFailed(varAssignment, elementName, var10);
      }

      if (this.verbose) {
         MungerLogger.logAppliedRemoveOverride(varAssignment.getName(), elementName);
      }

   }

   private DescriptorBean createProperty(DescriptorBean parentBean, StepExpression[] stepExprs, int stepExprsIdx, String propertyName, String elementName, VariableAssignment varAssignment) throws DeploymentPlanException {
      String methodName = "create" + propertyName;
      Method createMethod = this.lookupMethodVariants(methodName, parentBean, elementName);
      DescriptorBean newBean = null;
      String newBeanName = null;
      SchemaHelper sh = ((AbstractDescriptorBean)parentBean)._getSchemaHelper2();
      int idx = this.getPropertyIndex(parentBean, elementName);
      boolean isArray = sh.isArray(idx);
      PredicateInfo predicateInfo = null;
      PredicateExpression[] predExprs = new PredicateExpression[0];
      if (stepExprs[stepExprsIdx] != null && stepExprs[stepExprsIdx].getPredicate() != null) {
         predicateInfo = stepExprs[stepExprsIdx].getPredicate();
      } else if (isArray && stepExprs.length > stepExprsIdx + 1) {
         predicateInfo = stepExprs[stepExprsIdx + 1].getPredicate();
      }

      if (predicateInfo != null && predicateInfo.getExpressions() != null) {
         predExprs = (PredicateExpression[])predicateInfo.getExpressions().toArray(new PredicateExpression[0]);
         if (predExprs.length > 0 && predExprs[0].getLiteralValue() != null) {
            newBeanName = predExprs[0].getLiteralValue();
         }
      }

      Object[] params;
      if (createMethod.getParameterTypes().length == 0) {
         params = new Object[0];
      } else {
         if (createMethod.getParameterTypes().length != 1 || newBeanName == null) {
            throw new DeploymentPlanException("Creation of new element failed due to mismatch in expected parameters");
         }

         params = new Object[]{newBeanName};
      }

      try {
         newBean = (DescriptorBean)createMethod.invoke(parentBean, params);
      } catch (Exception var21) {
         this.throwPlanOperationFailed(varAssignment, elementName, var21);
      }

      PredicateExpression[] var17 = predExprs;
      int var18 = predExprs.length;

      for(int var19 = 0; var19 < var18; ++var19) {
         PredicateExpression predExpr = var17[var19];
         this.updateProperty(predExpr.getKeyName(), newBean, varAssignment, predExpr.getLiteralValue());
      }

      int nextStepIdx = stepExprsIdx + 1;
      if (predExprs.length == 0 && nextStepIdx < stepExprs.length && stepExprs[nextStepIdx] != null && stepExprs[nextStepIdx].getPredicate() != null && stepExprs[nextStepIdx].getPathName() == null) {
         Iterator var23 = stepExprs[nextStepIdx].getPredicate().getExpressions().iterator();

         while(var23.hasNext()) {
            PredicateExpression predExpr = (PredicateExpression)var23.next();
            this.updateProperty(predExpr.getKeyName(), newBean, varAssignment, predExpr.getLiteralValue());
         }
      }

      if (this.verbose) {
         if (params.length == 0) {
            MungerLogger.logAppliedCreateOverride(varAssignment.getName(), elementName);
         } else {
            MungerLogger.logAppliedCreateNameOverride(varAssignment.getName(), elementName, newBeanName);
         }
      }

      return newBean;
   }

   private Method lookupMethodVariants(String methodName, DescriptorBean bean, String elementName) throws DeploymentPlanException {
      Method method = null;
      String name;
      if (methodName.charAt(methodName.length() - 1) == 's') {
         name = methodName.substring(0, methodName.length() - 1);
         method = this.lookupMethod(name, bean);
      }

      if (method == null && methodName.endsWith("ies")) {
         name = methodName.substring(0, methodName.length() - 3) + "y";
         method = this.lookupMethod(name, bean);
      }

      if (method == null) {
         method = this.lookupMethod(methodName, bean);
      }

      if (method == null) {
         Loggable l = MungerLogger.logNoMethodLoggable(methodName, bean.getClass().getName(), elementName);
         l.log();
         throw new DeploymentPlanException(l.getMessage());
      } else {
         return method;
      }
   }

   private Method lookupMethod(String name, DescriptorBean bean) {
      if (bean != null && name != null) {
         Class beanClass = bean.getClass();
         Method[] methods = (Method[])this.methodCache.get(beanClass);
         if (methods == null) {
            try {
               methods = beanClass.getMethods();
               this.methodCache.put(beanClass, methods);
            } catch (Exception var9) {
               if (debugLogger.isDebugEnabled()) {
                  debugLogger.debug("Error getting methods: ", var9);
               }

               return null;
            }
         }

         Method[] var5 = methods;
         int var6 = methods.length;

         for(int var7 = 0; var7 < var6; ++var7) {
            Method method = var5[var7];
            if (name.equals(method.getName())) {
               return method;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private int getIndexFromPredicate(PredicateInfo predicateInfo, String elementName, VariableAssignment varAssignment) throws DeploymentPlanException {
      PredicateExpression[] predExpr = (PredicateExpression[])predicateInfo.getExpressions().toArray(new PredicateExpression[0]);
      if (predExpr.length != 0 && predExpr[0].getIndexValue() != null) {
         return this.convertPredicateIndextoInt(predExpr[0]);
      } else {
         Loggable l = MungerLogger.logXPathInvalidNoIdxLoggable(varAssignment.getName(), elementName);
         l.log();
         throw new DeploymentPlanException(l.getMessage());
      }
   }

   private boolean isArrayValue(String value, Class valueType) {
      String[] arrayValues;
      if (valueType.isPrimitive()) {
         arrayValues = value.split(",");
         return arrayValues.length > 1;
      } else if (valueType.equals(String.class)) {
         arrayValues = value.split("\"\\s*,\\s*\"");
         return arrayValues.length > 1;
      } else {
         return false;
      }
   }

   private Object convertValue(String value, Class valueType, DescriptorBean bean) throws DeploymentPlanException {
      if (debugLogger.isDebugEnabled()) {
         debugLogger.debug("Plan: convert type: " + valueType + " value: " + value);
      }

      if (valueType.isArray()) {
         Class compType = valueType.getComponentType();
         String[] arrayValues;
         String targetName;
         int i;
         if (compType.isPrimitive()) {
            if (compType == Byte.TYPE) {
               return this.parseByteArray(value);
            }

            arrayValues = value.split(",");
            Object[] values = this.allocatePrimitiveArray(compType, arrayValues.length);
            int i = 0;
            String[] var16 = arrayValues;
            i = arrayValues.length;

            for(int var19 = 0; var19 < i; ++var19) {
               targetName = var16[var19];
               values[i++] = this.convertPrimitiveValue(targetName, compType);
            }

            return values;
         }

         if (compType.equals(String.class)) {
            arrayValues = value.split("\"\\s*,\\s*\"");
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Plan: split: array length:" + arrayValues.length);
            }

            for(int i = 0; i < arrayValues.length; ++i) {
               arrayValues[i] = this.trimQuotes(arrayValues[i]);
            }

            return arrayValues;
         }

         if (compType.equals(TargetMBean.class)) {
            arrayValues = value.split("\"\\s*,\\s*\"");
            List targets = new ArrayList();
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Plan: split: array length:" + arrayValues.length);
            }

            if (bean instanceof SubDeploymentMBean) {
               SubDeploymentMBean subDeploymentMBean = (SubDeploymentMBean)bean;
               DomainMBean domain = ManagementUtils.getDomainMBean();
               if (subDeploymentMBean.getTargets().length > 0) {
                  TargetMBean target = subDeploymentMBean.getTargets()[0];

                  DescriptorBean parent;
                  for(parent = target.getParentBean(); !(parent instanceof DomainMBean); parent = parent.getParentBean()) {
                  }

                  if (parent instanceof DomainMBean) {
                     domain = (DomainMBean)parent;
                  }
               }

               for(i = 0; i < arrayValues.length; ++i) {
                  arrayValues[i] = this.trimQuotes(arrayValues[i]);
                  String partitionName = ApplicationVersionUtils.getPartitionName(((SubDeploymentMBean)bean).getParent().getName());
                  targetName = ApplicationVersionUtils.getApplicationId(arrayValues[i], (String)null, partitionName);
                  JMSServerMBean jmsServer = domain.lookupJMSServer(targetName);
                  if (debugLogger.isDebugEnabled()) {
                     debugLogger.debug("New target " + jmsServer + " added to sub deployment " + bean);
                  }

                  if (jmsServer != null) {
                     targets.add(jmsServer);
                  }
               }
            }

            return targets.size() == 0 ? null : targets.toArray(new TargetMBean[targets.size()]);
         }
      } else if (valueType.isPrimitive()) {
         return this.convertPrimitiveValue(value, valueType);
      }

      if (valueType.equals(String.class)) {
         return this.trimQuotes(value);
      } else if (valueType.equals(Properties.class)) {
         return this.convertPropertiesValue(value);
      } else {
         throw new DeploymentPlanException("Unrecognized primitive type: " + valueType);
      }
   }

   private Properties convertPropertiesValue(String value) throws DeploymentPlanException {
      StringTokenizer st = new StringTokenizer(value, ";");
      Properties properties = new Properties();

      try {
         while(st.hasMoreTokens()) {
            properties.load(new ByteArrayInputStream(st.nextToken().getBytes()));
         }

         return properties;
      } catch (IOException var5) {
         throw new DeploymentPlanException("Error loading properties value : " + value);
      }
   }

   private byte[] parseByteArray(String value) {
      String[] elements = value.split(",");
      byte[] bytes = new byte[elements.length];

      for(int i = 0; i < elements.length; ++i) {
         if (elements[i].length() != 1) {
            return value.getBytes();
         }

         bytes[i] = Byte.parseByte(elements[i]);
      }

      return bytes;
   }

   private Object convertPrimitiveValue(String value, Class valueType) throws DeploymentPlanException {
      if (valueType == Integer.TYPE) {
         return new Integer(value);
      } else if (valueType == Boolean.TYPE) {
         return new Boolean(value);
      } else if (valueType == Byte.TYPE) {
         return new Byte(value);
      } else if (valueType == Character.TYPE) {
         return new Character(value.charAt(0));
      } else if (valueType == Short.TYPE) {
         return new Short(value);
      } else if (valueType == Long.TYPE) {
         return new Long(value);
      } else if (valueType == Float.TYPE) {
         return new Float(value);
      } else if (valueType == Double.TYPE) {
         return new Double(value);
      } else {
         throw new DeploymentPlanException("Unrecognized primitive type: " + valueType);
      }
   }

   private String trimQuotes(String val) {
      if (val == null) {
         return val;
      } else {
         int len = val.length();
         if (len < 1) {
            return val;
         } else {
            if (val.charAt(0) == '"') {
               val = val.substring(1);
            }

            if (val.charAt(val.length() - 1) == '"') {
               val = val.substring(0, val.length() - 1);
            }

            return val;
         }
      }
   }

   private Object[] allocatePrimitiveArray(Class valueType, int length) {
      if (valueType == Integer.TYPE) {
         return new Integer[length];
      } else if (valueType == Boolean.TYPE) {
         return new Boolean[length];
      } else if (valueType == Byte.TYPE) {
         return new Byte[length];
      } else if (valueType == Character.TYPE) {
         return new Character[length];
      } else if (valueType == Short.TYPE) {
         return new Short[length];
      } else if (valueType == Long.TYPE) {
         return new Long[length];
      } else if (valueType == Float.TYPE) {
         return new Float[length];
      } else {
         return (Object[])(valueType == Double.TYPE ? new Double[length] : new Object[length]);
      }
   }

   private int convertPredicateIndextoInt(PredicateExpression predExpr) throws DeploymentPlanException {
      try {
         return Integer.parseInt(predExpr.getIndexValue());
      } catch (Exception var3) {
         throw new DeploymentPlanException(var3);
      }
   }

   private void throwPlanOperationFailed(VariableAssignment varAssignment, String elementName, Exception e) throws DeploymentPlanException {
      Loggable l = MungerLogger.logPlanOperationFailedLoggable(varAssignment.getName(), elementName, e);
      l.log();
      throw new DeploymentPlanException(l.getMessage(), e);
   }

   private void initializeSymbolTable(DeploymentPlanBean plan) {
      if (this.symbolTable == null) {
         this.symbolTable = new LinkedHashMap();
         VariableDefinitionBean vdb = plan.getVariableDefinition();
         VariableBean[] var3 = vdb.getVariables();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            VariableBean variableBean = var3[var5];
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Symbol Table: " + variableBean.getName() + ", " + variableBean.getValue());
            }

            this.symbolTable.put(variableBean.getName(), variableBean.getValue());
         }

      }
   }

   private void initializeSymbolTable(ResourceDeploymentPlanBean plan) {
      if (this.symbolTable == null) {
         this.symbolTable = new LinkedHashMap();
         VariableDefinitionBean vdb = plan.getVariableDefinition();
         VariableBean[] var3 = vdb.getVariables();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            VariableBean variableBean = var3[var5];
            if (debugLogger.isDebugEnabled()) {
               debugLogger.debug("Symbol Table: " + variableBean.getName() + ", " + variableBean.getValue());
            }

            this.symbolTable.put(variableBean.getName(), variableBean.getValue());
         }

      }
   }
}
