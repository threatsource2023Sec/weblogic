import java.util.Arrays;
import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyFrame;
import org.python.core.PyFunction;
import org.python.core.PyFunctionTable;
import org.python.core.PyInteger;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;
import org.python.core.imp;

@APIVersion(37)
@MTime(1498849384000L)
@Filename("dbexts.py")
public class dbexts$py extends PyFunctionTable implements PyRunnable {
   static dbexts$py self;
   static final PyCode f$0;
   static final PyCode f$1;
   static final PyCode console$2;
   static final PyCode f$3;
   static final PyCode f$4;
   static final PyCode f$5;
   static final PyCode html$6;
   static final PyCode f$7;
   static final PyCode f$8;
   static final PyCode f$9;
   static final PyCode mxODBCProxy$10;
   static final PyCode __init__$11;
   static final PyCode __getattr__$12;
   static final PyCode execute$13;
   static final PyCode gettypeinfo$14;
   static final PyCode executor$15;
   static final PyCode __init__$16;
   static final PyCode execute$17;
   static final PyCode connect$18;
   static final PyCode lookup$19;
   static final PyCode dbexts$20;
   static final PyCode __init__$21;
   static final PyCode __str__$22;
   static final PyCode __repr__$23;
   static final PyCode __getattr__$24;
   static final PyCode close$25;
   static final PyCode begin$26;
   static final PyCode commit$27;
   static final PyCode rollback$28;
   static final PyCode prepare$29;
   static final PyCode display$30;
   static final PyCode f$31;
   static final PyCode __execute__$32;
   static final PyCode isql$33;
   static final PyCode raw$34;
   static final PyCode f$35;
   static final PyCode f$36;
   static final PyCode callproc$37;
   static final PyCode pk$38;
   static final PyCode fk$39;
   static final PyCode table$40;
   static final PyCode proc$41;
   static final PyCode stat$42;
   static final PyCode typeinfo$43;
   static final PyCode tabletypeinfo$44;
   static final PyCode schema$45;
   static final PyCode bulkcopy$46;
   static final PyCode bcp$47;
   static final PyCode unload$48;
   static final PyCode Bulkcopy$49;
   static final PyCode __init__$50;
   static final PyCode f$51;
   static final PyCode f$52;
   static final PyCode __str__$53;
   static final PyCode __repr__$54;
   static final PyCode __getattr__$55;
   static final PyCode __filter__$56;
   static final PyCode f$57;
   static final PyCode f$58;
   static final PyCode f$59;
   static final PyCode format$60;
   static final PyCode done$61;
   static final PyCode batch$62;
   static final PyCode rowxfer$63;
   static final PyCode transfer$64;
   static final PyCode Unload$65;
   static final PyCode __init__$66;
   static final PyCode format$67;
   static final PyCode unload$68;
   static final PyCode f$69;
   static final PyCode Schema$70;
   static final PyCode __init__$71;
   static final PyCode computeschema$72;
   static final PyCode f$73;
   static final PyCode f$74;
   static final PyCode f$75;
   static final PyCode f$76;
   static final PyCode f$77;
   static final PyCode f$78;
   static final PyCode f$79;
   static final PyCode f$80;
   static final PyCode f$81;
   static final PyCode f$82;
   static final PyCode cckmp$83;
   static final PyCode f$84;
   static final PyCode __str__$85;
   static final PyCode f$86;
   static final PyCode IniParser$87;
   static final PyCode __init__$88;
   static final PyCode parse$89;
   static final PyCode f$90;
   static final PyCode f$91;
   static final PyCode __getitem__$92;
   static final PyCode f$93;
   static final PyCode random_table_name$94;
   static final PyCode ResultSetRow$95;
   static final PyCode __init__$96;
   static final PyCode __getitem__$97;
   static final PyCode __getslice__$98;
   static final PyCode __len__$99;
   static final PyCode __repr__$100;
   static final PyCode ResultSet$101;
   static final PyCode __init__$102;
   static final PyCode f$103;
   static final PyCode index$104;
   static final PyCode __getitem__$105;
   static final PyCode __getslice__$106;
   static final PyCode f$107;
   static final PyCode __repr__$108;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nThis script provides platform independence by wrapping Python\nDatabase API 2.0 compatible drivers to allow seamless database\nusage across implementations.\n\nIn order to use the C version, you need mxODBC and mxDateTime.\nIn order to use the Java version, you need zxJDBC.\n\n>>> import dbexts\n>>> d = dbexts.dbexts() # use the default db\n>>> d.isql('select count(*) count from player')\n\ncount\n-------\n13569.0\n\n1 row affected\n\n>>> r = d.raw('select count(*) count from player')\n>>> r\n([('count', 3, 17, None, 15, 0, 1)], [(13569.0,)])\n>>>\n\nThe configuration file follows the following format in a file name dbexts.ini:\n\n[default]\nname=mysql\n\n[jdbc]\nname=mysql\nurl=jdbc:mysql://localhost/ziclix\nuser=\npwd=\ndriver=org.gjt.mm.mysql.Driver\ndatahandler=com.ziclix.python.sql.handler.MySQLDataHandler\n\n[jdbc]\nname=pg\nurl=jdbc:postgresql://localhost:5432/ziclix\nuser=bzimmer\npwd=\ndriver=org.postgresql.Driver\ndatahandler=com.ziclix.python.sql.handler.PostgresqlDataHandler\n"));
      var1.setline(44);
      PyString.fromInterned("\nThis script provides platform independence by wrapping Python\nDatabase API 2.0 compatible drivers to allow seamless database\nusage across implementations.\n\nIn order to use the C version, you need mxODBC and mxDateTime.\nIn order to use the Java version, you need zxJDBC.\n\n>>> import dbexts\n>>> d = dbexts.dbexts() # use the default db\n>>> d.isql('select count(*) count from player')\n\ncount\n-------\n13569.0\n\n1 row affected\n\n>>> r = d.raw('select count(*) count from player')\n>>> r\n([('count', 3, 17, None, 15, 0, 1)], [(13569.0,)])\n>>>\n\nThe configuration file follows the following format in a file name dbexts.ini:\n\n[default]\nname=mysql\n\n[jdbc]\nname=mysql\nurl=jdbc:mysql://localhost/ziclix\nuser=\npwd=\ndriver=org.gjt.mm.mysql.Driver\ndatahandler=com.ziclix.python.sql.handler.MySQLDataHandler\n\n[jdbc]\nname=pg\nurl=jdbc:postgresql://localhost:5432/ziclix\nuser=bzimmer\npwd=\ndriver=org.postgresql.Driver\ndatahandler=com.ziclix.python.sql.handler.PostgresqlDataHandler\n");
      var1.setline(46);
      PyObject var3 = imp.importOne("os", var1, -1);
      var1.setlocal("os", var3);
      var3 = null;
      var3 = imp.importOne("re", var1, -1);
      var1.setlocal("re", var3);
      var3 = null;
      var1.setline(47);
      String[] var5 = new String[]{"StringType"};
      PyObject[] var6 = imp.importFrom("types", var5, var1, -1);
      PyObject var4 = var6[0];
      var1.setlocal("StringType", var4);
      var4 = null;
      var1.setline(49);
      PyString var7 = PyString.fromInterned("brian zimmer (bzimmer@ziclix.com)");
      var1.setlocal("__author__", var7);
      var3 = null;
      var1.setline(50);
      var3 = var1.getname("os").__getattr__("name");
      var1.setlocal("__OS__", var3);
      var3 = null;
      var1.setline(52);
      var1.setline(52);
      var6 = Py.EmptyObjects;
      PyFunction var8 = new PyFunction(var1.f_globals, var6, f$1);
      var1.setlocal("choose", var8);
      var3 = null;
      var1.setline(54);
      var6 = new PyObject[]{new PyTuple(Py.EmptyObjects)};
      var8 = new PyFunction(var1.f_globals, var6, console$2, PyString.fromInterned("Format the results into a list       of strings (one for each row):\n\n    <header>\n    <headersep>\n    <row1>\n    <row2>\n    ...\n\n    headers may be given as list of strings.\n\n    Columns are separated by colsep; the header is separated from\n    the result set by a line of headersep characters.\n\n    The function calls stringify to format the value data into a string.\n    It defaults to calling str() and striping leading and trailing whitespace.\n\n    - copied and modified from mxODBC\n    "));
      var1.setlocal("console", var8);
      var3 = null;
      var1.setline(108);
      var6 = new PyObject[]{new PyTuple(Py.EmptyObjects)};
      var8 = new PyFunction(var1.f_globals, var6, html$6, (PyObject)null);
      var1.setlocal("html", var8);
      var3 = null;
      var1.setline(124);
      var1.setline(124);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, f$9);
      var1.setlocal("comments", var8);
      var3 = null;
      var1.setline(126);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("mxODBCProxy", var6, mxODBCProxy$10);
      var1.setlocal("mxODBCProxy", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(146);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("executor", var6, executor$15);
      var1.setlocal("executor", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(163);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, connect$18, (PyObject)null);
      var1.setlocal("connect", var8);
      var3 = null;
      var1.setline(166);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, lookup$19, (PyObject)null);
      var1.setlocal("lookup", var8);
      var3 = null;
      var1.setline(169);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("dbexts", var6, dbexts$20);
      var1.setlocal("dbexts", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(448);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Bulkcopy", var6, Bulkcopy$49);
      var1.setlocal("Bulkcopy", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(525);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Unload", var6, Unload$65);
      var1.setlocal("Unload", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(553);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("Schema", var6, Schema$70);
      var1.setlocal("Schema", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(650);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("IniParser", var6, IniParser$87);
      var1.setlocal("IniParser", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(685);
      var6 = Py.EmptyObjects;
      var8 = new PyFunction(var1.f_globals, var6, random_table_name$94, (PyObject)null);
      var1.setlocal("random_table_name", var8);
      var3 = null;
      var1.setline(694);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("ResultSetRow", var6, ResultSetRow$95);
      var1.setlocal("ResultSetRow", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.setline(711);
      var6 = Py.EmptyObjects;
      var4 = Py.makeClass("ResultSet", var6, ResultSet$101);
      var1.setlocal("ResultSet", var4);
      var4 = null;
      Arrays.fill(var6, (Object)null);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$1(PyFrame var1, ThreadState var2) {
      var1.setline(52);
      Object var10000 = var1.getlocal(0);
      if (((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(new PyObject[]{var1.getlocal(1)});
      }

      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = new PyList(new PyObject[]{var1.getlocal(2)});
      }

      PyObject var3 = ((PyObject)var10000).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject console$2(PyFrame var1, ThreadState var2) {
      var1.setline(72);
      PyString.fromInterned("Format the results into a list       of strings (one for each row):\n\n    <header>\n    <headersep>\n    <row1>\n    <row2>\n    ...\n\n    headers may be given as list of strings.\n\n    Columns are separated by colsep; the header is separated from\n    the result set by a line of headersep characters.\n\n    The function calls stringify to format the value data into a string.\n    It defaults to calling str() and striping leading and trailing whitespace.\n\n    - copied and modified from mxODBC\n    ");
      var1.setline(75);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(76);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(76);
      PyObject[] var8 = Py.EmptyObjects;
      PyFunction var10002 = new PyFunction(var1.f_globals, var8, f$3);
      PyObject var10003 = var1.getglobal("list");
      PyObject var10005 = var1.getglobal("map");
      var1.setline(76);
      var8 = Py.EmptyObjects;
      PyObject var9 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003.__call__(var2, var10005.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var8, f$4)), (PyObject)var1.getlocal(1))));
      var1.setlocal(1, var9);
      var3 = null;
      var1.setline(77);
      var9 = var1.getglobal("map").__call__(var2, var1.getglobal("len"), var1.getlocal(1));
      var1.setlocal(3, var9);
      var3 = null;
      var1.setline(78);
      var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(79);
      var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var9 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var9._gt(Py.newInteger(0));
         var3 = null;
      }

      PyObject var4;
      PyObject[] var5;
      PyObject var6;
      PyObject var10;
      if (var10000.__nonzero__()) {
         var1.setline(80);
         var9 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(80);
            var4 = var9.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(4, var4);
            var1.setline(81);
            var10000 = var1.getglobal("map");
            var1.setline(81);
            var5 = Py.EmptyObjects;
            var10 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$5)), (PyObject)var1.getlocal(4));
            var1.setlocal(4, var10);
            var5 = null;
            var1.setline(82);
            var10 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(4))).__iter__();

            while(true) {
               var1.setline(82);
               var6 = var10.__iternext__();
               if (var6 == null) {
                  var1.setline(86);
                  var1.getlocal(2).__getattr__("append").__call__(var2, var1.getlocal(4));
                  break;
               }

               var1.setlocal(5, var6);
               var1.setline(83);
               PyObject var7 = var1.getlocal(4).__getitem__(var1.getlocal(5));
               var1.setlocal(6, var7);
               var7 = null;
               var1.setline(84);
               var7 = var1.getlocal(3).__getitem__(var1.getlocal(5));
               var10000 = var7._lt(var1.getglobal("len").__call__(var2, var1.getlocal(6)));
               var7 = null;
               if (var10000.__nonzero__()) {
                  var1.setline(85);
                  var7 = var1.getglobal("len").__call__(var2, var1.getlocal(6));
                  var1.getlocal(3).__setitem__(var1.getlocal(5), var7);
                  var7 = null;
               }
            }
         }
      }

      var1.setline(87);
      var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
      var10000 = var9._eq(Py.newInteger(1));
      var3 = null;
      PyString var12;
      if (var10000.__nonzero__()) {
         var1.setline(88);
         var12 = PyString.fromInterned("0 rows affected");
         var1.setlocal(7, var12);
         var3 = null;
      } else {
         var1.setline(89);
         var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
         var10000 = var9._eq(Py.newInteger(2));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(90);
            var12 = PyString.fromInterned("1 row affected");
            var1.setlocal(7, var12);
            var3 = null;
         } else {
            var1.setline(92);
            var9 = PyString.fromInterned("%d rows affected")._mod(var1.getglobal("len").__call__(var2, var1.getlocal(2))._sub(Py.newInteger(1)));
            var1.setlocal(7, var9);
            var3 = null;
         }
      }

      var1.setline(95);
      var9 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(2))).__iter__();

      while(true) {
         var1.setline(95);
         var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(103);
            var9 = var1.getglobal("len").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0)));
            var1.setlocal(10, var9);
            var3 = null;
            var1.setline(104);
            var3 = new PyList(new PyObject[]{PyString.fromInterned("-")._mul(var1.getlocal(10)._div(var1.getglobal("len").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("-"))))});
            var1.getlocal(2).__setslice__(Py.newInteger(1), Py.newInteger(1), (PyObject)null, var3);
            var3 = null;
            var1.setline(105);
            var1.getlocal(2).__getattr__("append").__call__(var2, PyString.fromInterned("\n")._add(var1.getlocal(7)));
            var1.setline(106);
            var9 = var1.getlocal(2);
            var1.f_lasti = -1;
            return var9;
         }

         var1.setlocal(5, var4);
         var1.setline(96);
         var10 = var1.getlocal(2).__getitem__(var1.getlocal(5));
         var1.setlocal(4, var10);
         var5 = null;
         var1.setline(97);
         PyList var11 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var11);
         var5 = null;
         var1.setline(98);
         var10 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(4))).__iter__();

         while(true) {
            var1.setline(98);
            var6 = var10.__iternext__();
            if (var6 == null) {
               var1.setline(100);
               var10 = PyString.fromInterned(" | ").__getattr__("join").__call__(var2, var1.getlocal(8));
               var1.getlocal(2).__setitem__(var1.getlocal(5), var10);
               var5 = null;
               break;
            }

            var1.setlocal(9, var6);
            var1.setline(99);
            var1.getlocal(8).__getattr__("append").__call__(var2, PyString.fromInterned("%-*s")._mod(new PyTuple(new PyObject[]{var1.getlocal(3).__getitem__(var1.getlocal(9)), var1.getlocal(4).__getitem__(var1.getlocal(9))})));
         }
      }
   }

   public PyObject f$3(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      PyObject var3 = var1.getlocal(0).__getattr__("upper").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$4(PyFrame var1, ThreadState var2) {
      var1.setline(76);
      Object var10000 = var1.getlocal(0);
      if (!((PyObject)var10000).__nonzero__()) {
         var10000 = PyString.fromInterned("");
      }

      Object var3 = var10000;
      var1.f_lasti = -1;
      return (PyObject)var3;
   }

   public PyObject f$5(PyFrame var1, ThreadState var2) {
      var1.setline(81);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject html$6(PyFrame var1, ThreadState var2) {
      var1.setline(109);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(110);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<table class=\"results\">"));
      var1.setline(111);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<tr class=\"headers\">"));
      var1.setline(112);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(112);
      PyObject[] var6 = Py.EmptyObjects;
      PyObject var7 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var6, f$7)), (PyObject)var1.getglobal("list").__call__(var2, var1.getlocal(1)));
      var1.setlocal(1, var7);
      var3 = null;
      var1.setline(113);
      var1.getglobal("map").__call__(var2, var1.getlocal(2).__getattr__("append"), var1.getlocal(1));
      var1.setline(114);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</tr>"));
      var1.setline(115);
      var10000 = var1.getlocal(0);
      if (var10000.__nonzero__()) {
         var7 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
         var10000 = var7._gt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(116);
         var7 = var1.getlocal(0).__iter__();

         while(true) {
            var1.setline(116);
            PyObject var4 = var7.__iternext__();
            if (var4 == null) {
               break;
            }

            var1.setlocal(3, var4);
            var1.setline(117);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("<tr class=\"row\">"));
            var1.setline(118);
            var10000 = var1.getglobal("map");
            var1.setline(118);
            PyObject[] var5 = Py.EmptyObjects;
            PyObject var8 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var5, f$8)), (PyObject)var1.getlocal(3));
            var1.setlocal(3, var8);
            var5 = null;
            var1.setline(119);
            var1.getglobal("map").__call__(var2, var1.getlocal(2).__getattr__("append"), var1.getlocal(3));
            var1.setline(120);
            var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</tr>"));
         }
      }

      var1.setline(121);
      var1.getlocal(2).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("</table>"));
      var1.setline(122);
      var7 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var7;
   }

   public PyObject f$7(PyFrame var1, ThreadState var2) {
      var1.setline(112);
      PyObject var3 = PyString.fromInterned("<td class=\"header\">%s</td>")._mod(var1.getlocal(0).__getattr__("upper").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$8(PyFrame var1, ThreadState var2) {
      var1.setline(118);
      PyObject var3 = PyString.fromInterned("<td class=\"value\">%s</td>")._mod(var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$9(PyFrame var1, ThreadState var2) {
      var1.setline(124);
      PyObject var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("{.*?}"), (PyObject)var1.getglobal("re").__getattr__("S")).__getattr__("sub").__call__((ThreadState)var2, PyString.fromInterned(""), (PyObject)var1.getlocal(0), (PyObject)Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject mxODBCProxy$10(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Wraps mxODBC to provide proxy support for zxJDBC's additional parameters."));
      var1.setline(127);
      PyString.fromInterned("Wraps mxODBC to provide proxy support for zxJDBC's additional parameters.");
      var1.setline(128);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$11, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(130);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$12, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(137);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, execute$13, (PyObject)null);
      var1.setlocal("execute", var4);
      var3 = null;
      var1.setline(142);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, gettypeinfo$14, (PyObject)null);
      var1.setlocal("gettypeinfo", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$11(PyFrame var1, ThreadState var2) {
      var1.setline(129);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("c", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getattr__$12(PyFrame var1, ThreadState var2) {
      var1.setline(131);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("execute"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(132);
         var3 = var1.getlocal(0).__getattr__("execute");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(133);
         PyObject var4 = var1.getlocal(1);
         var10000 = var4._eq(PyString.fromInterned("gettypeinfo"));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(134);
            var3 = var1.getlocal(0).__getattr__("gettypeinfo");
            var1.f_lasti = -1;
            return var3;
         } else {
            var1.setline(136);
            var3 = var1.getglobal("getattr").__call__(var2, var1.getlocal(0).__getattr__("c"), var1.getlocal(1));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject execute$13(PyFrame var1, ThreadState var2) {
      var1.setline(138);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(139);
         var1.getlocal(0).__getattr__("c").__getattr__("execute").__call__(var2, var1.getlocal(1), var1.getlocal(2));
      } else {
         var1.setline(141);
         var1.getlocal(0).__getattr__("c").__getattr__("execute").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject gettypeinfo$14(PyFrame var1, ThreadState var2) {
      var1.setline(143);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(144);
         var1.getlocal(0).__getattr__("c").__getattr__("gettypeinfo").__call__(var2, var1.getlocal(1));
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject executor$15(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Handles the insertion of values given dynamic data."));
      var1.setline(147);
      PyString.fromInterned("Handles the insertion of values given dynamic data.");
      var1.setline(148);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$16, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(155);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, execute$17, (PyObject)null);
      var1.setlocal("execute", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$16(PyFrame var1, ThreadState var2) {
      var1.setline(149);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("cols", var3);
      var3 = null;
      var1.setline(150);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("table", var3);
      var3 = null;
      var1.setline(151);
      if (var1.getlocal(0).__getattr__("cols").__nonzero__()) {
         var1.setline(152);
         var3 = PyString.fromInterned("insert into %s (%s) values (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(1), PyString.fromInterned(",").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("cols")), PyString.fromInterned(",").__getattr__("join").__call__(var2, (new PyTuple(new PyObject[]{PyString.fromInterned("?")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("cols"))))}));
         var1.getlocal(0).__setattr__("sql", var3);
         var3 = null;
      } else {
         var1.setline(154);
         var3 = PyString.fromInterned("insert into %s values (%%s)")._mod(var1.getlocal(1));
         var1.getlocal(0).__setattr__("sql", var3);
         var3 = null;
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject execute$17(PyFrame var1, ThreadState var2) {
      var1.setline(156);
      PyObject var3;
      if (var1.getglobal("__debug__").__nonzero__()) {
         PyObject var10000 = var1.getlocal(2);
         if (var10000.__nonzero__()) {
            var3 = var1.getglobal("len").__call__(var2, var1.getlocal(2));
            var10000 = var3._gt(Py.newInteger(0));
            var3 = null;
         }

         if (!var10000.__nonzero__()) {
            throw Py.makeException(var1.getglobal("AssertionError"), PyString.fromInterned("must have at least one row"));
         }
      }

      var1.setline(157);
      if (var1.getlocal(0).__getattr__("cols").__nonzero__()) {
         var1.setline(158);
         var3 = var1.getlocal(0).__getattr__("sql");
         var1.setlocal(4, var3);
         var3 = null;
      } else {
         var1.setline(160);
         var3 = var1.getlocal(0).__getattr__("sql")._mod(PyString.fromInterned(",").__getattr__("join").__call__(var2, (new PyTuple(new PyObject[]{PyString.fromInterned("?")}))._mul(var1.getglobal("len").__call__(var2, var1.getlocal(2).__getitem__(Py.newInteger(0))))));
         var1.setlocal(4, var3);
         var3 = null;
      }

      var1.setline(161);
      var1.getlocal(1).__getattr__("raw").__call__(var2, var1.getlocal(4), var1.getlocal(2), var1.getlocal(3));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject connect$18(PyFrame var1, ThreadState var2) {
      var1.setline(164);
      PyObject var3 = var1.getglobal("dbexts").__call__(var2, var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject lookup$19(PyFrame var1, ThreadState var2) {
      var1.setline(167);
      PyObject var10000 = var1.getglobal("dbexts");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      String[] var4 = new String[]{"jndiname"};
      var10000 = var10000.__call__(var2, var3, var4);
      var3 = null;
      PyObject var5 = var10000;
      var1.f_lasti = -1;
      return var5;
   }

   public PyObject dbexts$20(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(170);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("console"), Py.newInteger(0), var1.getname("None"), var1.getname("None")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$21, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(241);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$22, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(244);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$23, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(247);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$24, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(252);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, close$25, PyString.fromInterned(" close the connection to the database "));
      var1.setlocal("close", var4);
      var3 = null;
      var1.setline(256);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, begin$26, PyString.fromInterned(" reset ivars and return a new cursor, possibly binding an auxiliary datahandler "));
      var1.setlocal("begin", var4);
      var3 = null;
      var1.setline(271);
      var3 = new PyObject[]{var1.getname("None"), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, commit$27, PyString.fromInterned(" commit the cursor and create the result set "));
      var1.setlocal("commit", var4);
      var3 = null;
      var1.setline(290);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rollback$28, PyString.fromInterned(" rollback the cursor "));
      var1.setlocal("rollback", var4);
      var3 = null;
      var1.setline(294);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, prepare$29, PyString.fromInterned(" prepare the sql statement "));
      var1.setlocal("prepare", var4);
      var3 = null;
      var1.setline(302);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, display$30, PyString.fromInterned(" using the formatter, display the results "));
      var1.setlocal("display", var4);
      var3 = null;
      var1.setline(312);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, __execute__$32, PyString.fromInterned(" the primary execution method "));
      var1.setlocal("__execute__", var4);
      var3 = null;
      var1.setline(325);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, isql$33, PyString.fromInterned(" execute and display the sql "));
      var1.setlocal("isql", var4);
      var3 = null;
      var1.setline(330);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("comments"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, raw$34, PyString.fromInterned(" execute the sql and return a tuple of (headers, results) "));
      var1.setlocal("raw", var4);
      var3 = null;
      var1.setline(351);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, callproc$37, PyString.fromInterned(" execute a stored procedure "));
      var1.setlocal("callproc", var4);
      var3 = null;
      var1.setline(360);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, pk$38, PyString.fromInterned(" display the table's primary keys "));
      var1.setlocal("pk", var4);
      var3 = null;
      var1.setline(367);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, fk$39, PyString.fromInterned(" display the table's foreign keys "));
      var1.setlocal("fk", var4);
      var3 = null;
      var1.setline(379);
      var3 = new PyObject[]{var1.getname("None"), new PyTuple(new PyObject[]{PyString.fromInterned("TABLE")}), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, table$40, PyString.fromInterned("If no table argument, displays a list of all tables.  If a table argument,\n        displays the columns of the given table."));
      var1.setlocal("table", var4);
      var3 = null;
      var1.setline(390);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, proc$41, PyString.fromInterned("If no proc argument, displays a list of all procedures.  If a proc argument,\n        displays the parameters of the given procedure."));
      var1.setlocal("proc", var4);
      var3 = null;
      var1.setline(401);
      var3 = new PyObject[]{var1.getname("None"), var1.getname("None"), Py.newInteger(0), Py.newInteger(0)};
      var4 = new PyFunction(var1.f_globals, var3, stat$42, PyString.fromInterned(" display the table's indicies "));
      var1.setlocal("stat", var4);
      var3 = null;
      var1.setline(408);
      var3 = new PyObject[]{var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, typeinfo$43, PyString.fromInterned(" display the types available for the database "));
      var1.setlocal("typeinfo", var4);
      var3 = null;
      var1.setline(415);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, tabletypeinfo$44, PyString.fromInterned(" display the table types available for the database "));
      var1.setlocal("tabletypeinfo", var4);
      var3 = null;
      var1.setline(422);
      var3 = new PyObject[]{Py.newInteger(0), Py.newInteger(1), var1.getname("None")};
      var4 = new PyFunction(var1.f_globals, var3, schema$45, PyString.fromInterned("Displays a Schema object for the table.  If full is true, then generates\n        references to the table in addition to the standard fields.  If sort is true,\n        sort all the items in the schema, else leave them in db dependent order."));
      var1.setlocal("schema", var4);
      var3 = null;
      var1.setline(428);
      var3 = new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(0), var1.getname("executor")};
      var4 = new PyFunction(var1.f_globals, var3, bulkcopy$46, PyString.fromInterned("Returns a Bulkcopy object using the given table."));
      var1.setlocal("bulkcopy", var4);
      var3 = null;
      var1.setline(435);
      var3 = new PyObject[]{PyString.fromInterned("(1=1)"), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(0), var1.getname("executor")};
      var4 = new PyFunction(var1.f_globals, var3, bcp$47, PyString.fromInterned("Bulkcopy of rows from a src database to the current database for a given table and where clause."));
      var1.setlocal("bcp", var4);
      var3 = null;
      var1.setline(443);
      var3 = new PyObject[]{PyString.fromInterned(","), Py.newInteger(1)};
      var4 = new PyFunction(var1.f_globals, var3, unload$48, PyString.fromInterned(" Unloads the delimited results of the query to the file specified, optionally including headers. "));
      var1.setlocal("unload", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$21(PyFrame var1, ThreadState var2) {
      var1.setline(171);
      PyInteger var3 = Py.newInteger(1);
      var1.getlocal(0).__setattr__((String)"verbose", var3);
      var3 = null;
      var1.setline(172);
      PyList var8 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"results", var8);
      var3 = null;
      var1.setline(173);
      var8 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"headers", var8);
      var3 = null;
      var1.setline(174);
      PyObject var11 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("autocommit", var11);
      var3 = null;
      var1.setline(175);
      var11 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("formatter", var11);
      var3 = null;
      var1.setline(176);
      var11 = var1.getlocal(6);
      var1.getlocal(0).__setattr__("out", var11);
      var3 = null;
      var1.setline(177);
      var11 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("lastrowid", var11);
      var3 = null;
      var1.setline(178);
      var11 = var1.getglobal("None");
      var1.getlocal(0).__setattr__("updatecount", var11);
      var3 = null;
      var1.setline(180);
      PyObject var10000;
      if (var1.getlocal(5).__not__().__nonzero__()) {
         var1.setline(181);
         var11 = var1.getlocal(2);
         var10000 = var11._eq(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(182);
            var11 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("path").__getattr__("split").__call__(var2, var1.getglobal("__file__")).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("dbexts.ini"));
            var1.setlocal(7, var11);
            var3 = null;
            var1.setline(183);
            if (var1.getglobal("os").__getattr__("path").__getattr__("exists").__call__(var2, var1.getlocal(7)).__not__().__nonzero__()) {
               var1.setline(184);
               var11 = var1.getglobal("os").__getattr__("path").__getattr__("join").__call__((ThreadState)var2, (PyObject)var1.getglobal("os").__getattr__("environ").__getitem__(PyString.fromInterned("HOME")), (PyObject)PyString.fromInterned(".dbexts"));
               var1.setlocal(7, var11);
               var3 = null;
            }

            var1.setline(185);
            var11 = var1.getglobal("IniParser").__call__(var2, var1.getlocal(7));
            var1.getlocal(0).__setattr__("dbs", var11);
            var3 = null;
         } else {
            var1.setline(186);
            if (var1.getglobal("isinstance").__call__(var2, var1.getlocal(2), var1.getglobal("IniParser")).__nonzero__()) {
               var1.setline(187);
               var11 = var1.getlocal(2);
               var1.getlocal(0).__setattr__("dbs", var11);
               var3 = null;
            } else {
               var1.setline(189);
               var11 = var1.getglobal("IniParser").__call__(var2, var1.getlocal(2));
               var1.getlocal(0).__setattr__("dbs", var11);
               var3 = null;
            }
         }

         var1.setline(190);
         var11 = var1.getlocal(1);
         var10000 = var11._eq(var1.getglobal("None"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(190);
            var11 = var1.getlocal(0).__getattr__("dbs").__getitem__(new PyTuple(new PyObject[]{PyString.fromInterned("default"), PyString.fromInterned("name")}));
            var1.setlocal(1, var11);
            var3 = null;
         }
      }

      var1.setline(192);
      var11 = var1.getglobal("__OS__");
      var10000 = var11._eq(PyString.fromInterned("java"));
      var3 = null;
      PyObject var4;
      PyObject var5;
      PyObject[] var9;
      PyTuple var12;
      PyObject[] var13;
      if (var10000.__nonzero__()) {
         var1.setline(194);
         String[] var14 = new String[]{"zxJDBC"};
         var13 = imp.importFrom("com.ziclix.python.sql", var14, var1, -1);
         var4 = var13[0];
         var1.setlocal(8, var4);
         var4 = null;
         var1.setline(195);
         var11 = var1.getlocal(8);
         var1.setlocal(9, var11);
         var3 = null;
         var1.setline(196);
         if (!var1.getlocal(5).__not__().__nonzero__()) {
            var1.setline(211);
            var11 = var1.getlocal(9).__getattr__("lookup").__call__(var2, var1.getlocal(5));
            var1.getlocal(0).__setattr__("db", var11);
            var3 = null;
         } else {
            var1.setline(197);
            var11 = var1.getlocal(0).__getattr__("dbs").__getitem__(new PyTuple(new PyObject[]{PyString.fromInterned("jdbc"), var1.getlocal(1)}));
            var1.setlocal(10, var11);
            var3 = null;
            var1.setline(198);
            var12 = new PyTuple(new PyObject[]{var1.getlocal(10).__getitem__(PyString.fromInterned("url")), var1.getlocal(10).__getitem__(PyString.fromInterned("user")), var1.getlocal(10).__getitem__(PyString.fromInterned("pwd")), var1.getlocal(10).__getitem__(PyString.fromInterned("driver"))});
            var9 = Py.unpackSequence(var12, 4);
            var5 = var9[0];
            var1.getlocal(0).__setattr__("dburl", var5);
            var5 = null;
            var5 = var9[1];
            var1.setlocal(11, var5);
            var5 = null;
            var5 = var9[2];
            var1.setlocal(12, var5);
            var5 = null;
            var5 = var9[3];
            var1.setlocal(13, var5);
            var5 = null;
            var3 = null;
            var1.setline(199);
            if (var1.getlocal(10).__getattr__("has_key").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("datahandler")).__nonzero__()) {
               var1.setline(200);
               var8 = new PyList(Py.EmptyObjects);
               var1.getlocal(0).__setattr__((String)"datahandler", var8);
               var3 = null;
               var1.setline(201);
               var11 = var1.getlocal(10).__getitem__(PyString.fromInterned("datahandler")).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(",")).__iter__();

               while(true) {
                  var1.setline(201);
                  var4 = var11.__iternext__();
                  if (var4 == null) {
                     break;
                  }

                  var1.setlocal(14, var4);
                  var1.setline(202);
                  var5 = var1.getlocal(14).__getattr__("split").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(".")).__getitem__(Py.newInteger(-1));
                  var1.setlocal(15, var5);
                  var5 = null;
                  var1.setline(203);
                  var5 = var1.getglobal("__import__").__call__(var2, var1.getlocal(14), var1.getglobal("globals").__call__(var2), var1.getglobal("locals").__call__(var2), var1.getlocal(15));
                  var1.setlocal(16, var5);
                  var5 = null;
                  var1.setline(204);
                  var1.getlocal(0).__getattr__("datahandler").__getattr__("append").__call__(var2, var1.getlocal(16));
               }
            }

            var1.setline(205);
            PyList var16 = new PyList();
            var11 = var16.__getattr__("append");
            var1.setlocal(18, var11);
            var3 = null;
            var1.setline(205);
            var11 = var1.getlocal(10).__getattr__("keys").__call__(var2).__iter__();

            label94:
            while(true) {
               var1.setline(205);
               var4 = var11.__iternext__();
               if (var4 == null) {
                  var1.setline(205);
                  var1.dellocal(18);
                  var8 = var16;
                  var1.setlocal(17, var8);
                  var3 = null;
                  var1.setline(206);
                  PyDictionary var15 = new PyDictionary(Py.EmptyObjects);
                  var1.setlocal(20, var15);
                  var3 = null;
                  var1.setline(207);
                  var11 = var1.getlocal(17).__iter__();

                  while(true) {
                     var1.setline(207);
                     var4 = var11.__iternext__();
                     if (var4 == null) {
                        var1.setline(209);
                        var11 = var1.getglobal("apply").__call__((ThreadState)var2, var1.getlocal(9).__getattr__("connect"), (PyObject)(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("dburl"), var1.getlocal(11), var1.getlocal(12), var1.getlocal(13)})), (PyObject)var1.getlocal(20));
                        var1.getlocal(0).__setattr__("db", var11);
                        var3 = null;
                        break label94;
                     }

                     var1.setlocal(21, var4);
                     var1.setline(208);
                     var5 = var1.getlocal(10).__getitem__(var1.getlocal(21));
                     var1.getlocal(20).__setitem__(var1.getlocal(21), var5);
                     var5 = null;
                  }
               }

               var1.setlocal(19, var4);
               var1.setline(205);
               var5 = var1.getlocal(19);
               PyObject var10001 = var5._notin(new PyList(new PyObject[]{PyString.fromInterned("url"), PyString.fromInterned("user"), PyString.fromInterned("pwd"), PyString.fromInterned("driver"), PyString.fromInterned("datahandler"), PyString.fromInterned("name")}));
               var5 = null;
               if (var10001.__nonzero__()) {
                  var1.setline(205);
                  var1.getlocal(18).__call__(var2, var1.getlocal(19));
               }
            }
         }

         var1.setline(212);
         var11 = var1.getlocal(0).__getattr__("autocommit");
         var1.getlocal(0).__getattr__("db").__setattr__("autocommit", var11);
         var3 = null;
      } else {
         var1.setline(214);
         var11 = var1.getglobal("__OS__");
         var10000 = var11._eq(PyString.fromInterned("nt"));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(216);
            var11 = (new PyList(new PyObject[]{PyString.fromInterned("mx.ODBC.Windows"), PyString.fromInterned("ODBC.Windows")})).__iter__();

            while(true) {
               var1.setline(216);
               var4 = var11.__iternext__();
               if (var4 == null) {
                  var1.setline(223);
                  throw Py.makeException(var1.getglobal("ImportError").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("unable to find appropriate mxODBC module")));
               }

               var1.setlocal(22, var4);

               try {
                  var1.setline(218);
                  var5 = var1.getglobal("__import__").__call__(var2, var1.getlocal(22), var1.getglobal("globals").__call__(var2), var1.getglobal("locals").__call__(var2), PyString.fromInterned("Windows"));
                  var1.setlocal(9, var5);
                  var5 = null;
               } catch (Throwable var7) {
                  Py.setException(var7, var1);
                  continue;
               }

               var1.setline(225);
               var11 = var1.getlocal(0).__getattr__("dbs").__getitem__(new PyTuple(new PyObject[]{PyString.fromInterned("odbc"), var1.getlocal(1)}));
               var1.setlocal(10, var11);
               var3 = null;
               var1.setline(226);
               var12 = new PyTuple(new PyObject[]{var1.getlocal(10).__getitem__(PyString.fromInterned("url")), var1.getlocal(10).__getitem__(PyString.fromInterned("user")), var1.getlocal(10).__getitem__(PyString.fromInterned("pwd"))});
               var9 = Py.unpackSequence(var12, 3);
               var5 = var9[0];
               var1.getlocal(0).__setattr__("dburl", var5);
               var5 = null;
               var5 = var9[1];
               var1.setlocal(11, var5);
               var5 = null;
               var5 = var9[2];
               var1.setlocal(12, var5);
               var5 = null;
               var3 = null;
               var1.setline(227);
               var10000 = var1.getlocal(9).__getattr__("Connect");
               var13 = new PyObject[]{var1.getlocal(0).__getattr__("dburl"), var1.getlocal(11), var1.getlocal(12), Py.newInteger(1)};
               String[] var10 = new String[]{"clear_auto_commit"};
               var10000 = var10000.__call__(var2, var13, var10);
               var3 = null;
               var11 = var10000;
               var1.getlocal(0).__setattr__("db", var11);
               var3 = null;
               break;
            }
         }
      }

      var1.setline(229);
      var11 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("dbname", var11);
      var3 = null;
      var1.setline(230);
      var11 = var1.getlocal(9).__getattr__("sqltype").__getattr__("keys").__call__(var2).__iter__();

      while(true) {
         var1.setline(230);
         var4 = var11.__iternext__();
         if (var4 == null) {
            var1.setline(232);
            var11 = var1.getglobal("dir").__call__(var2, var1.getlocal(9)).__iter__();

            while(true) {
               var1.setline(232);
               var4 = var11.__iternext__();
               if (var4 == null) {
                  var1.setline(239);
                  var1.dellocal(9);
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal(21, var4);

               try {
                  var1.setline(234);
                  var5 = var1.getglobal("getattr").__call__(var2, var1.getlocal(9), var1.getlocal(21));
                  var1.setlocal(23, var5);
                  var5 = null;
                  var1.setline(235);
                  if (var1.getglobal("issubclass").__call__(var2, var1.getlocal(23), var1.getglobal("Exception")).__nonzero__()) {
                     var1.setline(236);
                     var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(21), var1.getlocal(23));
                  }
               } catch (Throwable var6) {
                  Py.setException(var6, var1);
               }
            }
         }

         var1.setlocal(21, var4);
         var1.setline(231);
         var1.getglobal("setattr").__call__(var2, var1.getlocal(0), var1.getlocal(9).__getattr__("sqltype").__getitem__(var1.getlocal(21)), var1.getlocal(21));
      }
   }

   public PyObject __str__$22(PyFrame var1, ThreadState var2) {
      var1.setline(242);
      PyObject var3 = var1.getlocal(0).__getattr__("dburl");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$23(PyFrame var1, ThreadState var2) {
      var1.setline(245);
      PyObject var3 = var1.getlocal(0).__getattr__("dburl");
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getattr__$24(PyFrame var1, ThreadState var2) {
      var1.setline(248);
      PyString var3 = PyString.fromInterned("cfg");
      PyObject var10000 = var3._eq(var1.getlocal(1));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(249);
         PyObject var4 = var1.getlocal(0).__getattr__("dbs").__getattr__("cfg");
         var1.f_lasti = -1;
         return var4;
      } else {
         var1.setline(250);
         throw Py.makeException(var1.getglobal("AttributeError").__call__(var2, PyString.fromInterned("'dbexts' object has no attribute '%s'")._mod(var1.getlocal(1))));
      }
   }

   public PyObject close$25(PyFrame var1, ThreadState var2) {
      var1.setline(253);
      PyString.fromInterned(" close the connection to the database ");
      var1.setline(254);
      var1.getlocal(0).__getattr__("db").__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject begin$26(PyFrame var1, ThreadState var2) {
      var1.setline(257);
      PyString.fromInterned(" reset ivars and return a new cursor, possibly binding an auxiliary datahandler ");
      var1.setline(258);
      PyTuple var3 = new PyTuple(new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects)});
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.getlocal(0).__setattr__("headers", var5);
      var5 = null;
      var5 = var4[1];
      var1.getlocal(0).__setattr__("results", var5);
      var5 = null;
      var3 = null;
      var1.setline(259);
      PyObject var6;
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(260);
         var6 = var1.getlocal(0).__getattr__("db").__getattr__("cursor").__call__(var2, var1.getlocal(1));
         var1.setlocal(2, var6);
         var3 = null;
      } else {
         var1.setline(262);
         var6 = var1.getlocal(0).__getattr__("db").__getattr__("cursor").__call__(var2);
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(263);
      var6 = var1.getglobal("__OS__");
      PyObject var10000 = var6._eq(PyString.fromInterned("java"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(264);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(0), (PyObject)PyString.fromInterned("datahandler")).__nonzero__()) {
            var1.setline(265);
            var6 = var1.getlocal(0).__getattr__("datahandler").__iter__();

            while(true) {
               var1.setline(265);
               PyObject var7 = var6.__iternext__();
               if (var7 == null) {
                  break;
               }

               var1.setlocal(3, var7);
               var1.setline(266);
               var5 = var1.getlocal(3).__call__(var2, var1.getlocal(2).__getattr__("datahandler"));
               var1.getlocal(2).__setattr__("datahandler", var5);
               var5 = null;
            }
         }
      } else {
         var1.setline(268);
         var6 = var1.getglobal("mxODBCProxy").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var6);
         var3 = null;
      }

      var1.setline(269);
      var6 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var6;
   }

   public PyObject commit$27(PyFrame var1, ThreadState var2) {
      var1.setline(272);
      PyString.fromInterned(" commit the cursor and create the result set ");
      var1.setline(273);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(1).__getattr__("description");
      }

      PyObject var3;
      if (var10000.__nonzero__()) {
         var1.setline(274);
         var3 = var1.getlocal(1).__getattr__("description");
         var1.getlocal(0).__setattr__("headers", var3);
         var3 = null;
         var1.setline(275);
         var3 = var1.getlocal(1).__getattr__("fetchall").__call__(var2);
         var1.getlocal(0).__setattr__("results", var3);
         var3 = null;
         var1.setline(276);
         if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("nextset")).__nonzero__()) {
            var1.setline(277);
            var3 = var1.getlocal(1).__getattr__("nextset").__call__(var2);
            var1.setlocal(3, var3);
            var3 = null;

            while(true) {
               var1.setline(278);
               if (!var1.getlocal(3).__nonzero__()) {
                  break;
               }

               var1.setline(279);
               var10000 = var1.getlocal(0);
               String var6 = "results";
               PyObject var4 = var10000;
               PyObject var5 = var4.__getattr__(var6);
               var5 = var5._iadd(var1.getlocal(1).__getattr__("fetchall").__call__(var2));
               var4.__setattr__(var6, var5);
               var1.setline(280);
               var3 = var1.getlocal(1).__getattr__("nextset").__call__(var2);
               var1.setlocal(3, var3);
               var3 = null;
            }
         }
      }

      var1.setline(281);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("lastrowid")).__nonzero__()) {
         var1.setline(282);
         var3 = var1.getlocal(1).__getattr__("lastrowid");
         var1.getlocal(0).__setattr__("lastrowid", var3);
         var3 = null;
      }

      var1.setline(283);
      if (var1.getglobal("hasattr").__call__((ThreadState)var2, (PyObject)var1.getlocal(1), (PyObject)PyString.fromInterned("updatecount")).__nonzero__()) {
         var1.setline(284);
         var3 = var1.getlocal(1).__getattr__("updatecount");
         var1.getlocal(0).__setattr__("updatecount", var3);
         var3 = null;
      }

      var1.setline(285);
      var10000 = var1.getlocal(0).__getattr__("autocommit").__not__();
      if (!var10000.__nonzero__()) {
         var3 = var1.getlocal(1);
         var10000 = var3._is(var1.getglobal("None"));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(286);
         if (var1.getlocal(0).__getattr__("db").__getattr__("autocommit").__not__().__nonzero__()) {
            var1.setline(287);
            var1.getlocal(0).__getattr__("db").__getattr__("commit").__call__(var2);
         }
      }

      var1.setline(288);
      var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
      }

      if (var10000.__nonzero__()) {
         var1.setline(288);
         var1.getlocal(1).__getattr__("close").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject rollback$28(PyFrame var1, ThreadState var2) {
      var1.setline(291);
      PyString.fromInterned(" rollback the cursor ");
      var1.setline(292);
      var1.getlocal(0).__getattr__("db").__getattr__("rollback").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject prepare$29(PyFrame var1, ThreadState var2) {
      var1.setline(295);
      PyString.fromInterned(" prepare the sql statement ");
      var1.setline(296);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var3 = null;

      Throwable var10000;
      label25: {
         boolean var10001;
         PyObject var4;
         try {
            var1.setline(298);
            var4 = var1.getlocal(2).__getattr__("prepare").__call__(var2, var1.getlocal(1));
         } catch (Throwable var6) {
            var10000 = var6;
            var10001 = false;
            break label25;
         }

         var1.setline(300);
         var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(2));

         try {
            var1.f_lasti = -1;
            return var4;
         } catch (Throwable var5) {
            var10000 = var5;
            var10001 = false;
         }
      }

      Throwable var7 = var10000;
      Py.addTraceback(var7, var1);
      var1.setline(300);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(2));
      throw (Throwable)var7;
   }

   public PyObject display$30(PyFrame var1, ThreadState var2) {
      var1.setline(303);
      PyString.fromInterned(" using the formatter, display the results ");
      var1.setline(304);
      PyObject var10000 = var1.getlocal(0).__getattr__("formatter");
      PyObject var3;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getattr__("verbose");
         var10000 = var3._gt(Py.newInteger(0));
         var3 = null;
      }

      if (var10000.__nonzero__()) {
         var1.setline(305);
         var3 = var1.getlocal(0).__getattr__("results");
         var1.setlocal(1, var3);
         var3 = null;
         var1.setline(306);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(307);
            var3 = var1.getlocal(0).__getattr__("out");
            Py.println(var3, PyString.fromInterned(""));
            var1.setline(308);
            var10000 = var1.getlocal(0).__getattr__("formatter");
            PyObject var10002 = var1.getlocal(1);
            PyObject var10003 = var1.getglobal("map");
            var1.setline(308);
            PyObject[] var6 = Py.EmptyObjects;
            var3 = var10000.__call__(var2, var10002, var10003.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var6, f$31)), (PyObject)var1.getlocal(0).__getattr__("headers"))).__iter__();

            while(true) {
               var1.setline(308);
               PyObject var4 = var3.__iternext__();
               if (var4 == null) {
                  var1.setline(310);
                  var3 = var1.getlocal(0).__getattr__("out");
                  Py.println(var3, PyString.fromInterned(""));
                  break;
               }

               var1.setlocal(2, var4);
               var1.setline(309);
               PyObject var5 = var1.getlocal(0).__getattr__("out");
               Py.println(var5, var1.getlocal(2));
            }
         }
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$31(PyFrame var1, ThreadState var2) {
      var1.setline(308);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __execute__$32(PyFrame var1, ThreadState var2) {
      var1.setline(313);
      PyString.fromInterned(" the primary execution method ");
      var1.setline(314);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var3 = null;

      PyObject var10000;
      PyObject[] var4;
      String[] var5;
      try {
         var1.setline(316);
         if (var1.getlocal(3).__nonzero__()) {
            var1.setline(317);
            var10000 = var1.getlocal(5).__getattr__("execute");
            var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
            var5 = new String[]{"maxrows"};
            var10000.__call__(var2, var4, var5);
            var4 = null;
         } else {
            var1.setline(318);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(319);
               var10000 = var1.getlocal(5).__getattr__("execute");
               var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(4)};
               var5 = new String[]{"maxrows"};
               var10000.__call__(var2, var4, var5);
               var4 = null;
            } else {
               var1.setline(321);
               var10000 = var1.getlocal(5).__getattr__("execute");
               var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(4)};
               var5 = new String[]{"maxrows"};
               var10000.__call__(var2, var4, var5);
               var4 = null;
            }
         }
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(323);
         var10000 = var1.getlocal(0).__getattr__("commit");
         var4 = new PyObject[]{var1.getlocal(5), var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringType"))};
         var5 = new String[]{"close"};
         var10000.__call__(var2, var4, var5);
         var4 = null;
         throw (Throwable)var6;
      }

      var1.setline(323);
      var10000 = var1.getlocal(0).__getattr__("commit");
      var4 = new PyObject[]{var1.getlocal(5), var1.getglobal("isinstance").__call__(var2, var1.getlocal(1), var1.getglobal("StringType"))};
      var5 = new String[]{"close"};
      var10000.__call__(var2, var4, var5);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject isql$33(PyFrame var1, ThreadState var2) {
      var1.setline(326);
      PyString.fromInterned(" execute and display the sql ");
      var1.setline(327);
      PyObject var10000 = var1.getlocal(0).__getattr__("raw");
      PyObject[] var3 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
      String[] var4 = new String[]{"maxrows"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(328);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject raw$34(PyFrame var1, ThreadState var2) {
      var1.setline(331);
      PyString.fromInterned(" execute the sql and return a tuple of (headers, results) ");
      var1.setline(332);
      PyList var3;
      PyObject[] var9;
      PyObject var10000;
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(333);
         var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(7, var3);
         var3 = null;
         var1.setline(334);
         var3 = new PyList(Py.EmptyObjects);
         var1.setlocal(8, var3);
         var3 = null;
         var1.setline(335);
         PyObject var8 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
         var10000 = var8._eq(var1.getglobal("type").__call__(var2, var1.getglobal("StringType")));
         var3 = null;
         if (var10000.__nonzero__()) {
            var1.setline(336);
            if (var1.getlocal(5).__nonzero__()) {
               var1.setline(336);
               var8 = var1.getlocal(5).__call__(var2, var1.getlocal(1));
               var1.setlocal(1, var8);
               var3 = null;
            }

            var1.setline(337);
            var10000 = var1.getglobal("filter");
            var1.setline(337);
            var9 = Py.EmptyObjects;
            PyFunction var10002 = new PyFunction(var1.f_globals, var9, f$35);
            PyObject var10003 = var1.getglobal("map");
            var1.setline(338);
            var9 = Py.EmptyObjects;
            var8 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var9, f$36)), (PyObject)var1.getlocal(1).__getattr__("split").__call__(var2, var1.getlocal(4))));
            var1.setlocal(9, var8);
            var3 = null;
         } else {
            var1.setline(340);
            var3 = new PyList(new PyObject[]{var1.getlocal(1)});
            var1.setlocal(9, var3);
            var3 = null;
         }

         var1.setline(341);
         var8 = var1.getlocal(9).__iter__();

         while(true) {
            var1.setline(341);
            PyObject var4 = var8.__iternext__();
            if (var4 == null) {
               var1.setline(345);
               var8 = var1.getlocal(7);
               var1.getlocal(0).__setattr__("headers", var8);
               var3 = null;
               var1.setline(346);
               var8 = var1.getlocal(8);
               var1.getlocal(0).__setattr__("results", var8);
               var3 = null;
               break;
            }

            var1.setlocal(10, var4);
            var1.setline(342);
            var10000 = var1.getlocal(0).__getattr__("__execute__");
            PyObject[] var5 = new PyObject[]{var1.getlocal(10), var1.getlocal(2), var1.getlocal(3), var1.getlocal(6)};
            String[] var6 = new String[]{"maxrows"};
            var10000.__call__(var2, var5, var6);
            var5 = null;
            var1.setline(343);
            var1.getlocal(7).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("headers"));
            var1.setline(344);
            var1.getlocal(8).__getattr__("append").__call__(var2, var1.getlocal(0).__getattr__("results"));
         }
      } else {
         var1.setline(348);
         var10000 = var1.getlocal(0).__getattr__("__execute__");
         var9 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(6)};
         String[] var7 = new String[]{"maxrows"};
         var10000.__call__(var2, var9, var7);
         var3 = null;
      }

      var1.setline(349);
      PyTuple var10 = new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("headers"), var1.getlocal(0).__getattr__("results")});
      var1.f_lasti = -1;
      return var10;
   }

   public PyObject f$35(PyFrame var1, ThreadState var2) {
      var1.setline(337);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$36(PyFrame var1, ThreadState var2) {
      var1.setline(338);
      PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject callproc$37(PyFrame var1, ThreadState var2) {
      var1.setline(352);
      PyString.fromInterned(" execute a stored procedure ");
      var1.setline(353);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var3 = null;

      try {
         var1.setline(355);
         PyObject var10000 = var1.getlocal(5).__getattr__("callproc");
         PyObject[] var4 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4)};
         String[] var5 = new String[]{"params", "bindings", "maxrows"};
         var10000.__call__(var2, var4, var5);
         var4 = null;
      } catch (Throwable var6) {
         Py.addTraceback(var6, var1);
         var1.setline(357);
         var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(5));
         throw (Throwable)var6;
      }

      var1.setline(357);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(5));
      var1.setline(358);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject pk$38(PyFrame var1, ThreadState var2) {
      var1.setline(361);
      PyString.fromInterned(" display the table's primary keys ");
      var1.setline(362);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(363);
      var1.getlocal(4).__getattr__("primarykeys").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(1));
      var1.setline(364);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(4));
      var1.setline(365);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject fk$39(PyFrame var1, ThreadState var2) {
      var1.setline(368);
      PyString.fromInterned(" display the table's foreign keys ");
      var1.setline(369);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(370);
      PyObject var10000 = var1.getlocal(1);
      if (var10000.__nonzero__()) {
         var10000 = var1.getlocal(2);
      }

      PyObject[] var4;
      if (var10000.__nonzero__()) {
         var1.setline(371);
         var10000 = var1.getlocal(5).__getattr__("foreignkeys");
         var4 = new PyObject[]{var1.getlocal(4), var1.getlocal(3), var1.getlocal(1), var1.getlocal(4), var1.getlocal(3), var1.getlocal(2)};
         var10000.__call__(var2, var4);
      } else {
         var1.setline(372);
         if (var1.getlocal(1).__nonzero__()) {
            var1.setline(373);
            var10000 = var1.getlocal(5).__getattr__("foreignkeys");
            var4 = new PyObject[]{var1.getlocal(4), var1.getlocal(3), var1.getlocal(1), var1.getlocal(4), var1.getlocal(3), var1.getglobal("None")};
            var10000.__call__(var2, var4);
         } else {
            var1.setline(374);
            if (var1.getlocal(2).__nonzero__()) {
               var1.setline(375);
               var10000 = var1.getlocal(5).__getattr__("foreignkeys");
               var4 = new PyObject[]{var1.getlocal(4), var1.getlocal(3), var1.getglobal("None"), var1.getlocal(4), var1.getlocal(3), var1.getlocal(2)};
               var10000.__call__(var2, var4);
            }
         }
      }

      var1.setline(376);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(5));
      var1.setline(377);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject table$40(PyFrame var1, ThreadState var2) {
      var1.setline(381);
      PyString.fromInterned("If no table argument, displays a list of all tables.  If a table argument,\n        displays the columns of the given table.");
      var1.setline(382);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(383);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(384);
         var1.getlocal(5).__getattr__("columns").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getlocal(1), var1.getglobal("None"));
      } else {
         var1.setline(386);
         var1.getlocal(5).__getattr__("tables").__call__(var2, var1.getlocal(4), var1.getlocal(3), var1.getglobal("None"), var1.getlocal(2));
      }

      var1.setline(387);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(5));
      var1.setline(388);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject proc$41(PyFrame var1, ThreadState var2) {
      var1.setline(392);
      PyString.fromInterned("If no proc argument, displays a list of all procedures.  If a proc argument,\n        displays the parameters of the given procedure.");
      var1.setline(393);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(394);
      if (var1.getlocal(1).__nonzero__()) {
         var1.setline(395);
         var1.getlocal(4).__getattr__("procedurecolumns").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getlocal(1), var1.getglobal("None"));
      } else {
         var1.setline(397);
         var1.getlocal(4).__getattr__("procedures").__call__(var2, var1.getlocal(3), var1.getlocal(2), var1.getglobal("None"));
      }

      var1.setline(398);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(4));
      var1.setline(399);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject stat$42(PyFrame var1, ThreadState var2) {
      var1.setline(402);
      PyString.fromInterned(" display the table's indicies ");
      var1.setline(403);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(404);
      PyObject var10000 = var1.getlocal(6).__getattr__("statistics");
      PyObject[] var4 = new PyObject[]{var1.getlocal(2), var1.getlocal(3), var1.getlocal(1), var1.getlocal(4), var1.getlocal(5)};
      var10000.__call__(var2, var4);
      var1.setline(405);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(6));
      var1.setline(406);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject typeinfo$43(PyFrame var1, ThreadState var2) {
      var1.setline(409);
      PyString.fromInterned(" display the types available for the database ");
      var1.setline(410);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(411);
      var1.getlocal(2).__getattr__("gettypeinfo").__call__(var2, var1.getlocal(1));
      var1.setline(412);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(2));
      var1.setline(413);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject tabletypeinfo$44(PyFrame var1, ThreadState var2) {
      var1.setline(416);
      PyString.fromInterned(" display the table types available for the database ");
      var1.setline(417);
      PyObject var3 = var1.getlocal(0).__getattr__("begin").__call__(var2);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(418);
      var1.getlocal(1).__getattr__("gettabletypeinfo").__call__(var2);
      var1.setline(419);
      var1.getlocal(0).__getattr__("commit").__call__(var2, var1.getlocal(1));
      var1.setline(420);
      var1.getlocal(0).__getattr__("display").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject schema$45(PyFrame var1, ThreadState var2) {
      var1.setline(425);
      PyString.fromInterned("Displays a Schema object for the table.  If full is true, then generates\n        references to the table in addition to the standard fields.  If sort is true,\n        sort all the items in the schema, else leave them in db dependent order.");
      var1.setline(426);
      PyObject var3 = var1.getlocal(0).__getattr__("out");
      PyObject var10001 = var1.getglobal("str");
      PyObject var10003 = var1.getglobal("Schema");
      PyObject[] var4 = new PyObject[]{var1.getlocal(0), var1.getlocal(1), var1.getlocal(4), var1.getlocal(2), var1.getlocal(3)};
      Py.println(var3, var10001.__call__(var2, var10003.__call__(var2, var4)));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject bulkcopy$46(PyFrame var1, ThreadState var2) {
      var1.setline(429);
      PyString.fromInterned("Returns a Bulkcopy object using the given table.");
      var1.setline(430);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      String[] var4;
      PyObject[] var5;
      if (var10000.__nonzero__()) {
         var1.setline(431);
         var10000 = var1.getglobal("dbexts");
         var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("dbs")};
         var4 = new String[]{"cfg"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(432);
      var10000 = var1.getglobal("Bulkcopy");
      var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(2), var1.getlocal(3), var1.getlocal(4), var1.getlocal(5), var1.getlocal(6)};
      var4 = new String[]{"include", "exclude", "autobatch", "executor"};
      var10000 = var10000.__call__(var2, var5, var4);
      var3 = null;
      var3 = var10000;
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(433);
      var3 = var1.getlocal(7);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject bcp$47(PyFrame var1, ThreadState var2) {
      var1.setline(436);
      PyString.fromInterned("Bulkcopy of rows from a src database to the current database for a given table and where clause.");
      var1.setline(437);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      PyObject[] var5;
      if (var10000.__nonzero__()) {
         var1.setline(438);
         var10000 = var1.getglobal("dbexts");
         var5 = new PyObject[]{var1.getlocal(1), var1.getlocal(0).__getattr__("dbs")};
         String[] var4 = new String[]{"cfg"};
         var10000 = var10000.__call__(var2, var5, var4);
         var3 = null;
         var3 = var10000;
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(439);
      var10000 = var1.getlocal(0).__getattr__("bulkcopy");
      var5 = new PyObject[]{var1.getlocal(0), var1.getlocal(2), var1.getlocal(5), var1.getlocal(6), var1.getlocal(7), var1.getlocal(8)};
      var3 = var10000.__call__(var2, var5);
      var1.setlocal(9, var3);
      var3 = null;
      var1.setline(440);
      var3 = var1.getlocal(9).__getattr__("transfer").__call__(var2, var1.getlocal(1), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(10, var3);
      var3 = null;
      var1.setline(441);
      var3 = var1.getlocal(10);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject unload$48(PyFrame var1, ThreadState var2) {
      var1.setline(444);
      PyString.fromInterned(" Unloads the delimited results of the query to the file specified, optionally including headers. ");
      var1.setline(445);
      PyObject var3 = var1.getglobal("Unload").__call__(var2, var1.getlocal(0), var1.getlocal(1), var1.getlocal(3), var1.getlocal(4));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(446);
      var1.getlocal(5).__getattr__("unload").__call__(var2, var1.getlocal(2));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject Bulkcopy$49(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("The idea for a bcp class came from http://object-craft.com.au/projects/sybase"));
      var1.setline(449);
      PyString.fromInterned("The idea for a bcp class came from http://object-craft.com.au/projects/sybase");
      var1.setline(450);
      PyObject[] var3 = new PyObject[]{new PyList(Py.EmptyObjects), new PyList(Py.EmptyObjects), Py.newInteger(0), var1.getname("executor")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$50, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(480);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$53, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      var1.setline(483);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$54, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      var1.setline(486);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getattr__$55, (PyObject)null);
      var1.setlocal("__getattr__", var4);
      var3 = null;
      var1.setline(490);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __filter__$56, (PyObject)null);
      var1.setlocal("__filter__", var4);
      var3 = null;
      var1.setline(498);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format$60, (PyObject)null);
      var1.setlocal("format", var4);
      var3 = null;
      var1.setline(501);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, done$61, (PyObject)null);
      var1.setlocal("done", var4);
      var3 = null;
      var1.setline(506);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, batch$62, (PyObject)null);
      var1.setlocal("batch", var4);
      var3 = null;
      var1.setline(513);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, rowxfer$63, (PyObject)null);
      var1.setlocal("rowxfer", var4);
      var3 = null;
      var1.setline(517);
      var3 = new PyObject[]{PyString.fromInterned("(1=1)"), new PyList(Py.EmptyObjects)};
      var4 = new PyFunction(var1.f_globals, var3, transfer$64, (PyObject)null);
      var1.setlocal("transfer", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$50(PyFrame var1, ThreadState var2) {
      var1.setline(451);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("dst", var3);
      var3 = null;
      var1.setline(452);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("table", var3);
      var3 = null;
      var1.setline(453);
      PyInteger var8 = Py.newInteger(0);
      var1.getlocal(0).__setattr__((String)"total", var8);
      var3 = null;
      var1.setline(454);
      PyList var10 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"rows", var10);
      var3 = null;
      var1.setline(455);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("autobatch", var3);
      var3 = null;
      var1.setline(456);
      PyDictionary var11 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"bindings", var11);
      var3 = null;
      var1.setline(458);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(458);
      PyObject[] var12 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var12, f$51)), (PyObject)var1.getlocal(3));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(459);
      var10000 = var1.getglobal("map");
      var1.setline(459);
      var12 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var12, f$52)), (PyObject)var1.getlocal(4));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(461);
      var3 = var1.getlocal(0).__getattr__("dst").__getattr__("verbose");
      var1.setlocal(7, var3);
      var3 = null;
      var1.setline(462);
      var8 = Py.newInteger(0);
      var1.getlocal(0).__getattr__("dst").__setattr__((String)"verbose", var8);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(464);
         var1.getlocal(0).__getattr__("dst").__getattr__("table").__call__(var2, var1.getlocal(0).__getattr__("table"));
         var1.setline(465);
         if (var1.getlocal(0).__getattr__("dst").__getattr__("results").__nonzero__()) {
            var1.setline(466);
            PyDictionary var9 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(8, var9);
            var4 = null;
            var1.setline(467);
            var4 = var1.getlocal(0).__getattr__("dst").__getattr__("results").__iter__();

            label28:
            while(true) {
               var1.setline(467);
               PyObject var5 = var4.__iternext__();
               PyObject var6;
               if (var5 == null) {
                  var1.setline(469);
                  var4 = var1.getlocal(0).__getattr__("__filter__").__call__(var2, var1.getlocal(8).__getattr__("keys").__call__(var2), var1.getlocal(3), var1.getlocal(4));
                  var1.setlocal(10, var4);
                  var4 = null;
                  var1.setline(470);
                  var4 = var1.getglobal("zip").__call__(var2, var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(10))), var1.getlocal(10)).__iter__();

                  while(true) {
                     var1.setline(470);
                     var5 = var4.__iternext__();
                     if (var5 == null) {
                        var1.setline(472);
                        var4 = var1.getglobal("None");
                        var1.setlocal(8, var4);
                        var4 = null;
                        break label28;
                     }

                     var1.setlocal(9, var5);
                     var1.setline(471);
                     var6 = var1.getlocal(8).__getitem__(var1.getlocal(9).__getitem__(Py.newInteger(1)));
                     var1.getlocal(0).__getattr__("bindings").__setitem__(var1.getlocal(9).__getitem__(Py.newInteger(0)), var6);
                     var6 = null;
                  }
               }

               var1.setlocal(9, var5);
               var1.setline(468);
               var6 = var1.getlocal(9).__getitem__(Py.newInteger(4));
               var1.getlocal(8).__setitem__(var1.getlocal(9).__getitem__(Py.newInteger(3)).__getattr__("lower").__call__(var2), var6);
               var6 = null;
            }
         } else {
            var1.setline(474);
            var4 = var1.getlocal(0).__getattr__("__filter__").__call__(var2, var1.getlocal(3), var1.getlocal(3), var1.getlocal(4));
            var1.setlocal(10, var4);
            var4 = null;
         }
      } catch (Throwable var7) {
         Py.addTraceback(var7, var1);
         var1.setline(476);
         var4 = var1.getlocal(7);
         var1.getlocal(0).__getattr__("dst").__setattr__("verbose", var4);
         var4 = null;
         throw (Throwable)var7;
      }

      var1.setline(476);
      var4 = var1.getlocal(7);
      var1.getlocal(0).__getattr__("dst").__setattr__("verbose", var4);
      var4 = null;
      var1.setline(478);
      var3 = var1.getlocal(6).__call__(var2, var1.getlocal(2), var1.getlocal(10));
      var1.getlocal(0).__setattr__("executor", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$51(PyFrame var1, ThreadState var2) {
      var1.setline(458);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$52(PyFrame var1, ThreadState var2) {
      var1.setline(459);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$53(PyFrame var1, ThreadState var2) {
      var1.setline(481);
      PyObject var3 = PyString.fromInterned("[%s].[%s]")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("dst"), var1.getlocal(0).__getattr__("table")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$54(PyFrame var1, ThreadState var2) {
      var1.setline(484);
      PyObject var3 = PyString.fromInterned("[%s].[%s]")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("dst"), var1.getlocal(0).__getattr__("table")}));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getattr__$55(PyFrame var1, ThreadState var2) {
      var1.setline(487);
      PyObject var3 = var1.getlocal(1);
      PyObject var10000 = var3._eq(PyString.fromInterned("columns"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(488);
         var3 = var1.getlocal(0).__getattr__("executor").__getattr__("cols");
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.f_lasti = -1;
         return Py.None;
      }
   }

   public PyObject __filter__$56(PyFrame var1, ThreadState var2) {
      var1.setline(491);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(491);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$57)), (PyObject)var1.getlocal(1));
      var1.setlocal(4, var4);
      var3 = null;
      var1.setline(492);
      if (var1.getlocal(3).__nonzero__()) {
         var1.setline(493);
         var10000 = var1.getglobal("filter");
         var1.setline(493);
         var3 = new PyObject[]{var1.getlocal(3)};
         var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$58)), (PyObject)var1.getlocal(4));
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(494);
      if (var1.getlocal(2).__nonzero__()) {
         var1.setline(495);
         var10000 = var1.getglobal("filter");
         var1.setline(495);
         var3 = new PyObject[]{var1.getlocal(2)};
         var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$59)), (PyObject)var1.getlocal(4));
         var1.setlocal(4, var4);
         var3 = null;
      }

      var1.setline(496);
      var4 = var1.getlocal(4);
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$57(PyFrame var1, ThreadState var2) {
      var1.setline(491);
      PyObject var3 = var1.getlocal(0).__getattr__("lower").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$58(PyFrame var1, ThreadState var2) {
      var1.setline(493);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._notin(var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$59(PyFrame var1, ThreadState var2) {
      var1.setline(495);
      PyObject var3 = var1.getlocal(0);
      PyObject var10000 = var3._in(var1.getlocal(1));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject format$60(PyFrame var1, ThreadState var2) {
      var1.setline(499);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__getattr__("bindings").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject done$61(PyFrame var1, ThreadState var2) {
      var1.setline(502);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("rows"));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(503);
         var3 = var1.getlocal(0).__getattr__("batch").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(504);
         PyInteger var4 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var4;
      }
   }

   public PyObject batch$62(PyFrame var1, ThreadState var2) {
      var1.setline(507);
      var1.getlocal(0).__getattr__("executor").__getattr__("execute").__call__(var2, var1.getlocal(0).__getattr__("dst"), var1.getlocal(0).__getattr__("rows"), var1.getlocal(0).__getattr__("bindings"));
      var1.setline(508);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("rows"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(509);
      PyObject var10000 = var1.getlocal(0);
      String var6 = "total";
      PyObject var4 = var10000;
      PyObject var5 = var4.__getattr__(var6);
      var5 = var5._iadd(var1.getlocal(1));
      var4.__setattr__(var6, var5);
      var1.setline(510);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"rows", var7);
      var3 = null;
      var1.setline(511);
      var3 = var1.getlocal(1);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject rowxfer$63(PyFrame var1, ThreadState var2) {
      var1.setline(514);
      var1.getlocal(0).__getattr__("rows").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.setline(515);
      if (var1.getlocal(0).__getattr__("autobatch").__nonzero__()) {
         var1.setline(515);
         var1.getlocal(0).__getattr__("batch").__call__(var2);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject transfer$64(PyFrame var1, ThreadState var2) {
      var1.setline(518);
      PyObject var3 = PyString.fromInterned("select %s from %s where %s")._mod(new PyTuple(new PyObject[]{PyString.fromInterned(", ").__getattr__("join").__call__(var2, var1.getlocal(0).__getattr__("columns")), var1.getlocal(0).__getattr__("table"), var1.getlocal(2)}));
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(519);
      var3 = var1.getlocal(1).__getattr__("raw").__call__(var2, var1.getlocal(4), var1.getlocal(3));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(5, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(6, var5);
      var5 = null;
      var3 = null;
      var1.setline(520);
      if (var1.getlocal(6).__nonzero__()) {
         var1.setline(521);
         var1.getglobal("map").__call__(var2, var1.getlocal(0).__getattr__("rowxfer"), var1.getlocal(6));
         var1.setline(522);
         var3 = var1.getlocal(0).__getattr__("done").__call__(var2);
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(523);
         PyInteger var6 = Py.newInteger(0);
         var1.f_lasti = -1;
         return var6;
      }
   }

   public PyObject Unload$65(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Unloads a sql statement to a file with optional formatting of each value."));
      var1.setline(526);
      PyString.fromInterned("Unloads a sql statement to a file with optional formatting of each value.");
      var1.setline(527);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned(","), Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$66, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(534);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, format$67, (PyObject)null);
      var1.setlocal("format", var4);
      var3 = null;
      var1.setline(542);
      var3 = new PyObject[]{PyString.fromInterned("w")};
      var4 = new PyFunction(var1.f_globals, var3, unload$68, (PyObject)null);
      var1.setlocal("unload", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$66(PyFrame var1, ThreadState var2) {
      var1.setline(528);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("db", var3);
      var3 = null;
      var1.setline(529);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("filename", var3);
      var3 = null;
      var1.setline(530);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("delimiter", var3);
      var3 = null;
      var1.setline(531);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("includeheaders", var3);
      var3 = null;
      var1.setline(532);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"formatters", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject format$67(PyFrame var1, ThreadState var2) {
      var1.setline(535);
      if (var1.getlocal(1).__not__().__nonzero__()) {
         var1.setline(536);
         PyString var5 = PyString.fromInterned("");
         var1.f_lasti = -1;
         return var5;
      } else {
         var1.setline(537);
         PyObject var4 = var1.getglobal("str").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var4);
         var4 = null;
         var1.setline(538);
         var4 = var1.getlocal(1).__getattr__("find").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(","));
         PyObject var10000 = var4._ne(Py.newInteger(-1));
         var4 = null;
         if (var10000.__nonzero__()) {
            var1.setline(539);
            var4 = PyString.fromInterned("\"\"%s\"\"")._mod(var1.getlocal(1));
            var1.setlocal(1, var4);
            var4 = null;
         }

         var1.setline(540);
         PyObject var3 = var1.getlocal(1);
         var1.f_lasti = -1;
         return var3;
      }
   }

   public PyObject unload$68(PyFrame var1, ThreadState var2) {
      var1.setline(543);
      PyObject var3 = var1.getlocal(0).__getattr__("db").__getattr__("raw").__call__(var2, var1.getlocal(1));
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(3, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(4, var5);
      var5 = null;
      var3 = null;
      var1.setline(544);
      var3 = var1.getglobal("open").__call__(var2, var1.getlocal(0).__getattr__("filename"), var1.getlocal(2));
      var1.setlocal(5, var3);
      var3 = null;
      var1.setline(545);
      if (var1.getlocal(0).__getattr__("includeheaders").__nonzero__()) {
         var1.setline(546);
         PyObject var10000 = var1.getlocal(5).__getattr__("write");
         PyString var10002 = PyString.fromInterned("%s\n");
         PyObject var10003 = var1.getlocal(0).__getattr__("delimiter").__getattr__("join");
         PyObject var10005 = var1.getglobal("map");
         var1.setline(546);
         PyObject[] var6 = Py.EmptyObjects;
         var10000.__call__(var2, var10002._mod(var10003.__call__(var2, var10005.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var6, f$69)), (PyObject)var1.getlocal(3)))));
      }

      var1.setline(547);
      if (var1.getlocal(4).__nonzero__()) {
         var1.setline(548);
         var3 = var1.getlocal(4).__iter__();

         while(true) {
            var1.setline(548);
            PyObject var7 = var3.__iternext__();
            if (var7 == null) {
               break;
            }

            var1.setlocal(6, var7);
            var1.setline(549);
            var1.getlocal(5).__getattr__("write").__call__(var2, PyString.fromInterned("%s\n")._mod(var1.getlocal(0).__getattr__("delimiter").__getattr__("join").__call__(var2, var1.getglobal("map").__call__(var2, var1.getlocal(0).__getattr__("format"), var1.getlocal(6)))));
         }
      }

      var1.setline(550);
      var1.getlocal(5).__getattr__("flush").__call__(var2);
      var1.setline(551);
      var1.getlocal(5).__getattr__("close").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$69(PyFrame var1, ThreadState var2) {
      var1.setline(546);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject Schema$70(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setlocal("__doc__", PyString.fromInterned("Produces a Schema object which represents the database schema for a table"));
      var1.setline(554);
      PyString.fromInterned("Produces a Schema object which represents the database schema for a table");
      var1.setline(555);
      PyObject[] var3 = new PyObject[]{var1.getname("None"), Py.newInteger(0), Py.newInteger(1)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$71, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(568);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, computeschema$72, (PyObject)null);
      var1.setlocal("computeschema", var4);
      var3 = null;
      var1.setline(622);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __str__$85, (PyObject)null);
      var1.setlocal("__str__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$71(PyFrame var1, ThreadState var2) {
      var1.setline(556);
      PyObject var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("db", var3);
      var3 = null;
      var1.setline(557);
      var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("table", var3);
      var3 = null;
      var1.setline(558);
      var3 = var1.getlocal(3);
      var1.getlocal(0).__setattr__("owner", var3);
      var3 = null;
      var1.setline(559);
      var3 = var1.getlocal(4);
      var1.getlocal(0).__setattr__("full", var3);
      var3 = null;
      var1.setline(560);
      var3 = var1.getlocal(5);
      var1.getlocal(0).__setattr__("sort", var3);
      var3 = null;
      var1.setline(561);
      var3 = var1.getlocal(0).__getattr__("db").__getattr__("verbose");
      var1.setlocal(6, var3);
      var3 = null;
      var1.setline(562);
      PyInteger var6 = Py.newInteger(0);
      var1.getlocal(0).__getattr__("db").__setattr__((String)"verbose", var6);
      var3 = null;
      var3 = null;

      PyObject var4;
      try {
         var1.setline(564);
         if (var1.getlocal(2).__nonzero__()) {
            var1.setline(564);
            var1.getlocal(0).__getattr__("computeschema").__call__(var2);
         }
      } catch (Throwable var5) {
         Py.addTraceback(var5, var1);
         var1.setline(566);
         var4 = var1.getlocal(6);
         var1.getlocal(0).__getattr__("db").__setattr__("verbose", var4);
         var4 = null;
         throw (Throwable)var5;
      }

      var1.setline(566);
      var4 = var1.getlocal(6);
      var1.getlocal(0).__getattr__("db").__setattr__("verbose", var4);
      var4 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject computeschema$72(PyFrame var1, ThreadState var2) {
      var1.setline(569);
      PyObject var10000 = var1.getlocal(0).__getattr__("db").__getattr__("table");
      PyObject[] var3 = new PyObject[]{var1.getlocal(0).__getattr__("table"), var1.getlocal(0).__getattr__("owner")};
      String[] var4 = new String[]{"owner"};
      var10000.__call__(var2, var3, var4);
      var3 = null;
      var1.setline(570);
      PyList var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"columns", var7);
      var3 = null;
      var1.setline(572);
      PyObject var9;
      if (var1.getlocal(0).__getattr__("db").__getattr__("results").__nonzero__()) {
         var1.setline(573);
         var10000 = var1.getglobal("map");
         var1.setline(573);
         var3 = Py.EmptyObjects;
         var9 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$73)), (PyObject)var1.getlocal(0).__getattr__("db").__getattr__("results"));
         var1.getlocal(0).__setattr__("columns", var9);
         var3 = null;
         var1.setline(574);
         if (var1.getlocal(0).__getattr__("sort").__nonzero__()) {
            var1.setline(574);
            var10000 = var1.getlocal(0).__getattr__("columns").__getattr__("sort");
            var1.setline(574);
            var3 = Py.EmptyObjects;
            var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$74)));
         }
      }

      var1.setline(576);
      var1.getlocal(0).__getattr__("db").__getattr__("fk").__call__(var2, var1.getglobal("None"), var1.getlocal(0).__getattr__("table"));
      var1.setline(578);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"imported", var7);
      var3 = null;
      var1.setline(579);
      if (var1.getlocal(0).__getattr__("db").__getattr__("results").__nonzero__()) {
         var1.setline(580);
         var10000 = var1.getglobal("map");
         var1.setline(580);
         var3 = Py.EmptyObjects;
         var9 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$75)), (PyObject)var1.getlocal(0).__getattr__("db").__getattr__("results"));
         var1.getlocal(0).__setattr__("imported", var9);
         var3 = null;
         var1.setline(581);
         if (var1.getlocal(0).__getattr__("sort").__nonzero__()) {
            var1.setline(581);
            var10000 = var1.getlocal(0).__getattr__("imported").__getattr__("sort");
            var1.setline(581);
            var3 = Py.EmptyObjects;
            var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$76)));
         }
      }

      var1.setline(583);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"exported", var7);
      var3 = null;
      var1.setline(584);
      if (var1.getlocal(0).__getattr__("full").__nonzero__()) {
         var1.setline(585);
         var1.getlocal(0).__getattr__("db").__getattr__("fk").__call__(var2, var1.getlocal(0).__getattr__("table"), var1.getglobal("None"));
         var1.setline(587);
         if (var1.getlocal(0).__getattr__("db").__getattr__("results").__nonzero__()) {
            var1.setline(588);
            var10000 = var1.getglobal("map");
            var1.setline(588);
            var3 = Py.EmptyObjects;
            var9 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$77)), (PyObject)var1.getlocal(0).__getattr__("db").__getattr__("results"));
            var1.getlocal(0).__setattr__("exported", var9);
            var3 = null;
            var1.setline(589);
            if (var1.getlocal(0).__getattr__("sort").__nonzero__()) {
               var1.setline(589);
               var10000 = var1.getlocal(0).__getattr__("exported").__getattr__("sort");
               var1.setline(589);
               var3 = Py.EmptyObjects;
               var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$78)));
            }
         }
      }

      var1.setline(591);
      var1.getlocal(0).__getattr__("db").__getattr__("pk").__call__(var2, var1.getlocal(0).__getattr__("table"));
      var1.setline(592);
      var7 = new PyList(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"primarykeys", var7);
      var3 = null;
      var1.setline(593);
      if (var1.getlocal(0).__getattr__("db").__getattr__("results").__nonzero__()) {
         var1.setline(595);
         var10000 = var1.getglobal("map");
         var1.setline(595);
         var3 = Py.EmptyObjects;
         var9 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$79)), (PyObject)var1.getlocal(0).__getattr__("db").__getattr__("results"));
         var1.getlocal(0).__setattr__("primarykeys", var9);
         var3 = null;
         var1.setline(596);
         if (var1.getlocal(0).__getattr__("sort").__nonzero__()) {
            var1.setline(596);
            var10000 = var1.getlocal(0).__getattr__("primarykeys").__getattr__("sort");
            var1.setline(596);
            var3 = Py.EmptyObjects;
            var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$80)));
         }
      }

      try {
         var1.setline(599);
         var9 = var1.getglobal("None");
         var1.getlocal(0).__setattr__("indices", var9);
         var3 = null;
         var1.setline(600);
         var1.getlocal(0).__getattr__("db").__getattr__("stat").__call__(var2, var1.getlocal(0).__getattr__("table"));
         var1.setline(601);
         var7 = new PyList(Py.EmptyObjects);
         var1.getlocal(0).__setattr__((String)"indices", var7);
         var3 = null;
         var1.setline(603);
         if (var1.getlocal(0).__getattr__("db").__getattr__("results").__nonzero__()) {
            var1.setline(604);
            PyDictionary var10 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(1, var10);
            var3 = null;
            var1.setline(606);
            var10000 = var1.getglobal("map");
            var1.setline(606);
            var3 = Py.EmptyObjects;
            PyFunction var10002 = new PyFunction(var1.f_globals, var3, f$81);
            PyObject var10003 = var1.getglobal("filter");
            var1.setline(606);
            var3 = Py.EmptyObjects;
            var9 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$82)), (PyObject)var1.getlocal(0).__getattr__("db").__getattr__("results")));
            var1.setlocal(2, var9);
            var3 = null;
            var1.setline(607);
            var3 = Py.EmptyObjects;
            PyFunction var11 = new PyFunction(var1.f_globals, var3, cckmp$83, (PyObject)null);
            var1.setlocal(3, var11);
            var3 = null;
            var1.setline(612);
            var1.getlocal(2).__getattr__("sort").__call__(var2, var1.getlocal(3));
            var1.setline(613);
            var9 = var1.getlocal(2).__iter__();

            while(true) {
               var1.setline(613);
               PyObject var8 = var9.__iternext__();
               if (var8 == null) {
                  var1.setline(617);
                  var9 = var1.getlocal(1).__getattr__("values").__call__(var2);
                  var1.getlocal(0).__setattr__("indices", var9);
                  var3 = null;
                  var1.setline(618);
                  if (var1.getlocal(0).__getattr__("sort").__nonzero__()) {
                     var1.setline(618);
                     var10000 = var1.getlocal(0).__getattr__("indices").__getattr__("sort");
                     var1.setline(618);
                     var3 = Py.EmptyObjects;
                     var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$84)));
                  }
                  break;
               }

               var1.setlocal(4, var8);
               var1.setline(614);
               if (var1.getlocal(1).__getattr__("has_key").__call__(var2, var1.getlocal(4).__getitem__(Py.newInteger(1))).__not__().__nonzero__()) {
                  var1.setline(615);
                  PyList var5 = new PyList(Py.EmptyObjects);
                  var1.getlocal(1).__setitem__((PyObject)var1.getlocal(4).__getitem__(Py.newInteger(1)), var5);
                  var5 = null;
               }

               var1.setline(616);
               var1.getlocal(1).__getitem__(var1.getlocal(4).__getitem__(Py.newInteger(1))).__getattr__("append").__call__(var2, var1.getlocal(4));
            }
         }
      } catch (Throwable var6) {
         Py.setException(var6, var1);
         var1.setline(620);
      }

      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$73(PyFrame var1, ThreadState var2) {
      var1.setline(573);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(3)), var1.getlocal(0).__getitem__(Py.newInteger(5)), var1.getlocal(0).__getitem__(Py.newInteger(6)), var1.getlocal(0).__getitem__(Py.newInteger(10))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$74(PyFrame var1, ThreadState var2) {
      var1.setline(574);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)), var1.getlocal(1).__getitem__(Py.newInteger(0)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$75(PyFrame var1, ThreadState var2) {
      var1.setline(580);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(2)), var1.getlocal(0).__getitem__(Py.newInteger(3)), var1.getlocal(0).__getitem__(Py.newInteger(7)), var1.getlocal(0).__getitem__(Py.newInteger(11)), var1.getlocal(0).__getitem__(Py.newInteger(12))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$76(PyFrame var1, ThreadState var2) {
      var1.setline(581);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(2)), var1.getlocal(1).__getitem__(Py.newInteger(2)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$77(PyFrame var1, ThreadState var2) {
      var1.setline(588);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(3)), var1.getlocal(0).__getitem__(Py.newInteger(6)), var1.getlocal(0).__getitem__(Py.newInteger(7)), var1.getlocal(0).__getitem__(Py.newInteger(11)), var1.getlocal(0).__getitem__(Py.newInteger(12))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$78(PyFrame var1, ThreadState var2) {
      var1.setline(589);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$79(PyFrame var1, ThreadState var2) {
      var1.setline(595);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(3)), var1.getlocal(0).__getitem__(Py.newInteger(4)), var1.getlocal(0).__getitem__(Py.newInteger(5))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$80(PyFrame var1, ThreadState var2) {
      var1.setline(596);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$81(PyFrame var1, ThreadState var2) {
      var1.setline(606);
      PyTuple var3 = new PyTuple(new PyObject[]{var1.getlocal(0).__getitem__(Py.newInteger(3)), var1.getlocal(0).__getitem__(Py.newInteger(5)).__getattr__("strip").__call__(var2), var1.getlocal(0).__getitem__(Py.newInteger(6)), var1.getlocal(0).__getitem__(Py.newInteger(7)), var1.getlocal(0).__getitem__(Py.newInteger(8))});
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$82(PyFrame var1, ThreadState var2) {
      var1.setline(606);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(5));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject cckmp$83(PyFrame var1, ThreadState var2) {
      var1.setline(608);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(1)));
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(609);
      var3 = var1.getlocal(2);
      PyObject var10000 = var3._eq(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(609);
         var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(3)), var1.getlocal(1).__getitem__(Py.newInteger(3)));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(610);
      var3 = var1.getlocal(2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$84(PyFrame var1, ThreadState var2) {
      var1.setline(618);
      PyObject var3 = var1.getglobal("cmp").__call__(var2, var1.getlocal(0).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), var1.getlocal(1).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __str__$85(PyFrame var1, ThreadState var2) {
      var1.setline(623);
      PyList var3 = new PyList(Py.EmptyObjects);
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(624);
      var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("Table"));
      var1.setline(625);
      var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("  ")._add(var1.getlocal(0).__getattr__("table")));
      var1.setline(626);
      var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nPrimary Keys"));
      var1.setline(627);
      PyObject var6 = var1.getlocal(0).__getattr__("primarykeys").__iter__();

      while(true) {
         var1.setline(627);
         PyObject var4 = var6.__iternext__();
         if (var4 == null) {
            var1.setline(629);
            var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nImported (Foreign) Keys"));
            var1.setline(630);
            var6 = var1.getlocal(0).__getattr__("imported").__iter__();

            while(true) {
               var1.setline(630);
               var4 = var6.__iternext__();
               if (var4 == null) {
                  var1.setline(632);
                  if (var1.getlocal(0).__getattr__("full").__nonzero__()) {
                     var1.setline(633);
                     var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nExported (Referenced) Keys"));
                     var1.setline(634);
                     var6 = var1.getlocal(0).__getattr__("exported").__iter__();

                     while(true) {
                        var1.setline(634);
                        var4 = var6.__iternext__();
                        if (var4 == null) {
                           break;
                        }

                        var1.setlocal(2, var4);
                        var1.setline(635);
                        var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("  %s (%s.%s) {%s}")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getlocal(2).__getitem__(Py.newInteger(3))})));
                     }
                  }

                  var1.setline(636);
                  var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nColumns"));
                  var1.setline(637);
                  var6 = var1.getlocal(0).__getattr__("columns").__iter__();

                  while(true) {
                     var1.setline(637);
                     var4 = var6.__iternext__();
                     PyObject var5;
                     if (var4 == null) {
                        var1.setline(640);
                        var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\nIndices"));
                        var1.setline(641);
                        var6 = var1.getlocal(0).__getattr__("indices");
                        PyObject var10000 = var6._is(var1.getglobal("None"));
                        var3 = null;
                        if (var10000.__nonzero__()) {
                           var1.setline(642);
                           var1.getlocal(1).__getattr__("append").__call__((ThreadState)var2, (PyObject)PyString.fromInterned(" (failed)"));
                        } else {
                           var1.setline(644);
                           var6 = var1.getlocal(0).__getattr__("indices").__iter__();

                           while(true) {
                              var1.setline(644);
                              var4 = var6.__iternext__();
                              if (var4 == null) {
                                 break;
                              }

                              var1.setlocal(2, var4);
                              var1.setline(645);
                              var5 = var1.getglobal("choose").__call__((ThreadState)var2, var1.getlocal(2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(0)), (PyObject)PyString.fromInterned("non-unique"), (PyObject)PyString.fromInterned("unique"));
                              var1.setlocal(4, var5);
                              var5 = null;
                              var1.setline(646);
                              var10000 = PyString.fromInterned(", ").__getattr__("join");
                              PyObject var10002 = var1.getglobal("map");
                              var1.setline(646);
                              PyObject[] var7 = Py.EmptyObjects;
                              var5 = var10000.__call__(var2, var10002.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var7, f$86)), (PyObject)var1.getlocal(2)));
                              var1.setlocal(5, var5);
                              var5 = null;
                              var1.setline(647);
                              var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("  %s index {%s} on (%s)")._mod(new PyTuple(new PyObject[]{var1.getlocal(4), var1.getlocal(2).__getitem__(Py.newInteger(0)).__getitem__(Py.newInteger(1)), var1.getlocal(5)})));
                           }
                        }

                        var1.setline(648);
                        var6 = PyString.fromInterned("\n").__getattr__("join").__call__(var2, var1.getlocal(1));
                        var1.f_lasti = -1;
                        return var6;
                     }

                     var1.setlocal(2, var4);
                     var1.setline(638);
                     var5 = var1.getglobal("choose").__call__((ThreadState)var2, var1.getlocal(2).__getitem__(Py.newInteger(3)), (PyObject)PyString.fromInterned("nullable"), (PyObject)PyString.fromInterned("non-nullable"));
                     var1.setlocal(3, var5);
                     var5 = null;
                     var1.setline(639);
                     var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("  %-20s %s(%s), %s")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getlocal(3)})));
                  }
               }

               var1.setlocal(2, var4);
               var1.setline(631);
               var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("  %s (%s.%s) {%s}")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(2)), var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(Py.newInteger(1)), var1.getlocal(2).__getitem__(Py.newInteger(3))})));
            }
         }

         var1.setlocal(2, var4);
         var1.setline(628);
         var1.getlocal(1).__getattr__("append").__call__(var2, PyString.fromInterned("  %s {%s}")._mod(new PyTuple(new PyObject[]{var1.getlocal(2).__getitem__(Py.newInteger(0)), var1.getlocal(2).__getitem__(Py.newInteger(2))})));
      }
   }

   public PyObject f$86(PyFrame var1, ThreadState var2) {
      var1.setline(646);
      PyObject var3 = var1.getlocal(0).__getitem__(Py.newInteger(4));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject IniParser$87(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(651);
      PyObject[] var3 = new PyObject[]{PyString.fromInterned("name")};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$88, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(659);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, parse$89, (PyObject)null);
      var1.setlocal("parse", var4);
      var3 = null;
      var1.setline(678);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$92, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$88(PyFrame var1, ThreadState var2) {
      var1.setline(652);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("key", var3);
      var3 = null;
      var1.setline(653);
      PyDictionary var4 = new PyDictionary(Py.EmptyObjects);
      var1.getlocal(0).__setattr__((String)"records", var4);
      var3 = null;
      var1.setline(654);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("\\[(jdbc|odbc|default)\\]"));
      var1.getlocal(0).__setattr__("ctypeRE", var3);
      var3 = null;
      var1.setline(655);
      var3 = var1.getglobal("re").__getattr__("compile").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("([a-zA-Z]+)[ \t]*=[ \t]*(.*)"));
      var1.getlocal(0).__setattr__("entryRE", var3);
      var3 = null;
      var1.setline(656);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("cfg", var3);
      var3 = null;
      var1.setline(657);
      var1.getlocal(0).__getattr__("parse").__call__(var2);
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject parse$89(PyFrame var1, ThreadState var2) {
      var1.setline(660);
      PyObject var3 = var1.getglobal("open").__call__((ThreadState)var2, (PyObject)var1.getlocal(0).__getattr__("cfg"), (PyObject)PyString.fromInterned("r"));
      var1.setlocal(1, var3);
      var3 = null;
      var1.setline(661);
      var3 = var1.getlocal(1).__getattr__("readlines").__call__(var2);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(662);
      var1.getlocal(1).__getattr__("close").__call__(var2);
      var1.setline(663);
      PyObject var10000 = var1.getglobal("filter");
      var1.setline(663);
      PyObject[] var6 = Py.EmptyObjects;
      PyFunction var10002 = new PyFunction(var1.f_globals, var6, f$90);
      PyObject var10003 = var1.getglobal("map");
      var1.setline(663);
      var6 = Py.EmptyObjects;
      var3 = var10000.__call__((ThreadState)var2, (PyObject)var10002, (PyObject)var10003.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var6, f$91)), (PyObject)var1.getlocal(2)));
      var1.setlocal(3, var3);
      var3 = null;
      var1.setline(664);
      var3 = var1.getglobal("None");
      var1.setlocal(4, var3);
      var3 = null;
      var1.setline(665);
      var3 = var1.getglobal("range").__call__(var2, var1.getglobal("len").__call__(var2, var1.getlocal(3))).__iter__();

      while(true) {
         var1.setline(665);
         PyObject var4 = var3.__iternext__();
         if (var4 == null) {
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal(5, var4);
         var1.setline(666);
         PyObject var5 = var1.getlocal(3).__getitem__(var1.getlocal(5));
         var1.setlocal(6, var5);
         var5 = null;
         var1.setline(667);
         var5 = var1.getlocal(0).__getattr__("ctypeRE").__getattr__("match").__call__(var2, var1.getlocal(6));
         var1.setlocal(7, var5);
         var5 = null;
         var1.setline(668);
         if (var1.getlocal(7).__nonzero__()) {
            var1.setline(669);
            PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
            var1.setlocal(4, var7);
            var5 = null;
            var1.setline(670);
            if (var1.getlocal(0).__getattr__("records").__getattr__("has_key").__call__(var2, var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))).__not__().__nonzero__()) {
               var1.setline(671);
               PyList var8 = new PyList(Py.EmptyObjects);
               var1.getlocal(0).__getattr__("records").__setitem__((PyObject)var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var8);
               var5 = null;
            }

            var1.setline(672);
            var1.getlocal(0).__getattr__("records").__getitem__(var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1))).__getattr__("append").__call__(var2, var1.getlocal(4));
         } else {
            var1.setline(674);
            var5 = var1.getlocal(0).__getattr__("entryRE").__getattr__("match").__call__(var2, var1.getlocal(6));
            var1.setlocal(7, var5);
            var5 = null;
            var1.setline(675);
            if (var1.getlocal(7).__nonzero__()) {
               var1.setline(676);
               var5 = var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(2));
               var1.getlocal(4).__setitem__(var1.getlocal(7).__getattr__("group").__call__((ThreadState)var2, (PyObject)Py.newInteger(1)), var5);
               var5 = null;
            }
         }
      }
   }

   public PyObject f$90(PyFrame var1, ThreadState var2) {
      var1.setline(663);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0));
      PyObject var10000 = var3._gt(Py.newInteger(0));
      var3 = null;
      if (var10000.__nonzero__()) {
         var3 = var1.getlocal(0).__getitem__(Py.newInteger(0));
         var10000 = var3._notin(new PyList(new PyObject[]{PyString.fromInterned("#"), PyString.fromInterned(";")}));
         var3 = null;
      }

      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject f$91(PyFrame var1, ThreadState var2) {
      var1.setline(663);
      PyObject var3 = var1.getlocal(0).__getattr__("strip").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$92(PyFrame var1, ThreadState var2) {
      var1.setline(678);
      PyObject var3 = var1.getlocal(1);
      PyObject[] var4 = Py.unpackSequence(var3, 2);
      PyObject var5 = var4[0];
      var1.setlocal(2, var5);
      var5 = null;
      var5 = var4[1];
      var1.setlocal(3, var5);
      var5 = null;
      var3 = null;
      var1.setline(679);
      var3 = var1.getlocal(3);
      PyObject var10000 = var3._eq(var1.getlocal(0).__getattr__("key"));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(679);
         var3 = var1.getlocal(0).__getattr__("records").__getitem__(var1.getlocal(2)).__getitem__(Py.newInteger(0)).__getitem__(var1.getlocal(3));
         var1.f_lasti = -1;
         return var3;
      } else {
         var1.setline(680);
         var10000 = var1.getglobal("filter");
         var1.setline(680);
         var4 = new PyObject[]{var1.getlocal(0).__getattr__("key"), var1.getlocal(3)};
         PyObject var6 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var4, f$93)), (PyObject)var1.getlocal(0).__getattr__("records").__getitem__(var1.getlocal(2)));
         var1.setlocal(4, var6);
         var4 = null;
         var1.setline(681);
         var10000 = var1.getlocal(4).__not__();
         if (!var10000.__nonzero__()) {
            var6 = var1.getglobal("len").__call__(var2, var1.getlocal(4));
            var10000 = var6._gt(Py.newInteger(1));
            var4 = null;
         }

         if (var10000.__nonzero__()) {
            var1.setline(682);
            throw Py.makeException(var1.getglobal("KeyError"), PyString.fromInterned("invalid key ('%s', '%s')")._mod(new PyTuple(new PyObject[]{var1.getlocal(2), var1.getlocal(3)})));
         } else {
            var1.setline(683);
            var3 = var1.getlocal(4).__getitem__(Py.newInteger(0));
            var1.f_lasti = -1;
            return var3;
         }
      }
   }

   public PyObject f$93(PyFrame var1, ThreadState var2) {
      var1.setline(680);
      PyObject var3 = var1.getlocal(0).__getitem__(var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getlocal(2));
      var3 = null;
      var3 = var10000;
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject random_table_name$94(PyFrame var1, ThreadState var2) {
      var1.setline(686);
      PyObject var3 = imp.importOne("random", var1, -1);
      var1.setlocal(2, var3);
      var3 = null;
      var1.setline(687);
      PyList var4 = new PyList(new PyObject[]{var1.getlocal(0), PyString.fromInterned("_")});
      var1.setlocal(3, var4);
      var3 = null;
      var1.setline(688);
      PyInteger var5 = Py.newInteger(0);
      var1.setlocal(4, var5);
      var3 = null;

      while(true) {
         var1.setline(689);
         var3 = var1.getlocal(4);
         PyObject var10000 = var3._lt(var1.getlocal(1));
         var3 = null;
         if (!var10000.__nonzero__()) {
            var1.setline(692);
            var3 = PyString.fromInterned("").__getattr__("join").__call__(var2, var1.getlocal(3));
            var1.f_lasti = -1;
            return var3;
         }

         var1.setline(690);
         var1.getlocal(3).__getattr__("append").__call__(var2, var1.getglobal("chr").__call__(var2, var1.getglobal("int").__call__(var2, Py.newInteger(100)._mul(var1.getlocal(2).__getattr__("random").__call__(var2)))._mod(Py.newInteger(26))._add(var1.getglobal("ord").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("A")))));
         var1.setline(691);
         var3 = var1.getlocal(4);
         var3 = var3._iadd(Py.newInteger(1));
         var1.setlocal(4, var3);
      }
   }

   public PyObject ResultSetRow$95(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(695);
      PyObject[] var3 = Py.EmptyObjects;
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$96, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(698);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$97, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(702);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getslice__$98, (PyObject)null);
      var1.setlocal("__getslice__", var4);
      var3 = null;
      var1.setline(706);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __len__$99, (PyObject)null);
      var1.setlocal("__len__", var4);
      var3 = null;
      var1.setline(708);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$100, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$96(PyFrame var1, ThreadState var2) {
      var1.setline(696);
      PyObject var3 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("row", var3);
      var3 = null;
      var1.setline(697);
      var3 = var1.getlocal(1);
      var1.getlocal(0).__setattr__("rs", var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject __getitem__$97(PyFrame var1, ThreadState var2) {
      var1.setline(699);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(700);
         var3 = var1.getlocal(0).__getattr__("rs").__getattr__("index").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(701);
      var3 = var1.getlocal(0).__getattr__("row").__getitem__(var1.getlocal(1));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getslice__$98(PyFrame var1, ThreadState var2) {
      var1.setline(703);
      PyObject var3 = var1.getglobal("type").__call__(var2, var1.getlocal(1));
      PyObject var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(703);
         var3 = var1.getlocal(0).__getattr__("rs").__getattr__("index").__call__(var2, var1.getlocal(1));
         var1.setlocal(1, var3);
         var3 = null;
      }

      var1.setline(704);
      var3 = var1.getglobal("type").__call__(var2, var1.getlocal(2));
      var10000 = var3._eq(var1.getglobal("type").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("")));
      var3 = null;
      if (var10000.__nonzero__()) {
         var1.setline(704);
         var3 = var1.getlocal(0).__getattr__("rs").__getattr__("index").__call__(var2, var1.getlocal(2));
         var1.setlocal(2, var3);
         var3 = null;
      }

      var1.setline(705);
      var3 = var1.getlocal(0).__getattr__("row").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __len__$99(PyFrame var1, ThreadState var2) {
      var1.setline(707);
      PyObject var3 = var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("row"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$100(PyFrame var1, ThreadState var2) {
      var1.setline(709);
      PyObject var3 = var1.getglobal("str").__call__(var2, var1.getlocal(0).__getattr__("row"));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject ResultSet$101(PyFrame var1, ThreadState var2) {
      var1.setlocal("__module__", var1.getname("__name__"));
      var1.setline(712);
      PyObject[] var3 = new PyObject[]{new PyList(Py.EmptyObjects)};
      PyFunction var4 = new PyFunction(var1.f_globals, var3, __init__$102, (PyObject)null);
      var1.setlocal("__init__", var4);
      var3 = null;
      var1.setline(715);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, index$104, (PyObject)null);
      var1.setlocal("index", var4);
      var3 = null;
      var1.setline(717);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getitem__$105, (PyObject)null);
      var1.setlocal("__getitem__", var4);
      var3 = null;
      var1.setline(719);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __getslice__$106, (PyObject)null);
      var1.setlocal("__getslice__", var4);
      var3 = null;
      var1.setline(721);
      var3 = Py.EmptyObjects;
      var4 = new PyFunction(var1.f_globals, var3, __repr__$108, (PyObject)null);
      var1.setlocal("__repr__", var4);
      var3 = null;
      return var1.getf_locals();
   }

   public PyObject __init__$102(PyFrame var1, ThreadState var2) {
      var1.setline(713);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(713);
      PyObject[] var3 = Py.EmptyObjects;
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$103)), (PyObject)var1.getlocal(1));
      var1.getlocal(0).__setattr__("headers", var4);
      var3 = null;
      var1.setline(714);
      var4 = var1.getlocal(2);
      var1.getlocal(0).__setattr__("results", var4);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject f$103(PyFrame var1, ThreadState var2) {
      var1.setline(713);
      PyObject var3 = var1.getlocal(0).__getattr__("upper").__call__(var2);
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject index$104(PyFrame var1, ThreadState var2) {
      var1.setline(716);
      PyObject var3 = var1.getlocal(0).__getattr__("headers").__getattr__("index").__call__(var2, var1.getlocal(1).__getattr__("upper").__call__(var2));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getitem__$105(PyFrame var1, ThreadState var2) {
      var1.setline(718);
      PyObject var3 = var1.getglobal("ResultSetRow").__call__(var2, var1.getlocal(0), var1.getlocal(0).__getattr__("results").__getitem__(var1.getlocal(1)));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __getslice__$106(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      PyObject var10000 = var1.getglobal("map");
      var1.setline(720);
      PyObject[] var3 = new PyObject[]{var1.getlocal(0)};
      PyObject var4 = var10000.__call__((ThreadState)var2, (PyObject)(new PyFunction(var1.f_globals, var3, f$107)), (PyObject)var1.getlocal(0).__getattr__("results").__getslice__(var1.getlocal(1), var1.getlocal(2), (PyObject)null));
      var1.f_lasti = -1;
      return var4;
   }

   public PyObject f$107(PyFrame var1, ThreadState var2) {
      var1.setline(720);
      PyObject var3 = var1.getglobal("ResultSetRow").__call__(var2, var1.getlocal(1), var1.getlocal(0));
      var1.f_lasti = -1;
      return var3;
   }

   public PyObject __repr__$108(PyFrame var1, ThreadState var2) {
      var1.setline(722);
      PyObject var3 = PyString.fromInterned("<%s instance {cols [%d], rows [%d]} at %s>")._mod(new PyTuple(new PyObject[]{var1.getlocal(0).__getattr__("__class__"), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("headers")), var1.getglobal("len").__call__(var2, var1.getlocal(0).__getattr__("results")), var1.getglobal("id").__call__(var2, var1.getlocal(0))}));
      var1.f_lasti = -1;
      return var3;
   }

   public dbexts$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"bool", "a", "b"};
      f$1 = Py.newCode(3, var2, var1, "<lambda>", 52, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"rows", "headers", "output", "collen", "row", "i", "entry", "affected", "l", "j", "totallen"};
      console$2 = Py.newCode(2, var2, var1, "console", 54, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"header"};
      f$3 = Py.newCode(1, var2, var1, "<lambda>", 76, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$4 = Py.newCode(1, var2, var1, "<lambda>", 76, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$5 = Py.newCode(1, var2, var1, "<lambda>", 81, false, false, self, 5, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"rows", "headers", "output", "row"};
      html$6 = Py.newCode(2, var2, var1, "html", 108, false, false, self, 6, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$7 = Py.newCode(1, var2, var1, "<lambda>", 112, false, false, self, 7, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$8 = Py.newCode(1, var2, var1, "<lambda>", 118, false, false, self, 8, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$9 = Py.newCode(1, var2, var1, "<lambda>", 124, false, false, self, 9, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      mxODBCProxy$10 = Py.newCode(0, var2, var1, "mxODBCProxy", 126, false, false, self, 10, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "c"};
      __init__$11 = Py.newCode(2, var2, var1, "__init__", 128, false, false, self, 11, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$12 = Py.newCode(2, var2, var1, "__getattr__", 130, false, false, self, 12, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sql", "params", "bindings", "maxrows"};
      execute$13 = Py.newCode(5, var2, var1, "execute", 137, false, false, self, 13, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "typeid"};
      gettypeinfo$14 = Py.newCode(2, var2, var1, "gettypeinfo", 142, false, false, self, 14, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      executor$15 = Py.newCode(0, var2, var1, "executor", 146, false, false, self, 15, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "table", "cols"};
      __init__$16 = Py.newCode(3, var2, var1, "__init__", 148, false, false, self, 16, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "db", "rows", "bindings", "sql"};
      execute$17 = Py.newCode(4, var2, var1, "execute", 155, false, false, self, 17, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dbname"};
      connect$18 = Py.newCode(1, var2, var1, "connect", 163, false, false, self, 18, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"dbname"};
      lookup$19 = Py.newCode(1, var2, var1, "lookup", 166, false, false, self, 19, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      dbexts$20 = Py.newCode(0, var2, var1, "dbexts", 169, false, false, self, 20, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dbname", "cfg", "formatter", "autocommit", "jndiname", "out", "fn", "zxJDBC", "database", "t", "dbuser", "dbpwd", "jdbcdriver", "dh", "classname", "datahandlerclass", "keys", "_[205_24]", "x", "props", "a", "modname", "p"};
      __init__$21 = Py.newCode(7, var2, var1, "__init__", 170, false, false, self, 21, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$22 = Py.newCode(1, var2, var1, "__str__", 241, false, false, self, 22, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$23 = Py.newCode(1, var2, var1, "__repr__", 244, false, false, self, 23, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$24 = Py.newCode(2, var2, var1, "__getattr__", 247, false, false, self, 24, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      close$25 = Py.newCode(1, var2, var1, "close", 252, false, false, self, 25, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "style", "c", "dh"};
      begin$26 = Py.newCode(2, var2, var1, "begin", 256, false, false, self, 26, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cursor", "close", "s"};
      commit$27 = Py.newCode(3, var2, var1, "commit", 271, false, false, self, 27, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      rollback$28 = Py.newCode(1, var2, var1, "rollback", 290, false, false, self, 28, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sql", "cur"};
      prepare$29 = Py.newCode(2, var2, var1, "prepare", 294, false, false, self, 29, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "res", "a"};
      display$30 = Py.newCode(1, var2, var1, "display", 302, false, false, self, 30, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$31 = Py.newCode(1, var2, var1, "<lambda>", 308, false, false, self, 31, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sql", "params", "bindings", "maxrows", "cur"};
      __execute__$32 = Py.newCode(5, var2, var1, "__execute__", 312, false, false, self, 32, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sql", "params", "bindings", "maxrows"};
      isql$33 = Py.newCode(5, var2, var1, "isql", 325, false, false, self, 33, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sql", "params", "bindings", "delim", "comments", "maxrows", "headers", "results", "statements", "a"};
      raw$34 = Py.newCode(7, var2, var1, "raw", 330, false, false, self, 34, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$35 = Py.newCode(1, var2, var1, "<lambda>", 337, false, false, self, 35, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"statement"};
      f$36 = Py.newCode(1, var2, var1, "<lambda>", 338, false, false, self, 36, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "procname", "params", "bindings", "maxrows", "cur"};
      callproc$37 = Py.newCode(5, var2, var1, "callproc", 351, false, false, self, 37, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "table", "owner", "schema", "cur"};
      pk$38 = Py.newCode(4, var2, var1, "pk", 360, false, false, self, 38, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "primary_table", "foreign_table", "owner", "schema", "cur"};
      fk$39 = Py.newCode(5, var2, var1, "fk", 367, false, false, self, 39, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "table", "types", "owner", "schema", "cur"};
      table$40 = Py.newCode(5, var2, var1, "table", 379, false, false, self, 40, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "proc", "owner", "schema", "cur"};
      proc$41 = Py.newCode(4, var2, var1, "proc", 390, false, false, self, 41, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "table", "qualifier", "owner", "unique", "accuracy", "cur"};
      stat$42 = Py.newCode(6, var2, var1, "stat", 401, false, false, self, 42, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sqltype", "cur"};
      typeinfo$43 = Py.newCode(2, var2, var1, "typeinfo", 408, false, false, self, 43, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cur"};
      tabletypeinfo$44 = Py.newCode(1, var2, var1, "tabletypeinfo", 415, false, false, self, 44, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "table", "full", "sort", "owner"};
      schema$45 = Py.newCode(5, var2, var1, "schema", 422, false, false, self, 45, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "dst", "table", "include", "exclude", "autobatch", "executor", "bcp"};
      bulkcopy$46 = Py.newCode(7, var2, var1, "bulkcopy", 428, false, false, self, 46, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "src", "table", "where", "params", "include", "exclude", "autobatch", "executor", "bcp", "num"};
      bcp$47 = Py.newCode(9, var2, var1, "bcp", 435, false, false, self, 47, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "filename", "sql", "delimiter", "includeheaders", "u"};
      unload$48 = Py.newCode(5, var2, var1, "unload", 443, false, false, self, 48, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Bulkcopy$49 = Py.newCode(0, var2, var1, "Bulkcopy", 448, false, false, self, 49, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "dst", "table", "include", "exclude", "autobatch", "executor", "_verbose", "colmap", "a", "cols"};
      __init__$50 = Py.newCode(7, var2, var1, "__init__", 450, false, false, self, 50, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$51 = Py.newCode(1, var2, var1, "<lambda>", 458, false, false, self, 51, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$52 = Py.newCode(1, var2, var1, "<lambda>", 459, false, false, self, 52, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __str__$53 = Py.newCode(1, var2, var1, "__str__", 480, false, false, self, 53, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$54 = Py.newCode(1, var2, var1, "__repr__", 483, false, false, self, 54, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "name"};
      __getattr__$55 = Py.newCode(2, var2, var1, "__getattr__", 486, false, false, self, 55, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "values", "include", "exclude", "cols"};
      __filter__$56 = Py.newCode(4, var2, var1, "__filter__", 490, false, false, self, 56, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"col"};
      f$57 = Py.newCode(1, var2, var1, "<lambda>", 491, false, false, self, 57, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "ex"};
      f$58 = Py.newCode(2, var2, var1, "<lambda>", 493, false, false, self, 58, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "inc"};
      f$59 = Py.newCode(2, var2, var1, "<lambda>", 495, false, false, self, 59, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "column", "type"};
      format$60 = Py.newCode(3, var2, var1, "format", 498, false, false, self, 60, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      done$61 = Py.newCode(1, var2, var1, "done", 501, false, false, self, 61, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "cnt"};
      batch$62 = Py.newCode(1, var2, var1, "batch", 506, false, false, self, 62, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "line"};
      rowxfer$63 = Py.newCode(2, var2, var1, "rowxfer", 513, false, false, self, 63, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "src", "where", "params", "sql", "h", "d"};
      transfer$64 = Py.newCode(4, var2, var1, "transfer", 517, false, false, self, 64, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Unload$65 = Py.newCode(0, var2, var1, "Unload", 525, false, false, self, 65, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "db", "filename", "delimiter", "includeheaders"};
      __init__$66 = Py.newCode(5, var2, var1, "__init__", 527, false, false, self, 66, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "o"};
      format$67 = Py.newCode(2, var2, var1, "format", 534, false, false, self, 67, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "sql", "mode", "headers", "results", "w", "a"};
      unload$68 = Py.newCode(3, var2, var1, "unload", 542, false, false, self, 68, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$69 = Py.newCode(1, var2, var1, "<lambda>", 546, false, false, self, 69, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      Schema$70 = Py.newCode(0, var2, var1, "Schema", 553, false, false, self, 70, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "db", "table", "owner", "full", "sort", "_verbose"};
      __init__$71 = Py.newCode(6, var2, var1, "__init__", 555, false, false, self, 71, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "idxdict", "idx", "cckmp", "a"};
      computeschema$72 = Py.newCode(1, var2, var1, "computeschema", 568, false, false, self, 72, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$73 = Py.newCode(1, var2, var1, "<lambda>", 573, false, false, self, 73, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      f$74 = Py.newCode(2, var2, var1, "<lambda>", 574, false, false, self, 74, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$75 = Py.newCode(1, var2, var1, "<lambda>", 580, false, false, self, 75, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      f$76 = Py.newCode(2, var2, var1, "<lambda>", 581, false, false, self, 76, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$77 = Py.newCode(1, var2, var1, "<lambda>", 588, false, false, self, 77, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      f$78 = Py.newCode(2, var2, var1, "<lambda>", 589, false, false, self, 78, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$79 = Py.newCode(1, var2, var1, "<lambda>", 595, false, false, self, 79, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      f$80 = Py.newCode(2, var2, var1, "<lambda>", 596, false, false, self, 80, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$81 = Py.newCode(1, var2, var1, "<lambda>", 606, false, false, self, 81, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$82 = Py.newCode(1, var2, var1, "<lambda>", 606, false, false, self, 82, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y", "c"};
      cckmp$83 = Py.newCode(2, var2, var1, "cckmp", 607, false, false, self, 83, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "y"};
      f$84 = Py.newCode(2, var2, var1, "<lambda>", 618, false, false, self, 84, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "d", "a", "nullable", "unique", "cname"};
      __str__$85 = Py.newCode(1, var2, var1, "__str__", 622, false, false, self, 85, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$86 = Py.newCode(1, var2, var1, "<lambda>", 646, false, false, self, 86, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      IniParser$87 = Py.newCode(0, var2, var1, "IniParser", 650, false, false, self, 87, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "cfg", "key"};
      __init__$88 = Py.newCode(3, var2, var1, "__init__", 651, false, false, self, 88, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "fp", "data", "lines", "current", "i", "line", "g"};
      parse$89 = Py.newCode(1, var2, var1, "parse", 659, false, false, self, 89, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$90 = Py.newCode(1, var2, var1, "<lambda>", 663, false, false, self, 90, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$91 = Py.newCode(1, var2, var1, "<lambda>", 663, false, false, self, 91, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "(ctype, skey)", "ctype", "skey", "t"};
      __getitem__$92 = Py.newCode(2, var2, var1, "__getitem__", 678, false, false, self, 92, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "p", "s"};
      f$93 = Py.newCode(3, var2, var1, "<lambda>", 680, false, false, self, 93, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"prefix", "num_chars", "random", "d", "i"};
      random_table_name$94 = Py.newCode(2, var2, var1, "random_table_name", 685, false, false, self, 94, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ResultSetRow$95 = Py.newCode(0, var2, var1, "ResultSetRow", 694, false, false, self, 95, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "rs", "row"};
      __init__$96 = Py.newCode(3, var2, var1, "__init__", 695, false, false, self, 96, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      __getitem__$97 = Py.newCode(2, var2, var1, "__getitem__", 698, false, false, self, 97, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j"};
      __getslice__$98 = Py.newCode(3, var2, var1, "__getslice__", 702, false, false, self, 98, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __len__$99 = Py.newCode(1, var2, var1, "__len__", 706, false, false, self, 99, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$100 = Py.newCode(1, var2, var1, "__repr__", 708, false, false, self, 100, (String[])null, (String[])null, 0, 4097);
      var2 = new String[0];
      ResultSet$101 = Py.newCode(0, var2, var1, "ResultSet", 711, false, false, self, 101, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"self", "headers", "results"};
      __init__$102 = Py.newCode(3, var2, var1, "__init__", 712, false, false, self, 102, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x"};
      f$103 = Py.newCode(1, var2, var1, "<lambda>", 713, false, false, self, 103, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      index$104 = Py.newCode(2, var2, var1, "index", 715, false, false, self, 104, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i"};
      __getitem__$105 = Py.newCode(2, var2, var1, "__getitem__", 717, false, false, self, 105, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self", "i", "j"};
      __getslice__$106 = Py.newCode(3, var2, var1, "__getslice__", 719, false, false, self, 106, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"x", "rs"};
      f$107 = Py.newCode(2, var2, var1, "<lambda>", 720, false, false, self, 107, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"self"};
      __repr__$108 = Py.newCode(1, var2, var1, "__repr__", 721, false, false, self, 108, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new dbexts$py("dbexts$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(dbexts$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.f$1(var2, var3);
         case 2:
            return this.console$2(var2, var3);
         case 3:
            return this.f$3(var2, var3);
         case 4:
            return this.f$4(var2, var3);
         case 5:
            return this.f$5(var2, var3);
         case 6:
            return this.html$6(var2, var3);
         case 7:
            return this.f$7(var2, var3);
         case 8:
            return this.f$8(var2, var3);
         case 9:
            return this.f$9(var2, var3);
         case 10:
            return this.mxODBCProxy$10(var2, var3);
         case 11:
            return this.__init__$11(var2, var3);
         case 12:
            return this.__getattr__$12(var2, var3);
         case 13:
            return this.execute$13(var2, var3);
         case 14:
            return this.gettypeinfo$14(var2, var3);
         case 15:
            return this.executor$15(var2, var3);
         case 16:
            return this.__init__$16(var2, var3);
         case 17:
            return this.execute$17(var2, var3);
         case 18:
            return this.connect$18(var2, var3);
         case 19:
            return this.lookup$19(var2, var3);
         case 20:
            return this.dbexts$20(var2, var3);
         case 21:
            return this.__init__$21(var2, var3);
         case 22:
            return this.__str__$22(var2, var3);
         case 23:
            return this.__repr__$23(var2, var3);
         case 24:
            return this.__getattr__$24(var2, var3);
         case 25:
            return this.close$25(var2, var3);
         case 26:
            return this.begin$26(var2, var3);
         case 27:
            return this.commit$27(var2, var3);
         case 28:
            return this.rollback$28(var2, var3);
         case 29:
            return this.prepare$29(var2, var3);
         case 30:
            return this.display$30(var2, var3);
         case 31:
            return this.f$31(var2, var3);
         case 32:
            return this.__execute__$32(var2, var3);
         case 33:
            return this.isql$33(var2, var3);
         case 34:
            return this.raw$34(var2, var3);
         case 35:
            return this.f$35(var2, var3);
         case 36:
            return this.f$36(var2, var3);
         case 37:
            return this.callproc$37(var2, var3);
         case 38:
            return this.pk$38(var2, var3);
         case 39:
            return this.fk$39(var2, var3);
         case 40:
            return this.table$40(var2, var3);
         case 41:
            return this.proc$41(var2, var3);
         case 42:
            return this.stat$42(var2, var3);
         case 43:
            return this.typeinfo$43(var2, var3);
         case 44:
            return this.tabletypeinfo$44(var2, var3);
         case 45:
            return this.schema$45(var2, var3);
         case 46:
            return this.bulkcopy$46(var2, var3);
         case 47:
            return this.bcp$47(var2, var3);
         case 48:
            return this.unload$48(var2, var3);
         case 49:
            return this.Bulkcopy$49(var2, var3);
         case 50:
            return this.__init__$50(var2, var3);
         case 51:
            return this.f$51(var2, var3);
         case 52:
            return this.f$52(var2, var3);
         case 53:
            return this.__str__$53(var2, var3);
         case 54:
            return this.__repr__$54(var2, var3);
         case 55:
            return this.__getattr__$55(var2, var3);
         case 56:
            return this.__filter__$56(var2, var3);
         case 57:
            return this.f$57(var2, var3);
         case 58:
            return this.f$58(var2, var3);
         case 59:
            return this.f$59(var2, var3);
         case 60:
            return this.format$60(var2, var3);
         case 61:
            return this.done$61(var2, var3);
         case 62:
            return this.batch$62(var2, var3);
         case 63:
            return this.rowxfer$63(var2, var3);
         case 64:
            return this.transfer$64(var2, var3);
         case 65:
            return this.Unload$65(var2, var3);
         case 66:
            return this.__init__$66(var2, var3);
         case 67:
            return this.format$67(var2, var3);
         case 68:
            return this.unload$68(var2, var3);
         case 69:
            return this.f$69(var2, var3);
         case 70:
            return this.Schema$70(var2, var3);
         case 71:
            return this.__init__$71(var2, var3);
         case 72:
            return this.computeschema$72(var2, var3);
         case 73:
            return this.f$73(var2, var3);
         case 74:
            return this.f$74(var2, var3);
         case 75:
            return this.f$75(var2, var3);
         case 76:
            return this.f$76(var2, var3);
         case 77:
            return this.f$77(var2, var3);
         case 78:
            return this.f$78(var2, var3);
         case 79:
            return this.f$79(var2, var3);
         case 80:
            return this.f$80(var2, var3);
         case 81:
            return this.f$81(var2, var3);
         case 82:
            return this.f$82(var2, var3);
         case 83:
            return this.cckmp$83(var2, var3);
         case 84:
            return this.f$84(var2, var3);
         case 85:
            return this.__str__$85(var2, var3);
         case 86:
            return this.f$86(var2, var3);
         case 87:
            return this.IniParser$87(var2, var3);
         case 88:
            return this.__init__$88(var2, var3);
         case 89:
            return this.parse$89(var2, var3);
         case 90:
            return this.f$90(var2, var3);
         case 91:
            return this.f$91(var2, var3);
         case 92:
            return this.__getitem__$92(var2, var3);
         case 93:
            return this.f$93(var2, var3);
         case 94:
            return this.random_table_name$94(var2, var3);
         case 95:
            return this.ResultSetRow$95(var2, var3);
         case 96:
            return this.__init__$96(var2, var3);
         case 97:
            return this.__getitem__$97(var2, var3);
         case 98:
            return this.__getslice__$98(var2, var3);
         case 99:
            return this.__len__$99(var2, var3);
         case 100:
            return this.__repr__$100(var2, var3);
         case 101:
            return this.ResultSet$101(var2, var3);
         case 102:
            return this.__init__$102(var2, var3);
         case 103:
            return this.f$103(var2, var3);
         case 104:
            return this.index$104(var2, var3);
         case 105:
            return this.__getitem__$105(var2, var3);
         case 106:
            return this.__getslice__$106(var2, var3);
         case 107:
            return this.f$107(var2, var3);
         case 108:
            return this.__repr__$108(var2, var3);
         default:
            return null;
      }
   }
}
