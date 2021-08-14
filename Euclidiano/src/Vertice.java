import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;


public class Vertice implements Comparator<Vertice>
{
	protected ArrayList <Vertice> vizinhos; // Permite laços
	protected int id;
	protected boolean visitado = false;
	
	Vertice (int id)
	{
		this.id = id;
		vizinhos = new ArrayList<Vertice>();
	}
	
	public void imprimeVertice()
	{
		System.out.println("ID do vértice: " + id);
		System.out.print("Vizinhos:");
		Collections.sort(vizinhos, new Vertice());
		
		LinkedHashSet<Vertice> vizinhosSemRepeticoes = new LinkedHashSet<Vertice>(vizinhos);
		
		for (Vertice v : vizinhosSemRepeticoes)
		{
			int numArestas = Collections.frequency(vizinhos, v);
			
			if (numArestas > 1)
				System.out.print(" " + v.id + " (" + numArestas + " arestas)");
			else
				System.out.print(" " + v.id);
		}
		
		System.out.println("");
		
		if (visitado == false)
			System.out.println("\nVértice não descoberto.");
		
		System.out.println("----------------");
	}
	
	protected void adicionaVizinho(Vertice v)
	{
		vizinhos.add(v);
	}
	
	protected int retornaGrauVertice()
	{
		return vizinhos.size();
	}
	
	public void reset()
	{
		visitado = false;
	}
	
	@Override
	public int compare(Vertice o1, Vertice o2) {
		int id1 = o1.id;
		int id2 = o2.id;
		return compareTo(id1, id2);
	}
	public int compareTo(int i1, int i2)
	{
		if (i1 < i2)
			return -1;
		if (i1 == i2)
			return 0;
		return 1;
	}
	
	private Vertice()
	{
		
	}
	
	

}
