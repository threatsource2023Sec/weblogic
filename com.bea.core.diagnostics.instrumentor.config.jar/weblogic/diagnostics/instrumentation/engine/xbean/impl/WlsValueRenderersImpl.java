package weblogic.diagnostics.instrumentation.engine.xbean.impl;

import com.bea.xbean.values.XmlComplexContentImpl;
import com.bea.xml.SchemaType;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsRenderer;
import weblogic.diagnostics.instrumentation.engine.xbean.WlsValueRenderers;

public class WlsValueRenderersImpl extends XmlComplexContentImpl implements WlsValueRenderers {
   private static final long serialVersionUID = 1L;
   private static final QName WLSRENDERER$0 = new QName("http://weblogic/diagnostics/instrumentation/engine/xbean", "wls-renderer");

   public WlsValueRenderersImpl(SchemaType sType) {
      super(sType);
   }

   public WlsRenderer[] getWlsRendererArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         List targetList = new ArrayList();
         this.get_store().find_all_element_users(WLSRENDERER$0, targetList);
         WlsRenderer[] result = new WlsRenderer[targetList.size()];
         targetList.toArray(result);
         return result;
      }
   }

   public WlsRenderer getWlsRendererArray(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsRenderer target = null;
         target = (WlsRenderer)this.get_store().find_element_user(WLSRENDERER$0, i);
         if (target == null) {
            throw new IndexOutOfBoundsException();
         } else {
            return target;
         }
      }
   }

   public int sizeOfWlsRendererArray() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         return this.get_store().count_elements(WLSRENDERER$0);
      }
   }

   public void setWlsRendererArray(WlsRenderer[] wlsRendererArray) {
      this.check_orphaned();
      this.arraySetterHelper(wlsRendererArray, WLSRENDERER$0);
   }

   public void setWlsRendererArray(int i, WlsRenderer wlsRenderer) {
      this.generatedSetterHelperImpl(wlsRenderer, WLSRENDERER$0, i, (short)2);
   }

   public WlsRenderer insertNewWlsRenderer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsRenderer target = null;
         target = (WlsRenderer)this.get_store().insert_element_user(WLSRENDERER$0, i);
         return target;
      }
   }

   public WlsRenderer addNewWlsRenderer() {
      synchronized(this.monitor()) {
         this.check_orphaned();
         WlsRenderer target = null;
         target = (WlsRenderer)this.get_store().add_element_user(WLSRENDERER$0);
         return target;
      }
   }

   public void removeWlsRenderer(int i) {
      synchronized(this.monitor()) {
         this.check_orphaned();
         this.get_store().remove_element(WLSRENDERER$0, i);
      }
   }
}
