package weblogic.management.scripting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
import javax.management.AttributeNotFoundException;
import javax.management.ObjectName;
import weblogic.management.scripting.utils.WLSTMsgTextFormatter;
import weblogic.utils.StringUtils;

public class BrowseHelper {
   WLScriptContext ctx = null;
   String cdArgument = "";
   boolean cdDone = false;
   String nextCdValue = "";
   boolean incrementCdCount = false;
   boolean configNavigatedBefore = false;
   boolean runtimeNavigatedBefore = false;
   private WLSTMsgTextFormatter txtFmt;

   public BrowseHelper(WLScriptContext ctx) {
      this.txtFmt = ctx.getWLSTMsgFormatter();
   }

   void changeToBeanLevel() {
      this.ctx.inMBeanType = false;
      this.ctx.atBeanLevel = true;
      this.ctx.inMBeanTypes = false;
   }

   void changeToMBeanTypeLevel() {
      this.ctx.inMBeanType = true;
      this.ctx.atBeanLevel = false;
      this.ctx.inMBeanTypes = false;
   }

   void changeToMBeanTypesLevel() {
      this.ctx.inMBeanType = false;
      this.ctx.atBeanLevel = false;
      this.ctx.inMBeanTypes = true;
   }

   void popAndPeek() {
      this.ctx.prompts.pop();
      this.ctx.beans.pop();
      this.ctx.wlcmo = this.ctx.beans.peek();
   }

   String getSanitizedName(ObjectName on) {
      String result = on.getKeyProperty("Name");
      return result == null ? "NO_NAME" : result;
   }

