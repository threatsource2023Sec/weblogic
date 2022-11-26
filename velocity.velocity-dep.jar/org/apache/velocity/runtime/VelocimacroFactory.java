package org.apache.velocity.runtime;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.apache.velocity.Template;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.directive.VelocimacroProxy;

public class VelocimacroFactory {
   private RuntimeServices rsvc = null;
   private VelocimacroManager vmManager = null;
   private boolean replaceAllowed = false;
   private boolean addNewAllowed = true;
   private boolean templateLocal = false;
   private boolean blather = false;
   private boolean autoReloadLibrary = false;
   private Vector macroLibVec = null;
   private Map libModMap;

   public VelocimacroFactory(RuntimeServices rs) {
      this.rsvc = rs;
      this.libModMap = new HashMap();
      this.vmManager = new VelocimacroManager(this.rsvc);
   }

   public void initVelocimacro() {
      synchronized(this) {
         this.setReplacementPermission(true);
         this.setBlather(true);
         this.logVMMessageInfo("Velocimacro : initialization starting.");
         this.vmManager.setNamespaceUsage(false);
         Object libfiles = this.rsvc.getProperty("velocimacro.library");
         if (libfiles != null) {
            if (libfiles instanceof Vector) {
               this.macroLibVec = (Vector)libfiles;
            } else if (libfiles instanceof String) {
               this.macroLibVec = new Vector();
               this.macroLibVec.addElement(libfiles);
            }

            for(int i = 0; i < this.macroLibVec.size(); ++i) {
               String lib = (String)this.macroLibVec.elementAt(i);
               if (lib != null && !lib.equals("")) {
                  this.vmManager.setRegisterFromLib(true);
                  this.logVMMessageInfo("Velocimacro : adding VMs from VM library template : " + lib);

                  try {
                     Template template = this.rsvc.getTemplate(lib);
                     Twonk twonk = new Twonk();
                     twonk.template = template;
                     twonk.modificationTime = template.getLastModified();
                     this.libModMap.put(lib, twonk);
                  } catch (Exception var8) {
                     this.logVMMessageInfo("Velocimacro : error using  VM library template " + lib + " : " + var8);
                  }

                  this.logVMMessageInfo("Velocimacro :  VM library template macro registration complete.");
                  this.vmManager.setRegisterFromLib(false);
               }
            }
         }

         this.setAddMacroPermission(true);
         if (!this.rsvc.getBoolean("velocimacro.permissions.allow.inline", true)) {
            this.setAddMacroPermission(false);
            this.logVMMessageInfo("Velocimacro : allowInline = false : VMs can not be defined inline in templates");
         } else {
            this.logVMMessageInfo("Velocimacro : allowInline = true : VMs can be defined inline in templates");
         }

         this.setReplacementPermission(false);
         if (this.rsvc.getBoolean("velocimacro.permissions.allow.inline.to.replace.global", false)) {
            this.setReplacementPermission(true);
            this.logVMMessageInfo("Velocimacro : allowInlineToOverride = true : VMs defined inline may replace previous VM definitions");
         } else {
            this.logVMMessageInfo("Velocimacro : allowInlineToOverride = false : VMs defined inline may NOT replace previous VM definitions");
         }

         this.vmManager.setNamespaceUsage(true);
         this.setTemplateLocalInline(this.rsvc.getBoolean("velocimacro.permissions.allow.inline.local.scope", false));
         if (this.getTemplateLocalInline()) {
            this.logVMMessageInfo("Velocimacro : allowInlineLocal = true : VMs defined inline will be local to their defining template only.");
         } else {
            this.logVMMessageInfo("Velocimacro : allowInlineLocal = false : VMs defined inline will be  global in scope if allowed.");
         }

         this.vmManager.setTemplateLocalInlineVM(this.getTemplateLocalInline());
         this.setBlather(this.rsvc.getBoolean("velocimacro.messages.on", true));
         if (this.getBlather()) {
            this.logVMMessageInfo("Velocimacro : messages on  : VM system will output logging messages");
         } else {
            this.logVMMessageInfo("Velocimacro : messages off : VM system will be quiet");
         }

         this.setAutoload(this.rsvc.getBoolean("velocimacro.library.autoreload", false));
         if (this.getAutoload()) {
            this.logVMMessageInfo("Velocimacro : autoload on  : VM system will automatically reload global library macros");
         } else {
            this.logVMMessageInfo("Velocimacro : autoload off  : VM system will not automatically reload global library macros");
         }

         this.rsvc.info("Velocimacro : initialization complete.");
      }
   }

