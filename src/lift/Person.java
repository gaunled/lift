/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lift;

import java.util.Random;

public class Person {
    String id;
    String imageurl;
    int awal;
    int akhir;
    int x;
    boolean finish;

    public Person(String id, int jmllantai) {
        Random rand = new Random();
        this.id = id;
        this.imageurl = "img/Standing Man Filled-50.png";
        this.awal = rand.nextInt(jmllantai);
        do {
            this.akhir = rand.nextInt(jmllantai);
        } while (this.awal == this.akhir);
        this.finish = false;
        this.x = 20;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }
    
    
}
