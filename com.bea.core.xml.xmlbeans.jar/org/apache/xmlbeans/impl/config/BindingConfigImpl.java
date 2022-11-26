package org.apache.xmlbeans.impl.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.xmlbeans.BindingConfig;
import org.apache.xmlbeans.InterfaceExtension;
import org.apache.xmlbeans.PrePostExtension;
import org.apache.xmlbeans.UserType;
import org.apache.xmlbeans.XmlObject;
import org.apache.xmlbeans.impl.jam.JamClassLoader;
import org.apache.xmlbeans.impl.jam.JamService;
import org.apache.xmlbeans.impl.jam.JamServiceFactory;
import org.apache.xmlbeans.impl.jam.JamServiceParams;
import org.apache.xmlbeans.impl.schema.StscState;
import org.apache.xmlbeans.impl.xb.xmlconfig.ConfigDocument;
import org.apache.xmlbeans.impl.xb.xmlconfig.Extensionconfig;
import org.apache.xmlbeans.impl.xb.xmlconfig.Nsconfig;
import org.apache.xmlbeans.impl.xb.xmlconfig.Qnameconfig;
import org.apache.xmlbeans.impl.xb.xmlconfig.Qnametargetenum;
import org.apache.xmlbeans.impl.xb.xmlconfig.Usertypeconfig;

public class BindingConfigImpl extends BindingConfig {
   private Map _packageMap;
   private Map _prefixMap;
   private Map _suffixMap;
   private Map _packageMapByUriPrefix;
   private Map _prefixMapByUriPrefix;
   private Map _suffixMapByUriPrefix;
   private Map _qnameTypeMap;
   private Map _qnameDocTypeMap;
   private Map _qnameElemMap;
   private Map _qnameAttMap;
   private List _interfaceExtensions;
   private List _prePostExtensions;
   private Map _userTypes;

   private BindingConfigImpl() {
      this._packageMap = Collections.EMPTY_MAP;
      this._prefixMap = Collections.EMPTY_MAP;
      this._suffixMap = Collections.EMPTY_MAP;
      this._packageMapByUriPrefix = Collections.EMPTY_MAP;
      this._prefixMapByUriPrefix = Collections.EMPTY_MAP;
      this._suffixMapByUriPrefix = Collections.EMPTY_MAP;
      this._qnameTypeMap = Collections.EMPTY_MAP;
      this._qnameDocTypeMap = Collections.EMPTY_MAP;
      this._qnameElemMap = Collections.EMPTY_MAP;
      this._qnameAttMap = Collections.EMPTY_MAP;
      this._interfaceExtensions = new ArrayList();
      this._prePostExtensions = new ArrayList();
      this._userTypes = Collections.EMPTY_MAP;
   }

   public static BindingConfig forConfigDocuments(ConfigDocument.Config[] configs, File[] javaFiles, File[] classpath) {
      return new BindingConfigImpl(configs, javaFiles, classpath);
   }

