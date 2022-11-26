package weblogic.servlet.jsp.dd;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import javax.servlet.descriptor.JspPropertyGroupDescriptor;
import weblogic.j2ee.descriptor.JspPropertyGroupBean;

public class JspPropertyGroupDescriptorImpl implements JspPropertyGroupDescriptor {
   private JspPropertyGroupBean bean;

   public JspPropertyGroupDescriptorImpl(JspPropertyGroupBean bean) {
      this.bean = bean;
   }

   public String getBuffer() {
      return this.bean.getBuffer();
   }

   public String getDefaultContentType() {
      return this.bean.getDefaultContentType();
   }

   public String getDeferredSyntaxAllowedAsLiteral() {
      return !this.bean.isDeferredSyntaxAllowedAsLiteralSet() ? null : Boolean.toString(this.bean.isDeferredSyntaxAllowedAsLiteral());
   }

   public String getElIgnored() {
      return !this.bean.isElIgnoredSet() ? null : Boolean.toString(this.bean.isElIgnored());
   }

   public String getErrorOnUndeclaredNamespace() {
      return !this.bean.isErrorOnUndeclaredNamespaceSet() ? null : Boolean.toString(this.bean.isErrorOnUndeclaredNamespace());
   }

   public Collection getIncludeCodas() {
      return arrayAsSafeList(this.bean.getIncludeCodas());
   }

   public Collection getIncludePreludes() {
      return arrayAsSafeList(this.bean.getIncludePreludes());
   }

   public String getIsXml() {
      return !this.bean.isIsXmlSet() ? null : Boolean.toString(this.bean.isIsXml());
   }

   public String getPageEncoding() {
      return this.bean.getPageEncoding();
   }

   public String getScriptingInvalid() {
      return !this.bean.isScriptingInvalidSet() ? null : Boolean.toString(this.bean.isScriptingInvalid());
   }

   public String getTrimDirectiveWhitespaces() {
      return !this.bean.isTrimDirectiveWhitespacesSet() ? null : Boolean.toString(this.bean.isTrimDirectiveWhitespaces());
   }

   public Collection getUrlPatterns() {
      return arrayAsSafeList(this.bean.getUrlPatterns());
   }

   private static Collection arrayAsSafeList(Object[] array) {
      return array != null && array.length != 0 ? Arrays.asList(array) : Collections.EMPTY_LIST;
   }
}
