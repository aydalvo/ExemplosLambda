import java.util.List;
import java.util.Optional;

public class Test01 {
    public static void main(String[] args) {
        Optional<String> test = Optional.ofNullable(findName("william"));
        System.out.println(test.orElse("EMPTY"));
    }

    private static String findName(String name) {
        List<String> list = List.of("William", "Teste");
        int i = list.indexOf(name);
        if (i!=-1) {
            return list.get(i);
        }
        return null;
    }
}
