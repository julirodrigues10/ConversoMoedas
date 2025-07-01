package src;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Scanner;

public class Principal {

    public static void main(String[] args) throws Exception {
        ClienteHttp cliente = new ClienteHttp();
        String url = "https://v6.exchangerate-api.com/v6/8af0fcd72d6e8f6257a32d23/latest/USD";

        String json = cliente.buscarDados(url);
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        // CORREÇÃO aqui:
        JsonObject rates = jsonObject.getAsJsonObject("conversion_rates");

        Scanner scanner = new Scanner(System.in);

        boolean continuar = true;
        while (continuar) {
            System.out.println("\n==== CONVERSOR DE MOEDAS ====");
            System.out.println("1. Converter moedas");
            System.out.println("2. Ver cotações fixas");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            if (opcao == 1) {
                System.out.println("Digite o valor para converter:");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                System.out.println("Digite a moeda de origem (ex: USD, BRL, EUR):");
                String moedaOrigem = scanner.nextLine().toUpperCase();

                System.out.println("Digite a moeda de destino (ex: USD, BRL, EUR):");
                String moedaDestino = scanner.nextLine().toUpperCase();

                try {
                    double taxaOrigem = rates.get(moedaOrigem).getAsDouble();
                    double taxaDestino = rates.get(moedaDestino).getAsDouble();

                    double valorConvertido = (valor / taxaOrigem) * taxaDestino;

                    System.out.println(valor + " " + moedaOrigem + " equivalem a " + valorConvertido + " " + moedaDestino);
                } catch (Exception e) {
                    System.out.println("Alguma das moedas digitadas não é válida. Use códigos como USD, BRL, EUR, etc.");
                }
            } else if (opcao == 2) {
                System.out.println("\nCotações fixas:");
                System.out.println("ARS (Peso argentino): " + rates.get("ARS").getAsDouble());
                System.out.println("BOB (Boliviano): " + rates.get("BOB").getAsDouble());
                System.out.println("BRL (Real brasileiro): " + rates.get("BRL").getAsDouble());
                System.out.println("CLP (Peso chileno): " + rates.get("CLP").getAsDouble());
                System.out.println("COP (Peso colombiano): " + rates.get("COP").getAsDouble());
                System.out.println("USD (Dólar americano): " + rates.get("USD").getAsDouble());

            } else if (opcao == 3) {
                System.out.println("Saindo do programa. Até logo!");
                continuar = false;
            } else {
                System.out.println("Opção inválida. Tente novamente.");
            }
        }

        scanner.close();
    }
}
