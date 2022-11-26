package com.sun.faces.application;

import com.sun.faces.config.WebConfiguration;
import com.sun.faces.io.FastStringWriter;
import com.sun.faces.renderkit.RenderKitUtils;
import com.sun.faces.spi.SerializationProvider;
import com.sun.faces.spi.SerializationProviderFactory;
import com.sun.faces.util.DebugUtil;
import com.sun.faces.util.FacesLogger;
import com.sun.faces.util.LRUMap;
import com.sun.faces.util.MessageUtils;
import com.sun.faces.util.ReflectionUtils;
import com.sun.faces.util.RequestStateManager;
import com.sun.faces.util.TypedCollections;
import com.sun.faces.util.Util;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.ResponseStateManager;

public class StateManagerImpl extends StateManager {
   private static final Logger LOGGER;
   private static final String STATEMANAGED_SERIAL_ID_KEY;
   private static final String LOGICAL_VIEW_MAP = "com.sun.faces.logicalViewMap";
   private SerializationProvider serialProvider;
   private WebConfiguration webConfig;
   private int noOfViews;
   private int noOfViewsInLogicalView;
   private boolean compressViewState;
   private Map classMap = new ConcurrentHashMap(32);
   private Random random;
   private boolean generateUniqueStateIds;

   public StateManagerImpl() {
      FacesContext fContext = FacesContext.getCurrentInstance();
      this.serialProvider = SerializationProviderFactory.createInstance(fContext.getExternalContext());
      this.webConfig = WebConfiguration.getInstance(fContext.getExternalContext());
      if (!this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.DevelopmentMode)) {
         this.classMap = new ConcurrentHashMap(32);
      }

