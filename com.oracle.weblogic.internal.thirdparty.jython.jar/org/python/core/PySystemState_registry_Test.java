package org.python.core;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Properties;
import junit.framework.TestCase;

public class PySystemState_registry_Test extends TestCase {
   private static final String REGISTRY = "registry";
   private static final String USER_REGISTRY = ".jython";
   private static final String DIST = "dist";
   private static final String FIRST_PROP = "first.test.property";
   private static final String SECOND_PROP = "second.test.property";
   private static final String USER_HOME = "user.home";
   private static final String PYTHON_HOME = "python.home";
   private static final String ANY = "any";
   private static final String PRE = "pre";
   private static final String POST = "post";
   private static final String INSTALLED = "installed";
   private static final String USER = "user";
   private String _originalUserHome;
   private File _root;
   private String _originalRegistryContent;
   private Properties _originalRegistry;
   private File _tmpDir;

   protected void setUp() throws Exception {
      this.findRoot();
      this.storeOriginals();
      this.uninitialize();
   }

   protected void tearDown() throws Exception {
      this.restoreOriginals();
      this.cleanup();
   }

   public void testInitialize_PrePostProperties() throws Exception {
      Properties preProps = this.createPreProperties();
      preProps.setProperty("first.test.property", "pre");
      preProps.setProperty("second.test.property", "pre");
      Properties postProps = this.createPostProperties();
      postProps.setProperty("second.test.property", "post");
      PySystemState.initialize(preProps, postProps);
      Properties registry = PySystemState.registry;
      String first = registry.getProperty("first.test.property", "any");
      String second = registry.getProperty("second.test.property", "any");
      assertEquals("pre", first);
      assertEquals("post", second);
   }

   public void testInitialize_Registry_User() throws Exception {
      String installedContent = "first.test.property".concat("=").concat("installed");
      this.appendToInstalledRegistry(installedContent);
      String userContent = "first.test.property".concat("=").concat("user");
      this.addUserRegistry(userContent);
      Properties preProps = this.createPreProperties();
      Properties postProps = this.createPostProperties();
      PySystemState.initialize(preProps, postProps);
      Properties registry = PySystemState.registry;
      String first = registry.getProperty("first.test.property", "any");
      assertEquals("user", first);
   }

   public void testInitialize_Pre_Registries() throws Exception {
      String contentToAppend = "first.test.property".concat("=").concat("installed");
      this.appendToInstalledRegistry(contentToAppend);
      String userContent = "first.test.property".concat("=").concat("user");
      this.addUserRegistry(userContent);
      Properties preProps = this.createPreProperties();
      preProps.setProperty("first.test.property", "pre");
      Properties postProps = this.createPostProperties();
      PySystemState.initialize(preProps, postProps);
      Properties registry = PySystemState.registry;
      String first = registry.getProperty("first.test.property", "any");
      assertEquals("pre", first);
   }

   public void testInitialize_Post_Registries() throws Exception {
      String contentToAppend = "first.test.property".concat("=").concat("installed");
      this.appendToInstalledRegistry(contentToAppend);
      String userContent = "first.test.property".concat("=").concat("user");
      this.addUserRegistry(userContent);
      Properties preProps = this.createPreProperties();
      preProps.setProperty("first.test.property", "pre");
      Properties postProps = this.createPostProperties();
      postProps.setProperty("first.test.property", "post");
      PySystemState.initialize(preProps, postProps);
      Properties registry = PySystemState.registry;
      String first = registry.getProperty("first.test.property", "any");
      assertEquals("post", first);
   }

   private void findRoot() throws Exception {
      Class thisClass = this.getClass();
      String classFileName = "/".concat(thisClass.getName().replace('.', '/')).concat(".class");
      URL url = thisClass.getResource(classFileName);
      assertNotNull(url);
      String path = URLDecoder.decode(url.getPath(), "UTF-8");
      assertTrue(path.endsWith(classFileName));
      String classesDirName = path.substring(0, path.length() - classFileName.length());
      File classesDir = new File(classesDirName);
      assertTrue(classesDir.exists());
      assertTrue(classesDir.isDirectory());
      this._root = new File(classesDir.getParentFile().getParentFile(), "dist");
      assertTrue(this._root.exists());
      assertTrue(this._root.isDirectory());
   }

