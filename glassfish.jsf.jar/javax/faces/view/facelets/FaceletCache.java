package javax.faces.view.facelets;

import java.io.IOException;
import java.net.URL;

public abstract class FaceletCache {
   private MemberFactory memberFactory;
   private MemberFactory viewMetadataMemberFactory;

   public abstract Object getFacelet(URL var1) throws IOException;

   public abstract boolean isFaceletCached(URL var1);

   public abstract Object getViewMetadataFacelet(URL var1) throws IOException;

   public abstract boolean isViewMetadataFaceletCached(URL var1);

   public void setCacheFactories(MemberFactory faceletFactory, MemberFactory viewMetadataFaceletFactory) {
      this.setMemberFactories(faceletFactory, viewMetadataFaceletFactory);
   }

   /** @deprecated */
   @Deprecated
   protected void setMemberFactories(MemberFactory faceletFactory, MemberFactory viewMetadataFaceletFactory) {
      if (null != faceletFactory && null != viewMetadataFaceletFactory) {
         this.memberFactory = faceletFactory;
         this.viewMetadataMemberFactory = viewMetadataFaceletFactory;
      } else {
         throw new NullPointerException("Neither faceletFactory no viewMetadataFaceletFactory may be null.");
      }
   }

   protected MemberFactory getMemberFactory() {
      return this.memberFactory;
   }

   protected MemberFactory getMetadataMemberFactory() {
      return this.viewMetadataMemberFactory;
   }

   public interface MemberFactory {
      Object newInstance(URL var1) throws IOException;
   }
}
