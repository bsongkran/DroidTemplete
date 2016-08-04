package com.example.droid.injection.scope;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by BERM-PC on 14/3/2559.
 */
@Scope
@Retention(RUNTIME)
public @interface ApplicationScope {
}
