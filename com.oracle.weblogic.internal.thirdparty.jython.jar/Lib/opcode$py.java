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

@APIVersion(37)
@MTime(1498849383000L)
@Filename("opcode.py")
public class opcode$py extends PyFunctionTable implements PyRunnable {
   static opcode$py self;
   static final PyCode f$0;
   static final PyCode def_op$1;
   static final PyCode name_op$2;
   static final PyCode jrel_op$3;
   static final PyCode jabs_op$4;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setglobal("__doc__", PyString.fromInterned("\nopcode module - potentially shared between dis and other modules which\noperate on bytecodes (e.g. peephole optimizers).\n"));
      var1.setline(5);
      PyString.fromInterned("\nopcode module - potentially shared between dis and other modules which\noperate on bytecodes (e.g. peephole optimizers).\n");
      var1.setline(7);
      PyList var3 = new PyList(new PyObject[]{PyString.fromInterned("cmp_op"), PyString.fromInterned("hasconst"), PyString.fromInterned("hasname"), PyString.fromInterned("hasjrel"), PyString.fromInterned("hasjabs"), PyString.fromInterned("haslocal"), PyString.fromInterned("hascompare"), PyString.fromInterned("hasfree"), PyString.fromInterned("opname"), PyString.fromInterned("opmap"), PyString.fromInterned("HAVE_ARGUMENT"), PyString.fromInterned("EXTENDED_ARG")});
      var1.setlocal("__all__", var3);
      var3 = null;
      var1.setline(11);
      PyTuple var6 = new PyTuple(new PyObject[]{PyString.fromInterned("<"), PyString.fromInterned("<="), PyString.fromInterned("=="), PyString.fromInterned("!="), PyString.fromInterned(">"), PyString.fromInterned(">="), PyString.fromInterned("in"), PyString.fromInterned("not in"), PyString.fromInterned("is"), PyString.fromInterned("is not"), PyString.fromInterned("exception match"), PyString.fromInterned("BAD")});
      var1.setlocal("cmp_op", var6);
      var3 = null;
      var1.setline(14);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("hasconst", var3);
      var3 = null;
      var1.setline(15);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("hasname", var3);
      var3 = null;
      var1.setline(16);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("hasjrel", var3);
      var3 = null;
      var1.setline(17);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("hasjabs", var3);
      var3 = null;
      var1.setline(18);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("haslocal", var3);
      var3 = null;
      var1.setline(19);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("hascompare", var3);
      var3 = null;
      var1.setline(20);
      var3 = new PyList(Py.EmptyObjects);
      var1.setlocal("hasfree", var3);
      var3 = null;
      var1.setline(22);
      PyDictionary var7 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("opmap", var7);
      var3 = null;
      var1.setline(23);
      PyObject var8 = (new PyList(new PyObject[]{PyString.fromInterned("")}))._mul(Py.newInteger(256));
      var1.setlocal("opname", var8);
      var3 = null;
      var1.setline(24);
      var8 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(256)).__iter__();

      while(true) {
         var1.setline(24);
         PyObject var4 = var8.__iternext__();
         if (var4 == null) {
            var1.setline(25);
            var1.dellocal("op");
            var1.setline(27);
            PyObject[] var9 = Py.EmptyObjects;
            PyFunction var10 = new PyFunction(var1.f_globals, var9, def_op$1, (PyObject)null);
            var1.setlocal("def_op", var10);
            var3 = null;
            var1.setline(31);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, name_op$2, (PyObject)null);
            var1.setlocal("name_op", var10);
            var3 = null;
            var1.setline(35);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, jrel_op$3, (PyObject)null);
            var1.setlocal("jrel_op", var10);
            var3 = null;
            var1.setline(39);
            var9 = Py.EmptyObjects;
            var10 = new PyFunction(var1.f_globals, var9, jabs_op$4, (PyObject)null);
            var1.setlocal("jabs_op", var10);
            var3 = null;
            var1.setline(46);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STOP_CODE"), (PyObject)Py.newInteger(0));
            var1.setline(47);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_TOP"), (PyObject)Py.newInteger(1));
            var1.setline(48);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_TWO"), (PyObject)Py.newInteger(2));
            var1.setline(49);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_THREE"), (PyObject)Py.newInteger(3));
            var1.setline(50);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOP"), (PyObject)Py.newInteger(4));
            var1.setline(51);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("ROT_FOUR"), (PyObject)Py.newInteger(5));
            var1.setline(53);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("NOP"), (PyObject)Py.newInteger(9));
            var1.setline(54);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNARY_POSITIVE"), (PyObject)Py.newInteger(10));
            var1.setline(55);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNARY_NEGATIVE"), (PyObject)Py.newInteger(11));
            var1.setline(56);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNARY_NOT"), (PyObject)Py.newInteger(12));
            var1.setline(57);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNARY_CONVERT"), (PyObject)Py.newInteger(13));
            var1.setline(59);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNARY_INVERT"), (PyObject)Py.newInteger(15));
            var1.setline(61);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_POWER"), (PyObject)Py.newInteger(19));
            var1.setline(62);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_MULTIPLY"), (PyObject)Py.newInteger(20));
            var1.setline(63);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_DIVIDE"), (PyObject)Py.newInteger(21));
            var1.setline(64);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_MODULO"), (PyObject)Py.newInteger(22));
            var1.setline(65);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_ADD"), (PyObject)Py.newInteger(23));
            var1.setline(66);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_SUBTRACT"), (PyObject)Py.newInteger(24));
            var1.setline(67);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_SUBSCR"), (PyObject)Py.newInteger(25));
            var1.setline(68);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_FLOOR_DIVIDE"), (PyObject)Py.newInteger(26));
            var1.setline(69);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_TRUE_DIVIDE"), (PyObject)Py.newInteger(27));
            var1.setline(70);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_FLOOR_DIVIDE"), (PyObject)Py.newInteger(28));
            var1.setline(71);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_TRUE_DIVIDE"), (PyObject)Py.newInteger(29));
            var1.setline(72);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SLICE+0"), (PyObject)Py.newInteger(30));
            var1.setline(73);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SLICE+1"), (PyObject)Py.newInteger(31));
            var1.setline(74);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SLICE+2"), (PyObject)Py.newInteger(32));
            var1.setline(75);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SLICE+3"), (PyObject)Py.newInteger(33));
            var1.setline(77);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SLICE+0"), (PyObject)Py.newInteger(40));
            var1.setline(78);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SLICE+1"), (PyObject)Py.newInteger(41));
            var1.setline(79);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SLICE+2"), (PyObject)Py.newInteger(42));
            var1.setline(80);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SLICE+3"), (PyObject)Py.newInteger(43));
            var1.setline(82);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_SLICE+0"), (PyObject)Py.newInteger(50));
            var1.setline(83);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_SLICE+1"), (PyObject)Py.newInteger(51));
            var1.setline(84);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_SLICE+2"), (PyObject)Py.newInteger(52));
            var1.setline(85);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_SLICE+3"), (PyObject)Py.newInteger(53));
            var1.setline(87);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_MAP"), (PyObject)Py.newInteger(54));
            var1.setline(88);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_ADD"), (PyObject)Py.newInteger(55));
            var1.setline(89);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_SUBTRACT"), (PyObject)Py.newInteger(56));
            var1.setline(90);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_MULTIPLY"), (PyObject)Py.newInteger(57));
            var1.setline(91);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_DIVIDE"), (PyObject)Py.newInteger(58));
            var1.setline(92);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_MODULO"), (PyObject)Py.newInteger(59));
            var1.setline(93);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_SUBSCR"), (PyObject)Py.newInteger(60));
            var1.setline(94);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_SUBSCR"), (PyObject)Py.newInteger(61));
            var1.setline(95);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_LSHIFT"), (PyObject)Py.newInteger(62));
            var1.setline(96);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_RSHIFT"), (PyObject)Py.newInteger(63));
            var1.setline(97);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_AND"), (PyObject)Py.newInteger(64));
            var1.setline(98);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_XOR"), (PyObject)Py.newInteger(65));
            var1.setline(99);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BINARY_OR"), (PyObject)Py.newInteger(66));
            var1.setline(100);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_POWER"), (PyObject)Py.newInteger(67));
            var1.setline(101);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("GET_ITER"), (PyObject)Py.newInteger(68));
            var1.setline(103);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_EXPR"), (PyObject)Py.newInteger(70));
            var1.setline(104);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_ITEM"), (PyObject)Py.newInteger(71));
            var1.setline(105);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_NEWLINE"), (PyObject)Py.newInteger(72));
            var1.setline(106);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_ITEM_TO"), (PyObject)Py.newInteger(73));
            var1.setline(107);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("PRINT_NEWLINE_TO"), (PyObject)Py.newInteger(74));
            var1.setline(108);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_LSHIFT"), (PyObject)Py.newInteger(75));
            var1.setline(109);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_RSHIFT"), (PyObject)Py.newInteger(76));
            var1.setline(110);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_AND"), (PyObject)Py.newInteger(77));
            var1.setline(111);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_XOR"), (PyObject)Py.newInteger(78));
            var1.setline(112);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("INPLACE_OR"), (PyObject)Py.newInteger(79));
            var1.setline(113);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BREAK_LOOP"), (PyObject)Py.newInteger(80));
            var1.setline(114);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("WITH_CLEANUP"), (PyObject)Py.newInteger(81));
            var1.setline(115);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_LOCALS"), (PyObject)Py.newInteger(82));
            var1.setline(116);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RETURN_VALUE"), (PyObject)Py.newInteger(83));
            var1.setline(117);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_STAR"), (PyObject)Py.newInteger(84));
            var1.setline(118);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EXEC_STMT"), (PyObject)Py.newInteger(85));
            var1.setline(119);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("YIELD_VALUE"), (PyObject)Py.newInteger(86));
            var1.setline(120);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_BLOCK"), (PyObject)Py.newInteger(87));
            var1.setline(121);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("END_FINALLY"), (PyObject)Py.newInteger(88));
            var1.setline(122);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_CLASS"), (PyObject)Py.newInteger(89));
            var1.setline(124);
            PyInteger var11 = Py.newInteger(90);
            var1.setlocal("HAVE_ARGUMENT", var11);
            var3 = null;
            var1.setline(126);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_NAME"), (PyObject)Py.newInteger(90));
            var1.setline(127);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_NAME"), (PyObject)Py.newInteger(91));
            var1.setline(128);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("UNPACK_SEQUENCE"), (PyObject)Py.newInteger(92));
            var1.setline(129);
            var1.getname("jrel_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("FOR_ITER"), (PyObject)Py.newInteger(93));
            var1.setline(130);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LIST_APPEND"), (PyObject)Py.newInteger(94));
            var1.setline(131);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_ATTR"), (PyObject)Py.newInteger(95));
            var1.setline(132);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_ATTR"), (PyObject)Py.newInteger(96));
            var1.setline(133);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_GLOBAL"), (PyObject)Py.newInteger(97));
            var1.setline(134);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_GLOBAL"), (PyObject)Py.newInteger(98));
            var1.setline(135);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DUP_TOPX"), (PyObject)Py.newInteger(99));
            var1.setline(136);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CONST"), (PyObject)Py.newInteger(100));
            var1.setline(137);
            var1.getname("hasconst").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(100));
            var1.setline(138);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_NAME"), (PyObject)Py.newInteger(101));
            var1.setline(139);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_TUPLE"), (PyObject)Py.newInteger(102));
            var1.setline(140);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_LIST"), (PyObject)Py.newInteger(103));
            var1.setline(141);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_SET"), (PyObject)Py.newInteger(104));
            var1.setline(142);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_MAP"), (PyObject)Py.newInteger(105));
            var1.setline(143);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_ATTR"), (PyObject)Py.newInteger(106));
            var1.setline(144);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("COMPARE_OP"), (PyObject)Py.newInteger(107));
            var1.setline(145);
            var1.getname("hascompare").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(107));
            var1.setline(146);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_NAME"), (PyObject)Py.newInteger(108));
            var1.setline(147);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("IMPORT_FROM"), (PyObject)Py.newInteger(109));
            var1.setline(148);
            var1.getname("jrel_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_FORWARD"), (PyObject)Py.newInteger(110));
            var1.setline(149);
            var1.getname("jabs_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_FALSE_OR_POP"), (PyObject)Py.newInteger(111));
            var1.setline(150);
            var1.getname("jabs_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_IF_TRUE_OR_POP"), (PyObject)Py.newInteger(112));
            var1.setline(151);
            var1.getname("jabs_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("JUMP_ABSOLUTE"), (PyObject)Py.newInteger(113));
            var1.setline(152);
            var1.getname("jabs_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_JUMP_IF_FALSE"), (PyObject)Py.newInteger(114));
            var1.setline(153);
            var1.getname("jabs_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("POP_JUMP_IF_TRUE"), (PyObject)Py.newInteger(115));
            var1.setline(155);
            var1.getname("name_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_GLOBAL"), (PyObject)Py.newInteger(116));
            var1.setline(157);
            var1.getname("jabs_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CONTINUE_LOOP"), (PyObject)Py.newInteger(119));
            var1.setline(158);
            var1.getname("jrel_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_LOOP"), (PyObject)Py.newInteger(120));
            var1.setline(159);
            var1.getname("jrel_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_EXCEPT"), (PyObject)Py.newInteger(121));
            var1.setline(160);
            var1.getname("jrel_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_FINALLY"), (PyObject)Py.newInteger(122));
            var1.setline(162);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_FAST"), (PyObject)Py.newInteger(124));
            var1.setline(163);
            var1.getname("haslocal").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(124));
            var1.setline(164);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_FAST"), (PyObject)Py.newInteger(125));
            var1.setline(165);
            var1.getname("haslocal").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(125));
            var1.setline(166);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("DELETE_FAST"), (PyObject)Py.newInteger(126));
            var1.setline(167);
            var1.getname("haslocal").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(126));
            var1.setline(169);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("RAISE_VARARGS"), (PyObject)Py.newInteger(130));
            var1.setline(170);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION"), (PyObject)Py.newInteger(131));
            var1.setline(171);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MAKE_FUNCTION"), (PyObject)Py.newInteger(132));
            var1.setline(172);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("BUILD_SLICE"), (PyObject)Py.newInteger(133));
            var1.setline(173);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MAKE_CLOSURE"), (PyObject)Py.newInteger(134));
            var1.setline(174);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_CLOSURE"), (PyObject)Py.newInteger(135));
            var1.setline(175);
            var1.getname("hasfree").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(135));
            var1.setline(176);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("LOAD_DEREF"), (PyObject)Py.newInteger(136));
            var1.setline(177);
            var1.getname("hasfree").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(136));
            var1.setline(178);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("STORE_DEREF"), (PyObject)Py.newInteger(137));
            var1.setline(179);
            var1.getname("hasfree").__getattr__("append").__call__((ThreadState)var2, (PyObject)Py.newInteger(137));
            var1.setline(181);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION_VAR"), (PyObject)Py.newInteger(140));
            var1.setline(182);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION_KW"), (PyObject)Py.newInteger(141));
            var1.setline(183);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("CALL_FUNCTION_VAR_KW"), (PyObject)Py.newInteger(142));
            var1.setline(185);
            var1.getname("jrel_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SETUP_WITH"), (PyObject)Py.newInteger(143));
            var1.setline(187);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("EXTENDED_ARG"), (PyObject)Py.newInteger(145));
            var1.setline(188);
            var11 = Py.newInteger(145);
            var1.setlocal("EXTENDED_ARG", var11);
            var3 = null;
            var1.setline(189);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("SET_ADD"), (PyObject)Py.newInteger(146));
            var1.setline(190);
            var1.getname("def_op").__call__((ThreadState)var2, (PyObject)PyString.fromInterned("MAP_ADD"), (PyObject)Py.newInteger(147));
            var1.setline(192);
            var1.dellocal("def_op");
            var1.dellocal("name_op");
            var1.dellocal("jrel_op");
            var1.dellocal("jabs_op");
            var1.f_lasti = -1;
            return Py.None;
         }

         var1.setlocal("op", var4);
         var1.setline(24);
         PyObject var5 = PyString.fromInterned("<%r>")._mod(new PyTuple(new PyObject[]{var1.getname("op")}));
         var1.getname("opname").__setitem__(var1.getname("op"), var5);
         var5 = null;
      }
   }

   public PyObject def_op$1(PyFrame var1, ThreadState var2) {
      var1.setline(28);
      PyObject var3 = var1.getlocal(0);
      var1.getglobal("opname").__setitem__(var1.getlocal(1), var3);
      var3 = null;
      var1.setline(29);
      var3 = var1.getlocal(1);
      var1.getglobal("opmap").__setitem__(var1.getlocal(0), var3);
      var3 = null;
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject name_op$2(PyFrame var1, ThreadState var2) {
      var1.setline(32);
      var1.getglobal("def_op").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(33);
      var1.getglobal("hasname").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject jrel_op$3(PyFrame var1, ThreadState var2) {
      var1.setline(36);
      var1.getglobal("def_op").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(37);
      var1.getglobal("hasjrel").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public PyObject jabs_op$4(PyFrame var1, ThreadState var2) {
      var1.setline(40);
      var1.getglobal("def_op").__call__(var2, var1.getlocal(0), var1.getlocal(1));
      var1.setline(41);
      var1.getglobal("hasjabs").__getattr__("append").__call__(var2, var1.getlocal(1));
      var1.f_lasti = -1;
      return Py.None;
   }

   public opcode$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
      var2 = new String[]{"name", "op"};
      def_op$1 = Py.newCode(2, var2, var1, "def_op", 27, false, false, self, 1, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "op"};
      name_op$2 = Py.newCode(2, var2, var1, "name_op", 31, false, false, self, 2, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "op"};
      jrel_op$3 = Py.newCode(2, var2, var1, "jrel_op", 35, false, false, self, 3, (String[])null, (String[])null, 0, 4097);
      var2 = new String[]{"name", "op"};
      jabs_op$4 = Py.newCode(2, var2, var1, "jabs_op", 39, false, false, self, 4, (String[])null, (String[])null, 0, 4097);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new opcode$py("opcode$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(opcode$py.class);
   }

   public PyObject call_function(int var1, PyFrame var2, ThreadState var3) {
      switch (var1) {
         case 0:
            return this.f$0(var2, var3);
         case 1:
            return this.def_op$1(var2, var3);
         case 2:
            return this.name_op$2(var2, var3);
         case 3:
            return this.jrel_op$3(var2, var3);
         case 4:
            return this.jabs_op$4(var2, var3);
         default:
            return null;
      }
   }
}
