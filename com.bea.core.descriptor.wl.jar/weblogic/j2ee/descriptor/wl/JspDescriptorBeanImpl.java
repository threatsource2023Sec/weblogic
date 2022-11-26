package weblogic.j2ee.descriptor.wl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.AbstractDescriptorBeanHelper;
import weblogic.descriptor.internal.AbstractSchemaHelper2;
import weblogic.descriptor.internal.Munger;
import weblogic.descriptor.internal.SchemaHelper;
import weblogic.utils.collections.CombinedIterator;

public class JspDescriptorBeanImpl extends AbstractDescriptorBean implements JspDescriptorBean, Serializable {
   private boolean _BackwardCompatible;
   private String _CompilerSourceVM;
   private String _CompilerTargetVM;
   private boolean _CompressHtmlTemplate;
   private boolean _Debug;
   private String _DefaultFileName;
   private boolean _EL22BackwardCompatible;
   private String _Encoding;
   private boolean _ExactMapping;
   private String _ExpressionInterceptor;
   private String _Id;
   private boolean _Keepgenerated;
   private boolean _OptimizeJavaExpression;
   private String _PackagePrefix;
   private int _PageCheckSeconds;
   private boolean _Precompile;
   private boolean _PrecompileContinue;
   private boolean _PrintNulls;
   private String _ResourceProviderClass;
   private boolean _RtexprvalueJspParamName;
   private boolean _StrictJspDocumentValidation;
   private boolean _StrictStaleCheck;
   private String _SuperClass;
   private boolean _Verbose;
   private String _WorkingDir;
   private static SchemaHelper2 _schemaHelper;

   public JspDescriptorBeanImpl() {
      this._initializeProperty(-1);
   }

   public JspDescriptorBeanImpl(DescriptorBean param0, int param1) {
      super(param0, param1);
      this._initializeProperty(-1);
   }

   public JspDescriptorBeanImpl(DescriptorBean param0, int param1, boolean param2) {
      super(param0, param1);
      this._setTransient(param2);
      this._initializeProperty(-1);
   }

   public boolean isKeepgenerated() {
      return this._Keepgenerated;
   }

   public boolean isKeepgeneratedInherited() {
      return false;
   }

   public boolean isKeepgeneratedSet() {
      return this._isSet(0);
   }

   public void setKeepgenerated(boolean param0) {
      boolean _oldVal = this._Keepgenerated;
      this._Keepgenerated = param0;
      this._postSet(0, _oldVal, param0);
   }

   public String getPackagePrefix() {
      return this._PackagePrefix;
   }

   public boolean isPackagePrefixInherited() {
      return false;
   }

   public boolean isPackagePrefixSet() {
      return this._isSet(1);
   }

   public void setPackagePrefix(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._PackagePrefix;
      this._PackagePrefix = param0;
      this._postSet(1, _oldVal, param0);
   }

   public String getSuperClass() {
      return this._SuperClass;
   }

   public boolean isSuperClassInherited() {
      return false;
   }

   public boolean isSuperClassSet() {
      return this._isSet(2);
   }