   private BindingConfigImpl(ConfigDocument.Config[] configs, File[] javaFiles, File[] classpath) {
      this._packageMap = new LinkedHashMap();
      this._prefixMap = new LinkedHashMap();
      this._suffixMap = new LinkedHashMap();
      this._packageMapByUriPrefix = new LinkedHashMap();
      this._prefixMapByUriPrefix = new LinkedHashMap();
      this._suffixMapByUriPrefix = new LinkedHashMap();
      this._qnameTypeMap = new LinkedHashMap();
      this._qnameDocTypeMap = new LinkedHashMap();
      this._qnameElemMap = new LinkedHashMap();
      this._qnameAttMap = new LinkedHashMap();
      this._interfaceExtensions = new ArrayList();
      this._prePostExtensions = new ArrayList();
      this._userTypes = new LinkedHashMap();

      for(int i = 0; i < configs.length; ++i) {
         ConfigDocument.Config config = configs[i];
         Nsconfig[] nsa = config.getNamespaceArray();

         for(int j = 0; j < nsa.length; ++j) {
            recordNamespaceSetting(nsa[j].getUri(), nsa[j].getPackage(), this._packageMap);
            recordNamespaceSetting(nsa[j].getUri(), nsa[j].getPrefix(), this._prefixMap);
            recordNamespaceSetting(nsa[j].getUri(), nsa[j].getSuffix(), this._suffixMap);
            recordNamespacePrefixSetting(nsa[j].getUriprefix(), nsa[j].getPackage(), this._packageMapByUriPrefix);
            recordNamespacePrefixSetting(nsa[j].getUriprefix(), nsa[j].getPrefix(), this._prefixMapByUriPrefix);
            recordNamespacePrefixSetting(nsa[j].getUriprefix(), nsa[j].getSuffix(), this._suffixMapByUriPrefix);
         }

         Qnameconfig[] qnc = config.getQnameArray();

         for(int j = 0; j < qnc.length; ++j) {
            List applyto = qnc[j].xgetTarget().xgetListValue();
            QName name = qnc[j].getName();
            String javaname = qnc[j].getJavaname();

            for(int k = 0; k < applyto.size(); ++k) {
               Qnametargetenum a = (Qnametargetenum)applyto.get(k);
               switch (a.enumValue().intValue()) {
                  case 1:
                     this._qnameTypeMap.put(name, javaname);
                     break;
                  case 2:
                     this._qnameDocTypeMap.put(name, javaname);
                     break;
                  case 3:
                     this._qnameElemMap.put(name, javaname);
                     break;
                  case 4:
                     this._qnameAttMap.put(name, javaname);
               }
            }
         }

         Extensionconfig[] ext = config.getExtensionArray();

         for(int j = 0; j < ext.length; ++j) {
            this.recordExtensionSetting(javaFiles, classpath, ext[j]);
         }

         Usertypeconfig[] utypes = config.getUsertypeArray();

         for(int j = 0; j < utypes.length; ++j) {
            this.recordUserTypeSetting(javaFiles, classpath, utypes[j]);
         }
      }

      this.secondPhaseValidation();
   }

   void addInterfaceExtension(InterfaceExtensionImpl ext) {
      if (ext != null) {
         this._interfaceExtensions.add(ext);
      }
   }

   void addPrePostExtension(PrePostExtensionImpl ext) {
      if (ext != null) {
         this._prePostExtensions.add(ext);
      }
   }

   void secondPhaseValidation() {
      Map methodSignatures = new HashMap();

      int i;
      for(i = 0; i < this._interfaceExtensions.size(); ++i) {
         InterfaceExtensionImpl interfaceExtension = (InterfaceExtensionImpl)this._interfaceExtensions.get(i);
         InterfaceExtensionImpl.MethodSignatureImpl[] methods = (InterfaceExtensionImpl.MethodSignatureImpl[])((InterfaceExtensionImpl.MethodSignatureImpl[])interfaceExtension.getMethods());

         for(int j = 0; j < methods.length; ++j) {
            InterfaceExtensionImpl.MethodSignatureImpl ms = methods[j];
            if (methodSignatures.containsKey(methods[j])) {
               InterfaceExtensionImpl.MethodSignatureImpl ms2 = (InterfaceExtensionImpl.MethodSignatureImpl)methodSignatures.get(methods[j]);
               if (!ms.getReturnType().equals(ms2.getReturnType())) {
                  error("Colliding methods '" + ms.getSignature() + "' in interfaces " + ms.getInterfaceName() + " and " + ms2.getInterfaceName() + ".", (XmlObject)null);
               }

               return;
            }

            methodSignatures.put(methods[j], methods[j]);
         }
      }

      for(i = 0; i < this._prePostExtensions.size() - 1; ++i) {
         PrePostExtensionImpl a = (PrePostExtensionImpl)this._prePostExtensions.get(i);

         for(int j = 1; j < this._prePostExtensions.size(); ++j) {
            PrePostExtensionImpl b = (PrePostExtensionImpl)this._prePostExtensions.get(j);
            if (a.hasNameSetIntersection(b)) {
               error("The applicable domain for handler '" + a.getHandlerNameForJavaSource() + "' intersects with the one for '" + b.getHandlerNameForJavaSource() + "'.", (XmlObject)null);
            }
         }
      }

   }

