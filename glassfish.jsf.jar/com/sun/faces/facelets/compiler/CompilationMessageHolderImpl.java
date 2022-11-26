package com.sun.faces.facelets.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class CompilationMessageHolderImpl implements CompilerPackageCompilationMessageHolder {
   private Map messageListMap;
   private CompilationManager compilationManager;

   private Map getMessageListMap() {
      if (null == this.messageListMap) {
         this.messageListMap = new HashMap();
      }

      return this.messageListMap;
   }

   public List getNamespacePrefixMessages(FacesContext context, String prefix) {
      List result = null;
      Map map = this.getMessageListMap();
      if (null == (result = (List)map.get(prefix))) {
         result = new ArrayList();
         map.put(prefix, result);
      }

      return (List)result;
   }

   public void processCompilationMessages(FacesContext context) {
      Map map = this.getMessageListMap();
      Collection values = map.values();
      Iterator var4 = values.iterator();

      while(var4.hasNext()) {
         List curList = (List)var4.next();
         Iterator var6 = curList.iterator();

         while(var6.hasNext()) {
            FacesMessage curMessage = (FacesMessage)var6.next();
            context.addMessage((String)null, curMessage);
         }
      }

   }

   public void removeNamespacePrefixMessages(String prefix) {
      Map map = this.getMessageListMap();
      map.remove(prefix);
   }

   public CompilationManager getCurrentCompositeComponentCompilationManager() {
      return this.compilationManager;
   }

   public void setCurrentCompositeComponentCompilationManager(CompilationManager manager) {
      this.compilationManager = manager;
   }
}
