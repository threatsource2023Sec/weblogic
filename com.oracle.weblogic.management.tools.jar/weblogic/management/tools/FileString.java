package weblogic.management.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

class FileString {
   private String[] m_file;
   private String m_fileName;
   private int m_current;

   public FileString(String fileName) throws FileNotFoundException, IOException {
      this.m_fileName = fileName;
      this.m_file = readFileInBuffer(fileName);
      this.m_current = 0;
   }

   private static String[] readFileInBuffer(String fileName) throws FileNotFoundException, IOException {
      File f = new File(fileName);
      BufferedReader br = new BufferedReader(new FileReader(f));
      List l = new ArrayList();

      for(String line = br.readLine(); null != line; line = br.readLine()) {
         l.add(line);
      }

      String[] result = new String[l.size()];
      Iterator it = l.iterator();

      for(int i = 0; i < l.size(); ++i) {
         result[i] = (String)it.next();
      }

      return result;
   }

   public String getFileName() {
      return this.m_fileName;
   }

   public int getCurrentIndex() {
      return this.m_current;
   }

   public String getPackageName() {
      String result = null;
      String[] hits = this.grep("package");
      if (hits.length > 0) {
         StringTokenizer st = new StringTokenizer(hits[0]);
         st.nextToken();
         result = st.nextToken();
         int index = result.indexOf(";");
         result = result.substring(0, index);
      }

      return result;
   }

   public String getImportClass(String c) {
      String[] hits = this.grep("import");

      for(int i = 0; i < hits.length; ++i) {
         if (-1 != hits[i].indexOf(c)) {
            StringTokenizer st = new StringTokenizer(hits[i]);
            st.nextToken();
            String pkg = st.nextToken();
            int index = pkg.indexOf(";");
            return pkg.substring(0, index);
         }
      }

      return null;
   }

   public String getSuperClassName() {
      String result = null;
      String extendClause = new String();
      Integer[] extend = this.grepByLineNumber("extends");
      if (extend.length > 0) {
         int lineNumber = extend[0];
         String currentLine = this.readLine(lineNumber++);

         for(extendClause = extendClause + currentLine; -1 == currentLine.indexOf("{"); extendClause = extendClause + " " + currentLine) {
            currentLine = this.readLine(lineNumber++);
         }

         StringTokenizer st = new StringTokenizer(extendClause);

         for(boolean found = false; !found && st.hasMoreTokens(); found = st.nextToken().equals("extends")) {
         }

         if (st.hasMoreTokens()) {
            result = st.nextToken();
         }
      }

      return result;
   }

   public String toString() {
      return this.getFileName();
   }

   public String readAndAdvance() {
      String result = this.readCurrent();
      ++this.m_current;
      return result;
   }

   public String readCurrent() {
      String result = this.m_file[this.m_current];
      return result;
   }

   public String readLine(int n) {
      return this.m_file[n];
   }

   public void advance() {
      ++this.m_current;
   }

   public String[] grep(String s) {
      List lResult = new ArrayList();

      for(int i = 0; i < this.m_file.length; ++i) {
         if (-1 != this.m_file[i].indexOf(s)) {
            lResult.add(this.m_file[i]);
         }
      }

      String[] result = new String[lResult.size()];
      Iterator it = lResult.iterator();

      for(int i = 0; i < result.length; ++i) {
         result[i] = (String)it.next();
      }

      return result;
   }

   public Integer[] grepByLineNumber(String s) {
      List lResult = new ArrayList();

      for(int i = 0; i < this.m_file.length; ++i) {
         if (-1 != this.m_file[i].indexOf(s)) {
            lResult.add(new Integer(i));
         }
      }

      Integer[] result = new Integer[lResult.size()];
      Iterator it = lResult.iterator();

      for(int i = 0; i < result.length; ++i) {
         result[i] = (Integer)it.next();
      }

      return result;
   }

   public boolean eof() {
      return this.m_current >= this.m_file.length;
   }
}
