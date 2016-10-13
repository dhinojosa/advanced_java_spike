package com.xyzcorp;

/**
 * Created by danno on 10/12/16.
 */
public interface Human {
    public String getFirstName();
    public String getLastName();
    public default String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
