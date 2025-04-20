package financiamento;

import com.model.financiamento.Contrato;
import com.model.financiamento.Parcela;
import com.model.financiamento.StatusPagamento;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContratoTest01 {

    public static void main(String[] args) {

        Contrato meuContratoTest = populaDados();
        System.out.println(meuContratoTest);

        Map<StatusPagamento, List<Parcela>> stringListMap = extractedParcelasAgrupadasPorStatusPagamento(meuContratoTest.getParcelas()
                , 0,0);

        if (1==1) {

            DoubleSummaryStatistics sumPaymentsPaid = getDoubleSummaryStatistics(stringListMap
                    ,StatusPagamento.Pagamento_Ok);
            System.out.println("sumPaymentsPaid:"+sumPaymentsPaid);

            sumInterestAndPrice(stringListMap, StatusPagamento.Pagamento_Em_aberto);

            DoubleSummaryStatistics sumPaymentsOpen = getDoubleSummaryStatistics(stringListMap
                    , StatusPagamento.Pagamento_Em_aberto);
            System.out.println("sumPaymentsOpen:"+sumPaymentsOpen);

            sumDoubleWithReduce(stringListMap, StatusPagamento.Pagamento_Ok);

            IntSummaryStatistics intSummaryStatistics = intSummaryStatisticsFilteredByStatusPagamento(stringListMap, StatusPagamento.Pagamento_Ok);

            System.out.println(intSummaryStatistics);

            List<Parcela> listaDeParcelasSemParcelaZero = meuContratoTest.getParcelas()
                    .stream()
                    .filter(p -> p.getNumeroParcela() > 0)
                    .toList();

            rechamadaFilteredByValuesMinAndMax(intSummaryStatistics, listaDeParcelasSemParcelaZero);


        } else {
            System.out.println("--- Parcelas sem pagamento. ---");
            List<Integer> collect = stringListMap.get(StatusPagamento.Pagamento_Em_aberto).stream()
                    .map(Parcela::getNumeroParcela)
                    .filter(numeroParcela -> numeroParcela > 0)
                    .toList();
            System.out.println(collect);

            int[] array = new int[collect.size()];
            for (int i = 0 ; i < array.length ; i++){
                array[i] = collect.get(i);
            }
            int i = missingNumber(array);
            System.out.println(i);

            List<Integer> integers = map(stringListMap.get(StatusPagamento.Pagamento_Ok), Parcela::getNumeroParcela);
            System.out.println(integers);

            extractedListMap(meuContratoTest);

            busca(meuContratoTest);

            listaIdsParcelasPagas(meuContratoTest);
            listarNumeroParcelaAndDtPagamentoParcelasPagasDecrescente(meuContratoTest);

            anyMatch(meuContratoTest);

            filterInstallmentsWithPaymentDate();
            listingInstallmentsByPaymentDate(meuContratoTest);
        }
    }

    private static void rechamadaFilteredByValuesMinAndMax(IntSummaryStatistics intSummaryStatistics
            , List<Parcela> listaDeParcelasSemParcelaZero) {

        int min = intSummaryStatistics.getMin();
        int max = intSummaryStatistics.getMax();
        long sum = intSummaryStatistics.getSum();
        long resto = sum - max - min;

        // ordenado na insercao
        TreeMap<Integer, List<Parcela>> treeMapRange = new TreeMap<>();

        while (!listaDeParcelasSemParcelaZero.isEmpty()) {

            /*System.out.println(min);
            System.out.println(max);
            System.out.println("Sum="+sum);
            System.out.println("resto="+ resto);*/

            int finalMax = intSummaryStatistics.getMax();
            int finalMin = intSummaryStatistics.getMin();

            List<Parcela> p1 = listaDeParcelasSemParcelaZero
                    .stream()
                    .filter(p -> p.getNumeroParcela() < finalMin)
                    .toList();

            if (!p1.isEmpty()) {
                treeMapRange.put(intSummaryStatistics.getMin(), p1);
                p1=null;
            }
            List<Parcela> p2 = listaDeParcelasSemParcelaZero
                    .stream()
                    .filter(p -> p.getNumeroParcela() > finalMax)
                    .toList();

            if (!p2.isEmpty()) {
                treeMapRange.put(intSummaryStatistics.getMax(), p2);
                p2=null;
            }

            List<Parcela> parcelas2 = listaDeParcelasSemParcelaZero
                    .stream()
                    .filter(p -> ((p.getNumeroParcela() > finalMin)
                            && (p.getNumeroParcela() < finalMax)))
                    .toList();
            /*System.out.println(listaDeParcelasSemParcelaZero.toString());*/

            listaDeParcelasSemParcelaZero = null;
            listaDeParcelasSemParcelaZero = new ArrayList<>();
            listaDeParcelasSemParcelaZero.addAll(parcelas2);

            Map<StatusPagamento, List<Parcela>> statusPagamentoListMap
                    = extractedParcelasAgrupadasPorStatusPagamento(parcelas2, min, max);

            System.out.println("--- Rechamada(s). ---");
            intSummaryStatistics = intSummaryStatisticsFilteredByStatusPagamento(statusPagamentoListMap, StatusPagamento.Pagamento_Ok);
            System.out.println(intSummaryStatistics);

            // caso a lista seja continua (não tenha gap)
            if(intSummaryStatistics.getCount()==0) {
                treeMapRange.put((min+1), listaDeParcelasSemParcelaZero);
                listaDeParcelasSemParcelaZero=null;
                listaDeParcelasSemParcelaZero=new ArrayList<>();
            }
            min = intSummaryStatistics.getMin();
            max = intSummaryStatistics.getMax();
            sum = intSummaryStatistics.getSum();
            resto = sum - max - min;
            /*System.out.println(min);
            System.out.println(max);
            System.out.println("Sum="+sum);
            System.out.println("resto="+resto);*/

        }

        System.out.println("--- treeMap ---");
        System.out.println("--- treeMap.size() ---" + treeMapRange.size());
        System.out.println("--- treeMap ---");
        for(Integer integer: treeMapRange.keySet()) {
            System.out.println(treeMapRange.get(integer));
        }
    }

    private static IntSummaryStatistics intSummaryStatisticsFilteredByStatusPagamento(Map<StatusPagamento, List<Parcela>> stringListMap
            , StatusPagamento statusPagamento) {

        System.out.println("--- Estatisticas parcelas pagas. ---");
        if (stringListMap.get(statusPagamento)==null) {
            return new IntSummaryStatistics();
        }
        IntSummaryStatistics intSummaryStatistics = stringListMap.get(statusPagamento)
                .stream()
                .collect(Collectors.summarizingInt(Parcela::getNumeroParcela));
        return intSummaryStatistics;
    }

    private static void sumDoubleWithReduce(Map<StatusPagamento, List<Parcela>> stringListMap, StatusPagamento statusPagamento) {
        if (stringListMap.get(statusPagamento)==null){
            return;
        }
        stringListMap.get(statusPagamento).stream()
                .map(parcela -> parcela.getJuros()+parcela.getAmortizacao())
                .reduce(Double::sum).ifPresent(System.out::println);
    }

    private static <T, R> List<R> map(List<T> list, Function<T, R> function){
        List<R> result = new ArrayList<>();
        for(T t : list) {
            R r = function.apply(t);
            result.add(r);
        }
        return result;
    }

    private static Map<StatusPagamento, List<Parcela>>
            extractedParcelasAgrupadasPorStatusPagamento(List<Parcela> parcelas, int min, int max) {

        Map<StatusPagamento, List<Parcela>> stringListMap = parcelas
                .stream()
                .filter(parcela -> parcela.getNumeroParcela() > 0
                        && parcela.getNumeroParcela() != min
                        && parcela.getNumeroParcela() != max)
                .collect(Collectors.groupingBy(parcela -> parcela.getFgPago().equals("S") ?
                        StatusPagamento.Pagamento_Ok : StatusPagamento.Pagamento_Em_aberto
                ));

//        System.out.println(stringListMap);
//        System.out.println(stringListMap.keySet());
//        System.out.println(stringListMap.get(StatusPagamento.Pagamento_Ok));
//        System.out.println(stringListMap.get(StatusPagamento.Pagamento_Em_aberto));

        return stringListMap;
    }

    private static DoubleSummaryStatistics getDoubleSummaryStatistics(Map<StatusPagamento, List<Parcela>> stringListMap
            , StatusPagamento statusPagamento) {
        if (stringListMap.get(statusPagamento)==null)
                return new DoubleSummaryStatistics();
        DoubleSummaryStatistics sumPayments = stringListMap.get(statusPagamento)
                .stream()
                .collect(Collectors.summarizingDouble(p -> p.getJuros() + p.getAmortizacao()));
        return sumPayments;
    }

    private static void sumInterestAndPrice(Map<StatusPagamento, List<Parcela>> stringListMap
            , StatusPagamento statusPagamento) {
        Double soma = stringListMap.get(statusPagamento)
                .stream().mapToDouble(p -> p.getJuros() + p.getAmortizacao()).sum();
        System.out.println(soma);
    }

    @SuppressWarnings({})
    private static void extractedListMap(Contrato meuContratoTest) {
        Map<LocalDate, List<Parcela>> listMap = meuContratoTest.getParcelas()
                .stream()
                .filter(parcela -> parcela.getFgPago().equals("S") && parcela.getNumeroParcela()>0)
                .collect(Collectors.groupingBy(Parcela::getDtPagamento));

        System.out.println(listMap.keySet());
        System.out.println(listMap.values());
    }

    @SuppressWarnings({})
    private static void busca(Contrato meuContratoTest) {
        List<Parcela> filteredList = meuContratoTest.getParcelas()
                .stream()
                .filter(parcela -> parcela.getFgPago().equals("S") && parcela.getNumeroParcela()>0)
                .toList();


        boolean match = false;
        for (Parcela p1 : filteredList) {
            for (Parcela p2 : filteredList) {
                if (p1.getNumeroParcela() < p2.getNumeroParcela() && p1.getDtPagamento().isBefore(p2.getDtPagamento()) && !match) {
                    System.out.println(p1.toString2());
                    match=true;
                }
            }
            match = false;
        }
    }

    private static void anyMatch(Contrato meuContratoTest) {
        Stream<Parcela> anyMatch = meuContratoTest.getParcelas()
                .stream()
                .filter(p -> p.getFgPago().equals("S"));
        System.out.println(anyMatch);
    }

    private static void listarNumeroParcelaAndDtPagamentoParcelasPagasDecrescente(Contrato meuContratoTest) {
        List<String> parcelasPagas = meuContratoTest.getParcelas()
                .stream()
                .filter(p -> p.getFgPago().equals("S") && p.getNumeroParcela()>0)
                .sorted(Comparator.comparing(Parcela::getDtPagamento).reversed())
                .map(Parcela::toString2)
                .toList();
        System.out.println(parcelasPagas);
    }

    private static void listaIdsParcelasPagas(Contrato meuContratoTest) {
        List<Integer> parcelasPagas = meuContratoTest.getParcelas()
                .stream()
                .sorted(Comparator.comparing(Parcela::getFgPago))
                .filter(p -> p.getFgPago().equals("S") && p.getNumeroParcela()>0)
                .map(Parcela::getNumeroParcela)
                .toList();

        System.out.println(parcelasPagas);
    }

    @SuppressWarnings({})
    private static void filterInstallmentsWithPaymentDate() {
    }

    private static void listingInstallmentsByPaymentDate(Contrato meuContratoTest) {
        List<Parcela> installments = meuContratoTest.getParcelas();
        installments.sort(Comparator.comparing(Parcela::getFgPago));
        System.out.println(installments);
    }

    @SuppressWarnings({})
    private static int missingNumber(int[] arr) {
        int n = arr.length + 1;

        // Calculate the sum of array elements
        int sum = 0;
        for (int i = 0; i < n - 1; i++) {
            sum += arr[i];
        }

        // Calculate the expected sum
        int expectedSum = (n * (n + 1 )) / 2;

        // Return the missing number
        return expectedSum - sum;
    }

    private static Contrato populaDados() {

        Double vlFinanciado = Double.parseDouble("50000.0");
        Double txJuros = Double.parseDouble("1.0");
        Double vlFuturo = Double.parseDouble("100000.0");
        Contrato c = new Contrato(1
                ,"AU0000000001"
                , null
                , vlFinanciado
                , txJuros
                , vlFuturo
                , 60
        );

        ArrayList<Parcela> parcelas = new ArrayList<>();
        Double juros = Double.valueOf(680.58);
        Double amortizacao = Double.valueOf(833.33);
        LocalDate localDate = LocalDate.of(2025, Month.APRIL, 15);
        LocalDate localDatePagamento = LocalDate.of(2025, Month.APRIL, 15);
        for (int i = 0; i < c.getNumeroParcelas()+1; i++) {
            Parcela p = new Parcela(i+1
                    , i
                    , (i<2 || i==4 || i==7 || i==34 || i==47 || i>59)  ? "S" : "N"
                    , juros+i
                    , amortizacao - i
                    , localDate.plusMonths(i)
                    , (i<2 || i==4 || i==7 || i==34 || i==47 || i>59) ? localDatePagamento.plusMonths(i) : null
                    , c.getId()
            );

            parcelas.add(p);
        }

        c.setParcelas(parcelas);
        return c;
    }
}
