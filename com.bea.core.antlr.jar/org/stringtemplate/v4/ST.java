package org.stringtemplate.v4;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.antlr.runtime.Token;
import org.stringtemplate.v4.compiler.CompiledST;
import org.stringtemplate.v4.compiler.FormalArgument;
import org.stringtemplate.v4.debug.AddAttributeEvent;
import org.stringtemplate.v4.debug.ConstructionEvent;
import org.stringtemplate.v4.debug.EvalTemplateEvent;
import org.stringtemplate.v4.gui.STViz;
import org.stringtemplate.v4.misc.Aggregate;
import org.stringtemplate.v4.misc.ErrorBuffer;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.MultiMap;

public class ST {
   public static final String VERSION = "4.0.7-SNAPSHOT";
   public static final String UNKNOWN_NAME = "anonymous";
   public static final Object EMPTY_ATTR = new Object();
   public static final String IMPLICIT_ARG_NAME = "it";
   public CompiledST impl;
   protected Object[] locals;
   public STGroup groupThatCreatedThisInstance;
   public DebugState debugState;

   protected ST() {
      if (STGroup.trackCreationEvents) {
         if (this.debugState == null) {
            this.debugState = new DebugState();
         }

         this.debugState.newSTEvent = new ConstructionEvent();
      }

   }

   public ST(String template) {
      this(STGroup.defaultGroup, template);
   }

   public ST(String template, char delimiterStartChar, char delimiterStopChar) {
      this(new STGroup(delimiterStartChar, delimiterStopChar), template);
   }

   public ST(STGroup group, String template) {
      this();
      this.groupThatCreatedThisInstance = group;
      this.impl = this.groupThatCreatedThisInstance.compile(group.getFileName(), (String)null, (List)null, template, (Token)null);
      this.impl.hasFormalArgs = false;
      this.impl.name = "anonymous";
      this.impl.defineImplicitlyDefinedTemplates(this.groupThatCreatedThisInstance);
   }

   public ST(ST proto) {
      this.impl = proto.impl;
      if (proto.locals != null) {
         this.locals = new Object[proto.locals.length];
         System.arraycopy(proto.locals, 0, this.locals, 0, proto.locals.length);
      } else if (this.impl.formalArguments != null && !this.impl.formalArguments.isEmpty()) {
         this.locals = new Object[this.impl.formalArguments.size()];
      }

      this.groupThatCreatedThisInstance = proto.groupThatCreatedThisInstance;
   }

   public synchronized ST add(String name, Object value) {
      if (name == null) {
         throw new NullPointerException("null attribute name");
      } else if (name.indexOf(46) >= 0) {
         throw new IllegalArgumentException("cannot have '.' in attribute names");
      } else {
         if (STGroup.trackCreationEvents) {
            if (this.debugState == null) {
               this.debugState = new DebugState();
            }

            this.debugState.addAttrEvents.map(name, new AddAttributeEvent(name, value));
         }

         FormalArgument arg = null;
         if (this.impl.hasFormalArgs) {
            if (this.impl.formalArguments != null) {
               arg = (FormalArgument)this.impl.formalArguments.get(name);
            }

            if (arg == null) {
               throw new IllegalArgumentException("no such attribute: " + name);
            }
         } else {
            if (this.impl.formalArguments != null) {
               arg = (FormalArgument)this.impl.formalArguments.get(name);
            }

            if (arg == null) {
               arg = new FormalArgument(name);
               this.impl.addArg(arg);
               if (this.locals == null) {
                  this.locals = new Object[1];
               } else {
                  Object[] copy = new Object[this.impl.formalArguments.size()];
                  System.arraycopy(this.locals, 0, copy, 0, Math.min(this.locals.length, this.impl.formalArguments.size()));
                  this.locals = copy;
               }

               this.locals[arg.index] = EMPTY_ATTR;
            }
         }

         Object curvalue = this.locals[arg.index];
         if (curvalue == EMPTY_ATTR) {
            this.locals[arg.index] = value;
            return this;
         } else {
            AttributeList multi = convertToAttributeList(curvalue);
            this.locals[arg.index] = multi;
            if (value instanceof List) {
               multi.addAll((List)value);
            } else if (value != null && value.getClass().isArray()) {
               if (value instanceof Object[]) {
                  multi.addAll(Arrays.asList((Object[])((Object[])value)));
               } else {
                  multi.addAll(convertToAttributeList(value));
               }
            } else {
               multi.add(value);
            }

            return this;
         }
      }
   }

