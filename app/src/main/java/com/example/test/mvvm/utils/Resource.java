package com.example.test.mvvm.utils;

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import static com.example.test.mvvm.utils.Status.ERROR;
import static com.example.test.mvvm.utils.Status.LOADING;
import static com.example.test.mvvm.utils.Status.SUCCESS;


/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Resource<T> {

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    // Custom fields : added for o2o service
    @Nullable
    public final String errorMessage;

    @Nullable
    public final Integer statusCode;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable String message, @Nullable String errorMessage, @Nullable Integer statusCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.errorMessage = errorMessage;
        this.statusCode = statusCode;
    }

    public static <T> Resource<T> success(@Nullable T data, Integer statusCode) {
        return new Resource<>(SUCCESS, data, null, null, statusCode);
    }

    public static <T> Resource<T> error(String msg, String emsg, @Nullable T data, Integer statusCode) {
        return new Resource<>(ERROR, data, msg, emsg, statusCode);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null, null, null);
    }


}