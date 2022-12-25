package Cizelgeleyici;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ProsesYonetici {
	public int varisZamani;
	public int oncelik;
	public int patlamaZamani;
	
	public int id;
	public int durum;	//Başlangıç mı yoksa yürütülyor mu kontrol değişkeni.
	
	public Process process;
	public ProsesYonetici() {
		durum=0;
	}
	
	public void ProsesBaslatildi(int saniye , ProsesYonetici p) throws IOException {
		List<String> params = java.util.Arrays.asList( "java", "-jar","ProManag.jar",Integer.toString(saniye),Integer.toString(p.id),
		Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"basladi");
		ProcessBuilder builder = new ProcessBuilder(params);
		process=builder.start();		
		Scanner scanner = new Scanner(process.getInputStream());
		while (scanner.hasNextLine()) {
		    System.out.println(scanner.nextLine());
		}
		
	}
	public void ProsesYurutuluyor(int saniye , ProsesYonetici p) throws IOException {
		
		List<String> params = java.util.Arrays.asList( "java", "-jar","ProManag.jar",Integer.toString(saniye),Integer.toString(p.id),
				Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"yurutuluyor");
				ProcessBuilder builder = new ProcessBuilder(params);
				process=builder.start();
				Scanner scanner = new Scanner(process.getInputStream());
				while (scanner.hasNextLine()) {
				    System.out.println(scanner.nextLine());
				}
		
	}
	public void ProsesAskida(int saniye , ProsesYonetici p, int kontrolId,int kontrolOncelik,int kontrolPatlama,int kontrolVaris) throws IOException {
		List<String> params = java.util.Arrays.asList( "java", "-jar","ProManag.jar",Integer.toString(saniye),Integer.toString(p.id),
				Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"askida");
				ProcessBuilder builder = new ProcessBuilder(params);
				process=builder.start();
				Scanner scanner = new Scanner(process.getInputStream());
				while (scanner.hasNextLine()) {
				    System.out.println(scanner.nextLine());
				}
		
	}
	public void ProsesSonlandi(int saniye , ProsesYonetici p) throws IOException {
		
		List<String> params = java.util.Arrays.asList( "java", "-jar","ProManag.jar",Integer.toString(saniye),Integer.toString(p.id),
				Integer.toString(p.oncelik),Integer.toString(p.patlamaZamani),"sonlandi");
				ProcessBuilder builder = new ProcessBuilder(params);
				process=builder.start();
				builder.command("asdasd");
				Scanner scanner = new Scanner(process.getInputStream());
				while (scanner.hasNextLine()) {
				    System.out.println(scanner.nextLine());
				}
			
		
	}
}
