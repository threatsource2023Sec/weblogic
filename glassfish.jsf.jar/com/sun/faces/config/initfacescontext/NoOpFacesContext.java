package com.sun.faces.config.initfacescontext;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKit;

public abstract class NoOpFacesContext extends FacesContext {
   public Iterator getClientIdsWithMessages() {
      List list = Collections.emptyList();
      return list.iterator();
   }

   public FacesMessage.Severity getMaximumSeverity() {
      return FacesMessage.SEVERITY_INFO;
   }

   public Iterator getMessages() {
      List list = Collections.emptyList();
      return list.iterator();
   }

   public Iterator getMessages(String clientId) {
      return this.getMessages();
   }

   public List getMessageList() {
      return Collections.emptyList();
   }

   public List getMessageList(String clientId) {
      return Collections.emptyList();
   }

   public RenderKit getRenderKit() {
      return null;
   }

   public boolean getRenderResponse() {
      return true;
   }

   public boolean getResponseComplete() {
      return true;
   }

   public boolean isValidationFailed() {
      return false;
   }

   public ResponseStream getResponseStream() {
      return null;
   }

   public void setResponseStream(ResponseStream responseStream) {
   }

   public ResponseWriter getResponseWriter() {
      return null;
   }

   public void setResponseWriter(ResponseWriter responseWriter) {
   }

   public void setViewRoot(UIViewRoot root) {
   }

   public void addMessage(String clientId, FacesMessage message) {
   }

   public void renderResponse() {
   }

   public void responseComplete() {
   }

   public void validationFailed() {
   }
}
