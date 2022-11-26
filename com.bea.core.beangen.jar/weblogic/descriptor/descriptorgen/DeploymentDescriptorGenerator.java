package weblogic.descriptor.descriptorgen;

import com.bea.util.jam.JAnnotatedElement;
import com.bea.util.jam.JAnnotation;
import com.bea.util.jam.JClass;
import com.bea.util.jam.JMethod;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.codegen.AnnotatableAttribute;
import weblogic.descriptor.codegen.AnnotatableClass;
import weblogic.descriptor.codegen.AnnotatableMethod;
import weblogic.descriptor.codegen.CodeGenOptions;
import weblogic.descriptor.codegen.CodeGenerator;
import weblogic.descriptor.codegen.Production;

public class DeploymentDescriptorGenerator extends CodeGenerator {
   public static final String DEFAULT_TEMPLATE = "weblogic/utils/descriptorgen/descriptor.template";

   public DeploymentDescriptorGenerator(CodeGenOptions options) {
      super(options);
   }

   public void generate() throws Exception {
      super.generate();
   }

   public Production getProduction(JClass jClass) {
      return new Production(jClass, this) {
         public Object getAnnotatableTarget() {
            if (this._target == null) {
               this._target = DeploymentDescriptorGenerator.this.new AnnotatableTarget(this.sourceJClass, this.getSuffix());
               this.validate(this._target);
            }

            return this._target;
         }

         public AnnotatableClass getAnnotatableSource() {
            if (this._source == null) {
               this._source = new AnnotatableClass(this.sourceJClass);
               this.validate(this._source);
            }

            return this._source;
         }

         void validate(AnnotatableClass c) {
            if (c.hasMethods()) {
               AnnotatableMethod[] m = c.getMethods();
               ArrayList ml = new ArrayList(Arrays.asList((Object[])m));

               AnnotatableMethod setter;
               for(int i = 0; i < m.length; ++i) {
                  if (m[i].isGetter()) {
                     ml.remove(m[i]);
                     setter = this.findSetter(m[i], m);
                     if (setter != null) {
                        ml.remove(setter);
                     }
                  }
               }

               Iterator iterator = ml.iterator();

               do {
                  if (!iterator.hasNext()) {
                     return;
                  }

                  setter = (AnnotatableMethod)iterator.next();
               } while(!setter.isSetter());

               throw new IllegalArgumentException("Setter not matched with getter for: " + setter);
            }
         }

         AnnotatableMethod findSetter(AnnotatableMethod getter, AnnotatableMethod[] m) {
            String propName = getter.getAnnotatableAttribute().getPropertyName();

            for(int i = 0; i < m.length; ++i) {
               if (m[i].isSetter()) {
                  String n = m[i].getAnnotatableAttribute().getPropertyName();
                  if (n.equals(propName)) {
                     return m[i];
                  }
               }
            }

            return null;
         }
      };
   }

   private static JAnnotation[] getTagsNamed(JAnnotatedElement element, String named) {
      List list = new ArrayList();
      JAnnotation[] anns = element.getAllJavadocTags();

      for(int i = 0; i < anns.length; ++i) {
         if (anns[i].getQualifiedName().equals(named)) {
            list.add(anns[i]);
         }
      }

      JAnnotation[] out = new JAnnotation[list.size()];
      list.toArray(out);
      return out;
   }

   class AnnotatableTarget extends AnnotatableClass {
      private String suffix;
      private AnnotatableClass[] syntheticDelegates;
      private AnnotatableAttribute[] syntheticAttributes;
      private HashMap attributesByElementNameLength;
      private Integer[] allAttributeElementNameLengths;

      public AnnotatableTarget(JClass jClass, String suffix) {
         super(jClass);
         this.suffix = suffix;
      }

      public AnnotatableClass getSourceClass() {
         return this.newAnnotatableClass(this.getJClass());
      }

      public String getName() {
         return this.suffix != null ? super.getName() + this.suffix : super.getName();
      }

      public String getSuperClassName() {
         String s = super.getSuperClassName();
         return s.equals("java.lang.Object") ? s : s + this.suffix;
      }

      public boolean hasDelegate() {
         return this.getAnnotation("delegate") != null;
      }

      public AnnotatableAttribute getDelegate() {
         try {
            return this.getAnnotation("delegate") == null ? null : new AnnotatableAttribute(this.getAnnotation("delegate").getAnnotatableClassValue().getJClass());
         } catch (Exception var2) {
            var2.printStackTrace();
            throw new AssertionError("Class for delegate[" + this.getAnnotation("delegate").getStringValue() + "] could not be found");
         }
      }

