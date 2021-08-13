import java.util.HashMap;


public class Vertice 
{
	protected HashMap<Integer, Vertice> vizinhos;
	protected int id;
	protected boolean visitado = false;
	
	Vertice (int id)
	{
		this.id = id;
		vizinhos = new HashMap<Integer, Vertice>();
	}
	
	protected Vertice(Vertice v) 
	{
		vizinhos = new HashMap<Integer, Vertice>(v.vizinhos);
		id = v.id;
		visitado = v.visitado;
	}
	
	public void imprimeVertice()
	{
		System.out.println("ID do vértice: " + id);
		System.out.print("Vizinhos:");
		
		for (Vertice v : vizinhos.values())
			System.out.print(" " + v.id);
		
		System.out.println("");
		
		if (visitado == false)
			System.out.println("\nVértice não descoberto.");
		
		System.out.println("----------------");
	}
	
	protected void adicionaVizinho(Vertice v)
	{
		vizinhos.put(v.id, v);
	}
	
	protected int retornaGrauVertice()
	{
		return vizinhos.size();
	}
	
	public void reset()
	{
		visitado = false;
	}
	
	

}
