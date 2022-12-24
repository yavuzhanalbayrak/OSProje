package Cizelgeleyici;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

public class Gorevlendirici {
	public Queue realTime = new LinkedList<Proses>();	//Gerçek zamanlı proses kuyruğu
	
	//Kullanıcı proses kuyruğu
	public Queue user1 = new LinkedList<Proses>();
	public Queue user2 = new LinkedList<Proses>();
	public Queue user3 = new LinkedList<Proses>();
	
	static int saniye;
	static int kontrolId = -1;
	static int kontrolVaris = -1;
	static int kontrolOncelik = -1;
	static int kontrolPatlama = -1;
	
	//Proses önceliğe göre kuyruklara eklenir.
	public void ekle(Proses p) {
		if(p.oncelik == 0) {
			realTime.add(p);
		}
		else if(p.oncelik == 1) {
			user1.add(p);
		}
		else if(p.oncelik == 2) {
			user2.add(p);
		}
		else {
			user3.add(p);
		}
	}
	
	public void calistir(Proses p) throws IOException {
		ProsesListesi plist=new ProsesListesi();
		Gorevlendirici gorevlendirici = new Gorevlendirici();
		Timer myTimer = new Timer();
		saniye=-1;
		
		TimerTask gorev = new TimerTask() {	//Zamanlayıcı başladı.
			//Her saniye tekrar eden döngü.
			@Override
			public void run() {
				for	(int i=0;i<plist.prosesler.size();i++) {	//Proses listesini gezen döngü.
					if(plist.prosesler.get(i).varisZamani == saniye) {	//Zamanı gelen proses görevlendirici tarafından kuyruklara atılır.
						gorevlendirici.ekle(plist.prosesler.get(i));	//kuyruğa ekleme.
						plist.prosesler.remove(i--);	//eklenen prosesi listeden silme.
					}
				}
				gorevlendirici.yazdir(gorevlendirici, p);
				
				//Proses listesinde ve kuyruklarda proses kalmaması durumunda görevlendirici çalışmayı durdurur.
				if(gorevlendirici.realTime.size() == 0 && gorevlendirici.user1.size() == 0 && gorevlendirici.user2.size() == 0 && gorevlendirici.user3.size() == 0 && plist.prosesler.size() == 0) 
				{
					System.out.print("Görevlendirici Sonlandı...");
					myTimer.cancel();
				}
				
			}
		};
		
		myTimer.schedule(gorev,0,100);
	}
	
	public void yazdir(Gorevlendirici gorevlendirici, Proses p) {
		
		
		if(gorevlendirici.realTime.size() != 0) {	//Gerçek zamanlı kuyruğunda proses varsa işleme alınır.(FCFS)
			p = (Proses) gorevlendirici.realTime.peek();
			
			if(saniye - p.varisZamani>=20) {	//20 saniyeyi geçen prosesler sonlanır.
				p.ProsesSonlandi(saniye--, p);
				gorevlendirici.realTime.remove();
				kontrolId=-1;	//Proses sonlandığından kontrol etmeye gerek yok.
			}
			else {
				if(p.id != kontrolId && kontrolId != -1) {	//Askıya alınan proses ekran çıktısı.
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris);
					kontrolId=-1;
				}
				if(p.durum == 0) {
					p.ProsesBaslatildi(saniye, p);
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {
					p.ProsesYurutuluyor(saniye, p);
					p.patlamaZamani--;
				}
				if(p.patlamaZamani==0) {
					try {	//prosesin bitmesi beklenir.
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					
					p.ProsesSonlandi(saniye+1, p);
					gorevlendirici.realTime.remove();
					kontrolId=-1;	//Proses sonlandığından kontrol etmeye gerek yok.
				}
			}
			
			
		}
		
		else if(gorevlendirici.user1.size() != 0) {	//Kullanıcı Proses kuyruğunun ilk kuyruğu
			p = (Proses) gorevlendirici.user1.poll();	//Proses kuyruktan alındı.
			
			if(saniye - p.varisZamani>=20) {
				p.ProsesSonlandi(saniye--, p);
				kontrolId = -1;
				}
			else {
				if(p.id != kontrolId && kontrolId != -1) {
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris);
					kontrolId=-1;
				}
				
				if(p.durum == 0) {	//Proses Başlatıldı
					p.ProsesBaslatildi(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {	//Proses yürütülüyor.
					p.ProsesYurutuluyor(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
				}
				if(p.patlamaZamani==0) {	//Proses, son saniyesindeyse;
					//proses bitene kadar beklenir bittikten sonra silinir.
					try {	//prosesin bitmesi beklenir.
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					p.ProsesSonlandi(saniye+1, p);
					kontrolId = -1;
				}
				else {
					gorevlendirici.user2.add(p);	//Proses bitmemişse bir alt kuyruğa atılır.
					
					//Proses Askıya alındı mı kontrol için bilgileri yedeklenir.
					kontrolId = p.id;
					kontrolOncelik=p.oncelik;
					kontrolVaris=p.varisZamani;
					kontrolPatlama=p.patlamaZamani;
				}
			}
			
			
		}
		
		else if(gorevlendirici.user2.size() != 0) {	//Kullanıcı Proses kuyruğunun ikinci kuyruğu
			p = (Proses) gorevlendirici.user2.poll();
			
			if(saniye - p.varisZamani>=20) {
				p.ProsesSonlandi(saniye--, p);
				kontrolId = -1;
				}
			else {

				if(p.id != kontrolId && kontrolId != -1) {
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris);
					kontrolId=-1;
				}
				
				if(p.durum == 0) {	//Proses Başlatıldı
					p.ProsesBaslatildi(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {
					p.ProsesYurutuluyor(saniye, p);
					p.oncelik++;
					p.patlamaZamani--;
				}
				if(p.patlamaZamani==0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					p.ProsesSonlandi(saniye+1, p);
					kontrolId = -1;
				}
				else {
					gorevlendirici.user3.add(p);	
					kontrolId = p.id;
					kontrolOncelik=p.oncelik;
					kontrolVaris=p.varisZamani;
					kontrolPatlama=p.patlamaZamani;
				}
			}
			
				
		}
		
		else if(gorevlendirici.user3.size() != 0) {	//Kullanıcı Proses kuyruğunun üçüncü kuyruğu
			
			p = (Proses) gorevlendirici.user3.poll();

			if(saniye - p.varisZamani>=20) {
				p.ProsesSonlandi(saniye--, p);
				kontrolId = -1;
				
				}
			else {
				if(p.id != kontrolId && kontrolId != -1) {
					p.ProsesAskida(saniye, p, kontrolId, kontrolOncelik, kontrolPatlama, kontrolVaris);
					kontrolId=-1;
				}
				
				if(p.durum == 0) {	//Proses Başlatıldı
					p.ProsesBaslatildi(saniye, p);
					p.patlamaZamani--;
					p.durum++;
				}
				else if(p.patlamaZamani != 0) {	//Proses çalıştıktan sonra tekrar kuyruğun sonuna atılır.
					p.ProsesYurutuluyor(saniye, p);
					p.patlamaZamani--;
				}
				
				
				if(p.patlamaZamani==0) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					p.ProsesSonlandi(saniye+1, p);
					kontrolId = -1;
				}
				else {
					gorevlendirici.user3.add(p);
					kontrolId = p.id;
					kontrolOncelik=p.oncelik;
					kontrolVaris=p.varisZamani;
					kontrolPatlama=p.patlamaZamani;
				}
				
			}
			
			
		}
		saniye++;
	}
	
}
