package org.python.modules.sre;

import java.util.ArrayList;
import java.util.List;
import org.python.core.ArgParser;
import org.python.core.BufferProtocol;
import org.python.core.Py;
import org.python.core.PyBuffer;
import org.python.core.PyBuiltinCallable;
import org.python.core.PyBuiltinMethod;
import org.python.core.PyCallIter;
import org.python.core.PyDataDescr;
import org.python.core.PyList;
import org.python.core.PyNewWrapper;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.PyType;
import org.python.core.Traverseproc;
import org.python.core.Visitproc;
import org.python.core.imp;
import org.python.expose.BaseTypeBuilder;
import org.python.expose.ExposeAsSuperclass;
import org.python.expose.ExposedType;

@ExposedType(
   name = "SRE_Pattern",
   doc = "Compiled regular expression objects"
)
public class PatternObject extends PyObject implements Traverseproc {
   int[] code;
   public PyString pattern;
   public int groups;
   public PyObject groupindex;
   public int flags;
   PyObject indexgroup;
   public int codesize;

   public PatternObject(PyString pattern, int flags, int[] code, int groups, PyObject groupindex, PyObject indexgroup) {
      if (pattern != null) {
         this.pattern = pattern;
      }

      this.flags = flags;
      this.code = code;
      this.codesize = code.length;
      this.groups = groups;
      this.groupindex = groupindex;
      this.indexgroup = indexgroup;
   }

   public PyString getPattern() {
      return this.pattern == null ? Py.EmptyString : this.pattern;
   }

   public int getFlags() {
      return this.flags;
   }

   public int getGroups() {
      return this.groups;
   }

   public PyObject getGroupindex() {
      return this.groupindex;
   }

   public PyObject match(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("match", args, kws, "string", "pos", "endpos");
      PyString string = extractPyString(ap, 0);
      int start = ap.getInt(1, 0);
      int end = ap.getInt(2, string.__len__());
      SRE_STATE state = new SRE_STATE(string, start, end, this.flags);
      state.ptr = state.start;
      int status = state.SRE_MATCH(this.code, 0, 1);
      MatchObject matchObject = this._pattern_new_match(state, string, status);
      return (PyObject)(matchObject != null ? matchObject : Py.None);
   }

   public PyObject search(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("search", args, kws, "string", "pos", "endpos");
      PyString string = extractPyString(ap, 0);
      int start = ap.getInt(1, 0);
      int end = ap.getInt(2, string.__len__());
      SRE_STATE state = new SRE_STATE(string, start, end, this.flags);
      int status = state.SRE_SEARCH(this.code, 0);
      MatchObject matchObject = this._pattern_new_match(state, string, status);
      return (PyObject)(matchObject != null ? matchObject : Py.None);
   }

   public PyObject sub(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("sub", args, kws, "repl", "string", "count");
      PyObject template = ap.getPyObject(0);
      int count = ap.getInt(2, 0);
      return this.subx(template, extractPyString(ap, 1), count, false);
   }

   public PyObject subn(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("subn", args, kws, "repl", "string", "count");
      PyObject template = ap.getPyObject(0);
      int count = ap.getInt(2, 0);
      return this.subx(template, extractPyString(ap, 1), count, true);
   }

   private PyObject subx(PyObject template, PyString instring, int count, boolean subn) {
      PyString string = instring;
      PyObject filter = null;
      boolean filter_is_callable = false;
      if (template.isCallable()) {
         filter = template;
         filter_is_callable = true;
      } else {
         boolean literal = false;
         if (template instanceof PyString) {
            literal = template.toString().indexOf(92) < 0;
         }

         if (literal) {
            filter = template;
            filter_is_callable = false;
         } else {
            filter = this.call("re", "_subx", new PyObject[]{this, template});
            filter_is_callable = filter.isCallable();
         }
      }

      SRE_STATE state = new SRE_STATE(instring, 0, Integer.MAX_VALUE, this.flags);
      PyList list = new PyList();
      int n = 0;
      int i = 0;

      while(count == 0 || n < count) {
         state.state_reset();
         state.ptr = state.start;
         int status = state.SRE_SEARCH(this.code, 0);
         if (status <= 0) {
            if (status == 0) {
               break;
            }

            this._error(status);
         }

         int b = state.start;
         int e = state.ptr;
         if (i < b) {
            list.append(string.__getslice__(Py.newInteger(i), Py.newInteger(b)));
         }

         if (i != b || i != e || n <= 0) {
            PyObject item;
            if (filter_is_callable) {
               MatchObject match = this._pattern_new_match(state, instring, 1);
               item = filter.__call__((PyObject)match);
            } else {
               item = filter;
            }

            if (item != Py.None) {
               list.append(item);
            }

            i = e;
            ++n;
         }

         if (state.ptr == state.start) {
            state.start = state.ptr + 1;
         } else {
            state.start = state.ptr;
         }
      }

      if (i < state.endpos) {
         list.append(string.__getslice__(Py.newInteger(i), Py.newInteger(state.endpos)));
      }

      PyObject outstring = this.join_list(list, string);
      return (PyObject)(subn ? new PyTuple(new PyObject[]{outstring, Py.newInteger(n)}) : outstring);
   }

