package hwl.model;

import java.util.Objects;

public class IntPair implements Comparable<IntPair> {
    public int first;
    public int second;

    public IntPair(int first, int second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntPair intPair = (IntPair) o;
        return first == intPair.first &&
                second == intPair.second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "IntPair{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    @Override
    public int compareTo(IntPair o) {
        if (this.first != o.first)
            return this.first - o.first;
        return this.second - o.second;
    }
}