      this.generateUniqueStateIds = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.GenerateUniqueServerStateIds);
      if (this.generateUniqueStateIds) {
         this.random = new Random(System.nanoTime() + (long)this.webConfig.getServletContext().hashCode());
      } else {
         this.random = null;
      }

      this.compressViewState = this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.CompressViewState);
   }

   public UIViewRoot restoreView(FacesContext context, String viewId, String renderKitId) {
      UIViewRoot viewRoot = null;
      if (this.isSavingStateInClient(context)) {
         viewRoot = this.restoreTree(context, viewId, renderKitId);
         if (viewRoot != null) {
            this.restoreState(context, viewRoot, renderKitId);
         }
      } else {
         ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
         Object id;
         if (this.hasGetStateMethod(rsm)) {
            Object[] stateArray = (Object[])((Object[])rsm.getState(context, viewId));
            id = stateArray != null ? stateArray[0] : null;
         } else {
            id = rsm.getTreeStructureToRestore(context, viewId);
         }

         if (null != id) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Begin restoring view in session for viewId " + viewId);
            }

            String idString = (String)id;
            int sep = idString.indexOf(58);

            assert -1 != sep;

            assert sep < idString.length();

            String idInLogicalMap = idString.substring(0, sep);
            String idInActualMap = idString.substring(sep + 1);
            ExternalContext externalCtx = context.getExternalContext();
            Object sessionObj = externalCtx.getSession(false);
            if (sessionObj == null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("Can't Restore Server View State, session expired for viewId: " + viewId);
               }

               return null;
            }

            Object[] stateArray = null;
            synchronized(sessionObj) {
               Map logicalMap = (Map)externalCtx.getSessionMap().get("com.sun.faces.logicalViewMap");
               if (logicalMap != null) {
                  Map actualMap = (Map)logicalMap.get(idInLogicalMap);
                  if (actualMap != null) {
                     RequestStateManager.set(context, "com.sun.faces.logicalViewMap", idInLogicalMap);
                     if (rsm.isPostback(context)) {
                        RequestStateManager.set(context, "com.sun.faces.actualViewMap", idInActualMap);
                     }

                     stateArray = (Object[])((Object[])actualMap.get(idInActualMap));
                  }
               }
            }

            if (stateArray == null) {
               if (LOGGER.isLoggable(Level.FINE)) {
                  LOGGER.fine("Session Available, but View State does not exist for viewId: " + viewId);
               }

               return null;
            }

            viewRoot = this.restoreTree((Object[])((Object[])((Object[])stateArray[0])).clone());
            viewRoot.processRestoreState(context, this.handleRestoreState(stateArray[1]));
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("End restoring view in session for viewId " + viewId);
            }
         }
      }

      return viewRoot;
   }

   public StateManager.SerializedView saveSerializedView(FacesContext context) {
      StateManager.SerializedView result = null;
      UIViewRoot viewRoot = context.getViewRoot();
      if (viewRoot.isTransient()) {
         return result;
      } else {
         this.checkIdUniqueness(context, viewRoot, new HashSet(viewRoot.getChildCount() << 1));
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("Begin creating serialized view for " + viewRoot.getViewId());
         }

         List treeList = new ArrayList(32);
         Object state = viewRoot.processSaveState(context);
         captureChild(treeList, 0, viewRoot);
         Object[] tree = treeList.toArray();
         if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.fine("End creating serialized view " + viewRoot.getViewId());
         }

         if (!this.isSavingStateInClient(context)) {
            int logicalMapSize = this.getNumberOfViewsParameter();
            int actualMapSize = this.getNumberOfViewsInLogicalViewParameter();
            ExternalContext externalContext = context.getExternalContext();
            Object sessionObj = externalContext.getSession(true);
            Map sessionMap = externalContext.getSessionMap();
            synchronized(sessionObj) {
               Map logicalMap = TypedCollections.dynamicallyCastMap((Map)sessionMap.get("com.sun.faces.logicalViewMap"), String.class, Map.class);
               if (logicalMap == null) {
                  logicalMap = new LRUMap(logicalMapSize);
                  sessionMap.put("com.sun.faces.logicalViewMap", logicalMap);
               }

               String idInLogicalMap = (String)RequestStateManager.get(context, "com.sun.faces.logicalViewMap");
               if (idInLogicalMap == null) {
                  idInLogicalMap = this.generateUniqueStateIds ? this.createRandomId() : this.createIncrementalRequestId(context);
               }

               assert null != idInLogicalMap;

               String idInActualMap = this.generateUniqueStateIds ? this.createRandomId() : this.createIncrementalRequestId(context);
               Map actualMap = TypedCollections.dynamicallyCastMap((Map)((Map)logicalMap).get(idInLogicalMap), String.class, Object[].class);
               if (actualMap == null) {
                  actualMap = new LRUMap(actualMapSize);
                  ((Map)logicalMap).put(idInLogicalMap, actualMap);
               }

               String id = idInLogicalMap + ':' + idInActualMap;
               result = new StateManager.SerializedView(this, id, (Object)null);
               Object[] stateArray = (Object[])((Map)actualMap).get(idInActualMap);
               if (stateArray != null) {
                  stateArray[0] = tree;
                  stateArray[1] = this.handleSaveState(state);
               } else {
                  ((Map)actualMap).put(idInActualMap, new Object[]{tree, this.handleSaveState(state)});
               }

               sessionMap.put("com.sun.faces.logicalViewMap", logicalMap);
            }
         } else {
            result = new StateManager.SerializedView(this, tree, state);
         }

         return result;
      }
   }

   public void writeState(FacesContext context, StateManager.SerializedView state) throws IOException {
      String renderKitId = context.getViewRoot().getRenderKitId();
      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
      if (this.hasGetStateMethod(rsm)) {
         Object[] stateArray = new Object[]{state.getStructure(), state.getState()};
         rsm.writeState(context, stateArray);
      } else {
         rsm.writeState(context, state);
      }

   }

   protected void checkIdUniqueness(FacesContext context, UIComponent component, Set componentIds) throws IllegalStateException {
      Iterator kids = component.getFacetsAndChildren();

      while(kids.hasNext()) {
         UIComponent kid = (UIComponent)kids.next();
         String id = kid.getClientId(context);
         if (!componentIds.add(id)) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
               LOGGER.log(Level.SEVERE, "jsf.duplicate_component_id_error", id);
            }

            FastStringWriter writer = new FastStringWriter(128);
            DebugUtil.simplePrintTree(context.getViewRoot(), id, writer);
            String message = MessageUtils.getExceptionMessageString("com.sun.faces.DUPLICATE_COMPONENT_ID_ERROR", id) + '\n' + writer.toString();
            throw new IllegalStateException(message);
         }

         this.checkIdUniqueness(context, kid, componentIds);
      }

   }

   protected int getNumberOfViewsInLogicalViewParameter() {
      if (this.noOfViewsInLogicalView != 0) {
         return this.noOfViewsInLogicalView;
      } else {
         String noOfViewsStr = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.NumberOfLogicalViews);
         String defaultValue = WebConfiguration.WebContextInitParameter.NumberOfLogicalViews.getDefaultValue();

         try {
            this.noOfViewsInLogicalView = Integer.valueOf(noOfViewsStr);
         } catch (NumberFormatException var6) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Error parsing the servetInitParameter " + WebConfiguration.WebContextInitParameter.NumberOfLogicalViews.getQualifiedName() + ". Using default " + this.noOfViewsInLogicalView);
            }

            try {
               this.noOfViewsInLogicalView = Integer.valueOf(defaultValue);
            } catch (NumberFormatException var5) {
            }
         }

         return this.noOfViewsInLogicalView;
      }
   }

   protected int getNumberOfViewsParameter() {
      if (this.noOfViews != 0) {
         return this.noOfViews;
      } else {
         String noOfViewsStr = this.webConfig.getOptionValue(WebConfiguration.WebContextInitParameter.NumberOfViews);
         String defaultValue = WebConfiguration.WebContextInitParameter.NumberOfViews.getDefaultValue();

         try {
            this.noOfViews = Integer.valueOf(noOfViewsStr);
         } catch (NumberFormatException var6) {
            if (LOGGER.isLoggable(Level.FINE)) {
               LOGGER.fine("Error parsing the servetInitParameter " + WebConfiguration.WebContextInitParameter.NumberOfViews.getQualifiedName() + ". Using default " + this.noOfViews);
            }

            try {
               this.noOfViews = Integer.valueOf(defaultValue);
            } catch (NumberFormatException var5) {
            }
         }

         return this.noOfViews;
      }
   }

   private Object handleSaveState(Object state) {
      if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.SerializeServerState)) {
         ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
         ObjectOutputStream oas = null;

         try {
            oas = this.serialProvider.createObjectOutputStream((OutputStream)(this.compressViewState ? new GZIPOutputStream(baos, 1024) : baos));
            oas.writeObject(state);
            oas.flush();
         } catch (Exception var12) {
            throw new FacesException(var12);
         } finally {
            if (oas != null) {
               try {
                  oas.close();
               } catch (IOException var11) {
               }
            }

         }

         return baos.toByteArray();
      } else {
         return state;
      }
   }

   private Object handleRestoreState(Object state) {
      if (this.webConfig.isOptionEnabled(WebConfiguration.BooleanWebContextInitParameter.SerializeServerState)) {
         ByteArrayInputStream bais = new ByteArrayInputStream((byte[])((byte[])state));
         ObjectInputStream ois = null;

         Object var4;
         try {
            ois = this.serialProvider.createObjectInputStream((InputStream)(this.compressViewState ? new GZIPInputStream(bais, 1024) : bais));
            var4 = ois.readObject();
         } catch (Exception var13) {
            throw new FacesException(var13);
         } finally {
            if (ois != null) {
               try {
                  ois.close();
               } catch (IOException var12) {
               }
            }

         }

         return var4;
      } else {
         return state;
      }
   }

   private String createIncrementalRequestId(FacesContext ctx) {
      Map sm = ctx.getExternalContext().getSessionMap();
      AtomicInteger idgen = (AtomicInteger)sm.get(STATEMANAGED_SERIAL_ID_KEY);
      if (idgen == null) {
         idgen = new AtomicInteger(1);
      }

      sm.put(STATEMANAGED_SERIAL_ID_KEY, idgen);
      return "j_id" + idgen.getAndIncrement();
   }

   private String createRandomId() {
      return Long.valueOf(this.random.nextLong()).toString();
   }

   private static void captureChild(List tree, int parent, UIComponent c) {
      if (!c.isTransient()) {
         TreeNode n = new TreeNode(parent, c);
         int pos = tree.size();
         tree.add(n);
         captureRest(tree, pos, c);
      }

   }

   private static void captureFacet(List tree, int parent, String name, UIComponent c) {
      if (!c.isTransient()) {
         FacetNode n = new FacetNode(parent, name, c);
         int pos = tree.size();
         tree.add(n);
         captureRest(tree, pos, c);
      }

   }

   private static void captureRest(List tree, int pos, UIComponent c) {
      int sz = c.getChildCount();
      if (sz > 0) {
         List child = c.getChildren();

         for(int i = 0; i < sz; ++i) {
            captureChild(tree, pos, (UIComponent)child.get(i));
         }
      }

      sz = c.getFacetCount();
      if (sz > 0) {
         Iterator i$ = c.getFacets().entrySet().iterator();

         while(i$.hasNext()) {
            Map.Entry entry = (Map.Entry)i$.next();
            captureFacet(tree, pos, (String)entry.getKey(), (UIComponent)entry.getValue());
         }
      }

   }

   private boolean hasGetStateMethod(ResponseStateManager instance) {
      return ReflectionUtils.lookupMethod(instance.getClass(), "getState", FacesContext.class, String.class) != null;
   }

   private UIComponent newInstance(TreeNode n) throws FacesException {
      try {
         Class t = this.classMap != null ? (Class)this.classMap.get(n.componentType) : null;
         if (t == null) {
            t = Util.loadClass(n.componentType, n);
            if (t == null || this.classMap == null) {
               throw new NullPointerException();
            }

            this.classMap.put(n.componentType, t);
         }

         UIComponent c = (UIComponent)t.newInstance();
         c.setId(n.id);
         return c;
      } catch (Exception var4) {
         throw new FacesException(var4);
      }
   }

   private UIViewRoot restoreTree(FacesContext context, String viewId, String renderKitId) {
      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
      Object[] treeStructure;
      if (this.hasGetStateMethod(rsm)) {
         Object[] stateArray = (Object[])((Object[])rsm.getState(context, viewId));
         if (stateArray == null) {
            return null;
         }

         treeStructure = (Object[])((Object[])stateArray[0]);
      } else {
         treeStructure = (Object[])((Object[])rsm.getTreeStructureToRestore(context, viewId));
      }

      if (treeStructure == null) {
         return null;
      } else {
         UIViewRoot root = this.restoreTree(treeStructure);
         root.setViewId(viewId);
         return root;
      }
   }

   private String createUniqueRequestId(FacesContext ctx) {
      Map sm = ctx.getExternalContext().getSessionMap();
      AtomicInteger idgen = (AtomicInteger)sm.get(STATEMANAGED_SERIAL_ID_KEY);
      if (idgen == null) {
         idgen = new AtomicInteger(1);
      }

      sm.put(STATEMANAGED_SERIAL_ID_KEY, idgen);
      return "j_id" + idgen.getAndIncrement();
   }

   private void restoreState(FacesContext context, UIViewRoot root, String renderKitId) {
      ResponseStateManager rsm = RenderKitUtils.getResponseStateManager(context, renderKitId);
      Object state;
      if (ReflectionUtils.lookupMethod(rsm.getClass(), "getState", FacesContext.class, String.class) != null) {
         Object[] stateArray = (Object[])((Object[])rsm.getState(context, root.getViewId()));
         state = stateArray[1];
      } else {
         state = rsm.getComponentStateToRestore(context);
      }

      root.processRestoreState(context, state);
   }

   private UIViewRoot restoreTree(Object[] tree) throws FacesException {
      for(int i = 0; i < tree.length; ++i) {
         UIComponent c;
         if (tree[i] instanceof FacetNode) {
            FacetNode fn = (FacetNode)tree[i];
            c = this.newInstance(fn);
            tree[i] = c;
            if (i != fn.parent) {
               ((UIComponent)tree[fn.parent]).getFacets().put(fn.facetName, c);
            }
         } else {
            TreeNode tn = (TreeNode)tree[i];
            c = this.newInstance(tn);
            tree[i] = c;
            if (i != tn.parent) {
               ((UIComponent)tree[tn.parent]).getChildren().add(c);
            }
         }
      }

      return (UIViewRoot)tree[0];
   }

   static {
      LOGGER = FacesLogger.APPLICATION.getLogger();
      STATEMANAGED_SERIAL_ID_KEY = StateManagerImpl.class.getName() + ".SerialId";
   }

   private static final class FacetNode extends TreeNode {
      public String facetName;
      private static final long serialVersionUID = -3777170310958005106L;

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

   private static class TreeNode implements Externalizable {
      private static final String NULL_ID = "";
      public String componentType;
      public String id;
      public int parent;
      private static final long serialVersionUID = -835775352718473281L;

      public TreeNode() {
      }

      public TreeNode(int parent, UIComponent c) {
         this.parent = parent;
         this.id = c.getId();
         this.componentType = c.getClass().getName();
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

      public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
         this.parent = in.readInt();
         this.componentType = in.readUTF();
         this.id = in.readUTF();
         if (this.id.length() == 0) {
            this.id = null;
         }

      }
   }
}