   private PyObject join_list(PyList list, PyString string) {
      PyObject joiner = string.__getslice__(Py.Zero, Py.Zero);
      return list.size() == 0 ? joiner : joiner.__getattr__("join").__call__((PyObject)list);
   }

   public PyObject split(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("split", args, kws, "string", "maxsplit");
      PyString string = extractPyString(ap, 0);
      int maxsplit = ap.getInt(1, 0);
      SRE_STATE state = new SRE_STATE(string, 0, Integer.MAX_VALUE, this.flags);
      PyList list = new PyList();
      int n = 0;
      int last = state.start;

      while(maxsplit == 0 || n < maxsplit) {
         state.state_reset();
         state.ptr = state.start;
         int status = state.SRE_SEARCH(this.code, 0);
         if (status <= 0) {
            if (status == 0) {
               break;
            }

            this._error(status);
         }

         if (state.start == state.ptr) {
            if (last == state.end) {
               break;
            }

            state.start = state.ptr + 1;
         } else {
            PyObject item = string.__getslice__(Py.newInteger(last), Py.newInteger(state.start));
            list.append(item);

            for(int i = 0; i < this.groups; ++i) {
               String s = state.getslice(i + 1, string.toString(), false);
               if (s != null) {
                  list.append(string.createInstance(s));
               } else {
                  list.append(Py.None);
               }
            }

            ++n;
            last = state.start = state.ptr;
         }
      }

      list.append(string.__getslice__(Py.newInteger(last), Py.newInteger(state.endpos)));
      return list;
   }

   private PyObject call(String module, String function, PyObject[] args) {
      PyObject sre = imp.importName(module, true);
      return sre.invoke(function, args);
   }

   public PyObject findall(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("findall", args, kws, "string", "pos", "endpos");
      PyString string = extractPyString(ap, 0);
      int start = ap.getInt(1, 0);
      int end = ap.getInt(2, Integer.MAX_VALUE);
      SRE_STATE state = new SRE_STATE(string, start, end, this.flags);
      List list = new ArrayList();

      while(state.start <= state.end) {
         state.state_reset();
         state.ptr = state.start;
         int status = state.SRE_SEARCH(this.code, 0);
         if (status <= 0) {
            if (status == 0) {
               break;
            }

            this._error(status);
         } else {
            Object item;
            switch (this.groups) {
               case 0:
                  item = string.__getslice__(Py.newInteger(state.start), Py.newInteger(state.ptr));
                  break;
               case 1:
                  item = string.createInstance(state.getslice(1, string.toString(), true));
                  break;
               default:
                  PyObject[] t = new PyObject[this.groups];

                  for(int i = 0; i < this.groups; ++i) {
                     t[i] = string.createInstance(state.getslice(i + 1, string.toString(), true));
                  }

                  item = new PyTuple(t);
            }

            list.add(item);
            if (state.ptr == state.start) {
               state.start = state.ptr + 1;
            } else {
               state.start = state.ptr;
            }
         }
      }

      return new PyList(list);
   }

   public PyObject finditer(PyObject[] args, String[] kws) {
      ScannerObject scanner = this.scanner(args, kws);
      PyObject search = scanner.__findattr__("search");
      return new PyCallIter(search, Py.None);
   }

   public ScannerObject scanner(PyObject[] args, String[] kws) {
      ArgParser ap = new ArgParser("scanner", args, kws, "pattern", "pos", "endpos");
      PyString string = extractPyString(ap, 0);
      ScannerObject self = new ScannerObject();
      self.state = new SRE_STATE(string, ap.getInt(1, 0), ap.getInt(2, Integer.MAX_VALUE), this.flags);
      self.pattern = this;
      self.string = string;
      return self;
   }

