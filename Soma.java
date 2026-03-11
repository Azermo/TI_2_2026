import java.util.*;

public class Soma {
	
	public int d;
	public int c;
	
	
	public int somar() {
		
		return this.d + this.c;
	}

	public static void main(String[] args) {
		
		int resultado = 0;
		
		Soma s = new Soma();
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Digite um numero: ");
		s.c = scanner.nextInt();
		System.out.println("Digite outro numero: ");
		s.d = scanner.nextInt();
		
		resultado = s.somar();
		
		System.out.println(resultado);
		
	}

}

