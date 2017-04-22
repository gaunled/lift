/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lift;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Lift extends javax.swing.JFrame implements Runnable{
 
    public static Random rand = new Random();
    public static int jmlLantai;
    public static ArrayList<Lantai> lantai;
    public static Semaphore semaphore = new Semaphore(1);
    
    private JLabel lift;
            
    public Lift() {
        initComponents();
        setSize(500, (jmlLantai*65) + 30);
        setResizable(false);
        setLocationRelativeTo(null);
        prepare();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("LIFT");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lift.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lift.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lift.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lift.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                jmlLantai = 5;
                jmlLantai = Integer.parseInt(JOptionPane.showInputDialog(null, "Masukkan jumlah lantai", "2"));
                new Lift().setVisible(true);
            }
        });
    }

    public void prepare() {
        for (int i = jmlLantai-1; i >= 0; i--) {
            JLabel temp = new JLabel("" + i);
            temp.setHorizontalAlignment(JLabel.CENTER);
            temp.setFont(new Font("Trebuchet MS", Font.BOLD, 40));
            temp.setForeground(Color.DARK_GRAY);
            temp.setBounds(20, ((jmlLantai-i-1)*65) + 10, 30, 40);
            add(temp);
        }
        
        lantai = new ArrayList();
        Thread th = new Thread(this);
        th.start();
    }
    
    @Override
    public void run() {
        Elevator elevator = new Elevator();
        for (int i = 0; i < jmlLantai; i++) {
            Lantai l = new Lantai(i);
            lantai.add(l);
            l.start();
        }
        elevator.start();
        while (true) {
            if (lantai.get(elevator.getCurrentlantai()).person.size() > 0) {
                elevator.person.add(lantai.get(elevator.getCurrentlantai()).person.get(0));
                lantai.get(elevator.getCurrentlantai()).person.remove(0);
                lantai.get(elevator.getCurrentlantai()).listlabel.remove(0);
                elevator.setTujuanlantai(elevator.person.get(0).akhir);
            }
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
            }
        }
    }
    
    class Lantai extends Thread{
        int lt;
        ArrayList<Person> person = new ArrayList();
        ArrayList<JLabel> listlabel = new ArrayList();

        public Lantai(int lt) {
            this.lt = lt;
        }
        
        @Override
        public void run() {
            while(true) {
                try {
                    semaphore.acquire();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Lift.class.getName()).log(Level.SEVERE, null, ex);
                }
                Person p = new Person("P"+person.size(), jmlLantai);
                JLabel lblperson = new JLabel(p.getId());
                lblperson.setBounds(210 + (person.size() * 15), (lt*65) + 20, 25, 25);
                lblperson.setIcon(new ImageIcon(p.getUrl()));
                lblperson.setOpaque(false);
                add(lblperson);
                listlabel.add(lblperson);
                person.add(p);
                repaint();
                semaphore.release();
                try {
                    Thread.sleep(rand.nextInt(10000) + 3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Lift.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        
    }
    
    class Elevator extends Thread{
        int count = 0;
        int currentlantai = 0;
        int tujuanlantai = 0;
        int arah = -1; //1 -> Naik, 0 -> Turun
        ArrayList<Person> person = new ArrayList();

        public Elevator() {
            lift = new JLabel();
            lift.setBounds(150, (jmlLantai-currentlantai-1)*65, 50, 65);
            lift.setOpaque(true);
            lift.setBackground(new Color(120, 120, 120));
            add(lift);
        }
        
        @Override
        public void run() {
            
            while(true) {
                if (tujuanlantai > currentlantai) {
                    currentlantai++;
                    arah = 1;
                } else if (tujuanlantai < currentlantai) {
                    currentlantai--;
                    arah = 0;
                } else {
                    arah = -1;
                }
                lift.setBounds(150, (jmlLantai-currentlantai-1)*65, 50, 65);
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
            }
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getCurrentlantai() {
            return currentlantai;
        }

        public void setCurrentlantai(int currentlantai) {
            this.currentlantai = currentlantai;
        }

        public int getTujuanlantai() {
            return tujuanlantai;
        }

        public void setTujuanlantai(int tujuanlantai) {
            this.tujuanlantai = tujuanlantai;
        }

        public int getArah() {
            return arah;
        }

        public void setArah(int arah) {
            this.arah = arah;
        }
        
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    }
}