   private void _error(int status) {
      if (status == -3) {
         throw Py.RuntimeError("maximum recursion limit exceeded");
      } else {
         throw Py.RuntimeError("internal error in regular expression engine");
      }
   }

   MatchObject _pattern_new_match(SRE_STATE state, PyString string, int status) {
      if (status <= 0) {
         if (status == 0) {
            return null;
         } else {
            this._error(status);
            return null;
         }
      } else {
         MatchObject match = new MatchObject();
         match.pattern = this;
         match.string = string;
         match.regs = null;
         match.groups = this.groups + 1;
         int base = state.beginning;
         match.mark = new int[match.groups * 2];
         match.mark[0] = state.start - base;
         match.mark[1] = state.ptr - base;
         int j = 0;

         for(int i = 0; i < this.groups; j += 2) {
            if (j + 1 <= state.lastmark && state.mark[j] != -1 && state.mark[j + 1] != -1) {
               match.mark[j + 2] = state.mark[j] - base;
               match.mark[j + 3] = state.mark[j + 1] - base;
            } else {
               match.mark[j + 2] = match.mark[j + 3] = -1;
            }

            ++i;
         }

         match.pos = state.pos;
         match.endpos = state.endpos;
         match.lastindex = state.lastindex;
         return match;
      }
   }

   private static PyString extractPyString(ArgParser ap, int pos) {
      PyObject obj = ap.getPyObject(pos);
      if (obj instanceof PyString) {
         return (PyString)obj;
      } else if (obj instanceof BufferProtocol) {
         PyBuffer buf = ((BufferProtocol)obj).getBuffer(284);
         Throwable var4 = null;

         PyString var5;
         try {
            var5 = new PyString(buf.toString());
         } catch (Throwable var14) {
            var4 = var14;
            throw var14;
         } finally {
            if (buf != null) {
               if (var4 != null) {
                  try {
                     buf.close();
                  } catch (Throwable var13) {
                     var4.addSuppressed(var13);
                  }
               } else {
                  buf.close();
               }
            }

         }

         return var5;
      } else {
         throw Py.TypeError("expected string or buffer, but got " + obj.getType());
      }
   }

   public int traverse(Visitproc visit, Object arg) {
      int retVal;
      if (this.pattern != null) {
         retVal = visit.visit(this.pattern, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      if (this.groupindex != null) {
         retVal = visit.visit(this.groupindex, arg);
         if (retVal != 0) {
            return retVal;
         }
      }

      return this.indexgroup != null ? visit.visit(this.indexgroup, arg) : 0;
   }

   public boolean refersDirectlyTo(PyObject ob) {
      return ob != null && (ob == this.pattern || ob == this.groupindex || ob == this.indexgroup);
   }

   static {
      PyType.addBuilder(PatternObject.class, new PyExposer());
   }

   private static class match_exposer extends PyBuiltinMethod {
      public match_exposer(String var1) {
         super(var1);
         super.doc = "match(string[, pos[, endpos]]) --> match object or None.\n    Matches zero or more characters at the beginning of the string";
      }

      public match_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "match(string[, pos[, endpos]]) --> match object or None.\n    Matches zero or more characters at the beginning of the string";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new match_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).match(var1, var2);
      }
   }

   private static class search_exposer extends PyBuiltinMethod {
      public search_exposer(String var1) {
         super(var1);
         super.doc = "search(string[, pos[, endpos]]) --> match object or None.\n    Scan through string looking for a match, and return a corresponding\n    match object instance. Return None if no position in the string matches.";
      }