   void populateNames(Object obj) throws ScriptException {
      try {
         if (obj instanceof ObjectName) {
            try {
               this.ctx.wlInstanceObjName_name = (String)this.ctx.getMBSConnection(this.ctx.domainType).getAttribute((ObjectName)obj, "Name");
               if (this.ctx.wlInstanceObjName_name == null) {
                  this.ctx.wlInstanceObjName_name = this.getSanitizedName((ObjectName)obj);
               }
            } catch (AttributeNotFoundException var5) {
               this.ctx.wlInstanceObjName_name = this.getSanitizedName((ObjectName)obj);
            }
         } else {
            this.ctx.wlInstanceObjNames_names = new String[this.ctx.wlInstanceObjNames.length];

            for(int i = 0; i < this.ctx.wlInstanceObjNames.length; ++i) {
               try {
                  if (this.ctx.wlInstanceObjNames[i] != null) {
                     this.ctx.wlInstanceObjNames_names[i] = (String)this.ctx.getMBSConnection(this.ctx.domainType).getAttribute(this.ctx.wlInstanceObjNames[i], "Name");
                     if (this.ctx.wlInstanceObjNames_names[i] == null) {
                        this.ctx.wlInstanceObjNames_names[i] = this.getSanitizedName(this.ctx.wlInstanceObjNames[i]);
                     }
                  }
               } catch (AttributeNotFoundException var4) {
                  this.ctx.wlInstanceObjNames_names[i] = this.getSanitizedName(this.ctx.wlInstanceObjNames[i]);
               }
            }
         }
      } catch (Throwable var6) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorPopulatingObjectNames(), var6);
      }

   }

   int determineSlipCount(String oname) {
      String[] foo = StringUtils.splitCompletely(oname, "/");
      return foo.length;
   }

   boolean delegateToDomainRuntimeHandler(String mname) {
      String s = null;
      String s1 = null;
      if (this.ctx.prompts.size() > 0) {
         s = (String)this.ctx.prompts.peek();
         s1 = (String)this.ctx.prompts.elementAt(0);
      }

      return this.ctx.domainType.equals("RuntimeDomainRuntime") && (s != null && s.equals("ServerRuntimes") || s != null && this.ctx.domainRuntimeHandler.isDomainRuntimeService(s) || mname.equals("ServerRuntimes") || this.ctx.domainRuntimeHandler.isDomainRuntimeService(mname) || s1 != null && s1.equals("ServerRuntimes") && mname.equals("..") && (this.ctx.prompts.size() == 3 || this.ctx.prompts.size() == 2) || s1 != null && this.ctx.domainRuntimeHandler.isDomainRuntimeService(s1) && mname.equals("..") && (this.ctx.prompts.size() == 3 || this.ctx.prompts.size() == 2));
   }

   boolean delegateToServerRuntimeHandler(String mname) {
      String s = null;
      String s1 = null;
      if (this.ctx.prompts.size() > 0) {
         s = (String)this.ctx.prompts.peek();
         s1 = (String)this.ctx.prompts.elementAt(0);
      }

      return this.ctx.domainType.equals("RuntimeRuntimeServerDomain") && (s != null && s.equals("ServerServices") || mname.equals("ServerServices") || s1 != null && s1.equals("ServerServices") && mname.equals("..") && (this.ctx.prompts.size() == 3 || this.ctx.prompts.size() == 2));
   }

   boolean checkNameStartsWithSlash(String mname) throws ScriptException {
      try {
         if (this.ctx.wlInstanceObjName == null && this.ctx.wlInstanceObjNames == null) {
            return false;
         }

         if (this.ctx.inMBeanType) {
            if (this.ctx.wlInstanceObjName == null) {
               return false;
            }

            String name = this.ctx.wlInstanceObjName.getKeyProperty("Name");
            if (name.equals(mname) || this.ctx.wlInstanceObjName_name.equals(mname)) {
               this.ctx.prompts.add(this.ctx.wlInstanceObjName_name);
               this.ctx.prompt = this.ctx.evaluatePrompt();
               this.changeToBeanLevel();
               this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(this.ctx.wlInstanceObjName);
               this.ctx.beans.push(this.ctx.wlcmo);
               return true;
            }
         } else if (this.ctx.inMBeanTypes) {
            for(int j = 0; j < this.ctx.wlInstanceObjNames.length; ++j) {
               ObjectName objName = this.ctx.wlInstanceObjNames[j];
               String oname = objName.getKeyProperty("Name");
               String _oname = this.ctx.wlInstanceObjNames_names[j];
               if ((oname.indexOf("/") != -1 || oname.indexOf("\\") != -1 || _oname.indexOf("/") != -1 || _oname.indexOf("\\") != -1) && (oname.equals(mname) || _oname.equals(mname))) {
                  this.ctx.prompts.add(_oname);
                  this.ctx.prompt = this.ctx.evaluatePrompt();
                  this.changeToBeanLevel();
                  this.ctx.wlcmo = this.ctx.getMBeanFromObjectName(objName);
                  this.ctx.beans.push(this.ctx.wlcmo);
                  return true;
               }
            }
         }
      } catch (Throwable var6) {
         this.ctx.throwWLSTException(this.txtFmt.getErrorCheckingSlashes(), var6);
      }

      return false;
   }

   void adminConfig() throws ScriptException {
      this.ctx.println("This tree is not supported.");
   }

   void initCommonVariables() {
      if (this.ctx.debug) {
         this.ctx.printDebug("Resetting the variables");
      }

      this.ctx.prompts = new Stack();
      this.ctx.beans = new Stack();
      this.ctx.beans.add(this.ctx.wlcmo);
      this.ctx.prompt = "";
      this.changeToBeanLevel();
      this.ctx.jndiNames = new ArrayList();
      this.ctx.initialContexts = new HashMap();
   }

   void handleJndiPop() throws ScriptException {
      if (this.ctx.prompts.size() != 0) {
         this.ctx.prompts.pop();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      }
   }

   void handleCustomDomainPop() throws ScriptException {
      if (this.ctx.inMBeanType) {
         this.ctx.atDomainLevel = true;
         this.ctx.atBeanLevel = false;
         this.ctx.inMBeanType = false;
         this.ctx.inMBeanTypes = false;
         this.popAndPeek();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else if (this.ctx.atBeanLevel) {
         this.ctx.atBeanLevel = false;
         this.changeToMBeanTypeLevel();
         this.popAndPeek();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else if (!this.ctx.atDomainLevel) {
         ;
      }
   }

   void handleDomainCustomDomainPop() throws ScriptException {
      if (this.ctx.inMBeanType) {
         this.ctx.atDomainLevel = true;
         this.ctx.atBeanLevel = false;
         this.ctx.inMBeanType = false;
         this.ctx.inMBeanTypes = false;
         this.popAndPeek();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else if (this.ctx.atBeanLevel) {
         this.ctx.atBeanLevel = false;
         this.changeToMBeanTypeLevel();
         this.popAndPeek();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else if (!this.ctx.atDomainLevel) {
         ;
      }
   }

   void handleEditCustomDomainPop() throws ScriptException {
      if (this.ctx.inMBeanType) {
         this.ctx.atDomainLevel = true;
         this.ctx.atBeanLevel = false;
         this.ctx.inMBeanType = false;
         this.ctx.inMBeanTypes = false;
         this.popAndPeek();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else if (this.ctx.atBeanLevel) {
         this.ctx.atBeanLevel = false;
         this.changeToMBeanTypeLevel();
         this.popAndPeek();
         this.ctx.prompt = this.ctx.evaluatePrompt();
      } else if (!this.ctx.atDomainLevel) {
         ;
      }
   }
}
