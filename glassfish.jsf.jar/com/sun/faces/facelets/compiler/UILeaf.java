package com.sun.faces.facelets.compiler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.search.UntargetableComponent;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;
import javax.faces.render.Renderer;

public class UILeaf extends UIComponentBase implements UntargetableComponent {
   private static final Map facets = new HashMap(0, 1.0F) {
      private static final long serialVersionUID = 6132215325480325558L;

      public void putAll(Map map) {
      }

      public UIComponent put(String name, UIComponent value) {
         return null;
      }
   };
   private UIComponent parent;
   private boolean returnLocalTransient = true;

   public ValueBinding getValueBinding(String binding) {
      return null;
   }

   public void setValueBinding(String name, ValueBinding binding) {
   }

   public ValueExpression getValueExpression(String name) {
      return null;
   }

   public void setValueExpression(String name, ValueExpression arg1) {
   }

   public String getFamily() {
      return "facelets.LiteralText";
   }

   public UIComponent getParent() {
      return this.parent;
   }

   public void setParent(UIComponent parent) {
      this.parent = parent;
   }

   public String getRendererType() {
      return null;
   }

   public void setRendererType(String rendererType) {
   }

   public boolean getRendersChildren() {
      return true;
   }

   public List getChildren() {
      return Collections.emptyList();
   }

   public int getChildCount() {
      return 0;
   }

   public UIComponent findComponent(String id) {
      return null;
   }

   public Map getFacets() {
      return facets;
   }

   public int getFacetCount() {
      return 0;
   }

   public UIComponent getFacet(String name) {
      return null;
   }

   public Iterator getFacetsAndChildren() {
      return Collections.emptyList().iterator();
   }

   public void broadcast(FacesEvent event) throws AbortProcessingException {
   }

   public void decode(FacesContext faces) {
   }

   public void encodeBegin(FacesContext faces) throws IOException {
   }

   public void encodeChildren(FacesContext faces) throws IOException {
   }

   public void encodeEnd(FacesContext faces) throws IOException {
   }

   public void encodeAll(FacesContext faces) throws IOException {
      this.encodeBegin(faces);
   }

   protected void addFacesListener(FacesListener faces) {
   }

   protected FacesListener[] getFacesListeners(Class faces) {
      return null;
   }

   protected void removeFacesListener(FacesListener faces) {
   }

   public void queueEvent(FacesEvent event) {
   }

   public void processDecodes(FacesContext faces) {
   }

   public void processValidators(FacesContext faces) {
   }

   public void processUpdates(FacesContext faces) {
   }

   protected FacesContext getFacesContext() {
      return FacesContext.getCurrentInstance();
   }

   protected Renderer getRenderer(FacesContext faces) {
      return null;
   }

   public boolean isTransient() {
      return this.returnLocalTransient || super.isTransient();
   }

   public void setTransient(boolean tranzient) {
      this.returnLocalTransient = false;
      super.setTransient(tranzient);
   }
}
