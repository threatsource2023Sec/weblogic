package org.python.compiler;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import org.python.antlr.ParseException;
import org.python.antlr.PythonTree;
import org.python.antlr.ast.Return;
import org.python.antlr.base.expr;
import org.python.core.Options;

public class ScopeInfo implements ScopeConstants {
   public PythonTree scope_node;
   public String scope_name;
   public int level;
   public int func_level;
   public int kind;
   public boolean unqual_exec;
   public boolean exec;
   public boolean from_import_star;
   public boolean contains_ns_free_vars;
   public boolean generator;
   private boolean hasReturnWithValue;
   public int yield_count;
   public int max_with_count;
   public ArgListCompiler ac;
   public Map tbl = new LinkedHashMap();
   public Vector names = new Vector();
   public int local = 0;
   private static final Object PRESENT = new Object();
   public Hashtable inner_free = new Hashtable();
   public Vector cellvars = new Vector();
   public Vector jy_paramcells = new Vector();
   public int jy_npurecell;
   public int cell;
   public int distance;
   public ScopeInfo up;
   public Vector freevars = new Vector();

   public void dump() {
      if (Options.verbose >= 3) {
         for(int i = 0; i < this.level; ++i) {
            System.err.print(' ');
         }

         System.err.print((this.kind != 2 ? this.scope_name : "class " + this.scope_name) + ": ");

         for(Iterator var6 = this.tbl.entrySet().iterator(); var6.hasNext(); System.err.print(" ")) {
            Map.Entry entry = (Map.Entry)var6.next();
            String name = (String)entry.getKey();
            SymInfo info = (SymInfo)entry.getValue();
            int flags = info.flags;
            System.err.print(name);
            if ((flags & 1) != 0) {
               System.err.print('=');
            }

            if ((flags & 2) != 0) {
               System.err.print('G');
            } else if ((flags & 64) != 0) {
               System.err.print('g');
            }

            if ((flags & 4) != 0) {
               System.err.print('P');
            } else if ((flags & 8) != 0) {
               System.err.print('p');
            }

            if ((flags & 16) != 0) {
               System.err.print('!');
            }

            if ((flags & 32) != 0) {
               System.err.print(",f");
            }
         }

         System.err.println();
      }
   }

   public ScopeInfo(String name, PythonTree node, int level, int kind, int func_level, ArgListCompiler ac) {
      this.scope_name = name;
      this.scope_node = node;
      this.level = level;
      this.kind = kind;
      this.func_level = func_level;
      this.ac = ac;
   }

   public int addGlobal(String name) {
      int global = this.kind == 2 ? 64 : 2;
      SymInfo info = (SymInfo)this.tbl.get(name);
      if (info == null) {
         this.tbl.put(name, new SymInfo(global | 1));
         return -1;
      } else {
         int prev = info.flags;
         info.flags |= global | 1;
         return prev;
      }
   }

   public void addParam(String name) {
      this.tbl.put(name, new SymInfo(5, this.local++));
      this.names.addElement(name);
   }

   public void markFromParam() {
      SymInfo info;
      for(Iterator var1 = this.tbl.values().iterator(); var1.hasNext(); info.flags |= 8) {
         info = (SymInfo)var1.next();
      }

   }

   public void addBound(String name) {
      SymInfo info = (SymInfo)this.tbl.get(name);
      if (info == null) {
         this.tbl.put(name, new SymInfo(1));
      } else {
         info.flags |= 1;
      }
   }

   public void addUsed(String name) {
      if (this.tbl.get(name) == null) {
         this.tbl.put(name, new SymInfo(0));
      }
   }

