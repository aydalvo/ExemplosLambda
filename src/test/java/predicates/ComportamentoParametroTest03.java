package predicates;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ComportamentoParametroTest03 extends ComportamentoParametroTest {
    public static void main(String[] args) {
        System.out.println(filter(getCars(), car -> "green".equals(car.getColor())));
        System.out.println(filter(getCars(), car -> car.getYear() < 2015));
        List<Integer> nums = List.of(1,2,3,4,5,6,7,8,9,10);
        System.out.println(filter(nums, n -> n % 2 == 0));
    }

    private static <T> List<T> filter(List<T> listTs, Predicate<T> predicate) {
        List<T> filteredList = new ArrayList<>();
        for(T t: listTs) {
            if(predicate.test(t)){
                filteredList.add(t);
            }
        }
        return filteredList;
    }
}
