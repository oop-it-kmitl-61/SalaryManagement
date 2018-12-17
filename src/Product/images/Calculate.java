package Product.images;
import javax.swing.*;

public class Calculate {
    private JFrame fr;
    private JPanel p1, p2, p3;
    private JLabel label1, label2, label3;
    private JTextField tf1, tf2;
    private JButton bnCal;


    public void init(){
        fr = new JFrame("Calculate rectangle");
        p1 = new JPanel();
        p2 = new JPanel();
        p3 = new JPanel();

        label1 = new JLabel("Enter the wide value");
        label2 = new JLabel("Enter the long value");

        fr.pack();
        fr.setVisible(true);
    }

    public static void main(String[] args){
        Calculate cl = new Calculate();
        cl.init();
    }
}
