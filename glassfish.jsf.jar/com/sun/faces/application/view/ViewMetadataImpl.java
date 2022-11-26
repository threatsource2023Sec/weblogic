package com.sun.faces.application.view;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.context.FacesFileNotFoundException;
import com.sun.faces.facelets.impl.DefaultFaceletFactory;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIImportConstants;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewMetadata;
import javax.faces.view.facelets.Facelet;

public class ViewMetadataImpl extends ViewMetadata {
   private String viewId;
   private DefaultFaceletFactory faceletFactory;

   public ViewMetadataImpl(String viewId) {
      this.viewId = viewId;
   }

   public String getViewId() {
      return this.viewId;
   }

   public UIViewRoot createMetadataView(FacesContext context) {
      UIViewRoot result = null;
      UIViewRoot currentViewRoot = context.getViewRoot();
      Map currentViewMapShallowCopy = Collections.emptyMap();

      try {
         context.setProcessingEvents(false);
         if (this.faceletFactory == null) {
            ApplicationAssociate associate = ApplicationAssociate.getInstance(context.getExternalContext());
            this.faceletFactory = associate.getFaceletFactory();

            assert this.faceletFactory != null;
         }

         ViewHandler vh = context.getApplication().getViewHandler();
         result = vh.createView(context, this.viewId);
         context.getAttributes().put("com.sun.faces.viewId", this.viewId);
         if (null != currentViewRoot) {
            Map currentViewMap = currentViewRoot.getViewMap(false);
            if (null != currentViewMap && !currentViewMap.isEmpty()) {
               currentViewMapShallowCopy = new HashMap(currentViewMap);
               Map resultViewMap = result.getViewMap(true);
               resultViewMap.putAll((Map)currentViewMapShallowCopy);
            }
         }

         if (null != currentViewRoot) {
            context.setViewRoot(result);
         }

         Facelet f = this.faceletFactory.getMetadataFacelet(context, result.getViewId());
         f.apply(context, result);
         importConstantsIfNecessary(context, result);
      } catch (FacesFileNotFoundException var14) {
         FacesFileNotFoundException ffnfe = var14;

         try {
            context.getExternalContext().responseSendError(404, ffnfe.getMessage());
         } catch (IOException var13) {
         }

         context.responseComplete();
      } catch (IOException var15) {
         throw new FacesException(var15);
      } finally {
         context.getAttributes().remove("com.sun.faces.viewId");
         context.setProcessingEvents(true);
         if (null != currentViewRoot) {
            context.setViewRoot(currentViewRoot);
            if (!((Map)currentViewMapShallowCopy).isEmpty()) {
               currentViewRoot.getViewMap(true).putAll((Map)currentViewMapShallowCopy);
               ((Map)currentViewMapShallowCopy).clear();
            }
         }

      }

      return result;
   }

   private static void importConstantsIfNecessary(FacesContext context, UIViewRoot root) {
      Iterator var2 = getImportConstants(root).iterator();

      while(var2.hasNext()) {
         UIImportConstants importConstants = (UIImportConstants)var2.next();
         String type = importConstants.getType();
         if (type == null) {
            throw new IllegalArgumentException("UIImportConstants type attribute is required.");
         }

         String var = importConstants.getVar();
         if (var == null) {
            int innerClass = type.lastIndexOf(36);
            int outerClass = type.lastIndexOf(46);
            var = type.substring(Math.max(innerClass, outerClass) + 1);
         }

         Map applicationMap = context.getExternalContext().getApplicationMap();
         if (!applicationMap.containsKey(type)) {
            applicationMap.putIfAbsent(var, collectConstants(type));
         }
      }

   }

   private static Map collectConstants(String type) {
      Map constants = new LinkedHashMap();
      Field[] var2 = toClass(type).getFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Field field = var2[var4];
         int modifiers = field.getModifiers();
         if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
            try {
               constants.put(field.getName(), field.get((Object)null));
            } catch (Exception var8) {
               throw new IllegalArgumentException(String.format("UIImportConstants cannot access constant field '%s' of type '%s'.", type, field.getName()), var8);
            }
         }
      }

      return Collections.unmodifiableMap(new ConstantsMap(constants, type));
   }

   private static Class toClass(String type) {
      try {
         return Class.forName(type, true, Thread.currentThread().getContextClassLoader());
      } catch (ClassNotFoundException var5) {
         int i = type.lastIndexOf(46);
         if (i > 0) {
            try {
               return toClass((new StringBuilder(type)).replace(i, i + 1, "$").toString());
            } catch (Exception var4) {
               Object var3 = null;
            }
         }

         throw new IllegalArgumentException(String.format("UIImportConstants cannot find type '%s' in classpath.", type), var5);
      }
   }

   private static class ConstantsMap extends HashMap {
      private static final long serialVersionUID = 7036447585721834948L;
      private String type;

      public ConstantsMap(Map map, String type) {
         this.type = type;
         this.putAll(map);
      }

      public Object get(Object key) {
         if (!this.containsKey(key)) {
            throw new IllegalArgumentException(String.format("UIImportConstants type '%s' does not have the constant '%s'.", this.type, key));
         } else {
            return super.get(key);
         }
      }

      public boolean equals(Object object) {
         return super.equals(object) && this.type.equals(((ConstantsMap)object).type);
      }

      public int hashCode() {
         return super.hashCode() + this.type.hashCode();
      }
   }
}
