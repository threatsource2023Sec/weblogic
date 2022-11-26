package javax.faces.component;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ELException;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.servlet.jsp.jstl.sql.Result;

public class UIData extends UIComponentBase implements NamingContainer {
   public static final String COMPONENT_TYPE = "javax.faces.Data";
   public static final String COMPONENT_FAMILY = "javax.faces.Data";
   private Integer first;
   private DataModel model = null;
   private Object oldVar;
   private int rowIndex = -1;
   private Integer rows;
   private Map saved = new HashMap();
   private Object value = null;
   private String var = null;
   private String baseClientId = null;
   private int baseClientIdLength;
   private StringBuilder clientIdBuilder = null;
   private Boolean isNested = null;
   private Object[] values;

   public UIData() {
      this.setRendererType("javax.faces.Table");
   }

   public String getFamily() {
      return "javax.faces.Data";
   }

   public int getFirst() {
      if (this.first != null) {
         return this.first;
      } else {
         ValueExpression ve = this.getValueExpression("first");
         if (ve != null) {
            Integer value;
            try {
               value = (Integer)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var4) {
               throw new FacesException(var4);
            }

            return null == value ? this.first : value;
         } else {
            return 0;
         }
      }
   }

   public void setFirst(int first) {
      if (first < 0) {
         throw new IllegalArgumentException(String.valueOf(first));
      } else {
         this.first = first;
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
      return this.rowIndex;
   }

   public void setRowIndex(int rowIndex) {
      this.saveDescendantState();
      this.rowIndex = rowIndex;
      DataModel localModel = this.getDataModel();
      localModel.setRowIndex(rowIndex);
      if (rowIndex == -1) {
         this.setDataModel((DataModel)null);
      }

      if (this.var != null) {
         Map requestMap = this.getFacesContext().getExternalContext().getRequestMap();
         if (rowIndex == -1) {
            this.oldVar = requestMap.remove(this.var);
         } else if (this.isRowAvailable()) {
            requestMap.put(this.var, this.getRowData());
         } else {
            requestMap.remove(this.var);
            if (null != this.oldVar) {
               requestMap.put(this.var, this.oldVar);
               this.oldVar = null;
            }
         }
      }

      this.restoreDescendantState();
   }

   public int getRows() {
      if (this.rows != null) {
         return this.rows;
      } else {
         ValueExpression ve = this.getValueExpression("rows");
         if (ve != null) {
            Integer value;
            try {
               value = (Integer)ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var4) {
               throw new FacesException(var4);
            }

            return null == value ? this.rows : value;
         } else {
            return 0;
         }
      }
   }

   public void setRows(int rows) {
      if (rows < 0) {
         throw new IllegalArgumentException(String.valueOf(rows));
      } else {
         this.rows = rows;
      }
   }

   public String getVar() {
      return this.var;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public Object saveState(FacesContext context) {
      if (this.values == null) {
         this.values = new Object[7];
      }

      this.values[0] = super.saveState(context);
      this.values[1] = this.first;
      this.values[2] = this.rowIndex;
      this.values[3] = this.rows;
      this.values[4] = this.saved;
      this.values[5] = this.value;
      this.values[6] = this.var;
      return this.values;
   }

   public void restoreState(FacesContext context, Object state) {
      this.values = (Object[])((Object[])state);
      super.restoreState(context, this.values[0]);
      this.first = (Integer)this.values[1];
      this.rowIndex = (Integer)this.values[2];
      this.rows = (Integer)this.values[3];
      this.saved = TypedCollections.dynamicallyCastMap((Map)this.values[4], String.class, SavedState.class);
      this.value = this.values[5];
      this.var = (String)this.values[6];
   }

   public Object getValue() {
      if (this.value != null) {
         return this.value;
      } else {
         ValueExpression ve = this.getValueExpression("value");
         if (ve != null) {
            try {
               return ve.getValue(this.getFacesContext().getELContext());
            } catch (ELException var3) {
               throw new FacesException(var3);
            }
         } else {
            return null;
         }
      }
   }

   public void setValue(Object value) {
      this.setDataModel((DataModel)null);
      this.value = value;
   }

   /** @deprecated */
   public void setValueBinding(String name, ValueBinding binding) {
      if ("value".equals(name)) {
         this.setDataModel((DataModel)null);
      } else if ("var".equals(name) || "rowIndex".equals(name)) {
         throw new IllegalArgumentException();
      }

      super.setValueBinding(name, binding);
   }

   public void setValueExpression(String name, ValueExpression binding) {
      if ("value".equals(name)) {
         this.model = null;
      } else if ("var".equals(name) || "rowIndex".equals(name)) {
         throw new IllegalArgumentException();
      }

      super.setValueExpression(name, binding);
   }

   public String getClientId(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         if (this.baseClientId == null && this.clientIdBuilder == null) {
            if (!this.isNestedWithinUIData()) {
               this.clientIdBuilder = new StringBuilder(super.getClientId(context));
               this.baseClientId = this.clientIdBuilder.toString();
               this.baseClientIdLength = this.baseClientId.length() + 1;
               this.clientIdBuilder.append(':');
               this.clientIdBuilder.setLength(this.baseClientIdLength);
            } else {
               this.clientIdBuilder = new StringBuilder();
            }
         }

         if (this.rowIndex >= 0) {
            String cid;
            if (!this.isNestedWithinUIData()) {
               cid = this.clientIdBuilder.append(this.rowIndex).toString();
               this.clientIdBuilder.setLength(this.baseClientIdLength);
            } else {
               cid = this.clientIdBuilder.append(super.getClientId(context)).append(':').append(this.rowIndex).toString();
               this.clientIdBuilder.setLength(0);
            }

            return cid;
         } else {
            return !this.isNestedWithinUIData() ? this.baseClientId : super.getClientId(context);
         }
      }
   }

   public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback) throws FacesException {
      if (null != context && null != clientId && null != callback) {
         String myId = super.getClientId(context);
         boolean found = false;
         if (clientId.equals(myId)) {
            try {
               callback.invokeContextCallback(context, this);
               return true;
            } catch (Exception var19) {
               throw new FacesException(var19);
            }
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

               label194:
               while(true) {
                  do {
                     do {
                        if (!var6.hasNext()) {
                           break label194;
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

            int savedRowIndex = this.getRowIndex();

            try {
               if (myId.endsWith(':' + Integer.toString(savedRowIndex, 10))) {
                  int lastSep = myId.lastIndexOf(58);

                  assert -1 != lastSep;

                  myId = myId.substring(0, lastSep);
               }

               int preRowIndexSep;
               if (clientId.startsWith(myId) && -1 != (preRowIndexSep = clientId.indexOf(58, myId.length()))) {
                  ++preRowIndexSep;
                  int postRowIndexSep;
                  if (preRowIndexSep < clientId.length() && -1 != (postRowIndexSep = clientId.indexOf(58, preRowIndexSep + 1))) {
                     int newRow;
                     try {
                        newRow = Integer.valueOf(clientId.substring(preRowIndexSep, postRowIndexSep));
                     } catch (NumberFormatException var20) {
                        String message = "Trying to extract rowIndex from clientId '" + clientId + "' " + var20.getMessage();
                        throw new NumberFormatException(message);
                     }

                     this.setRowIndex(newRow);
                     if (this.isRowAvailable()) {
                        found = super.invokeOnComponent(context, clientId, callback);
                     }
                  }
               }
            } catch (FacesException var21) {
               throw var21;
            } catch (Exception var22) {
               throw new FacesException(var22);
            } finally {
               this.setRowIndex(savedRowIndex);
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
         WrapperEvent revent = (WrapperEvent)event;
         if (this.isNestedWithinUIData()) {
            this.setDataModel((DataModel)null);
         }

         int oldRowIndex = this.getRowIndex();
         this.setRowIndex(revent.getRowIndex());
         FacesEvent rowEvent = revent.getFacesEvent();
         rowEvent.getComponent().broadcast(rowEvent);
         this.setRowIndex(oldRowIndex);
      }
   }

   public void encodeBegin(FacesContext context) throws IOException {
      this.setDataModel((DataModel)null);
      if (!this.keepSaved(context)) {
         this.saved = new HashMap();
      }

      super.encodeBegin(context);
   }

   public void processDecodes(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         this.setDataModel((DataModel)null);
         if (null == this.saved || !this.keepSaved(context)) {
            this.saved = new HashMap();
         }

         this.iterate(context, PhaseId.APPLY_REQUEST_VALUES);
         this.decode(context);
      }
   }

   public void processValidators(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         if (this.isNestedWithinUIData()) {
            this.setDataModel((DataModel)null);
         }

         this.iterate(context, PhaseId.PROCESS_VALIDATIONS);
      }
   }

   public void processUpdates(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else if (this.isRendered()) {
         if (this.isNestedWithinUIData()) {
            this.setDataModel((DataModel)null);
         }

         this.iterate(context, PhaseId.UPDATE_MODEL_VALUES);
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
         } else {
            this.setDataModel(new ScalarDataModel(current));
         }

         return this.model;
      }
   }

   protected void setDataModel(DataModel dataModel) {
      this.model = dataModel;
   }

   private void iterate(FacesContext context, PhaseId phaseId) {
      this.setRowIndex(-1);
      Iterator var3;
      UIComponent column;
      if (this.getFacetCount() > 0) {
         var3 = this.getFacets().values().iterator();

         while(var3.hasNext()) {
            column = (UIComponent)var3.next();
            if (phaseId == PhaseId.APPLY_REQUEST_VALUES) {
               column.processDecodes(context);
            } else if (phaseId == PhaseId.PROCESS_VALIDATIONS) {
               column.processValidators(context);
            } else {
               if (phaseId != PhaseId.UPDATE_MODEL_VALUES) {
                  throw new IllegalArgumentException();
               }

               column.processUpdates(context);
            }
         }
      }

      this.setRowIndex(-1);
      if (this.getChildCount() > 0) {
         var3 = this.getChildren().iterator();

         label126:
         while(true) {
            do {
               do {
                  do {
                     if (!var3.hasNext()) {
                        break label126;
                     }

                     column = (UIComponent)var3.next();
                  } while(!(column instanceof UIColumn));
               } while(!column.isRendered());
            } while(column.getFacetCount() <= 0);

            Iterator var5 = column.getFacets().values().iterator();

            while(var5.hasNext()) {
               UIComponent columnFacet = (UIComponent)var5.next();
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

      int processed = 0;
      int rowIndex = this.getFirst() - 1;
      int rows = this.getRows();

      label103:
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

         if (this.getChildCount() > 0) {
            Iterator var13 = this.getChildren().iterator();

            while(true) {
               UIComponent kid;
               do {
                  do {
                     do {
                        if (!var13.hasNext()) {
                           continue label103;
                        }

                        kid = (UIComponent)var13.next();
                     } while(!(kid instanceof UIColumn));
                  } while(!kid.isRendered());
               } while(kid.getChildCount() <= 0);

               Iterator var8 = kid.getChildren().iterator();

               while(var8.hasNext()) {
                  UIComponent grandkid = (UIComponent)var8.next();
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
      }

      this.setRowIndex(-1);
   }

   private boolean keepSaved(FacesContext context) {
      return this.contextHasErrorMessages(context) || this.isNestedWithinUIData();
   }

   private Boolean isNestedWithinUIData() {
      if (this.isNested != null) {
         return this.isNested;
      } else {
         UIComponent parent = this;

         while(null != (parent = ((UIComponent)parent).getParent())) {
            if (parent instanceof UIData) {
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
      String clientId;
      SavedState state;
      if (component instanceof EditableValueHolder) {
         EditableValueHolder input = (EditableValueHolder)component;
         clientId = component.getClientId(context);
         state = (SavedState)this.saved.get(clientId);
         if (state == null) {
            state = new SavedState();
         }

         input.setValue(state.getValue());
         input.setValid(state.isValid());
         input.setSubmittedValue(state.getSubmittedValue());
         input.setLocalValueSet(state.isLocalValueSet());
      } else if (component instanceof UIForm) {
         UIForm form = (UIForm)component;
         clientId = component.getClientId(context);
         state = (SavedState)this.saved.get(clientId);
         if (state == null) {
            state = new SavedState();
         }

         form.setSubmitted(state.getSubmitted());
         state.setSubmitted(form.isSubmitted());
      }

      Iterator var8;
      UIComponent facet;
      if (component.getChildCount() > 0) {
         var8 = component.getChildren().iterator();

         while(var8.hasNext()) {
            facet = (UIComponent)var8.next();
            this.restoreDescendantState(facet, context);
         }
      }

      if (component.getFacetCount() > 0) {
         var8 = component.getFacets().values().iterator();

         while(var8.hasNext()) {
            facet = (UIComponent)var8.next();
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
      String clientId;
      SavedState state;
      if (component instanceof EditableValueHolder) {
         EditableValueHolder input = (EditableValueHolder)component;
         clientId = component.getClientId(context);
         state = (SavedState)this.saved.get(clientId);
         if (state == null) {
            state = new SavedState();
            this.saved.put(clientId, state);
         }

         state.setValue(input.getLocalValue());
         state.setValid(input.isValid());
         state.setSubmittedValue(input.getSubmittedValue());
         state.setLocalValueSet(input.isLocalValueSet());
      } else if (component instanceof UIForm) {
         UIForm form = (UIForm)component;
         clientId = component.getClientId(context);
         state = (SavedState)this.saved.get(clientId);
         if (state == null) {
            state = new SavedState();
         }

         form.setSubmitted(state.getSubmitted());
         state.setSubmitted(form.isSubmitted());
      }

      Iterator var7;
      UIComponent facet;
      if (component.getChildCount() > 0) {
         var7 = component.getChildren().iterator();

         while(var7.hasNext()) {
            facet = (UIComponent)var7.next();
            this.saveDescendantState(facet, context);
         }
      }

      if (component.getFacetCount() > 0) {
         var7 = component.getFacets().values().iterator();

         while(var7.hasNext()) {
            facet = (UIComponent)var7.next();
            this.saveDescendantState(facet, context);
         }
      }

   }
}
