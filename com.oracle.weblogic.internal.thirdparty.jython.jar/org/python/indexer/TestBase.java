package org.python.indexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import junit.framework.TestCase;
import org.python.indexer.types.NType;
import org.python.indexer.types.NUnknownType;

public class TestBase extends TestCase {
   protected static final Level LOGGING_LEVEL;
   protected static final String TEST_DATA_DIR;
   protected static final String TEST_LIB_DIR;
   protected Indexer idx;

   private static void setUpLogging() {
      Logger indexerLogger = Logger.getLogger(Indexer.class.getCanonicalName());
      Handler logHandler = new ConsoleHandler();
      logHandler.setFormatter(new SimpleFormatter());
      logHandler.setLevel(Level.FINEST);
      indexerLogger.addHandler(logHandler);
   }

   protected void setUp() throws Exception {
      this.idx = new Indexer();
      this.idx.getLogger().setLevel(LOGGING_LEVEL);
      this.idx.enableAggressiveAssertions(true);
      this.idx.setProjectDir(TEST_DATA_DIR);
      AstCache.get().clearDiskCache();
      AstCache.get().clear();
   }

   protected void includeStandardLibrary() throws Exception {
      this.idx.addPath(TEST_LIB_DIR);
   }

   protected String abspath(String file) {
      return this.getTestFilePath(file);
   }

   protected String getTestFilePath(String file) {
      return TEST_DATA_DIR + file;
   }

