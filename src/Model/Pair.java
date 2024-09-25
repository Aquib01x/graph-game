package Model;


/** Pair<> is a type to store key/value information as a pair
 *
 * @param <Key> key of the key/value pair
 * @param <Value> value of the key/value pair
 */
public class Pair<Key, Value> {
    private Key key;
    private Value value;
    public Pair(Key key, Value value){
        this.key = key;
        this.value = value;
    }

    /** getKey() will return the key of the pair.
     *
     * @return key of the pair
     */
    public Key getKey() {
        return key;
    }

    /** setKey() will overwrite the key of the pair
     *
     * @param key new key of the pair
     */
    public void setKey(Key key) {
        this.key = key;
    }

    /** getValue() will return the value of the pair
     *
     * @return value of the pair
     */
    public Value getValue() {
        return value;
    }

    /** setValue() will overwrite the value of the pair
     *
     * @param value new value of the pair
     */
    public void setValue(Value value) {
        this.value = value;
    }

}
