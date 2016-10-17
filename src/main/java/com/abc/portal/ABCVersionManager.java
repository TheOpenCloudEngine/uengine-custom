package com.abc.portal;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.uengine.modeling.resource.PackageVersionManager;

/**
 * Created by jjy on 2016. 10. 17..
 */
@Component
@Scope("prototype")
public class ABCVersionManager extends PackageVersionManager{
}
