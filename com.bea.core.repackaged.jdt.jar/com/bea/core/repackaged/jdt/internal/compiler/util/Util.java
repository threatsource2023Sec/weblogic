package com.bea.core.repackaged.jdt.internal.compiler.util;

import com.bea.core.repackaged.jdt.core.compiler.CharOperation;
import com.bea.core.repackaged.jdt.internal.compiler.ClassFile;
import com.bea.core.repackaged.jdt.internal.compiler.ast.TypeDeclaration;
import com.bea.core.repackaged.jdt.internal.compiler.batch.FileSystem;
import com.bea.core.repackaged.jdt.internal.compiler.batch.Main;
import com.bea.core.repackaged.jdt.internal.compiler.env.AccessRuleSet;
import com.bea.core.repackaged.jdt.internal.compiler.impl.CompilerOptions;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ParameterizedTypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.ReferenceBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.TypeVariableBinding;
import com.bea.core.repackaged.jdt.internal.compiler.lookup.WildcardBinding;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Util implements SuffixConstants {
   public static final char C_BOOLEAN = 'Z';
   public static final char C_BYTE = 'B';
   public static final char C_CHAR = 'C';
   public static final char C_DOUBLE = 'D';
   public static final char C_FLOAT = 'F';
   public static final char C_INT = 'I';
   public static final char C_SEMICOLON = ';';
   public static final char C_COLON = ':';
   public static final char C_LONG = 'J';
   public static final char C_SHORT = 'S';
   public static final char C_VOID = 'V';
   public static final char C_TYPE_VARIABLE = 'T';
   public static final char C_STAR = '*';
   public static final char C_EXCEPTION_START = '^';
   public static final char C_EXTENDS = '+';
   public static final char C_SUPER = '-';
   public static final char C_DOT = '.';
   public static final char C_DOLLAR = '$';
   public static final char C_ARRAY = '[';
   public static final char C_RESOLVED = 'L';
   public static final char C_UNRESOLVED = 'Q';
   public static final char C_NAME_END = ';';
   public static final char C_PARAM_START = '(';
   public static final char C_PARAM_END = ')';
   public static final char C_GENERIC_START = '<';
   public static final char C_GENERIC_END = '>';
   public static final char C_CAPTURE = '!';
   private static final int DEFAULT_READING_SIZE = 8192;
   private static final int DEFAULT_WRITING_SIZE = 1024;
   public static final String UTF_8 = "UTF-8";
   public static final String LINE_SEPARATOR = System.getProperty("line.separator");
   public static final String EMPTY_STRING;
   public static final String COMMA_SEPARATOR;
   public static final int[] EMPTY_INT_ARRAY;
   public static final int ZIP_FILE = 0;
   public static final int JMOD_FILE = 1;

   static {
      EMPTY_STRING = new String(CharOperation.NO_CHAR);
      COMMA_SEPARATOR = new String(CharOperation.COMMA_SEPARATOR);
      EMPTY_INT_ARRAY = new int[0];
   }

   public static String buildAllDirectoriesInto(String outputPath, String relativeFileName) throws IOException {
      char fileSeparatorChar = File.separatorChar;
      String fileSeparator = File.separator;
      outputPath = outputPath.replace('/', fileSeparatorChar);
      relativeFileName = relativeFileName.replace('/', fileSeparatorChar);
      int separatorIndex = relativeFileName.lastIndexOf(fileSeparatorChar);
      String outputDirPath;
      String fileName;
      if (separatorIndex == -1) {
         if (outputPath.endsWith(fileSeparator)) {
            outputDirPath = outputPath.substring(0, outputPath.length() - 1);
            fileName = outputPath + relativeFileName;
         } else {
            outputDirPath = outputPath;
            fileName = outputPath + fileSeparator + relativeFileName;
         }
      } else if (outputPath.endsWith(fileSeparator)) {
         outputDirPath = outputPath + relativeFileName.substring(0, separatorIndex);
         fileName = outputPath + relativeFileName;
      } else {
         outputDirPath = outputPath + fileSeparator + relativeFileName.substring(0, separatorIndex);
         fileName = outputPath + fileSeparator + relativeFileName;
      }

      File f = new File(outputDirPath);
      f.mkdirs();
      if (f.isDirectory()) {
         return fileName;
      } else {
         if (outputPath.endsWith(fileSeparator)) {
            outputPath = outputPath.substring(0, outputPath.length() - 1);
         }

         f = new File(outputPath);
         boolean checkFileType = false;
         if (f.exists()) {
            checkFileType = true;
         } else if (!f.mkdirs()) {
            if (!f.exists()) {
               throw new IOException(Messages.bind(Messages.output_notValidAll, (Object)f.getAbsolutePath()));
            }

            checkFileType = true;
         }

         if (checkFileType && !f.isDirectory()) {
            throw new IOException(Messages.bind(Messages.output_isFile, (Object)f.getAbsolutePath()));
         } else {
            StringBuffer outDir = new StringBuffer(outputPath);
            outDir.append(fileSeparator);
            StringTokenizer tokenizer = new StringTokenizer(relativeFileName, fileSeparator);

            String token;
            for(token = tokenizer.nextToken(); tokenizer.hasMoreTokens(); token = tokenizer.nextToken()) {
               f = new File(outDir.append(token).append(fileSeparator).toString());
               checkFileType = false;
               if (f.exists()) {
                  checkFileType = true;
               } else if (!f.mkdir()) {
                  if (!f.exists()) {
                     throw new IOException(Messages.bind(Messages.output_notValid, outDir.substring(outputPath.length() + 1, outDir.length() - 1), outputPath));
                  }

                  checkFileType = true;
               }

               if (checkFileType && !f.isDirectory()) {
                  throw new IOException(Messages.bind(Messages.output_isFile, (Object)f.getAbsolutePath()));
               }
            }

            return outDir.append(token).toString();
         }
      }
   }

   public static char[] bytesToChar(byte[] bytes, String encoding) throws IOException {
      return getInputStreamAsCharArray(new ByteArrayInputStream(bytes), bytes.length, encoding);
   }

   public static int computeOuterMostVisibility(TypeDeclaration typeDeclaration, int visibility) {
      for(; typeDeclaration != null; typeDeclaration = typeDeclaration.enclosingType) {
         switch (typeDeclaration.modifiers & 7) {
            case 0:
               if (visibility != 2) {
                  visibility = 0;
               }
            case 1:
            case 3:
            default:
               break;
            case 2:
               visibility = 2;
               break;
            case 4:
               if (visibility == 1) {
                  visibility = 4;
               }
         }
      }

      return visibility;
   }

   public static byte[] getFileByteContent(File file) throws IOException {
      InputStream stream = null;

      byte[] var3;
      try {
         stream = new BufferedInputStream(new FileInputStream(file));
         var3 = getInputStreamAsByteArray(stream, (int)file.length());
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (IOException var7) {
            }
         }

      }

      return var3;
   }

   public static char[] getFileCharContent(File file, String encoding) throws IOException {
      InputStream stream = null;

      char[] var4;
      try {
         stream = new FileInputStream(file);
         var4 = getInputStreamAsCharArray(stream, (int)file.length(), encoding);
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (IOException var8) {
            }
         }

      }

      return var4;
   }

   private static FileOutputStream getFileOutputStream(boolean generatePackagesStructure, String outputPath, String relativeFileName) throws IOException {
      if (generatePackagesStructure) {
         return new FileOutputStream(new File(buildAllDirectoriesInto(outputPath, relativeFileName)));
      } else {
         String fileName = null;
         char fileSeparatorChar = File.separatorChar;
         String fileSeparator = File.separator;
         outputPath = outputPath.replace('/', fileSeparatorChar);
         int indexOfPackageSeparator = relativeFileName.lastIndexOf(fileSeparatorChar);
         if (indexOfPackageSeparator == -1) {
            if (outputPath.endsWith(fileSeparator)) {
               fileName = outputPath + relativeFileName;
            } else {
               fileName = outputPath + fileSeparator + relativeFileName;
            }
         } else {
            int length = relativeFileName.length();
            if (outputPath.endsWith(fileSeparator)) {
               fileName = outputPath + relativeFileName.substring(indexOfPackageSeparator + 1, length);
            } else {
               fileName = outputPath + fileSeparator + relativeFileName.substring(indexOfPackageSeparator + 1, length);
            }
         }

         return new FileOutputStream(new File(fileName));
      }
   }

   public static byte[] getInputStreamAsByteArray(InputStream stream, int length) throws IOException {
      byte[] contents;
      int contentsLength;
      int amountRead;
      if (length == -1) {
         contents = new byte[0];
         contentsLength = 0;
         int amountRead = true;

         do {
            int amountRequested = Math.max(stream.available(), 8192);
            if (contentsLength + amountRequested > contents.length) {
               System.arraycopy(contents, 0, contents = new byte[contentsLength + amountRequested], 0, contentsLength);
            }

            amountRead = stream.read(contents, contentsLength, amountRequested);
            if (amountRead > 0) {
               contentsLength += amountRead;
            }
         } while(amountRead != -1);

         if (contentsLength < contents.length) {
            System.arraycopy(contents, 0, contents = new byte[contentsLength], 0, contentsLength);
         }
      } else {
         contents = new byte[length];
         contentsLength = 0;

         for(amountRead = 0; amountRead != -1 && contentsLength != length; amountRead = stream.read(contents, contentsLength, length - contentsLength)) {
            contentsLength += amountRead;
         }
      }

      return contents;
   }

   public static char[] getInputStreamAsCharArray(InputStream stream, int length, String encoding) throws IOException {
      BufferedReader reader = null;

      try {
         reader = encoding == null ? new BufferedReader(new InputStreamReader(stream)) : new BufferedReader(new InputStreamReader(stream, encoding));
      } catch (UnsupportedEncodingException var8) {
         reader = new BufferedReader(new InputStreamReader(stream));
      }

      int totalRead = 0;
      char[] contents;
      if (length == -1) {
         contents = CharOperation.NO_CHAR;
      } else {
         contents = new char[length];
      }

      while(true) {
         int amountRequested;
         int current;
         if (totalRead < length) {
            amountRequested = length - totalRead;
         } else {
            current = reader.read();
            if (current < 0) {
               break;
            }

            amountRequested = Math.max(stream.available(), 8192);
            if (totalRead + 1 + amountRequested > contents.length) {
               System.arraycopy(contents, 0, contents = new char[totalRead + 1 + amountRequested], 0, totalRead);
            }

            contents[totalRead++] = (char)current;
         }

         current = reader.read(contents, totalRead, amountRequested);
         if (current < 0) {
            break;
         }

         totalRead += current;
      }

      int start = 0;
      if (totalRead > 0 && "UTF-8".equals(encoding) && contents[0] == '\ufeff') {
         --totalRead;
         start = 1;
      }

      if (totalRead < contents.length) {
         System.arraycopy(contents, start, contents = new char[totalRead], 0, totalRead);
      }

      return contents;
   }

   public static String getExceptionSummary(Throwable exception) {
      StringWriter stringWriter = new StringWriter();
      exception.printStackTrace(new PrintWriter(stringWriter));
      StringBuffer buffer = stringWriter.getBuffer();
      StringBuffer exceptionBuffer = new StringBuffer(50);
      exceptionBuffer.append(exception.toString());
      int i = 0;
      int lineSep = 0;
      int max = buffer.length();

      for(int line2Start = 0; i < max; ++i) {
         switch (buffer.charAt(i)) {
            case '\t':
            case ' ':
               break;
            case '\n':
            case '\r':
               if (line2Start > 0) {
                  exceptionBuffer.append(' ').append(buffer.substring(line2Start, i));
                  return exceptionBuffer.toString();
               }

               ++lineSep;
               break;
            default:
               if (lineSep > 0) {
                  line2Start = i;
                  lineSep = 0;
               }
         }
      }

      return exceptionBuffer.toString();
   }

   public static int getLineNumber(int position, int[] lineEnds, int g, int d) {
      if (lineEnds == null) {
         return 1;
      } else if (d == -1) {
         return 1;
      } else {
         int m = g;

         while(g <= d) {
            m = g + (d - g) / 2;
            int start;
            if (position < (start = lineEnds[m])) {
               d = m - 1;
            } else {
               if (position <= start) {
                  return m + 1;
               }

               g = m + 1;
            }
         }

         if (position < lineEnds[m]) {
            return m + 1;
         } else {
            return m + 2;
         }
      }
   }

   public static byte[] getZipEntryByteContent(ZipEntry ze, ZipFile zip) throws IOException {
      InputStream stream = null;

      byte[] var5;
      try {
         InputStream inputStream = zip.getInputStream(ze);
         if (inputStream == null) {
            throw new IOException("Invalid zip entry name : " + ze.getName());
         }

         stream = new BufferedInputStream(inputStream);
         var5 = getInputStreamAsByteArray(stream, (int)ze.getSize());
      } finally {
         if (stream != null) {
            try {
               stream.close();
            } catch (IOException var9) {
            }
         }

      }

      return var5;
   }

   public static int hashCode(Object[] array) {
      int prime = 31;
      if (array == null) {
         return 0;
      } else {
         int result = 1;

         for(int index = 0; index < array.length; ++index) {
            result = prime * result + (array[index] == null ? 0 : array[index].hashCode());
         }

         return result;
      }
   }

   public static final boolean isPotentialZipArchive(String name) {
      int lastDot = name.lastIndexOf(46);
      if (lastDot == -1) {
         return false;
      } else if (name.lastIndexOf(File.separatorChar) > lastDot) {
         return false;
      } else {
         int length = name.length();
         int extensionLength = length - lastDot - 1;
         int i;
         if (extensionLength == "java".length()) {
            for(i = extensionLength - 1; i >= 0 && Character.toLowerCase(name.charAt(length - extensionLength + i)) == "java".charAt(i); --i) {
               if (i == 0) {
                  return false;
               }
            }
         }

         if (extensionLength != "class".length()) {
            return true;
         } else {
            for(i = extensionLength - 1; i >= 0; --i) {
               if (Character.toLowerCase(name.charAt(length - extensionLength + i)) != "class".charAt(i)) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   public static final int archiveFormat(String name) {
      int lastDot = name.lastIndexOf(46);
      if (lastDot == -1) {
         return -1;
      } else if (name.lastIndexOf(File.separatorChar) > lastDot) {
         return -1;
      } else {
         int length = name.length();
         int extensionLength = length - lastDot - 1;
         int i;
         if (extensionLength == "java".length()) {
            for(i = extensionLength - 1; i >= 0 && Character.toLowerCase(name.charAt(length - extensionLength + i)) == "java".charAt(i); --i) {
               if (i == 0) {
                  return -1;
               }
            }
         }

         if (extensionLength == "class".length()) {
            for(i = extensionLength - 1; i >= 0; --i) {
               if (Character.toLowerCase(name.charAt(length - extensionLength + i)) != "class".charAt(i)) {
                  return 0;
               }
            }

            return -1;
         } else if (extensionLength != "jmod".length()) {
            return 0;
         } else {
            for(i = extensionLength - 1; i >= 0; --i) {
               if (Character.toLowerCase(name.charAt(length - extensionLength + i)) != "jmod".charAt(i)) {
                  return 0;
               }
            }

            return 1;
         }
      }
   }

   public static final boolean isClassFileName(char[] name) {
      int nameLength = name == null ? 0 : name.length;
      int suffixLength = SUFFIX_CLASS.length;
      if (nameLength < suffixLength) {
         return false;
      } else {
         int i = 0;

         for(int offset = nameLength - suffixLength; i < suffixLength; ++i) {
            char c = name[offset + i];
            if (c != SUFFIX_class[i] && c != SUFFIX_CLASS[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean isClassFileName(String name) {
      int nameLength = name == null ? 0 : name.length();
      int suffixLength = SUFFIX_CLASS.length;
      if (nameLength < suffixLength) {
         return false;
      } else {
         for(int i = 0; i < suffixLength; ++i) {
            char c = name.charAt(nameLength - i - 1);
            int suffixIndex = suffixLength - i - 1;
            if (c != SUFFIX_class[suffixIndex] && c != SUFFIX_CLASS[suffixIndex]) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean isExcluded(char[] path, char[][] inclusionPatterns, char[][] exclusionPatterns, boolean isFolderPath) {
      if (inclusionPatterns == null && exclusionPatterns == null) {
         return false;
      } else {
         int i;
         int length;
         if (inclusionPatterns != null) {
            i = 0;
            length = inclusionPatterns.length;

            while(true) {
               if (i >= length) {
                  return true;
               }

               char[] pattern = inclusionPatterns[i];
               char[] folderPattern = pattern;
               if (isFolderPath) {
                  int lastSlash = CharOperation.lastIndexOf('/', pattern);
                  if (lastSlash != -1 && lastSlash != pattern.length - 1) {
                     int star = CharOperation.indexOf('*', pattern, lastSlash);
                     if (star == -1 || star >= pattern.length - 1 || pattern[star + 1] != '*') {
                        folderPattern = CharOperation.subarray((char[])pattern, 0, lastSlash);
                     }
                  }
               }

               if (CharOperation.pathMatch(folderPattern, path, true, '/')) {
                  break;
               }

               ++i;
            }
         }

         if (isFolderPath) {
            path = CharOperation.concat(path, new char[]{'*'}, '/');
         }

         if (exclusionPatterns != null) {
            i = 0;

            for(length = exclusionPatterns.length; i < length; ++i) {
               if (CharOperation.pathMatch(exclusionPatterns[i], path, true, '/')) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static final boolean isJavaFileName(char[] name) {
      int nameLength = name == null ? 0 : name.length;
      int suffixLength = SUFFIX_JAVA.length;
      if (nameLength < suffixLength) {
         return false;
      } else {
         int i = 0;

         for(int offset = nameLength - suffixLength; i < suffixLength; ++i) {
            char c = name[offset + i];
            if (c != SUFFIX_java[i] && c != SUFFIX_JAVA[i]) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean isJavaFileName(String name) {
      int nameLength = name == null ? 0 : name.length();
      int suffixLength = SUFFIX_JAVA.length;
      if (nameLength < suffixLength) {
         return false;
      } else {
         for(int i = 0; i < suffixLength; ++i) {
            char c = name.charAt(nameLength - i - 1);
            int suffixIndex = suffixLength - i - 1;
            if (c != SUFFIX_java[suffixIndex] && c != SUFFIX_JAVA[suffixIndex]) {
               return false;
            }
         }

         return true;
      }
   }

   public static final boolean isJrt(String name) {
      return name.endsWith("jrt-fs.jar");
   }

   public static void reverseQuickSort(char[][] list, int left, int right) {
      int original_left = left;
      int original_right = right;
      char[] mid = list[left + (right - left) / 2];

      do {
         while(CharOperation.compareTo(list[left], mid) > 0) {
            ++left;
         }

         while(CharOperation.compareTo(mid, list[right]) > 0) {
            --right;
         }

         if (left <= right) {
            char[] tmp = list[left];
            list[left] = list[right];
            list[right] = tmp;
            ++left;
            --right;
         }
      } while(left <= right);

      if (original_left < right) {
         reverseQuickSort(list, original_left, right);
      }

      if (left < original_right) {
         reverseQuickSort(list, left, original_right);
      }

   }

   public static void reverseQuickSort(char[][] list, int left, int right, int[] result) {
      int original_left = left;
      int original_right = right;
      char[] mid = list[left + (right - left) / 2];

      do {
         while(CharOperation.compareTo(list[left], mid) > 0) {
            ++left;
         }

         while(CharOperation.compareTo(mid, list[right]) > 0) {
            --right;
         }

         if (left <= right) {
            char[] tmp = list[left];
            list[left] = list[right];
            list[right] = tmp;
            int temp = result[left];
            result[left] = result[right];
            result[right] = temp;
            ++left;
            --right;
         }
      } while(left <= right);

      if (original_left < right) {
         reverseQuickSort(list, original_left, right, result);
      }

      if (left < original_right) {
         reverseQuickSort(list, left, original_right, result);
      }

   }

   public static final int searchColumnNumber(int[] startLineIndexes, int lineNumber, int position) {
      switch (lineNumber) {
         case 1:
            return position + 1;
         case 2:
            return position - startLineIndexes[0];
         default:
            int line = lineNumber - 2;
            int length = startLineIndexes.length;
            return line >= length ? position - startLineIndexes[length - 1] : position - startLineIndexes[line];
      }
   }

   public static Boolean toBoolean(boolean bool) {
      return bool ? Boolean.TRUE : Boolean.FALSE;
   }

   public static String toString(Object[] objects) {
      return toString(objects, new Displayable() {
         public String displayString(Object o) {
            return o == null ? "null" : o.toString();
         }
      });
   }

   public static String toString(Object[] objects, Displayable renderer) {
      if (objects == null) {
         return "";
      } else {
         StringBuffer buffer = new StringBuffer(10);

         for(int i = 0; i < objects.length; ++i) {
            if (i > 0) {
               buffer.append(", ");
            }

            buffer.append(renderer.displayString(objects[i]));
         }

         return buffer.toString();
      }
   }

   public static void writeToDisk(boolean generatePackagesStructure, String outputPath, String relativeFileName, ClassFile classFile) throws IOException {
      FileOutputStream file = getFileOutputStream(generatePackagesStructure, outputPath, relativeFileName);
      BufferedOutputStream output = new BufferedOutputStream(file, 1024);

      try {
         output.write(classFile.header, 0, classFile.headerOffset);
         output.write(classFile.contents, 0, classFile.contentsOffset);
         output.flush();
      } catch (IOException var10) {
         throw var10;
      } finally {
         output.close();
      }

   }

   public static void recordNestedType(ClassFile classFile, TypeBinding typeBinding) {
      if (classFile.visitedTypes == null) {
         classFile.visitedTypes = new HashSet(3);
      } else if (classFile.visitedTypes.contains(typeBinding)) {
         return;
      }

      classFile.visitedTypes.add(typeBinding);
      TypeBinding[] upperBounds;
      int k;
      int max3;
      TypeBinding otherUpperBound;
      if (typeBinding.isParameterizedType() && (typeBinding.tagBits & 2048L) != 0L) {
         ParameterizedTypeBinding parameterizedTypeBinding = (ParameterizedTypeBinding)typeBinding;
         ReferenceBinding genericType = parameterizedTypeBinding.genericType();
         if ((genericType.tagBits & 2048L) != 0L) {
            recordNestedType(classFile, genericType);
         }

         upperBounds = parameterizedTypeBinding.arguments;
         if (upperBounds != null) {
            k = 0;

            for(max3 = upperBounds.length; k < max3; ++k) {
               otherUpperBound = upperBounds[k];
               if (otherUpperBound.isWildcard()) {
                  WildcardBinding wildcardBinding = (WildcardBinding)otherUpperBound;
                  TypeBinding bound = wildcardBinding.bound;
                  if (bound != null && (bound.tagBits & 2048L) != 0L) {
                     recordNestedType(classFile, bound);
                  }

                  ReferenceBinding superclass = wildcardBinding.superclass();
                  if (superclass != null && (superclass.tagBits & 2048L) != 0L) {
                     recordNestedType(classFile, superclass);
                  }

                  ReferenceBinding[] superInterfaces = wildcardBinding.superInterfaces();
                  if (superInterfaces != null) {
                     int k = 0;

                     for(int max3 = superInterfaces.length; k < max3; ++k) {
                        ReferenceBinding superInterface = superInterfaces[k];
                        if ((superInterface.tagBits & 2048L) != 0L) {
                           recordNestedType(classFile, superInterface);
                        }
                     }
                  }
               } else if ((otherUpperBound.tagBits & 2048L) != 0L) {
                  recordNestedType(classFile, otherUpperBound);
               }
            }
         }
      } else if (typeBinding.isTypeVariable() && (typeBinding.tagBits & 2048L) != 0L) {
         TypeVariableBinding typeVariableBinding = (TypeVariableBinding)typeBinding;
         TypeBinding upperBound = typeVariableBinding.upperBound();
         if (upperBound != null && (upperBound.tagBits & 2048L) != 0L) {
            recordNestedType(classFile, upperBound);
         }

         upperBounds = typeVariableBinding.otherUpperBounds();
         if (upperBounds != null) {
            k = 0;

            for(max3 = upperBounds.length; k < max3; ++k) {
               otherUpperBound = upperBounds[k];
               if ((otherUpperBound.tagBits & 2048L) != 0L) {
                  recordNestedType(classFile, otherUpperBound);
               }
            }
         }
      } else if (typeBinding.isNestedType()) {
         TypeBinding enclosingType = typeBinding;

         while(((TypeBinding)enclosingType).canBeSeenBy(classFile.referenceBinding.scope)) {
            enclosingType = ((TypeBinding)enclosingType).enclosingType();
            if (enclosingType == null) {
               break;
            }
         }

         boolean onBottomForBug445231 = enclosingType != null;
         classFile.recordInnerClasses(typeBinding, onBottomForBug445231);
      }

   }

   public static File getJavaHome() {
      String javaHome = System.getProperty("java.home");
      if (javaHome != null) {
         File javaHomeFile = new File(javaHome);
         if (javaHomeFile.exists()) {
            return javaHomeFile;
         }
      }

      return null;
   }

   public static void collectVMBootclasspath(List bootclasspaths, File javaHome) {
      List classpaths = collectPlatformLibraries(javaHome);
      bootclasspaths.addAll(classpaths);
   }

   public static void collectRunningVMBootclasspath(List bootclasspaths) {
      collectVMBootclasspath(bootclasspaths, (File)null);
   }

   public static long getJDKLevel(File javaHome) {
      String version = System.getProperty("java.version");
      return CompilerOptions.versionToJdkLevel(version);
   }

   public static List collectFilesNames() {
      return collectPlatformLibraries((File)null);
   }

   public static List collectPlatformLibraries(File javaHome) {
      String javaversion = null;
      javaversion = System.getProperty("java.version");
      if (javaversion != null && javaversion.equalsIgnoreCase("1.1.8")) {
         throw new IllegalStateException();
      } else {
         long jdkLevel = CompilerOptions.versionToJdkLevel(javaversion);
         if (jdkLevel >= 3473408L) {
            List filePaths = new ArrayList();
            if (javaHome == null) {
               javaHome = getJavaHome();
            }

            if (javaHome != null) {
               filePaths.add(FileSystem.getJrtClasspath(javaHome.getAbsolutePath(), (String)null, (AccessRuleSet)null, (Map)null));
               return filePaths;
            }
         }

         String bootclasspathProperty = System.getProperty("sun.boot.class.path");
         if (bootclasspathProperty == null || bootclasspathProperty.length() == 0) {
            bootclasspathProperty = System.getProperty("vm.boot.class.path");
            if (bootclasspathProperty == null || bootclasspathProperty.length() == 0) {
               bootclasspathProperty = System.getProperty("org.apache.harmony.boot.class.path");
            }
         }

         Set filePaths = new HashSet();
         if (bootclasspathProperty != null && bootclasspathProperty.length() != 0) {
            StringTokenizer tokenizer = new StringTokenizer(bootclasspathProperty, File.pathSeparator);

            while(tokenizer.hasMoreTokens()) {
               filePaths.add(tokenizer.nextToken());
            }
         } else {
            if (javaHome == null) {
               javaHome = getJavaHome();
            }

            if (javaHome != null) {
               File[] directoriesToCheck = null;
               if (System.getProperty("os.name").startsWith("Mac")) {
                  directoriesToCheck = new File[]{new File(javaHome, "../Classes")};
               } else {
                  directoriesToCheck = new File[]{new File(javaHome, "lib")};
               }

               File[][] systemLibrariesJars = Main.getLibrariesFiles(directoriesToCheck);
               if (systemLibrariesJars != null) {
                  int i = 0;

                  for(int max = systemLibrariesJars.length; i < max; ++i) {
                     File[] current = systemLibrariesJars[i];
                     if (current != null) {
                        int j = 0;

                        for(int max2 = current.length; j < max2; ++j) {
                           filePaths.add(current[j].getAbsolutePath());
                        }
                     }
                  }
               }
            }
         }

         List classpaths = new ArrayList();
         Iterator var17 = filePaths.iterator();

         while(var17.hasNext()) {
            String filePath = (String)var17.next();
            FileSystem.Classpath currentClasspath = FileSystem.getClasspath(filePath, (String)null, (AccessRuleSet)null, (Map)null, (String)null);
            if (currentClasspath != null) {
               classpaths.add(currentClasspath);
            }
         }

         return classpaths;
      }
   }

   public static int getParameterCount(char[] methodSignature) {
      try {
         int count = 0;
         int i = CharOperation.indexOf('(', methodSignature);
         if (i < 0) {
            throw new IllegalArgumentException();
         } else {
            ++i;

            while(methodSignature[i] != ')') {
               int e = scanTypeSignature(methodSignature, i);
               if (e < 0) {
                  throw new IllegalArgumentException();
               }

               i = e + 1;
               ++count;
            }

            return count;
         }
      } catch (ArrayIndexOutOfBoundsException var4) {
         throw new IllegalArgumentException(var4);
      }
   }

   public static int scanTypeSignature(char[] string, int start) {
      if (start >= string.length) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         switch (c) {
            case '!':
               return scanCaptureTypeSignature(string, start);
            case '*':
            case '+':
            case '-':
               return scanTypeBoundSignature(string, start);
            case 'B':
            case 'C':
            case 'D':
            case 'F':
            case 'I':
            case 'J':
            case 'S':
            case 'V':
            case 'Z':
               return scanBaseTypeSignature(string, start);
            case 'L':
            case 'Q':
               return scanClassTypeSignature(string, start);
            case 'T':
               return scanTypeVariableSignature(string, start);
            case '[':
               return scanArrayTypeSignature(string, start);
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public static int scanBaseTypeSignature(char[] string, int start) {
      if (start >= string.length) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         if ("BCDFIJSVZ".indexOf(c) >= 0) {
            return start;
         } else {
            throw new IllegalArgumentException();
         }
      }
   }

   public static int scanArrayTypeSignature(char[] string, int start) {
      int length = string.length;
      if (start >= length - 1) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         if (c != '[') {
            throw new IllegalArgumentException();
         } else {
            ++start;

            for(c = string[start]; c == '['; c = string[start]) {
               if (start >= length - 1) {
                  throw new IllegalArgumentException();
               }

               ++start;
            }

            return scanTypeSignature(string, start);
         }
      }
   }

   public static int scanCaptureTypeSignature(char[] string, int start) {
      if (start >= string.length - 1) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         if (c != '!') {
            throw new IllegalArgumentException();
         } else {
            return scanTypeBoundSignature(string, start + 1);
         }
      }
   }

   public static int scanTypeVariableSignature(char[] string, int start) {
      if (start >= string.length - 2) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         if (c != 'T') {
            throw new IllegalArgumentException();
         } else {
            int id = scanIdentifier(string, start + 1);
            c = string[id + 1];
            if (c == ';') {
               return id + 1;
            } else {
               throw new IllegalArgumentException();
            }
         }
      }
   }

   public static int scanIdentifier(char[] string, int start) {
      if (start >= string.length) {
         throw new IllegalArgumentException();
      } else {
         int p = start;

         do {
            char c = string[p];
            if (c == '<' || c == '>' || c == ':' || c == ';' || c == '.' || c == '/') {
               return p - 1;
            }

            ++p;
         } while(p != string.length);

         return p - 1;
      }
   }

   public static int scanClassTypeSignature(char[] string, int start) {
      if (start >= string.length - 2) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         if (c != 'L' && c != 'Q') {
            return -1;
         } else {
            for(int p = start + 1; p < string.length; ++p) {
               c = string[p];
               if (c == ';') {
                  return p;
               }

               int id;
               if (c == '<') {
                  id = scanTypeArgumentSignatures(string, p);
                  p = id;
               } else if (c == '.' || c == '/') {
                  id = scanIdentifier(string, p + 1);
                  p = id;
               }
            }

            throw new IllegalArgumentException();
         }
      }
   }

   public static int scanTypeBoundSignature(char[] string, int start) {
      if (start >= string.length) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         switch (c) {
            case '*':
               return start;
            case '+':
            case '-':
               ++start;
               c = string[start];
               if (c != '*' && start >= string.length - 1) {
                  throw new IllegalArgumentException();
               } else {
                  switch (c) {
                     case '!':
                        return scanCaptureTypeSignature(string, start);
                     case '*':
                        return start;
                     case '+':
                     case '-':
                        return scanTypeBoundSignature(string, start);
                     case 'L':
                     case 'Q':
                        return scanClassTypeSignature(string, start);
                     case 'T':
                        return scanTypeVariableSignature(string, start);
                     case '[':
                        return scanArrayTypeSignature(string, start);
                     default:
                        throw new IllegalArgumentException();
                  }
               }
            case ',':
            default:
               throw new IllegalArgumentException();
         }
      }
   }

   public static int scanTypeArgumentSignatures(char[] string, int start) {
      if (start >= string.length - 1) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         if (c != '<') {
            throw new IllegalArgumentException();
         } else {
            int e;
            for(int p = start + 1; p < string.length; p = e + 1) {
               c = string[p];
               if (c == '>') {
                  return p;
               }

               e = scanTypeArgumentSignature(string, p);
            }

            throw new IllegalArgumentException();
         }
      }
   }

   public static int scanTypeArgumentSignature(char[] string, int start) {
      if (start >= string.length) {
         throw new IllegalArgumentException();
      } else {
         char c = string[start];
         switch (c) {
            case '*':
               return start;
            case '+':
            case '-':
               return scanTypeBoundSignature(string, start);
            case ',':
            default:
               return scanTypeSignature(string, start);
         }
      }
   }

   public static boolean effectivelyEqual(Object[] one, Object[] two) {
      if (one == two) {
         return true;
      } else {
         int oneLength = one == null ? 0 : one.length;
         int twoLength = two == null ? 0 : two.length;
         if (oneLength != twoLength) {
            return false;
         } else if (oneLength == 0) {
            return true;
         } else {
            for(int i = 0; i < one.length; ++i) {
               if (one[i] != two[i]) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   public static void appendEscapedChar(StringBuffer buffer, char c, boolean stringLiteral) {
      switch (c) {
         case '\b':
            buffer.append("\\b");
            break;
         case '\t':
            buffer.append("\\t");
            break;
         case '\n':
            buffer.append("\\n");
            break;
         case '\f':
            buffer.append("\\f");
            break;
         case '\r':
            buffer.append("\\r");
            break;
         case '"':
            if (stringLiteral) {
               buffer.append("\\\"");
            } else {
               buffer.append(c);
            }
            break;
         case '\'':
            if (stringLiteral) {
               buffer.append(c);
            } else {
               buffer.append("\\'");
            }
            break;
         case '\\':
            buffer.append("\\\\");
            break;
         default:
            if (c >= ' ') {
               buffer.append(c);
            } else if (c >= 16) {
               buffer.append("\\u00").append(Integer.toHexString(c));
            } else if (c >= 0) {
               buffer.append("\\u000").append(Integer.toHexString(c));
            } else {
               buffer.append(c);
            }
      }

   }

   public interface Displayable {
      String displayString(Object var1);
   }
}