   public synchronized ST addAggr(String aggrSpec, Object... values) {
      int dot = aggrSpec.indexOf(".{");
      if (values != null && values.length != 0) {
         int finalCurly = aggrSpec.indexOf(125);
         if (dot >= 0 && finalCurly >= 0) {
            String aggrName = aggrSpec.substring(0, dot);
            String propString = aggrSpec.substring(dot + 2, aggrSpec.length() - 1);
            propString = propString.trim();
            String[] propNames = propString.split("\\ *,\\ *");
            if (propNames != null && propNames.length != 0) {
               if (values.length != propNames.length) {
                  throw new IllegalArgumentException("number of properties and values mismatch for aggregate attribute format: " + aggrSpec);
               } else {
                  int i = 0;
                  Aggregate aggr = new Aggregate();
                  String[] var10 = propNames;
                  int var11 = propNames.length;

                  for(int var12 = 0; var12 < var11; ++var12) {
                     String p = var10[var12];
                     Object v = values[i++];
                     aggr.properties.put(p, v);
                  }

                  this.add(aggrName, aggr);
                  return this;
               }
            } else {
               throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
            }
         } else {
            throw new IllegalArgumentException("invalid aggregate attribute format: " + aggrSpec);
         }
      } else {
         throw new IllegalArgumentException("missing values for aggregate attribute format: " + aggrSpec);
      }
   }

   public void remove(String name) {
      if (this.impl.formalArguments == null) {
         if (this.impl.hasFormalArgs) {
            throw new IllegalArgumentException("no such attribute: " + name);
         }
      } else {
         FormalArgument arg = (FormalArgument)this.impl.formalArguments.get(name);
         if (arg == null) {
            throw new IllegalArgumentException("no such attribute: " + name);
         } else {
            this.locals[arg.index] = EMPTY_ATTR;
         }
      }
   }

   protected void rawSetAttribute(String name, Object value) {
      if (this.impl.formalArguments == null) {
         throw new IllegalArgumentException("no such attribute: " + name);
      } else {
         FormalArgument arg = (FormalArgument)this.impl.formalArguments.get(name);
         if (arg == null) {
            throw new IllegalArgumentException("no such attribute: " + name);
         } else {
            this.locals[arg.index] = value;
         }
      }
   }

   public Object getAttribute(String name) {
      FormalArgument localArg = null;
      if (this.impl.formalArguments != null) {
         localArg = (FormalArgument)this.impl.formalArguments.get(name);
      }

      if (localArg != null) {
         Object o = this.locals[localArg.index];
         if (o == EMPTY_ATTR) {
            o = null;
         }

         return o;
      } else {
         return null;
      }
   }

   public Map getAttributes() {
      if (this.impl.formalArguments == null) {
         return null;
      } else {
         Map attributes = new HashMap();

         FormalArgument a;
         Object o;
         for(Iterator var2 = this.impl.formalArguments.values().iterator(); var2.hasNext(); attributes.put(a.name, o)) {
            a = (FormalArgument)var2.next();
            o = this.locals[a.index];
            if (o == EMPTY_ATTR) {
               o = null;
            }
         }

         return attributes;
      }
   }

   protected static AttributeList convertToAttributeList(Object curvalue) {
      AttributeList multi;
      if (curvalue == null) {
         multi = new AttributeList();
         multi.add(curvalue);
      } else if (curvalue instanceof AttributeList) {
         multi = (AttributeList)curvalue;
      } else if (curvalue instanceof List) {
         List listAttr = (List)curvalue;
         multi = new AttributeList(listAttr.size());
         multi.addAll(listAttr);
      } else if (curvalue instanceof Object[]) {
         Object[] a = (Object[])((Object[])curvalue);
         multi = new AttributeList(a.length);
         multi.addAll(Arrays.asList(a));
      } else if (curvalue.getClass().isArray()) {
         int length = Array.getLength(curvalue);
         multi = new AttributeList(length);

         for(int i = 0; i < length; ++i) {
            multi.add(Array.get(curvalue, i));
         }
      } else {
         multi = new AttributeList();
         multi.add(curvalue);
      }

      return multi;
   }

   public String getName() {
      return this.impl.name;
   }

   public boolean isAnonSubtemplate() {
      return this.impl.isAnonSubtemplate;
   }