   private static void recordNamespaceSetting(Object key, String value, Map result) {
      if (value != null) {
         if (key == null) {
            result.put("", value);
         } else if (key instanceof String && "##any".equals(key)) {
            result.put(key, value);
         } else {
            String uri;
            if (key instanceof List) {
               for(Iterator i = ((List)key).iterator(); i.hasNext(); result.put(uri, value)) {
                  uri = (String)i.next();
                  if ("##local".equals(uri)) {
                     uri = "";
                  }
               }
            }
         }

      }
   }

   private static void recordNamespacePrefixSetting(List list, String value, Map result) {
      if (value != null) {
         if (list != null) {
            Iterator i = list.iterator();

            while(i.hasNext()) {
               result.put(i.next(), value);
            }

         }
      }
   }

   private void recordExtensionSetting(File[] javaFiles, File[] classpath, Extensionconfig ext) {
      NameSet xbeanSet = null;
      Object key = ext.getFor();
      if (key instanceof String && "*".equals(key)) {
         xbeanSet = NameSet.EVERYTHING;
      } else if (key instanceof List) {
         NameSetBuilder xbeanSetBuilder = new NameSetBuilder();
         Iterator i = ((List)key).iterator();

         while(i.hasNext()) {
            String xbeanName = (String)i.next();
            xbeanSetBuilder.add(xbeanName);
         }

         xbeanSet = xbeanSetBuilder.toNameSet();
      }

      if (xbeanSet == null) {
         error("Invalid value of attribute 'for' : '" + key + "'.", ext);
      }

      Extensionconfig.Interface[] intfXO = ext.getInterfaceArray();
      Extensionconfig.PrePostSet ppXO = ext.getPrePostSet();
      if (intfXO.length > 0 || ppXO != null) {
         JamClassLoader jamLoader = this.getJamLoader(javaFiles, classpath);

         for(int i = 0; i < intfXO.length; ++i) {
            this.addInterfaceExtension(InterfaceExtensionImpl.newInstance(jamLoader, xbeanSet, intfXO[i]));
         }

         this.addPrePostExtension(PrePostExtensionImpl.newInstance(jamLoader, xbeanSet, ppXO));
      }

   }

   private void recordUserTypeSetting(File[] javaFiles, File[] classpath, Usertypeconfig usertypeconfig) {
      JamClassLoader jamLoader = this.getJamLoader(javaFiles, classpath);
      UserTypeImpl userType = UserTypeImpl.newInstance(jamLoader, usertypeconfig);
      this._userTypes.put(userType.getName(), userType);
   }

   private String lookup(Map map, Map mapByUriPrefix, String uri) {
      if (uri == null) {
         uri = "";
      }

      String result = (String)map.get(uri);
      if (result != null) {
         return result;
      } else {
         if (mapByUriPrefix != null) {
            result = this.lookupByUriPrefix(mapByUriPrefix, uri);
            if (result != null) {
               return result;
            }
         }

         return (String)map.get("##any");
      }
   }

   private String lookupByUriPrefix(Map mapByUriPrefix, String uri) {
      if (uri == null) {
         return null;
      } else if (!mapByUriPrefix.isEmpty()) {
         String uriprefix = null;
         Iterator i = mapByUriPrefix.keySet().iterator();

         while(true) {
            String nextprefix;
            do {
               if (!i.hasNext()) {
                  if (uriprefix != null) {
                     return (String)mapByUriPrefix.get(uriprefix);
                  }

                  return null;
               }

               nextprefix = (String)i.next();
            } while(uriprefix != null && nextprefix.length() < uriprefix.length());

            if (uri.startsWith(nextprefix)) {
               uriprefix = nextprefix;
            }
         }
      } else {
         return null;
      }
   }

