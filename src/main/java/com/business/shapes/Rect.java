// Tommy
// Dec 13, 2022
// Swim 4 Less App

package com.business.shapes;

public class Rect {

    // Data feilds
    private double length, width;

    // Constructors
    public Rect() {
        this.length = 5;
        this.width = 5;
    }

    // Overloading constructor
    public Rect(double length, double width) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be positive");
        } else {
            this.length = length;
        }

        if (width < 0) {
            throw new IllegalArgumentException("Width must be positive");
        } else {
            this.width = width;
        }
    }

    // Getters and setters
    public void setLength(double length) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be positive");
        } else {
            this.length = length;
        }
    }

    public void setWidth(double width) {
        if (width < 0) {
            throw new IllegalArgumentException("Width must be positive");
        } else {
            this.width = width;
        }
    }

    public void resize(double length, double width) {
        if (length < 0) {
            throw new IllegalArgumentException("Length must be positive");
        } else {
            this.length = length;
        }

        if (width < 0) {
            throw new IllegalArgumentException("Width must be positive");
        } else {
            this.width = width;
        }
    }

    public double getLength() {
        return length;
    }

    public double getWidth() {
        return width;
    }

    public double getArea() {
        return length * width;
    }

    public double getPerimeter() {
        return 2 * (length + width);
    }

    public double getDiagonal() {
        return Math.sqrt(length * length + width * width);
    }

    // toString method
    public String toString() {
        return "Rectangle: " + length + " x " + width;
    }
}
