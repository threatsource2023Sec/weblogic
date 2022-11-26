package com.bea.staxb.buildtime.internal.mbean;

import com.bea.util.jam.JClass;
import com.bea.util.jam.JProperty;
import com.bea.xbean.common.NameUtil;
import com.bea.xml.SchemaProperty;
import com.bea.xml.SchemaType;
import com.bea.xml.SchemaTypeSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;

public class DefaultTypeMatcher implements TypeMatcher {
   private TypeMatcherContext mContext;
   private final Map mapByShortName = new HashMap();
   private final Map mapByLowercasedShortName = new HashMap();

   public void init(TypeMatcherContext ctx) {
      this.mContext = ctx;
   }

   public TypeMatcher.MatchedType[] matchTypes(JClass[] jClasses, SchemaTypeSystem sts) {
      SchemaType[] types = sts.globalTypes();
      List result = new ArrayList();
      this.startMatch();

      int i;
      for(i = 0; i < jClasses.length; ++i) {
         this.putJavaName(jClasses[i].getSimpleName(), jClasses[i]);
      }

      for(i = 0; i < types.length; ++i) {
         JClass jClass = (JClass)this.getSchemaName(types[i].getName());
         if (jClass != null) {
            result.add(new TypeMatcher.MatchedType(jClass, types[i]));
         }
      }

      return (TypeMatcher.MatchedType[])((TypeMatcher.MatchedType[])result.toArray(new TypeMatcher.MatchedType[result.size()]));
   }

   public TypeMatcher.MatchedProperties[] matchProperties(JClass jClass, SchemaType sType) {
      SchemaProperty[] sProps = sType.getProperties();
      JProperty[] jProps = jClass.getProperties();
      List result = new ArrayList();
      this.startMatch();

      int i;
      for(i = 0; i < jProps.length; ++i) {
         this.putJavaName(jProps[i].getSimpleName(), jProps[i]);
      }

      for(i = 0; i < sProps.length; ++i) {
         JProperty jProp = (JProperty)this.getSchemaName(sProps[i].getName());
         if (jProp != null) {
            result.add(new TypeMatcher.MatchedProperties(jProp, sProps[i]));
         }
      }

      return (TypeMatcher.MatchedProperties[])((TypeMatcher.MatchedProperties[])result.toArray(new TypeMatcher.MatchedProperties[result.size()]));
   }

   public JClass substituteClass(JClass declaredClass) {
      return declaredClass;
   }

   public void startMatch() {
      this.mapByShortName.clear();
      this.mapByLowercasedShortName.clear();
   }

   public boolean putJavaName(String key, Object value) {
      this.verbose("JavaNameMatcher.put " + key);
      if (this.mapByShortName.containsKey(key)) {
         return false;
      } else {
         this.mapByShortName.put(key, value);
         String lcShortName = key.toLowerCase();
         if (this.mapByLowercasedShortName.containsKey(lcShortName)) {
            this.mapByLowercasedShortName.put(lcShortName, (Object)null);
         } else {
            this.mapByLowercasedShortName.put(lcShortName, value);
         }

         return true;
      }
   }

   public Object getSchemaName(QName name) {
      Object result = null;
      String localName = name.getLocalPart();
      this.verbose("JavaNameMatcher.getLocalPart " + localName);
      result = this.mapByShortName.get(localName);
      if (result != null) {
         this.verbose("javaTypeByShortName.get(localName): " + localName);
         return result;
      } else {
         String lcLocalName = localName.toLowerCase();
         this.verbose("JavaNameMatcher.lcLocalName " + lcLocalName);
         result = this.mapByLowercasedShortName.get(lcLocalName);
         if (result != null) {
            this.verbose("javaTypeByLowercasedShortName.get(lcLocalName): " + lcLocalName);
            return result;
         } else {
            String niceName = NameUtil.upperCamelCase(localName);
            this.verbose("JavaNameMatcher.jaxbName " + niceName);
            result = this.mapByShortName.get(niceName);
            if (result != null) {
               this.verbose("javaTypeByShortName.get(jaxbName): " + niceName);
               return result;
            } else {
               String lowercaseNiceName = niceName.toLowerCase();
               this.verbose("JavaNameMatcher.lcJaxbName " + lowercaseNiceName);
               result = this.mapByLowercasedShortName.get(lowercaseNiceName);
               if (result != null) {
                  this.verbose("javaTypeByShortName.get(lcJaxbName): " + lowercaseNiceName);
                  return result;
               } else {
                  this.verbose("javaTypeByShortName.get() no match found: " + localName);
                  return null;
               }
            }
         }
      }
   }

   private void verbose(String w) {
      this.mContext.getLogger().logVerbose(w);
   }
}