   public boolean addVelocimacro(String name, String macroBody, String[] argArray, String sourceTemplate) {
      if (name != null && macroBody != null && argArray != null && sourceTemplate != null) {
         if (!this.canAddVelocimacro(name, sourceTemplate)) {
            return false;
         } else {
            synchronized(this) {
               this.vmManager.addVM(name, macroBody, argArray, sourceTemplate);
            }

            if (this.blather) {
               String s = "#" + argArray[0];
               s = s + "(";

               for(int i = 1; i < argArray.length; ++i) {
                  s = s + " ";
                  s = s + argArray[i];
               }

               s = s + " ) : source = ";
               s = s + sourceTemplate;
               this.logVMMessageInfo("Velocimacro : added new VM : " + s);
            }

            return true;
         }
      } else {
         this.logVMMessageWarn("Velocimacro : VM addition rejected : programmer error : arg null");
         return false;
      }
   }

   private boolean canAddVelocimacro(String name, String sourceTemplate) {
      if (this.getAutoload()) {
         for(int i = 0; i < this.macroLibVec.size(); ++i) {
            String lib = (String)this.macroLibVec.elementAt(i);
            if (lib.equals(sourceTemplate)) {
               return true;
            }
         }
      }

      if (!this.addNewAllowed) {
         this.logVMMessageWarn("Velocimacro : VM addition rejected : " + name + " : inline VMs not allowed.");
         return false;
      } else if (!this.templateLocal && this.isVelocimacro(name, sourceTemplate) && !this.replaceAllowed) {
         this.logVMMessageWarn("Velocimacro : VM addition rejected : " + name + " : inline not allowed to replace existing VM");
         return false;
      } else {
         return true;
      }
   }

   private void logVMMessageInfo(String s) {
      if (this.blather) {
         this.rsvc.info(s);
      }

   }

   private void logVMMessageWarn(String s) {
      if (this.blather) {
         this.rsvc.warn(s);
      }

   }

   public boolean isVelocimacro(String vm, String sourceTemplate) {
      synchronized(this) {
         if (this.vmManager.get(vm, sourceTemplate) != null) {
            boolean var4 = true;
            return var4;
         } else {
            return false;
         }
      }
   }

   public Directive getVelocimacro(String vmName, String sourceTemplate) {
      VelocimacroProxy vp = null;
      synchronized(this) {
         vp = this.vmManager.get(vmName, sourceTemplate);
         if (vp != null && this.getAutoload()) {
            String lib = this.vmManager.getLibraryName(vmName, sourceTemplate);
            if (lib != null) {
               try {
                  Twonk tw = (Twonk)this.libModMap.get(lib);
                  if (tw != null) {
                     Template template = tw.template;
                     long tt = tw.modificationTime;
                     long ft = template.getResourceLoader().getLastModified(template);
                     if (ft > tt) {
                        this.logVMMessageInfo("Velocimacro : autoload reload for VMs from VM library template : " + lib);
                        tw.modificationTime = ft;
                        template = this.rsvc.getTemplate(lib);
                        tw.template = template;
                        tw.modificationTime = template.getLastModified();
                     }
                  }
               } catch (Exception var13) {
                  this.logVMMessageInfo("Velocimacro : error using  VM library template " + lib + " : " + var13);
               }

               vp = this.vmManager.get(vmName, sourceTemplate);
            }
         }

         return vp;
      }
   }

   public boolean dumpVMNamespace(String namespace) {
      return this.vmManager.dumpNamespace(namespace);
   }

   private void setTemplateLocalInline(boolean b) {
      this.templateLocal = b;
   }

   private boolean getTemplateLocalInline() {
      return this.templateLocal;
   }

   private boolean setAddMacroPermission(boolean arg) {
      boolean b = this.addNewAllowed;
      this.addNewAllowed = arg;
      return b;
   }

   private boolean setReplacementPermission(boolean arg) {
      boolean b = this.replaceAllowed;
      this.replaceAllowed = arg;
      return b;
   }

   private void setBlather(boolean b) {
      this.blather = b;
   }

   private boolean getBlather() {
      return this.blather;
   }

   private void setAutoload(boolean b) {
      this.autoReloadLibrary = b;
   }

   private boolean getAutoload() {
      return this.autoReloadLibrary;
   }

   private class Twonk {
      public Template template;
      public long modificationTime;

      private Twonk() {
      }

      // $FF: synthetic method
      Twonk(Object x1) {
         this();
      }
   }
}
