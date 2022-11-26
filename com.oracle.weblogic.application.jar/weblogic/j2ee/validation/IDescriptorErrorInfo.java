package weblogic.j2ee.validation;

import java.io.Serializable;

public interface IDescriptorErrorInfo extends Serializable {
   Object getTopLevelSearchKey();

   Object[] getElementErrorKeys();

   String[] getElementTypes();
}
