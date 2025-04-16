package predicates;

import com.model.Car;

import java.util.List;

public class ComportamentoParametroTest {
    private static List<Car> cars = List.of(
            new Car("green",2011)
            , new Car("black", 1998)
            , new Car("red", 2019)
    );

    public static List<Car> getCars() {
        return cars;
    }
}
