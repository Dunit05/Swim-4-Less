// Tommy
// Dec 13, 2022
// Swim 4 Less App

package com.business.pool;

import com.business.shapes.Rect;

public class SwimmingPool {

    // Data fields
    private Rect pool;
    private Rect walkway;
    private int poolNumber;
    // Static and non-static constantss
    public static int numOfPools = 0;
    public static final double WALKWAY_DISTANCE = (6 * 2), CONCRETE_COST = 1.80, FENCE_COST = 10.00,
            SMALL_POOL_COST = 20540.00,
            LARGE_POOL_COST = 39320.00;
    public static final int MAX_ID = 900, ONE_HUNDRED = 100, SMALL_POOL_MAX_AREA = 140;

    // Constructors
    public SwimmingPool() {
        pool = new Rect();
        walkway = new Rect(pool.getLength() + WALKWAY_DISTANCE, pool.getWidth() + WALKWAY_DISTANCE);
        poolNumber = (int) (Math.random() * MAX_ID) + ONE_HUNDRED;
        numOfPools++;
    }

    // Overloading constructor
    public SwimmingPool(double length, double width) {
        pool = new Rect(length, width);
        walkway = new Rect(length + WALKWAY_DISTANCE, width + WALKWAY_DISTANCE);
        poolNumber = (int) (Math.random() * MAX_ID) + ONE_HUNDRED;
        numOfPools++;
    }

    // Getters and setters
    public double getConcreteArea() {
        return walkway.getArea() - pool.getArea();
    }

    public double getConcreteCost() {
        return getConcreteArea() * CONCRETE_COST;
    }

    public double getPoolCost() {
        return pool.getArea() <= SMALL_POOL_MAX_AREA ? SMALL_POOL_COST : LARGE_POOL_COST;
    }

    public double getFenceLength() {
        return walkway.getPerimeter();
    }

    public double getFenceCost() {
        return getFenceLength() * FENCE_COST;
    }

    // Total cost of pool, walkway, and fence without tax
    public double getTotalCost() {
        return getConcreteCost() + getPoolCost() + getFenceCost();
    }

    public int getPoolId() {
        return poolNumber;
    }

    public void setLength(double length) {
        pool.setLength(length);
        walkway.setLength(length + WALKWAY_DISTANCE);
    }

    public void setWidth(double width) {
        pool.setWidth(width);
        walkway.setWidth(width + WALKWAY_DISTANCE);
    }

    // toString method
    public String toString() {
        return "Swimming Pool: " + pool.getLength() + " x " + pool.getWidth() + " = " + pool.getArea() + " sq. ft.";
    }
}
