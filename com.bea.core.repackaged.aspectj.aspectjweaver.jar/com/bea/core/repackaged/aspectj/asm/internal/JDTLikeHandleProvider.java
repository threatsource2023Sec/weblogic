package com.bea.core.repackaged.aspectj.asm.internal;

import com.bea.core.repackaged.aspectj.asm.AsmManager;
import com.bea.core.repackaged.aspectj.asm.IElementHandleProvider;
import com.bea.core.repackaged.aspectj.asm.IProgramElement;
import com.bea.core.repackaged.aspectj.bridge.ISourceLocation;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class JDTLikeHandleProvider implements IElementHandleProvider {
   private final AsmManager asm;
   private static final char[] empty = new char[0];
   private static final char[] countDelim;
   private static final String backslash = "\\";
   private static final String emptyString = "";

   public JDTLikeHandleProvider(AsmManager asm) {
      this.asm = asm;
   }

   public void initialize() {
   }

   public String createHandleIdentifier(IProgramElement ipe) {
      if (ipe != null && (!ipe.getKind().equals(IProgramElement.Kind.FILE_JAVA) || !ipe.getName().equals("<root>"))) {
         if (ipe.getHandleIdentifier(false) != null) {
            return ipe.getHandleIdentifier();
         } else if (ipe.getKind().equals(IProgramElement.Kind.FILE_LST)) {
            String configFile = this.asm.getHierarchy().getConfigFile();
            int start = configFile.lastIndexOf(File.separator);
            int end = configFile.lastIndexOf(".lst");
            if (end != -1) {
               configFile = configFile.substring(start + 1, end);
            } else {
               configFile = "=" + configFile.substring(start + 1);
            }

            ipe.setHandleIdentifier(configFile);
            return configFile;
         } else if (ipe.getKind() == IProgramElement.Kind.SOURCE_FOLDER) {
            StringBuffer sb = new StringBuffer();
            sb.append(this.createHandleIdentifier(ipe.getParent())).append("/");
            String folder = ipe.getName();
            if (folder.endsWith("/")) {
               folder = folder.substring(0, folder.length() - 1);
            }

            if (folder.indexOf("/") != -1) {
               folder = folder.replace("/", "\\/");
            }

            sb.append(folder);
            String handle = sb.toString();
            ipe.setHandleIdentifier(handle);
            return handle;
         } else {
            IProgramElement parent = ipe.getParent();
            if (parent != null && parent.getKind().equals(IProgramElement.Kind.IMPORT_REFERENCE)) {
               parent = ipe.getParent().getParent();
            }

            StringBuffer handle = new StringBuffer();
            handle.append(this.createHandleIdentifier(parent));
            handle.append(HandleProviderDelimiter.getDelimiter(ipe));
            if (!ipe.getKind().equals(IProgramElement.Kind.INITIALIZER) && (ipe.getKind() != IProgramElement.Kind.CLASS || !ipe.getName().endsWith("{..}"))) {
               if (ipe.getKind() == IProgramElement.Kind.INTER_TYPE_CONSTRUCTOR) {
                  handle.append(ipe.getName()).append("_new").append(this.getParameters(ipe));
               } else if (ipe.getKind().isDeclareAnnotation()) {
                  handle.append("declare \\@").append(ipe.getName().substring(9)).append(this.getParameters(ipe));
               } else {
                  if (ipe.getFullyQualifiedName() != null) {
                     handle.append(ipe.getFullyQualifiedName());
                  } else {
                     handle.append(ipe.getName());
                  }

                  handle.append(this.getParameters(ipe));
               }
            }

            handle.append(this.getCount(ipe));
            ipe.setHandleIdentifier(handle.toString());
            return handle.toString();
         }
      } else {
         return "";
      }
   }

   private String getParameters(IProgramElement ipe) {
      if (ipe.getParameterSignatures() != null && !ipe.getParameterSignatures().isEmpty()) {
         List sourceRefs = ipe.getParameterSignaturesSourceRefs();
         List parameterTypes = ipe.getParameterSignatures();
         StringBuffer sb = new StringBuffer();
         if (sourceRefs != null) {
            for(int i = 0; i < sourceRefs.size(); ++i) {
               String sourceRef = (String)sourceRefs.get(i);
               sb.append(HandleProviderDelimiter.getDelimiter(ipe));
               sb.append(sourceRef);
            }
         } else {
            Iterator i$ = parameterTypes.iterator();

            while(i$.hasNext()) {
               char[] element = (char[])i$.next();
               sb.append(HandleProviderDelimiter.getDelimiter(ipe));
               sb.append(NameConvertor.createShortName(element, false, false));
            }
         }

         return sb.toString();
      } else {
         return "";
      }
   }

   private char[] getCount(IProgramElement ipe) {
      char[] byteCodeName = ipe.getBytecodeName().toCharArray();
      int count;
      List kids;
      Iterator i$;
      IProgramElement object;
      String existingHandle;
      int suffixPosition;
      if (ipe.getKind().isInterTypeMember()) {
         count = 1;
         kids = ipe.getParent().getChildren();
         i$ = kids.iterator();

         while(i$.hasNext()) {
            object = (IProgramElement)i$.next();
            if (object.equals(ipe)) {
               break;
            }

            if (object.getKind().isInterTypeMember() && object.getName().equals(ipe.getName()) && this.getParameters(object).equals(this.getParameters(ipe))) {
               existingHandle = object.getHandleIdentifier();
               suffixPosition = existingHandle.indexOf(33);
               if (suffixPosition != -1) {
                  count = new Integer(existingHandle.substring(suffixPosition + 1)) + 1;
               } else if (count == 1) {
                  count = 2;
               }
            }
         }

         if (count > 1) {
            return CharOperation.concat(countDelim, (new Integer(count)).toString().toCharArray());
         }
      } else if (ipe.getKind().isDeclare()) {
         count = this.computeCountBasedOnPeers(ipe);
         if (count > 1) {
            return CharOperation.concat(countDelim, (new Integer(count)).toString().toCharArray());
         }
      } else {
         String sig1;
         String existingHandle;
         int suffixPosition;
         String ipeSig;
         boolean idx;
         int idx;
         Iterator i$;
         IProgramElement object;
         if (ipe.getKind().equals(IProgramElement.Kind.ADVICE)) {
            count = 1;
            kids = ipe.getParent().getChildren();
            ipeSig = ipe.getBytecodeSignature();
            idx = false;
            ipeSig = this.shortenIpeSig(ipeSig);
            i$ = kids.iterator();

            label207:
            while(true) {
               do {
                  do {
                     do {
                        if (!i$.hasNext()) {
                           break label207;
                        }

                        object = (IProgramElement)i$.next();
                        if (object.equals(ipe)) {
                           break label207;
                        }
                     } while(object.getKind() != ipe.getKind());
                  } while(!object.getName().equals(ipe.getName()));

                  sig1 = object.getBytecodeSignature();
                  if (sig1 != null && (idx = sig1.indexOf(")")) != -1) {
                     sig1 = sig1.substring(0, idx);
                  }

                  if (sig1 != null && sig1.indexOf("Lorg/aspectj/lang") != -1) {
                     if (sig1.endsWith("Lorg/aspectj/lang/JoinPoint$StaticPart;")) {
                        sig1 = sig1.substring(0, sig1.lastIndexOf("Lorg/aspectj/lang/JoinPoint$StaticPart;"));
                     }

                     if (sig1.endsWith("Lorg/aspectj/lang/JoinPoint;")) {
                        sig1 = sig1.substring(0, sig1.lastIndexOf("Lorg/aspectj/lang/JoinPoint;"));
                     }

                     if (sig1.endsWith("Lorg/aspectj/lang/JoinPoint$StaticPart;")) {
                        sig1 = sig1.substring(0, sig1.lastIndexOf("Lorg/aspectj/lang/JoinPoint$StaticPart;"));
                     }
                  }
               } while((sig1 != null || ipeSig != null) && (sig1 == null || !sig1.equals(ipeSig)));

               existingHandle = object.getHandleIdentifier();
               suffixPosition = existingHandle.indexOf(33);
               if (suffixPosition != -1) {
                  count = new Integer(existingHandle.substring(suffixPosition + 1)) + 1;
               } else if (count == 1) {
                  count = 2;
               }
            }

            if (count > 1) {
               return CharOperation.concat(countDelim, (new Integer(count)).toString().toCharArray());
            }
         } else {
            if (ipe.getKind().equals(IProgramElement.Kind.INITIALIZER)) {
               count = 1;
               kids = ipe.getParent().getChildren();
               ipeSig = ipe.getBytecodeSignature();
               idx = false;
               ipeSig = this.shortenIpeSig(ipeSig);
               i$ = kids.iterator();

               while(i$.hasNext()) {
                  object = (IProgramElement)i$.next();
                  if (object.equals(ipe)) {
                     break;
                  }

                  if (object.getKind() == ipe.getKind() && object.getName().equals(ipe.getName())) {
                     sig1 = object.getBytecodeSignature();
                     if (sig1 != null && (idx = sig1.indexOf(")")) != -1) {
                        sig1 = sig1.substring(0, idx);
                     }

                     if (sig1 != null && sig1.indexOf("Lorg/aspectj/lang") != -1) {
                        if (sig1.endsWith("Lorg/aspectj/lang/JoinPoint$StaticPart;")) {
                           sig1 = sig1.substring(0, sig1.lastIndexOf("Lorg/aspectj/lang/JoinPoint$StaticPart;"));
                        }

                        if (sig1.endsWith("Lorg/aspectj/lang/JoinPoint;")) {
                           sig1 = sig1.substring(0, sig1.lastIndexOf("Lorg/aspectj/lang/JoinPoint;"));
                        }

                        if (sig1.endsWith("Lorg/aspectj/lang/JoinPoint$StaticPart;")) {
                           sig1 = sig1.substring(0, sig1.lastIndexOf("Lorg/aspectj/lang/JoinPoint$StaticPart;"));
                        }
                     }

                     if (sig1 == null && ipeSig == null || sig1 != null && sig1.equals(ipeSig)) {
                        existingHandle = object.getHandleIdentifier();
                        suffixPosition = existingHandle.indexOf(33);
                        if (suffixPosition != -1) {
                           count = new Integer(existingHandle.substring(suffixPosition + 1)) + 1;
                        } else if (count == 1) {
                           count = 2;
                        }
                     }
                  }
               }

               return (new Integer(count)).toString().toCharArray();
            }

            if (ipe.getKind().equals(IProgramElement.Kind.CODE)) {
               count = CharOperation.lastIndexOf('!', byteCodeName);
               if (count != -1) {
                  return this.convertCount(CharOperation.subarray(byteCodeName, count + 1, byteCodeName.length));
               }
            } else if (ipe.getKind() == IProgramElement.Kind.CLASS) {
               count = 1;
               kids = ipe.getParent().getChildren();
               int lastSquareBracket;
               if (ipe.getName().endsWith("{..}")) {
                  i$ = kids.iterator();

                  label232:
                  while(true) {
                     while(true) {
                        do {
                           do {
                              if (!i$.hasNext()) {
                                 break label232;
                              }

                              object = (IProgramElement)i$.next();
                              if (object.equals(ipe)) {
                                 break label232;
                              }
                           } while(object.getKind() != ipe.getKind());
                        } while(!object.getName().endsWith("{..}"));

                        existingHandle = object.getHandleIdentifier();
                        suffixPosition = existingHandle.lastIndexOf(33);
                        lastSquareBracket = existingHandle.lastIndexOf(91);
                        if (suffixPosition != -1 && lastSquareBracket < suffixPosition) {
                           count = new Integer(existingHandle.substring(suffixPosition + 1)) + 1;
                        } else if (count == 1) {
                           count = 2;
                        }
                     }
                  }
               } else {
                  i$ = kids.iterator();

                  label251:
                  while(true) {
                     while(true) {
                        do {
                           do {
                              if (!i$.hasNext()) {
                                 break label251;
                              }

                              object = (IProgramElement)i$.next();
                              if (object.equals(ipe)) {
                                 break label251;
                              }
                           } while(object.getKind() != ipe.getKind());
                        } while(!object.getName().equals(ipe.getName()));

                        existingHandle = object.getHandleIdentifier();
                        suffixPosition = existingHandle.lastIndexOf(33);
                        lastSquareBracket = existingHandle.lastIndexOf(91);
                        if (suffixPosition != -1 && lastSquareBracket < suffixPosition) {
                           count = new Integer(existingHandle.substring(suffixPosition + 1)) + 1;
                        } else if (count == 1) {
                           count = 2;
                        }
                     }
                  }
               }

               if (count > 1) {
                  return CharOperation.concat(countDelim, (new Integer(count)).toString().toCharArray());
               }
            }
         }
      }

      return empty;
   }

   private String shortenIpeSig(String ipeSig) {
      int idx;
      if (ipeSig != null && (idx = ipeSig.indexOf(")")) != -1) {
         ipeSig = ipeSig.substring(0, idx);
      }

      if (ipeSig != null && ipeSig.indexOf("Lorg/aspectj/lang") != -1) {
         if (ipeSig.endsWith("Lorg/aspectj/lang/JoinPoint$StaticPart;")) {
            ipeSig = ipeSig.substring(0, ipeSig.lastIndexOf("Lorg/aspectj/lang/JoinPoint$StaticPart;"));
         }

         if (ipeSig.endsWith("Lorg/aspectj/lang/JoinPoint;")) {
            ipeSig = ipeSig.substring(0, ipeSig.lastIndexOf("Lorg/aspectj/lang/JoinPoint;"));
         }

         if (ipeSig.endsWith("Lorg/aspectj/lang/JoinPoint$StaticPart;")) {
            ipeSig = ipeSig.substring(0, ipeSig.lastIndexOf("Lorg/aspectj/lang/JoinPoint$StaticPart;"));
         }
      }

      return ipeSig;
   }

   private int computeCountBasedOnPeers(IProgramElement ipe) {
      int count = 1;
      Iterator i$ = ipe.getParent().getChildren().iterator();

      while(i$.hasNext()) {
         IProgramElement object = (IProgramElement)i$.next();
         if (object.equals(ipe)) {
            break;
         }

         if (object.getKind() == ipe.getKind() && object.getKind().toString().equals(ipe.getKind().toString())) {
            String existingHandle = object.getHandleIdentifier();
            int suffixPosition = existingHandle.indexOf(33);
            if (suffixPosition != -1) {
               count = new Integer(existingHandle.substring(suffixPosition + 1)) + 1;
            } else if (count == 1) {
               count = 2;
            }
         }
      }

      return count;
   }

   private char[] convertCount(char[] c) {
      return (c.length != 1 || c[0] == ' ' || c[0] == '1') && c.length <= 1 ? empty : CharOperation.concat(countDelim, c);
   }

   public String getFileForHandle(String handle) {
      IProgramElement node = this.asm.getHierarchy().getElement(handle);
      if (node != null) {
         return this.asm.getCanonicalFilePath(node.getSourceLocation().getSourceFile());
      } else {
         return handle.charAt(0) != HandleProviderDelimiter.ASPECT_CU.getDelimiter() && handle.charAt(0) != HandleProviderDelimiter.COMPILATIONUNIT.getDelimiter() ? "" : "\\" + handle.substring(1);
      }
   }

   public int getLineNumberForHandle(String handle) {
      IProgramElement node = this.asm.getHierarchy().getElement(handle);
      if (node != null) {
         return node.getSourceLocation().getLine();
      } else {
         return handle.charAt(0) != HandleProviderDelimiter.ASPECT_CU.getDelimiter() && handle.charAt(0) != HandleProviderDelimiter.COMPILATIONUNIT.getDelimiter() ? -1 : 1;
      }
   }

   public int getOffSetForHandle(String handle) {
      IProgramElement node = this.asm.getHierarchy().getElement(handle);
      if (node != null) {
         return node.getSourceLocation().getOffset();
      } else {
         return handle.charAt(0) != HandleProviderDelimiter.ASPECT_CU.getDelimiter() && handle.charAt(0) != HandleProviderDelimiter.COMPILATIONUNIT.getDelimiter() ? -1 : 0;
      }
   }

   public String createHandleIdentifier(ISourceLocation location) {
      IProgramElement node = this.asm.getHierarchy().findElementForSourceLine(location);
      return node != null ? this.createHandleIdentifier(node) : null;
   }

   public String createHandleIdentifier(File sourceFile, int line, int column, int offset) {
      IProgramElement node = this.asm.getHierarchy().findElementForOffSet(sourceFile.getAbsolutePath(), line, offset);
      return node != null ? this.createHandleIdentifier(node) : null;
   }

   public boolean dependsOnLocation() {
      return false;
   }

   static {
      countDelim = new char[]{HandleProviderDelimiter.COUNT.getDelimiter()};
   }
}
