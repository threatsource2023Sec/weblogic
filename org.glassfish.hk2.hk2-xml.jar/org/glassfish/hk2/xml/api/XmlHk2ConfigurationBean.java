package org.glassfish.hk2.xml.api;

import java.util.Map;
import javax.xml.namespace.QName;
import org.glassfish.hk2.api.ActiveDescriptor;
import org.glassfish.hk2.xml.internal.ModelImpl;
import org.jvnet.hk2.annotations.Contract;

@Contract
public interface XmlHk2ConfigurationBean {
   Map _getBeanLikeMap();

   XmlHk2ConfigurationBean _getParent();

   String _getXmlPath();

   String _getInstanceName();

   QName _getKeyPropertyName();

   String _getKeyValue();

   ModelImpl _getModel();

   ActiveDescriptor _getSelfDescriptor();

   Object _lookupChild(String var1, String var2);

   Object _lookupChild(String var1, String var2, String var3);

   boolean _isSet(String var1);

   boolean _isSet(String var1, String var2);

   Object _getProperty(String var1);

   Object _getProperty(String var1, String var2);

   void _setProperty(String var1, Object var2);

   void _setProperty(String var1, String var2, Object var3);

   XmlRootHandle _getRoot();
}
