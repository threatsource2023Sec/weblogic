package com.sun.faces.facelets.tag.composite;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.visit.VisitCallback;
import javax.faces.component.visit.VisitContext;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ComponentSystemEvent;
import javax.faces.event.ComponentSystemEventListener;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

public class BehaviorHolderWrapper extends UIComponent implements ClientBehaviorHolder {
   private final UIComponent parent;
   private final String virtualEvent;
   private final String event;

   public BehaviorHolderWrapper(UIComponent parent, String virtualEvent, String event) {
      this.parent = parent;
      this.virtualEvent = virtualEvent;
      this.event = event;
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
      this.parent.broadcast(event);
   }

   public void decode(FacesContext context) {
      this.parent.decode(context);
   }

   public void encodeBegin(FacesContext context) throws IOException {
      this.parent.encodeBegin(context);
   }

   public void encodeChildren(FacesContext context) throws IOException {
      this.parent.encodeChildren(context);
   }

   public void encodeEnd(FacesContext context) throws IOException {
      this.parent.encodeEnd(context);
   }

   public UIComponent findComponent(String expr) {
      return this.parent.findComponent(expr);
   }

   public Map getAttributes() {
      return this.parent.getAttributes();
   }

   public Map getPassThroughAttributes(boolean create) {
      return this.parent.getPassThroughAttributes(create);
   }

   public int getChildCount() {
      return this.parent.getChildCount();
   }

   public List getChildren() {
      return this.parent.getChildren();
   }

   public String getClientId(FacesContext context) {
      return this.parent.getClientId(context);
   }

   public UIComponent getFacet(String name) {
      return this.parent.getFacet(name);
   }

   public Map getFacets() {
      return this.parent.getFacets();
   }

   public Iterator getFacetsAndChildren() {
      return this.parent.getFacetsAndChildren();
   }

   public String getFamily() {
      return this.parent.getFamily();
   }

   public String getId() {
      return this.parent.getId();
   }

   public UIComponent getParent() {
      return this.parent.getParent();
   }

   public String getRendererType() {
      return this.parent.getRendererType();
   }

   public boolean getRendersChildren() {
      return this.parent.getRendersChildren();
   }

   /** @deprecated */
   public ValueBinding getValueBinding(String name) {
      return this.parent.getValueBinding(name);
   }

   public ValueExpression getValueExpression(String name) {
      return this.parent.getValueExpression(name);
   }

   public boolean invokeOnComponent(FacesContext context, String clientId, ContextCallback callback) throws FacesException {
      return this.parent.invokeOnComponent(context, clientId, callback);
   }

   public boolean isInView() {
      return this.parent.isInView();
   }

   public boolean isRendered() {
      return this.parent.isRendered();
   }

   public boolean isTransient() {
      return this.parent.isTransient();
   }

   public void processDecodes(FacesContext context) {
      this.parent.processDecodes(context);
   }

   public void processEvent(ComponentSystemEvent event) throws AbortProcessingException {
      this.parent.processEvent(event);
   }

   public void processRestoreState(FacesContext context, Object state) {
      this.parent.processRestoreState(context, state);
   }

   public Object processSaveState(FacesContext context) {
      return this.parent.processSaveState(context);
   }

   public void processUpdates(FacesContext context) {
      this.parent.processUpdates(context);
   }

   public void processValidators(FacesContext context) {
      this.parent.processValidators(context);
   }

   public void queueEvent(FacesEvent event) {
      this.parent.queueEvent(event);
   }

   public void restoreState(FacesContext context, Object state) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         this.parent.restoreState(context, state);
      }
   }

   public Object saveState(FacesContext context) {
      if (context == null) {
         throw new NullPointerException();
      } else {
         return this.parent.saveState(context);
      }
   }

   public void setId(String id) {
      this.parent.setId(id);
   }

   public void setParent(UIComponent parent) {
      parent.setParent(parent);
   }

   public void setRendered(boolean rendered) {
      this.parent.setRendered(rendered);
   }

   public void setRendererType(String rendererType) {
      this.parent.setRendererType(rendererType);
   }

   public void setTransient(boolean newTransientValue) {
      this.parent.setTransient(newTransientValue);
   }

   /** @deprecated */
   public void setValueBinding(String name, ValueBinding binding) {
      this.parent.setValueBinding(name, binding);
   }

   public void setValueExpression(String name, ValueExpression binding) {
      this.parent.setValueExpression(name, binding);
   }

   public void subscribeToEvent(Class eventClass, ComponentSystemEventListener componentListener) {
      this.parent.subscribeToEvent(eventClass, componentListener);
   }

   public void unsubscribeFromEvent(Class eventClass, ComponentSystemEventListener componentListener) {
      this.parent.unsubscribeFromEvent(eventClass, componentListener);
   }

   public boolean visitTree(VisitContext context, VisitCallback callback) {
      return this.parent.visitTree(context, callback);
   }

   protected void addFacesListener(FacesListener listener) {
   }

   protected FacesContext getFacesContext() {
      return FacesContext.getCurrentInstance();
   }

   protected FacesListener[] getFacesListeners(Class clazz) {
      return new FacesListener[0];
   }

   protected Renderer getRenderer(FacesContext context) {
      return null;
   }

   protected void removeFacesListener(FacesListener listener) {
   }

   public void addClientBehavior(String eventName, ClientBehavior behavior) {
      if (this.parent instanceof ClientBehaviorHolder) {
         ClientBehaviorHolder parentHolder = (ClientBehaviorHolder)this.parent;
         if (this.virtualEvent.equals(eventName)) {
            parentHolder.addClientBehavior(this.event, behavior);
         }

      } else {
         throw new FacesException("Unable to attach behavior to non-ClientBehaviorHolder parent:" + this.parent);
      }
   }

   public Map getClientBehaviors() {
      if (this.parent instanceof ClientBehaviorHolder) {
         ClientBehaviorHolder parentHolder = (ClientBehaviorHolder)this.parent;
         Map behaviors = new HashMap(1);
         behaviors.put(this.virtualEvent, parentHolder.getClientBehaviors().get(this.event));
         return Collections.unmodifiableMap(behaviors);
      } else {
         throw new FacesException("Unable to get behaviors from non-ClientBehaviorHolder parent:" + this.parent);
      }
   }

   public String getDefaultEventName() {
      return this.virtualEvent;
   }

   public Collection getEventNames() {
      return Collections.singleton(this.virtualEvent);
   }
}
