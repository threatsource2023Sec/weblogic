package com.bea.core.repackaged.jdt.internal.compiler.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ManifestAnalyzer {
   private static final int START = 0;
   private static final int IN_CLASSPATH_HEADER = 1;
   private static final int PAST_CLASSPATH_HEADER = 2;
   private static final int SKIPPING_WHITESPACE = 3;
   private static final int READING_JAR = 4;
   private static final int CONTINUING = 5;
   private static final int SKIP_LINE = 6;
   private static final char[] CLASSPATH_HEADER_TOKEN = "Class-Path:".toCharArray();
   private int classpathSectionsCount;
   private ArrayList calledFilesNames;

   public boolean analyzeManifestContents(InputStream inputStream) throws IOException {
      char[] chars = Util.getInputStreamAsCharArray(inputStream, -1, "UTF-8");
      return this.analyzeManifestContents(chars);
   }

   public boolean analyzeManifestContents(char[] chars) {
      int state = 0;
      int substate = 0;
      StringBuffer currentJarToken = new StringBuffer();
      this.classpathSectionsCount = 0;
      this.calledFilesNames = null;
      int i = 0;
      int max = chars.length;

      while(i < max) {
         int currentChar = chars[i++];
         if (currentChar == '\r' && i < max) {
            currentChar = chars[i++];
         }

         switch (state) {
            case 0:
               if (currentChar == CLASSPATH_HEADER_TOKEN[0]) {
                  state = 1;
                  substate = 1;
               } else {
                  state = 6;
               }
               break;
            case 1:
               if (currentChar == '\n') {
                  state = 0;
               } else if (currentChar != CLASSPATH_HEADER_TOKEN[substate++]) {
                  state = 6;
               } else if (substate == CLASSPATH_HEADER_TOKEN.length) {
                  state = 2;
               }
               break;
            case 2:
               if (currentChar != ' ') {
                  return false;
               }

               state = 3;
               ++this.classpathSectionsCount;
               break;
            case 3:
               if (currentChar == '\n') {
                  state = 5;
               } else if (currentChar != ' ') {
                  currentJarToken.append((char)currentChar);
                  state = 4;
               } else {
                  this.addCurrentTokenJarWhenNecessary(currentJarToken);
               }
               break;
            case 4:
               if (currentChar == '\n') {
                  state = 5;
               } else if (currentChar == ' ') {
                  state = 3;
                  this.addCurrentTokenJarWhenNecessary(currentJarToken);
               } else {
                  currentJarToken.append((char)currentChar);
               }
               break;
            case 5:
               if (currentChar == '\n') {
                  this.addCurrentTokenJarWhenNecessary(currentJarToken);
                  state = 0;
               } else if (currentChar == ' ') {
                  state = 3;
               } else if (currentChar == CLASSPATH_HEADER_TOKEN[0]) {
                  this.addCurrentTokenJarWhenNecessary(currentJarToken);
                  state = 1;
                  substate = 1;
               } else if (this.calledFilesNames == null) {
                  this.addCurrentTokenJarWhenNecessary(currentJarToken);
                  state = 0;
               } else {
                  this.addCurrentTokenJarWhenNecessary(currentJarToken);
                  state = 6;
               }
               break;
            case 6:
               if (currentChar == '\n') {
                  state = 0;
               }
         }
      }

      switch (state) {
         case 0:
            return true;
         case 1:
            return true;
         case 2:
            return false;
         case 3:
            this.addCurrentTokenJarWhenNecessary(currentJarToken);
            return true;
         case 4:
            return false;
         case 5:
            this.addCurrentTokenJarWhenNecessary(currentJarToken);
            return true;
         case 6:
            if (this.classpathSectionsCount != 0 && this.calledFilesNames == null) {
               return false;
            }

            return true;
         default:
            return true;
      }
   }

   private boolean addCurrentTokenJarWhenNecessary(StringBuffer currentJarToken) {
      if (currentJarToken != null && currentJarToken.length() > 0) {
         if (this.calledFilesNames == null) {
            this.calledFilesNames = new ArrayList();
         }

         this.calledFilesNames.add(currentJarToken.toString());
         currentJarToken.setLength(0);
         return true;
      } else {
         return false;
      }
   }

   public int getClasspathSectionsCount() {
      return this.classpathSectionsCount;
   }

   public List getCalledFileNames() {
      return this.calledFilesNames;
   }
}
