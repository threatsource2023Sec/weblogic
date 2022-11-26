package org.python.indexer;

import java.io.File;
import java.util.List;
import java.util.Set;
import org.python.indexer.ast.NAlias;
import org.python.indexer.ast.NAssert;
import org.python.indexer.ast.NAssign;
import org.python.indexer.ast.NAttribute;
import org.python.indexer.ast.NAugAssign;
import org.python.indexer.ast.NBinOp;
import org.python.indexer.ast.NBlock;
import org.python.indexer.ast.NBody;
import org.python.indexer.ast.NBoolOp;
import org.python.indexer.ast.NBreak;
import org.python.indexer.ast.NCall;
import org.python.indexer.ast.NClassDef;
import org.python.indexer.ast.NCompare;
import org.python.indexer.ast.NComprehension;
import org.python.indexer.ast.NContinue;
import org.python.indexer.ast.NDelete;
import org.python.indexer.ast.NDict;
import org.python.indexer.ast.NEllipsis;
import org.python.indexer.ast.NExceptHandler;
import org.python.indexer.ast.NExec;
import org.python.indexer.ast.NExprStmt;
import org.python.indexer.ast.NFor;
import org.python.indexer.ast.NFunctionDef;
import org.python.indexer.ast.NGeneratorExp;
import org.python.indexer.ast.NGlobal;
import org.python.indexer.ast.NIf;
import org.python.indexer.ast.NIfExp;
import org.python.indexer.ast.NImport;
import org.python.indexer.ast.NImportFrom;
import org.python.indexer.ast.NIndex;
import org.python.indexer.ast.NKeyword;
import org.python.indexer.ast.NLambda;
import org.python.indexer.ast.NList;
import org.python.indexer.ast.NListComp;
import org.python.indexer.ast.NModule;
import org.python.indexer.ast.NName;
import org.python.indexer.ast.NNode;
import org.python.indexer.ast.NNum;
import org.python.indexer.ast.NPass;
import org.python.indexer.ast.NPlaceHolder;
import org.python.indexer.ast.NPrint;
import org.python.indexer.ast.NQname;
import org.python.indexer.ast.NRaise;
import org.python.indexer.ast.NRepr;
import org.python.indexer.ast.NReturn;
import org.python.indexer.ast.NSlice;
import org.python.indexer.ast.NStr;
import org.python.indexer.ast.NSubscript;
import org.python.indexer.ast.NTryExcept;
import org.python.indexer.ast.NTryFinally;
import org.python.indexer.ast.NTuple;
import org.python.indexer.ast.NUnaryOp;
import org.python.indexer.ast.NUrl;
import org.python.indexer.ast.NWhile;
import org.python.indexer.ast.NWith;
import org.python.indexer.ast.NYield;
import org.python.indexer.types.NDictType;
import org.python.indexer.types.NFuncType;
import org.python.indexer.types.NListType;
import org.python.indexer.types.NModuleType;
import org.python.indexer.types.NTupleType;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnionType;

public class IndexerTest extends TestBase {
   public void testBuiltinModulePresent() throws Exception {
      NType mod = this.idx.moduleTable.lookupType("__builtin__");
      assertNotNull("missing __builtin__ module", mod);
      assertTrue("wrong type: " + mod.getClass(), mod instanceof NModuleType);
   }

   public void testLazyModuleLoad() throws Exception {
      assertNull("'array' module should not yet be loaded", this.idx.moduleTable.lookupType("array"));
      this.assertNoBinding("array");
      assertNotNull(this.idx.loadModule("array"));
      assertNotNull("'array' module should have been loaded", this.idx.moduleTable.lookupType("array"));
      this.assertModuleBinding("array");
   }

