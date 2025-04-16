package predicates;

import com.model.Car;

import java.util.ArrayList;
import java.util.List;

public class ComportamentoParametroTest01 extends ComportamentoParametroTest {

    public static void main(String[] args) {
        System.out.println(filterCarByColor(getCars(), "green"));
        System.out.println(filterCarByColor(getCars(), "red"));

        System.out.println(filterCarByFabricationUntilYear(getCars(), 2015));
    }

    private static List<Car> filterCarByColor(List<Car> cars, String color){
        ArrayList<Car> filteredByColor = new ArrayList<>();
        for (Car car: cars) {
            if (color.equals(car.getColor())){
                filteredByColor.add(car);
            }
        }
        return filteredByColor;
    }

    private static List<Car> filterCarByFabricationUntilYear(List<Car> cars, int year) {
        ArrayList<Car> filteredByYear = new ArrayList<>();
        for (Car car: cars){
            if(car.getYear() < year) {
                filteredByYear.add(car);
            }
        }
        return filteredByYear;
    }
}
