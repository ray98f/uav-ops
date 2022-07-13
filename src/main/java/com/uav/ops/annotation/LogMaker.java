package com.uav.ops.annotation;

import java.lang.annotation.*;

/**
 * @author frp
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogMaker {
    String value() default "";
}
