/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lift;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Lift extends javax.swing.JFrame implements Runnable{

    public static final String UP = "UP";
    public static final String DOWN = "DOWN";
    
    
    public static int jmlLantai;
    public static ArrayList<Lantai> lantai;
    public static String arah;
    
    private JLabel lift;
            
    public Lift() {
        initComponents();
        setSize(500, (jmlLantai*65) + 40);
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
        
        arah = UP;
        
        lift = new JLabel();
        lift.setBounds(150, (jmlLantai-1)*65, 50, 65);
        lift.setOpaque(true);
        lift.setBackground(new Color(120, 120, 120));
        add(lift);
       
        lantai = new ArrayList();
        
        for(int i=0;i<jmlLantai;i++){ //inisialisasi lantai
            lantai.add(new Lantai());
        }
        
        Thread th = new Thread(this);
        th.start();
    }

    public void cycle() {
        if (arah.equals(UP)) {
            int newY = lift.getY() - 2;
            if (newY > 0) {
                lift.setBounds(150, lift.getY() - 10, 50, 65);
            } else {
                lift.setBounds(150, 0, 50, 65);
                arah = DOWN;
            }
        } else {
            int newY = lift.getY() + 2;
            if (newY < (jmlLantai-1)*65) {
                lift.setBounds(150, lift.getY() + 10, 50, 65);
            } else {
                lift.setBounds(150, (jmlLantai-1)*65, 50, 65);
                arah = UP;
            }
        }
    }
    
    @Override
    public void run() {
        SpawnPerson spawn = new SpawnPerson();
        spawn.start();
        while(true) {
            try {
                cycle();
                Thread.sleep(100);
            } catch (Exception e) {
            }
        }
    }
    
    class SpawnPerson extends Thread{
        int countPerson = 0;
        @Override
        public void run() {
            while(true) {                
                int spawn = (int) (Math.random()*jmlLantai);
                
                JLabel person = new JLabel(new ImageIcon("src/img/man.png"));
                person.setBounds(220 + (lantai.get(spawn).personCount * 20), ((jmlLantai-spawn-1)*65) + 10, 50, 50);
                
                lantai.get(spawn).person.add("p"+countPerson);
                lantai.get(spawn).personImage.add(person);
                lantai.get(spawn).personCount++;
                add(person);
                repaint();
                System.out.println("p"+countPerson+" at lantai "+spawn);
                countPerson++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Lift.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
    }
    
    public class Lantai {
        int personCount = 0;
        ArrayList<String> person = new ArrayList();
        ArrayList<JLabel> personImage = new ArrayList();
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
