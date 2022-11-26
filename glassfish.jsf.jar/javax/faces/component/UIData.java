package javax.faces.component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.util.AnnotationLiteral;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreValidateEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.CollectionDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.FacesDataModel;
import javax.faces.model.IterableDataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.servlet.jsp.jstl.sql.Result;

public class UIData extends UIComponentBase implements NamingContainer, UniqueIdVendor {
   public static final String COMPONENT_TYPE = "javax.faces.Data";
   public static final String COMPONENT_FAMILY = "javax.faces.Data";
   private DataModel model = null;
   private Object oldVar;
   private String baseClientId = null;
   private int baseClientIdLength;
   private StringBuilder clientIdBuilder = null;
   private Boolean isNested = null;
   private Map _rowDeltaStates = new HashMap();
   private Map _rowTransientStates = new HashMap();
   private Object _initialDescendantFullComponentState = null;

   public UIData() {
      this.setRendererType("javax.faces.Table");
   }

   public String getFamily() {
      return "javax.faces.Data";
   }

   public int getFirst() {
      return (Integer)this.getStateHelper().eval(UIData.PropertyKeys.first, 0);
   }

   public void setFirst(int first) {
      if (first < 0) {
         throw new IllegalArgumentException(String.valueOf(first));
      } else {
         this.getStateHelper().put(UIData.PropertyKeys.first, first);
      }
   }

   public UIComponent getFooter() {
      return this.getFacet("footer");
   }

   public void setFooter(UIComponent footer) {
      this.getFacets().put("footer", footer);
   }

   public UIComponent getHeader() {
      return this.getFacet("header");
   }

   public void setHeader(UIComponent header) {
      this.getFacets().put("header", header);
   }

   public boolean isRowAvailable() {
      return this.getDataModel().isRowAvailable();
   }

   public int getRowCount() {
      return this.getDataModel().getRowCount();
   }

   public Object getRowData() {
      return this.getDataModel().getRowData();
   }

   public int getRowIndex() {
      return (Integer)this.getStateHelper().eval(UIData.PropertyKeys.rowIndex, -1);
   }

   public void setRowIndex(int rowIndex) {
      if (this.isRowStatePreserved()) {
         this.setRowIndexRowStatePreserved(rowIndex);
      } else {
         this.setRowIndexWithoutRowStatePreserved(rowIndex);
      }

   }

   private void setRowIndexWithoutRowStatePreserved(int rowIndex) {
      this.saveDescendantState();
      this.getStateHelper().put(UIData.PropertyKeys.rowIndex, rowIndex);
      DataModel localModel = this.getDataModel();
      localModel.setRowIndex(rowIndex);
      if (rowIndex == -1) {
         this.setDataModel((DataModel)null);
      }

      String var = (String)this.getStateHelper().get(UIData.PropertyKeys.var);
      if (var != null) {
         Map requestMap = this.getFacesContext().getExternalContext().getRequestMap();
         if (rowIndex == -1) {
            this.oldVar = requestMap.remove(var);
         } else if (this.isRowAvailable()) {
            requestMap.put(var, this.getRowData());
         } else {
            requestMap.remove(var);
            if (null != this.oldVar) {
               requestMap.put(var, this.oldVar);
               this.oldVar = null;
            }
         }
      }

      this.restoreDescendantState();
   }

   private void setRowIndexRowStatePreserved(int rowIndex) {
      if (rowIndex < -1) {
         throw new IllegalArgumentException("rowIndex is less than -1");
      } else if (this.getRowIndex() != rowIndex) {
         FacesContext facesContext = this.getFacesContext();
         if (this._initialDescendantFullComponentState != null) {
            Map sm = this.saveFullDescendantComponentStates(facesContext, (Map)null, this.getChildren().iterator(), false);
            if (sm != null && !sm.isEmpty()) {
               this._rowDeltaStates.put(this.getContainerClientId(facesContext), sm);
            }

            if (this.getRowIndex() != -1) {
               this._rowTransientStates.put(this.getContainerClientId(facesContext), this.saveTransientDescendantComponentStates(facesContext, (Map)null, this.getChildren().iterator(), false));
            }
         }

         this.getStateHelper().put(UIData.PropertyKeys.rowIndex, rowIndex);
         DataModel localModel = this.getDataModel();
         localModel.setRowIndex(rowIndex);
         if (rowIndex == -1) {
            this.setDataModel((DataModel)null);
         }

         String var = (String)this.getStateHelper().get(UIData.PropertyKeys.var);
         if (var != null) {
            Map requestMap = this.getFacesContext().getExternalContext().getRequestMap();
            if (rowIndex == -1) {
               this.oldVar = requestMap.remove(var);
            } else if (this.isRowAvailable()) {
               requestMap.put(var, this.getRowData());
            } else {
               requestMap.remove(var);
               if (null != this.oldVar) {
                  requestMap.put(var, this.oldVar);
                  this.oldVar = null;
               }
            }
         }

         if (this._initialDescendantFullComponentState != null) {
            Object rowState = this._rowDeltaStates.get(this.getContainerClientId(facesContext));
            if (rowState == null) {
               this.restoreFullDescendantComponentStates(facesContext, this.getChildren().iterator(), this._initialDescendantFullComponentState, false);
            } else {
               this.restoreFullDescendantComponentDeltaStates(facesContext, this.getChildren().iterator(), rowState, this._initialDescendantFullComponentState, false);
            }

            if (this.getRowIndex() == -1) {
               this.restoreTransientDescendantComponentStates(facesContext, this.getChildren().iterator(), (Map)null, false);
            } else {
               rowState = this._rowTransientStates.get(this.getContainerClientId(facesContext));
               if (rowState == null) {
                  this.restoreTransientDescendantComponentStates(facesContext, this.getChildren().iterator(), (Map)null, false);
               } else {
                  this.restoreTransientDescendantComponentStates(facesContext, this.getChildren().iterator(), (Map)rowState, false);
               }
            }
         }

      }
   }

