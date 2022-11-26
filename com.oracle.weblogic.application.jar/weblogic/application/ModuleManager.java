package weblogic.application;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import weblogic.application.internal.ModuleAttributes;

public final class ModuleManager {
   private Module[] modules = new Module[0];
   private Map attributes = new HashMap(0);
   private Module[] additionalModules = new Module[0];
   private Map additionalAttributes = new HashMap(0);
   private Map additionalModuleUris;

   public ModuleManager() {
      this.additionalModuleUris = Collections.EMPTY_MAP;
   }

   public void setAdditionalModuleUris(Map additionalModuleUris) {
      this.additionalModuleUris = additionalModuleUris;
   }

   public synchronized void setModules(Module[] modules, ApplicationContextInternal appCtx) {
      Map newAttributes = new HashMap(modules.length);
      Module[] var4 = modules;
      int var5 = modules.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         Module m = var4[var6];
         ModuleAttributes modAttribs = (ModuleAttributes)this.attributes.get(m.getId());
         if (modAttribs == null) {
            modAttribs = new ModuleAttributes(m, appCtx);
         }

         newAttributes.put(m.getId(), modAttribs);
      }

      this.attributes = newAttributes;
      this.modules = modules;
   }

   public synchronized void setAdditionalModules(Module[] modules, ApplicationContextInternal appCtx) {
      if (this.additionalModules.length == 0 && this.additionalAttributes.size() == 0) {
         this.additionalModules = modules;
         Module[] var3 = modules;
         int var4 = modules.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Module m = var3[var5];
            ModuleAttributes modAttribs = new ModuleAttributes(m, appCtx);
            this.additionalAttributes.put(m.getId(), modAttribs);
         }
      }

   }

   public synchronized Module findModuleWithId(String id) {
      ModuleAttributes a = (ModuleAttributes)this.attributes.get(id);
      if (a == null) {
         a = (ModuleAttributes)this.additionalAttributes.get(id);
      }

      return a == null ? null : a.getModule();
   }

   public synchronized void merge() {
      Module[] mergedModules = new Module[this.modules.length + this.additionalModules.length];
      System.arraycopy(this.modules, 0, mergedModules, 0, this.modules.length);
      System.arraycopy(this.additionalModules, 0, mergedModules, this.modules.length, this.additionalModules.length);
      this.modules = mergedModules;
      this.additionalModules = new Module[0];
      Iterator keys = this.additionalAttributes.keySet().iterator();

      while(keys.hasNext()) {
         String key = (String)keys.next();
         this.attributes.put(key, this.additionalAttributes.get(key));
      }

      this.additionalAttributes.clear();
   }

   public synchronized List findModulesWithIds(String[] ids) {
      List rtn = new ArrayList();

      for(int i = 0; i < ids.length; ++i) {
         if (this.attributes.containsKey(ids[i])) {
            rtn.add(((ModuleAttributes)this.attributes.get(ids[i])).getModule());
         }

         if (this.additionalAttributes.containsKey(ids[i])) {
            rtn.add(((ModuleAttributes)this.additionalAttributes.get(ids[i])).getModule());
         }
      }

      return rtn;
   }

   public synchronized List findModulesWithTypes(String... types) {
      List rtn = new ArrayList();
      String[] var3 = types;
      int var4 = types.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String type = var3[var5];
         Module[] var7 = this.modules;
         int var8 = var7.length;

         int var9;
         Module module;
         for(var9 = 0; var9 < var8; ++var9) {
            module = var7[var9];
            if (type.equals(module.getType())) {
               rtn.add(module);
            }
         }

         var7 = this.additionalModules;
         var8 = var7.length;

         for(var9 = 0; var9 < var8; ++var9) {
            module = var7[var9];
            if (type.equals(module.getType())) {
               rtn.add(module);
            }
         }
      }

      return rtn;
   }

   public synchronized Module[] getModules() {
      return this.modules;
   }

   public synchronized Module[] getAdditionalModules() {
      return this.additionalModules;
   }

   public synchronized boolean validateModuleIds(String[] ids) {
      for(int i = 0; i < ids.length; ++i) {
         if (!this.isValidModuleId(ids[i])) {
            return false;
         }
      }

      return true;
   }

   public synchronized String[] getValidModuleIds(String[] ids) {
      List rtn = new ArrayList();

      for(int i = 0; i < ids.length; ++i) {
         if (this.isValidModuleId(ids[i])) {
            rtn.add(ids[i]);
         }
      }

      return (String[])((String[])rtn.toArray(new String[rtn.size()]));
   }

   public synchronized String[] getInvalidModuleIds(String[] ids) {
      List rtn = new ArrayList();

      for(int i = 0; i < ids.length; ++i) {
         if (!this.isValidModuleId(ids[i])) {
            rtn.add(ids[i]);
         }
      }

      return (String[])((String[])rtn.toArray(new String[rtn.size()]));
   }

   public synchronized Map getModuleIdToUriMapping(String[] ids) {
      Map rtn = new HashMap(ids.length);

      for(int i = 0; i < ids.length; ++i) {
         Module m = this.findModuleWithId(ids[i]);
         if (m == null) {
            rtn.put(ids[i], this.additionalModuleUris.get(ids[i]));
         } else {
            rtn.put(ids[i], this.getAttributes(m.getId()).getURI());
         }
      }

      return rtn;
   }

   public boolean areNewUris(String[] ids) {
      for(int i = 0; i < ids.length; ++i) {
         if (this.attributes.containsKey(ids[i])) {
            return false;
         }
      }

      return true;
   }

   private boolean isValidModuleId(String id) {
      return this.attributes.containsKey(id) || this.additionalModuleUris.containsKey(id) || this.additionalAttributes.containsKey(id);
   }

   public boolean isNewUri(String id) {
      return !this.attributes.containsKey(id);
   }

   public ModuleAttributes getAttributes(String id) {
      ModuleAttributes a = (ModuleAttributes)this.attributes.get(id);
      if (a == null) {
         a = (ModuleAttributes)this.additionalAttributes.get(id);
      }

      return a;
   }

   public Collection getAllAttributes() {
      return this.attributes.values();
   }

   public void reset() {
      this.modules = new Module[0];
      this.additionalModules = new Module[0];
      this.additionalModuleUris.clear();
      Iterator var1 = this.attributes.values().iterator();

      ModuleAttributes a;
      while(var1.hasNext()) {
         a = (ModuleAttributes)var1.next();
         a.clear();
      }

      var1 = this.additionalAttributes.values().iterator();

      while(var1.hasNext()) {
         a = (ModuleAttributes)var1.next();
         a.clear();
      }

      this.additionalAttributes.clear();
   }
}
