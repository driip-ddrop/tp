package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

public class Remark {
    public final String value;

    public Remark(String remark) {
        requireNonNull(remark);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return o == this // returns true if it is the same object
                || (o instanceof Remark) // instanceof handles nulls
                && value.equals(((Remark) o).value); //check the state
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
