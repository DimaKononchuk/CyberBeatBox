import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(int width, int height, String Text){
        super();
        super.setSize(width,height);
        super.setText(Text);
        super.setFont(new Font("Serif", Font.ITALIC, 18));



    }
}
