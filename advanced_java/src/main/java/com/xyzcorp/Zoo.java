package com.xyzcorp;

import java.util.List;

/**
 * Created by danno on 10/12/16.
 */
public class Zoo<T> {

    public <X> void foo(T t, X x,  List<? super  X> list) {
      //return list.set(1, x);
    }

}
