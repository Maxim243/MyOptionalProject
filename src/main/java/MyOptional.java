import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class MyOptional<T> {
    private final T value;
    private static final MyOptional<?> EMPTY = new MyOptional<>(null);

    private MyOptional(T value) {
        this.value = value;
    }

//Returns an Optional describing the given non-null value.
    //Params:
    //value – the value to describe, which must be non-null
    //Returns:
    //an Optional with the value present
    //Throws:
    //NullPointerException – if value is null

    @Override
    public String toString() {
        return "MyOptional{" +
                "value=" + value +
                '}';
    }

    public static <T> MyOptional<T> of(T value) throws NullPointerException {
        if (value == null) {
            throw new NullPointerException();
        } else {
            return new MyOptional<>(value);
        }
    }

    public static <T> MyOptional<T> empty() {
        @SuppressWarnings("unchecked")
        MyOptional<T> t = (MyOptional<T>) EMPTY;
        return t;
    }

    /**
     * Returns an {@code Optional} describing the given value, if
     * non-{@code null}, otherwise returns an empty {@code Optional}.
     *
     * @param value the possibly-{@code null} value to describe
     * @param <T>   the type of the value
     * @return an {@code Optional} with a present value if the specified value
     * is non-{@code null}, otherwise an empty {@code Optional}
     */


    public static <T> MyOptional<T> ofNullable(T value) {
        if (value != null) {
            return MyOptional.of(value);
        } else {
            return empty();
        }
    }

    //    If a value is present, returns the value, otherwise throws NoSuchElementException.
//            Returns:
//    the non-null value described by this Optional
//    Throws:
//    NoSuchElementException – if no value is present
//    API Note:
//    The preferred alternative to this method is orElseThrow().
    public T get() {
        if (value == null) {
            throw new NoSuchElementException();
        } else {
            return value;
        }
    }

    //    If a value is present, returns true, otherwise false.
//    Returns: true if a value is present, otherwise false
    public boolean isPresent() {
        return value != null;
    }

    public boolean isEmpty() {
        return value == null;
    }

//    If a value is present, performs the given action with the value, otherwise does nothing.
//            Params:
//    action – the action to be performed, if a value is present
//    Throws:
//    NullPointerException – if value is present and the given action is null

    public void ifPresent(Consumer<T> consumer) {
        if (value != null) {
            consumer.accept(value);
        }
    }

    //    If a value is present, performs the given action with the value, otherwise performs the given empty-based action.
//    Params:
//    action – the action to be performed, if a value is present emptyAction – the empty-based action to be performed, if no value is present
//    Throws:
//    NullPointerException – if a value is present and the given action is null, or no value is present and the given empty-based action is null.
    public void ifPresentOrElse(Consumer<T> consumer, Runnable action) {
        if (value != null) {
            consumer.accept(value);
        } else {
            action.run();
        }
    }

    //    If a value is present, and the value matches the given predicate, returns an Optional describing the value, otherwise returns an empty Optional.
//    Params:
//    predicate – the predicate to apply to a value, if present
//    Returns:
//    an Optional describing the value of this Optional, if a value is present and the value matches the given predicate, otherwise an empty Optional
//    Throws:
//    NullPointerException – if the predicate is nul
    public MyOptional<T> filter(Predicate<T> predicate) {
        Objects.requireNonNull(predicate);
        if (isPresent() && predicate.test(value)) {
            return MyOptional.of(value);
        } else {
            return empty();
        }
    }

    public <U> MyOptional<U> map(Function<T, U> mapper) {
        Objects.requireNonNull(mapper);
        if (this.isPresent()) {
            return MyOptional.ofNullable(mapper.apply(value));
        } else {
            return empty();
        }
    }
}
