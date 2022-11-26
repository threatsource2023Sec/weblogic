package weblogic.servlet.jsp.dd;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.servlet.descriptor.JspConfigDescriptor;
import weblogic.j2ee.descriptor.JspConfigBean;
import weblogic.j2ee.descriptor.JspPropertyGroupBean;
import weblogic.j2ee.descriptor.TagLibBean;

public class JspConfigDescriptorImpl implements JspConfigDescriptor {
   private JspConfigBean bean;

   public JspConfigDescriptorImpl(JspConfigBean bean) {
      this.bean = bean;
   }

   public Collection getJspPropertyGroups() {
      JspPropertyGroupBean[] jspPropertyGroups = this.bean.getJspPropertyGroups();
      if (jspPropertyGroups != null && jspPropertyGroups.length != 0) {
         List result = new ArrayList();
         JspPropertyGroupBean[] var3 = jspPropertyGroups;
         int var4 = jspPropertyGroups.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            JspPropertyGroupBean gBean = var3[var5];
            result.add(new JspPropertyGroupDescriptorImpl(gBean));
         }

         return result;
      } else {
         return Collections.EMPTY_LIST;
      }
   }

   public Collection getTaglibs() {
      TagLibBean[] tagLibs = this.bean.getTagLibs();
      if (tagLibs != null && tagLibs.length != 0) {
         List result = new ArrayList();
         TagLibBean[] var3 = tagLibs;
         int var4 = tagLibs.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            TagLibBean taglib = var3[var5];
            result.add(new TaglibDescriptorImpl(taglib));
         }

         return result;
      } else {
         return Collections.EMPTY_LIST;
      }
   }
}
