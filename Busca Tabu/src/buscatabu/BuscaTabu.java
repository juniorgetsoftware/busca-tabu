package buscatabu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BuscaTabu {

	private final double PESO_MAXIMO = 400; // Peso máximo
	private int maxBT = 400; // Número máximo de iterações sem melhora

	private ArrayList<ItemMochila> itensMochila;

	private double alpha; // Somatório de todas as utilidades

	private int[] bestSolucao;
	private double bestAvaliacao;
	private int bestIt = 0; // Melhor iteração

	private int[] currentSolution;

	private List<Integer> tabu;

	public BuscaTabu() {

		tabu = new ArrayList<>();
		itensMochila = new ArrayList<>();

		itensMochila.add(new ItemMochila("map", 9d, 150d));
		itensMochila.add(new ItemMochila("compass", 13d, 35d));
		itensMochila.add(new ItemMochila("water", 15d, 200d));
		itensMochila.add(new ItemMochila("sandwich", 50d, 160d));
		itensMochila.add(new ItemMochila("glucose", 15d, 60d));
		itensMochila.add(new ItemMochila("tin", 68d, 45d));
		itensMochila.add(new ItemMochila("banana", 27d, 60d));
		itensMochila.add(new ItemMochila("apple", 39d, 40d));
		itensMochila.add(new ItemMochila("cheese", 23d, 30d));
		itensMochila.add(new ItemMochila("beer", 52d, 10d));
		itensMochila.add(new ItemMochila("suntan cream", 11d, 70d));
		itensMochila.add(new ItemMochila("camera", 32d, 30d));
		itensMochila.add(new ItemMochila("T-shirt", 24d, 15d));
		itensMochila.add(new ItemMochila("trousers", 48d, 10d));
		itensMochila.add(new ItemMochila("umbrella", 73d, 40d));
		itensMochila.add(new ItemMochila("waterproof trousers", 42d, 70d));
		itensMochila.add(new ItemMochila("waterproof overclothes", 43d, 75d));
		itensMochila.add(new ItemMochila("note-case", 22d, 80d));
		itensMochila.add(new ItemMochila("sunglasses", 7d, 20d));
		itensMochila.add(new ItemMochila("towel", 18d, 12d));
		itensMochila.add(new ItemMochila("socks", 4d, 50d));
		itensMochila.add(new ItemMochila("book", 30d, 10d));

		currentSolution = new int[itensMochila.size()];
		bestSolucao = new int[itensMochila.size()];
		initAlpha();

		initFirstSolution();

		bestAvaliacao = avaliacao(currentSolution);
		bestSolucao = currentSolution.clone();
		bestIt = 0;
	}

	private void initAlpha() {
		for (ItemMochila d : itensMochila) {
			alpha += d.getBenefincio();
		}
	}

	private void initFirstSolution() {
		Random r = new Random();
		for (int i = 0; i < currentSolution.length; i++) {
			currentSolution[i] = r.nextInt(2);
		}
	}

	public void run() {
		int itAtual = 0;

		while ((itAtual - bestIt) < maxBT) {
			itAtual++;
			int random = getRandomPosition();
			System.out.println("\n" + Arrays.toString(currentSolution));
			System.out.println("Random: " + random);
			if (!isTabu(random)) {
				addToTabu(random);
				if (currentSolution[random] == 0) {
					currentSolution[random] = 1;
				} else {
					currentSolution[random] = 0;
				}
			} else {
				if (funcaoAspiracao(random, currentSolution.clone())) {
					if (currentSolution[random] == 0) {
						currentSolution[random] = 1;
					} else {
						currentSolution[random] = 0;
					}
				}
			}

			double aval = avaliacao(currentSolution);
			System.out.println("Ite: " + itAtual + " avaliacao: " + aval);
			if (aval > bestAvaliacao) {
				bestAvaliacao = aval;
				bestIt = itAtual;
				bestSolucao = currentSolution.clone();
			}
		}
	}

	/**
	 * Avalia se a solução gerada é melhor que a atual mesmo com as penalidades caso
	 * ela for maior que o peso
	 */
	private double avaliacao(int[] solution) {
		double beneficio = 0;
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] == 1) {
				beneficio += itensMochila.get(i).getBenefincio();
			}
		}

		double peso = 0;
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] == 1) {
				peso += itensMochila.get(i).getPeso();
				;
			}
		}

		System.out.println("Aval Beneficio: " + beneficio);
		System.out.println("Aval peso: " + peso);
		double max = Math.max(0, peso - PESO_MAXIMO);

		return beneficio - alpha * max;
	}

	private int getRandomPosition() {
		return new Random().nextInt(itensMochila.size());
	}

	private void addToTabu(int value) {
		if (tabu.size() > 10) {
			tabu.remove(0);
		}
		tabu.add(value);
	}

	private boolean isTabu(int i) {
		return tabu.contains(i);
	}

	/**
	 * Função que avalia se deve ser permitido um movimento tabu após um tempo
	 */
	private boolean funcaoAspiracao(int position, int[] solution) {
		if (solution[position] == 0) {
			solution[position] = 1;
		} else {
			solution[position] = 0;
		}

		double beneficio = 0;
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] == 1) {
				beneficio += itensMochila.get(i).getBenefincio();
			}
		}

		double peso = 0;
		for (int i = 0; i < solution.length; i++) {
			if (solution[i] == 1) {
				peso += itensMochila.get(i).getPeso();
			}
		}

		System.out.println("Aval Beneficio: " + beneficio);
		System.out.println("Aval peso: " + peso);
		double max = Math.max(0, peso - PESO_MAXIMO);

		double aval = beneficio - alpha * max;

		return aval > bestAvaliacao;
	}

	public void printData() {
		System.out.println("\nItens: " + Arrays.toString(bestSolucao));
		System.out.println("Beneficio: " + bestAvaliacao);
		System.out.println("Best iterção: " + bestIt);

		double beneficio = 0;
		double peso = 0;
		
		for (int i = 0; i < bestSolucao.length; i++) {
			if (bestSolucao[i] == 1) {
				System.out.println("Item: " + itensMochila.get(i).getNome() + "\tPeso: " + itensMochila.get(i).getPeso()
						+ "\nBeneficio: " + itensMochila.get(i).getBenefincio());
				beneficio += itensMochila.get(i).getBenefincio();
				peso += itensMochila.get(i).getPeso();
			}
		}
		
		System.out.println("\nBeneficio real: " + beneficio);
		System.out.println("Peso real: " + peso);

	}

	public static void main(String[] args) {
		BuscaTabu b = new BuscaTabu();
		b.run();
		b.printData();
	}
}