      public search_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "search(string[, pos[, endpos]]) --> match object or None.\n    Scan through string looking for a match, and return a corresponding\n    match object instance. Return None if no position in the string matches.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new search_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).search(var1, var2);
      }
   }

   private static class sub_exposer extends PyBuiltinMethod {
      public sub_exposer(String var1) {
         super(var1);
         super.doc = "sub(repl, string[, count = 0]) --> newstring\n    Return the string obtained by replacing the leftmost non-overlapping\n    occurrences of pattern in string by the replacement repl.";
      }

      public sub_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "sub(repl, string[, count = 0]) --> newstring\n    Return the string obtained by replacing the leftmost non-overlapping\n    occurrences of pattern in string by the replacement repl.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new sub_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).sub(var1, var2);
      }
   }

   private static class subn_exposer extends PyBuiltinMethod {
      public subn_exposer(String var1) {
         super(var1);
         super.doc = "subn(repl, string[, count = 0]) --> (newstring, number of subs)\n    Return the tuple (new_string, number_of_subs_made) found by replacing\n    the leftmost non-overlapping occurrences of pattern with the\n    replacement repl.";
      }

      public subn_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "subn(repl, string[, count = 0]) --> (newstring, number of subs)\n    Return the tuple (new_string, number_of_subs_made) found by replacing\n    the leftmost non-overlapping occurrences of pattern with the\n    replacement repl.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new subn_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).subn(var1, var2);
      }
   }

   private static class split_exposer extends PyBuiltinMethod {
      public split_exposer(String var1) {
         super(var1);
         super.doc = "split(string[, maxsplit = 0])  --> list.\n    Split string by the occurrences of pattern.";
      }

      public split_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "split(string[, maxsplit = 0])  --> list.\n    Split string by the occurrences of pattern.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new split_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).split(var1, var2);
      }
   }

   private static class findall_exposer extends PyBuiltinMethod {
      public findall_exposer(String var1) {
         super(var1);
         super.doc = "findall(string[, pos[, endpos]]) --> list.\n   Return a list of all non-overlapping matches of pattern in string.";
      }

      public findall_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "findall(string[, pos[, endpos]]) --> list.\n   Return a list of all non-overlapping matches of pattern in string.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new findall_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).findall(var1, var2);
      }
   }

   private static class finditer_exposer extends PyBuiltinMethod {
      public finditer_exposer(String var1) {
         super(var1);
         super.doc = "finditer(string[, pos[, endpos]]) --> iterator.\n    Return an iterator over all non-overlapping matches for the \n    RE pattern in string. For each match, the iterator returns a\n    match object.";
      }

      public finditer_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "finditer(string[, pos[, endpos]]) --> iterator.\n    Return an iterator over all non-overlapping matches for the \n    RE pattern in string. For each match, the iterator returns a\n    match object.";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new finditer_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).finditer(var1, var2);
      }
   }

   private static class scanner_exposer extends PyBuiltinMethod {
      public scanner_exposer(String var1) {
         super(var1);
         super.doc = "";
      }

      public scanner_exposer(PyType var1, PyObject var2, PyBuiltinCallable.Info var3) {
         super(var1, var2, var3);
         super.doc = "";
      }

      public PyBuiltinCallable bind(PyObject var1) {
         return new scanner_exposer(this.getType(), var1, this.info);
      }

      public PyObject __call__(PyObject[] var1, String[] var2) {
         return ((PatternObject)this.self).scanner(var1, var2);
      }
   }

   private static class pattern_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public pattern_descriptor() {
         super("pattern", PyString.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PatternObject)var1).getPattern();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class flags_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public flags_descriptor() {
         super("flags", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PatternObject)var1).getFlags());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class groupindex_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public groupindex_descriptor() {
         super("groupindex", PyObject.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return ((PatternObject)var1).getGroupindex();
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class groups_descriptor extends PyDataDescr implements ExposeAsSuperclass {
      public groups_descriptor() {
         super("groups", Integer.class, (String)null);
      }

      public Object invokeGet(PyObject var1) {
         return Py.newInteger(((PatternObject)var1).getGroups());
      }

      public boolean implementsDescrGet() {
         return true;
      }

      public boolean implementsDescrSet() {
         return false;
      }

      public boolean implementsDescrDelete() {
         return false;
      }
   }

   private static class PyExposer extends BaseTypeBuilder {
      public PyExposer() {
         PyBuiltinMethod[] var1 = new PyBuiltinMethod[]{new match_exposer("match"), new search_exposer("search"), new sub_exposer("sub"), new subn_exposer("subn"), new split_exposer("split"), new findall_exposer("findall"), new finditer_exposer("finditer"), new scanner_exposer("scanner")};
         PyDataDescr[] var2 = new PyDataDescr[]{new pattern_descriptor(), new flags_descriptor(), new groupindex_descriptor(), new groups_descriptor()};
         super("SRE_Pattern", PatternObject.class, Object.class, (boolean)1, "Compiled regular expression objects", var1, var2, (PyNewWrapper)null);
      }
   }
}
