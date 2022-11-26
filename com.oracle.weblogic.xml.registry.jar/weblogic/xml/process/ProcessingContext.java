package weblogic.xml.process;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import weblogic.utils.AssertionError;

public class ProcessingContext extends Node {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private boolean referenced = false;
   private final Map bound = new HashMap();

   public ProcessingContext(String tagName) throws XMLProcessingException {
      super(tagName);
   }

   public ProcessingContext(ProcessingContext parent, String tagName) throws XMLProcessingException {
      super(parent, tagName);
   }

   public ProcessingContext newTextNode() {
      ProcessingContext newTextCtx = this.newElementNode("#text");
      newTextCtx.referenced = true;
      return newTextCtx;
   }

   public ProcessingContext newElementNode(String tagName) {
      ProcessingContext newCtx = null;

      try {
         newCtx = new ProcessingContext(this, tagName);
      } catch (XMLProcessingException var4) {
         throw new AssertionError(var4);
      }

      newCtx.bound.putAll(this.getAllBoundObjects());
      return newCtx;
   }

   public void addBoundObject(Object obj, String varName) {
      this.bound.put(varName, obj);
   }

   public void addBoundObjects(Map bindings) {
      this.bound.putAll(bindings);
   }

   public Object getBoundObject(String varName) {
      return this.bound.get(varName);
   }

   public Map getAllBoundObjects() {
      return this.bound;
   }

   public void setReferenced(boolean val) {
      this.referenced = val;
   }

   public boolean referenced() {
      return this.referenced;
   }

   public String toString() {
      StringBuffer sbuff = new StringBuffer();
      sbuff.append("name = " + this.name);
      sbuff.append("\npath = " + this.path);
      sbuff.append("\nvalue = " + this.value);
      sbuff.append("\nbound objects = ");
      Iterator i = this.bound.keySet().iterator();

      while(i.hasNext()) {
         String varName = (String)i.next();
         Object obj = this.bound.get(varName);
         sbuff.append("\n\t" + varName + " = " + obj.getClass().getName() + "[" + obj.hashCode() + "]");
      }

      return sbuff.toString();
   }
}