   private void uninitialize() throws Exception {
      Field field = PySystemState.class.getDeclaredField("initialized");
      field.setAccessible(true);
      field.set((Object)null, false);
      PySystemState.registry = null;
   }

   private void storeOriginals() throws Exception {
      this._originalRegistry = PySystemState.registry;
      this._originalUserHome = System.getProperty("user.home");
      File installedRegistry = new File(this.getRoot(), "registry");
      assertTrue(installedRegistry.exists());
      this._originalRegistryContent = this.readAll(installedRegistry);
   }

   private void restoreOriginals() throws Exception {
      PySystemState.registry = this._originalRegistry;
      String originalUserHome = this.getOriginalUserHome();
      if (originalUserHome != null) {
         System.setProperty("user.home", originalUserHome);
      }

      this.writeInstalledRegistry(this.getOriginalRegistryContent());
   }

   private void writeInstalledRegistry(String content) throws IOException {
      if (content != null && content.length() > 0) {
         File installedRegistry = new File(this.getRoot(), "registry");
         assertTrue(installedRegistry.exists());
         this.write(installedRegistry, content);
      }

   }

   private void appendToInstalledRegistry(String contentToAppend) throws IOException {
      if (contentToAppend != null && contentToAppend.length() > 0) {
         String content = this.getOriginalRegistryContent().concat(contentToAppend);
         this.writeInstalledRegistry(content);
      }

   }

   private void addUserRegistry(String content) throws Exception {
      File tmpDir = this.createTempDir();
      System.setProperty("user.home", tmpDir.getCanonicalPath());
      File userRegistry = new File(tmpDir, ".jython");
      this.write(userRegistry, content);
      assertTrue(userRegistry.exists());
      assertTrue(userRegistry.isFile());
   }

   private Properties createPreProperties() {
      return new Properties(System.getProperties());
   }

   private Properties createPostProperties() throws Exception {
      Properties postProps = new Properties();
      postProps.setProperty("python.home", this.getRoot().getCanonicalPath());
      return postProps;
   }

   private File createTempDir() throws Exception {
      String name = this.getClass().getSimpleName();
      File tmpFile = File.createTempFile(name, "");
      assertTrue(tmpFile.exists());
      assertTrue(tmpFile.isFile());
      File parent = tmpFile.getParentFile();
      assertTrue(parent.exists());
      assertTrue(parent.isDirectory());
      assertTrue(tmpFile.delete());
      File tmpDir = new File(parent, name);
      assertTrue(tmpDir.mkdir());
      assertTrue(tmpDir.exists());
      assertTrue(tmpDir.isDirectory());
      this._tmpDir = tmpDir;
      return tmpDir;
   }

   private void cleanup() {
      File tmpDir = this.getTmpDir();
      if (tmpDir != null && tmpDir.exists()) {
         assertTrue(this.rmdir(tmpDir));
      }

      this._tmpDir = null;
   }

   private String readAll(File file) throws IOException {
      FileReader fileReader = new FileReader(file);

      try {
         StringBuffer sb = new StringBuffer();
         char[] b = new char[8192];

         int n;
         while((n = fileReader.read(b)) > 0) {
            sb.append(b, 0, n);
         }

         String var6 = sb.toString();
         return var6;
      } finally {
         fileReader.close();
      }
   }

   private void write(File file, String content) throws IOException {
      FileWriter writer = new FileWriter(file);
      writer.write(content);
      writer.flush();
      writer.close();
   }

   private boolean rmdir(File dir) {
      boolean success = true;
      if (dir.exists()) {
         File[] files = dir.listFiles();

         for(int i = 0; i < files.length; ++i) {
            File file = files[i];
            if (file.isFile()) {
               success = this.carryOnResult(file.delete(), success);
            } else if (file.isDirectory()) {
               success = this.carryOnResult(this.rmdir(file), success);
            }
         }

         success = this.carryOnResult(dir.delete(), success);
      }

      return success;
   }

   private boolean carryOnResult(boolean newResult, boolean existingResult) {
      return existingResult ? newResult : existingResult;
   }

   private File getRoot() {
      return this._root;
   }

   private File getTmpDir() {
      return this._tmpDir;
   }

   private String getOriginalUserHome() {
      return this._originalUserHome;
   }

   private String getOriginalRegistryContent() {
      return this._originalRegistryContent;
   }
}
