package weblogic.application.naming;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import weblogic.application.ModuleExtension;
import weblogic.diagnostics.debug.DebugLogger;
import weblogic.j2ee.J2EELogger;
import weblogic.j2ee.descriptor.EnvEntryBean;
import weblogic.j2ee.descriptor.InjectionTargetBean;

public class MergedEnvEntryBean implements EnvEntryBean {
   private String name;
   private String value;
   private String type;
   private String lookupName;
   private List contributions;
   private StringBuilder envEntryMergeErrors;
   private DebugLogger debugLogger;
   private String appId;
   private String moduleId;
   private Collection modExtensions;
   private String mappedName;
   private String id;
   private Set injectionTargets;
   private Set descriptions;

   public MergedEnvEntryBean(DebugLogger debugLogger, StringBuilder envEntryMergeErrors, String name, String appId, String moduleId, Collection modExtensions, EnvEntryBean envEntryBean) {
      this.appId = appId;
      this.moduleId = moduleId;
      this.modExtensions = modExtensions;
      this.envEntryMergeErrors = envEntryMergeErrors;
      this.debugLogger = debugLogger;
      this.contribute(envEntryBean);
   }

   StringBuilder contribute(EnvEntryBean envEntryBean) {
      if (this.contributions == null) {
         this.contributions = new LinkedList();
         this.setEnvEntryName(envEntryBean.getEnvEntryName());
         this.setEnvEntryType(envEntryBean.getEnvEntryType());
         String value = envEntryBean.getEnvEntryValue();
         if (value != null) {
            this.setEnvEntryValue(value);
            this.setEnvEntryType(envEntryBean.getEnvEntryType());
         }

         this.setLookupName(envEntryBean.getLookupName());
         this.setMappedName(envEntryBean.getMappedName());
         this.setId(envEntryBean.getId());
         this.injectionTargets = this.newSetInstanceIfNotEmpty(envEntryBean.getInjectionTargets());
         this.descriptions = this.newSetInstanceIfNotEmpty(envEntryBean.getDescriptions());
      } else {
         if (this.getEnvEntryValue() != null) {
            if (this.areStringsIncompatible(this.getEnvEntryValue(), envEntryBean.getEnvEntryValue())) {
               this.addEnvEntryMergeError("values clash");
            }

            if (envEntryBean.getLookupName() != null) {
               this.addEnvEntryMergeError("value and lookupName clash");
            }
         } else if (this.getLookupName() != null) {
            if (this.areStringsIncompatible(this.getLookupName(), envEntryBean.getLookupName())) {
               this.addEnvEntryMergeError("lookupNames clash");
            }

            if (envEntryBean.getEnvEntryValue() != null) {
               this.addEnvEntryMergeError("value and lookupName clash");
            }
         } else if (envEntryBean.getEnvEntryValue() != null) {
            this.setEnvEntryValue(envEntryBean.getEnvEntryValue());
         } else if (envEntryBean.getLookupName() != null) {
            this.setLookupName(envEntryBean.getLookupName());
         }

         if (this.areStringsIncompatible(this.getId(), envEntryBean.getId())) {
            this.addEnvEntryMergeError("ids clash");
         } else {
            this.setId(envEntryBean.getId());
         }

         if (envEntryBean.getEnvEntryValue() != null) {
            if (this.areStringsIncompatible(this.getEnvEntryType(), envEntryBean.getEnvEntryType())) {
               this.addEnvEntryMergeError("types clash");
            } else {
               this.setEnvEntryType(envEntryBean.getEnvEntryType());
            }
         }

         if (this.areStringsIncompatible(this.getMappedName(), envEntryBean.getMappedName())) {
            this.addEnvEntryMergeError("mapped names clash");
         } else {
            this.setMappedName(envEntryBean.getMappedName());
         }

         this.injectionTargets = this.appendToSet(this.injectionTargets, envEntryBean.getInjectionTargets());
         this.descriptions = this.appendToSet(this.descriptions, envEntryBean.getDescriptions());
      }

      this.contributions.add(new Contribution(this.debugLogger, envEntryBean));
      return this.envEntryMergeErrors;
   }

   private Set appendToSet(Set set, Object[] newElems) {
      if (newElems != null && newElems.length > 0) {
         if (set == null) {
            set = this.newSetInstanceIfNotEmpty(newElems);
         } else {
            Collections.addAll(set, newElems);
         }
      }

      return set;
   }

