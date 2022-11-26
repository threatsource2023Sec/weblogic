package org.stringtemplate.v4;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import org.stringtemplate.v4.compiler.BytecodeDisassembler;
import org.stringtemplate.v4.compiler.CompiledST;
import org.stringtemplate.v4.compiler.Compiler;
import org.stringtemplate.v4.compiler.FormalArgument;
import org.stringtemplate.v4.debug.EvalExprEvent;
import org.stringtemplate.v4.debug.EvalTemplateEvent;
import org.stringtemplate.v4.debug.IndentEvent;
import org.stringtemplate.v4.debug.InterpEvent;
import org.stringtemplate.v4.misc.ArrayIterator;
import org.stringtemplate.v4.misc.ErrorManager;
import org.stringtemplate.v4.misc.ErrorType;
import org.stringtemplate.v4.misc.Interval;
import org.stringtemplate.v4.misc.Misc;
import org.stringtemplate.v4.misc.STNoSuchAttributeException;
import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public class Interpreter {
   public static final int DEFAULT_OPERAND_STACK_SIZE = 100;
   public static final Set predefinedAnonSubtemplateAttributes = new HashSet() {
      {
         this.add("i");
         this.add("i0");
      }
   };
   Object[] operands;
   int sp;
   int nwline;
   STGroup group;
   Locale locale;
   ErrorManager errMgr;
   public static boolean trace = false;
   protected List executeTrace;
   public boolean debug;
   protected List events;

   public Interpreter(STGroup group, boolean debug) {
      this(group, Locale.getDefault(), group.errMgr, debug);
   }

   public Interpreter(STGroup group, Locale locale, boolean debug) {
      this(group, locale, group.errMgr, debug);
   }

   public Interpreter(STGroup group, ErrorManager errMgr, boolean debug) {
      this(group, Locale.getDefault(), errMgr, debug);
   }

   public Interpreter(STGroup group, Locale locale, ErrorManager errMgr, boolean debug) {
      this.operands = new Object[100];
      this.sp = -1;
      this.nwline = 0;
      this.debug = false;
      this.group = group;
      this.locale = locale;
      this.errMgr = errMgr;
      this.debug = debug;
      if (debug) {
         this.events = new ArrayList();
         this.executeTrace = new ArrayList();
      }

   }

   public int exec(STWriter out, InstanceScope scope) {
      ST self = scope.st;
      if (trace) {
         System.out.println("exec(" + self.getName() + ")");
      }

      try {
         this.setDefaultArguments(out, scope);
         return this._exec(out, scope);
      } catch (Exception var7) {
         StringWriter sw = new StringWriter();
         PrintWriter pw = new PrintWriter(sw);
         var7.printStackTrace(pw);
         pw.flush();
         this.errMgr.runTimeError(this, scope, ErrorType.INTERNAL_ERROR, "internal error: " + sw.toString());
         return 0;
      }
   }

   protected int _exec(STWriter out, InstanceScope scope) {
      ST self = scope.st;
      int start = out.index();
      int prevOpcode = 0;
      int n = 0;
      byte[] code = self.impl.instrs;

      int opcode;
      for(int ip = 0; ip < self.impl.codeSize; prevOpcode = (short)opcode) {
         if (trace || this.debug) {
            this.trace(scope, ip);
         }

         opcode = (short)code[ip];
         scope.ip = ip++;
         int nargs;
         int nameIndex;
         String name;
         Object left;
         Object right;
         ST st;
         Object[] options;
         ArgumentsMap attrs;
         int n1;
         int nmaps;
         int strIndex;
         Object o;
         switch (opcode) {
            case 1:
               this.load_str(self, ip);
               ip += 2;
               break;
            case 2:
               nameIndex = getShort(code, ip);
               ip += 2;
               name = self.impl.strings[nameIndex];

               try {
                  o = this.getAttribute(scope, name);
                  if (o == ST.EMPTY_ATTR) {
                     o = null;
                  }
               } catch (STNoSuchAttributeException var31) {
                  this.errMgr.runTimeError(this, scope, ErrorType.NO_SUCH_ATTRIBUTE, name);
                  o = null;
               }

               this.operands[++this.sp] = o;
               break;
            case 3:
               int valueIndex = getShort(code, ip);
               ip += 2;
               o = self.locals[valueIndex];
               if (o == ST.EMPTY_ATTR) {
                  o = null;
               }

               this.operands[++this.sp] = o;
               break;
            case 4:
               nameIndex = getShort(code, ip);
               ip += 2;
               o = this.operands[this.sp--];
               name = self.impl.strings[nameIndex];
               this.operands[++this.sp] = this.getObjectProperty(out, scope, o, name);
               break;
            case 5:
               Object propName = this.operands[this.sp--];
               o = this.operands[this.sp];
               this.operands[this.sp] = this.getObjectProperty(out, scope, o, propName);
               break;
            case 6:
               int optionIndex = getShort(code, ip);
               ip += 2;
               o = this.operands[this.sp--];
               options = (Object[])((Object[])this.operands[this.sp]);
               options[optionIndex] = o;
               break;
            case 7:
               nameIndex = getShort(code, ip);
               name = self.impl.strings[nameIndex];
               ip += 2;
               o = this.operands[this.sp--];
               attrs = (ArgumentsMap)this.operands[this.sp];
               attrs.put(name, o);
               break;
            case 8:
               nameIndex = getShort(code, ip);
               ip += 2;
               name = self.impl.strings[nameIndex];
               nargs = getShort(code, ip);
               ip += 2;
               st = self.groupThatCreatedThisInstance.getEmbeddedInstanceOf(this, scope, name);
               this.storeArgs(scope, nargs, st);
               this.sp -= nargs;
               this.operands[++this.sp] = st;
               break;
            case 9:
               nargs = getShort(code, ip);
               ip += 2;
               name = (String)this.operands[this.sp - nargs];
               st = self.groupThatCreatedThisInstance.getEmbeddedInstanceOf(this, scope, name);
               this.storeArgs(scope, nargs, st);
               this.sp -= nargs;
               --this.sp;
               this.operands[++this.sp] = st;
               break;
            case 10:
               nameIndex = getShort(code, ip);
               ip += 2;
               name = self.impl.strings[nameIndex];
               attrs = (ArgumentsMap)this.operands[this.sp--];
               st = self.groupThatCreatedThisInstance.getEmbeddedInstanceOf(this, scope, name);
               this.storeArgs(scope, attrs, st);
               this.operands[++this.sp] = st;
               break;
            case 11:
               nameIndex = getShort(code, ip);
               ip += 2;
               name = self.impl.strings[nameIndex];
               nargs = getShort(code, ip);
               ip += 2;
               this.super_new(scope, name, nargs);
               break;
            case 12:
               nameIndex = getShort(code, ip);
               ip += 2;
               name = self.impl.strings[nameIndex];
               attrs = (ArgumentsMap)this.operands[this.sp--];
               this.super_new(scope, name, attrs);
               break;
            case 13:
               o = this.operands[this.sp--];
               n1 = this.writeObjectNoOptions(out, scope, o);
               n += n1;
               this.nwline += n1;
               break;
            case 14:
               options = (Object[])((Object[])this.operands[this.sp--]);
               o = this.operands[this.sp--];
               int n2 = this.writeObjectWithOptions(out, scope, o, options);
               n += n2;
               this.nwline += n2;
               break;
            case 15:
               st = (ST)this.operands[this.sp--];
               o = this.operands[this.sp--];
               this.map(scope, o, st);
               break;
            case 16:
               nmaps = getShort(code, ip);
               ip += 2;
               List templates = new ArrayList();

               for(int i = nmaps - 1; i >= 0; --i) {
                  templates.add((ST)this.operands[this.sp - i]);
               }

               this.sp -= nmaps;
               o = this.operands[this.sp--];
               if (o != null) {
                  this.rot_map(scope, o, templates);
               }
               break;
            case 17:
               st = (ST)this.operands[this.sp--];
               nmaps = getShort(code, ip);
               ip += 2;
               List exprs = new ObjectList();

               for(int i = nmaps - 1; i >= 0; --i) {
                  exprs.add(this.operands[this.sp - i]);
               }

               this.sp -= nmaps;
               this.operands[++this.sp] = this.zip_map(scope, exprs, st);
               break;
            case 18:
               ip = getShort(code, ip);
               break;
            case 19:
               int addr = getShort(code, ip);
               ip += 2;
               o = this.operands[this.sp--];
               if (!this.testAttributeTrue(o)) {
                  ip = addr;
               }
               break;
            case 20:
               this.operands[++this.sp] = new Object[Compiler.NUM_OPTIONS];
               break;
            case 21:
               this.operands[++this.sp] = new ArgumentsMap();
               break;
            case 22:
               nameIndex = getShort(code, ip);
               ip += 2;
               name = self.impl.strings[nameIndex];
               attrs = (ArgumentsMap)this.operands[this.sp];
               this.passthru(scope, name, attrs);
               break;
            case 23:
            default:
               this.errMgr.internalError(self, "invalid bytecode @ " + (ip - 1) + ": " + opcode, (Throwable)null);
               self.impl.dump();
               break;
            case 24:
               this.operands[++this.sp] = new ObjectList();
               break;
            case 25:
               o = this.operands[this.sp--];
               List list = (ObjectList)this.operands[this.sp];
               this.addToList(scope, list, o);
               break;
            case 26:
               this.operands[this.sp] = this.toString(out, scope, this.operands[this.sp]);
               break;
            case 27:
               this.operands[this.sp] = this.first(scope, this.operands[this.sp]);
               break;
            case 28:
               this.operands[this.sp] = this.last(scope, this.operands[this.sp]);
               break;
            case 29:
               this.operands[this.sp] = this.rest(scope, this.operands[this.sp]);
               break;
            case 30:
               this.operands[this.sp] = this.trunc(scope, this.operands[this.sp]);
               break;
            case 31:
               this.operands[this.sp] = this.strip(scope, this.operands[this.sp]);
               break;
            case 32:
               o = this.operands[this.sp--];
               if (o.getClass() == String.class) {
                  this.operands[++this.sp] = ((String)o).trim();
               } else {
                  this.errMgr.runTimeError(this, scope, ErrorType.EXPECTING_STRING, (Object)"trim", o.getClass().getName());
                  this.operands[++this.sp] = o;
               }
               break;
            case 33:
               this.operands[this.sp] = this.length(this.operands[this.sp]);
               break;
            case 34:
               o = this.operands[this.sp--];
               if (o.getClass() == String.class) {
                  this.operands[++this.sp] = ((String)o).length();
               } else {
                  this.errMgr.runTimeError(this, scope, ErrorType.EXPECTING_STRING, (Object)"strlen", o.getClass().getName());
                  this.operands[++this.sp] = 0;
               }
               break;
            case 35:
               this.operands[this.sp] = this.reverse(scope, this.operands[this.sp]);
               break;
            case 36:
               this.operands[this.sp] = !this.testAttributeTrue(this.operands[this.sp]);
               break;
            case 37:
               right = this.operands[this.sp--];
               left = this.operands[this.sp--];
               this.operands[++this.sp] = this.testAttributeTrue(left) || this.testAttributeTrue(right);
               break;
            case 38:
               right = this.operands[this.sp--];
               left = this.operands[this.sp--];
               this.operands[++this.sp] = this.testAttributeTrue(left) && this.testAttributeTrue(right);
               break;
            case 39:
               strIndex = getShort(code, ip);
               ip += 2;
               this.indent(out, scope, strIndex);
               break;
            case 40:
               out.popIndentation();
               break;
            case 41:
               try {
                  if (prevOpcode == 41 || prevOpcode == 39 || this.nwline > 0) {
                     out.write(Misc.newline);
                  }

                  this.nwline = 0;
               } catch (IOException var32) {
                  this.errMgr.IOError(self, ErrorType.WRITE_IO_ERROR, var32);
               }
            case 42:
               break;
            case 43:
               --this.sp;
               break;
            case 44:
               this.operands[++this.sp] = null;
               break;
            case 45:
               this.operands[++this.sp] = true;
               break;
            case 46:
               this.operands[++this.sp] = false;
               break;
            case 47:
               strIndex = getShort(code, ip);
               ip += 2;
               Object o = self.impl.strings[strIndex];
               n1 = this.writeObjectNoOptions(out, scope, o);
               n += n1;
               this.nwline += n1;
         }
      }

      if (this.debug) {
         opcode = out.index() - 1;
         EvalTemplateEvent e = new EvalTemplateEvent(scope, start, opcode);
         this.trackDebugEvent(scope, e);
      }

      return n;
   }

   void load_str(ST self, int ip) {
      int strIndex = getShort(self.impl.instrs, ip);
      ip += 2;
      this.operands[++this.sp] = self.impl.strings[strIndex];
   }

   void super_new(InstanceScope scope, String name, int nargs) {
      ST self = scope.st;
      ST st = null;
      CompiledST imported = self.impl.nativeGroup.lookupImportedTemplate(name);
      if (imported == null) {
         this.errMgr.runTimeError(this, scope, ErrorType.NO_IMPORTED_TEMPLATE, name);
         st = self.groupThatCreatedThisInstance.createStringTemplateInternally(new CompiledST());
      } else {
         st = imported.nativeGroup.getEmbeddedInstanceOf(this, scope, name);
         st.groupThatCreatedThisInstance = this.group;
      }

      this.storeArgs(scope, nargs, st);
      this.sp -= nargs;
      this.operands[++this.sp] = st;
   }

   void super_new(InstanceScope scope, String name, Map attrs) {
      ST self = scope.st;
      ST st = null;
      CompiledST imported = self.impl.nativeGroup.lookupImportedTemplate(name);
      if (imported == null) {
         this.errMgr.runTimeError(this, scope, ErrorType.NO_IMPORTED_TEMPLATE, name);
         st = self.groupThatCreatedThisInstance.createStringTemplateInternally(new CompiledST());
      } else {
         st = imported.nativeGroup.createStringTemplateInternally(imported);
         st.groupThatCreatedThisInstance = this.group;
      }

      this.storeArgs(scope, attrs, st);
      this.operands[++this.sp] = st;
   }

   void passthru(InstanceScope scope, String templateName, Map attrs) {
      CompiledST c = this.group.lookupTemplate(templateName);
      if (c != null) {
         if (c.formalArguments != null) {
            Iterator var5 = c.formalArguments.values().iterator();

            while(true) {
               FormalArgument arg;
               do {
                  if (!var5.hasNext()) {
                     return;
                  }

                  arg = (FormalArgument)var5.next();
               } while(attrs.containsKey(arg.name));

               try {
                  Object o = this.getAttribute(scope, arg.name);
                  if (o == ST.EMPTY_ATTR && arg.defaultValueToken == null) {
                     attrs.put(arg.name, (Object)null);
                  } else if (o != ST.EMPTY_ATTR) {
                     attrs.put(arg.name, o);
                  }
               } catch (STNoSuchAttributeException var8) {
                  if (arg.defaultValueToken == null) {
                     this.errMgr.runTimeError(this, scope, ErrorType.NO_SUCH_ATTRIBUTE_PASS_THROUGH, arg.name);
                     attrs.put(arg.name, (Object)null);
                  }
               }
            }
         }
      }
   }

   void storeArgs(InstanceScope scope, Map attrs, ST st) {
      if (st.impl.hasFormalArgs) {
         boolean argumentCountMismatch = false;
         Map formalArguments = st.impl.formalArguments;
         if (formalArguments == null) {
            formalArguments = Collections.emptyMap();
         }

         Iterator var6 = formalArguments.entrySet().iterator();

         label88: {
            Map.Entry formalArgument;
            do {
               do {
                  do {
                     if (!var6.hasNext()) {
                        break label88;
                     }

                     formalArgument = (Map.Entry)var6.next();
                  } while(((FormalArgument)formalArgument.getValue()).defaultValueToken != null);
               } while(((FormalArgument)formalArgument.getValue()).defaultValue != null);
            } while(attrs != null && attrs.containsKey(formalArgument.getKey()));

            argumentCountMismatch = true;
         }

         if (attrs != null && attrs.size() > formalArguments.size()) {
            argumentCountMismatch = true;
         }

         if (argumentCountMismatch) {
            int nargs = attrs != null ? attrs.size() : 0;
            int nformalArgs = formalArguments.size();
            this.errMgr.runTimeError(this, scope, ErrorType.ARGUMENT_COUNT_MISMATCH, nargs, st.impl.name, nformalArgs);
         }
      }

      if (attrs != null) {
         Iterator var9 = attrs.entrySet().iterator();

         while(true) {
            while(true) {
               while(var9.hasNext()) {
                  Map.Entry argument = (Map.Entry)var9.next();
                  if (!st.impl.hasFormalArgs) {
                     if (st.impl.formalArguments != null && st.impl.formalArguments.containsKey(argument.getKey())) {
                        st.rawSetAttribute((String)argument.getKey(), argument.getValue());
                     } else {
                        try {
                           st.impl = st.impl.clone();
                           st.add((String)argument.getKey(), argument.getValue());
                        } catch (CloneNotSupportedException var8) {
                           this.errMgr.runTimeError(this, scope, ErrorType.NO_SUCH_ATTRIBUTE, argument.getKey());
                        }
                     }
                  } else if (st.impl.formalArguments != null && st.impl.formalArguments.containsKey(argument.getKey())) {
                     st.rawSetAttribute((String)argument.getKey(), argument.getValue());
                  } else {
                     this.errMgr.runTimeError(this, scope, ErrorType.NO_SUCH_ATTRIBUTE, argument.getKey());
                  }
               }

               return;
            }
         }
      }
   }

   void storeArgs(InstanceScope scope, int nargs, ST st) {
      if (nargs > 0 && !st.impl.hasFormalArgs && st.impl.formalArguments == null) {
         st.add("it", (Object)null);
      }

      int nformalArgs = 0;
      if (st.impl.formalArguments != null) {
         nformalArgs = st.impl.formalArguments.size();
      }

      int firstArg = this.sp - (nargs - 1);
      int numToStore = Math.min(nargs, nformalArgs);
      if (st.impl.isAnonSubtemplate) {
         nformalArgs -= predefinedAnonSubtemplateAttributes.size();
      }

      if (nargs < nformalArgs - st.impl.numberOfArgsWithDefaultValues || nargs > nformalArgs) {
         this.errMgr.runTimeError(this, scope, ErrorType.ARGUMENT_COUNT_MISMATCH, nargs, st.impl.name, nformalArgs);
      }

      if (st.impl.formalArguments != null) {
         Iterator argNames = st.impl.formalArguments.keySet().iterator();

         for(int i = 0; i < numToStore; ++i) {
            Object o = this.operands[firstArg + i];
            String argName = (String)argNames.next();
            st.rawSetAttribute(argName, o);
         }

      }
   }

   protected void indent(STWriter out, InstanceScope scope, int strIndex) {
      String indent = scope.st.impl.strings[strIndex];
      if (this.debug) {
         int start = out.index();
         EvalExprEvent e = new IndentEvent(scope, start, start + indent.length() - 1, this.getExprStartChar(scope), this.getExprStopChar(scope));
         this.trackDebugEvent(scope, e);
      }

      out.pushIndentation(indent);
   }

   protected int writeObjectNoOptions(STWriter out, InstanceScope scope, Object o) {
      int start = out.index();
      int n = this.writeObject(out, scope, o, (String[])null);
      if (this.debug) {
         EvalExprEvent e = new EvalExprEvent(scope, start, out.index() - 1, this.getExprStartChar(scope), this.getExprStopChar(scope));
         this.trackDebugEvent(scope, e);
      }

      return n;
   }

   protected int writeObjectWithOptions(STWriter out, InstanceScope scope, Object o, Object[] options) {
      int start = out.index();
      String[] optionStrings = null;
      int n;
      if (options != null) {
         optionStrings = new String[options.length];

         for(n = 0; n < Compiler.NUM_OPTIONS; ++n) {
            optionStrings[n] = this.toString(out, scope, options[n]);
         }
      }

      if (options != null && options[Interpreter.Option.ANCHOR.ordinal()] != null) {
         out.pushAnchorPoint();
      }

      n = this.writeObject(out, scope, o, optionStrings);
      if (options != null && options[Interpreter.Option.ANCHOR.ordinal()] != null) {
         out.popAnchorPoint();
      }

      if (this.debug) {
         EvalExprEvent e = new EvalExprEvent(scope, start, out.index() - 1, this.getExprStartChar(scope), this.getExprStopChar(scope));
         this.trackDebugEvent(scope, e);
      }

      return n;
   }

   protected int writeObject(STWriter out, InstanceScope scope, Object o, String[] options) {
      int n = 0;
      if (o == null) {
         if (options == null || options[Interpreter.Option.NULL.ordinal()] == null) {
            return 0;
         }

         o = options[Interpreter.Option.NULL.ordinal()];
      }

      if (o instanceof ST) {
         scope = new InstanceScope(scope, (ST)o);
         if (options != null && options[Interpreter.Option.WRAP.ordinal()] != null) {
            try {
               out.writeWrap(options[Interpreter.Option.WRAP.ordinal()]);
            } catch (IOException var8) {
               this.errMgr.IOError(scope.st, ErrorType.WRITE_IO_ERROR, var8);
            }
         }

         n = this.exec(out, scope);
      } else {
         o = this.convertAnythingIteratableToIterator(scope, o);

         try {
            if (o instanceof Iterator) {
               n = this.writeIterator(out, scope, o, options);
            } else {
               n = this.writePOJO(out, scope, o, options);
            }
         } catch (IOException var7) {
            this.errMgr.IOError(scope.st, ErrorType.WRITE_IO_ERROR, var7, o);
         }
      }

      return n;
   }

   protected int writeIterator(STWriter out, InstanceScope scope, Object o, String[] options) throws IOException {
      if (o == null) {
         return 0;
      } else {
         int n = 0;
         Iterator it = (Iterator)o;
         String separator = null;
         if (options != null) {
            separator = options[Interpreter.Option.SEPARATOR.ordinal()];
         }

         int nw;
         for(boolean seenAValue = false; it.hasNext(); n += nw) {
            Object iterValue = it.next();
            boolean needSeparator = seenAValue && separator != null && (iterValue != null || options[Interpreter.Option.NULL.ordinal()] != null);
            if (needSeparator) {
               n += out.writeSeparator(separator);
            }

            nw = this.writeObject(out, scope, iterValue, options);
            if (nw > 0) {
               seenAValue = true;
            }
         }

         return n;
      }
   }

   protected int writePOJO(STWriter out, InstanceScope scope, Object o, String[] options) throws IOException {
      String formatString = null;
      if (options != null) {
         formatString = options[Interpreter.Option.FORMAT.ordinal()];
      }

      AttributeRenderer r = scope.st.impl.nativeGroup.getAttributeRenderer(o.getClass());
      String v;
      if (r != null) {
         v = r.toString(o, formatString, this.locale);
      } else {
         v = o.toString();
      }

      int n;
      if (options != null && options[Interpreter.Option.WRAP.ordinal()] != null) {
         n = out.write(v, options[Interpreter.Option.WRAP.ordinal()]);
      } else {
         n = out.write(v);
      }

      return n;
   }

   protected int getExprStartChar(InstanceScope scope) {
      Interval templateLocation = scope.st.impl.sourceMap[scope.ip];
      return templateLocation != null ? templateLocation.a : -1;
   }

   protected int getExprStopChar(InstanceScope scope) {
      Interval templateLocation = scope.st.impl.sourceMap[scope.ip];
      return templateLocation != null ? templateLocation.b : -1;
   }

   protected void map(InstanceScope scope, Object attr, final ST st) {
      this.rot_map(scope, attr, new ArrayList() {
         {
            this.add(st);
         }
      });
   }

   protected void rot_map(InstanceScope scope, Object attr, List prototypes) {
      if (attr == null) {
         this.operands[++this.sp] = null;
      } else {
         attr = this.convertAnythingIteratableToIterator(scope, attr);
         if (attr instanceof Iterator) {
            List mapped = this.rot_map_iterator(scope, (Iterator)attr, prototypes);
            this.operands[++this.sp] = mapped;
         } else {
            ST proto = (ST)prototypes.get(0);
            ST st = this.group.createStringTemplateInternally(proto);
            if (st != null) {
               this.setFirstArgument(scope, st, attr);
               if (st.impl.isAnonSubtemplate) {
                  st.rawSetAttribute("i0", 0);
                  st.rawSetAttribute("i", 1);
               }

               this.operands[++this.sp] = st;
            } else {
               this.operands[++this.sp] = null;
            }
         }

      }
   }

   protected List rot_map_iterator(InstanceScope scope, Iterator attr, List prototypes) {
      List mapped = new ArrayList();
      Iterator iter = attr;
      int i0 = 0;
      int i = 1;
      int ti = 0;

      while(iter.hasNext()) {
         Object iterValue = iter.next();
         if (iterValue == null) {
            mapped.add((Object)null);
         } else {
            int templateIndex = ti % prototypes.size();
            ++ti;
            ST proto = (ST)prototypes.get(templateIndex);
            ST st = this.group.createStringTemplateInternally(proto);
            this.setFirstArgument(scope, st, iterValue);
            if (st.impl.isAnonSubtemplate) {
               st.rawSetAttribute("i0", i0);
               st.rawSetAttribute("i", i);
            }

            mapped.add(st);
            ++i0;
            ++i;
         }
      }

      return mapped;
   }

   protected ST.AttributeList zip_map(InstanceScope scope, List exprs, ST prototype) {
      if (exprs != null && prototype != null && exprs.size() != 0) {
         int numExprs;
         for(numExprs = 0; numExprs < exprs.size(); ++numExprs) {
            Object attr = exprs.get(numExprs);
            if (attr != null) {
               exprs.set(numExprs, this.convertAnythingToIterator(scope, attr));
            }
         }

         numExprs = exprs.size();
         CompiledST code = prototype.impl;
         Map formalArguments = code.formalArguments;
         if (code.hasFormalArgs && formalArguments != null) {
            String[] formalArgumentNames = (String[])formalArguments.keySet().toArray(new String[formalArguments.size()]);
            int nformalArgs = formalArgumentNames.length;
            if (prototype.isAnonSubtemplate()) {
               nformalArgs -= predefinedAnonSubtemplateAttributes.size();
            }

            if (nformalArgs != numExprs) {
               this.errMgr.runTimeError(this, scope, ErrorType.MAP_ARGUMENT_COUNT_MISMATCH, (Object)numExprs, nformalArgs);
               int shorterSize = Math.min(formalArgumentNames.length, numExprs);
               numExprs = shorterSize;
               String[] newFormalArgumentNames = new String[shorterSize];
               System.arraycopy(formalArgumentNames, 0, newFormalArgumentNames, 0, shorterSize);
               formalArgumentNames = newFormalArgumentNames;
            }

            ST.AttributeList results = new ST.AttributeList();
            int i = 0;

            while(true) {
               int numEmpty = 0;
               ST embedded = this.group.createStringTemplateInternally(prototype);
               embedded.rawSetAttribute("i0", i);
               embedded.rawSetAttribute("i", i + 1);

               for(int a = 0; a < numExprs; ++a) {
                  Iterator it = (Iterator)exprs.get(a);
                  if (it != null && it.hasNext()) {
                     String argName = formalArgumentNames[a];
                     Object iteratedValue = it.next();
                     embedded.rawSetAttribute(argName, iteratedValue);
                  } else {
                     ++numEmpty;
                  }
               }

               if (numEmpty == numExprs) {
                  return results;
               }

               results.add(embedded);
               ++i;
            }
         } else {
            this.errMgr.runTimeError(this, scope, ErrorType.MISSING_FORMAL_ARGUMENTS);
            return null;
         }
      } else {
         return null;
      }
   }

   protected void setFirstArgument(InstanceScope scope, ST st, Object attr) {
      if (!st.impl.hasFormalArgs && st.impl.formalArguments == null) {
         st.add("it", attr);
      } else if (st.impl.formalArguments == null) {
         this.errMgr.runTimeError(this, scope, ErrorType.ARGUMENT_COUNT_MISMATCH, 1, st.impl.name, 0);
      } else {
         st.locals[0] = attr;
      }
   }

   protected void addToList(InstanceScope scope, List list, Object o) {
      o = this.convertAnythingIteratableToIterator(scope, o);
      if (o instanceof Iterator) {
         Iterator it = (Iterator)o;

         while(it.hasNext()) {
            list.add(it.next());
         }
      } else {
         list.add(o);
      }

   }

   public Object first(InstanceScope scope, Object v) {
      if (v == null) {
         return null;
      } else {
         Object r = v;
         v = this.convertAnythingIteratableToIterator(scope, v);
         if (v instanceof Iterator) {
            Iterator it = (Iterator)v;
            if (it.hasNext()) {
               r = it.next();
            }
         }

         return r;
      }
   }

   public Object last(InstanceScope scope, Object v) {
      if (v == null) {
         return null;
      } else if (v instanceof List) {
         return ((List)v).get(((List)v).size() - 1);
      } else if (v.getClass().isArray()) {
         return Array.get(v, Array.getLength(v) - 1);
      } else {
         Object last = v;
         v = this.convertAnythingIteratableToIterator(scope, v);
         if (v instanceof Iterator) {
            for(Iterator it = (Iterator)v; it.hasNext(); last = it.next()) {
            }
         }

         return last;
      }
   }

   public Object rest(InstanceScope scope, Object v) {
      if (v == null) {
         return null;
      } else if (v instanceof List) {
         List elems = (List)v;
         return elems.size() <= 1 ? null : elems.subList(1, elems.size());
      } else {
         v = this.convertAnythingIteratableToIterator(scope, v);
         if (!(v instanceof Iterator)) {
            return null;
         } else {
            List a = new ArrayList();
            Iterator it = (Iterator)v;
            if (!it.hasNext()) {
               return null;
            } else {
               it.next();

               while(it.hasNext()) {
                  Object o = it.next();
                  a.add(o);
               }

               return a;
            }
         }
      }
   }

   public Object trunc(InstanceScope scope, Object v) {
      if (v == null) {
         return null;
      } else if (v instanceof List) {
         List elems = (List)v;
         return elems.size() <= 1 ? null : elems.subList(0, elems.size() - 1);
      } else {
         v = this.convertAnythingIteratableToIterator(scope, v);
         if (v instanceof Iterator) {
            List a = new ArrayList();
            Iterator it = (Iterator)v;

            while(it.hasNext()) {
               Object o = it.next();
               if (it.hasNext()) {
                  a.add(o);
               }
            }

            return a;
         } else {
            return null;
         }
      }
   }

   public Object strip(InstanceScope scope, Object v) {
      if (v == null) {
         return null;
      } else {
         v = this.convertAnythingIteratableToIterator(scope, v);
         if (v instanceof Iterator) {
            List a = new ArrayList();
            Iterator it = (Iterator)v;

            while(it.hasNext()) {
               Object o = it.next();
               if (o != null) {
                  a.add(o);
               }
            }

            return a;
         } else {
            return v;
         }
      }
   }

   public Object reverse(InstanceScope scope, Object v) {
      if (v == null) {
         return null;
      } else {
         v = this.convertAnythingIteratableToIterator(scope, v);
         if (!(v instanceof Iterator)) {
            return v;
         } else {
            List a = new LinkedList();
            Iterator it = (Iterator)v;

            while(it.hasNext()) {
               a.add(0, it.next());
            }

            return a;
         }
      }
   }

   public Object length(Object v) {
      if (v == null) {
         return 0;
      } else {
         int i = 1;
         if (v instanceof Map) {
            i = ((Map)v).size();
         } else if (v instanceof Collection) {
            i = ((Collection)v).size();
         } else if (v instanceof Object[]) {
            i = ((Object[])((Object[])v)).length;
         } else if (v.getClass().isArray()) {
            i = Array.getLength(v);
         } else if (v instanceof Iterator) {
            Iterator it = (Iterator)v;

            for(i = 0; it.hasNext(); ++i) {
               it.next();
            }
         }

         return i;
      }
   }

   protected String toString(STWriter out, InstanceScope scope, Object value) {
      if (value != null) {
         if (value.getClass() == String.class) {
            return (String)value;
         } else {
            StringWriter sw = new StringWriter();

            Object stw;
            try {
               Class writerClass = out.getClass();
               Constructor ctor = writerClass.getConstructor(Writer.class);
               stw = (STWriter)ctor.newInstance(sw);
            } catch (Exception var8) {
               stw = new AutoIndentWriter(sw);
               this.errMgr.runTimeError(this, scope, ErrorType.WRITER_CTOR_ISSUE, out.getClass().getSimpleName());
            }

            if (this.debug && !scope.earlyEval) {
               scope = new InstanceScope(scope, scope.st);
               scope.earlyEval = true;
            }

            this.writeObjectNoOptions((STWriter)stw, scope, value);
            return sw.toString();
         }
      } else {
         return null;
      }
   }

   public Object convertAnythingIteratableToIterator(InstanceScope scope, Object o) {
      Iterator iter = null;
      if (o == null) {
         return null;
      } else {
         if (o instanceof Collection) {
            iter = ((Collection)o).iterator();
         } else if (o instanceof Object[]) {
            iter = Arrays.asList((Object[])((Object[])o)).iterator();
         } else if (o.getClass().isArray()) {
            iter = new ArrayIterator(o);
         } else if (o instanceof Map) {
            if (scope.st.groupThatCreatedThisInstance.iterateAcrossValues) {
               iter = ((Map)o).values().iterator();
            } else {
               iter = ((Map)o).keySet().iterator();
            }
         }

         return iter == null ? o : iter;
      }
   }

   public Iterator convertAnythingToIterator(InstanceScope scope, Object o) {
      o = this.convertAnythingIteratableToIterator(scope, o);
      if (o instanceof Iterator) {
         return (Iterator)o;
      } else {
         List singleton = new ST.AttributeList(1);
         singleton.add(o);
         return singleton.iterator();
      }
   }

   protected boolean testAttributeTrue(Object a) {
      if (a == null) {
         return false;
      } else if (a instanceof Boolean) {
         return (Boolean)a;
      } else if (a instanceof Collection) {
         return ((Collection)a).size() > 0;
      } else if (a instanceof Map) {
         return ((Map)a).size() > 0;
      } else {
         return a instanceof Iterator ? ((Iterator)a).hasNext() : true;
      }
   }

   protected Object getObjectProperty(STWriter out, InstanceScope scope, Object o, Object property) {
      if (o == null) {
         this.errMgr.runTimeError(this, scope, ErrorType.NO_SUCH_PROPERTY, "null." + property);
         return null;
      } else {
         try {
            ST self = scope.st;
            ModelAdaptor adap = self.groupThatCreatedThisInstance.getModelAdaptor(o.getClass());
            return adap.getProperty(this, self, o, property, this.toString(out, scope, property));
         } catch (STNoSuchPropertyException var7) {
            this.errMgr.runTimeError(this, scope, ErrorType.NO_SUCH_PROPERTY, (Throwable)var7, o.getClass().getName() + "." + property);
            return null;
         }
      }
   }

   public Object getAttribute(InstanceScope scope, String name) {
      ST p;
      Object o;
      for(InstanceScope current = scope; current != null; current = current.parent) {
         p = current.st;
         FormalArgument localArg = null;
         if (p.impl.formalArguments != null) {
            localArg = (FormalArgument)p.impl.formalArguments.get(name);
         }

         if (localArg != null) {
            o = p.locals[localArg.index];
            return o;
         }
      }

      p = scope.st;
      STGroup g = p.impl.nativeGroup;
      o = this.getDictionary(g, name);
      if (o != null) {
         return o;
      } else {
         throw new STNoSuchAttributeException(name, scope);
      }
   }

   public Object getDictionary(STGroup g, String name) {
      if (g.isDictionary(name)) {
         return g.rawGetDictionary(name);
      } else {
         if (g.imports != null) {
            Iterator var3 = g.imports.iterator();

            while(var3.hasNext()) {
               STGroup sup = (STGroup)var3.next();
               Object o = this.getDictionary(sup, name);
               if (o != null) {
                  return o;
               }
            }
         }

         return null;
      }
   }

   public void setDefaultArguments(STWriter out, InstanceScope scope) {
      ST invokedST = scope.st;
      if (invokedST.impl.formalArguments != null && invokedST.impl.numberOfArgsWithDefaultValues != 0) {
         Iterator var4 = invokedST.impl.formalArguments.values().iterator();

         while(true) {
            while(true) {
               while(true) {
                  FormalArgument arg;
                  do {
                     do {
                        if (!var4.hasNext()) {
                           return;
                        }

                        arg = (FormalArgument)var4.next();
                     } while(invokedST.locals[arg.index] != ST.EMPTY_ATTR);
                  } while(arg.defaultValueToken == null);

                  if (arg.defaultValueToken.getType() == 4) {
                     CompiledST code = arg.compiledDefaultValue;
                     if (code == null) {
                        code = new CompiledST();
                     }

                     ST defaultArgST = this.group.createStringTemplateInternally(code);
                     defaultArgST.groupThatCreatedThisInstance = this.group;
                     String defArgTemplate = arg.defaultValueToken.getText();
                     if (defArgTemplate.startsWith("{" + this.group.delimiterStartChar + "(") && defArgTemplate.endsWith(")" + this.group.delimiterStopChar + "}")) {
                        invokedST.rawSetAttribute(arg.name, this.toString(out, new InstanceScope(scope, invokedST), defaultArgST));
                     } else {
                        invokedST.rawSetAttribute(arg.name, defaultArgST);
                     }
                  } else {
                     invokedST.rawSetAttribute(arg.name, arg.defaultValue);
                  }
               }
            }
         }
      }
   }

   public static String getEnclosingInstanceStackString(InstanceScope scope) {
      List templates = getEnclosingInstanceStack(scope, true);
      StringBuilder buf = new StringBuilder();
      int i = 0;

      for(Iterator var4 = templates.iterator(); var4.hasNext(); ++i) {
         ST st = (ST)var4.next();
         if (i > 0) {
            buf.append(" ");
         }

         buf.append(st.getName());
      }

      return buf.toString();
   }

   public static List getEnclosingInstanceStack(InstanceScope scope, boolean topdown) {
      List stack = new LinkedList();

      for(InstanceScope p = scope; p != null; p = p.parent) {
         if (topdown) {
            stack.add(0, p.st);
         } else {
            stack.add(p.st);
         }
      }

      return stack;
   }

   public static List getScopeStack(InstanceScope scope, boolean topdown) {
      List stack = new LinkedList();

      for(InstanceScope p = scope; p != null; p = p.parent) {
         if (topdown) {
            stack.add(0, p);
         } else {
            stack.add(p);
         }
      }

      return stack;
   }

   public static List getEvalTemplateEventStack(InstanceScope scope, boolean topdown) {
      List stack = new LinkedList();

      for(InstanceScope p = scope; p != null; p = p.parent) {
         EvalTemplateEvent eval = (EvalTemplateEvent)p.events.get(p.events.size() - 1);
         if (topdown) {
            stack.add(0, eval);
         } else {
            stack.add(eval);
         }
      }

      return stack;
   }

   protected void trace(InstanceScope scope, int ip) {
      ST self = scope.st;
      StringBuilder tr = new StringBuilder();
      BytecodeDisassembler dis = new BytecodeDisassembler(self.impl);
      StringBuilder buf = new StringBuilder();
      dis.disassembleInstruction(buf, ip);
      String name = self.impl.name + ":";
      if (Misc.referenceEquals(self.impl.name, "anonymous")) {
         name = "";
      }

      tr.append(String.format("%-40s", name + buf));
      tr.append("\tstack=[");

      for(int i = 0; i <= this.sp; ++i) {
         Object o = this.operands[i];
         this.printForTrace(tr, scope, o);
      }

      tr.append(" ], calls=");
      tr.append(getEnclosingInstanceStackString(scope));
      tr.append(", sp=" + this.sp + ", nw=" + this.nwline);
      String s = tr.toString();
      if (this.debug) {
         this.executeTrace.add(s);
      }

      if (trace) {
         System.out.println(s);
      }

   }

   protected void printForTrace(StringBuilder tr, InstanceScope scope, Object o) {
      if (o instanceof ST) {
         if (((ST)o).impl == null) {
            tr.append("bad-template()");
         } else {
            tr.append(" " + ((ST)o).impl.name + "()");
         }

      } else {
         o = this.convertAnythingIteratableToIterator(scope, o);
         if (o instanceof Iterator) {
            Iterator it = (Iterator)o;
            tr.append(" [");

            while(it.hasNext()) {
               Object iterValue = it.next();
               this.printForTrace(tr, scope, iterValue);
            }

            tr.append(" ]");
         } else {
            tr.append(" " + o);
         }

      }
   }

   public List getEvents() {
      return this.events;
   }

   protected void trackDebugEvent(InstanceScope scope, InterpEvent e) {
      this.events.add(e);
      scope.events.add(e);
      if (e instanceof EvalTemplateEvent) {
         InstanceScope parent = scope.parent;
         if (parent != null) {
            scope.parent.childEvalTemplateEvents.add((EvalTemplateEvent)e);
         }
      }

   }

   public List getExecutionTrace() {
      return this.executeTrace;
   }

   public static int getShort(byte[] memory, int index) {
      int b1 = memory[index] & 255;
      int b2 = memory[index + 1] & 255;
      return b1 << 8 | b2;
   }

   protected static class ArgumentsMap extends HashMap {
   }

   protected static class ObjectList extends ArrayList {
   }

   public static enum Option {
      ANCHOR,
      FORMAT,
      NULL,
      SEPARATOR,
      WRAP;
   }
}
