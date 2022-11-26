package weblogic.management.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.utils.Debug;
import weblogic.utils.compiler.CodeGenerationException;

public class TagParser {
   private List m_listener = new ArrayList();
   private String m_fileName;
   private List m_classTags = new ArrayList();
   private List m_taggedMethods = new ArrayList();
   private HashMap m_defaultValues = new HashMap();

   public TagParser(String fileName) {
      this.m_fileName = fileName;
   }

   public static void main(String[] argv) {
      try {
         TagParser tp = new TagParser(argv[0]);
         tp.parse();
         p("============= " + argv[0]);
         p("CLASSTAGS:");
         Iterator it = tp.getClassTags();

         while(it.hasNext()) {
            p("   " + it.next().toString());
         }

         p("");
         p("METHOD TAGS:");
         it = tp.getTaggedMethods();

         while(it.hasNext()) {
            TaggedMethod tm = (TaggedMethod)it.next();
            p("   METHOD:" + tm.getMethodName());
            String[] tags = tm.getTags();

            for(int i = 0; i < tags.length; ++i) {
               p("      " + tags[i]);
            }
         }

         p("");
         p("METHODS WITH A DEFAULT TAG:");
         TaggedMethod[] tm = tp.getMethodsWithTag("@default");

         for(int i = 0; i < tm.length; ++i) {
            p("   " + tm[i].getMethodName());
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   private static void p(String s) {
      Debug.say("@@@ " + s);
   }

   public String getFileName() {
      return this.m_fileName;
   }

   public String getCurrentClassName() {
      int ind1 = this.m_fileName.lastIndexOf("/") + 1;
      if (ind1 <= 0) {
         ind1 = this.m_fileName.lastIndexOf("\\") + 1;
      }

      if (ind1 <= 0) {
         ind1 = 0;
      }

      int ind2 = this.m_fileName.indexOf(".java");
      return this.m_fileName.substring(ind1, ind2);
   }

   public DefaultValue getDefaultValue(String getterMethod) {
      Debug.assertion(0 == getterMethod.indexOf("get") || 0 == getterMethod.indexOf("is"), "Illegal getDefaultValue for non-getter:" + getterMethod + " file:" + this.m_fileName);
      return (DefaultValue)this.m_defaultValues.get(getterMethod);
   }

   public TaggedMethod[] getMethodsWithTag(String tagName) {
      HashMap result = new HashMap();
      if (-1 == tagName.indexOf("@")) {
         tagName = "@" + tagName;
      }

      Iterator it = this.m_taggedMethods.iterator();

      while(it.hasNext()) {
         TaggedMethod tm = (TaggedMethod)it.next();
         String[] tags = tm.getTags();

         for(int i = 0; i < tags.length; ++i) {
            if (-1 != tags[i].indexOf(tagName) && null == result.get(tm.getMethodSignature())) {
               result.put(tm.getMethodSignature(), tm);
            }
         }
      }

      Object[] aResult = result.values().toArray();
      TaggedMethod[] tResult = new TaggedMethod[aResult.length];
      System.arraycopy(aResult, 0, tResult, 0, aResult.length);
      return tResult;
   }

   public Iterator getTaggedMethods() {
      return this.m_taggedMethods.iterator();
   }

   public Iterator getClassTags() {
      return this.m_classTags.iterator();
   }

   public void parse() throws IOException {
      FileString fs = new FileString(this.m_fileName);
      this.parseClassTags(fs);
      this.parseMethodTags(fs);
      this.initDefaultValues();
      String[] parents = this.getSuperClassFileNames(this.m_fileName);

      for(int i = 0; i < parents.length; ++i) {
         fs = new FileString(parents[i]);

         for(String line = fs.readAndAdvance(); -1 == line.indexOf("{"); line = fs.readAndAdvance()) {
         }

         this.parseMethodTags(fs);
      }

      if (!this.isAbstractClass()) {
         this.validateMethodTags();
      }

   }

   public String locateFileFromPackage(String pkg) {
      String result = null;
      String path = pkg.replace('.', File.separatorChar) + ".java";
      File f = new File(path);
      if (f.exists()) {
         result = path;
      }

      return result;
   }

   private String[] getSuperClassFileNames(String current) throws FileNotFoundException, IOException {
      List lResult = new ArrayList();
      FileString fs = new FileString(current);
      String parentClass = fs.getSuperClassName();

      while(null != parentClass && -1 != parentClass.indexOf("MBean") && -1 == parentClass.indexOf("WeblogicMBean")) {
         String parentImport = fs.getImportClass(parentClass);
         String thisPackage = fs.getPackageName();
         String parentFileName = null;
         if (null != parentImport) {
            parentFileName = this.locateFileFromPackage(parentImport);
         } else {
            parentFileName = this.locateFileFromPackage(thisPackage + "." + parentClass);
            if (null == parentFileName) {
               parentFileName = this.locateFileFromPackage(parentClass);
            }
         }

         if (null != parentFileName) {
            lResult.add(parentFileName);
            fs = new FileString(parentFileName);
            parentClass = fs.getSuperClassName();
         } else {
            parentClass = null;
         }
      }

      String[] result = new String[lResult.size()];
      Iterator it = lResult.iterator();

      for(int i = 0; i < result.length; ++i) {
         result[i] = (String)it.next();
      }

      return result;
   }

   private boolean isNewMethod(List tags, TaggedMethod tm) {
      Iterator it = tags.iterator();

      TaggedMethod tm2;
      do {
         if (!it.hasNext()) {
            return true;
         }

         tm2 = (TaggedMethod)it.next();
      } while(!tm2.getMethodName().equals(tm.getMethodName()));

      return false;
   }

   private boolean isAbstractClass() {
      Iterator it = this.m_classTags.iterator();

      String tag;
      do {
         if (!it.hasNext()) {
            return false;
         }

         tag = (String)it.next();
      } while(!"@abstract".equals(tag));

      return true;
   }

   private void parseMethodTags(FileString fs) throws IOException {
      String line = fs.readAndAdvance();
      int lastSeen = -1;

      while(!fs.eof()) {
         while(!fs.eof() && -1 == line.indexOf("(") && -1 == line.indexOf("/**")) {
            line = fs.readAndAdvance();
         }

         if (!fs.eof() && line.indexOf("/**") > 0) {
            lastSeen = fs.getCurrentIndex();
         } else if (!fs.eof() && line.indexOf("(") > 0 && line.indexOf("*") == -1 && -1 != lastSeen) {
            List tags = this.parseTags(fs, lastSeen, fs.getCurrentIndex());
            Iterator it = tags.iterator();
            List tagList = new ArrayList();

            while(it.hasNext()) {
               String tagLine = it.next().toString();
               tagList.add(tagLine);
            }

            TaggedMethod tm = new TaggedMethod(line, tagList);
            this.m_taggedMethods.add(tm);
            lastSeen = -1;
         }

         if (!fs.eof()) {
            line = fs.readAndAdvance();
         }
      }

   }

   private void parseClassTags(FileString fs) throws IOException {
      while(-1 == fs.readCurrent().indexOf("interface") && -1 == fs.readCurrent().indexOf("/**")) {
         fs.advance();
      }

      if (-1 != fs.readCurrent().indexOf("/**")) {
         this.m_classTags = this.parseTags(fs);
         fs.advance();
      }

   }

   private List parseTags(FileString fs, int beginning, int end) throws IOException {
      List result = new ArrayList();

      for(int i = beginning; i < end; ++i) {
         String line = fs.readLine(i);
         int ind = line.indexOf("@");
         if (ind != -1) {
            result.add(line.substring(ind));
         }
      }

      return result;
   }

   private List parseTags(FileString fs) throws IOException {
      List result = new ArrayList();

      for(String line = fs.readAndAdvance(); !fs.eof() && -1 == line.indexOf("*/"); line = fs.readAndAdvance()) {
         int ind = line.indexOf("@");
         if (ind != -1) {
            result.add(line.substring(ind));
         }
      }

      return result;
   }

   private void initDefaultValues() {
      try {
         TaggedMethod[] methods = this.getMethodsWithTag("@default");

         for(int i = 0; i < methods.length; ++i) {
            String tagValue = methods[i].getTagValue("@default");
            String type = methods[i].getReturnType();
            String methodName = methods[i].getMethodName();
            DefaultValue defaultValue = null;
            if (!"String".equals(type) && !"java.lang.String".equals(type)) {
               if ("boolean".equals(type)) {
                  defaultValue = new DefaultValue(Boolean.valueOf(tagValue).toString(), true);
               } else {
                  if (!"int".equals(type) && !"Integer".equals(type) && !"java.lang.Integer".equals(type)) {
                     throw new CodeGenerationException("Can't set default value for " + methods[i].getMethodName() + ". Its type isn't supported");
                  }

                  try {
                     defaultValue = new DefaultValue(Integer.valueOf(tagValue).toString(), true);
                  } catch (NumberFormatException var8) {
                     throw new CodeGenerationException("Error parsing int for " + methods[i].getMethodName());
                  }
               }
            } else {
               defaultValue = new DefaultValue(tagValue, false);
            }

            this.m_defaultValues.put(methodName, defaultValue);
         }
      } catch (CodeGenerationException var9) {
         var9.printStackTrace();
      }

   }

   private void validateMethodTags() {
      TaggedMethod[] methods = this.getMethodsWithTag("@dtd-order");
      TaggedMethod[] valid = new TaggedMethod[methods.length];
      int orCount = 0;

      int i;
      for(i = 0; i < methods.length; ++i) {
         String tag = methods[i].getTagValue("@dtd-order");
         int ind = tag.length();
         int or = tag.indexOf("|");
         int opt = tag.indexOf("?");
         if (or != -1 && opt != -1 && or > opt) {
            throw new RuntimeException(this.m_fileName + ": Error with dtd-order tag for method" + methods[i].getMethodName() + " tag:" + tag + ". The | must always come before the ?");
         }

         if (or != -1) {
            ind = or;
         } else if (opt != -1) {
            ind = opt;
         }

         Debug.assertion(-1 != ind, methods[i].getMethodName() + " tag:" + tag);
         int order = Integer.parseInt(tag.substring(0, ind));
         if (order > methods.length - 1) {
            throw new RuntimeException(this.m_fileName + ": The dtd-order for method " + methods[i].getMethodSignature() + " is too high.  Make sure the dtd-order in this interface is correct");
         }

         if (valid[i] != null) {
            if (or == -1 || valid[i].getTagValue("@dtd-order").indexOf("|") == -1) {
               throw new RuntimeException(this.m_fileName + ": The dtd-order for method " + methods[i].getMethodSignature() + " is incorrect.  The method " + valid[i].getMethodSignature() + "is also declared with a dtd-order of " + order + ".  You must fix the ordering of these methods or declare them with the '|' (or) tag.");
            }

            ++orCount;
         } else {
            valid[i] = methods[i];
         }
      }

      i = 0;

      for(int i = 0; i < valid.length; ++i) {
         if (valid[i] == null) {
            ++i;
         }
      }

      if (i != orCount) {
         throw new RuntimeException(this.m_fileName + ": The dtd-order values for some methods in this class are incorrect.");
      }
   }
}
