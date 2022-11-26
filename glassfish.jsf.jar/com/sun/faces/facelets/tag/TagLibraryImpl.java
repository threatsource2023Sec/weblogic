package com.sun.faces.facelets.tag;

import com.sun.faces.util.Util;
import java.lang.reflect.Method;
import java.net.URL;

public class TagLibraryImpl extends AbstractTagLibrary {
   public TagLibraryImpl(String namespace) {
      super(namespace);
   }

   public void putConverter(String name, String id) {
      Util.notNull("name", name);
      Util.notNull("id", id);
      this.addConverter(name, id);
   }

   public void putConverter(String name, String id, Class handlerClass) {
      Util.notNull("name", name);
      Util.notNull("id", id);
      Util.notNull("handlerClass", handlerClass);
      this.addConverter(name, id, handlerClass);
   }

   public void putValidator(String name, String id) {
      Util.notNull("name", name);
      Util.notNull("id", id);
      this.addValidator(name, id);
   }

   public void putValidator(String name, String id, Class handlerClass) {
      Util.notNull("name", name);
      Util.notNull("id", id);
      Util.notNull("handlerClass", handlerClass);
      this.addValidator(name, id, handlerClass);
   }

   public void putBehavior(String name, String id) {
      Util.notNull("name", name);
      Util.notNull("id", id);
      this.addBehavior(name, id);
   }

   public void putBehavior(String name, String id, Class handlerClass) {
      Util.notNull("name", name);
      Util.notNull("id", id);
      Util.notNull("handlerClass", handlerClass);
      this.addBehavior(name, id, handlerClass);
   }

   public void putTagHandler(String name, Class type) {
      Util.notNull("name", name);
      Util.notNull("type", type);
      this.addTagHandler(name, type);
   }

   public void putComponent(String name, String componentType, String rendererType) {
      Util.notNull("name", name);
      Util.notNull("componentType", componentType);
      this.addComponent(name, componentType, rendererType);
   }

   public void putComponent(String name, String componentType, String rendererType, Class handlerClass) {
      Util.notNull("name", name);
      Util.notNull("handlerClass", handlerClass);
      this.addComponent(name, componentType, rendererType, handlerClass);
   }

   public void putUserTag(String name, URL source) {
      Util.notNull("name", name);
      Util.notNull("source", source);
      this.addUserTag(name, source);
   }

   public void putCompositeComponentTag(String name, String resourceId) {
      Util.notNull("name", name);
      Util.notNull("resourceId", resourceId);
      this.addCompositeComponentTag(name, resourceId);
   }

   public void putFunction(String name, Method method) {
      Util.notNull("name", name);
      Util.notNull("method", method);
      this.addFunction(name, method);
   }
}
