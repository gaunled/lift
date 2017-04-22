/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lift;

import java.util.Random;

public class Person {
    String id;
    String url;
    int awal;
    int akhir;
    boolean finish;

    public Person(String id, int jmllantai) {
        Random rand = new Random();
        this.id = id;
        this.awal = rand.nextInt(jmllantai);
        this.url = "src/img/man.png";
        do {
            this.akhir = rand.nextInt(jmllantai);
        } while (this.awal == this.akhir);
        this.finish = false;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    
    public int getAwal() {
        return awal;
    }

    public void setAwal(int awal) {
        this.awal = awal;
    }

    public int getAkhir() {
        return akhir;
    }

    public void setAkhir(int akhir) {
        this.akhir = akhir;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
    
    
}