   private Set newSetInstanceIfNotEmpty(Object[] objects) {
      Set set = null;
      if (objects != null && objects.length > 0) {
         set = new LinkedHashSet();
         Collections.addAll(set, objects);
      }

      return set;
   }

   private void addEnvEntryMergeError(String reason) {
      if (this.envEntryMergeErrors == null) {
         this.envEntryMergeErrors = new StringBuilder("Incompatible multiple declarations of env-entry(s): ");
      }

      this.envEntryMergeErrors.append(reason).append(" for ").append(this.name).append(". ");
   }

   private boolean areStringsIncompatible(String value1, String value2) {
      if (value1 == null) {
         return false;
      } else if (value2 == null) {
         return false;
      } else {
         return !value1.equals(value2);
      }
   }

   public void reportIfMultiple() {
      try {
         if (this.contributions != null && this.contributions.size() > 1) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PrintWriter pw = new PrintWriter(baos);
            pw.println();
            pw.println("Multiple contributions found for jndi name in application, " + this.appId + ", module: " + this.moduleId);
            pw.println("Module Extensions:");
            Iterator var3 = this.modExtensions.iterator();

            while(var3.hasNext()) {
               ModuleExtension modExtension = (ModuleExtension)var3.next();
               pw.println("  " + modExtension.getClass().getName());
            }

            pw.println("Env Entry Beans:");
            var3 = this.contributions.iterator();

            while(var3.hasNext()) {
               Contribution contribution = (Contribution)var3.next();
               contribution.report(this.debugLogger, pw);
            }

            pw.flush();
            J2EELogger.logMultipleEnvEntryReport(baos.toString());
         }
      } catch (Throwable var5) {
         var5.printStackTrace();
      }

   }

   public void addDescription(String desc) {
      this.descriptions.add(desc);
   }

   public InjectionTargetBean createInjectionTarget() {
      throw new UnsupportedOperationException();
   }

   public void destroyInjectionTarget(InjectionTargetBean arg0) {
      throw new UnsupportedOperationException();
   }

   public String[] getDescriptions() {
      return (String[])this.descriptions.toArray(new String[this.descriptions.size()]);
   }

   public String getEnvEntryName() {
      return this.name;
   }

   public String getEnvEntryType() {
      return this.type;
   }

   public String getEnvEntryValue() {
      return this.value;
   }

   public String getId() {
      return this.id;
   }

   public InjectionTargetBean[] getInjectionTargets() {
      return (InjectionTargetBean[])this.injectionTargets.toArray(new InjectionTargetBean[this.injectionTargets.size()]);
   }

   public String getLookupName() {
      return this.lookupName;
   }

   public String getMappedName() {
      return this.mappedName;
   }

   public void removeDescription(String arg0) {
      throw new UnsupportedOperationException();
   }

   public void setDescriptions(String[] newDescrs) {
      this.descriptions = this.appendToSet(this.descriptions, newDescrs);
   }

   public void setEnvEntryName(String arg0) {
      this.name = arg0;
   }

   public void setEnvEntryType(String arg0) {
      this.type = arg0;
   }

   public void setEnvEntryValue(String arg0) {
      this.value = arg0;
   }

   public void setId(String id) {
      this.id = id;
   }

   public void setLookupName(String arg0) {
      this.lookupName = arg0;
   }

   public void setMappedName(String mappedName) {
      this.mappedName = mappedName;
   }

   private static class Contribution {
      StackTraceElement[] trace;
      EnvEntryBean envEntryBean;

      public Contribution(DebugLogger debugLogger, EnvEntryBean envEntryBean) {
         this.envEntryBean = envEntryBean;
         if (debugLogger != null && debugLogger.isDebugEnabled()) {
            this.trace = (StackTraceElement[])Thread.getAllStackTraces().get(Thread.currentThread());
         }

      }

      public void report(DebugLogger debugLogger, PrintWriter pw) {
         pw.println("  name: " + this.envEntryBean.getEnvEntryName() + ", value: " + this.envEntryBean.getEnvEntryValue() + ", lookupName: " + this.envEntryBean.getLookupName());
         if (debugLogger != null && debugLogger.isDebugEnabled() && this.trace != null) {
            pw.println("  contributed from:");
            StackTraceElement[] var3 = this.trace;
            int var4 = var3.length;

            for(int var5 = 0; var5 < var4; ++var5) {
               StackTraceElement elem = var3[var5];
               pw.println("    " + elem);
            }
         }

      }
   }
}
