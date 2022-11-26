package com.bea.staxb.buildtime;

import com.bea.xml.SchemaGlobalElement;
import com.bea.xml.SchemaParticle;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeLoader;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;

public class WrappedOperationInfo {
   private QName input;
   private QName output;
   private boolean allowArraysWrapped;
   private boolean isWrappedArray;

   public WrappedOperationInfo(QName input, QName output) {
      this(input, output, false);
   }

   public WrappedOperationInfo(QName input, QName output, boolean allowWrappedArray) {
      this.allowArraysWrapped = false;
      this.isWrappedArray = false;
      this.input = input;
      this.output = output;
      this.allowArraysWrapped = allowWrappedArray;
      if (input == null) {
         throw new IllegalArgumentException("Input element can't be null");
      }
   }

   public QName[] getElements() {
      Set result = new HashSet();
      if (this.input != null) {
         result.add(this.input);
      }

      if (this.output != null) {
         result.add(this.output);
      }

      return (QName[])((QName[])result.toArray(new QName[0]));
   }

   public boolean isWrappedArray() {
      return this.isWrappedArray;
   }

   public boolean isWrapped(SchemaTypeLoader stl) {
      boolean wrapped = this.isWrappedElement(stl, this.input, this.allowArraysWrapped);
      if (wrapped && this.output != null) {
         wrapped = this.isWrappedElement(stl, this.output, this.allowArraysWrapped);
      }

      return wrapped;
   }

   public boolean isWrappedElement(SchemaTypeLoader stl, QName element, boolean checkWrappedArray) {
      SchemaGlobalElement docType = stl.findElement(element);
      if (docType == null) {
         throw new IllegalArgumentException("Wrapper schema element " + element + " is not found in wsdl.");
      } else {
         SchemaType type = docType.getType();
         boolean max1Property = false;
         if (element == this.output) {
            max1Property = true;
         }

         return this.hasWrapperFormat(type, max1Property, checkWrappedArray);
      }
   }

   public boolean hasWrapperFormat(SchemaType type, boolean max1Property, boolean checkWrappedArray) {
      if (type.isSimpleType()) {
         return false;
      } else if (type.getAttributeProperties().length > 0) {
         return false;
      } else if (type.getContentType() == 1) {
         return true;
      } else if (type.getContentType() != 3 && type.getContentType() != 4) {
         return false;
      } else {
         SchemaParticle contents = type.getContentModel();
         String expectedNS = findNameSpace(type);
         if (contents.getParticleType() == 4) {
            if (contents.getMaxOccurs() == null || contents.getMaxOccurs().compareTo(BigInteger.ONE) == 1) {
               if (!checkWrappedArray) {
                  return false;
               }

               this.isWrappedArray = true;
            }

            return namespaceEqual(expectedNS, contents.getName().getNamespaceURI());
         } else {
            SchemaProperty[] props = type.getElementProperties();
            if (props.length == 0 && contents.countOfParticleChild() == 0 && contents.getParticleType() == 5) {
               return true;
            } else {
               if (props.length > 1) {
                  for(int i = 0; i < props.length; ++i) {
                     if (isMultiple(props[i])) {
                        return false;
                     }
                  }
               }

               if (contents.getParticleType() != 3) {
                  return false;
               } else {
                  SchemaParticle[] children = contents.getParticleChildren();
                  if (max1Property && children.length > 1) {
                     return false;
                  } else {
                     HashSet usedNames = new HashSet();

                     for(int i = 0; i < children.length; ++i) {
                        if (children[i].getParticleType() != 5) {
                           if (children[i].getParticleType() != 4) {
                              return false;
                           }

                           QName name = children[i].getName();
                           if (!namespaceEqual(expectedNS, name.getNamespaceURI())) {
                              return false;
                           }

                           if (contents.getMaxOccurs() == null || contents.getMaxOccurs().compareTo(BigInteger.ONE) == 1) {
                              return false;
                           }

                           if (usedNames.contains(name.getLocalPart())) {
                              return false;
                           }

                           usedNames.add(name.getLocalPart());
                        }
                     }

                     return true;
                  }
               }
            }
         }
      }
   }

   private static boolean namespaceEqual(String n1, String n2) {
      return true;
   }

   private static String findNameSpace(SchemaType type) {
      if (type.getName() != null) {
         return type.getName().getNamespaceURI();
      } else {
         SchemaType outType = type.getOuterType();
         return outType != null && outType.getDocumentElementName() != null ? outType.getDocumentElementName().getNamespaceURI() : null;
      }
   }

   private static boolean isMultiple(SchemaProperty prop) {
      return prop.getMaxOccurs() == null || prop.getMaxOccurs().compareTo(BigInteger.ONE) > 0;
   }
}