   public int write(STWriter out) throws IOException {
      Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, this.impl.nativeGroup.errMgr, false);
      InstanceScope scope = new InstanceScope((InstanceScope)null, this);
      return interp.exec(out, scope);
   }

   public int write(STWriter out, Locale locale) {
      Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, this.impl.nativeGroup.errMgr, false);
      InstanceScope scope = new InstanceScope((InstanceScope)null, this);
      return interp.exec(out, scope);
   }

   public int write(STWriter out, STErrorListener listener) {
      Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, new ErrorManager(listener), false);
      InstanceScope scope = new InstanceScope((InstanceScope)null, this);
      return interp.exec(out, scope);
   }

   public int write(STWriter out, Locale locale, STErrorListener listener) {
      Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, new ErrorManager(listener), false);
      InstanceScope scope = new InstanceScope((InstanceScope)null, this);
      return interp.exec(out, scope);
   }

   public int write(File outputFile, STErrorListener listener) throws IOException {
      return this.write(outputFile, listener, "UTF-8", Locale.getDefault(), -1);
   }

   public int write(File outputFile, STErrorListener listener, String encoding) throws IOException {
      return this.write(outputFile, listener, encoding, Locale.getDefault(), -1);
   }

   public int write(File outputFile, STErrorListener listener, String encoding, int lineWidth) throws IOException {
      return this.write(outputFile, listener, encoding, Locale.getDefault(), lineWidth);
   }

   public int write(File outputFile, STErrorListener listener, String encoding, Locale locale, int lineWidth) throws IOException {
      Writer bw = null;

      int var11;
      try {
         FileOutputStream fos = new FileOutputStream(outputFile);
         OutputStreamWriter osw = new OutputStreamWriter(fos, encoding);
         bw = new BufferedWriter(osw);
         AutoIndentWriter w = new AutoIndentWriter(bw);
         w.setLineWidth(lineWidth);
         int n = this.write((STWriter)w, (Locale)locale, (STErrorListener)listener);
         bw.close();
         bw = null;
         var11 = n;
      } finally {
         if (bw != null) {
            bw.close();
         }

      }

      return var11;
   }

   public String render() {
      return this.render(Locale.getDefault());
   }

   public String render(int lineWidth) {
      return this.render(Locale.getDefault(), lineWidth);
   }

   public String render(Locale locale) {
      return this.render(locale, -1);
   }

   public String render(Locale locale, int lineWidth) {
      StringWriter out = new StringWriter();
      STWriter wr = new AutoIndentWriter(out);
      wr.setLineWidth(lineWidth);
      this.write((STWriter)wr, (Locale)locale);
      return out.toString();
   }

   public STViz inspect() {
      return this.inspect(Locale.getDefault());
   }

   public STViz inspect(int lineWidth) {
      return this.inspect(this.impl.nativeGroup.errMgr, Locale.getDefault(), lineWidth);
   }

   public STViz inspect(Locale locale) {
      return this.inspect(this.impl.nativeGroup.errMgr, locale, -1);
   }

   public STViz inspect(ErrorManager errMgr, Locale locale, int lineWidth) {
      ErrorBuffer errors = new ErrorBuffer();
      this.impl.nativeGroup.setListener(errors);
      StringWriter out = new StringWriter();
      STWriter wr = new AutoIndentWriter(out);
      wr.setLineWidth(lineWidth);
      Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, true);
      InstanceScope scope = new InstanceScope((InstanceScope)null, this);
      interp.exec(wr, scope);
      List events = interp.getEvents();
      EvalTemplateEvent overallTemplateEval = (EvalTemplateEvent)events.get(events.size() - 1);
      STViz viz = new STViz(errMgr, overallTemplateEval, out.toString(), interp, interp.getExecutionTrace(), errors.errors);
      viz.open();
      return viz;
   }

   public List getEvents() {
      return this.getEvents(Locale.getDefault());
   }

   public List getEvents(int lineWidth) {
      return this.getEvents(Locale.getDefault(), lineWidth);
   }

   public List getEvents(Locale locale) {
      return this.getEvents(locale, -1);
   }

   public List getEvents(Locale locale, int lineWidth) {
      StringWriter out = new StringWriter();
      STWriter wr = new AutoIndentWriter(out);
      wr.setLineWidth(lineWidth);
      Interpreter interp = new Interpreter(this.groupThatCreatedThisInstance, locale, true);
      InstanceScope scope = new InstanceScope((InstanceScope)null, this);
      interp.exec(wr, scope);
      return interp.getEvents();
   }

   public String toString() {
      if (this.impl == null) {
         return "bad-template()";
      } else {
         String name = this.impl.name + "()";
         if (this.impl.isRegion) {
            name = "@" + STGroup.getUnMangledTemplateName(name);
         }

         return name;
      }
   }

   public static String format(String template, Object... attributes) {
      return format(-1, template, attributes);
   }

   public static String format(int lineWidth, String template, Object... attributes) {
      template = template.replaceAll("%([0-9]+)", "arg$1");
      ST st = new ST(template);
      int i = 1;
      Object[] var5 = attributes;
      int var6 = attributes.length;

      for(int var7 = 0; var7 < var6; ++var7) {
         Object a = var5[var7];
         st.add("arg" + i, a);
         ++i;
      }

      return st.render(lineWidth);
   }

   public static final class AttributeList extends ArrayList {
      public AttributeList(int size) {
         super(size);
      }

      public AttributeList() {
      }
   }

   public static class DebugState {
      public ConstructionEvent newSTEvent;
      public MultiMap addAttrEvents = new MultiMap();
   }

   public static enum RegionType {
      IMPLICIT,
      EMBEDDED,
      EXPLICIT;
   }
}
