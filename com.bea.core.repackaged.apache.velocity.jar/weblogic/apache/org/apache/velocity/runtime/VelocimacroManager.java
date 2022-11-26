package weblogic.apache.org.apache.velocity.runtime;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.Hashtable;
import weblogic.apache.org.apache.velocity.context.InternalContextAdapter;
import weblogic.apache.org.apache.velocity.runtime.directive.VelocimacroProxy;
import weblogic.apache.org.apache.velocity.runtime.parser.node.SimpleNode;
import weblogic.apache.org.apache.velocity.util.StringUtils;

public class VelocimacroManager {
   private RuntimeServices rsvc = null;
   private static String GLOBAL_NAMESPACE = "";
   private boolean registerFromLib = false;
   private Hashtable namespaceHash = new Hashtable();
   private Hashtable libraryMap = new Hashtable();
   private boolean namespacesOn = true;
   private boolean inlineLocalMode = false;

   VelocimacroManager(RuntimeServices rs) {
      this.rsvc = rs;
      this.addNamespace(GLOBAL_NAMESPACE);
   }

   public boolean addVM(String vmName, String macroBody, String[] argArray, String namespace) {
      MacroEntry me = new MacroEntry(this, vmName, macroBody, argArray, namespace);
      me.setFromLibrary(this.registerFromLib);
      boolean isLib = true;
      if (this.registerFromLib) {
         this.libraryMap.put(namespace, namespace);
      } else {
         isLib = this.libraryMap.containsKey(namespace);
      }

      if (!isLib && this.usingNamespaces(namespace)) {
         Hashtable local = this.getNamespace(namespace, true);
         local.put(vmName, me);
         return true;
      } else {
         MacroEntry exist = (MacroEntry)this.getNamespace(GLOBAL_NAMESPACE).get(vmName);
         if (exist != null) {
            me.setFromLibrary(exist.getFromLibrary());
         }

         this.getNamespace(GLOBAL_NAMESPACE).put(vmName, me);
         return true;
      }
   }

   public VelocimacroProxy get(String vmName, String namespace) {
      if (this.usingNamespaces(namespace)) {
         Hashtable local = this.getNamespace(namespace, false);
         if (local != null) {
            MacroEntry me = (MacroEntry)local.get(vmName);
            if (me != null) {
               return me.createVelocimacro(namespace);
            }
         }
      }

      MacroEntry me = (MacroEntry)this.getNamespace(GLOBAL_NAMESPACE).get(vmName);
      return me != null ? me.createVelocimacro(namespace) : null;
   }

   public boolean dumpNamespace(String namespace) {
      synchronized(this) {
         if (this.usingNamespaces(namespace)) {
            Hashtable h = (Hashtable)this.namespaceHash.remove(namespace);
            if (h == null) {
               boolean var4 = false;
               return var4;
            } else {
               h.clear();
               boolean var5 = true;
               return var5;
            }
         } else {
            boolean var3 = false;
            return var3;
         }
      }
   }

   public void setNamespaceUsage(boolean b) {
      this.namespacesOn = b;
   }

   public void setRegisterFromLib(boolean b) {
      this.registerFromLib = b;
   }

   public void setTemplateLocalInlineVM(boolean b) {
      this.inlineLocalMode = b;
   }

   private Hashtable getNamespace(String namespace) {
      return this.getNamespace(namespace, false);
   }

   private Hashtable getNamespace(String namespace, boolean addIfNew) {
      Hashtable h = (Hashtable)this.namespaceHash.get(namespace);
      if (h == null && addIfNew) {
         h = this.addNamespace(namespace);
      }

      return h;
   }

   private Hashtable addNamespace(String namespace) {
      Hashtable h = new Hashtable();
      Object oh;
      if ((oh = this.namespaceHash.put(namespace, h)) != null) {
         this.namespaceHash.put(namespace, oh);
         return null;
      } else {
         return h;
      }
   }

   private boolean usingNamespaces(String namespace) {
      if (!this.namespacesOn) {
         return false;
      } else {
         return this.inlineLocalMode;
      }
   }

   public String getLibraryName(String vmName, String namespace) {
      if (this.usingNamespaces(namespace)) {
         Hashtable local = this.getNamespace(namespace, false);
         if (local != null) {
            MacroEntry me = (MacroEntry)local.get(vmName);
            if (me != null) {
               return null;
            }
         }
      }

      MacroEntry me = (MacroEntry)this.getNamespace(GLOBAL_NAMESPACE).get(vmName);
      return me != null ? me.getSourceTemplate() : null;
   }

   protected class MacroEntry {
      String macroname;
      String[] argarray;
      String macrobody;
      String sourcetemplate;
      SimpleNode nodeTree = null;
      VelocimacroManager manager = null;
      boolean fromLibrary = false;

      MacroEntry(VelocimacroManager vmm, String vmName, String macroBody, String[] argArray, String sourceTemplate) {
         this.macroname = vmName;
         this.argarray = argArray;
         this.macrobody = macroBody;
         this.sourcetemplate = sourceTemplate;
         this.manager = vmm;
      }

      public void setFromLibrary(boolean b) {
         this.fromLibrary = b;
      }

      public boolean getFromLibrary() {
         return this.fromLibrary;
      }

      public SimpleNode getNodeTree() {
         return this.nodeTree;
      }

      public String getSourceTemplate() {
         return this.sourcetemplate;
      }

      VelocimacroProxy createVelocimacro(String namespace) {
         VelocimacroProxy vp = new VelocimacroProxy();
         vp.setName(this.macroname);
         vp.setArgArray(this.argarray);
         vp.setMacrobody(this.macrobody);
         vp.setNodeTree(this.nodeTree);
         vp.setNamespace(namespace);
         return vp;
      }

      void setup(InternalContextAdapter ica) {
         if (this.nodeTree == null) {
            this.parseTree(ica);
         }

      }

      void parseTree(InternalContextAdapter ica) {
         try {
            BufferedReader br = new BufferedReader(new StringReader(this.macrobody));
            this.nodeTree = VelocimacroManager.this.rsvc.parse(br, "VM:" + this.macroname, true);
            this.nodeTree.init(ica, (Object)null);
         } catch (Exception var3) {
            VelocimacroManager.this.rsvc.error("VelocimacroManager.parseTree() : exception " + this.macroname + " : " + StringUtils.stackTrace(var3));
         }

      }
   }
}
