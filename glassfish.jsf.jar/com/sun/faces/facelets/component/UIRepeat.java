package com.sun.faces.facelets.component;

import com.sun.faces.cdi.CdiUtils;
import com.sun.faces.facelets.tag.IterationStatus;
import com.sun.faces.renderkit.RenderKitUtils;
import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UINamingContainer;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.component.visit.VisitHint;
import javax.faces.component.visit.VisitResult;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.event.PhaseId;
import javax.faces.event.PostValidateEvent;
import javax.faces.event.PreValidateEvent;
import javax.faces.model.ArrayDataModel;
import javax.faces.model.DataModel;
import javax.faces.model.IterableDataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.ResultSetDataModel;
import javax.faces.model.ScalarDataModel;
import javax.faces.render.Renderer;

public class UIRepeat extends UINamingContainer {
   public static final String COMPONENT_TYPE = "facelets.ui.Repeat";
   public static final String COMPONENT_FAMILY = "facelets";
   private static final DataModel EMPTY_MODEL = new ListDataModel(Collections.emptyList());
   private Object value;
   private transient DataModel model;
   private String var;
   private String varStatus;
   private int index = -1;
   private Integer begin;
   private Integer end;
   private Integer step;
   private Integer size;
   private transient StringBuffer buffer;
   private transient Object origValueOfVar;
   private transient Object origValueOfVarStatus;
   private Map childState;
   private static final SavedState NULL_STATE = new SavedState();

   public UIRepeat() {
      this.setRendererType("facelets.ui.Repeat");
   }

   public String getFamily() {
      return "facelets";
   }

   public void setEnd(Integer end) {
      this.end = end;
   }

