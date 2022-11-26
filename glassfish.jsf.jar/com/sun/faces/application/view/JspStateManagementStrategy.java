package com.sun.faces.application.view;

import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.Util;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.ProjectStage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;
import javax.faces.view.StateManagementStrategy;

public class JspStateManagementStrategy extends StateManagementStrategy {
   private static final Logger LOGGER;
   private Map classMap;
   private boolean isDevelopmentMode;

   public JspStateManagementStrategy() {
      this(FacesContext.getCurrentInstance());
   }

   public JspStateManagementStrategy(FacesContext context) {
      this.isDevelopmentMode = context.isProjectStage(ProjectStage.Development);
      this.classMap = new ConcurrentHashMap(32);
   }

   private void captureChild(List tree, int parent, UIComponent c) {
      if (!c.isTransient()) {
         TreeNode n = new TreeNode(parent, c);
         int pos = tree.size();
         tree.add(n);
         this.captureRest(tree, pos, c);
      }

   }

   private void captureFacet(List tree, int parent, String name, UIComponent c) {
      if (!c.isTransient()) {
         FacetNode n = new FacetNode(parent, name, c);
         int pos = tree.size();
         tree.add(n);
         this.captureRest(tree, pos, c);
      }

   }

   private void captureRest(List tree, int pos, UIComponent c) {
      int sz = c.getChildCount();
      if (sz > 0) {
         List child = c.getChildren();

         for(int i = 0; i < sz; ++i) {
            this.captureChild(tree, pos, (UIComponent)child.get(i));
         }
      }

      sz = c.getFacetCount();
      if (sz > 0) {
         Iterator var7 = c.getFacets().entrySet().iterator();

         while(var7.hasNext()) {
            Map.Entry entry = (Map.Entry)var7.next();
            this.captureFacet(tree, pos, (String)entry.getKey(), (UIComponent)entry.getValue());
         }
      }

   }

   private UIComponent newInstance(TreeNode n) throws FacesException {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "newInstance", n.componentType);
      }

      try {
         Class t = this.classMap != null ? (Class)this.classMap.get(n.componentType) : null;
         if (t == null) {
            t = Util.loadClass(n.componentType, n);
            if (t != null && this.classMap != null) {
               this.classMap.put(n.componentType, t);
            } else if (!this.isDevelopmentMode) {
               throw new NullPointerException();
            }
         }

         assert t != null;

         UIComponent c = (UIComponent)t.newInstance();
         c.setId(n.id);
         return c;
      } catch (NullPointerException | InstantiationException | IllegalAccessException | ClassNotFoundException var4) {
         throw new FacesException(var4);
      }
   }

   private UIViewRoot restoreTree(FacesContext context, String renderKitId, Object[] tree) throws FacesException {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "restoreTree", renderKitId);
      }

      for(int i = 0; i < tree.length; ++i) {
         UIComponent c;
         if (tree[i] instanceof FacetNode) {
            FacetNode fn = (FacetNode)tree[i];
            c = this.newInstance(fn);
            tree[i] = c;
            if (i != fn.getParent()) {
               ((UIComponent)tree[fn.getParent()]).getFacets().put(fn.facetName, c);
            }
         } else {
            TreeNode tn = (TreeNode)tree[i];
            c = this.newInstance(tn);
            tree[i] = c;
            if (i != tn.parent) {
               ((UIComponent)tree[tn.parent]).getChildren().add(c);
            } else {
               assert c instanceof UIViewRoot;

               UIViewRoot viewRoot = (UIViewRoot)c;
               context.setViewRoot(viewRoot);
               viewRoot.setRenderKitId(renderKitId);
            }
         }
      }

      return (UIViewRoot)tree[0];
   }

   public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.log(Level.FINEST, "restoreView", new Object[]{viewId, renderKitId});
      }

      UIViewRoot result = null;
      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
      Object[] state = (Object[])((Object[])rsm.getState(context, viewId));
      if (state != null && state.length >= 2) {
         if (state[0] != null) {
            result = this.restoreTree(context, renderKitId, (Object[])((Object[])((Object[])state[0])).clone());
            context.setViewRoot(result);
         }

         if (result != null && state[1] != null) {
            result.processRestoreState(context, state[1]);
         }
      }

      return result;
   }

   public Object saveView(FacesContext context) {
      if (LOGGER.isLoggable(Level.FINEST)) {
         LOGGER.finest("saveView");
      }

      UIViewRoot viewRoot = context.getViewRoot();
      Util.checkIdUniqueness(context, viewRoot, new HashSet(viewRoot.getChildCount() << 1));
      Object state = viewRoot.processSaveState(context);
      List treeList = new ArrayList(32);
      this.captureChild(treeList, 0, viewRoot);
      Object[] tree = treeList.toArray();
      Object[] result = new Object[]{tree, state};
      return result;
   }

   static {
      LOGGER = FacesLogger.APPLICATION_VIEW.getLogger();
   }

   private static class TreeNode implements Externalizable {
      private static final long serialVersionUID = -835775352718473281L;
      private static final String NULL_ID = "";
      private String componentType;
      private String id;
      private int parent;

      public TreeNode() {
      }

      public TreeNode(int parent, UIComponent c) {
         this.parent = parent;
         this.id = c.getId();
         this.componentType = c.getClass().getName();
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.parent = in.readInt();
         this.componentType = in.readUTF();
         this.id = in.readUTF();
         if (this.id.length() == 0) {
            this.id = null;
         }

      }

      public void writeExternal(ObjectOutput out) throws IOException {
         out.writeInt(this.parent);
         out.writeUTF(this.componentType);
         if (this.id != null) {
            out.writeUTF(this.id);
         } else {
            out.writeUTF("");
         }

      }

      public int getParent() {
         return this.parent;
      }
   }

   private static final class FacetNode extends TreeNode {
      private static final long serialVersionUID = -3777170310958005106L;
      private String facetName;

      public FacetNode() {
      }

      public FacetNode(int parent, String name, UIComponent c) {
         super(parent, c);
         this.facetName = name;
      }

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         super.readExternal(in);
         this.facetName = in.readUTF();
      }

      public void writeExternal(ObjectOutput out) throws IOException {
         super.writeExternal(out);
         out.writeUTF(this.facetName);
      }
   }
}