      public boolean hasSyntheticDelegates() {
         return this.getSyntheticDelegates().length > 0;
      }

      public AnnotatableClass[] getSyntheticDelegates() {
         if (this.syntheticDelegates == null) {
            JClass[] interfaces = this.getJClass().getInterfaces();
            if (interfaces.length > 1) {
               this.syntheticDelegates = new AnnotatableClass[interfaces.length - 1];

               for(int i = 1; i < interfaces.length; ++i) {
                  this.syntheticDelegates[i - 1] = this.newAnnotatableClass(interfaces[i]);
               }
            } else {
               this.syntheticDelegates = new AnnotatableClass[0];
            }
         }

         return this.syntheticDelegates;
      }

      public boolean hasSyntheticAttributes() {
         return this.getSyntheticAttributes().length > 0;
      }

      public AnnotatableAttribute[] getSyntheticAttributes() {
         if (this.syntheticAttributes == null) {
            JMethod[] ms = this.getJClass().getDeclaredMethods();
            ArrayList synths = new ArrayList();

            int i;
            for(i = 0; i < ms.length; ++i) {
               if (this.newAnnotatableMethod(ms[i]).isGetter() && ms[i].getReturnType().isArrayType()) {
                  synths.add(this.newAnnotatableAttribute(ms[i]));
               }
            }

            this.syntheticAttributes = new AnnotatableAttribute[synths.size()];

            for(i = 0; i < synths.size(); ++i) {
               this.syntheticAttributes[i] = (AnnotatableAttribute)synths.get(i);
            }
         }

         return this.syntheticAttributes;
      }

      public Integer[] getAttributeElementNameLengths() {
         if (this.allAttributeElementNameLengths == null) {
            HashMap attrs = this.getAttributeMapByElementNameLength();
            this.allAttributeElementNameLengths = new Integer[attrs.size()];
            int i = 0;

            for(Iterator iter = attrs.keySet().iterator(); iter.hasNext(); ++i) {
               this.allAttributeElementNameLengths[i] = (Integer)iter.next();
            }
         }

         return this.allAttributeElementNameLengths;
      }

      public AnnotatableAttribute[] getAttributesByElementNameLength(Integer nameLength) {
         HashMap mapByLength = this.getAttributeMapByElementNameLength();
         HashMap map = (HashMap)mapByLength.get(nameLength);
         AnnotatableAttribute[] atts = new AnnotatableAttribute[map.size()];
         int i = 0;

         for(Iterator iter = map.values().iterator(); iter.hasNext(); ++i) {
            atts[i] = (AnnotatableAttribute)iter.next();
         }

         return atts;
      }

      HashMap getAttributeMapByElementNameLength() {
         if (this.attributesByElementNameLength == null) {
            this.attributesByElementNameLength = new HashMap();
            JMethod[] methods = this.getJClass().getDeclaredMethods();

            int i;
            for(i = 0; i < methods.length; ++i) {
               if (this.newAnnotatableMethod(methods[i]).isGetter()) {
                  this.accumulate(this.newAnnotatableAttribute(methods[i]));
               }
            }

            for(i = 0; i < methods.length; ++i) {
               if (this.newAnnotatableMethod(methods[i]).isGetter() && methods[i].getReturnType().isArrayType()) {
                  JClass jc = methods[i].getReturnType().getArrayComponentType();
                  if (!jc.isPrimitiveType()) {
                     AnnotatableAttribute tt = this.newAnnotatableAttribute(methods[i]);
                     this.accumulate(tt);
                  }
               }
            }

            for(i = 0; i < methods.length; ++i) {
               if (this.newAnnotatableMethod(methods[i]).isGetter()) {
                  JAnnotation[] ja = DeploymentDescriptorGenerator.getTagsNamed(methods[i], "elementName");
                  if (ja != null && ja.length != 1) {
                     for(int j = 1; j < ja.length; ++j) {
                        AnnotatableAttribute ttx = this.newAnnotatableAttribute(methods[i]);
                        this.accumulate(ttx);
                     }
                  }
               }
            }
         }

         return this.attributesByElementNameLength;
      }

      void accumulate(AnnotatableAttribute attribute) {
         String elementName = attribute.getType().getElementName();
         Integer key = new Integer(elementName.length());
         HashMap map = (HashMap)this.attributesByElementNameLength.get(key);
         if (map == null) {
            map = new HashMap();
            this.attributesByElementNameLength.put(key, map);
         }

         map.put(elementName, attribute);
      }
   }
}
