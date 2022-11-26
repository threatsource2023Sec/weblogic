package weblogic.xml.process;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import weblogic.utils.AssertionError;

public final class GAction {
   private static final boolean debug = false;
   private static final boolean verbose = false;
   private String elementName;
   private String elementContext;
   private List params = new ArrayList();
   private LinkedList codeFragments = new LinkedList();

   public void setElementName(String val) {
      this.elementName = val;
   }

   public String getElementName() {
      return this.elementName;
   }

   public void setElementContext(String val) {
      this.elementContext = val;
   }

   public String getElementContext() {
      return this.elementContext;
   }

   public void setParams(List p) {
      this.params = p;
   }

   public List getParams() {
      return this.params;
   }

   public Class getParamClass(String name) {
      Iterator i = this.params.iterator();

      Param p;
      do {
         if (!i.hasNext()) {
            return null;
         }

         p = (Param)i.next();
      } while(!p.getName().equals(name));

      return p.getClazz();
   }

   public String getPath() {
      String path = null;
      if (this.elementContext != null && this.elementContext.length() != 0) {
         path = "." + this.elementContext + "." + this.elementName + ".";
      } else {
         path = "." + this.elementName + ".";
      }

      return path;
   }

   public void addCodeFragment(Object val) {
      this.codeFragments.add(val);
   }

   public LinkedList getCodeFragments() {
      return this.codeFragments;
   }

   public String getJavaCode() {
      if (this.codeFragments.size() == 0) {
         return null;
      } else {
         StringBuffer sbuf = new StringBuffer();
         Iterator i = this.codeFragments.iterator();

         while(i.hasNext()) {
            try {
               String frag = (String)i.next();
               sbuf.append(frag);
            } catch (ClassCastException var4) {
               throw new AssertionError();
            }
         }

         return sbuf.toString();
      }
   }

   public boolean delayedWrite() {
      Iterator i = this.codeFragments.iterator();
      boolean haveWriteXmlCall = false;

      while(i.hasNext()) {
         Object frag = i.next();
         if (frag instanceof WriteXmlFunctionRef) {
            haveWriteXmlCall = true;
         } else if (haveWriteXmlCall && frag instanceof SetAttrValFunctionRef) {
            return true;
         }
      }

      return false;
   }

   public static class Param {
      private String name;
      private Class clazz;

      public Param(String paramName, Class paramClass) {
         this.name = paramName;
         this.clazz = paramClass;
      }

      public String getName() {
         return this.name;
      }

      public Class getClazz() {
         return this.clazz;
      }
   }
}
