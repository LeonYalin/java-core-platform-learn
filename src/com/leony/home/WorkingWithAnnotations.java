package com.leony.home;

import java.lang.annotation.Annotation;

/**
 * Created by leony on 25/09/2018.
 */
@DoesMagic(go = true)
public class WorkingWithAnnotations {

    @DoesMagic("Lalala")
    public void useSimpleAnnotations() {
        /* Basic annotations */
        // @Override annotation - override parent method
        // @Deprecated annotation - method is deprecated
        // @SuppressWarnings annotation - hide compiler warnings, e.g. @SuppressWarnings("deprecation")
        // @Retention - @annotation availability, e.g @Retention(RetentionPolicy.RUNTIME)

        try {
            DoesMagic classAnnotation = this.getClass().getAnnotation(DoesMagic.class);
            DoesMagic methodAnnotation = this.getClass().getMethod("useSimpleAnnotations").getAnnotation(DoesMagic.class);
            System.out.println("Print custom 'DoesMagic' CLASS annotation go() value: " + classAnnotation.go());
            System.out.println("Print custom 'DoesMagic' METHOD annotation go() value: " + methodAnnotation.go());
            System.out.println("Print custom 'DoesMagic' CLASS annotation value() value: " + classAnnotation.value());
            System.out.println("Print custom 'DoesMagic' METHOD annotation value() value: " + methodAnnotation.value());
        } catch (NoSuchMethodException e) {
            System.out.println(e.getMessage());
        }
    }
}
