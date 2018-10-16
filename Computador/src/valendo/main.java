package valendo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import view.InterfaceReceptor;
import java.util.concurrent.TimeUnit;
import view.InterfaceReceptor;
import valendo.ReceiveLogsTopic;
/**
 *
 * @author Hericlys Sousa
 */
public class main {
    public static void main(String [] args){
        InterfaceReceptor a = new InterfaceReceptor();
        a.setVisible(true);
        while(a.isVisible()){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
            }
        }
        String d = a.IP;
        System.out.println(d);
        ReceiveLogsTopic j = new ReceiveLogsTopic(d);
        j.run();
    }
}