   public void cook(ScopeInfo up, int distance, CompilationContext ctxt) throws Exception {
      if (up != null) {
         this.up = up;
         this.distance = distance;
         boolean func = this.kind == 1;
         Vector purecells = new Vector();
         this.cell = 0;
         boolean some_inner_free = this.inner_free.size() > 0;
         Enumeration e = this.inner_free.keys();

         int flags;
         while(e.hasMoreElements()) {
            String name = (String)e.nextElement();
            SymInfo info = (SymInfo)this.tbl.get(name);
            if (info == null) {
               this.tbl.put(name, new SymInfo(32));
            } else {
               flags = info.flags;
               if (func) {
                  if ((flags & 2) == 0 && (flags & 1) != 0) {
                     info.flags |= 16;
                     if ((info.flags & 4) != 0) {
                        this.jy_paramcells.addElement(name);
                     }

                     this.cellvars.addElement(name);
                     info.env_index = this.cell++;
                     if ((flags & 4) == 0) {
                        purecells.addElement(name);
                     }
                  }
               } else {
                  info.flags |= 32;
               }
            }
         }

         boolean some_free = false;
         boolean nested = up.kind != 0;
         Iterator var16 = this.tbl.entrySet().iterator();

         while(var16.hasNext()) {
            Map.Entry entry = (Map.Entry)var16.next();
            String name = (String)entry.getKey();
            SymInfo info = (SymInfo)entry.getValue();
            int flags = info.flags;
            if (nested && (flags & 32) != 0) {
               up.inner_free.put(name, PRESENT);
            }

            if ((flags & 86) == 0) {
               if ((flags & 1) != 0) {
                  this.names.addElement(name);
                  info.locals_index = this.local++;
               } else {
                  info.flags |= 32;
                  some_free = true;
                  if (nested) {
                     up.inner_free.put(name, PRESENT);
                  }
               }
            }
         }

         if ((this.jy_npurecell = purecells.size()) > 0) {
            int sz = purecells.size();

            for(flags = 0; flags < sz; ++flags) {
               this.names.addElement(purecells.elementAt(flags));
            }
         }

         if (some_free && nested) {
            up.contains_ns_free_vars = true;
         }

         if (this.unqual_exec || this.from_import_star) {
            if (some_inner_free) {
               this.dynastuff_trouble(true, ctxt);
            } else if (this.func_level > 1 && some_free) {
               this.dynastuff_trouble(false, ctxt);
            }
         }

      }
   }

   private void dynastuff_trouble(boolean inner_free, CompilationContext ctxt) throws Exception {
      StringBuilder illegal = new StringBuilder();
      if (this.unqual_exec && this.from_import_star) {
         illegal.append("function '").append(this.scope_name).append("' uses import * and bare exec, which are illegal");
      } else if (this.unqual_exec) {
         illegal.append("unqualified exec is not allowed in function '").append(this.scope_name).append("'");
      } else {
         illegal.append("import * is not allowed in function '").append(this.scope_name).append("'");
      }

      if (inner_free) {
         illegal.append(" because it contains a function with free variables");
      } else {
         illegal.append(" because it contains free variables");
      }

      ctxt.error(illegal.toString(), true, this.scope_node);
   }

   public void setup_closure() {
      this.setup_closure(this.up);
   }

   public void setup_closure(ScopeInfo up) {
      int free = this.cell;
      Map up_tbl = up.tbl;
      boolean nested = up.kind != 0;
      Iterator var5 = this.tbl.entrySet().iterator();

      while(true) {
         while(true) {
            String name;
            SymInfo info;
            int flags;
            do {
               if (!var5.hasNext()) {
                  return;
               }

               Map.Entry entry = (Map.Entry)var5.next();
               name = (String)entry.getKey();
               info = (SymInfo)entry.getValue();
               flags = info.flags;
            } while((flags & 32) == 0);

            SymInfo up_info = (SymInfo)up_tbl.get(name);
            if (up_info != null) {
               int up_flags = up_info.flags;
               if ((up_flags & 48) != 0) {
                  info.env_index = free++;
                  this.freevars.addElement(name);
                  continue;
               }

               if (nested && (up_flags & 2) != 0) {
                  info.flags = 3;
                  continue;
               }
            }

            info.flags &= -33;
         }
      }
   }

   public String toString() {
      return "ScopeInfo[" + this.scope_name + " " + this.kind + "]@" + System.identityHashCode(this);
   }

   public void defineAsGenerator(expr node) {
      this.generator = true;
      if (this.hasReturnWithValue) {
         throw new ParseException("'return' with argument inside generator", node);
      }
   }

   public void noteReturnValue(Return node) {
      if (this.generator) {
         throw new ParseException("'return' with argument inside generator", node);
      } else {
         this.hasReturnWithValue = true;
      }
   }
}