   public void setSuperClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._SuperClass;
      this._SuperClass = param0;
      this._postSet(2, _oldVal, param0);
   }

   public int getPageCheckSeconds() {
      if (!this._isSet(3)) {
         return this._isProductionModeEnabled() ? -1 : 1;
      } else {
         return this._PageCheckSeconds;
      }
   }

   public boolean isPageCheckSecondsInherited() {
      return false;
   }

   public boolean isPageCheckSecondsSet() {
      return this._isSet(3);
   }

   public void setPageCheckSeconds(int param0) {
      int _oldVal = this._PageCheckSeconds;
      this._PageCheckSeconds = param0;
      this._postSet(3, _oldVal, param0);
   }

   public boolean isPrecompile() {
      return this._Precompile;
   }

   public boolean isPrecompileInherited() {
      return false;
   }

   public boolean isPrecompileSet() {
      return this._isSet(4);
   }

   public void setPrecompile(boolean param0) {
      boolean _oldVal = this._Precompile;
      this._Precompile = param0;
      this._postSet(4, _oldVal, param0);
   }

   public boolean isPrecompileContinue() {
      return this._PrecompileContinue;
   }

   public boolean isPrecompileContinueInherited() {
      return false;
   }

   public boolean isPrecompileContinueSet() {
      return this._isSet(5);
   }

   public void setPrecompileContinue(boolean param0) {
      boolean _oldVal = this._PrecompileContinue;
      this._PrecompileContinue = param0;
      this._postSet(5, _oldVal, param0);
   }

   public boolean isVerbose() {
      if (!this._isSet(6)) {
         return !this._isProductionModeEnabled();
      } else {
         return this._Verbose;
      }
   }

   public boolean isVerboseInherited() {
      return false;
   }

   public boolean isVerboseSet() {
      return this._isSet(6);
   }

   public void setVerbose(boolean param0) {
      boolean _oldVal = this._Verbose;
      this._Verbose = param0;
      this._postSet(6, _oldVal, param0);
   }

   public String getWorkingDir() {
      return this._WorkingDir;
   }

   public boolean isWorkingDirInherited() {
      return false;
   }

   public boolean isWorkingDirSet() {
      return this._isSet(7);
   }

   public void setWorkingDir(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._WorkingDir;
      this._WorkingDir = param0;
      this._postSet(7, _oldVal, param0);
   }

   public boolean isPrintNulls() {
      return this._PrintNulls;
   }

   public boolean isPrintNullsInherited() {
      return false;
   }

   public boolean isPrintNullsSet() {
      return this._isSet(8);
   }

   public void setPrintNulls(boolean param0) {
      boolean _oldVal = this._PrintNulls;
      this._PrintNulls = param0;
      this._postSet(8, _oldVal, param0);
   }

   public boolean isBackwardCompatible() {
      return this._BackwardCompatible;
   }

   public boolean isBackwardCompatibleInherited() {
      return false;
   }

   public boolean isBackwardCompatibleSet() {
      return this._isSet(9);
   }

   public void setBackwardCompatible(boolean param0) {
      boolean _oldVal = this._BackwardCompatible;
      this._BackwardCompatible = param0;
      this._postSet(9, _oldVal, param0);
   }

   public String getEncoding() {
      return this._Encoding;
   }

   public boolean isEncodingInherited() {
      return false;
   }

   public boolean isEncodingSet() {
      return this._isSet(10);
   }

   public void setEncoding(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Encoding;
      this._Encoding = param0;
      this._postSet(10, _oldVal, param0);
   }

   public boolean isExactMapping() {
      return this._ExactMapping;
   }

   public boolean isExactMappingInherited() {
      return false;
   }

   public boolean isExactMappingSet() {
      return this._isSet(11);
   }

   public void setExactMapping(boolean param0) {
      boolean _oldVal = this._ExactMapping;
      this._ExactMapping = param0;
      this._postSet(11, _oldVal, param0);
   }

   public String getDefaultFileName() {
      return this._DefaultFileName;
   }

   public boolean isDefaultFileNameInherited() {
      return false;
   }

   public boolean isDefaultFileNameSet() {
      return this._isSet(12);
   }

   public void setDefaultFileName(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._DefaultFileName;
      this._DefaultFileName = param0;
      this._postSet(12, _oldVal, param0);
   }

   public boolean isRtexprvalueJspParamName() {
      return this._RtexprvalueJspParamName;
   }

   public boolean isRtexprvalueJspParamNameInherited() {
      return false;
   }

   public boolean isRtexprvalueJspParamNameSet() {
      return this._isSet(13);
   }

   public void setRtexprvalueJspParamName(boolean param0) {
      boolean _oldVal = this._RtexprvalueJspParamName;
      this._RtexprvalueJspParamName = param0;
      this._postSet(13, _oldVal, param0);
   }

   public boolean isDebug() {
      return this._Debug;
   }

   public boolean isDebugInherited() {
      return false;
   }

   public boolean isDebugSet() {
      return this._isSet(14);
   }

   public void setDebug(boolean param0) {
      boolean _oldVal = this._Debug;
      this._Debug = param0;
      this._postSet(14, _oldVal, param0);
   }

   public String getId() {
      return this._Id;
   }

   public boolean isIdInherited() {
      return false;
   }

   public boolean isIdSet() {
      return this._isSet(15);
   }

   public void setId(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._Id;
      this._Id = param0;
      this._postSet(15, _oldVal, param0);
   }

   public boolean isCompressHtmlTemplate() {
      return this._CompressHtmlTemplate;
   }

   public boolean isCompressHtmlTemplateInherited() {
      return false;
   }

   public boolean isCompressHtmlTemplateSet() {
      return this._isSet(16);
   }

   public void setCompressHtmlTemplate(boolean param0) {
      boolean _oldVal = this._CompressHtmlTemplate;
      this._CompressHtmlTemplate = param0;
      this._postSet(16, _oldVal, param0);
   }

   public boolean isOptimizeJavaExpression() {
      return this._OptimizeJavaExpression;
   }

   public boolean isOptimizeJavaExpressionInherited() {
      return false;
   }

   public boolean isOptimizeJavaExpressionSet() {
      return this._isSet(17);
   }

   public void setOptimizeJavaExpression(boolean param0) {
      boolean _oldVal = this._OptimizeJavaExpression;
      this._OptimizeJavaExpression = param0;
      this._postSet(17, _oldVal, param0);
   }

   public String getResourceProviderClass() {
      return this._ResourceProviderClass;
   }

   public boolean isResourceProviderClassInherited() {
      return false;
   }

   public boolean isResourceProviderClassSet() {
      return this._isSet(18);
   }

   public void setResourceProviderClass(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ResourceProviderClass;
      this._ResourceProviderClass = param0;
      this._postSet(18, _oldVal, param0);
   }

   public boolean isStrictStaleCheck() {
      return this._StrictStaleCheck;
   }

   public boolean isStrictStaleCheckInherited() {
      return false;
   }

   public boolean isStrictStaleCheckSet() {
      return this._isSet(19);
   }

   public void setStrictStaleCheck(boolean param0) {
      boolean _oldVal = this._StrictStaleCheck;
      this._StrictStaleCheck = param0;
      this._postSet(19, _oldVal, param0);
   }

   public boolean isStrictJspDocumentValidation() {
      return this._StrictJspDocumentValidation;
   }

   public boolean isStrictJspDocumentValidationInherited() {
      return false;
   }

   public boolean isStrictJspDocumentValidationSet() {
      return this._isSet(20);
   }

   public void setStrictJspDocumentValidation(boolean param0) {
      boolean _oldVal = this._StrictJspDocumentValidation;
      this._StrictJspDocumentValidation = param0;
      this._postSet(20, _oldVal, param0);
   }

   public String getExpressionInterceptor() {
      return this._ExpressionInterceptor;
   }

   public boolean isExpressionInterceptorInherited() {
      return false;
   }

   public boolean isExpressionInterceptorSet() {
      return this._isSet(21);
   }

   public void setExpressionInterceptor(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._ExpressionInterceptor;
      this._ExpressionInterceptor = param0;
      this._postSet(21, _oldVal, param0);
   }

   public boolean isEL22BackwardCompatible() {
      return this._EL22BackwardCompatible;
   }

   public boolean isEL22BackwardCompatibleInherited() {
      return false;
   }

   public boolean isEL22BackwardCompatibleSet() {
      return this._isSet(22);
   }

   public void setEL22BackwardCompatible(boolean param0) {
      boolean _oldVal = this._EL22BackwardCompatible;
      this._EL22BackwardCompatible = param0;
      this._postSet(22, _oldVal, param0);
   }

   public String getCompilerSourceVM() {
      return this._CompilerSourceVM;
   }

   public boolean isCompilerSourceVMInherited() {
      return false;
   }

   public boolean isCompilerSourceVMSet() {
      return this._isSet(23);
   }

   public void setCompilerSourceVM(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompilerSourceVM;
      this._CompilerSourceVM = param0;
      this._postSet(23, _oldVal, param0);
   }

   public String getCompilerTargetVM() {
      return this._CompilerTargetVM;
   }

   public boolean isCompilerTargetVMInherited() {
      return false;
   }

   public boolean isCompilerTargetVMSet() {
      return this._isSet(24);
   }

   public void setCompilerTargetVM(String param0) {
      param0 = param0 == null ? null : param0.trim();
      String _oldVal = this._CompilerTargetVM;
      this._CompilerTargetVM = param0;
      this._postSet(24, _oldVal, param0);
   }

   public Object _getKey() {
      return super._getKey();
   }

   public void _validate() throws IllegalArgumentException {
      super._validate();
   }

   protected void _unSet(int idx) {
      if (!this._initializeProperty(idx)) {
         super._unSet(idx);
      } else {
         this._markSet(idx, false);
      }

   }

   protected AbstractDescriptorBeanHelper _createHelper() {
      return new Helper(this);
   }

   public boolean _isAnythingSet() {
      return super._isAnythingSet();
   }

   private boolean _initializeProperty(int idx) {
      boolean initOne = idx > -1;
      if (!initOne) {
         idx = 23;
      }

      try {
         switch (idx) {
            case 23:
               this._CompilerSourceVM = null;
               if (initOne) {
                  break;
               }
            case 24:
               this._CompilerTargetVM = null;
               if (initOne) {
                  break;
               }
            case 12:
               this._DefaultFileName = null;
               if (initOne) {
                  break;
               }
            case 10:
               this._Encoding = null;
               if (initOne) {
                  break;
               }
            case 21:
               this._ExpressionInterceptor = null;
               if (initOne) {
                  break;
               }
            case 15:
               this._Id = null;
               if (initOne) {
                  break;
               }
            case 1:
               this._PackagePrefix = null;
               if (initOne) {
                  break;
               }
            case 3:
               this._PageCheckSeconds = 1;
               if (initOne) {
                  break;
               }
            case 18:
               this._ResourceProviderClass = null;
               if (initOne) {
                  break;
               }
            case 2:
               this._SuperClass = null;
               if (initOne) {
                  break;
               }
            case 7:
               this._WorkingDir = null;
               if (initOne) {
                  break;
               }
            case 9:
               this._BackwardCompatible = false;
               if (initOne) {
                  break;
               }
            case 16:
               this._CompressHtmlTemplate = false;
               if (initOne) {
                  break;
               }
            case 14:
               this._Debug = false;
               if (initOne) {
                  break;
               }
            case 22:
               this._EL22BackwardCompatible = false;
               if (initOne) {
                  break;
               }
            case 11:
               this._ExactMapping = true;
               if (initOne) {
                  break;
               }
            case 0:
               this._Keepgenerated = false;
               if (initOne) {
                  break;
               }
            case 17:
               this._OptimizeJavaExpression = false;
               if (initOne) {
                  break;
               }
            case 4:
               this._Precompile = false;
               if (initOne) {
                  break;
               }
            case 5:
               this._PrecompileContinue = false;
               if (initOne) {
                  break;
               }
            case 8:
               this._PrintNulls = true;
               if (initOne) {
                  break;
               }
            case 13:
               this._RtexprvalueJspParamName = false;
               if (initOne) {
                  break;
               }
            case 20:
               this._StrictJspDocumentValidation = false;
               if (initOne) {
                  break;
               }
            case 19:
               this._StrictStaleCheck = true;
               if (initOne) {
                  break;
               }
            case 6:
               this._Verbose = true;
               if (initOne) {
                  break;
               }
            default:
               if (initOne) {
                  return false;
               }
         }

         return true;
      } catch (RuntimeException var4) {
         throw var4;
      } catch (Exception var5) {
         throw (Error)(new AssertionError("Impossible Exception")).initCause(var5);
      }
   }

   public Munger.SchemaHelper _getSchemaHelper() {
      return null;
   }

   public String _getElementName(int propIndex) {
      return this._getSchemaHelper2().getElementName(propIndex);
   }

   public SchemaHelper _getSchemaHelper2() {
      if (_schemaHelper == null) {
         _schemaHelper = new SchemaHelper2();
      }

      return _schemaHelper;
   }

   public static class SchemaHelper2 extends AbstractSchemaHelper2 implements SchemaHelper {
      public int getPropertyIndex(String s) {
         switch (s.length()) {
            case 2:
               if (s.equals("id")) {
                  return 15;
               }
            case 3:
            case 4:
            case 6:
            case 9:
            case 12:
            case 15:
            case 16:
            case 20:
            case 21:
            case 25:
            case 27:
            case 28:
            case 29:
            default:
               break;
            case 5:
               if (s.equals("debug")) {
                  return 14;
               }
               break;
            case 7:
               if (s.equals("verbose")) {
                  return 6;
               }
               break;
            case 8:
               if (s.equals("encoding")) {
                  return 10;
               }
               break;
            case 10:
               if (s.equals("precompile")) {
                  return 4;
               }
               break;
            case 11:
               if (s.equals("super-class")) {
                  return 2;
               }

               if (s.equals("working-dir")) {
                  return 7;
               }

               if (s.equals("print-nulls")) {
                  return 8;
               }
               break;
            case 13:
               if (s.equals("exact-mapping")) {
                  return 11;
               }

               if (s.equals("keepgenerated")) {
                  return 0;
               }
               break;
            case 14:
               if (s.equals("package-prefix")) {
                  return 1;
               }
               break;
            case 17:
               if (s.equals("default-file-name")) {
                  return 12;
               }
               break;
            case 18:
               if (s.equals("compiler-source-vm")) {
                  return 23;
               }

               if (s.equals("compiler-target-vm")) {
                  return 24;
               }

               if (s.equals("page-check-seconds")) {
                  return 3;
               }

               if (s.equals("strict-stale-check")) {
                  return 19;
               }
               break;
            case 19:
               if (s.equals("backward-compatible")) {
                  return 9;
               }

               if (s.equals("precompile-continue")) {
                  return 5;
               }
               break;
            case 22:
               if (s.equals("expression-interceptor")) {
                  return 21;
               }

               if (s.equals("compress-html-template")) {
                  return 16;
               }
               break;
            case 23:
               if (s.equals("resource-provider-class")) {
                  return 18;
               }
               break;
            case 24:
               if (s.equals("optimize-java-expression")) {
                  return 17;
               }
               break;
            case 26:
               if (s.equals("el-2.2-backward-compatible")) {
                  return 22;
               }

               if (s.equals("rtexprvalue-jsp-param-name")) {
                  return 13;
               }
               break;
            case 30:
               if (s.equals("strict-jsp-document-validation")) {
                  return 20;
               }
         }

         return super.getPropertyIndex(s);
      }

      public SchemaHelper getSchemaHelper(int propIndex) {
         switch (propIndex) {
            default:
               return super.getSchemaHelper(propIndex);
         }
      }

      public String getElementName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "keepgenerated";
            case 1:
               return "package-prefix";
            case 2:
               return "super-class";
            case 3:
               return "page-check-seconds";
            case 4:
               return "precompile";
            case 5:
               return "precompile-continue";
            case 6:
               return "verbose";
            case 7:
               return "working-dir";
            case 8:
               return "print-nulls";
            case 9:
               return "backward-compatible";
            case 10:
               return "encoding";
            case 11:
               return "exact-mapping";
            case 12:
               return "default-file-name";
            case 13:
               return "rtexprvalue-jsp-param-name";
            case 14:
               return "debug";
            case 15:
               return "id";
            case 16:
               return "compress-html-template";
            case 17:
               return "optimize-java-expression";
            case 18:
               return "resource-provider-class";
            case 19:
               return "strict-stale-check";
            case 20:
               return "strict-jsp-document-validation";
            case 21:
               return "expression-interceptor";
            case 22:
               return "el-2.2-backward-compatible";
            case 23:
               return "compiler-source-vm";
            case 24:
               return "compiler-target-vm";
            default:
               return super.getElementName(propIndex);
         }
      }

      public boolean isConfigurable(int propIndex) {
         switch (propIndex) {
            case 0:
               return true;
            case 3:
               return true;
            case 6:
               return true;
            default:
               return super.isConfigurable(propIndex);
         }
      }
   }

   protected static class Helper extends AbstractDescriptorBeanHelper {
      private JspDescriptorBeanImpl bean;

      protected Helper(JspDescriptorBeanImpl bean) {
         super(bean);
         this.bean = bean;
      }

      public String getPropertyName(int propIndex) {
         switch (propIndex) {
            case 0:
               return "Keepgenerated";
            case 1:
               return "PackagePrefix";
            case 2:
               return "SuperClass";
            case 3:
               return "PageCheckSeconds";
            case 4:
               return "Precompile";
            case 5:
               return "PrecompileContinue";
            case 6:
               return "Verbose";
            case 7:
               return "WorkingDir";
            case 8:
               return "PrintNulls";
            case 9:
               return "BackwardCompatible";
            case 10:
               return "Encoding";
            case 11:
               return "ExactMapping";
            case 12:
               return "DefaultFileName";
            case 13:
               return "RtexprvalueJspParamName";
            case 14:
               return "Debug";
            case 15:
               return "Id";
            case 16:
               return "CompressHtmlTemplate";
            case 17:
               return "OptimizeJavaExpression";
            case 18:
               return "ResourceProviderClass";
            case 19:
               return "StrictStaleCheck";
            case 20:
               return "StrictJspDocumentValidation";
            case 21:
               return "ExpressionInterceptor";
            case 22:
               return "EL22BackwardCompatible";
            case 23:
               return "CompilerSourceVM";
            case 24:
               return "CompilerTargetVM";
            default:
               return super.getPropertyName(propIndex);
         }
      }

      public int getPropertyIndex(String propName) {
         if (propName.equals("CompilerSourceVM")) {
            return 23;
         } else if (propName.equals("CompilerTargetVM")) {
            return 24;
         } else if (propName.equals("DefaultFileName")) {
            return 12;
         } else if (propName.equals("Encoding")) {
            return 10;
         } else if (propName.equals("ExpressionInterceptor")) {
            return 21;
         } else if (propName.equals("Id")) {
            return 15;
         } else if (propName.equals("PackagePrefix")) {
            return 1;
         } else if (propName.equals("PageCheckSeconds")) {
            return 3;
         } else if (propName.equals("ResourceProviderClass")) {
            return 18;
         } else if (propName.equals("SuperClass")) {
            return 2;
         } else if (propName.equals("WorkingDir")) {
            return 7;
         } else if (propName.equals("BackwardCompatible")) {
            return 9;
         } else if (propName.equals("CompressHtmlTemplate")) {
            return 16;
         } else if (propName.equals("Debug")) {
            return 14;
         } else if (propName.equals("EL22BackwardCompatible")) {
            return 22;
         } else if (propName.equals("ExactMapping")) {
            return 11;
         } else if (propName.equals("Keepgenerated")) {
            return 0;
         } else if (propName.equals("OptimizeJavaExpression")) {
            return 17;
         } else if (propName.equals("Precompile")) {
            return 4;
         } else if (propName.equals("PrecompileContinue")) {
            return 5;
         } else if (propName.equals("PrintNulls")) {
            return 8;
         } else if (propName.equals("RtexprvalueJspParamName")) {
            return 13;
         } else if (propName.equals("StrictJspDocumentValidation")) {
            return 20;
         } else if (propName.equals("StrictStaleCheck")) {
            return 19;
         } else {
            return propName.equals("Verbose") ? 6 : super.getPropertyIndex(propName);
         }
      }

      public Iterator getChildren() {
         List iterators = new ArrayList();
         return new CombinedIterator(iterators);
      }

      protected long computeHashValue(CRC32 crc) {
         try {
            StringBuffer buf = new StringBuffer();
            long superValue = super.computeHashValue(crc);
            if (superValue != 0L) {
               buf.append(String.valueOf(superValue));
            }

            long childValue = 0L;
            if (this.bean.isCompilerSourceVMSet()) {
               buf.append("CompilerSourceVM");
               buf.append(String.valueOf(this.bean.getCompilerSourceVM()));
            }

            if (this.bean.isCompilerTargetVMSet()) {
               buf.append("CompilerTargetVM");
               buf.append(String.valueOf(this.bean.getCompilerTargetVM()));
            }

            if (this.bean.isDefaultFileNameSet()) {
               buf.append("DefaultFileName");
               buf.append(String.valueOf(this.bean.getDefaultFileName()));
            }

            if (this.bean.isEncodingSet()) {
               buf.append("Encoding");
               buf.append(String.valueOf(this.bean.getEncoding()));
            }

            if (this.bean.isExpressionInterceptorSet()) {
               buf.append("ExpressionInterceptor");
               buf.append(String.valueOf(this.bean.getExpressionInterceptor()));
            }

            if (this.bean.isIdSet()) {
               buf.append("Id");
               buf.append(String.valueOf(this.bean.getId()));
            }

            if (this.bean.isPackagePrefixSet()) {
               buf.append("PackagePrefix");
               buf.append(String.valueOf(this.bean.getPackagePrefix()));
            }

            if (this.bean.isPageCheckSecondsSet()) {
               buf.append("PageCheckSeconds");
               buf.append(String.valueOf(this.bean.getPageCheckSeconds()));
            }

            if (this.bean.isResourceProviderClassSet()) {
               buf.append("ResourceProviderClass");
               buf.append(String.valueOf(this.bean.getResourceProviderClass()));
            }

            if (this.bean.isSuperClassSet()) {
               buf.append("SuperClass");
               buf.append(String.valueOf(this.bean.getSuperClass()));
            }

            if (this.bean.isWorkingDirSet()) {
               buf.append("WorkingDir");
               buf.append(String.valueOf(this.bean.getWorkingDir()));
            }

            if (this.bean.isBackwardCompatibleSet()) {
               buf.append("BackwardCompatible");
               buf.append(String.valueOf(this.bean.isBackwardCompatible()));
            }

            if (this.bean.isCompressHtmlTemplateSet()) {
               buf.append("CompressHtmlTemplate");
               buf.append(String.valueOf(this.bean.isCompressHtmlTemplate()));
            }

            if (this.bean.isDebugSet()) {
               buf.append("Debug");
               buf.append(String.valueOf(this.bean.isDebug()));
            }

            if (this.bean.isEL22BackwardCompatibleSet()) {
               buf.append("EL22BackwardCompatible");
               buf.append(String.valueOf(this.bean.isEL22BackwardCompatible()));
            }

            if (this.bean.isExactMappingSet()) {
               buf.append("ExactMapping");
               buf.append(String.valueOf(this.bean.isExactMapping()));
            }

            if (this.bean.isKeepgeneratedSet()) {
               buf.append("Keepgenerated");
               buf.append(String.valueOf(this.bean.isKeepgenerated()));
            }

            if (this.bean.isOptimizeJavaExpressionSet()) {
               buf.append("OptimizeJavaExpression");
               buf.append(String.valueOf(this.bean.isOptimizeJavaExpression()));
            }

            if (this.bean.isPrecompileSet()) {
               buf.append("Precompile");
               buf.append(String.valueOf(this.bean.isPrecompile()));
            }

            if (this.bean.isPrecompileContinueSet()) {
               buf.append("PrecompileContinue");
               buf.append(String.valueOf(this.bean.isPrecompileContinue()));
            }

            if (this.bean.isPrintNullsSet()) {
               buf.append("PrintNulls");
               buf.append(String.valueOf(this.bean.isPrintNulls()));
            }

            if (this.bean.isRtexprvalueJspParamNameSet()) {
               buf.append("RtexprvalueJspParamName");
               buf.append(String.valueOf(this.bean.isRtexprvalueJspParamName()));
            }

            if (this.bean.isStrictJspDocumentValidationSet()) {
               buf.append("StrictJspDocumentValidation");
               buf.append(String.valueOf(this.bean.isStrictJspDocumentValidation()));
            }

            if (this.bean.isStrictStaleCheckSet()) {
               buf.append("StrictStaleCheck");
               buf.append(String.valueOf(this.bean.isStrictStaleCheck()));
            }

            if (this.bean.isVerboseSet()) {
               buf.append("Verbose");
               buf.append(String.valueOf(this.bean.isVerbose()));
            }

            crc.update(buf.toString().getBytes());
            return crc.getValue();
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void computeDiff(AbstractDescriptorBean other) {
         try {
            super.computeDiff(other);
            JspDescriptorBeanImpl otherTyped = (JspDescriptorBeanImpl)other;
            this.computeDiff("CompilerSourceVM", this.bean.getCompilerSourceVM(), otherTyped.getCompilerSourceVM(), false);
            this.computeDiff("CompilerTargetVM", this.bean.getCompilerTargetVM(), otherTyped.getCompilerTargetVM(), false);
            this.computeDiff("DefaultFileName", this.bean.getDefaultFileName(), otherTyped.getDefaultFileName(), false);
            this.computeDiff("Encoding", this.bean.getEncoding(), otherTyped.getEncoding(), false);
            this.computeDiff("ExpressionInterceptor", this.bean.getExpressionInterceptor(), otherTyped.getExpressionInterceptor(), false);
            this.computeDiff("Id", this.bean.getId(), otherTyped.getId(), false);
            this.computeDiff("PackagePrefix", this.bean.getPackagePrefix(), otherTyped.getPackagePrefix(), false);
            this.computeDiff("PageCheckSeconds", this.bean.getPageCheckSeconds(), otherTyped.getPageCheckSeconds(), true);
            this.computeDiff("ResourceProviderClass", this.bean.getResourceProviderClass(), otherTyped.getResourceProviderClass(), false);
            this.computeDiff("SuperClass", this.bean.getSuperClass(), otherTyped.getSuperClass(), false);
            this.computeDiff("WorkingDir", this.bean.getWorkingDir(), otherTyped.getWorkingDir(), false);
            this.computeDiff("BackwardCompatible", this.bean.isBackwardCompatible(), otherTyped.isBackwardCompatible(), false);
            this.computeDiff("CompressHtmlTemplate", this.bean.isCompressHtmlTemplate(), otherTyped.isCompressHtmlTemplate(), false);
            this.computeDiff("Debug", this.bean.isDebug(), otherTyped.isDebug(), false);
            this.computeDiff("EL22BackwardCompatible", this.bean.isEL22BackwardCompatible(), otherTyped.isEL22BackwardCompatible(), false);
            this.computeDiff("ExactMapping", this.bean.isExactMapping(), otherTyped.isExactMapping(), false);
            this.computeDiff("Keepgenerated", this.bean.isKeepgenerated(), otherTyped.isKeepgenerated(), true);
            this.computeDiff("OptimizeJavaExpression", this.bean.isOptimizeJavaExpression(), otherTyped.isOptimizeJavaExpression(), false);
            this.computeDiff("Precompile", this.bean.isPrecompile(), otherTyped.isPrecompile(), false);
            this.computeDiff("PrecompileContinue", this.bean.isPrecompileContinue(), otherTyped.isPrecompileContinue(), false);
            this.computeDiff("PrintNulls", this.bean.isPrintNulls(), otherTyped.isPrintNulls(), false);
            this.computeDiff("RtexprvalueJspParamName", this.bean.isRtexprvalueJspParamName(), otherTyped.isRtexprvalueJspParamName(), false);
            this.computeDiff("StrictJspDocumentValidation", this.bean.isStrictJspDocumentValidation(), otherTyped.isStrictJspDocumentValidation(), false);
            this.computeDiff("StrictStaleCheck", this.bean.isStrictStaleCheck(), otherTyped.isStrictStaleCheck(), false);
            this.computeDiff("Verbose", this.bean.isVerbose(), otherTyped.isVerbose(), true);
         } catch (Exception var3) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var3);
         }
      }

      protected void applyPropertyUpdate(BeanUpdateEvent event, BeanUpdateEvent.PropertyUpdate update) {
         try {
            JspDescriptorBeanImpl original = (JspDescriptorBeanImpl)event.getSourceBean();
            JspDescriptorBeanImpl proposed = (JspDescriptorBeanImpl)event.getProposedBean();
            String prop = update.getPropertyName();
            int type = update.getUpdateType();
            if (!update.isDerivedUpdate()) {
               if (prop.equals("CompilerSourceVM")) {
                  original.setCompilerSourceVM(proposed.getCompilerSourceVM());
                  original._conditionalUnset(update.isUnsetUpdate(), 23);
               } else if (prop.equals("CompilerTargetVM")) {
                  original.setCompilerTargetVM(proposed.getCompilerTargetVM());
                  original._conditionalUnset(update.isUnsetUpdate(), 24);
               } else if (prop.equals("DefaultFileName")) {
                  original.setDefaultFileName(proposed.getDefaultFileName());
                  original._conditionalUnset(update.isUnsetUpdate(), 12);
               } else if (prop.equals("Encoding")) {
                  original.setEncoding(proposed.getEncoding());
                  original._conditionalUnset(update.isUnsetUpdate(), 10);
               } else if (prop.equals("ExpressionInterceptor")) {
                  original.setExpressionInterceptor(proposed.getExpressionInterceptor());
                  original._conditionalUnset(update.isUnsetUpdate(), 21);
               } else if (prop.equals("Id")) {
                  original.setId(proposed.getId());
                  original._conditionalUnset(update.isUnsetUpdate(), 15);
               } else if (prop.equals("PackagePrefix")) {
                  original.setPackagePrefix(proposed.getPackagePrefix());
                  original._conditionalUnset(update.isUnsetUpdate(), 1);
               } else if (prop.equals("PageCheckSeconds")) {
                  original.setPageCheckSeconds(proposed.getPageCheckSeconds());
                  original._conditionalUnset(update.isUnsetUpdate(), 3);
               } else if (prop.equals("ResourceProviderClass")) {
                  original.setResourceProviderClass(proposed.getResourceProviderClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 18);
               } else if (prop.equals("SuperClass")) {
                  original.setSuperClass(proposed.getSuperClass());
                  original._conditionalUnset(update.isUnsetUpdate(), 2);
               } else if (prop.equals("WorkingDir")) {
                  original.setWorkingDir(proposed.getWorkingDir());
                  original._conditionalUnset(update.isUnsetUpdate(), 7);
               } else if (prop.equals("BackwardCompatible")) {
                  original.setBackwardCompatible(proposed.isBackwardCompatible());
                  original._conditionalUnset(update.isUnsetUpdate(), 9);
               } else if (prop.equals("CompressHtmlTemplate")) {
                  original.setCompressHtmlTemplate(proposed.isCompressHtmlTemplate());
                  original._conditionalUnset(update.isUnsetUpdate(), 16);
               } else if (prop.equals("Debug")) {
                  original.setDebug(proposed.isDebug());
                  original._conditionalUnset(update.isUnsetUpdate(), 14);
               } else if (prop.equals("EL22BackwardCompatible")) {
                  original.setEL22BackwardCompatible(proposed.isEL22BackwardCompatible());
                  original._conditionalUnset(update.isUnsetUpdate(), 22);
               } else if (prop.equals("ExactMapping")) {
                  original.setExactMapping(proposed.isExactMapping());
                  original._conditionalUnset(update.isUnsetUpdate(), 11);
               } else if (prop.equals("Keepgenerated")) {
                  original.setKeepgenerated(proposed.isKeepgenerated());
                  original._conditionalUnset(update.isUnsetUpdate(), 0);
               } else if (prop.equals("OptimizeJavaExpression")) {
                  original.setOptimizeJavaExpression(proposed.isOptimizeJavaExpression());
                  original._conditionalUnset(update.isUnsetUpdate(), 17);
               } else if (prop.equals("Precompile")) {
                  original.setPrecompile(proposed.isPrecompile());
                  original._conditionalUnset(update.isUnsetUpdate(), 4);
               } else if (prop.equals("PrecompileContinue")) {
                  original.setPrecompileContinue(proposed.isPrecompileContinue());
                  original._conditionalUnset(update.isUnsetUpdate(), 5);
               } else if (prop.equals("PrintNulls")) {
                  original.setPrintNulls(proposed.isPrintNulls());
                  original._conditionalUnset(update.isUnsetUpdate(), 8);
               } else if (prop.equals("RtexprvalueJspParamName")) {
                  original.setRtexprvalueJspParamName(proposed.isRtexprvalueJspParamName());
                  original._conditionalUnset(update.isUnsetUpdate(), 13);
               } else if (prop.equals("StrictJspDocumentValidation")) {
                  original.setStrictJspDocumentValidation(proposed.isStrictJspDocumentValidation());
                  original._conditionalUnset(update.isUnsetUpdate(), 20);
               } else if (prop.equals("StrictStaleCheck")) {
                  original.setStrictStaleCheck(proposed.isStrictStaleCheck());
                  original._conditionalUnset(update.isUnsetUpdate(), 19);
               } else if (prop.equals("Verbose")) {
                  original.setVerbose(proposed.isVerbose());
                  original._conditionalUnset(update.isUnsetUpdate(), 6);
               } else {
                  super.applyPropertyUpdate(event, update);
               }

            }
         } catch (RuntimeException var7) {
            throw var7;
         } catch (Exception var8) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var8);
         }
      }

      protected AbstractDescriptorBean finishCopy(AbstractDescriptorBean initialCopy, boolean includeObsolete, List excludeProps) {
         try {
            JspDescriptorBeanImpl copy = (JspDescriptorBeanImpl)initialCopy;
            super.finishCopy(copy, includeObsolete, excludeProps);
            if ((excludeProps == null || !excludeProps.contains("CompilerSourceVM")) && this.bean.isCompilerSourceVMSet()) {
               copy.setCompilerSourceVM(this.bean.getCompilerSourceVM());
            }

            if ((excludeProps == null || !excludeProps.contains("CompilerTargetVM")) && this.bean.isCompilerTargetVMSet()) {
               copy.setCompilerTargetVM(this.bean.getCompilerTargetVM());
            }

            if ((excludeProps == null || !excludeProps.contains("DefaultFileName")) && this.bean.isDefaultFileNameSet()) {
               copy.setDefaultFileName(this.bean.getDefaultFileName());
            }

            if ((excludeProps == null || !excludeProps.contains("Encoding")) && this.bean.isEncodingSet()) {
               copy.setEncoding(this.bean.getEncoding());
            }

            if ((excludeProps == null || !excludeProps.contains("ExpressionInterceptor")) && this.bean.isExpressionInterceptorSet()) {
               copy.setExpressionInterceptor(this.bean.getExpressionInterceptor());
            }

            if ((excludeProps == null || !excludeProps.contains("Id")) && this.bean.isIdSet()) {
               copy.setId(this.bean.getId());
            }

            if ((excludeProps == null || !excludeProps.contains("PackagePrefix")) && this.bean.isPackagePrefixSet()) {
               copy.setPackagePrefix(this.bean.getPackagePrefix());
            }

            if ((excludeProps == null || !excludeProps.contains("PageCheckSeconds")) && this.bean.isPageCheckSecondsSet()) {
               copy.setPageCheckSeconds(this.bean.getPageCheckSeconds());
            }

            if ((excludeProps == null || !excludeProps.contains("ResourceProviderClass")) && this.bean.isResourceProviderClassSet()) {
               copy.setResourceProviderClass(this.bean.getResourceProviderClass());
            }

            if ((excludeProps == null || !excludeProps.contains("SuperClass")) && this.bean.isSuperClassSet()) {
               copy.setSuperClass(this.bean.getSuperClass());
            }

            if ((excludeProps == null || !excludeProps.contains("WorkingDir")) && this.bean.isWorkingDirSet()) {
               copy.setWorkingDir(this.bean.getWorkingDir());
            }

            if ((excludeProps == null || !excludeProps.contains("BackwardCompatible")) && this.bean.isBackwardCompatibleSet()) {
               copy.setBackwardCompatible(this.bean.isBackwardCompatible());
            }

            if ((excludeProps == null || !excludeProps.contains("CompressHtmlTemplate")) && this.bean.isCompressHtmlTemplateSet()) {
               copy.setCompressHtmlTemplate(this.bean.isCompressHtmlTemplate());
            }

            if ((excludeProps == null || !excludeProps.contains("Debug")) && this.bean.isDebugSet()) {
               copy.setDebug(this.bean.isDebug());
            }

            if ((excludeProps == null || !excludeProps.contains("EL22BackwardCompatible")) && this.bean.isEL22BackwardCompatibleSet()) {
               copy.setEL22BackwardCompatible(this.bean.isEL22BackwardCompatible());
            }

            if ((excludeProps == null || !excludeProps.contains("ExactMapping")) && this.bean.isExactMappingSet()) {
               copy.setExactMapping(this.bean.isExactMapping());
            }

            if ((excludeProps == null || !excludeProps.contains("Keepgenerated")) && this.bean.isKeepgeneratedSet()) {
               copy.setKeepgenerated(this.bean.isKeepgenerated());
            }

            if ((excludeProps == null || !excludeProps.contains("OptimizeJavaExpression")) && this.bean.isOptimizeJavaExpressionSet()) {
               copy.setOptimizeJavaExpression(this.bean.isOptimizeJavaExpression());
            }

            if ((excludeProps == null || !excludeProps.contains("Precompile")) && this.bean.isPrecompileSet()) {
               copy.setPrecompile(this.bean.isPrecompile());
            }

            if ((excludeProps == null || !excludeProps.contains("PrecompileContinue")) && this.bean.isPrecompileContinueSet()) {
               copy.setPrecompileContinue(this.bean.isPrecompileContinue());
            }

            if ((excludeProps == null || !excludeProps.contains("PrintNulls")) && this.bean.isPrintNullsSet()) {
               copy.setPrintNulls(this.bean.isPrintNulls());
            }

            if ((excludeProps == null || !excludeProps.contains("RtexprvalueJspParamName")) && this.bean.isRtexprvalueJspParamNameSet()) {
               copy.setRtexprvalueJspParamName(this.bean.isRtexprvalueJspParamName());
            }

            if ((excludeProps == null || !excludeProps.contains("StrictJspDocumentValidation")) && this.bean.isStrictJspDocumentValidationSet()) {
               copy.setStrictJspDocumentValidation(this.bean.isStrictJspDocumentValidation());
            }

            if ((excludeProps == null || !excludeProps.contains("StrictStaleCheck")) && this.bean.isStrictStaleCheckSet()) {
               copy.setStrictStaleCheck(this.bean.isStrictStaleCheck());
            }

            if ((excludeProps == null || !excludeProps.contains("Verbose")) && this.bean.isVerboseSet()) {
               copy.setVerbose(this.bean.isVerbose());
            }

            return copy;
         } catch (RuntimeException var6) {
            throw var6;
         } catch (Exception var7) {
            throw (Error)(new AssertionError("Impossible Exception")).initCause(var7);
         }
      }

      protected void inferSubTree(Class clazz, Object annotation) {
         super.inferSubTree(clazz, annotation);
         Object currentAnnotation = null;
      }
   }
}