   protected String getSource(String file) throws Exception {
      String path = this.getTestFilePath(file);
      StringBuilder sb = new StringBuilder();
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));

      String line;
      while((line = in.readLine()) != null) {
         sb.append(line);
         sb.append("\n");
      }

      in.close();
      return sb.toString();
   }

   protected String makeModule(String... lines) {
      StringBuilder sb = new StringBuilder();
      String[] var3 = lines;
      int var4 = lines.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         String line = var3[var5];
         sb.append(line).append("\n");
      }

      return sb.toString();
   }

   protected String index(String filename, String... lines) throws Exception {
      String src = this.makeModule(lines);
      this.idx.loadString(filename, src);
      this.idx.ready();
      return src;
   }

   protected int nthIndexOf(String s, String find, int n) {
      if (n <= 0) {
         throw new IllegalArgumentException();
      } else {
         int index = -1;

         for(int i = 0; i < n; ++i) {
            index = s.indexOf(find, index == -1 ? 0 : index + 1);
            if (index == -1) {
               throw new IllegalArgumentException();
            }
         }

         return index;
      }
   }

   public void testHandleExceptionLoggingNulls() throws Exception {
      try {
         this.idx.enableAggressiveAssertions(false);
         this.idx.getLogger().setLevel(Level.OFF);
         this.idx.handleException((String)null, new Exception());
         this.idx.handleException("oops", (Throwable)null);
      } catch (Throwable var2) {
         fail("should not have thrown: " + var2);
      }

   }

   public void testDataFileFindable() throws Exception {
      assertTrue("Test file not found", (new File(TEST_DATA_DIR)).exists());
   }

   public void testLoadDataFile() throws Exception {
      String path = this.abspath("test-load.txt");
      BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
      assertEquals(in.readLine().trim(), "hello");
      in.close();
   }

   public void testGetSource() throws Exception {
      String src = this.getSource("testsrc.txt");
      assertEquals("one\ntwo\n\nthree\n", src);
   }

   public void testStringModule() throws Exception {
      this.idx.loadString("test-string-module.py", this.makeModule("def foo():", "  pass"));
      this.idx.ready();
      this.assertFunctionBinding("test-string-module.foo");
   }

   public void testNthIndexOf() throws Exception {
      String s = "ab a b ab a\nb aab";
      assertEquals(0, this.nthIndexOf(s, "ab", 1));
      assertEquals(7, this.nthIndexOf(s, "ab", 2));
      assertEquals(15, this.nthIndexOf(s, "ab", 3));

      try {
         assertEquals(-1, this.nthIndexOf(s, "ab", 0));
         assertTrue(false);
      } catch (IllegalArgumentException var4) {
         assertTrue(true);
      }

      try {
         assertEquals(-1, this.nthIndexOf(s, "ab", 4));
         assertTrue(false);
      } catch (IllegalArgumentException var3) {
         assertTrue(true);
      }

   }

   public void testIndexerDefaults() throws Exception {
      this.includeStandardLibrary();
      assertEquals("wrong project dir", TEST_DATA_DIR, this.idx.projDir);
      assertEquals("unexpected load path entries", 1, this.idx.path.size());
      assertEquals(TEST_LIB_DIR, (String)this.idx.path.get(0));
   }

   public String buildIndex(String... files) throws Exception {
      String[] var2 = files;
      int var3 = files.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         String f = var2[var4];
         this.idx.loadFile(this.abspath(f));
      }

      this.idx.ready();
      return this.getSource(files[0]);
   }

   public NBinding getBinding(String qname) throws Exception {
      NBinding b = this.idx.lookupQname(qname);
      assertNotNull("no binding found for " + qname, b);
      return b;
   }

   public NBinding assertBinding(String qname, NBinding.Kind kind) throws Exception {
      NBinding b = this.getBinding(qname);
      assertEquals(kind, b.getKind());
      return b;
   }

   public void assertNoBinding(String qname) throws Exception {
      NBinding b = this.idx.lookupQname(qname);
      assertNull("Should not have found binding for " + qname, b);
   }

   public NBinding assertAttributeBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.ATTRIBUTE);
   }

   public NBinding assertBuiltinBinding(String qname) throws Exception {
      NBinding b = this.getBinding(qname);
      assertTrue(b.isBuiltin());
      return b;
   }

   public NBinding assertClassBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.CLASS);
   }

   public NBinding assertConstructorBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.CONSTRUCTOR);
   }

   public NBinding assertFunctionBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.FUNCTION);
   }

   public NBinding assertMethodBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.METHOD);
   }

   public NBinding assertModuleBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.MODULE);
   }

   public NBinding assertScopeBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.SCOPE);
   }

   public NBinding assertVariableBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.VARIABLE);
   }

   public NBinding assertParamBinding(String qname) throws Exception {
      return this.assertBinding(qname, NBinding.Kind.PARAMETER);
   }

   public void assertStaticSynthetic(NBinding b) {
      assertTrue(b.isStatic());
      assertTrue(b.isSynthetic());
   }

   public Def getDefinition(String qname, int offset, int length) throws Exception {
      NBinding b = this.getBinding(qname);
      assertNotNull(b.getDefs());
      Iterator var5 = b.getDefs().iterator();

      Def def;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         def = (Def)var5.next();
      } while(offset != def.start() || length != def.end() - def.start());

      return def;
   }

   public void assertDefinition(String qname, int offset, int length) throws Exception {
      Def def = this.getDefinition(qname, offset, length);
      if (def == null) {
         fail("No definition for " + qname + " at " + offset + " of len " + length);
      }

   }

   public void assertNoDefinition(String msg, String qname, int pos, int len) throws Exception {
      Def def = this.getDefinition(qname, pos, len);
      assertNull(msg, def);
   }

   public void assertDefinition(String qname, int offset) throws Exception {
      String[] names = qname.split("[.&@]");
      this.assertDefinition(qname, offset, names[names.length - 1].length());
   }

   public void assertDefinition(String qname, String name, int offset) throws Exception {
      this.assertDefinition(qname, offset, name.length());
   }

   public Ref getRefOrNull(String qname, int offset, int length) throws Exception {
      NBinding b = this.getBinding(qname);
      assertNotNull("Null refs list for " + qname, b.getRefs());
      Iterator var5 = b.getRefs().iterator();

      Ref ref;
      do {
         if (!var5.hasNext()) {
            return null;
         }

         ref = (Ref)var5.next();
      } while(offset != ref.start() || length != ref.length());

      return ref;
   }

   public Ref getRefOrFail(String qname, int offset, int length) throws Exception {
      Ref ref = this.getRefOrNull(qname, offset, length);
      assertNotNull("No reference to " + qname + " at offset " + offset + " of length " + length, ref);
      return ref;
   }

   public void assertReference(String qname, int offset, int length) throws Exception {
      assertTrue(this.getRefOrFail(qname, offset, length).isRef());
   }

   public void assertReference(String qname, int offset, String refname) throws Exception {
      this.assertReference(qname, offset, refname.length());
   }

   public void assertReference(String qname, int offset) throws Exception {
      String[] names = qname.split("[.&@]");
      this.assertReference(qname, offset, names[names.length - 1]);
   }

   public void assertNoReference(String msg, String qname, int pos, int len) throws Exception {
      assertNull(msg, this.getRefOrNull(qname, pos, len));
   }

   public void assertCall(String qname, int offset, int length) throws Exception {
      assertTrue(this.getRefOrFail(qname, offset, length).isCall());
   }

   public void assertCall(String qname, int offset, String refname) throws Exception {
      this.assertCall(qname, offset, refname.length());
   }

   public void assertCall(String qname, int offset) throws Exception {
      String[] names = qname.split("[.&@]");
      this.assertCall(qname, offset, names[names.length - 1]);
   }

   public void assertConstructed(String qname, int offset, int length) throws Exception {
      assertTrue(this.getRefOrFail(qname, offset, length).isNew());
   }

   public void assertConstructed(String qname, int offset, String refname) throws Exception {
      this.assertConstructed(qname, offset, refname.length());
   }

   public void assertConstructed(String qname, int offset) throws Exception {
      String[] names = qname.split("[.&@]");
      this.assertConstructed(qname, offset, names[names.length - 1]);
   }

   public NType getTypeBinding(String typeQname) throws Exception {
      NType type = this.idx.lookupQnameType(typeQname);
      assertNotNull("No recorded type for " + typeQname, type);
      return type;
   }

   public NBinding assertBindingType(String bindingQname, String typeQname) throws Exception {
      NBinding b = this.getBinding(bindingQname);
      NType expected = this.getTypeBinding(typeQname);
      assertEquals("Wrong binding type", expected, NUnknownType.follow(b.getType()));
      return b;
   }

   public NBinding assertBindingType(String bindingQname, Class type) throws Exception {
      NBinding b = this.getBinding(bindingQname);
      NType btype = NUnknownType.follow(b.getType());
      assertTrue("Wrong type: expected " + type + " but was " + btype, type.isInstance(btype));
      return b;
   }

   public void assertListType(String bindingQname) throws Exception {
      this.assertListType(bindingQname, (String)null);
   }

   public void assertListType(String bindingQname, String eltTypeQname) throws Exception {
      NBinding b = this.getBinding(bindingQname);
      NType btype = b.followType();
      assertTrue(btype.isListType());
      if (eltTypeQname != null) {
         NType eltType = this.getTypeBinding(eltTypeQname);
         assertEquals(eltType, NUnknownType.follow(btype.asListType().getElementType()));
      }

   }

   public void assertStringType(String bindingQname) throws Exception {
      this.assertBindingType(bindingQname, "__builtin__.str");
   }

   public void assertNumType(String bindingQname) throws Exception {
      this.assertBindingType(bindingQname, "__builtin__.float");
   }

   public void assertInstanceType(String bindingQname, String classQname) throws Exception {
      this.assertBindingType(bindingQname, classQname);
   }

   static {
      LOGGING_LEVEL = Level.OFF;
      String home = System.getProperty("python.home", "dist");
      String test = System.getProperty("python.test.source.dir", "tests/java");
      File source = new File(test, "org/python/indexer");
      TEST_DATA_DIR = (new File(source, "data")).getAbsolutePath() + File.separator;
      TEST_LIB_DIR = (new File(home, "Lib")).getAbsolutePath() + File.separator;
      setUpLogging();
   }
}
