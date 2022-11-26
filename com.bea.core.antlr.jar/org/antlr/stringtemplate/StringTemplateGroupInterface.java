package org.antlr.stringtemplate;

import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.antlr.stringtemplate.language.InterfaceLexer;
import org.antlr.stringtemplate.language.InterfaceParser;

public class StringTemplateGroupInterface {
   protected String name;
   protected Map templates;
   protected StringTemplateGroupInterface superInterface;
   protected StringTemplateErrorListener listener;
   public static StringTemplateErrorListener DEFAULT_ERROR_LISTENER = new StringTemplateErrorListener() {
      public void error(String s, Throwable e) {
         System.err.println(s);
         if (e != null) {
            e.printStackTrace(System.err);
         }

      }

      public void warning(String s) {
         System.out.println(s);
      }
   };

   public StringTemplateGroupInterface(Reader r) {
      this(r, DEFAULT_ERROR_LISTENER, (StringTemplateGroupInterface)null);
   }

   public StringTemplateGroupInterface(Reader r, StringTemplateErrorListener errors) {
      this(r, errors, (StringTemplateGroupInterface)null);
   }

   public StringTemplateGroupInterface(Reader r, StringTemplateErrorListener errors, StringTemplateGroupInterface superInterface) {
      this.templates = new LinkedHashMap();
      this.superInterface = null;
      this.listener = DEFAULT_ERROR_LISTENER;
      this.listener = errors;
      this.setSuperInterface(superInterface);
      this.parseInterface(r);
   }

   public StringTemplateGroupInterface getSuperInterface() {
      return this.superInterface;
   }

   public void setSuperInterface(StringTemplateGroupInterface superInterface) {
      this.superInterface = superInterface;
   }

   protected void parseInterface(Reader r) {
      try {
         InterfaceLexer lexer = new InterfaceLexer(r);
         InterfaceParser parser = new InterfaceParser(lexer);
         parser.groupInterface(this);
      } catch (Exception var4) {
         String name = "<unknown>";
         if (this.getName() != null) {
            name = this.getName();
         }

         this.error("problem parsing group " + name + ": " + var4, var4);
      }

   }

   public void defineTemplate(String name, LinkedHashMap formalArgs, boolean optional) {
      TemplateDefinition d = new TemplateDefinition(name, formalArgs, optional);
      this.templates.put(d.name, d);
   }

   public List getMissingTemplates(StringTemplateGroup group) {
      List missing = new ArrayList();
      Iterator it = this.templates.keySet().iterator();

      while(it.hasNext()) {
         String name = (String)it.next();
         TemplateDefinition d = (TemplateDefinition)this.templates.get(name);
         if (!d.optional && !group.isDefined(d.name)) {
            missing.add(d.name);
         }
      }

      if (missing.size() == 0) {
         missing = null;
      }

      return missing;
   }

   public List getMismatchedTemplates(StringTemplateGroup group) {
      List mismatched = new ArrayList();
      Iterator it = this.templates.keySet().iterator();

      while(true) {
         TemplateDefinition d;
         do {
            if (!it.hasNext()) {
               if (mismatched.size() == 0) {
                  mismatched = null;
               }

               return mismatched;
            }

            String name = (String)it.next();
            d = (TemplateDefinition)this.templates.get(name);
         } while(!group.isDefined(d.name));

         StringTemplate defST = group.getTemplateDefinition(d.name);
         Map formalArgs = defST.getFormalArguments();
         boolean ack = false;
         if (d.formalArgs != null && formalArgs == null || d.formalArgs == null && formalArgs != null || d.formalArgs.size() != formalArgs.size()) {
            ack = true;
         }

         if (!ack) {
            Iterator it2 = formalArgs.keySet().iterator();

            while(it2.hasNext()) {
               String argName = (String)it2.next();
               if (d.formalArgs.get(argName) == null) {
                  ack = true;
                  break;
               }
            }
         }

         if (ack) {
            mismatched.add(this.getTemplateSignature(d));
         }
      }
   }

   public String getName() {
      return this.name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public void error(String msg) {
      this.error(msg, (Exception)null);
   }

   public void error(String msg, Exception e) {
      if (this.listener != null) {
         this.listener.error(msg, e);
      } else {
         System.err.println("StringTemplate: " + msg);
         if (e != null) {
            e.printStackTrace();
         }
      }

   }

   public String toString() {
      StringBuffer buf = new StringBuffer();
      buf.append("interface ");
      buf.append(this.getName());
      buf.append(";\n");
      Iterator it = this.templates.keySet().iterator();

      while(it.hasNext()) {
         String name = (String)it.next();
         TemplateDefinition d = (TemplateDefinition)this.templates.get(name);
         buf.append(this.getTemplateSignature(d));
         buf.append(";\n");
      }

      return buf.toString();
   }

   protected String getTemplateSignature(TemplateDefinition d) {
      StringBuffer buf = new StringBuffer();
      if (d.optional) {
         buf.append("optional ");
      }

      buf.append(d.name);
      if (d.formalArgs != null) {
         StringBuffer args = new StringBuffer();
         args.append('(');
         int i = 1;

         for(Iterator it = d.formalArgs.keySet().iterator(); it.hasNext(); ++i) {
            String name = (String)it.next();
            if (i > 1) {
               args.append(", ");
            }

            args.append(name);
         }

         args.append(')');
         buf.append(args);
      } else {
         buf.append("()");
      }

      return buf.toString();
   }

   static class TemplateDefinition {
      public String name;
      public LinkedHashMap formalArgs;
      public boolean optional = false;

      public TemplateDefinition(String name, LinkedHashMap formalArgs, boolean optional) {
         this.name = name;
         this.formalArgs = formalArgs;
         this.optional = optional;
      }
   }
}
