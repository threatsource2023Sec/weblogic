package javax.faces.view;

import java.util.List;
import javax.faces.component.UIComponent;

public interface AttachedObjectTarget {
   String ATTACHED_OBJECT_TARGETS_KEY = "javax.faces.view.AttachedObjectTargets";

   List getTargets(UIComponent var1);

   String getName();
}
