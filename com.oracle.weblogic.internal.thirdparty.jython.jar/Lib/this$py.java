import org.python.compiler.APIVersion;
import org.python.compiler.Filename;
import org.python.compiler.MTime;
import org.python.core.CodeBootstrap;
import org.python.core.CodeLoader;
import org.python.core.Py;
import org.python.core.PyCode;
import org.python.core.PyDictionary;
import org.python.core.PyFrame;
import org.python.core.PyFunctionTable;
import org.python.core.PyList;
import org.python.core.PyObject;
import org.python.core.PyRunnable;
import org.python.core.PyRunnableBootstrap;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.core.ThreadState;

@APIVersion(37)
@MTime(1498849383000L)
@Filename("this.py")
public class this$py extends PyFunctionTable implements PyRunnable {
   static this$py self;
   static final PyCode f$0;

   public PyObject f$0(PyFrame var1, ThreadState var2) {
      var1.setline(1);
      PyString var3 = PyString.fromInterned("Gur Mra bs Clguba, ol Gvz Crgref\n\nOrnhgvshy vf orggre guna htyl.\nRkcyvpvg vf orggre guna vzcyvpvg.\nFvzcyr vf orggre guna pbzcyrk.\nPbzcyrk vf orggre guna pbzcyvpngrq.\nSyng vf orggre guna arfgrq.\nFcnefr vf orggre guna qrafr.\nErnqnovyvgl pbhagf.\nFcrpvny pnfrf nera'g fcrpvny rabhtu gb oernx gur ehyrf.\nNygubhtu cenpgvpnyvgl orngf chevgl.\nReebef fubhyq arire cnff fvyragyl.\nHayrff rkcyvpvgyl fvyraprq.\nVa gur snpr bs nzovthvgl, ershfr gur grzcgngvba gb thrff.\nGurer fubhyq or bar-- naq cersrenoyl bayl bar --boivbhf jnl gb qb vg.\nNygubhtu gung jnl znl abg or boivbhf ng svefg hayrff lbh'er Qhgpu.\nAbj vf orggre guna arire.\nNygubhtu arire vf bsgra orggre guna *evtug* abj.\nVs gur vzcyrzragngvba vf uneq gb rkcynva, vg'f n onq vqrn.\nVs gur vzcyrzragngvba vf rnfl gb rkcynva, vg znl or n tbbq vqrn.\nAnzrfcnprf ner bar ubaxvat terng vqrn -- yrg'f qb zber bs gubfr!");
      var1.setlocal("s", var3);
      var3 = null;
      var1.setline(23);
      PyDictionary var8 = new PyDictionary(Py.EmptyObjects);
      var1.setlocal("d", var8);
      var3 = null;
      var1.setline(24);
      PyObject var9 = (new PyTuple(new PyObject[]{Py.newInteger(65), Py.newInteger(97)})).__iter__();

      while(true) {
         var1.setline(24);
         PyObject var4 = var9.__iternext__();
         if (var4 == null) {
            var1.setline(28);
            PyObject var10000 = PyString.fromInterned("").__getattr__("join");
            PyList var10002 = new PyList();
            var9 = var10002.__getattr__("append");
            var1.setlocal("_[28_15]", var9);
            var3 = null;
            var1.setline(28);
            var9 = var1.getname("s").__iter__();

            while(true) {
               var1.setline(28);
               var4 = var9.__iternext__();
               if (var4 == null) {
                  var1.setline(28);
                  var1.dellocal("_[28_15]");
                  Py.println(var10000.__call__((ThreadState)var2, (PyObject)var10002));
                  var1.f_lasti = -1;
                  return Py.None;
               }

               var1.setlocal("c", var4);
               var1.setline(28);
               var1.getname("_[28_15]").__call__(var2, var1.getname("d").__getattr__("get").__call__(var2, var1.getname("c"), var1.getname("c")));
            }
         }

         var1.setlocal("c", var4);
         var1.setline(25);
         PyObject var5 = var1.getname("range").__call__((ThreadState)var2, (PyObject)Py.newInteger(26)).__iter__();

         while(true) {
            var1.setline(25);
            PyObject var6 = var5.__iternext__();
            if (var6 == null) {
               break;
            }

            var1.setlocal("i", var6);
            var1.setline(26);
            PyObject var7 = var1.getname("chr").__call__(var2, var1.getname("i")._add(Py.newInteger(13))._mod(Py.newInteger(26))._add(var1.getname("c")));
            var1.getname("d").__setitem__(var1.getname("chr").__call__(var2, var1.getname("i")._add(var1.getname("c"))), var7);
            var7 = null;
         }
      }
   }

   public this$py(String var1) {
      self = this;
      String[] var2 = new String[0];
      f$0 = Py.newCode(0, var2, var1, "<module>", 0, false, false, self, 0, (String[])null, (String[])null, 0, 4096);
   }

   public PyCode getMain() {
      return f$0;
   }

   public static void main(String[] var0) {
      Py.runMain(CodeLoader.createSimpleBootstrap((new this$py("this$py")).getMain()), var0);
   }

   public static CodeBootstrap getCodeBootstrap() {
      return PyRunnableBootstrap.getFilenameConstructorReflectionBootstrap(this$py.class);
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