   public void testNativeModulesAvailable() throws Exception {
      String[] var1 = new String[]{"array", "ctypes", "errno", "math", "operator", "os", "signal", "sys", "thread", "time"};
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         String name = var1[var3];
         this.assertNoBinding(name);
         assertNotNull(name, this.idx.loadModule(name));
         this.assertModuleBinding(name);
      }

   }

   public void testBuiltinObject() throws Exception {
      this.assertClassBinding("__builtin__.object");
      this.assertClassBinding("__builtin__.object.__class__");
   }

   public void testBuiltinTuple() throws Exception {
      this.assertClassBinding("__builtin__.tuple");
      this.assertMethodBinding("__builtin__.tuple.__rmul__");
      this.assertMethodBinding("__builtin__.tuple.__iter__");
   }

   public void testBuiltinList() throws Exception {
      this.assertClassBinding("__builtin__.list");
      this.assertMethodBinding("__builtin__.list.append");
      this.assertMethodBinding("__builtin__.list.count");
   }

   public void testBuiltinNum() throws Exception {
      this.assertClassBinding("__builtin__.float");
      NBinding b = this.assertMethodBinding("__builtin__.float.fromhex");
      assertTrue(b.isBuiltin());
   }

   public void testBuiltinStr() throws Exception {
      this.assertClassBinding("__builtin__.str");
      this.assertMethodBinding("__builtin__.str.encode");
      this.assertMethodBinding("__builtin__.str.startswith");
      this.assertMethodBinding("__builtin__.str.split");
      this.assertMethodBinding("__builtin__.str.partition");
   }

   public void testBuiltinDict() throws Exception {
      this.assertClassBinding("__builtin__.dict");
      this.assertMethodBinding("__builtin__.dict.__getitem__");
      this.assertMethodBinding("__builtin__.dict.keys");
      this.assertMethodBinding("__builtin__.dict.clear");
   }

   public void testBuiltinFile() throws Exception {
      this.assertClassBinding("__builtin__.file");
      this.assertMethodBinding("__builtin__.file.__enter__");
      this.assertMethodBinding("__builtin__.file.readline");
      this.assertMethodBinding("__builtin__.file.readlines");
      this.assertMethodBinding("__builtin__.file.isatty");
   }

   public void testBuiltinFuncs() throws Exception {
      this.assertFunctionBinding("__builtin__.apply");
      this.assertFunctionBinding("__builtin__.abs");
      this.assertFunctionBinding("__builtin__.hex");
      this.assertFunctionBinding("__builtin__.range");
      this.assertFunctionBinding("__builtin__.globals");
      this.assertFunctionBinding("__builtin__.open");
   }

   public void testBuiltinTypes() throws Exception {
      this.assertClassBinding("__builtin__.ArithmeticError");
      this.assertClassBinding("__builtin__.ZeroDivisionError");
      this.assertAttributeBinding("__builtin__.True");
      this.assertAttributeBinding("__builtin__.False");
      this.assertAttributeBinding("__builtin__.None");
      this.assertAttributeBinding("__builtin__.Ellipsis");
   }

   public void testStrConstructor() throws Exception {
      this.index("newstr.py", new String[]{"x = str([])"});
      this.assertStringType("newstr.x");
   }

   public void testListSubscript() throws Exception {
      this.index("test.py", new String[]{"x = [1, 2, 3]", "y = x[2]"});
      this.assertNumType("test.y");
   }

   public void testBuiltinSys() throws Exception {
      this.idx.loadModule("sys");
      this.assertModuleBinding("sys");
      this.assertAttributeBinding("sys.__stderr__");
      NBinding b = this.assertFunctionBinding("sys.exit");
      assertTrue(b.isBuiltin());
      this.assertFunctionBinding("sys.getprofile");
      this.assertFunctionBinding("sys.getdefaultencoding");
      this.assertAttributeBinding("sys.api_version");
      this.assertNumType("sys.api_version");
      this.assertAttributeBinding("sys.argv");
      this.assertBindingType("sys.argv", NListType.class);
      this.assertAttributeBinding("sys.byteorder");
      this.assertStringType("sys.byteorder");
      this.assertAttributeBinding("sys.flags");
      this.assertBindingType("sys.flags", NDictType.class);
   }

   public void testFetchAst() throws Exception {
      NModule ast = this.idx.getAstForFile(this.abspath("hello.py"));
      assertNotNull("failed to load file", ast);
      assertEquals("module has wrong name", "hello", ast.name);
      assertNotNull("AST has no body", ast.body);
      assertNotNull("AST body has no children", ast.body.seq);
      assertEquals("wrong number of children", 1, ast.body.seq.size());
      NNode e = (NNode)ast.body.seq.get(0);
      assertTrue("Incorrect AST: " + e.getClass(), e instanceof NExprStmt);
      e = ((NExprStmt)e).value;
      assertTrue("Incorrect AST: " + e.getClass(), e instanceof NStr);
      assertEquals("Wrong string content", "Hello", ((NStr)e).n.toString());
   }

   public void testFileLoad() throws Exception {
      this.idx.loadFile(this.abspath("testfileload.py"), true);
      this.idx.ready();
      assertEquals("loaded more than 1 file", 1, this.idx.numFilesLoaded());
   }

   public void testAstCacheTmpDir() throws Exception {
      AstCache cache = AstCache.get();
      File f = new File(AstCache.CACHE_DIR);
      assertTrue(f.exists());
      assertTrue(f.canRead());
      assertTrue(f.canWrite());
      assertTrue(f.isDirectory());
   }

   public void testAstCacheNames() throws Exception {
      AstCache cache = AstCache.get();
      String sourcePath = this.abspath("hello.py");
      String cachePath = cache.getCachePath(new File(sourcePath));
      String cachedName = (new File(cachePath)).getName();
      assertTrue("Invalid cache name: " + cachedName, cachedName.matches("^hello.py[A-Za-z0-9]{32}.ast$"));
   }

   public void testAstCache() throws Exception {
      AstCache cache = AstCache.get();
      String sourcePath = this.abspath("hello.py");
      NModule ast = cache.getSerializedModule(sourcePath);
      assertNull(ast);
      cache.getAST(sourcePath);
      ast = cache.getSerializedModule(sourcePath);
      assertNotNull(ast);
      assertEquals(sourcePath, ast.getFile());
   }

   public void testAstCacheEmptyFile() throws Exception {
      AstCache cache = AstCache.get();
      NModule mod = cache.getAST(this.abspath("empty_file.py"));
      assertNotNull(mod);
      NBlock seq = mod.body;
      assertNotNull(seq);
      assertTrue(seq.seq.isEmpty());
   }

   public void testConstructedTypes() throws Exception {
      this.assertNoneType(new NAlias((String)null, (NQname)null, (NName)null));
      this.assertNoneType(new NAssert((NNode)null, (NNode)null));
      this.assertNoneType(new NAssign((List)null, (NNode)null));
      this.assertNoneType(new NAttribute(new NStr(), new NName("")));
      this.assertNoneType(new NAugAssign((NNode)null, (NNode)null, (String)null));
      this.assertNoneType(new NBinOp((NNode)null, (NNode)null, (String)null));
      this.assertNoneType(new NBlock((List)null));
      this.assertNoneType(new NBody((List)null));
      this.assertNoneType(new NBoolOp((NBoolOp.OpType)null, (List)null));
      this.assertNoneType(new NBreak());
      this.assertNoneType(new NCall((NNode)null, (List)null, (List)null, (NNode)null, (NNode)null));
      this.assertNoneType(new NClassDef((NName)null, (List)null, (NBlock)null));
      this.assertNoneType(new NCompare((NNode)null, (List)null, (List)null));
      this.assertNoneType(new NComprehension((NNode)null, (NNode)null, (List)null));
      this.assertNoneType(new NContinue());
      this.assertNoneType(new NDelete((List)null));
      this.assertNoneType(new NDict((List)null, (List)null));
      this.assertNoneType(new NEllipsis());
      this.assertNoneType(new NExceptHandler((NNode)null, (NNode)null, (NBlock)null));
      this.assertNoneType(new NExec((NNode)null, (NNode)null, (NNode)null));
      this.assertNoneType(new NExprStmt((NNode)null));
      this.assertNoneType(new NFor((NNode)null, (NNode)null, (NBlock)null, (NBlock)null));
      this.assertNoneType(new NFunctionDef((NName)null, (List)null, (NBlock)null, (List)null, (NName)null, (NName)null));
      this.assertNoneType(new NGeneratorExp((NNode)null, (List)null));
      this.assertNoneType(new NGlobal((List)null));
      this.assertNoneType(new NIf((NNode)null, (NBlock)null, (NBlock)null));
      this.assertNoneType(new NIfExp((NNode)null, (NNode)null, (NNode)null));
      this.assertNoneType(new NImport((List)null));
      this.assertNoneType(new NImportFrom((String)null, (NQname)null, (List)null));
      this.assertNoneType(new NIndex((NNode)null));
      this.assertNoneType(new NKeyword((String)null, (NNode)null));
      this.assertNoneType(new NLambda((List)null, (NNode)null, (List)null, (NName)null, (NName)null));
      this.assertNoneType(new NList((List)null));
      this.assertNoneType(new NListComp((NNode)null, (List)null));
      this.assertNoneType(new NModule((NBlock)null, 0, 1));
      this.assertNoneType(new NName(""));
      this.assertNoneType(new NNum(-1));
      this.assertNoneType(new NPass());
      this.assertNoneType(new NPlaceHolder());
      this.assertNoneType(new NPrint((NNode)null, (List)null));
      this.assertNoneType(new NQname((NQname)null, new NName("")));
      this.assertNoneType(new NRaise((NNode)null, (NNode)null, (NNode)null));
      this.assertNoneType(new NRepr((NNode)null));
      this.assertNoneType(new NReturn((NNode)null));
      this.assertNoneType(new NSlice((NNode)null, (NNode)null, (NNode)null));
      this.assertNoneType(new NStr());
      this.assertNoneType(new NSubscript((NNode)null, (NNode)null));
      this.assertNoneType(new NTryExcept((List)null, (NBlock)null, (NBlock)null));
      this.assertNoneType(new NTryFinally((NBlock)null, (NBlock)null));
      this.assertNoneType(new NTuple((List)null));
      this.assertNoneType(new NUnaryOp((NNode)null, (NNode)null));
      this.assertNoneType(new NUrl(""));
      this.assertNoneType(new NWhile((NNode)null, (NBlock)null, (NBlock)null));
      this.assertNoneType(new NWith((NNode)null, (NNode)null, (NBlock)null));
      this.assertNoneType(new NYield((NNode)null));
   }

   private void assertNoneType(NNode n) {
      assertEquals(n.getType(), Indexer.idx.builtins.None);
   }

   public void testClassTypeBuiltinAttrs() throws Exception {
      String file = "classtype_builtins.py";
      this.buildIndex(new String[]{file});
      NModuleType module = (NModuleType)this.idx.moduleTable.lookupType(this.abspath(file));
      Scope mtable = module.getTable();
      assertTrue(mtable.lookupType("MyClass").isClassType());
      assertTrue(mtable.lookupType("MyClassNoDoc").isClassType());
      assertTrue(mtable.lookupType("MyClass").getTable().getParent() == mtable);
      assertEquals(NBinding.Kind.CLASS, mtable.lookup("MyClass").getKind());
      Scope t = mtable.lookupType("MyClass").getTable();
      assertTrue(t.lookupType("__bases__").isTupleType());
      assertTrue(t.lookupType("__dict__").isDictType());
      assertEquals(this.idx.builtins.BaseStr, t.lookupType("__name__"));
      assertEquals(this.idx.builtins.BaseStr, t.lookupType("__module__"));
      assertEquals(this.idx.builtins.BaseStr, t.lookupType("__doc__"));
      t = mtable.lookupType("MyClassNoDoc").getTable();
      assertEquals(this.idx.builtins.BaseStr, t.lookupType("__doc__"));
   }

   public void testMethodBuiltinAttrs() throws Exception {
      String file = "classtype_builtins.py";
      this.buildIndex(new String[]{file});
      Scope mtable = this.idx.moduleTable.lookupType(this.abspath(file)).getTable();
      NBinding method = mtable.lookupType("MyClass").getTable().lookup("__init__");
      assertNotNull(method);
      assertEquals(NBinding.Kind.CONSTRUCTOR, method.getKind());
      assertEquals("classtype_builtins.MyClass.__init__", method.getQname());
      NType ftype = mtable.lookupType("MyClass").getTable().lookupType("__init__");
      assertTrue(ftype.isFuncType());
      NBinding c = mtable.lookup("MyClass");
      String[] var6 = new String[]{"im_class", "__class__", "im_self", "__self__"};
      int var7 = var6.length;

      for(int var8 = 0; var8 < var7; ++var8) {
         String special = var6[var8];
         NBinding attr = ftype.getTable().lookup(special);
         assertNotNull("missing binding for " + special, attr);
         assertEquals(c.getType(), attr.getType());
      }

   }

   public void testModulePaths() throws Exception {
      this.idx.loadModule("pkg");
      this.idx.loadModule("pkg.animal");
      this.idx.loadModule("pkg.mineral.stone.lapis");
      this.idx.ready();
      this.assertModuleBinding("pkg");
      this.assertModuleBinding("pkg.animal");
      this.assertModuleBinding("pkg.mineral.stone.lapis");
   }

   public void testCircularImport() throws Exception {
      this.idx.loadModule("pkg.animal.mammal.cat");
      this.idx.ready();
   }

   public void testBasicDefsAndRefs() throws Exception {
      this.idx.loadModule("refs");
      this.idx.ready();
      this.assertScopeBinding("refs.foo");
      String src = this.getSource("refs.py");
      this.assertDefinition("refs.foo", "foo", this.nthIndexOf(src, "foo", 1));
      this.assertNoReference("Definition site should not produce a reference", "refs.foo", this.nthIndexOf(src, "foo", 1), "foo".length());
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 2));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 3));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 4));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 5));
      this.assertNoReference("Should not have been a reference inside a string", "refs.foo", this.nthIndexOf(src, "foo", 6), "foo".length());
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 7));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 8));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 9));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 10));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 11));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 12));
      this.assertNoReference("Function param cannot refer to outer scope", "refs.foo", this.nthIndexOf(src, "foo", 13), "foo".length());
      this.assertNoReference("Function param 'foo' should hide outer foo", "refs.foo", this.nthIndexOf(src, "foo", 14), "foo".length());
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 15));
      this.assertReference("refs.foo", this.nthIndexOf(src, "foo", 16));
   }

   public void testAutoClassBindings() throws Exception {
      this.idx.loadModule("class1");
      this.idx.ready();
      this.assertModuleBinding("class1");
      this.assertClassBinding("class1.A");
      NBinding b = this.assertAttributeBinding("class1.A.__bases__");
      this.assertStaticSynthetic(b);
      assertTrue(b.getType().isTupleType());
      assertTrue(((NTupleType)b.getType()).getElementTypes().isEmpty());
      b = this.assertAttributeBinding("class1.A.__name__");
      this.assertStaticSynthetic(b);
      assertEquals(b.getType(), this.idx.builtins.BaseStr);
      b = this.assertAttributeBinding("class1.A.__module__");
      this.assertStaticSynthetic(b);
      assertEquals(b.getType(), this.idx.builtins.BaseStr);
      b = this.assertAttributeBinding("class1.A.__doc__");
      this.assertStaticSynthetic(b);
      assertEquals(b.getType(), this.idx.builtins.BaseStr);
      b = this.assertAttributeBinding("class1.A.__dict__");
      this.assertStaticSynthetic(b);
      assertTrue(b.getType().isDictType());
      assertEquals(((NDictType)b.getType()).getKeyType(), this.idx.builtins.BaseStr);
      assertTrue(((NDictType)b.getType()).getValueType().isUnknownType());
   }

   public void testLocalVarRef() throws Exception {
      this.idx.loadModule("class2");
      this.idx.ready();
      this.assertFunctionBinding("class2.hi");
      this.assertParamBinding("class2.hi@msg");
      String src = this.getSource("class2.py");
      this.assertReference("class2.hi@msg", this.nthIndexOf(src, "msg", 2));
   }

   public void testClassMemberBindings() throws Exception {
      this.idx.loadModule("class1");
      this.idx.ready();
      this.assertScopeBinding("class1.A.a");
      this.assertConstructorBinding("class1.A.__init__");
      this.assertMethodBinding("class1.A.hi");
      this.assertParamBinding("class1.A.__init__@self");
      this.assertParamBinding("class1.A.hi@self");
      this.assertParamBinding("class1.A.hi@msg");
      String src = this.getSource("class1.py");
      this.assertReference("class1.A.hi@msg", this.nthIndexOf(src, "msg", 2));
      this.assertReference("class1.A", src.indexOf("A.a"), 1);
      this.assertReference("class1.A.a", src.indexOf("A.a") + 2, 1);
      this.assertScopeBinding("class1.x");
      this.assertScopeBinding("class1.y");
      this.assertScopeBinding("class1.z");
      this.assertReference("class1.A", src.indexOf("= A") + 2, 1);
      this.assertConstructed("class1.A", src.indexOf("A()"), 1);
      this.assertReference("class1.y", src.indexOf("y.b"), 1);
      this.assertInstanceType("class1.y", "class1.A");
      this.assertReference("class1.A.b", src.indexOf("y.b") + 2, 1);
      this.assertScopeBinding("class1.z");
      this.assertNumType("class1.z");
   }

   public void testCallNewRef() throws Exception {
      this.idx.loadModule("callnewref");
      this.idx.ready();
      String src = this.getSource("callnewref.py");
      String fsig = "callnewref.myfunc";
      this.assertFunctionBinding(fsig);
      this.assertDefinition(fsig, "myfunc", src.indexOf("myfunc"));
      this.assertReference(fsig, this.nthIndexOf(src, "myfunc", 2));
      this.assertCall(fsig, this.nthIndexOf(src, "myfunc", 3));
      String csig = "callnewref.MyClass";
      this.assertClassBinding(csig);
      this.assertDefinition(csig, "MyClass", src.indexOf("MyClass"));
      this.assertReference(csig, this.nthIndexOf(src, "MyClass", 2));
      this.assertConstructed(csig, this.nthIndexOf(src, "MyClass", 3));
      String msig = "callnewref.MyClass.mymethod";
      this.assertMethodBinding(msig);
      this.assertDefinition(msig, "mymethod", src.indexOf("mymethod"));
      this.assertReference(msig, this.nthIndexOf(src, "mymethod", 2));
      this.assertCall(msig, this.nthIndexOf(src, "mymethod", 3));
   }

   public void testPackageLoad() throws Exception {
      this.idx.loadModule("pkgload");
      this.idx.ready();
      this.assertModuleBinding("pkgload");
      this.assertModuleBinding("pkg");
      this.assertScopeBinding("pkg.myvalue");
   }

   public void testUnqualifiedSamePkgImport() throws Exception {
      this.idx.loadModule("pkg.animal.reptile.snake");
      this.idx.ready();
      this.assertModuleBinding("pkg.animal.reptile.snake");
      this.assertModuleBinding("pkg.animal.reptile.croc");
      this.assertClassBinding("pkg.animal.reptile.snake.Snake");
      this.assertClassBinding("pkg.animal.reptile.snake.Python");
      this.assertClassBinding("pkg.animal.reptile.croc.Crocodilian");
      this.assertClassBinding("pkg.animal.reptile.croc.Gavial");
      String snakeSrc = this.getSource("pkg/animal/reptile/snake.py");
      this.assertReference("pkg.animal.reptile.croc", snakeSrc.indexOf("croc"));
      this.assertReference("pkg.animal.reptile.croc", this.nthIndexOf(snakeSrc, "croc", 2));
      this.assertReference("pkg.animal.reptile.croc.Gavial", snakeSrc.indexOf("Gavial"));
   }

   public void testAbsoluteImport() throws Exception {
      this.idx.loadModule("pkg.mineral.metal.lead");
      this.idx.ready();
      this.assertModuleBinding("pkg");
      this.assertModuleBinding("pkg.plant");
      this.assertModuleBinding("pkg.plant.poison");
      this.assertModuleBinding("pkg.plant.poison.eggplant");
      String src = this.getSource("pkg/mineral/metal/lead.py");
      this.assertReference("pkg", this.nthIndexOf(src, "pkg", 1));
      this.assertReference("pkg", this.nthIndexOf(src, "pkg", 2));
      this.assertReference("pkg.plant", this.nthIndexOf(src, "plant", 1));
      this.assertReference("pkg.plant", this.nthIndexOf(src, ".plant", 2) + 1);
      this.assertReference("pkg.plant.poison", this.nthIndexOf(src, "poison", 1));
      this.assertReference("pkg.plant.poison", this.nthIndexOf(src, ".poison", 2) + 1);
      this.assertReference("pkg.plant.poison.eggplant", this.nthIndexOf(src, "eggplant", 1));
      this.assertReference("pkg.plant.poison.eggplant", this.nthIndexOf(src, ".eggplant", 2) + 1);
   }

   public void testAbsoluteImportAs() throws Exception {
      this.idx.loadModule("pkg.mineral.metal.iron");
      this.idx.ready();
      this.assertModuleBinding("pkg");
      this.assertModuleBinding("pkg.mineral");
      this.assertModuleBinding("pkg.mineral.metal");
      this.assertModuleBinding("pkg.mineral.metal.iron");
      this.assertModuleBinding("pkg.plant");
      this.assertModuleBinding("pkg.plant.poison");
      this.assertModuleBinding("pkg.plant.poison.eggplant");
      String adjectives = "pkg.plant.poison.eggplant.adjectives";
      this.assertScopeBinding(adjectives);
      String aubergine = "pkg.mineral.metal.iron.aubergine";
      this.assertScopeBinding(aubergine);
      this.assertBindingType(aubergine, "pkg.plant.poison.eggplant");
      String src = this.getSource("pkg/mineral/metal/iron.py");
      this.assertReference("pkg", src.indexOf("pkg"));
      this.assertReference("pkg.plant", src.indexOf("plant"));
      this.assertReference("pkg.plant.poison", src.indexOf("poison"));
      this.assertReference("pkg.plant.poison.eggplant", src.indexOf("eggplant"));
      this.assertReference(aubergine, this.nthIndexOf(src, "aubergine", 2));
      this.assertReference(adjectives, src.indexOf("adjectives"));
   }

   public void testImportFrom() throws Exception {
      this.idx.loadModule("pkg.other.color.white");
      this.idx.ready();
      String src = this.getSource("pkg/other/color/white.py");
      this.assertReference("pkg.other.color.red", src.indexOf("red"));
      this.assertReference("pkg.other.color.green", src.indexOf("green"));
      this.assertReference("pkg.other.color.blue", src.indexOf("blue"));
      this.assertReference("pkg.other.color.red.r", src.indexOf("r as"), 1);
      this.assertReference("pkg.other.color.blue.b", src.indexOf("b as"), 1);
      this.assertReference("pkg.other.color.red.r", src.indexOf("= R") + 2, 1);
      this.assertReference("pkg.other.color.green.g", src.indexOf("g #"), 1);
      this.assertReference("pkg.other.color.blue.b", src.indexOf("= B") + 2, 1);
   }

   public void testImportStar() throws Exception {
      this.idx.loadModule("pkg.other.color.crimson");
      this.idx.ready();
      String src = this.getSource("pkg/other/color/crimson.py");
      this.assertReference("pkg.other.color.red.r", src.indexOf("r,"), 1);
      this.assertReference("pkg.other.color.red.g", src.indexOf("g,"), 1);
      this.assertReference("pkg.other.color.red.b", src.indexOf("b"), 1);
   }

   public void testImportStarAll() throws Exception {
      this.idx.loadModule("pkg.misc.moduleB");
      this.idx.ready();
      String src = this.getSource("pkg/misc/moduleB.py");
      this.assertReference("pkg.misc.moduleA.a", src.indexOf("a #"), 1);
      this.assertReference("pkg.misc.moduleA.b", src.indexOf("b #"), 1);
      this.assertReference("pkg.misc.moduleA.c", src.indexOf("c #"), 1);
      this.assertNoReference("Should not have imported 'd'", "pkg.misc.moduleA.d", src.indexOf("d #"), 1);
   }

   public void testImportFromInitPy() throws Exception {
      this.idx.loadModule("pkg.animal");
      this.idx.ready();
      this.assertModuleBinding("pkg");
      this.assertModuleBinding("pkg.animal");
      this.assertModuleBinding("pkg.animal.animaltest");
      this.assertScopeBinding("pkg.animal.success");
      this.assertScopeBinding("pkg.animal.animaltest.living");
   }

   public void testTempName() throws Exception {
      String src = this.index("tmpname.py", new String[]{"def purge():", "  cache.clear()", "cache = {}"});
      this.assertScopeBinding("tmpname.cache");
      this.assertBindingType("tmpname.cache", NDictType.class);
      this.assertDefinition("tmpname.cache", "cache", src.lastIndexOf("cache"));
      this.assertReference("tmpname.cache", src.indexOf("cache"));
      this.assertNoDefinition("Temp-def should have been replaced", "tmpname.cache", src.indexOf("cache"), "cache".length());
      this.assertCall("__builtin__.dict.clear", src.lastIndexOf("clear"));
   }

   public void testTempAttr() throws Exception {
      String src = this.index("tmpattr.py", new String[]{"x = app.usage", "app.usage = 'hi'"});
      this.assertScopeBinding("tmpattr.x");
      this.assertScopeBinding("tmpattr.app");
      this.assertAttributeBinding("tmpattr.app.usage");
      this.assertStringType("tmpattr.app.usage");
      this.assertStringType("tmpattr.x");
      this.assertDefinition("tmpattr.app.usage", src.lastIndexOf("usage"));
      this.assertReference("tmpattr.app.usage", src.indexOf("usage"));
   }

   public void testTempAttrOnParam() throws Exception {
      String src = this.index("tmpattr_param.py", new String[]{"def foo(x):", "  x.hello = 'hi'", "def bar(y=None):", "  y.hello = 'hola'"});
      this.assertFunctionBinding("tmpattr_param.foo");
      this.assertParamBinding("tmpattr_param.foo@x");
      this.assertAttributeBinding("tmpattr_param.foo@x.hello");
      this.assertStringType("tmpattr_param.foo@x.hello");
      this.assertReference("tmpattr_param.foo@x", src.indexOf("x.hello"), 1);
      this.assertFunctionBinding("tmpattr_param.bar");
      this.assertParamBinding("tmpattr_param.bar@y");
      this.assertAttributeBinding("tmpattr_param.bar@y.hello");
      this.assertStringType("tmpattr_param.bar@y.hello");
      this.assertReference("tmpattr_param.bar@y", src.indexOf("y.hello"), 1);
   }

   public void testParamDefaultLambdaBinding() throws Exception {
      String src = this.index("test.py", new String[]{"def foo(arg=lambda name: name + '!'):", "  x = arg('hi')"});
      this.assertFunctionBinding("test.foo");
      this.assertParamBinding("test.foo@arg");
      this.assertFunctionBinding("test.lambda%1");
      this.assertParamBinding("test.lambda%1@name");
      this.assertReference("test.lambda%1@name", src.lastIndexOf("name"));
      this.assertCall("test.foo@arg", src.lastIndexOf("arg"));
      this.assertStringType("test.foo&x");
   }

   public void testNestedLambdaParam() throws Exception {
      this.index("test.py", new String[]{"def util(create):", "  return create()", "z = lambda:util(create=lambda: str())", "y = z()()"});
      this.assertScopeBinding("test.z");
      this.assertFunctionBinding("test.lambda%1&lambda%1");
   }

   public void testReassignAttrOfUnknown() throws Exception {
      this.index("reassign.py", new String[]{"app.foo = 'hello'", "app.foo = 2"});
      this.assertScopeBinding("reassign.app");
      NBinding nb = this.assertAttributeBinding("reassign.app.foo");
      NType type = nb.getType();
      assertTrue(type.isUnionType());
      Set types = ((NUnionType)type).getTypes();
      assertEquals(2, types.size());
      assertTrue(types.contains(this.idx.builtins.BaseStr));
      assertTrue(types.contains(this.idx.builtins.BaseNum));
   }

   public void testRefToProvisionalBinding() throws Exception {
      this.index("provisional.py", new String[]{"for a in []:", "  a.dump()", "for a in []:", "  a.dump()"});
      this.assertModuleBinding("provisional");
      this.assertScopeBinding("provisional.a");
      this.assertNoBinding("provisional.a.dump");
   }

   public void testRefToProvisionalBindingNewType() throws Exception {
      this.index("provisional.py", new String[]{"for b in []:", "  b.dump()", "for b in ():", "  b.dump()"});
      this.assertModuleBinding("provisional");
      this.assertScopeBinding("provisional.b");
      this.assertNoBinding("provisional.b.dump");
   }

   public void testSkipClassScope() throws Exception {
      String src = this.index("skipclass.py", new String[]{"def aa():", "  xx = 'foo'", "  class bb:", "    xx = 10", "    def cc(self):", "      print bb.xx", "      print xx"});
      this.assertReference("skipclass.aa&bb.xx", this.nthIndexOf(src, "xx", 3));
      this.assertReference("skipclass.aa&xx", this.nthIndexOf(src, "xx", 4));
   }

   public void testLambdaArgs() throws Exception {
      String src = this.index("lambda_args.py", new String[]{"y = lambda x='hi': x.upper()", "y = lambda x='there': x.lower()"});
      this.assertScopeBinding("lambda_args.y");
      this.assertFunctionBinding("lambda_args.lambda%1");
      this.assertParamBinding("lambda_args.lambda%1@x");
      this.assertStringType("lambda_args.lambda%1@x");
      this.assertReference("lambda_args.lambda%1@x", this.nthIndexOf(src, "x", 2));
      this.assertCall("__builtin__.str.upper", src.indexOf("upper"));
      this.assertFunctionBinding("lambda_args.lambda%2");
      this.assertParamBinding("lambda_args.lambda%1@x");
      this.assertReference("lambda_args.lambda%2@x", this.nthIndexOf(src, "x", 4));
      this.assertCall("__builtin__.str.lower", src.indexOf("lower"));
   }

   public void testFunArgs() throws Exception {
      String src = this.index("funargs.py", new String[]{"def foo(x, y='hi'):", "  z = 9", "  return x + y.upper() + z"});
      this.assertFunctionBinding("funargs.foo");
      this.assertParamBinding("funargs.foo@x");
      this.assertReference("funargs.foo@x", this.nthIndexOf(src, "x", 2));
      this.assertParamBinding("funargs.foo@y");
      this.assertStringType("funargs.foo@y");
      this.assertReference("funargs.foo@y", this.nthIndexOf(src, "y", 2));
      this.assertCall("__builtin__.str.upper", src.indexOf("upper"));
      this.assertVariableBinding("funargs.foo&z");
      this.assertReference("funargs.foo&z", this.nthIndexOf(src, "z", 2));
   }

   public void testDatetime() throws Exception {
      String src = this.index("date_time.py", new String[]{"from datetime import datetime as dt", "import datetime", "now = dt.now()", "d = now.date()", "tz = now.tzinfo"});
      this.assertModuleBinding("datetime");
      this.assertClassBinding("datetime.datetime");
      this.assertMethodBinding("datetime.datetime.date");
      this.assertReference("datetime", this.nthIndexOf(src, "datetime", 1));
      this.assertReference("datetime.datetime", this.nthIndexOf(src, "datetime", 2));
      this.assertReference("datetime.datetime", this.nthIndexOf(src, "dt", 1), 2);
      this.assertReference("datetime.datetime", this.nthIndexOf(src, "dt", 2), 2);
      this.assertReference("datetime", this.nthIndexOf(src, "datetime", 3));
      this.assertCall("datetime.datetime.now", this.nthIndexOf(src, "now", 2));
      this.assertCall("datetime.datetime.date", this.nthIndexOf(src, "date()", 1));
      this.assertReference("datetime.time.tzinfo", this.nthIndexOf(src, "tzinfo", 1));
      this.assertBindingType("date_time.tz", "datetime.tzinfo");
   }

   public void testUnpackList() throws Exception {
      this.index("unpacklist.py", new String[]{"a = [1, 2]", "(b, c) = [3, 4]", "[d, e] = ['hi', 'there']"});
      this.assertScopeBinding("unpacklist.a");
      this.assertScopeBinding("unpacklist.b");
      this.assertScopeBinding("unpacklist.c");
      this.assertScopeBinding("unpacklist.d");
      this.assertScopeBinding("unpacklist.e");
      this.assertListType("unpacklist.a", "__builtin__.float");
      this.assertNumType("unpacklist.b");
      this.assertNumType("unpacklist.c");
      this.assertStringType("unpacklist.d");
      this.assertStringType("unpacklist.e");
   }

   public void testStringSlice() throws Exception {
      String src = this.index("slicestring.py", new String[]{"a = 'hello'[2]", "b = 'hello'[2:4]", "test = 'testing'", "test[-3:].lower()"});
      this.assertScopeBinding("slicestring.a");
      this.assertScopeBinding("slicestring.b");
      this.assertStringType("slicestring.a");
      this.assertStringType("slicestring.b");
      this.assertCall("__builtin__.str.lower", src.lastIndexOf("lower"));
   }

   public void testUnionStringSliceTempAttr() throws Exception {
      String src = this.index("tmpattr_slice.py", new String[]{"def foo(filename):", "  module = filename or '<unknown>'", "  module[-3:].lower()"});
      this.assertCall("__builtin__.str.lower", src.lastIndexOf("lower"));
   }

   public void testSelfBinding() throws Exception {
      String src = this.index("selfish.py", new String[]{"class Foo():", "  def hello(self):", "    print self"});
      this.assertClassBinding("selfish.Foo");
      this.assertMethodBinding("selfish.Foo.hello");
      this.assertParamBinding("selfish.Foo.hello@self");
      this.assertDefinition("selfish.Foo.hello@self", this.nthIndexOf(src, "self", 1));
      this.assertReference("selfish.Foo.hello@self", this.nthIndexOf(src, "self", 2));
      this.assertBindingType("selfish.Foo.hello@self", "selfish.Foo");
   }

   public void testInstanceAttrs() throws Exception {
      String src = this.index("attr.py", new String[]{"class Foo():", "  def __init__(self):", "    self.elts = []", "  def add(self, item):", "    self.elts.append(item)"});
      this.assertClassBinding("attr.Foo");
      this.assertConstructorBinding("attr.Foo.__init__");
      this.assertParamBinding("attr.Foo.__init__@self");
      this.assertDefinition("attr.Foo.__init__@self", this.nthIndexOf(src, "self", 1));
      this.assertReference("attr.Foo.__init__@self", this.nthIndexOf(src, "self", 2));
      this.assertBindingType("attr.Foo.__init__@self", "attr.Foo");
      this.assertAttributeBinding("attr.Foo.elts");
      this.assertListType("attr.Foo.elts");
      this.assertMethodBinding("attr.Foo.add");
      this.assertParamBinding("attr.Foo.add@self");
      this.assertBindingType("attr.Foo.add@self", "attr.Foo");
      this.assertParamBinding("attr.Foo.add@item");
      this.assertReference("attr.Foo.add@self", this.nthIndexOf(src, "self", 4));
      this.assertReference("attr.Foo.elts", this.nthIndexOf(src, "elts", 2));
      this.assertCall("__builtin__.list.append", src.indexOf("append"));
      this.assertReference("attr.Foo.add@item", src.lastIndexOf("item"));
   }

   public void testInstanceAttrsWithStdLib() throws Exception {
      this.includeStandardLibrary();
      String src = this.index("dice.py", new String[]{"import random", "class Dice(object):", "  def __init__(self):", "    self.__random = random.Random()", "  def set_seed(self, seed):", "    self.__random.seed(seed)"});
      this.assertModuleBinding("random");
      NBinding r = this.assertClassBinding("random.Random");
      assertFalse(r.isBuiltin());
      this.assertReference("random", this.nthIndexOf(src, "random", 3));
      this.assertConstructed("random.Random", src.indexOf("Random"));
      this.assertClassBinding("dice.Dice");
      this.assertReference("__builtin__.object", src.indexOf("object"));
      this.assertConstructorBinding("dice.Dice.__init__");
      this.assertParamBinding("dice.Dice.__init__@self");
      this.assertDefinition("dice.Dice.__init__@self", this.nthIndexOf(src, "self", 1));
      this.assertReference("dice.Dice.__init__@self", this.nthIndexOf(src, "self", 2));
      this.assertBindingType("dice.Dice.__init__@self", "dice.Dice");
      this.assertAttributeBinding("dice.Dice.__random");
      this.assertInstanceType("dice.Dice.__random", "random.Random");
      this.assertMethodBinding("dice.Dice.set_seed");
      this.assertParamBinding("dice.Dice.set_seed@self");
      this.assertBindingType("dice.Dice.set_seed@self", "dice.Dice");
      this.assertParamBinding("dice.Dice.set_seed@seed");
      this.assertReference("dice.Dice.set_seed@self", this.nthIndexOf(src, "self", 4));
      this.assertReference("dice.Dice.__random", this.nthIndexOf(src, "__random", 2));
      this.assertCall("random.Random.seed", this.nthIndexOf(src, "seed", 3));
      this.assertReference("dice.Dice.set_seed@seed", src.lastIndexOf("seed"));
   }

   public void testOsPath() throws Exception {
      String src = this.index("test.py", new String[]{"from os import path", "print path.devnull", "base, ext = path.split('/foo/bar/baz.py')", "print ext.endswith('py')"});
      this.assertReference("os.path.devnull", src.indexOf("devnull"));
      this.assertStringType("os.path.devnull");
      this.assertStringType("test.base");
      this.assertStringType("test.ext");
      this.assertCall("os.path.split", src.indexOf("split"));
      this.assertCall("__builtin__.str.endswith", src.indexOf("endswith"));
   }

   public void testImportOsPath() throws Exception {
      String src = this.index("test.py", new String[]{"import os.path", "print os.path.devnull"});
      this.assertReference("os", this.nthIndexOf(src, "os", 1));
      this.assertReference("os", this.nthIndexOf(src, "os", 2));
      this.assertReference("os.path", this.nthIndexOf(src, "path", 1));
      this.assertReference("os.path", this.nthIndexOf(src, "path", 2));
      this.assertReference("os.path.devnull", src.indexOf("devnull"));
   }

   public void testExceptionsModule() throws Exception {
      String src = this.index("test.py", new String[]{"import exceptions", "raise exceptions.NotImplementedError"});
      this.assertModuleBinding("exceptions");
      this.assertClassBinding("exceptions.NotImplementedError");
      this.assertReference("exceptions.NotImplementedError", src.indexOf("Not"));
   }

   public void testDupFunctionDecl() throws Exception {
      this.index("test.py", new String[]{"if x:", "  def a(args):", "    print args", "elif y:", "  def a(args):", "    print args"});
      this.assertFunctionBinding("test.a");
      this.assertParamBinding("test.a@args");
   }

   public void testResolveExportedNames() throws Exception {
      String src = this.index("test.py", new String[]{"__all__ = ['foo', 'bar' + 'baz', 'one', 'two']", "def foo(x):", "  return x", "bar = 6", "baz = 7", "barbaz = 8", "one = 'hi'", "two = 'there'"});
      this.assertReference("test.foo", src.indexOf("'foo"), 5);
      this.assertReference("test.one", src.indexOf("'one"), 5);
      this.assertReference("test.two", src.indexOf("'two"), 5);
      this.assertNoReference("Should not have referenced 'bar'", "test.bar", src.indexOf("bar"), 3);
   }

   public void testImportFromPlusAssign() throws Exception {
      String src = this.index("test.py", new String[]{"from os import sep", "os = 10", "print os"});
      this.assertModuleBinding("os");
      this.assertReference("os", src.indexOf("os"));
      this.assertNoDefinition("Import-from should not introduce a definition", "test.os", src.indexOf("os"), "os".length());
      this.assertDefinition("test.os", this.nthIndexOf(src, "os", 2));
      this.assertNumType("test.os");
      this.assertReference("test.os", src.lastIndexOf("os"));
   }

   public void testCircularTypeFunAndTuple() throws Exception {
      this.index("test.py", new String[]{"def foo():", "  return (foo,)"});
      this.assertFunctionBinding("test.foo");
      NType ftype = this.idx.lookupQnameType("test.foo");
      assertTrue(ftype instanceof NFuncType);
      NType rtype = ftype.asFuncType().getReturnType();
      assertTrue(rtype instanceof NTupleType);
      assertEquals(1, rtype.asTupleType().getElementTypes().size());
      assertEquals(ftype, rtype.asTupleType().getElementTypes().get(0));
      assertEquals("<FuncType=#1:_:<TupleType:[<#1>]>>", ftype.toString());
   }

   public void testCircularTypeXInOwnList() throws Exception {
      this.index("test.py", new String[]{"x = (2,)", "y = [x]", "x = y"});
      NType xtype = this.idx.lookupQnameType("test.x");
      assertTrue(xtype instanceof NUnionType);
      Set types = xtype.asUnionType().getTypes();
      assertEquals(2, types.size());
      NType[] array = (NType[])types.toArray(new NType[2]);
      boolean array0List = array[0] instanceof NListType;
      boolean array1List = array[1] instanceof NListType;
      assertTrue(array0List || array1List);
      int other = array0List ? 1 : 0;
      assertTrue("Expected tuple: " + array[other], array[other].isTupleType());
      assertEquals(1, array[other].asTupleType().getElementTypes().size());
      assertEquals(this.idx.builtins.BaseNum, array[other].asTupleType().getElementTypes().get(0));
      String s = xtype.toString();
      int index = s.indexOf("<TupleType=#");
      assertTrue(index != -1);
      int spot = index + "<TupleType=#".length();
      int num = Integer.parseInt(s.substring(spot, spot + 1));
      String ttype = "<TupleType=#" + num + ":[<ClassType:float>]>";
      String ref = "<#" + num + ">";
      if (array0List) {
         assertEquals("<UnionType:[<ListType:" + ttype + ">," + ref + "]>", s);
      } else {
         assertEquals("<UnionType:[" + ttype + ",<ListType:" + ref + ">]>", s);
      }

   }

   public void testFunReturn() throws Exception {
      this.index("fret.py", new String[]{"def foo(x): return x", "a = foo('a')", "b = foo('b')", "c = foo('c')"});
      NType ftype = this.idx.lookupQnameType("fret.foo");
      assertEquals("<FuncType:_:<UnknownType:null>>", ftype.toString());
      NType ctype = this.idx.lookupQnameType("fret.c");
      assertEquals(ctype.follow(), ftype.asFuncType().getReturnType());
   }

   public void testListCompForIn() throws Exception {
      this.index("listforin.py", new String[]{"[line for line in ['foo']]"});
      this.assertStringType("listforin.line");
   }

   public void testNoAddToBuiltin() throws Exception {
      String src = this.index("nob.py", new String[]{"x = [line.rstrip() + '\\n' for line in ['a ']]"});
      this.assertStringType("nob.line");
      this.assertCall("__builtin__.str.rstrip", src.indexOf("rstrip"));
      this.assertNoBinding("__builtin__.list.rstrip");
      this.assertListType("nob.x", "__builtin__.str");
   }

   public void testDecoratorSyntax() throws Exception {
      String deco1 = "@deco1";
      String deco2 = "@deco2 ('yargh')";
      String src = this.index("deco.py", new String[]{deco1, deco2, "def foo(): pass"});
      this.assertFunctionBinding("deco.foo");
      NModule m = this.idx.getAstForFile("deco.py");
      assertNotNull(m);
      NNode obj = (NNode)m.body.seq.get(0);
      assertTrue(obj instanceof NFunctionDef);
      NFunctionDef f = (NFunctionDef)obj;
      List decos = f.getDecoratorList();
      assertNotNull(decos);
      assertEquals(2, decos.size());
      assertTrue(decos.get(0) instanceof NName);
      NName d1 = (NName)decos.get(0);
      assertEquals(this.nthIndexOf(src, "deco1", 1), d1.start());
      assertEquals("deco1".length(), d1.length());
      assertEquals("deco1", d1.id);
      assertTrue(decos.get(1) instanceof NCall);
      NCall d2 = (NCall)decos.get(1);
      assertTrue(d2.func instanceof NName);
      assertEquals("deco2", ((NName)d2.func).id);
   }

   public void testBasicDecoratorSyntax() throws Exception {
      String src = this.index("deco.py", new String[]{"def deco1(func): print 'hello'; return func", "@deco1()", "def foo(): pass"});
      this.assertFunctionBinding("deco.deco1");
      this.assertFunctionBinding("deco.foo");
      this.assertCall("deco.deco1", this.nthIndexOf(src, "deco1", 2));
   }
}
