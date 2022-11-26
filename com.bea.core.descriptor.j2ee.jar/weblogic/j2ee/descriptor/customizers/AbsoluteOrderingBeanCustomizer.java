package weblogic.j2ee.descriptor.customizers;

import com.bea.staxb.runtime.StaxDeSerializer;
import com.bea.staxb.runtime.StaxSerializer;
import weblogic.descriptor.beangen.Customizer;

public interface AbsoluteOrderingBeanCustomizer extends Customizer, StaxSerializer, StaxDeSerializer {
}