   public Integer getEnd() {
      if (this.end != null) {
         return this.end;
      } else {
         ValueExpression ve = this.getValueExpression("end");
         return ve != null ? (Integer)ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setSize(Integer size) {
      this.size = size;
   }

   public Integer getSize() {
      if (this.size != null) {
         return this.size;
      } else {
         ValueExpression ve = this.getValueExpression("size");
         return ve != null ? (Integer)ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setOffset(Integer offset) {
      this.begin = offset;
   }

   public Integer getOffset() {
      if (this.begin != null) {
         return this.begin;
      } else {
         ValueExpression ve = this.getValueExpression("offset");
         return ve != null ? (Integer)ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setBegin(Integer begin) {
      this.begin = begin;
   }

   public Integer getBegin() {
      if (this.begin != null) {
         return this.begin;
      } else {
         ValueExpression ve = this.getValueExpression("begin");
         return ve != null ? (Integer)ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public void setStep(Integer step) {
      this.step = step;
   }

   public Integer getStep() {
      if (this.step != null) {
         return this.step;
      } else {
         ValueExpression ve = this.getValueExpression("step");
         return ve != null ? (Integer)ve.getValue(this.getFacesContext().getELContext()) : null;
      }
   }

   public String getVar() {
      return this.var;
   }

   public void setVar(String var) {
      this.var = var;
   }

   public String getVarStatus() {
      return this.varStatus;
   }

   public void setVarStatus(String varStatus) {
      this.varStatus = varStatus;
   }

   private void resetDataModel() {
      if (this.isNestedInIterator()) {
         this.setDataModel((DataModel)null);
      }

   }

   private void setDataModel(DataModel model) {
      this.model = model;
   }

   private DataModel getDataModel() {
      if (this.model == null) {
         Object val = this.getValue();
         if (val == null) {
            Integer begin = this.getBegin();
            Integer end = this.getEnd();
            if (end == null) {
               if (begin != null) {
                  throw new IllegalArgumentException("end");
               }

               this.model = EMPTY_MODEL;
            } else {
               int b = begin == null ? 0 : begin;
               int e = end;
               int d = b < e ? 1 : (b > e ? -1 : 0);
               int s = Math.abs(e - b) + 1;
               Integer[] array = new Integer[s];

               for(int i = 0; i < s; ++i) {
                  array[i] = b + i * d;
               }

               this.model = new ArrayDataModel(array);
               this.setBegin(0);
               this.setEnd(s);
            }
         } else if (val instanceof DataModel) {
            this.model = (DataModel)val;
         } else if (val instanceof List) {
            this.model = new ListDataModel((List)val);
         } else if (Object[].class.isAssignableFrom(val.getClass())) {
            this.model = new ArrayDataModel((Object[])((Object[])val));
         } else if (val instanceof ResultSet) {
            this.model = new ResultSetDataModel((ResultSet)val);
         } else if (val instanceof Iterable) {
            this.model = new IterableDataModel((Iterable)val);
         } else if (val instanceof Map) {
            this.model = new IterableDataModel(((Map)val).entrySet());
         } else {
            DataModel dataModel = CdiUtils.createDataModel(val.getClass());
            if (dataModel != null) {
               dataModel.setWrappedData(val);
               this.model = dataModel;
            } else {
               this.model = new ScalarDataModel(val);
            }
         }
      }

      return this.model;
   }

   public Object getValue() {
      if (this.value == null) {
         ValueExpression ve = this.getValueExpression("value");
         if (ve != null) {
            return ve.getValue(this.getFacesContext().getELContext());
         }
      }

      return this.value;
   }

   public void setValue(Object value) {
      this.value = value;
   }

   private StringBuffer getBuffer() {
      if (this.buffer == null) {
         this.buffer = new StringBuffer();
      }

      this.buffer.setLength(0);
      return this.buffer;
   }

   public String getClientId(FacesContext faces) {
      String id = super.getClientId(faces);
      if (this.index >= 0) {
         id = this.getBuffer().append(id).append(getSeparatorChar(faces)).append(this.index).toString();
      }

      return id;
   }

   private void captureOrigValue(FacesContext ctx) {
      if (this.var != null || this.varStatus != null) {
         Map attrs = ctx.getExternalContext().getRequestMap();
         if (this.var != null) {
            this.origValueOfVar = attrs.get(this.var);
         }

         if (this.varStatus != null) {
            this.origValueOfVarStatus = attrs.get(this.varStatus);
         }
      }

   }

   private void restoreOrigValue(FacesContext ctx) {
      if (this.var != null || this.varStatus != null) {
         Map attrs = ctx.getExternalContext().getRequestMap();
         if (this.var != null) {
            if (this.origValueOfVar != null) {
               attrs.put(this.var, this.origValueOfVar);
            } else {
               attrs.remove(this.var);
            }
         }

         if (this.varStatus != null) {
            if (this.origValueOfVarStatus != null) {
               attrs.put(this.varStatus, this.origValueOfVarStatus);
            } else {
               attrs.remove(this.varStatus);
            }
         }
      }

   }

   private Map getChildState() {
      if (this.childState == null) {
         this.childState = new HashMap();
      }

      return this.childState;
   }

   private void clearChildState() {
      this.childState = null;
   }

   private void saveChildState(FacesContext ctx) {
      if (this.getChildCount() > 0) {
         Iterator var2 = this.getChildren().iterator();

         while(var2.hasNext()) {
            UIComponent uiComponent = (UIComponent)var2.next();
            this.saveChildState(ctx, uiComponent);
         }
      }

   }

   private void removeChildState(FacesContext ctx) {
      if (this.getChildCount() > 0) {
         Iterator var2 = this.getChildren().iterator();

         while(var2.hasNext()) {
            UIComponent uiComponent = (UIComponent)var2.next();
            this.removeChildState(ctx, uiComponent);
         }

         if (this.childState != null) {
            this.childState.remove(this.getClientId(ctx));
         }
      }

   }

   private void removeChildState(FacesContext faces, UIComponent c) {
      String id = c.getId();
      c.setId(id);
      Iterator itr = c.getFacetsAndChildren();

      while(itr.hasNext()) {
         this.removeChildState(faces, (UIComponent)itr.next());
      }

      if (this.childState != null) {
         this.childState.remove(c.getClientId(faces));
      }

   }

   private void saveChildState(FacesContext faces, UIComponent c) {
      if (c instanceof EditableValueHolder && !c.isTransient()) {
         String clientId = c.getClientId(faces);
         SavedState ss = (SavedState)this.getChildState().get(clientId);
         if (ss == null) {
            ss = new SavedState();
            this.getChildState().put(clientId, ss);
         }

         ss.populate((EditableValueHolder)c);
      }

      Iterator itr = c.getFacetsAndChildren();

      while(itr.hasNext()) {
         this.saveChildState(faces, (UIComponent)itr.next());
      }

   }

   private void restoreChildState(FacesContext ctx) {
      if (this.getChildCount() > 0) {
         Iterator var2 = this.getChildren().iterator();

         while(var2.hasNext()) {
            UIComponent uiComponent = (UIComponent)var2.next();
            this.restoreChildState(ctx, uiComponent);
         }
      }

   }

   private void restoreChildState(FacesContext faces, UIComponent c) {
      String id = c.getId();
      c.setId(id);
      if (c instanceof EditableValueHolder) {
         EditableValueHolder evh = (EditableValueHolder)c;
         String clientId = c.getClientId(faces);
         SavedState ss = (SavedState)this.getChildState().get(clientId);
         if (ss != null) {
            ss.apply(evh);
         } else {
            NULL_STATE.apply(evh);
         }
      }

      Iterator itr = c.getFacetsAndChildren();

      while(itr.hasNext()) {
         this.restoreChildState(faces, (UIComponent)itr.next());
      }

   }

   private boolean keepSaved(FacesContext context) {
      return this.hasErrorMessages(context) || this.isNestedInIterator();
   }

   private boolean hasErrorMessages(FacesContext context) {
      FacesMessage.Severity sev = context.getMaximumSeverity();
      return sev != null && FacesMessage.SEVERITY_ERROR.compareTo(sev) <= 0;
   }

   private boolean isNestedInIterator() {
      for(UIComponent parent = this.getParent(); parent != null; parent = parent.getParent()) {
         if (parent instanceof UIData || parent instanceof UIRepeat) {
            return true;
         }
      }

      return false;
   }

   private void setIndex(FacesContext ctx, int index) {
      DataModel localModel = this.getDataModel();
      if (this.index != -1 && localModel.isRowAvailable()) {
         this.saveChildState(ctx);
      } else if (this.index >= 0 && this.childState != null) {
         this.removeChildState(ctx);
      }

      this.index = index;
      localModel.setRowIndex(index);
      if (this.index != -1 && this.var != null && localModel.isRowAvailable()) {
         Map attrs = ctx.getExternalContext().getRequestMap();
         attrs.put(this.var, localModel.getRowData());
      }

      if (this.index != -1 && localModel.isRowAvailable()) {
         this.restoreChildState(ctx);
      }

   }

   private void updateIterationStatus(FacesContext ctx, IterationStatus status) {
      if (this.varStatus != null) {
         Map attrs = ctx.getExternalContext().getRequestMap();
         attrs.put(this.varStatus, status);
      }

   }

   private boolean isIndexAvailable() {
      return this.getDataModel().isRowAvailable();
   }

   public void process(FacesContext faces, PhaseId phase) {
      if (this.isRendered()) {
         this.resetDataModel();
         if (PhaseId.RENDER_RESPONSE.equals(phase) && !this.hasErrorMessages(faces)) {
            this.clearChildState();
         }

         this.captureOrigValue(faces);
         this.setIndex(faces, -1);

         try {
            if (this.getChildCount() > 0) {
               Integer begin = this.getBegin();
               Integer step = this.getStep();
               Integer end = this.getEnd();
               Integer offset = this.getOffset();
               if (null != offset && offset > 0) {
                  begin = offset;
               }

               Integer size = this.getSize();
               if (null != size) {
                  end = size;
               }

               String rendererType = this.getRendererType();
               Renderer renderer = null;
               if (rendererType != null) {
                  renderer = this.getRenderer(faces);
               }

               int rowCount = this.getDataModel().getRowCount();
               int i = begin != null ? begin : 0;
               int e = end != null ? end : rowCount;
               int s = step != null ? step : 1;
               this.validateIterationControlValues(rowCount, i, e);
               if (null != size && size > 0) {
                  e = size - 1;
               }

               this.setIndex(faces, i);
               this.updateIterationStatus(faces, new IterationStatus(true, i + s > e || rowCount == 1, i, begin, end, step));

               while(i <= e && this.isIndexAvailable()) {
                  if (PhaseId.RENDER_RESPONSE.equals(phase) && renderer != null) {
                     renderer.encodeChildren(faces, this);
                  } else {
                     Iterator itr = this.getChildren().iterator();

                     while(itr.hasNext()) {
                        UIComponent c = (UIComponent)itr.next();
                        if (PhaseId.APPLY_REQUEST_VALUES.equals(phase)) {
                           c.processDecodes(faces);
                        } else if (PhaseId.PROCESS_VALIDATIONS.equals(phase)) {
                           c.processValidators(faces);
                        } else if (PhaseId.UPDATE_MODEL_VALUES.equals(phase)) {
                           c.processUpdates(faces);
                        } else if (PhaseId.RENDER_RESPONSE.equals(phase)) {
                           c.encodeAll(faces);
                        }
                     }
                  }

                  i += s;
                  this.setIndex(faces, i);
                  this.updateIterationStatus(faces, new IterationStatus(false, i + s >= e, i, begin, end, step));
               }
            }
         } catch (IOException var19) {
            throw new FacesException(var19);
         } finally {
            this.setIndex(faces, -1);
            this.restoreOrigValue(faces);
         }

         if (PhaseId.RENDER_RESPONSE.equals(phase)) {
            this.resetClientIds(this);
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

   public boolean invokeOnComponent(FacesContext faces, String clientId, ContextCallback callback) throws FacesException {
      String id = super.getClientId(faces);
      if (clientId.equals(id)) {
         this.pushComponentToEL(faces, this);

         try {
            callback.invokeContextCallback(faces, this);
         } finally {
            this.popComponentFromEL(faces);
         }

         return true;
      } else {
         if (clientId.startsWith(id)) {
            int prevIndex = this.index;
            int idxStart = clientId.indexOf(getSeparatorChar(faces), id.length());
            if (idxStart == -1 || !Character.isDigit(clientId.charAt(idxStart + 1))) {
               return super.invokeOnComponent(faces, clientId, callback);
            }

            int idxEnd = clientId.indexOf(getSeparatorChar(faces), idxStart + 1);
            if (idxEnd != -1) {
               int newIndex = Integer.parseInt(clientId.substring(idxStart + 1, idxEnd));
               boolean found = false;

               try {
                  this.captureOrigValue(faces);
                  this.setIndex(faces, newIndex);
                  if (this.isIndexAvailable()) {
                     found = super.invokeOnComponent(faces, clientId, callback);
                  }
               } finally {
                  this.setIndex(faces, prevIndex);
                  this.restoreOrigValue(faces);
               }

               return found;
            }
         }

         return false;
      }
   }

   public boolean visitTree(VisitContext context, VisitCallback callback) {
      if (!this.isVisitable(context)) {
         return false;
      } else {
         FacesContext facesContext = context.getFacesContext();
         boolean visitRows = this.requiresRowIteration(context);
         int oldRowIndex = -1;
         if (visitRows) {
            oldRowIndex = this.getDataModel().getRowIndex();
            this.setIndex(facesContext, -1);
         }

         this.setDataModel((DataModel)null);
         this.pushComponentToEL(facesContext, (UIComponent)null);

         try {
            VisitResult result = context.invokeVisitCallback(this, callback);
            boolean var13;
            if (result == VisitResult.COMPLETE) {
               var13 = true;
               return var13;
            } else if (result != VisitResult.ACCEPT || !this.doVisitChildren(context)) {
               return false;
            } else if (visitRows) {
               if (!this.visitChildren(context, callback)) {
                  return false;
               } else {
                  var13 = true;
                  return var13;
               }
            } else {
               Iterator var7 = this.getChildren().iterator();

               UIComponent kid;
               do {
                  if (!var7.hasNext()) {
                     return false;
                  }

                  kid = (UIComponent)var7.next();
               } while(!kid.visitTree(context, callback));

               boolean var9 = true;
               return var9;
            }
         } finally {
            this.popComponentFromEL(facesContext);
            if (visitRows) {
               this.setIndex(facesContext, oldRowIndex);
            }

         }
      }
   }

   private boolean requiresRowIteration(VisitContext ctx) {
      boolean shouldIterate = !ctx.getHints().contains(VisitHint.SKIP_ITERATION);
      if (!shouldIterate) {
         FacesContext faces = ctx.getFacesContext();
         String sourceId = RenderKitUtils.PredefinedPostbackParameter.BEHAVIOR_SOURCE_PARAM.getValue(faces);
         boolean containsSource = sourceId != null ? sourceId.startsWith(super.getClientId(faces) + getSeparatorChar(faces)) : false;
         return containsSource;
      } else {
         return shouldIterate;
      }
   }

   private boolean doVisitChildren(VisitContext context) {
      if (this.requiresRowIteration(context)) {
         this.setIndex(context.getFacesContext(), -1);
      }

      Collection idsToVisit = context.getSubtreeIdsToVisit(this);

      assert idsToVisit != null;

      return !idsToVisit.isEmpty();
   }

   private void validateIterationControlValues(int rowCount, int begin, int end) {
      if (rowCount != 0) {
         if (begin > rowCount) {
            throw new FacesException("Iteration start index is greater than the number of available rows.");
         } else if (begin > end) {
            throw new FacesException("Iteration start index is greater than the end index.");
         } else if (end > rowCount) {
            throw new FacesException("Iteration end index is greater than the number of available rows.");
         }
      }
   }

   private boolean visitChildren(VisitContext context, VisitCallback callback) {
      Integer begin = this.getBegin();
      Integer end = this.getEnd();
      Integer step = this.getStep();
      int rowCount = this.getDataModel().getRowCount();
      int i = begin != null ? begin : 0;
      int e = end != null ? end : rowCount;
      int s = step != null ? step : 1;
      this.validateIterationControlValues(rowCount, i, e);
      FacesContext faces = context.getFacesContext();
      this.setIndex(faces, i);
      this.updateIterationStatus(faces, new IterationStatus(true, i + s > e || rowCount == 1, i, begin, end, step));

      while(i < e && this.isIndexAvailable()) {
         this.setIndex(faces, i);
         this.updateIterationStatus(faces, new IterationStatus(false, i + s >= e, i, begin, end, step));
         Iterator var11 = this.getChildren().iterator();

         while(var11.hasNext()) {
            UIComponent kid = (UIComponent)var11.next();
            if (kid.visitTree(context, callback)) {
               return true;
            }
         }

         i += s;
      }

      return false;
   }

   public void processDecodes(FacesContext faces) {
      if (this.isRendered()) {
         this.setDataModel((DataModel)null);
         if (!this.keepSaved(faces)) {
            this.childState = null;
         }

         this.process(faces, PhaseId.APPLY_REQUEST_VALUES);
         this.decode(faces);
      }
   }

   public void processUpdates(FacesContext faces) {
      if (this.isRendered()) {
         this.resetDataModel();
         this.process(faces, PhaseId.UPDATE_MODEL_VALUES);
      }
   }

   public void processValidators(FacesContext faces) {
      if (this.isRendered()) {
         this.resetDataModel();
         Application app = faces.getApplication();
         app.publishEvent(faces, PreValidateEvent.class, this);
         this.process(faces, PhaseId.PROCESS_VALIDATIONS);
         app.publishEvent(faces, PostValidateEvent.class, this);
      }
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      if (event instanceof IndexedEvent) {
         IndexedEvent idxEvent = (IndexedEvent)event;
         this.resetDataModel();
         int prevIndex = this.index;
         FacesEvent target = idxEvent.getTarget();
         FacesContext ctx = target.getFacesContext();
         UIComponent source = target.getComponent();
         UIComponent compositeParent = null;

         try {
            int rowCount = this.getDataModel().getRowCount();
            int idx = idxEvent.getIndex();
            this.setIndex(ctx, idx);
            Integer begin = this.getBegin();
            Integer end = this.getEnd();
            Integer step = this.getStep();
            int b = begin != null ? begin : 0;
            int e = end != null ? end : rowCount;
            int s = step != null ? step : 1;
            this.updateIterationStatus(ctx, new IterationStatus(idx == b, idx + s >= e || rowCount == 1, idx, begin, end, step));
            if (this.isIndexAvailable()) {
               if (!UIComponent.isCompositeComponent(source)) {
                  compositeParent = UIComponent.getCompositeComponentParent(source);
               }

               if (compositeParent != null) {
                  compositeParent.pushComponentToEL(ctx, (UIComponent)null);
               }

               source.pushComponentToEL(ctx, (UIComponent)null);
               source.broadcast(target);
            }
         } finally {
            source.popComponentFromEL(ctx);
            if (compositeParent != null) {
               compositeParent.popComponentFromEL(ctx);
            }

            this.updateIterationStatus(ctx, (IterationStatus)null);
            this.setIndex(ctx, prevIndex);
         }
      } else {
         super.broadcast(event);
      }

   }

   public void queueEvent(FacesEvent event) {
      super.queueEvent(new IndexedEvent(this, event, this.index));
   }

   public void restoreState(FacesContext faces, Object object) {
      if (faces == null) {
         throw new NullPointerException();
      } else if (object != null) {
         Object[] state = (Object[])((Object[])object);
         super.restoreState(faces, state[0]);
         this.childState = (Map)state[1];
         this.begin = (Integer)state[2];
         this.end = (Integer)state[3];
         this.step = (Integer)state[4];
         this.var = (String)state[5];
         this.varStatus = (String)state[6];
         this.value = state[7];
      }
   }

   public Object saveState(FacesContext faces) {
      this.resetClientIds(this);
      if (faces == null) {
         throw new NullPointerException();
      } else {
         Object[] state = new Object[]{super.saveState(faces), this.childState, this.begin, this.end, this.step, this.var, this.varStatus, this.value};
         return state;
      }
   }

   public void encodeChildren(FacesContext faces) throws IOException {
      if (this.isRendered()) {
         this.setDataModel((DataModel)null);
         if (!this.keepSaved(faces)) {
            this.childState = null;
         }

         this.process(faces, PhaseId.RENDER_RESPONSE);
      }
   }

   public boolean getRendersChildren() {
      if (this.getRendererType() != null) {
         Renderer renderer = this.getRenderer(this.getFacesContext());
         if (renderer != null) {
            return renderer.getRendersChildren();
         }
      }

      return true;
   }

   private static final class IndexedEvent extends FacesEvent {
      private static final long serialVersionUID = 1L;
      private final FacesEvent target;
      private final int index;

      public IndexedEvent(UIRepeat owner, FacesEvent target, int index) {
         super(owner);
         this.target = target;
         this.index = index;
      }

      public PhaseId getPhaseId() {
         return this.target.getPhaseId();
      }

      public void setPhaseId(PhaseId phaseId) {
         this.target.setPhaseId(phaseId);
      }

      public boolean isAppropriateListener(FacesListener listener) {
         return this.target.isAppropriateListener(listener);
      }

      public void processListener(FacesListener listener) {
         UIRepeat owner = (UIRepeat)this.getComponent();
         int prevIndex = owner.index;
         FacesContext ctx = FacesContext.getCurrentInstance();

         try {
            owner.setIndex(ctx, this.index);
            if (owner.isIndexAvailable()) {
               this.target.processListener(listener);
            }
         } finally {
            owner.setIndex(ctx, prevIndex);
         }

      }

      public int getIndex() {
         return this.index;
      }

      public FacesEvent getTarget() {
         return this.target;
      }
   }

   private static final class SavedState implements Serializable {
      private Object submittedValue;
      private static final long serialVersionUID = 2920252657338389849L;
      private boolean valid;
      private Object value;
      private boolean localValueSet;

      private SavedState() {
         this.valid = true;
      }

      Object getSubmittedValue() {
         return this.submittedValue;
      }

      void setSubmittedValue(Object submittedValue) {
         this.submittedValue = submittedValue;
      }

      boolean isValid() {
         return this.valid;
      }

      void setValid(boolean valid) {
         this.valid = valid;
      }

      Object getValue() {
         return this.value;
      }

      public void setValue(Object value) {
         this.value = value;
      }

      boolean isLocalValueSet() {
         return this.localValueSet;
      }

      public void setLocalValueSet(boolean localValueSet) {
         this.localValueSet = localValueSet;
      }

      public String toString() {
         return "submittedValue: " + this.submittedValue + " value: " + this.value + " localValueSet: " + this.localValueSet;
      }

      public void populate(EditableValueHolder evh) {
         this.value = evh.getLocalValue();
         this.valid = evh.isValid();
         this.submittedValue = evh.getSubmittedValue();
         this.localValueSet = evh.isLocalValueSet();
      }

      public void apply(EditableValueHolder evh) {
         evh.setValue(this.value);
         evh.setValid(this.valid);
         evh.setSubmittedValue(this.submittedValue);
         evh.setLocalValueSet(this.localValueSet);
      }

      // $FF: synthetic method
      SavedState(Object x0) {
         this();
      }
   }
}
