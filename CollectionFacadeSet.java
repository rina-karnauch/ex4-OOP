import java.util.Collection;

/**
 * The job of this class, which implements SimpleSet, is to wrap an object implementing javas Collection String
 * with a class that has a common type with your own implementations for sets.
 *
 * @author rina.karnauch
 */
public class CollectionFacadeSet implements SimpleSet {

    /*
    a field of the wrapped collection the facade is created for
     */
    private Collection<String> wrappedCollection;

    /**
     * constructor
     * @param collectionToWrap the collection to wrap
     */
    public CollectionFacadeSet(Collection<String> collectionToWrap) {
        this.wrappedCollection = collectionToWrap;
    }

    /**
     * Add a specified element to the set if it's not already in it.
     *
     * @param newValue New value to add to the set
     * @return False iff newValue already exists in the set
     */
    public boolean add(String newValue) {
        if (newValue == null || this.contains(newValue)) {
            return false;
        }
        return this.wrappedCollection.add(newValue);
    }

    /**
     * Look for a specified value in the set.
     *
     * @param searchVal Value to search for
     * @return True iff searchVal is found in the set
     */
    public boolean contains(String searchVal) {
        if (searchVal != null) {
            return this.wrappedCollection.contains(searchVal);
        }
        return false;
    }

    /**
     * Remove the input element from the set.
     *
     * @param toDelete Value to delete
     * @return True if toDelete is found and deleted
     */
    public boolean delete(String toDelete) {
        if (toDelete == null || !this.wrappedCollection.contains(toDelete)) {
            return false;
        }
        this.wrappedCollection.remove(toDelete);
        return true;
    }

    /**
     * @return The number of elements currently in the set
     */
    public int size() {
        return this.wrappedCollection.size();
    }
}
