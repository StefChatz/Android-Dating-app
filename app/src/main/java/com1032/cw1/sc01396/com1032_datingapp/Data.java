package com1032.cw1.sc01396.com1032_datingapp;
/*
 *@author Stefanos Chatzakis
 */
public class Data {

    private String description;

    private int imagePath;

    /*
     * A parameterized constructor for the class.
     * @param imagePath, description.
     */

    public Data(int imagePath, String description) {
        this.imagePath = imagePath;
        this.description = description;
    }

    /*
     * A getter for the description.
     * @return String.
     */

    public String getDescription() {
        return description;
    }

    /*
     * A parameterized constructor for the class.
     * @return int.
     */

    public int getImagePath() {
        return imagePath;
    }

}