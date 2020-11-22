public abstract class SimpleHashSet implements SimpleSet {

    /*
    lower load factor of this hash table
     */
    private float lowerLoadFactor;

    /*
    upper load factor of this hash table
     */
    private float upperLoadFactor;

    /*
    load factor of table
     */
    private float loadFactor;

    /*
     * current maximum capacity of current hash table
     */
    private int capacity;

    /*
     * current filled capacity of hash table
     */
    private int currentFilledCapacity;

    /*
     * Describes the capacity of a newly created hash set.
     */
    protected static final int INITIAL_CAPACITY = 16;

    /*
     * Describes the lower load factor of a newly created hash set.
     */
    protected static final float DEFAULT_LOWER_CAPACITY = 0.25f;

    /**
     * Describes the higher load factor of a newly created hash set.
     */
    protected static final float DEFAULT_HIGHER_CAPACITY = 0.75f;

    /*
     * describes an action to be done
     */
    protected static final String ADD = "adding";

    /*
     * describes an action to be done
     */
    protected static final String REMOVE = "removing";

    /**
     * Constructs a new hash set with the default capacities given in
     * DEFAULT_LOWER_CAPACITY and DEFAULT_HIGHER_CAPACITY.
     */
    protected SimpleHashSet() {
        this.lowerLoadFactor = DEFAULT_LOWER_CAPACITY;
        this.upperLoadFactor = DEFAULT_HIGHER_CAPACITY;
        this.capacity = INITIAL_CAPACITY;
        this.currentFilledCapacity = 0;
        this.loadFactor = 0;
    }

    /**
     * Constructs a new hash set with capacity INITIAL_CAPACITY.
     *
     * @param upperLoadFactor the upper load factor before rehashing
     * @param lowerLoadFactor the lower load factor before rehashing
     */
    protected SimpleHashSet(float upperLoadFactor, float lowerLoadFactor) {
        this.lowerLoadFactor = lowerLoadFactor;
        this.upperLoadFactor = upperLoadFactor;
        this.capacity = INITIAL_CAPACITY;
        this.currentFilledCapacity = 0;
        this.loadFactor = 0;
    }

    /**
     * The current capacity (number of cells) of the table.
     *
     * @return The current capacity (number of cells) of the table.
     */
    public abstract int capacity();

    /**
     * a method to get the number of elements currently in the set
     *
     * @return The number of elements currently in the set
     */
    public abstract int size();


    /**
     * a method to get the lower load factor of the hash table
     *
     * @return The lower load factor of the table.
     */
    protected float getLowerLoadFactor() {
        return this.lowerLoadFactor;
    }


    /**
     * a method to get the higher load factor of the hash table
     *
     * @return The higher load factor of the table.
     */
    protected float getUpperLoadFactor() {
        return this.upperLoadFactor;
    }

    /**
     * Clamps hashing indices to fit within the current table capacity (see the exercise description for details)
     *
     * @param index the index before clamping.
     * @return an index properly clamped.
     */
    protected int clamp(int index) {
        int tableSize = this.capacity();
        return (index & (tableSize - 1));
    }

    /**
     * a getter for current filled capacity field
     *
     * @return the current field capacity value
     */
    protected int getCurrentFilledCapacity() {
        return this.currentFilledCapacity;
    }

    /**
     * a setter for filled capacity amount
     *
     * @param newFilledAmount new filled capacity to set
     */
    protected void setCurrentFilledCapacity(int newFilledAmount) {
        this.currentFilledCapacity = newFilledAmount;
    }

    /**
     * a getter function for the tables capacity
     *
     * @return capacity of table at current moment.
     */
    protected int getCapacity() {
        return this.capacity;
    }

    /**
     * a setter function for capacity field
     *
     * @param newCapacity the new capacity for the table
     */
    protected void setCapacity(int newCapacity) {
        this.capacity = newCapacity;
    }

    /**
     * a setter function for load factor field
     *
     * @param newValue the new load factor
     */
    protected void setLoadFactor(float newValue) {
        this.loadFactor = newValue;
    }

    /**
     * a method to calculate the new load factor after inserting or deleting.
     *
     * @return float of new current load factor
     */
    protected float loadFactorCalculator() {
        return ((float) this.getCurrentFilledCapacity() / (float) this.getCapacity());
    }
}
