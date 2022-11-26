package org.hibernate.validator.path;

import javax.validation.Path;

public interface ContainerElementNode extends Path.ContainerElementNode {
   Object getValue();
}
