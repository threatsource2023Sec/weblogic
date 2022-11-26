import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("wsgiref/__init__.py")
public class wsgiref$py extends PyFunctionTable implements PyRunnable {
   static wsgiref$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("wsgiref -- a WSGI (PEP 333) Reference Library\n\nCurrent Contents:\n\n* util -- Miscellaneous useful functions and wrappers\n\n* headers -- Manage response headers\n\n* handlers -- base classes for server/gateway implementations\n\n* simple_server -- a simple BaseHTTPServer that supports WSGI\n\n* validate -- validation wrapper that sits between an app and a server\n  to detect errors in either\n\nTo-Do:\n\n* cgi_gateway -- Run WSGI apps under CGI (pending a deployment standard)\n\n* cgi_wrapper -- Run CGI apps under WSGI\n\n* router -- a simple middleware component that handles URL traversal\n"));
      var1.setline(23);
      PyString.fromInterned("wsgiref -- a WSGI (PEP 333) Reference Library\n\nCurrent Contents:\n\n* util -- Miscellaneous useful functions and wrappers\n\n* headers -- Manage response headers\n\n* handlers -- base classes for server/gateway implementations\n\n* simple_server -- a simple BaseHTTPServer that supports WSGI\n\n* validate -- validation wrapper that sits between an app and a server\n  to detect errors in either\n\nTo-Do:\n\n* cgi_gateway -- Run WSGI apps under CGI (pending a deployment standard)\n\n* cgi_wrapper -- Run CGI apps under WSGI\n\n* router -- a simple middleware component that handles URL traversal\n");
      var1.f_lasti = -1;
      return Py.None;
   }

   public wsgiref$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new wsgiref$py("wsgiref$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(wsgiref$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         default:
            return null;
      }
   }
}
