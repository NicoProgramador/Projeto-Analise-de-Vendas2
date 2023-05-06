package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import entities.Sale;

public class Program {

	public static void main(String[] args) {
		
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);
		
		System.out.print("Entre o caminho do arquivo: ");
		String path = sc.nextLine();
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			
			Set<String> names = new HashSet<>();
			List<Sale> list = new ArrayList<>();
			
			String line = br.readLine();
			while (line != null) {
				String[] fields = line.split(",");
				list.add(new Sale(Integer.parseInt(fields[0]), Integer.parseInt(fields[1]), fields[2], Integer.parseInt(fields[3]), Double.parseDouble(fields[4])));
				line = br.readLine();
			}
			
			names = list.stream()
					.map(s -> s.getSeller())
					.collect(Collectors.toSet());
								
			System.out.println();
			System.out.println("Total de vendas por vendador:");
			
			for (String seller : names) {
				double sum = 0.0;
				sum = list.stream()
						.filter(s -> s.getSeller().equals(seller))
						.map(total -> total.getTotal())
						.reduce(0.0, (x,y) -> x + y);
				System.out.println(seller + " - R$ " + String.format("%.2f", sum));
			}
			
			
		} catch (IOException e) {
			System.out.println("Erro: " + path + " (O sistema não pode encontrar o arquivo especificado)");
		}
		
		sc.close();
	}

}