   public int getRows() {
      return (Integer)this.getStateHelper().eval(UIData.PropertyKeys.rows, 0);
   }

   public void setRows(int rows) {
      if (rows < 0) {
         throw new IllegalArgumentException(String.valueOf(rows));
      } else {
         this.getStateHelper().put(UIData.PropertyKeys.rows, rows);
      }
   }

   public String getVar() {
      return (String)this.getStateHelper().get(UIData.PropertyKeys.var);
   }

   public void setVar(String var) {
      this.getStateHelper().put(UIData.PropertyKeys.var, var);
   }

   public boolean isRowStatePreserved() {
      Boolean b = (Boolean)this.getStateHelper().get(UIData.PropertyKeys.rowStatePreserved);
      return b == null ? false : b;
   }

   public void setRowStatePreserved(boolean preserveComponentState) {
      this.getStateHelper().put(UIData.PropertyKeys.rowStatePreserved, preserveComponentState);
   }

   public Object getValue() {
      return this.getStateHelper().eval(UIData.PropertyKeys.value);
   }

   public void setValue(Object value) {
      this.setDataModel((DataModel)null);
      this.getStateHelper().put(UIData.PropertyKeys.value, value);
   }

   /** @deprecated */
   public void setValueBinding(String name, ValueBinding binding) {
      if (null != name) {
         switch (name) {
            case "value":
               this.setDataModel((DataModel)null);
               break;
            case "var":
            case "rowIndex":
               throw new IllegalArgumentException();
         }
      }

      super.setValueBinding(name, binding);
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if (null != name) {
         switch (name) {
            case "value":
               this.model = null;
               break;
            case "var":
            case "rowIndex":
               throw new IllegalArgumentException();
         }
      }

      super.setValueExpression(name, binding);
   }

