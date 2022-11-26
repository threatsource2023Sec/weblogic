package weblogic.servlet.internal.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import weblogic.descriptor.BeanUpdateEvent;
import weblogic.descriptor.Descriptor;
import weblogic.descriptor.DescriptorBean;
import weblogic.descriptor.DescriptorDiff;
import weblogic.descriptor.internal.AbstractDescriptorBean;
import weblogic.descriptor.internal.DescriptorDiffImpl;
import weblogic.j2ee.descriptor.WebAppBean;
import weblogic.j2ee.descriptor.WebFragmentBean;

public class WebFragmentMergeDispatcher {
   private List mergers = new ArrayList();
   private WebAppBean workingWebAppBean;
   private WebAppBean baseWebAppBean;

   public WebFragmentMergeDispatcher(WebAppBean workingWebAppBean) {
      this.workingWebAppBean = workingWebAppBean;
      this.mergers.add(new SecurityConstraintsMerger());
      this.mergers.add(new RefMerger());
      this.mergers.add(new DistributablesMerger());
      this.mergers.add(new NullMerger());
      this.mergers.add(new InitParamMerger());
      this.mergers.add(new MappingMerger());
      this.mergers.add(new GeneralRuleMerger());
   }

   private void initBaseWebAppBean() {
      if (this.baseWebAppBean == null) {
         Descriptor clone = (Descriptor)((DescriptorBean)this.workingWebAppBean).getDescriptor().clone();
         this.baseWebAppBean = (WebAppBean)clone.getRootBean();
      }

   }

   private DescriptorBean findTargetBean(DescriptorBean proposedBean) {
      AbstractDescriptorBean rootBean = (AbstractDescriptorBean)this.workingWebAppBean;
      return rootBean.findByQualifiedName((AbstractDescriptorBean)proposedBean);
   }

   public void merge(WebFragmentBean webFragmentBean) throws MergeException {
      this.initBaseWebAppBean();
      DescriptorDiff diff = ((DescriptorBean)this.baseWebAppBean).getDescriptor().computeDiff(((DescriptorBean)webFragmentBean).getDescriptor());
      DescriptorDiffImpl diffImpl = (DescriptorDiffImpl)diff;
      Iterator it = diffImpl.iterator();

      while(it.hasNext()) {
         BeanUpdateEvent beanUpdate = (BeanUpdateEvent)it.next();
         DescriptorBean sourceBean = beanUpdate.getSourceBean();
         DescriptorBean proposedBean = beanUpdate.getProposedBean();
         DescriptorBean targetBean = this.findTargetBean(proposedBean);
         BeanUpdateEvent.PropertyUpdate[] var9 = beanUpdate.getUpdateList();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            BeanUpdateEvent.PropertyUpdate update = var9[var11];
            Merger merger = this.getMerger(proposedBean, update);
            merger.merge(targetBean, sourceBean, proposedBean, update);
         }
      }

   }

   private Merger getMerger(DescriptorBean bean, BeanUpdateEvent.PropertyUpdate update) {
      Iterator var3 = this.mergers.iterator();

      Merger m;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         m = (Merger)var3.next();
      } while(!m.accept(bean, update));

      return m;
   }
}
