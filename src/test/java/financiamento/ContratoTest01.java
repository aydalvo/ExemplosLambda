package financiamento;

import com.model.financiamento.Contrato;
import com.model.financiamento.Parcela;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ContratoTest01 {

    public static void main(String[] args) {
        Contrato meuContratoTest = populaDados();
        System.out.println(meuContratoTest);

        busca(meuContratoTest);

        listaIdsParcelasPagas(meuContratoTest);
        listarNumeroParcelaAndDtPagamentoParcelasPagasDecrescente(meuContratoTest);

        anyMatch(meuContratoTest);

        filterInstallmentsWithPaymentDate();
        listingInstallmentsByPaymentDate(meuContratoTest);
    }

    private static void busca(Contrato meuContratoTest) {
        List<Parcela> filteredList = meuContratoTest.getParcelas()
                .stream()
                .filter(parcela -> parcela.getFgPago().equals("S") && parcela.getNumeroParcela()>0)
                .collect(Collectors.toUnmodifiableList());


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
                .collect(Collectors.toUnmodifiableList());
        System.out.println(parcelasPagas);
    }

    private static void listaIdsParcelasPagas(Contrato meuContratoTest) {
        List<Integer> parcelasPagas = meuContratoTest.getParcelas()
                .stream()
                .sorted(Comparator.comparing(Parcela::getFgPago))
                .filter(p -> p.getFgPago().equals("S") && p.getNumeroParcela()>0)
                .map(Parcela::getNumeroParcela)
                .collect(Collectors.toUnmodifiableList());

        System.out.println(parcelasPagas);
    }

    private static void filterInstallmentsWithPaymentDate() {
    }

    private static void listingInstallmentsByPaymentDate(Contrato meuContratoTest) {
        List<Parcela> installments = meuContratoTest.getParcelas();
        installments.sort(Comparator.comparing(Parcela::getFgPago));
        System.out.println(installments);
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
                , 10
        );

        ArrayList<Parcela> parcelas = new ArrayList<>();
        Double juros = Double.parseDouble("500.0");
        Double amortizacao = Double.parseDouble("1000.0");
        LocalDate localDate = LocalDate.of(2025, Month.APRIL, 15);
        LocalDate localDatePagamento = LocalDate.of(2025, Month.APRIL, 15);
        for (int i = 0; i < c.getNumeroParcelas()+1; i++) {
            Parcela p = new Parcela(i+1
                    , i
                    , i%2==0 ? "S" : "N"
                    , juros-i
                    , amortizacao+i
                    , localDate.plusMonths(i)
                    , i%2==0 ? localDatePagamento.plusMonths(i) : null
                    , c.getId()
            );

            parcelas.add(p);
        }

        c.setParcelas(parcelas);
        return c;
    }
}