   public String getClientId(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (this.baseClientId == null && this.clientIdBuilder == null) {
            if (!this.isNestedWithinIterator()) {
               this.clientIdBuilder = new StringBuilder(super.getClientId(context));
               this.baseClientId = this.clientIdBuilder.toString();
               this.baseClientIdLength = this.baseClientId.length() + 1;
               this.clientIdBuilder.append(UINamingContainer.getSeparatorChar(context));
               this.clientIdBuilder.setLength(this.baseClientIdLength);
            } else {
               this.clientIdBuilder = new StringBuilder();
            }
         }

         int rowIndex = this.getRowIndex();
         if (rowIndex >= 0) {
            String cid;
            if (!this.isNestedWithinIterator()) {
               cid = this.clientIdBuilder.append(rowIndex).toString();
               this.clientIdBuilder.setLength(this.baseClientIdLength);
            } else {
               cid = this.clientIdBuilder.append(super.getClientId(context)).append(UINamingContainer.getSeparatorChar(context)).append(rowIndex).toString();
               this.clientIdBuilder.setLength(0);
            }

            return cid;
         } else {
            return !this.isNestedWithinIterator() ? this.baseClientId : super.getClientId(context);
         }
      }
   }

   public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback) throws FacesException {
      if (null != context && null != clientId && null != callback) {
         String myId = super.getClientId(context);
         boolean found = false;
         if (clientId.equals(myId)) {
            boolean var34;
            try {
               this.pushComponentToEL(context, this.compositeParent);
               callback.invokeContextCallback(context, this);
               var34 = true;
            } catch (Exception var27) {
               throw new FacesException(var27);
            } finally {
               this.popComponentFromEL(context);
            }

            return var34;
         } else {
            Iterator var6;
            UIComponent column;
            if (this.getFacetCount() > 0) {
               var6 = this.getFacets().values().iterator();

               while(var6.hasNext()) {
                  column = (UIComponent)var6.next();
                  if (clientId.equals(column.getClientId(context))) {
                     callback.invokeContextCallback(context, column);
                     return true;
                  }
               }
            }

            if (this.getChildCount() > 0) {
               var6 = this.getChildren().iterator();

               label342:
               while(true) {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           break label342;
                        }

                        column = (UIComponent)var6.next();
                     } while(!(column instanceof UIColumn));
                  } while(column.getFacetCount() <= 0);

                  Iterator var8 = column.getFacets().values().iterator();

                  while(var8.hasNext()) {
                     UIComponent facet = (UIComponent)var8.next();
                     if (facet.invokeOnComponent(context, clientId, callback)) {
                        return true;
                     }
                  }
               }
            }

            if (this.getChildCount() > 0) {
               var6 = this.getChildren().iterator();

               while(var6.hasNext()) {
                  column = (UIComponent)var6.next();
                  if (column instanceof UIColumn && column.invokeOnComponent(context, clientId, callback)) {
                     return true;
                  }
               }
            }

            int savedRowIndex = this.getRowIndex();
            char sepChar = UINamingContainer.getSeparatorChar(context);
            if (myId.endsWith(sepChar + Integer.toString(savedRowIndex, 10))) {
               int lastSep = myId.lastIndexOf(sepChar);

               assert -1 != lastSep;

               myId = myId.substring(0, lastSep);
            }

            if (clientId.startsWith(myId)) {
               try {
                  int preRowIndexSep;
                  if (-1 != (preRowIndexSep = clientId.indexOf(sepChar, myId.length()))) {
                     ++preRowIndexSep;
                     int postRowIndexSep;
                     if (preRowIndexSep < clientId.length() && -1 != (postRowIndexSep = clientId.indexOf(sepChar, preRowIndexSep + 1))) {
                        int newRow;
                        try {
                           newRow = Integer.parseInt(clientId.substring(preRowIndexSep, postRowIndexSep));
                        } catch (NumberFormatException var29) {
                           String message = "Trying to extract rowIndex from clientId '" + clientId + "' " + var29.getMessage();
                           throw new NumberFormatException(message);
                        }

                        this.setRowIndex(newRow);
                        if (this.isRowAvailable()) {
                           found = super.invokeOnComponent(context, clientId, callback);
                        }
                     }
                  }
               } catch (FacesException var30) {
                  throw var30;
               } catch (NumberFormatException var31) {
                  throw new FacesException(var31);
               } finally {
                  this.setRowIndex(savedRowIndex);
               }
            }

            return found;
         }
      } else {
         throw new NullPointerException();
      }
   }

   public void queueEvent(FacesEvent event) {
      super.queueEvent(new WrapperEvent(this, event, this.getRowIndex()));
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      if (!(event instanceof WrapperEvent)) {
         super.broadcast(event);
      } else {
         FacesContext context = event.getFacesContext();
         WrapperEvent revent = (WrapperEvent)event;
         if (this.isNestedWithinIterator()) {
            this.setDataModel((DataModel)null);
         }

         int oldRowIndex = this.getRowIndex();
         this.setRowIndex(revent.getRowIndex());
         FacesEvent rowEvent = revent.getFacesEvent();
         UIComponent source = rowEvent.getComponent();
         UIComponent compositeParent = null;

         try {
            if (!UIComponent.isCompositeComponent(source)) {
               compositeParent = UIComponent.getCompositeComponentParent(source);
            }

            if (compositeParent != null) {
               compositeParent.pushComponentToEL(context, (UIComponent)null);
            }

            source.pushComponentToEL(context, (UIComponent)null);
            source.broadcast(rowEvent);
         } finally {
            source.popComponentFromEL(context);
            if (compositeParent != null) {
               compositeParent.popComponentFromEL(context);
            }

         }

         this.setRowIndex(oldRowIndex);
      }
   }

   public void encodeBegin(FacesContext context) throws IOException {
      this.preEncode(context);
      super.encodeBegin(context);
   }

   public void processDecodes(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.pushComponentToEL(context, this);
         this.preDecode(context);
         this.iterate(context, PhaseId.APPLY_REQUEST_VALUES);
         this.decode(context);
         this.popComponentFromEL(context);
      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.pushComponentToEL(context, this);
         Application app = context.getApplication();
         app.publishEvent(context, PreValidateEvent.class, this);
         this.preValidate(context);
         this.iterate(context, PhaseId.PROCESS_VALIDATIONS);
         app.publishEvent(context, PostValidateEvent.class, this);
         this.popComponentFromEL(context);
      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.pushComponentToEL(context, this);
         this.preUpdate(context);
         this.iterate(context, PhaseId.UPDATE_MODEL_VALUES);
         this.popComponentFromEL(context);
      }
   }

   public String createUniqueId(FacesContext context, String seed) {
      Integer i = (Integer)this.getStateHelper().get(UIData.PropertyKeys.lastId);
      int lastId = i != null ? i : 0;
      StateHelper var10000 = this.getStateHelper();
      ++lastId;
      var10000.put(UIData.PropertyKeys.lastId, lastId);
      return "j_id" + (seed == null ? lastId : seed);
   }

   public boolean visitTree(VisitContext context, VisitCallback callback) {
      if (!this.isVisitable(context)) {
         return false;
      } else {
         FacesContext facesContext = context.getFacesContext();
         boolean visitRows = this.requiresRowIteration(context);
         int oldRowIndex = -1;
         if (visitRows) {
            oldRowIndex = this.getRowIndex();
            this.setRowIndex(-1);
         }

         this.pushComponentToEL(facesContext, (UIComponent)null);

         boolean var7;
         try {
            VisitResult result = context.invokeVisitCallback(this, callback);
            if (result == VisitResult.COMPLETE) {
               var7 = true;
               return var7;
            }

            if (result != VisitResult.ACCEPT || !this.doVisitChildren(context, visitRows)) {
               return false;
            }

            if (this.visitFacets(context, callback, visitRows)) {
               var7 = true;
               return var7;
            }

            if (!this.visitColumnsAndColumnFacets(context, callback, visitRows)) {
               if (!this.visitRows(context, callback, visitRows)) {
                  return false;
               }

               var7 = true;
               return var7;
            }

            var7 = true;
         } finally {
            this.popComponentFromEL(facesContext);
            if (visitRows) {
               this.setRowIndex(oldRowIndex);
            }

         }

         return var7;
      }
   }

   public void markInitialState() {
      if (this.isRowStatePreserved() && this.getFacesContext().getAttributes().containsKey("javax.faces.IS_BUILDING_INITIAL_STATE")) {
         this._initialDescendantFullComponentState = this.saveDescendantInitialComponentStates(this.getFacesContext(), this.getChildren().iterator(), false);
      }

      super.markInitialState();
   }

   private void restoreFullDescendantComponentStates(FacesContext facesContext, Iterator childIterator, Object state, boolean restoreChildFacets) {
      Iterator descendantStateIterator = null;

      while(childIterator.hasNext()) {
         if (descendantStateIterator == null && state != null) {
            descendantStateIterator = ((Collection)state).iterator();
         }

         UIComponent component = (UIComponent)childIterator.next();
         component.setId(component.getId());
         if (!component.isTransient()) {
            Object childState = null;
            Object descendantState = null;
            if (descendantStateIterator != null && descendantStateIterator.hasNext()) {
               Object[] object = (Object[])descendantStateIterator.next();
               childState = object[0];
               descendantState = object[1];
            }

            component.clearInitialState();
            component.restoreState(facesContext, childState);
            component.markInitialState();
            Iterator childsIterator;
            if (restoreChildFacets) {
               childsIterator = component.getFacetsAndChildren();
            } else {
               childsIterator = component.getChildren().iterator();
            }

            this.restoreFullDescendantComponentStates(facesContext, childsIterator, descendantState, true);
         }
      }

   }

   private Collection saveDescendantInitialComponentStates(FacesContext facesContext, Iterator childIterator, boolean saveChildFacets) {
      Collection childStates = null;

      while(childIterator.hasNext()) {
         if (childStates == null) {
            childStates = new ArrayList();
         }

         UIComponent child = (UIComponent)childIterator.next();
         if (!child.isTransient()) {
            Iterator childsIterator;
            if (saveChildFacets) {
               childsIterator = child.getFacetsAndChildren();
            } else {
               childsIterator = child.getChildren().iterator();
            }

            Object descendantState = this.saveDescendantInitialComponentStates(facesContext, childsIterator, true);
            Object state = child.saveState(facesContext);
            childStates.add(new Object[]{state, descendantState});
         }
      }

      return childStates;
   }

   private Map saveFullDescendantComponentStates(FacesContext facesContext, Map stateMap, Iterator childIterator, boolean saveChildFacets) {
      while(childIterator.hasNext()) {
         UIComponent child = (UIComponent)childIterator.next();
         if (!child.isTransient()) {
            Iterator childsIterator;
            if (saveChildFacets) {
               childsIterator = child.getFacetsAndChildren();
            } else {
               childsIterator = child.getChildren().iterator();
            }

            stateMap = this.saveFullDescendantComponentStates(facesContext, (Map)stateMap, childsIterator, true);
            Object state = child.saveState(facesContext);
            if (state != null) {
               if (stateMap == null) {
                  stateMap = new HashMap();
               }

               ((Map)stateMap).put(child.getClientId(facesContext), state);
            }
         }
      }

      return (Map)stateMap;
   }

   private void restoreFullDescendantComponentDeltaStates(FacesContext facesContext, Iterator childIterator, Object state, Object initialState, boolean restoreChildFacets) {
      Map descendantStateIterator = null;
      Iterator descendantFullStateIterator = null;

      while(childIterator.hasNext()) {
         if (descendantStateIterator == null && state != null) {
            descendantStateIterator = (Map)state;
         }

         if (descendantFullStateIterator == null && initialState != null) {
            descendantFullStateIterator = ((Collection)initialState).iterator();
         }

         UIComponent component = (UIComponent)childIterator.next();
         component.setId(component.getId());
         if (!component.isTransient()) {
            Object childInitialState = null;
            Object descendantInitialState = null;
            Object childState = null;
            if (descendantStateIterator != null && descendantStateIterator.containsKey(component.getClientId(facesContext))) {
               childState = descendantStateIterator.get(component.getClientId(facesContext));
            }

            if (descendantFullStateIterator != null && descendantFullStateIterator.hasNext()) {
               Object[] object = (Object[])((Object[])descendantFullStateIterator.next());
               childInitialState = object[0];
               descendantInitialState = object[1];
            }

            component.clearInitialState();
            if (childInitialState != null) {
               component.restoreState(facesContext, childInitialState);
               component.markInitialState();
               component.restoreState(facesContext, childState);
            } else {
               component.restoreState(facesContext, childState);
               component.markInitialState();
            }

            Iterator childsIterator;
            if (restoreChildFacets) {
               childsIterator = component.getFacetsAndChildren();
            } else {
               childsIterator = component.getChildren().iterator();
            }

            this.restoreFullDescendantComponentDeltaStates(facesContext, childsIterator, state, descendantInitialState, true);
         }
      }

   }

   private void restoreTransientDescendantComponentStates(FacesContext facesContext, Iterator childIterator, Map state, boolean restoreChildFacets) {
      while(childIterator.hasNext()) {
         UIComponent component = (UIComponent)childIterator.next();
         component.setId(component.getId());
         if (!component.isTransient()) {
            component.restoreTransientState(facesContext, state == null ? null : state.get(component.getClientId(facesContext)));
            Iterator childsIterator;
            if (restoreChildFacets) {
               childsIterator = component.getFacetsAndChildren();
            } else {
               childsIterator = component.getChildren().iterator();
            }

            this.restoreTransientDescendantComponentStates(facesContext, childsIterator, state, true);
         }
      }

   }

   private Map saveTransientDescendantComponentStates(FacesContext facesContext, Map childStates, Iterator childIterator, boolean saveChildFacets) {
      while(childIterator.hasNext()) {
         UIComponent child = (UIComponent)childIterator.next();
         if (!child.isTransient()) {
            Iterator childsIterator;
            if (saveChildFacets) {
               childsIterator = child.getFacetsAndChildren();
            } else {
               childsIterator = child.getChildren().iterator();
            }

            childStates = this.saveTransientDescendantComponentStates(facesContext, (Map)childStates, childsIterator, true);
            Object state = child.saveTransientState(facesContext);
            if (state != null) {
               if (childStates == null) {
                  childStates = new HashMap();
               }

               ((Map)childStates).put(child.getClientId(facesContext), state);
            }
         }
      }

      return (Map)childStates;
   }

   public void restoreState(FacesContext context, Object state) {
      if (state != null) {
         Object[] values = (Object[])((Object[])state);
         super.restoreState(context, values[0]);
         Object restoredRowStates = UIComponentBase.restoreAttachedState(context, values[1]);
         if (restoredRowStates == null) {
            if (!this._rowDeltaStates.isEmpty()) {
               this._rowDeltaStates.clear();
            }
         } else {
            this._rowDeltaStates = (Map)restoredRowStates;
         }

      }
   }

   private void resetClientIds(UIComponent component) {
      Iterator iterator = component.getFacetsAndChildren();

      while(iterator.hasNext()) {
         UIComponent child = (UIComponent)iterator.next();
         this.resetClientIds(child);
         child.setId(child.getId());
      }

   }

   public Object saveState(FacesContext context) {
      this.resetClientIds(this);
      if (this.initialStateMarked()) {
         Object superState = super.saveState(context);
         if (superState == null && this._rowDeltaStates.isEmpty()) {
            return null;
         } else {
            Object[] values = null;
            Object attachedState = UIComponentBase.saveAttachedState(context, this._rowDeltaStates);
            if (superState != null || attachedState != null) {
               values = new Object[]{superState, attachedState};
            }

            return values;
         }
      } else {
         Object[] values = new Object[]{super.saveState(context), UIComponentBase.saveAttachedState(context, this._rowDeltaStates)};
         return values;
      }
   }

   protected DataModel getDataModel() {
      if (this.model != null) {
         return this.model;
      } else {
         Object current = this.getValue();
         if (current == null) {
            this.setDataModel(new ListDataModel(Collections.EMPTY_LIST));
         } else if (current instanceof DataModel) {
            this.setDataModel((DataModel)current);
         } else if (current instanceof List) {
            this.setDataModel(new ListDataModel((List)current));
         } else if (Object[].class.isAssignableFrom(current.getClass())) {
            this.setDataModel(new ArrayDataModel((Object[])((Object[])current)));
         } else if (current instanceof ResultSet) {
            this.setDataModel(new ResultSetDataModel((ResultSet)current));
         } else if (current instanceof Result) {
            this.setDataModel(new ResultDataModel((Result)current));
         } else if (current instanceof Collection) {
            this.setDataModel(new CollectionDataModel((Collection)current));
         } else if (current instanceof Iterable) {
            this.setDataModel(new IterableDataModel((Iterable)current));
         } else if (current instanceof Map) {
            this.setDataModel(new IterableDataModel(((Map)current).entrySet()));
         } else {
            DataModel dataModel = this.createDataModel(current.getClass());
            if (dataModel != null) {
               dataModel.setWrappedData(current);
               this.setDataModel(dataModel);
            } else {
               this.setDataModel(new ScalarDataModel(current));
            }
         }

         return this.model;
      }
   }

   private DataModel createDataModel(Class forClass) {
      List dataModel = new ArrayList(1);
      CDI cdi = CDI.current();
      this.getDataModelClassesMap(cdi).entrySet().stream().filter((e) -> {
         return ((Class)e.getKey()).isAssignableFrom(forClass);
      }).findFirst().ifPresent((e) -> {
         dataModel.add(cdi.select((Class)e.getValue(), new Annotation[]{new FacesDataModelAnnotationLiteral((Class)e.getKey())}).get());
      });
      return dataModel.isEmpty() ? null : (DataModel)dataModel.get(0);
   }

   private Map getDataModelClassesMap(CDI cdi) {
      BeanManager beanManager = cdi.getBeanManager();
      Bean bean = beanManager.resolve(beanManager.getBeans("comSunFacesDataModelClassesMap"));
      Object beanReference = beanManager.getReference(bean, Map.class, beanManager.createCreationalContext(bean));
      return (Map)beanReference;
   }

   protected void setDataModel(DataModel dataModel) {
      this.model = dataModel;
   }

   private boolean requiresRowIteration(VisitContext ctx) {
      return !ctx.getHints().contains(VisitHint.SKIP_ITERATION);
   }

   private void preDecode(FacesContext context) {
      this.setDataModel((DataModel)null);
      Map saved = (Map)this.getStateHelper().get(UIData.PropertyKeys.saved);
      if (null == saved || !this.keepSaved(context)) {
         this.getStateHelper().remove(UIData.PropertyKeys.saved);
      }

   }

   private void preValidate(FacesContext context) {
      if (this.isNestedWithinIterator()) {
         this.setDataModel((DataModel)null);
      }

   }

   private void preUpdate(FacesContext context) {
      if (this.isNestedWithinIterator()) {
         this.setDataModel((DataModel)null);
      }

   }

   private void preEncode(FacesContext context) {
      this.setDataModel((DataModel)null);
      if (!this.keepSaved(context)) {
         this.getStateHelper().remove(UIData.PropertyKeys.saved);
      }

   }

   private void iterate(FacesContext context, PhaseId phaseId) {
      this.setRowIndex(-1);
      if (this.getFacetCount() > 0) {
         Iterator var3 = this.getFacets().values().iterator();

         while(var3.hasNext()) {
            UIComponent facet = (UIComponent)var3.next();
            if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
               facet.processDecodes(context);
            } else if (phaseId == PhaseId.PROCESS_VALIDATIONS) {
               facet.processValidators(context);
            } else {
               if (phaseId != PhaseId.UPDATE_MODEL_VALUES) {
                  throw new IllegalArgumentException();
               }

               facet.processUpdates(context);
            }
         }
      }

      List renderedColumns = new ArrayList(this.getChildCount());
      Iterator var12;
      if (this.getChildCount() > 0) {
         var12 = this.getChildren().iterator();

         while(var12.hasNext()) {
            UIComponent child = (UIComponent)var12.next();
            if (child instanceof UIColumn && child.isRendered()) {
               renderedColumns.add((UIColumn)child);
            }
         }
      }

      this.setRowIndex(-1);
      var12 = renderedColumns.iterator();

      while(true) {
         UIColumn column;
         do {
            if (!var12.hasNext()) {
               int processed = 0;
               int rowIndex = this.getFirst() - 1;
               int rows = this.getRows();

               label94:
               while(true) {
                  if (rows > 0) {
                     ++processed;
                     if (processed > rows) {
                        break;
                     }
                  }

                  ++rowIndex;
                  this.setRowIndex(rowIndex);
                  if (!this.isRowAvailable()) {
                     break;
                  }

                  Iterator var17 = renderedColumns.iterator();

                  while(true) {
                     UIColumn kid;
                     do {
                        if (!var17.hasNext()) {
                           continue label94;
                        }

                        kid = (UIColumn)var17.next();
                     } while(kid.getChildCount() <= 0);

                     Iterator var9 = kid.getChildren().iterator();

                     while(var9.hasNext()) {
                        UIComponent grandkid = (UIComponent)var9.next();
                        if (grandkid.isRendered()) {
                           if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
                              grandkid.processDecodes(context);
                           } else if (phaseId == PhaseId.PROCESS_VALIDATIONS) {
                              grandkid.processValidators(context);
                           } else {
                              if (phaseId != PhaseId.UPDATE_MODEL_VALUES) {
                                 throw new IllegalArgumentException();
                              }

                              grandkid.processUpdates(context);
                           }
                        }
                     }
                  }
               }

               this.setRowIndex(-1);
               return;
            }

            column = (UIColumn)var12.next();
         } while(column.getFacetCount() <= 0);

         Iterator var6 = column.getFacets().values().iterator();

         while(var6.hasNext()) {
            UIComponent columnFacet = (UIComponent)var6.next();
            if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
               columnFacet.processDecodes(context);
            } else if (phaseId == PhaseId.PROCESS_VALIDATIONS) {
               columnFacet.processValidators(context);
            } else {
               if (phaseId != PhaseId.UPDATE_MODEL_VALUES) {
                  throw new IllegalArgumentException();
               }

               columnFacet.processUpdates(context);
            }
         }
      }
   }

   private boolean doVisitChildren(VisitContext context, boolean visitRows) {
      if (visitRows) {
         this.setRowIndex(-1);
      }

      Collection idsToVisit = context.getSubtreeIdsToVisit(this);

      assert idsToVisit != null;

      return !idsToVisit.isEmpty();
   }

   private boolean visitFacets(VisitContext context, VisitCallback callback, boolean visitRows) {
      if (visitRows) {
         this.setRowIndex(-1);
      }

      if (this.getFacetCount() > 0) {
         Iterator var4 = this.getFacets().values().iterator();

         while(var4.hasNext()) {
            UIComponent facet = (UIComponent)var4.next();
            if (facet.visitTree(context, callback)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean visitColumnsAndColumnFacets(VisitContext context, VisitCallback callback, boolean visitRows) {
      if (visitRows) {
         this.setRowIndex(-1);
      }

      if (this.getChildCount() > 0) {
         Iterator var4 = this.getChildren().iterator();

         while(true) {
            UIComponent column;
            do {
               do {
                  if (!var4.hasNext()) {
                     return false;
                  }

                  column = (UIComponent)var4.next();
               } while(!(column instanceof UIColumn));

               VisitResult result = context.invokeVisitCallback(column, callback);
               if (result == VisitResult.COMPLETE) {
                  return true;
               }
            } while(column.getFacetCount() <= 0);

            Iterator var7 = column.getFacets().values().iterator();

            while(var7.hasNext()) {
               UIComponent columnFacet = (UIComponent)var7.next();
               if (columnFacet.visitTree(context, callback)) {
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private boolean visitRows(VisitContext context, VisitCallback callback, boolean visitRows) {
      int processed = 0;
      int rowIndex = 0;
      int rows = 0;
      if (visitRows) {
         rowIndex = this.getFirst() - 1;
         rows = this.getRows();
      }

      label57:
      do {
         if (visitRows) {
            if (rows > 0) {
               ++processed;
               if (processed > rows) {
                  break;
               }
            }

            ++rowIndex;
            this.setRowIndex(rowIndex);
            if (!this.isRowAvailable()) {
               break;
            }
         }

         if (this.getChildCount() > 0) {
            Iterator var7 = this.getChildren().iterator();

            while(true) {
               UIComponent kid;
               do {
                  do {
                     if (!var7.hasNext()) {
                        continue label57;
                     }

                     kid = (UIComponent)var7.next();
                  } while(!(kid instanceof UIColumn));
               } while(kid.getChildCount() <= 0);

               Iterator var9 = kid.getChildren().iterator();

               while(var9.hasNext()) {
                  UIComponent grandkid = (UIComponent)var9.next();
                  if (grandkid.visitTree(context, callback)) {
                     return true;
                  }
               }
            }
         }
      } while(visitRows);

      return false;
   }

   private boolean keepSaved(FacesContext context) {
      return this.contextHasErrorMessages(context) || this.isNestedWithinIterator();
   }

   private Boolean isNestedWithinIterator() {
      if (this.isNested != null) {
         return this.isNested;
      } else {
         UIComponent parent = this;

         while(null != (parent = ((UIComponent)parent).getParent())) {
            if (parent instanceof UIData || parent.getClass().getName().endsWith("UIRepeat")) {
               this.isNested = Boolean.TRUE;
               break;
            }
         }

         if (this.isNested == null) {
            this.isNested = Boolean.FALSE;
         }

         return this.isNested;
      }
   }

   private boolean contextHasErrorMessages(FacesContext context) {
      FacesMessage.Severity sev = context.getMaximumSeverity();
      return sev != null && FacesMessage.SEVERITY_ERROR.compareTo(sev) >= 0;
   }

   private void restoreDescendantState() {
      FacesContext context = this.getFacesContext();
      if (this.getChildCount() > 0) {
         Iterator var2 = this.getChildren().iterator();

         while(var2.hasNext()) {
            UIComponent kid = (UIComponent)var2.next();
            if (kid instanceof UIColumn) {
               this.restoreDescendantState(kid, context);
            }
         }
      }

   }

   private void restoreDescendantState(UIComponent component, FacesContext context) {
      String id = component.getId();
      component.setId(id);
      Map saved = (Map)this.getStateHelper().get(UIData.PropertyKeys.saved);
      String clientId;
      SavedState state;
      if (component instanceof EditableValueHolder) {
         EditableValueHolder input = (EditableValueHolder)component;
         clientId = component.getClientId(context);
         state = saved == null ? null : (SavedState)saved.get(clientId);
         if (state == null) {
            input.resetValue();
         } else {
            input.setValue(state.getValue());
            input.setValid(state.isValid());
            input.setSubmittedValue(state.getSubmittedValue());
            input.setLocalValueSet(state.isLocalValueSet());
         }
      } else if (component instanceof UIForm) {
         UIForm form = (UIForm)component;
         clientId = component.getClientId(context);
         state = saved == null ? null : (SavedState)saved.get(clientId);
         if (state == null) {
            form.setSubmitted(false);
         } else {
            form.setSubmitted(state.getSubmitted());
         }
      }

      Iterator var9;
      UIComponent facet;
      if (component.getChildCount() > 0) {
         var9 = component.getChildren().iterator();

         while(var9.hasNext()) {
            facet = (UIComponent)var9.next();
            this.restoreDescendantState(facet, context);
         }
      }

      if (component.getFacetCount() > 0) {
         var9 = component.getFacets().values().iterator();

         while(var9.hasNext()) {
            facet = (UIComponent)var9.next();
            this.restoreDescendantState(facet, context);
         }
      }

   }

   private void saveDescendantState() {
      FacesContext context = this.getFacesContext();
      if (this.getChildCount() > 0) {
         Iterator var2 = this.getChildren().iterator();

         while(var2.hasNext()) {
            UIComponent kid = (UIComponent)var2.next();
            if (kid instanceof UIColumn) {
               this.saveDescendantState(kid, context);
            }
         }
      }

   }

   private void saveDescendantState(UIComponent component, FacesContext context) {
      Map saved = (Map)this.getStateHelper().get(UIData.PropertyKeys.saved);
      if (component instanceof EditableValueHolder) {
         EditableValueHolder input = (EditableValueHolder)component;
         SavedState state = null;
         String clientId = component.getClientId(context);
         if (saved == null) {
            state = new SavedState();
         }

         if (state == null) {
            state = (SavedState)saved.get(clientId);
            if (state == null) {
               state = new SavedState();
            }
         }

         state.setValue(input.getLocalValue());
         state.setValid(input.isValid());
         state.setSubmittedValue(input.getSubmittedValue());
         state.setLocalValueSet(input.isLocalValueSet());
         if (state.hasDeltaState()) {
            this.getStateHelper().put(UIData.PropertyKeys.saved, clientId, state);
         } else if (saved != null) {
            this.getStateHelper().remove(UIData.PropertyKeys.saved, clientId);
         }
      } else if (component instanceof UIForm) {
         UIForm form = (UIForm)component;
         String clientId = component.getClientId(context);
         SavedState state = null;
         if (saved == null) {
            state = new SavedState();
         }

         if (state == null) {
            state = (SavedState)saved.get(clientId);
            if (state == null) {
               state = new SavedState();
            }
         }

         state.setSubmitted(form.isSubmitted());
         if (state.hasDeltaState()) {
            this.getStateHelper().put(UIData.PropertyKeys.saved, clientId, state);
         } else if (saved != null) {
            this.getStateHelper().remove(UIData.PropertyKeys.saved, clientId);
         }
      }

      Iterator var8;
      UIComponent facet;
      if (component.getChildCount() > 0) {
         var8 = component.getChildren().iterator();

         while(var8.hasNext()) {
            facet = (UIComponent)var8.next();
            this.saveDescendantState(facet, context);
         }
      }

      if (component.getFacetCount() > 0) {
         var8 = component.getFacets().values().iterator();

         while(var8.hasNext()) {
            facet = (UIComponent)var8.next();
            this.saveDescendantState(facet, context);
         }
      }

   }

   private static class FacesDataModelAnnotationLiteral extends AnnotationLiteral implements FacesDataModel {
      private static final long serialVersionUID = 1L;
      private final Class forClass;

      public FacesDataModelAnnotationLiteral(Class forClass) {
         this.forClass = forClass;
      }

      public Class forClass() {
         return this.forClass;
      }
   }

   static enum PropertyKeys {
      first,
      rowIndex,
      rows,
      saved,
      value,
      var,
      lastId,
      rowStatePreserved;
   }
}
