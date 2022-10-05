import javax.swing.*;
import java.awt.*;

public class labelString extends JLabel {

    labelString(String text){
        super();

        super.setText(text);
        super.setFont(new Font("Serif", Font.BOLD, 14));
        super.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }
}
