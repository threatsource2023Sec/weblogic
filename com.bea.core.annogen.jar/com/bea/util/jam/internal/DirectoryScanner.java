package com.bea.util.jam.internal;

import com.bea.util.jam.provider.JamLogger;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class DirectoryScanner {
   private boolean mCaseSensitive = true;
   private File mRoot;
   private JamLogger mLogger;
   private List mIncludeList = null;
   private List mExcludeList = null;
   private String[] mIncludes;
   private String[] mExcludes;
   private Vector mFilesIncluded;
   private Vector mDirsIncluded;
   private boolean mIsDirty = false;
   private String[] mIncludedFilesCache = null;

   public DirectoryScanner(File dirToScan, JamLogger logger) {
      if (logger == null) {
         throw new IllegalArgumentException("null logger");
      } else {
         this.mLogger = logger;
         this.mRoot = dirToScan;
      }
   }

   public void include(String pattern) {
      if (this.mIncludeList == null) {
         this.mIncludeList = new ArrayList();
      }

      this.mIncludeList.add(pattern);
      this.mIsDirty = true;
   }

   public void exclude(String pattern) {
      if (this.mExcludeList == null) {
         this.mExcludeList = new ArrayList();
      }

      this.mExcludeList.add(pattern);
      this.mIsDirty = true;
   }

   public String[] getIncludedFiles() throws IOException {
      if (!this.mIsDirty && this.mIncludedFilesCache != null) {
         return this.mIncludedFilesCache;
      } else {
         String[] exc;
         if (this.mIncludeList != null) {
            exc = new String[this.mIncludeList.size()];
            this.mIncludeList.toArray(exc);
            this.setIncludes(exc);
         } else {
            this.setIncludes((String[])null);
         }

         if (this.mExcludeList != null) {
            exc = new String[this.mExcludeList.size()];
            this.mExcludeList.toArray(exc);
            this.setExcludes(exc);
         } else {
            this.setExcludes((String[])null);
         }

         this.scan();
         this.mIncludedFilesCache = new String[this.mFilesIncluded.size()];
         this.mFilesIncluded.copyInto(this.mIncludedFilesCache);
         return this.mIncludedFilesCache;
      }
   }

   public void setDirty() {
      this.mIsDirty = true;
   }

   public File getRoot() {
      return this.mRoot;
   }

   private void setIncludes(String[] includes) {
      if (includes == null) {
         this.mIncludes = null;
      } else {
         this.mIncludes = new String[includes.length];

         for(int i = 0; i < includes.length; ++i) {
            String pattern = includes[i].replace('/', File.separatorChar).replace('\\', File.separatorChar);
            if (pattern.endsWith(File.separator)) {
               pattern = pattern + "**";
            }

            this.mIncludes[i] = pattern;
         }
      }

   }

   private void setExcludes(String[] excludes) {
      if (excludes == null) {
         this.mExcludes = null;
      } else {
         this.mExcludes = new String[excludes.length];

         for(int i = 0; i < excludes.length; ++i) {
            String pattern = excludes[i].replace('/', File.separatorChar).replace('\\', File.separatorChar);
            if (pattern.endsWith(File.separator)) {
               pattern = pattern + "**";
            }

            this.mExcludes[i] = pattern;
         }
      }

   }

   private void scan() throws IllegalStateException, IOException {
      if (this.mIncludes == null) {
         this.mIncludes = new String[1];
         this.mIncludes[0] = "**";
      }

      if (this.mExcludes == null) {
         this.mExcludes = new String[0];
      }

      this.mFilesIncluded = new Vector();
      this.mDirsIncluded = new Vector();
      if (this.isIncluded("") && !this.isExcluded("")) {
         this.mDirsIncluded.addElement("");
      }

      this.scandir(this.mRoot, "", true);
   }

   private void scandir(File dir, String vpath, boolean fast) throws IOException {
      if (this.mLogger.isVerbose((Object)this)) {
         this.mLogger.verbose("[DirectoryScanner] scanning dir " + dir + " for '" + vpath + "'");
      }

      String[] newfiles = dir.list();
      if (newfiles == null) {
         throw new IOException("IO error scanning directory " + dir.getAbsolutePath());
      } else {
         for(int i = 0; i < newfiles.length; ++i) {
            String name = vpath + newfiles[i];
            File file = new File(dir, newfiles[i]);
            if (file.isDirectory()) {
               if (this.isIncluded(name) && !this.isExcluded(name)) {
                  this.mDirsIncluded.addElement(name);
                  if (this.mLogger.isVerbose((Object)this)) {
                     this.mLogger.verbose("...including dir " + name);
                  }

                  this.scandir(file, name + File.separator, fast);
               } else if (this.couldHoldIncluded(name)) {
                  this.scandir(file, name + File.separator, fast);
               }
            } else if (file.isFile() && this.isIncluded(name)) {
               if (!this.isExcluded(name)) {
                  this.mFilesIncluded.addElement(name);
                  if (this.mLogger.isVerbose((Object)this)) {
                     this.mLogger.verbose("...including " + name + " under '" + dir);
                  }
               } else if (this.mLogger.isVerbose((Object)this)) {
                  this.mLogger.verbose("...EXCLUDING " + name + " under '" + dir);
               }
            }
         }

      }
   }

   private boolean isIncluded(String name) {
      for(int i = 0; i < this.mIncludes.length; ++i) {
         if (matchPath(this.mIncludes[i], name, this.mCaseSensitive)) {
            return true;
         }
      }

      return false;
   }

   private boolean couldHoldIncluded(String name) {
      for(int i = 0; i < this.mIncludes.length; ++i) {
         if (matchPatternStart(this.mIncludes[i], name, this.mCaseSensitive)) {
            return true;
         }
      }

      return false;
   }

   private boolean isExcluded(String name) {
      for(int i = 0; i < this.mExcludes.length; ++i) {
         if (matchPath(this.mExcludes[i], name, this.mCaseSensitive)) {
            return true;
         }
      }

      return false;
   }

   private static boolean matchPatternStart(String pattern, String str, boolean mCaseSensitive) {
      if (str.startsWith(File.separator) != pattern.startsWith(File.separator)) {
         return false;
      } else {
         Vector patDirs = tokenizePath(pattern);
         Vector strDirs = tokenizePath(str);
         int patIdxStart = 0;
         int patIdxEnd = patDirs.size() - 1;
         int strIdxStart = 0;

         int strIdxEnd;
         for(strIdxEnd = strDirs.size() - 1; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd; ++strIdxStart) {
            String patDir = (String)patDirs.elementAt(patIdxStart);
            if (patDir.equals("**")) {
               break;
            }

            if (!match(patDir, (String)strDirs.elementAt(strIdxStart), mCaseSensitive)) {
               return false;
            }

            ++patIdxStart;
         }

         if (strIdxStart > strIdxEnd) {
            return true;
         } else {
            return patIdxStart <= patIdxEnd;
         }
      }
   }

   private static boolean matchPath(String pattern, String str, boolean mCaseSensitive) {
      if (str.startsWith(File.separator) != pattern.startsWith(File.separator)) {
         return false;
      } else {
         Vector patDirs = tokenizePath(pattern);
         Vector strDirs = tokenizePath(str);
         int patIdxStart = 0;
         int patIdxEnd = patDirs.size() - 1;
         int strIdxStart = 0;

         int strIdxEnd;
         String patDir;
         for(strIdxEnd = strDirs.size() - 1; patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd; ++strIdxStart) {
            patDir = (String)patDirs.elementAt(patIdxStart);
            if (patDir.equals("**")) {
               break;
            }

            if (!match(patDir, (String)strDirs.elementAt(strIdxStart), mCaseSensitive)) {
               return false;
            }

            ++patIdxStart;
         }

         int patIdxTmp;
         if (strIdxStart > strIdxEnd) {
            for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               if (!patDirs.elementAt(patIdxTmp).equals("**")) {
                  return false;
               }
            }

            return true;
         } else if (patIdxStart > patIdxEnd) {
            return false;
         } else {
            while(patIdxStart <= patIdxEnd && strIdxStart <= strIdxEnd) {
               patDir = (String)patDirs.elementAt(patIdxEnd);
               if (patDir.equals("**")) {
                  break;
               }

               if (!match(patDir, (String)strDirs.elementAt(strIdxEnd), mCaseSensitive)) {
                  return false;
               }

               --patIdxEnd;
               --strIdxEnd;
            }

            if (strIdxStart > strIdxEnd) {
               for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                  if (!patDirs.elementAt(patIdxTmp).equals("**")) {
                     return false;
                  }
               }

               return true;
            } else {
               while(patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                  patIdxTmp = -1;

                  int patLength;
                  for(patLength = patIdxStart + 1; patLength <= patIdxEnd; ++patLength) {
                     if (patDirs.elementAt(patLength).equals("**")) {
                        patIdxTmp = patLength;
                        break;
                     }
                  }

                  if (patIdxTmp == patIdxStart + 1) {
                     ++patIdxStart;
                  } else {
                     patLength = patIdxTmp - patIdxStart - 1;
                     int strLength = strIdxEnd - strIdxStart + 1;
                     int foundIdx = -1;
                     int i = 0;

                     label110:
                     while(i <= strLength - patLength) {
                        for(int j = 0; j < patLength; ++j) {
                           String subPat = (String)patDirs.elementAt(patIdxStart + j + 1);
                           String subStr = (String)strDirs.elementAt(strIdxStart + i + j);
                           if (!match(subPat, subStr, mCaseSensitive)) {
                              ++i;
                              continue label110;
                           }
                        }

                        foundIdx = strIdxStart + i;
                        break;
                     }

                     if (foundIdx == -1) {
                        return false;
                     }

                     patIdxStart = patIdxTmp;
                     strIdxStart = foundIdx + patLength;
                  }
               }

               for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                  if (!patDirs.elementAt(patIdxTmp).equals("**")) {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private static boolean match(String pattern, String str, boolean mCaseSensitive) {
      char[] patArr = pattern.toCharArray();
      char[] strArr = str.toCharArray();
      int patIdxStart = 0;
      int patIdxEnd = patArr.length - 1;
      int strIdxStart = 0;
      int strIdxEnd = strArr.length - 1;
      boolean containsStar = false;

      int patIdxTmp;
      for(patIdxTmp = 0; patIdxTmp < patArr.length; ++patIdxTmp) {
         if (patArr[patIdxTmp] == '*') {
            containsStar = true;
            break;
         }
      }

      char ch;
      if (!containsStar) {
         if (patIdxEnd != strIdxEnd) {
            return false;
         } else {
            for(patIdxTmp = 0; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               ch = patArr[patIdxTmp];
               if (ch != '?') {
                  if (mCaseSensitive && ch != strArr[patIdxTmp]) {
                     return false;
                  }

                  if (!mCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[patIdxTmp])) {
                     return false;
                  }
               }
            }

            return true;
         }
      } else if (patIdxEnd == 0) {
         return true;
      } else {
         while((ch = patArr[patIdxStart]) != '*' && strIdxStart <= strIdxEnd) {
            if (ch != '?') {
               if (mCaseSensitive && ch != strArr[strIdxStart]) {
                  return false;
               }

               if (!mCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxStart])) {
                  return false;
               }
            }

            ++patIdxStart;
            ++strIdxStart;
         }

         if (strIdxStart > strIdxEnd) {
            for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
               if (patArr[patIdxTmp] != '*') {
                  return false;
               }
            }

            return true;
         } else {
            while((ch = patArr[patIdxEnd]) != '*' && strIdxStart <= strIdxEnd) {
               if (ch != '?') {
                  if (mCaseSensitive && ch != strArr[strIdxEnd]) {
                     return false;
                  }

                  if (!mCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxEnd])) {
                     return false;
                  }
               }

               --patIdxEnd;
               --strIdxEnd;
            }

            if (strIdxStart > strIdxEnd) {
               for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                  if (patArr[patIdxTmp] != '*') {
                     return false;
                  }
               }

               return true;
            } else {
               while(patIdxStart != patIdxEnd && strIdxStart <= strIdxEnd) {
                  patIdxTmp = -1;

                  int patLength;
                  for(patLength = patIdxStart + 1; patLength <= patIdxEnd; ++patLength) {
                     if (patArr[patLength] == '*') {
                        patIdxTmp = patLength;
                        break;
                     }
                  }

                  if (patIdxTmp == patIdxStart + 1) {
                     ++patIdxStart;
                  } else {
                     patLength = patIdxTmp - patIdxStart - 1;
                     int strLength = strIdxEnd - strIdxStart + 1;
                     int foundIdx = -1;

                     label166:
                     for(int i = 0; i <= strLength - patLength; ++i) {
                        for(int j = 0; j < patLength; ++j) {
                           ch = patArr[patIdxStart + j + 1];
                           if (ch != '?' && (mCaseSensitive && ch != strArr[strIdxStart + i + j] || !mCaseSensitive && Character.toUpperCase(ch) != Character.toUpperCase(strArr[strIdxStart + i + j]))) {
                              continue label166;
                           }
                        }

                        foundIdx = strIdxStart + i;
                        break;
                     }

                     if (foundIdx == -1) {
                        return false;
                     }

                     patIdxStart = patIdxTmp;
                     strIdxStart = foundIdx + patLength;
                  }
               }

               for(patIdxTmp = patIdxStart; patIdxTmp <= patIdxEnd; ++patIdxTmp) {
                  if (patArr[patIdxTmp] != '*') {
                     return false;
                  }
               }

               return true;
            }
         }
      }
   }

   private static Vector tokenizePath(String path) {
      Vector ret = new Vector();
      StringTokenizer st = new StringTokenizer(path, File.separator);

      while(st.hasMoreTokens()) {
         ret.addElement(st.nextToken());
      }

      return ret;
   }
}
