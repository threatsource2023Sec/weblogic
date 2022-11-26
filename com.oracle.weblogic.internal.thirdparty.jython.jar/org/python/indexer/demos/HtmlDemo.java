package org.python.indexer.demos;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import org.python.indexer.Indexer;
import org.python.indexer.Util;

public class HtmlDemo {
   private static final File OUTPUT_DIR = new File((new File("./html")).getAbsolutePath());
   private static final String CSS = ".builtin {color: #5b4eaf;}\n.comment, .block-comment {color: #005000; font-style: italic;}\n.constant {color: #888888;}\n.decorator {color: #778899;}\n.doc-string {color: #005000;}\n.error {border-bottom: 1px solid red;}\n.field-name {color: #2e8b57;}\n.function {color: #880000;}\n.identifier {color: #8b7765;}\n.info {border-bottom: 1px dotted RoyalBlue;}\n.keyword {color: #0000cd;}\n.lineno {color: #aaaaaa;}\n.number {color: #483d8b;}\n.parameter {color: #2e8b57;}\n.string {color: #4169e1;}\n.type-name {color: #4682b4;}\n.warning {border-bottom: 1px dotted orange;}\n";
   private Indexer indexer;
   private File rootDir;
   private String rootPath;
   private Linker linker;

   private void makeOutputDir() throws Exception {
      if (!OUTPUT_DIR.exists()) {
         OUTPUT_DIR.mkdirs();
         info("created directory: " + OUTPUT_DIR.getAbsolutePath());
      }

   }

   private void start(File stdlib, File fileOrDir) throws Exception {
      this.rootDir = fileOrDir.isFile() ? fileOrDir.getParentFile() : fileOrDir;
      this.rootPath = this.rootDir.getCanonicalPath();
      this.indexer = new Indexer();
      this.indexer.addPath(stdlib.getCanonicalPath());
      info("building index...");
      this.indexer.loadFileRecursive(fileOrDir.getCanonicalPath());
      this.indexer.ready();
      info(this.indexer.getStatusReport());
      this.generateHtml();
   }

   private void generateHtml() throws Exception {
      info("generating html...");
      this.makeOutputDir();
      this.linker = new Linker(this.rootPath, OUTPUT_DIR);
      this.linker.findLinks(this.indexer);
      int rootLength = this.rootPath.length();
      Iterator var2 = this.indexer.getLoadedFiles().iterator();

      while(var2.hasNext()) {
         String path = (String)var2.next();
         if (path.startsWith(this.rootPath)) {
            File destFile = Util.joinPath(OUTPUT_DIR, path.substring(rootLength));
            destFile.getParentFile().mkdirs();
            String destPath = destFile.getAbsolutePath() + ".html";
            String html = this.markup(path);
            Util.writeFile(destPath, html);
         }
      }

      info("wrote " + this.indexer.getLoadedFiles().size() + " files to " + OUTPUT_DIR);
   }

   private String markup(String path) throws Exception {
      String source = Util.readFile(path);
      List styles = (new Styler(this.indexer, this.linker)).addStyles(path, source);
      styles.addAll(this.linker.getStyles(path));
      source = (new StyleApplier(path, source, styles)).apply();
      String outline = (new HtmlOutline(this.indexer)).generate(path);
      return "<html><head title=\"" + path + "\">" + "<style type='text/css'>\n" + ".builtin {color: #5b4eaf;}\n.comment, .block-comment {color: #005000; font-style: italic;}\n.constant {color: #888888;}\n.decorator {color: #778899;}\n.doc-string {color: #005000;}\n.error {border-bottom: 1px solid red;}\n.field-name {color: #2e8b57;}\n.function {color: #880000;}\n.identifier {color: #8b7765;}\n.info {border-bottom: 1px dotted RoyalBlue;}\n.keyword {color: #0000cd;}\n.lineno {color: #aaaaaa;}\n.number {color: #483d8b;}\n.parameter {color: #2e8b57;}\n.string {color: #4169e1;}\n.type-name {color: #4682b4;}\n.warning {border-bottom: 1px dotted orange;}\n" + "</style>\n" + "</head>\n<body>\n" + "<table width=100% border='1px solid gray'><tr><td valign='top'>" + outline + "</td><td>" + "<pre>" + this.addLineNumbers(source) + "</pre>" + "</td></tr></table></body></html>";
   }

   private String addLineNumbers(String source) {
      StringBuilder result = new StringBuilder((int)((double)source.length() * 1.2));
      int count = 1;
      String[] var4 = source.split("\n");
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         String line = var4[var6];
         result.append("<span class='lineno'>");
         result.append(count++);
         result.append("</span> ");
         result.append(line);
         result.append("\n");
      }

      return result.toString();
   }

   private static void abort(String msg) {
      System.err.println(msg);
      System.exit(1);
   }

   private static void info(Object msg) {
      System.out.println(msg);
   }

   private static void usage() {
      info("Usage:  java org.python.indexer.HtmlDemo <python-stdlib> <file-or-dir>");
      info("  first arg specifies the root of the python standard library");
      info("  second arg specifies file or directory for which to generate the index");
      info("Example that generates an index for just the email libraries:");
      info(" java org.python.indexer.HtmlDemo ./CPythonLib ./CPythonLib/email");
      System.exit(0);
   }

   private static File checkFile(String path) {
      File f = new File(path);
      if (!f.canRead()) {
         abort("Path not found or not readable: " + path);
      }

      return f;
   }

   public static void main(String[] args) throws Exception {
      if (args.length != 2) {
         usage();
      }

      File fileOrDir = checkFile(args[1]);
      File stdlib = checkFile(args[0]);
      if (!stdlib.isDirectory()) {
         abort("Not a directory: " + stdlib);
      }

      (new HtmlDemo()).start(stdlib, fileOrDir);
   }
}
