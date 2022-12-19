package iot.gui.custom;

import iot.gui.custom.menu_bar.IDSMenuBar;

import javax.swing.*;
import java.awt.*;

public class ETFrame extends JFrame {

    public ETFrame() throws HeadlessException {
        super();

        this.init();
    }
    private void init() {
        this.setTitle("Sim IDS");
        this.setSize(700, 500);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screensize = toolkit.getScreenSize();
        this.setLocation(
                (screensize.width - super.getWidth()) / 2,
                (screensize.height - super.getHeight()) /2
        );

        this.setResizable(false);
        this.setDefaultCloseOperation(super.EXIT_ON_CLOSE);

        this.setJMenuBar(new IDSMenuBar());
    }

}
