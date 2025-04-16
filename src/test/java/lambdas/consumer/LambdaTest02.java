package lambdas.consumer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class LambdaTest02 {
    public static void main(String[] args) {
        List<String> strings = List.of("Natsu", "Allucard");
        List<Integer> integers = map(strings, (String s) -> s.length());
        System.out.println(integers);
        List<String> upperCase = map(strings, String::toUpperCase); // method reference
        System.out.println(upperCase);
    }

    private static <T,R> List<R> map(List<T> listTs, Function<T,R> function) {
        List<R> result = new ArrayList<>();
        for(T t: listTs) {
            R r = function.apply(t);
            result.add(r);
        }
        return result;
    }
}
