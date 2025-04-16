package predicates;

import com.intefaces.CarPredicate;
import com.model.Car;

import java.util.ArrayList;
import java.util.List;

public class ComportamentoParametroTest02 extends ComportamentoParametroTest {

    public static void main(String[] args) {

        System.out.println(filter(getCars(), car -> "green".equals(car.getColor())));
        System.out.println(filter(getCars(), car -> "red".equals(car.getColor())));
        System.out.println(filter(getCars(), car -> "black".equals(car.getColor())));
        System.out.println(filter(getCars(), car -> car.getYear() < 2015));

    }

    public static List<Car> filter(List<Car> cars, CarPredicate predicate) {
        List<Car> filteredCar = new ArrayList<>();
        for (Car car : cars){
            if (predicate.test(car)) {
                filteredCar.add(car);
            }
        }
        return filteredCar;
    }
}
