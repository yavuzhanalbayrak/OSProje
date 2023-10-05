package Cizelgeleyici;

public class Proses {
	public int varisZamani;
	public int oncelik;
	public int patlamaZamani;
	
	public int id;
	public int durum;	//Başlangıç mı yoksa yürütülyor mu kontrol değişkeni.
	
	public Proses() {
		durum=0;
	}
	
	public void ProsesBaslatildi(int saniye , Proses p) {
		System.out.println(saniye+". saniye "+" Proses Başlatıldı (id:"+p.id+" Öncelik: "+p.oncelik+" Kalan Süre: "+p.patlamaZamani+")");
	}
	public void ProsesYurutuluyor(int saniye , Proses p) {
		System.out.println(saniye+". saniye "+" Proses Yürütülüyor (id:"+p.id+" Öncelik: "+p.oncelik+" Kalan Süre: "+p.patlamaZamani+")");
	}
	public void ProsesAskida(int saniye , Proses p, int kontrolId,int kontrolOncelik,int kontrolPatlama,int kontrolVaris) {
		System.out.println(saniye+". saniye "+" Proses Askıya Alındı (id:"+kontrolId+" Öncelik: "+(kontrolOncelik)+" Kalan Süre: "+(kontrolPatlama)+")");
	}
	public void ProsesSonlandi(int saniye , Proses p) {
		System.out.println(saniye+". saniye "+" Proses Sonlandı (id:"+p.id+" Öncelik: "+p.oncelik+" Kalan Süre: "+(p.patlamaZamani)+")");
	}
}
