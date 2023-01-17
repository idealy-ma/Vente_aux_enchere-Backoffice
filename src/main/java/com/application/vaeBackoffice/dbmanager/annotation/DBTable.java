/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/AnnotationType.java to edit this template
 */
package com.application.vaeBackoffice.dbmanager.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * @author i.m.a
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
    public String tableName();
}