   static void warning(String s, XmlObject xo) {
      StscState.get().error(s, 1, xo);
   }

   static void error(String s, XmlObject xo) {
      StscState.get().error(s, 0, xo);
   }

   public String lookupPackageForNamespace(String uri) {
      return this.lookup(this._packageMap, this._packageMapByUriPrefix, uri);
   }

   public String lookupPrefixForNamespace(String uri) {
      return this.lookup(this._prefixMap, this._prefixMapByUriPrefix, uri);
   }

   public String lookupSuffixForNamespace(String uri) {
      return this.lookup(this._suffixMap, this._suffixMapByUriPrefix, uri);
   }

   /** @deprecated */
   public String lookupJavanameForQName(QName qname) {
      String result = (String)this._qnameTypeMap.get(qname);
      return result != null ? result : (String)this._qnameDocTypeMap.get(qname);
   }

   public String lookupJavanameForQName(QName qname, int kind) {
      switch (kind) {
         case 1:
            return (String)this._qnameTypeMap.get(qname);
         case 2:
            return (String)this._qnameDocTypeMap.get(qname);
         case 3:
            return (String)this._qnameElemMap.get(qname);
         case 4:
            return (String)this._qnameAttMap.get(qname);
         default:
            return null;
      }
   }

   public UserType lookupUserTypeForQName(QName qname) {
      return qname == null ? null : (UserType)this._userTypes.get(qname);
   }

   public InterfaceExtension[] getInterfaceExtensions() {
      return (InterfaceExtension[])((InterfaceExtension[])this._interfaceExtensions.toArray(new InterfaceExtension[this._interfaceExtensions.size()]));
   }

   public InterfaceExtension[] getInterfaceExtensions(String fullJavaName) {
      List result = new ArrayList();

      for(int i = 0; i < this._interfaceExtensions.size(); ++i) {
         InterfaceExtensionImpl intfExt = (InterfaceExtensionImpl)this._interfaceExtensions.get(i);
         if (intfExt.contains(fullJavaName)) {
            result.add(intfExt);
         }
      }

      return (InterfaceExtension[])((InterfaceExtension[])result.toArray(new InterfaceExtension[result.size()]));
   }

   public PrePostExtension[] getPrePostExtensions() {
      return (PrePostExtension[])((PrePostExtension[])this._prePostExtensions.toArray(new PrePostExtension[this._prePostExtensions.size()]));
   }

   public PrePostExtension getPrePostExtension(String fullJavaName) {
      for(int i = 0; i < this._prePostExtensions.size(); ++i) {
         PrePostExtensionImpl prePostExt = (PrePostExtensionImpl)this._prePostExtensions.get(i);
         if (prePostExt.contains(fullJavaName)) {
            return prePostExt;
         }
      }

      return null;
   }

   private JamClassLoader getJamLoader(File[] javaFiles, File[] classpath) {
      JamServiceFactory jf = JamServiceFactory.getInstance();
      JamServiceParams params = jf.createServiceParams();
      params.set14WarningsEnabled(false);
      params.setShowWarnings(false);
      int i;
      if (javaFiles != null) {
         for(i = 0; i < javaFiles.length; ++i) {
            params.includeSourceFile(javaFiles[i]);
         }
      }

      params.addClassLoader(this.getClass().getClassLoader());
      if (classpath != null) {
         for(i = 0; i < classpath.length; ++i) {
            params.addClasspath(classpath[i]);
         }
      }

      JamService service;
      try {
         service = jf.createService(params);
      } catch (IOException var7) {
         error("Error when accessing .java files.", (XmlObject)null);
         return null;
      }

      return service.getClassLoader();
   }
}
