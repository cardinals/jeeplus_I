package com.jeeplus.modules.interfaces.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Json {
	Class<?> type() default Json.class;
	String include() default "";
	String filter() default "";
}